var menuId = '53300';
var hhuTokenGrid = '';
var grid_url = ctx +'/system/rzgl/hhuLog!queryHhuToken.do';

Ext.onReady(function() {	
	var _cm = [{
		header : system.logMgt.hhuLog.hhuToken.grid.col.token,
		dataIndex : 'TOKEN',
		width : 200
	}, {
		header : system.logMgt.hhuLog.hhuToken.grid.col.msn,
		dataIndex : 'MSN',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuToken.grid.col.optid,
		dataIndex : 'OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuToken.grid.col.op_type,
		dataIndex : 'OP_TYPE',
		width : 120	
	}, {
		header : system.logMgt.hhuLog.hhuToken.grid.col.op_date,
		dataIndex : 'OP_DATE',
		width : 120
	}];
	
	hhuTokenGrid = new Wg.Grid( {
		url : grid_url,
		el : 'hhuTokenGrid',
		title : system.logMgt.hhuLog.hhuToken.grid.title,
		heightPercent : 0.8,
		cModel : _cm
	});
	var logId = $F('logId');
	var p = {
		logId : logId
	};
	hhuTokenGrid.init(p);	 
});