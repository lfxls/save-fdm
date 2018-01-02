Ext.ns("ami.systemModule.ggdmgl");

Ext.ns("systemModule.ggdmgl");

systemModule.ggdmgl.dmgl_value_edit = function(){};
systemModule.ggdmgl.dmgl_value_edit.prototype.resourceBundle = {
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
			dmfl:'编码分类不为空',
			dmmc:'编码名称不为空',
			dmz:'编码值不为空',
			disp_sn_notNull:'排序序号不能为空',
			disp_sn_isNumber:'排序序号必须是最大长度为5的数字'
		},
		Logger:{	
			adddmz:'新增编码名称：',
			upddmz:'修改编码名称：'
		},
		Confirm:{
			addmz:'确认新增编码？',
			upddmz:'确认修改编码？'
		}
};
var systemModule_ggdmgl_dmgl_value_edit = new systemModule.ggdmgl.dmgl_value_edit();

