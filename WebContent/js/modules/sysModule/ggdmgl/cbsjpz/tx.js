Ext.onReady(function() {
	if(!$E($F('txid'))) {
		$('txid').readOnly = true;
		$('txid').style.backgroundColor = "#cccccc";
	}
});

function save() {
	if ($E($F('txid'))) {
		Ext.Msg.alert(add_null_ts, tx_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('txid').focus();
				}
			});
		return false;
	}
	
	if (!isNumAndLetter($F('txid'))) {
		Ext.Msg.alert(add_null_ts, ffzf_err,
			function(btn, text) {
				if (btn == 'ok') {
					$('txid').focus();
				}
			});
		return false;
	}
		
	if ($E($F('txmc'))) {
		Ext.Msg.alert(add_null_ts, mc_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('txmc').focus();
				}
			});
		return false;
	}
		
	var p = {
			rwlx : '04',
			txid : $F('txid'),
			txmc : $F('txmc'),
			dlx : $F('dlx'),
			xzyy : $F('xzyy'),
			logger : txlx_bc_logger + $F('txmc')
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


