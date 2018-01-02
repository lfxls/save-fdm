<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="cache-control" content="no-cache">
<%@ include file="/jsp/meta.jsp" %>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/left/advQuery_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/left/advQuery.js"> </script>
</head>
<body>
<FIELDSET class="ext_fieldset"><legend class="ext_legend"><s:text name="left.gjcx.td.qy"></s:text> </legend>
<table width="99%" align="center" cellspacing="5" cellpadding="0">
	<tr>
		<td align="right" width="10%"><s:text name="left.gjcx.td.dw"></s:text>&nbsp;</td>
		<td>
			<input id="type" name="type" type="hidden" value="<s:property value="#request.type"/>">
			<input id="yhlx" name="yhlx" type="hidden" value="<s:property value="#request.yhlx"/>">
			<select id="sjdwdm" style="width:150px;" onchange="changeDw(this.value);"></select>
		</td>
		<td align="right"><s:text name="left.gjcx.td.gds"></s:text>&nbsp;</td>
		<td>
		<select id="dwdm" name="dwdm" style="width:150px;">
		</select>
		</td>
		<s:if test="#request.yhlx == '01'">
			<td align="right"><s:text name="left.gjcx.td.xl"></s:text>&nbsp;</td>
			<td>
			<input id="xlmc" name="xlmc" type="text" value="" style="width:150px;"/>
			</td>
		</s:if>
		<s:if test="#request.yhlx != '01'">
			<td align="right"><s:text name="left.gjcx.td.tq"></s:text>&nbsp;</td>
			<td>
				<input id="tqmc" name="tqmc" type="text" value="" style="width:150px;"/>
			</td>
		</s:if>
	</tr>
</table>
</FIELDSET>
<FIELDSET class="ext_fieldset"><legend class="ext_legend"><s:text name="left.gjcx.td.yh"></s:text> </legend>
<table width="99%" align="center" cellspacing="5" cellpadding="0">
	<tr>
		<td align="right" width="15%">
		<s:text name="left.cx.td.hh"></s:text>
		</td>
		<td><input type="text" id="hh" style="width: 280px"><s:text name="left.gjcx.td.hh.tip"></s:text> </td>
	</tr>
	<tr>
		<td align="right"><s:text name="left.cx.td.zdjh"></s:text>&nbsp;</td><td><input type="text" id="zdjh" style="width: 280px"><s:text name="left.gjcx.td.hh.tip"></s:text> </td>
	</tr>
	<tr>
		<td align="right"></td><td><input type="text" id="zdjhFrom">~<input type="text" id="zdjhTo"></td>
	</tr>
	<tr>
		<td align="right"><s:text name="left.cx.td.bjjh"></s:text> &nbsp;</td><td><input type="text" id="bjjh" style="width: 280px"><s:text name="left.gjcx.td.hh.tip"></s:text> </td>
	</tr>
	<tr>
		<td align="right"><s:text name="left.gjcx.td.sim"></s:text> &nbsp;</td><td><input type="text" id="simkh" style="width: 280px"><s:text name="left.gjcx.td.hh.tip"></s:text> </td>
	</tr>
</table>
</FIELDSET>
<FIELDSET class="ext_fieldset"><legend class="ext_legend"><s:text name="left.gjcx.td.zd"></s:text> </legend>
<table  width="99%" align="center" cellspacing="5" cellpadding="5">
	<tr>
		<td align="right" width="18%"><s:text name="left.gjcx.td.gylx"></s:text> &nbsp;</td><td>
			<s:select list="#request.zdgyls" listKey="BM" listValue="MC" name="zdgylx" id="zdgylx" cssStyle="width:150px;"/>
		</td>
	</tr>
	<tr>
		<td align="right"><s:text name="left.gjcx.td.zdzt"></s:text> &nbsp;</td><td>
			<s:checkboxlist list="#request.zdztls" listKey="BM" listValue="MC" name ="zdzt" id="zdzt"/>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top"><s:text name="left.gjcx.td.zdzt"></s:text> &nbsp;</td><td>
			<div style="width: 100%;height: 50px; border:solid 1px;border-color:gray; overflow: auto;">
				<s:checkboxlist list="#request.dydjls" listKey="BM" listValue="MC" name ="dydj" id="dydj"/>
			</div>
		</td>
	</tr>
	<tr>
		<td align="right"><s:text name="left.gjcx.td.sdrl"></s:text> &nbsp;</td><td>
			<s:radio name="rl" onclick="changeRl(this.value);" list="#{'0':'<s:text name=\"left.gjcx.td.dj\"/>','1':'<s:text name=\"left.gjcx.td.zdy\"/>'}" value="0"/>&nbsp;
			<s:select list="#request.sdrlls" listKey="BM" listValue="MC" name ="sdrl" id="sdrl" cssStyle="width:150px;"/>
			<div id="zdyrl" style="display: none; margin:0;padding:0;">
				<s:text name="left.gjcx.td.dy"></s:text> <input type="text" id="minrl" style="width: 50px">kVA&nbsp;<s:text name="left.gjcx.td.xy"></s:text> <input type="text" id="maxrl" style="width: 50px">kVA
			</div>
		</td>
	</tr>
</table>
</FIELDSET>
<div align="center"><button onclick="query();" class="ext_btn"><s:text name="left.tab.cx"></s:text> </button></div>
</body>
</html>