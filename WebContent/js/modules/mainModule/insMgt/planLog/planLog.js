var planLogGrid = '';
var grid_url = ctx + '/main/insMgt/planLog!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130
	},{
		header: common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
	},{
		header: main.insMgt.planLog.grid.col.devType,
		dataIndex : 'DEVTYPE',
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
	
	 planLogGrid = new Wg.Grid( {
		url : grid_url,
		el : 'planLogGrid',
		title : main.insMgt.planLog.grid.title,
		heightPercent : 0.95,
		widthPercent : 1.03,
		margin : 60,
		cModel : _cm
	});
	 
	var p = {
		pid : $F('pid')
	};
	planLogGrid.init(p);
});

//查询
//function query()
//{
//	if(!$E($F('pid'))) {
//		if(!/^\d+$/.test($F('pid'))){
//			Wg.alert(main.insMgt.plan.valid.pid.formatCheck);
//			return;
//		}
//	}
//	var p = $FF('planLogForm');
//	planLogGrid.reload(p);
//}
//
////安装计划状态下拉
//function changeStatus(){
//	query();
//}
//
////设备类型下拉
//function changeDevType(){
//	query();
//}
//
//function queryPop() {
//	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
//	var baseParam = {
//		url : url,
//		title : main.insMgt.order.win.title.pop,
//		el : 'selPOP',
//		width : 660,
//		height : 550,
//		draggable : true
//	};
//	
//	selWin = new Wg.window(baseParam); 
//	selWin.open();
//}
//
//function logReset() {
//	$('pid').value = '';
//	$('status').value = '';
//	$('devType').value = '';
//	$('popName').value = '';
//	$('popid').value = '';
//	$('startDate').value = '';
//	$('endDate').value = '';
//}