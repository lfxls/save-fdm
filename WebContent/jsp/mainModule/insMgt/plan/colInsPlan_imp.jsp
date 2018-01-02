<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtFileUpload.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtFileUpload.js"> </script>
	<script type="text/javascript">
		//加载国际化资源文件
		loadProperties('mainModule', 'mainModule_insMgt');
	</script>
	<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/colInsPlan_imp.js"></script>
</head>

<script>
	var lang = '${appLang}.xls';
</script>
<body class="body">
	<div id="cInsPlanImpForm" class="ext-form"></div>
</body>
</html>