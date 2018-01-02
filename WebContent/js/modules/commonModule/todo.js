var _url = ctx + '/todo!query.do';
var _Grid = '';

Ext.onReady(function() {
	handlerType = 'query';
	
	var _cm = [ {
			header : common_todo_xh,
			width : 70,
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
	              var start = store.lastOptions.params.start;
	              if (isNaN(start)) {
	                 start = 0;
	              }
	              return start + rowIndex + 1;   
			}
		}, {
			header : common_todo_ms,
			dataIndex : 'TODODESC',
			width :400
		}, {
			header : common_todo_ywlx,
			dataIndex : 'TODOMC',
			width :150
		}, {
			header : common_todo_fqr,
			dataIndex : 'ORIGINATOR',
			width : 120
		}, {
			header : common_todo_zt,
			dataIndex : 'ZTMC',
			width :130
		}, {
			header : common_todo_gxsj,
			dataIndex : 'ACTIVETIME',
			width :140
		}, {
			header : common_todo_cl,
			dataIndex : 'URL',
			width :120,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a title="\{1}\" href="javascript:pending(\'{0}\');">{2}</a>',
								record.data.URL, common_todo_cl, common_todo_cl);
				return url;
			}			
		}, {
			dataIndex : 'APPTODOTYPE',
			hidden : true
		}, {
			dataIndex : 'STATUS',
			hidden : true
		}];

		_Grid = new Wg.Grid( {
			url : _url,
			el : 'grid',
			heightPercent : 0.85,
			cModel : _cm
		});
		
		var p = $FF('queryForm');
		_Grid.init(p, false, false);
});

function query() {
	var p = $FF('queryForm');
	_Grid.reload(p);
}

//具体处理页面
function pending(_url){
	top.openpageOnTree(common_todo_title, '100000', common_todo_title, null, _url, 'yes', 1);
}
