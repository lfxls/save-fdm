var menuId = '12200';
var custGrid = '';
var grid_url = ctx + '/main/insMgt/insUnPlan!queryCust.do';
Ext.onReady(function() {	
	var _cm = [{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120,
		sortable : true
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	}, {
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120
	}, {
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		dataIndex : 'UID',
		hidden : true	
	}];
	
	var toolbar = [{
		id : 'select',
		text : main.insMgt.unPlan.grid.tb.select,
		tooltip : main.insMgt.unPlan.grid.tb.select,
		iconCls : 'select',
		handler : function() {
			selectCust();
		}
	}];
	
	custGrid = new Wg.Grid( {
		url : grid_url,
		el : 'custGrid',
		title : main.insMgt.plan.grid.title.custList,
		heightPercent : 0.73,
		cModel : _cm,
		tbar : toolbar
	});
	
	var p = $FF('custForm');
	custGrid.init(p,true,false);
	 
});

// 查询
function query() {
	var p = $FF('custForm');
	custGrid.reload(p);
}

//选择用户
function selectCust(){
	var records = custGrid.getRecords();
	if(!records || records.length == 0) {
		Wg.alert(main.insMgt.unPlan.valid.selCust);
		return;
	}
	var cnos = "";
	for(var i = 0; i < records.length; i++) {
		if(i == 0) {
			cnos = records[i].data.CNO;
		} else {
			cnos = cnos + "," + records[i].data.CNO;
		}
	}
	if($F('operateType') == "01") {//新增情况下，选择用户
		if($E(parent.$('cnos').value)) {
			parent.$('cnos').value = cnos;
		} else {
			parent.$('cnos').value = parent.$('cnos').value + "," + cnos;
		}
	} else if($F('operateType') == "02"){//编辑情况下，选择用户
		if($E(parent.$('ecnos').value)) {
			parent.$('ecnos').value = cnos;
		} else {
			parent.$('ecnos').value = parent.$('ecnos').value + "," + cnos;
		}
	}
	parent.query();
	parent.win.close();
}

/**
 * 获取单位树
 * @returns
 */
function getDwTree() {
	var	title = common.kw.other.PU;
	getTree('dw', title, unitCode, unitName, 'dw');
}