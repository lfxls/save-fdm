<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript">
	//加载国际化资源文件
	loadProperties('mainModule', 'mainModule_insMgt');
</script>
<script type="text/javascript" src="${ctx}/js/modules/mainModule/insMgt/plan/dcuPlan_op.js"></script>
</head>
<body class="body">
	<form id="dcuPlanOPForm" name="dcuPlanOPForm" class="ui-form">
		<s:hidden id="operateType" name="operateType"></s:hidden>
		<s:hidden id="bussType" name="bussType"></s:hidden>
		<s:hidden id="dwdm" name="dwdm"></s:hidden>
		<s:hidden id="dwmc" name="dwmc"></s:hidden>
		<s:hidden id="pid" name="pid"></s:hidden>
		<s:hidden id="uname" name="uname"/>
		<table class="fluid-grid">
		   	<!-- 是更换则显示老集中器号  -->
			<s:if test="bussType == 1 || bussType == 2">
				<tr>
					<td width="30%" align="right">
						<label>
							<s:text name="mainModule.insMgt.plan.oldDcuNo" /><font color="red">*</font>
						</label>
					</td>
					<td>
						<s:textfield id="odsn" name="odsn"
							cssStyle="width:300px;background-color:#cccccc;" readonly="true"
							cssClass="ui-input"></s:textfield>&nbsp;
							<!-- 选择可更换的集中器   -->	
							<a href="javascript:queryDcu();"
							   title=<s:text name="mainModule.insMgt.plan.tip.selDcu"></s:text>>
							   <img src="${ctx}/public/extjs/resources/icons/fam/cog_edit.png" /></a>	
					</td>
				</tr>				
			</s:if>
		
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
					<label><s:text name="mainModule.insMgt.plan.devAddress" /></label>
				</td>
				<td>
					<s:textfield id="devAddr" name="devAddr"
						cssStyle="width:300px;background-color:#cccccc;" readonly="true"
						cssClass="ui-input"></s:textfield>
				</td>
			</tr>
			
			<tr>
				<td width="30%" align="right">
					<label>
						<s:text name="mainModule.insMgt.plan.dcuModel" /><font color="red">*</font>
					</label>
				</td>
				<td>
					<s:select id="dcuM" name="dcuM" list="dcuMList" listKey="BM"
						listValue="MC" cssStyle="width:300px;" cssClass="ui-select" />
				</td>
			</tr>	
			
			<s:if test="bussType == 0 || bussType == 1">
				<tr>
					<td width="30%" align="right">
						<label>
							<s:text name="mainModule.insMgt.plan.newDcuNo" />
						</label>
					</td>
					<td>
						<s:textfield id="ndsn" name="ndsn" cssStyle="width:300px;"
							cssClass="ui-input"></s:textfield>&nbsp;
					</td>
				</tr>	
			</s:if>
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="save();"
					value="<s:text name='mainModule.insMgt.plan.btn.save'/>"
					class="ext_btn" />
				
					<!-- 为新装业务类型可选择 -->		
					<s:if test="bussType == '0'">
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
			$('tfId').value = '${singleMap.TFID}'; 
			$('tfName').value = '${singleMap.TFNAME}'; 
			$('devAddr').value = '${singleMap.ADDR}'; 
			$('dcuM').value = '${singleMap.DCUM}'; 
			if('0' == bussType) {//新装
				$('ndsn').value = '${singleMap.NDSN}'; 
			} else if('1' == bussType) {//更换
				$('ndsn').value = '${singleMap.NDSN}'; 
				$('odsn').value = '${singleMap.ODSN}'; 
			} else if('2' == bussType) {//拆回
				$('odsn').value = '${singleMap.ODSN}';
				$('dcuM').disabled = true;
				$('dcuM').style.backgroundColor='#cccccc';
			}
		}
	}
</script>
</html>