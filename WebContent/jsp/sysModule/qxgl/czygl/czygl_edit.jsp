<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/czygl_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/czygl_edit.js"></script>
</head>
<body>
<s:hidden id="dwdm" name="dwdm" />
<s:hidden id="bmid"	name="bmid" />
<table>
	<tr>
		<td valign="top" width="40%">
			<form id="czyForm" name="czyForm">
				<table class="fluid-grid">
					<tr>
						<td width="35%" align="right"><label><font color="red">*</font><s:text name="sysModule.qxgl.czygl.bm"/>:</label></td>
						<td width="60%" >
							<input id="bmmc" name="bmmc" disabled="disabled" class="ui-input"/>
						</td>
						<td width="5%"><a href="javascript:getBmTree('leaf');"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/></a></td>
					</tr>
					<tr>
						<td align="right"><label><font color="red">*</font><s:text name="sysModule.qxgl.czygl.czyid"/>:</label></td>
						<td><input type="text" id="czyid" name="czyid" class="ui-input" onblur="checkLength(this,20);"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><font color="red">*</font><s:text name="sysModule.qxgl.czygl.xm"/>:</label></td>
						<td><input type="text" id="xm" name="xm" onblur="checkLength(this,64);" class="ui-input"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><font color="red">*</font><s:text name="sysModule.qxgl.czygl.edit.mm"/>:</label></td>
						<td><input type="password" id="mm" name="mm" class="ui-input"onblur="checkLength(this,64);"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><font color="red">*</font><s:text name="sysModule.qxgl.czygl.edit.qrmm"/>:</label></td>
						<td><input type="password" id="qrmm" name="qrmm" class="ui-input" onblur="checkLength(this,64);"/></td>
						<td></td>
					</tr>
					<tr>
						<td width="35%" align="right"><label><s:text name="sysModule.qxgl.czygl.insteam"/>:</label></td>
						<td width="60%" >
							<s:textfield cssClass="ui-input" id="tname" name="tname" readonly="true" />
							<s:hidden id="tno" name="tno"/>  
						</td>
						<td width="5%"><a href="javascript:selectInsteam();"><img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png"/></a></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.edit.sjhm"/>:</label></td>
						<td><input type="text" id="sjhm" name="sjhm" onblur="checkLength(this,40);" class="ui-input"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.edit.sjtxfws"/>:</label></td>
						<td><s:select name="txfws" list="txfwsLs" listKey="BM" listValue="MC" cssClass="ui-input"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.edit.dhhm" />:</label></td>
						<td><input type="text" id="dhhm" name="dhhm" onblur="checkLength(this,40);" class="ui-input"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.edit.yxdz" />:</label></td>
						<td><input type="text" id="yxdz" name="yxdz" onblur="checkLength(this,64);" class="ui-input"/></td>
						<td></td>
					</tr>				
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.zt"/>:</label></td>
						<td><s:select name="zt" list="czyzt" listKey="BM" listValue="MC" cssClass="ui-select"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.bdip"/>:</label></td>
						<td><input type="text" id="ip" name="ip" onblur="checkLength(this,64);" class="ui-input"/></td>
						<td></td>
					</tr>
					<tr>
						<td align="right"><label><s:text name="sysModule.qxgl.czygl.dlsjqj"/>:</label></td>
						<td>
						<input style="width:45%" name="sysjq" id="sysjq" type="text" 
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',noPlugin:'1',isShowClear:true,readOnly:'true'})" class="Wdate"/>
							~
						<input style="width:45%" name="sysjz" id="sysjz" type="text" 
							onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm',noPlugin:'1',isShowClear:true,readOnly:'true',minDate:document.all.sysjq.value})" class="Wdate"/>	
						</td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="button" onclick="save('${operateType}')" class="ext_btn" value="<s:text name="sysModule.qxgl.czygl.edit.save"/>">
						</td>
						<td></td>
					</tr>
				</table>
			</form>
		</td>
		<td width="2%" ></td>
		<td valign="top" width="30%" style="padding-bottom:12px">
			<div class="ext-panel">
				<div id="cdtree" ></div>
			</div>
		</td>
		<td width="2%"></td>
		<td valign="top" width="30%"  style="padding-bottom:12px">
			<div class="ext-panel">
				<div id="qxtree" class="ext-panel"></div>
			</div>
		</td>
	</tr>
</table>

</body>

<script type="text/javascript">
	//操作类型
	var operateType = '${operateType}';
	//编辑
	if('edit' == operateType){
		//加载税费信息
		$('czyid').value = '${czy.czyid}';
		$('czyid').style.backgroundColor='#cccccc';
		$('czyid').readOnly = true;
				
		$('dwdm').value = '${czy.dwdm}';
		$('bmmc').value = '${czy.bmmc}';
		$('bmmc').style.backgroundColor='#cccccc';
		if(!$E($F('tname'))) {
			$('tname').style.backgroundColor='#cccccc';
		}
		$('bmid').value = '${czy.bmid}';
		$('xm').value = '${czy.xm}';
		if(!$E($('mm'))){
			$('mm').value = '${czy.mm}';
			$('qrmm').value = '${czy.mm}';
		}
		$('sjhm').value = '${czy.sjhm}';
		$('dhhm').value = '${czy.dhhm}';
		$('yxdz').value = '${czy.yxdz}';
		$('zt').value = '${czy.zt}';
		$('ip').value = '${czy.bdip}';
		$('sysjq').value = '${czy.sysjq}';
		$('sysjz').value = '${czy.sysjz}';
		$('txfws').value = '${czy.txfws}';
		
	}
</script>
</html>