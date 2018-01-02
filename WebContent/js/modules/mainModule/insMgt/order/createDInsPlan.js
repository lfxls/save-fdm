var menuId = '12200';
Ext.onReady(function() {
	changeBType($F('bussType'));
});

/**
 * 变压器选择
 */
function queryTf(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initTf.do?dwdm={0}&dwmc={1}&selTfFlag={2}', 
							$F('dwdm'), $En($F('dwmc')), 'dcu');
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
function queryDcu(type){
	var url = String.format(ctx + '/main/insMgt/insPlan!initMeter.do?type={0}&bussType={1}',type,$F('bussType'));
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.dcu,
		el : 'queryDcu',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

function changeBType(bussType) {
	if('0' == bussType) {//新装
		if ($('odsn_s')) {
			$('odsn_s').style.display = 'none';
		}
		if ($('ndsn_s')) {
			$('ndsn_s').style.display = '';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = '';
		}
	 	$('dcuM').disabled = false; 
		$('dcuM').style.backgroundColor='';
	} else if('1' == bussType) {//换
		if ($('odsn_s')) {
			$('odsn_s').style.display = '';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = 'none';
		}
	 	$('dcuM').disabled = false; 
		$('dcuM').style.backgroundColor='';
	} else if('2' == bussType) {//拆
		if ($('odsn_s')) {
			$('odsn_s').style.display = '';
		}
		if ($('ndsn_s')) {
			$('ndsn_s').style.display = 'none';
		}
		$('dcuM').disabled = true;
		$('dcuM').style.backgroundColor='#cccccc';
	}
}

/**
 * 安装计划保存校验
 */
function saveValidate(){
	//表计局号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('odsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldDcu.select, function(btn,text){
				if(btn=='ok'){
					$('odsn').focus();
				}
			});
			return false;
		}
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
	if(trim($('dcuM').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.dcuModel.select, function(btn,text){
			if(btn=='ok'){
				$('dcuM').focus();
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
	var bussType = $F('bussType'); //业务类型
	var zh = '';
	if(bussType == '0'){//新装
		zh = $F('bussType') + ',' + '' + ',' +
		$F('uid') + ',' + $F('uname') + ',' + 
		$F('tfId') + ',' + $F('tfName') + ',' + 
		$F('addr') + ',' + $F('dcuM') + ',' + 
		$F('ndsn') + ',' + '';
	} else if(bussType == '1'){//更换
		zh = $F('bussType') + ',' + '' + ',' +
		$F('uid') + ',' + $F('uname') + ',' + 
		$F('tfId') + ',' + $F('tfName') + ',' + 
		$F('addr') + ',' + $F('dcuM') + ',' + 
		$F('ndsn') + ',' + $F('odsn');
	} else if(bussType == '2'){//拆除
		zh = $F('bussType') + ',' + '' + ',' +
		$F('uid') + ',' + $F('uname') + ',' + 
		$F('tfId') + ',' + $F('tfName') + ',' + 
		$F('addr') + ',' + $F('dcuM') + ',' + 
		'' + ',' + $F('odsn');
	}
	if(parent.$('dczh').value == '') {
		parent.$('dczh').value = zh;
	} else {
		parent.$('dczh').value = parent.$('dczh').value + ';' + zh;
	}
	parent.dInsPQuery();
	parent.win.close();
}