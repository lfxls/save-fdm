var menuId = '51200';
var grid_url = ctx + '/system/qyjggl/insteamMgt!query.do';
var insteamsts = '';
var listGrid = null;
loadProperties('sysModule', 'sysModule_orgMgt');

Ext.onReady(function() {
	hideLeft();	
	var listCm = [{
		header : sys.orgMgt.insteamMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+sys.orgMgt.insteamMgt.hint.edit+' href="javascript:editInsteam(\'{0}\');"></a>', 
					record.data.TNO);
			url += String.format('<a class="del" title='+sys.orgMgt.insteamMgt.hint.del+' href="javascript:delInsteam(\'{0}\');"></a>', record.data.TNAME);
			return url;
		}
	},{
		header : sys.orgMgt.insteamMgt.grid.col.tname,
		dataIndex : 'TNAME',
		width : 160
	},{
		header : sys.orgMgt.insteamMgt.grid.col.rspName,
		dataIndex : 'RSP_NAME',
		width : 160
	},{
		header : sys.orgMgt.insteamMgt.grid.col.pnum,
		dataIndex : 'P_NUM',
		width : 160
	},{
		header : sys.orgMgt.insteamMgt.grid.col.phone,
		dataIndex : 'PHONE',
		width : 160
	},{
		header : sys.orgMgt.insteamMgt.grid.col.status,
		dataIndex : 'STATUS',
		width : 160
	},{
		header : sys.orgMgt.insteamMgt.grid.col.crtDate,
		dataIndex : 'CRT_DATE',
		width : 80
	},{
		dataIndex : 'INSTEAMSTS',
		hidden : true
	},{
		dataIndex : 'TNO',
		hidden : true
	}];
		
	var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : sys.orgMgt.insteamMgt.hint.add,
        tooltip : sys.orgMgt.insteamMgt.hint.add,
        iconCls : 'add',
        handler : function(){
        	addInsteam();
        }
    }];
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 30,
		title : sys.orgMgt.insteamMgt.grid.title,
		heightPercent : 0.80,
		widthPercent : 1,
		margin : 60,
		tbar : toolbar,
		cModel : listCm
	});
	
	var status = $('status').value;
	var tname = $F('tname');
	var p = {
		tname : tname,
		status : status,
		rsp_name : '',
		p_num : '',
		phone : '',
		crt_date : ''		
	};
	listGrid.init(p);
});

//查询
function query() {
	var p=$FF('insteamform');
	listGrid.reload(p);
}
//定义reloadGrid函数，方便insteamMgt_edit引用
function reloadGrid() {
	var tno = $F('tno');
	var tname = $F('tname');
	var rsp_name = $F('rsp_name');
	var status = $('status').value;
	var phone = $F('phone');
	var crt_date = $F('crt_date');
	var p = {
		tno : tno,
		tname : tname,
		rsp_name : rsp_name,
		status : status,
		phone : phone
	};
	listGrid.reload(p);	
}

//新增安装队
function addInsteam() {
	var url = String.format(ctx + '/system/qyjggl/insteamMgt!initInsteamMgtEdit.do?operateType={0}','add');
	var baseParam = {
		url : url,
		el : 'Insteamwin',
		title : sys.orgMgt.insteamMgt.add.title,
		width : 600,
		height :420,
		draggable : true
	};
	Insteamwin = new Wg.window(baseParam);
	Insteamwin.open();
}

//修改安装队
function editInsteam(tno) {
	var url = String.format(
			ctx + '/system/qyjggl/insteamMgt!initInsteamMgtEdit.do?operateType={0}&tno={1}',
			 'edit', $En(tno) );
	var baseParam = {
			url : url,
			title : sys.orgMgt.insteamMgt.edit.title,
			el : 'Insteamwin',
			width : 600,
			height : 420,
			draggable : true
	};
	Insteamwin = new Wg.window(baseParam);
	Insteamwin.open();
}

//删除安装队
function delInsteam(tname) {
	var p = {
		tname : tname
	};
	Ext.apply(p, {
		logger : sys.orgMgt.insteamMgt.del.logger + tname
	});
	Wg.confirm(sys.orgMgt.insteamMgt.del.confirm, function() {
		dwrAction.doDbWorks('insteamMgtAction', menuId + '03', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					reloadGrid();
				}
			});
		});
	});
}