var colFbGrid = '';
var menuId = '12200';
var grid_url = ctx + '/main/insMgt/insFb!queryColFb.do';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();//显示左边树
	
	var _cm = [{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
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
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.newColNo,
		dataIndex : 'NCSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldColNo,
		dataIndex : 'OCSN',
		width : 100
	},{
		header : common.kw.other.Longitude,
		dataIndex : 'LON',
		width : 130
	},{
		header : common.kw.other.Latitude,
		dataIndex : 'LAT',
		width : 100
	},{
		header :  common.kw.meter.sealID,
		dataIndex : 'SEALID',
		width : 140
	},{
		header :  main.insMgt.fb.grid.col.err,
		dataIndex : 'ERR',
		width : 140
	},{
		header :  common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 140
	},{
		dataIndex : 'UID',
		hidden : true,
		width : 140
	}];

	colFbGrid = new Wg.Grid({
		url : grid_url,
		el : 'colFbGrid',
		title : main.insMgt.fb.grid.title.colFb,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false
	});
	
	var p = $FF('colFbForm');
	
	//grid数据初始
	colFbGrid.init(p);// （是否选择、是否单选）
});

/**
 * 树过滤
 * @param _node
 * @returns {Boolean}
 */
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType != 'dw'){
		Wg.alert(main.insMgt.plan.valid.selPU);
		return;
	}
	 
	$('nodeId').value = _node.id;
	$('nodeType').value = nodeType;
	$('nodeDwdm').value = _node.attributes.dwdm;
	$('nodeText').value = _node.attributes.text;
	
	query();
}

/**
 * 查询
 */
function query(){
	if ($E($F('nodeId'))) {
		Wg.alert(main.insMgt.plan.valid.PUNotEmpty);
		return;
	}
	var p = $FF('colFbForm');
	colFbGrid.reload(p);	
}

/**
 * 处理状态下拉框
 * @param status
 */
function changeStatus(status){
	query();
}

/**
 * 业务类型下拉框
 * @param bussType
 */
function changeBType(bussType){
	query();
}