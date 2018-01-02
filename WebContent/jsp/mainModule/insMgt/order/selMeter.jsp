<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/selMeter.js"></script>
</head>
<body class="body">
<form id="queryMeterForm" id="queryMeterForm" onsubmit="return false;" class="ui-form">
	<s:hidden id="bussType" name="bussType" />
	<s:hidden id="omsns" name="omsns" />
	<s:hidden id="domsns" name="domsns" />
	<s:hidden id="nmsns" name="nmsns" />
	<s:hidden id="dnmsns" name="dnmsns" />
	<s:hidden id="type" name="type" />
	<s:hidden id="uid" name="uid" />
	<table width="100%" class="fluid-grid">
	  <tr>
	 	<td width="6%" align="right">
	 		<label><s:text name="mainModule.insMgt.plan.meterNo"/></label>
	 	</td>
		<td width="30%">
			<input type="text" name="msn" id="msn" class="ui-input"/>
		</td>
		
		<td width="6%" align="right">
			<label><s:text name="common.kw.meter.MType" /></label>
		</td>
		<td width="30%">
			<s:select id="mType" name="mType" list="mTypeList" listKey="BM"
				listValue="MC" cssClass="ui-select" onchange="changeMType()"/>
		</td>
		
		<td rowspan="2" width="25%" align="center">
			<button class="ext_btn" onclick="query();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
		
		
	  </tr>
	  
	  <tr>
		<td width="6%" align="right">
			<label><s:text name="common.kw.meter.MCMethod" /></label>
		</td>
		<td width="30%"> 
			<s:select id="wir" name="wir" list="wirList" listKey="BM"
				listValue="MC" cssClass="ui-select" onchange="changeWir()"/>
		</td>
		
		<td width="6%" align="right">
			<label><s:text name="mainModule.insMgt.plan.meterMode" /></label>
		</td>
		<td width="30%">
			<s:select id="mMode" name="mMode" list="mModeList" listKey="BM"
				listValue="MC" cssClass="ui-select" onchange="changeMMode()"/>
		</td>
	  </tr>
	</table>
</form>
	<div id="MeterGrid"></div>
</body>
</html> 
