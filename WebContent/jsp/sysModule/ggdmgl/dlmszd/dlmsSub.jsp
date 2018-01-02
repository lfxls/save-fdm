<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/dlmsSub_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/dlmsSub.js"> </script>
</head>
<body class="body">
<s:hidden id="lang" name="lang"></s:hidden>
<s:hidden id="opt" name="opt"></s:hidden>
<input type="hidden" id="lang_exist_flag" name="lang_exist_flag" value="${dlms_sub.LANG_EXIST_FLAG }"/>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.text.dlms_sub_protocol"/></label><font color="red">*</font></td>
		<td width="15%">
	  		<input type="text" id="dlms_sub_protocol" name="dlms_sub_protocol" value="${dlms_sub.DLMS_SUB_PROTOCOL }" class="ui-input"/>
	  	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.text.dlms_sub_name"/></label><font color="red">*</font></td>
	 	<td>
	 		<input type="text" id="dlms_sub_name" name="dlms_sub_protocol" value="${dlms_sub.DLMS_SUB_NAME }" class="ui-input"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.text.status"/></label></td>
	 	<td>
	 		<s:select name="status" id="status" value="dlms_sub.STATUS"
							list="#{'0':getText('sysModule.ggdmgl.dlmszd.dlmsSub.select.status0'), 
								'1':getText('sysModule.ggdmgl.dlmszd.dlmsSub.select.status1')}" cssClass="ui-select"></s:select>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="right">
	    	<label><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.text.base_protocol"/></label>
	    </td>
	    <td>
			<s:select value="dlms_sub.BASE_PROTOCOL" emptyOption="true" id="base_protocol" name="base_protocol" list="dlms_subList" listKey="DLMS_SUB_PROTOCOL" listValue="DLMS_SUB_NAME" cssClass="ui-select"></s:select>
	    </td>
	  </tr>
	  <tr>
	  	<td align="right">
	    	<label><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.text.desc"/></label>
	    </td>
	 	<td>
	 		<input type="text" id="desc" name="desc" value="${dlms_sub.REMARK }" class="ui-input"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="center" colspan="2">
	    	<input type="button" onClick="saveDlmsSub()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.button.save"/>">
	  	</td>
	 </tr>
</table>
</html>