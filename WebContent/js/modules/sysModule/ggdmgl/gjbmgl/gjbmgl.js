var menuId = '54600';
Ext.onReady(function() {
	var czid = $F('czid');
	if (czid == '02') { // 编辑需要初始化
		initGjbm();
		$('gjbm').disabled = 'disabled';
		$('yy').disabled = 'disabled';
	}
});

function initGjbm() {
	dwrAction.doAjax('gjbmglAction', 'initGjbm', {
		gjbm : $F('gjbm'),
		lang : $F('yy')
	}, function(res) {
		if (res.success) {
			var gjbm = Ext.decode(res.msg);
			for ( var o in gjbm) {
				if ($(o) && gjbm[o])
					$(o).value = gjbm[o];
			}
			$('gjbm').readOnly = true;
			$('gjbm').style.backgroundColor = "#cccccc";
			$('yy').readOnly = true;
			$('yy').style.backgroundColor = "#cccccc";
		}
	});
}

function save() {
	if ($F('czid') == '01') {
		addGjbm();
	} else {
		editGjbm();
	}

}
// 添加
function addGjbm() {
	if (valid()) {
		var p = $FF('gjbmForm');
		Ext.apply(p, {
			logger : gjbmgl_title_addgjbm+"：" + $F('gjbm') 
		});
		Wg.confirm(gjbmgl_title_sureaddgjbm, function() {
			dwrAction.doDbWorks('gjbmglAction', menuId + $F('czid'), p,
					function(re) {
						Wg.alert(re.msg, function() {
							if (re.success) {
								parent.query();
								parent.win.close();
							}
						});
					});
		});
	}

}

// 编辑
function editGjbm() {
	if (valid()) {
		var p = $FF('gjbmForm');
		Ext.apply(p, {
			logger : gjbmgl_title_upgjbm+":" + $F('gjbm') 
		});
		Wg.confirm(gjbmgl_title_sureupgjbm, function() {
			dwrAction.doDbWorks('gjbmglAction', menuId + $F('czid'), p,
					function(re) {
						Wg.alert(re.msg, function() {
							if (re.success) {
								parent.query();
								parent.win.close();
							}
						});
					});
		});
	}
}

function valid() {
	if ($E($F('gjbm'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_gjbm_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('gjbm').focus();
			}
		});
		return false;
	}
	if ($E($F('gjmc'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_gjmc_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('gjmc').focus();
			}
		});
		return false;
	}
	if ($E($F('yy'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_yy_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('yy').focus();
			}
		});
		return false;
	}
	if ($E($F('gylx'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_gylx_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('gylx').focus();
			}
		});
		return false;
	}
	if ($E($F('gjlb'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_gjlb_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('gjlb').focus();
			}
		});
		return false;
	}
	if ($E($F('gjdj'))) {
		Ext.Msg.alert(gjbmgl_node_ts, gjbmgl_gjdj_notnull, function(btn, text) {
			if (btn == 'ok') {
				$('gjdj').focus();
			}
		});
		return false;
	}
	return true;
}