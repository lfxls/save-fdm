<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/unPlan/selTf.js"></script>
</head>
<body class="body">
<form id="tfFrom" name="tfFrom" onsubmit="return false;" class="ui-form">
<table class="fluid-grid">
	<s:hidden id="dwdm" name="dwdm"/>
	<s:hidden id="dwmc" name="dwmc"/>
	<s:hidden id="tfIds" name="tfIds" />
	<s:hidden id="operateType" name="operateType"/>
	<tr>
		<td width="15%" align="right">
			<label><s:text name="common.kw.other.PU" /></label>
		</td>
		<td width="21%">
			<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true"
				cssStyle="background-color:#cccccc;" cssClass="ui-input" />
		    <s:hidden id="nodeTypedw" name="nodeTypedw"/>
			<s:hidden id="nodeIddw" name="nodeIddw"/>
		</td>
		<td width="1%">
			<a href="javascript:getDwTree();"
		   		title=<s:text name="mainModule.insMgt.plan.tip.selPU"></s:text>>
		   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
		</td>
		<td width="15%" align="right">
			<label><s:text name="common.kw.transformer.TFID"></s:text></label>
		</td>
		<td width="25%">
		   <s:textfield id="tfId" name="tfId" cssClass="ui-input" cssStyle="width:140px"></s:textfield>
		</td>
		<td width="20%">
		    <button class="ext_btn" onclick="queryTf();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
	</tr>
	<tr>
		<td align="right"><label><s:text name="common.kw.transformer.TFName"/></label></td>
	    <td>
	    	<input type="text" name="tfName" id="tfName"  class="ui-input"/>
	    </td>
	</tr>
</table>
<div id="tfGrid"></div>
</form>
</html>