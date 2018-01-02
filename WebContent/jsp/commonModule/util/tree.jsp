<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript">
	var treeType = '${treeType}';
	var nodeId = '${nodeId}';
	var nodeText = '${nodeText}';
	var nodeType = '${nodeType}';
</script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/util/tree.js"> </script>
</head>
<body class="body">
<table class="fluid-grid">
	<tr>
		<td>
			<div id="tree"></div>
		</td>
	</tr>
</table>
</html>