var menuId = '54200';

//数据验证
function dataValidate(){
	//参数分类编码不能为空
	if($E($F('csflbm'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_csflbm);
		return false;
	}
	//参数分类名称不能为空
	if($E($F('csflmc'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_csflmc);
		return false;
	}
	
	//有效标志不能为空
	if($E($F('syzt'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_yxbz);
		return false;
	}
	return true;
}

//保存
function editParaSort(){
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	//参数
	var param = {
		paraSortId: $F('paraSortId'),
		csflbm : $F('csflbm'),
		csflmc : $F('csflmc'),
		syzt : $F('syzt'),
		csflms : $F('csflms'),
		type:'csfl',
		lang: parent.$F('lang'),
		logger : $F('czbs')=='01' ? systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_addSortPara : systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_updSortPara
	};
	
	if($F('czbs')=='01'){
		//新增编码类型
		Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_addParaSort_comfirm, function() {
			dwrAction.doDbWorks('xtcsAction', menuId + addOpt, param, function(res) {
				Wg.alert(res.msg, function() {
					if (res.success) {
						//更新grid
						query();
						//关闭窗口
						parent.win.close();
					}
				});
			});
		});
	}else{ 
		//更新编码类型
		Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_updParaSort_comfirm, function() {
			dwrAction.doDbWorks('xtcsAction', menuId + updOpt, param, function(res) {
				Wg.alert(res.msg, function() {
					if (res.success) {
						//更新grid
						query();
						//关闭窗口
						parent.win.close();
					}
				});
			});
		});
	}
}

//更新grid
function query(){
	var csflmc = parent.$F('csflmc');
	var lang = parent.$F('lang');
	var param = {
		lang:lang,
		csflmc:csflmc,
		code_sort_id:-1
	};
	parent.CsSortGrid.reload(param);
	parent.CsGrid.reload(param);
}


