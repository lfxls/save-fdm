<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script> 
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/simMgt/sim_edit.js"></script>

</head>
<body>
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="simForm" name="simForm">
				<table class="fluid-grid">
					<tr>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.simMgt.simno"/><font color="red">*</font></label>
						</td>
						<td>
							<s:textfield cssClass="ui-input" id="simno" name="simno" onblur="isNumbers()"></s:textfield>
						</td>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.simMgt.msp"/><font color="red">*</font></label>
						</td>
						<td>
							<s:select name="msp" id="msp" list="txfwsLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.simMgt.simsn"/></label>
						</td>
						<td>
							<s:textfield cssClass="ui-input" name="simsn" id="simsn" onblur="checkLength(this,32);"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan='4'>
							<input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="mainModule.arcMgt.simMgt.edit.save"/>">
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
		$('simno').style.backgroundColor='#cccccc';
		$('simno').readOnly = true;
	}
</script>
</html>