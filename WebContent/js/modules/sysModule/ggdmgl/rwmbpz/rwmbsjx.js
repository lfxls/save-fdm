var menuId = '54400';
//待选数据项
var dxsjxGrid="";
//任务数据项
var rwsjxGrid="";
var dxsjxGrid_url = ctx + '/system/ggdmgl/rwmbpz!queryDxsjx.do';
var rwsjxGrid_url = ctx + '/system/ggdmgl/rwmbpz!queryRwsjx.do';

Ext.onReady(function(){
	hideLeft();
	//待选数据项
	var dxsjxCm = [ {
		header: dxsjxCm_operate,
		dataIndex:'OP',
		width:55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title='+dxsjxCm_new_title+'  href="javascript:addItem(\'{0}\',\'{1}\');"></a>',
							record.data.SJXBM,record.data.ARRAY_STRUCT_ITEM);
			return url;
		}
	},{
		header : dxsjxCm_sjxbm,
		dataIndex : 'SJXBM',
		width : 130,
		sortable : true
	},{
		header : dxsjxCm_sjxmc,
		dataIndex : 'SJXMC',
		width :210,
		sortable : true
	},{
		header : dxsjxCm_zgy,
		dataIndex : 'DLMS_SUB_NAME',
		width :110,
		sortable : true
	},{
		header : dxsjxCm_jgtnr,
		dataIndex : 'ARRAY_STRUCT_ITEM',
		width :330,
		sortable : true
	}];
	
	//任务数据项
	var rwsjxCm = [{
		header: dxsjxCm_operate,
		dataIndex:'OP',
		width:55,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="del" title='+rwsjxCm_del_title+'  href="javascript:delItem(\'{0}\',\'{1}\',\'{2}\',\'{3}\');"></a>',
							record.data.SJXBM,record.data.ARRAY_STRUCT_ITEM,record.data.DYSJB,record.data.DYZD);
			return url;
		}
	},{
		header : dxsjxCm_sjxbm,
		dataIndex : 'SJXBM',
		width : 130,
		sortable : true
	},{
		header : dxsjxCm_sjxmc,
		dataIndex : 'SJXMC',
		width :210,
		sortable : true
	},{
		header : dxsjxCm_zgy,
		dataIndex : 'DLMS_SUB_NAME',
		width :110,
		sortable : true
	},{
		header : dxsjxCm_jgtnr,
		dataIndex : 'ARRAY_STRUCT_ITEM',
		width :250,
		sortable : true
	},{
		header : rwsjxCm_dysjb,
		dataIndex : 'DYSJB',
		width :100,
		sortable : true
	},{
		header : rwsjxCm_dyzd,
		dataIndex : 'DYZD',
		width :80,
		sortable : true
	},{
		header : rwsjxCm_xh,
		dataIndex : 'XH',
		width :50,
		sortable : true
	}];

	dxsjxGrid = new Wg.Grid( {
		url : dxsjxGrid_url,
		el : 'dxsjxGrid',
		frame: true,
		title : dxsjxCm_title,
		heightPercent : 0.4,
		cModel : dxsjxCm
	});
	dxsjxGrid.init({});
	
	var toolbar = [{
        id: 'add-buton',
		text:dxsjxCm_toolbar_new,
        tooltip:dxsjxCm_toolbar_new,
        iconCls:'edit',
        handler: function(){
        	configSjx();
        }
    }];
	
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	rwsjxGrid_url = rwsjxGrid_url+'?rwsx='+rwsx+'&zdgylx='+zdgylx;
	rwsjxGrid = new Wg.Grid( {
		url : rwsjxGrid_url,
		el : 'rwsjxGrid',
		frame: true,
		title : rwsjxCm_title,
		tbar:toolbar,
		heightPercent : 0.45,
		cModel : rwsjxCm
	});
	
	rwsjxGrid.init({});
});

//根据查询条件从新加载两个grid
function query(){
	var sjxbm = $F('sjxbm');
	var sjxmc = $F('sjxmc');
	var p = {
		sjxbm:sjxbm,
		sjxmc:sjxmc
	};
	
	//加载gird
	dxsjxGrid.reload(p);
}

function configSjx(){
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	
	var url = String.format(ctx +  '/system/ggdmgl/rwmbpz!showSjxzdWin.do?rwsx={0}&zdgylx={1}',rwsx, zdgylx);
	var baseParam = {
			url : url,
			title : configSjxWin_title,
			el : 'configSjx',
			width : 650,
			height : 520,
			draggable : true,
			closeBtnShow:false
		};
	configSjxWin = new Wg.window(baseParam);
	configSjxWin.open();
}
/**
 * 新增任务数据项
 * @param sjxbm
 * @param struct
 * @returns
 */
function addItem(sjxbm,struct){
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	var sjxsx = struct!=''?'02':'01';
	
	var p = {
		rwsx:rwsx,
		zdgylx:zdgylx,
		sjxsx:sjxsx,
		sjxbm:sjxbm
	};
	Wg.confirm(addItem_Confirm, function() {	
		dwrAction.doDbWorks('rwmbpzAction', menuId + '01', p, function(res) {
			 Wg.alert(res.msg, function(){
				 if(res.success) {
					 rwsjxGrid.reload();
				 }
			 });
		});
	});
}

/**
 * 删除数据项
 */
function delItem(sjxbm,struct,dysjb,dyzd){
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	
	var p = {
		rwsx:rwsx,
		zdgylx:zdgylx,
		sjxbm:sjxbm,
		struct:struct,
		dysjb:dysjb,
		dyzd:dyzd,
		logger :delItem_logger1+ zdgylx + delItem_logger2 + rwsx+delItem_logger3+sjxbm+delItem_logger4
	};
	Wg.confirm(delItem_Confirm, function() {	
		dwrAction.doDbWorks('rwmbpzAction', menuId + '03', p, function(res) {
				 Wg.alert(res.msg, function(){
					 if(res.success) {
						 rwsjxGrid.reload();
					 }
				 });
			});
	});
}