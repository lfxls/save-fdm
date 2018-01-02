var menuId = '123456';
var selClientNo = '';
// 客户信息Grid
var clientGrid = '';
// license信息Grid
var licGrid = '';
var clientUrl = ctx + '/common/license/licMgt!query.do';
var licUrl = ctx + '/common/license/licMgt!queryDetail.do';
Ext.onReady(function() {
	hideLeft();
	var clientCm = [{
		header : '操作',
		dataIndex : 'OP',
		width : 60,
		align : 'center',
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title=' + '编辑' + ' href="javascript:editClient(\'{0}\',\'{1}\');" />&nbsp;', record.data.clientNo, 'edit');
			url += String.format('<a class="del" title=' + '删除' + ' href="javascript:delClient(\'{0}\');" />', record.data.clientNo);
			url += String.format('<a class="imp" title=' + '导入硬件信息' + ' href="javascript:importMachInfo(\'{0}\');" />', record.data.clientNo);
			return url;
		}
	}, {
		header : '客户编号',
		dataIndex : 'clientNo',
		width : 90,
		sortable : true,
		align : 'center'
	}, {
		header : '客户名称',
		dataIndex : 'clientName',
		width : 90,
		align : 'center'
	}, {
		header : 'CPU标识',
		dataIndex : 'cpuId',
		width : 120,
		align : 'center'
	}, {
		header : 'BIOS标识',
		dataIndex : 'biosId',
		width : 120,
		align : 'center'
	}, {
		header : '硬盘标识',
		dataIndex : 'diskId',
		width : 120,
		align : 'center'
	}];

	var clientToolBar = [ {
		id : 'clientAddBtn',
		text : '新增',
		tooltip : 'Add',
		iconCls : 'Add',
		handler : function() {
			editClient('', 'add');
		}
	}];

	clientGrid = new Wg.Grid({
		url : clientUrl,
		el : 'clientGrid',
		frame : true,
		pageSize: 20,
		title : '客户信息',
		heightPercent : 0.85,
		widthPercent : 0.48,
		tbar : clientToolBar,
		cModel : clientCm
	});

	var licCm = [{
		header : '操作',
		dataIndex : 'OP',
		width : 60,
		align : 'center',
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="edit" title=' + '编辑' + ' href="javascript:editLic(\'{0}\',\'{1}\');" />&nbsp;', record.data.licNo, 'edit');
			url += String.format('<a class="del" title=' + '删除' + ' href="javascript:delLic(\'{0}\');" />', record.data.licNo);
			url += String.format('<a class="exp" title=' + '生成license文件' + ' href="javascript:generateLicenseFile(\'{0}\');" />', record.data.licNo);
			return url;
		}
	}, {
		header : 'license编号',
		dataIndex : 'licNo',
		hidden : true,
		align : 'center'
	}, {
		header : '电表数量',
		dataIndex : 'meterNum',
		width : 90,
		align : 'center'
	}, {
		header : '过期时间',
		dataIndex : 'expDate',
		width : 90,
		align : 'center'
	}, {
		header : '创建时间',
		dataIndex : 'createDate',
		width : 90,
		sortable : true,
		align : 'center'
	}];

	var licToolBar = [{
		id : 'licAddBtn',
		text : '新增',
		tooltip : 'Add',
		iconCls : 'Add',
		handler : function(config) {
			editLic('', 'add');
		}
	},{
		id : 'licBtn',
		text : '免费license',
		tooltip : 'license',
		iconCls : 'license',
		handler : function(config) {
			freeLicense();
		}
	}];

	licGrid = new Wg.Grid({
		url : licUrl,
		el : 'licGrid',
		frame : true,
		pageSize: 20,
		title : 'license信息',
		heightPercent : 0.85,
		widthPercent : 0.48,
		tbar : licToolBar,
		cModel : licCm
	});
	
	clientGrid.init({});
	licGrid.init({clientNo: selClientNo});
	
	/*//定义客户Grid的加载事件
	clientGrid.grid.getStore().on('load', function(store, records, obj){
		var clientNo = store.getAt(0).get('clientNo');
		selClientNo = clientNo;
		licGrid.init({clientNo: clientNo});
	});*/
	
	//客户信息行单击事件，根据客户编号获取对应的license信息
	clientGrid.grid.addListener('rowclick',
		function(grid, rowIndex, e) {
			var clientNo = grid.getStore().getAt(rowIndex).get('clientNo');
			selClientNo = clientNo;
			licGrid.reload({clientNo: clientNo});
	});
});

