<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/transfMgt/transfSel.js"></script>
</head>
<body>
<form id="transfForm" name="transfForm" onsubmit="return false;" class="ui-form">
<s:hidden id="tfid" name="tfid"/>
<s:hidden id="nodeTextdw" name="nodeTextdw"/>
<s:hidden id="nodeIddw" name="nodeIddw"/>
<s:hidden id="nodeTypedw" name="nodeTypedw"/>
	<div class="ui-panel">
		<div class="ui-panel-body">
				<table class="fluid-grid">
					<tr>
						<!-- 隐藏掉单位选择 -->
						<%-- <td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.uid"/></label>
						</td>
						<td width="14%" align="left">
							<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true" cssClass="ui-input"/>
							<s:hidden id="nodeIddw" name="nodeIddw"/>
							<s:hidden id="nodeTypedw" name="nodeTypedw"/>
						</td>
						<td width="1%" align="right">
							<a id="dwdms" href="javascript:getDwTree('dw');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td> --%>
						
						<td width="9%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.tfName"/></label>
						</td>
						<td width="14%" align="left">
							<label><input type="text" id="tfname" name="tfname" class="ui-input"/>
						</td>
						<td width="8%" align="center">
							<input type="button"
								onclick="query()"
								value='<s:text name="mainModule.arcMgt.transfMgt.query"/>'
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