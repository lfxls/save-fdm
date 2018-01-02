var menuId = '51100';
var tree_url = ctx + '/system/qyjggl/jggl!getQxFwTree.do';
var QxTree = '';
Ext.onReady(function() {
	QxTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : 300,
		width : 350,
		rootVisible : false,
		isCheckTree:true,
		border : true
	});
	QxTree.init('qxRoot', 'qxRoot', 'null',function(treeloader, node) {
		treeloader.baseParams = {
				dwdm : $F('dwdm'),
				bmid : $F('bmid')
			};
		});
});

//修改机构
function updJg() {
	var o = QxTree.tree.getChecked('id');
	var _dwdms = '';
	for(var i=0;i<o.length;i++) {
		if(o[i] != $F('dwdm'))
		_dwdms += o[i] + ',';
	}
	
	
	if (o.length == 0) {
		parent.Wg.alert(jggl_upd_qx_null);
		return;
	} else {
		if (Ext.isEmpty(_dwdms))
			_dwdms = _dwdms.substring(0, _dwdms.length - 1);
	}
	
	var p = {
		type : 'jg',
		dwdm : $F('dwdm'),
		bmid : $F('bmid'),
		dwdms : _dwdms,
		cover : $('sffg').checked ? '1' : '0',
		logger : jggl_upd_logger + $F('bmid')
	};
	
	parent.Wg.confirm(jggl_upd_confirm, function() {
		dwrAction.doDbWorks('jgglAction', menuId + '04', p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					parent.win.close();
				}
			});
		});
	});
}
