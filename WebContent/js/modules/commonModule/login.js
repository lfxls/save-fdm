Ext.onReady(function() {
	var czyId = $F('czyId');
	//初始定位光标输入框
	if (trim(czyId)=='') {
		$('czyId').focus();
	}else{
		$('pwd').focus();
	}

	//清除错误消息显示
    $('czyId').onclick = function(){
		$('errid').style.display = 'none'; 
    };
    $('pwd').onclick = function(){
    	$('errid').style.display = 'none'; 
    };
    
    //清除输入框值
//    $('czy_p').onclick = function(){
//		$("czyId").value = "";
//    };
//    $('pwd_p').onclick = function(){
//    	$("pwd").value = "";
//    };
});

function maxWin() {
	window.moveTo(0, 0);
    window.resizeTo(screen.availWidth, screen.availHeight);
}

function login() {
	var czyid = $F('czyId');
	var pwd = $F('pwd');
	if ($E(czyid)) {
		$('errmsg').innerHTML = login_null;
		$('errid').style.display = '';
		$("czyId").value = "";
		$("czyId").focus();
		return;
	}

	if ($E(pwd)) {
		$('errmsg').innerHTML = login_null;
		$('errid').style.display = '';
		$("pwd").value = "";
		$("pwd").focus();
		return;
	}
	
	var lang =$F('lang');
	var p = {
		czyId : czyid,
		pwd : pwd,
		lang : lang
	};
	var url = ctx + '/login!login.do';
	Ext.Ajax.request( {
		url : url,
		params : p,
		loadMask : true,
		method:'POST',
		success : function(result, request) {
			var re = result.responseText;
			if (re == 'null') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_null;
				return;
			} else if (re == 'notfound') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_notfound;
				return;
			} else if (re == 'pwd') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_pwd;
				return;
			} else if (re == 'lock') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_lock;
				return;
			} else if (re == 'out') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_out;
				return;
			} else if (re == 'norole') {
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_norole;
				return;
			} else if(re== "login_bindIp"){
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_bindIp;
				return;
			} else if(re== "login_bindDate"){
				$('errid').style.display = '';
				$('errmsg').innerHTML = login_bindDate;
				return;
			} else if(re == 'ok') {
				window.location = ctx + '/login!loginSuccess.do';
			}
		},
		failure : function(res) {
			$('errmsg').innerHTML = 'error';
		}
	});

}

document.onkeydown = onkey;
function onkey(e) {
	var key = '';
	if(Ext.isIE) {
		key = window.event.keyCode;
	} else {
        key = e.which;
	}
	if(key == 13) {
		login();
	}
}

function changeLang(){
	var lang =$F('lang');
	var url = String.format(ctx + '/login!init.do?lang={0}',lang);
	location.href=url;
}

maxWin();
