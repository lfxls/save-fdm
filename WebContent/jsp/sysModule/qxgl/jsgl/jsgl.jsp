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
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/jsgl/jsgl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/jsgl/jsgl.js"> </script>
</head>
<body>

<input id="jsid" name="jsid" type="hidden">
<table width="100%" height="100%" >
	<tr>
		<td valign="top" width="20%" style="padding-right:15px">
			<div id="grid"></div>
			<div class="hr10"></div>
			<div class="ui-panel">
				<div class="ui-panel-header">
					<s:text name="sysModule.qxgl.jsgl.query.title"/>
				</div>
				<div class="ui-panel-body">
					<div class="ui-panel-item" style="border-top:0">
					<table class="fluid-grid">
						<tr>
							<td align="right" width="10%">
								<input id="jsid" name="jsid" type="hidden">
								<label class="ui-label"><s:text name="sysModule.qxgl.jsgl.jsmc"/>:</label>
							</td>
							<td>
								<input class="ui-input" id="jsmc" name="jsmc" type="text" onblur="checkLength(this,32);">
							</td>
						</tr>
						<tr style="display: none">
							<td>
							</td>
							<td>
								<input type="checkbox" name="cduBz" id="cduBz" value ="1" style="margin-right:3px"/>
								<s:text name="sysModule.qxgl.jsgl.cdubz"/>
							</td>
						</tr>
						<tr style="display: none">
							<td align="right" width="10%"><label class="ui-label"><s:text name="sysModule.qxgl.jsgl.kfwzy"/></label></td>
							<td>
								<input type="checkbox" name="electricity" id="electricity" value ="03" style="margin-right:3px" checked="checked"/><s:text name="sysModule.qxgl.jsgl.electricity"/>
								<input type="checkbox" name="water" id="water" value ="02" style="margin-right:3px" checked="checked"/><s:text name="sysModule.qxgl.jsgl.water"/>
								<input type="checkbox" name="gas" id="gas" value ="03" style="margin-right:3px" checked="checked"/><s:text name="sysModule.qxgl.jsgl.gas"/>
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<button onclick="saveJs()" class="ext_btn"><s:text name="sysModule.qxgl.czygl.edit.save"/></button>
							</td>
						</tr>
					</table>
					</div>
				</div>
			</div>
		</td>
		<td valign="top" width="26%" style="padding-right:15px">
			<div class="ext-panel">
			 <div id="cdtree" ></div>
			 </div>
		</td>
		<td valign="top" width="26%" style="padding-right:15px">
			<div class="ext-panel">
			 <div id="cztree" class="ext-panel"></div>
			  </div>
		</td>
		<td valign="top" width="26%" style="padding-right:15px">
			<div class="ext-panel">
			 <div id="sytree" class="ext-panel" ></div>
			  </div>
		</td>
		<td valign="top" width="20%" style="display: none">
			<div class="ext-panel">
			 <div id="dytree" class="ext-panel"></div>
			  </div>
		</td>
	</tr>
</table>
</html>