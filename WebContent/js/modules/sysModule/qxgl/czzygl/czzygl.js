var menuId = '52300';
var tree_url = ctx + '/system/qxgl/czzygl!getCzTree.do';
var grid_url = ctx + '/system/qxgl/czzygl!query.do';
var CzTree = '';
var CzGrid = '';
var h1 = 0.55;
Ext.onReady(function() {
	hideLeft();
	CzTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : screen.height * 0.52,
		//widthPercent : 1,
		width : 680,
		rootVisible : false,
		border : true
	});
	CzTree.init('root', 'root', 'cd', function(treeloader, node) {
		if ($E($F('czlbid'))) {
			return false;
		}
		treeloader.baseParams = {
			sjcdbm : node.id,
			czlbid : $F('czlbid')
		};
		return true;
	});

	var _cm = [ {
		header : czzygl_cm_id,
		dataIndex : 'CZLBID',
		sortable : true
	}, {
		header : czzygl_cm_mc,
		dataIndex : 'CZLBMC',
		width:200,
		sortable : true
	} ];

	CzGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		sort : 'CZLBID',
		title : czzygl_grid_title,
		height : screen.height * 0.55,
		widthPercent : 0.7,
		margin : 347,
		cModel : _cm
	});
	CzGrid.init({});
	CzGrid.grid.addListener('rowclick', function(grid, rowIndex, e) {
		$('czlbid').value = grid.getStore().getAt(rowIndex).get('CZLBID');
		CzTree.reload();
	});
});
