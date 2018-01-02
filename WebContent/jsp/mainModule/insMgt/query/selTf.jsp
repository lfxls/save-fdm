<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/query/selTf.js"></script>
</head>
<body class="body">
<form id="queryTfFrom" name="queryTfFrom" onsubmit="return false;" class="ui-form">
<table class="fluid-grid">
	<s:hidden id="uid" name="uid"/>
	<tr>
		<td width="15%" align="right">
			<label><s:text name="common.kw.transformer.TFName"></s:text></label>
		</td>
		<td width="25%">
		   <s:textfield id="tfName" name="tfName" cssClass="ui-input" cssStyle="width:140px"></s:textfield>
		</td>
		<td width="10%">
		    <button class="ext_btn" onclick="queryTf();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
	</tr>
</table>
<div id="TfGrid"></div>
</form>
</html>