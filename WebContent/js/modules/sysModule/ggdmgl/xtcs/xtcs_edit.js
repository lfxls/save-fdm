var menuId = '54200';
//数据验证
function dataValidate(){
	//参数名称不能为空
	if($E($F('csmc'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_csmc);
		return false;
	}
	//参数值不能为空
	if($E($F('csz'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_csz);
		return false;
	}
	//有效标志不能为空
	if($E($F('syzt'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_yxbz);
		return false;
	}
	//xtcs_prompt_null_cszlx
	if($E($F('xtcszlx'))){
		Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_null_cszlx);
		return false;
	}
	return true;
}

//编辑代码值
function editPara(){
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	//参数
	var param = {
		paraId : $F('paraId'),
		paraSortId: $F('paraSortId'),
		csmc : $F('csmc'),
		xtcszlx : $F('xtcszlx'),
		csz : $F('csz'),
		cszsx : $F('cszsx'),
		cszxx : $F('cszxx'),
		syzt : $F('syzt'),
		type: 'csxx',
		lang: parent.$F('lang'),
		logger : $F('czbs')=='01' ? systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_addPara : systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_updPara
	};
	if($F('czbs')=='01'){
		//新增编码值
		Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_addPara_comfirm, function() {
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
		//更新编码值
		Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_updPara_comfirm, function() {
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
	var lang = parent.$F('lang');
	var param = {
		lang:lang,
		paraSortId:$F('paraSortId')
	};
	parent.CsGrid.reload(param);
}