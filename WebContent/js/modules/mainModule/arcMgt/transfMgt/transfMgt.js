var menuId = '11800';
var grid_url = ctx + '/main/arcMgt/transfMgt!query.do';

var tfStatus = '';
var listGrid = null;

//加载国际化文件
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var listCm = [{
		header : main.arcMgt.transfMgt.grid.col.OP,
		dataIndex : 'OP',
		hidden: $F('transformerFromOther')=='true',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = '';
			//transformerFromOther配置变压器档案是否只从其他系统同步：true只从其他系统同步，界面上不显示增删改按钮；false不只可以从其他系统同步，也可FDM直接录入
			if (/*record.data.TRANSFORMERFROMOTHER*/$F('transformerFromOther') == 'false') {
				url = String.format('<a class="edit" title='+main.arcMgt.transfMgt.hint.edit+' href="javascript:editTransf(\'{0}\');"></a>', 
						record.data.TFID);
				url += String.format('<a class="del" title='+main.arcMgt.transfMgt.hint.del+' href="javascript:delTransf(\'{0}\');"></a>', 
						record.data.TFID);
			}
			return url;
		}
	},{
		header : main.arcMgt.transfMgt.grid.col.tfid,
		dataIndex : 'TFID',
		width : 120
	},{
		header : main.arcMgt.transfMgt.grid.col.tfname,
		dataIndex : 'TFNAME',
		width : 120
	},{
		header : main.arcMgt.transfMgt.grid.col.uid,
		dataIndex : 'UNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.UNAME+'</a>&nbsp;&nbsp;',
					record.data.UID);
			return url;
		}
	},{
		dataIndex : 'UID',
		hidden:true
	},{
		header : main.arcMgt.transfMgt.grid.col.addr,
		dataIndex : 'ADDR',
		width : 300
	},{
		header : main.arcMgt.transfMgt.grid.col.cap,
		dataIndex : 'CAP',
		width : 160
	},{
		header : main.arcMgt.transfMgt.grid.col.status,
		dataIndex : 'STATUS',
		width : 120
	},{
		header :  main.arcMgt.transfMgt.grid.col.dataSrc,
		dataIndex : 'DATASRC',
		width : 160
	},{
		dataIndex : 'TFSTATUS',
		hidden : true
	}/*,{
		dataIndex : 'TRANSFORMERFROMOTHER',
		hidden : true
	}*/];
		
	var toolbar = null;
	//transformerFromOther配置变压器档案是否只从其他系统同步：true只从其他系统同步，界面上不显示增删改按钮；false不只可以从其他系统同步，也可FDM直接录入
	if ( $F('transformerFromOther') == 'false' ) {
		toolbar = [{
	        id : 'add-buton',
			text : main.arcMgt.transfMgt.hint.add,
	        tooltip : main.arcMgt.transfMgt.hint.add,
	        iconCls : 'add',
	        handler : function(){
	        	addTransf();
	        }
	    }];
	}
	
	listGrid = new Wg.Grid( {
			url : grid_url,
			el : 'listGrid',
			pageSize : 30,
			title : main.arcMgt.transfMgt.grid.title,
			heightPercent : 0.80,
			widthPercent : 1,
			margin : 60,
			tbar : toolbar,
			cModel : listCm
		});
	
	var status = $('status').value;
	var tfName = $F('tfName');
	var nodeId = $F('nodeId');
	var p = {
		tfName : tfName,
		status : status,
		nodeId : nodeId,
		uid : '',
		addr : '',
		cap : ''		
	};
	listGrid.init(p);
});

//查询
function query() {
	var p=$FF('transfForm');
	listGrid.reload(p);
}

//定义reloadGrid函数，方便引用
function reloadGrid() {
	var p=$FF('transfForm');
	listGrid.reload(p);	
}

//新增变压器
function addTransf() {
	var url = String.format(ctx + '/main/arcMgt/transfMgt!initTransfMgtEdit.do?operateType={0}','add');
	var baseParam = {
		url : url,
		el : 'Transfwin',
		title : main.arcMgt.transfMgt.add.title,
		width : 600,
		height :420,
		draggable : true
	};
	Transfwin = new Wg.window(baseParam);
	Transfwin.open();
}

//修改变压器
function editTransf(tfId) {	
	var url = String.format(
			ctx + '/main/arcMgt/transfMgt!initTransfMgtEdit.do?operateType={0}&tfId={1}',
			 'edit', $En(tfId));
	var baseParam = {
			url : url,
			title : main.arcMgt.transfMgt.edit.title,
			el : 'Transfwin',
			width : 600,
			height : 420,
			draggable : true
	};
	Transfwin = new Wg.window(baseParam);
	Transfwin.open();
}

//删除变压器
function delTransf(tfId) {
	var p = {
		tfId : tfId
	};
	Ext.apply(p, {
		logger : main.arcMgt.transfMgt.del.logger + tfId
	});
	Wg.confirm(main.arcMgt.transfMgt.del.confirm, function() {
		dwrAction.doDbWorks('transfMgtAction', menuId + '03', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					reloadGrid();
				}
			});
		});
	});
}

//复位按钮
function Reset(){
	/*$('nodeId').vaule="";
	$('nodeText').value="";
	$('nodeDwdm').value="";*/
	$('tfId').value="";
	$('tfName').value="";
	$('status').value="";
	$('dataSrc').value="";
}