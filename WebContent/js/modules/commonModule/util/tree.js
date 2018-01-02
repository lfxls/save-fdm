var tree_url = ctx + '/util!getTree.do?treeType=' + treeType;
var tree = '';
Ext.onReady(function() {
	
	tree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : Ext.getBody().getHeight()*0.9,
		width : Ext.getBody().getWidth()*0.9,
		rootVisible : true,
		border : true
	});
	
	//使用根节点信息初始化树
	tree.init(nodeId, nodeText, nodeType);
	
	//树单击事件响应，如果页面定义了事件就用页面的，如果页面没有定义就用默认的
	var treeHandler = parent.treeClickHandler ? parent.treeClickHandler : function treeClickHandler(node) {
		try{
			parent.$('nodeId' + treeType).value = node.id;
		}catch(e){};
		try{
			parent.$('nodeText' + treeType).value = node.text;
		}catch(e){};
		try{
			parent.$('nodeType' + treeType).value = node.attributes.ndType;
		}catch(e){};
		try{
			parent.win.close();
		}catch(e){};
	};
	//单击事件
	tree.tree.addListener("click", treeHandler);
});