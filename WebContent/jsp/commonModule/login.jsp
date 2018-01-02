<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
	<meta charset="UTF-8"/>
	<title>
		${sysTitle }
	</title>
	
<script type="text/javascript" src="${ctx}/js/locale/commonModule/login_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/login.js"></script>
</head>
<body class="fn-no-padding">
	<table class="login-frame">
		<tr class="login-header">
			<td style="height:85px;width:40%">
				<div class="logo-text">
					HEXING
				</div>
			</td>
			<td>
				<div class="banner-text"><s:text name="login.04.title"/></div>
			</td>
			<td style="height:85px;width:40%">
				<div class="lang-select">
					<i class="fa fa-globe"></i><span class="lang-result">ENGLISH</span><i id="lang-icon" class="fa fa-chevron-circle-down"></i>
					<div class="lang-drop" id="lang-drop" style="display:none">
						<a rel="en_US">ENGLISH</a>
						<a rel="zh_CN">中文</a>
					</div>
				</div>
				
			</td>
		</tr>
		<tr>
			<td rowspan="3"><s:select tabindex="3" id="lang" name="lang" list="langList" listKey="BM" listValue="MC" cssClass="ui-select" onchange="changeLang()" cssStyle="display:none" /></td>
			<td class="login-banner">
				<img src="${ctx}/public/images/login/deco.png">
			</td>
			<td rowspan="3"></td>
		</tr>
		<tr>
			<td class="login-body-wrapper">
				<div class="login-body">
					<form id="queryForm" onsubmit="return false;">
						
						<div class="ui-form-item fn-relative">
							<label class="pass-label fa fa-user" ></label>
							<input tabindex="1" type="text" id="czyId" name="czyId" value="<c:out value='${cookie.userName.value}'/>" placeholder="<s:text name="login.czy"/>" class="ui-input user-input">
						</div>
						<div class="ui-form-item fn-relative">
							<label class="pass-label fa fa-lock" ></label>
							<input tabindex="2" type="password" id="pwd" name="pwd" placeholder='<s:text name="login.pwd"/>'  class="ui-input password-input">	
						</div>
						<div class="ui-form-item">
							<a class="login-button" href="javascript:login();">Login</a>
						</div>
						<div id="errid" class="login-form-error" style="display:none">
							<p id="errmsg"></p>
						</div>
					</form>

				</div>
			</td>
		</tr>
		<tr>
			<td class="login-footer">
				<span>Producted By</span>
				<img src="${ctx}/public/images/login/hexing.png">
			</td>
		</tr>
	</table>
</body>
<script>
	var langSelect = {
		setVars : function(){
			langSelect.drop = jQuery('#lang-drop');
			langSelect.trigger = jQuery('.lang-select');
			langSelect.result = jQuery('.lang-result');
			langSelect.icon = jQuery('#lang-icon');
			langSelect.menuItem = jQuery('#lang-drop a');
			langSelect.hiddenSelect = jQuery('#lang');
		},
		
		event_lang_toggle : function(){
			langSelect.drop.toggle();
		},
		
		event_text_update : function(){
			var _text = jQuery("option:selected", langSelect.hiddenSelect).text();
			langSelect.result.text(_text);
		},
		
		event_icon_update : function(){
			if(langSelect.drop.is(':hidden')){
				langSelect.icon.attr('class', 'fa fa-chevron-circle-down');
			}else{
				langSelect.icon.attr('class', 'fa fa-chevron-circle-up');
			}
		},
		
		event_lang_select : function(){
			var _value = jQuery(this).attr('rel');
			jQuery("option[value=" +_value + "]",langSelect.hiddenSelect).attr('selected', true);
			langSelect.hiddenSelect.trigger("change");
			
		},
		
		bindEvents : function(){
			langSelect.trigger.on('click', langSelect.event_lang_toggle);
			langSelect.trigger.on('click',langSelect.event_icon_update);
			langSelect.menuItem.on('click',langSelect.event_lang_select);
			langSelect.menuItem.on('click',langSelect.event_text_update);
			
		},
		
		init : function(){
			
			langSelect.setVars();
			langSelect.bindEvents();
			langSelect.event_text_update()
		}
			
	}
	
	langSelect.init();
</script>
</html>