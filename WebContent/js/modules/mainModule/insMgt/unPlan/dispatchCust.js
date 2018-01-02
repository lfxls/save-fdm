var menuId = '12100';
var selCustGrid = '';
var unSelCustGrid = '';
var selUrl = ctx + '/main/insMgt/insUnPlan!querySelCust.do';
var unSelUrl = ctx + '/main/insMgt/insUnPlan!queryUnSelCust.do';
Ext.onReady(function() {	
	var unSel_cm = [{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100,
		sortable : true
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 100
	}, {
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 100
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		dataIndex : 'UID',
		hidden : true	
	}];
	
	var sel_cm = [{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100,
		sortable : true
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 100
	}, {
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 100
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		dataIndex : 'UID',
		hidden : true	
	}];
	
	var unSeltb = [{
		id : 'unSel',
		text : main.insMgt.unPlan.grid.tb.query,
		tooltip : main.insMgt.unPlan.grid.tb.query,
		iconCls : 'history',
		handler : function() {
			queryUnSelCust();
		}
	}];
	
	var seltb = [{
		id : 'sel',
		text : main.insMgt.unPlan.grid.tb.query,
		tooltip : main.insMgt.unPlan.grid.tb.query,
		iconCls : 'history',
		handler : function() {
			querySelCust();
		}
	}];
	
	if($F('operateType') != '04') {
		showSToolBar(unSeltb);
		showUSToolBar(seltb);
	} else {
		$('fTime').disabled=true;
	}
	
	unSelCustGrid = new Wg.Grid( {
		url : unSelUrl,
		el : 'unSelCustGrid',
		title : main.insMgt.unPlan.grid.title.unSelCust,
		heightPercent : 0.40,
		widthPercent : 0.98,
		cModel : unSel_cm,
		tbar : unSeltb,
		collapsible : true
	});
	
	selCustGrid = new Wg.Grid( {
		url : selUrl,
		el : 'selCustGrid',
		title : main.insMgt.unPlan.grid.title.selCust,
		heightPercent : 0.40,
		widthPercent : 0.98,
		cModel : sel_cm,
		tbar : seltb,
		collapsible : true
	});
	
	var p = $FF('dispForm');
	//初始可选用户
	unSelCustGrid.init(p,true,false);
	//初始化已选用户
	selCustGrid.init(p,true,false);
});

//查询可选用户
function queryUnSelCust() {
	var cnos = '';
	if(!$E($F('cnos'))) {
		cnos = $F('cnos');
	} 
	if(!$E($F('ecnos'))) {
		if(cnos == '') {
			cnos = $F('ecnos');
		} else {
			cnos = cnos + ',' + $F('ecnos');
		}
	} 
	var p = {
		cno : $F('cno'),
		cname : $F('cname'),
		nodeIddw : $F('nodeIddw'),
		cnos : cnos,
		dcnos : $F('dcnos')
	};
	unSelCustGrid.reload(p);
}

//查询已选用户
function querySelCust() {
	var cnos = '';
	if(!$E($F('cnos'))) {
		cnos = $F('cnos');
	} 
	if(!$E($F('ecnos'))) {
		if(cnos == '') {
			cnos = $F('ecnos');
		} else {
			cnos = cnos + ',' + $F('ecnos');
		}
	} 
	var p = {
		cno : $F('cno'),
		cname : $F('cname'),
		nodeIddw : $F('nodeIddw'),
		cnos : cnos	
	};
	selCustGrid.reload(p);
}

/**
 * 添加用户到已选区域
 */
function addCust() {
	var records = unSelCustGrid.getRecords();
	if(!records || records.length == 0) {
		Wg.alert(main.insMgt.unPlan.valid.selCust);
		return;
	}
	var cnos = "";
	for(var i = 0; i < records.length; i++) {
		if(i == 0) {
			cnos = records[i].data.CNO;
		} else {
			cnos = cnos + "," + records[i].data.CNO;
		}
	}
	if($F('operateType') == "01") {
		//新增情况下，选择用户存到cnos中
		if($E($('cnos').value)) {
			$('cnos').value = cnos;
		} else {
			$('cnos').value = $('cnos').value + "," + cnos;
		}
	} else if($F('operateType') == "02"){
		//编辑情况下，将选择的用户存到ecnos中
		if($E($('ecnos').value)) {
			$('ecnos').value = cnos;
		} else {
			$('ecnos').value = $('ecnos').value + "," + cnos;
		}
	}
	queryUnSelCust();
	querySelCust();
}

/**
 * 从已选区域中移除用户
 */
function delCust() {
	var records = selCustGrid.getRecords();
	if(!records || records.length == 0) {
		Wg.alert(main.insMgt.unPlan.valid.selCust);
		return;
	}
	
	for(var i = 0; i < records.length; i++) {
		var cnos = $F('cnos');//记录新增用户派发时的已选用户
		var ecnos = $F('ecnos');//记录编辑用户派发时，新选的用户，旧的还是存在cnos中
		var dcnos = $F('dcnos');;//记录编辑用户派发时的移除用户
		remCust(records[i].data.CNO,cnos,ecnos,dcnos);
	}
	
	queryUnSelCust();
	querySelCust();
}

