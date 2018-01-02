var menuId = '51100';
//机构代码列表
var grid_url = ctx + '/system/qyjggl/jggl!queryDetail.do';
var JgGrid = '';
var h1 = 0.55;

Ext.onReady(function() {
	hideLeft();
	var dwmc = $F('dwmc');
	grid_url += '?dwmc='+dwmc;
	var dwdm = $F('dwdm');
	grid_url += '&dwdm='+dwdm;
	//表格列
	var _cm = [{
			header : jggl_cm_dwdm,
			dataIndex : 'DWDM',
			width : 200,
			sortable : true
		}, {
			header : jggl_cm_dwmc,
			dataIndex : 'DWMC',
			width : 500,
			sortable : true,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.DWMC+'</a>',
						record.data.DWDM);
				return url;
			}
		}];


	//加载数据
	JgGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		heightPercent : 0.95,
		cModel : _cm
	});
	JgGrid.init({});
});