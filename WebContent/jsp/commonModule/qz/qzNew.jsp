<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/qzNew.js"> </script>
</head>
<body style="overflow-x:auto;overflow-y:hidden">
<s:form theme="simple" id="queryForm" >
<table class="fluid-grid">
	<tr>
		<td width="30%">
			<label class="ui-label"> <s:text name="common.qzsz.qzm"></s:text> <font color="red">*</font>:</label>
		</td>
		<td width="70%">
			<s:textfield name="qzm" id="qzm" cssClass="ui-input"></s:textfield>
		</td>
	</tr>
	<tr>			
		<td align="right" >
			<label class="ui-label"><s:text name="common.qz.smzq"></s:text> : </label> 
		</td>
		<td align="left" >
			<input id="yjyx" type="radio" name="yxq" value="0" checked="checked" style="margin-right:3px">
			<s:text name="common.qz.yjbc" ></s:text>
			
			<input id="yxsj" type="radio" name="yxq" style="margin:0 3px 0 25px">
			<input id="yxrq" name="yxrq" value="<s:property value='yxrq'/>" type="text" 
		      onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd ',isShowClear:'true'})"
		      class="Wdate" style="width: 100px" />
		</td>
	</tr>
	<tr>
		<td align="right" ><label  class="ui-label"><s:text name="common.qz.isgx"></s:text> : </label> </td>
		<td align="left" ><input type="checkbox" id="sfgx" name="sfgx">
		</td>
	</tr>
</table>
<div class="form-action" style="text-align:center" >
	<input type="button" class="ext_btn" value='<s:text name="common.qzsz.edit.btn.bc"/>' onclick="saveGroup()"/>
</div>
</s:form>
</body>
</html>