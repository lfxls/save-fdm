var menuId = '54400';
var grid="";
var grid_url = ctx + '/system/ggdmgl/rwmbpz!query.do';

Ext.onReady(function(){
	hideLeft();
	grid_url += '?zdgylx='+$F('zdgylx');
	//代码分类的grid
	var cm = [ {
		header: cm_op,
		dataIndex:'OP',
		width:55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+cm_edit_title+'  href="javascript:config(\'{0}\',\'{1}\');"></a>',
							record.data.RWSX,record.data.ZDGYLX);
			return url;
		}
	},{
		header : cm_rwsx,
		dataIndex : 'RWSX',
		width : 100,
		sortable : true
	},{
		header : cm_rwsxmc,
		dataIndex : 'RWSXMC',
		width :260,
		sortable : true
	},{
		header : cm_rwlxmc,
		dataIndex : 'RWLXMC',
		width :80,
		sortable : true
	},{
		header : cm_zdgylx,
		dataIndex : 'ZDGYMC',
		width :130,
		sortable : true
	},{
		header : cm_sjjg,
		dataIndex : 'SJJG',
		width :130,
		sortable : true
	},{
		dataIndex : 'ZDGYLX',
		hidden : true
	}];

	grid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		frame: true,
		title : cm_title,
		heightPercent : 0.95,
		cModel : cm
	});

	grid.init({});
});

//根据查询条件从新加载两个grid
function query(){
	//代码分类加载
	var p = {
		zdgylx:$F('zdgylx')
	};
	
	//加载gird
	grid.reload(p);
}

function config(rwsx,zdgylx){
	var url = String.format(ctx +  '/system/ggdmgl/rwmbpz!showConfigWin.do?rwsx={0}&zdgylx={1}',rwsx, zdgylx);
	var baseParam = {
			url : url,
			title : configWin_title,
			el : 'taskConfig',
			width : 980,
			height : 620,
			draggable : true
		};
	configWin = new top.Wg.window(baseParam);
	configWin.open();
}