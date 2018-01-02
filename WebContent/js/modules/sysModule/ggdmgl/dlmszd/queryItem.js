var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var menuId = "54300";
var queryUrl = Module_URL + 'query.do' ;
var initDlmsParamsNormalUrl = Module_URL + 'initDlmsParamsNormal.do' ;
var initDlmsParamsStructUrl = Module_URL + 'initDlmsParamsStruct.do' ;

var structSplitor = "#";
var DATATYPESTRUCT = "array_struct";
var resource = systemModule_ggdmgl_dlmszd_queryItem.resourceBundle;
var itemGrid;
Ext.onReady(function(){
	var _cm = [ 
		{
			header : resource.Grid.cm.opt,
			dataIndex : 'OP',
			width : 60,
			renderer : function(value, cellmeta, record) {
				var editFunction = "editItem";
				if(record.data.ARRAY_STRUCT_LEN>0){//结构体
					editFunction = "editStructItem";
				} 
				var url = String.format('<a class="edit" title='+resource.Grid.cm.opt_edit+
						' href="javascript:' + editFunction + '(\'{0}\');"></a>',
						record.data.ITEM_ID);
				url += String.format('<a class="del" title='+resource.Grid.cm.opt_del+
						' href="javascript:delItem(\'{0}\' , \'{1}\');" /></a>',
						record.data.ITEM_ID ,
						record.data.CLASS_ID + structSplitor + record.data.OBIS + structSplitor + record.data.ATTRIBUTE_ID);
				return url;
			}
		}
		,{
			dataIndex : 'ITEM_ID',
			hidden : true
		}
		,{
			header : resource.Grid.cm.sjxmc,
			dataIndex : 'ITEM_NAME',
			width : 150
		}
		,{
			header : 'CLASS_ID',
			dataIndex : 'CLASS_ID',
			width : 80
		},{
			header : 'OBIS',
			dataIndex : 'OBIS',
			width : 100
		},{
			header : resource.Grid.cm.sx,
			dataIndex : 'ATTRIBUTE_ID',
			width : 50
		},{
			header :resource.Grid.cm.lg,
			dataIndex : 'SCALE',
			width : 50
		}
		,{
			dataIndex : 'DLMS_DATA_TYPE',
			hidden : true
		}
		,{
			header : resource.Grid.cm.sjlx,
			dataIndex : 'DLMS_DATA_TYPE_NAME',
			width : 80
		}
		,{
			dataIndex : 'CALLING_DATA_TYPE',
			hidden : true
		}
		,{
			header : resource.Grid.cm.dyfsjlx,
			dataIndex : 'CALLING_DATA_TYPE_NAME',
			width : 80
		}
		,{
			dataIndex : 'OPT_TYPE',
			hidden : true
		}
		,{
			header : resource.Grid.cm.czlx,
			dataIndex : 'OPT_TYPE_NAME',
			width : 80
		}
		,{
			header : resource.Grid.cm.jgtcd,
			dataIndex : 'ARRAY_STRUCT_LEN',
			width : 80
		}
		,{
			header : resource.Grid.cm.jgtnr,
			dataIndex : 'ARRAY_STRUCT_ITEM',
			width : 200
		}
		,{
			header : resource.Grid.cm.zdy,
			dataIndex : 'CUSTOMIZE_CLASS',
			width : 150
		}
	];
	var gridToolBar = [{
        id: 'add-buton',
		text:resource.Grid.button.add.text,
        tooltip:resource.Grid.button.add.tip,
        iconCls:'add',
        handler: function(){
			addItem();
        }
    }];
	
	itemGrid = new Wg.Grid( {
		title:resource.Grid.Title,
		url : queryUrl,
		el : 'div_grid',
		sort : 'ITEM_NAME',
		heightPercent : 0.85,
		widthPercent : 1,
		margin : 30,
		cModel : _cm ,
		tbar:gridToolBar
	});
	
	itemGrid.init({
		lang:$F('lang'),
		dlms_sub_protocol:$F('dlms_sub_protocol'),
		item_sort:$F('item_sort')
	});
});
//
function queryItem(){
	itemGrid.reload({
		lang:$F('lang'),
		dlms_sub_protocol:$F('dlms_sub_protocol'),
		item_sort:$F('item_sort'),
		item_name:$F('item_name')
		,item_id_query:$F('item_id_query')
	});
}
//新增、修改数据项（普通）
function addItem(){
	var url = String.format(initDlmsParamsNormalUrl + '?lang={0}&dlms_sub_protocol={1}&item_sort={2}',$F('lang'),$F('dlms_sub_protocol'),$F('item_sort'));
	var baseParam = {
		url : url,
		el:'dlmsParams',
		title : resource.Title.addTitle,
		width : 470,
		height : 420,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function editItem(item_id){
	var url = String.format(initDlmsParamsNormalUrl + '?lang={0}&dlms_sub_protocol={1}&item_sort={2}&item_id={3}'
			,$F('lang'),$F('dlms_sub_protocol'),$F('item_sort'),item_id.replace(/\#/g,"%23"));
	var baseParam = {
		url : url,
		el:'dlmsParams',
		title : resource.Title.editTitle,
		width : 470,
		height : 420,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
//新增、修改数据项（结构体）
function addStructItem(){
	var url = String.format(initDlmsParamsStructUrl + '?lang={0}&dlms_sub_protocol={1}&item_sort={2}',$F('lang'),$F('dlms_sub_protocol'),$F('item_sort'));
	var baseParam = {
		url : url,
		el:'dlmsParams',
		title : resource.Title.addTitle,
		width : 800,
		height : 480,
		draggable : false
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function editStructItem(item_id){
	var url = String.format(initDlmsParamsStructUrl + '?lang={0}&dlms_sub_protocol={1}&item_sort={2}&item_id={3}'
			,$F('lang'),$F('dlms_sub_protocol'),$F('item_sort'),item_id.replace(/\#/g,"%23"));
	var baseParam = {
		url : url,
		el:'dlmsParams',
		title : resource.Title.editTitle,
		width : 800,
		height : 480,
		draggable : false
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}

//删除数据项
function delItem(item_id , base_attributes){
	var type = "DLMS_PARAMS";
	//参数
	var p = {
		type:type,
		dlms_sub_protocol:$F('dlms_sub_protocol'),
		item_id:item_id,
		base_attributes:base_attributes
	};
	Wg.confirm(resource.Confirm.del, function() {
		dwrAction.doDbWorks('dlmszdAction', menuId + delOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.delSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新窗口
					queryItem();
				});
			}else {
				var msg = res.msg== null?resource.Remark.delFailed:res.msg;
				Wg.alert(msg);
			}
		});
	});
}
