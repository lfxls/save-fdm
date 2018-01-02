<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/reportModule/insInfoReport/insProRep/insProRep.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
		<tr>
		
			<td width="16%" align="right">
				<label class="ui-label">
						<s:text name="report.insInfoReport.insProRep.insNum"></s:text> >
				</label>
			</td>
			<td width="20%">
				<s:textfield id="insNum" name="insNum" cssClass="ui-input" style="width:48%"/>
				
			</td>
			<td width="16%" align="right">
				<label class="ui-label">
						<s:text name="report.insInfoReport.insProRep.delayNum"></s:text>
				</label>
			</td>
			<td width="20%">
				<s:textfield id="delayNum" name="delayNum" cssClass="ui-input" />
			</td>
			<td align="center">
			<input id="qy" type="button" onclick="query();" value="<s:text name="report.insInfoReport.query"/>" class="ext_btn" />
			<input id="re" type="button" onclick="reset();" value="<s:text name="report.insInfoReport.reset"/>" class="ext_btn" />
			</td>
		</tr>
		</table>
	</div>
</div>
</form>

<div id="chartDiv" align="center"></div>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>