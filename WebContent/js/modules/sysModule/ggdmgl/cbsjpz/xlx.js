Ext.onReady(function() {
	if(!$E($F('xlid'))) {
		$('xlid').readOnly = true;
		$('xlid').style.backgroundColor = "#cccccc";
	}
});

function save() {
	if ($E($F('xlid'))) {
		Ext.Msg.alert(add_null_ts, xlx_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('xlid').focus();
				}
			});
		return false;
	}
	
	if (!isNumAndLetter($F('xlid'))) {
		Ext.Msg.alert(add_null_ts, ffzf_err,
			function(btn, text) {
				if (btn == 'ok') {
					$('xlid').focus();
				}
			});
		return false;
	}
	
	if ($E($F('xlmc'))) {
		Ext.Msg.alert(add_null_ts, mc_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('xlmc').focus();
				}
			});
		return false;
	}	
		
	var p = {
			rwlx : '03',
			xlid : $F('xlid'),
			xlmc : $F('xlmc'),
			pxbm : $F('pxbm'),
			dlx : $F('dlx'),
			xzyy : $F('xzyy'),
			logger : xlx_bc_logger + $F('xlmc')
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


