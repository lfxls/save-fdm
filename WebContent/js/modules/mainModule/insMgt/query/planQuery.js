var menuId = '12700';
var planQueryGrid = '';
var grid_url = ctx + '/main/insMgt/planQuery!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="soubiao" title="\{1}\" href="javascript:showFB(\'{0}\');"></a>',
					record.data.PID, main.insMgt.planQuery.grid.col.fbData);
			return url;
		}
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = '';
			if(record.data.PID != '') {
				url = String.format('<a href="javascript:showPlanLog(\'{0}\');">'+record.data.PID+'</a>&nbsp;&nbsp;',
						record.data.PID);
			}
			return url;
		}
	},{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>&nbsp;&nbsp;',
					record.data.WOID);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.planType,
		dataIndex : 'PTNAME',
		width : 120
	},{
		header : common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
    },{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 100
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 130
	},{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 120
	},{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.UNAME+'</a>&nbsp;&nbsp;',
					record.data.UID);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.newMeterNo,
		dataIndex : 'NMSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldMeterNo,
		dataIndex : 'OMSN',
		width : 100
	},{
		header : common.kw.meter.MType,
		dataIndex : 'MTNAME',
		width : 100
	},{
		header : common.kw.meter.MCMethod,
		dataIndex : 'WNAME',
		width : 130
	},{
		header : main.insMgt.plan.grid.col.meterMode,
		dataIndex : 'MMNAME',
		width : 100
	},{
		header :  main.insMgt.plan.grid.col.updTime,
		dataIndex : 'UTIME',
		width : 140
	},{
		header :  main.insMgt.plan.grid.col.operator,
		dataIndex : 'OPR',
		width : 120
	},{
		dataIndex : 'STATUS',
		hidden : true
    },{
		dataIndex : 'BTYPE',
		hidden : true
    },{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'PID',
		hidden : true		
	}];
	
	 planQueryGrid = new Wg.Grid( {
		url : grid_url,
		el : 'planQueryGrid',
		title : main.insMgt.planQuery.grid.title,
		heightPercent : 0.7,
		widthPercent : 1,
		margin : 60,
		cModel : _cm
	});
	 
	var p = $FF('planQueryForm');
	planQueryGrid.init(p);
});

//查询
function query()
{
//	if(!$E($F('pid'))) {
//		if(!/^\d+$/.test($F('pid'))){
//			Wg.alert(main.insMgt.plan.valid.pid.formatCheck);
//			return;
//		}
//	}
//	if(!$E($F('woid'))) {
//		if(!/^\d+$/.test($F('woid'))){
//			Wg.alert(main.insMgt.order.valid.workOrderNo.formatCheck);
//			return;
//		}
//	}
	var p = $FF('planQueryForm');
	planQueryGrid.reload(p);
}

//安装计划状态下拉
function changeStatus(){
	query();
}

//计划类型下拉
function changePType(){
	query();
}

//业务类型下拉
function changeBType(){
	query();
}

function queryPop() {
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

function pReset() {
	$('nodeText').value = '';
	$('nodeId').value = '';
	$('nodeType').value = '';
	$('nodeDwdm').value = '';
	$('cno').value = '';
	$('bussType').value = '';
	$('woid').value = '';
	$('status').value = '';
	$('pType').value = '';
	$('pid').value = '';
	$('nmsn').value = '';
}

function showFB(pid){
	var url = String.format(ctx + '/main/insMgt/insFb!init.do?pid={0}',pid);
	top.openpageOnTree(main.insMgt.fb.grid.title.meterFb, '122000', main.insMgt.fb.grid.title.meterFb, null, url, 'yes', 1);
}

function showPlanLog(pid){
	var url = String.format(ctx + '/main/insMgt/planLog!init.do?pid={0}',pid);
	var baseParam = {
		url : url,
		title : main.insMgt.planLog.title,
		el : 'planOPLog',
		width : 800,
		height : 450,
		draggable : true
	};
	
	var planOPLogWin = new Wg.window(baseParam); 
	planOPLogWin.open();
}

//各页面自定义此方法(过滤节点)
function checkNode(_node) {
	if(_node.attributes.ndType != 'dw') {
		Wg.alert(main.insMgt.planQuery.valid.nvl.node);
		return;
	} else {
		$('nodeDwdm').value = _node.attributes.dwdm;
		$('nodeText').value = _node.attributes.text;
		$('nodeId').value = _node.id;
		$('nodeType').value = _node.attributes.ndType;
	}
	var p = $FF('planQueryForm');
	planQueryGrid.reload(p);
}