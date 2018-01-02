<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<html>
<head>
<%@ include file="/jsp/meta.jsp" %>
<%
	List<Object> shortCutsLs = (List<Object>)request.getAttribute("shortCutsLs");
	shortCutsLs = shortCutsLs==null?new ArrayList<Object>():shortCutsLs;
	
	int records = shortCutsLs.size();
	int col = 7;
	int row = 0;
	if(records%col==0){
		row = shortCutsLs.size()/col;
	}else{
		row = shortCutsLs.size()/col+1;
	}
%>

<script>
	Ext.onReady(function() {
		hideLeft();
	});
	function openTabMenu(cdmc,cdid,url){
		url = ctx+url;
		top.openpageOnTree(cdmc,cdid,cdmc,'',url,'yes',1);
	}
	function delShortCuts(menuId){
		Wg.confirm(comm_kjfs_delConfirm, function() {
			dwrAction.doAjax('viewAction','delShortCuts',{menuId:menuId},function(res){
	    		if(res.success){
	    			Wg.alert(res.msg);
	    			window.location.href=ctx+"/view!initShortCuts.do";
	    		}
	    	});
		});
	}
</script>
<body>
	<table width="100%" cellspacing="0" cellpadding="0" class="ext_condition">
		<%for(int i=0; i<row; i++){ 
		%>
			<tr>
				<%for(int ii=0; ii<col; ii++) {
					if(i*col+ii>shortCutsLs.size()-1){
				%>
				<td width="5%" class="ext_tb_td"></td>
				<%		
					}else{
						Map<String,Object> rowMap = (Map<String,Object>)shortCutsLs.get(i*col+ii);
						String cdmc = String.valueOf(rowMap.get("CDMC"));
						String url = String.valueOf(rowMap.get("URL"));
						String cdid = String.valueOf(rowMap.get("CDID"));
				%>
				<td width="5%" class="ext_tb_title" align="middle"><a href="javascript:openTabMenu('<%=cdmc%>','<%=cdid%>','<%=url%>')"><%=cdmc %></a>
				<a href="javascript:delShortCuts('<%=cdid%>')"><img src="${ctx}/public/extjs/resources/icons/fam/cross.gif" align="middle" title='<s:text name="common.kjfs.del"/>'/></a>
				</td>
				<%}
			  } %>
			</tr>
		<%} %>
		</table>
</body>
</html>
