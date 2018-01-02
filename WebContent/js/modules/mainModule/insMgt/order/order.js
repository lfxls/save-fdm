var menuId = '12300';
var mInsPGrid = '';
var dInsPGrid = '';
var cInsPGrid = '';
var surPGrid = '';
var mUrl = ctx + '/main/insMgt/insOrder!queryMInsPlan.do';
var dUrl = ctx + '/main/insMgt/insOrder!queryDInsPlan.do';
var cUrl = ctx + '/main/insMgt/insOrder!queryCInsPlan.do';
var sUrl = ctx + '/main/insMgt/insOrder!querySurPlan.do';
Ext.onReady(function() {
	if($F('oType') != '3'){
		mInsPGridInit();//初始表安装计划grid
//		dInsPGridInit();//初始集中器安装计划grid
//		cInsPGridInit();//初始采集器安装计划grid
	}else{
		surPGridInit();//初始化勘察计划grid
	} 
});

/**
 * 初始化表安装计划grid
 */
function mInsPGridInit() {
	var m_cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if($F('operateType') != '04') {//非查看工单情况下，可以删除
				url += String.format('<a class="del" title="\{1}\" href="javascript:doMInsPDel(\'{0}\');"></a>',
						record.data.SN, main.insMgt.plan.grid.op.del);
			}
			return url;
		}
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130	
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 120
    },{
		header : common.kw.other.Status,
		dataIndex : 'STNAME',
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
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 130
	},{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
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
		header : main.insMgt.plan.grid.col.newMeterNo,
		dataIndex : 'NMSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldMeterNo,
		dataIndex : 'OMSN',
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
		dataIndex : 'SN',
		hidden : true		
	}];

	var mToolbar = [];
	if($F('operateType') != '04') {//非查看工单显示操作按钮
		showMToolBar(mToolbar);
	} 

	var fields = [
    	{name: 'OP'},
    	{name: 'PID'},
    	{name: 'BTNAME'},
    	{name: 'CNO'},
    	{name: 'CNAME'},
    	{name: 'ADDR'},
    	{name: 'TFNAME'},
    	{name: 'UNAME'},
    	{name: 'NMSN'},
    	{name: 'OMSN'},
    	{name: 'TFNAME'},
    	{name: 'MTNAME'},
    	{name: 'WNAME'},
    	{name: 'MMNAME'},
  	];
	      	
	
	mInsPGrid = new Wg.Grid({
		url : mUrl,
		el : 'mInsPGrid',
		title : main.insMgt.plan.grid.title.selMInsPln,
		heightPercent : 0.82,
		widthPercent : 1,
		margin : 60,
		cModel : m_cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : mToolbar,
		gFields: fields,//字段列
		collapsible : true
	});
	
	//grid数据初始
	var p = $FF('cWOForm');
	mInsPGrid.init(p);
	if($F('operateType') == '04') {//查看工单情况下隐藏第一列
		mInsPGrid.hideCol(0);
		$('fTime').disabled=true;//计划完成时间不可编辑
	}
	mInsPGrid.grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if (record.data.PID == '') {
			return "insPln-create";
		} 
	};
}

/**
 * 初始化集中器安装计划grid
 */
function dInsPGridInit() {
	var m_cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			url += String.format('<a class="del" title="\{1}\" href="javascript:doDInsPDel(\'{0}\');"></a>',
					record.data.SN, main.insMgt.plan.grid.op.del);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 120
    },{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
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
		header : main.insMgt.plan.grid.col.newDcuNo,
		dataIndex : 'NDSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldDcuNo,
		dataIndex : 'ODSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.dcuModel,
		dataIndex : 'DMNAME',
		width : 100
	},{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'PID',
		hidden : true		
	},{
		dataIndex : 'SN',
		hidden : true		
	}];

	var dToolbar = [{
			id : 'selectD',
			text : main.insMgt.order.grid.tb.select,
			tooltip : main.insMgt.order.grid.tb.select,
			iconCls : 'select',
			handler : function() {
				addDInsP();
			}
		},{
			id : 'addD',
			text : main.insMgt.order.grid.tb.create,
			tooltip : main.insMgt.order.grid.tb.create,
			iconCls : 'add',
			handler : function() {
				createDInsP();
			}		
		}];

	dInsPGrid = new Wg.Grid({
		url : dUrl,
		el : 'dInsPGrid',
		title : main.insMgt.plan.grid.title.dcu,
		heightPercent : 0.42,
		widthPercent : 1,
		margin : 60,
		cModel : m_cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		pageNotSupport:true,
		tbar : dToolbar,
		collapsed : true,
		collapsible : true
	});
	
	//grid数据初始
	var p = $FF('cWOForm');
	dInsPGrid.init(p);
}

