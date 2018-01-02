var hhuDuLogGrid = '';
var menuId = '53300';
var grid_url = ctx + '/system/rzgl/hhuLog!queryHhuDuLog.do';

Ext.onReady(function() {
	handlerType = 'query';
	hideLeft();//隐藏左边树
	
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="soubiao" title="\{1}\" href="javascript:openDuWin(\'{0}\');">'+'</a>',
					record.data.LOGID, system.logMgt.hhuLog.du.grid.col.du);
			return url;
		}
	},{
		header : system.logMgt.hhuLog.du.grid.col.reqId,
		dataIndex : 'REQID',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.optId,
		dataIndex : 'OPTID',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.hhuID,
		dataIndex : 'HHUID',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.upt_way,
		dataIndex : 'UPT_WAY',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.upt_rst,
		dataIndex : 'UPT_RST',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.err_msg,
		dataIndex : 'ERR_MSG',
		width : 120
	},{
		header : system.logMgt.hhuLog.du.grid.col.upt_date,
		dataIndex : 'UPT_DATE',
		width : 160
	},{
		dataIndex : 'LOGID',
		hidden : true
	}];

	hhuDuLogGrid = new Wg.Grid({
		url : grid_url,
		el : 'hhuDuLogGrid',
		title : system.logMgt.hhuLog.du.grid.title,
		heightPercent : 0.75,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn : true,
		checkOnly : false
	});
	
	var p = $FF('hhuDuLogForm');
	
	//grid数据初始
	hhuDuLogGrid.init(p);// （是否选择、是否单选）
});

//查询
function query() {
	var p=$FF('hhuDuLogForm');
	hhuDuLogGrid.reload(p);
}

/**
 * 查看基础数据信息
 */
function openDuWin(logId){
	var url = ctx + String.format('/system/rzgl/hhuLog!initHhuTf.do?logId={0}', logId);
	var baseParam = {
		url : url,
		title : system.logMgt.hhuLog.du.grid.title.du,
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
	$('upt_rst').value="";
	$('startDate').value="";
	$('endDate').value="";
}