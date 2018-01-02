<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js">
</script>
<script type="text/javascript" src="${ctx}/public/js/commForZzrw.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js">
	
</script>
<script type="text/javascript"
	src="${ctx}/js/locale/commonModule/qz/qzsz_${appLang}.js">
	
</script>
<script type="text/javascript"
	src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js">
	
</script>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/qz/mdm_qzzd.js">
	
</script>
</head>

<div  class="ui-tab">
<ul class="ui-tab-items">
<li class="ui-tab-item" ><a href="javascript:qzBj();"><s:text name="common.qzsz.bj"></s:text></a></li>
   <li class="ui-tab-item ui-tab-item-current"><a href="#"><s:text name="common.qzsz.zd"></s:text> </a></li>   
</ul>
</div>

<body id="bodycss" style="background-repeat: repeat;" onkeydown="keyDown()" class="body">
<s:form theme="simple" id="queryForm" class="ui-form"> 
	<table class="fluid-grid">
		<s:hidden name="nodeId" id="nodeId" />
		<s:hidden name="nodeType" id="nodeType" />
		<s:hidden name="nodeDwdm" id="nodeDwdm" />
		<s:hidden name="yhlx" id="yhlx" />
		<s:hidden id="zxsj" name="zxsj" />
		<tr>
			<td align="right"  ><label> <s:text name="advModule.fhkz.plkz.text.nodename" /></label></td>
			<td align="left"><s:textfield name="nodeText" id="nodeText" readonly="true" cssClass="ui-input" cssStyle="width:125px"></s:textfield></td>

			<td align="right"><label><s:text name="basicModule.dagl.zdgl.zdjh" /></label> </td>
			<td align="left"><s:textfield id="zdjh" name="zdjh" cssClass="ui-input" cssStyle="width:125px"></s:textfield>  </td>
				
				
			<td align="right"><label><s:text name="common.mdmsy.cs.cs"></s:text></label> </td>
			<td align="left"><s:select id="csbm" name="csbm" list="csLst" listKey="BM" listValue="MC" cssStyle="width:125px" cssClass="ui-select" /></td>
				
		</tr>
		<tr>
			<td align="right"><label><s:text name="basicModule.csgl.flxz.flxz.gylx"></s:text></label> </td>
			<td align="left"><s:select id="zdgylx" name="zdgylx" list="zdgylxLst" listKey="BM" listValue="MC" cssClass="ui-select" cssStyle="width:125px" /></td>
				
			<td align="right"><label><s:text name="basicModule.dagl.cjqda.cjfs"></s:text></label> </td>
			<td align="left"><s:select id="cjfs" name="cjfs" list="cjfsLst" listKey="BM" listValue="MC" cssClass="ui-select" cssStyle="width:125px" /></td>
				
			<td align="right"><label><s:text name="basicModule.dagl.zdgl.zdzt"></s:text></label> </td>
			<td align="left"><s:select id="zdzt" name="zdzt" list="zdztLst" listKey="BM" listValue="MC" cssClass="ui-select" cssStyle="width:125px" /></td>
				
		</tr>
		<tr>
			<td align="right"><label><s:text name="basicModule.dagl.zdgl.zdlx"></s:text></label> </td>
			<td align="left"><s:select id="zdlx" name="zdlx" list="zdlxLst" cssClass="ui-select"
				listKey="BM" listValue="MC" cssStyle="width:125px" /></td>
				
			<td align="right"><label><s:text name="runModule.cjsbjk.sbzxjk.zxzt" /> </label> </td>
			<td align="left"><s:select id="zxzt" name="zxzt" list="zxztlst" cssClass="ui-select"
				listKey="BM" listValue="MC" cssStyle="width:125px" /></td>	
				
			<td align="right"></td>
			<td align="left"> 
			<input type="button" class="ext_btn" value="<s:text name="runModule.byqjk.syjk.cx" />" onclick="query()">
			 </td>
	  </tr>
	</table>
</s:form>
<div id="grid"></div>

</body>
</html>
