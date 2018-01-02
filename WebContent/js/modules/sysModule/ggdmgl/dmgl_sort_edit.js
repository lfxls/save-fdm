var menuId = '54100';

//数据验证
function dataValidate(){
	//对于分类名称的检查
	var cateName = $F('cateName');
	if(cateName == ""){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Validate.dmflmc);
		return false;
	}
	//对于分类编码的检查
	var cateCode = $F('cateCode');
	if(cateCode == ""){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Validate.dmflbm);
		return false;
	}
	
	//代码分类不能包含特殊字符，但允许空格、-、.、_	/^[a-zA-Z\d\.\s-_]+$/
	if(!$E($F('cateCode')) && !/^[a-zA-Z\d\.\s-_]+$/.test($F('cateCode'))){
		parent.Wg.alert(systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Validate.dmflbmFormat);
		return false;
	}
	
	return true;
}

//保存
function editCodeSort(){
	var cateCodeOld = $F('cateCodeOld');
	var cateName = $F('cateName');
	var cateCode = $F('cateCode');
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	//参数
	var p = {
			cateCodeOld: cateCodeOld,
			cateCode: cateCode,
			name: cateName,
			type: 'dmfl',
			lang: parent.$F('yy'),
			logger: cateCodeOld==""?systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Logger.adddmfl + cateName :systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Logger.upddmfl + cateName
		};
	
	if(cateCodeOld==""){
		//新增编码类型
		parent.Wg.confirm(systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Confirm.adddmfl, function() {
			dwrAction.doDbWorks('dmglAction', menuId + addOpt, p, function(res) {
				parent.Wg.alert(res.msg, function() {
					if (res.success) {
						//更新grid
						parent.query();
						//关闭窗口
						parent.win.close();
					}
				});
			});
		});
	}else{ 
		//更新编码类型
		parent.Wg.confirm(systemModule_ggdmgl_dmgl_sort_edit.resourceBundle.Confirm.upddmfl, function() {
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
	var lang = parent.$F('lang');
	var dmflmcParant = parent.$F('dmflmc');
	var dmflmc = $F('cateName');
	var cateCodeParent = parent.$F('cateCode');
	var cateCode = $F('cateCode');
	var pForCode = {
		lang: lang,
		name: dmflmc,
		cateCode: cateCode
	};
	var pForSort = {
			lang: lang,
			name: dmflmcParant,
			cateCode: cateCodeParent
	};
	parent.categoryGrid.reload(pForSort);
	parent.codeGrid.reload(pForCode);
}


