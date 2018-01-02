<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/mainMdmPage.js"> </script>
</head>
<body>
<table width="100%" align="center" cellpadding="0" cellspacing="5" border="0">
<s:hidden id="czyId" name="czyId" />
<s:hidden id="nodeType" name="nodeType" /> 
<s:hidden id="nodeId" name="nodeId" />
<s:hidden id="nodeDwdm" name="nodeDwdm" />
	<tr height="50%">
		<td width="50%"  valign="top" style="padding:0 7px 14px 0">
			<div class="ui-panel">
				<div class="ui-panel-header">
					<s:text name="common.zysz.datj"/>
				</div>
				<div class="ui-panel-body">
					<div class="ui-table-container">
						<table class="ui-table ui-table-inbox" width="100%" height="200px" align="center" cellspacing="0" cellpadding="0" class="home_table">
							<thead>
							<tr class="home_title">
								<th><s:text name="common.mdmsy.cs.cs"></s:text> </th>
								<th><s:text name="common.zysz.tyyhs"/></th>
								<th><s:text name="common.zysz.tyzds"/></th>
							</tr>
							</thead>
							<tbody>
							<s:iterator value="#request.re" status="stuts">
							<tr>
							     <td class="home_td">&nbsp;${CSMC}</td>
								<td class="home_td">&nbsp;${TYYHS}</td>
								<td class="home_td">&nbsp;${TYZDS}</td>
							</tr>
							</s:iterator>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</td>
		<td width="50%" valign="top" style="padding:0 0 14px 7px">
			<div class="ui-panel">
				<div class="ui-panel-header">
					<s:text name="common.mdmsy.cs.xlxsl"></s:text>
				</div>
				<div class="ui-panel-body">
					<div id="xlxs">FusionCharts.</div>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td valign="top" style="padding-right:7px">
			<div class="ui-panel">
				<div class="ui-panel-header">
					<s:text name="common.zysz.cbcgl"/>
				</div>
				<div class="ui-panel-body">
					<div id="cgl">FusionCharts.</div>
				</div>
			</div>
		</td>
		<td valign="top" style="padding-left:7px">
			<div class="ui-panel">
				<div class="ui-panel-header">
					<s:text name="common.zysz.yctj"/>
				</div>
				<div class="ui-panel-body">
					<div id="yctj">FusionCharts.</div>
				</div>
			</div>
		</td>
	</tr>
</table>

</body>
</html>