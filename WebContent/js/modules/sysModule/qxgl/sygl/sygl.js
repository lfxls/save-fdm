var menuId = '52400';
var grid_url = ctx + '/system/qxgl/sygl!query.do';

var h1 = 0.5;
var syGrid = null;
Ext.onReady(function() {
	hideLeft();
	
	//首页信息数据列
	var listCm = [{
		header : sysModule_qxgl_sygl.resourceBundle.Grid.op,
		dataIndex : 'OP',
		width : 100,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='
									+ sysModule_qxgl_sygl.resourceBundle.Text.edit
									+ ' href="javascript:editSy(\'{0}\', \'{1}\');"></a>',
							record.data.SYID, record.data.LANG);
			url += String
					.format(
							'<a class="del" title='
									+ sysModule_qxgl_sygl.resourceBundle.Text.del
									+ ' href="javascript:delSy(\'{0}\', \'{1}\', \'{2}\');"></a>',
							record.data.SYID, record.data.LANG, record.data.SYMC);
			return url;
		}
	},{
		header : sysModule_qxgl_sygl.resourceBundle.Grid.symc,
		dataIndex : 'SYMC',
		width : 200,
		sortable : true,
		renderer : function(value, cellmeta, record) {
			var url = String
					.format(
							'<a href="javascript:preview(\'{0}\',\'{1}\');">{0}</a>&nbsp;&nbsp;',
							value, record.data.URL);
			return url;
		}
	},{
		header : sysModule_qxgl_sygl.resourceBundle.Grid.sylx,
		dataIndex : 'NAME',
		width : 200
	},{
		header : sysModule_qxgl_sygl.resourceBundle.Grid.url,
		dataIndex : 'URL',
		width : 300
	},{
		header : sysModule_qxgl_sygl.resourceBundle.Grid.ms,
		dataIndex : 'MS',
		width : 510
	},{
		dataIndex : 'SYID',
		hidden : true
	},{
		dataIndex : 'LANG',
		hidden : true
	}];
	
	var toolbar = null;
	toolbar = [{
        id: 'add-buton',
		text : sysModule_qxgl_sygl.resourceBundle.Text.add,
        tooltip : sysModule_qxgl_sygl.resourceBundle.Text.add,
        iconCls : 'add',
        handler : function(){
        	addSy();
        }
    }];
	
	//税费信息列表
	syGrid = new Wg.Grid( {
		url : grid_url,
		el : 'syGrid',
		pageSize: 30,
		title : sysModule_qxgl_sygl.resourceBundle.Title.list,
		heightPercent : 0.78,
		widthPercent : 1,
		margin : 60,
		stripeRows : true,
		tbar : toolbar,
		cModel : listCm
	});
	
	var p = {
		lang : $F('lang')
	};
	syGrid.init(p);
	
});

// 查询首页
function query() {
	reloadGrid();
}

// 新增首页
function addSy() {
	var url = ctx + String.format('/system/qxgl/sygl!editSy.do?lang={0}', $F('lang'));
	var baseParam = {
		url : url,
		title : sysModule_qxgl_sygl.resourceBundle.Title.add,
		el : 'addSy',
		width : 480,
		height : 320,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}

//修改首页
function editSy(syid, lang) {
	var url = ctx + String.format('/system/qxgl/sygl!editSy.do?syid={0}&lang={1}', syid, lang);
	var baseParam = {
		url : url,
		title : sysModule_qxgl_sygl.resourceBundle.Title.edit,
		el : 'editSy',
		width : 480,
		height : 320,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}

//删除首页
function delSy(syid, lang, name) {
	if ($E(syid)) {
		return;
	}
	var p = {
		syid : syid,
		lang : lang,
		logger : sysModule_qxgl_sygl.resourceBundle.Logger.del + name
	};

	Wg.confirm(sysModule_qxgl_sygl.resourceBundle.Confirm.del, function() {
		dwrAction.doDbWorks('syglAction', menuId + '03', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}

//重新加载首页
function reloadGrid(){
	var p = {
		lang : $F('lang')
	};
	syGrid.reload(p);
}

//预览首页
function preview(mc, url) {
	var url = String.format(ctx + url, '');
	var baseParam = {
		url : url,
		title : sysModule_qxgl_sygl.resourceBundle.Title.preview,
		el : 'preview',
		width : 1200,
		height : 600,
		draggable : true
	};
	viewWin = new Wg.window(baseParam);
	viewWin.open();
}

