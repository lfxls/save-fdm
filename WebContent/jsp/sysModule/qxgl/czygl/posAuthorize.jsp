<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/czygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/posAuthorize.js"></script>
</head>
<body>

<input type="hidden" name="prepayMode" id="prepayMode" value="${prepayMode}"/>
<input type="hidden" name="czyid" id="czyid" value="${czyid}"/>
<table width="99%" height="95%" cellspacing="5" cellpadding="0">
	<tr>
		<td valign="top" colspan="2">
		<s:text name="sysModule.qxgl.czygl.posAuthorize.dw"/>
		<input style="background-color: #cccccc" id="dwmc" name="dwmc" readonly="true"/>
		<s:hidden id="dwdm" name="dwdm"/>
		<s:hidden id="bmid"	name="bmid" />
		&nbsp;<a href="javascript:getDwTree('dw');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/></a>
		&nbsp;&nbsp;
		<s:text name="sysModule.qxgl.czygl.posAuthorize.posName"/>
		&nbsp;<input type="text" id="posName" name="posName" />&nbsp;&nbsp;
		<input type="button" onclick="queryPos()"  value='<s:text name="sysModule.qxgl.czygl.btn.query"/>' class="ext_btn"/>
		<input type="button" onclick="clearInput()"  value='<s:text name="sysModule.qxgl.czygl.btn.clear"/>' class="ext_btn"/>
		</td>
	</tr>
	<tr>
		<td align="left"><div id="listGrid"></div></td>
		<td align="left"><div id="listDetailGrid"></div></td>
	</tr>
</table>
</body>
</html>