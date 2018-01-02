<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/addJg_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/addJg.js"> </script>
</head>
<body class="body">
<table class="fluid-grid">
	<tr>
	  <td width="30%" align="right"><label><s:text name="sysModule.qyjggl.jggl.bmmc"/>:</label></td>
	  <td align="left">
	  	<s:hidden id="sjbmid" name="sjbmid"/>
	  	<s:hidden id="dwdm" name="dwdm"/>
	  	<s:textfield id="jgmc" name="jgmc" cssClass="ui-input" onblur="checkLength(this,256);"/>
	  </td>
	</tr>	
	<tr style="display: none;">
	  <td><label><s:text name="sysModule.qyjggl.jggl.bmlx"/>:</label></td>
	  <td>
	  	<s:select id="jglx" name="jglx" list="jglxList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select>
	  </td>
	</tr>	
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>
	<tr>
	  <td align="center" colspan="2"></td>
	</tr>	
	<tr>
	  <td align="center" colspan="2">			  	
	  	&nbsp;<input type="button" onclick="addJg()" class="ext_btn" value="<s:text name="sysModule.qyjggl.jggl.save"/>">
	  </td>
	</tr>	
</table>
</html>