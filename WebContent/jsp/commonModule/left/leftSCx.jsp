<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/meta.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
		<script type="text/javascript" src="${ctx}/public/extjs/ExtPagingTree.js"> </script>
		<script type="text/javascript" src="${ctx}/js/locale/commonModule/left/leftDw_${appLang}.js"> </script>
		<script type="text/javascript" src="${ctx}/js/modules/commonModule/left/leftS.js"> </script>
	</head>
	<body class="fn-no-padding" >
	<s:hidden id="viewType" name="viewType"/>
	<input type="hidden" id="isAdv" value=""/>
	<div class="leftWarp">
		<div class="leftTab">
			<ul>
				<s:if test="supportTerminal=='true'">
					<li class="leftMenuOff" onclick="javascript:changeViews('sb')" title='<s:text name="left.tab.sb"/>'>
						<s:text name="left.tab.sb"/>
					</li>
				</s:if>
				<li class="leftMenuOff" onclick="javascript:changeViews('bj')" title='<s:text name="left.tab.bj"/>'>
					<s:text name="left.tab.bj"/>
				</li>
				<li class="leftMenuOn"  title='<s:text name="left.tab.cx"/>'>
					<s:text name="left.tab.cx"/>
				</li>
			</ul>
		</div>
	  	<div class="mainAread">
		<table width="100%" height="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top">
					<table width="290" align="center" cellspacing="0" cellpadding="0" class="ext_condition">
						<tr>
						<td>
							<table width="100%" align="center" cellspacing="1" cellpadding="0">
								<tr>
									<td align="right" width="40%"><s:text name="left.td.zslx"/>&nbsp;</td>
									<td align="left" width="60%"><s:select id="type" name="type" list="#request.typeList" listKey="BM" listValue="MC" onchange="changeZslx(this.value)"></s:select></td>
								</tr>
								<tr id="zdjhtr">
									<td align="right"><s:text name="left.cx.td.zdjh"></s:text> &nbsp;</td>
									<td><input type="text" id="zdjh"  onkeypress="if(event.keyCode==13){query();return false;}"></td>
								</tr>
								<tr id="zdljdztr" style="display:none">
									<td align="right"><s:text name="left.cx.td.ljdz"></s:text> &nbsp;</td><td><input type="text" id="zdljdz"></td>
								</tr>
								<tr id="bjjhtr" style="display:none">
									<td align="right"><s:text name="left.cx.td.bjjh"></s:text> &nbsp;</td>
									<td><input type="text" id="bjjh" onkeypress="if(event.keyCode==13){query();return false;}"></td>
								</tr>
								<tr id="hhtr" style="display:none">
									<td align="right"><s:text name="left.cx.td.hh"></s:text> &nbsp;</td>
									<td><input type="text" id="hh" onkeypress="if(event.keyCode==13){query();return false;}"></td>
								</tr>
								<tr id="hmtr" style="display:none">
									<td align="right"><s:text name="left.cx.td.hm"></s:text>&nbsp;</td>
									<td><input type="text" id="hm" onkeypress="if(event.keyCode==13){query();return false;}"></td>
								</tr>
								<tr id="bjhhtr" style="display:none">
									<td align="right"><s:text name="left.cx.td.hh"></s:text>&nbsp;</td><td><input type="text" id="bjhh"></td>
								</tr>
								<tr id="bjtxdztr" style="display:none">
									<td align="right"><s:text name="left.cx.td.txdz"></s:text>&nbsp;</td><td><input type="text" id="bjtxdz"></td>
								</tr>
								<tr id="tqtr" style="display:none">
									<td align="right"><s:text name="left.cx.td.tqmc"></s:text>&nbsp;</td><td><input type="text" id="tqmc"></td>
								</tr>
								<tr>
									<td colspan="2" align="center">
									<button onclick="query();" class = "ext_btn"><s:text name="left.tab.cx"></s:text> </button>
									<input type="hidden" id="sbid" value="">
									</td>
								</tr>
							</table>
						</td>
						</tr>
					</table>
					<div id="tree"></div>
				</td>
			</tr>
		</table>
		</div>
	</div>
	</body>
</html>