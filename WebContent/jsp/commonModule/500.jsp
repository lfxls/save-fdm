<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>500</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/hexing.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/other.css" />
</head>
<body class="fn-no-padding" style="background:#d3d4c6">
  <div class="error-500">
    <div class="error-header">
    	<div class="error-header-main">
	    	<div class="error-header-wrapper">
		    	<p class="opps">Opps ...</p>
		    	<div>
		    		<span class="error-action">Why not back to <a href="javascript:logout()">homepage</a></span>
		    		<span>it appears there has been an internal server error</span>
		    	</div>
	    	</div>
    	</div>
    	<div class="error-header-wave"></div>
    </div>
    <div class="error-ground"></div>
    <div class="error-bg"></div>
  </div>
</body>
<script language="javascript">
	function logout(){
		var url =  '<%=request.getContextPath()%>/login!init.do';
		//没有父窗口
		if(window.opener && (typeof(window.opener.document)=='unknown'||typeof(window.opener.document) =='undefined')){
			top.location.href=url;
		}else{
			top.location.href =url;	
		}
	}

</script>
</html>