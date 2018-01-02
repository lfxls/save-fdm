var menuId = '15100';
Ext.onReady(function() {
});

/**
 * 用户选择
 */
function queryCust(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initCust.do?');
	var baseParam = {
		url : url,
		title : main.srvyMgt.plan.win.title.cust,
		el : 'queryCust',
		width : 620,
		height : 380,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}


/**
 * 保存安装计划表计
 */
function save(){
	var operateType = $F('operateType'); //操作类型

		if(operateType == '01'){//新增
			addSurPAdd();
		}else if(operateType == '02'){//编辑
			addSurPEdit();
		}
}

/**
 * 新装新增保存
 */
function addSurPAdd(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var cno = $F('cno');
	//dwr传回参数
	var p = $FF('sPlanOPForm');
	Ext.apply(p, {
		logger : main.srvyMgt.plan.log.add.addSurPlan + cno
	});
	
	Wg.confirm(main.srvyMgt.plan.hint.sur.confirmAdd, function() {
		dwrAction.doDbWorks('surPlanAction', menuId + '01', p, function(res) {
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
 * 编辑保存
 */
function addSurPEdit(){
	//校验
	if(!saveValidate(false)){
		return;
	}
	
	var cno = $F('cno');
	//dwr传回参数
	var p = $FF('sPlanOPForm');
	Ext.apply(p, {
		logger : main.srvyMgt.plan.log.edit.addSurPlan + cno
	});
	
	Wg.confirm(main.srvyMgt.plan.hint.sur.confirmEdit, function() {
		dwrAction.doDbWorks('surPlanAction', menuId + '02', p, function(res) {
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
	
	//用户
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.srvyMgt.plan.valid.tip,
				main.srvyMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
	}
	
	return true;
}