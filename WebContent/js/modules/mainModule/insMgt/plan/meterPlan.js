var meterPGrid = '';
var menuId = '12200';
var meterP_url = ctx + '/main/insMgt/insPlan!queryMInsPlan.do';
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
						record.data.STATUS, record.data.PID, record.data.BTYPE, main.insMgt.plan.grid.op.edit);

				url += String.format('<a class="del" title="\{3}\" href="javascript:doDel(\'{0}\',\'{1}\',\'{2}\');"></a>',
						record.data.STATUS, record.data.PID, record.data.BTYPE, main.insMgt.plan.grid.op.del);
			}
			return url;
		}
	},{
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
		dataIndex : 'BTYPE',
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
			text : main.insMgt.plan.grid.tb.add,
			tooltip : main.insMgt.plan.grid.tb.add,
			iconCls : 'add',
			handler : function() {
				addMInsPAdd();
			}
		},{
			id : 'change',
			text : main.insMgt.plan.grid.tb.change,
			tooltip : main.insMgt.plan.grid.tb.change,
			iconCls : 'batch-edit',
			handler : function() {
				chanMInsPAdd();
			}		
		},{
			id : 'remove',
			text : main.insMgt.plan.grid.tb.remove,
			tooltip : main.insMgt.plan.grid.tb.remove,
			iconCls : 'batch-delete',
			handler : function() {
				remMInsPAdd();
			}		
		},{
	        id: 'import',
			text : main.insMgt.plan.grid.tb.importExcel,
	        tooltip : main.insMgt.plan.grid.tb.importExcel,
	        iconCls : 'import',
	        handler: function(){
	    		importExcel();
	        }
	    }];

	meterPGrid = new Wg.Grid({
		url : meterP_url,
		el : 'meterPGrid',
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
	
	var p = $FF('meterPlanForm');
	
	//grid数据初始
	meterPGrid.init(p);// （是否选择、是否单选）
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
	var p = $FF('meterPlanForm');
	meterPGrid.reload(p);	
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
 * 新装表计
 */
function addMInsPAdd(){
	var url = ctx + String.format('/main/insMgt/insPlan!initMInsPAdd.do?operateType={0}&bussType={1}', '01', '0');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.addInsMeterPlan,
		el : 'addMeter',
		width : 660,
		height : 550,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();
}

/**
 * 更换表计
 */
function chanMInsPAdd(){
	var url = ctx + String.format('/main/insMgt/insPlan!initMInsPAdd.do?operateType={0}&bussType={1}', 
			'01', '1');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.addChanMeterPlan,
		el : 'changeMeter',
		width : 660,
		height : 550,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();
}

/**
 * 拆除表计
 */
function remMInsPAdd(){
	var url = ctx + String.format('/main/insMgt/insPlan!initMInsPAdd.do?operateType={0}&bussType={1}', 
			'01', '2');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.addRemMeterPlan,
		el : 'removeMeter',
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
	var url = String.format(ctx + '/main/insMgt/insPlan!initMInsPImport.do');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.meterInsPlan.imp,
		el : 'mImport',
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
//	if(status != '0'){
//		Wg.alert(main.insMgt.plan.valid.canNotEdit);
//		return;	
//	}
	
	var winTitle = '';
	if(btype == '0'){
		winTitle = main.insMgt.plan.win.title.editInsMeterPlan;
	}else if(btype == '1'){
		winTitle = main.insMgt.plan.win.title.editChanMeterPlan;
	}else if(btype == '2'){
		winTitle = main.insMgt.plan.win.title.editRemMeterPlan;
	}
	
	var url = ctx + String.format('/main/insMgt/insPlan!initMInsPEdit.do?operateType={0}&bussType={1}&pid={2}', 
						'02', btype, pid);
	var baseParam = {
			url : url,
			title : winTitle,
			el : 'editMIP',
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
function doDel(status, pid, bussType){
	if(status != '0'){
		Wg.alert(main.insMgt.plan.valid.canNotDel);
		return;	
	}
	
	var p = { 
		pid : pid,
		status : status,
		operateType : '03',
		bussType : bussType,
		insType : 'meter',
		logger : main.insMgt.plan.log.del.meterPlan + pid
	};
	
	Wg.confirm(main.insMgt.plan.hint.confirmDel, function() {
		dwrAction.doDbWorks('insPlanAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				query();
			});
		});
	});	
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