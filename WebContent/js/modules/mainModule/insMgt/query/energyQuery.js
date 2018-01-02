var menuId = '12800';
var energyQueryGrid = '';
var grid_url = ctx + '/main/insMgt/energyQuery!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	var _cm = [{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.UNAME+'</a>&nbsp;&nbsp;',
					record.data.UID);
			return url;
		}
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120
	},{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 120
	},{
		header : main.insMgt.plan.grid.col.oldMeterNo,
		dataIndex : 'OMSN',
		width : 120
	},{
		header : main.insMgt.fb.grid.col.fbDate,
		dataIndex : 'FBDATE',
		width : 140
	},{
		dataIndex : 'UID',
		hidden : true
	}];
	
	if(!$E($F('dlsjx'))) {
		var arrObj = $F('dlsjx').split(";");  
		for(var i = 0; i < arrObj.length; i++){
			var arrSjx = arrObj[i].split(",");
			_cm.push( {
				dataIndex : arrSjx[0],
				header : arrSjx[1],
				width : 200
			});
		}
	}
	
	 energyQueryGrid = new Wg.Grid( {
		url : grid_url,
		el : 'energyQueryGrid',
		title : main.insMgt.energyQuery.grid.title,
		heightPercent : 0.7,
		widthPercent : 1,
		margin : 60,
		cModel : _cm
	});
	 
	var p = $FF('energyQueryForm');
	energyQueryGrid.init(p);
});

//查询
function query()
{
	var p = $FF('energyQueryForm');
	energyQueryGrid.reload(p);
}

function pReset() {
	$('nodeText').value = '';
	$('nodeId').value = '';
	$('nodeType').value = '';
	$('nodeDwdm').value = '';
	$('cno').value = '';
	$('tfId').value = '';
	$('tfName').value = '';
	$('startDate').value = '';
	$('endDate').value = '';
	$('omsn').value = '';
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
	var p = $FF('energyQueryForm');
	energyQueryGrid.reload(p);
}

/**
 * 变压器选择
 */
function queryTf(){
	var url = String.format(ctx + '/main/insMgt/energyQuery!initTf.do?uid={0}', 
							$F('nodeId'));
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