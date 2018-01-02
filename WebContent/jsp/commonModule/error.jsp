<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/jsp/meta.jsp" %>
<style type="text/css">
<!--
body {
	margin-top: 40px;
}
-->
</style>
</head>
<body>
<div style="width:100%; height:300px; overflow: auto;">
<table width="400px" align="center" cellspacing="0" cellpadding="0" style="border:1px solid #99BBE8;">
	<tr height="80px"> 
		<td align="center" style="font-size: 13px; font-weight: bold;">
		 	<a onclick="javascript:showErrorMsg();">
		 	<img src="${ctx}/public/extjs/resources/images/default/window/icon-error.gif" height="13" width="13">
		 	&nbsp;<s:text name="session.error"/>&nbsp;</a>
		</td>
	</tr>
	<s:if test="#parameters.stack != null">
		<tr id="showErrorMsgDiv" style="display: none;">
			<td style="WIDTH:680px; word-wrap:break-word;"><pre><s:property value="#parameters.stack" /></pre></td>
		</tr>
	</s:if>
	<s:if test="exceptionStack!= null">
		<tr id="showErrorMsgDiv" style="display: none;">
			<td style="WIDTH:680px; word-wrap:break-word;"><pre><s:property value="exceptionStack" /></pre></td>
		</tr>
	</s:if>
</table>
<!-- a style="font-size: 13px; font-weight: bold;" href="javascript:history.go(-1)"><s:text name="session.error"/></a-->

</div>
<script language="javascript">
function showErrorMsg(){
  var showErrorMsgDiv = $("showErrorMsgDiv");  
  if (showErrorMsgDiv.style.display == "none"){
  	showErrorMsgDiv.style.display ="block";
  }else{
  	showErrorMsgDiv.style.display ="none";
  }  
}
</script>
</body>
</html>
