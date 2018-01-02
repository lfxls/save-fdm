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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/selCust.js"></script>
</head>
<body class="body">
<form id="queryCustForm" id="queryCustForm" onsubmit="return false;" class="ui-form">
	<s:hidden id="otype" name="otype"/>
	<table width="100%" class="fluid-grid">
	  <tr>
	 	<td width="6%" align="right"><label><s:text name="common.kw.customer.CSN"/></label></td>
		<td width="15%">
			<input type="text" name="cno" id="cno" class="ui-input"/>
		</td>
		
	    <td width="6%" align="right"><label><s:text name="common.kw.customer.CName"/></label></td>
	    <td width="15%">
	    	<input type="text" name="cname" id="cname"  class="ui-input"/>
	    </td>
	    
	    <td width="10%">
			<button class="ext_btn" onclick="query();">
				<s:text name="mainModule.insMgt.plan.btn.query" />
			</button>
		</td>
	  </tr>
	</table>
</form>
	<div id="CustGrid"></div>
</body>
</html> 
