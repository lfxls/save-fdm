loadProperties('sysModule', 'sysModule_paramData');
var menuId = '54200';
var delOpt='03';
var grid_url = ctx + '/system/paramData/paramMgt!query.do';
var paramGrid = '';
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
		if($F('paramType')=="2"){
			url += String.format('<a class="edit" title="\{3}\" href="javascript:initEditParam(\'{0}\',\'{1}\',\'{2}\',\'{4}\');"></a>',
					record.data.TIID,record.data.TINAME,record.data.SORTNUM,system.paramData.paramMgt.grid.col.edit,"");
			if(record.data.STATUSVALUE=="1"){
			url += String.format('<a class="del" title="\{2}\" href="javascript:delParam(\'{0}\',\'{1}\');"></a>',
					record.data.TIID,record.data.TINAME,system.paramData.paramMgt.grid.col.del);
			}
		}
		else{
			url += String.format('<a class="edit" title="\{3}\" href="javascript:initEditParam(\'{0}\',\'{1}\',\'{2}\',\'{4}\');"></a>',
					record.data.ITEM_ID,record.data.ITEM_NAME,record.data.SORTNUM,system.paramData.paramMgt.grid.col.edit,record.data.BUSS_TYPE);
		if(record.data.STATUSVALUE=="1"){
			url += String.format('<a class="del" title="\{2}\" href="javascript:delParam(\'{0}\',\'{1}\');"></a>',
					record.data.ITEM_ID,record.data.ITEM_NAME,system.paramData.paramMgt.grid.col.del);
		}
		}
		return url;
	}
	},{
		header : system.paramData.paramMgt.grid.col.item_id,
		dataIndex : 'ITEM_ID',
		width : 180,
		locked:true,
		sortable : true
	},{
		header : system.paramData.paramMgt.grid.col.item_name,
		dataIndex : 'ITEM_NAME',
		width : 160
	},{
		header : system.paramData.paramMgt.grid.col.test_item_id,
		dataIndex : 'TIID',
		width : 180,
		locked:true,
		sortable : true
	},{
		header : system.paramData.paramMgt.grid.col.test_item_name,
		dataIndex : 'TINAME',
		width : 180,
		locked:true,
		sortable : true,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openTestItem(\'{0}\');">'+record.data.TINAME+'</a>',
					record.data.TIID);
			return url;
		}
	},{
		header : system.paramData.paramMgt.grid.col.value,
		dataIndex : 'VALUE',
		width : 120
	},{
		header : system.paramData.paramMgt.grid.col.level,
		dataIndex : 'LEVEL',
		width : 120
	},{
		header :common.kw.other.Status,
		dataIndex : 'STATUS',
		width : 120
	},{
		header : system.paramData.paramMgt.grid.col.scale,
		dataIndex : 'SCALE',
		width : 120,
	}, {
		header : system.paramData.paramMgt.grid.col.unit,
		dataIndex : 'UNIT',
		width : 120
	}, {
		header : system.paramData.paramMgt.grid.col.sort,
		dataIndex : 'SORTNUM',
		width : 120
	},{
		dataIndex : 'BUSS_TYPE',
		hidden:true,
	},{
		dataIndex : 'STATUSVALUE',
		hidden:true
	}];
	var toolbar = [{
        id: 'add-buton',
		text:system.paramData.paramMgt.grid.tb.add,
        tooltip:system.paramData.paramMgt.grid.tb.add,
        iconCls:'add',
        handler: function(){
        	initAddParam();
        }
    }];
	paramGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : system.paramData.paramMgt.grid.title,
		heightPercent : 0.80,
		cModel : _cm,
		excelUrl : grid_url,
		tbar:toolbar,
//		excelParam : ['nodeId','nodeType','yhlx','yhzt']
	});
	var p=$FF('queryForm');
	paramGrid.init(p, false, false);
	showCol();
	//showLabel();
	
});

