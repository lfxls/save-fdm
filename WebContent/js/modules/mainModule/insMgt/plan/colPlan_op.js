var menuId = '12200';
Ext.onReady(function() {
});
/**
 * 变压器选择
 */
function queryTf(){
	var url = String.format(ctx + '/main/insMgt/plan/insPlan!initTf.do?dwdm={0}&dwmc={1}&selTfFlag={2}', 
			unitCode, $En(unitName),'col');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.tf,
		el : 'queryTf',
		width : 600,
		height : 450,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();	
}

/**
 * 采集器选择
 */
function queryCol(){
	var url = String.format(ctx + '/main/insMgt/plan/insPlan!initCol.do?');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.col,
		el : 'queryCol',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 保存安装计划采集器
 */
function save(){
	var bussType = $F('bussType'); //业务类型
	var operateType = $F('operateType'); //操作类型
	if(bussType == '0'){//新装
		if(operateType == '01'){//新增
			addCInsPAdd();
		}else if(operateType == '02'){//编辑
			addCInsPEdit();
		}
		
	} else if(bussType == '1'){//更换
		if(operateType == '01'){//新增
			chanCInsPAdd();
		}else if(operateType == '02'){//编辑
			chanCInsPEdit();
		}
		
	} else if(bussType == '2'){//拆除
		if(operateType == '01'){//新增
			remCInsPAdd();
		}else if(operateType == '02'){//编辑
			remCInsPEdit();
		}
	}
}

/**
 * 新装新增保存
 */
function addCInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	//dwr传回参数
	var p = $FF('colPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.add.addColPlan + tfId
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmAdd , function() {
		dwrAction.doDbWorks('insPlanAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 更换新增保存
 */
function chanCInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	//dwr传回参数
	var p = $FF('colPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.add.chanColPlan + tfId
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmAdd, function() {
		dwrAction.doDbWorks('insPlanAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 拆除新增保存
 */
function remCInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	//dwr传回参数
	var p = $FF('colPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.add.remColPlan + tfId
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmAdd , function() {
		dwrAction.doDbWorks('insPlanAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 新装编辑保存
 */
function addCInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('colPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.edit.addColPlan + pid
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmEdit , function() {
		dwrAction.doDbWorks('insPlanAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 更换编辑保存
 */
function chanCInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('colPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.edit.chanColPlan + pid
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmEdit , function() {
		dwrAction.doDbWorks('insPlanAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 拆除编辑保存
 */
function remCInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('cInsPlanOPForm');
	Ext.apply(p, {
		insType : 'col',
		logger : main.insMgt.plan.log.edit.remColPlan + pid
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmEdit , function() {
		dwrAction.doDbWorks('insPlanAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 安装计划保存校验
 */
function saveValidate(){
	//老采集器号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('ocsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldColNo.select, function(btn,text){
				if(btn=='ok'){
					$('ocsn').focus();
				}
			});
			return false;
		}
	}
	
	//变压器
	if(trim($('tfName').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.tf.select, function(btn,text){
			if(btn=='ok'){
				$('tfName').focus();
			}
		});
		return false;
	}
	
	//采集器型号
	if(trim($('colM').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.colModel.select, function(btn,text){
			if(btn=='ok'){
				$('colM').focus();
			}
		});
		return false;
	}
	
	return true;
}