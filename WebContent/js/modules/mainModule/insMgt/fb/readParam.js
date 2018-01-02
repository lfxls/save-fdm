var menuId = '12400';
var readPGrid = '';
var grid_url = ctx + '/main/insMgt/insFb!queryReadParam.do';
Ext.onReady(function() {	
	var _cm = [{
		header : main.insMgt.fb.grid.col.obis,
		dataIndex : 'OBIS',
		width : 180,
		sortable : true
	}, {
		header : main.insMgt.fb.grid.col.obisName,
		dataIndex : 'ITEMNAME',
		width : 280
	}, {
		header : main.insMgt.fb.grid.col.readResult,
		dataIndex : 'RST',
		width : 230
	}, {
		dataIndex : 'HC',
		width : 5
	}];
	
	readPGrid = new Wg.Grid( {
		url : grid_url,
		el : 'readPGrid',
		title : main.insMgt.fb.grid.title.readParam,
		heightPercent : 0.82,
		cModel : _cm
	});
	
	var p = {
		pid : $F('pid'),	
		msn : $F('msn'),
		flag : $F('flag'),	
		verid : $F('verid')	
	};
	readPGrid.init(p);
	 
});