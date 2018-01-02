var menuId = '52100';
var grid_url = ctx + '/system/qxgl/czygl!getPosMachineList.do';
var gridDetail_url = ctx + '/system/qxgl/czygl!getAuthorizedPosMachineList.do';
var h1 = 0.5;
var listGrid = null;
var listDetailGrid = null;
var dwWin = null;
Ext.onReady(function() {
	hideLeft();
	
	//数据列
	var listCm = [{
		header : czygl_posMachineAuth_list_operate,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title='+czygl_posMachineAuth_list_authorize+' href="javascript:authPos(\'{0}\');"/>', record.data.POSMID);
			return url;
		}
	},{
		header : czygl_posMachineAuth_list_posmid,
		dataIndex : 'POSMID',
		width : 120
	},{
		header : czygl_posMachineAuth_list_model,
		dataIndex : 'MODEL',
		width : 120
	},{
		header : czygl_posMachineAuth_list_posName,
		dataIndex : 'POSNAME',
		width : 120
	}];
	
	//列表
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize: 30,
		title : czygl_posMachineAuth_list_title1,
		height : 400,
		width:Ext.getBody().getWidth()*0.55,
		cModel : listCm
	});
	
	var czyid = $F('czyid');
	var p={
		departId :unitCode,
		czyid : czyid
	};
	listGrid.init(p);
	
	//数据列
	var listDetailCm = [{
		header : czygl_posMachineAuth_list_operate,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="del" title='+czygl_posMachineAuth_list_cancelAuthorize+' href="javascript:cancelAuthPos(\'{0}\');"/>', record.data.POSMID);
			return url;
		}
	},{
		header : czygl_posMachineAuth_list_posmid,
		dataIndex : 'POSMID',
		width : 120
	},{
		header : czygl_posMachineAuth_list_model,
		dataIndex : 'MODEL',
		width : 100
	},{
		header : czygl_posMachineAuth_list_posName,
		dataIndex : 'POSNAME',
		width : 110
	}];
	
	//列表
	listDetailGrid = new Wg.Grid( {
		url : gridDetail_url,
		el : 'listDetailGrid',
		pageSize: 100,
		title : czygl_posMachineAuth_list_title2,
		height : 400,
		width:Ext.getBody().getWidth()*0.41,
		stripeRows:true,
		pageNotSupport:true,
		cModel : listDetailCm
	});
	listDetailGrid.init({czyid : czyid});
});

// 查询pos机
function query() {
	reloadGrid();
}

//pos机授权
function authPos(posmid) {
	var czyid = $('czyid').value;
	var p = {
		czyid : czyid,
		posmid: posmid,
		type:'posMachine',
		logger :czygl_posMachineAuth_logger_value1+posmid+czygl_posMachineAuth_logger_value2+czyid
	};

	Wg.confirm(czygl_posMachineAuth_confirm1, function() {
		dwrAction.doDbWorks('czyglAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}


//pos机授权取消
function cancelAuthPos(posmid) {
	var czyid = $('czyid').value;
	var p = {
		czyid : czyid,
		posmid: posmid,
		type:'posMachine',
		logger : czygl_posMachineAuth_logger_value3+czyid+czygl_posMachineAuth_logger_value4+posmid
	};

	Wg.confirm(czygl_posMachineAuth_confirm2, function() {
		dwrAction.doDbWorks('czyglAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}

function getPosTree(){
	var url = ctx+'/jsp/prepay/posmgt/posTree.jsp';
	var baseParam = {
		url : url,
		title : czygl_posMachineAuth_tree_title,
		el:'posTree',
		width : 300,
		height : 360,
		draggable : true
	};
	posWin = new Wg.window(baseParam);
	posWin.open();
}

function reloadGrid(){
	var czyid = $F('czyid');
	var departId = $F('departId');
	var posId = $F('posId');
	var posmid = $F('posmid');
	if(departId=='' && posId==''){
		departId = unitCode;
	}
	var p = {
		czyid : czyid,
		posId:posId,
		departId:departId,
		posmid:posmid
	};
	listGrid.reload(p);
	listDetailGrid.reload(p);
}
