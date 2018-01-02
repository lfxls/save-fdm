loadProperties('reportModule', 'reportModule_insQualReport');
var menuId = '22200';
var grid_url = ctx + '/report/insQualReport/probNumRep!queryDetail.do';
var probNumGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 180,
		
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 180,
	},{
		dataIndex : 'TNO',
		hidden : true
	},{
		dataIndex : 'TIID',
		hidden : true
	},{
		dataIndex : 'VERID',
		hidden : true
	},{
		header : report.insQualReport.probDetailRep.grid.col.tiname,
		dataIndex : 'TINAME',
		width : 160,
		renderer : function(value, cellmeta, record) {
			
			
				var url = String.format('<a href="javascript:openTestItem(\'{0}\',\'{1}\',\'{2}\');">'+record.data.TINAME+'</a>',
						record.data.PID,record.data.TIID,record.data.VERID);
				return url;
			
		}
	},{
		header : common.kw.meter.MSN,
		dataIndex : 'MSN',
		width : 100,
	},{
		header : report.insQualReport.probDetailRep.grid.col.RESULT,
		dataIndex : 'RST',
		width : 90,
	}];
	
	probNumGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insQualReport.probDetailRep.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	probNumGrid.init(p, false, false);
});

function query(){
	var p=$FF('queryForm');
	probNumGrid.reload(p);
}

function openTestItem(pid,tiid,verid){
	var url = String.format(ctx +  '/report/insQualReport/probNumRep!initTestDetail.do?pid={0}&tiid={1}&verid={2}',pid,tiid,verid);
	var baseParam = {
		url : url,
		title : report.insQualReport.TestDetailRep.grid.title,
		el : 'probDetail',
		width : 800,
		height : 600,
		draggable : true
	};
	DetailWin = new Wg.window(baseParam);
	DetailWin.open();	
}