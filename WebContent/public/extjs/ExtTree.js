Ext.namespace("ycl.Widgets");
// Ext Tree by ycl 2009/10
Wg = ycl.Widgets;
/*
 * _cfg =
 * {url:_URL,el:'treeDiv',height:450,width:300,isCheckTree:true,border:false,rootVisible:true}
 */
Wg.Tree = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
				tree : '',
				selectedNode : ''
			});
};
Ext.apply(Wg.Tree.prototype, {
			init : function(_rootId, _rootText, _rootType, load_fn) { // 初始化
				var obj = this;
				var _root = new Ext.tree.AsyncTreeNode({
							id : _rootId,
							text : _rootText,
							ndType : _rootType,
							expanded : true
						});

				var _loader = new Ext.tree.TreeLoader({
							url : this.url
						});
				
				//传递参数
				var load_fn = load_fn ? load_fn : function(treeloader, node) {
					this.baseParams = {
							nodeId : node.id,
							nodeType : node.attributes.ndType,
							objectValue :node.attributes.objectValue
					};
				};
				
				_loader.on('beforeload', load_fn);
				
				this.margin = this.margin? this.margin : 0;
				this.widthPercent = this.widthPercent? this.widthPercent : 1.00;
				this.tree = new Ext.tree.TreePanel({
							title : this.title,
							el : this.el,
							root : _root,
							loader : _loader,
							margin : obj.margin,
							widthPercent : obj.widthPercent ? obj.widthPercent : 1,
							border : this.border ? this.border : false,
							width : obj.width ? obj.width : Ext.getBody().getWidth()*obj.widthPercent - obj.margin,
							height : this.height ? this.height : 440,
							resize : function(){					
								var realWidth = Ext.getBody().getWidth() * obj.widthPercent - obj.margin;
								this.setWidth(realWidth);
							},
							rootVisible : this.rootVisible
									? this.rootVisible
									: false,
							useArrows : true,
							autoScroll : true,
							containerScroll:true,
							animate : true
						});
				
				if(window.attachEvent){
					if(!obj.width){
						window.attachEvent('onresize', function(){
							obj.tree.resize();
						})
					}
				}else{
					if(!obj.width){
						window.addEventListener('resize', function(){
							obj.tree.resize();
						})
					}
				}
				
				if (this.isCheckTree) {
					this.tree.on('checkchange', function(node, checked) {
								node.expand();
								node.attributes.checked = checked;
								node.eachChild(function(child) {
											child.ui.toggleCheck(checked);
											child.attributes.checked = checked;
											child.fireEvent('checkchange',
													child, checked);
										});
							}, this.tree);
					//this.tree.expandAll();
				}

				this.tree.on('click', function(_node, event) {
							obj.selectedNode = _node;
						});
				this.tree.render();
			},
			getRoot: function() {
				return this.tree.getRootNode();
			},
			reload : function(p) {
				var root = this.tree.getRootNode();
				if (root) {
					root.reload(p);
				}
			},
			getSelectNode : function() {
				return this.selectedNode;
			},
			removeNode : function() {
				var node = this.selectedNode;
				if (node) {
					node.remove();
					this.selectedNode = '';
				}
			},
			addNode : function(_node) {
				var node = this.selectedNode;
				if (node && !node.isLeaf()) {
					node.appendChild(_node);
					node.expand(true);
				}
			},
			removeChildren : function() {
				var node = this.selectedNode;
				if (node && !node.isLeaf() && node.expanded) {
					var _child = node.firstChild;
					while (_child) {
						_child.remove();
						_child = node.firstChild;
					}
				} else {
					Ext.Msg.alert("Warning", Wg_Tree_removeChildren_alert);
				}
			},
			expandAll : function() {
				this.tree.expandAll();
			},
			getCheckChildren : function() {
				if (this.isCheckTree) {
					var o = this.tree.getChecked('id');
					var ids = '';
					for(var i=0;i<o.length;i++) {
						ids += o[i] + ',';
					}
					if(!Ext.isEmpty(ids)) {
						return ids.substring(0,ids.length-1);
					} else {
						return null;
					}
				}
			},
			removeAll : function() {
				var root = this.tree.getRootNode();
				var _child = root.firstChild;
				while (_child) {
					_child.remove();
					_child = root.firstChild;
				}
			},
			rootAddNode : function(obj) {
				var root = this.tree.getRootNode();
				root.expand(true);
				if (obj && obj.length > 0) {
					for (var i = 0; i < obj.length; i++) {
						root.appendChild(new Ext.tree.TreeNode(obj[i]));
					}
				} else {
					Ext.Msg.alert("Warning", Wg_Tree_rootAddNode_alert);
				}
			}
		});
