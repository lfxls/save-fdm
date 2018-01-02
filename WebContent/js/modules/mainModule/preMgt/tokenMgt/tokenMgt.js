var menuId = '14100';
var grid_url = ctx + '/main/preMgt/tokenMgt!query.do';

var tokenSts = '';
var listGrid = null;
loadProperties('mainModule', 'mainModule_preMgt');

Ext.onReady(function() {
	handlerType = 'query';
	//showLeft();
	var listCm = [{
		header : main.preMgt.tokenMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			//alert(record.data.STATUS);
			if(record.data.STATUS == 'Unhandled'||record.data.STATUS == '未处理') {
			var url = String.format('<a class="edit" title='+main.preMgt.tokenMgt.hint.edit+' href="javascript:editToken(\'{0}\');"></a>', 
					record.data.TID);
			url += String.format('<a class="del" title='+main.preMgt.tokenMgt.hint.del+' href="javascript:delToken(\'{0}\');"></a>', record.data.TID);
			}
			return url;
		}
	},{
		header : main.preMgt.tokenMgt.grid.col.msn,
		dataIndex : 'MSN',
		width : 120
	},{
		header : main.preMgt.tokenMgt.grid.col.cno,
		dataIndex : 'CNO',
		width : 120
	},{
		header : main.preMgt.tokenMgt.grid.col.orderid,
		dataIndex : 'ORDERID',
		width : 120
	},{
		header : main.preMgt.tokenMgt.grid.col.token,
		dataIndex : 'TOKEN',
		width : 180
	},{
		header : main.preMgt.tokenMgt.grid.col.sort,
		dataIndex : 'SORT',
		width : 160
	},{
		header : main.preMgt.tokenMgt.grid.col.upt_date,
		dataIndex : 'UPT_DATE',
		width : 120
	},{
		header : main.preMgt.tokenMgt.grid.col.status,
		dataIndex : 'STATUS',
		width : 200
	},{
		dataIndex : 'TOKENSTS',
		hidden : true
	},{
		dataIndex : 'TID',
		hidden : true
	}];
	
	var fields = [
		{name: 'OP'},
		{name: 'MSN'},
		{name: 'CNO'},
		{name: 'ORDERID'},
		{name: 'TOKEN'},
		{name: 'SORT'},
		{name: 'UPT_DATE'},
		{name: 'STATUS'},
		{name: 'TID'}
    ];
    
    var tokenToolbar = [{
			id : 'add',
			text : main.preMgt.tokenMgt.hint.add,
			tooltip : main.preMgt.tokenMgt.hint.add,
			iconCls : 'add',
			handler : function() {
				addToken();
			}
		},{
	        id: 'import',
			text : main.preMgt.tokenMgt.hint.importExcel,
	        tooltip : main.preMgt.tokenMgt.hint.importExcel,
	        iconCls : 'import',
	        handler: function(){
	    		importToken();
	        }
	    }];
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 30,
		title : main.preMgt.tokenMgt.grid.title,
		heightPercent : 0.80,
		widthPercent : 1,
		margin : 60,
		/*tbar : toolbar,*/
		cModel : listCm,
		tbar : tokenToolbar,
		view: new Ext.grid.GroupingView({
            forceFit: false,
            groupTextTpl: main.preMgt.tokenMgt.grid.col.msn +': '+ '{[values.rs[0].data.MSN]}({[values.rs.length]} {[values.rs.length > 1 ? "'+main.preMgt.tokenMgt.grid.col.tpls_title+'" : "'+main.preMgt.tokenMgt.grid.col.tpls_title+'"]})'
        }),		
		isGrouping: true,
		gFields: fields,
		gSortInfo: {field: 'MSN', direction: "ASC"},
		gGroupField: 'MSN'
	});
	
	var status = $('status').value;
	/*var tid = $F('tid');*/
	var p = {
		tid : '',
		status : status,
		msn : '',
		cno : '',
		orderid : '',
		token : '',
		sort : '',
		upt_date : ''
	};
	listGrid.init(p);
});


//查询
function query() {
	var p=$FF('tokenForm');
	listGrid.reload(p);
}
//定义reloadGrid函数，方便引用
function reloadGrid() {
	/*var tid= $F('tid');*/
	var msn = $F('msn');
	var cno = $F('cno');
	var orderid = $F('orderid');
	var token = $F('token');
	var sort = $F('sort');
	var status = $('status').value;
	var p = {
		/*tid : tid,*/
		msn : msn,
		cno : cno,
		orderid : orderid,
		token : token,
		sort : sort,
		status : status
	};
	listGrid.reload(p);	
}

//新增Token
function addToken() {
	var url = String.format(ctx + '/main/preMgt/tokenMgt!initTokenMgtEdit.do?operateType={0}','add');
	var baseParam = {
		url : url,
		el : 'Tokenwin',
		title : main.preMgt.tokenMgt.add.title,
		width : 600,
		height :420,
		draggable : true
	};
	Tokenwin = new Wg.window(baseParam);
	Tokenwin.open();
}

//修改Token
function editToken(tid) {
	
	//token.value = token.value.replace(/(\w{4})(?=\w)/g,"$1-");
	//token = token.replace(/(\w{4})(?=\w)/g,"$1-");
	
	var url = String.format(
			ctx + '/main/preMgt/tokenMgt!initTokenMgtEdit.do?operateType={0}&tid={1}',
			 'edit', $En(tid));
	var baseParam = {
			url : url,
			title : main.preMgt.tokenMgt.edit.title,
			el : 'Tokenwin',
			width : 600,
			height : 420,
			draggable : true
	};
	Tokenwin = new Wg.window(baseParam);
	Tokenwin.open();
}

//删除Token
function delToken(tid) {
	var p = {
		tid : tid
	};
	Ext.apply(p, {
		logger : main.preMgt.tokenMgt.del.logger + tid
	});
	Wg.confirm(main.preMgt.tokenMgt.del.confirm, function() {
		dwrAction.doDbWorks('tokenMgtAction', menuId + '03', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					reloadGrid();
				}
			});
		});
	});
}

//导入Token
function importToken(){
	var url = String.format(ctx + '/main/preMgt/tokenMgt!initTokenImport.do');
	var baseParam = {
		url : url,
		title : main.preMgt.tokenMgt.token.imp,
		el : 'mImport',
		width : 920,
		height : 600,
		draggable : true
	};
	
	impWin = new Wg.window(baseParam); 
	impWin.open();
}
