<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/transfMgt/transfMgt.js"></script>
</head>
<body>
<form id="transfForm" name="transfForm" onsubmit="return false;" class="ui-form">
<%-- <s:hidden id="tfid" name="tfid"/> --%>
<s:hidden id="transformerFromOther" name="transformerFromOther"/>
	<div class="ui-panel">
		<div class="ui-panel-header">
			<s:text name="mainModule.arcMgt.transfMgt.query.title"/>
		</div>
		<div class="ui-panel-body">
			<div class="ui-panel-item">
				<table class="fluid-grid">
					<tr>
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.uid"/></label>
						</td>
						<td width="14%" align="left">
							<s:textfield id="nodeText" name="nodeText" readonly="true" cssClass="ui-input"/>
							<s:hidden id="nodeType" name="nodeType"/>
							<s:hidden id="nodeId" name="nodeId"/>
							<s:hidden id="nodeDwdm" name="nodeDwdm"/>
						</td>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.tfId"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="tfId" name="tfId" class="ui-input"/>
						</td>
											
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.tfName"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="tfName" name="tfName" class="ui-input"/>
						</td>
						<td width="8%" align="center" valign="middle">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.arcMgt.transfMgt.query"/>'
								class="ext_btn" />
						</td>
					</tr>
					<tr>
						<td width="9%" align="right" >
							<label><s:text name="mainModule.arcMgt.transfMgt.status"/></label>
						</td>
						<td width="14%" align="left">
								<s:select name="status" id="status" list="tfStatus"
									listKey="BM" listValue="MC" cssClass="ui-select" />
						</td>
						<td width="9%" align="right">
							<label class="ui-label">
								<s:text name="mainModule.arcMgt.transfMgt.dataSrc"></s:text>
							</label>		
						</td>
						<td width="14%" align="right">
							<s:select id="dataSrc" name="dataSrc" list="dataSrcLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td></td>
						<td></td>
						<td align="center">
							<input id="re" type="button" onclick="Reset();" value="<s:text name="common.kw.other.reset"/>" class="ext_btn" />
						</td>
						<%-- <td width="8%" align="center">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.arcMgt.transfMgt.query"/>'
								class="ext_btn" />
						</td> --%>
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