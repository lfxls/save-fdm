<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript">
	var STRUCTITEMSPLITOR = "${STRUCTITEM_SEPARATOR}";
	var allItemData = new Array();
	<c:forEach items="${dlmsParamsList }" var="item" >
		allItemData.push({key:"${item.CLASS_ID }${STRUCTITEM_ATTRIBUTE_SEPARATOR}${item.OBIS }${STRUCTITEM_ATTRIBUTE_SEPARATOR}${item.ATTRIBUTE_ID }",
				name:"${item.ITEM_NAME eq null ? item.ITEM_ID: item.ITEM_NAME }"});
	</c:forEach>
</script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/dlmsParams_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/dlmsParamsStruct.js"> </script>
<style>
	.selectTitle{
		height:25px;
		width:300px;
		padding-top:2px;
		overflow: hidden;
	}
	.selectDiv{
		width:300px;
		overflow: hidden;
	}
</style>
</head>
<body class="body">
<s:hidden id="lang" name="lang"></s:hidden>
<s:hidden id="opt" name="opt"></s:hidden>
<input type="hidden" id="lang_exist_flag" name="lang_exist_flag" value="${dlms_params.LANG_EXIST_FLAG }"/>
<s:hidden id="dlms_sub_protocol" name="dlms_sub_protocol"></s:hidden>
<s:hidden id="item_sort" name="item_sort"></s:hidden>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType"/></label></td>
		<td width="15%">
			<span id="paramTypeStruct">
		  		&nbsp;&nbsp; <input type="radio" name="paramType" value="1"  checked="checked"/><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType_struct"/>
			</span>
			<span  id="paramTypeNormal">
				&nbsp;&nbsp;<input type="radio" name="paramType" value="0" onclick="changeParamType();"/><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType_normal"/>
			</span>
	  	</td>
	 	<td width="10%" align="right"><label>ID</label><font color="red">*</font></td>
	 	<td width="15%"><input id="item_id" name="item_id" value="${dlms_params.ITEM_ID }"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.class_id"/></label><font color="red">*</font></td>
	 	<td>
	 		<input id="class_id" name="class_id" class="ui-input" value="${dlms_params.CLASS_ID }"/>
	 	</td>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.obis"/></label><font color="red">*</font></td>
	 	<td>
	 		<input id="obis" name="obis" class="ui-input" value="${dlms_params.OBIS }"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.attribute_id"/></label><font color="red">*</font></td>
	 	<td>
	 		<input id="attribute_id" name="attribute_id" class="ui-input" value="${dlms_params.ATTRIBUTE_ID }"/>
	 	</td>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.item_name"/></label><font color="red">*</font></td>
	 	<td>
	 		<input id="item_name" name="item_name" class="ui-input" value="${dlms_params.ITEM_NAME }"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.opt_type"/></label><font color="red">*</font></td>
	 	<td>
	 		<s:select value="dlms_params.OPT_TYPE" id="opt_type" name="opt_type" list="optTypeList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select>
	 	</td>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.customize_class"/></label></td>
	 	<td>
	 		<input id="customize_class" name="customize_class" class="ui-input" value="${dlms_params.CUSTOMIZE_CLASS }"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right" valign="top" style="padding-top:15px;">
	 		<label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.array_struct"/></label>
	 	</td>
	 	<td colspan="3" >
	 		<table width="100%">
	 			<tr>
	 				<td width="50%">
	 					<div>
				 			<div class="selectTitle">
					 			<input type="text" id="searchValue" value="" class="ui-input" style="width: 150px"/>&nbsp;<input type="button" id="search" class="ext_btn" onclick="search();" value='<s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.button.search" />'/>
				 			</div>
				 			<div class="selectDiv">
				 				<select id="allItem" class="ui-select" style="height:250px" multiple="multiple" ondblclick="selectItem()">
				 					<c:forEach items="${dlmsParamsList }" var="item" >
									    <option value="${item.CLASS_ID }${STRUCTITEM_ATTRIBUTE_SEPARATOR}${item.OBIS }${STRUCTITEM_ATTRIBUTE_SEPARATOR}${item.ATTRIBUTE_ID }" >${item.ITEM_NAME eq null ? item.ITEM_ID: item.ITEM_NAME }</option>
				 					</c:forEach>
								</select>
				 			</div>
				 		</div>
	 				</td>
	 				<td width="50%">
	 					<div class="ui-panel" style="width:100%">
				 			<div class="ui-panel-header">
				 				<s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.selectedItem"/>
				 			</div>
				 			<div class="ui-panel-body">
				 				<select id="selectedItem" class="ui-select" style="height:250px; width:100%; border:0" multiple="multiple" ondblclick="cancelSelectItem()">
				 					<c:forEach items="${selectedItemList }" var="temp" >
									    <option value="${temp.baseAttributes }">${temp.itemName }</option>
				 					</c:forEach>
								</select>
				 			</div>
				 		</div>
	 				</td>
	 			</tr>
	 		</table>
	 		
	 	</td>
	 </tr>
	 <tr>
	 	<td align="center" colspan="4">
	    	<input type="button" onClick="saveDlmsParams()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.button.save"/>">
	  	</td>
	 </tr>
</table>
</html>