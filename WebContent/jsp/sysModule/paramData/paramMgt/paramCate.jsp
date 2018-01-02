<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/paramData/paramMgt/paramCate.js"> </script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding:10px">
				<table class="fluid-grid">
					<tr>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.CateName"></s:text></label>
						</td>
						<td width="13%">
							<s:textfield id="cateName" name="cateName" cssClass="ui-input"/>
						</td>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.verName"></s:text></label>
						</td>
						<td width="13%">
							<s:select id="verId" name="verId" list="verLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.paramType"></s:text></label>
						</td> 
						<td width="13%">
							<s:select id="paramType" name="paramType" list="paramTypeLs" listKey="BM" listValue="MC"  cssClass="ui-select"/>
						</td>
						
						<td align="center">
							<input id="qy" type="button" onclick="query();" value="<s:text name="sysModule.paramData.paramMgt.query"/>" class="ext_btn" />
						</td>
					</tr>
				
				</table>
				

		
			</div>
			
		</div>
	</form>
	
	<div class="ui-togglable-bar" targetId="queryForm" onclick="toggleQueryForm(this)">
	<a class="btn-toggle" href="javascript:void(0)" ></a>
</div>
<div class="hr10"></div>
			
<div id="grid"></div>
</body>
</html>