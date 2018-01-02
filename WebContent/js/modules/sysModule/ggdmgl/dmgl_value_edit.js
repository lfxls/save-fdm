var menuId = '54100';
//数据验证
function dataValidate(){
	//检查编码名称
	var codeName = $F('codeName');
	if(codeName == ""){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Validate.dmmc);
		return false;
	}
	//检查编码值
	var codeValue = $F('codeValue');
	if(codeValue == ""){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Validate.dmz);
		return false;
	}
	//检查序号
	var disp_sn = $F('disp_sn');
	if(disp_sn == ""){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Validate.disp_sn_notNull);
		return false;
	}
	
	if( !$E($F('disp_sn')) && ( !/^\d{1,5}$/.test($F('disp_sn')) ) ){
		Ext.Msg.alert(ExtTools_alert_title, systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Validate.disp_sn_isNumber, function(btn,text){
			if(btn=='ok'){
				$('disp_sn').focus();
			}
		});	
		return false;
	}
	return true;
}

//编辑代码值
function editCodeValue(){
	var cateCode  = $F('cateCode');
	var cateName = $F('cateName');
	var codeValueOld = $F('codeValueOld');
	var codeValue = $F('codeValue');
	var codeName = $F('codeName');
	var isShow = $F('isShow');
	var disp_sn = $F('disp_sn');
	
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	//参数
	var p = {
		cateCode: cateCode,
		cateName: cateName,
		valueOld: codeValueOld,
		value: codeValue,
		name: codeName,
		isShow: isShow,
		disp_sn: disp_sn,
		type:'dmxx',
		lang: parent.$F('yy'),
		logger: codeValueOld=="" ? systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Logger.adddmz + codeName : systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Logger.upddmz + codeName
	};
	if(codeValueOld == ""){
		//新增编码值
		parent.Wg.confirm(systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Confirm.addmz, function() {
			dwrAction.doDbWorks('dmglAction', menuId + addOpt, p, function(res) {
				parent.Wg.alert(res.msg, function() {
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
		parent.Wg.confirm(systemModule_ggdmgl_dmgl_value_edit.resourceBundle.Confirm.upddmz, function() {
			dwrAction.doDbWorks('dmglAction', menuId + updOpt, p, function(res) {
				parent.Wg.alert(res.msg, function() {
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
	var p = {
		lang: parent.$F('yy'),
		cateCode: $F('cateCode')
	};
	parent.codeGrid.reload(p);
}