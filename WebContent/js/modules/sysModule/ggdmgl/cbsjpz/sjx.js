Ext.onReady(function() {
	if(!$E($F('sjx'))) {
		$('sjx').readOnly = true;
		$('sjx').style.backgroundColor = "#cccccc";
	}
});

function save() {
	if ($E($F('sjx'))) {
		Ext.Msg.alert(add_null_ts, sjx_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('sjx').focus();
				}
			});
		return false;
	}
	
	if (!isNumAndLetter($F('sjx'))) {
		Ext.Msg.alert(add_null_ts, sjx_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('sjx').focus();
				}
			});
		return false;
	}
		
	if ($E($F('sjxmc'))) {
		Ext.Msg.alert(add_null_ts, mc_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('sjxmc').focus();
				}
			});
		return false;
	}
		
	var p = {
			rwlx : '02',
			sjx : $F('sjx'),
			sjxmc : $F('sjxmc'),
			dlx : $F('dlx'),
			xzyy : $F('xzyy'),
			logger : sjx_bc_logger + $F('sjxmc')
	};
	Wg.confirm(confirm_bc, function(){
		dwrAction.doDbWorks('cbsjpzAction', '54700' + '01', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.changeDlx('');
					parent.win.close();
				}
			});
		});
	});
}