/**
 * 选择处理人
 */
function selPop() {
	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.pop,
		el : 'selPOP',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 移除用户
 */
function remCust(cno,cnos,ecnos,dcnos) {
	var newCnos = '';
	var newEcnos = '';
	if(!$E(cnos)) {//存在用户户号分配组合值
		var cnoArray = cnos.split(",");
		for(var i = 0; i < cnoArray.length; i++) {
			if(cno != cnoArray[i] && newCnos != '') {
				newCnos = newCnos + "," + cnoArray[i];
			} else if(cno != cnoArray[i] && newCnos == ''){
				newCnos = cnoArray[i];
			}
			if($F('operateType') == '02') {//是编辑进来的情况下
				if(cno == cnoArray[i]) {
					if($E(dcnos)) {
						dcnos = cno;//记录移出已选择用户
					} else {
						dcnos = dcnos + "," + cno;//记录移出已选择用户
					}
				}
			}
		}
	}
	if(!$E(ecnos)) {//存在用户户号分配组合值
		var ecnoArray = ecnos.split(",");
		for(var i = 0; i < ecnoArray.length; i++) {
			if(cno != ecnoArray[i] && newEcnos != '') {
				newEcnos = newEcnos + "," + ecnoArray[i];
			} else if(cno != ecnoArray[i] && newEcnos == ''){
				newEcnos = ecnoArray[i];
			}
		}
	}
	cnos = newCnos;
	ecnos = newEcnos;
	$('cnos').value = cnos;
	$('ecnos').value = ecnos;
	$('dcnos').value = dcnos;
}

/**
 * 保存用户分配
 */
function save() {
	var cnos = $F('cnos');
	var ecnos = $F('ecnos');
	if($E(cnos) && $E(ecnos)){
		Wg.alert(main.insMgt.unPlan.valid.addCust);
		return;
	}
	if($E($F('fTime'))) {
		Wg.alert(main.insMgt.order.valid.plnFTime.notEmpty);
		return;
	}
	var popid = $F('popid');//处理人
	
	var operateType = $F('operateType');
	if("01" == operateType) {//新增
		var p = {
			cnos : cnos,//多个户号组合
			dispFlag : 'meter',
			popid : popid,
			fTime : $F('fTime')
		};
		addDispCust(p);
	} else if("02" == operateType) {//编辑
		var p = {
			cnos : cnos,//多个户号组合
			ecnos : ecnos,//多个户号组合
			woid : $F('woid'),
			dispFlag : 'meter',
			popid : popid,
			fTime : $F('fTime')
		};
		editDispCust(p);
	}
}

function addDispCust(p) {
	Ext.apply(p, {
		logger : main.insMgt.unPlan.log.add.dispCust
	});
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmAdd, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					$('operateType').value = '02';
					$('woid').value = res.dataObject;
					url = ctx + String.format('/main/insMgt/insUnPlan!initCustDisp.do?operateType={0}&dispFlag={1}&woid={2}&fTime={3}', 
							'02','meter',$F('woid'),$F('fTime'));
					window.location.href = url;
				}
			});
		});
	});	
}

function editDispCust(p) {
	Ext.apply(p, {
		logger : main.insMgt.unPlan.log.edit.dispCust
	});
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmEdit, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					url = ctx + String.format('/main/insMgt/insUnPlan!initCustDisp.do?operateType={0}&dispFlag={1}&woid={2}&fTime={3}', 
							'02','meter',$F('woid'),$F('fTime'));
					window.location.href = url;
					//刷新grid 这个一定要在前
//					parent.query();
					//关闭窗口
//					parent.win.close();
				}
			});
		});
	});	
}

/**
 * 获取单位树
 * @returns
 */
function getDwTree(type) {
	var	title = common.kw.other.PU;
	getTree('dw', title, unitCode, unitName, 'dw');
	if(type == 'sel') {
		$('nodeIdSel').value = $F('nodeIddw');
		$('nodeTextSel').value = $F('nodeTextdw');
		$('nodeTypeSel').value = $F('nodeTypedw');
	} else if(type == 'unSel') {
		$('nodeIdUnSel').value = $F('nodeIddw');
		$('nodeTextUnSel').value = $F('nodeTextdw');
		$('nodeTypeUnSel').value = $F('nodeTypedw');
	}
}

function showSToolBar(unSeltb) {
	unSeltb.push({
		id : 'add',
		text : main.insMgt.unPlan.grid.tb.add,
		tooltip : main.insMgt.unPlan.grid.tb.add,
		iconCls : 'add',
		handler : function() {
			addCust();
		}
	});
}

function showUSToolBar(seltb) {
	seltb.push({
		id : 'del',
		text : main.insMgt.unPlan.grid.tb.del,
		tooltip : main.insMgt.unPlan.grid.tb.del,
		iconCls : 'batch-delete',
		handler : function() {
			delCust();
		}
	});
}