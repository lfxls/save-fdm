loadProperties('reportModule', 'reportModule_insInfoReport');
var menuId = '21100';
var grid_url = ctx + '/report/insInfoReport/planStsRep!query.do';
var planGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 180,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryPlan(\'{0}\');">'+record.data.PID+'</a>',
					record.data.PID);
			return url;
		}
	},{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 180,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>',
					record.data.WOID);
			return url;
		}
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120
	},{
		header :common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	},{
		header : report.insInfoReport.grid.col.OMSN,
		dataIndex : 'OMSN',
		width : 120
	},{
		header : report.insInfoReport.grid.col.NMSN,
		dataIndex : 'NMSN',
		width : 120
	},{
		header : report.insInfoReport.planStsRep.grid.col.status,
		dataIndex : 'STATUSNAME',
		width : 80
	},{
		dataIndex : 'STATUS',
		hidden:true
	},{
		header : common.kw.plan.busstype,
		dataIndex : 'BUSSTYPE',
		width : 100
	},{
		header :common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 120
	},{
		header :report.insInfoReport.grid.col.OPTNAME,
		dataIndex : 'OPTNAME',
		width : 150
	},{
		header : report.insInfoReport.grid.col.PFDATE,
		dataIndex : 'PFDATE',
		width : 160
	},{
		header : report.insInfoReport.grid.col.OPDATE,
		dataIndex : 'OPDATE',
		width : 160,
	},{
		header : report.insInfoReport.planStsRep.grid.col.delayDate,
		dataIndex : 'DELAYDATE',
		width : 90,
	}];
	
	planGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insInfoReport.planStsRep.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	planGrid.init(p, false, false);
	showChart();
});

function query(){
	var p=$FF('queryForm');
	planGrid.reload(p);
	showChart();
}
function showChart(){
	var p={
		startDate:$F('startDate'),
		endDate:$F('endDate'),
		tno:$F('tno')
	};
	dwrAction.doAjax("planStsRepAction", "getChart",p, function(res) {
		if (res.success) {
			if  ( FusionCharts( "planStsRep" ) )  FusionCharts( "planStsRep" ).dispose();
			var ac1 = new FusionCharts(Pie3D, "planStsRep", "600", "250", "0", "0");
			var obj = Ext.decode(res.msg);
			ac1.setDataXML(obj.pieXml);
			ac1.render("chartDiv");
		}
	});
}
function reload(status){
	$('status').value=status;
	var p=$FF('queryForm');
	planGrid.reload(p);
}
function Reset(){
	$('tno').value="";
	$('tname').value="";
	$('status').value="";
	$('delayDate').value="";
	$('woid').value="";
}
