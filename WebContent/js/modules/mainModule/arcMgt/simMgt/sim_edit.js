var menuId = '11500';
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	hideLeft();
});

function save(operateType){
	if('add' == operateType){
		addSim();
	}	
	if('edit' == operateType){
		updSim();
	}
}

//新增sim卡
function addSim() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var simno = $F('simno');
	var simsn = $F('simsn');
	var msp = $F('msp');		
	var p = {
		simno : simno,
		simsn : simsn,
		msp : msp,		
		logger : main.arcMgt.simMgt.add.logger + simno
	};

	Wg.confirm(main.arcMgt.simMgt.add.confirm, function() {
		dwrAction.doDbWorks('simMgtAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

//修改sim卡
function updSim() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var simno = $F('simno');
	var simsn = $F('simsn');
	var msp = $F('msp');
	var p = {
		simno : simno,
		simsn : simsn,
		msp : msp,
		logger : main.arcMgt.simMgt.upd.logger + simno
	};

	Wg.confirm(main.arcMgt.simMgt.upd.confirm, function() {
		
		dwrAction.doDbWorks('simMgtAction', menuId + '02', p, function(res) {
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
	var simno = $F('simno');
	var msp = $F('msp');
	if($E(simno)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.simMgt.valid.simno, function(btn,text){
			if(btn=='ok'){
				$('simno').focus();
			}
		});
		return false;
	}
	
	if($E(msp)) {
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.simMgt.valid.msp, function(btn,text){
			if(btn=='ok'){
				$('msp').focus();
			}
		});
		return false;
	}
	
	return true;	
}

function isNumbers(){
	//alert("1");
	//SIM卡号必须是数字
	if(!$E($F('simno')) && !/^\d{1,32}$/.test($F('simno'))){
		Ext.Msg.alert(ExtTools_alert_title, main.arcMgt.simMgt.valid.simnoisnumber, function(btn,text){
			if(btn=='ok'){
				$('simno').focus();
			}
		});	
		return false;
	}
}
