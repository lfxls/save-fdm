var menuId = '51100';
// 修改部门
function updateJg() {
	var bmmc = $F('bmmc');
	if ($E(bmmc)) {
		parent.Wg.alert(jggl_add_jgmc_null);
		return;
	}

	var p = {
		type : 'jg',
		bmmc : $F('bmmc'),
		bmid : $F('bmid'),
		logger : jggl_update_logger + bmmc
	};
	
	parent.Wg.confirm(jggl_update_confirm, function() {
		dwrAction.doDbWorks('jgglAction', menuId + updOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					var node = parent.JgTree.getSelectNode();
					if(node.attributes.ndType=='jg'){ //如果节点是机构，刷新父亲节点 
						if(node.parentNode!=null){
							parent.parentNode = node.parentNode;
							node.parentNode.reload();
						}else{
							parent.parentNode.reload();
						}
					}else{    //如果非机构节点 ，刷新本身节点 
						node.reload();
					}
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}