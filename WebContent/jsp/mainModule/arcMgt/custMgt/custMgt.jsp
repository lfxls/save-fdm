<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/custMgt/custMgt.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
	<table class="fluid-grid">
	<tr>
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="common.kw.other.PU"></s:text>
		</label>
	</td>
	<td width="16%" >
		<s:textfield id="nodeText" name="nodeText" readonly="true" cssClass="ui-input"/>
		<s:hidden id="nodeType" name="nodeType"/>
		<s:hidden id="nodeId" name="nodeId"/>
		<s:hidden id="nodeDwdm" name="nodeDwdm"/>
	</td>
	<td width="13%" align="right">
		<label class="ui-label">
				<s:text name="common.kw.customer.CSN"></s:text>
		</label>
	</td>
	<td width="16%">
		<s:textfield id="cno" name="cno" cssClass="ui-input"/>
	</td>
				
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="common.kw.customer.CName"></s:text>
		</label>		
	</td>
	<td width="16%">
		<s:textfield cssClass="ui-input" id="cname" name="cname"/>
	</td>
	<td align="center">
		<input id="qy" type="button" onclick="query();" value="<s:text name="mainModule.arcMgt.custMgt.query"/>" class="ext_btn" />
	</td>
	</tr>
	
	<tr>		
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="mainModule.arcMgt.custMgt.custStatus"></s:text>
		</label>		
	</td>

	<td width="16%">
			<s:select id="custStatus" name="custStatus" list="custStatusLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
	</td>
	<td width="13%" align="right">
		<label class="ui-label">
			<s:text name="mainModule.arcMgt.custMgt.dataSrc"></s:text>
		</label>		
	</td>
	<td width="13%" align="right">
			<s:select id="dataSrc" name="dataSrc" list="dataSrcLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
	</td>
	<td></td>
	<td></td>
	<td align="center">
		<input id="reset" type="button" onclick="Reset();" value="<s:text name="report.insInfoReport.reset"/>" class="ext_btn" />
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