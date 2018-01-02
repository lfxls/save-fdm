//首页管理
Ext.ns("ami.sysModule.qxgl.sygl");
Ext.ns("sysModule.qxgl.sygl");

sysModule.qxgl.sygl = function(){};
sysModule.qxgl.sygl.prototype.resourceBundle = {
		Text:{
			add:'添加',
			edit:'修改',
			del:'删除',
			warn:'提示',
			preview:'预览窗口'
		},
		Grid:{
			op:'操作',
			symc:'首页名称',
			sylx:'首页类型',
			url:'URL',
			ms:'描述'
		},
		Button:{
		},
		Prompt:{
			mccf:'首页名称已存在，请重新输入！'
		},
		Error:{
		},
		Remark:{
		},
		Title:{
			list:'首页管理列表',
			add:'新增首页信息',
			edit:'编辑首页信息',
			add_success:'新增首页成功！',
			upd_success:'修改首页成功！',
			del_success:'删除首页成功！'
		},
		Validate:{
			lang:'语言不能为空！',
			symc:'请输入首页名称！',
			url: '请输入URL！'
		},
		Logger:{
			add:'新建首页:',
			edit:'编辑首页:',
			del:'删除首页:'
		},
		Confirm:{
			add:'确定要新建该首页信息吗？',
			edit:'确定要编辑该首页信息吗？',
			del:'确定要删除该首页信息吗？'
		}
};

var sysModule_qxgl_sygl = new sysModule.qxgl.sygl();

