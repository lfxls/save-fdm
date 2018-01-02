<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/meta.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
		<script type="text/javascript" src="${ctx}/public/extjs/ExtPagingTree.js"> </script>
		<script type="text/javascript" src="${ctx}/js/locale/commonModule/left/leftDw_${appLang}.js"> </script>
		<script type="text/javascript" src="${ctx}/js/modules/commonModule/left/leftS.js"> </script>
	</head>
	<body class="fn-no-padding">
	<div class="side-area">
		<div class="leftTab">
			<ul>
				<s:if test="supportTerminal=='true'">
				<li class="ui-side-tab-item ui-side-tab-item-current" onclick="javascript:changeViews('sb')" title='<s:text name="left.tab.sb"/>'>
					<a class="icon-terminal" href="###"></a>
				</li>
				</s:if>
				<li class="ui-side-tab-item" onclick="javascript:changeViews('bj')" title='<s:text name="left.tab.bj"/>'>
					<a class="icon-meter" href="###"></a>
				</li>
				<li class="leftMenuOff" onclick="javascript:changeViews('cx')" title='<s:text name="left.tab.cx"/>'>
					<s:text name="left.tab.cx"/>
				</li>
			</ul>
		</div>
	  	<div class="mainAread">
			<s:hidden id="viewType" name="viewType"/>
			<div id="tree"></div>
		</div>
	</div>
	</body>
</html>