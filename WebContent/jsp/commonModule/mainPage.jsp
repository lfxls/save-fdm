<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<%-- 
<script type="text/javascript" src="${ctx}/js/modules/commonModule/mainPage.js"> </script>
 --%>
</head>
<body>
	
	<table class="welcome-frame">
		<tr>
			<td class="welcome-left">
				<h3><s:text name="login.homepage.welcome.text"/></h3>  
				<h2><s:text name="login.homepage.system.text"/></h2>
			</td>
			<td class="welcome-right">
				<img src="${ctx}/public/images/login/mdc-bg.png">
			</td>
		</tr> 
	 </table>


	<div id="id_obj" style="display: none">
		<object classid="cltsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="100%" height="400" id="Untitled-1" align="middle">
		<param name="allowScriptAccess" value="sameDomain" />
		<param name="movie" value="focus.swf" /> 
		<param name="quality" value="high" /> 
		<param name="bgcolor" value="#ffffff" />
		<embed src="mymovie.swf" quality="high" bgcolor="#ffffff" width="100%" height="400" name="mymovie" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
		</object>
	
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"  type="application/x-shockwave-flash" data="c.swf?path=movie.swf" width="100%" height="300">
		<param name="movie" value="focus.swf" />
		</object>
	</div>
</body>
</html>