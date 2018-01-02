<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script> 
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/insteamMgt/insteamMgt_edit.js"></script>
</head>
<body>
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="insteamForm" name="insteamForm">
			<s:hidden id="tno" name="tno"/>
				<table class="fluid-grid">
					<tr>
						<td align="right"><label><s:text name="sysModule.qyjggl.insteamMgt.tname"/><font color="red">*</font></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="tname" name="tname" onblur="checkLength(this,256);"></s:textfield>
							<s:hidden id="tno" name="tno" />
						</td>
						<td align="right"><label><s:text name="sysModule.qyjggl.insteamMgt.status"/><font color="red">*</font></label></td>
						<td><s:select name="status" id="status" list="insteamsts" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
					</tr>
					<tr></tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qyjggl.insteamMgt.rspName"/><font color="red">*</font></label></td>
						<td>
							<s:textfield cssClass="ui-input" name="rsp_name" id="rsp_name" onblur="checkLength(this,256);"/>
						</td>
						<td align="right"><label><s:text name="sysModule.qyjggl.insteamMgt.pnum"/><font color="red">*</font></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="p_num" name="p_num" onblur="pnumIsNumbers()"/>
						</td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qyjggl.insteamMgt.phone"/></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="phone" name="phone" onblur="phoneIsNumbers()"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan='4'>
							<input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="sysModule.qyjggl.insteamMgt.edit.save"/>">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
</body>
<!-- <script type="text/javascript">
	var operateType = '${operateType}';
	if('edit' == operateType) {
		$('tname').style.backgroundColor='#cccccc';
		$('tname').readOnly = true;
	}
</script> -->
</html>