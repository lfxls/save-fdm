loadProperties('reportModule', 'reportModule_insInfoReport');
var grid_url = ctx + '/report/insInfoReport/woStsRep!query.do';
var planGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 180,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>',
					record.data.WOID);
			return url;
		}
	},{
		header :  report.insInfoReport.woStsRep.grid.col.woType,
		dataIndex : 'WOTYPE',
		width : 150
	},{
		header : report.insInfoReport.woStsRep.grid.col.status,
		dataIndex : 'STATUSNAME',
		width : 80
	},{
		dataIndex : 'STATUS',
		hidden:true
	},{
		header :common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 120
	},{
		header :report.insInfoReport.grid.col.OPTNAME,
		dataIndex : 'OPTNAME',
		width : 150	
	},{
		header :report.insInfoReport.woStsRep.grid.col.insNum,
		dataIndex : 'INSNUM',
		width : 120,
		renderer : function(value, cellmeta, record) {
			if(record.data.INSNUM=="0")
			{
			return record.data.INSNUM;
			}
			else{
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.INSNUM+'</a>',
					record.data.WOID,"1");
			return url;
			}
		}
	},{
		header : report.insInfoReport.woStsRep.grid.col.uninsNum,
		dataIndex : 'UNINSNUM',
		width : 120,
		renderer : function(value, cellmeta, record) {
			if(record.data.UNINSNUM=="0")
			{
			return record.data.UNINSNUM;
			}
			else{
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.UNINSNUM+'</a>',
					record.data.WOID,"2");
			return url;
			}
		}
	},{
		header : report.insInfoReport.woStsRep.grid.col.totalNum,
		dataIndex : 'TOTALNUM',
		width : 120,
		renderer : function(value, cellmeta, record) {
			if(record.data.TOTALNUM=="0")
			{
			return record.data.TOTALNUM;
			}
			else{
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.TOTALNUM+'</a>',
					record.data.WOID,"0");
			return url;
			}
		}
	},{
		header : report.insInfoReport.grid.col.PFDATE,
		dataIndex : 'PFDATE',
		width : 140
	},{
		header : report.insInfoReport.grid.col.OPDATE,
		dataIndex : 'OPDATE',
		width : 140,
	},{
		header : report.insInfoReport.woStsRep.grid.col.delayDate,
		dataIndex : 'DELAYDATE',
		width : 90,
	}];
	
	planGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insInfoReport.planStsRep.grid.title,
		heightPercent : 0.75,
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
		tno:$F('tno')
	};
	dwrAction.doAjax("woStsRepAction", "getChart",p, function(res) {
		if (res.success) {
			if  ( FusionCharts( "woStsRep" ) )  FusionCharts( "woStsRep" ).dispose();
			var ac1 = new FusionCharts(Pie3D, "woStsRep", "600", "250", "0", "0");
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
	$('woid').value="";
	$('delayDate').value="";
}
function openDetail(woid,type){
	var url = String.format(ctx + '/report/insInfoReport/insDetailRep!init.do?woid={0}&proType={1}', woid, type);
//	var baseParam = {
//		url : url,
//		title : report.insInfoReport.insDetailRep.grid.title,
//		el : 'treeDiv',
//		width : 900,
//		height : 500,
//		draggable : true
//	};
//	
//	treeWin = new Wg.window(baseParam);
//	treeWin.open();
	top.openpageOnTree(report.insInfoReport.insDetailRep.grid.title,"21310",report.insInfoReport.insDetailRep.grid.title,null,url,'yes',1);

}