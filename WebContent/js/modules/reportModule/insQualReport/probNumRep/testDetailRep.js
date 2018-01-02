loadProperties('reportModule', 'reportModule_insQualReport');
var menuId = '22200';
var grid_url = ctx + '/report/insQualReport/probNumRep!queryTestDetail.do';
var testGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : report.insQualReport.testDetailRep.grid.col.itemId,
		dataIndex : 'OBIS',
		width : 160,
		
	},{
		header : report.insQualReport.testDetailRep.grid.col.itemName,
		dataIndex : 'ITEM_NAME',
		width : 120,
	},{
		header : report.insQualReport.testDetailRep.grid.col.RESULT,
		dataIndex : 'RST',
		width : 110,
	},{
		header : report.insQualReport.testDetailRep.grid.col.VALUE,
		dataIndex : 'VALUE',
		width : 200
	}];
	
	testGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insQualReport.testDetailRep.grid.title,
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