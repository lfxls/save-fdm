loadProperties('sysModule', 'sysModule_paramData');
var menuId = '54200';
var menuId = '22200';
var grid_url = ctx + '/system/paramData/paramMgt!queryTestDetail.do';
var testGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : system.paramData.paramMgt.testDetail.grid.col.itemId,
		dataIndex : 'OBIS',
		width : 160,
		
	},{
		header : system.paramData.paramMgt.testDetail.grid.col.itemName,
		dataIndex : 'ITEM_NAME',
		width : 200,
	},{
		header : system.paramData.paramMgt.testDetail.grid.col.FHINT,
		dataIndex : 'FHINT',
		width : 200,
	},{
		header : system.paramData.paramMgt.testDetail.grid.col.sort,
		dataIndex : 'SORT',
		width : 100,
	}];
	
	testGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : system.paramData.paramMgt.testDetail.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	testGrid.init(p, false, false);
});

function query(){
	var p=$FF('queryForm');
	testGrid.reload(p);
}