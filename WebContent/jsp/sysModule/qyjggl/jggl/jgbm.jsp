<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/jggl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/jgbm.js"> </script>
</head>
<body class="body">
<s:hidden name="dwmc" id="dwmc"></s:hidden>
<s:hidden name="dwdm" id="dwdm"></s:hidden>
<div id="grid"></div>
</html>