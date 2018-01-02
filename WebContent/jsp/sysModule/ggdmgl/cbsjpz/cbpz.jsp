<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/cbsjpz/cbsjpz_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/cbsjpz/cbpz.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="dlx" name="dlx"/>
<s:hidden id="xlx" name="xlx"/>
<table class="fluid-grid">
  <tr>
    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.sjx" /></label><font color="red">*</font><label>:</label></td>
    <td width="15%"><s:textfield id="sjx" name="sjx" cssClass="ui-input"/>&nbsp;<a href="javascript:getSjx();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/></a></td>
    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.sjxmc" /></label><font color="red">*</font><label>:</label></td>
    <td width="15%"><s:textfield id="sjxmc" name="sjxmc" cssClass="ui-input"/></td>    
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.kd" /></label><font color="red">*</font><label>:</label></td>
    <td><s:textfield id="kd" name="kd" onblur="checkLength(this,20)" cssClass="ui-input"/></td>    
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.dw" />:</label></td>
    <td><s:textfield id="dw" name="dw" onblur="checkLength(this,10)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.sfxs" />:</label></td>
    <td><s:select id="sfxs" name="sfxs" list="sfxsList" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.txid" />:</label></td>
    <td><s:select id="txid" name="txid" list="txlxList" listKey="BM" listValue="MC" cssClass="ui-select"/></td>    
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.ctpt" />:</label></td>
    <td><s:select id="ctpt" name="ctpt" list="ctptList" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.fmt" />:</label></td>
    <td><s:textfield id="fmt" name="fmt" onblur="checkLength(this,25)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.pz.px" />:</label></td>
    <td><s:textfield id="pxbm" name="pxbm" onblur="checkLength(this,8)" cssClass="ui-input"/></td>
    <td></td>
    <td></td>    
  </tr>  
  <tr>
    <td colspan="4" align="center">
    	<input type="button" class="ext_btn" onclick="save()" value="<s:text name="sysModule.ggdmgl.xtcs.bc"/>">
    </td>
  </tr>    
</table>
</form>
</body>
</html>