/**
 * 初始化采集器安装计划grid
 */
function cInsPGridInit() {
	var m_cm = [{
		header : common.kw.other.Operat,
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			url += String.format('<a class="del" title="\{1}\" href="javascript:doCInsPDel(\'{0}\');"></a>',
					record.data.SN, main.insMgt.plan.grid.op.del);
			return url;
		}
	},{
		header : main.insMgt.plan.grid.col.bussType,
		dataIndex : 'BTNAME',
		width : 120
    },{
		header : common.kw.transformer.TFName,
		dataIndex : 'TFNAME',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.devAddr,
		dataIndex : 'ADDR',
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
		header : main.insMgt.plan.grid.col.newColNo,
		dataIndex : 'NCSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.oldColNo,
		dataIndex : 'OCSN',
		width : 100
	},{
		header : main.insMgt.plan.grid.col.colModel,
		dataIndex : 'CMNAME',
		width : 100
	},{
		dataIndex : 'TFID',
		hidden : true
    },{
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'PID',
		hidden : true		
	},{
		dataIndex : 'SN',
		hidden : true		
	}];

	var cToolbar = [{
			id : 'selectC',
			text : main.insMgt.order.grid.tb.select,
			tooltip : main.insMgt.order.grid.tb.select,
			iconCls : 'select',
			handler : function() {
				addDInsP();
			}
		},{
			id : 'addC',
			text : main.insMgt.order.grid.tb.create,
			tooltip : main.insMgt.order.grid.tb.create,
			iconCls : 'add',
			handler : function() {
				createCInsP();
			}		
		}];

	cInsPGrid = new Wg.Grid({
		url : cUrl,
		el : 'cInsPGrid',
		title : main.insMgt.plan.grid.title.col,
		heightPercent : 0.42,
		widthPercent : 1,
		margin : 60,
		cModel : m_cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		pageNotSupport:true,
		tbar : cToolbar,
		collapsible : true
	});
	
	//grid数据初始
	var p = $FF('cWOForm');
	cInsPGrid.init(p);
}


/**
 * 初始化勘察计划grid
 */
