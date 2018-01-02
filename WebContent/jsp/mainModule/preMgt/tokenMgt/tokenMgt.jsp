<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/preMgt/tokenMgt/tokenMgt.js"></script>
</head>
<body>
<form id="tokenForm" name="tokenForm" onsubmit="return false;" class="ui-form">
	<div class="ui-panel">
		<div class="ui-panel-header">
			<s:text name="mainModule.preMgt.tokenMgt.query.title"/>
		</div>
		<div class="ui-panel-body">
			<div class="ui-panel-item">
				<table class="fluid-grid">
					<tr>
						<td width="9%" align="right">
							<label><s:text name="mainModule.preMgt.tokenMgt.msn"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="msn" name="msn" class="ui-input"/></label>
						</td>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.preMgt.tokenMgt.cno"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="cno" name="cno" class="ui-input"/></label>
						</td>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.preMgt.tokenMgt.orderid"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="orderid" name="orderid" class="ui-input"/></label>
						</td>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.preMgt.tokenMgt.status"/></label>
						</td>
						<td width="14%" align="left">
								<s:select name="status" id="status" list="tokenSts"
									listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="8%" align="center">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.preMgt.tokenMgt.query"/>'
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