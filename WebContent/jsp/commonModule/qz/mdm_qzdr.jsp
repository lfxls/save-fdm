<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<%@ include file="/jsp/meta.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtFileUpload.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtFileUpload.js"> </script>
	<script type="text/javascript" src="${ctx}/js/locale/basicModule/dagl/dadr/dadr_${appLang}.js"> </script>
	<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
	<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/mdm_qzpldr.js"> </script>
</head>

<body class="body">
	<s:hidden id="qzlx" name="qzlx"></s:hidden>
	<div class="ui-panel-body" style="padding:5px 0">
		<div id="uploadForm" class="ext-form"></div>
	</div>
</body>
</html>