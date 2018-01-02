<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />
<script type="text/javascript" src="${ctx}/public/extjs/ExtTree.js"></script>
<script type="text/javascript" src="${ctx}/js/locale/sysModule/qxgl/czygl/pwd_${appLang}.js"></script>
<script type="text/javascript" src="${ctx}/js/modules/sysModule/qxgl/czygl/pwd.js"></script>
<script type="text/javascript" src="${ctx}/public/js/passwordstrength.js"> </script>
</head>
<body class="body">
<input type="hidden" id="czyid" name="czyid" value="${czy.czyid}"/>
<table width="100%" align="center" cellspacing="5" cellpadding="0" class="fluid-grid">
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" width="45%"><s:text name="sysModule.qxgl.czygl.xm"/><font color="red">*</font></td>
		<td width="55%"><input type="text" id="xm" name="xm" value="${czy.xm}" onblur="checkLength(this,64);" class="ui-input" style="width:200px"/></td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.oldPwd"></s:text><font color="red">*</font></td>
		<td><input type="password" id="oldPwd" name="oldPwd" value="${czy.mm}" class="ui-input" style="width:200px"/></td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.newPwd"></s:text><font color="red">*</font></td>
		<td><input type="password" id="newPwd" name="newPwd" value="${czy.mm}" onKeyUp="ps.update(this.value);" class="ui-input" style="width:200px"/></td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.newPwdConfirm"></s:text><font color="red">*</font></td>
		<td><input type="password" id="rePwd" name="rePwd" value="${czy.mm}" class="ui-input" style="width:200px"/>
		<div style="margin-bottom: 5px; width: 200px;">
				<script language="javascript">
					var ps = new PasswordStrength();
					ps.setSize("200","20");
					ps.setMinLength(1);
				</script>
			</div>
		</td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.edit.sjhm"/></td>
		<td><input type="text" id="sjhm" name="sjhm" value="${czy.sjhm}" onblur="checkLength(this,40);" class="ui-input" style="width:200px"/></td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.edit.dhhm" /></td>
		<td><input type="text" id="dhhm" name="dhhm" value="${czy.dhhm}" onblur="checkLength(this,40);" class="ui-input" style="width:200px"/></td>
	</tr>
	<tr>
		<td align="right"><s:text name="sysModule.qxgl.czygl.edit.yxdz" /></td>
		<td><input type="text" id="yxdz" name="yxdz" value="${czy.yxdz}" onblur="checkLength(this,64);" class="ui-input" style="width:200px"/></td>
	</tr>	
	<tr>
		<td colspan="2" align="center"><div style="margin-bottom: 5px; width: 200px;" align="center">
				<input type="button" onclick="save()" class="ext_btn" value='<s:text name="sysModule.qxgl.sygl.edit.save"></s:text>'/>
			</div></td>
	</tr>
		
</table>	 
</html>