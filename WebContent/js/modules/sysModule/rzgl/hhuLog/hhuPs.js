var menuId = '53300';
var hhuPsGrid = '';
var grid_url = ctx +'/system/rzgl/hhuLog!queryHhuPs.do';

Ext.onReady(function() {	
	var _cm = [{
		header : system.logMgt.hhuLog.hhuPs.grid.col.verId,
		dataIndex : 'VERID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.pm_type,
		dataIndex : 'PM_TYPE',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.cate_name,
		dataIndex : 'CATE_NAME',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.obis,
		dataIndex : 'OBIS',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.optid,
		dataIndex : 'OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.op_type,
		dataIndex : 'OP_TYPE',
		width : 120	
	}, {
		header : system.logMgt.hhuLog.hhuPs.grid.col.op_date,
		dataIndex : 'OP_DATE',
		width : 120
	}];
	
	hhuPsGrid = new Wg.Grid( {
		url : grid_url,
		el : 'hhuPsGrid',
		title : system.logMgt.hhuLog.hhuPs.grid.title,
		heightPercent : 0.8,
		cModel : _cm
	});
	var logId = $F('logId');
	var p = {	
		logId : logId
	};
	hhuPsGrid.init(p);
	 
});