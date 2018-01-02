<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>403</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/hexing.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/other.css" />
</head>
<body class="fn-no-padding">
  <div class="error-403">
    <div class="error-dialog">
    	<p class="stop">Stop !!!</p>
    	<p>It seems you got somewhere you didn’t supposed to ...</p>
    	<p class="error-action">Why not go back to <a href="javascript:logout()">homepage</a></p>
    	<div class="error-triangle"></div>
    </div>
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