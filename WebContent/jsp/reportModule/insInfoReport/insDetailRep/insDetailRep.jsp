<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/reportModule/insInfoReport/insDetailRep/insDetailRep.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="tno" name="tno"></s:hidden>
<s:hidden id="woid" name="woid"></s:hidden>
<s:hidden id="pid" name="pid"></s:hidden>
<s:hidden id="opDate" name="opDate"></s:hidden>
<s:hidden id="dateType" name="dateType"></s:hidden>
<s:hidden id="proType" name="proType"></s:hidden>
<s:hidden id="bussType" name="bussType"></s:hidden>
</form>
<div id="grid"></div>
</body>
</html>