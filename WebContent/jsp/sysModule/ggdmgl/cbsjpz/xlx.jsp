<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/cbsjpz/cbsjpz_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/cbsjpz/xlx.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<s:hidden id="dlx" name="dlx"/>
<table class="fluid-grid">
  <tr>
    <td width="10%" align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.xlx.bgid" /></label><font color="red">*</font><label>:</label></td>
    <td width="15%"><s:textfield id="xlid" name="xlid" onblur="checkLength(this,3)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.xlx.mc" /></label><font color="red">*</font><label>:</label></td>
    <td><s:textfield id="xlmc" name="xlmc" onblur="checkLength(this,50)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="sysModule.ggdmgl.cbsjpz.xlx.sort" />:</label></td>
    <td><s:textfield id="pxbm" name="pxbm" onblur="checkLength(this,8)" cssClass="ui-input"/></td>
  </tr>
  <tr>
    <td align="right"><label><s:text name="login.lang" />:</label></td>
    <td><s:select id="xzyy" name="xzyy" list="xzyyList" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
  </tr>  
  <tr>
    <td colspan="2" align="center"><input type="button" class="ext_btn" onclick="save()" value="<s:text name="sysModule.ggdmgl.xtcs.bc"/>"></td>
  </tr>    
</table>
</form>
</body>
</html>