<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/qz.js"> </script>
</head>
<body style="overflow-x:auto;overflow-y:hidden">
<s:form theme="simple">
<s:hidden id="colNum" name="colNum"/>
<s:hidden id="flg" name="flg"/>
<s:hidden id="qzfl" name="qzfl"/>
<s:hidden id="qzlx" name="qzlx"/>
<table class="fluid-grid">
	<tr>
		<td>
			<label><s:text name="common.qz.yyqz"/></label>
		</td>
		<td>
			<s:select list="allGroupList" listKey="QZID" cssClass="ui-select" listValue = "QZMC" name= "allgroup" id = "allgroup" cssStyle = "width:150px"></s:select>
		</td>
		
	</tr>
	<tr>
		<td>
			<label><s:text name="common.qzsz.qzm"/></label>
		</td>
		<td>
			<s:textfield name="qzm" id= "qzm" size="25" value="" cssStyle = "width:150px" cssClass="ui-input"/>
		</td>
		<td>
		  	<label><s:text name="common.qz.smzq"/></label>
		</td>
		<td>
			<s:textfield name="smzq" cssClass="ui-input" id= "smzq" size="25" value="" cssStyle = "width:150px"/><label><s:text name="common.qz.bz"/></label>
		</td>
	</tr>
</table>
</s:form>
<div id="groupGrid"></div>
<br>
</body>
</html>