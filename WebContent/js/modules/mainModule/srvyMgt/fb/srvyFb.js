var srvyFbGrid = '';
var menuId = '12900';
var srvyFb_url = ctx + '/main/srvyMgt/srvyFb!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if(record.data.CNAME != record.data.R_CNAME) {
				url += String.format('<a class="enable" title="\{3}\" href="javascript:doUpdAr(\'{0}\',\'{1}\',\'{2}\');"></a>',
						record.data.PID, record.data.CNO, main.srvyMgt.fb.grid.op.editC);
			}
			return url;
		}
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:showPlanLog(\'{0}\');">'+record.data.PID+'</a>&nbsp;&nbsp;',
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
		width : 120
    },{
		header : main.insMgt.plan.grid.col.btStatus,
		dataIndex : 'PLNSTNAME',
		width : 120
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
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	},{
		header : common.kw.other.Longitude,
		dataIndex : 'LON',
		width : 130
	},{
		header : common.kw.other.Latitude,
		dataIndex : 'LAT',
		width : 130
	},{
		header :  main.srvyMgt.fb.grid.col.cabLen,
		dataIndex : 'CABLE_LEN',
		width : 120
	},{
		header :  main.srvyMgt.fb.grid.col.cabType,
		dataIndex : 'CABTNAME',
		width : 120
	},{
		header :  common.kw.meter.MCMethod,
		dataIndex : 'WIRNAME',
		width : 140
	},{
		header : main.srvyMgt.fb.grid.col.cBreaker,
		dataIndex : 'CBRKNAME',
		width : 120
	},{
		header : main.srvyMgt.fb.grid.col.rCName,
		dataIndex : 'R_CNAME',
		width : 120
	},{
		header : main.srvyMgt.fb.grid.col.pNature,
		dataIndex : 'PNATNAME',
		width : 120
	},{
		header : main.srvyMgt.fb.grid.col.pStatus,
		dataIndex : 'PSTANAME',
		width : 120
	},{
		header : main.srvyMgt.fb.grid.col.pNNum,
		dataIndex : 'PNNUMNAME',
		width : 120
	},{
		header : main.srvyMgt.fb.grid.col.pENum,
		dataIndex : 'PENUMNAME',
		width : 120
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
		dataIndex : 'DTNAME',
		width : 140
	},{
		dataIndex : 'UID',
		hidden : true
	},{
		dataIndex : 'R_ADDR',
		hidden : true
	},{
		dataIndex : 'R_PHONE',
		hidden : true
	}];

	srvyFbGrid = new Wg.Grid({
		url : srvyFb_url,
		el : 'srvyFbGrid',
		title : main.srvyMgt.fb.grid.title.srvyFb,
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
	srvyFbGrid.init(p);// （是否选择、是否单选）
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
	var p = $FF('srvyFbForm');
	srvyFbGrid.reload(p);	
}

/**
 * 处理状态下拉框
 * @param status
 */
function changeStatus(status){
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
 * 业务类型下拉框
 * @param bussType
 */
function changeBType(bussType){
	query();
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
	$('plnStatus').value = '';
	$('pid').value = '';
	$('bussType').value = '';
}

function doUpdAr(pid,cno){
	
	var p = { 
			pid : pid,
			cno:cno,
		};
	Wg.confirm(main.srvyMgt.fb.hint.sur.confirmUpd, function() {
		/*srvyFbAction.updCurtomer(pid,cname,rname, function(res) {
			Wg.alert(res.msg, function() {
				query();
			});
		});*/
		dwrAction.doAjax('srvyFbAction', 'updCurtomer', p, function(res) {
			Wg.alert(res.msg, function() {
				query();
			});
		});

	
	});	
}

function showPlanLog(pid){
	var url = String.format(ctx + '/main/insMgt/planLog!init.do?pid={0}',pid);
	var baseParam = {
		url : url,
		title : main.srvyMgt.planLog.title,
		el : 'planOPLog',
		width : 800,
		height : 450,
		draggable : true
	};
	
	var planOPLogWin = new Wg.window(baseParam); 
	planOPLogWin.open();
}