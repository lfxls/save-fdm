var menuId = '54700';
var _url = ctx + '/system/ggdmgl/cbsjpz!queryDetail.do';
var _Grid = '';

Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [ {
			header : cbpz_sjx,
			dataIndex : 'SJX',
			width : 150
		}, {
			header : cbpz_sjxmc,
			dataIndex : 'SJXMC',
			width :200
		}];
	
		_Grid = new Wg.Grid( {
			url : _url,
			el : '_grid',
			heightPercent : 0.85,
			pageNotSupport : true,
			cModel : _cm
		});
		
		var p = $FF('queryForm');
		_Grid.init(p, true, true);
			// 全选或单选事件
		_Grid.grid.addListener('rowclick', rowclickFn);
});

function rowclickFn(_Grid, rowindex, e) {
	_Grid.getSelectionModel().each(function(rec) {
		parent.$('sjx').value = rec.get('SJX');
		parent.$('sjxmc').value = rec.get('SJXMC');
		parent.win.close();
	});
}

