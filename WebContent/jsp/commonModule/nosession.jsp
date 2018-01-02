<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp" %>
    <title><s:text name="session.title"/></title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body style="background:#fffaec">
    <div class="session-message">
        <div class="session-bg-sun">
        </div>
        <div class="session-wayout">
            <p><s:text name="session.content"/> <a href="javascript:logout()"><s:text name="session.relogin"/></a></p>
        </div>  
    </div>
    <div class="session-bg-ground">
        <div class="session-bg-dog"></div>
    </div>
</body>
<script language="javascript">
	function logout(){
		var url =  '<s:url action="login" namespace="/" method="init" />';
		//没有父窗口
		if(window.opener && (typeof(window.opener.document)=='unknown'||typeof(window.opener.document) =='undefined')){
			top.location.href=url;
		}else{
			top.location.href =url;	
		}
	}

</script>
<html>