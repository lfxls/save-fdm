<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${ctx}/public/extjs/ExtPagingTree.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtFileUpload.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtFileUpload.js"> </script>
<script type="text/javascript"
	src="${ctx}/js/locale/commonModule/license/ligMgt_${appLang}.js"></script>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/license/mach_import.js"></script>
</head>
<body>
	<input type="hidden" id="clientNo" name="clientNo" value="${clientNo}"/>
	<div id="uploadForm"></div>
</body>
</html>