<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/cbsjpz/cbsjpz_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/cbsjpz/sjx.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="dlx" name="dlx"/>
<table class="fluid-grid">
  <tr>
    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.sjx.sjx" /></label><font color="red">*</font><label>:</label></td>
    <td width="15%"><s:textfield id="sjx" name="sjx" onblur="checkLength(this,20)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.sjx.mc" /></label><font color="red">*</font><label>:</label></td>
    <td><s:textfield id="sjxmc" name="sjxmc" onblur="checkLength(this,50)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="login.lang" />:</label></td>
    <td><s:select id="xzyy" name="xzyy" list="xzyyList" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
  </tr>
  <tr>
    <td colspan="2" align="center">
    	<input type="button" class="ext_btn" onclick="save()" value="<s:text name="sysModule.ggdmgl.xtcs.bc"/>">
    </td>
  </tr>    
</table>
</form>
</body>
</html>