<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>    
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czzygl/czzygl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czzygl/czzygl.js"> </script>
</head>
<body >
<table style="table-layout:fixed" width="100%" height="100%" >
	<tr>
		<td valign="top" ><div id="grid"></div></td>
		<td width="15px"></td>
		<td valign="top" width="50%">
			<div class="ui-panel">
				<div class="ui-panel-header">
					Panel Title
				</div>
				<div class="ui-panel-body">
					<div id="tree"></div>
				</div>
			</div>
			 <input type="hidden" id="czlbid"></input>
		</td>
	</tr>
</table>
</html>