var tree_url = ctx + '/system/qxgl/czygl!getJgTree.do';
var JgTree = '';
Ext.onReady(function() {
	var nodeType = $F('nodeType');
	JgTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : 320,
		width : 260,
		rootVisible : true,
		border : true
	});
	JgTree.init(unitCode, unitName, 'DW');
	JgTree.tree.addListener("click", treeHandler);
});

function treeHandler(node) {
	var selType = $('selType').value;
	var nodeId = node.id;
	var nodeText = node.attributes.text;
	var dwdm = node.attributes.info;
	if('leaf'==selType){
		if (node.isLeaf()) {
			parent.$('bmid').value = nodeId;
			parent.$('dwdm').value = dwdm;
			parent.$('bmmc').value = nodeText;
			try{
				parent.JsTree.reload();
				parent.QxTree.reload();
			}catch(e){
			}
			parent.YhWin.closeWin();
		} else {
			parent.Wg.alert(czygl_tree_select_dw);
			return;
		}
	}else{
		if (node.isLeaf()) {
			parent.$('bmid').value = nodeId;
			parent.$('dwdm').value = dwdm;
			parent.$('bmmc').value = nodeText;
			try{
				parent.JsTree.reload();
				parent.QxTree.reload();
			}catch(e){
			}
		} else {
			parent.$('dwdm').value = nodeId;
			parent.$('bmmc').value = nodeText;
			parent.$('bmid').value = "";
			try{
				parent.JsTree.reload();
				parent.QxTree.reload();
			}catch(e){
			}
		}
		parent.YhWin.closeWin();
	}
}
