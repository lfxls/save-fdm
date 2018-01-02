var menuId = '51100';
// 新增机构
function addJg() {
	var bmmc = $F('jgmc');
	if ($E(bmmc)) {
		parent.Wg.alert(jggl_add_jgmc_null);
		return;
	}

	var p = {
		type : 'jg',
		dwdm : $F('dwdm'),
		sjbmid : $F('sjbmid'),
		bmmc : bmmc,
		bmlx : $F('jglx'),
		logger : jggl_add_logger + bmmc
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