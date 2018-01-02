var menuId = '12600';
var woQueryGrid = '';
var grid_url = ctx + '/main/insMgt/orderQuery!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = '';
			if(record.data.WOID != '') {
				url = String.format('<a href="javascript:showWOLog(\'{0}\');">'+record.data.WOID+'</a>&nbsp;&nbsp;',
						record.data.WOID);
			}
			return url;
		}
	},{
		header: main.insMgt.order.grid.col.type,
		dataIndex : 'TYPENM',
		width : 120
	},{
		header: common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
	},{
		header: main.insMgt.order.grid.col.pop,
		dataIndex : 'OPTNAME',
		width : 120
	},{
		header: main.insMgt.order.grid.col.teamName,
		dataIndex : 'TNAME',
		width : 120
	},{
		header: main.insMgt.order.grid.col.createDate,
		dataIndex: 'CDATE',
		width: 150
	},{
		header: main.insMgt.order.grid.col.finishTime,
		dataIndex: 'FTIME',
		width: 150
	},{
		header: main.insMgt.order.grid.col.cop,
		dataIndex: 'COPTNAME',
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
	
	 woQueryGrid = new Wg.Grid( {
		url : grid_url,
		el : 'woQueryGrid',
		title : main.insMgt.orderQuery.grid.title,
		heightPercent : 0.7,
		widthPercent : 1,
		margin : 60,
		cModel : _cm
	});
	 
	var p = $FF('woQueryForm');
	woQueryGrid.init(p);
});

//查询
function query()
{
//	if(!$E($F('woid'))) {
//		if(!/^\d+$/.test($F('woid'))){
//			Wg.alert(main.insMgt.order.valid.workOrderNo.formatCheck);
//			return;
//		}
//	}
	var p = $FF('woQueryForm');
	woQueryGrid.reload(p);
}

//工单状态下拉
function changeStatus(){
	query();
}

//工单状态下拉
function changeType(){
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
}

function woReset() {
	$('woid').value = '';
	$('type').value = '';
	$('status').value = '';
	$('popName').value = '';
	$('popid').value = '';
	$('startDate').value = '';
	$('endDate').value = '';
}

/**
 * 查询工单日志
 * @param woid
 */
function showWOLog(woid) {
	var url = String.format(ctx + '/main/insMgt/orderLog!init.do?woid={0}',woid);
	var baseParam = {
		url : url,
		title : main.insMgt.orderLog.title,
		el : 'woOPLog',
		width : 800,
		height : 450,
		draggable : true
	};
	
	var woOPLogWin = new Wg.window(baseParam); 
	woOPLogWin.open();
}