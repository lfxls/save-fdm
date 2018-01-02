<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/hhuMgt/hhuMgt.js"></script>

</head>
<body>
<form id="hhuForm" name="hhuForm" onsubmit="return false;" class="ui-form">
	<div class="ui-panel">
		<div class="ui-panel-header">
			<s:text name="mainModule.arcMgt.hhuMgt.query.title"/>
		</div>
		<div class="ui-panel-body">
			<div class="ui-panel-item">
				<table class="fluid-grid">
					<tr>
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.hhuid"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="hhuid" name="hhuid" class="ui-input"/>
						</td>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.model"/></label>
						</td>
						<td width="14%" align="left">
								<s:select name="model" id="model" list="hhuModel"
									listKey="BM" listValue="MC" cssClass="ui-select" />
						</td> 
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.status"/></label>
						</td>
						<td width="14%" align="left">
								<s:select name="status" id="status" list="hhuStatus"
									listKey="BM" listValue="MC" cssClass="ui-select" />
						</td>
						<td width="8%" align="center">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.arcMgt.hhuMgt.query"/>'
								class="ext_btn" />
						</td>
					</tr>
				</table>
			</div>
			<div class="ui-panel-item">
			 	<div id="listGrid"></div> 
			</div>
		</div>
	</div>
</form>
</body>
</html>