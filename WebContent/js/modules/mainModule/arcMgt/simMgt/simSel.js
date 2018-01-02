var menuId = '11500';
var grid_url = ctx + '/main/arcMgt/simMgt!querySel.do';
var insteamsts = '';
var listGrid = null;
loadProperties('mainModule', 'mainModule_arcMgt');

Ext.onReady(function() {
	var listCm = [{
		header : main.arcMgt.simMgt.grid.col.OP,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'+main.arcMgt.simMgt.hint.select+'" href="javascript:selectSim(\'{0}\');" ></a>',
					record.data.SIMNO);
			return url;
		}
	},{
		header : main.arcMgt.simMgt.grid.col.simno,
		dataIndex : 'SIMNO',
		width : 100
	},{
		header : main.arcMgt.simMgt.grid.col.simsn,
		dataIndex : 'SIMSN',
		width : 100
	},{
		header : main.arcMgt.simMgt.grid.col.msp,
		dataIndex : 'MSP',
		width : 100
	},{
		dataIndex : 'TXFWSLS',
		hidden : true
	}];
		
	/*var toolbar = null;
	toolbar = [{
        id : 'add-buton',
		text : main.arcMgt.simMgt.hint.add,
        tooltip : main.arcMgt.simMgt.hint.add,
        iconCls : 'add',
        handler : function(){
        	addSim();
        }
    }];*/
	
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize : 5,
		title : main.arcMgt.simMgt.grid.title,
		heightPercent : 0.7,
		widthPercent : 1,
		isExport: false,
		margin : 60,
		//tbar : toolbar,
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

function selectSim(simno){
	parent.$('simno').value=simno;
	parent.win.close();
}