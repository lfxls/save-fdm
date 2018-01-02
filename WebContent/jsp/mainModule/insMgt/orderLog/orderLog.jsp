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
		<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/orderLog/orderLog.js"> </script>
	</head>
	<body class="body">	
	<s:hidden id="woid" name="woid" />
	<%-- <form id="woLogForm" name="woLogForm" onsubmit="return false;" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="mainModule.insMgt.orderLog.title"/>
			</div>	
			
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<td width="13%" align="right">
								<label><s:text name="common.kw.workorder.WOID"/></label>
							</td>
							<td width="16%">
								<s:textfield id="woid" name="woid" cssClass="ui-input" />
							</td>
							
							<td width="13%" align="right">
								<label><s:text name="common.kw.other.Status"/></label>
							</td>
							<td width="16%">
								<s:select id="status" name="status" cssClass="ui-select"
									list="stLs" listKey="BM" listValue="MC"
									onchange="changeStatus(this.value)" />
							</td>
						   	
						   	<td width="13%" align="right">
								<label><s:text name="mainModule.insMgt.order.pop" /></label>
							</td>
							<td width="16%">
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
								id="qy" class="ui-button ui-button-blue ui-button-small"
								onclick="query();"
								value="<s:text name="mainModule.insMgt.plan.btn.query"/>">
							</td>
						</tr>
						
						<tr>
							<td align="right">
						   		<label><s:text name="mainModule.insMgt.order.startDate"/></label>
						   	</td>
						    <td>
						    	<input name="startDate" id="startDate" value="${startDate}" type="text" 
					   					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'endDate\')}'})" class="Wdate"/>
							</td>
						    <td align="right">
						    	<label><s:text name="mainModule.insMgt.order.endDate"/></label>
						    </td>
							<td>
								<input name="endDate" id="endDate" value="${endDate}" type="text" 
					 				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'startDate\')}'})" class="Wdate"/>
							</td>
							<td></td>
							<td></td>
							<td align="center">
								<input id="reset" type="button" onclick="logReset();" 
									value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
									class="ext_btn" />
							</td>
						</tr>		
					</table>				
				</div>
				
				<div class="ui-panel-item">
				 	<div id="woLogGrid"></div> 
				</div>
			</div>
		</div>
	</form>	 --%> 
	<div id="woLogGrid"></div> 
	</body>
</html>