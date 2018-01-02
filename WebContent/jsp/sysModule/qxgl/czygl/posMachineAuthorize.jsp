<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/czygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/posMachineAuthorize.js"></script>
</head>
<body>

<input type="hidden" name="prepayMode" id="prepayMode" value="${prepayMode}"/>
<input type="hidden" name="czyid" id="czyid" value="${czyid}"/>
<table width="99%" height="95%" cellspacing="5" cellpadding="0">
	<tr>
		<td valign="top" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
<!--		<s:text name="sysModule.qxgl.czygl.posMachineAuthorize.pos"/>-->
<!--		<s:hidden id="posId" name="posId"/>-->
<!--		<s:hidden id="departId" name="departId"/>-->
<!--		<input id="posName" type="text" readonly="true" value="${unitName}"/>-->
<!--		<a href="javascript:getPosTree();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
		<s:text name="sysModule.qxgl.czygl.posMachineAuthorize.posmid"/>
	    <input type="text" id="posmid" name="posmid" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="query()"  value='<s:text name="sysModule.qxgl.czygl.btn.query"/>' class="ext_btn"/>
		</td>
	</tr>
	<tr>
		<td align="left"><div id="listGrid"></div></td>
		<td align="left"><div id="listDetailGrid"></div></td>
	</tr>
</table>
</body>
</html>