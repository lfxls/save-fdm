var menuId = "54300";
var type = "DLMS_PARAMS";
var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var initUrl = Module_URL + 'init.do';
var DATATYPESTRUCT = "array_struct";
var CALLINGDATATYPESTRUCT = "string";
var resource = systemModule_ggdmgl_dlmszd_dlmsParams.resourceBundle;

Ext.onReady(function(){
	if(updOpt == $F('opt')){
		if($F('lang_exist_flag') != "1") Ext.getDom("item_name").value="";
		Ext.get("paramTypeNormal").hide();
		//初始化已选中
		var selectedSelect = $("selectedItem");
		var len = selectedSelect.options.length;
		for(var i = 0; i < len; i++) {
			var selectedValue = selectedSelect.options[i].value;
			for(var j = 0; j < allItemData.length; j++) {
				if(selectedValue == allItemData[j].key) {
					selectedSelect.options[i].text = allItemData[j].name;
					break;
				}
			}
		}
		$("item_id").disabled = true;
	}
});
function changeParamType(){
	parent.addItem();
	//parent.win.close();
}

//选择数据项
function selectItem() {
	exchangeItem("allItem" , "selectedItem" , true);
}
//反选 数据项
function cancelSelectItem() {
	exchangeItem("selectedItem", "allItem"  , false);
}
function exchangeItem(srcSelectId , targetSelectId , checkValue){
	var allItemSelect = $(srcSelectId);
	var index=allItemSelect.selectedIndex;
	var selectedOption = allItemSelect.options[index];
	var srcValue = selectedOption.value;
	if(checkValue){
		//判断是否已选择
		if(isInSelect(targetSelectId , srcValue)) return false;
	}
	//增加选中
	var selectedItemSelect = $(targetSelectId);
	selectedItemSelect.options.add(new Option(selectedOption.text,srcValue)); //这个兼容IE与firefox
	//删除原有
	allItemSelect.options.remove(index);
}
function isInSelect(selectId , srcValue){
	//判断是否已选择
	var oSelect = $(selectId);
	for(var i = 0; i < oSelect.options.length; i++) {
		var oOption = oSelect.options[i];
		if(srcValue == oOption.value) {
			return true;
		}
	}
	return false;
}
//查询
function search(){
	var searchValue = $F("searchValue");
	//清空select 
	$("allItem").options.length=0;
	//查询
	for(var i=0; i<allItemData.length ; i++){
		if(isInSelect("selectedItem" , allItemData[i].key)) continue;
		if($E(searchValue)){  //显示全部
			$("allItem").options.add(new Option( allItemData[i].name, allItemData[i].key));
		}else if(allItemData[i].name.indexOf(searchValue)!=-1){ //模糊查询
			$("allItem").options.add(new Option( allItemData[i].name, allItemData[i].key));
		}
	}
}
//保存
function validData(){
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
	if($("selectedItem").options.length == 0){
		Wg.alert(resource.Prompt.selectedItemIsNull);
		return false;
	}
	return true;
}
function saveDlmsParams(){
	//验证各个字段信息
	if(!validData()) return false;
	//参数
	var selectedSelect = $("selectedItem");
	var array_struct_len = selectedSelect.options.length;
	var array_struct_item ="";
	for(var i = 0; i < selectedSelect.options.length; i++) {
		if(i!=0)array_struct_item += STRUCTITEMSPLITOR;
		array_struct_item += selectedSelect.options[i].value;
	}
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
		opt_type:$F('opt_type'),
		dlms_data_type:DATATYPESTRUCT,
		calling_data_type:CALLINGDATATYPESTRUCT,
		array_struct_len:array_struct_len,
		array_struct_item:array_struct_item
		,customize_class:$F('customize_class')
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