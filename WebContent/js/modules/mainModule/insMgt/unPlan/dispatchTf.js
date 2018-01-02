var menuId = '12100';
var dispGrid = '';
var grid_url = ctx + '/main/insMgt/insUnPlan!queryTfDisp.do';
Ext.onReady(function() {	
	var _cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if($F('status') != '1' || $F('status') != '2' || $F('status') != '3') {
				url += String.format('<a class="del" title="\{1}\" href="javascript:delTf(\'{0}\');"></a>',
						record.data.TFID, main.insMgt.plan.grid.op.del);
			}
			return url;
		}
	}, {
		header : common.kw.transformer.TFID,
		dataIndex : 'TFID',
		width : 120,
		sortable : true
	}, {
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 120
	}, {
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120
	},{
		header : main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
		width : 120
	},{
		dataIndex : 'UID',
		hidden : true	
	}];
	
	var toolbar = [{
		id : 'add',
		text : main.insMgt.unPlan.grid.tb.add,
		tooltip : main.insMgt.unPlan.grid.tb.add,
		iconCls : 'add',
		handler : function() {
			selTf();
		}
	}];
	
	dispGrid = new Wg.Grid( {
		url : grid_url,
		el : 'dispGrid',
		title : main.insMgt.unPlan.grid.title.selected.tf,
		heightPercent : 0.73,
		cModel : _cm,
		tbar : toolbar
	});
	
	var p = $FF('dispForm');
	dispGrid.init(p);
});

// 查询
function query() {
	var tfIds = '';
	if(!$E($F('tfIds'))) {
		tfIds = $F('tfIds');
	} 
	if(!$E($F('etfIds'))) {
		if(tfIds == '') {
			tfIds = $F('etfIds');
		} else {
			tfIds = tfIds + ',' + $F('etfIds');
		}
	} 
	var p = {
			tfIds : tfIds	
	};
	dispGrid.reload(p);
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

function selTf() {
	var tfIds = '';
	if(!$E($F('tfIds'))) {
		tfIds = $F('tfIds');
	}
	if(!$E($F('etfIds'))) {
		if(tfIds == '') {
			tfIds = $F('etfIds');
		} else {
			tfIds = tfIds + ',' + $F('etfIds');
		}
	}
	var url = ctx + String.format('/main/insMgt/insUnPlan!initTf.do?operateType={0}&tfIds={1}',
			$F('operateType'),tfIds);
	var baseParam = {
		url : url,
		title : main.insMgt.unPlan.win.title.unSelected.tf,
		el : 'selTf',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 删除分配变压器
 */
function delTf(tfId) {
	var tfIds = $F('tfIds');//新增时分配变压器ID组合
	var etfIds = $F('etfIds');//编辑时分配变压器ID组合
	var newTfIds = '';
	var newEtfIds = '';
	if(!$E(tfIds)) {//存在变压器ID分配组合值
		var tfIdArray = tfIds.split(",");
		for(var i = 0; i < tfIdArray.length; i++) {
			if(tfId != tfIdArray[i] && newTfIds != '') {
				newTfIds = newTfIds + "," + tfIdArray[i];
			} else if(tfId != tfIdArray[i] && newTfIds == ''){
				newTfIds = tfIdArray[i];
			}
		}
	}
	if(!$E(etfIds)) {//存在变压器ID分配组合值
		var etfIdArray = etfIds.split(",");
		for(var i = 0; i < etfIdArray.length; i++) {
			if(tfId != etfIdArray[i] && newEtfIds != '') {
				newEtfIds = newEtfIds + "," + etfIdArray[i];
			} else if(tfId != etfIdArray[i] && newEtfIds == ''){
				newEtfIds = etfIdArray[i];
			}
		}
	}
	$('tfIds').value = newTfIds;
	$('etfIds').value = newEtfIds;
	query();
}

/**
 * 保存变压器分配
 */
function save() {
	var tfIds = $F('tfIds');
	var etfIds = $F('etfIds');
	if($E(tfIds) && $E(etfIds)){
		Wg.alert(main.insMgt.unPlan.valid.addTf);
		return;
	}
	
	var popid = $F('popid');//处理人
	
	var operateType = $F('operateType');
	if("01" == operateType) {//新增
		var p = {
			tfIds : tfIds,//多个变压器ID组合
			dispFlag : $F('dispFlag'),
			popid : popid
		};
		addDispTf(p);
	} else if("02" == operateType) {//编辑
		var p = {
			tfIds : tfIds,//多个变压器组合
			etfIds : etfIds,//多个变压器组合
			dispFlag : $F('dispFlag'),
			woid : $F('woid'),
			popid : popid
		};
		editDispTf(p);
	}
}

function addDispTf(p) {
	Ext.apply(p, {
		logger : main.insMgt.unPlan.log.add.dispTf
	});
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmAdd, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}

function editDispTf(p) {
	Ext.apply(p, {
		logger : main.insMgt.unPlan.log.edit.dispTf
	});
	
	Wg.confirm(main.insMgt.unPlan.hint.confirmAdd, function() {
		dwrAction.doDbWorks('insUnPlanAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新grid 这个一定要在前
					parent.query();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});	
}