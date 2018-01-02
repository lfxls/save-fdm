var woLogGrid = '';
var grid_url = ctx + '/main/insMgt/orderLog!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130
	},{
		header: main.insMgt.order.grid.col.type,
		dataIndex : 'TNAME',
		width : 120
	},{
		header: common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
	},{
		header: main.insMgt.orderLog.grid.col.OPDate,
		dataIndex: 'OPDATE',
		width: 150
	},{
		header: main.insMgt.order.grid.col.pop,
		dataIndex : 'OPTNAME',
		width : 120
	},{
		dataIndex: 'STATUS',
		hidden : true,
		width: 100
	},{
		dataIndex: 'OPTID',
		hidden : true,
		width: 100
	}];
	
	 woLogGrid = new Wg.Grid( {
		url : grid_url,
		el : 'woLogGrid',
		title : main.insMgt.orderLog.grid.title,
		heightPercent : 0.95,
		widthPercent : 1.03,
		margin : 60,
		cModel : _cm
	});
	 
	var p = {
		woid : $F('woid')
	};
	woLogGrid.init(p);
});

//查询
/*function query()
{
	if(!$E($F('woid'))) {
		if(!/^\d+$/.test($F('woid'))){
			Wg.alert(main.insMgt.order.valid.workOrderNo.formatCheck);
			return;
		}
	}
	var p = $FF('woLogForm');
	woLogGrid.reload(p);
}*/

//工单状态下拉
/*function changeStatus(){
	query();
}

function queryPop() {
	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.pop,
		el : 'selPOP',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}*/

/*function logReset() {
	$('woid').value = '';
	$('status').value = '';
	$('popName').value = '';
	$('popid').value = '';
	$('startDate').value = '';
	$('endDate').value = '';
}*/

/*function showWO(woid,startDate) {
	var url = String.format(ctx + '/main/insMgt/insOrder!init.do?woid={0}&startDate={1}',woid,startDate);
	top.openpageOnTree(main.insMgt.order.win.title.wo, '123000', main.insMgt.order.win.title.wo, null, url, 'yes', 1);
}*/
