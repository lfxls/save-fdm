Ext.ns("ami.systemModule.ggdmgl");

Ext.ns("systemModule.ggdmgl");

systemModule.ggdmgl.dmgl_sort_edit = function(){};
systemModule.ggdmgl.dmgl_sort_edit.prototype.resourceBundle = {
		Text:{
		},
		Grid:{
		},
		Button:{
		},
		Prompt:{
		},
		Error:{
		},
		Remark:{
		},
		Title:{
		},
		Validate:{
			dmflmc:'分类名称不能为空',
			dmflbm:'分类编码不能为空',
			dmflbmFormat:'分类编码不能包含特殊字符'
		},
		Logger:{
			adddmfl:'新增分类名称：',
		    upddmfl:'修改分类名称：'
		},
		Confirm:{
			adddmfl:'确认新增分类？',
			upddmfl:'确认修改分类？'
		}
};
var systemModule_ggdmgl_dmgl_sort_edit = new systemModule.ggdmgl.dmgl_sort_edit();

