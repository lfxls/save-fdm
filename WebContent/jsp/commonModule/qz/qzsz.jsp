<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qzsz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/qzsz.js"> </script>
</head>
<!-- 
<body id="bodycss" style="background-repeat:repeat;" onkeydown="keyDown()">
 -->
<body class="body">
	<form id="qzszQueryForm" name="qzszQueryForm" onsubmit="return false;" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="common.qzsz.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<table class="fluid-grid">
						<tr>
							<td align="right" width="10%">
								<label class="ui-label"><s:text name="common.qzsz.qzm"></s:text>:</label>
							</td>
							<td align="left" width="17%">
								<s:textfield name="nodeText" id="nodeText" value="" cssClass="ui-input" />
							</td>
							
							<td width="10%">
							</td>
							<td width="17%">
							</td>
							
							<td width="10%">
								<input type="button" onClick="query()" class="ext_btn"
								value="<s:text name="common.qzsz.cx"></s:text>" />
							</td>
							<td width="17%">
							</td>
							
							<td width="19%">
							</td>
						</tr>
					</table>
				</div>
				
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">

</script>
</html>
