<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dmgl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dmgl.js"> </script>
</head>
<body>
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid" id="queryTable">
			<tr>
				<td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.yy"/></label></td>
			  	<td width="16%"><s:select id="yy" name="lang" list="langList" listKey="BM" listValue="MC" cssClass="ui-select" onchange="changeLang();"></s:select></td>
			    <td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.dmflmc"/></label></td>
			    <td width="16%"><input type="text" id="dmflmc" name="name" class="ui-input"/></td>
			    <td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.dmgl.cateCode"/></label></td>
			    <td width="16%"><input type="text" id="cateCode" name="cateCode" class="ui-input"/></td>
			    <td width="13%" align="center"><input type="button" onClick="query()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dmgl.cx"/>"></td>
			</tr>
		</table>
	</div>
</div>
<div class="ui-togglable-bar" targetId="queryTable" onclick="toggleQueryForm(this)">
	<a class="btn-toggle" href="javascript:void(0)" ></a>
</div>
<div class="hr10"></div>
<table width="100%">
	<tr>
		<td style="padding-right:7px"><div id="p_code_sort_grid"></div></td>
		<td style="padding-left:7px"><div id="p_code_grid"></div></td>
	</tr>
</table>
</html>