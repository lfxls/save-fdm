loadProperties('reportModule', 'reportModule_insInfoReport');
var menuId = '21300';
var grid_url = ctx + '/report/insInfoReport/insNumRep!query.do';
var insGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : report.insInfoReport.insNumRep.grid.col.TName,
		dataIndex : 'TNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.TNAME+'</a>',
					record.data.TNO,record.data.TNAME);
			return url;
		}
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
		title :report.insInfoReport.insNumRep.grid.title,
		heightPercent : 0.80,
		
		cModel : _cm,
	});
	var p=$FF('queryForm');
	insGrid.init(p, false, false);
	//showChart();
});
function query(){
	var p=$FF('queryForm');
	insGrid.reload(p);
}
function showChart(){
	
	var p;
	if($F('dateType')==0){
		p={
				tno:$F('tno'),
				chartType:$F('chartType'),
				startDate:$F('startDate'),
				endDate:$F('endDate')
		};
	}
	else if($F('dateType')==1){
		p={
				tno:$F('tno'),
				chartType:$F('chartType'),
				startMonth:$F('startMonth'),
				endMonth:$F('endMonth')
		};
	}
	alert($F('chartType'));
	dwrAction.doAjax('insNumRepAction', 'getChart', p, function(res) {
		if (res.success) {
			var chart;
			if  ( FusionCharts( "Chart1Id" ) )  FusionCharts( "Chart1Id" ).dispose();
			if  ( FusionCharts( "Chart2Id" ) )  FusionCharts( "Chart2Id" ).dispose();
			if('01' == $F('chartType')){
				chart = new FusionCharts(MSLine, "Chart1Id", "100%", "280",
					"0", "0");
			}
			if('02' == $F('chartType')){
				chart = new FusionCharts(MSColumn3D, "Chart2Id", "100%", "280",
					"0", "0");
			}					
			chart.setDataXML(res.dataObject);
			chart.render("chartDiv");
		}
	});
	
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
	query();
}
function openDetail(tno,tname){
	var title =report.insInfoReport.insNumRep.insNumDetailRep.title;
	var  url=String.format(ctx + '/report/insInfoReport/insNumRep!initDetail.do?tno={0}&tname={1}&startMonth={2}&&endMonth={3}',tno,tname,$F('startMonth'),$F('endMonth'));
	top.openpageOnTree(title, '41200', title, null, url, 'yes', 1);
}
function openInsDetail(tno,bussType,opDate){
	var dateType=$F('dateType');
	var url = String.format(ctx + '/report/insInfoReport/insDetailRep!init.do?tno={0}&bussType={1}&opDate={2}&dateType={3}', tno, bussType,opDate,dateType);
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
function Reset(){
	$('tno').value="";
	$('tname').value="";
}
