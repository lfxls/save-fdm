var menuId = '53300';
var hhuTfGrid = '';
var grid_url = ctx +'/system/rzgl/hhuLog!queryHhuTf.do';

Ext.onReady(function() {	
	var _cm = [{
		header : system.logMgt.hhuLog.hhutf.grid.col.tfid,
		dataIndex : 'TFID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhutf.grid.col.tfname,
		dataIndex : 'TFNAME',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhutf.grid.col.optId,
		dataIndex : 'OPTID',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhutf.grid.col.op_type,
		dataIndex : 'OP_TYPE',
		width : 120
	}, {
		header : system.logMgt.hhuLog.hhutf.grid.col.op_date,
		dataIndex : 'OP_DATE',
		width : 120
	}];
	
	hhuTfGrid = new Wg.Grid( {
		url : grid_url,
		el : 'hhuTfGrid',
		title : system.logMgt.hhuLog.hhutf.grid.title,
		heightPercent : 0.8,
		cModel : _cm
	});
	var logId = $F('logId');
	var p = {
		logId : logId
	};
	hhuTfGrid.init(p);
	 
});