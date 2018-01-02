<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/license/licMgt_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/license/licMgt.js"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="5" cellpadding="0" class="ext_condition">
	<tr>
		<td align="right">客户编号</td>
		<td><input type="text" id="clientNo" name="clientNo"/></td>
		<td align="center"><button type="button" onclick="queryClient()" class="ext_btn">查询</button></td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="5" cellpadding="0" class="ext_condition">
	<tr>
		<td width="50%"><div id="clientGrid"></div></td>
		<td><div id="licGrid"></div></td>
	</tr>
</table>
<div id="uploadForm"></div>
</body>
</html>