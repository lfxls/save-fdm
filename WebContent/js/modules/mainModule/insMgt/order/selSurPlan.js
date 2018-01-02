var selSurPGrid = '';
var surP_url = ctx + '/main/insMgt/insOrder!querySPlan.do';
Ext.onReady(function() {
	var _cm = [{
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
		header : common.kw.other.Mobile,
		dataIndex : 'MNO',
		width : 130
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
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'WIR',
		hidden : true		
	},{
		dataIndex : 'PID',
		hidden : true		
	}];

	var surPToolbar = [{
			id : 'add',
			text : main.insMgt.order.grid.tb.add,
			tooltip : main.insMgt.order.grid.tb.add,
			iconCls : 'add',
			handler : function() {
				selSurP();
			}
		}];

	selSurPGrid = new Wg.Grid({
		url : surP_url,
		el : 'selSurPGrid',
		title : main.insMgt.plan.grid.title.survyP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : surPToolbar
	});
	
	var p = $FF('selSurPForm');
	
	//grid数据初始
	selSurPGrid.init(p, true, false);// （是否选择、是否单选）
});


/**
 * 查询
 */
function query(){
	var p = $FF('selSurPForm');
	selSurPGrid.reload(p);	
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
function selSurP() {
	var record = selSurPGrid.getRecords();
	if(!record || record.length ==0){
		Wg.alert(main.insMgt.order.valid.selSurP);
		return;
	}
	var zh = '';
	for(var i = 0; i < record.length; i++) {
		var oValue =
		record[i].data.PID + ',' +
		record[i].data.CNO + ',' +
		record[i].data.CNAME + ',' +
		record[i].data.ADDR + ',' +
		record[i].data.MNO + ',' +
		record[i].data.UID + ',' +
		record[i].data.UNAME + ',' +
		record[i].data.STATUS;
		if(i == 0) {
			zh = oValue;
		} else {
			zh = zh + ";" + oValue;
		}
	}
	if(parent.$('sszh').value == '') {
		parent.$('sszh').value = zh;
	} else {
		parent.$('sszh').value = parent.$('sszh').value + ';' + zh;
	}
	parent.surPQuery();
	parent.win.close();
}