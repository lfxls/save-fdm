var menuId = "00000";
Ext.onReady(function() {
	$('qzm').focus();
});

function saveGroup() {
/*	
	var smzq =$F('smzq');
	if (smzq == '') {
		Ext.Msg.alert(alert_Title,smzq_null,function(btn,text){
			if(btn=='ok'){
				$('smzq').focus();
			}
		});
		return;
	}
	if (!isNumber(smzq)) {
		Ext.Msg.alert(alert_Title,smzq_num,function(btn,text){
			if(btn=='ok'){
				$('smzq').focus();
			}
		});
		return;
	}
	if(smzq*1>999){
		Ext.Msg.alert(alert_Title,smzq_numMax,function(btn,text){
			if(btn=='ok'){
				$('smzq').focus();
			}
		});
		return;
	}
	*/
	
	var qzm = $F('qzm');
	if (qzm == '') {
		Ext.Msg.alert(alert_Title,qzm_null,function(btn,text){
			if(btn=='ok'){
				$('qzm').focus();
			}
		});
		return;
	}
	if (!checkGroupName(qzm)) {
		Ext.Msg.alert(alert_Title,qzm_error,function(btn,text){
			if(btn=='ok'){
				$('qzm').focus();
			}
		});
		return;
	}
	
	var p = $FF('queryForm');
	p.sfgx=$('sfgx').checked == true?'1':'0';
	p.smzq=$('yxsj').checked == true?'1':'0';
	var menuIds = menuId+addOpt;
	dwrAction.doDbWorks('qzszAction',menuIds, p, function(res) {
		Wg.alert(res.msg,function(){
			if(res.success) {
				parent.simpleGrid.reload({});
				parent.win.close();
			}
		});
	});
}