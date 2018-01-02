var colGrid = '';
var menuId = '12200';
var col_url = ctx + '/main/insMgt/plan/insPlan!queryCol.do';
Ext.onReady(function() {
	
	var _cm = [{
		header : common.kw.other.Operat,
		width : 70,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'
									+ main.insMgt.plan.text.selCol
									+ '" href="javascript:selectCjq(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\',\'{6}\');" ></a>', 
									record.data.DWDMID, record.data.DWMC, record.data.BYQ,
									record.data.BYQID, record.data.SBDZ, record.data.JZQH, record.data.CJQH);
			return url;
		}
	},{
		header : common.kw.terminal.Collector,
		dataIndex : 'CSN',
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
		header : common.kw.transformer.TFName,
		dataIndex : 'TFID',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
		width : 130
	},{
		header : common.kw.meter.MCMethod,
		dataIndex : 'WIR',
		width : 130
	},{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    }];
	
	colGrid = new Wg.Grid({
		url : col_url,
		el : 'colGrid',
		title : main.insMgt.plan.grid.title.selCol,
		heightPercent : 0.62,
		cModel : _cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false
	});
	
	//grid数据初始
	var p = $FF('queryColForm');
	//grid数据初始
	colGrid.init(p);// （是否选择、是否单选）
});

/**
 * 查询
 */
function query(){
	var p = $FF('queryColForm');
	colGrid.reload(p);
}

/**
 * 选择集中器采集器
 */
function selectCol(csn,tfId,tfName,addr){
	//校验换的表计是否还有未完成的
	var param = {
			deviceOldNo : csn,
			valType : 'col'
	};
	
	dwrAction.doAjax("insPlanAction", "validateReplace", param, function(re){
    	 if(re.success){
    			parent.$('csn').value = csn;
    			parent.$('tfId').value = tfId;
    			parent.$('tfName').value = tfName;
    			parent.$('devAddr').value = addr;
    			parent.win.close();
         }else{
 			Wg.alert(re.msg, function() {
			
			});
         }
     });	
}
