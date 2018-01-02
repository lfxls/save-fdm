<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/rzgl/txrz/txrz_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/txrz/txrz.js"></script>
</head>
<body>
<form id="txform" name="txform">
<div class="ui-panel">
	<div class="ui-panel-body" style="padding:10px">
		<table class="fluid-grid">
			<tr>
				<td width="10%" align="right"><label><s:text name="sysModule.rzgl.txrz.dw" /></label></td>
				<td width="15%">
					<s:textfield id="nodeText" name="nodeText" disabled="true" cssClass="ui-input"/>
					<s:hidden id="nodeId" name="nodeId"/>
				</td>
				<td width="10%" align="right"><label><s:text name="sysModule.rzgl.txrz.czy" /></label></td>
				<td width="15%"><s:textfield id="czybm" name="czybm" cssClass="ui-input"/></td>
				
				<td width="10%" align="right"><label><s:text name="sysModule.rzgl.txrz.czlx" /></label></td>
		    	<td width="20%"><s:select id="czlx" name="czlx" list="czlxList" listKey="CZID" listValue="CZMC"
						headerKey="" headerValue="%{headerValue}" cssClass="ui-select" cssStyle="width:200px"/></td>
			</tr>
			<tr>
				<td align="right"><label><s:text name="sysModule.rzgl.txrz.zdjh" /></label></td>
				<td><s:textfield id="zdjh" name="zdjh" cssClass="ui-input"/></td>
				<td align="right"><label><s:text name="sysModule.rzgl.txrz.sjxmc" /></label></td>
				<td><s:textfield id="csxmc" name="csxmc" cssClass="ui-input"/></td>
				<td align="right"><label><s:text name="sysModule.rzgl.txrz.sj" /></label></td>
				<td><input name="kssj" id="kssj" value="${kssj}" type="text" style="width:90px"
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'jssj\')}'})" class="Wdate"/>&nbsp;&nbsp;~&nbsp;&nbsp;
					<input name="jssj" id="jssj" value="${jssj}" type="text" style="width:90px"
					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'kssj\')}',maxDate:'%y-%M-%d'})" class="Wdate"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="ui-panel-footer">
		<input type="button" onclick="query();" class="ext_btn" value="<s:text name="sysModule.rzgl.txrz.btn.query"/>">
	</div>
</div>
</form>
<div class="hr10"></div>

<div id="txGrid"></div>
</body>
</html>