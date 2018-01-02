var menuId = '55100';
var grid_url = ctx + '/system/aqgl/mygl!query.do';
var bjGrid = '';
var param = '';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var _cm = [ {
		header : basicModule_aqgl_mygl.resourceBundle.Grid.op,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url='';
			url += String.format('<a class="key" title=' + basicModule_aqgl_mygl.resourceBundle.Title.button_title + ' href="javascript:updateKey(\'{0}\',\'{1}\',\'{2}\',\'{3}\');"></a>',
					record.data.TXDZ, record.data.DWDM, record.data.YHLX, record.data.ZDGYLX, basicModule_aqgl_mygl.resourceBundle.Grid.btnSet);
			return url;
		}
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_dw,
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
		dataIndex : 'ZDGYLX',
		hidden : true
	},{
		dataIndex : 'YHLX',
		hidden : true
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_hh,
		dataIndex : 'HH',
		width : 120
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_hm,
		dataIndex : 'HM',
		width : 120
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_bh,
		dataIndex : 'BJJH',
		width : 120
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_bdz,
		dataIndex : 'TXDZ',
		width : 120
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_blx,
		dataIndex : 'BJLXMC',
		width : 120
	},{
		header : basicModule_aqgl_mygl.resourceBundle.Grid.cm_mybb,
		dataIndex : 'MYBB',
		width : 100
	}];
	
	bjGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title:basicModule_aqgl_mygl.resourceBundle.Grid.title,
		heightPercent : 0.85,
		cModel : _cm 
	});
	
	bjGrid.init({});
});

//树过滤
function checkNode(_node) {
	var nodeType = _node.attributes.ndType;
	if(nodeType == 'sb') {
		Wg.alert(basicModule_aqgl_mygl.resourceBundle.Prompt.alert_selMeter);
		return false;
	} else {
		return true;
	}
}

//查询表计
function query() {
	if($E($F('nodeId'))) {
		Wg.alert(basicModule_aqgl_mygl.resourceBundle.Prompt.alert_seldw);
		return;
	}
	var p = $FF('queryForm');
	bjGrid.reload(p);
}

//更新密钥
function updateKey(txdz, dwdm, yhlx, zdgylx){
	param = {
			txdz : txdz,
			dwdm : dwdm,
			yhlx : yhlx,
			zdgylx : zdgylx
	};
	
	Wg.confirm(basicModule_aqgl_mygl.resourceBundle.Prompt.alert_confirm, function() {
		pro = new Wg.Progress({
			beanId : 'myglAction',
			baseParam : param,
			taskName : basicModule_aqgl_mygl.resourceBundle.Text.gprswaitinfo,
			taskType : 'updateKey',
			menuId : '5510001',
			endFunction : getResult
		});
		
		pro.init();
	});
	
}

//判断是否下发成功
function getResult() {
	param.taskId = pro.getTaskId();
	dwrAction.doAjax("myglAction","getResult", param, function(res) {
		if(res.success) {
			Wg.alert(res.msg);
			bjGrid.reload($FF('queryForm'));
		}
	});
}
