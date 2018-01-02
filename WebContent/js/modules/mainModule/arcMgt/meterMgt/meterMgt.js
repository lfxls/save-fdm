loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11200';
var grid_url = ctx + '/main/arcMgt/meterMgt!query.do';
var meterGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	showLeft();
	var _cm = [
	{
	header : main.arcMgt.meterMgt.grid.col.op,
	dataIndex : 'OP',
	width : 80,
	locked:true,
	renderer : function(value, cellmeta, record) {
		var url="";
		url += String.format('<a class="edit" title="\{3}\" href="javascript:initEditMeter(\'{0}\',\'{1}\',\'{2}\');"></a>',
				record.data.MSN,record.data.STATUS,record.data.TFNAME,main.arcMgt.meterMgt.grid.col.edit);
		if(record.data.STATUS!="1"){
			url += String.format('<a class="del" title="\{1}\" href="javascript:delMeter(\'{0}\');"></a>',
					record.data.MSN,main.arcMgt.meterMgt.grid.col.del);
		}
		return url;
	}
	},{
		header : main.arcMgt.meterMgt.grid.col.msn,
		dataIndex : 'MSN',
		width : 120,
		locked:true,
		sortable : true
	},{
		header : common.kw.other.PU,
		dataIndex : 'UNAME',
		width : 120
	},{
		dataIndex : 'UID',
		hidden:true
	},{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120,
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	}, {
		header :main.arcMgt.meterMgt.grid.col.mboxid,
		dataIndex : 'MBOXID',
		width : 120
	},{
		header : common.kw.meter.MType,
		dataIndex : 'MTYPE',
		width : 120,
	}, {
		header : main.arcMgt.meterMgt.grid.col.mode,
		dataIndex : 'MODE',
		width : 120
	}, {
		header :common.kw.other.Status,
		dataIndex : 'STATUSNAME',
		width : 120
	},{
		dataIndex : 'STATUS',
		hidden:true
	}, {
		header :common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 150
	},{
		header : main.arcMgt.meterMgt.grid.col.mfname,
		dataIndex : 'MF',
		width : 120
	}, {
		header : main.arcMgt.meterMgt.grid.col.CT,
		dataIndex : 'CT',
		width : 120
	}, {
		header : main.arcMgt.meterMgt.grid.col.PT,
		dataIndex : 'PT',
		width : 120
	}, {
		header :common.kw.meter.MCMethod,
		dataIndex : 'WIRING',
		width : 150
	}, {
		header : common.kw.other.Longitude,
		dataIndex : 'LON',
		width : 120
	}, {
		header : common.kw.other.Latitude,
		dataIndex : 'LAT',
		width : 120
	},{
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		header :  main.arcMgt.meterMgt.grid.col.dataSrc,
		dataIndex : 'DATASRC',
		width : 120
	},{
		header : main.arcMgt.meterMgt.grid.col.ins.date,
		dataIndex : 'INS_DATE',
		width : 120
	},{
		header : main.arcMgt.meterMgt.grid.col.unins.date,
		dataIndex : 'UNINS_DATE',
		width : 120
	},{
		header :common.kw.sim.NO,
		dataIndex : 'SIMNO',
		width : 120
	}];
	
	if(helpDocAreaId == 'CameroonEneo') {
		_cm.push({
			header : main.arcMgt.meterMgt.grid.col.matCode,
			dataIndex : 'MATCODE',
			width : 120
		});
	}
	var toolbar = [{
        id: 'add-buton',
		text:main.arcMgt.meterMgt.grid.tb.add,
        tooltip:main.arcMgt.meterMgt.grid.tb.add,
        iconCls:'add',
        handler: function(){
        	initAddMeter();
        }
    },{
        id: 'import-buton',
		text:main.arcMgt.meterMgt.grid.tb.imp,
        tooltip:main.arcMgt.meterMgt.grid.tb.imp,
        iconCls:'import',
        handler: function(){
        	initImportMeter();
        }
    }];
	meterGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : main.arcMgt.meterMgt.grid.title,
		heightPercent : 0.80,
		cModel : _cm,
		excelUrl : grid_url,
		tbar:toolbar,
	});
	meterGrid.init({});
	
});
	function query(){
		if($E($F('nodeId'))){
			Wg.alert(main.arcMgt.custMgt.valid.nvl.node);
			return false;
		}
		var p = $FF('queryForm');
		meterGrid.reload(p);
	}
	function initAddMeter(){
		var url = String.format(ctx + '/main/arcMgt/meterMgt!initMeter.do?');
		top.openpageOnTree(main.arcMgt.meterMgt.add.wh,menuId+"01",main.arcMgt.meterMgt.add.wh,null,url,'yes',1);
	}
	function initImportMeter(){
		var url = String.format(ctx + "/main/arcMgt/meterMgt!initImport.do");
		var baseParam = {
				url : url,
				title : main.arcMgt.meterMgt.imp.wh,
				el : 'import',
				width : 700,
				height : 400,
				draggable : true
			};
		ImpWin = new Wg.window(baseParam);
		ImpWin.open();
	}
	function initEditMeter(MSN,STATUS,TFNAME){
		var url = String.format(
		ctx + '/main/arcMgt/meterMgt!initMeter.do?msn={0}&status={1}&czid={2}',$En(MSN),STATUS,'02');
		top.openpageOnTree(main.arcMgt.meterMgt.edit.wh,menuId+"01",main.arcMgt.meterMgt.edit.wh,null,url,'yes',1);
	}
	
	function delMeter(MSN){
		var czid = '03';
		var p = {msn:MSN};
		Ext.apply(p,{logger:main.arcMgt.meterMgt.del.logger + MSN});
		Wg.confirm(main.arcMgt.meterMgt.del.confirm,function(){
			dwrAction.doDbWorks('meterMgtAction',menuId + czid, p, function(res){
				Wg.alert(res.msg,function(){
					
					if(res.success) {
						query();
					}
				});
			});
		});
	}
	// 各页面自定义此方法(过滤节点)
	function checkNode(_node) {
			
		$('nodeDwdm').value = _node.attributes.dwdm;
		$('nodeText').value = _node.attributes.text;
		$('nodeId').value = _node.id;
		$('nodeType').value = _node.attributes.ndType;

		var p = $FF('queryForm');
		meterGrid.reload(p);
		return true;
	}
	function Reset(){
		$('nodeId').value="";
		$('nodeText').value="";
		$('msn').value="";
		$('mtype').value="";
		$('mfid').value="";
		$('mode').value="";
		$('status').value="";
		$('dataSrc').value="";
	}
	