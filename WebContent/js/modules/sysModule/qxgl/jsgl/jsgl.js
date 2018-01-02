var menuId = '52200';
var cd_tree_url = ctx + '/system/qxgl/jsgl!getJsCdTree.do';
var cz_tree_url = ctx + '/system/qxgl/jsgl!getJsCzTree.do';
var grid_url = ctx + '/system/qxgl/jsgl!query.do';
var sy_tree_url = ctx + '/system/qxgl/jsgl!getJsSyTree.do';
//var dy_tree_url = ctx + '/system/qxgl/jsgl!getJsDyTree.do';
var JsCdTree = '';
var JsCzTree = '';
var JsSyTree = '';
var JsGrid = '';
var h1 = 0.65;
Ext.onReady(function() {
	hideLeft();
	
	//解决左侧菜单收缩时，菜单Grid宽度失去控制，做一个延时加载表格
	//setTimeout("init()",200);
	init();
});

function init(){
	JsCdTree = new Wg.Tree( {
		url : cd_tree_url,
		el : 'cdtree',
		title : jsgl_cdTree_title,
		height : screen.height * h1,
		width : 300,
		rootVisible : false,
		isCheckTree:true,
		border : true
	});
	JsCdTree.init('root', 'root', 'cd', function(treeloader, node) {
		this.baseParams = {
			sjcdbm : node.id,
			jsid : $E($F('jsid')) ? 'root' : $F('jsid')
		};
	});

	JsCzTree = new Wg.Tree( {
		url : cz_tree_url,
		el : 'cztree',
		title : jsgl_czTree_title,
		height : screen.height * h1,
		width : 270,
		rootVisible : false,
		isCheckTree:true,
		border : true
	});
	
	JsCzTree.init('root', 'root', 'cz', function(treeloader, node) {
		this.baseParams = {
			jsid : $E($F('jsid')) ? 'root' : $F('jsid')
		};
	});

	JsSyTree = new Wg.Tree( {
		url : sy_tree_url,
		el : 'sytree',
		title : jsgl_jsSyTree_mainPage,
		height : screen.height * h1,
		width : 270,
		rootVisible : false,
		isCheckTree:true,
		border : true
	});
	JsSyTree.init('root', 'root', 'sy', function(treeloader, node) {
		this.baseParams = {
			jsid : $E($F('jsid')) ? 'root' : $F('jsid')
		};
	});
	
//	JsDyTree = new Wg.Tree( {
//		url : dy_tree_url,
//		el : 'dytree',
//		title : jsgl_jsSyTree_dy,
//		height : screen.height * h1,
//		width : 220,
//		rootVisible : false,
//		isCheckTree:true,
//		border : true
//	});
//	JsDyTree.init('root', 'root', 'dy', function(treeloader, node) {
//		this.baseParams = {
//			jsid : $E($F('jsid')) ? 'root' : $F('jsid')
//		};
//	});
	
	var _cm = [{
		header : jsgl_cm_op,
		dataIndex : 'OP',
		width : 60,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a  class="del" href="javascript:delJs(\'{0}\');" title="'+jsgl_grid_del+'"></a>',
							record.data.JSID);
			return url;
		}
		},{
			header : jsgl_cm_jsmc,
			dataIndex : 'JSMC',
			width : 130,
			sortable : true
		},
//		{
//			header : jsgl_cm_cdubz,
//			dataIndex : 'CDUBZMC',
//			width : 90,
//			sortable : true
//		},{
//			dataIndex : 'CDUBZ',
//			hidden : true
//		},
//		{
//			dataIndex : 'RES_TYPE',
//			hidden:true
//		},
		{
			dataIndex : 'JSID',
			hidden : true
		} ];
	
	var toolbar = [{
        id: 'add-verion-buton',
		text:jsgl_tbar_add,
        tooltip:jsgl_tbar_add,
        iconCls:'add',
        handler: function(){
        	addJs();
        }
    }];
	
	JsGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		sort : 'JSID',
		title : jsgl_grid_title,
