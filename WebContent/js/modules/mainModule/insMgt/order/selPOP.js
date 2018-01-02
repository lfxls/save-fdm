var popGrid = '';
var pop_url = ctx + '/main/insMgt/insOrder!queryPOP.do';
Ext.onReady(function() {
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 70,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'
									+ main.insMgt.order.text.selPOP
									+ '" href="javascript:selectPOP(\'{0}\',\'{1}\');" ></a>',
			record.data.OPID,record.data.OPNAME);
			return url;
		}
	},{
		header : main.insMgt.order.grid.col.opId,
		dataIndex : 'OPID',
		width : 120
    },{
		header : main.insMgt.order.grid.col.name,
		dataIndex : 'OPNAME',
		width : 120
    },{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120
	},{
		header : common.kw.other.Mobile,
		dataIndex : 'PHONE',
		width : 100
	},{
		header : main.insMgt.order.grid.col.cDate,
		dataIndex : 'CDATE',
		width : 130
	},{
		header : main.insMgt.order.grid.col.teamName,
		dataIndex : 'TNAME',
		width : 130
	}];

	popGrid = new Wg.Grid({
		url : pop_url,
		el : 'selPOPGrid',
		title : main.insMgt.order.grid.title.operator,
		heightPercent : 0.62,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false
	});
	
	var p = $FF('selPOPForm');
	
	//grid数据初始
	popGrid.init(p, false, false);// （是否选择、是否单选）
});


/**
 * 查询
 */
function query(){
	var p = $FF('selPOPForm');
	popGrid.reload(p);	
}

/**
 * 设置处理人
 * @param name
 */
function selectPOP(popid,name) {
	parent.$('popid').value=popid;
	parent.$('popName').value=name;
	parent.win.close();
}

/**
 * 获取单位树
 * @returns
 */
function getDwTree() {
	var	title = common.kw.other.PU;
	getTree('dw', title, unitCode, unitName, 'dw');
}