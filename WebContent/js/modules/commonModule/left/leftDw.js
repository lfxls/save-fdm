var availH = top.Ext.getBody().getHeight() - 76;
var h1 = (availH-31);//左侧树的总高度440
var h2 = (availH-230); // 查询树
var h4 = (availH-102);// 群组树
var w1 = 279;
var _URL = ctx + '/leftTree!getViews.do';
var leftTree = '';
var changeTreeUtl = ctx + '/leftTree!init.do';

Ext.onReady(function(){
	var h = h1;
	// 低压情况下多展示个台区查询条件
	if($F('viewType') == 'cx'){
		if($F('type') == 'sb'){
			//$('hhtr').style.display = 'inline';
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
//			$('zdjhtr').style.display = '';
			$('zdljdztr').style.display = 'none';
			$('hmtr').style.display = '';
			$('bjjhtr').style.display = '';
			$('bjhhtr').style.display = 'none';
			$('bjtxdztr').style.display = 'none';
			$('bjjh').value = '';
			$('bjtxdz').value = '';
		}
	}
	
	if($F('viewType') == 'qz'){
		h = h4;
	}
	if ($F('viewType')=='cx') {
		h = h2;
	}
	var initViewType = $F('viewType');
	if($F('viewType') == 'qz'){
		initViewType = $F('type') + $F('viewType');
	}
	leftTree = new Wg.PagingTree({url:_URL,el:'tree',limit:30,height: h, width: w1,rootVisble:false});
	//leftTree.init('root','root',initViewType,$F('yhlx'));
	leftTree.init('root','root',initViewType,'');
	leftTree.tree.addListener("click",handler);
	//leftTree.tree.on('contextmenu', contextmenu);
});

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
	}else{
		activeFrame = top.$('homeFrame_'+tabIndex);
		if(!$E(activeFrame)){
			var win = activeFrame.contentWindow;
			if(!$E(win)) {
				if(!$E(win.treeHandler))
				win.treeHandler(_node, _e);
			}
		}
	}
	return;
}

//切换试图
function changeViews(viewType) {
	var url = ctx + '/leftTree!init.do?viewType='
			+ viewType + '&yhlx=' + $F('yhlx');
	window.location = url;
}

//切换用户类型
function changeTree(type) {
	var url =  changeTreeUtl + '?viewType=' + type + '&yhlx=' + $F('yhlx');
	window.location = url;
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

		treeMenu.removeAll();
		if (nodeType == 'sb') { //设备
			for ( var i = 0; i < zgbsbMenuItems.length; i++) {
				treeMenu.addItem(zgbsbMenuItems[i]);
			}
			
			treeMenu.addItem(sbMenuItems);
		} else if (nodeType == 'bj') { //用户
			treeMenu.addItem(bjMenuItems);
		}else if(nodeType!='bjqz'){
			//表计群组不支持在线监控
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
		id : 'cbsjcx',
		text :dwMenu_cbsjcx,
		handler : menuHandler
	},{
		id : 'lhzsjcx',
		text : dwMenu_lhzsjcx,
		handler : menuHandler
	}];
/*,{
		id : 'fhsjcx',
		text : dwMenu_fhsjcx,
		handler : menuHandler
},{
	id : 'ssgk',
	text : dwMenu_ssgk,
	handler : menuHandler
}, {
	id : 'dlsjcx',
	text : dwMenu_dlsjcx,
	handler : menuHandler
} ,{
	id : 'zxjk',
	text : dwMenu_zxqk,
	handler : menuHandler
} */

//单户设备
var zgbsbMenuItems = [ {
	id : 'cssz',
	text : zgbsbMenu_cssz,
	handler : menuHandler
}, {
	id : 'rwsz',
	text : zgbsbMenu_rwsz,
	handler : menuHandler
}, {
	id : 'sjzc',
	text : zgbsbMenu_sjzc,
	handler : menuHandler
}, {
	id : 'bwcx',
	text : zgbsbMenu_bwcx,
	handler : menuHandler
}];

//设备
var sbMenuItems = [ {
	id : 'qzsz',
	text : qzMenu_jrqz,
	handler : menuHandler
},{
	id : 'yhdacx',
	text : qzMenu_dacx,
	handler : menuHandler
}];

