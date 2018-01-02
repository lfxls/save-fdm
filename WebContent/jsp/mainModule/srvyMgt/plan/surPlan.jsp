<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_srvyMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/srvyMgt/plan/surPlan.js"></script>
</head>
<body class="body">
	
	<form id="surPlanForm" name="surPlanForm" onsubmit="return false;" class="ui-form">
	
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.srvyMgt.plan.survey.title"/>
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
									value="<s:text name='common.kw.other.query'/>"
									class="ext_btn" />
							</td>
						</tr>
						
						
						
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="surPGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>