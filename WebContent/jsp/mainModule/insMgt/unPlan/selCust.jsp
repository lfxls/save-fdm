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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/unPlan/selCust.js"></script>
</head>
<body class="body">
<form id="custForm" id="custForm" onsubmit="return false;" class="ui-form">
	<s:hidden id="cnos" name="cnos" />
	<s:hidden id="dcnos" name="dcnos" />
	<s:hidden id="operateType" name="operateType" />
	<table width="100%" class="fluid-grid">
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
	 	<td width="15%" align="right"><label><s:text name="common.kw.customer.CSN"/></label></td>
		<td width="25%">
			<input type="text" name="cno" id="cno" class="ui-input"/>
		</td>
		
	    <td width="20%" rowspan="2" align="center">
			<button class="ext_btn" onclick="query();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
	  </tr>
	  <tr>
	  	<td align="right"><label><s:text name="common.kw.customer.CName"/></label></td>
	    <td>
	    	<input type="text" name="cname" id="cname"  class="ui-input"/>
	    </td>
	  </tr>
	</table>
</form>
	<div id="custGrid"></div>
</body>
</html> 
