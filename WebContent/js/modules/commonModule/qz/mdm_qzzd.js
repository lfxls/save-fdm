var Module_URL = ctx + "/qzsz!";
var Grid_URL = Module_URL + 'queryMdmQzZd.do';
var meuid='00002';
var simpleGrid = "";
var type="";
var sjid="";
Ext.onReady(function() {
	showLeft();
	var _cm = [  {
		header : dwmc_cm,
		dataIndex : 'DWMC',
		width : 100
	}, {
		header : zdjh_cm,
		dataIndex : 'ZDJH',
		width : 100,
		sortable : true
	}, {
		header : zdlx_cm,
		dataIndex : 'ZDLX',
		width : 100
	}, {
		header : zdzt_cm,
		dataIndex : 'ZDZT',
		width : 100
	}, {
		header : gylx_cm,
		dataIndex : 'ZDGYLX',
		width : 100,
		sortable : true
	}, {
		header : cs_cm,
		dataIndex : 'ZZCJBM',
		width : 120,
		sortable : true
	}, {
		header : cjfs_cm,
		dataIndex : 'CJFS',
		width : 120,
		sortable : true
	}];
	
	var toolbar = [{
        id: 'add-buton',
		text:grid_toolbar_g_add,
        tooltip:grid_toolbar_g_add,
        iconCls:'add',
        handler: function(){
        	add();
        }
    },{
        id: 'add-butons',
		text:import_text,
        tooltip:import_text,
        iconCls:'add',
        handler: function(){
    	batch();
        }
    }];
	
	
	simpleGrid = new Wg.Grid( {
		url : Grid_URL,
		el : 'grid',
		title : zdlb_cm,
		heightPercent : 0.75,
		widthPercent : 0.98,
		cModel : _cm,
		tbar:toolbar,
		hasTbar : true
	});
	var p = $FF('queryForm');
	simpleGrid.init(p,true,false);
});
//查询
function query(lx,id){
	type=lx;
	sjid=id;
	var p = $FF('queryForm');
	if(lx!=null&&lx!=''){
		p.type=lx;
		p.sjid=id;
	}
	simpleGrid.reload(p);
}



/**
 * 各页面自定义此方法(过滤节点)
 * 
 * @return
 */
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType=='bjqz'){
		Wg.alert(zd_sel);
		return;
	}
	
	$('nodeDwdm').value = _node.attributes.dwdm;
	$('nodeText').value = _node.attributes.text;
	$('nodeId').value = _node.id;
	$('nodeType').value = _node.attributes.ndType;
	$('yhlx').value = _node.attributes.yhlx;

	var p = $FF('queryForm');
	simpleGrid.reload(p);
	return true;

}

// 重写左边树点击事件
function treeHandler(_node, _e) {
	// 限制节点
	if (!checkNode(_node)) {
		return;
	}
	query();
}

//添加
function add(){
	var record = simpleGrid.getRecords();
	if(!record || record.length<1){
		Wg.alert(zd_sel);
		return false;
	}
	var totalCount = simpleGrid.grid.getStore().getTotalCount();
	var selectAllFlg = simpleGrid.allSelected;
	var zh = getGridColumn(record, "ZDJH"); // 获取组合字符串(终端局号)
	var url = String.format(
			ctx + '/qzsz!initMdmjrqz.do?jh={0}&qzlx={1}&totalCount={2}&selectAllFlg={3}&type={4}&sjid={5}',zh,'zd',totalCount,selectAllFlg,type,sjid);
	var baseParam = {
		url : url,
		el : 'qzwin',
		title : grid_toolbar_g_add,
		width : 500,
		height : 300,
		draggable : true
	};
	qzWin = new Wg.window(baseParam);
	qzWin.open();
}


//添加
function batch(){
	
	
	var url = String.format(
			ctx + '/qzsz!initBatch.do?qzlx={0}','zd');
	var baseParam = {
		url : url,
		el : 'qzwin',
		title : import_text,
		width : 890,
		height : 260,
		draggable : true
	};
	qzWin = new Wg.window(baseParam);
	qzWin.open();
}


//群组添加
function qzBj() {
	var url = ctx + '/qzsz!initMdmQzBj.do';
	window.location = url;
}