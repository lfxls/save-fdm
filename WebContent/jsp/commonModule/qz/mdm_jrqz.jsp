<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
	<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
	<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js"></script>
	<script type="text/javascript"	src="${ctx}/js/locale/commonModule/qz/qzsz_${appLang}.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
	<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/mdm_jrqz.js"> </script>
</head>
<body  id="bodycss">
	<s:form theme="simple"  id="queryForm">
	<s:hidden id="jh" name="jh"></s:hidden>
	<s:hidden id="qzlx" name="qzlx"></s:hidden>
	<s:hidden id="totalCount" name="totalCount" />
	<s:hidden id="selectAllFlg" name="selectAllFlg" />
	<s:hidden name="nodeId" id="nodeId" />
	<s:hidden name="nodeType" id="nodeType" />
	<s:hidden name="nodeDwdm" id="nodeDwdm" />
	<s:hidden name="sjid" id="sjid" />
	<s:hidden name="type" id="type" />
	
	<li id="l2" class="ui-tab-item ui-tab-item-current"> 
		<a href="javascript:pageTo('l2');"><s:text name="common.qz.btnjrqz"></s:text> </a>
	</li>
	<li id="l1" class="ui-tab-item">
		<a	href="javascript:pageTo('l1');"><s:text name="common.qz.btnxjqz"></s:text></a>
	</li>
	
	<div id="my-tabs"></div>
	<div id="tab1" style="display: none">
	<div class="hr10"></div>

	<table class="fluid-grid">
		<tr>
			<td width="30%">
				<label class="ui-label"> <s:text name="common.qzsz.qzm"></s:text> <font color="red">*</font>:</label>
			</td>
			<td width="70%">
				<s:textfield name="qzm" id="qzm" cssClass="ui-input" cssStyle="width:240px"></s:textfield>
			</td>
		</tr>
		
		<tr>
			<td align="right" >
				<label class="ui-label"><s:text name="common.qz.smzq"></s:text> : </label> 
			</td>
			<td align="left" >
				<input id="yjyx" type="radio" name="yxq" value="0" checked="checked" style="margin-right:3px">
				<s:text name="common.qz.yjbc" ></s:text>
				<input id="yxsj" type="radio" name="yxq" style="margin:0 3px 0 25px">
				<input id="yxrq" name="yxrq" value="<s:property value='yxrq'/>" type="text" 
	      		onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd ',isShowClear:'true'})"
	     		class="Wdate" style="width: 100px" />
			</td>
		</tr>
		
		<tr>
			<td align="right" >
				<label  class="ui-label"><s:text name="common.qz.isgx"></s:text> : </label> 
			</td>
			<td align="left" >
				<input type="checkbox" id="sfgx" name="sfgx">
			</td>
		</tr>
	</table>
	
	<div class="hr10"></div>
	<div class="form-action" style="text-align:center">
		<input type="button" class="ext_btn" onclick="save('','tab1')"
			value="<s:text name="basicModule.dagl.sim.simda.save"></s:text>" />
	</div>
	</div>
</s:form>

<div id="tab2">
	<table class="fluid-grid">
	    <tr>
			<td>
				<label class="ui-label"> <s:text name="common.qzsz.qzm"></s:text><font color="red">*</font>:</label>
			</td>
			<td>
				<%-- 
		 		<s:select id="qzid" name="qzid" list="qzmcList" listKey="BM"
						listValue="MC" cssClass="ui-select" cssStyle="width:100px" />
				 --%>		
				<s:textfield name="nodeText" id = "nodeText" value = "" cssClass="ui-input" cssStyle="width:140px"/>
			</td>
			
			<td colspan="2">
				<input type="button" onClick="query()" class = "ext_btn" value="<s:text name="common.qzsz.cx"></s:text>" />
			</td>
		</tr>
	</table>
	<div id="grid" ></div>
</div>
</body>
</html>