function surPGridInit() {
	var s_cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 80,
		renderer : function(value, cellmeta, record) {
			var url = "";
			if($F('operateType') != '04') {//非查看工单情况下，可以删除
				url += String.format('<a class="del" title="\{1}\" href="javascript:doSurPDel(\'{0}\');"></a>',
						record.data.SN, main.insMgt.plan.grid.op.del);
			}
			return url;
		}
	},{
		header : common.kw.plan.planno,
		dataIndex : 'PID',
		width : 130	
	},{
		header : common.kw.other.Status,
		dataIndex : 'STNAME',
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
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 130
	},{
		header : common.kw.other.Mobile,
		dataIndex : 'MNO',
		width : 130
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
		dataIndex : 'UID',
		hidden : true
    },{
		dataIndex : 'SN',
		hidden : true		
	}];

	var sToolbar = [];
	if($F('operateType') != '04') {//非查看工单显示操作按钮
		sToolbar.push( {
			id : 'selectS',
			text : main.insMgt.order.grid.tb.select,
			tooltip : main.insMgt.order.grid.tb.select,
			iconCls : 'select',
			handler : function() {
				addSurP();
			}
		},{
			id : 'addS',
			text : main.insMgt.order.grid.tb.create,
			tooltip : main.insMgt.order.grid.tb.create,
			iconCls : 'add',
			handler : function() {
				createSurP();
			}		
		});
	} 
	
	surPGrid = new Wg.Grid({
		url : sUrl,
		el : 'surPGrid',
		title : main.insMgt.plan.grid.title.selSurPln,
		heightPercent : 0.82,
		widthPercent : 1,
		margin : 60,
		cModel : s_cm,
		stripeRows : true,
		notLockColumn:true,
		checkOnly : false,
		tbar : sToolbar,
		collapsible : true
	});
	
	//grid数据初始
	var p = $FF('cWOForm');
	surPGrid.init(p);
	if($F('operateType') == '04') {//查看工单情况下隐藏第一列
		surPGrid.hideCol(0);
		$('fTime').disabled=true;//计划完成时间不可编辑
	}
	surPGrid.grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if (record.data.PID == '') {
			return "insPln-create";
		} 
	};
}
/**
 * 查询已选表安装计划
 */
function mInsPQuery() {
	var p = $FF('cWOForm');
	mInsPGrid.reload(p);
	mInsPGrid.grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if (record.data.PID == '') {
			return "insPln-create";
		} 
	};
}

/**
 * 查询已选集中器安装计划
 */
function dInsPQuery() {
	var p = $FF('cWOForm');
	dInsPGrid.reload(p);	
}

/**
 * 查询已选采集器安装计划
 */
function cInsPQuery() {
	var p = $FF('cWOForm');
	cInsPGrid.reload(p);	
}

/**
 * 查询已选勘察计划
 */
function surPQuery() {
	var p = $FF('cWOForm');
	surPGrid.reload(p);
}

/**
 * 添加表安装计划
 */
function addMInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initMInsP.do?mszh={0}&mdzh={1}',
			$F('mszh'),$F('mdzh'));
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.sel.meterInsPlan,
		el : 'selMeter',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 新增表安装计划
 */
function createMInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initCMInsP.do?mczh={0}&mdzh={1}',
			$F('mczh'),$F('mdzh'));
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.add.meterInsPlan,
		el : 'addMeter',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 添加集中器安装计划
 */
function addDInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initDInsP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.sel.dcuInsPlan,
		el : 'selDcu',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 新增集中器安装计划
 */
function createDInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initCDInsP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.add.dInsPlan,
		el : 'addDcu',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 添加采集器安装计划
 */
function addCInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initCInsP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.sel.colInsPlan,
		el : 'selCol',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 新增采集器安装计划
 */
function createCInsP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initCCInsP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.add.cInsPlan,
		el : 'addCol',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 添加勘察计划
 */
function addSurP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initSurP.do?sszh={0}&sdzh={1}',
			$F('sszh'),$F('sdzh'));
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.sel.SurPlan,
		el : 'selSurvy',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 新增勘察计划
 */
