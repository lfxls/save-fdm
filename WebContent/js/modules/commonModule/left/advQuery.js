Ext.onReady(function() {
	// 默认查询当前单位
	dwrAction.doAjax('leftTreeAction','getAdvList',{dwdm:'',doType:'dw'},{});
});

//单位取供电所
function changeDw(val) {
	dwrAction.doAjax('leftTreeAction','getAdvList',{dwdm:val,doType:'gds'},{});
}

// 查询
function query() {
	if (!valid())
		return;
	var p = {
		type : $F('type'),
		dylx : $F('dylx'),
		dwdm : $F('dwdm'),
		sjdwdm : $F('sjdwdm'),
		xlmc : $('xlmc') ? $F('xlmc') : '',
		tqmc : $('tqmc') ? $F('tqmc') : '',
		hhs : $F('hh'),
		advzdjhs : $F('zdjh'),
		zdljdzs : $F('zdljdz'),
		bjjhs : $F('bjjh'),
		simkh : $F('simkh'),
		zdgylx : $F('zdgylx'),
		zdzt : $F('zdzt').toString(),
		dydj : $F('dydj').toString(),
		sdrl : $F('sdrl'),
		zdysdrl : $F('rl') == '0' ? '' : 'true',
		minrl : $F('minrl'),
		maxrl : $F('maxrl'),
		zdjhFrom:$F('zdjhFrom'),
		zdjhTo:$F('zdjhTo')
	};
	if ($F('fwjb') == '10' && $F('sjdwdm') == '-1') { // 省级的查询必须取个单位
		Ext.apply(p, {
			dwdm : top.leftFrame.dwdm.value
		});
	}
	dwrAction.doAjax('leftTreeAction','setQueryInfo', p, {callback:advCallBack});
}

var advCallBack = function(res) {
	if (top.leftFrame.getAdvQuery()) {
		top.leftFrame.getAdvQuery();
	}
	if (top.win) {
		top.win.close();
	}
};
// 容量
function changeRl(v) {
	if (v == '0') {
		$('sdrl').style.display = 'inline';
		$('zdyrl').style.display = 'none';
	} else {
		$('sdrl').style.display = 'none';
		$('zdyrl').style.display = 'inline';
	}
}

function valid() {
	if ($F('hh') && $F('hh').split(',').length > 1000) {
		Wg.alert(plcxhh);
		return false;
	}
	if ($F('zdjh') && $F('zdjh').split(',').length > 1000) {
		Wg.alert(plcxzdjh);
		return false;
	}
	if ($F('zdljdz') && $F('zdljdz').split(',').length > 1000) {
		Wg.alert(plcxzdljdz);
		return false;
	}
	if ($F('bjjh') && $F('bjjh').split(',').length > 1000) {
		Wg.alert(plcxbjjh);
		return false;
	}
	if ($F('simkh') && $F('simkh').split(',').length > 1000) {
		Wg.alert(plcxsimkh);
		return false;
	}
	return true;
}