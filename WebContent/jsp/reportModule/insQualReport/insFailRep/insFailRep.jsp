<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/reportModule/insQualReport/insFailRep/insFailRep.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
			<tr>
				<td width="30%">
		<table class="fluid-grid">
		<tr>
			<td width="10%" align="right">
				<label class="ui-label">
						<s:text name="common.kw.insteam.insteam"></s:text>
				</label>
			</td>
			<td width="13%">
				<s:textfield id="tname" name="tname" cssClass="ui-input" readonly="true" />
				<s:hidden id="tno" name="tno"></s:hidden>
				<a id="teams" href="javascript:selectInsteam();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">
				<label class="ui-label">
						<s:text name="report.insQualReport.insFailRep.TIID"></s:text>
				</label>
			</td>
			<td width="13%">
			<s:select id="tiid" name="tiid" list="tiLs" listKey="BM" listValue="MC" cssClass="ui-select"/>		
			</td>
		</tr>
		<tr>
			<td  width="10%" align="right">
			<label class="ui-label">
						<s:text name="report.insQualReport.insFailRep.level"></s:text>
				</label>
			</td>
			<td width="13%">
			<s:select id="level" name="level" list="levelLs" listKey="BM" listValue="MC" cssClass="ui-select"/>		
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td  align="left">
			<input id="qy" type="button" onclick="query();" value="<s:text name="report.insQualReport.query"/>" class="ext_btn" />
	 		<input id="reset" type="button" onclick="Reset();" value="<s:text name="report.insQualReport.reset"/>" class="ext_btn" />
			</td>
		</tr>
		</table>
		</td>
		<td width="70%" >
				<table class="fluid-grid" >
				<tr>
					<td width="33%" align="center">
						<div id="chartDiv">FusionCharts.</div>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</div>
</div>
</form>

<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>