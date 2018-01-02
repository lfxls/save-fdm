<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/sygl/sygl_${appLang}.js"> </script>
	<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/sygl/sygl.js"> </script>
</head>
<body class="body">
	<form id="syglQueryForm" name="syglQueryForm" class="ui-form">
		<s:hidden id="syid" name="syid" />
		
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="sysModule.qxgl.sygl.query.title"/>
			</div>	
			
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<td width="10%" align="right">
								<label><s:text name="sysModule.qxgl.sygl.yy" /></label>
							</td>
							<td width="17%" align="left">
								<s:select id="lang" name="lang"
									list="langList" listKey="BM" listValue="MC"
									cssClass="ui-select" onchange="query();"></s:select>
							</td>
							
							<td width="10%">
							</td>
							<td width="17%" align="right">
								<input type="button" id="qy" 
								value="<s:text name="sysModule.qxgl.sygl.btn.query" />"
								onclick="query();" class="ext_btn" />
							</td>
							
							<td width="10%">

							</td>
							<td width="17%">
							</td>
							
							<td width="19%">
							</td>
						</tr>
					</table>		
				</div>
				
				<div class="ui-panel-item">
				 	<div id="syGrid"></div> 
				</div>
			</div>
		</div>
	</form>
</body>
</html>