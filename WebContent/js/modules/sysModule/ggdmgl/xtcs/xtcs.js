var menuId = '54200';
var CsGrid="";
var CsSortGrid="";
var url = ctx + '/system/ggdmgl/xtcs!queryDetail.do';
var sort_url = ctx + '/system/ggdmgl/xtcs!query.do';
var tempParaSortId = "";

Ext.onReady(function(){
	hideLeft();
	//参数分类的grid
	var sort_cm = [ {
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_opt,
		dataIndex : 'OP',
		width : 60,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_edit+' href="javascript:initEditParaSort(\'{0}\',\'{1}\');"></a>',
								record.data.PARA_SORT_ID, '02');
			url += String.format('<a class="del" title='+systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_del+' href="javascript:delParaSort(\'{0}\');"></a>',
								record.data.PARA_SORT_ID);
			return url;
		}
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_csflmc,
		dataIndex : 'NAME',
		width : 120,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_csflbm,
		dataIndex : 'P_SORT_CODE',
		width :90,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_csflsm,
		dataIndex : 'SORT_DESC',
		width :140,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_yxbz,
		dataIndex : 'SYZT',
		width :70,
		sortable : true
	},{
		dataIndex : 'PARA_SORT_ID',
		hidden : true
	}];

	var csSortToolbar = [{
        id: 'add-buton',
		text: systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_add,
        tooltip: systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_add,
        iconCls:'add',
        handler: function(){
        	initEditParaSort(-1, '01');
        }
    }];

	CsSortGrid = new Wg.Grid( {
		url : sort_url,
		el : 'sysparaSortGrid',
		frame: true,
		title : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaSortGrid_cm_title,
		heightPercent : 0.9,
		widthPercent : 0.5,
		margin : 22,
		tbar:csSortToolbar,
		cModel : sort_cm,
		canResize:false
	});
	
	//参数的grid
	var _cm = [ {
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_opt,
		dataIndex : 'OP',
		width : 60,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_edit+' href="javascript:initEditPara(\'{0}\',\'{1}\',\'{2}\');"></a>',
								record.data.PARA_SORT_ID,record.data.PARA_ID, '02');
			url += String.format('<a class="del" title='+systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_del+' href="javascript:delPara(\'{0}\');"></a>',
								record.data.PARA_ID);
			return url;
		}
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_csflbm,
		dataIndex : 'PARA_SORT_ID',
		width : 90,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_csmc,
		dataIndex : 'NAME',
		width :120,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_cszlx,
		dataIndex : 'TYPE_CODE',
		width :80,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_csz,
		dataIndex : 'DEFAULT_VALUE',
		width :70,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_cszsx,
		dataIndex : 'MAX_LIMIT',
		width :70,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_cszxx,
		dataIndex : 'MIN_LIMIT',
		width :70,
		sortable : true
	},{
		header : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_yxbz,
		dataIndex : 'SYZT',
		width :70,
		sortable : true
	},{
		dataIndex : 'PARA_ID',
		hidden : true
	}];
	
	var csToolbar = [{
        id: 'add-buton',
		text: systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_add,
        tooltip: systemModule_ggdmgl_xtcs.resourceBundle.Button.xtcs_button_add,
        iconCls:'add',
        handler: function(config){
        	initEditPara(tempParaSortId,'-1', '01');
        }
    }];

	CsGrid = new Wg.Grid( {
		url : url,
		el : 'sysparaGrid',
		frame: true,
		title : systemModule_ggdmgl_xtcs.resourceBundle.Grid.xtcs_grid_sysparaGrid_cm_title,
		heightPercent : 0.9,
		widthPercent : 0.5,
		margin : 22,
		tbar:csToolbar,
		cModel : _cm,
		canResize:false
	});
	
	CsSortGrid.init({});
	CsGrid.init({});
	
	CsSortGrid.grid.addListener('rowclick', function(grid, rowIndex, e) {
		//根据参数分类进行查询
		var value= grid.getStore().getAt(rowIndex).get('PARA_SORT_ID');
		tempParaSortId = value;
		var p ={
		 lang:$F('lang'),
		 paraSortId:value
		};
		CsGrid.reload(p);
	});	
});


