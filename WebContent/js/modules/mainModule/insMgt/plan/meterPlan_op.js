var menuId = '12200';
Ext.onReady(function() {
});

/**
 * 用户选择
 */
function queryCust(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initCust.do?otype={0}','0');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.cust,
		el : 'queryCust',
		width : 620,
		height : 380,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 变压器选择
 */
function queryTf(){
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return;
	}
	var url = String.format(ctx + '/main/insMgt/insPlan!initTf.do?uid={0}&selTfFlag={1}', 
							$F('uid'), 'meter');
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
 * 表查询
 */
function queryMeter(type){
	var url = '';
	if(type == 'new') {
		if(!saveValidate()) {
			return;
		}
		url = String.format(ctx + '/main/insMgt/insPlan!initMeter.do?type={0}&bussType={1}&mType={2}&wir={3}&mMode={4}&uid={5}',
				type,$F('bussType'),$F('mType'),$F('wir'),$F('mMode'),$F('uid'));
	} else {
		url = String.format(ctx + '/main/insMgt/insPlan!initMeter.do?type={0}&bussType={1}&mType={2}&wir={3}&mMode={4}',
				type,$F('bussType'));
	}
	
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.meter,
		el : 'queryMeter',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 保存安装计划表计
 */
function save(){
	var bussType = $F('bussType'); //业务类型
	var operateType = $F('operateType'); //操作类型

	if(bussType == '0'){//新装
		if(operateType == '01'){//新增
			addMInsPAdd();
		}else if(operateType == '02'){//编辑
			addMInsPEdit();
		}
	} else if(bussType == '1'){//更换
		if(operateType == '01'){//新增
			chanMInsPAdd();
		}else if(operateType == '02'){//编辑
			chanMInsPEdit();
		}
		
	} else if(bussType == '2'){//拆除
		if(operateType == '01'){//新增
			remMInsPAdd();
		}else if(operateType == '02'){//编辑
			remMInsPEdit();
		}
	}
}

/**
 * 新装新增保存
 */
function addMInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var cno = $F('cno');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.add.addMeterPlan + cno
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
function chanMInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var cno = $F('cno');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.add.chanMeterPlan + cno
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
function remMInsPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var cno = $F('cno');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.add.remMeterPlan + cno
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
function addMInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.edit.addMeterPlan + pid
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
 * 更换编辑保存
 */
function chanMInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.edit.chanMeterPlan + pid
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
function remMInsPEdit(){
	//校验
	if(!saveValidate(true)){
		return;
	}
	
	var pid = $F('pid');
	//dwr传回参数
	var p = $FF('mPlanOPForm');
	Ext.apply(p, {
		logger : main.insMgt.plan.log.edit.remMeterPlan + pid
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
	//老表计局号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('omsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldMeterNo.select, function(btn,text){
				if(btn=='ok'){
					$('omsn').focus();
				}
			});
			return false;
		}
	}
	
	
	//用户
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
	}
	
	//变压器
	if(trim($('tfName').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.tf.select, function(btn,text){
			if(btn=='ok'){
				$('tfId').focus();
			}
		});
		return false;
	}
	
	//表计类型
	if(trim($('mType').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.meterType.select, function(btn,text){
			if(btn=='ok'){
				$('mType').focus();
			}
		});
		return false;
	}
	
	//接线方式
	if(trim($('wir').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.wiring.select, function(btn,text){
			if(btn=='ok'){
				$('wir').focus();
			}
		});
		return false;
	}
	
	//表计模式
	if(trim($('mMode').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.meterMode.select, function(btn,text){
			if(btn=='ok'){
				$('mMode').focus();
			}
		});
		return false;
	}
	return true;
}