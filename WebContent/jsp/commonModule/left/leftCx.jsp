<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/meta.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
		<script type="text/javascript" src="${ctx}/public/extjs/ExtPagingTree.js"> </script>
		<script type="text/javascript" src="${ctx}/js/locale/commonModule/left/leftDw_${appLang}.js"> </script>
		<script type="text/javascript" src="${ctx}/js/modules/commonModule/left/leftDw.js"> </script>
	</head>
	<body class="fn-no-padding" >
	<s:hidden id="viewType" name="viewType"/>
	<input type="hidden" id="isAdv" value=""/>
	<div class="side-area"> 
		<div class="ui-side-tab">
			<ul>
				<li class="ui-side-tab-item" onclick="javascript:changeViews('bj')" title='<s:text name="left.tab.bj"/>'>
					<a class="icon-meter" href="###"></a>
				</li>
				<%-- <s:if test="supportTerminal=='true'">
				<li class="ui-side-tab-item" onclick="javascript:changeViews('sb')" title='<s:text name="left.tab.sb"/>'>
					<a class="icon-terminal" href="###"></a>
				</li>
				</s:if>
				<li class="ui-side-tab-item" onclick="javascript:changeViews('qz')" title='<s:text name="left.tab.qz"/>'>
					<a class="icon-group" href="###"></a>
				</li> --%>
				<li class="ui-side-tab-item ui-side-tab-item-current" onclick="javascript:changeViews('cx')"  title='<s:text name="left.tab.cx"/>'>
					<a class="icon-query" href="###"></a>
				</li>
			</ul>
		</div>
	  	<div class="side-content">
			<div class="side-area-form">
				<div class="side-form-item" style="display:none">
					<s:select id="type" name="type" list="#request.typeList"
						listKey="BM" listValue="MC" cssClass="ui-select"
						onchange="changeZslx(this.value)">
					</s:select>
				</div>
				
				<div class="side-form-item" id="zdjhtr" style="display:none">
					<h4><s:text name="left.cx.td.zdjh"></s:text></h4>
					<input type="text" class="ui-input" id="zdjh" onkeypress="if(event.keyCode==13){query();return false;}">
				</div>

				<div class="side-form-item" id="zdljdztr" style="display:none">
					<h4><s:text name="left.cx.td.ljdz"></s:text></h4>
					<input class="ui-input" type="text" id="zdljdz" >
				</div>

				<div class="side-form-item" id="bjjhtr" style="display:none">
					<label><h4><s:text name="left.cx.td.bjjh"></s:text></h4></label>
					<input class="ui-input" type="text" id="bjjh" onkeypress="if(event.keyCode==13){query();return false;}" >
				</div>

				<div class="side-form-item" id="hhtr" style="display:none">
					<label><h4><s:text name="left.cx.td.hh"></s:text></h4></label>
					<input class="ui-input" type="text" id="hh" onkeypress="if(event.keyCode==13){query();return false;}">
				</div>

				<div class="side-form-item" id="hmtr" style="display:none">
					<label><h4><s:text name="left.cx.td.hm"></s:text></h4></label>
					<input class="ui-input" type="text" id="hm" onkeypress="if(event.keyCode==13){query();return false;}">
				</div>
				
				<div class="side-form-item" id="bjhhtr" style="display:none">
					<h4><s:text name="left.cx.td.hh"></s:text></h4>
					<input class="ui-input" type="text" id="bjhh" >
				</div>
 				
				<div class="side-form-item" id="bjtxdztr" style="display:none">
					<h4><s:text name="left.cx.td.txdz"></s:text></h4>
					<input class="ui-input" type="text" id="bjtxdz">
				</div>

				<div class="side-form-item" id="tqtr" style="display:none">
					<h4><s:text name="left.cx.td.tqmc"></s:text></h4>
					<input class="ui-input" type="text" id="tqmc">
				</div>
				<div class="side-form-item" style="text-align:center">
					<button onclick="query();" class="ui-button ui-button-blue ui-button-small"><s:text name="left.tab.cx"></s:text> </button>
					<!-- button onclick="advQuery()" class="ui-button ui-button-blue ui-button-small"><s:text name="left.cx.td.gj"></s:text> </button>
					<button onclick="addGroup()" class="ui-button ui-button-blue ui-button-small"><s:text name="left.cx.td.jrqz"></s:text> </button -->
					<input type="hidden" id="sbid" value="">
				</div>
			</div>
			<div class="hr10"></div>
			<div class="side-area-tree" id="tree"></div>
		</div>
	</div>
	</body>
</html>