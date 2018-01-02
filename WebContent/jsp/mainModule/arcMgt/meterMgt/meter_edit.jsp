<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/meterMgt/meter_edit.js"></script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
	<s:hidden id="czid" name="czid"/>
	<s:hidden id="status" name="status"/>
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding: 10px">
				<table class="fluid-grid">
					<tr>
					<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.meter.MSN"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="msn" name="msn" cssClass="ui-input" checkLength="32"/>
					</td>
					<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.other.PU"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true" cssClass="ui-input" />
							<s:hidden id="nodeIddw" name="nodeIddw" />
							<s:hidden id="nodeTypedw" name="nodeTypedw" />
							<a id="dwdms" href="javascript:getDwTree('dw');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>

						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.mode"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:select id="mode" name="mode" list="modeLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					<tr>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.meter.MType"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:select id="mtype" name="mtype" list="mtypeLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
									<s:text name="common.kw.meter.MCMethod"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
								<s:select id="wiring" name="wiring" list="wiringLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.mboxid"></s:text>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="mboxid" name="mboxid" cssClass="ui-input"  onblur="checkLength(this,32)" />
						</td>
					</tr>
					
					<tr>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.other.Longitude"></s:text>
							</label>
						</td>
						<td width="16%">
								<s:textfield id="lon" name="lon" cssClass="ui-input" />
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.other.Latitude"></s:text>
							</label>
						</td>
						<td width="16%">
								<s:textfield id="lat" name="lat" cssClass="ui-input" />
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.MF"></s:text>
							</label>
						</td>
						<td width="16%">
							<s:select id="mfid" name="mfid" list="mfLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					
					<tr>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.ct"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
								<s:select id="ct" name="ct" list="ctLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.pt"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
								<s:select id="pt" name="pt" list="ptLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.meterMgt.sealid"></s:text>
							</label>
						</td>
						<td width="16%">
								<s:textfield id="sealid" name="sealid" cssClass="ui-input" onblur="checkLength(this,128)" />
						</td>
					</tr>
					<tr>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.sim.NO"></s:text>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="simno" name="simno" cssClass="ui-input" onblur="checkLength(this,11)" readOnly="true" />
							<a id="sims" href="javascript:selectSim();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>
						<c:if test='${sysMap.helpDocAreaId=="CameroonEneo"}'>
							<td width="13%" align="right">
								<label class="ui-label">
									<s:text name="mainModule.arcMgt.meterMgt.matCode"></s:text>
								</label>
							</td>
							<td width="16%">
								<s:textfield id="matCode" name="matCode" cssClass="ui-input" />
							</td>
						</c:if>
					</tr>
					<tr>
					<td align="center" colspan='7'>
							<input id="save" type="button" onclick="Save();" value="<s:text name="mainModule.arcMgt.meterMgt.save"/>" class="ext_btn" />
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