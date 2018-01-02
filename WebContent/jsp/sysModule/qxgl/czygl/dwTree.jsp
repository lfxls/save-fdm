<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/czygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/dwTree.js"> </script>
</head>
<body>
<table width="99%" height="100%" cellspacing="5" cellpadding="5" border="0">
	<tr>
		<td valign="top">
		<s:hidden id="treeType" name="treeType"/>
		<div id="tree"></div></td>
	</tr>
</table>
</html>