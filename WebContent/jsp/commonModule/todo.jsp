<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/view_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/todo.js"></script>
</head>
<body class="body">
<form id="queryForm" onsubmit="return false;">
<table class="fluid-grid">
  <tr>
    <td width="12%" align="center"><label><s:text name="common.todo.ywlx" /></label></td>
    <td width="15%" class="label-group"><s:select id="ywlx" name="ywlx" list="ywlxLs" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
    <td width="12%" align="center"><label><s:text name="common.todo.zt" /></label></td>
    <td width="15%" class="label-group"><s:select id="dbzt" name="dbzt" list="dbztLs" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
    <td width="5%" ></td>
    <td >
	    <a class="ui-button ui-button-small ui-button-blue" href="#" onclick="query();"><label><s:text name="common.qzsz.cx" /></label></a>
    </td>
  </tr>
</table>
</form>
<div class="hr10"></div>
<div id="grid"></div>
</body>
</html>