function createSurP() {
	var url = ctx + String.format('/main/insMgt/insOrder!initCSurP.do?sczh={0}&sdzh={1}',
			$F('sczh'),$F('sdzh'));
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.add.SurPlan,
		el : 'addSurvy',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

function queryPop() {
	var url = ctx + String.format('/main/insMgt/insOrder!initPOP.do');
	var baseParam = {
		url : url,
		title : main.insMgt.order.win.title.pop,
		el : 'selPOP',
		width : 660,
		height : 550,
		draggable : true
	};
	
	selWin = new Wg.window(baseParam); 
	selWin.open();
}

/**
 * 对已加入工单生成页面中的表安装计划进行删除操作
 * @param sn
 */
function doMInsPDel(sn) {
	var mszh = $F('mszh');//存储已选安装计划
	var mczh = $F('mczh');//存储新增安装计划
	var mdzh = $F('mdzh');//存储删除已选安装计划
	var newSzh = '';
	if(!$E(mszh)) {
		var mszhArray = mszh.split(';');
		for(var i = 0; i < mszhArray.length; i++) {
			if((i + 1) != sn) {//不匹配要删除的序号
				if($E(newSzh)) {
					newSzh = mszhArray[i];
				} else {
					newSzh = newSzh + ';' + mszhArray[i];
				}
			} else {//匹配要删除的序号
				if($E(mdzh)) {
					mdzh = mszhArray[i];
				} else {
					mdzh = mdzh + ',' + mszhArray[i];
				}
			}
		}
		$('mszh').value = newSzh;
	} 
	if(newSzh == mszh) {
		var count = 0;
		if(!$E(newSzh)) {
			count = newSzh.split(';').length;
		}
		var newCzh = '';
		var mczhArray = mczh.split(';');
		for(var i = 0; i < mczhArray.length; i++) {
			if((i + 1 + count) != sn) {
				if($E(newCzh)) {
					newCzh = mczhArray[i];
				} else {
					newCzh = newCzh + ';' + mczhArray[i];
				}
			}
		}
		$('mczh').value = newCzh;
	} else {
		$('mszh').value = newSzh;
	}
	$('mdzh').value = mdzh;
	mInsPQuery();
}

/**
 * 对已加入工单生成页面中的集中器安装计划进行删除操作
 * @param sn
 */
function doDInsPDel(sn) {
	var dszh = $F('dszh');
	var dczh = $F('dczh');
	var newSzh = '';
	if(!$E(dszh)) {
		var dszhArray = dszh.split(';');
		for(var i = 0; i < dszhArray.length; i++) {
			if((i + 1) != sn) {
				if($E(newSzh)) {
					newSzh = dszhArray[i];
				} else {
					newSzh = newSzh + ';' + dszhArray[i];
				}
			}
		}
	} 
	if(newSzh == dszh) {
		var count = 0;
		if(!$E(newSzh)) {
			count = newSzh.split(';').length;
			$('dszh').value = newSzh;
		}
		var newCzh = '';
		var dczhArray = dczh.split(';');
		for(var i = 0; i < dczhArray.length; i++) {
			if((i + 1 + count) != sn) {
				if($E(newCzh)) {
					newCzh = dczhArray[i];
				} else {
					newCzh = newCzh + ';' + dczhArray[i];
				}
			}
		}
		$('dczh').value = newCzh;
	} else {
		$('dszh').value = newSzh;
	}
	dInsPQuery();
}

/**
 * 对已加入工单生成页面中的采集器安装计划进行删除操作
 * @param sn
 */
function doCInsPDel(sn) {
	var cszh = $F('cszh');
	var cczh = $F('cczh');
	var newSzh = '';
	if(!$E(cszh)) {
		var cszhArray = cszh.split(';');
		for(var i = 0; i < cszhArray.length; i++) {
			if((i + 1) != sn) {
				if($E(newSzh)) {
					newSzh = cszhArray[i];
				} else {
					newSzh = newSzh + ';' + cszhArray[i];
				}
			}
		}
	} 
	if(newSzh == cszh) {
		var count = 0;
		if(!$E(newSzh)) {
			count = newSzh.split(';').length;
			$('cszh').value = newSzh;
		}
		var newCzh = '';
		var cczhArray = cczh.split(';');
		for(var i = 0; i < cczhArray.length; i++) {
			if((i + 1 + count) != sn) {
				if($E(newCzh)) {
					newCzh = cczhArray[i];
				} else {
					newCzh = newCzh + ';' + cczhArray[i];
				}
			}
		}
		$('cczh').value = newCzh;
	} else {
		$('cszh').value = newSzh;
	}
	cInsPQuery();
}

/**
 * 对已加入工单生成页面中的勘察计划进行删除操作
 * @param sn
 */
function doSurPDel(sn) {
	var sszh = $F('sszh');//存储已选安装计划
	var sczh = $F('sczh');//存储新增安装计划
	var sdzh = $F('sdzh');//存储删除已选安装计划
	var newSzh = '';
	if(!$E(sszh)) {
		var sszhArray = sszh.split(';');
		for(var i = 0; i < sszhArray.length; i++) {
			if((i + 1) != sn) {//不匹配要删除的序号
				if($E(newSzh)) {
					newSzh = sszhArray[i];
				} else {
					newSzh = newSzh + ';' + sszhArray[i];
				}
			} else {//匹配要删除的序号
				if($E(sdzh)) {
					sdzh = sszhArray[i];
				} else {
					sdzh = sdzh + ',' + sszhArray[i];
				}
			}
		}
		$('sszh').value = newSzh;
	} 
	if(newSzh == sszh) {
		var count = 0;
		if(!$E(newSzh)) {
			count = newSzh.split(';').length;
		}
		var newCzh = '';
		var sczhArray = sczh.split(';');
		for(var i = 0; i < sczhArray.length; i++) {
			if((i + 1 + count) != sn) {
				if($E(newCzh)) {
					newCzh = sczhArray[i];
				} else {
					newCzh = newCzh + ';' + sczhArray[i];
				}
			}
		}
		$('sczh').value = newCzh;
	} else {
		$('sszh').value = newSzh;
	}
	$('sdzh').value = sdzh;
	surPQuery();
}

/**
 * 生成工单
 */
function createWO() {
	if($E($F('mszh')) && $E($F('mczh')) &&
			$E($F('dszh')) && $E($F('dczh')) &&
			$E($F('cszh')) && $E($F('cczh'))&&
			$E($F('sszh')) && $E($F('sczh'))) {
		Wg.alert(main.insMgt.order.valid.addInsPlan);
		return;
	}
	
	if($E($F('fTime'))) {
		Wg.alert(main.insMgt.order.valid.plnFTime.notEmpty);
		return;
	}
	
	var p = $FF('cWOForm');
	
	var operateType = $F('operateType');
	if(operateType == '01') {
		addWO(p);//新增工单
	} else if(operateType == '02') {
		editWO(p);//编辑工单
	}
}

/**
 * 新增工单
 * @param p
 */
function addWO(p) {
	Ext.apply(p, {
		logger : main.insMgt.order.log.add.workOrder
	});
	
	Wg.confirm(main.insMgt.order.hint.confirmAdd, function() {
		dwrAction.doDbWorks('insOrderAction', menuId + addOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					$('woid').value=res.dataObject;
					var url = String.format(ctx + '/main/insMgt/insOrder!initEditInsWO.do?woid={0}&operateType={1}&fTime={2}&oType={3}',
							$F('woid'),'02',$F('fTime'),$F('oType'));
					window.location.href = url;
				}
			});
		});
	});	
}

/**
 * 编辑工单
 * @param p
 */
function editWO(p) {
	Ext.apply(p, {
		logger : main.insMgt.order.log.edit.workOrder
	});
	
	Wg.confirm(main.insMgt.order.hint.confirmEdit, function() {
		dwrAction.doDbWorks('insOrderAction', menuId + updOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					var url = String.format(ctx + '/main/insMgt/insOrder!initEditInsWO.do?woid={0}&operateType={1}&fTime={2}&oType={3}',
							$F('woid'),'02',$F('fTime'),$F('oType'));
					window.location.href = url;
				}
			});
		});
	});	
}

/**
 * 显示表安装计划操作按钮
 * @param mToolbar
 */
function showMToolBar(mToolbar) {
	mToolbar.push( {
		id : 'selectM',
		text : main.insMgt.order.grid.tb.select,
		tooltip : main.insMgt.order.grid.tb.select,
		iconCls : 'select',
		handler : function() {
			addMInsP();
		}
	},{
		id : 'addM',
		text : main.insMgt.order.grid.tb.create,
		tooltip : main.insMgt.order.grid.tb.create,
		iconCls : 'add',
		handler : function() {
			createMInsP();
		}		
	});
}