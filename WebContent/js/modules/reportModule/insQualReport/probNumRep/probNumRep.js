loadProperties('reportModule', 'reportModule_insQualReport');
var menuId = '22200';
var grid_url = ctx + '/report/insQualReport/probNumRep!query.do';
var probNumGrid = '';

Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
		header :common.kw.insteam.insteam,
		dataIndex : 'TNAME',
		width : 120
	},{
		dataIndex : 'TNO',
		hidden : true
	},{
		header :report.insQualReport.probNumRep.grid.col.failNum,
		dataIndex : 'FAILNUM',
		width : 150,
		renderer : function(value, cellmeta, record) {
			if(record.data.FAILNUM=="0"){
				return record.data.FAILNUM;
			}
			else{
				var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.FAILNUM+'</a>',
						record.data.TNO,"1");
				return url;
			}
		}
	},{
		header : report.insQualReport.probNumRep.grid.col.successNum,
		dataIndex : 'SUCCESSNUM',
		width : 140,
		renderer : function(value, cellmeta, record) {
			if(record.data.SUCCESSNUM=="0"){
				return record.data.SUCCESSNUM;
			}
			else{
				var url = String.format('<a href="javascript:openDetail(\'{0}\',\'{1}\');">'+record.data.SUCCESSNUM+'</a>',
						record.data.TNO,"0");
				return url;
			}
		}
	},{
		header : report.insQualReport.probNumRep.grid.col.totalNum,
		dataIndex : 'TOTALNUM',
		width : 140,
	}];
	
	probNumGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : report.insQualReport.probNumRep.grid.title,
		heightPercent : 0.80,
		widthPercent:0.99,
		cModel : _cm,
	});
	var p=$FF('queryForm');
	probNumGrid.init(p, false, false);
});

function query(){
	if(valid()){
		var p=$FF('queryForm');
		probNumGrid.reload(p);
	}
}
function openDetail(tno,result){
	var url = String.format(ctx +  '/report/insQualReport/probNumRep!initDetail.do?tno={0}&rst={1}',tno,result);
//	var baseParam = {
//		url : url,
//		title : report.insQualReport.probDetailRep.grid.title,
//		el : 'probDetail',
//		width : 800,
//		height : 600,
//		draggable : true
//	};
//	DetailWin = new Wg.window(baseParam);
//	DetailWin.open();
	top.openpageOnTree(report.insQualReport.probDetailRep.grid.title,"22210",report.insQualReport.probDetailRep.grid.title,null,url,'yes',1);

}
function Reset(){
	$('tno').value="";
	$('tname').value="";
	$('failNum').value="";
	$('abnorNum').value="";
}

function valid(){
	var str2=/^(0|[1-9][0-9]*)$/;
	var failNum=$F('failNum');
	var abnorNum=$F('abnorNum');
	
	if(!($E(failNum)||str2.test(failNum))){
		Ext.Msg.alert(report.insQualReport.alert.title,report.insQualReport.probDetailRep.valid.failNum,function(btn,text){
			if(btn=='ok'){
				$('failNum').value="";
				$('failNum').focus();
			}
		});
		return false;
	}
	
	if(!($E($F('abnorNum'))||str2.test(abnorNum))){
		Ext.Msg.alert(report.insQualReport.alert.title,report.insQualReport.probDetailRep.valid.abnorNum,function(btn,text){
			if(btn=='ok'){
				$('abnorNum').value="";
				$('abnorNum').focus();
			}
		});
		return false;
	}


	
	return true;
}