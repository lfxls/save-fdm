var _url = ctx + '/system/ggdmgl/cbsjpz!querySjx.do';
var _Grid = '';

Ext.onReady(function() {
	
	var _cm = [ {
			header: comm_op,
			dataIndex:'OP',
			width:70,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title="\'{2}\'" href="javascript:editSjx(\'{0}\', \'{1}\');"></a>',
								record.data.SJX, record.data.DLX, comm_edit);
				url += String.format('<a class="del" title="\'{2}\'" href="javascript:delSjx(\'{0}\', \'{1}\');"></a>',
								record.data.SJX, record.data.DLX, comm_del);
				return url;
			}
		}, {
			header : sjx_sjx,
			dataIndex : 'SJX',
			width : 150
		}, {
			header : sjx_mc,
			dataIndex : 'SJXMC',
			width :200
		}, {
			header : sjx_cblx,
			dataIndex : 'MC',
			width :150
		}, {
			header : comm_zt,
			dataIndex : 'ISUSE',
			width :150
		}, {
			dataIndex : 'DLX',
			hidden : true
		}];
	
		var toolbar = [{
	        id: 'add-buton',
			text: comm_add,
	        tooltip: comm_add,
	        iconCls:'add',
	        handler: function(){
	        	editSjx('', $F('dlx'));
	        }
	    }];

		_Grid = new Wg.Grid( {
			url : _url,
			el : 'grid',
			heightPercent : 0.75,
			widthPercent : 1,
			margin : 60,
			tbar:toolbar,
			cModel : _cm
		});
		
		var p = $FF('queryForm');
		_Grid.init(p, false, false);
		
});

// 变更大类型
function changeDlx(value) {
	var p = $FF('queryForm');
	_Grid.reload(p);
}

function editSjx(sjx, dlx) {
	var url = String.format(
		  ctx + '/system/ggdmgl/cbsjpz!editSjx.do?sjx={0}&dlx={1}', sjx, dlx);

	var baseParam = {
		url : url,
		el : 'sjx',
		title : cbsjpz_t_sjx,
		width : 360,
		height : 260,
		draggable : true
	};
	
	sjxWin = new Wg.window(baseParam);
	sjxWin.open();
}

function delSjx(sjx, dlx) {
	var p = {
			rwlx : '02',
			sjx : sjx,
			dlx : dlx,
			logger : sjx_logger + sjx
	};
	Wg.confirm(confirm_sc, function(){
		dwrAction.doDbWorks('cbsjpzAction', '54700' + '03', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					changeDlx('');
				}
			});
		});
	});
}


