var tree_url = ctx + '/system/rzgl/darz!getCdTree.do';
var cdTree = '';
var h1 = 0.55;

Ext.onReady(function() {
	cdTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : 310,
		width : 290,
		rootVisible : false,
		border : true
	});
	cdTree.init('root', 'root', 'cd', function(treeloader, node) {
		treeloader.baseParams = {
			sjcdbm : node.id
		};
	});
	
	cdTree.tree.addListener("click", treeHandler);
});

function treeHandler(node) {
	var nodeId = node.id;
	var nodeText = node.attributes.text;

	parent.$('menuId').value = nodeId;
	parent.$('cdmc').value = nodeText;
	parent.cdWin.closeWin();
}
