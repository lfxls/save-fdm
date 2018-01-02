<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/paramData/paramMgt/paramList.js"> </script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
		<s:hidden id="cateId" name="cateId"></s:hidden>
		<s:hidden id="verId" name="verId"></s:hidden>
		<s:hidden id="paramType" name="paramType"></s:hidden>
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding:10px">
				<table class="fluid-grid">
					<tr id="param">
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.itemSort"></s:text></label>
						</td>
						<td width="25%">
							<s:select id="itemSort" name="itemSort" list="itemSortLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.param"></s:text></label>
						</td>
						<td  width="30%">
							<s:textfield id="itemName" name="itemName" cssClass="ui-input" cssStyle="width:180px" />
						</td>
						
						
						<td align="center">
							<input id="qy" type="button" onclick="query();" value="<s:text name="sysModule.paramData.paramMgt.query"/>" class="ext_btn" />
						</td>
					</tr>
					<tr id="testParam">
					<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.testParam"></s:text></label>
						</td>
						<td  width="16%">
							<s:textfield id="tiName" name="tiName" cssClass="ui-input" cssStyle="width:180px" />
						</td>
						<td align="center">
							<input id="qy" type="button" onclick="query();" value="<s:text name="sysModule.paramData.paramMgt.query"/>" class="ext_btn" />
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