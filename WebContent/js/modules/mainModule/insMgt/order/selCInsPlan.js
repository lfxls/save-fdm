var selCInsPGrid = '';
var grid_url = ctx + '/main/insMgt/insOrder!queryCPlan.do';
Ext.onReady(function() {
	var _cm = [{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 120
    },{
		header : common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
    },{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.UNAME+'</a>&nbsp;&nbsp;',
					record.data.UID);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.devAddress,
		dataIndex : 'ADDR',
		width : 130
	},{
		header : main.insMgt.plan.grid.col.newDcuNo,
		dataIndex : 'NCSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldDcuNo,
		dataIndex : 'OCSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.colModel,
		dataIndex : 'CMNAME',
		width : 100
	},{
		header :  main.insMgt.plan.grid.col.updTime,
		dataIndex : 'UTIME',
		width : 140
	},{
		header :  main.insMgt.plan.grid.col.operator,
		dataIndex : 'OPR',
		width : 140
	},{
		dataIndex : 'STATUS',
		hidden : true
    },{
		dataIndex : 'BUSSTYPE',
		hidden : true
    },{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'COLM',
		hidden : true			
	},{
		dataIndex : 'PID',
		hidden : true		
	}];

	var dToolbar = [{
			id : 'select',
			text : main.insMgt.order.grid.tb.select,
			tooltip : main.insMgt.order.grid.tb.select,
			iconCls : 'select',
			handler : function() {
				selCInsP();
			}
		}];

	selCInsPGrid = new Wg.Grid({
		url : grid_url,
		el : 'selCInsPGrid',
		title : main.insMgt.plan.grid.title.colInsP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : dToolbar
	});
	
	var p = $FF('selCInsPForm');
	
	//grid数据初始
	selCInsPGrid.init(p, true, false);// （是否选择、是否单选）
});


/**
 * 查询
 */
function query(){
	var p = $FF('selCInsPForm');
	selCInsPGrid.reload(p);	
}

/**
 * 获取单位树
 * @returns
 */
function getDwTree() {
	var	title = common.kw.other.PU;
	getTree('dw', title, unitCode, unitName, 'dw');
}

/**
 * 选择表安装计划
 */
function selCInsP() {
	var record = selCInsPGrid.getRecords();
	if(!record || record.length ==0){
		Wg.alert(main.insMgt.order.valid.selInsP);
		return;
	}
	var zh = '';
	for(var i = 0; i < record.length; i++) {
		var oValue = record[i].data.BUSSTYPE + ',' +
		record[i].data.PID + ',' +
		record[i].data.UID + ',' +
		record[i].data.UNAME + ',' +
		record[i].data.TFID + ',' +
		record[i].data.TFNAME + ',' +
		record[i].data.ADDR + ',' +
		record[i].data.COLM + ',' +
		record[i].data.NCSN + ',' +
		record[i].data.OCSN;
		if(i == 0) {
			zh = oValue;
		} else {
			zh = zh + ";" + oValue;
		}
	}
	if(parent.$('cszh').value == '') {
		parent.$('cszh').value = zh;
	} else {
		parent.$('cszh').value = parent.$('cszh').value + ';' + zh;
	}
	parent.cInsPQuery();
	parent.win.close();
}