<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
	List<Object> sjxLs = (List<Object>)request.getAttribute("sjxLs");
	List<Object> fieldLs = (List<Object>)request.getAttribute("fieldLs");
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/ggdmgl/rwmbpz/rwsjxzd_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/ggdmgl/rwmbpz/rwsjxzd.js"> </script>
</head>
<body class="body">
<s:hidden id="rwsx" name="rwsx"/>
<s:hidden id="zdgylx" name="zdgylx"/>
<div>
	<label><s:text name="sysModule.ggdmgl.rwmbpz.rwsjxzd.td.sjb"></s:text></label>
	<s:select id="sjb" name="sjb" list="tableLs" listKey="BM" listValue="MC" cssClass="ui-select" cssStyle="width:180px;" onchange="query();"></s:select>
</div>
<div class="hr10"></div>
<table class="ui-table">
	<thead>
	<tr>
		<th><s:text name="sysModule.ggdmgl.rwmbpz.rwsjxzd.td.sjx"></s:text></th>
		<th><s:text name="sysModule.ggdmgl.rwmbpz.rwsjxzd.td.zd"></s:text></th>
		<th><s:text name="sysModule.ggdmgl.rwmbpz.rwsjxzd.td.xh"></s:text></th>
	</tr>
	</thead>
	<%
		for(int i=0; i<sjxLs.size(); i++){
			Map<String,Object> rowMap = (Map<String,Object>)sjxLs.get(i);
			String sjxsx = String.valueOf(rowMap.get("SJXSX"));
			String struct = String.valueOf(rowMap.get("ARRAY_STRUCT_ITEM")==null?"":rowMap.get("ARRAY_STRUCT_ITEM"));	
			struct = struct==null?"":struct;
			String sjxbm = String.valueOf(rowMap.get("SJXBM"));
			String sjxmc = String.valueOf(rowMap.get("SJXMC")==null?"":rowMap.get("SJXMC"));
			String xh = String.valueOf(rowMap.get("XH")==null?"":rowMap.get("XH"));
			//打包数据项
			String dbsjxbm = rowMap.get("DBSJXBM")==null?"null":(String)rowMap.get("DBSJXBM");
			String dyzd = rowMap.get("DYZD")==null?"":(String)rowMap.get("DYZD");
			
			String sjxmcFull = !struct.equals("")?"<b>"+sjxmc+"-Package("+sjxbm+")</b>":sjxmc+"("+sjxbm+")";
	%>
	<tbody>
	<tr>
		<td align="left" width="60%"><%=sjxmcFull %></td>
		<td align="center">
		<input type="hidden" id="sjxsx" name="sjxsx" value="<%=sjxsx%>"/>
		<input type="hidden" id="sjxbm" name="sjxbm" value="<%=sjxbm%>"/>
		<input type="hidden" id="dbsjxbm" name="dbsjxbm" value="<%=dbsjxbm%>"/>
		
		<!-- 非机构体显示字段对应关系 -->
		<%if("".equals(struct)){ %>
			<select id="zd" name="zd" class="ui-select">
				<% for(int ii=0; ii<fieldLs.size(); ii++) {
					Map<String,Object> tempRow = (Map<String,Object>)fieldLs.get(ii);
					String bm = String.valueOf(tempRow.get("BM"));
					String mc = String.valueOf(tempRow.get("MC"));
					String selectValue = "";
					if(bm.equals(dyzd)){
						selectValue = "selected";
					}
				%>
				<option value="<%=bm%>" <%=selectValue %>><%=mc %></option>
				<%} %>
			</select>
		<%}else {%>
			<s:hidden id="zd" name="zd"/>
		<%} %>
		</td>
		<td align="center" width="25%">
		<%if(!"".equals(struct) || "01".equals(sjxsx)){ %>
			<input type="text" name="xh" id="xh" value="<%=xh%>" class="ui-input" style="width:50px;"/>
		<%}else{ %>
			<s:hidden id="xh" name="xh"/>
		<%} %>
		</td>
	</tr>
	</tbody>
	<%} %>
	<tr width="15%">
		<td colspan="3" align="center"><input type="button" class="ext_btn" onclick="save()" value='<s:text name="sysModule.ggdmgl.rwmbpz.rwsjxzd.btn.bc"/>'/></td>
	</tr>
</table>

</html>