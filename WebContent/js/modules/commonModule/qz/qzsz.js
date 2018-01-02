var Module_URL = ctx + "/qzsz!";
var Grid_URL = Module_URL + 'query.do';
var simpleGrid = '';
var menuId = "00001";
// 生成备选用户Grid
Ext.onReady(function() {
	var _cm = [{
		header : cz_cm,
		dataIndex : 'OP',
		width : 80,
		renderer : render
	}, {
		header : qzm_cm,
		dataIndex : 'QZMC',
		width : 250,
		sortable : true
	}, {
		header : cjsj_cm,
		dataIndex : 'CJSJ',
		sortable : true,
		width : 150
	}, {
		header : smzq_cm,
		dataIndex : 'SMZQ',
		width : 100,
		sortable : true
	}, {
		header : sfgx_cm,
		dataIndex : 'SFGX',
		width : 100,
		sortable : true
	}, {
		header : czy_cm,
		dataIndex : 'CZYID',
		width : 100,
		sortable : true
	}, {
		header : qzfl_cm,
		dataIndex : 'QZFLMC',
		width : 100,
		sortable : true
	 }, {
		dataIndex : 'QZID',
		hidden : true
	}, {
		dataIndex : 'QZFL',
		hidden : true
	} ,{
		dataIndex : 'QZLX',
		hidden : true
	}];
	
	var toolbar = [{
        id: 'add-buton',
		text:grid_toolbar_g_new,
        tooltip:grid_toolbar_g_new,
        iconCls:'add',
//      tbar:toolbar,
        handler: function(){
        	newGroup();
        }
    }];
	
	simpleGrid = new Wg.Grid({
		url : Grid_URL,
		el : 'grid',
		title : grid_title,
		cModel : _cm,
		tbar:toolbar,
		heightPercent : 0.80,
		widthPercent : 1,
		margin : 60,
		sort : 'QZID'
	});
	simpleGrid.init({
		qzlx : $F('qzlx'),
		cjkssj : $F('cjkssj'),
		cjjssj : $F('cjjssj')
	}, false, false);
});

//操作标志
function render(value, cellmeta, record) {
	var qzid = record.data.QZID;
	var qzfl = record.data.QZFL;
	var qzlx = record.data.QZLX;
	var url = String.format('<a class="edit" title='+cz_upd_cm+' href="javascript:updateQz(\'{0}\',\'{1}\',\'{2}\');" ></a>',
				qzid, qzfl, qzlx);
		url += String.format('<a class="del" title='+cz_del_cm+' href="javascript:deleteQz(\'{0}\',\'{1}\');" ></a> ',
				qzid,record.data.QZMC);
	return url;
}

//新增群组
function newGroup(){
	var url = String.format(ctx + "/qzsz!initNewGroup.do");
	var baseParam = {
		url : url,
		title : grid_toolbar_g_new,
		el : 'qzNew',
		width : 600,
		height : 300,
		draggable : false
	};
	groupWin = new Wg.window(baseParam);
	groupWin.open();
}

// 删除
function deleteQz(qzid,qzmc) {
	Wg.confirm(del_sure, function() {
		var baseParam = {
			logger : del_qz + qzmc,
			qzid : qzid
		};
		dwrAction.doDbWorks("qzAction", '0000003', baseParam, function(res) {
			query();
		});
	});
}

// 修改
function updateQz(qzid, qzfl,qzlx) {
	var updateUrl = Module_URL + 'updateGroup.do?qzid=' + qzid + '&qzfl='
			+ qzfl + '&qzlx='+ qzlx;
	var baseParam = {
		url : updateUrl,
		title : upd_title,
		el : 'detail',
		width : 600,
		height : 500,
		draggable : true
	};
	
    simpleWin = new Wg.window(baseParam);
	simpleWin.open();
}

/***************查询************* */
function query() {
	var baseParam = {
		qzm : $('nodeText').value,
		cjkssj : $F('cjkssj'),
		cjjssj : $F('cjjssj')
	};
	simpleGrid.reload(baseParam);
}