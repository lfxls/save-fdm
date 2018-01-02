loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11100';
var grid_url = ctx + '/main/arcMgt/custMgt!query.do';
var CustGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var _cm = [
{
	header : main.arcMgt.custMgt.grid.col.op,
	dataIndex : 'OP',
	width : 80,
	locked:true,
	renderer : function(value, cellmeta, record) {
		var url="";
		url += String.format('<a class="edit" title="\{1}\" href="javascript:initEditCust(\'{0}\');"></a>',
				record.data.CNO,main.arcMgt.custMgt.grid.col.edit);
		if(record.data.STATUS!='1'){
			url += String.format('<a class="del" title="\{1}\" href="javascript:delCust(\'{0}\');"></a>',
					record.data.CNO,main.arcMgt.custMgt.grid.col.del);
		}
		return url;
	}
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120,
		locked:true,
		sortable : true
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	},{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120
	},{
		dataIndex : 'UID',
		hidden:true
	},{
		dataIndex : 'STATUS',
		hidden:true
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		header :  main.arcMgt.custMgt.grid.col.custStatus,
		dataIndex : 'CUSTSTATUS',
		width : 120
	},{
		header :  main.arcMgt.custMgt.grid.col.dataSrc,
		dataIndex : 'DATASRC',
		width : 120
	},{
		header : main.arcMgt.custMgt.grid.col.billing_date,
		dataIndex : 'BILLING_DATE',
		width : 120,
	}, {
		header : common.kw.other.Telephone,
		dataIndex : 'PHONE',
		width : 120
	}];
	var toolbar = [{
        id: 'add-buton',
		text:main.arcMgt.custMgt.tb.add,
        tooltip:main.arcMgt.custMgt.tb.add,
        iconCls:'add',
        handler: function(){
        	initAddCust();
        }
    },{
        id: 'import-buton',
		text:main.arcMgt.custMgt.tb.imp,
        tooltip:main.arcMgt.custMgt.tb.imp,
        iconCls:'import',
        handler: function(){
        	initImport();
        }
    }];
	CustGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : main.arcMgt.custMgt.grid.title,
		heightPercent : 0.80,
//		width:Ext.getBody().getWidth()*0.97,
		cModel : _cm,
		//excelUrl : grid_url,
		tbar:toolbar,
//		excelParam : ['nodeId','nodeType','yhlx','yhzt']
	});
	CustGrid.init({});
	
});
	function query(){
		if($E($F('nodeId'))){
			Wg.alert(main.arcMgt.custMgt.valid.nvl.node);
			return false;
		}
		var p = $FF('queryForm');
		CustGrid.reload(p);
	}
	function initAddCust(){
		var url = String.format(ctx + '/main/arcMgt/custMgt!initCust.do?');
		top.openpageOnTree(main.arcMgt.custMgt.add.wh,menuId+"01",main.arcMgt.custMgt.add.wh,null,url,'yes',1);
	
	}
	function initImport(){
		var url = String.format(ctx + "/main/arcMgt/custMgt!initImport.do");
		var baseParam = {
				url : url,
				title : main.arcMgt.custMgt.imp.wh,
				el : 'import',
				width : 700,
				height : 400,
				draggable : true
			};
		ImpWin = new Wg.window(baseParam);
		ImpWin.open();
	}
	function initEditCust(CNO){
		
		var url = String.format(
		ctx + '/main/arcMgt/custMgt!initCust.do?cno={0}&czid={1}',$En(CNO),'02');
		top.openpageOnTree(main.arcMgt.custMgt.edit.wh,menuId+"01",main.arcMgt.custMgt.edit.wh,null,url,'yes',1);
	}
	
	function delCust(CNO){
		var czid = '03';
		var p = {cno: CNO};
		Ext.apply(p,{logger:main.arcMgt.custMgt.del.logger + CNO});
		Wg.confirm(main.arcMgt.custMgt.del.confirm,function(){
			dwrAction.doDbWorks('custMgtAction',menuId + czid, p, function(res){
				Wg.alert(res.msg,function(){
					if(res.success) {
						query();
					}
				});
			});
		});
	}function Reset(){
		$('nodeText').value="";
		$('nodeId').value="";
		$('cno').value="";
		$('cname').value="";
		$('custStatus').value="";
		$('dataSrc').value="";
	}
	// 各页面自定义此方法(过滤节点)
	function checkNode(_node) {
		var nodeType = _node.attributes.ndType;
		//yaoj 2015.1.06 ban "Group" node
		if(nodeType == 'bj'){
			Wg.alert(main.arcMgt.custMgt.valid.nvl.node);
			return false;
		}
		else{
			
		$('nodeDwdm').value = _node.attributes.dwdm;
		$('nodeText').value = _node.attributes.text;
		$('nodeId').value = _node.id;
		$('nodeType').value = _node.attributes.ndType;

		var p = $FF('queryForm');
		CustGrid.reload(p);
		return true;
		}
	}