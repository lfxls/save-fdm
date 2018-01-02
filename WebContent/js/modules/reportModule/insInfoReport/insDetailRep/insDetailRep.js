loadProperties('reportModule', 'reportModule_insInfoReport');
var grid_url = ctx + '/report/insInfoReport/insDetailRep!query.do';
var detailGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 180,
		
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 180,
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 150
	},{
		header :report.insInfoReport.grid.col.OMSN,
		dataIndex : 'OMSN',
		width : 150
	},{
		header : report.insInfoReport.grid.col.NMSN,
		dataIndex : 'NMSN',
		width : 150
	},{
		header : report.insInfoReport.grid.col.bussType,
		dataIndex : 'BUSSTYPE',
		width : 150
	},{
		header : report.insInfoReport.grid.col.PFDATE,
		dataIndex : 'PFDATE',
		width : 160
	},{
		header : report.insInfoReport.grid.col.OPDATE,
		dataIndex : 'OPDATE',
		width : 160,
		renderer : function(value, cellmeta, record) {
			if(record.data.STATUS=="3"||record.data.STATUS=="4")
			{
			return record.data.OPDATE;
			}
			else{
			return "";
			}
		}
	}, {
		header :common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 190
	},{
		header :report.insInfoReport.grid.col.OPTNAME,
		dataIndex : 'OPTNAME',
		width : 190
	},{
		dataIndex : 'STATUS',
		hidden : true
	}];
	
	detailGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title :report.insInfoReport.insDetailRep.grid.title,
		heightPercent : 0.95,
		widthPercent : 1,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	detailGrid.init(p, false, false);
});