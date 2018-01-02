<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/simMgt/simSel.js"></script>
</head>
<body>
<form id="simForm" name="simForm" onsubmit="return false;" class="ui-form">
	<div class="ui-panel">
		<%-- <div class="ui-panel-header">
			<s:text name="mainModule.arcMgt.simMgt.query.title"/>
		</div> --%>
		<div class="ui-panel-body">
			<!-- <div class="ui-panel-item"> -->
				<table class="fluid-grid">
					<tr>
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.simMgt.simno"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="simno" name="simno" class="ui-input"/>
						</td>
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.simMgt.simsn"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="simsn" name="simsn" class="ui-input"/>
						</td>
						<td width="8%" align="center">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.arcMgt.simMgt.query"/>'
								class="ext_btn" />
						</td>
					</tr>
				</table>
			<!-- </div> -->
			<div class="ui-panel-item">
			 	<div id="listGrid"></div> 
			</div>
		</div>
	</div>
</form>
</body>
</html>