loadProperties('sysModule', 'sysModule_paramData');
var addOpt='01';
var updOpt='02';
var menuId = '54200';
Ext.onReady(function() {
	$('itemName').style.backgroundColor='#CCCCCC';
	$('testName').style.backgroundColor='#CCCCCC';
	if($F('czid')=="02"){
		$('params').style.display="none";
		$('testparams').style.display="none";
	}
	if($F('paramType')==0){
		$('param1').style.display="";
		$('param2').style.display="";
		$('test1').style.display="none";
		$('test2').style.display="none";
		$('valuetd1').style.display="none";
		$('valuetd2').style.display="none";
		$('leveltd1').style.display="none";
		$('leveltd2').style.display="none";
		$('sValuetd').style.display="none";
	}
	else if($F('paramType')=="1"){
		$('param1').style.display="";
		$('param2').style.display="";
		$('test1').style.display="none";
		$('test2').style.display="none";
		$('leveltd1').style.display="none";
		$('leveltd2').style.display="none";
		changeBussType();
	}
	else if($F('paramType')=="2"){
		$('test1').style.display="";
		$('test2').style.display="";
		$('param1').style.display="none";
		$('param2').style.display="none";
		$('valuetd1').style.display="none";
		$('valuetd2').style.display="none";
		$('sValuetd').style.display="none";
		$('leveltd1').style.display="";
		$('leveltd2').style.display="";
	}

});

function Save(){
	var czid=$F('czid');
	if(valid()){
		if(czid =='02'){
			editParam();
		}
		else{
			addParam();
		}
	}
	
}
function changeBussType(){
	var bussType=$F('bussType');
	if(bussType=='1'){
		$('valuetd1').style.display="none";
		$('valuetd2').style.display="none";
		$('sValuetd').style.display="none";
	}
	else if(bussType=="2"){
		$('valuetd2').style.display="none";
		$('valuetd1').style.display="";
		$('sValuetd').style.display="";
	}
	else {
		$('sValuetd').style.display="none";
		$('valuetd1').style.display="";
		$('valuetd2').style.display="";
	}
	
}

function addParam(){
	var bussType=$F('bussType');
	var p=$FF('queryForm');
	if(bussType=='2'){
		p.value=$F('svalue');
	}
	Ext.apply(p,{logger:system.paramData.paramMgt.add.logger + $F('itemName')});//apply属性拷贝，会覆盖
	Wg.confirm(system.paramData.paramMgt.add.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId +addOpt, p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}
function editParam(){
	var bussType=$F('bussType');
	var p=$FF('queryForm');
	if(bussType=='2'){
		p.value=$F('svalue');
	}
	Ext.apply(p,{logger:system.paramData.paramMgt.edit.logger + $F('itemName')});//apply属性拷贝，会覆盖
	Wg.confirm(system.paramData.paramMgt.edit.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId +updOpt, p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}
function getParamList(){
	var paramType=$F('paramType');
	var cateId=$F('cateId');
	var verId=$F('verId');
	var url = String.format(ctx + '/system/paramData/paramMgt!initParamList.do?cateId={0}&paramType={1}&verId={2}',cateId,paramType,verId);
	
	var baseParam = {
			url : url,
			title : system.paramData.paramMgt.param.wh,
			el : 'add',
			width : 700,
			height : 400,
			draggable : true
		};
	EditWin = new Wg.window(baseParam);
	EditWin.open();
}
function valid(){
	
	
	if($E($F('status'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.status,function(btn,text){
			if(btn=='ok'){
				$('status').focus();
			}
		});
		return false;	
	}
	if($F("paramType")==1){
		if($F('bussType')=="0"||$F('bussType')==""){
			if($E($F('value'))){
				Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.value,function(btn,text){
					if(btn=='ok'){
						$('value').focus();
					}
				});
				return false;	
			}
		}
	}
	if($F("paramType")==2){
	
		if($E($F('testName'))){
			Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.testName,function(btn,text){
				if(btn=='ok'){
					$('testName').focus();
				}
			});
			return false;
		}
	if($E($F('level'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.level,function(btn,text){
			if(btn=='ok'){
				$('level').focus();
			}
		});
		return false;	
	}
	}
	else{
		if($E($F('itemName'))){
			Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.itemName,function(btn,text){
				if(btn=='ok'){
					$('itemName').focus();
				}
			});
			return false;
		}
	}
	var str=/^[0-9]*$/; 
	if($E($F('sortNum'))){
		
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.sort,function(btn,text){
			if(btn=='ok'){
				$('sortNum').focus();
			}
		});
		return false;	
	}
	else if(!$F('sortNum').match(str)){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.validformat.sort,function(btn,text){
			if(btn=='ok'){
				$('sortNum').focus();
			}
		});
		return false;		
	}
	return true;
}
