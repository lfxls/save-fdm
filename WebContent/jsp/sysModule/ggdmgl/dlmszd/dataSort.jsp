<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/dataSort_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/dataSort.js"> </script>
</head>
<body class="body">
<s:hidden id="lang" name="lang"></s:hidden>
<s:hidden id="opt" name="opt"></s:hidden>
<input type="hidden" id="lang_exist_flag" name="lang_exist_flag" value="${dlms_data_sort.LANG_EXIST_FLAG }"/>
<s:hidden id="dlms_sub_protocol" name="dlms_sub_protocol"></s:hidden>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dataSort.text.code"/></label></td>
		<td width="15%">
	  		<input id="item_sort" name="item_sort" value="${dlms_data_sort.ITEM_SORT }" class="ui-input"/>
	  	</td>
	 </tr>
	 <tr>
	 	<td align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.dataSort.text.name"/></label></td>
	 	<td>
	 		<input id="item_sort_name" name="item_sort_name" value="${dlms_data_sort.ITEM_SORT_NAME }" class="ui-input"/>
	 	</td>
	 </tr>
	 <tr>
	 	<td align="center" colspan="2">
	    	<input type="button" onClick="saveDataSort()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.dataSort.button.save"/>">
	  	</td>
	 </tr>
</table>
</html>