var menuId = '12300';
var meterGrid = '';
var meterUrl = ctx + '/main/insMgt/insOrder!queryMeter.do';
Ext.onReady(function() {
	var _cm = [{
		header : common.kw.other.Operat,
		width : 70,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'
									+ main.insMgt.plan.text.selMeter
									+ '" href="javascript:selectMeter(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\',\'{7}\',\'{8}\',\'{9}\',\'{10}\');" ></a>', 
									record.data.MSN, record.data.CNO, record.data.CNAME, record.data.TFID, record.data.TFNAME, record.data.ADDR,
									record.data.MTYPE, record.data.WIR, record.data.MMODE,record.data.UID,record.data.UNAME);
			return url;
		}
	},{
		header : common.kw.meter.MSN,
		dataIndex : 'MSN',
		width : 100
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 100
	},{
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 100
	},{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a href="javascript:openDwWin(\'{0}\');">'+record.data.UNAME+'</a>&nbsp;&nbsp;',
					record.data.UID);
			return url;
		}
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 130,
		hidden : true
	},{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : common.kw.meter.MType,
		dataIndex : 'MTNAME',
		width : 100
	},{
		header : common.kw.meter.MCMethod,
		dataIndex : 'WNAME',
		width : 130
	},{
		header : main.insMgt.plan.grid.col.meterMode,
		dataIndex : 'MMNAME',
		width : 100
	},{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'MTYPE',
		hidden : true			
	},{
		dataIndex : 'MMODE',
		hidden : true			
	},{
		dataIndex : 'WIR',
		hidden : true		
	}];
	
	meterGrid = new Wg.Grid({
		url : meterUrl,
		el : 'MeterGrid',
		title : main.insMgt.plan.grid.title.meter,
		heightPercent : 0.62,
		cModel : _cm,
		stripeRows : true,
		notLockColumn : true,
		checkOnly : false
	});
	
	var p = $FF('queryMeterForm');
	//grid数据初始
	meterGrid.init(p);// （是否选择、是否单选）
	
	var type = $F('type');
	if(type == 'new') {
		$('mType').disabled = true;
	 	$('wir').disabled = true;
	 	$('mMode').disabled = true; 
		$('mType').style.backgroundColor='#cccccc';
		$('wir').style.backgroundColor='#cccccc';
		$('mMode').style.backgroundColor='#cccccc';
	}
});

/**
 * 查询
 */
function query(){
	var p = $FF('queryMeterForm');
	meterGrid.reload(p);
}

/**
 * 表计类型改变
 */
function changeMType(){
	query();
}

/**
 * 接线方式改变
 */
function changeWir(){
	query();
}

/**
 * 表计模式改变
 */
function changeMMode(){
	query();
}

/**
 * 选择表计
 */
function selectMeter(msn,cno,cname,tfId,tfName,addr,mType,wir,mMode,uid,uname){
	var type = $F('type');
	//校验换的表计是否还有未完成的
//	var param = {
//			deviceOldNo : omsn,
//			valType : 'meter'
//	};
	
//	dwrAction.doAjax("insPlanAction", "validateReplace", param, function(re){
//    	 if(re.success){
	if(type == 'new') {
		parent.$('nmsn').value = msn;
		parent.win.close();
	} else {
		parent.$('cno').value = cno;
		parent.$('cname').value = cname;
		parent.$('tfName').value = tfName;
		parent.$('uid').value = uid;
		parent.$('uname').value = uname;
		parent.$('tfId').value = tfId;
		parent.$('addr').value = addr;
		parent.$('omsn').value = msn; 
		if($F('bussType') == '2') {//拆表
			parent.$('mType').value = mType;
			parent.$('wir').value = wir;
			parent.$('mMode').value = mMode;
		}
		parent.win.close();
	}
//         }else{
// 			Wg.alert(re.msg, function() {
//			
//			});
//         }
//     });		
}