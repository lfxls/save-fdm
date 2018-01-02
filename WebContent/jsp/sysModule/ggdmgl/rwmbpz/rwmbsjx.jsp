<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/rwmbpz/rwmbsjx_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/rwmbpz/rwmbsjx.js"> </script>
</head>
<body class="body">
<s:hidden id="rwsx" name="rwsx"/>
<s:hidden id="zdgylx" name="zdgylx"/>
<table class="fluid-grid">
	<tr>
	    <td width="2%"></td>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.rwmbpz.rwmbsjx.text.sjxmc"/></label></td>
	  	<td width="15%">
	  		<s:textfield id="sjxmc" name="sjxmc" cssClass="ui-input"/>
	  	</td>
	  	<td width="10%" align="right">
	  		<label><s:text name="sysModule.ggdmgl.rwmbpz.rwmbsjx.text.sjxbm"/></label>
	  	</td>
	  	<td width="20%">
	  		<s:textfield id="sjxbm" name="sjxbm" cssClass="ui-input"/>
	  	</td>
	  	<td width="2%"></td>
		<td>
			<input type="button" onClick="query()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.rwmbpz.rwmbsjx.btn.cx"/>">
		</td>
	</tr>
</table>

<div id=dxsjxGrid></div>
<div class="hr10"></div>
<div id="rwsjxGrid"></div>
</html>