var menuId = '53300';
var hhuWoGrid = '';
var grid_url = ctx +'/system/rzgl/hhuLog!queryHhuWo.do';

Ext.onReady(function() {	
	var _cm = [{
		header : system.logMgt.hhuLog.hhuwo.grid.col.woid,
		dataIndex : 'WOID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.type,
		dataIndex : 'TYPE',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.status,
		dataIndex : 'STATUS',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.p_optId,
		dataIndex : 'P_OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.tname,
		dataIndex : 'TNAME',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.c_optid,
		dataIndex : 'C_OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuwo.grid.col.c_date,
		dataIndex : 'C_DATE',
		width : 120
	}];
	
	hhuWoGrid = new Wg.Grid( {
		url : grid_url,
		el : 'hhuWoGrid',
		title : system.logMgt.hhuLog.hhuwo.grid.title,
		heightPercent : 0.8,
		cModel : _cm
	});
	var logId = $F('logId');
	var p = {
		logId : logId
	};
	hhuWoGrid.init(p);
	 
});