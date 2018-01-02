loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11200';
Ext.onReady(function() {
//	hideLeft();
	if($F('czid')=='02'){
		if($F('status')=='1'){
			$('dwdms').style.display='none';
			$('nodeTextdw').style.backgroundColor='#CCCCCC';
		}
		$('msn').readOnly=true;
		$('msn').style.backgroundColor='#CCCCCC';
	}
	
});

function addMeter(){
	var p=$FF('queryForm');
	p.dataSrc='1';
	Ext.apply(p,{logger:main.arcMgt.meterMgt.add.logger + $F('msn')});//apply属性拷贝，会覆盖
	Wg.confirm(main.arcMgt.meterMgt.add.confirm,function(){
		dwrAction.doDbWorks('meterMgtAction',menuId + '01', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					$('dwdms').style.display='none';
					$('nodeTextdw').style.backgroundColor='#CCCCCC';
					$('msn').readOnly=true;
					$('msn').style.backgroundColor='#CCCCCC';
					$('czid').value="02";
				}
			});
		});
	});
}
function editMeter(){
		var p=$FF('queryForm');
		Ext.apply(p,{logger:main.arcMgt.meterMgt.edit.logger + $F('msn')});//apply属性拷贝，会覆盖
		Wg.confirm(main.arcMgt.meterMgt.edit.confirm,function(){
			dwrAction.doDbWorks('meterMgtAction',menuId + $F('czid'), p, function(res){
				Wg.alert(res.msg,function(){
					if(res.success) {
					}
				});
			});
		});
}

function Save(){
	var czid=$F('czid');
	if(valid()){
		if(czid =='02'){
			editMeter();
		}
		else{
			addMeter();
		}
	}
	
}
function valid() {
	$('msn').value = $F('msn').trim();

	var reg=/^[a-zA-Z0-9]+$/;
	//表计局号
	if($E($F('msn'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.msn,function(btn,text){
			if(btn=='ok'){
				$('msn').focus();
			}
		});
		return false;
	}
	else if(!$F('msn').match(reg)) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.format.msn,function(btn,text){
			if(btn=='ok'){
				$('msn').focus();
			}
		});
		return false;
		
	}
	//单位
	if($E($F('nodeIddw'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.uid,function(btn,text){
			if(btn=='ok'){
				$('nodeIddw').focus();
			}
		});
		return false;
	}
	//表计模式
	if($E($F('mode'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.mode,function(btn,text){
			if(btn=='ok'){
				$('mode').focus();
			}
		});
		return false;
	}
	//表计类型
	if($E($F('mtype'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.mtype,function(btn,text){
			if(btn=='ok'){
				$('mtype').focus();
			}
		});
		return false;
	}
	
	//接线方式
	if($E($F('wiring'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.wiring,function(btn,text){
			if(btn=='ok'){
				$('wiring').focus();
			}
		});
		return false;
	}
	//CT
	if($E($F('ct'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.ct,function(btn,text){
			if(btn=='ok'){
				$('ct').focus();
			}
		});
		return false;
	}
	
	//PT
	if($E($F('pt'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.pt,function(btn,text){
			if(btn=='ok'){
				$('pt').focus();
			}
		});	
		return false;
	}
	if(!$E($F('lon'))) {
		
	var str=/^-?((0|[1-9]\d?|1[1-7]\d)(\.\d{1,10})?|180(\.0{1,7})?)?$/;
	var re=$F('lon').match(str);
	if(re==null){
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.lon,function(btn,text){
			if(btn=='ok'){
				$('lon').focus();
			}
		});	
		return false;
	}
	}
	if(!$E($F('lat'))) {
		var str=/^-?((0|[1-8]\d?|1[1-7]\d)(\.\d{1,10})?|90(\.0{1,7})?)?$/;
		var re=$F('lat').match(str);
		if(re==null){
			Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.lat,function(btn,text){
				if(btn=='ok'){
					$('lat').focus();
				}
			});	
			return false;
		}
	}
	


	return true;
}


//单位树
function getDwTree() {
	var	title = main.arcMgt.meterMgt.tree.title.dw;
	getTree('dw', title, unitCode, unitName, 'dw');
	$('tfname').value="";
	$('tfid').value="";
}

function selectTransfomer(){
	var nodeIddw=$F('nodeIddw');
	if($E(nodeIddw)){
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.meterMgt.valid.uid,function(btn,text){
			if(btn=='ok'){
				$('nodeIddw').focus();
			}
		});	
	}
	else{
		selectTransf(nodeIddw);
	}
}
