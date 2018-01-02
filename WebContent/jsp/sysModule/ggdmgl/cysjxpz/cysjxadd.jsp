<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/cysjxpz/cysjxpz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/cysjxpz/cysjxadd.js"> </script>
</head>
<body class="body">
<form id="queryForm">
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cysjxpz.gylx"/></label></td>
		<td width="15%"><s:select id="gylx" name="gylx" list="gyLs" listKey="BM" listValue="MC" cssClass="ui-select" onchange="query()"/></td>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cysjxpz.sjxbm"/></label></td>
		<td width="15%"><s:textfield id="sjxbm" name="sjxbm" cssClass="ui-input"/></td>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cysjxpz.sjxmc"/></label></td>
		<td width="20%"><s:textfield id="sjxmc" name="sjxmc" size="30" cssClass="ui-input"/></td>
		<td>
			<input type="button" id="qy" class="ext_btn" onclick="query();" value="<s:text name="sysModule.ggdmgl.cysjxpz.cx"/>">
		</td>
	</tr>
</table>
</form>

<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>