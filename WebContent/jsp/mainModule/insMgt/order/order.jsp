<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript">
		//加载国际化资源文件
		loadProperties('mainModule', 'mainModule_insMgt');
	</script>
	<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/order/order.js"></script>
</head>
<body class="body">
	<form id="cWOForm" name="cWOForm" onsubmit="return false;" class="ui-form">
		<s:hidden id="mszh" name="mszh" />
		<s:hidden id="mczh" name="mczh" />
		<s:hidden id="mdzh" name="mdzh" />
		<s:hidden id="dszh" name="dszh" />
		<s:hidden id="dczh" name="dczh" />
		<s:hidden id="cszh" name="cszh" />
		<s:hidden id="cczh" name="cczh" />
		<s:hidden id="sszh" name="sszh" />
		<s:hidden id="sczh" name="sczh" />
		<s:hidden id="sdzh" name="sdzh" />
		<s:hidden id="woid" name="woid" />
		<s:hidden id="operateType" name="operateType" />
		<s:hidden id="oType" name="oType" />
		<table class="fluid-grid">
			<tr>
				<td width="13%" align="right">
					<label><s:text name="mainModule.insMgt.order.pop" /></label>
				</td>
				<td width="16%">
					<s:textfield id="popName" name="popName"
							cssStyle="width:300px;background-color:#cccccc;" readonly="true"
							cssClass="ui-input"></s:textfield>&nbsp;
							<s:hidden id="popid" name="popid" />
							<s:if test="operateType != '04'">
							<a href="javascript:queryPop();"
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
				<td width="29%"></td>
				<td align="center" width="13%">
					<s:if test="operateType != '04'">
						<input type="button" class="ui-button ui-button-blue ui-button-small" onclick="createWO();"
						value="<s:text name="mainModule.insMgt.order.btn.createWO"/>">
					</s:if>
				</td>
			</tr>
		</table>
	</form>
	<div class="ui-panel">
	   <div class="ui-panel-body">	
	    <div class="ui-panel-item">
	       <div id="mInsPGrid"></div>
	       <div class="hr10"></div>
	       <div id="dInsPGrid"></div>
	       <div class="hr10"></div>
	       <div id="cInsPGrid"></div>
	       <div class="hr10"></div>
	       <div id="surPGrid"></div>
	  	</div>
	  </div>
	</div>
</body>
</html>