function query(){
	var p=$FF('queryForm');
	paramGrid.reload(p);
	 showCol();
}
//参数分类暂时隐藏不用
//function showLabel(){
//	var p=$FF('queryForm');
//	$('cateId').value="";
//	dwrAction.doAjax("paramMgtAction","ChangeCate",p , function(res) {
//		if (res.success) {
//			var ul=document.getElementById("cateLs"); 
//			var arrLI = Ext.query("li");
//			for(var i = 0; i < arrLI.length; i++){ 
//				ul.removeChild(arrLI[i]);
//			}
//			var cateLs=Ext.decode(res.msg);
//			for(var i=0;i<cateLs.length;i++){
//				var obj=document.createElement("li"); 
//				obj.id=cateLs[i].BM+"li";
//				if(i==0){
//					$('cateId').value=cateLs[0].BM;
//					obj.className = 'ui-tab-item ui-tab-item-current';	
//				}
//				else{
//					obj.className="ui-tab-item";
//				}
//				obj.innerHTML="<a href='javascript:changeCate("+cateLs[i].BM+")'>"+cateLs[i].MC+"</a>"; 
//				ul.appendChild(obj); 
//			}
//			showCol();
//			query();
//		}
//	});
//}
function initEditParam(item_id,item_name,sortNum,buss_type){
	var paramType=$F('paramType');
	var verId=$F('verId');
	var bussType="1";
	if(buss_type==null||buss_type==""){
		 bussType="0";
	}
	else if(buss_type=="FROZEN_MONTH_BDATE"){
		bussType="2";
	}
	var url;
	if(paramType=="2"){
		url = String.format(ctx + '/system/paramData/paramMgt!initParam.do?testId={0}&paramType={1}&testName={2}&verId={3}&sortNum={4}&czid={5}&bussType={6}',$En(item_id),paramType,item_name,verId,sortNum,'02',bussType);

	}
	else{
		 url = String.format(ctx + '/system/paramData/paramMgt!initParam.do?itemId={0}&paramType={1}&itemName={2}&verId={3}&sortNum={4}&czid={5}&bussType={6}',$En(item_id),paramType,item_name,verId,sortNum,'02',bussType);
	}
	
	var baseParam = {
			url : url,
			title : system.paramData.paramMgt.edit.wh,
			el : 'edit',
			width : 600,
			height : 400,
			draggable : true
		};
	EditWin = new Wg.window(baseParam);
	EditWin.open();
	
}
function initAddParam(){
	var paramType=$F('paramType');
	var verId=$F('verId');
	if($E(verId)){
		Wg.alert(system.paramData.paramMgt.valid.verId);
		return false;
	}
	
	var url = String.format(ctx + '/system/paramData/paramMgt!initParam.do?paramType={0}&verId={1}&czid={2}',paramType,verId,'01');
	
	var baseParam = {
			url : url,
			title : system.paramData.paramMgt.add.wh,
			el : 'add',
			width : 800,
			height : 500,
			draggable : true
		};
	EditWin = new Wg.window(baseParam);
	EditWin.open();
	
}


function delParam(itemId,item_name){
	var p ;

	if($F('paramType')==2){
		p={
				testId:itemId,
				verId:$F('verId'),
				paramType:$F('paramType')	
		};
	}
	else{
		p= {
				itemId: itemId,
				verId:$F('verId'),
				paramType:$F('paramType')
			};
	}
	
	Ext.apply(p,{logger:system.paramData.paramMgt.del.logger + item_name});
	Wg.confirm(system.paramData.paramMgt.del.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId + delOpt, p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					query();
				}
			});
		});
	});
}

function cateMgt(){
	var url = String.format(ctx + '/system/paramData/paramMgt!initCate.do?verId={0}&paramType={1}',$F('verId'),	$F('paramType'));
	top.openpageOnTree(system.paramData.paramMgt.cate.wh,"",system.paramData.paramMgt.cate.wh,null,url,'yes',1);
	//showLabel();
}

function initPageSet() {
	var xlx = $F('cateId');
	if(!$E(xlx)) {
		// 清除元素背景色
		var arrLI = Ext.query("li");
		for(var i = 0; i < arrLI.length; i++){ 
			$(arrLI[i].id).className = 'ui-tab-item';
		}
		
		// 设置选定TAB背景色
		for(var i = 0; i < arrLI.length; i++){
			if((xlx+'li') == arrLI[i].id){
				$(arrLI[i].id).className = 'ui-tab-item ui-tab-item-current';	
			}
		}
	}
}

function changepmType(){
	var url = String.format(ctx	+ '/system/paramData/paramMgt!init.do?paramType={0}&verId={1}', $F('paramType'),$F('verId'));
	location.href = url;
}
function showCol(){
	var paramType=$F('paramType');
	paramGrid.showCol(1);
	paramGrid.showCol(2);
	paramGrid.hideCol(3);
	paramGrid.hideCol(4);
	paramGrid.hideCol(5);
	paramGrid.hideCol(6);
	paramGrid.showCol(8);
	paramGrid.showCol(9);
	if(paramType=='1'){
		paramGrid.showCol(5);
	}
	if(paramType=='2'){
		paramGrid.hideCol(1);
		paramGrid.hideCol(2);
		paramGrid.showCol(4);
		paramGrid.hideCol(5);
		paramGrid.showCol(6);
		paramGrid.hideCol(8);
		paramGrid.hideCol(9);
	}
}

function openTestItem(tiid){
	var verId=$F('verId');
	var url = String.format(ctx +  '/system/paramData/paramMgt!initTestDetail.do?testId={0}&verId={1}',$En(tiid),$En(verId));
	var baseParam = {
		url : url,
		title :system.paramData.paramMgt.testDetail.grid.title,
		el : 'testGrid',
		width : 750,
		height : 400,
		draggable : true
	};
	DetailWin = new Wg.window(baseParam);
	DetailWin.open();	
}