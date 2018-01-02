var Module_URL = ctx + "/qzsz!";
var Grid_URL = Module_URL + 'queryMdmQzBj.do';
var meuid='00002';
var simpleGrid = "";
var type="";
var sjid="";
Ext.onReady(function() {
	showLeft();
	var _cm = [  {
		header : dwmc_cm,
		dataIndex : 'DWMC',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.DWMC+'</a>&nbsp;&nbsp;',
					record.data.DWDM);
			return url;
		}
	}, {
		header : bjjh_cm,
		dataIndex : 'BJJH',
		width : 100,
		sortable : true
	}, {
		header : yhh_cm,
		dataIndex : 'HH',
		width : 100,
		sortable : true
	}, {
		header : yhm_cm,
		dataIndex : 'HM',
		width : 100,
		sortable : true
	}, {
		header : azdz_cm,
		dataIndex : 'BJAZDZ',
		width : 140,
		sortable : true
	}, {
		header : ydsx_cm,
		dataIndex : 'YDSXMC',
		width : 120,
		sortable : true
	}, {
		header : bjzt_cm,
		dataIndex : 'BJZTMC',
		width : 90
	}, {
		header : gylx_cm,
		dataIndex : 'TXGYMC',
		width : 100,
		sortable : true
	}, {
		header : cs_cm,
		dataIndex : 'BJCSMC',
		width : 95,
		sortable : true
	}, {
		header : jxfs_cm,
		dataIndex : 'JXFSMC',
		width : 100,
		sortable : true
	},{
		dataIndex : 'DWDM',
		hidden : true
    }
/*	, {
		header : hy_cm,
		dataIndex : 'HYMC',
		width : 120,
		sortable : true
	}, {
		header : bjms_cm,
		dataIndex : 'BJMSMC',
		width : 100
	}, {
		header : bjlx_cm,
		dataIndex : 'BJLXMC',
		width : 100
	}, {
		header : zdjh_cm,
		dataIndex : 'ZDJH',
		width : 100,
		sortable : true
	}*/
	];
	
	var toolbar = [{
        id: 'add-buton',
		text:grid_toolbar_g_add,
        tooltip:grid_toolbar_g_add,
        iconCls:'group',
        handler: function(){
        	add();
        }
    }
/*	,{
        id: 'add-butons',
		text:import_text,
        tooltip:import_text,
        iconCls:'import',
        handler: function(){
        	batch();
        }
    }*/
	];
	
	simpleGrid = new Wg.Grid( {
		url : Grid_URL,
		el : 'grid',
		title : bjlb_cm,
		heightPercent : 0.65,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		tbar:toolbar,
		hasTbar : true
	});
	var p = $FF('queryForm');
	simpleGrid.init( p, true, false);// （是否选择、是否单选）
});

//行业，用电属性树
function getTree(treeType) {
	var title = '';
	if (treeType == 'hy') {
		title = yhgl_tree_title_hy;
	} else if (treeType == 'sx') {
		title = yhgl_tree_title_yhsx;
	}
	var url = String.format(ctx + '/qzsz!initTree.do?treeType={0}',treeType);
	var baseParam = {
		url : url,
		title : title,
		el : 'treeDiv',
		width : 340,
		height : 420,
		draggable : true
	};
	YhWin = new Wg.window(baseParam);
	YhWin.open();
}

// 各页面自定义此方法(过滤节点)
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	//yaoj 2015.1.06 ban "Group" node
	if(nodeType == 'qzRoot'){
		Wg.alert(root_ban);
		return false;
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

//重写左边树点击事件
function treeHandler(_node, _e) {
	// 限制节点
	if (!checkNode(_node)) {
		return;
	}
	query();
}

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

//重置按钮
function rests(){
	$('hyid').value="";
	$('hymc').value="";
	$('ydsx').value="";
	$('ydsxmc').value="";
	$('bjjh').value="";
	$('bjlx').value="";
	$('bjzt').value="";
	$('bjgylx').value="";
	$('bjms').value="";
	$('jxfs').value="";
	$('csbm').value="";
	$('gddy').value="";
	$('yhh').value="";
	$('yhm').value="";
}

//添加
function add(){
	var record = simpleGrid.getRecords();
	if(!record || record.length<1){
		Wg.alert(bj_sel);
		return false;
	}
	var totalCount = simpleGrid.grid.getStore().getTotalCount();
	var selectAllFlg = simpleGrid.allSelected;
	var zh = getGridColumn(record, "BJJH"); // 获取组合字符串(终端局号)
	var url = String.format(ctx + '/qzsz!initMdmjrqz.do?jh={0}&qzlx={1}&totalCount={2}&selectAllFlg={3}&type={4}&sjid={5}',
							zh,'bj',totalCount,selectAllFlg,type,sjid);
	var baseParam = {
		url : url,
		el : 'qzwin',
		title : grid_toolbar_g_add,
		width : 750,
		height : 450,
		draggable : true
	};
	qzWin = new Wg.window(baseParam);
	qzWin.open();
}

//群组添加
function qzZd() {
	var url = ctx + '/qzsz!initMdmQzZd.do';
	window.location = url;
}

//导入
function batch(){
	var url = String.format(ctx + '/qzsz!initBatch.do?qzlx={0}','bj');
	var baseParam = {
		url : url,
		el : 'qzwin',
		title : import_text,
		width : 700,
		height : 300,
		draggable : true
	};
	qzWin = new Wg.window(baseParam);
	qzWin.open();
}

