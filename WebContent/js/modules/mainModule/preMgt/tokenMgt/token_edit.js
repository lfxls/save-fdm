var menuId = '14100';
loadProperties('mainModule', 'mainModule_preMgt');

Ext.onReady(function() {
	hideLeft();
});

function save(operateType){
	if('add' == operateType){
		addToken();
	}	
	if('edit' == operateType){
		updToken();
	}
}

//新增Token
function addToken() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var tid = $F('tid');
	var msn = $F('msn');
	var token = $F('token');
	//把'-'过滤
	token=token.replace(/-/g,'');
	
	var cno = $F('cno');
	var orderid = $F('orderid');
	var p = {
		tid : tid,
		msn : msn,
		token : token,
		cno : cno,
		orderid : orderid,
		logger : main.preMgt.tokenMgt.add.logger + token
	};
	
	//var p=$FF('tokenForm');
		
	Ext.apply(p,{logger:main.preMgt.tokenMgt.add.logger + $F('token')});//apply属性拷贝，会覆盖

	Wg.confirm(main.preMgt.tokenMgt.add.confirm, function() {
		dwrAction.doDbWorks('tokenMgtAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

//修改Token
function updToken() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var tid = $F('tid');
	var msn = $F('msn');
	var token = $F('token');
	//Token把'-'过滤
	token=token.replace(/-/g,'');
	
	var cno = $F('cno');
	var orderid = $F('orderid');
	var p = {
		tid : tid,
		msn : msn,
		token : token,
		cno : cno,
		orderid : orderid,
		logger : main.preMgt.tokenMgt.upd.logger + token
	};
	
	//var p=$FF('tokenForm');
	
	Ext.apply(p,{logger:main.preMgt.tokenMgt.upd.logger + $F('token')});//apply属性拷贝，会覆盖
	Wg.confirm(main.preMgt.tokenMgt.upd.confirm, function() {		
		dwrAction.doDbWorks('tokenMgtAction', menuId + '02', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {					
					parent.query();
					parent.win.close();					
				}
			});
		});
	});
}

//检验函数
function dataValidate() {

	//$('msn').value = $F('msn').trim();
	
	var msn = $F('msn');
	var token = $F('token');
	
	var reg=/[a-zA-Z0-9]+/;
		
	if($E(msn)) {
		Ext.Msg.alert(ExtTools_alert_title, main.preMgt.tokenMgt.valid.msn, function(btn,text){
			if(btn=='ok'){
				$('msn').focus();
			}
		});
		return false;
	}
	
	if ( !$E(msn) ) {
		//var reg = /[a-zA-Z0-9]+/;
		var reg = /^[a-zA-Z0-9]+$/;
		var re = msn.match(reg);
		if (re == null) {
				Ext.Msg.alert(ExtTools_alert_title, main.preMgt.tokenMgt.valid.format.msn, function(btn,text){
				if(btn=='ok'){
					$('msn').focus();
				}
			});
			return false;
		}		
	}
	
	if($E(token)) {
		Ext.Msg.alert(ExtTools_alert_title, main.preMgt.tokenMgt.valid.token, function(btn,text){
			if(btn=='ok'){
				$('token').focus();
			}
		});
		return false;
	}
	
	var reg = /^\w{24}$/;
	var str = $F('token');
	//把'-'过滤
	str=str.replace(/-/g,'');
	//alert(str.length);
	if( !$E(str) && (str.length!=20) ){
		Ext.Msg.alert(ExtTools_alert_title,main.preMgt.tokenMgt.valid.tokenIs20Num, function(btn,text){
			if(btn=='ok'){
				$('token').focus();
			}
		});	
		return false;
	}	
	return true;	
}

//Token必须为20位的数字，-需要过滤掉
function Is20bitNumbers(obj) {
	/*//Token必须为20位的数字
	var reg = /^\d{20}$/;
	var str = $F('token');
	//把'-'过滤
	str=str.replace(/-/g,'');
	if( !$E(str) && !reg.test(str) ){
		Ext.Msg.alert(ExtTools_alert_title,main.preMgt.tokenMgt.valid.tokenIsNum, function(btn,text){
			if(btn=='ok'){
				$('token').focus();
			}
		});	
		return false;
	}
	//每4位加一个'-'
	obj.value = obj.value.replace(/(\w{4})(?=\w)/g,"$1-");*/
	
	//用于匹配字母，数字或下划线字符;
	var reg = /^\d*$/;
    var str = obj.value;
    //把'-'过滤
    str=str.replace(/-/g,'');
    if (!reg.test(str)) {//验证是否符合规则，不符合的话，把最后一个字符去掉
    	Ext.Msg.alert(ExtTools_alert_title,main.preMgt.tokenMgt.valid.tokenIsNum, function(btn,text){
			if(btn=='ok'){
				$('token').focus();
			}
		});	
		return false;
        /*var leng = str.length;
        var strTemp = str.substr(0, leng - 1);
        obj.value = strTemp;
        obj.focus();
        return;*/
    }
    //每4位加一个'-'
	obj.value = obj.value.replace(/(\w{4})(?=\w)/g,"$1-");
}
