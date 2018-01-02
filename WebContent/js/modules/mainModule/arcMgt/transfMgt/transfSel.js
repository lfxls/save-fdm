var menuId = '11800';
var grid_url = ctx + '/main/arcMgt/transfMgt!querySel.do';

var tfStatus = '';
var listGrid = null;
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	//handlerType = 'query';
	//hideLeft();
	var listCm = [{
		header : main.arcMgt.transfMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'+main.arcMgt.transfMgt.hint.select+'" href="javascript:selectTransf(\'{0}\',\'{1}\');" ></a>',
					record.data.TFNAME, record.data.TFID);
			return url;
		}
	},{
		header : main.arcMgt.transfMgt.grid.col.tfname,
		dataIndex : 'TFNAME',
		width : 120
	},{
		header : main.arcMgt.transfMgt.grid.col.uid,
		dataIndex : 'UNAME',
		width : 120
	},{
		dataIndex : 'UID',
		hidden:true
	},{
		header : main.arcMgt.transfMgt.grid.col.addr,
		dataIndex : 'ADDR',
		width : 120
	},{
		header : main.arcMgt.transfMgt.grid.col.cap,
		dataIndex : 'CAP',
		width : 160
	}
	/*,{
		header : main.arcMgt.transfMgt.grid.col.status,
		dataIndex : 'STATUS',
		width : 120
	},{
		dataIndex : 'TFSTATUS',
		hidden : true
	}*/
	,{
		dataIndex : 'TFID',
		hidden : true
	}];
		
	/*var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : main.arcMgt.transfMgt.hint.add,
        tooltip : main.arcMgt.transfMgt.hint.add,
        iconCls : 'add',
        handler : function(){
        	addTransf();
        }
    }];*/
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 5,
		title : main.arcMgt.transfMgt.grid.title,
		heightPercent : 0.7,
		isExport: false,
		widthPercent : 1,
		cModel : listCm
	});
	
	var p=$FF('transfForm');
	listGrid.init(p);
});


//查询
function query() {
	var p=$FF('transfForm');
	listGrid.reload(p);
}

function selectTransf(tfname, tfid){	
	parent.$('tfname').value=tfname;
	parent.$('tfid').value=tfid;
	parent.win.close();
}

function getDwTree() {
	var	title = main.arcMgt.meterMgt.tree.title.dw;
	getTree('dw', title, unitCode, unitName, 'dw');
}
