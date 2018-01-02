<%
   	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	int totalPage = Integer.valueOf(request.getParameter("totalPage"));
	int currentPage = Integer.valueOf(request.getParameter("currentPage"));
	//String formId = request.getParameter("form");
	//String tabId = request.getParameter("tab");
	if(totalPage>1){
		int s = 1;
		int e = totalPage;
		if(currentPage >=1 && currentPage <= 5){
			if(totalPage > 10) e = 10;
		}else if(currentPage > 5 && (totalPage - currentPage)>5){
			s = currentPage - 4;
			e = currentPage + 5;
		}else{
			if(totalPage > 10) s = totalPage - 9;
		}
		%>
		<ul class="ui-pagination">
		<%
		if(s >= 1){
			if(currentPage == 1){
			%>
			<li class="ui-pagination-current"><a href="javascript:;">&laquo;</a></li>
			<%
			}else{
				%>
				<li><a href="javascript:;" data-page="1">&laquo;</a></li>
				<%
			}
		}
		for(int i=s ; i<=e; i++){
			if(currentPage == i){
				%><li class="ui-pagination-current"><a href="javascript:;"><%=currentPage %></a></li><%
			}else{
				%><li><a href="javascript:;" data-page="<%=i %>"><%=i %></a></li><%
			}
		}
		if( e<= totalPage){
			if(currentPage == totalPage){
			%>
			<li class="ui-pagination-current"><a href="javascript:;">&raquo;</a></li>
			<%
			} else{
			%>
			<li><a href="javascript:;" data-page="<%=totalPage %>">&raquo;</a></li>
			<%
			}
		}
		%></ul><%
	}else if(totalPage == 1){
		%>
		<ul  class="ui-pagination">
		  <li class="ui-pagination-current"><a href="javascript:;">&laquo;</a></li>
		  <li class="ui-pagination-current"><a href="javascript:;">1</a></li>
		  <li class="ui-pagination-current"><a href="javascript:;">&raquo;</a></li>
		</ul>
		<%
	}
%>
<script type="text/javascript" src="<%=path %>/public/js/jquery.js"></script>
<script type="text/javascript">
$.noConflict();
jQuery(document).ready(function($){
	$(".ui-pagination").find("a").click(function(){
		var pageNo = $(this).attr("data-page");
		if(!pageNo)return false;
		if(pageHandler)pageHandler(pageNo);
		//$("#pageNo").val(pageNo);
		//form.submit();
		//var url = form.attr("action");
		//var data = form.serialize();
		//tab.load(url+"?"+data+"&pageNo="+pageNo);
	});
});
</script>
