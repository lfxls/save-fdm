<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dmgl_value_edit_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dmgl_value_edit.js"> </script>
</head>
<body class="body">
<s:hidden id="cateCode" name="cateCode"/>
<s:hidden id="cateName" name="cateName"/>
<input type="hidden" id="codeValueOld" name="codeValueOld" value="<s:property value="code.codeValue"/>"/>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_value_edit.dmmc"/></label><font color="red">*</font></td>
		<td width="15%">
			<input type="text" id="codeName" name="codeName" class="ui-input" value="<s:property value='code.codeName'/>"/>	  
	    </td>
	    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_value_edit.dmz"/></label><font color="red">*</font></td>
		<td width="15%">
			  <input type="text" id="codeValue" name="codeValue" class="ui-input" value="<s:property value='code.codeValue'/>"/>	  
	    </td>
	</tr>
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_value_edit.sort"/></label><font color="red">*</font></td>
		<td width="15%">
			  <input type="text" id="disp_sn" name="disp_sn" class="ui-input" value="<s:property value='code.disp_sn'/>"/>	  
	    </td> 
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_value_edit.isshow"/></label></td>
		<td width="15%" class="label-group">
			<s:radio id="isShow" name="isShow" list ="#{'1':getText('sysModule.ggdmgl.dmgl.dmgl_value_edit.yes'),'0':getText('sysModule.ggdmgl.dmgl.dmgl_value_edit.no')}" value="%{isShow }" />
	    </td>
	</tr>
	<tr>
	  <td colspan="4" align="center">
	  	<button id="save" onClick="editCodeValue();" class="ext_btn"><s:text name="sysModule.ggdmgl.dmgl.dmgl_value_edit.bc"/></button></td>
	</tr>	
</table>
</html>