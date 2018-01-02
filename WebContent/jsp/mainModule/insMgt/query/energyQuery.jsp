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
		<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/query/energyQuery.js"> </script>
	</head>
	<body class="body">	
	<form id="energyQueryForm" name="energyQueryForm" onsubmit="return false;" class="ui-form">
		<s:hidden id="dlsjx" name="dlsjx"></s:hidden>
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.query.energy.title"/>
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
								<label><s:text name="common.kw.transformer.TFName"/></label>
							</td>
							<td width="15%">
								<s:hidden id="tfId" name="tfId"></s:hidden>
								<s:textfield id="tfName" name="tfName" cssClass="ui-input" />
							</td>
							<td width="1%">
								<a href="javascript:queryTf();"
								   title=<s:text name="mainModule.insMgt.plan.tip.selTf"></s:text>>
								   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
							</td>
							
							<td width="13%" align="right">
								<label><s:text name="common.kw.customer.CSN"/></label>
							</td>
							<td width="16%">
								<s:textfield id="cno" name="cno" cssClass="ui-input" />
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
								<label><s:text name="mainModule.insMgt.plan.oldMeterNo" /></label>
							</td>
							<td>
								<s:textfield id="omsn" name="omsn" cssClass="ui-input" />
							</td>
							
							<td align="right">
								<label><s:text name="common.kw.other.sdate" /></label>
							</td>
							<td>
								<input name="startDate" id="startDate" value="${startDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'endDate\')}'})"  class="Wdate"/>
							</td>
							<td></td>
							<td align="right">
								<label><s:text name="common.kw.other.edate"/></label>
							</td>
							<td>
								<input name="endDate" id="endDate" value="${endDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate"/>
							</td>
							
							<td align="center">
								<input id="reset" type="button" onclick="pReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>
					</table>				
				</div>
				
				<div class="ui-panel-item">
				 	<div id="energyQueryGrid"></div> 
				</div>
			</div>
		</div>
	</form>	 
	</body>
</html>