<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/dlmsParams_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/dlmsParamsNormal.js"> </script>
</head>
<body class="body">
<s:hidden id="lang" name="lang"></s:hidden>
<s:hidden id="opt" name="opt"></s:hidden>
<input type="hidden" id="lang_exist_flag" name="lang_exist_flag" value="${dlms_params.LANG_EXIST_FLAG }"/>
<s:hidden id="dlms_sub_protocol" name="dlms_sub_protocol"></s:hidden>
<s:hidden id="item_sort" name="item_sort"></s:hidden>
<table class="fluid-grid">
	<tr>
		<td width="15%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType"/></label></td>
		<td width="20%" class="label-group">
			<span id="paramTypeNormal">
				&nbsp;&nbsp;<input type="radio" name="paramType" value="0" checked="checked"/><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType_normal"/>
			</span>
			<span id="paramTypeStruct">
		  		&nbsp;&nbsp;<input type="radio" name="paramType" value="1" onclick="changeParamType();"/><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.paramType_struct"/>
			</span>
	  	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label>ID</label><font color="red">*</font></td>
	 	<td><input id="item_id" name="item_id" value="${dlms_params.ITEM_ID }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.class_id"/></label><font color="red">*</font></td>
	 	<td><input id="class_id" name="class_id" value="${dlms_params.CLASS_ID }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.obis"/></label><font color="red">*</font></td>
	 	<td><input id="obis" name="obis" value="${dlms_params.OBIS }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.attribute_id"/></label><font color="red">*</font></td>
	 	<td><input id="attribute_id" name="attribute_id" value="${dlms_params.ATTRIBUTE_ID }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.item_name"/></label><font color="red">*</font></td>
	 	<td><input id="item_name" name="item_name" value="${dlms_params.ITEM_NAME }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.scale"/></label></td>
	 	<td><input id="scale" name="scale" value="${dlms_params.SCALE }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.dlms_data_type"/></label><font color="red">*</font></td>
	 	<td><s:select value="dlms_params.DLMS_DATA_TYPE" id="dlms_data_type" name="dlms_data_type" list="dataTypeList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select></td>
	 </tr>
	  <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.calling_data_type"/></label><font color="red">*</font></td>
	 	<td><s:select value="dlms_params.CALLING_DATA_TYPE" id="calling_data_type" name="calling_data_type" list="callingTypeList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.opt_type"/></label><font color="red">*</font></td>
	 	<td><s:select value="dlms_params.OPT_TYPE" id="opt_type" name="opt_type" list="optTypeList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select></td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.text.customize_class"/></label></td>
	 	<td><input id="customize_class" name="customize_class" value="${dlms_params.CUSTOMIZE_CLASS }" class="ui-input"/></td>
	 </tr>
	 <tr>
	 	<td align="center" colspan="2">
	    	<input type="button" onClick="saveDlmsParams()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.dlmsParams.button.save"/>">
	  	</td>
	 </tr>
</table>
</html>