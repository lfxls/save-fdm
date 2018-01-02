<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/createSurPlan.js"></script>
</head>
<body class="body">
	<form id="surPForm" name="surPForm" class="ui-form">
		<s:hidden id="uid" name="uid"/>
		<s:hidden id="uname" name="uname"/>
		<s:hidden id="sczh" name="sczh"/>
		<s:hidden id="sdzh" name="sdzh"/>
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
						<a id="cno_sel" href="javascript:queryCust();"
						   title=<s:text name="mainModule.insMgt.plan.tip.selCust"></s:text>>
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
					value="<s:text name='mainModule.insMgt.plan.btn.save'/>"
					class="ext_btn" />
				
					<input type="button" onclick="reset();"
					value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
					class="ext_btn"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>