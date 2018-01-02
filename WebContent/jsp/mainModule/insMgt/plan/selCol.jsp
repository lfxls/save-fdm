<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/selCol.js"></script>
</head>
<body class="body">
<form id="queryColForm" id="queryColForm" onsubmit="return false;" class="ui-form">
	<table width="100%" class="fluid-grid">
	  <tr>
	 	<td width="6%" align="right">
	 		<label><s:text name="mainModule.insMgt.plan.colNo"/></label>
	 	</td>
		<td width="30%">
			<input type="text" name="csn" id="csn" class="ui-input"/>
		</td>
		<td width="6%" align="right">
			<label><s:text name="mainModule.insMgt.plan.colModel" /></label>
		</td>
		<td width="30%">
			<s:select id="colM" name="colM" list="colMList" listKey="BM"
				listValue="MC" cssClass="ui-select" onchange="changeColM()"/>
		</td>
		<td width="25%" align="center">
			<button class="ext_btn" onclick="query();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
	  </tr>
	</table>
</form>
	<div id="colGrid"></div>
</body>
</html> 
