var _url = ctx + '/system/ggdmgl/cbsjpz!queryXlx.do';
var _Grid = '';

Ext.onReady(function() {
	
	var _cm = [ {
			header: comm_op,
			dataIndex:'OP',
			width:70,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title="\'{2}\'" href="javascript:editXlx(\'{0}\', \'{1}\');"></a>',
								record.data.XLID, record.data.DLX, comm_edit);
				url += String.format('<a class="del" title="\'{2}\'" href="javascript:delXlx(\'{0}\', \'{1}\');"></a>',
								record.data.XLID, record.data.DLX, comm_del);
				return url;
			}
		}, {
			header : xlx_lxid,
			dataIndex : 'XLID',
			width : 150
		}, {
			header : xlx_mc,
			dataIndex : 'XLMC',
			width :200
		}, {
			header : xlx_cblx,
			dataIndex : 'MC',
			width :150
		}, {
			header : xlx_px,
			dataIndex : 'PXBM',
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
	        	editXlx('', $F('dlx'));
	        }
	    }];

		_Grid = new Wg.Grid( {
			url : _url,
			el : 'grid',
			heightPercent : 0.9,
			widthPercent : 1,
			margin : 60,
			tbar:toolbar,
			cModel : _cm
		});
		
		var p = $FF('queryForm');
		_Grid.init(p, false, false);
		
});

// 变更大类型
function changeDlx(value){
	var p = $FF('queryForm');
	_Grid.reload(p);
}

function editXlx(xlx, dlx) {
	var url = String.format(
		  ctx + '/system/ggdmgl/cbsjpz!editXlx.do?xlid={0}&dlx={1}', xlx, dlx);

	var baseParam = {
		url : url,
		el:'xlx',
		title : cbsjpz_t_xlx,
		width : 360,
		height : 300,
		draggable : true
	};
	
	xlxWin = new Wg.window(baseParam);
	xlxWin.open();
}

function delXlx(xlx, dlx) {
	var p = {
			rwlx : '03',
			xlid : xlx,
			dlx : dlx,
			logger : xlx_logger + xlx
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


