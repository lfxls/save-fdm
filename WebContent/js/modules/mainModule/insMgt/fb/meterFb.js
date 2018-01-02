var meterFbGrid = '';
var menuId = '12200';
var meterFb_url = ctx + '/main/insMgt/insFb!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	
	var _cm = [{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryPlan(\'{0}\');">'+record.data.PID+'</a>&nbsp;&nbsp;',
					record.data.PID);
			return url;
		}
    },{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = '';
			if(record.data.WOID != '') {
				url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>&nbsp;&nbsp;',
						record.data.WOID);
			}
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 100
    },{
		header : main.insMgt.plan.grid.col.btStatus,
		dataIndex : 'PLNSTANAME',
		width : 120
    },{
		header : main.insMgt.fb.grid.col.dataType,
		dataIndex : 'DTNAME',
		width : 120
    },{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
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
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 140
	},{
		header : main.insMgt.plan.grid.col.newMeterNo,
		dataIndex : 'NMSN',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openShowWin(\'{0}\',\'{1}\',\'{2}\',\'{3}\');">'+record.data.NMSN+'</a>&nbsp;&nbsp;',
					record.data.PID,record.data.NMSN,'0',record.data.VERID);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.oldMeterNo,
		dataIndex : 'OMSN',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openShowWin(\'{0}\',\'{1}\',\'{2}\',\'{3}\');">'+record.data.OMSN+'</a>&nbsp;&nbsp;',
					record.data.PID,record.data.OMSN,'1','');
			return url;
		}
	},{
		header : common.kw.terminal.Collector,
		dataIndex : 'CSN',
		width : 100
	},{
		header : main.insMgt.fb.grid.col.clType,
		dataIndex : 'CLTYPE',
		width : 100
	},{
		header : common.kw.other.Longitude,
		dataIndex : 'LON',
		width : 130
	},{
		header : common.kw.other.Latitude,
		dataIndex : 'LAT',
		width : 130
	},{
		header :  main.insMgt.fb.grid.col.ct,
		dataIndex : 'CT',
		width : 80
	},{
		header :  main.insMgt.fb.grid.col.pt,
		dataIndex : 'PT',
		width : 80
	},{
		header :  main.insMgt.fb.grid.col.sealID,
		dataIndex : 'SEALID',
		width : 140
	},{
		header :  common.kw.sim.NO,
		dataIndex : 'SIMNO',
		width : 140
	},{
		header : main.insMgt.fb.grid.col.mBoxID,
		dataIndex : 'BOXID',
		width : 140
	},{
		header : common.kw.operator.optname,
		dataIndex : 'XM',
		width : 140
	},{
		header : common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 140
	},{
		header : main.insMgt.fb.grid.col.remark,
		dataIndex : 'REMARK',
		width : 140
	},{
		header : main.insMgt.fb.grid.col.opDate,
		dataIndex : 'OPDATE',
		width : 140
	},{
		header : main.insMgt.fb.grid.col.fbDate,
		dataIndex : 'FBDATE',
		width : 140
	},{
		header : common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 140
	},{
		dataIndex : 'UID',
		hidden : true,
		width : 140
	},{
		dataIndex : 'VERID',
		hidden : true,
		width : 100
	}];

	meterFbGrid = new Wg.Grid({
		url : meterFb_url,
		el : 'meterFbGrid',
		title : main.insMgt.fb.grid.title.meterFb,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false
	});
	
	var p = $FF('meterFbForm');
	
	//grid数据初始
	meterFbGrid.init(p);// （是否选择、是否单选）
});

/**
 * 树过滤
 * @param _node
 * @returns {Boolean}
 */
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType != 'dw'){
		Wg.alert(main.insMgt.plan.valid.selPU);
		return;
	}
	 
	$('nodeId').value = _node.id;
	$('nodeType').value = nodeType;
	$('nodeDwdm').value = _node.attributes.dwdm;
	$('nodeText').value = _node.attributes.text;
	
	query();
}

/**
 * 查询
 */
function query(){
//	if ($E($F('nodeId'))) {
//		Wg.alert(main.insMgt.plan.valid.PUNotEmpty);
//		return;
//	}
//	if(!$E($F('pid'))) {
//		if(!/^\d+$/.test($F('pid'))){
//			Wg.alert(main.insMgt.plan.valid.pid.formatCheck);
//			return;
//		}
//	}
	var p = $FF('meterFbForm');
	meterFbGrid.reload(p);	
}

/**
 * 处理状态下拉框
 * @param status
 */
function changeStatus(status){
	query();
}

/**
 * 业务类型下拉框
 * @param bussType
 */
function changeBType(bussType){
	query();
}

/**
 * 安装计划状态下拉框
 * @param plnStatus
 */
function changePStatus(plnStatus){
	query();
}

/**
 * 数据类型下拉框
 * @param dataType
 */
function changeDType(dataType){
	query();
}

/**
 * 查看表参数反馈信息
 */
function openShowWin(pid,msn,flag,verid){
	var url = ctx + String.format('/main/insMgt/insFb!initMPhoto.do?pid={0}&msn={1}&flag={2}&verid={3}', 
			pid,msn,flag,verid);
	var baseParam = {
		url : url,
		title : main.insMgt.fb.win.title.mParamFb,
		el : 'meterParam',
		width : 700,
		height : 580,
		draggable : true
	};
	
	paramWin = new Wg.window(baseParam); 
	paramWin.open();
}

/**
 * 重置
 */
function fbReset() {
	$('nodeText').value = '';
	$('nodeType').value = '';
	$('nodeId').value = '';
	$('nodeDwdm').value = '';
	$('cno').value = '';
	$('status').value = '';
	$('bussType').value = '';
	$('dataType').value = '';
	$('plnStatus').value = '';
	$('pid').value = '';
	$('msn').value = '';
	$('omsn').value = '';
}