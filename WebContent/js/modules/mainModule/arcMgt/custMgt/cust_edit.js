loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11100';
var grid_url = ctx + '/main/arcMgt/custMgt!queryDetail.do';
var CustGrid = '';

Ext.onReady(function() {
//	handlerType = 'query';
	//hideLeft();
	
		var _cm = [
		       	{
		       		header : common.kw.meter.MSN,
		       		dataIndex : 'MSN',
		       		width : 120,
		       		locked:true,
		       		sortable : true
		       	}, {
		       		header : common.kw.meter.MType,
		       		dataIndex : 'M_TYPE',
		       		width : 120
		       	},{
		       		header : main.arcMgt.custMgt.grid.col.mode,
		       		dataIndex : 'MODE',
		       		width : 120
		       	},{
		       		header : common.kw.transformer.TFName,
		       		dataIndex : 'TFNAME',
		       		width : 120
		       	}, 
		       	{
		       		dataIndex : 'TFID',
		       		hidden:true
		       	},  {
		       		header : main.arcMgt.custMgt.grid.col.ct,
		       		dataIndex : 'CT',
		       		width : 120
		       	},{
		       		header : main.arcMgt.custMgt.grid.col.pt,
		       		dataIndex : 'PT',
		       		width : 120,
		       	}, {
		       		header : main.arcMgt.custMgt.grid.col.ins.date,
		       		dataIndex : 'INS_DATE',
		       		width : 120
		       	}];
		       	
		       	CustGrid = new Wg.Grid( {
		       		url : grid_url,
		       		el : 'grid',
		       		title : main.arcMgt.custMgt.metergrid.title,
		       		heightPercent : 0.40,
		       		cModel : _cm,
		       		excelUrl : grid_url,
		       	});
		       	var p = {cno : $F('cno')};
		       	CustGrid.init(p,false,false);
		       	showDwdms();
	
});

function showDwdms(){
	var record=CustGrid.grid.getStore();
	if($F('czid')=='02'){
		if($F('status')=="1"){
			$('dwdms').style.display='none';
			$('nodeTextdw').style.backgroundColor='#CCCCCC';
		}
		$('cno').readOnly=true;
		$('cno').style.backgroundColor='#CCCCCC';
		$('billing_date').value=$F('billing_dates');
		$('grid').style.display='';
	}
	 if($E($F('nodeTextdw'))){
		 $('dwdms').style.display="";
	}
	 else if(record==null||record.size==0){
		$('dwdms').style.display="";
		
	}
}

function addCust(){
	var p={
			nodeIddw:$F('nodeIddw'),
			cno:$F('cno'),
			cname:$F('cname'),
			addr:$F('addr'),
			billing_date:$F('billing_date'),
			phone:$F('phone'),
			dataSrc:'1',
			custStatus:'0'
		};
		
	Ext.apply(p,{logger:main.arcMgt.custMgt.add.logger + $F('cno')+"["+$F('cname')+"]"});//apply属性拷贝，会覆盖
	Wg.confirm(main.arcMgt.custMgt.add.confirm,function(){
		dwrAction.doDbWorks('custMgtAction',menuId +'01', p, function(res){
			Wg.alert(res.msg,function(){
				if(res.success) {
					$('cno').readOnly=true;
					$('cno').style.backgroundColor='#CCCCCC';
					$('czid').value="02";
				}
			});
		});
	});
}
function editCust(){
	var p={
			nodeIddw:$F('nodeIddw'),
			cno:$F('cno'),
			cname:$F('cname'),
			addr:$F('addr'),
			billing_date:$F('billing_date'),
			phone:$F('phone')
		};
		
		Ext.apply(p,{logger:main.arcMgt.custMgt.edit.logger + $F('cno')+"["+$F('cname')+"]"});//apply属性拷贝，会覆盖
		Wg.confirm(main.arcMgt.custMgt.edit.confirm,function(){
			dwrAction.doDbWorks('custMgtAction',menuId + $F('czid'), p, function(res){
				Wg.alert(res.msg,function(){
					if(res.success) {
					}
				});
			});
		});
}

function save2(){
	var czid=$F('czid');
	if(valid()){
		if(czid =='02'){
			editCust();
		}
		else{
			addCust();
		}
	}
	
}
function valid() {
	$('cno').value = $F('cno').trim();
	var reg=/^[a-zA-Z0-9]+$/;
	//户号
	if($E($F('cno'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.cno,function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
	}
	else if(!$F('cno').match(reg)) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.format.cno,function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
		
	}
	//户名
	if($E($F('cname'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.cname,function(btn,text){
			if(btn=='ok'){
				$('cname').focus();
			}
		});
		return false;
	}
	//单位
	if($E($F('nodeIddw'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.uid,function(btn,text){
			if(btn=='ok'){
				$('nodeIddw').focus();
			}
		});
		return false;
	}
	//用户地址
	if($E($F('addr'))) {
		Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.addr,function(btn,text){
			if(btn=='ok'){
				$('addr').focus();
			}
		});
		return false;
	}
	if(!$E($F('phone'))){
		var str=/^\d{0,32}$/;
		if(!$F('phone').match(str)){
			Ext.Msg.alert(main.arcMgt.alert.title,main.arcMgt.custMgt.valid.phone,function(btn,text){
				if(btn=='ok'){
					$('phone').focus();
				}
			});
			return false;
		}
	}
	return true;
}

//单位树
function getDwTree() {
	var	title = main.arcMgt.custMgt.tree.title.dw;
	if(czid='01'){
		getTree('dw', title, unitCode, unitName, 'dw');
	}
	else{
		var totalCount = CustGrid.grid.getStore().getTotalCount();
		if(totalCount!=0){
			Wg.alert(main.arcMgt.custMgt.bind.meter);
		}
		else{
			getTree('dw', title, unitCode, unitName, 'dw');
		}
	}
}
