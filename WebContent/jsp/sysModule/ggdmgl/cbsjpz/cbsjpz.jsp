<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
<script type="text/javascript"
	src="${ctx}/js/locale/sysModule/ggdmgl/cbsjpz/cbsjpz_${appLang}.js"></script>
<script type="text/javascript"
	src="${ctx}/js/modules/sysModule/ggdmgl/cbsjpz/cbsjpz.js"></script>
</head>
<body class="body">
	<form id="queryForm" onsubmit="return false;" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="sysModule.ggdmgl.cbsjpz.title" />
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<s:hidden id="xlx" name="xlx" />
					<table class="fluid-grid">
						<tr>
							<td width="12%" align="center"><label><s:text
										name="sysModule.ggdmgl.cbsjpz.cblx" /></label></td>
							<td width="50%" class="label-group"><s:radio id="dlx"
									name="dlx" list="dlxList" listKey="BM" listValue="MC"
									onclick="changeDlx(this.value)"></s:radio></td>
							<td align="right"><a
								class="ui-button ui-button-small ui-button-blue" href="#"
								onclick="querySjx();"><label><s:text
											name="sysModule.ggdmgl.cbsjpz.sjzdys" /></label></a> <a
								class="ui-button ui-button-small ui-button-blue" href="#"
								onclick="queryXlx();"><label><s:text
											name="sysModule.ggdmgl.cbsjpz.bgfl" /></label></a> <a
								class="ui-button ui-button-small ui-button-blue" href="#"
								onclick="queryTx();"><label><s:text
											name="sysModule.ggdmgl.cbsjpz.txfl" /></label></a></td>
						</tr>
					</table>
				</div>
				<div class="hr10"></div>
				<div class="ui-tab">
					<ul class="ui-tab-items">
						<s:iterator value="xlxList" id="item" status="status">
							<li id="<s:property value="#item.BM"/>li" class="ui-tab-item"><a
								href="javascript:changeXlx('<s:property value="#item.BM"/>');"><s:property
										value="#item.MC" /></a></li>
						</s:iterator>
					</ul>
				</div>
				<div class="hr10"></div>
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>