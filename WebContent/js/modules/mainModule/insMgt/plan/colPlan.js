var colPGrid = '';
var menuId = '12200';
var colPUrl = ctx + '/main/insMgt/plan/insPlan!queryCInsPlan.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	
	var _cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			url += String.format('<a class="edit" title="\{3}\" href="javascript:doEdit(\'{0}\',\'{1}\',\'{2}\');"></a>',
					record.data.STATUS, record.data.PID, record.data.BUSSTYPE, main.insMgt.plan.grid.op.edit);

			url += String.format('<a class="del" title="\{2}\" href="javascript:doDel(\'{0}\',\'{1}\',\'{2}\');"></a>',
					record.data.STATUS, record.data.PID, record.data.BUSSTYPE, main.insMgt.plan.grid.op.del);
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
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
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
		header : main.insMgt.plan.grid.col.newColNo,
		dataIndex : 'NCSN',
		width : 140
	},{
		header : main.insMgt.plan.grid.col.oldColNo,
		dataIndex : 'OCSN',
		width : 140
	},{
		header : main.insMgt.plan.grid.col.updTime,
		dataIndex : 'UTIME',
		width : 140
	},{
		header : main.insMgt.plan.grid.col.operator,
		dataIndex : 'OPTID',
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
		dataIndex : 'PID',
		hidden : true		
	}];

	var colPToolbar = [{
			id : 'add',
			text : main.insMgt.plan.grid.tb.add,
			tooltip : main.insMgt.plan.grid.tb.add,
			iconCls : 'add',
			handler : function() {
				addCInsPAdd();
			}
		},{
			id : 'change',
			text : main.insMgt.plan.grid.tb.change,
			tooltip : main.insMgt.plan.grid.tb.change,
			iconCls : 'in',
			handler : function() {
				chanCInsPAdd();
			}		
		},{
			id : 'remove',
			text : main.insMgt.plan.grid.tb.remove,
			tooltip : main.insMgt.plan.grid.tb.remove,
			iconCls : 'cancel',
			handler : function() {
				remCInsPAdd();
			}		
		},{
	        id: 'import',
			text : main.insMgt.plan.grid.tb.importExcel,
	        tooltip : main.insMgt.plan.grid.tb.importExcel,
	        iconCls : 'none',
	        handler: function(){
	    		importExcel();
	        }
	    }];

	colPGrid = new Wg.Grid({
		url : colPUrl,
		el : 'colPGrid',
		title : main.insMgt.plan.grid.title.colInsP,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : colPToolbar
	});
	
	var p = $FF('colPlanForm');
	//grid数据初始
	colPGrid.init(p);// （是否选择、是否单选）
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
	var p = $FF('colPlanForm');
	colPGrid.reload(p);	
}

/**
 * 处理状态下拉框
 * @param clzt
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
 * 新装采集器
 */
function addCInsPAdd(){
	var url = ctx + String.format('/main/insMgt/plan/insPlan!initCInsPAdd.do?operateType={0}&bussType={1}', '01', '0');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.addInsColPlan,
		el : 'addCol',
		width : 660,
		height : 550,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();
}

/**
 * 更换采集器
 */
function chanCInsPAdd(){
	var url = ctx + String.format('/main/insMgt/plan/insPlan!initCInsPAdd.do?operateType={0}&bussType={1}', 
			'01', '1');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.addChanColPlan,
		el : 'changeCol',
		width : 660,
		height : 550,
		draggable : true
	};
	
	addWin = new Wg.window(baseParam); 
	addWin.open();		
}

/**
 * 拆除采集器
 */
function remCInsPAdd(){
		var url = ctx + String.format('/main/insMgt/plan/insPlan!initCInsPAdd.do?operateType={0}&bussType={1}', 
				'01', '2');
		var baseParam = {
			url : url,
			title : main.insMgt.plan.win.title.addRemColPlan,
			el : 'removeCol',
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
	var url = String.format(ctx + '/main/insMgt/insPlan!initCInsPImport.do');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.colInsPlan.imp,
		el : 'cImport',
		width : 920,
		height : 550,
		draggable : true
	};
	
	impWin = new Wg.window(baseParam); 
	impWin.open();
}

/**
 * 编辑
 */
function doEdit(status, pid, bussType){
	if(status != '0'){
		Wg.alert(main.insMgt.plan.valid.canNotEdit);
		return;	
	}
	
	var winTitle = '';
	if(bussType == '0'){
		winTitle = main.insMgt.plan.win.title.editInsColPlan;
	}else if(bussType == '1'){
		winTitle = main.insMgt.plan.win.title.editChanColPlan;
	}else if(bussType == '2'){
		winTitle = main.insMgt.plan.win.title.editRemColPlan;
	}
	
	var url = ctx + String.format('/main/insMgt/plan/insPlan!initCInsPEdit.do?operateType={0}&bussType={1}&pid={2}', 
						'02', bussType, pid);
	var baseParam = {
			url : url,
			title : winTitle,
			el : 'editCInsP',
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
		insType : 'col',
		logger : main.insMgt.plan.log.del.colPlan + pid
	};
	
	Wg.confirm(main.insMgt.plan.hint.confirmDel, function() {
		dwrAction.doDbWorks('insPlanAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					query();
				}
			});
		});
	});	
}
