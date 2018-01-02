var menuId = '12400';
var testPGrid = '';
var testOBISGrid = '';
var tp_url = ctx + '/main/insMgt/insFb!queryTestParam.do';
var to_url = ctx + '/main/insMgt/insFb!queryTestOBIS.do';
Ext.onReady(function() {	
	var tp_cm = [{
		header : main.insMgt.fb.grid.col.testId,
		dataIndex : 'TIID',
		width : 100,
		sortable : true
	},{
		header : main.insMgt.fb.grid.col.testName,
		dataIndex : 'TINAME',
		width : 180,
		sortable : true
	}, {
		header : main.insMgt.fb.grid.col.testRst,
		dataIndex : 'TRSTN',
		width : 280
	}, {
		dateIndex : 'TRST',
		hidden : true
	}, {
		dateIndex : 'PID',
		hidden : true
	}, {
		dateIndex : 'MSN',
		hidden : true
	}];
	
	var to_cm = [{
		header : main.insMgt.fb.grid.col.obis,
		dataIndex : 'OBIS',
		width : 180,
		sortable : true
	}, {
		header : main.insMgt.fb.grid.col.obisName,
		dataIndex : 'ITEMNAME',
		width : 280
	}, {
		header : main.insMgt.fb.grid.col.obisOPRst,
		dataIndex : 'ORSTN',
		width : 100
	}, {
		header : main.insMgt.fb.grid.col.resultValue,
		dataIndex : 'OPVALUE',
		width : 100
	}];
	
	testPGrid = new Wg.Grid( {
		url : tp_url,
		el : 'testPGrid',
		title : main.insMgt.fb.grid.title.testParam,
		heightPercent : 0.4,
		cModel : tp_cm
	});
	
	testOBISGrid = new Wg.Grid( {
		url : to_url,
		el : 'testOBISGrid',
		title : main.insMgt.fb.grid.title.testOBIS,
		heightPercent : 0.4,
		cModel : to_cm
	});
	
	var p = {
		pid : $F('pid'),	
		msn : $F('msn'),
		flag : $F('flag'),	
		verid : $F('verid')	
	};
	
	testPGrid.init(p);
	testPGrid.grid.addListener('rowclick', function(grid, rowindex, e) {
		var re = grid.getStore().getAt(rowindex);
		var tiid = re.get('TIID');
		var p ={
				tiid:tiid,
				pid:$F('pid'),
				msn:$F('msn'),
				verid:$F('verid')
		};
		testOBISGrid.reload(p);
	});
	testOBISGrid.init({});
});