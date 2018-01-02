loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11700';
Ext.onReady(function() {
	hideLeft();
	if($F('czid')=='02'){
		$('mfid').disabled=true;
		$('mfid').style.backgroundColor='#CCCCCC';
		$('model').disabled=true;
		$('model').style.backgroundColor='#CCCCCC';
		$('verid').readOnly=true;
		$('verid').style.backgroundColor='#CCCCCC';
	}
	if($F('mfid')!='14'){
		$('veridTd').style.display="none";
		$('veridTd2').style.display="none";
	}
	else{
		$('veridTd').style.display="";
		$('veridTd2').style.display="";
	}
});

function addModel(){
	var p=$FF('queryForm');
	var model=$F('model');
	Ext.apply(p,{logger:main.arcMgt.modelMgt.add.logger + $F('vername')});//apply属性拷贝，会覆盖
	Wg.confirm(main.arcMgt.modelMgt.add.confirm,function(){
		dwrAction.doDbWorks('modelMgtAction',menuId +'01', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					parent.query();
					parent.win.close();
				}
				else{
				}
			});
		});
	});
}
function editModel(){
		var p=$FF('queryForm');
		Ext.apply(p,{logger:main.arcMgt.modelMgt.edit.logger + $F('vername')});//apply属性拷贝，会覆盖
		Wg.confirm(main.arcMgt.modelMgt.edit.confirm,function(){
			dwrAction.doDbWorks('modelMgtAction',menuId + $F('czid'), p, function(res){
				Wg.alert(res.msg,function(){
					if(res.success) {
						parent.query();
						parent.win.close();
					}
					else{
					}
				});
			});
		});
}

function Save(){
	var czid=$F('czid');
	if(valid()){
		if(czid =='02'){
			editModel();
		}
		else{
			addModel();
		}
	}
	
}

function valid(){
	//版本名称
	if($E($F('vername'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.modelMgt.valid.vername,function(btn,text){
			if(btn=='ok'){
				$('vername').focus();
			}
		});
		return false;
	}	
	//厂商
	if($E($F('mfid'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.modelMgt.valid.mfid,function(btn,text){
			if(btn=='ok'){
				$('mfid').focus();
			}
		});
		return false;
	}	
	//表型
	if($E($F('model'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.modelMgt.valid.model,function(btn,text){
			if(btn=='ok'){
				$('model').focus();
			}
		});
		return false;
	}	
	//版本ID
	if($F('mfid')=='14'){
		if($E($F('verid'))) {
			Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.modelMgt.valid.verid,function(btn,text){
				if(btn=='ok'){
					$('verid').focus();
				}
			});
			return false;
		}	
	}
	return true;
}
function ChangeMf(mfid){
	dwrAction.doAjax('modelMgtAction','getModels',{mfid:$F('mfid')},function(res) {
		if (res.success) {
			$('model').options.length=0;
			var obj =res.dataObject;
			for(var i=0;i<obj.length;i++){
				var optionObj = document.createElement("option"); 
				optionObj.value = obj[i].BM;
				optionObj.text=obj[i].MC;
				$('model').appendChild(optionObj);
			}
		}
	});
	if(mfid!='14'){
		$('veridTd').style.display="none";
		$('veridTd2').style.display="none";
	}
	else{
		$('veridTd').style.display="";
		$('veridTd2').style.display="";
	}
	

	
}