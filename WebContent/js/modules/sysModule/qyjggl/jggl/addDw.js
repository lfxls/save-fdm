var menuId = '51100';
// 新增单位
function addDw() {
	var dwmc = $F('dwmc');
	if ($E(dwmc)) {
		parent.Wg.alert(jggl_add_dwmc_null);
		return;
	}

	var p = {
		type :'dw',
		sjdwdm : $F('sjdwdm'),
		dwmc : $F('dwmc'),
		sjdwjb :$F('sjdwjb'),
		logger : jggl_add_logger + dwmc
	};
	
	parent.Wg.confirm(jggl_add_confirm, function() {
		dwrAction.doDbWorks('jgglAction', menuId + addOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					var node = parent.JgTree.getSelectNode();
					node.reload();
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}