var dcuUnPGrid = '';
var menuId = '12100';
var grid_url = ctx + '/main/insMgt/insUnPlan!queryDInsP.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	
	var _cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if(record.data.STATUS == '0') {//未处理状态下可以编辑和删除
				url += String.format('<a class="del" title="\{2}\" href="javascript:doDel(\'{0}\',\'{1}\');"></a>',
						record.data.STATUS, record.data.PID, main.insMgt.plan.grid.op.del);
			}
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 120
    },{
		header : common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
    },{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 100,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDispWin(\'{0}\',\'{1}\',\'{2}\');">'
					+'<font color="red">'+
					record.data.WOID
					+'</font></a>&nbsp;&nbsp;',
					record.data.WOID,record.data.STATUS,record.data.POPTID);
			return url;
		}
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
		header :  common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 140
	},{
		header :  main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
		width : 140
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
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'PID',
		hidden : true		
	},{
		dataIndex : 'POPTID',
		hidden : true		
	},{
		dataIndex : 'TFID',
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

	dcuUnPGrid = new Wg.Grid({
		url : grid_url,
		el : 'dcuUnPGrid',
		title : main.insMgt.plan.grid.title.dcuInsP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : toolbar
	});
	
	var p = $FF('dcuUnPlanForm');
	
	//grid数据初始
	dcuUnPGrid.init(p);// （是否选择、是否单选）
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
	if ($E($F('nodeId'))) {
		Wg.alert(main.insMgt.plan.valid.PUNotEmpty);
		return;
	}
	var p = $FF('dcuUnPlanForm');
	dcuUnPGrid.reload(p);	
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
 * 新增变压器派发
 */
function dispatch(){
	var url = ctx + String.format('/main/insMgt/insUnPlan!initTfDisp.do?operateType={0}&dispFlag={1}', 
			'01','dcu');
	var baseParam = {
		url : url,
		title : main.insMgt.unPlan.win.title.addTfDisp,
		el : 'addTfDisp',
		width : 760,
		height : 650,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();
}

/**
 * 删除
 */
function doDel(status, pid, bussType){
	if(status != '0'){
		Wg.alert(main.insMgt.plan.valid.canNotDel);
		return;	
	}
	
	var p = { 
		pid : pid,
		operateType : '03',
		dispFlag : 'dcu',
		logger : main.insMgt.unPlan.log.del.tfDisp + pid
	};
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmDel, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					query();
				}
			});
		});
	});	
}

/**
 * 查看工单下的分配的用户
 * @param woid
 */
function openDispWin(woid,status,popid) {
	var winTitle = '';
	if(status == '0') {//可以编辑用户分配
		winTitle = main.insMgt.unPlan.win.title.editTfDisp;
	} else {//查看用户分配
		winTitle = main.insMgt.unPlan.win.title.showTfDisp;
	}
	var url = ctx + String.format('/main/insMgt/insUnPlan!initTfDisp.do?operateType={0}&dispFlag={1}&status={2}&woid={3}&popid={4}', 
			'02','dcu',status,woid,popid);
	var baseParam = {
		url : url,
		title : winTitle,
		el : 'disp',
		width : 760,
		height : 650,
		draggable : true
	};
	
	dispWin = new Wg.window(baseParam); 
	dispWin.open();
}