//表计
var bjMenuItems = [ {
	id : 'qzsz',
	text : qzMenu_jrqz,
	handler : menuHandler
},{
	id : 'yhdacx',
	text : qzMenu_dacx,
	handler : menuHandler
}];
/*,{
	id : 'lhzsjcx_dh',
	text : dwMenu_lhzsjcx,
	handler : menuHandler
},{
	id : 'dlsjcx_dh',
	text : bj_dlsjcx_dh,
	handler : menuHandler
},{
	id : 'jssjcx_dh',
	text : bj_jssjcx_dh,
	handler : menuHandler
},{
	id : 'cbsjcx_dh',
	text : bj_cbsjcx_dh,
	handler : menuHandler
},{
	id : 'fhsjcx_dh',
	text : dwMenu_fhsjcx,
	handler : menuHandler
}*/


function menuHandler(item) {
	if (!selectedNode)
		return;
	var nodeId = selectedNode.id;
	var nodeText = encodeURIComponent(selectedNode.text);
	if (nodeText.indexOf('(') > -1) { //去掉有些节点的'('
		nodeText = nodeText.substring(0, nodeText.indexOf('('));
	}
	var nodeType = selectedNode.attributes.ndType;
	var yhlx = selectedNode.attributes.yhlx;
	var zdljdz = selectedNode.attributes.zdljdz;
	var dwdm = selectedNode.attributes.dwdm;
	var zdgylx = selectedNode.attributes.zdgylx;
	var hh = selectedNode.attributes.hh;
	var url = '';
	var actionName = '';
	var text="";
	var menuId = "";
	if (item.id == 'cssz') { //参数设置
		text = zgbsbMenu_cssz;
		menuId = "14100";
		actionName = '/basic/csgl/zdcssz';
		url = ctx + '/basic/csgl/zdcssz!init.do';
	} else if (item.id == 'rwsz') { //任务设置
		text = zgbsbMenu_rwsz;
		menuId = "15100";
		actionName = '/basic/rwgl/zdrwgl';
		url = ctx + '/basic/rwgl/zdrwgl!init.do';
	} else if (item.id == 'sjzc') { //数据召测
		text = zgbsbMenu_sjzc;
		menuId = "16100";
		actionName = '/basic/sjzc/sssjzc';
		url = ctx + '/basic/sjzc/sssjzc!init.do';
	} else if (item.id == 'bwcx') { //报文查询
		text = zgbsbMenu_bwcx;
		menuId = "17100";
		actionName = '/basic/bwcx/bwcx';
		url = String.format(ctx + '/basic/bwcx/bwcx!init.do?zdljdz={0}&nodeText={1}&zdgylx={2}',nodeId,nodeText,zdgylx);
	} else if (item.id == 'qzsz') { // 群组操作
		var qzfl = '';
		var qzlx = $F('type');
		if(qzlx==''){
			qzlx = $F('viewType');
		}
		top.addGroup(nodeId, $F('yhlx'),qzlx);
		return;
	} else if(item.id == 'yhdacx') {
		text = menuHandler_yhdacx;
		menuId = "41100";
		actionName = '/query/dhcx/yhdacx';
		url = String.format(ctx + '/query/dhcx/yhdacx!init.do?hh={0}&hm={1}&yhlx={2}',hh,nodeText,yhlx);
	} else if(item.id == 'yhsjcx') {
		text = menuHandler_yhsjcx;
		menuId = "41200";
		actionName = '/query/dhcx/cbsj';
		url = String.format(ctx + '/query/dhcx/cbsj!init.do?hh={0}&hm={1}&yhlx={2}&nodeDwdm={3}',hh,nodeText,yhlx,dwdm);
	}else if(item.id == 'ssgk'){
		//抄表成功率
		text = dwMenu_ssgk;
		menuId = "21100";
		actionName = '/adv/cbgl/cbcgl';
		url = String.format(ctx + '/adv/cbgl/cbcgl!init.do?dwdm={0}',dwdm);
	}else if(item.id == 'cbsjcx'){
		//抄表数据查询
		text = dwMenu_cbsjcx;
		menuId = "21200";
		actionName = '/adv/cbgl/cbsjcx';
		url = String.format(ctx + '/adv/cbgl/cbsjcx!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	}else if(item.id == 'dlsjcx'){
		//电量消费查询
		text = dwMenu_dlsjcx;
		menuId = "21300";
		actionName = '/adv/cbgl/dlsjcx';
		url = String.format(ctx + '/adv/cbgl/dlsjcx!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	}else if(item.id == 'cbsjcx_dh'){
		//抄表数据查询-单户
		text = bj_cbsjcx_dh;
		menuId = "41200";
		actionName = '/query/dhcx/cbsj';
		url = String.format(ctx + '/query/dhcx/cbsj!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	}else if(item.id == 'dlsjcx_dh'){
		//电量数据查询-单户
		text = bj_dlsjcx_dh;
		menuId = "41300";
		actionName = '/query/dhcx/dlsj';
		url = String.format(ctx + '/query/dhcx/dlsj!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	}else if(item.id == 'jssjcx_dh'){
		//结算数据查询-单户
		text = bj_jssjcx_dh;
		menuId = "41500";
		actionName = '/query/dhcx/yhsjcx';
		url = String.format(ctx + '/query/dhcx/yhsjcx!initJssj.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	}else if(item.id == 'zxjk'){
		//结算数据查询-单户
		text = dwMenu_zxqk;
		menuId = "31400";
		actionName = '/run/cjsbjk/sbzxjk';
		url = String.format(ctx + '/run/cjsbjk/sbzxjk!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',nodeId,dwdm,nodeText,nodeType);
	} else if(item.id =='fhsjcx'){
		//负荷数据查询
		text = dwMenu_fhsjcx;
		menuId = "21600";
		actionName = '/adv/cbgl/fhsjcx';
		url = String.format(ctx+ '/adv/cbgl/fhsjcx!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',
								nodeId, dwdm, nodeText, nodeType);
	} else if(item.id =='lhzsjcx'){
		//拉合闸数据查询
		text = dwMenu_lhzsjcx;
		menuId = "19210"; 
		actionName = '/adv/cbgl/lhzcx'; 
		url = String.format(ctx+ '/adv/cbgl/lhzcx!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',
								nodeId, dwdm, nodeText, nodeType);
		
	} else if(item.id == 'fhsjcx_dh'){
		//负荷数据查询_单表
		text = dwMenu_fhsjcx;
		menuId = "41400";
		actionName = '/query/dhcx/fhsj'; 
		url = String.format(ctx+ '/query/dhcx/fhsj!init.do?nodeId={0}&nodeDwdm={1}&nodeText={2}&nodeType={3}',
								nodeId, dwdm, nodeText, nodeType);
		
	} else if(item.id == 'lhzsjcx_dh'){
		//拉合闸数据查询_单表
		alert('Please waite for a moment, developing...');
		return;
		
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


//加入群组
function addGroup() {
	var queryRoot = leftTree.tree.getNodeById('queryRoot');
	
	if(queryRoot.hasChildNodes() && queryRoot.text.indexOf(query_result) > -1) {
		top.addGroup('',$F('yhlx'),$F('type'));
	} else {
		top.Wg.alert(addGroup_alert);
		return;
	}
}	

//高级查询弹出
function advQuery() {
	$('sbid').value ='';
	top.advQuery($F('type')); 
}
//高级查询
function getAdvQuery() {
	var queryRoot = leftTree.tree.getNodeById('queryRoot');
	queryRoot.setText(query_result);
	queryRoot.attributes.total = '';
	queryRoot.attributes.start = '';
	queryRoot.attributes.end = '';
	queryRoot.attributes.pages = true;
	queryRoot.reload();
	leftTree.tree.show();
}

function onkey() {
	if (window.event.keyCode==13) {
		query();
	}
}

// 查询树时设备类型变换
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

//群组树时展示类型变换
function changeQz(val){
	var node = leftTree.getRoot();
	node.attributes.viewType = val + "qz";
	node.reload();
	leftTree.tree.show();
}

// 弹出群组设置页面
function qzsz(){
	top.qzszQuery(); 
}
//弹出群组添加页面
function qzadd(){
	top.qzadd(); 
}