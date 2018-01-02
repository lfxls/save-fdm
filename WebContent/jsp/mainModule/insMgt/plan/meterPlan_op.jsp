<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/meterPlan_op.js"></script>
</head>
<body class="body">
	<form id="mPlanOPForm" name="mPlanOPForm" class="ui-form">
		<s:hidden id="operateType" name="operateType"></s:hidden>
		<s:hidden id="bussType" name="bussType"></s:hidden>
		<s:hidden id="pid" name="pid"></s:hidden>
		<s:hidden id="uid" name="uid"/>
		<s:hidden id="uname" name="uname"/>
		<s:hidden id="mno" name="mno"/>
		<s:hidden id="helpDocAreaId" name="helpDocAreaId"/>
		<table class="fluid-grid">
			<!-- 是更换和拆回则显示老表计局号  -->
			<s:if test="bussType == 1 || bussType == 2">
				<tr>
					<td width="30%" align="right">
						<label>
							<s:text name="mainModule.insMgt.plan.oldMeterNo" /><font color="red">*</font>
						</label>
					</td>
					<td>
						<s:textfield id="omsn" name="omsn"
							cssStyle="width:300px;background-color:#cccccc;" readonly="true"
							cssClass="ui-input"></s:textfield>&nbsp;
							<!-- 选择可更换的表计   -->	
							<a href="javascript:queryMeter('old');"
							   title=<s:text name="mainModule.insMgt.plan.tip.selMeter"></s:text>>
							   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
					</td>
				</tr>				
			</s:if>
		
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.customer.CSN" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:textfield id="cno" name="cno"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp; 
					<!-- 为新装业务类型可选择 -->	
					<s:if test="bussType == 0 ">
						<a href="javascript:queryCust();"
						   title=<s:text name="mainModule.insMgt.plan.tip.selCust"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
					</s:if>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.transformer.TFName" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:hidden id="tfId" name="tfId"/>
					<s:textfield id="tfName" name="tfName"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>&nbsp; 
					<!-- 为新装业务类型可选择 -->		
					<s:if test="bussType == 0">
						<a href="javascript:queryTf();"
						   title=<s:text name="mainModule.insMgt.plan.tip.selTf"></s:text>>
						   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
				    </s:if>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label><s:text name="common.kw.customer.CName" /></label>
				</td>
				<td>
					<s:textfield id="cname" name="cname"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label><s:text name="common.kw.customer.CA" /></label>
				</td>
				<td>
					<s:textfield id="addr" name="addr"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.meter.MType" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="mType" name="mType" list="mTypeList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>
				
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="common.kw.meter.MCMethod" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="wir" name="wir" list="wirList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>
				
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.meterMode" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="mMode" name="mMode" list="mModeList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>	
			
			<s:if test="bussType == 0 || bussType == 1">
				<tr>
					<td width="30%" align="right">
						<label><s:text name="mainModule.insMgt.plan.newMeterNo"/></label>
					</td>
					<td>
						<s:textfield id="nmsn" name="nmsn"
							cssStyle="width:300px;background-color:#cccccc;" readonly="true"
							cssClass="ui-input"></s:textfield>&nbsp;
							<a href="javascript:queryMeter('new');"
							   title=<s:text name="mainModule.insMgt.plan.tip.selMeter"></s:text>>
							   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>
					</td>
				</tr>
			</s:if>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="save();"
					value="<s:text name='mainModule.insMgt.plan.btn.save'/>"
					class="ext_btn" />
				
					<!-- 为新装业务类型可选择 -->		
					<s:if test="bussType == 0">
						<input type="button" onclick="reset();"
						value="<s:text name="mainModule.insMgt.plan.btn.reset"/>" 
						class="ext_btn"/>
					</s:if>
				</td>
			</tr>
		</table>
	</form>
</body>

<script type="text/javascript">
    var bussType = '${bussType}';
	var operateType = '${operateType}';
	//编辑
	if('02' == operateType) {
		if('${singleMap}') {
			$('bussType').value = '${singleMap.BUSSTYPE}';
			$('cno').value = '${singleMap.CNO}';
			$('cname').value = '${singleMap.CNAME}';
			$('tfId').value = '${singleMap.TFID}';
			$('tfName').value = '${singleMap.TFNAME}';
			$('addr').value = '${singleMap.ADDR}';
			$('mType').value = '${singleMap.MTYPE}';
			$('wir').value = '${singleMap.WIR}';
			$('mMode').value = '${singleMap.MMODE}';
			$('pid').value = '${singleMap.PID}';
			if('0' == bussType) {//新装
				$('nmsn').value = '${singleMap.NMSN}';
			} else if('1' == bussType ) {//更换
				$('omsn').value = '${singleMap.OMSN}';
				$('nmsn').value = '${singleMap.NMSN}';
			} else if('2' == bussType ) {//拆回
				$('omsn').value = '${singleMap.OMSN}';
				$('mType').disabled = true;
			 	$('wir').disabled = true;
			 	$('mMode').disabled = true; 
				$('mType').style.backgroundColor='#cccccc';
				$('wir').style.backgroundColor='#cccccc';
				$('mMode').style.backgroundColor='#cccccc';
			}
		}
	} else {
		if('2' == bussType ) {//拆回
			$('mType').disabled = true;
		 	$('wir').disabled = true;
		 	$('mMode').disabled = true; 
			$('mType').style.backgroundColor='#cccccc';
			$('wir').style.backgroundColor='#cccccc';
			$('mMode').style.backgroundColor='#cccccc';
		}
	}
</script>
</html>