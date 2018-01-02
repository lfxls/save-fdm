<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<title><s:text name="sysModule.ggdmgl.dlmszd.title.pageTitle"/></title>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/queryItem_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/queryItem.js"> </script>
</head>
<body class="body">
<s:hidden id="dlms_sub_protocol" name="dlms_sub_protocol"/>
<s:hidden id="item_sort" name="item_sort"/>
<s:hidden id="lang" name="lang"/>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd_queryItem.text.sjxmc"/></label></td>
		<td width="15%"><s:textfield id="item_name" name="item_name" cssClass="ui-input"/></td>
	    <td width="10%" align="right"><label>ID</label></td>
	    <td width="20%"><s:textfield id="item_id_query" name="item_id_query" cssClass="ui-input"></s:textfield></td>
		<td>
	    	<input type="button" onClick="queryItem()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd_queryItem.button.item_query"/>">
	  </td>
	</tr>
</table>
<div class="divider-dashed"></div>
<div id="div_grid"></div>
</html>