var menuId = '52401';
var grid_url = ctx + '/system/rzgl/darz!query.do';
var grid = '';

Ext.onReady(function() {
	hideLeft();
	var _cm = [ {
		header : darz_cm_dwmc,
		dataIndex : 'DWMC',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.DWMC+'</a>&nbsp;&nbsp;',
					record.data.DWDM);
			return url;
		}
	},{
		header : darz_cm_czy,
		dataIndex : 'CZY',
		width : 120
	},{
		header : darz_cm_cdmc,
		dataIndex : 'CDMC',
		width : 120,
		sortable : true
	},{
		header : darz_cm_czlb,
		dataIndex : 'CZLB',
		width : 150,
		sortable : true
	},{
		header : darz_cm_czmc,
		dataIndex : 'CZMC',
		width : 150,
		sortable : true
	},{
		header : darz_cm_czsj,
		dataIndex : 'CZSJ',
		width : 150,
		sortable : true
	},{
		id:'RZNR',
		header : darz_cm_rznr,
		dataIndex : 'RZNR',
		width : 250,
		sortable : true
	},{
		dataIndex : 'CDID',
		hidden : true
	},{
		dataIndex : 'DWDM',
		hidden : true
	}];
	
	grid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		sort : 'CDID',
		title : darz_grid_title,
		heightPercent : 0.75,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		autoExpandColumn:'RZNR'
	});
	
	/**dwrAction.doAjax('darzAction','getMenu',{root : $F('root')},function(){ DEL PANC 2012/6/4 改修
		var p = $FF('daform');
		grid.init(p);
	});**/
	
	var p = $FF('daform');
	grid.init(p);
	
});

/**  DEL PANC 2012/6/4 改修
function getMenu() {
	var p = {root : $F('root')};
	dwrAction.doAjax('darzAction','getMenu',p);
}
**/

//查询
function query() {
	var p = $FF('daform');
	Wg.showLoading();
	grid.ds.on('load',function(){
	   Wg.removeLoading();
	}); 
	grid.reload(p);
}

//树
function getTree() {
	var	title = darz_cd_bt;
	var url = String.format(ctx + '/system/rzgl/darz!getTree.do','');
	var baseParam = {
		url : url,
		title : title,
		el : 'treeDiv',
		width : 350,
		height : 430,
		draggable : true
	};
	cdWin = new Wg.window(baseParam);
	cdWin.open();
}
function Reset(){
	$('cdmc').value="";
	$('czyId').value="";
	$('rznr').value="";
	$('kssj').value="";
	$('jssj').value="";
	$('menuId').value="";
		
}
