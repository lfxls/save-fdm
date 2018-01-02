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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/createMInsPlan.js"></script>
</head>
<body class="body">
	<form id="cMInsPForm" name="cMInsPForm" class="ui-form">
		<s:hidden id="uid" name="uid"/>
		<s:hidden id="uname" name="uname"/>
		<s:hidden id="mczh" name="mczh"/>
		<s:hidden id="mdzh" name="mdzh"/>
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
			<tr id="omsn_s">
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.oldMeterNo" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:textfield id="omsn" name="omsn"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp;
						<a href="javascript:queryMeter('old');"
						   title=<s:text name="mainModule.insMgt.plan.tip.selMeter"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
				</td>
			</tr>				
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
					<label>
						<s:text name="common.kw.meter.MType" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="mType" name="mType" list="mTypeList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>
				
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.meter.MCMethod" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="wir" name="wir" list="wirList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>
				
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.meterMode" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="mMode" name="mMode" list="mModeList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>	
			
			<tr id="nmsn_s">
				<td width="30%" align="right">
					<label><s:text name="mainModule.insMgt.plan.newMeterNo"/></label>
				</td>
				<td>
					<s:textfield id="nmsn" name="nmsn"
							cssStyle="width:300px;background-color:#cccccc;" readonly="true"
							cssClass="ui-input"></s:textfield>&nbsp;
							<a href="javascript:queryMeter('new');"
							   title=<s:text name="mainModule.insMgt.plan.tip.selMeter"></s:text>>
							   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
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