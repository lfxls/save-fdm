var menuId = '12200';
Ext.onReady(function() {
});

/**
 * 变压器选择
 */
function queryTf(){
	var url = String.format(ctx + '/main/insMgt/plan/insPlan!initTf.do?dwdm={0}&dwmc={1}&selTfFlag={2}', 
			unitCode, $En(unitName),'dcu');
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
 * 集中器选择
 */
function queryDcu(type){
	var url = String.format(ctx + '/main/insMgt/plan/insPlan!initDcu.do?type={0}',type);
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.dcu,
		el : 'queryDcu',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 保存安装计划集中器
 */
function save(){
	var bussType = $F('bussType'); //业务类型
	var operateType = $F('operateType'); //操作类型
	if(bussType == '0'){//新装
		if(operateType == '01'){//新增
			addDInsPAdd();
		}else if(operateType == '02'){//编辑
			addDInsPEdit();
		}
		
	} else if(bussType == '1'){//更换
		if(operateType == '01'){//新增
			chanDInsPAdd();
		}else if(operateType == '02'){//编辑
			chanDInsPEdit();
		}
		
	} else if(bussType == '2'){//拆除
		if(operateType == '01'){//新增
			remDInsPAdd();
		}else if(operateType == '02'){//编辑
			remDInsPEdit();
		}
	}
}

/**
 * 新装新增保存
 */
function addDInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.add.addDcuPlan + tfId
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
 * 更换新增保存
 */
function chanDInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.add.chanDcuPlan + tfId
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
 * 拆除新增保存
 */
function remDInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var tfId = $F('tfId');
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.add.remDcuPlan + tfId
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
 * 新装编辑保存
 */
function addDInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.edit.addDcuPlan + pid
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
function chanDInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.edit.chanDcuPlan + pid
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmEdit, function() {
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
function remDInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('dcuPlanOPForm');
	Ext.apply(p, {
		insType : 'dcu',
		logger : main.insMgt.plan.log.edit.remDcuPlan + pid
	});
	
	Wg.confirm(main.insMgt.plan.hint.confirmEdit, function() {
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
	//老集中器号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('odsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldDcuNo.select, function(btn,text){
				if(btn=='ok'){
					$('odsn').focus();
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
	
	//集中器型号
	if(trim($('dcuM').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.dcuModel.select, function(btn,text){
			if(btn=='ok'){
				$('dcuM').focus();
			}
		});
		return false;
	}
	
	return true;
}