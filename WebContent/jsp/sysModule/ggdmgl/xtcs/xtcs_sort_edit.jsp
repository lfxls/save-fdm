<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/xtcs/xtcs_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/xtcs/xtcs_sort_edit.js"> </script>
</head>
<body class="body">
<s:hidden id="paraSortId" name="paraSortId"/>
<s:hidden id="czbs" name="czbs"/>
<table class="fluid-grid">
	<tr>
	  <td width="20%" align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csflbm"/></label><font color="red">*</font></td>
	  <td width="20%">
	  	<input type="text" id="csflbm" name="csflbm" class="ui-input" value="<s:property value='csfl.p_sort_code'/>"/>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csflmc"/></label><font color="red">*</font></td>
	  <td>
	  	<input type="text" id="csflmc" name="csflmc" class="ui-input" value="<s:property value='csfl.name'/>"/>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.yxbz"/></label><font color="red">*</font></td>
	  <td>
	  	<s:select id="syzt" name="syzt" list="syztList" listKey="BM" listValue="MC" cssClass="ui-select"></s:select>
	  </td>
	</tr>
	<tr>
	  <td align="right"><label><s:text name="sysModule.ggdmgl.xtcs.csflms"/></label></td>
	  <td>
			<input type="text" id="csflms" name="csflms" class="ui-input" value="<s:property value='csfl.sort_desc'/>" size="30"/>
	  </td>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	  	<input type="button" onClick="editParaSort();" class="ext_btn" value="<s:text name="sysModule.ggdmgl.xtcs.bc"/>"></td>
	</tr>	
</table>
</body>
</html>