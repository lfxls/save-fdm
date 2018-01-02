var selMInsPGrid = '';
var meterP_url = ctx + '/main/insMgt/insOrder!queryMPlan.do';
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
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 100
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 130
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
		header : main.insMgt.plan.grid.col.newMeterNo,
		dataIndex : 'NMSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldMeterNo,
		dataIndex : 'OMSN',
		width : 100
	},{
		header : common.kw.meter.MType,
		dataIndex : 'MTNAME',
		width : 100
	},{
		header : common.kw.meter.MCMethod,
		dataIndex : 'WNAME',
		width : 130
	},{
		header : main.insMgt.plan.grid.col.meterMode,
		dataIndex : 'MMNAME',
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
		dataIndex : 'MMODE',
		hidden : true			
	},{
		dataIndex : 'MTYPE',
		hidden : true			
	},{
		dataIndex : 'WIR',
		hidden : true		
	},{
		dataIndex : 'PID',
		hidden : true		
	}];

	var meterPToolbar = [{
			id : 'add',
			text : main.insMgt.order.grid.tb.add,
			tooltip : main.insMgt.order.grid.tb.add,
			iconCls : 'add',
			handler : function() {
				selMInsP();
			}
		}];

	selMInsPGrid = new Wg.Grid({
		url : meterP_url,
		el : 'selMInsPGrid',
		title : main.insMgt.plan.grid.title.meterInsP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : meterPToolbar
	});
	
	var p = $FF('selMInsPForm');
	
	//grid数据初始
	selMInsPGrid.init(p, true, false);// （是否选择、是否单选）
});


/**
 * 查询
 */
function query(){
	var p = $FF('selMInsPForm');
	selMInsPGrid.reload(p);	
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
function selMInsP() {
	var record = selMInsPGrid.getRecords();
	if(!record || record.length ==0){
		Wg.alert(main.insMgt.order.valid.selInsP);
		return;
	}
	var zh = '';
	for(var i = 0; i < record.length; i++) {
		var oValue = record[i].data.BUSSTYPE + ',' +
		record[i].data.PID + ',' +
		record[i].data.CNO + ',' +
		record[i].data.CNAME + ',' +
		record[i].data.UID + ',' +
		record[i].data.UNAME + ',' +
		record[i].data.TFID + ',' +
		record[i].data.TFNAME + ',' +
		record[i].data.MTYPE + ',' +
		record[i].data.WIR + ',' +
		record[i].data.MMODE + ',' +
		record[i].data.NMSN + ',' +
		record[i].data.OMSN + ',' +
		record[i].data.STATUS;
		if(i == 0) {
			zh = oValue;
		} else {
			zh = zh + ";" + oValue;
		}
	}
	if(parent.$('mszh').value == '') {
		parent.$('mszh').value = zh;
	} else {
		parent.$('mszh').value = parent.$('mszh').value + ';' + zh;
	}
	parent.mInsPQuery();
	parent.win.close();
}