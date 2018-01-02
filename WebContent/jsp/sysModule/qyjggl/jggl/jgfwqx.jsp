<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/jggl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/jgfwqx.js"> </script>
</head>
<body class="body">
<table class="fluid-grid">
	<tr>
		<td valign="top"><div id="tree"></div></td>
	</tr>
	<tr>
		<td valign="top">
			<input id="sffg" name="sffg" type="checkbox">
			<label><s:text name="sysModule.qyjggl.jggl.fgczyqx"/>:</label>&nbsp;
			<s:hidden id="dwdm" name="dwdm"/>
			<s:hidden id="bmid" name="bmid"/>
			&nbsp;<input type="button" onclick="updJg();" class="ext_btn" value="<s:text name="sysModule.qyjggl.jggl.xgqx"/>">
		</td>
	</tr>
</table>
</html>