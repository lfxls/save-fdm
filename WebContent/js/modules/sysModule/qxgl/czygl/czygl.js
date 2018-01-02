var menuId = '52100';
var grid_url = ctx + '/system/qxgl/czygl!query.do';
var czyzt = '';

var h1 = 0.5;
var listGrid = null;
Ext.onReady(function() {
	hideLeft();
	
//	var prepayMode = $('prepayMode').value;
//	var usePosMach = $('usePosMach').value;
	
	//用户信息数据列
	var listCm = [{
		header : czygl_posAuth_list_op,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title='+czygl_czy_list_edit_hint+' href="javascript:editCzy(\'{0}\',\'{1}\');"></a>', 
					record.data.CZYID, record.data.TNAME);
			url += String.format('<a class="del" title='+czygl_czy_list_del_hint+' href="javascript:delCzy(\'{0}\');"></a>', record.data.CZYID);
			if(record.data.CZYZT == '2') {
				url += String.format('<a class="unlock" title='+czygl_czy_list_unlock_hint+' href="javascript:unlockCzy(\'{0}\');"></a>', record.data.CZYID);
			}
			/*
			CDU和POS机授权不在操作员管理里面维护 2014-02-19 edit by jun
			//预付费模式并且非小型预付费显示售电点授权
			if(prepayMode=='true' && sysMode=='0'){
				url += String.format('<a class="posStation" title="{0}" href="javascript:posAuthorize(\'{1}\');"/>', czygl_czy_list_auth_hint,record.data.CZYID);
			}
			//预付费模式并且使用POS机显示POS机授权
			if(prepayMode=='true' && usePosMach=='true'){
				url += String.format('<a class="posm" title="{0}" href="javascript:posMachineAuthorize(\'{1}\');"/>',czygl_czy_list_posauth_hint, record.data.CZYID);
			}*/
			return url;
		}
	},{
		header : czygl_czy_list_czyid,
		dataIndex : 'CZYID',
		width : 80
	},{
		header : czygl_czy_list_xm,
		dataIndex : 'XM',
		width : 80
	},{
		header : czygl_czy_list_bmmc,
		dataIndex : 'BMMC',
		width : 120
	},{
		header : czygl_czy_list_dwmc,
		dataIndex : 'DWMC',
		width : 120,
		renderer:function(v,p){  
           p.attr =  'ext:qtitle='+czygl_czy_list_dwPath;  
           p.attr += ' ext:qtip="'  + v + '"';  
           return v;  
        }
	},{
		header : czygl_czy_list_tname,
		dataIndex : 'TNAME',
		width : 120
	},{
		header : czygl_czy_list_sjhm,
		dataIndex : 'SJHM',
		width : 100
	},{
		header : czygl_czy_list_dhhm,
		dataIndex : 'DHHM',
		width : 100
	},{
		header : czygl_czy_list_yxdz,
		dataIndex : 'YXDZ',
		width : 100
	},{
		header : czygl_czy_list_zt,
		dataIndex : 'ZT',
		width : 80
	},{
		header : czygl_czy_list_cjrq,
		dataIndex : 'CJRQ',
		width : 80
	},{
		dataIndex : 'CZYZT',
		hidden : true
	},{
		dataIndex : 'MMCWCS',
		hidden : true
	}];
	
	
	var toolbar = null;
	toolbar = [{
        id: 'add-buton',
		text:czygl_czy_list_add_hint,
        tooltip:czygl_czy_list_add_hint,
        iconCls:'add',
        handler: function(){
        	addCzy();
        }
    }];
	
	//税费信息列表
	listGrid = new Wg.Grid( {
		url : grid_url,
		el : 'listGrid',
		pageSize: 30,
		title : czygl_czy_list_title,
		heightPercent:0.80,
		widthPercent : 1,
		margin : 60,
		tbar:toolbar,
		cModel : listCm
	});
	
	var dwdm = $F('dwdm');
	var bmid = $F('bmid');
	var p = {
		dwdm:dwdm,
		bmid:bmid
	};
	//listGrid.init(p, true, false);
	listGrid.init(p);
	
	//单选事件
	//listGrid.grid.addListener('rowclick', rowclickFn);
});

