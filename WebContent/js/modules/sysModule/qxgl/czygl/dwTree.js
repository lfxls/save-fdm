var tree_url = ctx + '/system/qxgl/czygl!getDwTree.do';
var yhglTree = '';
Ext.onReady(function() {
	yhglTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : 320,
		width : 240,
		rootVisible : false,
		border : true
	});
	yhglTree.init('root', 'root', 'null', function(treeloader, node) {
		treeloader.baseParams = {
			treeType : $F('treeType'),
			nodeId : node.id
		};
	});
	yhglTree.tree.addListener("click", treeHandler);
});

function treeHandler(node) {
	var treeType = $F('treeType');
	var nodeId = node.id;
	var nodeType = node.attributes.ndType;
	var nodeText = node.attributes.text;
	parent.$('dwdm').value = nodeId;
	parent.$('dwmc').value = nodeText;
	parent.dwWin.closeWin();
}
