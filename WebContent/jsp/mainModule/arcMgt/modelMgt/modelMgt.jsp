<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/modelMgt/modelMgt.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
	<table class="fluid-grid">
	<tr>
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="mainModule.arcMgt.modelMgt.mfid"></s:text>
		</label>
	</td>
	<td width="16%">
		<s:select id="mfid" name="mfid" list="mfLs" listKey="BM" listValue="MC" cssClass="ui-select" onchange="ChangeMf(this.value)"/>
	</td>
	<td width="13%" align="right">
		<label class="ui-label">
				<s:text name="mainModule.arcMgt.modelMgt.model"></s:text>
		</label>
	</td>
	<td width="16%">
		<s:select id="model" name="model" list="modelLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
	</td>
				
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="mainModule.arcMgt.modelMgt.vername"></s:text>
		</label>		
	</td>
	<td width="16%">
		<s:textfield cssClass="ui-input" id="vername" name="vername"/>
	</td>
	<td align="center">
		<input id="qy" type="button" onclick="query();" value="<s:text name="mainModule.arcMgt.modelMgt.query"/>" class="ext_btn" />
	</td>
	</tr>
	</table>
	</div>
</div>
</form>
<div class="ui-togglable-bar" targetId="queryForm" onclick="toggleQueryForm(this)">
	<a class="btn-toggle" href="javascript:void(0)" ></a>
</div>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>