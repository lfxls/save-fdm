var grid_url = ctx + '/system/rzgl/dlrz!query.do';

var dlrzGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var _cm = [{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_gh,
		dataIndex : 'CZYID',
		width : 80
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_czyxm,
		dataIndex : 'CZYXM',
		width : 100
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_dwmc,
		dataIndex : 'DWMC',
		width : 200,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.DWMC+'</a>&nbsp;&nbsp;',
					record.data.DWDM);
			return url;
		}
			
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_dlsj,
		dataIndex : 'DLSJ',
		width : 150
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_dcsj,
		dataIndex : 'TCSJ',
		width : 150
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_ip,
		dataIndex : 'IP',
		width : 120
	},{
		header : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_cm_session,
//		id : 'SID',
		dataIndex : 'SID',
		width : 140
	},{
		dataIndex : 'DWDM',
		hidden : true
	}];
	
	dlrzGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : sysModule_rzgl_dlrz.resourceBundle.Grid.dlrz_grid_title,
		heightPercent : 0.75,
		widthPercent : 1,
		margin : 60,
		cModel : _cm
//		autoExpandColumn : 'SID'
	});
	var p = $FF('queryForm');
	dlrzGrid.init(p);
});

//树过滤
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType != 'dw'){
		Wg.alert(sysModule_rzgl_dlrz.resourceBundle.Prompt.dlrz_prompt_null_dept);
		return false;
	}
	return true;
}

//查询
function query() {
	//单位代码非空验证
	if($E($F('nodeText'))){
		Ext.Msg.alert(sysModule_rzgl_dlrz.resourceBundle.Prompt.dlrz_prompt_alert, 
				sysModule_rzgl_dlrz.resourceBundle.Prompt.dlrz_prompt_null_dept, function(btn,text){
			if(btn == 'ok'){
				$('nodeText').focus();
			}
		});
		return false;
	}
	
	var p = $FF('queryForm');
	dlrzGrid.reload(p);
}
function Reset(){
	$('nodeId').value="";
	$('nodeText').value="";
	$('czy').value="";
	$('ip').value="";
	$('kssj').value="";
	$('jssj').value="";
}

