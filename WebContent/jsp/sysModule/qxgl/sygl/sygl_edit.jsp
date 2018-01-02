<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/sygl/sygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/sygl/sygl_edit.js"></script>
</head>
<body class="body">
<form id="sygl">
<s:hidden id="syid" name="sygl.syid"/>
<s:hidden id="lang" name="sygl.lang"/>
<s:hidden id="xtmr" name="sygl.xtmr"/>
<table class="fluid-grid">
	<tr>
		<td width="30%" align="right"><label><s:text name="sysModule.qxgl.sygl.edit.symc"/><font color="red">*</font></label></td>
		<td width="70%" align="center"><s:textfield id="symc" name="sygl.symc" size="40" cssClass="ui-input" onblur="checkLength(this,30);"/></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.qxgl.sygl.edit.url"/><font color="red">*</font></label></td>
		<td align="center"><s:textfield id="url" name="sygl.url" size="40" cssClass="ui-input" onblur="checkLength(this,100);"/></td>
	</tr>	
	<tr>
		<td align="right"><label><s:text name="sysModule.qxgl.sygl.edit.ms"/></label></td>
		<td align="center"><s:textfield id="ms" name="sygl.ms" size="40" cssClass="ui-input" onblur="checkLength(this,100);"/></td>
	</tr>
	<tr>
		<td align="right"><s:checkbox id="sylx" name="sylx" /></td>
		<td align="left"><label><s:text name="sysModule.qxgl.sygl.edit.sylx"/></label></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="button" onclick="saveSy()" value='<s:text name="sysModule.qxgl.sygl.edit.save"/>' class="ext_btn"/>
		</td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
	//首页类型
	var sylx = '${sygl.xtmr}';
	if('01' == sylx){
		$('sylx').checked = true;
	}else{
		$('sylx').checked = false;
	}
</script>
</html>