<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/reportModule/insInfoReport/insNumRep/insNumDetailRep.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
		<tr>
			<td width="10%" align="right">
				<label class="ui-label">
						<s:text name="report.insInfoReport.planStsRep.team"></s:text>
				</label>
			</td>
			<td width="13%">
				<s:textfield id="tname" name="tname" cssClass="ui-input" readonly="true"/>
				<s:hidden id="tno" name="tno"></s:hidden>
				<a id="teams" href="javascript:selectInsteam();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
			</td>
			
			<td  width="10%" align="right">
			<label class="ui-label">
						<s:text name="report.insInfoReport.planStsRep.date"></s:text>
				</label>
			</td>
			  <td width="8%"><s:select id="dateType" name="dateType" list="dateTypeLs" listKey="BM" listValue="MC" cssClass="ui-select" onchange="changeDateType(this.value)" /></td>
			<td width="20%">
			<div id="Day">
				<input name="startDate" id="startDate" value="${startDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'endDate\')}'})"  class="Wdate" style="width:48%"/>~
				<input name="endDate" id="endDate" value="${endDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate" style="width:48%"/>
			</div>
			<div id="Month" style="display:none">
			<s:hidden id="startMonth2" name="startMonth"/>
			<s:hidden id="endMonth2" name="endMonth"/>
				<input name="startMonth" id="startMonth" value="${startMonth}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'endMonth\')}'})"  class="Wdate" style="width:48%"/>~
				<input name="endMonth" id="endMonth" value="${endMonth}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'startMonth\')}',maxDate:'%y-%M-%d'})" class="Wdate" style="width:48%"/>
 			</div>
 			</td>
			<td align="right" class="label-group"><s:radio id="chartType" name="chartType" list="chartTypeLs" listKey="BM" listValue="MC" onclick="showChart('');"></s:radio></td>
			<td align="center">
			<input id="qy" type="button" onclick="query();" value="<s:text name="report.insInfoReport.query"/>" class="ext_btn" />
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