// 查询客户信息，同时加载license信息
function queryClient() {
	var clientNo = $F('clientNo');
	clientGrid.reload({clientNo: clientNo});
	licGrid.reload({clientNo: clientNo});
}

// 新增或者编辑客户
function editClient(clientNo, optType) {
	var url = String.format(ctx
			+ '/common/license/licMgt!initClient.do?clientNo={0}&optType={1}',
			clientNo, optType);
	var title = '';
	if (optType == 'add') {
		title = '新增客户信息';
	} else {
		title = '编辑客户信息';
	}

	var baseParam = {
		url : url,
		el : 'editClient',
		title : title,
		width : 700,
		height : 180,
		draggable : true
	};
	var win = new Wg.window(baseParam);
	win.open();
}

// 新增或者编辑license
function editLic(licNo, optType) {
	// 新增license前需要选择一个客户
	if (optType == 'add') {
		if (selClientNo == '') {
			parent.Wg.alert('请选择一个客户！');
			return false;
		}
	}
	var url = String
			.format(ctx + '/common/license/licMgt!initLic.do?clientNo={0}&licNo={1}&optType={2}', selClientNo, licNo, optType);

	var title = '';
	if (optType == 'add') {
		title = '新增license信息';
	} else {
		title = '编辑license信息';
	}

	var baseParam = {
		url : url,
		el : 'editLic',
		title : title,
		width : 550,
		height : 150,
		draggable : true
	};
	var win = new Wg.window(baseParam);
	win.open();
}

//删除一个客户信息
function delClient(clientNo) {
	var param = {
		clientNo : clientNo,
		optObj : 'client',
		logger : '删除客户' + clientNo
	};
	parent.Wg.confirm('确定要删除客户？', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + delOpt, param, function(
				res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					clientGrid.reload();
				}
			});
		});
	});
}

//删除一条license信息
function delLic(licNo) {
	var param = {
		licNo : licNo,
		optObj : 'license',
		logger : '删除license' + licNo
	};
	parent.Wg.confirm('确定要删除license?', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + delOpt, param, function(
				res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					licGrid.reload({
						clientNo : selClientNo
					});
				}
			});
		});
	});
}

//生成license文件
function generateLicenseFile(licNo){
	/*Ext.Ajax.request({
	    url : ctx + '/common/license/licMgt!generateLicenseFile.do',
	    method : 'POST',
	    params : {
	    	licNo : licNo
	    },
	    success : function(form, action){
	        var obj = Ext.util.JSON.decode(form.responseText);
	        if(!obj.success){
	        	//生成失败
	        	Wg.alert(obj.msg);
	        } 
	    },
	    failure:function(form, action){
        	Wg.alert('系统问题！');
	    }
	});*/
	window.open(ctx + '/common/license/licMgt!generateLicenseFile.do?licNo=' + licNo);
}

//导入硬件信息
function importMachInfo(clientNo){
	var url = String.format(ctx + '/common/license/licMgt!initMach.do?clientNo={0}', clientNo);
	var baseParam = {
		url : url,
		el : 'importMachInfo',
		title : '导入硬件信息',
		width : 600,
		height : 155,
		draggable : true
	};
	var win = new Wg.window(baseParam);
	win.open();
}

//获取免费license
function freeLicense(){
	window.open(ctx + '/common/license/licMgt!generateLicenseFile.do?licNo=freeLicense');
}