/*function rowclickFn(grid, rowindex, e) {
	grid.getSelectionModel().each(function(rec) {
		czyzt = rec.get('CZYZT');
	});
}*/

//树
function getBmTree(selType) {
	var title = czygl_czy_bmTreeTitle;
	var url = String.format(ctx + '/system/qxgl/czygl!initBmTree.do?selType={0}', selType);
	var baseParam = {
		url : url,
		title : title,
		el : 'treeDiv',
		width : 320,
		height : 450,
		draggable : true
	};
	YhWin = new Wg.window(baseParam);
	YhWin.open();
}
// 查询操作员
function queryCzy() {
	reloadGrid();
}

// 新增操作员
function addCzy() {
	var url = ctx + String.format('/system/qxgl/czygl!initEditCzy.do?operateType={0}', 'add');
	var baseParam = {
		url : url,
		title : czygl_czy_list_addWinTitle,
		el : 'addCzy',
		width :900,
		height : 650,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}

//修改操作员
function editCzy(czyid,tname) {
	/*console.log(tname);*/
	var url = ctx + String.format('/system/qxgl/czygl!initEditCzy.do?operateType={0}&czyid={1}&tname={2}', 'edit', czyid, $En(tname));
	    /*url=encodeURI(url); */
	   
	var baseParam = {
		url : url,
		title : czygl_czy_list_editWinTitle,
		el : 'editCzy',
		width :900,
		height : 650,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}

// 删除操作员
function delCzy(czyid) {
	if ($E(czyid)) {
		Wg.alert(czygl_upd_null_id);
		return;
	}

	if('0' == czyzt){
		Wg.alert(czygl_del_able);
		return;
	}
	
	var p = {
		czyid : czyid,
		type:'czy',
		logger : czygl_del_logger + czyid
	};

	Wg.confirm(czygl_del_confirm, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '03', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}

//解锁操作员
function unlockCzy(czyid) {
	if ($E(czyid)) {
		Wg.alert(czygl_unlock_null_id);
		return;
	}
	
	var p = {
		czyid : czyid,
		type:'czy',
		logger : czygl_unlock_logger + czyid
	};

	Wg.confirm(czygl_unlock_confirm, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '05', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					reloadGrid();
				}
			});
		});
	});
}

function reloadGrid(){
	var qczyid = $F('qczyid');
	var qxm = $F('qxm');
	var dwdm = $F('dwdm');
	var bmid = $F('bmid');
	var zt = $('zt').value;
	var tno = $F('tno');
	var p = {
		czyid : qczyid,
		xm : qxm,
		dwdm : dwdm,
		bmid : bmid,
		zt : zt,
		tno : tno
	};
	listGrid.reload(p);
}

//reset按钮
function clearInput(){
	$('dwdm').value = '';
	$('bmmc').value = '';
	$('tname').value = '';
	$('tno').value = '';
	$('qczyid').value = '';
	$('qxm').value = '';
	$('zt').value = '';
	//reset时，除了把界面上的输入项情况外，还需要把隐藏域中的bmid的值也清空
	$('bmid').value = '';
}

//售电点授权
function posAuthorize(czyid) {
	var url = ctx +'/system/qxgl/czygl!initPosAuthorize.do?czyid='+czyid;
	var baseParam = {
		url : url,
		title : czygl_czy_list_authWinTitle,
		el : 'addPos',
		width :1000,
		height : 520,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}

//pos机授权
function posMachineAuthorize(czyid){
	var url = ctx +'/system/qxgl/czygl!initPosMachineAuthorize.do?czyid='+czyid;
	var baseParam = {
		url : url,
		title : czygl_czy_list_posMachineAuthWinTitle,
		el : 'addPosMachine',
		width :1000,
		height : 520,
		draggable : true
	};
	addWin = new Wg.window(baseParam);
	addWin.open();
}