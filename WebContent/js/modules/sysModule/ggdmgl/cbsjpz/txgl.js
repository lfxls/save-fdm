var _url = ctx + '/system/ggdmgl/cbsjpz!queryTxlx.do';
var _Grid = '';

Ext.onReady(function() {
	
	var _cm = [ {
			header: comm_op,
			dataIndex:'OP',
			width:70,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title="\'{2}\'" href="javascript:editTx(\'{0}\', \'{1}\');"></a>',
								record.data.TXID, record.data.DLX, comm_edit);
				url += String.format('<a class="del" title="\'{2}\'" href="javascript:delTx(\'{0}\', \'{1}\');"></a>',
								record.data.TXID, record.data.DLX, comm_del);
				return url;
			}
		}, {
			header : txlx_txid,
			dataIndex : 'TXID',
			width : 150
		}, {
			header : txlx_mc,
			dataIndex : 'TXMC',
			width :200
		}, {
			header : txlx_cblx,
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
	        	editTx('', $F('dlx'));
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
function changeDlx(value){
	var p = $FF('queryForm');
	_Grid.reload(p);
}

function editTx(txid, dlx) {
	var url = String.format(
		  ctx + '/system/ggdmgl/cbsjpz!editTxlx.do?txid={0}&dlx={1}', txid, dlx);

	var baseParam = {
		url : url,
		el:'txlx',
		title : cbsjpz_t_tx,
		width : 360,
		height : 270,
		draggable : true
	};
	
	txWin = new Wg.window(baseParam);
	txWin.open();
}

function delTx(txid, dlx) {
	var p = {
			rwlx : '04',
			txid : txid,
			dlx : dlx,
			logger : txlx_logger + txid
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


