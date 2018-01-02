<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('sysModule', 'sysModule_logMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/hhuLog/hhuWoLog.js"></script>
</head>
<body class="body">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current">
				<a href="#"><s:text name="sysModule.rzgl.hhuLog.hhuWoLog" /></a>
			</li>
			<li class="ui-tab-item">
				<a href="${ctx}/system/rzgl/hhuLog!initHhuDuLog.do"><s:text name="sysModule.rzgl.hhuLog.hhuDuLog" /></a>
			</li>
		</ul>
	</div>
	<div class="hr10"></div>
	<form id="hhuWoLogForm" name="hhuWoLogForm" onsubmit="return false;" class="ui-form">
	<div class="ui-panel">
		<div class="ui-panel-header">
			<s:text name="sysModule.rzgl.hhuLog.hhuWoLog.title"/>
		</div>
		<div class="ui-panel-body">
			<div class="ui-panel-item">
				<table class="fluid-grid">
					<tr>
						<td	width="12%" align="right">
							<label><s:text name="sysModule.rzgl.hhuLog.hhuWoLog.optId" /></label>
						</td>
						<td width="14%">
							<s:textfield id="optId" name="optId" onblur="checkLength(this,128)" cssClass="ui-input"/>
						</td>						
						<td width="12%" align="right">
							<label><s:text name="sysModule.rzgl.hhuLog.hhuWoLog.hhuId" /></label>
						</td>
						<td width="14%">
							<s:textfield id="hhuId" name="hhuId" onblur="checkLength(this,64)" cssClass="ui-input"/>
						</td>
						<td width="12%" align="right">
							<label><s:text name="sysModule.rzgl.hhuLog.hhuWoLog.optRst" /></label>
						</td>
						<td width="14%">
							<s:select name="opt_rst" id="opt_rst" list="hhuRst"
								listKey="BM" listValue="MC" cssClass="ui-select" />	
						</td>
						<td width="16%" valign="middle" align="center" >
							<input type="button"
									onclick="query();"
									value="<s:text name='sysModule.rzgl.hhuLog.hhuWoLog.query'/>"
									class="ext_btn" />
						</td>
					</tr>
					<tr>
						<td  width="12%" align="right">
							<label class="ui-label"><s:text name="sysModule.rzgl.hhuLog.hhuWoLog.op_date"></s:text></label>
						</td>
						<td width="20%">
							<div id="Date">
								<input name="startDate" id="startDate" value="${startDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})"  class="Wdate" style="width:48%"/>~
								<input name="endDate" id="endDate" value="${endDate}" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" class="Wdate" style="width:48%"/>
							</div>		
						</td>
						
						<td width="12%" align="right">
							<label><s:text name="sysModule.rzgl.hhuLog.hhuWoLog.optType" /></label>
						</td>
						<td width="14%">
							<s:select name="opt_type" id="opt_type" list="hhuOptType"
								listKey="BM" listValue="MC" cssClass="ui-select" />
						</td>
						<td></td>
						<td></td>
						<td align="center">
							<input id="re" type="button" onclick="Reset();" value="<s:text name="common.kw.other.reset"/>" class="ext_btn" />
						</td>
					</tr>				
				</table>
			</div>
		</div>
		<div class="ui-panel-item">
			<div id="hhuWoLogGrid"></div>
		</div>
	</div>		
	</form>
</body>
</html>