<%@page import="cn.hexing.ami.dao.common.pojo.XtCd"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp" %>
<title>
	${sysTitle }
</title>
<STYLE type="text/css">
.CONN:link {font-weight:bold;text-decoration: none;}/*超链接文字格式*/ 
.CONN:visited {font-weight:bold;text-decoration: none; }/*浏览过的链接文字格式*/ 
.CONN:active {font-weight:bold; text-decoration: none;}/*按下链接的格式*/ 
.CONN:hover {font-weight:bold;text-decoration: none; }/*鼠标转到链接*/ 
</STYLE>
<script type="text/javascript" src="${ctx}/public/js/jquery.js"></script>
<script type="text/javascript">
$.noConflict();
</script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtMenu.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/view_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/view.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/tabCloseMenu.js"> </script>
</head>
<c:set var="firstMenuId" value=""  />
<c:forEach items="${rootMenus}" var="menu" varStatus="status">
	<c:if test="${status.first}"><c:set var="firstMenuId" value="${menu.id}"  /></c:if>
</c:forEach>
<script language="javascript">
var firstMenuId = "${firstMenuId}";
var syJson = Ext.util.JSON.decode('${syJson}');
var helpDocAreaId = "${helpDocAreaId}";
var cdIds = "${cdIds}";
var licenseDays = "${LICENSE_DAYS}";
var xtid = "${xtid}";
</script>
<body>
<input type="hidden" name="sbgjFlag" id="sbgjFlag" value="${sbgjFlag}"/>
<input type="hidden" name="alermRefreshInterval" id="alermRefreshInterval" value="${alermRefreshInterval}"/>
<div id="toptb">
	<div class="site-header">
		<div class="site-nav-container">
			<div class="logo-area">
			<%--<div class="logo-image"><a href="###"><img src="${ctx}/public/images/login/logo-s.png"></a></div> --%>
				<div class="logo-image"><img src="${ctx}/public/images/login/logo-s.png"></a></div>
				<div class="logo-text">
					<span><s:text name="login.homepage.system.text"/></span>
				</div>
			</div>


			<div id="fullHead" class="nav-menu-area">
				<ul class="ui-nav">				  
                    <c:forEach items="${rootMenus}" var="menu">
                      <li class="ui-nav-item" id="<c:out value="${menu.id}"/>">
                      	<a href="javascript:;"><i class="icon-<c:out value='${menu.id}'/>"></i><c:out value="${menu.text}"/></a>
                      </li>
                    </c:forEach>
				</ul>
			</div>
			<div class="nav-respond-area"></div>
			<div class="user-action-area">
				<ul class="ui-nav">
				  <li id="user-menu" class="ui-nav-item fn-relative">
				    <a href="###"><c:out value='${CURR_STAFF.xm}'/></a>	
				    <ul class="dropdown">
				    	<li><a href="javascript:initPwd();"><i class="icon icon-account"></i><s:text name="view.profile"/></a></li>
				    	<li class="dropdown-divider"></li>
				    	<li><a href="javascript:loginOut();"><i class="icon icon-logout"></i><s:text name="view.logout"/></a></li>
				    </ul>
				  </li>
				</ul>
			</div>
		</div>
	</div>

</div>
<div id="sub-nav" class="sub-nav" style="display: none;">
	<%
		List<XtCd> root = (List<XtCd>)request.getAttribute("rootMenus");
		for(XtCd cd:root){
			String text=cd.getId();
			Map<String,Object> menu = cd.getMenu();
			if(!menu.isEmpty()){
				List<XtCd> item = (List<XtCd>)menu.get("items");
				int leafNum = (Integer)menu.get("leafNum");
				int index = 0;
				int flag = (int)(leafNum/3d+0.2d);//leafNum<20?6:leafNum<40?10:14;
				//int flag = (int)(leafNum/2.5d+0.5d);//leafNum<20?6:leafNum<40?10:14;
			%>
<table id="sub<%=cd.getId()%>" class="sub-nav-container" data-leaf="<%=leafNum%>">
		<td class="sub-nav-col">
		<%
				//if(item.size()>6)flag = 14;
				for(int i=0,s=item.size();i<s;i++){
					XtCd cd1 = item.get(i);
					if(cd1.getIsLeaf().equals("0")&& index >=flag){
					%>
					</td>
					<td class="sub-nav-col">
					<%
					index = 0;
					}
					if(cd1.getIsLeaf().equals("0")){
					%><h3><i class="icon-<%=cd1.getId()%>"></i><%=cd1.getText() %></h3><%
					Map<String,Object> menu1 = cd1.getMenu();
					if(menu1== null || menu1.isEmpty()) continue;
					List<XtCd> item1 = (List<XtCd>)menu1.get("items");
					for(int j=0,s1=item1.size();j<s1;j++){
						XtCd cd2 = item1.get(j);
						if(cd2.getIsLeaf().equals("0")&& index >=flag){
						%>
						</td>
						<td class="sub-nav-col">
						<%
						index=0;
						}
						if(cd2.getIsLeaf().equals("0")){
							%><h3><i class="icon-<%=cd2.getId()%>"></i><%=cd1.getText() +"-"+ cd2.getText() %></h3><%
							Map<String,Object> menu2  = cd2.getMenu();
							if(menu2!=null && !menu2.isEmpty()){
								List<XtCd> item2 = (List<XtCd>)menu2.get("items");
								for(XtCd cd3:item2){
									if(cd3.getIsLeaf().equals("0")&& index >=flag){
										%>
										</td>
										<td class="sub-nav-col">
										<%
										index=0;
									}
									if(cd3.getIsLeaf().equals("1")){
										%><a href="javascript:;" id="<%=cd3.getId()%>" data-url="<%=cd3.getUrl()%>" data-text="<%=cd3.getText()%>">
										<i class="icon-dot"></i><%=cd3.getText() %></a><%
										index++;
									}
								}
							}
						
						}else{
						%><a href="javascript:;" id="<%=cd2.getId()%>" data-url="<%=cd2.getUrl()%>" data-text="<%=cd2.getText()%>">
						<i class="icon-dot"></i><%=cd2.getText() %></a><%
						}
						index++;
					}
					}else{
					%><a href="javascript:;" id="<%=cd1.getId()%>" data-url="<%=cd1.getUrl()%>" data-text="<%=cd1.getText()%>">
					  <i class="icon-dot"></i><%=cd1.getText() %></a><%
					}
					index++;
					
				}
			}
		%>
	</td>
</table>
			<%
		}
	%>
