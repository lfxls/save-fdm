var tree_url = ctx + '/qzsz!getTree.do';
var yhglTree = '';
Ext.onReady(function() {
	yhglTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : 300,
		width : 300,
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
	if (treeType == 'hy') {
		parent.$('hyid').value = nodeId;
		parent.$('hymc').value = nodeText.trim();
		parent.YhWin.closeWin();
	} else if (treeType == 'sx') {
		parent.$('ydsx').value = nodeId;
		parent.$('ydsxmc').value = nodeText;
		parent.YhWin.closeWin();
	}
}
