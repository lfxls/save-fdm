var menuId = '51100';
// 修改单位 
function updateDw() {
	var dwmc = $F('dwmc');
	if ($E(dwmc)) {
		parent.Wg.alert(jggl_add_dwmc_null);
		return;
	}
	
	var p = {
		type : 'dw',
		dwmc : $F('dwmc'),
		dwdm : $F('dwdm'),
		logger : jggl_update_logger + dwmc
	};
	
	parent.Wg.confirm(jggl_update_confirm, function() {
		dwrAction.doDbWorks('jgglAction', menuId + '02', p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					var node = parent.JgTree.getSelectNode();
					node.parentNode.reload();
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}