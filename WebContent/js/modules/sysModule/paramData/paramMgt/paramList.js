loadProperties('sysModule', 'sysModule_paramData');
var menuId = '54200';
var grid_url = ctx + '/system/paramData/paramMgt!queryParamList.do';
var pmListGrid = '';
Ext.onReady(function() {
	if($F('paramType')==2){
		$('testParam').style.display="";
		$('param').style.display="none";
	}
	else{
		$('testParam').style.display="none";
		$('param').style.display="";
	}
	
	var _cm = [
{
	header : system.paramData.paramMgt.grid.col.op,
	dataIndex : 'OP',
	width : 60,
	renderer : function(value, cellmeta, record) {
		var url="";
		if($F('paramType')==2){
			url=String.format('<a class="addCell" title="'+system.paramData.paramMgt.hint.select+'" href="javascript:selectParam(\'{0}\',\'{1}\',\'{2}\');" ></a>',
					record.data.TIID, record.data.TINAME,"");
		}
		else{
			 url = String.format('<a class="addCell" title="'+system.paramData.paramMgt.hint.select+'" href="javascript:selectParam(\'{0}\',\'{1}\',\'{2}\');" ></a>',
					record.data.ITEM_ID, record.data.ITEM_NAME,record.data.BUSSTYPE);
		}
		return url;
	}
},
	       	{
	       		header : system.paramData.paramMgt.grid.col.item_id,
	       		dataIndex : 'ITEM_ID',
	       		width : 180,
	       		sortable : true
	       	},{
	    		header : system.paramData.paramMgt.grid.col.item_name,
	    		dataIndex : 'ITEM_NAME',
	    		width : 220
	    	},
	    	{
	    		dataIndex : 'BUSSTYPE',
	    		hidden:true
	    	},
	    	{
	    		header : system.paramData.paramMgt.grid.col.itemSort,
	    		dataIndex : 'ITEMSORT',
	    		width : 120
	    	},{
	       		dataIndex : 'TIID',
	       		width : 180,
	       		hidden:true
	       	},{
	    		header : system.paramData.paramMgt.grid.col.test_item_name,
	    		dataIndex : 'TINAME',
	    		width : 220
	    	},
	       	];
	
	pmListGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		//title : system.paramData.paramMgt.ParamListgrid.title,
		heightPercent : 0.70,
		widthPercent :1,
		cModel : _cm,
	//	pageNotSupport : true,
		excelUrl : grid_url
	});
	var p=$FF('queryForm');
	pmListGrid.init(p, false, false);
	showCol();
//	pmListGrid.grid.addListener('rowclick', rowclickFn);
});

function query(){
	var p=$FF('queryForm');
	pmListGrid.reload(p);
}
//function rowclickFn(pmListGrid, rowindex, e) {
//	pmListGrid.getSelectionModel().each(function(rec) {
//		parent.$('itemId').value = rec.get('ITEM_ID');
//		parent.$('itemName').value = rec.get('ITEM_NAME');
//		if(rec.get('BUSSTYPE')==null||rec.get('BUSSTYPE')==""){
//			parent.$('bussType').value ="0";
//		}
//		else {
//			parent.$('bussType').value ="1";
//		}
//		if($F('paramType')=="1"){
//			parent.changeBussType();
//		}
//		parent.win.close();
//	});
//}

function selectParam(item_id,item_name,busstype){
	parent.$('itemId').value =item_id;
	parent.$('itemName').value = item_name;
	if(busstype==null||busstype==""){
		parent.$('bussType').value ="0";
	}
	else if(busstype=="FROZEN_MONTH_BDATE"){
		parent.$('bussType').value ="2";
	}
	else {
		parent.$('bussType').value ="1";
	}
	if($F('paramType')=="1"){
		parent.changeBussType();
	}
	if($F('paramType')=="2"){
		parent.$('testId').value =item_id;
		parent.$('testName').value = item_name;
	}
	parent.win.close();
}
function showCol(){
	if($F('paramType')=="2"){
		pmListGrid.hideCol(1);
		pmListGrid.hideCol(2);
		pmListGrid.hideCol(4);
		pmListGrid.showCol(6);
	
	}
	else{
		pmListGrid.showCol(1);
		pmListGrid.showCol(2);
		pmListGrid.showCol(4);
		pmListGrid.hideCol(6);
		
	}
}