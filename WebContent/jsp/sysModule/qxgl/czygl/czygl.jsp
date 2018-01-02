<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var wrongPwdTimes = ${sysMap.wrongPwdTimes == null?"3":sysMap.wrongPwdTimes};
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/czygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/czygl.js"></script>
</head>
<body class="body">
<form id="czyform" name="czyform" onsubmit="return false;" class="ui-form">
	<input type="hidden" name="prepayMode" id="prepayMode" value="${prepayMode}"/>
	<input type="hidden" name="usePosMach" id="usePosMach" value="${usePosMach}"/>
	<s:hidden id="dwdm" name="dwdm"/>
	<s:hidden id="bmid"	name="bmid" />
	
	<div class="ui-panel">
		<div class="ui-panel-header">
			<s:text name="sysModule.qxgl.czygl.query.title"/>
		</div>
		<div class="ui-panel-body">
			<div class="ui-panel-item">
				<table class="fluid-grid">
					<tr>
						<td width="6%" align="right">
							<label><s:text name="sysModule.qxgl.czygl.bm"/>:</label>
						</td>
						<td width="10%" align="left">
							<s:textfield id="bmmc" name="bmmc" cssStyle="background-color:#cccccc;" 
								readonly="true" cssClass="ui-input"/>
						</td>
						<td	width="1%" align="left">
							<a href="javascript:getBmTree('node');"><img
									src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>						
						<td width="10%" align="right">
							<label><s:text name="sysModule.qxgl.czygl.tname"/>:</label>
						</td>
						<td width="10%" align="left">
							<s:textfield id="tname" name="tname" cssStyle="background-color:#cccccc;" 
								readonly="true" cssClass="ui-input"/>
							<s:hidden id="tno" name="tno"/>
						</td>
						<td width="1%" align="left">
							<a href="javascript:selectInsteam();">
								<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/>
							</a>
						</td>						
						<td width="4%" align="right">
							<label><s:text name="sysModule.qxgl.czygl.czyid"/>:</label>
						</td>
						<td width="10%" align="left">
							<input type="text" id="qczyid" name="qczyid" class="ui-input"/>
						</td>						
						<td width="6%" align="right">
						  	<label><s:text name="sysModule.qxgl.czygl.xm"/>:</label>
						</td>
						<td width="10%" align="left">
							<input type="text" id="qxm" name="qxm" class="ui-input"/>
						</td>						
						<td width="6%" align="right">
							<label><s:text name="sysModule.qxgl.czygl.zt"/>:</label>
						</td>
						<td width="10%" align="left">
								<s:select name="zt" id="zt" list="czyzt"
									listKey="BM" listValue="MC" cssClass="ui-select" />
						</td>
						<td width="7%" align="center">
							<input type="button"
								onclick="queryCzy()"
								value='<s:text name="sysModule.qxgl.czygl.btn.query"/>'
								class="ext_btn" />
						</td>
						<td width="7%" align="center">
							<input type="reset"
								onclick="clearInput()"
								value='<s:text name="sysModule.qxgl.czygl.btn.clear"/>'
								class="ext_btn" />
						</td>
					</tr>
				</table>			
			</div>
			
			<div class="ui-panel-item">
			 	<div id="listGrid"></div> 
			</div>
		</div>
	</div>
</form>
</body>
</html>