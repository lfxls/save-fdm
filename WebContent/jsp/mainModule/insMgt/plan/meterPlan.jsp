<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/meterPlan.js"></script>
</head>
<body class="body">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current"><a href="#"><s:text
						name="mainModule.insMgt.plan.meter" /></a>
			</li>
			<%-- <li class="ui-tab-item"><a
				href="${ctx}/main/insMgt/insPlan!initDInsP.do"><s:text
						name="mainModule.insMgt.plan.dcu" /></a>
			</li>
			<li class="ui-tab-item"><a
				href="${ctx}/main/insMgt/insPlan!initCInsP.do"><s:text
						name="mainModule.insMgt.plan.col" /></a>
			</li> --%>
		</ul>
	</div>
	
	<div class="hr10"></div>
	
	<form id="meterPlanForm" name="meterPlanForm" onsubmit="return false;" class="ui-form">
	
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.plan.meter.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
						    <td width="13%" align="right">
								<label><s:text name="common.kw.other.PU" /></label>
							</td>
							<td width="16%">
								<s:textfield id="nodeText" name="nodeText" readonly="true"
									cssStyle="background-color:#cccccc;" cssClass="ui-input" />
							    <s:hidden id="nodeType" name="nodeType"/>
								<s:hidden id="nodeId" name="nodeId"/>
								<s:hidden id="nodeDwdm" name="nodeDwdm"/>
							</td>
							
							<td width="13%" align="right">
								<label><s:text name="common.kw.customer.CSN" /></label>
							</td>
							<td width="16%">
								<s:textfield id="cno" name="cno" onblur="checkLength(this,32)" cssClass="ui-input"/>
							</td>
							
							<td width="13%" align="right">
								<label><s:text name="common.kw.other.Status" /></label>
							</td>
							<td width="16%">
								<s:select id="status" name="status"
									list="stList" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" cssClass="ui-select" />
							</td>
							
							<td width="19%" align="center">
								<input type="button"
									onclick="query();"
									value="<s:text name='mainModule.insMgt.plan.btn.query'/>"
									class="ext_btn" />
							</td>
						</tr>
						
						<tr>
							<td align="right">
								<label><s:text name="mainModule.insMgt.plan.bussType" /></label>
							</td>
							<td>
								<s:select id="bussType" name="bussType"
									list="btList" listKey="BM" listValue="MC"
									onchange="changeBType(this.value)" cssClass="ui-select" />
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td align="center">
								<input id="reset" type="button" onclick="mpReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>
						
						
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="meterPGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>