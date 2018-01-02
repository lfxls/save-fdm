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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/colPlan.js"></script>
</head>
<body class="body">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item"><a 
				href="${ctx}/main/insMgt/insPlan!init.do"><s:text
						name="mainModule.insMgt.plan.meter" /></a>
			</li>
			<li class="ui-tab-item"><a href="${ctx}/main/insMgt/insPlan!initDInsP.do"><s:text
						name="mainModule.insMgt.plan.dcu" /></a>
			</li>
			<li class="ui-tab-item ui-tab-item-current"><a
				href="#"><s:text
						name="mainModule.insMgt.plan.col" /></a>
			</li>
		</ul>
	</div>
	
	<div class="hr10"></div>
	
	<form id="colPlanForm" name="colPlanForm" onsubmit="return false;" class="ui-form">
	
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.plan.col.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
						    <td width="15%" align="right">
								<label><s:text name="common.kw.other.PU" /></label>
							</td>
							<td width="25%">
								<s:textfield id="nodeText" name="nodeText" readonly="true"
									cssStyle="background-color:#cccccc;" cssClass="ui-input" />
							    <s:hidden id="nodeType" name="nodeType"/>
								<s:hidden id="nodeId" name="nodeId"/>
								<s:hidden id="nodeDwdm" name="nodeDwdm"/>
							</td>
							
							<td width="15%" align="right">
								<label><s:text name="mainModule.insMgt.plan.colModel" /></label>
							</td>
							<td width="25%">
								<s:select id="colM" name="colM"
									list="colMList" listKey="BM" listValue="MC"
									onchange="changeColM(this.value)" cssClass="ui-select" />
							</td>
							
							<td width="20%" rowspan="2" align="center">
								<input type="button"
									onclick="query();"
									value="<s:text name='mainModule.insMgt.plan.btn.query'/>"
									class="ext_btn" />
							</td>
						</tr>
						
						<tr>
							<td width="15%" align="right">
								<label><s:text name="common.kw.other.Status" /></label>
							</td>
							<td width="25%">
								<s:select id="status" name="status"
									list="stList" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" cssClass="ui-select" />
							</td>
							
							<td width="15%" align="right">
								<label><s:text name="mainModule.insMgt.plan.bussType" /></label>
							</td>
							<td width="25%">
								<s:select id="bussType" name="bussType"
									list="btList" listKey="BM" listValue="MC"
									onchange="changeBType(this.value)" cssClass="ui-select" />
							</td>
							
						</tr>
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="colPGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>