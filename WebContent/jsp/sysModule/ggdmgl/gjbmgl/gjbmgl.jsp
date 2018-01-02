<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/gjbmgl/gjbm_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/gjbmgl/gjbmgl.js"></script>
</head>
<body class="body">
<form id="gjbmForm" style="margin-top: 5px">
<s:hidden id="czid"	name="czid" />
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gjbm"/></label> :<font color="red">*</font></td>
		<td width="30%"><s:textfield id="gjbm" name="gjbm"  maxLength="20" cssClass="ui-input" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></s:textfield></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gjmc"/></label> :<font color="red">*</font></td>
		<td><s:textfield id="gjmc" name="gjmc" maxLength="256" cssClass="ui-input"></s:textfield></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.lb"/></label> :<font color="red">*</font></td>
		<td><s:select id="gjlb" name="gjlb" list="gjlbls" listKey="BM"
			listValue="MC" cssClass="ui-select" /></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gylx"/></label> :<font color="red">*</font></td>
		<td><s:select id="gylx" name="gylx" list="gylxls" listKey="BM"
			listValue="MC" cssClass="ui-select" /></td>
	</tr>

	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.gjdj"/></label> :<font color="red">*</font></td>
		<td><s:select id="gjdj" name="gjdj" list="gjdjls" listKey="BM"
			listValue="MC" cssClass="ui-select" /></td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="sysModule.ggdmgl.gjbmgl.yy"/></label> :<font color="red">*</font></td>
		<td><s:select id="yy" name="yy" list="yyls" listKey="BM"
			listValue="MC" cssClass="ui-select" /></td>
	</tr>
</table>
</form>
<div style="margin-top: 5px;" align="center">
	<input type="button" class="ext_btn" onclick="save()" value="<s:text name="basicModule.dagl.sim.simda.save"/>">
</div>
</body>
</html>