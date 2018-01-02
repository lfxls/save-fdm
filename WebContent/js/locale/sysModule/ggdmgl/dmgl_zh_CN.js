Ext.ns("ami.systemModule.ggdmgl");

Ext.ns("systemModule.ggdmgl");

systemModule.ggdmgl.dmgl = function(){};
systemModule.ggdmgl.dmgl.prototype.resourceBundle = {
		Text:{
		},
		Grid:{
			cz:'操作',
			xg:'修改',
			sc:'删除',
			xz:'新增',
			dm_sort_cm:{
				dmflmc:'分类名称',
				dmfl:'分类编码'
			},
			dmfllb:'分类列表',
			dm_cm:{
				dmfl:'分类编码', 
				dmmc:'编码名称',
				dmz:'编码值',
				disp_sn:'排序序号',
				isshow:'是否显示',
				yes : '是',
				No : '否'
			},
			dmlb:'编码列表'
		},
		Button:{
		},
		Prompt:{
			dmfl:'请选择编码分类'
		},
		Error:{
		},
		Remark:{
		},
		Title:{
			editdmfl:'编辑分类',
			editdmv:'编辑编码',
			addmv:'新增编码',
			adddmfl:'新增分类'
		},
		Validate:{
			
		},
		Logger:{
			delfl1:'删除分类：',
			delfl2:'以及相关编码',
			delvalue:'删除编码：'
		},
		Confirm:{
			delfl:'确认删除分类？',
			delvalue:'确认删除编码？'
		}
};
var systemModule_ggdmgl_dmgl = new systemModule.ggdmgl.dmgl();

