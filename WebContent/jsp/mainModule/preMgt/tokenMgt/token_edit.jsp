<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script> 
<script type="text/javascript" src="${ctx}/js/modules/mainModule/preMgt/tokenMgt/token_edit.js"></script>

</head>
<body>
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="tokenForm" name="tokenForm">
			<s:hidden id="tid" name="tid"/>
				<table class="fluid-grid">
					<tr>
						<td align="right"><label><s:text name="mainModule.preMgt.tokenMgt.msn"/><font color="red">*</font></label></td>
						<td>
							<s:textfield id="msn" name="msn" cssClass="ui-input" onblur="checkLength(this,32);"></s:textfield>
						</td>				
						<td align="right"><label><s:text name="mainModule.preMgt.tokenMgt.token"/><font color="red">*</font></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="token" name="token" size="27" onkeyup="Is20bitNumbers(this);"></s:textfield>
						</td>
						
					</tr>
					<tr>
						<td align="right"><label><s:text name="mainModule.preMgt.tokenMgt.cno"/></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="cno" name="cno" onblur="checkLength(this,32);"></s:textfield>
						</td>
						<td align="right"><label><s:text name="mainModule.preMgt.tokenMgt.orderid"/></label></td>
						<td>
							<s:textfield cssClass="ui-input" id="orderid" name="orderid" onblur="checkLength(this,32);"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" colspan='4'>
							<input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="mainModule.preMgt.tokenMgt.edit.save"/>">
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
</body>
</html>