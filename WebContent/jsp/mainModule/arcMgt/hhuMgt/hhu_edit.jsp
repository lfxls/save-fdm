<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script> 
<script type="text/javascript" src="${ctx}/js/modules/mainModule/arcMgt/hhuMgt/hhu_edit.js"></script>

</head>
<body>
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="hhuForm" name="hhuForm">
				<table class="fluid-grid">
					<tr>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.hhuid"/><font color="red">*</font></label>
						</td>
						<td>
							<s:textfield id="hhuid" name="hhuid" cssClass="ui-input" onblur="checkLength(this,32);"></s:textfield>
						</td>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.model"/><font color="red">*</font></label>
						</td>
						<td>
							<s:select name="model" id="model" list="hhuModel" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.status"/><font color="red">*</font></label>
						</td>
						<td>
							<s:select name="status" id="status" list="hhuStatus" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.bcap"/></label>
						</td>
						<td>
							<s:textfield cssClass="ui-input" id="bcap" name="bcap"/>
							<%-- <s:textfield cssClass="ui-input" id="bcap" name="bcap" onblur="isNumbers()"/> --%>
						</td>
						<td>mAh</td>
					</tr>
					<tr>
						<td align="right">
							<label><s:text name="mainModule.arcMgt.hhuMgt.appvn"/></label>
						</td>
						<td>
							<s:textfield cssClass="ui-input" name="appvn" id="appvn" onblur="checkLength(this,32);"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan='5'>
							<input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="mainModule.arcMgt.hhuMgt.edit.save"/>">
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
		$('hhuid').style.backgroundColor='#cccccc';
		$('hhuid').readOnly = true;
	}
</script>
</html>