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
	loadProperties('mainModule', 'mainModule_srvyMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/srvyMgt/fb/srvyFb.js"></script>
</head>
<body class="body">
	<form id="srvyFbForm" name="srvyFbForm" onsubmit="return false;" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.srvyMgt.fb.srvy.title"/>
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
							
							<td  width="13%" align="right">
								<label><s:text name="common.kw.plan.planno"/></label>
							</td>
							<td width="16%">
								<s:textfield id="pid" name="pid" cssClass="ui-input" />
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
								<label><s:text name="mainModule.insMgt.fb.plnStatus" /></label>
							</td>
							<td>
								<s:select id="plnStatus" name="plnStatus"
									list="plnStList" listKey="BM" listValue="MC"
									onchange="changePStatus(this.value)" cssClass="ui-select" />
							</td>
							<td align="right">
								<label><s:text name="common.kw.other.Status" /></label>
							</td>
							<td>
								<s:select id="status" name="status"
									list="stList" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" cssClass="ui-select" />
							</td>
							
							<td align="right">
								<label><s:text name="mainModule.insMgt.plan.bussType" /></label>
							</td>
							<td>
								<s:select id="bussType" name="bussType"
									list="btList" listKey="BM" listValue="MC"
									onchange="changeBType(this.value)" cssClass="ui-select" />
							</td>
							<td align="center">
								<input id="reset" type="button" onclick="fbReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="srvyFbGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>