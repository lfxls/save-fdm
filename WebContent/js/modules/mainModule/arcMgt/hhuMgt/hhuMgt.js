var menuId = '11600';
var grid_url = ctx + '/main/arcMgt/hhuMgt!query.do';
var hhumodel = '';
var hhustatus = '';
var listGrid = null;
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	hideLeft();	
	var listCm = [{
		header : main.arcMgt.hhuMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+main.arcMgt.hhuMgt.hint.edit+' href="javascript:editHhu(\'{0}\');"></a>', 
					record.data.HHUID);
			url += String.format('<a class="del" title='+main.arcMgt.hhuMgt.hint.del+' href="javascript:delHhu(\'{0}\');"></a>', 
					record.data.HHUID);
			url += String.format('<a class="doback" title='+main.arcMgt.hhuMgt.hint.dataInitReset+' href="javascript:dataInitReset(\'{0}\');"></a>', 
					record.data.HHUID);
			return url;			
		}
	},{
		header : main.arcMgt.hhuMgt.grid.col.hhuid,
		dataIndex : 'HHUID',
		width : 120
	},{
		header : main.arcMgt.hhuMgt.grid.col.model,
		dataIndex : 'MODEL',
		width : 120
	},{
		header : main.arcMgt.hhuMgt.grid.col.bcap,
		dataIndex : 'BCAP',
		width : 160
	},{
		header : main.arcMgt.hhuMgt.grid.col.appvn,
		dataIndex : 'APPVN',
		width : 120
	},{
		header : main.arcMgt.hhuMgt.grid.col.status,
		dataIndex : 'STATUS',
		width : 120		
	},{
		header : main.arcMgt.hhuMgt.grid.col.datainit,
		dataIndex : 'DATA_INIT',
		width : 120
	},{
		header : main.arcMgt.hhuMgt.grid.col.wh_date,
		dataIndex : 'WH_DATE',
		width : 120
	},{
		dataIndex : 'HHUMODEL',
		hidden : true
	},{
		dataIndex : 'HHUSTATUS',
		hidden : true
	}];
		
	var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : main.arcMgt.hhuMgt.hint.add,
        tooltip : main.arcMgt.hhuMgt.hint.add,
        iconCls : 'add',
        handler : function(){
        	addHhu();
        }
    }];
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 30,
		title : main.arcMgt.hhuMgt.grid.title,
		heightPercent : 0.80,
		widthPercent : 1,
		margin : 60,
		tbar : toolbar,
		cModel : listCm
	});
	
	var status = $('status').value;
	var model = $('model').value;
	/*var data_init = $('data_init').value;*/
	var hhuid = $F('hhuid');
	var p = {
		hhuid : hhuid,
		model : model,
		bcap : '',
		appvn : '',
		wh_date : '',
		status : status
		/*data_init : data_init*/		
	};
	listGrid.init(p);
});


//查询
function query() {
	var p=$FF('hhuForm');
	listGrid.reload(p);
}
//定义reloadGrid函数，方便hhu_edit引用
function reloadGrid() {
	var hhuid = $F('hhuid');
	var model = $('model').value;
	var bcap = $F('bcap');
	var appvn = $F('appvn');
	var wh_date = $F('wh_date');
	var status = $('status').value;
	/*var data_init = $('data_init').value;*/
	var p = {
		hhuid : hhuid,
		model : model,
		bcap : bcap,
		appvn : appvn,
		wh_date : wh_date,
		status : status
		/*data_init : data_init*/
	};
	listGrid.reload(p);	
}

//新增HHU
function addHhu() {
	var url = String.format(ctx + '/main/arcMgt/hhuMgt!initHhuMgtEdit.do?operateType={0}','add');
	var baseParam = {
		url : url,
		el : 'Hhuwin',
		title : main.arcMgt.hhuMgt.add.title,
		width : 600,
		height :420,
		draggable : true
	};
	Hhuwin = new Wg.window(baseParam);
	Hhuwin.open();
}

//修改HHU
function editHhu(hhuid) {
	var url = String.format(
			ctx + '/main/arcMgt/hhuMgt!initHhuMgtEdit.do?operateType={0}&hhuid={1}',
			 'edit', $En(hhuid) /*hhuid*/);
	var baseParam = {
			url : url,
			title : main.arcMgt.hhuMgt.edit.title,
			el : 'Hhuwin',
			width : 600,
			height : 420,
			draggable : true
	};
	Hhuwin = new Wg.window(baseParam);
	Hhuwin.open();
}

//删除HHU
function delHhu(hhuid) {
	var p = {
		hhuid : hhuid
	};
	Ext.apply(p, {
		logger : main.arcMgt.hhuMgt.del.logger + hhuid
	});
	Wg.confirm(main.arcMgt.hhuMgt.del.confirm, function() {
		dwrAction.doDbWorks('hhuMgtAction', menuId + '03', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					reloadGrid();
				}
			});
		});
	});
}

function dataInitReset(hhuid) {
	var p = {
		hhuid : hhuid
	};
	Ext.apply(p, {
		logger : main.arcMgt.hhuMgt.reset.logger + hhuid
	});
	Wg.confirm(main.arcMgt.hhuMgt.reset.confirm, function() {
		dwrAction.doDbWorks('hhuMgtAction', menuId + '04', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					reloadGrid();
				}
			});
		});
	});
}