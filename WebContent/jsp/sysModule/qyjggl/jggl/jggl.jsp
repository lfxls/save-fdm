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
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qyjggl/jggl/jggl_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qyjggl/jggl/jggl.js"> </script>
</head>
<body class="fn-no-padding">
<table width="100%" height="100%">
	<tr>
		<td valign="top" width="260px" style="border-right:1px dashed #ccc">
			<div id="tree"></div>
			<c:if test='${sysMode!="1"}'>
			<div class="ui-panel" style="margin: 12px 15px">
				<div class="ui-panel-header">
					<s:text name="sysModule.qyjggl.jggl.cxs"/>
				</div>
				<div class="ui-panel-body">
					<div class="ui-panel-item" style="padding:0 15px 10px 15px">
						<div class="extend-form-item" >
							<h4><label><s:text name='sysModule.qyjggl.jggl.nodeName'></s:text></label></h4>
							<input style="width:100%" placeholder="<s:text name='sysModule.qyjggl.jggl.nodeName'></s:text>" type="text" size="14" name="dwmc" id="dwmc" class="ui-input"/>
						</div>
						<div class="extend-form-item">
							<h4><label><s:text name='sysModule.qyjggl.jggl.nodeCode'></s:text></label></h4>
							<input style="width:100%" placeholder="<s:text name='sysModule.qyjggl.jggl.nodeCode'></s:text>" type="text" size="14" name="dwdm" id="dwdm" class="ui-input"/>
						</div>
					</div>
					<div class="ui-panel-item">
						<input type="button" value='<s:text name="sysModule.qyjggl.jggl.btnQuery"></s:text>' class="ext_btn" onclick="queryDw()"/>
					</div>
				</div>
			</div>
			</c:if>
		</td>
		<td valign="top">
		<div class="ui-panel" style="margin:12px 15px">
			<div class="ui-panel-header"><s:text name="sysModule.qyjggl.jggl.name"/></div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
					<c:choose>
		  			<c:when test='${sysMode!="1"}'>
		  			<table class="fluid-grid">
						<tr>
							<td width="10%" align="right">
							   <label><s:text name="sysModule.qyjggl.jggl.node"/>:</label>
							 </td>
							<td width="17%" align="left">
								<s:textfield id="nodeText" name="nodeText" cssStyle="background-color:#cccccc;" 
								readonly="true" cssClass="ui-input"/>
								<s:hidden id="nodeType" name="nodeType"/>
							</td>
							
							<td width="10%" align="right">
							   <label><s:text name="sysModule.qyjggl.jggl.code"></s:text>:</label>
							</td>
							<td width="17%" align="left">
								<s:textfield id="nodeId" name="nodeId" cssStyle="background-color:#cccccc;" 
								readonly="true" cssClass="ui-input"/>
							</td>
							
							<td width="10%">
							</td>
							<td width="17%">
							</td>
							<td width="19%"></td>
						</tr>
					</table>
					</c:when>
					<c:otherwise>
						<s:hidden name="nodeId" id="nodeId"></s:hidden>
						<s:hidden name="nodeText" id="nodeText"></s:hidden>
						<s:hidden name="nodeType" id="nodeType"></s:hidden>
					</c:otherwise>
					</c:choose>
				</div>
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
		</td>
	</tr>
</table>
</html>