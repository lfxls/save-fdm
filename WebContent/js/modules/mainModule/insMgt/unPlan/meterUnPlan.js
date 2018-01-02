var meterUnPGrid = '';
var menuId = '12100';
var grid_url = ctx + '/main/insMgt/insUnPlan!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if(record.data.STATUS == '0' || record.data.STATUS == '1' || record.data.STATUS == '4') {//未处理，已分配，已撤销状态下可以删除
				url += String.format('<a class="edit" title="\{5}\" href="javascript:doEdit(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
						record.data.STATUS, record.data.WOID, record.data.POPID,record.data.POPNAME, record.data.FTIME, main.insMgt.plan.grid.op.edit);
				url += String.format('<a class="del" title="\{2}\" href="javascript:doDel(\'{0}\',\'{1}\');"></a>',
						record.data.STATUS, record.data.WOID, main.insMgt.plan.grid.op.del);
			} else {
				url += String.format('<a class="soubiao" title="\{5}\" href="javascript:doEdit(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
						record.data.STATUS, record.data.WOID, record.data.POPID,record.data.POPNAME,record.data.FTIME,main.insMgt.plan.grid.op.show);
				if(record.data.STATUS == '2') {//状态为已派工
					url += String.format('<a class="doback" title="\{1}\" href="javascript:doRevok(\'{0}\');"></a>',
							record.data.WOID, main.insMgt.order.grid.op.doRevoke);
				}
			}
			return url;
		}
	},{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>&nbsp;&nbsp;',
					record.data.WOID);
			return url;
		}
	},{
		header : common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
    },{
		header :  main.insMgt.unPlan.grid.col.poptid,
		dataIndex : 'POPNAME',
		width : 140
	},{
		header :  main.insMgt.unPlan.grid.col.cDate,
		dataIndex : 'CDATE',
		width : 140
	},{
		header: main.insMgt.order.grid.col.finishTime,
		dataIndex: 'FTIME',
		width: 150
	},{
		header :  main.insMgt.unPlan.grid.col.coptid,
		dataIndex : 'COPTNAME',
		width : 140
	},{
		dataIndex : 'STATUS',
		hidden : true
    },{
		dataIndex : 'POPID',
		hidden : true
    }];

	var toolbar = [{
			id : 'add',
			text : main.insMgt.unplan.grid.tb.dispatch,
			tooltip : main.insMgt.unplan.grid.tb.dispatch,
			iconCls : 'add',
			handler : function() {
				dispatch();
			}
		}];

	meterUnPGrid = new Wg.Grid({
		url : grid_url,
		el : 'meterUnPGrid',
		title : main.insMgt.plan.grid.title.meterInsP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : toolbar
	});
	
	var p = $FF('meterUnPlanForm');
	
	//grid数据初始
	meterUnPGrid.init(p);// （是否选择、是否单选）
});

/**
 * 查询
 */
function query(){
//	if(!$E($F('woid'))) {
//		if(!/^\d+$/.test($F('woid'))){
//			Wg.alert(main.insMgt.order.valid.workOrderNo.formatCheck);
//			return;
//		}
//	}
	var p = $FF('meterUnPlanForm');
	meterUnPGrid.reload(p);	
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
 * 新增用户派发
 */
function dispatch(){
	var url = ctx + String.format('/main/insMgt/insUnPlan!initCustDisp.do?operateType={0}&dispFlag={1}', 
			'01','meter');
	top.openpageOnTree(main.insMgt.unPlan.win.title.addCustDisp, '121001', main.insMgt.unPlan.win.title.addCustDisp, null, url, 'yes', 1);
}

/**
 * 删除
 */
function doDel(status, woid){
//	if(status != '0'){
//		Wg.alert(main.insMgt.plan.valid.canNotDel);
//		return;	
//	}
	
	var p = { 
		woid : woid,
		operateType : '03',
		dispFlag : 'meter',
		logger : main.insMgt.unPlan.log.del.custDisp + woid
	};
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmDel, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
					query();
			});
		});
	});	
}

/**
 * 查看工单下的分配的用户
 * @param woid
 */
function doEdit(status,woid,popid,popName,fTime) {
	var winTitle = '';
	var url = '';
	if(status == '0' || status == '1' || status == '4') {//可以编辑用户分配
		winTitle = main.insMgt.unPlan.win.title.editCustDisp;
		url = ctx + String.format('/main/insMgt/insUnPlan!initCustDisp.do?operateType={0}&dispFlag={1}&status={2}&woid={3}&fTime={4}', 
				'02','meter',status,woid,fTime);
	} else {//查看用户分配
		winTitle = main.insMgt.unPlan.win.title.showCustDisp;
		url = ctx + String.format('/main/insMgt/insUnPlan!initCustDisp.do?operateType={0}&dispFlag={1}&status={2}&woid={3}&fTime={4}', 
				'04','meter',status,woid,fTime);
	}
	top.openpageOnTree(winTitle, '121001', winTitle, null, url, 'yes', 1);
}

//撤销工单
function doRevok(woid) {
	 Wg.confirm(main.insMgt.order.hint.confirmRev, function(){
		dwrAction.doAjax('insOrderAction','revokeWorkOrder',{woid:woid},function(res){
			if(res.success) {
				Wg.alert(main.insMgt.order.hint.revoke.success);
				query();
			}
		});
	 });
}

function queryPop() {
	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.pop,
		el : 'selPOP',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

function unPlnReset() {
	$('woid').value = '';
	$('status').value = '';
	$('popid').value = '';
	$('popName').value = '';
}