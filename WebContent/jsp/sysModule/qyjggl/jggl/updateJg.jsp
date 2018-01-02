<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/addJg_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/updateJg.js"> </script>
</head>
<body class="body">
<table class="fluid-grid">
	<tr height="80%"></tr>
	<tr>
	  <td></td>
	  <td width="30%" align="right"><label><s:text name="sysModule.qyjggl.jggl.bmmc"/>:</label></td>
	  <td>
	  	<s:textfield id="bmmc" name="bmmc" cssClass="ui-input"/>
	  	<s:hidden id="bmid" name="bmid" value="%{bmid}"/>
	  </td>
    </tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>    
	<tr>
    	<td></td>
	  <td align="center" colspan="2">				  	
	  	&nbsp;<input type="button" onclick="updateJg()" class="ext_btn" value="<s:text name="sysModule.qyjggl.jggl.save"/>">
	  </td>
	</tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>		
</table>
</html>