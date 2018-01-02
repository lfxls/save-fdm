var menuId = '51200';
var grid_url = ctx + '/system/qyjggl/insteamMgt!querySel.do';
var insteamsts = '';
var listGrid = null;
loadProperties('sysModule', 'sysModule_orgMgt');

Ext.onReady(function() {
	//hideLeft();	
	var listCm = [{
		header : sys.orgMgt.insteamMgt.sel.grid.col.OP,
		dataIndex : 'OP',
		width : 60,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'+sys.orgMgt.insteamMgt.hint.select+'" href="javascript:selectInsteam(\'{0}\',\'{1}\');" ></a>',
					record.data.TNAME, record.data.TNO);
			return url;
		}
	},{
		header : sys.orgMgt.insteamMgt.grid.col.tname,
		dataIndex : 'TNAME',
		width : 120
	},{
		header : sys.orgMgt.insteamMgt.grid.col.rspName,
		dataIndex : 'RSP_NAME',
		width : 120
	},{
		header : sys.orgMgt.insteamMgt.grid.col.phone,
		dataIndex : 'PHONE',
		width : 120
	},{
		dataIndex : 'TNO',
		hidden : true
	}];
		
	/*var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : insteamMgt_insteam_list_add_hint,
        tooltip : insteamMgt_insteam_list_add_hint,
        iconCls : 'add',
        handler : function(){
        	addInsteam();
        }
    }];*/
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 5,
		title : sys.orgMgt.insteamMgt.grid.title,
		heightPercent : 0.7,
		isExport: false,
		widthPercent : 1,
		margin : 60,
		//tbar : toolbar,
		cModel : listCm
	});
	
	/*var status = $('status').value;*/
	var tname = $F('tname');
	var p = {
		tname : tname,
		status : '',
		rsp_name : '',
		phone : '',
		crt_date : ''		
	};
	listGrid.init(p);
	//listGrid.init(p, true, false);
	
	//单选事件
	/*listGrid.grid.addListener('rowclick', rowclickFn);*/
});

/*function rowclickFn(grid, rowindex, e) {
	grid.getSelectionModel().each(function(rec) {
		insteamsts = rec.get('INSTEAMSTS');
	});
}*/

//查询
function query() {
	/*var p = {tname:$F('tname')};*/
	var p=$FF('queryForm');
	listGrid.reload(p);
}
	
function selectInsteam(tname, tno){
	parent.$('tname').value=tname;
	parent.$('tno').value=tno;
	parent.win.close();
}