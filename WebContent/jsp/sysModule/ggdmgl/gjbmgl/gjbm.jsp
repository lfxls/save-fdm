<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/public/js/commForZzrw.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/gjbmgl/gjbm_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/gjbmgl/gjbm.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<table class="fluid-grid">
	<tr>
		<td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gjbm"/></label></td>
		<td width="17%"><s:textfield id="gjbm" name="gjbm" cssClass="ui-input"/></td>
		<td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gjmc"/></label></td>
		<td width="17%"><s:textfield id="gjmc" name="gjmc" cssClass="ui-input"/></td>
		
		<td width="13%" align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.lb"/></label></td>
		<td width="17%" ><s:select id="gjlb" name="gjlb" list="gjlbls" listKey="BM"
			listValue="MC" cssClass="ui-select" /></td>
		<td rowspan="2" width="10%" align="right"><input type="button" id="qy" class="ext_btn" onclick="query();" value="<s:text	name="basicModule.dagl.sim.simgl.cx"/>"></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.yy"/></label></td>
		<td ><s:select id="yy" name="yy" list="yyls" listKey="BM"
			listValue="MC" cssClass="ui-select"/></td>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gylx"/></label></td>
		<td ><s:select id="gylx" name="gylx" list="gylxls" listKey="BM"
			listValue="MC" cssClass="ui-select"/></td>
		<td align="right"></td>
		
	</tr>
</table>
</form>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>