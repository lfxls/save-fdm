<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp" %>
<style type="text/css">
<!--
body {
	margin-top: 160px;
}
-->
</style>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
<table width="660px" align="center" cellspacing="0" cellpadding="0" style="border:1px solid #99BBE8;">
	<tr height="80px"> 
		<td align="center" style="font-size: 13px;font-weight: bold;">
		 	<img src="${ctx}/public/extjs/resources/images/default/window/icon-warning.gif" height="13" width="13">&nbsp;<a href="javascript:back()">
		 	<s:text name="session.noright"></s:text></a>
		</td>
	</tr>
</table>
<script language="javascript">
function back(){
	history.go(-1);
}
</script>
</body>
</html>
