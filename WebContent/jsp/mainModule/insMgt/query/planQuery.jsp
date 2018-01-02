<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <%@ include file="/jsp/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css"/>
		<script type="text/javascript">
			//加载国际化资源文件
			loadProperties('mainModule', 'mainModule_insMgt');
		</script>
		<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/query/planQuery.js"> </script>
	</head>
	<body class="body">	
	<form id="planQueryForm" name="planQueryForm" onsubmit="return false;" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.plan.detail.title"/>
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
								<label><s:text name="common.kw.plan.planno"/></label>
							</td>
							<td width="16%">
								<s:textfield id="pid" name="pid" cssClass="ui-input" />
							</td>
							
							<td width="13%" align="right">
								<label><s:text name="common.kw.other.Status"/></label>
							</td>
							<td width="16%">
								<s:select id="status" name="status" cssClass="ui-select"
									list="stLs" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" />
							</td>

							<td width="19%" align="center">
								<input type="button"
								id="qy" class="ui-button ui-button-blue ui-button-small"
								onclick="query();"
								value="<s:text name="mainModule.insMgt.plan.btn.query"/>">
							</td>
						</tr>
						
						<tr>
							<td align="right">
								<label><s:text name="mainModule.insMgt.plan.bussType" /></label>
							</td>
							<td>
								<s:select id="bussType" name="bussType"
									list="btLs" listKey="BM" listValue="MC"
									onchange="changeBType(this.value)" cssClass="ui-select" />
							</td>
							
							<td align="right">
								<label><s:text name="mainModule.insMgt.plan.planType" /></label>
							</td>
							<td>
								<s:select id="pType" name="pType"
									list="ptLs" listKey="BM" listValue="MC"
									onchange="changePType(this.value)" cssClass="ui-select" />
							</td>
							
							<td align="right">
								<label><s:text name="common.kw.workorder.WOID"/></label>
							</td>
							<td>
								<s:textfield id="woid" name="woid" cssClass="ui-input" />
							</td>
							
							<td align="center">
								<input id="reset" type="button" onclick="pReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<label><s:text name="common.kw.customer.CSN" /></label>
							</td>
							<td>
								<s:textfield id="cno" name="cno" onblur="checkLength(this,32)" cssClass="ui-input"/>
							</td>
							<td align="right">
								<label><s:text name="mainModule.insMgt.plan.nmsn" /></label>
							</td>
							<td>
								<s:textfield id="nmsn" name="nmsn" onblur="checkLength(this,32)" cssClass="ui-input"/>
							</td>
						</tr>
					</table>				
				</div>
				
				<div class="ui-panel-item">
				 	<div id="planQueryGrid"></div> 
				</div>
			</div>
		</div>
	</form>	 
	</body>
</html>