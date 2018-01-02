<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script> 
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/transfMgt/transf_edit.js"></script>

</head>
<body>
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="transfForm" name="transfForm">
			<%-- <s:hidden id="tfid" name="tfid"/> --%>
				<table class="fluid-grid">
					<tr>
						<td width="25%" align="right"><label><s:text name="mainModule.arcMgt.transfMgt.tfId"/><font color="red">*</font></label></td>
						<td width="25%">
							<s:textfield cssClass="ui-input" id="tfId" name="tfId" onblur="checkLength(this,32);"></s:textfield>
						</td>
						<td width="5%"></td>
						<td width="20%" align="right"><label><s:text name="mainModule.arcMgt.transfMgt.tfName"/><font color="red">*</font></label></td>
						<td width="25%">
							<s:textfield cssClass="ui-input" id="tfName" name="tfName" onblur="checkLength(this,256);"></s:textfield>
						</td>				
						
					</tr>
					<tr>
						<td width="25%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.uid"/><font color="red">*</font></label>
						</td>
						<td width="25%">
							<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true" cssClass="ui-input" />
							<s:hidden id="nodeIddw" name="nodeIddw" />
							<s:hidden id="nodeTypedw" name="nodeTypedw" />
							<a id="dwdms" href="javascript:getDwTree('dw');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>
						<td width="5%"></td>
						<td width="20%" align="right"><label><s:text name="mainModule.arcMgt.transfMgt.status"/><font color="red">*</font></label></td>
						<td width="25%">
							<s:select name="status" id="status" list="tfStatus" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					<tr>
						<td width="25%" align="right">
							<label><s:text name="mainModule.arcMgt.transfMgt.cap"/></label>
						</td>
						<td width="25%">
							<s:textfield cssClass="ui-input" name="cap" id="cap"/>
						</td>
						<td width="5%">kVA</td>
						<td width="20%">
							<label><s:text name="mainModule.arcMgt.transfMgt.addr"/></label>
						</td>
						<td width="25%">
							<s:textfield cssClass="ui-input" id="addr" name="addr" onblur="checkLength(this,256);"/>
						</td>					
					</tr>
					<tr>
						<td align="center" colspan='5'>
							<input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="mainModule.arcMgt.transfMgt.edit.save"/>">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	var operateType = '${operateType}';
	if('edit' == operateType) {
		$('tfId').style.backgroundColor='#cccccc';
		$('tfId').readOnly = true;
	}
</script>
</html>