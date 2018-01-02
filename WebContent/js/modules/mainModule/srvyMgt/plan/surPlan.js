var surPGrid = '';
var menuId = '15100';
var surP_url = ctx + '/main/srvyMgt/surPlan!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			//安装计划状态为未处理的情况下可以编辑和删除
			if(record.data.STATUS == '0') {
				url += String.format('<a class="edit" title="\{3}\" href="javascript:doEdit(\'{0}\',\'{1}\',\'{2}\');"></a>',
						record.data.STATUS, record.data.PID, record.data.BTYPE, main.srvyMgt.plan.grid.op.edit);

				url += String.format('<a class="del" title="\{1}\" href="javascript:doDel(\'{0}\');"></a>',
						record.data.PID, main.srvyMgt.plan.grid.op.del);
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
		header :  main.srvyMgt.plan.grid.col.updTime,
		dataIndex : 'UTIME',
		width : 140
	},{
		header :  main.srvyMgt.plan.grid.col.operator,
		dataIndex : 'OPR',
		width : 140
	},{
		dataIndex : 'STATUS',
		hidden : true
    }];

	var meterPToolbar = [{
			id : 'add',
			text : main.srvyMgt.plan.grid.sur.add,
			tooltip : main.srvyMgt.plan.grid.sur.add,
			iconCls : 'add',
			handler : function() {
				addSurPAdd();
			}
		},{
	        id: 'import',
			text : main.srvyMgt.plan.grid.tb.importExcel,
	        tooltip : main.srvyMgt.plan.grid.tb.importExcel,
	        iconCls : 'import',
	        handler: function(){
	    		importExcel();
	        }
	    }];

	surPGrid = new Wg.Grid({
		url : surP_url,
		el : 'surPGrid',
		title : main.srvyMgt.plan.grid.title.surP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : meterPToolbar
	});
	
	var p = $FF('surPlanForm');
	
	//grid数据初始
	surPGrid.init(p);// （是否选择、是否单选）
});

/**
 * 树过滤
 * @param _node
 * @returns {Boolean}
 */
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType != 'dw'){
		Wg.alert(main.srvyMgt.plan.valid.selPU);
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
	var p = $FF('surPlanForm');
	surPGrid.reload(p);	
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
 * 新增计划
 */
function addSurPAdd(){
	var url = ctx + String.format('/main/srvyMgt/surPlan!initPAdd.do?operateType={0}&bussType={1}', '01', '0');
	var baseParam = {
		url : url,
		title : main.srvyMgt.plan.win.title.addSurPlan,
		el : 'addSurvey',
		width : 660,
		height : 550,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();
}


/**
 * Excel文件导入
 */
function importExcel(){
	var url = String.format(ctx + '/main/srvyMgt/surPlan!initSurPImport.do');
	var baseParam = {
		url : url,
		title : main.srvyMgt.plan.win.title.surPlan.imp,
		el : 'sImport',
		width : 920,
		height : 600,
		draggable : true
	};
	
	impWin = new Wg.window(baseParam); 
	impWin.open();
}

/**
 * 编辑
 */
function doEdit(status, pid, btype){
	
	
	var url = ctx + String.format('/main/srvyMgt/surPlan!initSurEdit.do?operateType={0}&bussType={1}&pid={2}', 
						'02', btype, pid);
	var baseParam = {
			url : url,
			title : main.srvyMgt.plan.win.title.editSurPlan,
			el : 'editSP',
			width : 660,
			height : 550,
			draggable : true
	};
	
	editWin = new Wg.window(baseParam); 
	editWin.open();
}

/**
 * 删除
 */
function doDel(pid){
	
	var p = { 
		pid : pid,
		logger : main.srvyMgt.plan.log.del.surPlan + pid
	};
	
	Wg.confirm(main.srvyMgt.plan.hint.sur.confirmDel, function() {
		dwrAction.doDbWorks('surPlanAction', menuId + '03', p, function(res) {
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

function mpReset() {
	$('nodeText').value='';
	$('nodeId').value='';
	$('nodeDwdm').value='';
	$('nodeType').value='';
	$('cno').value='';
	$('status').value='';
	$('bussType').value='';
}