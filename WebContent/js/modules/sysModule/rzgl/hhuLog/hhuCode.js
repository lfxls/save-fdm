var menuId = '53300';
var hhuCodeGrid = '';
var grid_url = ctx +'/system/rzgl/hhuLog!queryHhuCode.do';

Ext.onReady(function() {	
	var _cm = [{
		header : system.logMgt.hhuLog.hhuCode.grid.col.code_name,
		dataIndex : 'CODE_NAME',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuCode.grid.col.name,
		dataIndex : 'NAME',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuCode.grid.col.lang,
		dataIndex : 'LANGUAGE',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuCode.grid.col.optid,
		dataIndex : 'OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhuCode.grid.col.op_type,
		dataIndex : 'OP_TYPE',
		width : 120	
	}, {
		header : system.logMgt.hhuLog.hhuCode.grid.col.op_date,
		dataIndex : 'OP_DATE',
		width : 120
	}];
	
	hhuCodeGrid = new Wg.Grid( {
		url : grid_url,
		el : 'hhuCodeGrid',
		title : system.logMgt.hhuLog.hhuCode.grid.title,
		heightPercent : 0.8,
		cModel : _cm
	});
	var logId = $F('logId');
	var p = {	
		logId : logId
	};
	hhuCodeGrid.init(p);	 
});