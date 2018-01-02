<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/paramData/paramMgt/paramCate_edit.js"> </script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
		<s:hidden id="czid" name="czid"></s:hidden>
		<s:hidden id="cateId" name="cateId"></s:hidden>
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding:10px">
				<table class="fluid-grid">
					<tr>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.CateName"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:textfield id="cateName" name="cateName" cssClass="ui-input"  onblur="checkLength(this,64)"/>
						</td>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.paramType"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:select id="paramType" name="paramType" list="paramTypeLs" listKey="BM" listValue="MC"  cssClass="ui-select"/>
						</td>
					</tr>
						
					<tr>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.verName"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:select id="verId" name="verId" list="verLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
						
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.status"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:select id="status" name="status" list="statusLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					<tr>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.sortNum"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:textfield id="sortNum" name="sortNum" cssClass="ui-input"/>
						</td>
					</tr>
					<tr>
						<td align="center" colspan='4'>
							<input id="save" type="button" onclick="Save();" value="<s:text name="sysModule.paramData.paramMgt.save"/>" class="ext_btn" />
						</td>
					</tr>
				
				</table>
				

		
			</div>
			
		</div>
	</form>
</body>
</html>