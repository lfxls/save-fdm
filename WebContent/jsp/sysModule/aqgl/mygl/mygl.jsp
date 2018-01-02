<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/aqgl/mygl/mygl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/aqgl/mygl/mygl.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="basicModule.csgl.flxz.flxz.jd"/></label></td>
		<td width="20%">
			<s:textfield id="nodeText" name="nodeText" disabled="true" cssClass="ui-input"/>
			<s:hidden id="nodeType" name="nodeType"/>
			<s:hidden id="nodeId" name="nodeId"/>
			<s:hidden id="nodeDwdm" name="nodeDwdm"/>
		</td>
		<td width="10%" align="right"><label><s:text name="basicModule.csgl.flxz.flxz.bh"/></label></td>
		<td width="20%"><s:textfield id="bjjh" name="bjjh" cssClass="ui-input"/></td>
		<td width="10%"></td>
		<td><input type="button" id="qy" class="ext_btn" onclick="query();" value="<s:text name="basicModule.csgl.flxz.flxz.cx"/>"></td>
	</tr>
</table>
</form>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>