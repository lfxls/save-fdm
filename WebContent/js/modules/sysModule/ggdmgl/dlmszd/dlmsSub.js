var menuId = "54300";
var type = "DLMS_SUB";
var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var initUrl = Module_URL + 'init.do';
var resource = systemModule_ggdmgl_dlmszd_dlmsSub.resourceBundle;

Ext.onReady(function(){
	if(updOpt == $F('opt')){
		Ext.getDom("dlms_sub_protocol").disabled = "disabled";
		Ext.getDom("base_protocol").disabled = "disabled";
		if($F('lang_exist_flag') != "1") Ext.getDom("dlms_sub_name").value="";
	}
});
function saveDlmsSub(){
	//验证各个字段信息
	if($E($F('dlms_sub_protocol'))){
		Wg.alert(resource.Prompt.protocolIsNull);
		return false;
	}
	if($E($F('dlms_sub_name'))){
		Wg.alert(resource.Prompt.nameIsNull);
		return false;
	}
	//参数
	var p = {
		type:type,
		dlms_sub_protocol : $F('dlms_sub_protocol'),
		dlms_sub_name  : $F('dlms_sub_name'),
		desc:$F('desc'),
		lang: $F('lang'),
		base_protocol:$F('base_protocol'),
		lang_exist_flag:$F('lang_exist_flag'),
		status:$F("status")
	};
	if($F('opt') == addOpt){
		//新增
		p.logger = resource.Logger.add + ":" + $F('dlms_sub_name');
		dwrAction.doDbWorks('dlmszdAction', menuId + addOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.addSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新父窗口
					parent.window.location.href = initUrl+"?lang=" + $F('lang') + "&dlms_sub_protocol=" + $F('dlms_sub_protocol');
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
					parent.window.location.href = initUrl+"?lang=" + $F('lang') + "&dlms_sub_protocol=" + $F('dlms_sub_protocol');
				});
			}else {
				Wg.alert(resource.Remark.editFailed);
			}
		});
	}
}