//根据查询条件从新加载两个grid
function query(){
	//参数分类加载
	var p = {
		lang:$F('lang'),
		csflmc:$F('csflmc'),
		paraSortId:-1
	};
	
	tempParaSortId = "";
	//加载gird
	CsSortGrid.reload(p);
	CsGrid.reload(p);
}


//初始化参数类别编辑窗口
function initEditParaSort(paraSortId, czbs){
	 var lang = $F('lang');
	 var url = String.format(
		  ctx + '/system/ggdmgl/xtcs!initEditParaSort.do?paraSortId={0}&lang={1}&czbs={2}',paraSortId,lang, czbs);
	 var title = '';
	 if(paraSortId == '-1'){
	 	title = systemModule_ggdmgl_xtcs.resourceBundle.Window.xtcs_window_newParaSort_title;
	 } else {
	 	title = systemModule_ggdmgl_xtcs.resourceBundle.Window.xtcs_window_editParaSort_title;
	 }
	 
	 var baseParam = {
		url : url,
		el:'editParaSort',
		title : title,
		width : 480,
		height : 300,
		draggable : true
     };
	 AdvWin = new Wg.window(baseParam);
	 AdvWin.open();
}

//初始化参数信息编辑窗口
function initEditPara(paraSortId, paraId, czbs){
	  //如果代码类ID为空，提示信息
	  if($E(paraSortId) || paraSortId == -1){
	  	Wg.alert(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_sel_csflbm);
	  	return false;
	  }
	  
	  var lang = $F('lang');
	  var url = String.format(
		  ctx + '/system/ggdmgl/xtcs!initEditPara.do?paraId={0}&lang={1}&czbs={2}&paraSortId={3}',paraId,lang,czbs, paraSortId);
	  var title = '';
	  if(paraId == '-1'){
	 	 title = systemModule_ggdmgl_xtcs.resourceBundle.Window.xtcs_window_addPara_title;
	  } else {
	 	 title = systemModule_ggdmgl_xtcs.resourceBundle.Window.xtcs_window_editPara_title;
	  }
	  
	  var baseParam = {
		url : url,
		el:'editPara',
		title : title,
		width : 400,
		height : 380,
		draggable : true
	 };
	 AdvWin = new Wg.window(baseParam);
	 AdvWin.open();
}

//删除一条参数分类数据，以及级联的参数数据
function delParaSort(paraSortId){
	//删除参数分类
	var param = {
		paraSortId : paraSortId,
		type:'csfl',
		logger : systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_delSortPara
	};
	
	Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_delParaSort_comfirm, function() {
		dwrAction.doDbWorks('xtcsAction', menuId + delOpt, param, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					queryParaSort();
				}
			});
		});
	});
}

//删除一条参数数据
function delPara(paraId){
	var param = {
		paraId : paraId,
		type:'csxx',
		logger : systemModule_ggdmgl_xtcs.resourceBundle.LOG.xtcs_log_delPara
	};
	
	Wg.confirm(systemModule_ggdmgl_xtcs.resourceBundle.Prompt.xtcs_prompt_delPara_comfirm, function() {
		dwrAction.doDbWorks('xtcsAction', menuId + delOpt, param, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					queryPara(tempParaSortId);
				}
			});
		});
	});
}

//删除后的更新grid
function queryPara(tempParaSortId){
	//参数分类加载
	var p = {
		lang:$F('lang'),
		paraSortId:tempParaSortId
	};
	//加载gird
	CsGrid.reload(p);
}

//删除分类后刷新页面
function queryParaSort(){
	var p = {
		lang:$F('lang'),
		csflmc:$F('csflmc')
	};
	//加载gird
	CsSortGrid.reload(p);
	CsGrid.reload(p);
}
