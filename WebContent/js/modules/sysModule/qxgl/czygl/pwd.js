var menuId = '52100';
//保存
function save() {
	if ($E($F('xm'))) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_name_null,function(btn,text){
			if(btn=='ok'){
				$('xm').focus();
			}
		});
		return false;
	}
	if($E($F('oldPwd'))) {
		Wg.alert(czygl_pwd_old_null);
		return;
	}
	if($E($F('newPwd'))) {
		Wg.alert(czygl_pwd_new_null);
		return;
	}
	if($F('newPwd') != $F('rePwd')) {
		Wg.alert(czygl_pwd_pwd_wrong);
		return;
	}
	if(!$E($F('yxdz'))){
		var validate = /^[_a-zA-Z0-9]+@([_a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,3}$/;
		if(!validate.test($F('yxdz'))){
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_yxdz_null,function(btn,text){
				if(btn=='ok'){
					$('yxdz').focus();
				}
			});
			return false;
		}
	}
	
	var p = {
		xm: $F('xm'),
		oldPwd:$F('oldPwd'),
		newPwd:$F('newPwd'),
		sjhm: $F('sjhm'),
		dhhm: $F('dhhm'),
		yxdz: $F('yxdz'),
		type:'czy',
		logger : czygl_pwd_logger
	};
	Wg.confirm(czygl_pwd_confirm, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '04', p, function(res) {
			Wg.alert(res.msg);
		});
	});
	
}