//		height: screen.height * h1 - 100,
		height: screen.height * 0.615 - 100,
		widthPercent : 0.27,
		tbar:toolbar,
		cModel : _cm,
		canResize:false
	});
	JsGrid.init( {},true,true);
	JsGrid.grid.getSelectionModel().addListener('rowselect', function(sm, rowIndex, re) {
		$('jsid').value = re.get('JSID');
		$('jsmc').value = re.get('JSMC');
//		var cdubz= re.get('CDUBZ');
		//选中状态
//		if(cdubz=='01'){
//			document.getElementById('cduBz').checked=true;
//		}else{
//			document.getElementById('cduBz').checked=false;
//		}
//		document.getElementById('electricity').checked=false;
//		document.getElementById('water').checked=false;
//		document.getElementById('gas').checked=false;
//		var resType = re.get('RES_TYPE');
/*		var resTypes = resType.split(",");
		
		for(var i = 0 ; i < resTypes.length; i++){
			if(resTypes[i] == '01'){
				document.getElementById('gas').checked=true;}
			if(resTypes[i] == '02'){
				document.getElementById('water').checked=true;}
			if(resTypes[i] == '03'){
				document.getElementById('electricity').checked=true;}
		}*/
		JsCdTree.reload();
		JsCzTree.reload();
		JsSyTree.reload();
		//JsDyTree.reload();
	});
	
	/*JsGrid.grid.getSelectionModel().addListener('rowdeselect', function(sm, rowIndex, re) {
		alert('deselect---');
		$('jsmc').value='';
		$('jsid').value = 'root';
		JsCdTree.reload();
		JsCzTree.reload();
		JsSyTree.reload();
		JsDyTree.reload();
	});*/
	
}

function addJs(){
	$('jsmc').focus();
	$('jsmc').value='';
	$('jsid').value = 'root';
//	document.getElementById('cduBz').checked=true;
//	document.getElementById('electricity').checked=true;
//	document.getElementById('water').checked=true;
//	document.getElementById('gas').checked=true;
	JsCdTree.reload();
	JsCzTree.reload();
	JsSyTree.reload();
	//JsDyTree.reload();
	
	JsGrid.sm.clearSelections();
}

function saveJs(){
	var recs = JsGrid.grid.getSelectionModel().getSelections(); 
	//默认false
//	var cdubz = '02';
//	if(document.getElementById('cduBz').checked == true){
//		cdubz = '01';
//	}
	
	//新增
	if(recs.length==0){
//		insJs(cdubz);
		insJs();
	}else{
	//编辑
		//获取当前选中的记录
		var jsid = recs[0].get('JSID');
//		updJs(jsid,cdubz);
		updJs(jsid);
		
	}
}

// 新增角色
function insJs() {
	var jsmc = $F('jsmc');
	if($E(jsmc)) {
		Ext.Msg.alert(ExtTools_alert_title,
				jsgl_add_null_jsname,function(btn,text){
			if(btn=='ok'){
				$('jsmc').focus();
			}
		});
		return;
	}
	var cdids = getMenusFromTree();
	if(!cdids) return;
	
	var czids = getCzFromTree();
	if(!czids) return;
	
	var syids = getSyFromTree();
	if(!syids) return;
	
//	var xxbms = getDyFromTree();
//	if(!xxbms) return;
	
//	var resType='';
//	if(document.getElementById('gas').checked){
//		resType += "01,";
//	}
//	if(document.getElementById('water').checked){
//		resType += '02,';
//	}
//	if(document.getElementById('electricity').checked){
//		resType += '03,';
//	}
//	
//	resType = resType.substring(0, resType.length-1);
	var p = {
		jsmc : jsmc,
		cdids : cdids,
		czids : czids,
		syids : syids,
//		xxbms:  xxbms,
//		cdubz:cdubz,
//		resType:resType,
		logger : jsgl_add_logger + jsmc
	};
	
	Wg.confirm(jsgl_add_confirm, function() {
		
		dwrAction.doDbWorks('jsglAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {					
					JsGrid.reload();
					$('jsmc').value='';
					$('jsid').value='';				
				}
			});
		});
	});
	
	/*Wg.confirm(jsgl_add_confirm, function() {
		Ext.Msg.wait(
			jsgl_add_null_wait,
			jsgl_add_null_ts,{
				interval : 10,   //每次进度的时间间隔
				duration : 1000 * 1000, //进度条跑动的持续时间
				increment : 300   //进度条的增量，这个值设的越大，进度条跑的越慢，不能小于1，如果小于1，进度条会跑出范围

			}
		);
		dwrAction.doDbWorks('jsglAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					// 隐藏进度条提示
					Ext.Msg.hide();
					JsGrid.reload();
					$('jsmc').value='';
					$('jsid').value='';
				}
			});
		});
	});*/
}

