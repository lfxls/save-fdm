<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/custMgt/cust_edit.js"></script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
	<s:hidden id="czid" name="czid"/>
	<s:hidden id="status" name="status"/>
	<s:hidden id="billing_dates" name="billing_dates"/>
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding: 10px">
				<table class="fluid-grid">
					<tr>
						
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.customer.CSN"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="cno" name="cno" cssClass="ui-input"  onblur="checkLength(this,32)" />
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.customer.CName"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
						<s:textfield cssClass="ui-input" id="cname" name="cname" onblur="checkLength(this,256)"/>
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
							</td>
						<td width="4%">
							<a id="dwdms" href="javascript:getDwTree('dw');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>
						
					</tr>
					<tr>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.customer.CA"></s:text><font color="red">*</font>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="addr" name="addr" cssClass="ui-input" onblur="checkLength(this,256)"  />
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
									<s:text name="mainModule.arcMgt.custMgt.billing_date"></s:text>
							</label>
						</td>
						<td width="16%">
							<select id="billing_date" name="billing_date"  Class="ui-select">
									<%
										for (int i = 1; i < 32; i++) {
									%>
									<option value="<%=i%>"><%=i%></option>
									<%}%>
							</select> 
						<%-- 	<select id="billing_hour" name="billing_hour" style="width: 49%" Class="ui-select">
									<%
										for (int i = 0; i < 24; i++) {
									%>
									<option value="<%=i%>"><%=i%>:00
									</option>
									<%}%>
							</select> --%>
						</td>
						<td width="13%" align="right">
							<label class="ui-label">
								<s:text name="common.kw.other.Telephone"></s:text>
							</label>
						</td>
						<td width="16%">
							<s:textfield id="phone" name="phone" cssClass="ui-input"    />
						</td>
					</tr>
					<tr>
					<td align="center" colspan='7'>
							<input id="save" type="button" onclick="save2();" value="<s:text name="mainModule.arcMgt.custMgt.save"/>" class="ext_btn" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<div class="hr10"></div>
	<div id="grid" style="display:none"></div>
</body>
</html>