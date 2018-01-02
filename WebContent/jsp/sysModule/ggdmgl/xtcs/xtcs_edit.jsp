<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/xtcs/xtcs_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/xtcs/xtcs_edit.js"> </script>
</head>
<body class="body">
<s:hidden id="paraId" name="paraId"/>
<s:hidden id="paraSortId" name="paraSortId"/>
<s:hidden id="czbs" name="czbs"/>
<table class="fluid-grid">
	<tr>
	  <td width="20%" align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csmc"/></label><font color="red">*</font></td>
	  <td width="20%"><input type="text" id="csmc" name="csmc" class="ui-input" value="<s:property value='csxx.name'/>"/></td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.cszlx"/></label><font color="red">*</font></td>
	  <td>
	  	<s:select id="xtcszlx" name="xtcszlx" list="xtcszlxList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csz"/></label><font color="red">*</font></td>
		<td>
			<input type="text" id="csz" name="csz" class="ui-input" value="<s:property value='csxx.default_value'/>"/>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.cszsx"/></label></td>
		<td>
			<input type="text" id="cszsx" name="cszsx" class="ui-input" value="<s:property value='csxx.max_limit'/>"/>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.cszxx"/></label></td>
		<td>
			<input type="text" id="cszxx" name="cszxx" class="ui-input" value="<s:property value='csxx.min_limit'/>"/>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.yxbz"/></label><font color="red">*</font></td>
	  <td>
	  	<s:select id="syzt" name="syzt" list="syztList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select>
	  </td>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	  	<input type="button" onClick="editPara();" class="ext_btn" value="<s:text name="sysModule.ggdmgl.xtcs.bc"/>"></td>
	</tr>
</table>
</html>