var availH = top.Ext.getBody().getHeight() - 110;
var h1 = (availH-40) *0.95;//左侧树的总高度440
var h2 = (availH-100) *0.92; // 查询树
var w1 = 295;
var _URL = ctx + '/leftTree!getViewSimple.do';
var leftTree = '';

Ext.onReady(function(){
	var h = h1;
	var inputsWithPlaceholder = Ext.select("input[placeholder]");
	// 低压情况下多展示个台区查询条件
	if($F('viewType') == 'cx'){
		if($F('type') == 'sb'){
			$('zdjhtr').style.display = '';
			$('zdljdztr').style.display = 'none';
			$('bjhhtr').style.display = 'none';
			$('hmtr').style.display = 'none';
			$('bjjhtr').style.display = 'none';
			$('bjtxdztr').style.display = 'none';
			
			$('bjjh').value = '';
			$('hm').value = '';
			$('bjtxdz').value = '';

		}else{
			$('hhtr').style.display = '';
			$('zdjhtr').style.display = '';
			$('zdljdztr').style.display = 'none';
			$('hmtr').style.display = '';
			$('bjjhtr').style.display = '';
			
			$('bjhhtr').style.display = 'none';
			$('bjtxdztr').style.display = 'none';
			
			$('bjjh').value = '';
			$('bjtxdz').value = '';

		}
	}
	
	if ($F('viewType')=='cx') {
		h = h2;
	}
	var initViewType = $F('viewType');
	leftTree = new Wg.PagingTree({url:_URL,el:'tree',limit:30,height: h1, width: w1,rootVisble:false});
	leftTree.init('root','root',initViewType,'');
	leftTree.tree.addListener("click",handler);
//	leftTree.tree.addListener("beforeexpandnode",function(node, deep, animal){
//		//alert('node---:'+node.id);
//	})
	leftTree.tree.on('contextmenu', contextmenu);
});

//查询树时设备类型变换
function changeZslx(){
	var type = $F('type');
	var queryFlag = false;
	
	var zdjh = $F('zdjh');
	var bjjh = $F('bjjh');
	var hh = $F('hh');
	var hm = $F('hm');
	
	if(type=='bj'){
		$('bjjhtr').style.display = '';
		$('hhtr').style.display = '';
		$('hmtr').style.display = '';
		if(zdjh!='' || bjjh!='' || hh!='' || hm !=''){
			queryFlag = true;
		}
		showPlaceholder();
	}else{
		$('bjjhtr').style.display = 'none';
		$('hhtr').style.display = 'none';
		$('hmtr').style.display = 'none';
		
		$('bjjh').value = '';
		$('hh').value = '';
		$('hm').value = '';

		if(zdjh!=''){
			queryFlag = true;
		}
	}
	//有查询条件才执行查询
	if(queryFlag){
		query();
	}
}
	
//切换试图
function changeViews(viewType) {
	var url = ctx + '/leftTree!initS.do?viewType='+ viewType;
	window.location = url;
}

//左边树响应
function handler(_node, _e) {
	var tabIndex = top.getCurrentTabIndex();
	var activeFrame = top.$('mainFrame'+tabIndex);
	if(!$E(activeFrame)){
		var win = activeFrame.contentWindow;
		if(!$E(win)) {
			if(!$E(win.treeHandler))
			win.treeHandler(_node, _e);
		}
	}
	return;
}

//右键菜单
var treeMenu = '';
var nodeType = '';

var selectedNode = null;
// 创建右键菜单
function contextmenu(node, e) {
	if (!treeMenu) {
		treeMenu = new Ext.menu.Menu({
			id : 'treeMenu'
		});
	}
	if (nodeType != node.attributes.ndType) {
		var zdyt = node.attributes.zdyt;
		var nodeType = node.attributes.ndType;
		var zdyt = node.attributes.zdyt;
		var _viewType = node.attributes.viewType;
		var zdlx = node.attributes.zdlx;
		treeMenu.removeAll();
		if (nodeType == 'sb') { //设备
			//虚拟终端 
			if(zdlx == '05'){ //GPRS表 
				treeMenu.addItem(allMenuItems);
			}else{ 
				treeMenu.addItem(zgbsbMenuItems);
			}
		} else if (nodeType == 'bj') { //表用户
			treeMenu.addItem(dhMenuItems);
		}else{
			//单位这块的菜单暂时去掉
			for ( var i = 0; i < dwMenuItems.length; i++) {
				treeMenu.addItem(dwMenuItems[i]);
			}
		}
		treeMenu.doLayout();
	}
	selectedNode = node;
	coords = e.getXY();
	if (node.id != '33101' && node.id != 'qzRoot' && node.id != 'queryRoot')
		treeMenu.showAt([ coords[0], coords[1] ]);
}

//批量查询
var dwMenuItems = [ {
	id : 'saleDailyReport',
	text : leftS_saleDailyReport,
	handler : menuHandler
},{
	id : 'saleMonthlyReport',
	text : leftS_saleMonthlyReport,
	handler : menuHandler
}];

