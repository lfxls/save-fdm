var menuId = '54700';
var _url = ctx + '/system/ggdmgl/cbsjpz!query.do';
var _Grid = '';

Ext.onReady(function() {
	initPageSet(); //
		
	var _cm = [ {
			header: comm_op,
			dataIndex:'OP',
			width:55,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title="\'{3}\'" href="javascript:editCbpz(\'{0}\', \'{1}\', \'{2}\');"></a>',
								record.data.SJX, record.data.XLX, record.data.DLX, comm_edit);
				url += String.format('<a class="del" title="\'{3}\'" href="javascript:delCbpz(\'{0}\', \'{1}\', \'{2}\');"></a>',
								record.data.SJX, record.data.XLX, record.data.DLX, comm_del);								
				return url;
			}
		}, {
			header : cbpz_sjx,
			dataIndex : 'SJX',
			width : 150
		}, {
			header : cbpz_sjxmc,
			dataIndex : 'SJXMC',
			width :200
		}, {
			header : cbpz_dw,
			dataIndex : 'DW',
			width :80
		}, {
			header : cbpz_kd,
			dataIndex : 'WIDTH',
			width : 80
		}, {
			header : cbpz_ctpt,
			dataIndex : 'XCTPT',
			width :80
		}, {
			header : cbpz_sfxx,
			dataIndex : 'SFXS',
			width :80
		}, {
			header : cbpz_fmt,
			dataIndex : 'FORMAT',
			width :120
		}, {
			header : cbpz_px,
			dataIndex : 'PXBM',
			width :80
		}, {
			header : cbpz_txmc,
			dataIndex : 'TXMC',
			width :200
		}, {
			dataIndex : 'DLX',
			hidden : true
		}, {
			dataIndex : 'XLX',
			hidden : true
		}];
	
		var toolbar = [{
	        id: 'add-buton',
			text: comm_add,
	        tooltip: comm_add,
	        iconCls:'add',
	        handler: function(){
	        	editCbpz('', $F('xlx'), $F('dlx'));
	        }
	    }];

		_Grid = new Wg.Grid( {
			url : _url,
			el : 'grid',
			heightPercent : 0.7,
			widthPercent :1,
			margin : 60,
			tbar:toolbar,
			cModel : _cm
		});
		
		var p = $FF('queryForm');
		_Grid.init(p, false, false);
});

// 变更大类型
function changeDlx(value){
	var url = String.format(ctx	+ '/system/ggdmgl/cbsjpz!init.do?dlx={0}', value);
	location.href = url;
}

// 变更小类型
function changeXlx(value){
	$('xlx').value = value;
	initPageSet();
	var p = $FF('queryForm');
	_Grid.reload(p);
}

function query(){
	var p = $FF('queryForm');
	_Grid.reload(p);
}

// 初始化显示
function initPageSet() {
	var xlx = $F('xlx');
	if(!$E(xlx)) {
		// 清除元素背景色
		var arrLI = Ext.query("li");
		for(var i = 0; i < arrLI.length; i++){ 
			$(arrLI[i].id).className = 'ui-tab-item';
		}
		
		// 设置选定TAB背景色
		for(var i = 0; i < arrLI.length; i++){
			if((xlx+'li') == arrLI[i].id){
				$(arrLI[i].id).className = 'ui-tab-item ui-tab-item-current';	
			}
		}
	}
}

function editCbpz(sjx, xlx, dlx) {
	var url = String.format(
		  ctx + '/system/ggdmgl/cbsjpz!initCbpz.do?sjx={0}&xlx={1}&dlx={2}', sjx, xlx, dlx);

	var baseParam = {
		url : url,
		el : 'cbpz',
		title : cbsjpz_t_pz,
		width : 600,
		height : 400,
		draggable : true
	};
	
	pzWin = new Wg.window(baseParam);
	pzWin.open();
}

function delCbpz(sjx, xlx, dlx) {
	var p = {
			rwlx : '01',
			sjx : sjx,
			dlx : dlx,
			logger : pz_logger + sjx
	};
	Wg.confirm(confirm_sc, function(){
		dwrAction.doDbWorks('cbsjpzAction', '54700' + '03', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					query();
				}
			});
		});
	});
}

// 变更大类型
function querySjx() {
	var url = String.format(ctx + '/system/ggdmgl/cbsjpz!initSjx.do?dlx={0}', $F('dlx'));
	top.openpageOnTree(cbsjpz_t_sjx, '55500', cbsjpz_t_sjx, null, url, 'yes', 1);
}

// 变更小类型
function queryXlx() {
	var url = String.format(ctx + '/system/ggdmgl/cbsjpz!initXlx.do?dlx={0}', $F('dlx'));
	top.openpageOnTree(cbsjpz_t_xlx, '55600', cbsjpz_t_xlx, null, url, 'yes', 1);
}

// 图表类型
function queryTx() {
	var url = String.format(ctx + '/system/ggdmgl/cbsjpz!initTxlx.do?dlx={0}', $F('dlx'));
	top.openpageOnTree(cbsjpz_t_tx, '55700', cbsjpz_t_tx, null, url, 'yes', 1);
}
