var menuId = '11600';
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	hideLeft();
});

function save(operateType){
	if('add' == operateType){
		addHhu();
	}	
	if('edit' == operateType){
		updHhu();
	}
}

//新增HHU
function addHhu() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var hhuid = $F('hhuid');
	var model = $F('model');
	var bcap = $F('bcap');
	var appvn = $F('appvn');
	var status = $F('status');
	var p = {
		hhuid : hhuid,
		model : model,
		bcap : bcap,
		appvn : appvn,
		status : status,
		logger : main.arcMgt.hhuMgt.add.logger + hhuid
	};

	Wg.confirm(main.arcMgt.hhuMgt.add.confirm, function() {
		dwrAction.doDbWorks('hhuMgtAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

//修改HHU
function updHhu() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var hhuid = $F('hhuid');
	var model = $('model').value;
	var bcap = $F('bcap');
	var appvn = $F('appvn');
	var status = $('status').value;
	var p = {
		hhuid : hhuid,
		model : model,
		bcap : bcap,
		appvn : appvn,
		status : status,
		logger : main.arcMgt.hhuMgt.upd.logger + hhuid
	};

	Wg.confirm(main.arcMgt.hhuMgt.upd.confirm, function() {
		
		dwrAction.doDbWorks('hhuMgtAction', menuId + '02', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {					
					parent.query();
					parent.win.close();					
				}
			});
		});
	});
}

//检验函数
function dataValidate() {
	var hhuid = $F('hhuid');
	var model = $F('model');
	var status = $F('status');
	if($E(hhuid)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.hhuMgt.valid.hhuid, function(btn,text){
			if(btn=='ok'){
				$('hhuid').focus();
			}
		});
		return false;
	}
		
	if($E(model)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.hhuMgt.valid.model, function(btn,text){
			if(btn=='ok'){
				$('model').focus();
			}
		});
		return false;
	}
	
	if($E(status)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.hhuMgt.valid.status, function(btn,text){
			if(btn=='ok'){
				$('status').focus();
			}
		});
		return false;
	}
	
	//HHUID作为主键，不能传参时包含'，需要限制特殊字符，根据项目需求，数字和字母的组合可以满足main.arcMgt.hhuMgt.valid.hhuid.fmat
	if ( !$E(hhuid) && !/^[A-Za-z0-9]+$/.test(hhuid) ) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.hhuMgt.valid.hhuidFormat, function(btn,text){
			if(btn=='ok'){
				$('hhuid').focus();
			}
		});	
		return false;
	}
	
	//^[1-9]\d*$ 匹配正整数
	if( !$E($F('bcap')) && ( !/^[1-9]\d{1,7}$/.test($F('bcap')) ) ){
		Ext.Msg.alert(ExtTools_alert_title,main.arcMgt.hhuMgt.valid.bcap, function(btn,text){
			if(btn=='ok'){
				$('bcap').focus();
			}
		});	
		return false;
	}
	
	return true;	
}