<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/xtcs/xtcs_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/xtcs/xtcs.js"> </script>
</head>
<body>
<table class="fluid-grid">
	<tr>
		<td width="15%" align="right"><label><s:text name="sysModule.ggdmgl.xtcs.yy"/></label></td>
	  	<td width="20%"><s:select id="lang" name="lang" list="langList" listKey="BM" listValue="MC" cssClass="ui-select" onchange="query();"></s:select>
	  	</td>
	    <td width="15%" align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csflmc"/></label></td>
	    <td width="20%"><input type="text" id="csflmc" name="csflmc" class="ui-input"/></td>
	    <td width="10%"><input type="button" onClick="query()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.xtcs.cx"/>"></td>
	</tr>
</table>
<div class="hr10"></div>
<table width="100%">
	<tr>
		<td style="padding-right:7px"><div id="sysparaSortGrid"></div></td>
		<td style="padding-left:7px"><div id="sysparaGrid"></div></td>
	</tr>
</table>
</body>
</html>