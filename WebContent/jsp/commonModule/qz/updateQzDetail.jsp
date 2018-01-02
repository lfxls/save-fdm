<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/updateQzDetail_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/updateQzDetail.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
</head>
<body style="overflow-x:auto;overflow-y:hidden">
<s:form theme="simple" id="queryForm" >
	<s:hidden name="qzid" id ="qzid"/>
	<s:hidden name="qzfl" id ="qzfl"/>
	<s:hidden name="qzlx" id ="qzlx"/>
	<s:hidden name="smzq" id ="smzq"/>
	<s:hidden name="sfgxs" id ="sfgxs"/>
	<table class="fluid-grid">
		<tr>
			<td width="30%">
				<label class="ui-label"> <s:text name="common.qzsz.qzm"></s:text> <font color="red">*</font>:</label>
			</td>
			<td width="70%">
				<s:textfield name="qzm" id="qzm" cssClass="ui-input" cssStyle="width:180px"></s:textfield>
			</td>
		</tr>
		<tr>
			<td align="right" >
			 	 <label  class="ui-label"><s:text name="common.qz.smzq"></s:text> : </label> 
			</td>
			<td align="left" >
			 	<input id="yjyx" type="radio" name="yxq" value="0" checked="checked" style="margin-right:3px">
				<s:text name="common.qz.yjbc" ></s:text>
				<input id="yxsj" type="radio" name="yxq" style="margin:0 3px 0 25px">
				<input id="yxrq" name="yxrq" type="text"
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
</s:form>
<div class="hr10"></div>
<div class="form-action" style="text-align:center">
		<input type="button" class="ext_btn" onclick="saveGroup()"
			value="<s:text name="common.qzsz.edit.btn.bc"></s:text>" />
</div>
<div id="groupGrid"></div>

</body>
</html>