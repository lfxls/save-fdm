<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/aqgl/cjqmysj/cjqmysj_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/aqgl/cjqmysj/cjqmysj.js"> </script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="nodeType" name="nodeType"/>
<s:hidden id="nodeId" name="nodeId"/>
<s:hidden id="nodeDwdm" name="nodeDwdm"/>
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
			<tr>
				<td width="10%" align="right"><label><s:text name="basicModule.aqgl.mygl.cjqmysj.dwdm"/></label></td>
				<td width="15%"><s:textfield id="nodeText" name="nodeText" disabled="true" cssClass="ui-input"/></td>
				<td width="15%" align="right"><label><s:text name="basicModule.aqgl.mygl.cjqmysj.cjqjh"/></label></td>
				<td width="15%"><s:textfield id="cjqjh" name="cjqjh" cssClass="ui-input"/></td>
				<td width="15%" align="right"><label><s:text name="basicModule.aqgl.mygl.cjqmysj.azsj"/></label></td>
				<td width="15%"><input id="kssj" name="kssj" value="<s:property value='kssj'/>" type="text" readonly="readonly" 
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:document.all.jssj.value})"
						class="Wdate"/>  
				</td>
				<td width="2%">~</td>
				<td width="15%"><input id="jssj" name="jssj" value="<s:property value='jssj'/>" type="text" readonly="readonly" 
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:document.all.kssj.value})"
						class="Wdate" /></td>
			</tr>
		</table>
	</div>
	<div class="ui-panel-footer">
		<input type="button" onclick="query();" class="ext_btn" value="<s:text name="basicModule.aqgl.mygl.cjqmysj.cx"/>">
	</div>
</div>

</form>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>