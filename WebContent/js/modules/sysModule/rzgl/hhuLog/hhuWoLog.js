var hhuWoLogGrid = '';
var menuId = '53300';
var grid_url = ctx + '/system/rzgl/hhuLog!query.do';

Ext.onReady(function() {
	handlerType = 'query';
	hideLeft();//隐藏左边树
	
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="soubiao" title="\{1}\" href="javascript:openWoWin(\'{0}\');">'+'</a>',
					record.data.LOGID, system.logMgt.hhuLog.wo.grid.col.wo);
			return url;
		}
	},{
		header : system.logMgt.hhuLog.wo.grid.col.reqId,
		dataIndex : 'REQID',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.opt_type,
		dataIndex : 'OPT_TYPE',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.optId,
		dataIndex : 'OPTID',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.hhuID,
		dataIndex : 'HHUID',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.upt_way,
		dataIndex : 'UPT_WAY',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.opt_rst,
		dataIndex : 'OPT_RST',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.err_msg,
		dataIndex : 'ERR_MSG',
		width : 120
	},{
		header : system.logMgt.hhuLog.wo.grid.col.op_date,
		dataIndex : 'OP_DATE',
		width : 160
	},{
		dataIndex : 'LOGID',
		hidden : true
	}];

	hhuWoLogGrid = new Wg.Grid({
		url : grid_url,
		el : 'hhuWoLogGrid',
		title : system.logMgt.hhuLog.wo.grid.title,
		heightPercent : 0.75,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn : true,
		checkOnly : false
	});
	
	var p = $FF('hhuWoLogForm');
	
	//grid数据初始
	hhuWoLogGrid.init(p);// （是否选择、是否单选）
});

//查询
function query() {
	var p=$FF('hhuWoLogForm');
	hhuWoLogGrid.reload(p);
}

/**
 * 查看工单信息
 */
function openWoWin(logId){
	var url = ctx + String.format('/system/rzgl/hhuLog!initHhuWo.do?logId={0}', logId);
	var baseParam = {
		url : url,
		title : system.logMgt.hhuLog.wo.grid.title.wo,
		el : 'hhuWoLogParam',
		width : 900,
		height : 550,
		draggable : true
	};
	
	paramWin = new Wg.window(baseParam); 
	paramWin.open();
}

function Reset(){
	$('optId').value="";
	$('hhuId').value="";
	$('opt_rst').value="";
	$('startDate').value="";
	$('endDate').value="";
	$('opt_type').value="";
}