</div>
</body>
<script type="text/javascript">

jQuery(document).ready(function($){
	var siteHeaderAction = {
			setVar : function(){
				siteHeaderAction.sub_nav_listener = $("#sub-nav");
				siteHeaderAction.sub_nav_trigger_parent = $('.nav-menu-area');
				siteHeaderAction.sub_nav_trigger = $('.nav-menu-area .ui-nav-item');
				
				siteHeaderAction.dropdown_trigger = $('#user-menu');
				siteHeaderAction.dropdown_listener = $('#user-menu').find('.dropdown');
			},
			
			bindOpenTreeHandler : function(){
				siteHeaderAction.sub_nav_listener.delegate('a','click',function(){
					var id = $(this).attr("id");
					var url = $(this).attr("data-url");
					var text = $(this).attr("data-text");
					if(!url || url == null || url == "null")return false;
					url = ctx + url +'?timestamp=' + new Date().getTime();
					openpageOnTree(text,id,text,'',url,'yes',1);
					siteHeaderAction.sub_nav_listener.hide();
				})
			},
			subMenuMouseenter : function(){
				siteHeaderAction.sub_nav_trigger_parent.delegate('.ui-nav-item', 'mouseenter', function(){
					siteHeaderAction.sub_nav_trigger.removeClass('ui-nav-item-active');
					$(this).addClass('ui-nav-item-active');
					var txt = $(this).attr('id');
					siteHeaderAction.sub_nav_listener.find('table').hide();
					siteHeaderAction.sub_nav_listener.find('#sub'+txt).show();
					siteHeaderAction.sub_nav_listener.show();
				});
			},
			subMenuMouseleave : function(){
				$('.logo-area').on('mouseenter',function(e){
					siteHeaderAction.sub_nav_trigger.removeClass('ui-nav-item-active');
					siteHeaderAction.sub_nav_listener.hide();
				});
				$('.nav-respond-area').on('mouseenter',function(e){
					siteHeaderAction.sub_nav_trigger.removeClass('ui-nav-item-active');
					siteHeaderAction.sub_nav_listener.hide();
				});
				
				siteHeaderAction.sub_nav_listener.on('mouseleave', function(){
					siteHeaderAction.sub_nav_trigger.removeClass('ui-nav-item-active');
					siteHeaderAction.sub_nav_listener.hide();
				});
			},
			initSubMenu : function(){
				siteHeaderAction.bindOpenTreeHandler();
				siteHeaderAction.subMenuMouseenter();
				siteHeaderAction.subMenuMouseleave();
			},
			
			initDropdown : function(){
				siteHeaderAction.dropdown_trigger.on('mouseenter', function(){
					siteHeaderAction.dropdown_trigger.addClass('ui-nav-item-active');
					siteHeaderAction.dropdown_listener.show();
				})
				siteHeaderAction.dropdown_trigger.on('mouseleave', function(){
					siteHeaderAction.dropdown_trigger.removeClass('ui-nav-item-active');
					siteHeaderAction.dropdown_listener.hide();
				})
			},
			
			init : function(){
				siteHeaderAction.setVar();
				siteHeaderAction.initSubMenu();
				siteHeaderAction.initDropdown();
			}
	}

	
	siteHeaderAction.init();
});
</script>
</html>