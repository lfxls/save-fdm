//首页管理
Ext.ns("ami.sysModule.qxgl.sygl");
Ext.ns("sysModule.qxgl.sygl");

sysModule.qxgl.sygl = function(){};
sysModule.qxgl.sygl.prototype.resourceBundle = {
		Text:{
			add:'Add',
			edit:'Edit',
			del:'Delete',
			warn:'Note',
			preview:'Preview Window'
		},
		Grid:{
			op:'Edit',
			symc:'Homepage Name',
			sylx:'Homepage Type',
			url:'URL',
			ms:'Description'
		},
		Button:{
		},
		Prompt:{
			mccf:'Homepage name already exists, please input again!'
		},
		Error:{
		},
		Remark:{
		},
		Title:{
			list:'List of Homepage Management',
			add:'Add Homepage Informtion',
			edit:'Edit Homepage Information',
			add_success:'Homepage is added!',
			upd_success:'Homepage is modified!',
			del_success:'Homepage is deleted!'
		},
		Validate:{
			lang:'Language can not be empty!',
			symc:'Please input homepage name!',
			url: 'Please input URL!'
		},
		Logger:{
			add:'New Homepage:',
			edit:'Edit Homepage:',
			del:'Delete Homepage:'
		},
		Confirm:{
			add:'Are you sure new the Homepage?',
			edit:'Are you sure edit the Homepage?',
			del:'Are you sure delete the Homepage?'
		}
};

var sysModule_qxgl_sygl = new sysModule.qxgl.sygl();

