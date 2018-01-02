var menuId = "54300";
var type = "DLMS_DATA_SORT";
var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var resource = systemModule_ggdmgl_dlmszd_dataSort.resourceBundle;

Ext.onReady(function(){
	if(updOpt == $F('opt')){
		Ext.getDom("item_sort").disabled = "disabled";
		if($F('lang_exist_flag') != "1") Ext.getDom("item_sort_name").value="";
	}
});
function saveDataSort(){
	var dmmc = $F('dmmc');
	//验证各个字段信息
	if($E($F('item_sort'))){
		Wg.alert(resource.Prompt.noIsNull);
		return false;
	}
	//参数
	var p = {
		type:type,
		dlms_sub_protocol : $F('dlms_sub_protocol'),
		lang: $F('lang'),
		lang_exist_flag:$F('lang_exist_flag'),
		item_sort  : $F('item_sort'),
		item_sort_name:$F('item_sort_name')
	};
	if($F('opt') == addOpt){
		//新增
		p.logger = resource.Logger.add + ":" + $F('item_sort_name');
		dwrAction.doDbWorks('dlmszdAction', menuId + addOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.addSuccess:res.msg;
				Wg.alert(msg, function() {
					//调用父窗口增加tab
					parent.addTab($F('item_sort') , $F('item_sort_name') , true);
					parent.win.close(); //关闭弹出窗口
				});
			}else {
				var msg = res.msg== null?resource.Remark.addFailed:res.msg;
				Wg.alert(msg);
			}
		});
	}else{ 
		//更新
		p.logger = resource.Logger.edit + ":" + $F('dlms_sub_name');
		dwrAction.doDbWorks('dlmszdAction', menuId + updOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.editSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新父窗口
					parent.refreshTab($F('item_sort') , $F('item_sort_name'));
					parent.win.close(); //关闭弹出窗口
				});
			}else {
				Wg.alert(resource.Remark.editFailed);
			}
		});
	}
}