<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/paramData/paramMgt/testDetail.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="testId" name="testId"></s:hidden>
<s:hidden id="verId" name="verId"></s:hidden>
</form>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>