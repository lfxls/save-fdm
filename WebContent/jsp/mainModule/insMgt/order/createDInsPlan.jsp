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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/createDInsPlan.js"></script>
</head>
<body class="body">
	<form id="cDInsPForm" name="cDInsPForm" class="ui-form">
		<s:hidden id="uid" name="uid"/>
		<s:hidden id="uname" name="uname"/>
		<table class="fluid-grid">
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.bussType" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="bussType" name="bussType" list="btList" listKey="BM"
						listValue="MC" onchange="changeBType(this.value)" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>
			<tr id="odsn_s">
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.oldDcuNo" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:textfield id="odsn" name="odsn"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp;
						<a href="javascript:queryDcu();"
						   title=<s:text name="mainModule.insMgt.plan.tip.selDcu"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
				</td>
			</tr>				
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.transformer.TFName" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:hidden id="tfId" name="tfId"/>
					<s:textfield id="tfName" name="tfName"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp; 
						<a id="tf_sel" href="javascript:queryTf();"
						   title=<s:text name="mainModule.insMgt.plan.tip.selTf"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					<label><s:text name="mainModule.insMgt.plan.devAddress" /></label>
				</td>
				<td>
					<s:textfield id="devAddr" name="devAddr"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.dcuModel" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="dcuM" name="dcuM" list="dcuMList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>	
			
			<tr id="ndsn_s">
				<td width="30%" align="right">
					<label><s:text name="mainModule.insMgt.plan.newDcuNo"/></label>
				</td>
				<td>
					<s:textfield id="ndsn" name="ndsn"
						cssStyle="width:300px;"	cssClass="ui-input"></s:textfield>&nbsp;
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