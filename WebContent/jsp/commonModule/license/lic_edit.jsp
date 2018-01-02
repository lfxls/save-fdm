<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript"
	src="${ctx}/js/locale/commonModule/license/ligMgt_${appLang}.js"></script>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/license/lic_edit.js"></script>
</head>
<body>
	<form id="licForm" name="licForm">
		<input type="hidden" id="clientNo" name="clientNo" value="${clientNo}" />
		<input type="hidden" id="licNo" name="licNo" value="${licNo}" />
		<table width="100%" cellspacing="5" cellpadding="5">
			<tr height="30px">
				<td align="right">电表数量：</td>
				<td><input type="text" id="meterNum" name="meterNum" value="${license.meterNum }"/></td>
				<td align="right">过期时间：</td>
				<td><input id="expDate" name="expDate" type="text" size="15"
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:true})"
					class="Wdate" readonly="readonly" value="${license.expDate }"/></td>
			</tr>
			<tr height="30px">
				<td align="right" colspan="2"><button type="button"
						onclick="saveLic('${optType}')" class="ext_btn">保存</button></td>
				<td align="left" colspan="2"><button type="button"
						onclick="cancelWin()" class="ext_btn">取消</button></td>
			</tr>
		</table>
	</form>
</body>
</html>