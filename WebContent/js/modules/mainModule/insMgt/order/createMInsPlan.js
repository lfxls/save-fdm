var menuId = '12200';
Ext.onReady(function() {
	changeBType($F('bussType'));
});

/**
 * 用户选择
 */
function queryCust(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initCust.do?otype={0}','0');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.cust,
		el : 'queryCust',
		width : 620,
		height : 380,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 变压器选择
 */
function queryTf(){
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return;
	}
	var url = String.format(ctx + '/main/insMgt/insPlan!initTf.do?uid={0}&selTfFlag={1}', 
							$F('uid'), 'meter');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.tf,
		el : 'queryTf',
		width : 600,
		height : 450,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();	
}

/**
 * 表计选择
 */
function queryMeter(type){
	var mczhs = $F('mczh');//新增的表安装计划
	var mdzhs = $F('mdzh');//选择表安装计划
	var omsns = '';//记录已选区域中新增换表或拆表选择的老表号
	var nmsns = '';//记录已选区域中新增换表或拆表选择的新表号
	var domsns = '';//记录已选区域中删除换表或拆表安装计划的老表号
	var dnmsns = '';//记录已选区域中删除换表或拆表安装计划的新表号
	var url = '';
	if(type == 'new') {//新表查询
		if(!saveValidate()) {
			return;
		}
		url = String.format(ctx + '/main/insMgt/insOrder!initMeter.do?type={0}&bussType={1}&omsns={2}&domsns={3}&nmsns={4}&dnmsns={5}&mType={6}&wir={7}&mMode={8}&uid={9}',
				type,$F('bussType'),omsns,domsns,nmsns,dnmsns,$F('mType'),$F('wir'),$F('mMode'),$F('uid'));
		if(!$E(mczhs)) {
			nmsns = splitNMSN(mczhs);
		}
		if(!$E(mdzhs)) {
			dnmsns = splitNMSN(mdzhs);
		}
	} else {//老表查询
		url = String.format(ctx + '/main/insMgt/insOrder!initMeter.do?type={0}&bussType={1}&omsns={2}&domsns={3}&nmsns={4}&dnmsns={5}',
				type,$F('bussType'),omsns,domsns,nmsns,dnmsns);
		if(!$E(mczhs)) {
			omsns = splitOMSN(mczhs);
		}
		if(!$E(mdzhs)) {
			domsns = splitOMSN(mdzhs);
		}
	}
//	if(!$E(mczhs)) {
//		var mczhsArray = mczhs.split(';');
//		for(var i = 0; i < mczhsArray.length; i++) {
//			var mczh = mczhsArray[i].split(',');
//			if(mczh.length >= 14) {//老表号值存在第14个位置
//				if(omsns == '') {
//					omsns = mczh[13];
//				} else {
//					omsns = omsns + ',' + mczh[13];
//				}
//			}
//		}
//	}
//	
//	if(!$E(mdzhs)) {
//		var mdzhsArray = mdzhs.split(';');
//		for(var i = 0; i < mdzhsArray.length; i++) {
//			var mdzh = mdzhsArray[i].split(',');
//			if(mdzh.length >= 14) {//老表号值存在第14个位置
//				if(domsns == '') {
//					domsns = mdzh[13];
//				} else {
//					domsns = domsns + ',' + mdzh[13];
//				}
//			}
//		}
//	}
//	var url = String.format(ctx + '/main/insMgt/insOrder!initMeter.do?type={0}&bussType={1}&omsns={2}&domsns={3}&nmsns={4}&dnmsns={5}&mType={6}&wir={7}&mMode={8}',
//			type,$F('bussType'),omsns,domsns,nmsns,dnmsns,$F('mType'),$F('wir'),$F('mMode'));
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.meter,
		el : 'queryMeter',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

/**
 * 剥离出老表号
 */
function splitOMSN(zhs) {
	var msns = '';
	var zhsArray = zhs.split(';');
	for(var i = 0; i < zhsArray.length; i++) {
		var zh = zhsArray[i].split(',');
		if(zh.length >= 14) {//老表号值存在第14个位置
			if(zh[13] != '') {
				if(msns == '') {
					msns = zh[13];
				} else {
					msns = msns + ',' + zh[13];
				}
			}
		}
	}
	return msns;
}

/**
 * 剥离出新表号
 */
function splitNMSN(zhs) {
	var msns = '';
	var zhsArray = zhs.split(';');
	for(var i = 0; i < zhsArray.length; i++) {
		var zh = zhsArray[i].split(',');
		if(zh.length >= 13) {//新表号值存在第13个位置
			if(zh[12] != '') {
				if(msns == '') {
					msns = zh[12];
				} else {
					msns = msns + ',' + zh[12];
				}
			}
		}
	}
	return msns;
}

function changeBType(bussType) {
	if('0' == bussType) {//新装
		if ($('omsn_s')) {
			$('omsn_s').style.display = 'none';
		}
		if ($('nmsn_s')) {
			$('nmsn_s').style.display = '';
		}
		if ($('cno_sel')) {
			$('cno_sel').style.display = '';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = '';
		}
		$('mType').disabled = false;
	 	$('wir').disabled = false;
	 	$('mMode').disabled = false; 
		$('mType').style.backgroundColor='';
		$('wir').style.backgroundColor='';
		$('mMode').style.backgroundColor='';
	} else if('1' == bussType) {//换
		if ($('omsn_s')) {
			$('omsn_s').style.display = '';
		}
		if ($('nmsn_s')) {
			$('nmsn_s').style.display = '';
		}
		if ($('cno_sel')) {
			$('cno_sel').style.display = 'none';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = 'none';
		}
		$('mType').disabled = false;
	 	$('wir').disabled = false;
	 	$('mMode').disabled = false; 
		$('mType').style.backgroundColor='';
		$('wir').style.backgroundColor='';
		$('mMode').style.backgroundColor='';
	} else if('2' == bussType) {//拆
		if ($('omsn_s')) {
			$('omsn_s').style.display = '';
		}
		if ($('nmsn_s')) {
			$('nmsn_s').style.display = 'none';
		}
		if ($('cno_sel')) {
			$('cno_sel').style.display = 'none';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = 'none';
		}
		$('mType').disabled = true;
	 	$('wir').disabled = true;
	 	$('mMode').disabled = true; 
		$('mType').style.backgroundColor='#cccccc';
		$('wir').style.backgroundColor='#cccccc';
		$('mMode').style.backgroundColor='#cccccc';
	}
}

/**
 * 安装计划保存校验
 */
function saveValidate(){
	//表计局号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('omsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldMeterNo.select, function(btn,text){
				if(btn=='ok'){
					$('omsn').focus();
				}
			});
			return false;
		}
	}
	
	//用户
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
	}
	
	//变压器
	if(trim($('tfName').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.tf.select, function(btn,text){
			if(btn=='ok'){
				$('tfId').focus();
			}
		});
		return false;
	}
	
	//表计类型
	if(trim($('mType').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.meterType.select, function(btn,text){
			if(btn=='ok'){
				$('mType').focus();
			}
		});
		return false;
	}
	
	//接线方式
	if(trim($('wir').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.wiring.select, function(btn,text){
			if(btn=='ok'){
				$('wir').focus();
			}
		});
		return false;
	}
	
	//表计模式
	if(trim($('mMode').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.meterMode.select, function(btn,text){
			if(btn=='ok'){
				$('mMode').focus();
			}
		});
		return false;
	}
	return true;
}

