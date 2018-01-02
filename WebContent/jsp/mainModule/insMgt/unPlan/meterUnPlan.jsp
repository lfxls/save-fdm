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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/unPlan/meterUnPlan.js"></script>
</head>
<body class="body">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current"><a href="#"><s:text
						name="mainModule.insMgt.plan.meter" /></a>
			</li>
			<%-- <li class="ui-tab-item"><a
				href="${ctx}/main/insMgt/insUnPlan!initDcuUnP.do"><s:text
						name="mainModule.insMgt.plan.dcu" /></a>
			</li>
			<li class="ui-tab-item"><a
				href="${ctx}/main/insMgt/insUnPlan!initColUnP.do"><s:text
						name="mainModule.insMgt.plan.col" /></a>
			</li> --%>
		</ul>
	</div>
	
	<div class="hr10"></div>
	
	<form id="meterUnPlanForm" name="meterUnPlanForm" onsubmit="return false;" class="ui-form">
	
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.plan.meter.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<td width="10%" align="right">
								<label><s:text name="common.kw.workorder.WOID"/></label>
							</td>
							<td width="17%">
								<s:textfield id="woid" name="woid" cssClass="ui-input" />
							</td>
							
							<td width="10%" align="right">
								<label><s:text name="common.kw.other.Status"/></label>
							</td>
							<td width="17%">
								<s:select id="status" name="status" cssClass="ui-select"
									list="stList" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" />
							</td>
							
							<td width="10%" align="right">
								<label><s:text name="mainModule.insMgt.order.pop" /></label>
							</td>
							<td width="17%">
								<s:textfield id="popName" name="popName"
									cssStyle="background-color:#cccccc;" readonly="true"
									cssClass="ui-input"></s:textfield>&nbsp;
								<s:hidden id="popid" name="popid" />
								<a href="javascript:queryPop();"
							   		title=<s:text name="mainModule.insMgt.order.tip.selPop"></s:text>>
							   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" />
							   	</a>
							</td>
							
							<td width="19%" align="center">
								<input type="button"
									onclick="query();"
									value="<s:text name='mainModule.insMgt.plan.btn.query'/>"
									class="ext_btn" />
							</td>
						</tr>
						<tr>
							<td colspan="6"></td>
							<td align="center">
								<input id="reset" type="button" onclick="unPlnReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>
					</table>
				</div>
				
			    <div class="ui-panel-item">
					<div id="meterUnPGrid"></div>
				</div>
			</div>
		</div>	
	
	</form>
</body>
</html>