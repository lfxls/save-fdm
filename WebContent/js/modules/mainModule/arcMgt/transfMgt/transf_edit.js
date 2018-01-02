var menuId = '11800';
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	hideLeft();
});

function save(operateType){
	if('add' == operateType){
		addTransf();
	}	
	if('edit' == operateType){
		updTransf();
	}
}

//新增变压器
function addTransf() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var p=$FF('transfForm');
	p.dataSrc = '1';
	Ext.apply(p,{logger:main.arcMgt.transfMgt.add.logger + $F('tfId')});//apply属性拷贝，会覆盖

	Wg.confirm(main.arcMgt.transfMgt.add.confirm, function() {
		dwrAction.doDbWorks('transfMgtAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

//修改变压器
function updTransf() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}	
	var p=$FF('transfForm');
	Ext.apply(p,{logger:main.arcMgt.transfMgt.upd.logger + $F('tfId')});//apply属性拷贝，会覆盖

	Wg.confirm(main.arcMgt.transfMgt.upd.confirm, function() {
		
		dwrAction.doDbWorks('transfMgtAction', menuId + '02', p, function(res) {
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
	var tfId = $F('tfId');
	var tfName = $F('tfName');
	var nodeIddw = $F('nodeIddw');
	var status = $F('status');
	
	if($E(tfId)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.tfId, function(btn,text){
			if(btn=='ok'){
				$('tfId').focus();
			}
		});
		return false;
	}
	if($E(tfName)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.tfName, function(btn,text){
			if(btn=='ok'){
				$('tfName').focus();
			}
		});
		return false;
	}
	//不需要检验变压器名是否有特殊字符
	/*var containSpecial = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/); 
	if(!$E(tfName) && containSpecial.test(tfName)){
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.tfnameSpecial, function(btn,text){
			if(btn=='ok'){
				$('tfName').focus();
			}
		});	
		return false;
	}*/
	
	if($E(nodeIddw)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.uid, function(btn,text){
			if(btn=='ok'){
				$('nodeIddw').focus();
			}
		});
		return false;
	}
	
	if($E(status)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.status, function(btn,text){
			if(btn=='ok'){
				$('status').focus();
			}
		});
		return false;
	}
	
	//电池容量必须是小于10位的数值
	if(!$E($F('cap')) && !/^\d{1,10}$/.test($F('cap'))){
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.transfMgt.valid.cap, function(btn,text){
			if(btn=='ok'){
				$('cap').focus();
			}
		});	
		return false;
	}
	
	return true;	
}

//单位树
function getDwTree() {
	var	title = main.arcMgt.transfMgt.tree.title.dw;
	getTree('dw', title, unitCode, unitName, 'dw');
}
