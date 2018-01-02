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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/selSurPlan.js"></script>
</head>
<body class="body">
	<form id="selSurPForm" name="selSurPForm" onsubmit="return false;" class="ui-form">
		<s:hidden id="sdzh" name="sdzh" />
		<s:hidden id="sszh" name="sszh" />
		<div class="ui-panel">
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
						    <td width="15%" align="right">
								<label><s:text name="common.kw.other.PU" /></label>
							</td>
							<td width="25%">
								<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true"
									cssStyle="background-color:#cccccc;" cssClass="ui-input" />
							    <s:hidden id="nodeTypedw" name="nodeTypedw"/>
								<s:hidden id="nodeIddw" name="nodeIddw"/>
							</td>
							<td width="1%">
								<a href="javascript:getDwTree();"
							   		title=<s:text name="mainModule.insMgt.plan.tip.selPU"></s:text>>
							   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
							</td>
							
							<td width="15%" align="right">
								<label><s:text name="common.kw.customer.CSN" /></label>
							</td>
							<td width="25%">
								<s:textfield id="cno" name="cno" onblur="checkLength(this,32)" cssClass="ui-input"/>
							</td>
							
							<td width="20%" rowspan="2" align="center">
								<input type="button"
									onclick="query();"
									value="<s:text name='mainModule.insMgt.plan.btn.query'/>"
									class="ext_btn" />
							</td>
						</tr>
						
						<%-- <tr>
							<td width="15%" align="right">
								<label><s:text name="mainModule.insMgt.plan.bussType" /></label>
							</td>
							<td width="25%">
								<s:select id="bussType" name="bussType"
									list="btList" listKey="BM" listValue="MC"
									onchange="changeBType(this.value)" cssClass="ui-select" />
							</td>
						</tr> --%>
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="selSurPGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>