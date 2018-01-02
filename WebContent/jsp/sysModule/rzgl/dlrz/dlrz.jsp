<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/sysModule/rzgl/dlrz/dlrz_${appLang}.js"></script>
	<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/dlrz/dlrz.js"></script>
</head>
<body class="body">
	<form id="queryForm" name="queryForm" onsubmit="return false;" class="ui-form">
		<s:hidden id="nodeType" name="nodeType"/>
		<s:hidden id="nodeId" name="nodeId"/>
		<s:hidden id="nodeDwdm" name="nodeDwdm"/>
		
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="sysModule.dlrz.query.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<td width="10%" align="right">
								<label><s:text name="sysModule.rzgl.dlrz.dw" />:</label>
							</td>
							<td width="17%" align="left">
								<s:textfield id="nodeText" name="nodeText" cssStyle="background-color:#cccccc;" 
									   readonly="true" cssClass="ui-input"/>
							</td>
							
							<td width="10%" align="right">
								<label><s:text name="sysModule.rzgl.dlrz.czy" />:</label>
							</td>
							<td width="17%" align="left">
								<s:textfield name="czy" id="czy" cssClass="ui-input"/>
							</td>
							
							<td width="10%" align="right">
								<label><s:text name="sysModule.rzgl.dlrz.ip" />:</label>
							</td>
							<td width="17%" align="left">
								<s:textfield name="ip" id="ip" cssClass="ui-input"/>
							</td>
							
							<td width="19%" align="center">
								<input type="button" onclick="query();" class="ext_btn"
								value="<s:text name="sysModule.rzgl.dlrz.btn.query"/>">
							</td>
						</tr>
						
						<tr>		
						   	<td width="10%" align="right">
								<label><s:text name="sysModule.rzgl.dlrz.sj" />:</label>
							</td>
						    <td width="17%" align="left">
								<input id="kssj" name="kssj"
								value="<s:property value='kssj'/>" type="text"
								onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'jssj\')||\'%y-%M-%d\'}'})"
								class="Wdate" readonly="readonly" style="width:47%;" /> 
								~
								<input id="jssj"
								name="jssj" value="<s:property value='jssj'/>" type="text"
								onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'kssj\')}',maxDate:'%y-%M-%d'})"
								class="Wdate" readonly="readonly" style="width:47%;" />
							</td>
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						   <td width="19%" align="center">
							<input id="re" type="button" onclick="Reset();" value="<s:text name="common.kw.other.reset"/>" class="ext_btn" />
						   </td>
						</tr>
					</table>			
				</div>
				
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>