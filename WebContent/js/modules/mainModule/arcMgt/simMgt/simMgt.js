var menuId = '11500';
var grid_url = ctx + '/main/arcMgt/simMgt!query.do';
var insteamsts = '';
var listGrid = null;
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	hideLeft();	
	var listCm = [{
		header : main.arcMgt.simMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+main.arcMgt.simMgt.hint.edit+' href="javascript:editSim(\'{0}\');"></a>', 
					record.data.SIMNO);
			url += String.format('<a class="del" title='+main.arcMgt.simMgt.hint.del+' href="javascript:delSim(\'{0}\');"></a>', record.data.SIMNO);
			return url;
		}
	},{
		header : main.arcMgt.simMgt.grid.col.simno,
		dataIndex : 'SIMNO',
		width : 160
	},{
		header : main.arcMgt.simMgt.grid.col.simsn,
		dataIndex : 'SIMSN',
		width : 160
	},{
		header : main.arcMgt.simMgt.grid.col.msp,
		dataIndex : 'MSP',
		width : 160
	},{
		dataIndex : 'TXFWSLS',
		hidden : true
	}];
		
	var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : main.arcMgt.simMgt.hint.add,
        tooltip : main.arcMgt.simMgt.hint.add,
        iconCls : 'add',
        handler : function(){
        	addSim();
        }
    }];
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 30,
		title : main.arcMgt.simMgt.grid.title,
		heightPercent : 0.80,
		widthPercent : 1,
		margin : 60,
		tbar : toolbar,
		cModel : listCm
	});
	
	var simno = $F('simno');
	var simsn = $F('simsn');
	var p = {
		simno : simno,
		simsn : simsn,
		msp : ''		
	};
	listGrid.init(p);
	//listGrid.init(p, true, false);
});

//查询
function query() {
	var p=$FF('simForm');
	listGrid.reload(p);
}
//定义reloadGrid函数，方便insteamMgt_edit引用
function reloadGrid() {
	var simno = $F('simno');
	var simsn = $F('simsn');
	var msp = $('msp').value;
	var p = {
		simno : simno,
		simsn : simsn,
		msp : msp
	};
	listGrid.reload(p);	
}

//新增安装队
function addSim() {
	var url = String.format(ctx + '/main/arcMgt/simMgt!initSimMgtEdit.do?operateType={0}','add');
	var baseParam = {
		url : url,
		el : 'Simwin',
		title : main.arcMgt.simMgt.add.title,
		width : 600,
		height :420,
		draggable : true
	};
	Simwin = new Wg.window(baseParam);
	Simwin.open();
}

//修改安装队
function editSim(simno) {	
	var url = String.format(
			ctx + '/main/arcMgt/simMgt!initSimMgtEdit.do?operateType={0}&simno={1}',
			 'edit', $En(simno) );
	var baseParam = {
			url : url,
			title : main.arcMgt.simMgt.edit.title,
			el : 'Simwin',
			width : 600,
			height : 420,
			draggable : true
	};
	Simwin = new Wg.window(baseParam);
	Simwin.open();
}

//删除安装队
function delSim(simno) {
	var p = {
		simno : simno
	};
	Ext.apply(p, {
		logger : main.arcMgt.simMgt.del.logger + simno
	});
	Wg.confirm(main.arcMgt.simMgt.del.confirm, function() {
		dwrAction.doDbWorks('simMgtAction', menuId + '03', p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					listGrid.reload($FF('simForm'));
					//reloadGrid();
				}
			});
		});
	});
}