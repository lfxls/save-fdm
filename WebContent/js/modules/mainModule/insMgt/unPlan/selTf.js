var menuId = '12100';
var tfGrid = '';
var grid_url = ctx + '/main/insMgt/insUnPlan!queryTf.do';
Ext.onReady(function() {
	var _cm = [{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		sortable : true
	}, {
		header :common.kw.transformer.TFID,
		dataIndex : 'TFID',
		width : 120
	}, {
		header :common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 120
	}, {
		header :main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
		width : 120
	}];
	
	var toolbar = [{
		id : 'select',
		text : main.insMgt.unPlan.grid.tb.select,
		tooltip : main.insMgt.unPlan.grid.tb.select,
		iconCls : 'select',
		handler : function() {
			selectTf();
		}
	}];
	
	var p = $FF('tfFrom');
	tfGrid = new Wg.Grid( {
		url : grid_url,
		el : 'tfGrid',
		title : main.insMgt.plan.grid.title.tfList,
		heightPercent : 0.83,
		cModel : _cm,
		tbar : toolbar
	});
	
	tfGrid.init(p,true,false);
});

/**
 * 选择变压器
 */
function selectTf() {
	var records = tfGrid.getRecords();
	if(!records || records.length == 0) {
		Wg.alert(main.insMgt.unPlan.valid.selTf);
		return;
	}
	var tfIds = "";
	for(var i = 0; i < records.length; i++) {
		if(i == 0) {
			tfIds = records[i].data.TFID;
		} else {
			tfIds = tfIds + "," + records[i].data.TFID;
		}
	}
	if($F('operateType') == "01") {//新增情况下，选择用户
		if($E(parent.$('tfIds').value)) {
			parent.$('tfIds').value = tfIds;
		} else {
			parent.$('tfIds').value = parent.$('tfIds').value + "," + tfIds;
		}
	} else if($F('operateType') == "02"){//编辑情况下，选择用户
		if($E(parent.$('etfIds').value)) {
			parent.$('etfIds').value = tfIds;
		} else {
			parent.$('etfIds').value = parent.$('etfIds').value + "," + tfIds;
		}
	}
	parent.query();
	parent.win.close();
}

//查询变压器
function query() {
	var p = $FF('tfFrom');
	tfGrid.reload(p);
}

/**
 * 获取单位树
 * @returns
 */
function getDwTree() {
	var	title = common.kw.other.PU;
	getTree('dw', title, unitCode, unitName, 'dw');
}