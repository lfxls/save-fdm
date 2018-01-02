<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/addDw_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/addDw.js"> </script>
</head>
<body class="body">
<table class="fluid-grid">
    <tr></tr>
	<tr>
	  <td width="30%" align="right"><label><s:text name="sysModule.qyjggl.jggl.dwmc"/>:</label></td>
	  <td><s:textfield id="dwmc" name="dwmc" cssClass="ui-input"/>
	  	<s:hidden id="sjdwdm" name="sjdwdm" value="%{dwdm}"/>
	  	<s:hidden id="sjdwjb" name="sjdwjb" value="%{dwjb}"/>
	  </td>
    </tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>    
	<tr>
	  <td align="center" colspan="2">				  	
	  	&nbsp;<input type="button" onclick="addDw()" class="ext_btn" value="<s:text name="sysModule.qyjggl.jggl.save"/>">
	  </td>
	</tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>		
</table>
</html>