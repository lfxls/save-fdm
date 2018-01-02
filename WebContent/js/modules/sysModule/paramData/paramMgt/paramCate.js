loadProperties('sysModule', 'sysModule_paramData');
var menuId = '54200';
var grid_url = ctx + '/system/paramData/paramMgt!queryDetail.do';
var cateGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [
	{
	header : common.kw.other.Operat,
	dataIndex : 'OP',
	width : 80,
	locked:true,
	renderer : function(value, cellmeta, record) {
		var url="";
		url += String.format('<a class="edit" title="\{5}\" href="javascript:initEditCate(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
				record.data.CATEID,record.data.CATENAME,record.data.VERID,record.data.STATUS,record.data.SORTNUM,system.paramData.paramMgt.grid.col.edit);
		url += String.format('<a class="del" title="\{4}\" href="javascript:delCate(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{5}\');"></a>',
				record.data.CATENAME,record.data.CATEID,record.data.STATUS,record.data.PARAMTYPE,system.paramData.paramMgt.grid.col.del,record.data.VERID);
		return url;
	}
	},{
		header : system.paramData.paramMgt.grid.col.cate_name,
		dataIndex : 'CATENAME',
		width : 120,
		locked:true,
		sortable : true
	},{
		dataIndex : 'CATEID',
		hidden:true,
	},{
		header : system.paramData.paramMgt.grid.col.vername,
		dataIndex : 'VERNAME',
		width : 160
	},{
		dataIndex : 'VERID',
		hidden:true,
	},{
		header : system.paramData.paramMgt.grid.col.paramType,
		dataIndex : 'PMTYPE',
		width : 120
	},{
		dataIndex : 'PARAMTYPE',
		hidden:true,
	},{
		header : system.paramData.paramMgt.grid.col.status,
		dataIndex : 'STATUSNAME',
		width : 120
	},{
		dataIndex : 'STATUS',
		hidden:true,
	},{
		header : system.paramData.paramMgt.grid.col.sort,
		dataIndex : 'SORTNUM',
		width : 120
	}];
	var toolbar = [{
        id: 'add-buton',
		text:system.paramData.paramMgt.grid.tb.add,
        tooltip:system.paramData.paramMgt.grid.tb.add,
        iconCls:'add',
        handler: function(){
        	initAddCate();
        }
    }];
	cateGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : system.paramData.paramCateMgt.grid.title,
		heightPercent : 0.80,
		cModel : _cm,
		excelUrl : grid_url,
		tbar:toolbar,
//		excelParam : ['nodeId','nodeType','yhlx','yhzt']
	});
	var p=$FF('queryForm');
	cateGrid.init(p,false,false);
	
	
});

function query(){

	var p=$FF('queryForm');
	cateGrid.reload(p);
}

function initAddCate(){
	var paramType=$F('paramType');
	var verId=$F('verId');
	var url = String.format(ctx + '/system/paramData/paramMgt!initEditCate.do?paramType={0}&czid={1}&verId={2}',paramType,'01',verId);
	
	var baseParam = {
			url : url,
			title :system.paramData.paramCateMgt.add.wh,
			el : 'add',
			width : 700,
			height : 400,
			draggable : true
		};
	AddWin = new Wg.window(baseParam);
	AddWin.open();
}
function initEditCate(cateId,catename,verid,status,sortNum){
	var paramType=$F('paramType');
	var url = String.format(ctx + '/system/paramData/paramMgt!initEditCate.do?cateId={0}&verId={1}&paramType={2}&cateName={3}&status={4}&sortNum={5}&czid={6}',cateId,verid,paramType,catename,status,sortNum,'02');
	
	var baseParam = {
			url : url,
			title : system.paramData.paramCateMgt.edit.wh,
			el : 'edit',
			width : 700,
			height : 400,
			draggable : true
		};
	EditWin = new Wg.window(baseParam);
	EditWin.open();
}
function delCate(catename,cateid,status,paramType,verId){
	if(status=='0'){
		Wg.alert(system.paramData.paramMgt.enableCate);
	}
	
	var p = {
			paramType:paramType,
			cateId:cateid,
			status:status,
			verId:verId
			};
	Ext.apply(p,{logger:system.paramData.paramCateMgt.del.logger + catename});
	Wg.confirm(system.paramData.paramCateMgt.del.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId + '06', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					query();
				}
			});
		});
	});
}