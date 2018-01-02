Ext.onReady(function() {
	$('sjx').readOnly = true;
	$('sjx').style.backgroundColor = "#cccccc";
	$('sjxmc').readOnly = true;
	$('sjxmc').style.backgroundColor = "#cccccc";
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
	
	if ($E($F('sjxmc'))) {
		Ext.Msg.alert(add_null_ts, mc_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('sjxmc').focus();
				}
			});
		return false;
	}
	
	if ($E($F('kd')) || !isNumber($F('kd'))) {
		Ext.Msg.alert(add_null_ts, kd_null,
			function(btn, text) {
				if (btn == 'ok') {
					$('kd').focus();
				}
			});
		return false;
	}
		
	var p = {
			rwlx : '01',
			sjx : $F('sjx'),
			sjxmc : $F('sjxmc'),
			dlx : $F('dlx'),
			xlx : $F('xlx'),
			dw : $F('dw'),
			kd : $F('kd'),
			sfxs : $F('sfxs'),
			fmt : $F('fmt'),
			ctpt : $F('ctpt'),
			txid : $F('txid'),
			pxbm : $F('pxbm'),
			logger : pz_bc_logger + $F('sjxmc')
	};
	Wg.confirm(confirm_bc, function(){
		dwrAction.doDbWorks('cbsjpzAction', '54700' + '01', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

function getSjx() {
	var url = String.format(ctx + '/system/ggdmgl/cbsjpz!initDetail.do?dlx={0}', $F('dlx'));
	var baseParam = {
		url : url,
		title : cbpz_sjx,
		el : 'selsjx',
		width : 500,
		height : 300,
		draggable : true
	};
	
	sjxWin = new Wg.window(baseParam);
	sjxWin.open();
}

