var menuId = '54500';
var grid_url = ctx + '/system/ggdmgl/cysjxpz!queryDetail.do';
var cysjxGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	hideLeft();
	var _cm = [ {
		header : basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.op,
		dataIndex : 'OP',
		width : 50,
		renderer : function(value, cellmeta, record) {
			return String.format('<a class="addCell" title=' + basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.add_btnSet 
					+ ' href="javascript:addSjx(\'{0}\',\'{1}\',\'{2}\');"></a>', record.data.GYLX, record.data.SJXBM, record.data.FLID);
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
	},{
		dataIndex : 'FLID',
		hidden : true
	}];
	
	cysjxGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title:basicModule_ggdmgl_cysjxpz.resourceBundle.Grid.title,
		heightPercent : 0.80,
		cModel : _cm 
	});
	
	cysjxGrid.init($FF('queryForm'));
});

//查询
function query() {
	var p = $FF('queryForm');
	cysjxGrid.reload(p);
}

//删除数据项
function addSjx(gylx, sjxbm, flid){
	var param = {
			gylx : gylx,
			sjxbm: sjxbm,
			flid : flid,
			logger : basicModule_ggdmgl_cysjxpz.resourceBundle.Logger.add_sjx_logger + sjxbm
	};
	
	Wg.confirm(basicModule_ggdmgl_cysjxpz.resourceBundle.Confirm.alert_del_confirm, function(){
		dwrAction.doDbWorks('cysjxpzAction','54500' + '01', param, function(res){
			Wg.alert(res.msg, function(){
				if(res.success) {
					parent.cysjxGrid.reload({gylx : gylx});
				}
			});
		});
	});
}
