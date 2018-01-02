var menuId = '52100';
var grid_url = ctx + '/system/qxgl/czygl!queryDetail.do';
var gridDetail_url = ctx + '/system/qxgl/czygl!getAuthorizedPosList.do';
var h1 = 0.5;
var listGrid = null;
var listDetailGrid = null;
var dwWin = null;
Ext.onReady(function() {
	hideLeft();
	
	//数据列
	var listCm = [{
		header : czygl_posAuth_list_op,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title='+czygl_posAuth_list_edit+' href="javascript:authPos(\'{0}\',\'{1}\');"/>', record.data.POSID,record.data.P_DEPTID);
			return url;
		}
	},{
		dataIndex : 'POSID',
		hidden : true
	},{
		dataIndex : 'P_DEPTID',
		hidden : true
	},{
		header : czygl_posAuth_list_posName,
		dataIndex : 'POSNAME',
		width : 120
	},{
		id:"DWMC",
		header : czygl_posAuth_list_dwmc,
		dataIndex : 'DWMC',
		width : 120
	}];
	
	//列表
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize: 30,
		title : czygl_posAuth_list_title1,
		height : 400,
		width:Ext.getBody().getWidth()*0.55,
		stripeRows:true,
		autoExpandColumn:'DWMC',
		cModel : listCm
	});
	
	var czyid = $F('czyid');
	var p = {
		czyid : czyid
	};
	listGrid.init(p);
	
	//数据列
	var listDetailCm = [{
		header : czygl_posAuth_list_op,
		dataIndex : 'OP',
		width : 55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="del" title='+czygl_posAuth_list_edit2+' href="javascript:cancelAuthPos(\'{0}\');"/>', record.data.POSID);
			return url;
		}
	},{
		dataIndex : 'POSID',
		hidden : true
	},{
		header : czygl_posAuth_list_posName,
		dataIndex : 'POSNAME',
		width : 120
	},{
		id:'DWMC',
		header : czygl_posAuth_list_dwmc,
		dataIndex : 'DWMC',
		width : 120
	}];
	
	//列表
	listDetailGrid = new Wg.Grid( {
		url : gridDetail_url,
		el : 'listDetailGrid',
		pageSize: 100,
		title : czygl_posAuth_list_title2,
		height : 400,
		width:Ext.getBody().getWidth()*0.41,
		stripeRows:true,
		pageNotSupport:true,
		autoExpandColumn:'DWMC',
		cModel : listDetailCm
	});
	
	listDetailGrid.init(p);
});

//树
function getDwTree(treeType) {
	var title = czygl_posAuth_list_dwTreeTitle;
	
	var url = String.format(
			ctx + '/system/qxgl/czygl!initDwTree.do?treeType={0}',treeType);
	var baseParam = {
		url : url,
		title : title,
		el : 'treeDiv',
		width : 300,
		height : 420,
		draggable : true
	};
	dwWin = new Wg.window(baseParam);
	dwWin.open();
}

// 查询售电点
function queryPos() {
	reloadGrid();
}

//售电点授权
function authPos(posid,dwdm) {
	var czyid = $('czyid').value;
	var p = {
		czyid : czyid,
		posid:posid,
		dwdm:dwdm,
		type:'pos',
		logger : czygl_posAuth_logger_value1 + czyid+czygl_posAuth_logger_value2+posid+czygl_posAuth_logger_value3
	};

	Wg.confirm(czygl_posAuth_confirm1, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}


//售电点授权取消
function cancelAuthPos(posid) {
	var czyid = $('czyid').value;
	var p = {
		czyid : czyid,
		posid:posid,
		type:'pos',
		logger : czygl_posAuth_cancel_logger_value1 + czyid+czygl_posAuth_cancel_logger_value2+posid+czygl_posAuth_cancel_logger_value3
	};

	Wg.confirm(czygl_posAuth_confirm2, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '03', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}

function reloadGrid(){
	var czyid = $F('czyid');
	var dwdm = $F('dwdm');
	var posName = $F('posName');
	var p = {
		czyid : czyid,
		dwdm:dwdm,
		posName:posName
	};
	listGrid.reload(p);
	listDetailGrid.reload(p);
}

function clearInput(){
	$('dwdm').value = '';
	$('dwmc').value = '';
	$('posName').value = '';
}