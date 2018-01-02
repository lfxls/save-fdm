var menuId = '55200';
var grid_url = ctx + '/system/aqgl/cjqmysj!query.do';
var cjqGrid = '';
var param = '';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var _cm = [ {
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.op,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url='';
			url += String.format('<a class="key" title=' + basicModule_aqgl_cjqmysj.resourceBundle.Title.button_title 
					+ ' href="javascript:updateKey(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
					record.data.CJQJH, record.data.BXJH, record.data.MYBB, record.data.DWDM, record.data.CJQLX);
			return url;
		}
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_cjqjh,
		dataIndex : 'CJQJH',
		width : 110
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_dw,
		dataIndex : 'DWMC',
		width : 150,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.DWMC+'</a>',
					record.data.DWDM);
			return url;
		}
	},{
		dataIndex : 'DWDM',
		hidden : true
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_bxjh,
		dataIndex : 'BXJH',
		width : 100
	},{
		dataIndex : 'ZDGYLX',
		hidden : true
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_zdgylxmc,
		dataIndex : 'ZDGYLXMC',
		width : 110
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_cjfs,
		dataIndex : 'CJFSMC',
		width : 120
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_mybb,
		dataIndex : 'MYBB',
		width : 120
	},{
		header : basicModule_aqgl_cjqmysj.resourceBundle.Grid.cm_azrq,
		dataIndex : 'AZRQ',
		width : 120
	},{
		dataIndex : 'CJQLX',
		hidden : true
	}];
	
	cjqGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title:basicModule_aqgl_cjqmysj.resourceBundle.Grid.title,
		heightPercent : 0.8,
		cModel : _cm 
	});
	
	cjqGrid.init({});
});

//树过滤
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType == 'sb') {
		Wg.alert(basicModule_aqgl_cjqmysj.resourceBundle.Prompt.alert_selMeter);
		return false;
	} else {
		return true;
	}
}

//查询采集器
function query() {
	if($E($F('nodeId'))) {
		Wg.alert(basicModule_aqgl_cjqmysj.resourceBundle.Prompt.alert_seldw);
		return;
	}
	var p = $FF('queryForm');
	cjqGrid.reload(p);
}

//更新密钥
function updateKey(cjqjh, bxjh, mybb, dwdm, cjqlx){
	param = {
			cjqjh : cjqjh,
			bxjh : bxjh,
			mybb : mybb,
			dwdm : dwdm,
			cjqlx : cjqlx
	};
	
	Wg.confirm(basicModule_aqgl_cjqmysj.resourceBundle.Prompt.alert_confirm, function() {
		pro = new Wg.Progress({
			beanId : 'cjqmysjAction',
			baseParam : param,
			taskName : basicModule_aqgl_cjqmysj.resourceBundle.Text.gprswaitinfo,
			taskType : 'updateKey',
			menuId : '5520001',
			endFunction : getResult
		});
		
		pro.init();
	});
	
}

//判断是否下发成功
function getResult() {
	param.taskId = pro.getTaskId();
	dwrAction.doAjax("cjqmysjAction","getResult", param, function(res) {
		if(res.success) {
			Wg.alert(res.msg);
			cjqGrid.reload($FF('queryForm'));
		}else {
			Wg.alert(res.msg);
		}
	});
}
