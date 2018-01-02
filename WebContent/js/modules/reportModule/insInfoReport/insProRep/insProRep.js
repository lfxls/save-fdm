loadProperties('reportModule', 'reportModule_insInfoReport');
var menuId = '21100';
var grid_url = ctx + '/report/insInfoReport/insProRep!query.do';
var _Grid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header : common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 120,
	},{
		dataIndex : 'TNO',
		hidden:true
	},{
		header : report.insInfoReport.insProRep.grid.col.subNum,
		dataIndex : 'SUBNUM',
		width : 120,
		renderer : function(value, cellmeta, record) {
			if(record.data.SUBNUM=="0")
			{
			return record.data.SUBNUM;
			}
			else{
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.SUBNUM+'</a>',
					record.data.TNO,"0");
			return url;
			}
		}
	},{
		header : report.insInfoReport.insProRep.grid.col.insNum,
		dataIndex : 'INSNUM',
		width : 150,
		renderer : function(value, cellmeta, record) {
			if(record.data.INSNUM=="0")
			{
			return record.data.INSNUM;
			}
			else{
			var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.INSNUM+'</a>',
					record.data.TNO,"1");
			return url;
			}
		}
	},{
		header : report.insInfoReport.insProRep.grid.col.uninsNum,
		dataIndex : 'UNINSNUM',
		width : 160,
		renderer : function(value, cellmeta, record) {
			if(record.data.UNINSNUM=="0")
			{
			return record.data.UNINSNUM;
			}
			else{
				var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.UNINSNUM+'</a>',
						record.data.TNO,"2");
				return url;
			}
		}
	},{
		header : report.insInfoReport.insProRep.grid.col.DELAYNUM,
		dataIndex : 'DELAYNUM',
		width : 220,
		renderer : function(value, cellmeta, record) {
			if(record.data.DELAYNUM=="0")
				{
				return record.data.DELAYNUM;
				}
			else{
				var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.DELAYNUM+'</a>',
						record.data.TNO,"3");
				return url;
			}
		}
	},{
		header : report.insInfoReport.insProRep.grid.col.dateNum,
		dataIndex : 'DATENUM',
		width : 120
	},{
		header : report.insInfoReport.insProRep.grid.col.averNum,
		dataIndex : 'AVERNUM',
		width : 200
	}];
	
	_Grid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insInfoReport.insProRep.grid.title,
		heightPercent : 0.50,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	_Grid.init(p, false, false);
	showChart();
});

function query(){
	if(valid()){
		var p=$FF('queryForm');
		_Grid.reload(p);
		showChart();
	}
}
function showChart(){
	var p=$FF('queryForm');
	dwrAction.doAjax("insProRepAction", "getChart",p, function(res) {
		if (res.success) {
			if  ( FusionCharts( "insProRep" ) )  FusionCharts( "insProRep" ).dispose();
			var ac1 = new FusionCharts(MSColumn2D, "insProRep", "100%", "280", "0", "0");
			ac1.setDataXML(res.dataObject);
			ac1.render("chartDiv");
		}
	});
}
function openDetail(tno,type){
	var url = String.format(ctx + '/report/insInfoReport/insDetailRep!init.do?tno={0}&proType={1}', tno, type);
//	var baseParam = {
//		url : url,
//		title : report.insInfoReport.insDetailRep.grid.title,
//		el : 'treeDiv',
//		width : 900,
//		height : 700,
//		draggable : true
//	};
	top.openpageOnTree(report.insInfoReport.insDetailRep.grid.title,"21110",report.insInfoReport.insDetailRep.grid.title,null,url,'yes',1);
//	treeWin = new Wg.window(baseParam);
//	treeWin.open();
}
function valid(){
	var str=/^(\d{1,2}(\.\d{1,3})?|100)$/; 
	var str2=/^(0|[1-9][0-9]*)$/;
	var insNum=$F('insNum');
	var delayNum=$F('delayNum');
	
	if(!($E(delayNum)||str2.test(delayNum))){
		Ext.Msg.alert(report.insInfReport.alert.title,report.insInfReport.insProRep.valid.delayNum,function(btn,text){
			if(btn=='ok'){
				$('delayNum').value="";
				$('delayNum').focus();
			}
		});
		return false;
	}
	
	if(!($E($F('insNum'))||str2.test(insNum))){
		Ext.Msg.alert(report.insInfReport.alert.title,report.insInfReport.insProRep.valid.insNum,function(btn,text){
			if(btn=='ok'){
				$('insNum').value="";
				$('insNum').focus();
			}
		});
		return false;
	}


	
	return true;
}