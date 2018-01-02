<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/paramData/paramMgt/param_edit.js"> </script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;">
		<s:hidden id="czid" name="czid"></s:hidden>
		<s:hidden id="cateId" name="cateId"></s:hidden>
		<s:hidden id="verId" name="verId"></s:hidden>
		<s:hidden id="paramType" name="paramType"></s:hidden>
		<s:hidden id="bussType" name="bussType"></s:hidden>
		<s:hidden id="oldStatus" name="oldStatus"></s:hidden>
		<s:hidden id="cate_name" name="cate_name"></s:hidden>
		<div class="ui-panel">
			<div class="ui-panel-body" style="padding:10px">
				<table class="fluid-grid">
					<tr>
						<td width="16%" id="param1">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.param"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%" id="param2">
							<s:textfield id="itemName" name="itemName" cssClass="ui-input" readOnly="true" />
							<s:hidden id="itemId" name="itemId"></s:hidden>
						<a id="params" href="javascript:getParamList();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>
						<td  width="16%" id="test1" >
							<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.testId"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%" id="test2">
							<s:textfield id="testName" name="testName" cssClass="ui-input" readOnly="true" />
							<s:hidden id="testId" name="testId"></s:hidden>
							<a id="testparams" href="javascript:getParamList();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
						</td>
						<td width="16%">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.status"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%">
							<s:select id="status" name="status" list="statusLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
					</tr>
					<tr>
					
						
						<td width="16%" id="valuetd1">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.value"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%"   id="sValuetd">
								<s:select id="svalue" name="svalue" list="svalueLs" listKey="BM" listValue="MC"   cssClass="ui-select"/>
						</td>
						<td width="16%"  id="valuetd2">
							<s:textfield id="value" name="value" cssClass="ui-input"/>
						</td>
						
						<td width="16%" id="leveltd1">
						<label class="ui-label"><s:text name="sysModule.paramData.paramMgt.level"></s:text><font color="red">*</font></label>
						</td>
						<td width="16%"  id="leveltd2">
							<s:select id="level" name="level" list="levelLs" listKey="BM" listValue="MC" cssClass="ui-select"/>
						</td>
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