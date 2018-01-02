<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<title><s:text name="sysModule.ggdmgl.dlmszd.title.pageTitle"/></title>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/dlmszd/dlmszd_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/dlmszd/dlmszd.js"> </script>
<style>
	#tabtest{padding:0px}
	.fiedtext{text-align: right;overflow: hidden;white-space:nowrap; }
</style>
</head>
<body class="body">
<script type="text/javascript">
	Ext.onReady(function(){
		var activeFlag = false;
		<c:forEach items="${dlmsDataSortList}" var="dataSort">
			activeFlag = false;
			<c:if test="${dataSort.ITEM_SORT eq item_sort}">
				activeFlag = true;
			</c:if>
			addTab('${dataSort.ITEM_SORT}' ,'${dataSort.ITEM_SORT_NAME}' ,activeFlag);
		</c:forEach>
	});
</script>
<table class="fluid-grid">
	<tr>
		<td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.text.yy"/></label></td>
	  	<td width="15%"><s:select id="lang" name="lang" list="langList" listKey="BM" listValue="MC" cssClass="ui-select" onchange="query();"></s:select></td>
	    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.dlmszd.text.dlms_sub_protocol"/></label></td>
	   	<td width="20%"><s:select id="dlms_sub_protocol" name="dlms_sub_protocol" list="dlms_subList" listKey="DLMS_SUB_PROTOCOL" listValue="DLMS_SUB_NAME" cssClass="ui-select" onchange="query();"></s:select></td>
	    <td><input type="button" onClick="addDlmsSub()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.button.add_dlmssub"/>">
	    	&nbsp;&nbsp;
	    	<input type="button" onClick="expDlmsSub()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.button.exp_dlmssub"/>">
	  </td>
	</tr>
</table>
<div class="hr10"></div>
<div class="ui-panel">
	<div class="ui-panel-header">
		<s:text name="sysModule.ggdmgl.dlmszd.title.dlmsSub"/>
	</div>
	<div class="ui-panel-body">
		<div class="ui-panel-item">
		<table class="fluid-grid">
			<tr height="20">
				<td width="10%"><label class="ui-label"><s:text name="sysModule.ggdmgl.dlmszd.text.gymc"/>：</label></td>
				<td width="30%">${dlms_sub.DLMS_SUB_NAME }</td>
				<td width="10%"><label class="ui-label"><s:text name="sysModule.ggdmgl.dlmszd.text.gymc_parent"/>：</label></td>
				<td width="30%">${dlms_sub.BASE_PROTOCOL_NAME }</td>
				<td width="20%" rowspan="2" >
					<input type="button" onClick="editDlmsSub()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.button.edit_dlmssub"/>">
					&nbsp;
					<input type="button" onClick="deleteDlmsSub()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.button.del_dlmssub"/>">
				</td>
			</tr>
			<tr height="20">
				<td class="fiedtext"><label class="ui-label"><s:text name="sysModule.ggdmgl.dlmszd.text.status"/>：</label></td>
				<td>
					<c:if test="${dlms_sub.STATUS eq '1'}"><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.select.status1"/></c:if>
					<c:if test="${dlms_sub.STATUS eq '0'}"><s:text name="sysModule.ggdmgl.dlmszd.dlmsSub.select.status0"/></c:if>
				</td>
				<td class="fiedtext"><label class="ui-label"><s:text name="sysModule.ggdmgl.dlmszd.text.desc"/>：</label></td>
				<td>${dlms_sub.REMARK }</td>
			</tr>
		</table>
		</div>
	</div>
</div>
<div class="hr10"></div>
<div class="ui-panel" id="dlmsParamsFieldset">
	<div class="ui-panel-header">
		<s:text name="sysModule.ggdmgl.dlmszd.title.dlmsParams"/>
	</div>
	<div class="ui-panel-body">
		<div id="button_addDataSort">
			<s:text name="sysModule.ggdmgl.dlmszd.text.addDataSort"/>
			<input type="button" onClick="addDataSort()" class="ext_btn" value="<s:text name="sysModule.ggdmgl.dlmszd.button.add_dataSort"/>">
		</div>
		<div id="div_tabs"></div>
	</div>
</div>
</html>