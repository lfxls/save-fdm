<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dmgl_sort_edit_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dmgl_sort_edit.js"> </script>
</head>
<body class="body">
<input type="hidden" id="cateCodeOld" name="cateCodeOld" value="${cateCode}"/>
<table class="fluid-grid">
	<tr>
	  <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_sort_edit.dmflmc"/></label><font color="red">*</font></td>
	  <td width="15%"><input type="text" id="cateName" name="cateName" class="ui-input" value="<s:property value="codeCategory.cateName"/>" onblur="checkLength(this,255);"/></td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmgl_sort_edit.dmflbm"/></label><font color="red">*</font></td>
	  <td><input type="text" id="cateCode" name="cateCode" class="ui-input" value="<s:property value="codeCategory.cateCode"/>" onblur="checkLength(this,16);"/></td>
	</tr>
	<tr>
	  <td colspan="2" align="center"><input type="button" onClick="editCodeSort();" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dmgl.dmgl_sort_edit.bc"/>"></td>
	</tr>	
</table>
</html>