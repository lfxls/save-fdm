var menuId = '53200';
var grid_url = ctx + '/system/rzgl/txrz!query.do';
//var excelParam = ['czyid','kssj','jssj','menuid','rznr','sjcdbm'];
var txGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	
	var _cm = [ 
	 {
		header : sysModule_rzgl_txrz.resourceBundle.Grid.zdjh,
		dataIndex : 'ZDJH',
		width : 120,
		sortable : true
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.dw,
		dataIndex : 'DWMC',
		width : 120
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.czlx,
		dataIndex : 'CZMC',
		width : 120
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.rznr,
		dataIndex : 'RZNRH',
		width : 200,
		sortable : true/*,
		renderer:function(v,p){  
           p.attr =  'ext:qtitle='+sysModule_rzgl_txrz.resourceBundle.Grid.rznr;
           p.attr += ' ext:qtip="'  + v + '"';
           return v;  
		}*/
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.czsj,
		dataIndex : 'CZSJ',
		width : 120,
		sortable : true
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.czyid,
		dataIndex : 'CZYID',
		width : 80,
		sortable : true
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.ip,
		dataIndex : 'IP',
		width : 120,
		sortable : true
	},{
		header : sysModule_rzgl_txrz.resourceBundle.Grid.sjxmc,
		dataIndex : 'SJXMC',
		width : 150,
		sortable : true/*,
		renderer:function(v,p){  
           p.attr =  'ext:qtitle='+sysModule_rzgl_txrz.resourceBundle.Grid.sjxmc;
           p.attr += ' ext:qtip="'  + v + '"';
           return v;  
		}		*/
	},{
		dataIndex : 'DWDM',
		hidden : true
	}];
	
	txGrid = new Wg.Grid( {
		url : grid_url,
		el : 'txGrid',
		sort : 'DWDM',
		pageSize: 30,
		title : sysModule_rzgl_txrz.resourceBundle.Title.list,
		heightPercent : 0.75,
		stripeRows : true,
		cModel : _cm
	});
	
	var p = $FF('txform');
	txGrid.init(p);
	
});

//查询
function query() {
	/*if($E($F('nodeText'))){
		Ext.Msg.alert(sysModule_rzgl_txrz.resourceBundle.Text.warn, 
		sysModule_rzgl_txrz.resourceBundle.Validate.dwmc, function(btn,text){
			if(btn == 'ok'){
				$('nodeText').focus();
			}
		});
		return false;
	}*/
	
	var p = $FF('txform');
	txGrid.reload(p);
}

function reset() {
	$('txform').reset();
}

//树过滤
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType != 'dw'){
		Wg.alert(sysModule_rzgl_txrz.resourceBundle.Prompt.dw);
		return false;
	}
	return true;
}
