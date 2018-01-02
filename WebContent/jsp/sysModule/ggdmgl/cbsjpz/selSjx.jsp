<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/public/js/commForZzrw.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/cbsjpz/cbsjpz_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/cbsjpz/selSjx.js"></script>
</head>
<body>
<form id="queryForm" onsubmit="return false;">
<s:hidden id="dlx" name="dlx"/>
</form>
<div id="_grid"></div>
</body>
</html>