//单户设备
var zgbsbMenuItems = [ {
	id : 'message',
	text : zgbsbMenu_bwcx,
	handler : menuHandler
}];

//单户
var dhMenuItems = [ {
	id : 'vending',
	text : leftS_vending,
	handler : menuHandler
},{
	id : 'reissue',
	text : leftS_reissue,
	handler : menuHandler
}, {
	id : 'vendingReport',
	text : leftS_vendingReport,
	handler : menuHandler
}];

//单户和单户设备
var allMenuItems = [ {
	id : 'message',
	text : zgbsbMenu_bwcx,
	handler : menuHandler
},{
	id : 'vending',
	text : leftS_vending,
	handler : menuHandler
},{
	id : 'reissue',
	text : leftS_reissue,
	handler : menuHandler
}, {
	id : 'vendingReport',
	text : leftS_vendingReport,
	handler : menuHandler
}];

function menuHandler(item) {
	if (!selectedNode)
		return;
	var nodeId = selectedNode.id;
	var nodeText = encodeURIComponent(selectedNode.text);
	var nodeType = selectedNode.attributes.ndType;
	var yhlx = selectedNode.attributes.yhlx;
	var zdljdz = selectedNode.attributes.zdljdz;
	var dwdm = selectedNode.attributes.dwdm;
	var zdgylx = selectedNode.attributes.zdgylx;
	var hh = selectedNode.attributes.hh;
	var hm = selectedNode.attributes.hm;
	var url = '';
	var actionName = '';
	var text="";
	var menuId = "";
	if (item.id == 'vending') { //日常售电
		text = leftS_vending;
		menuId = "62100";
		actionName = '/prepay/vending/dailyVending';
		url = String.format(ctx + '/prepay/vending/dailyVending!init.do?accountNo={0}',hh);
		hideLeft();
	} else if (item.id == 'reissue') { //数据补发
		text = leftS_reissue;
		menuId = "62200";
		actionName = 'prepay/vending/orderReissue';
		url = String.format(ctx + '/prepay/vending/orderReissue!init.do?accountNo={0}',hh);
		hideLeft();
	} else if (item.id == 'vendingReport') { //购电报表
		text = leftS_vendingReport;
		menuId = "66404";
		actionName = '/prepay/report/reportMgt';
		url = String.format(ctx + '/prepay/report/reportMgt!accDayDuraRecordInit.do?hh={0}',hh);
		hideLeft();
	} else if (item.id == 'message') { //报文查询
		text = zgbsbMenu_bwcx;
		menuId = "17100";
		actionName = '/basic/bwcx/bwcx';
		url = ctx + '/basic/bwcx/bwcx!init.do?zdljdz='+zdljdz+'&azdz='+hm;
	} else if (item.id == 'saleDailyReport') { // 单位销售日报表
		text = leftS_saleDailyReport;
		menuId = "66101";
		actionName = '/prepay/report/reportMgt';
		url = ctx + '/prepay/report/reportMgt!saleDayRecordInit.do';
		hideLeft();
	} else if(item.id == 'saleMonthlyReport') {//单位销售月报表
		text = leftS_saleMonthlyReport;
		menuId = "66102";
		actionName = '/prepay/report/reportMgt';
		url = ctx + '/prepay/report/reportMgt!saleMonthRecordInit.do';
		hideLeft();
	} 
	dwrAction.doAjax('leftTreeAction', 'getQxcd', {
		actionName : actionName
	}, function(res) {
		if (res.success) {
			top.openpageOnTree(text,menuId,text,'',url,'yes',1);
		} else {
			top.Wg.alert(menuHandler_norole);
			return;
		}
	});
	leftTree.selectedNode = selectedNode;
};

//查询
function query() {
	if(!$F('hh') && !$F('hm') && !$F('zdjh') && !$F('zdljdz') && !$F('tqmc') && !$F('bjjh')) {
		top.Wg.alert(query_alert);
		return;
	} else {
		$('sbid').value ='';
		var queryInfo ={
			dwdm:$('dwdm') ? $F('dwdm'): '',
			hh:$F('hh'),
			bjhh:$F('bjhh'),
			hm:$F('hm'),
			bjjh:$F('bjjh'),
			bjtxdz:$F('bjtxdz'),
			zdjh:$F('zdjh'),
			zdljdz:$F('zdljdz'),
			tqmc:$F('tqmc'),
			yhlx:$F('yhlx'),
			type:$F('type')
		};
		// 把参数放入session里，供左边树初始化用
		dwrAction.doAjax('leftTreeAction','setQueryInfo',queryInfo,function(res){
			if(res.success) {
				var queryRoot = leftTree.tree.getNodeById('queryRoot');
				queryRoot.setText(query_result);
				queryRoot.attributes.total = '';
				queryRoot.attributes.start = '';
				queryRoot.attributes.end = '';
				queryRoot.attributes.pages = true;
				queryRoot.reload();
				leftTree.tree.show();
			}
		});
	}
}

function onkey() {
	if (window.event.keyCode==13) {
		query();
	}
}