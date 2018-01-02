var menuId = '12400';
var setPGrid = '';
var grid_url = ctx + '/main/insMgt/insFb!querySetParam.do';
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
		header : main.insMgt.fb.grid.col.setResult,
		dataIndex : 'RSTN',
		width : 120
	} ,{
		header : main.insMgt.fb.grid.col.resultValue,
		dataIndex : 'VALUE',
		width : 120
	}];
	
	setPGrid = new Wg.Grid( {
		url : grid_url,
		el : 'setPGrid',
		title : main.insMgt.fb.grid.title.setParam,
		heightPercent : 0.82,
		cModel : _cm
	});
	
	var p = {
		pid : $F('pid'),	
		msn : $F('msn'),
		flag : $F('flag'),	
		verid : $F('verid')	
	};
	setPGrid.init(p);
	 
});