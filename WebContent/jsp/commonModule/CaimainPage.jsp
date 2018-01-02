<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%> --%>
<!DOCTYPE html>
<html>

<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/caiMainPage.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/cai.css" />
<title>Home</title>
</head>
<body class="body">
	<%-- 	<%
List<Object> categories=new ArrayList<Object>();
for(int i=0;i<5;i++)
{
	Map<String,String> map1 = new HashMap<String,String>();
	 map1.put("xman", "david");
	 map1.put("yman","99999");
	 categories.add(map1);
}
System.out.println(((Map<String,String>)categories.get(0)).get("xman")); 
request.setAttribute("categories",categories);
%> --%>
	<!--这个count记录了List的大小，js判断应该显示几个，剩下的display:none; -->
	<div id="headShow" class="content">
		<div class="background">
			<span class="cat-title">Alarm Dashboard</spcan>
				<%-- <div class="cat-punch-line">
					<img src="${ctx}/public/images/cai-slide-title-border.png"
						alt="boder"> <span>Simply Easy Monitoring</span> <img
						src="${ctx}/public/images/cai-slide-title-border.png" alt="boder">
				</div> --%>
		</div>
	</div>
	<input type="hidden" id="deptSize" name="deptSize" value="${deptSize}" />
	<s:set name="deptSize" value="deptSize" />
	<div class="container">
	<s:if test="#deptSize!= 0">
		<s:iterator status="stat" value="list" var="s">
			<s:if test="#stat.modulus(4)==1">
				<div class="rows">
					<div class="row">
						<table class="fluid-table" style="width: 100%">
							<tr>
								<td style="padding-left: 5px; padding-right: 5px; width: 20%;">
									<button id="department_<s:property value="%{#stat.count}"/>"
										class="cai_btn"
										style="display: none; background-color: #009933; border-color: #63a680;"
										onclick="feedback(this.id);">
										<span class="button_inner_text"> <s:if
												test="#stat.count<=#deptSize">
												<s:property value="list.get(#stat.count-1).DWMC" />
											</s:if>
										</span>
										<%-- <s:property value="#s.DWMC"/> --%>
									</button>
								</td>
								<td style="padding-left: 5px; padding-right: 5px; width: 20%;">
									<button id="department_<s:property value="%{#stat.count+1}"/>"
										class="cai_btn"
										style="display: none; background-color: #009933; border-color: #63a680;"
										onclick="feedback(this.id);">
										<span class="button_inner_text"> <s:if
												test="#stat.count+1<=#deptSize">
												<s:property value="list.get(#stat.count).DWMC" />
											</s:if>
										</span>
									</button>
								</td>
								<td style="padding-left: 5px; padding-right: 5px; width: 20%;">
									<button id="department_<s:property value="%{#stat.count+2}"/>"
										class="cai_btn"
										style="display: none; background-color: #009933; border-color: #63a680;"
										onclick="feedback(this.id);">
										<span class="button_inner_text"> <s:if
												test="#stat.count+2<=#deptSize">
												<s:property value="list.get(#stat.count+1).DWMC" />
											</s:if>
									   </span>
									</button>
								</td>
								<td style="padding-left: 5px; padding-right: 5px; width: 20%;">
									<button id="department_<s:property value="%{#stat.count+3}"/>"
										class="cai_btn"
										style="display: none; background-color: #009933; border-color: #63a680;"
										onclick="feedback(this.id);">
										<span class="button_inner_text"> <s:if
												test="#stat.count+3<=#deptSize">
												<s:property value="list.get(#stat.count+2).DWMC" />
											</s:if>
										</span>
									</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="gap3"></div>

			</s:if>
		</s:iterator>
		</s:if>
	</div>
	<div id="forHiddens"></div>
</body>
</html>