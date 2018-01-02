var menuId = '12300';
var woGrid = '';
var grid_url = ctx + '/main/insMgt/insOrder!query.do';
Ext.onReady(function() {
	handlerType = 'query';
	var _cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			//工单状态为未处理或已分配或已撤销的情况下可以编辑和删除
			if(record.data.STATUS == '0' || record.data.STATUS == '1' || record.data.STATUS == '4') {
				url += String.format('<a class="edit" title="\{6}\" href="javascript:doEdit(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');"></a>',
						record.data.WOID, record.data.STATUS, record.data.POPID, record.data.POPNAME, record.data.FTIME, record.data.OTYPE,main.insMgt.plan.grid.op.edit);
				url += String.format('<a class="del" title="\{2}\" href="javascript:doDel(\'{0}\',\'{1}\');"></a>',
						record.data.WOID, record.data.STATUS, main.insMgt.plan.grid.op.del);
			} else {
				url += String.format('<a class="soubiao" title="\{5}\" href="javascript:showWO(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
						record.data.WOID, record.data.POPID,record.data.POPNAME,record.data.FTIME,record.data.OTYPE,main.insMgt.plan.grid.op.show);
				if(record.data.STATUS == '2') {//工单状态为已派工
					url += String.format('<a class="doback" title="\{1}\" href="javascript:doRevok(\'{0}\');"></a>',
							record.data.WOID, main.insMgt.order.grid.op.doRevoke);
				}
			}
			return url;
		}
	},{
		header : common.kw.workorder.WOID,
		dataIndex : 'WOID',
		width : 130,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:queryWO(\'{0}\');">'+record.data.WOID+'</a>&nbsp;&nbsp;',
					record.data.WOID);
			return url;
		}
	},{
		header: common.kw.other.Status,
		dataIndex : 'SNAME',
		width : 100
	}/*,{
		header: main.insMgt.order.grid.col.rsp,
		dataIndex : 'RSP',
		align : 'center',
		width : 150
	}*/,{
		header: main.insMgt.order.grid.col.pop,
		dataIndex : 'POPNAME',
		width : 120
	},{
		header: main.insMgt.order.grid.col.createDate,
		dataIndex: 'CDATE',
		width: 150
	},{
		header: main.insMgt.order.grid.col.finishTime,
		dataIndex: 'FTIME',
		width: 150
	},{
		header: main.insMgt.order.grid.col.cop,
		dataIndex: 'COPTNAME',
		width : 120
	},{
		header: main.insMgt.order.grid.col.type,
		dataIndex: 'OTNAME',
		width : 120
	},{
		dataIndex: 'OTYPE',
		hidden : true,
		width: 100
	},{
		dataIndex: 'STATUS',
		hidden : true,
		width: 100
	},{
		dataIndex: 'POPID',
		hidden : true,
		width: 100
	}];
	
	var toolbar = [{
	        id: 'cInsWO',
			text:main.insMgt.order.grid.tb.cInsWO,
	        tooltip:main.insMgt.order.grid.tb.cInsWO,
	        iconCls:'add',
	        handler: function(){
	        	cInsWO();
	        }
		}];
	
	if('CameroonEneo'==$F('helpDocAreaId')){
		toolbar.push({
	        id: 'cSurWO',
	        text:main.insMgt.order.grid.tb.cSurWo,
	        tooltip:main.insMgt.order.grid.tb.cSurWo,
	        iconCls:'add',
	        handler: function(){
	        	cSurWO();
	        }
		})
	}
	 woGrid = new Wg.Grid( {
		url : grid_url,
		el : 'woGrid',
		title : main.insMgt.order.grid.title,
		heightPercent : 0.7,
		widthPercent : 1,
		margin : 60,
		cModel : _cm,
		stripeRows : true,
		tbar:toolbar
	});
	 
	var p = $FF('queryWOForm');
	woGrid.init(p);
});

//查询
function query()
{
//	if(!$E($F('woid'))) {
//		if(!/^\d+$/.test($F('woid'))){
//			Wg.alert(main.insMgt.order.valid.workOrderNo.formatCheck);
//			return;
//		}
//	}
	var p = $FF('queryWOForm');
	woGrid.reload(p);
}

//工单状态下拉
function changeStatus(){
	query();
}
//工单类型下拉
function changeType(){
	query();
}
//生成安装工单
function cInsWO(){
	var url = String.format(ctx + '/main/insMgt/insOrder!initCInsWO.do?operateType={0}','01');
	top.openpageOnTree(main.insMgt.order.win.title.createWO, '123001', main.insMgt.order.win.title.createWO, null, url, 'yes', 1);
}

//生成勘察工单
function cSurWO(){
	var url = String.format(ctx + '/main/insMgt/insOrder!initCInsWO.do?operateType={0}&oType={1}','01','3');
	top.openpageOnTree(main.insMgt.order.win.title.createWO, '123001', main.insMgt.order.win.title.createWO, null, url, 'yes', 1);
}

//删除
function doDel(woid, status){
//	if(status != '0'){
//		Wg.alert(main.insMgt.order.valid.wo.notDel);
//		return;
//	}
	
	var param = {
		woid : woid
	};
	
	Ext.apply(param, {
		logger : main.insMgt.order.log.del.workOrder
	});
    Wg.confirm(main.insMgt.order.hint.confirmDel, function(){
    	dwrAction.doDbWorks("insOrderAction", menuId + delOpt, param, function(re){
       		Wg.alert(re.msg,function(){
       			query();
       		}); 
        });
    });  
}

//编辑
function doEdit(woid, status, popid, popName,fTime,oType){
//	if(status != '0'){
//		Wg.alert(main.insMgt.order.valid.wo.notEdit);
//		return;
//	}
	var url = String.format(ctx + '/main/insMgt/insOrder!initEditInsWO.do?woid={0}&operateType={1}&fTime={2}&oType={3}',
			woid,'02',fTime,oType);
	top.openpageOnTree(main.insMgt.order.win.title.editWO, '123001', main.insMgt.order.win.title.editWO, null, url, 'yes', 1);
}

//查看工单
function showWO(woid,popid,popName,fTime,oType){
	var url = String.format(ctx + '/main/insMgt/insOrder!initEditInsWO.do?woid={0}&operateType={1}&fTime={2}&oType={3}',
			woid,'04',fTime,oType);
	top.openpageOnTree(main.insMgt.order.win.title.woDetail, '123001', main.insMgt.order.win.title.woDetail, null, url, 'yes', 1);
}


//撤销工单
function doRevok(woid) {
	 Wg.confirm(main.insMgt.order.hint.confirmRev, function(){
		dwrAction.doAjax('insOrderAction','revokeWorkOrder',{woid:woid},function(res){
			if(res.success) {
				Wg.alert(main.insMgt.order.hint.revoke.success);
				query();
			}
		});
	 });
}

function queryPop() {
	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.pop,
		el : 'selPOP',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

function fbReset() {
	$('woid').value = '';
	$('status').value = '';
	$('popName').value = '';
	$('popid').value = '';
	$('startDate').value = '';
	$('endDate').value = '';
}
