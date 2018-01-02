var dcuGrid = '';
var menuId = '12200';
var dcu_url = ctx + '/main/insMgt/plan/insPlan!queryDcu.do';
Ext.onReady(function() {
		var _cm = [{
			header : common.kw.other.Operat,
			width : 70,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="addCell" title="'
										+ main.insMgt.plan.text.selDcu
										+ '" href="javascript:selectDcu(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');" ></a>', 
										record.data.TFID, record.data.TFNAME, record.data.ADDR,
										record.data.DCUM, record.data.DSN);
				return url;
			}
		},{
			header : common.kw.terminal.DCU,
			dataIndex : 'DSN',
			width : 140
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
			header : common.kw.transformer.TFName,
			dataIndex : 'TFNAME',
			width : 100
		},{
			header : main.insMgt.plan.grid.col.devAddr,
			dataIndex : 'ADDR',
			width : 130
		},{
			header : main.insMgt.plan.grid.col.dcuMode,
			dataIndex : 'DMNAME',
			width : 140
		},{
			header : common.kw.meter.MCMethod,
			dataIndex : 'WIR',
			width : 120
		},{
			dataIndex : 'TFID',
			hidden : true
	    },{
			dataIndex : 'DCUM',
			hidden : true			
		}];
		
		dcuGrid = new Wg.Grid({
			url : dcu_url,
			el : 'dcuGrid',
			title : main.insMgt.plan.grid.title.dcu,
			heightPercent : 0.62,
			cModel : _cm,
			stripeRows : true,
			notLockColumn : true,
			checkOnly : false
		});
		
		var p = $FF('queryDcuForm');
		//grid数据初始
		dcuGrid.init(p);// （是否选择、是否单选）
});

/**
 * 查询
 */
function query(){
	var p = $FF('queryDcuForm');
	dcuGrid.reload(p);
}

/**
 * 集中器型号类型改变
 */
function changeDcuM(){
	query();
}

/**
 * 选择集中器
 */
function selectDcu(tfId, tfName, addr, dcuM, dsn){
	
	//校验换的表计是否还有未完成的
	var param = {
			deviceOldNo : dsn,
			valType : 'dcu'
	};
	
	dwrAction.doAjax("insPlanAction", "validateReplace", param, function(re){
    	 if(re.success){
    		 	parent.$('odsn').value = dsn;
    			parent.$('tfId').value = tfId;
    			parent.$('tfName').value = tfName;
    			parent.$('devAddr').value = addr;
    			parent.$('dcuM').value = dcuM;
    			parent.win.close();
         }else{
 			Wg.alert(re.msg, function() {
			
			});
         }
     });	
}