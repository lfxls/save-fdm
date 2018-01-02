var menuId = '12200';
var TfGrid = '';
var tfUrl = ctx + '/main/insMgt/insPlan!queryTf.do';
Ext.onReady(function() {
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 70,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'
									+ main.insMgt.plan.text.selectTf
									+ '" href="javascript:selectTf(\'{0}\',\'{1}\',\'{2}\');" ></a>',
			record.data.TFID, record.data.TFNAME, record.data.ADDR);
			return url;
		}
	} ,{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		sortable : true
	}, {
		header :common.kw.transformer.TFID,
		dataIndex : 'TFID',
		width : 120
	}, {
		header :common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 120
	}, {
		header :main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
		width : 120
	}];
	
	var p = $FF('queryTfFrom');
	TfGrid = new Wg.Grid( {
		url : tfUrl,
		el : 'TfGrid',
		title : main.insMgt.plan.grid.title.tfList,
		heightPercent : 0.83,
		cModel : _cm
	});
	TfGrid.init(p);
});

/**
 * 选择变压器
 */
function selectTf(tfId,tfName,addr) {
	parent.$('tfId').value = tfId; //变压器ID
	parent.$('tfName').value = tfName;//变压器名称
	if($F('selTfFlag') != 'meter'){//集中器和采集器挂在变压器地址下
		parent.$('devAddr').value = addr;//安装地址
	} 
	parent.win.close();
}

//查询变压器
function queryTf() {
	var p = $FF('queryTfFrom');
	TfGrid.reload(p);
}