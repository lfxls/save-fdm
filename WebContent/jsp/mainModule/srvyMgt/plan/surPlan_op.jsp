<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_srvyMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/srvyMgt/plan/surPlan_op.js"></script>
</head>
<body class="body">
	<form id="sPlanOPForm" name="sPlanOPForm" class="ui-form">
		<s:hidden id="operateType" name="operateType"></s:hidden>
		<s:hidden id="bussType" name="bussType"></s:hidden>
		<s:hidden id="pid" name="pid"></s:hidden>
		<s:hidden id="uid" name="uid"/>
		<s:hidden id="uname" name="uname"/>
		<s:hidden id="tfId" name="uid"/>
		<s:hidden id="tfName" name="uname"/>
		<table class="fluid-grid">
		
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.customer.CSN" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:textfield id="cno" name="cno"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp; 
						<a href="javascript:queryCust();"
						   title=<s:text name="mainModule.srvyMgt.plan.tip.selCust"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
				</td>
			</tr>
			
			
			<tr>
				<td width="30%" align="right">
					<label><s:text name="common.kw.customer.CName" /></label>
				</td>
				<td>
					<s:textfield id="cname" name="cname"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label><s:text name="common.kw.customer.CA" /></label>
				</td>
				<td>
					<s:textfield id="addr" name="addr"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label><s:text name="common.kw.other.Mobile" /></label>
				</td>
				<td>
					<s:textfield id="mno" name="mno"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="save();"
					value="<s:text name='mainModule.srvyMgt.plan.btn.save'/>"
					class="ext_btn" />
				
					<!-- 为新装业务类型可选择 -->		
					<s:if test="bussType == 0">
						<input type="button" onclick="reset();"
						value="<s:text name="mainModule.srvyMgt.plan.btn.reset"/>" 
						class="ext_btn"/>
					</s:if>
				</td>
			</tr>
		</table>
	</form>
</body>

<script type="text/javascript">
    var bussType = '${bussType}';
	var operateType = '${operateType}';
	//编辑
	if('02' == operateType) {
		if('${singleMap}') {
			$('cno').value = '${singleMap.CNO}';
			$('cname').value = '${singleMap.CNAME}';
			$('addr').value = '${singleMap.ADDR}';
			$('mno').value = '${singleMap.MNO}';
			$('pid').value = '${singleMap.PID}';
		}
	}
	
</script>
</html>