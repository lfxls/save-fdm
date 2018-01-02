Ext.namespace("ycl.Widgets");
// Ext Tree by ycl 2009/10
Wg = ycl.Widgets;
/*
 * _cfg = {url:_URL,el:'treeDiv',limit:10,height:450,width:300,border:false,rootVisible:true}
 */
Wg.PagingTree = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
				tree : '',
				selectedNode : ''
			});
}

Ext.apply(Wg.PagingTree.prototype, {
	init : function(_rootId, _rootText, _viewType, _yhlx) { // 初始化
		var obj = this;
		var _root = new Ext.tree.AsyncTreeNode({
					id : _rootId,
					text : _rootText,
					viewType : _viewType,
					yhlx : _yhlx,
					pages:true,
					expanded : true
				});
		var _url = this.url;
		var _limit = this.limit ? this.limit : 30; // pageSize
		var _loader = new Ext.tree.TreeLoader({
					url : _url
				});
		
		_loader.on('beforeload', function(treeLoader, node) {
					if (Ext.isEmpty(node) || node.isLeaf())
						return false;
					var _isPaging = node.attributes.pages;
					var _nodeType = node.attributes.ndType;
					var _viewType = node.attributes.viewType;
					var _yhlx = node.attributes.yhlx;
					
					this.baseParams =  {
							nodeId : node.id,
							nodeType : _nodeType,
							viewType : _viewType,
							yhlx: _yhlx
					};
					if(_viewType =='dw' || _viewType =='sb') { //电网 设备
						Ext.apply(this.baseParams,{dwlx: node.attributes.dwlx});
					} else if(_viewType =='qz') { //群组
						
					} else if(_viewType =='cx') { //查询
					}

					if (!_isPaging) { // 不分页
						return;
					}
					
					var _total = node.attributes.total;

					if(!_total) {
						Ext.apply(this.baseParams,{count: true});
						_total = getResponseText(_url,this.baseParams); // 取子节点总数
					}
					if (!_total || _total == 0) return false;
					
					node.attributes.total = _total;

					var _start = node.attributes.start;
					if (Ext.isEmpty(_start)) {
						_start = 0;
						node.attributes.start = _start;
					}

					var _end = node.attributes.end;
					if (Ext.isEmpty(_end)) {
						_end = _start + _limit;
					}
					if (_end > _total) {
						_end = _total;
					}

					var text = node.text;
					var pos = text.lastIndexOf("("); // 更换页面分页信息
					if (!Ext.isEmpty(pos) && pos > 0) {
						text = text.substr(0, text.lastIndexOf("("));
					}

					if (_total > _limit) {
						node.setText(text + "(" + (_start + 1) + "~" + _end + "/"
								+ _total + ")");
					} else {
						if(_nodeType == 'xl' || _nodeType == 'cx' || _nodeType == 'qz') //线路、群组、查询显示线路下用户数
						node.setText(text + "(" + _total + ")");
						var p = {
								count: false
							};
						Ext.apply(this.baseParams, p);
						return;
					}

					var p = {
						start : _start,
						end : _end,
						pages : _isPaging,
						count: false
					};
					Ext.apply(this.baseParams, p);
				});

		this.tree = new Ext.tree.TreePanel({
			title : this.title,
			el : this.el,
			root : _root,
			loader : _loader,
			border : this.border ? this.border : false,
			width : this.width ? this.width : 320,
			height : this.height ? this.height : 600,
			useArrows : true,
			autoScroll : true,
			animate : true,
			split : true,
			rootVisible : this.rootVisible,
			tbar : [{
				iconCls:'first',
				tooltip : ExtTree_first,
				handler : function() {
					obj.jumpPage('first');
				}
			}, {
				iconCls:'prev',
				tooltip : ExtTree_prev,
				handler : function() {
					obj.jumpPage('prev');
				}
			}, {
				iconCls:'next',
				tooltip : ExtTree_next,
				handler : function() {
					obj.jumpPage('next');
				}
			}, {
				iconCls:'last',
				tooltip : ExtTree_last,
				handler : function() {
					obj.jumpPage('last');
				}
			}, '-', {
				xtype : 'numberfield',
				id : 'pageTo',
				allowBlank : false,
				value : 1,
				minValue : 1,
				width : 25
			}, {
				iconCls:'refresh',
				tooltip : ExtTree_refresh,
				handler : function() {
					obj.jumpPage('to', Ext.getCmp('pageTo').value);
				}
			}]
		});

		this.tree.on('click', function(_node, event) {
					obj.selectedNode = _node;
				});
		this.tree.render();
	},
	getRoot: function() {
		return this.tree.getRootNode();
	},
	getSelectNode : function() {
		if (this.selectedNode) {
			return this.selectedNode;
		}
	},
	reload : function() {
		var root = this.tree.getRootNode();
		root.reload();
	},
	jumpPage : function(page, to) {
		if (!this.selectedNode)
			return;
		var selectedNode = this.selectedNode;
		var _isPaging = selectedNode.attributes.pages;
		var isLeaf = selectedNode.isLeaf();
		if (Ext.isEmpty(selectedNode) || !_isPaging || isLeaf){
			return; // 必须选中节点
		}

		var start = selectedNode.attributes.start; // 起始行
		var limit = this.limit ? this.limit : 30; // 每页显示记录数
		var total = selectedNode.attributes.total;
		
		//如果不是第一页才判断total和limit的关系  zhouh
		if(to != 1){
			if(total < limit) return;
		}
		 
		if (Ext.isEmpty(start) || page == 'first') { // 第一页
			start = 0;
		} else if (page == 'prev') { // 前一页
			start = start - limit;
		} else if (page == 'next') { // 下一页
			start = start + limit;
		} else if (page == 'last') { // 最后页
			start = Math.floor(total / limit) * limit;
		} else if (page == 'to') {
			start = limit * to - limit;
		}

		if(start < 0) return;
		if(start >= total) return;
		//start = start == total ? total - 1 : start;
		
		var end = start + limit;
		end = end > total ? total : end;

		selectedNode.attributes.start = start;
		selectedNode.attributes.end = end;
		selectedNode.reload();
	},
	removeAll : function() {
		var root = this.tree.getRootNode();
		var _child = root.firstChild;
		while (_child) {
			_child.remove();
			_child = root.firstChild;
		}
	}
});


function getResponseText(_url, _param) {
	var http_request = false;
	if (window.XMLHttpRequest) { // Mozilla 浏览器
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {// 设置MiME类别
			http_request.overrideMimeType('text/xml');
		}
	} else if (window.ActiveXObject) { // IE浏览器
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	if (!http_request) { // 异常，创建对象实例失败
		return false;
	}
	var res = '';
	http_request.open('POST', _url, false);
	http_request.setRequestHeader("CONTENT-TYPE",
			"application/x-www-form-urlencoded");
	
	if(Ext.isIE) {
		http_request.onreadystatechange = function() {
			if (http_request.readyState == 4) {
				if (http_request.status == 200) {
					try {
						res = http_request.responseText;
						Wg.removeLoading();
						return;
					} catch (e) {
						return;
					}
				} 
			}
		};
	} else {
		http_request.onload=function() {
			try {
				res  = http_request.responseText;
				Wg.removeLoading();
				return;
			} catch (e) {
				return;
			}
         };
	}
	var pp = '';
	if (_param) {
		pp=Ext.urlEncode(_param);
	}
	http_request.send(Ext.isEmpty(pp) ? null : pp);
	return res;
};

Ext.QuickTips.init();
