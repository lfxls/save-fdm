<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/autoBan/css/style.css" />
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('sysModule', 'sysModule_logMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/hhuLog/hhuWo.js"></script>
</head>
<body class="body">
<s:hidden id="logId" name="logId"/>
<form id="hhuWoForm" name="hhuWoForm" onsubmit="return false;" class="ui-form">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current">
				<a href="#"><s:text name="sysModule.rzgl.hhuLog.hhuWo" /></a>
			</li>
		</ul>
	</div>
	<div class="hr10"></div>
	<div id="hhuWoGrid"></div>
</form>
</body>
</html>