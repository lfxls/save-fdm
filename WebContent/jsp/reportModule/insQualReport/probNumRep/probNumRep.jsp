<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/reportModule/insQualReport/probNumRep/probNumRep.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
		<tr>
		
				<td width="10%" align="right">
				<label class="ui-label">
						<s:text name="common.kw.insteam.insteam"></s:text>
				</label>
			</td>
			<td width="13%">
				<s:textfield id="tname" name="tname" cssClass="ui-input" readonly="true"  />
				<s:hidden id="tno" name="tno"></s:hidden>
				<a id="teams" href="javascript:selectInsteam();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
			</td>
			
			<td width="16%" align="right">
				<label class="ui-label">
						<s:text name="report.insQualReport.probNumRep.failNum"></s:text> &gt;
				</label>
			</td>
			<td width="20%">
				<s:textfield id="failNum" name="failNum" cssClass="ui-input" />
			</td>
			
		<%-- 	<td width="16%" align="right">
				<label class="ui-label">
						<s:text name="report.insQualReport.probNumRep.AbnorNum"></s:text> &gt;
				</label>
			</td>
			<td width="20%">
				<s:textfield id="abnorNum" name="abnorNum" cssClass="ui-input" />
			</td> --%>
			
			<td align="center">
				<input id="qy" type="button" onclick="query();" value="<s:text name="report.insQualReport.query"/>" class="ext_btn" />
				<input id="reset" type="button" onclick="Reset();" value="<s:text name="report.insQualReport.reset"/>" class="ext_btn" />
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