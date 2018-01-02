<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
	<script type="text/javascript" src="${ctx}/js/locale/sysModule/rzgl/darz/darz_${appLang}.js"> </script>
	<script type="text/javascript" src="${ctx}/js/modules/sysModule/rzgl/darz/darz.js"> </script>
</head>
<body class="body">
	<form id="daform" name="daform" onsubmit="return false;" class="ui-form">
		<s:hidden id="menuId" name="menuId"/>
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="sysModule.darz.query.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<c:if test='${sysMode=="0"}'>  
								<td width="10%" align="right">
									<label><s:text name="sysModule.darz.td.gncd"/>:</label>
								</td>
								<td width="17%" align="left">
									<s:textfield id="cdmc" name="cdmc" cssStyle="background-color:#f0f0f0;" 
									   readonly="true" cssClass="ui-input"/>
									<a href="javascript:getTree();">
										<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" />
									</a>
								</td>
							</c:if>
							
							<td width="10%" align="right">
								<label><s:text name="sysModule.darz.td.czy" />:</label>
							</td>
							<td width="17%" align="left">
								<input type="text" id="czyId" name="czyId" class="ui-input"/>
							</td>	
								
							<td width="10%" align="right">
								<label><s:text name="sysModule.darz.td.cznr"/>:</label>
							</td>
							<td width="17%" align="left">
								<s:textfield id="rznr" name="rznr" cssClass="ui-input"/>
							</td>
							
							<td width="19%" align="center">
								<input type="button" onclick="query();" class="ext_btn"
								value="<s:text name="sysModule.rzgl.dlrz.btn.query"/>">
							</td>
						</tr>
						
						<tr>
				   			<td width="10%" align="right">
								<label><s:text name="sysModule.rzgl.txrz.sj"/>:</label>
							</td>
						    <td width="17%" align="left">
								<input name="kssj" id="kssj" value="${kssj}" type="text"
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',maxDate:'#F{$dp.$D(\'jssj\')||\'%y-%M-%d\'}'})"
								class="Wdate" style="width:47%"/> 
								~ 
								<input name="jssj" id="jssj" value="${jssj}" type="text"
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'#F{$dp.$D(\'kssj\')}',maxDate:'%y-%M-%d'})"
								class="Wdate" style="width:47%"/>
							</td>
							<td colspan="3"></td>
							<td></td>
							<td width="19%" align="center">
							  <input id="re" type="button" onclick="Reset();" value="<s:text name="common.kw.other.reset"/>" class="ext_btn" />
							</td>
						</tr>
					</table>				
				</div>
				
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>