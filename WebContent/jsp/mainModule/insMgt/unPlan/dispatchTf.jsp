<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/unPlan/dispatchTf.js"></script>
</head>
<body class="body">
<form id="dispForm" id="dispForm" onsubmit="return false;" class="ui-form">
	<s:hidden id="tfIds" name="tfIds"/>
	<s:hidden id="etfIds" name="etfIds"/>
	<s:hidden id="operateType" name="operateType"/>
	<s:hidden id="status" name="status"/>
	<s:hidden id="woid" name="woid"/>
	<s:hidden id="dispFlag" name="dispFlag"/>
	<table width="100%" class="fluid-grid">
	  <tr>
		<td width="10%" align="right">
			<label><s:text name="mainModule.insMgt.order.pop"/>
			</label>
		</td>
		<td width="25%">
			<s:textfield id="popid" name="popid" readonly="true"
			cssStyle="background-color:#cccccc;" cssClass="ui-input" />
			<s:if test="status != 1 || status != 2 || status != 3">
				<a href="javascript:selPop();"
		   		title=<s:text name="mainModule.insMgt.unPlan.tip.selPOP"></s:text>>
		   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
			</s:if>
		</td>
		<td width="65%">
		</td>
	  </tr>
	</table>
	<div id="dispGrid"></div>
	<table width="100%" class="fluid-grid">
		<tr>
			<td align="center">
				<s:if test="status != 1 || status != 2 || status != 3">
					<button class="ext_btn" onclick="save();">
						<s:text name="mainModule.insMgt.plan.btn.save" />
					</button>
				</s:if>
			</td>
		</tr>
	</table>
</form>
</body>
</html> 
