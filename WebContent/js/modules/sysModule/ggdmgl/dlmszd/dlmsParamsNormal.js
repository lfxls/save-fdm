var menuId = "54300";
var type = "DLMS_PARAMS";
var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var initUrl = Module_URL + 'init.do';

var resource = systemModule_ggdmgl_dlmszd_dlmsParams.resourceBundle;

Ext.onReady(function(){
	if(updOpt == $F('opt')){
		if($F('lang_exist_flag') != "1") Ext.getDom("item_name").value="";
		Ext.get("paramTypeStruct").hide();
		$("item_id").disabled = true;
	}
});
function changeParamType(){
	parent.addStructItem();
	//parent.win.close();
}
function saveDlmsParams(){
	//验证各个字段信息
	if(trim($F('item_id'))=="") {
		Wg.alert(resource.Prompt.idIsNull);
		return false;
	}
	if(trim($F('class_id'))=="") {
		Wg.alert(resource.Prompt.classidIsNull);
		return false;
	}
	if(trim($F('obis'))=="") {
		Wg.alert(resource.Prompt.obisIsNull);
		return false;
	}
	if(trim($F('attribute_id'))=="") {
		Wg.alert(resource.Prompt.attributeidIsNull);
		return false;
	}
	if(trim($F('item_name'))=="") {
		Wg.alert(resource.Prompt.itemnameIsNull);
		return false;
	}
	//参数
	var p = {
		type:type,
		lang: $F('lang'),
		lang_exist_flag:$F('lang_exist_flag'),
		dlms_sub_protocol : $F('dlms_sub_protocol'),
		item_sort  : $F('item_sort'),
		item_id  : $F('item_id'),
		paramType:$F('paramType'),
		class_id: $F('class_id'),
		obis:$F('obis'),
		attribute_id:$F('attribute_id'),
		item_name:$F('item_name'),
		scale:$F('scale'),
		dlms_data_type:$F('dlms_data_type'),
		calling_data_type:$F('calling_data_type'),
		opt_type:$F('opt_type'),
		customize_class:$F('customize_class')
	};
	if($F('opt') == addOpt){
		//新增
		p.logger = resource.Logger.add + ":" + $F('item_name');
		dwrAction.doDbWorks('dlmszdAction', menuId + addOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.addSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新父窗口
					parent.item_name.value = $F('item_name');
					parent.queryItem();
					parent.win.close();
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
					parent.item_name.value = $F('item_name');
					parent.queryItem();
					parent.win.close();
				});
			}else {
				var msg = res.msg== null?resource.Remark.editFailed:res.msg;
				Wg.alert(msg);
			}
		});
	}
}