/**
 * 保存安装计划表计
 */
function save(){
	if(!saveValidate()) {
		return;
	}
	if(existSame()) {
		return;
	}
	var bussType = $F('bussType'); //业务类型
	var zh = '';
	if(bussType == '0'){//新装
		zh = $F('bussType') + ',' + '' + ',' +
		$F('cno') + ',' + $F('cname') + ',' + 
		$F('uid') + ',' +
		$F('uname') + ',' + $F('tfId') + ',' +
		$F('tfName') + ',' + $F('mType') + ',' +
		$F('wir') + ',' + $F('mMode') + ',' +
		$F('nmsn') + ',' + ''+ ',' +
		'0';
	} else if(bussType == '1'){//更换
		zh = $F('bussType') + ',' + '' + ',' + 
		$F('cno') + ',' + $F('cname') + ',' + 
		$F('uid') + ',' +
		$F('uname') +',' + $F('tfId') + ',' +
		$F('tfName') + ',' + $F('mType') + ',' +
		$F('wir') + ',' + $F('mMode') + ',' +
		$F('nmsn') + ',' + $F('omsn') + ',' +
		'0';
	} else if(bussType == '2'){//拆除
		zh = $F('bussType') + ',' + '' + ',' +
		$F('cno') + ',' + $F('cname') + ',' + 
		$F('uid') + ',' +
		$F('uname') + ',' + $F('tfId') + ',' +
		$F('tfName') + ',' + $F('mType') + ',' +
		$F('wir') + ',' + $F('mMode') + ',' +
		'' + ',' + $F('omsn') + ',' +
		'0';
	}
	if(parent.$('mczh').value == '') {
		parent.$('mczh').value = zh;
	} else {
		parent.$('mczh').value = parent.$('mczh').value + ';' + zh;
	}
	parent.mInsPQuery();
	parent.win.close();
}

/**
 * 验证创建的安装计划是否存在新表号或老表号重复情况
 * @returns {Boolean}
 */
function existSame() {
	if(parent.$('mczh').value == '') {
		return false;
	} 
	var exist = false;
	var existZh = parent.$('mczh').value;
	var zhs = existZh.split(";");
	var bussType = $F('bussType'); //业务类型
	var nmsn = $F('nmsn'); //新表号
	var omsn = $F('omsn'); //老表号
	for(var i = 0; i < zhs.length; i++) {
		var zh = zhs[i].split(",");
		if((bussType == '0' && !$E(nmsn) && nmsn == zh[11]) ||
		   (bussType == '1' && (!$E(nmsn) && nmsn == zh[11])) ||
		   (bussType == '1' && omsn == zh[12]) ||
		   (bussType == '2' && omsn == zh[12])) {
			exist = true;
			break;
		}
	}
	if(exist == true) {
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.sameMeter.inOtherPlan, function(btn,text){
			if(btn=='ok'){
				
			}
		});
	}
	return exist;
}