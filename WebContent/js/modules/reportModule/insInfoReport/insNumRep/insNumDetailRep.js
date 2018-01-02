loadProperties('reportModule', 'reportModule_insInfoReport');
var menuId = '21100';
var grid_url = ctx + '/report/insInfoReport/insNumRep!queryDetail.do';
var insGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : report.insInfoReport.insNumRep.grid.col.TName,
		dataIndex : 'TNAME',
		width : 120,
	},{
		dataIndex : 'TNO',
		hidden:true
	},{
		header : report.insInfoReport.insNumRep.grid.col.uptDate,
		dataIndex : 'OPDATE',
		width : 120
	},{
		header : report.insInfoReport.insNumRep.grid.col.insNum,
		dataIndex : 'INSNUM',
		width : 150,
		renderer : function(value, cellmeta, record) {
			if(record.data.INSNUM=="0")
			{
			return record.data.INSNUM;
			}
			else{
			var url = String.format('<a href="javascript:openInsDetail(\'{0}\',\'{1}\',\'{2}\');">'+record.data.INSNUM+'</a>',
					record.data.TNO,"0",record.data.OPDATE);
			return url;
			}
		}
	},{
		header : report.insInfoReport.insNumRep.grid.col.replaceNum,
		dataIndex : 'REPNUM',
		width : 150,
		renderer : function(value, cellmeta, record) {
			if(record.data.REPNUM=="0")
			{
			return record.data.REPNUM;
			}
			else{
			var url = String.format('<a href="javascript:openInsDetail(\'{0}\',\'{1}\',\'{2}\');">'+record.data.REPNUM+'</a>',
					record.data.TNO,"1",record.data.OPDATE);
			return url;
			}
		}
	},{
		header : report.insInfoReport.insNumRep.grid.col.uninsNum,
		dataIndex : 'UNINSNUM',
		width : 150,
		renderer : function(value, cellmeta, record) {
			if(record.data.UNINSNUM=="0")
			{
			return record.data.UNINSNUM;
			}
			else{
			var url = String.format('<a href="javascript:openInsDetail(\'{0}\',\'{1}\',\'{2}\');">'+record.data.UNINSNUM+'</a>',
					record.data.TNO,"2",record.data.OPDATE);
			return url;
			}
		}
	},{
		header : report.insInfoReport.insNumRep.grid.col.subNum,
		dataIndex : 'TOLNUM',
		width : 120
	},{
		header : report.insInfoReport.insNumRep.grid.col.PNum,
		dataIndex : 'PNUM',
		width : 180,
	}, {
		header :report.insInfoReport.insNumRep.grid.col.averPNum,
		dataIndex : 'AVERNUM',
		width : 190
	}];
	
	insGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insInfoReport.insNumRep.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	insGrid.init(p, false, false);
	showChart();
});
function query(){
	var p=$FF('queryForm');
	insGrid.reload(p);
	showChart();
}
function changeDateType(value){
	if(value==0){

		$('Day').style.display='';
		$('Month').style.display='none';
	}
	else{
		$('Day').style.display='none';
		$('Month').style.display='';
	}
}

function showChart(){
	var p=$FF('queryForm');
	dwrAction.doAjax('insNumRepAction', 'getChart', p, function(res) {
		if (res.success) {
			var chart;
			if  ( FusionCharts( "Chart1Id" ) )  FusionCharts( "Chart1Id" ).dispose();
			if  ( FusionCharts( "Chart2Id" ) )  FusionCharts( "Chart2Id" ).dispose();
			if('0' == $F('chartType')){
				chart = new FusionCharts(MSLine, "Chart1Id", "90%", "280",
					"0", "0");
			}
			if('1' == $F('chartType')){
				chart = new FusionCharts(MSColumn3D, "Chart2Id", "90%", "280",
					"0", "0");
			}					
			chart.setDataXML(res.dataObject);
			chart.render("chartDiv");
		}
	});
	
}
function openInsDetail(tno,bussType,opDate){
	var dateType=$F('dateType');
	var url = String.format(ctx + '/report/insInfoReport/insDetailRep!init.do?tno={0}&bussType={1}&opDate={2}&dateType={3}', tno, bussType,opDate,dateType);
	top.openpageOnTree(report.insInfoReport.insDetailRep.grid.title,"21310",report.insInfoReport.insDetailRep.grid.title,null,url,'yes',1);

}
