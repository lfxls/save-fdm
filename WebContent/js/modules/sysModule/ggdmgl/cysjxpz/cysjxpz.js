var menuId = '54500';
var grid_url = ctx + '/system/ggdmgl/cysjxpz!query.do';
var cysjxGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	hideLeft();
	var _cm = [ {
		header : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.op,
		dataIndex : 'OP',
		width : 50,
		renderer : function(value, cellmeta, record) {
			return String.format('<a class="del" title=' + basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.del_btnSet 
					+ ' href="javascript:delSjx(\'{0}\',\'{1}\');"></a>', record.data.GYLX, record.data.SJXBM);
		}
	},{
		header : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.cm_sjxbm,
		dataIndex : 'SJXBM',
		width : 160
	},{
		header : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.cm_sjxmc,
		dataIndex : 'SJXMC',
		width : 500
	},{
		dataIndex : 'GYLX',
		hidden : true
	},{
		header : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.cm_gylxmc,
		dataIndex : 'GYLXMC',
		width : 180
	}];
	
	var toolbar = [{
        id: 'add-buton',
		text:basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.add_btnSet,
        tooltip:basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.add_btnSet,
        iconCls:'add',
        handler: function(){
        	showAddWin();
        }
    }];
	
	cysjxGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title:basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.title,
		heightPercent : 0.9,
		tbar:toolbar,
		cModel : _cm 
	});
	
	cysjxGrid.init({});
});

//查询采集器
function query() {
	var p = $FF('queryForm');
	cysjxGrid.reload(p);
}

//添加数据项页面
function showAddWin(){
	var url = String.format(ctx +  '/system/ggdmgl/cysjxpz!showAddWin.do');
	var baseParam = {
			url : url,
			title : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.add_btnSet,
			el : 'showAddWin',
			width : 980,
			height : 600,
			draggable : true
		};
	configWin = new Wg.window(baseParam);
	configWin.open();
}

//删除数据项
function delSjx(gylx, sjxbm){
	var param = {
			gylx : gylx,
			sjxbm: sjxbm,
			logger : basicModule_ggdmgl_cysjxpz.resourceBundle.Logger.del_sjx_logger + sjxbm
	};
	
	Wg.confirm(basicModule_ggdmgl_cysjxpz.resourceBundle.Confirm.alert_del_confirm, function(){
		dwrAction.doDbWorks('cysjxpzAction','54500' + '03', param, function(res){
			Wg.alert(res.msg, function(){
				if(res.success) {
					query();
				}
			});
		});
	});
}
