loadProperties('sysModule', 'sysModule_paramData');
var menuId = '54200';
function Save(){
	var czid=$F('czid');
	if(valid()){
		if(czid =='02'){
			editCate();
		}
		else{
			addCate();
		}
	}
	
}
function addCate(){
	var p=$FF('queryForm');
	Ext.apply(p,{logger:system.paramData.paramCateMgt.add.logger + $F('cateName')});//apply属性拷贝，会覆盖
	Wg.confirm(system.paramData.paramCateMgt.add.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId +'04', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}
function editCate(){
	var p=$FF('queryForm');
	Ext.apply(p,{logger:system.paramData.paramCateMgt.edit.logger + $F('cateName')});//apply属性拷贝，会覆盖
	Wg.confirm(system.paramData.paramCateMgt.edit.confirm,function(){
		dwrAction.doDbWorks('paramMgtAction',menuId +'05', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
			});
		});
	});
}

function valid(){
	
	if($E($F('cateName'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.cateName,function(btn,text){
			if(btn=='ok'){
				$('cateName').focus();
			}
		});
		return false;
	}
	if($E($F('paramType'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.paramType,function(btn,text){
			if(btn=='ok'){
				$('paramType').focus();
			}
		});
		return false;	
	}
	if($E($F('verId'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.verId,function(btn,text){
			if(btn=='ok'){
				$('verId').focus();
			}
		});
		return false;	
	}
	if($E($F('status'))){
		Ext.Msg.alert(system.paramData.paramMgt.title,system.paramData.paramMgt.valid.status,function(btn,text){
			if(btn=='ok'){
				$('status').focus();
			}
		});
		return false;	
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