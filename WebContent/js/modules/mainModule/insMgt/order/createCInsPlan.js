var menuId = '12200';
Ext.onReady(function() {
	changeBType($F('bussType'));
});

/**
 * 变压器选择
 */
function queryTf(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initTf.do?dwdm={0}&dwmc={1}&selTfFlag={2}', 
							$F('dwdm'), $En($F('dwmc')), 'col');
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
 * 采集器选择
 */
function queryCol(type){
	var url = String.format(ctx + '/main/insMgt/insPlan!initCol.do?type={0}&bussType={1}',type,$F('bussType'));
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.col,
		el : 'queryCol',
		width : 620,
		height : 420,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}

function changeBType(bussType) {
	if('0' == bussType) {//新装
		if ($('ocsn_s')) {
			$('ocsn_s').style.display = 'none';
		}
		if ($('ncsn_s')) {
			$('ncsn_s').style.display = '';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = '';
		}
	 	$('colM').disabled = false; 
		$('colM').style.backgroundColor='';
	} else if('1' == bussType) {//换
		if ($('ocsn_s')) {
			$('ocsn_s').style.display = '';
		}
		if ($('tf_sel')) {
			$('tf_sel').style.display = 'none';
		}
	 	$('colM').disabled = false; 
		$('colM').style.backgroundColor='';
	} else if('2' == bussType) {//拆
		if ($('ocsn_s')) {
			$('ocsn_s').style.display = '';
		}
		if ($('ncsn_s')) {
			$('ncsn_s').style.display = 'none';
		}
		$('colM').disabled = true;
		$('colM').style.backgroundColor='#cccccc';
	}
}

/**
 * 安装计划保存校验
 */
function saveValidate(){
	//表计局号
	if($('bussType').value == '1' || $('bussType').value == '2'){
		if(trim($('ocsn').value) == ''){
			Ext.Msg.alert(main.insMgt.plan.valid.tip,
					main.insMgt.plan.valid.oldCol.select, function(btn,text){
				if(btn=='ok'){
					$('ocsn').focus();
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
	
	//采集器型号
	if(trim($('colM').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.colModel.select, function(btn,text){
			if(btn=='ok'){
				$('colM').focus();
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
		$F('addr') + ',' + $F('colM') + ',' + 
		$F('ncsn') + ',' + '';
	} else if(bussType == '1'){//更换
		zh = $F('bussType') + ',' + '' + ',' +
		$F('uid') + ',' + $F('uname') + ',' + 
		$F('tfId') + ',' + $F('tfName') + ',' + 
		$F('addr') + ',' + $F('colM') + ',' + 
		$F('ncsn') + ',' + $F('ocsn');
	} else if(bussType == '2'){//拆除
		zh = $F('bussType') + ',' + '' + ',' +
		$F('uid') + ',' + $F('uname') + ',' + 
		$F('tfId') + ',' + $F('tfName') + ',' + 
		$F('addr') + ',' + $F('colM') + ',' + 
		'' + ',' + $F('ocsn');
	}
	if(parent.$('cczh').value == '') {
		parent.$('cczh').value = zh;
	} else {
		parent.$('cczh').value = parent.$('cczh').value + ';' + zh;
	}
	parent.cInsPQuery();
	parent.win.close();
}