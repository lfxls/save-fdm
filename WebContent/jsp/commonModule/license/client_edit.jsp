<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/js/modules/commonModule/license/client_edit.js"></script>
</head>
<body>
	<form id="clientForm" name="clientForm">
		<table width="100%" cellspacing="5" cellpadding="5" border="0">
			<tr height="30px">
				<td align="right">客户编号：<font color="red">*</font></td>
				<td><input type="text" id="clientNo" name="clientNo" value="<s:property value='client.clientNo'/>"/></td>
				<td align="right">客户名称：<font color="red">*</font></td>
				<td><input type="text" id="clientName" name="clientName" value="<s:property value='client.clientName'/>"/></td>
				<td align="right">联系方式：</td>
				<td><input type="text" id="contact" name="contact" value="<s:property value='client.contact'/>"/></td>
			</tr>
			<tr height="30px">
				<td align="right">国家：</td>
				<td><input type="text" id="countryNo" name="countryNo" value="<s:property value='client.countryNo'/>"/></td>
				<td align="right">地址：</td>
				<td colspan="3"><input type="text" id="address" name="address" value="<s:property value='client.address'/>" size="65"/></td>
			</tr>
			<tr height="30px">
				<td align="right" colspan="3"><button type="button" onclick="saveClient('${optType}')" class="ext_btn">保存</button></td>
				<td align="left" colspan="3"><button type="button" onclick="cancelWin()" class="ext_btn">取消</button></td>
			</tr>
		</table>
	</form>
</body>
</html>