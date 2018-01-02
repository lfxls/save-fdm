Ext.ns("systemModule.ggdmgl");

systemModule.ggdmgl.xtcs = function(){};
systemModule.ggdmgl.xtcs.prototype.resourceBundle = {
		Text:{
		},
		Tree:{
		},
		Window:{
			xtcs_window_editParaSort_title : '编辑参数分类',
			xtcs_window_newParaSort_title : '添加参数分类',
			xtcs_window_editPara_title : '编辑参数',
			xtcs_window_addPara_title : '添加参数'
		},
		Grid: {
			xtcs_grid_sysparaSortGrid_cm_title : '参数分类列表',
			xtcs_grid_sysparaSortGrid_cm_opt : '操作',
			xtcs_grid_sysparaSortGrid_cm_csflmc : '参数分类名称',
			xtcs_grid_sysparaSortGrid_cm_csflbm : '参数分类编码',
			xtcs_grid_sysparaSortGrid_cm_csflsm : '参数分类说明',
			xtcs_grid_sysparaSortGrid_cm_yxbz : '有效标志',
			xtcs_grid_sysparaGrid_cm_title : '参数列表',
			xtcs_grid_sysparaGrid_cm_opt : '操作',
			xtcs_grid_sysparaGrid_cm_csflbm : '参数分类编码',
			xtcs_grid_sysparaGrid_cm_csmc : '参数名称',
			xtcs_grid_sysparaGrid_cm_cszlx : '参数值类型',
			xtcs_grid_sysparaGrid_cm_csz : '参数值',
			xtcs_grid_sysparaGrid_cm_cszsx : '参数值上限',
			xtcs_grid_sysparaGrid_cm_cszxx : '参数值下限',
			xtcs_grid_sysparaGrid_cm_yxbz : '有效标志'
		},
		Button:{
			xtcs_button_add : '新增',
			xtcs_button_edit : '修改',
			xtcs_button_del : '删除'
		},
		Prompt:{
			xtcs_prompt_null_csflbm: '参数分类编码不能为空',
			xtcs_prompt_null_csflmc: '参数分类名称不能为空',
			xtcs_prompt_null_yxbz: '有效标志不能为空',
			xtcs_prompt_null_cszlx: '参数值类型不能为空',
			xtcs_prompt_null_csmc: '参数名称不能为空',
			xtcs_prompt_null_csz : '参数值不能为空',
			xtcs_prompt_addParaSort_comfirm: '确认新增系统参数类型吗?',
			xtcs_prompt_updParaSort_comfirm: '确认更新系统参数类型吗?',
			xtcs_prompt_delParaSort_comfirm: '确认删除系统参数类型吗?',
			xtcs_prompt_addPara_comfirm: '确认新增系统参数吗?',
			xtcs_prompt_updPara_comfirm: '确认更新系统参数吗?',
			xtcs_prompt_delPara_comfirm: '确认删除系统参数吗?',
			xtcs_prompt_sel_csflbm: '请选择参数分类编码'
		},
		Error:{
		},
		Remark:{
		},
		Validate:{
		},
		LOG:{
			xtcs_log_addSortPara: '新增系统参数分类', 
			xtcs_log_delSortPara: '删除系统参数分类', 
			xtcs_log_updSortPara: '编辑系统参数分类', 
			xtcs_log_addPara: '新增系统参数', 
			xtcs_log_delPara: '删除系统参数', 
			xtcs_log_updPara: '编辑系统参数'
		}
};
var systemModule_ggdmgl_xtcs = new systemModule.ggdmgl.xtcs();