// 从树中取得勾选节点
function getMenusFromTree() {
	var ids = JsCdTree.getCheckChildren();
	if(ids && ids.length > 0) {
		return getMenuId(ids);
	} else {
		Wg.alert(jsgl_add_null_jscd);
		return false;
	}
}	


function getMenuId(ids) {
	var v = '';
	var idArray = ids.split(",");
	for(var i=0;i<idArray.length;i++) {
		var node = JsCdTree.tree.getNodeById(idArray[i]);
		if(node.getDepth() > 1) { // 回溯父节点
			var parentNodeId = node.parentNode.id;
			if(idArray.indexOf(parentNodeId) < 0) {
				idArray.push(parentNodeId);
			}
		}
	}
	for(var k =0;k<idArray.length;k++) {
		v += idArray[k] + ',';
	}
	return v.substring(0,v.length-1);
}


// 从树中取得勾选节点
function getCzFromTree() {
	var ids = JsCzTree.getCheckChildren();
	if(ids && ids.length > 0) {
		return ids;
	} else {
		Wg.alert(jsgl_add_null_jscz);
		return false;
	}
}	

//从树中取得勾选节点 首页
function getSyFromTree() {
	var ids = JsSyTree.getCheckChildren();
	if (ids==null) {
		return true;
	}
	if(ids && ids.length > 0) {
		return ids;
	} 
}

//从树中取得勾选节点 订阅
function getDyFromTree() {
	var ids = JsDyTree.getCheckChildren();
	if (ids==null) {
		return true;
	}
	if(ids && ids.length > 0) {
		return ids;
	} 
}

// 修改角色
function updJs(jsid) {
	var jsmc = $F('jsmc');
	if($E(jsmc)) {
		Ext.Msg.alert(ExtTools_alert_title,
				jsgl_add_null_jsname,function(btn,text){
			if(btn=='ok'){
				$('jsmc').focus();
			}
		});
		return;
	}
	
	var cdids = getMenusFromTree();
	if(!cdids) return;
	
	var czids = getCzFromTree();
	if(!czids) return;
	
	var syids = getSyFromTree();
	if(!syids) return;
	
//	var xxbms = getDyFromTree();
//	if(!xxbms) return;
	
//	var resType='';
//	if(document.getElementById('gas').checked){
//		resType += "01,";
//	}
//	if(document.getElementById('water').checked){
//		resType += '02,';
//	}
//	if(document.getElementById('electricity').checked){
//		resType += '03,';
//	}
//	
//	resType = resType.substring(0, resType.length-1);
	
	var p = {
		jsmc:jsmc,
		jsid : jsid,
		cdids : cdids,
		czids : czids,
		syids : syids,
//		xxbms: xxbms,
//		cdubz:cdubz,
//		resType:resType,
		logger : jsgl_upd_logger + jsmc+'('+jsid+')'
	};
	
	Wg.confirm(jsgl_upd_confirm, function() {
		
		dwrAction.doDbWorks('jsglAction', menuId + '02', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {					
					JsGrid.reload();
					addJs();				
				}
			});
		});
	});
	
	/*Wg.confirm(jsgl_upd_confirm, function() {
		Ext.Msg.wait(
			jsgl_add_null_wait,
			jsgl_add_null_ts,{
				interval : 10,   //每次进度的时间间隔
				duration : 1000 * 1000, //进度条跑动的持续时间
				increment : 300   //进度条的增量，这个值设的越大，进度条跑的越慢，不能小于1，如果小于1，进度条会跑出范围

			}
		);
		
		dwrAction.doDbWorks('jsglAction', menuId + '02', p, function(res) {
			// 隐藏进度条提示
			Ext.Msg.hide();
			Wg.alert(res.msg);
			JsGrid.reload();
			addJs();
		});
	});*/
}

// 删除角色
function delJs(jsid) {
	var p = {
		jsid : jsid,
		logger : jsgl_del_logger + jsid
	};
	
	Wg.confirm(jsgl_del_confirm, function() {
		dwrAction.doAjax('jsglAction','jsIsUse',{jsid : jsid},function(res){
			if(res.success && res.msg == ''){
				dwrAction.doDbWorks('jsglAction', menuId + '03', p, function(res) {
					Wg.alert(res.msg, function() {
						if (res.success) {
							$('jsmc').value='';
							JsGrid.reload();
							$('jsid').value = 'root';
							JsCdTree.reload();
							JsCzTree.reload();
						}
					});
				});
			} else {
				Wg.alert(jsgl_use_undel);
			}
		});
	});
}