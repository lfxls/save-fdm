loadProperties('reportModule', 'reportModule_insQualReport');
var menuId = '22100';
var grid_url = ctx + '/report/insQualReport/insFailRep!query.do';
var failGrid = '';
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
		header : report.insQualReport.grid.col.OMSN,
		dataIndex : 'OMSN',
		width : 120
	},{
		header : report.insQualReport.grid.col.NMSN,
		dataIndex : 'NMSN',
		width : 120
	},{
		header :common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 120
	},{
		header :report.insQualReport.grid.col.OPTNAME,
		dataIndex : 'OPTNAME',
		width : 150
	},{
		header : report.insQualReport.insFailRep.grid.col.TINAME,
		dataIndex : 'TINAME',
		width : 140
	},{
		header : report.insQualReport.insFailRep.grid.col.LEVEL,
		dataIndex : 'LEVEL',
		width : 140,
	},{
		header : report.insQualReport.insFailRep.grid.col.RESULT,
		dataIndex : 'RESULT',
		width : 140,
	}];
	
	failGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insQualReport.insFailRep.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	failGrid.init(p, false, false);
	showChart();
});

function query(){
	var p=$FF('queryForm');
	failGrid.reload(p);
	showChart();
}
function showChart(){
	var p={
		tno:$F('tno')
	};
	dwrAction.doAjax("insFailRepAction", "getChart",p, function(res) {
		if (res.success) {
			if  ( FusionCharts( "insFailRep" ) )  FusionCharts( "insFailRep" ).dispose();
			var ac1 = new FusionCharts(Pie3D, "insFailRep", "600", "250", "0", "0");
			var obj = Ext.decode(res.msg);
			ac1.setDataXML(obj.pieXml);
			ac1.render("chartDiv");
		}
	});
}
function reload(tiid){
	$('tiid').value=tiid;
	var p=$FF('queryForm');
	failGrid.reload(p);
}
function Reset(){
	$('tno').value="";
	$('tname').value="";
	$('tiid').value="";
	$('level').value="";
}
