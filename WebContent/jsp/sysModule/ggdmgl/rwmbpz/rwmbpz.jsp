<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/rwmbpz/rwmbpz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/rwmbpz/rwmbpz.js"> </script>
</head>
<body class="body">
<input type="hidden" name="zdgylx" id="zdgylx" value="03"/>
<!-- 
<table width="100%" cellspacing="0" cellpadding="0" class="ext_condition">
	<tr>
		<td>
	  	规约类型
	  	<s:select id="zdgylx" name="zdgylx" list="zdgyLs" listKey="BM" listValue="MC" cssStyle="width:80px;" onchange="query();"></s:select>
	  </td>
	</tr>
</table>
 -->
<div id="grid"></div>
</html>