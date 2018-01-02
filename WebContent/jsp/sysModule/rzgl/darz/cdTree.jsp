<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/darz/cdTree.js"> </script>
</head>
<body>
<s:hidden id="treeType" name="treeType" value="cd"/>
<div id="tree"></div>
</body>
</html>