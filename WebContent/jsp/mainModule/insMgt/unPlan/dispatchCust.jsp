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
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/unPlan/dispatchCust.js"></script>
</head>
<body class="body">
<form id="dispForm" id="dispForm" onsubmit="return false;" class="ui-form">
	<s:hidden id="cnos" name="cnos"/>
	<s:hidden id="ecnos" name="ecnos"/>
	<s:hidden id="dcnos" name="dcnos"/>
	<s:hidden id="operateType" name="operateType"/>
	<s:hidden id="status" name="status"/>
	<s:hidden id="woid" name="woid"/>
	<table width="100%" class="fluid-grid">
		<tr>
		   	<td width="10%" align="right">
				<label><s:text name="common.kw.other.PU" /></label>
			</td>
			<td width="17%">
				<s:textfield id="nodeTextdw" name="nodeTextdw" readonly="true"
					cssStyle="background-color:#cccccc;" cssClass="ui-input" />
			    <s:hidden id="nodeTypedw" name="nodeTypedw"/>
				<s:hidden id="nodeIddw" name="nodeIddw"/>
			</td>
			<td width="1%">
				<a href="javascript:getDwTree('unSel');"
			   		title=<s:text name="mainModule.insMgt.plan.tip.selPU"></s:text>>
			   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
			</td>
		 	<td width="10%" align="right"><label><s:text name="common.kw.customer.CSN"/></label></td>
			<td width="17%">
				<input type="text" name="cno" id="cno" class="ui-input"/>
			</td>
			<td width="10%" align="right"><label><s:text name="common.kw.customer.CName"/></label></td>
		    <td width="17%">
		    	<input type="text" name="cname" id="cname"  class="ui-input"/>
		    </td>
		    <td width="18%" rowspan="2" align="center">
				<%-- <button class="ext_btn" onclick="queryUnSelCust();">
					<s:text name="mainModule.insMgt.plan.btn.queryUnS" />
				</button>&nbsp;&nbsp;
				<button class="ext_btn" onclick="querySelCust();">
					<s:text name="mainModule.insMgt.plan.btn.querySel" />
				</button> --%>
			</td>
		 </tr>
	</table>
	<div id="unSelCustGrid"></div>
	<div class="hr10"></div>
	<div id="selCustGrid"></div>
	<div class="hr10"></div>
	<table width="100%" class="fluid-grid">
	  <tr>
		<td width="13%" align="right">
			<label><s:text name="mainModule.insMgt.order.pop"/>
			</label>
		</td>
		<td width="16%">
			<s:textfield id="popName" name="popName" readonly="true"
			cssStyle="background-color:#cccccc;" cssClass="ui-input" />
			<s:hidden id="popid" name="popid" />
		</td>
		<td width="1%">
			<s:if test="operateType != '04'">
				<a href="javascript:selPop();"
		   		title=<s:text name="mainModule.insMgt.order.tip.selPop"></s:text>>
		   		<img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
			</s:if>
		</td>
		<td width="13%" align="right">
	   		<label><s:text name="mainModule.insMgt.order.finishTime"/></label>
	   	</td>
	    <td width="16%">
	    	<input name="fTime" id="fTime" value="${fTime}" type="text" 
   					onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:'true',minDate:'%y-%M-%d'})" class="Wdate"/>
		</td>
		<td width="18%" align="center">
			<s:if test="operateType != '04'">
					<button class="ext_btn" onclick="save();">
						<s:text name="mainModule.insMgt.plan.btn.save" />
					</button>
			</s:if>
		</td>
		<td width="23%"></td>
	  </tr>
	</table>
</form>
</body>
</html> 
