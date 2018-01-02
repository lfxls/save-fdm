<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtGrid.css" />
	<script type="text/javascript" src="${ctx}/public/extjs/ExtGrid.js"></script>
	<script type="text/javascript" src="${ctx}/public/js/commForZzrw.js"></script>
	<script type="text/javascript" src="${ctx}/public/extjs/ExtProgress.js"></script>
	<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qzsz_${appLang}.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/commonModule/qz/qz_${appLang}.js"></script>
	<script type="text/javascript" src="${ctx}/js/modules/commonModule/qz/mdm_qzbj.js"></script>
</head>

<body class="body">
	<div class="ui-tab">
		<ul class="ui-tab-items">
			<li class="ui-tab-item ui-tab-item-current"><a href="#"><s:text
						name="common.qzsz.bj"></s:text> </a>
			</li>
			<li class="ui-tab-item" style="display: none"><a
				href="javascript:qzZd()"><s:text name="common.qzsz.zd"></s:text>
				</a>
			</li>
		</ul>
	</div>

	<div class="hr10"></div>
	
	<form id="queryForm" name="queryForm" class="ui-form">
		<div class="ui-panel">
			<div class="ui-panel-header">
				<s:text name="common.qztj.title"/>
			</div>
			<div class="ui-panel-body">
				<div class="ui-panel-item">
						<table class="fluid-grid">
							<s:hidden name="nodeId" id="nodeId" />
							<s:hidden name="nodeType" id="nodeType" />
							<s:hidden name="nodeDwdm" id="nodeDwdm" />
							<s:hidden name="yhlx" id="yhlx" />
							<s:hidden id="zxsj" name="zxsj" />
				
							<tr>
								<td align="right" width="12%">
									<label> <s:text name="advModule.fhkz.plkz.text.nodename" />:</label>
								</td>
								<td align="left" width="17%">
									<s:textfield name="nodeText" id="nodeText" cssClass="ui-input" 
										cssStyle="background-color:#cccccc;" readonly="true">
									</s:textfield>
								</td>
				
								<td align="right" width="12%">
						   			<label><s:text name="basicModule.dagl.yhda.ydsx"></s:text>:</label>
						   		</td>
								<td align="left" width="17%">
									<s:textfield cssClass="ui-input" id="ydsxmc" name="ydsxmc" 
									cssStyle="background-color:#cccccc;" readonly="readonly"/>
									<s:hidden id="ydsx" name="ydsx"/>
							    </td>
								<td align="left" width="2%">
									<a href="javascript:getTree('sx');"><img
									src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
								</td>
				
								<td align="right" width="12%">
									<label><s:text name="basicModule.csgl.flxz.flxz.gylx"></s:text>: </label>
								</td>
								<td align="left" width="17%">
									<s:select id="bjgylx" name="bjgylx"
										list="bjgylxLst" cssClass="ui-select" listKey="BM" listValue="MC"
										/>
								</td>
								
								<td width="11%"  align="center">

									<%-- 	
									<input type="button" class="ext_btn"
										onclick="rests()"
										value="<s:text name="basicModule.dagl.bjda.reset"></s:text>">
									 --%>	
								</td> 
							</tr>
							
							<tr style="display: none">
							    <td align="right">
									<label><s:text name="basicModule.dagl.mdm.newbjrk.bjlx"></s:text>: </label>
								</td>
								<td align="left">
									<s:select id="bjlx" name="bjlx" list="bjlxLst"
										cssClass="ui-select" listKey="BM" listValue="MC"
										/>
								</td>
							
								<td align="right">
									<label><s:text name="basicModule.dagl.mdm.bjrk.bjzt"></s:text>: </label>
								</td>
								<td align="left">
									<s:select id="bjzt" name="bjzt" list="bjztLst"
										cssClass="ui-select" listKey="BM" listValue="MC"
										 />
								</td>
								<td></td>
								
								<td align="right">
									<label><s:text name="basicModule.dagl.mdm.newbjrk.bjms"></s:text>:</label>
								</td>
								<td align="left">
									<s:select id="bjms" name="bjms" list="bjmsLs"
										cssClass="ui-select" listKey="BM" listValue="MC"
										 />
								</td>
							</tr>
							
							<tr>
								<td align="right" width="12%">
									<label><s:text name="basicModule.dagl.mdm.newbjrk.jxfs"></s:text>:</label>
								</td>
								<td align="left" width="17%">
									<s:select id="jxfs" name="jxfs" list="jxfsLst"
										cssClass="ui-select" listKey="BM" listValue="MC"
									/>
								</td>
				
								<td align="right" width="12%">
									<label><s:text name="common.mdmsy.cs.cs"></s:text>: </label>
								</td>
								<td align="left" width="17%">
									<s:select id="csbm" name="csbm" list="csLst"
										cssClass="ui-select" listKey="BM" listValue="MC"
									/>
								</td>
								<td width="2%"></td>
								
							    <td align="right" width="10%">
									<label><s:text name="basicModule.dagl.gybjgl.bjjh"></s:text>:</label>
								</td>
								<td align="left" width="17%">
									<s:textfield id="bjjh" name="bjjh" cssClass="ui-input" >
									</s:textfield>
								</td>
								
								
								<td width="11%" align="center">
									<input type="button" class="ext_btn"
										value="<s:text name="runModule.byqjk.syjk.cx" />"
										onclick="query()"> 
								</td>
							<%-- 
								<td align="right" width="10%">
						   			<label><s:text name="basicModule.dagl.yhda.ydsx"></s:text>:</label>
						   		</td>
								<td align="left" width="17%">
									<s:textfield cssClass="ui-input" id="ydsxmc" name="ydsxmc" 
									cssStyle="background-color:#cccccc;" readonly="readonly"/>
									<s:hidden id="ydsx" name="ydsx"/>
									<a href="javascript:getTree('sx');"><img
									src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
							    </td>
							
								<td align="right">
									<label><s:text name="basicModule.dagl.bjda.tariffGroupId"></s:text></label>
								</td>
								<td align="left">
									<s:select cssClass="ui-input" id="tariffGroup"
										name="tariffGroup" list="tariffGroupLs" listKey="BM"
										listValue="MC" cssStyle="width:125px" />
								</td>
						    --%>
							</tr>
							<%-- 
							<tr>
								<td align="right">
									<label><s:text name="basicModule.dagl.bjda.voltageGroup"></s:text></label>
								</td>
								<td align="left">
									<s:select cssClass="ui-input" id="voltageGroup"
										name="voltageGroup" list="voltageGroupLs" listKey="BM"
										listValue="MC" cssStyle="width:125px" />
								</td>
							</tr>
							 --%>
				
						   <tr>
							    <td align="right" width="12%">
									<label><s:text name="basicModule.dagl.yhgl.hh"></s:text>:</label>
								</td>
								<td align="left" width="17%">
									<s:textfield id="yhh" name="yhh" cssClass="ui-input" >
									</s:textfield>
								</td>
								
							    <td align="right" width="12%">
									<label><s:text name="basicModule.dagl.yhgl.hm"></s:text>: </label>
								</td>
								<td align="left" width="17%">
									<s:textfield id="yhm" name="yhm" cssClass="ui-input" >
									</s:textfield>
								</td>
								<td width="2%"></td>
								
								<td width="12%">
								</td>
						   		<td width="17%">
								</td>
								
						   		<td width="11%" align="center">
						   			<input type="reset" value="<s:text name='advanceModule.lhzbb.lhzcx.cz'/>" class="ext_btn" />
								</td>
						   </tr>
						
						   <tr style="display: none">
								<td align="right" style="display: none">
									<label><s:text name="basicModule.dagl.yhda.gddy"></s:text>:</label>
								</td>
								<td align="left" style="display: none">
									<s:select id="gddy" name="gddy" list="gddyLst" cssClass="ui-select" listKey="BM" listValue="MC"/>
								</td>
								
							    <td align="right" style="display: none">
							    	<label><s:text name="basicModule.dagl.yhda.hy"></s:text>:</label>
							    </td>
								<td align="left" style="display: none">
									<s:textfield cssClass="ui-input" id="hymc" name="hymc" readonly="readonly"/>
									<s:hidden id="hyid" name="hyid"/>
									<a href="javascript:getTree('hy');"><img
									src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
								</td>
								
								<td >
								</td>
						   </tr>
					</table>
				</div>
				
				<div class="ui-panel-item">
					<div id="grid"></div>
				</div>
			</div>
		</div>
	</form>
</body>


<script type="text/javascript">

</script>
</html>
