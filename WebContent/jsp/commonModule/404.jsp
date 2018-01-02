<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>404</title>
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/reset.css" />
     <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/hexing.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/css/other.css" />
</head>
<body class="fn-no-padding" style="background: #f9f9f9">
	<div class="error-404">
		<p class="error-message"><span>404</span> PAGE NOT FOUND</p>
		<p class="error-wayout">Why not try the <a href="javascript:logout()">homepage</a></p>
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