<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/autoBan/css/style.css" />
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/fb/photo.js"></script>
</head>
<body class="body">
<s:hidden id="flag" name="flag" />
<s:hidden id="msn" name="msn" />
<s:hidden id="pid" name="pid" />
<s:hidden id="verid" name="verid" />
<s:hidden id="pics" name="pics" />
<s:hidden id="spics" name="spics" />
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current"><a href="#"><s:text
						name="mainModule.insMgt.fb.photo" /></a>
			</li>
			<li class="ui-tab-item"><a
				href="${ctx}/main/insMgt/insFb!initMReadParam.do?flag=${flag}&msn=${msn}&pid=${pid}&verid=${verid}"><s:text
						name="mainModule.insMgt.fb.readParam" /></a>
			</li>
			<s:if test="flag == 0">
				<li class="ui-tab-item"><a
					href="${ctx}/main/insMgt/insFb!initMSetParam.do?flag=${flag}&msn=${msn}&pid=${pid}&verid=${verid}"><s:text
							name="mainModule.insMgt.fb.setParam" /></a>
				</li>
				<li class="ui-tab-item"><a
					href="${ctx}/main/insMgt/insFb!initMTestParam.do?flag=${flag}&msn=${msn}&pid=${pid}&verid=${verid}"><s:text
							name="mainModule.insMgt.fb.testParam" /></a>
				</li>
			</s:if>
		</ul>
	</div>
	
	<div class="hr10"></div>
	<div id="noData"></div>
	<div id="mod18" class="mod18">
		<div id="picBox" class="picBox"></div>
	</div>

	<div id="mod19" class="mod19">
		<span id="prev" class="btn prev"></span>
		<span id="next" class="btn next"></span>
		<div id="listBox" class="listBox"></div>
	</div>
</body>
</html>