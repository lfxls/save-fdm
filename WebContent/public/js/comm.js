var MSBar3D = ctx + "/public/charts/MSBar3D.swf"; //top10横向3d柱状图
var MSBar2D = ctx + "/public/charts/MSBar2D.swf"; //top10横向2d柱状图
var StBar2D = ctx + "/public/charts/StackedColumn2D.swf"; //堆栈2d柱状图
var StBar3DLineDY = ctx + "/public/charts/StackedColumn3DLineDY.swf"; //堆栈3d柱状图
var MSCombi2D = ctx + "/public/charts/MSCombi2D.swf"; //多效果柱状图
var Pie3D = ctx + "/public/charts/Pie3D.swf"; //3D饼图
var Pie2D = ctx + "/public/charts/Pie2D.swf"; //2D饼图
var MSLine = ctx + "/public/charts/MSLine.swf"; //多条曲线图
var Line = ctx + "/public/charts/Line.swf";//单条曲线
var MSColumn3D = ctx + "/public/charts/MSColumn3D.swf"; //纵向3d柱状图
var MSColumn2D = ctx + "/public/charts/MSColumn2D.swf"; //纵向2d柱状图
var LinearGauges = ctx + "/public/charts/HLinearGauge.swf";//线型横向指针图
var AngularGauges = ctx + "/public/charts/AngularGauge.swf";//钟表型指针图
var VLED = ctx + "/public/charts/VLED.swf"; //纵向LED图
var ScrollCombi2D = ctx + "/public/charts/ScrollCombi2D.swf"; //具有滚动条多效果柱状图
var StackedColumn3D = ctx + "/public/charts/StackedColumn3D.swf";
var StackedColumn2D = ctx + "/public/charts/StackedColumn2D.swf";

//组合图表,曲线和柱状图
var MSColumnLineDY2D = ctx + "/public/fusionchart3/MSColumn2DLineDY.swf";
var MSColumnLineDY3D = ctx + "/public/fusionchart3/MSColumn3DLineDY.swf";
function errh(msg, exc) {
	msg = trim(msg);
	//临时处理dwr产生failed to read input错误的办法 2012-11-20 jun
	if (msg.indexOf('Failed to read input')!=-1 || msg.indexOf('0x80040111')!=-1) {
		return;
	}
	//中文转码
	msg = $En(msg);
	var url = String.format(ctx + "/error!initError.do?stack={0}",msg);
	if(msg == 'nosession') {
		url = ctx + "/error!initNoSession.do";
		//会话失效，单独处理
		top.window.location = url;
		return;
	} else if(msg == 'noright') {
		url = ctx + "/error!initNoRight.do";
	}
	
	var baseParam = {
		url : url,
		title : common_ERROR_TITLE,
		el : 'errorWin',
		width : 800,
		height : 410,
		draggable : true
	};
	addWin = new top.Wg.window(baseParam);
	addWin.open();
	//top.window.location = url;
}
DWREngine.setErrorHandler(errh);

function $(e) {
	if (typeof(Ext.getDom(e)) != 'undefined')//typeof的运算数未定义,返回的就是 "undefined". 

	{
		return Ext.getDom(e);
	} else {
		return false;
	}
}

function $F(e){
	var v = dwr.util.getValue(e);
	if ($E(v)) {
		return '';
	} else {
		return trim(v);
	}
}

function $E(v){
	return Ext.isEmpty(v);
}

function $FF(e) {
	return dwr.util.getFormValues(e);
}

function $En(v) {
	return encodeURIComponent(v);
}

// 键盘上点击退格键与删除键无效
// 在各自页面body里加onkeydown="keyDown()"
function keyDown(){
	if(window.event.keyCode == 8 || window.event.keyCode == 46){
		event.keyCode = 0;
		event.returnvalue = false;
	}
}

//左边树加入群组
function addGroup(nodeId,doType) {
	var p = {doType : doType};
	if(nodeId && doType == 'right') { //从右边节点加入
		Ext.apply(p,{zdjhs:nodeId});
	}
	Wg.showLoading();
	dwrAction.doAjax('leftTreeAction','initGroup', p, function(res){
		var url = '<s:url action="common" namespace="/" method="initQz"/>';
		var baseParam = {
			url:url,
			title:' ',
			el:'group',
			width:720,
			height:550,
			draggable:true
		};
		Wg.removeLoading();
		var GroupWin = new Wg.window(baseParam);
		GroupWin.open();
	});
}

function showLeft() {
	if(top.Ext.getCmp('ws'))
	top.Ext.getCmp('ws').expand(true);
}

function hideLeft() {
	if(top.Ext.getCmp('ws'))
	top.Ext.getCmp('ws').collapse(true);
}

var simpleGrid = '';
//通讯控制类公用Js
window.onunload = function() {
	if (!Ext.isEmpty(simpleGrid)){
		if(!Ext.isEmpty(simpleGrid.fileUrl)) {
			simpleGrid.delFile();
		}
	}
	
};

/**
 * 节点过滤
 * @param _node
 * @return
 */
function nodeFilter(_node) {
	var nodeId = _node.id;
	var nodeType = _node.attributes.ndType;
	if (nodeType == 'qzRoot') {
		return false;
	}
	return true;
};

/**
 * 各页面自定义此方法(过滤节点)
 * @return
 */
function checkNode(_node) {
	if(handlerType == 'control'){
 		if(_node.attributes.yhlx == '03' && _node.attributes.ndType == 'bj'){
			Wg.alert(sbNotSel);
			return false;
		}
		if ($('yhlx')) {
			$('yhlx').value = _node.attributes.yhlx;
			// 显示台区编码，名称，和安装地址
			if($('yhlx').value == '03'){
				if(simpleGrid){
					simpleGrid.hideCol(3);
					simpleGrid.hideCol(4);
					simpleGrid.showCol(5);
					simpleGrid.showCol(6);
					simpleGrid.showCol(7);
				}
			// 显示户号，户名
			}else{
				if(simpleGrid){
					simpleGrid.showCol(3);
					simpleGrid.showCol(4);
					simpleGrid.hideCol(5);
					simpleGrid.hideCol(6);
					simpleGrid.hideCol(7);
				}
			}
		}
	}
	return true;
}

/**
 * 左边树点击响应
 * @param _node
 * @param _e
 * @return
 */
var handlerType = '';
function treeHandler(_node, _e) {
	if ($E(handlerType)) {
		return;
	} 
	if (handlerType == 'query') {
		var nodeType = _node.attributes.ndType;
		if (nodeType == 'cx') {
			return false;
		}
		treeHandlerQuery(_node, _e);
	} else if (handlerType == 'control') {
		treeHandlerControl(_node, _e);
	}
}
/**
 * 左边树点击 查询类响应
 * @param _node
 * @param _e 
 * @return
 */
function treeHandlerQuery(_node, _e) {
	//限制节点
	if (!nodeFilter(_node) || !checkNode(_node)) {
		return;
	}
	
	var _yhlx = _node.attributes.yhlx;
	var _nodeType = _node.attributes.ndType;
	var _nodeId = _node.id;
	var _nodeDwdm = _node.attributes.dwdm;
	var _nodeText = _node.attributes.text;
	var _viewType = _node.attributes.viewType;
	var _nodeHh = _node.attributes.hh;

	if (_nodeText.indexOf('(') > -1) { //去掉有些节点的'('
		_nodeText = _nodeText.substring(0, _nodeText.indexOf('('));
	}

	if ($('nodeId')) {
		$('nodeId').value = _nodeId;
	}
	if ($('nodeType')) {
		$('nodeType').value = _nodeType;
	}
	if ($('nodeDwdm')) {
		$('nodeDwdm').value = _nodeDwdm;
	}
	if ($('nodeText')) {
		$('nodeText').value = _nodeText;
	}
	if ($('nodeHh')) {
		$('nodeHh').value = _nodeHh;
	}
	// 用户类型（专，公，低压）和展示类型（设备或者表计）
	if ($('yhlx') && !$('zgbYhlx')) {
		//$('yhlx').value = _yhlx; 
	}
	if ($('viewType')) {
		$('viewType').value = _viewType;
	}
};

/**
 * 左边树点击 控制类响应
 * @param _node
 * @param _e 
 * @return
 */
function treeHandlerControl(_node, _e) {
	//限制节点
	if (!nodeFilter(_node) || !checkNode(_node)) {
		return;
	}
	getUserList(_node);
};
/**
 * 根据节点类型，节点id查找相关树信息
 * @return
 */
function getUserList(node) {
	var _nodeType = node.attributes.ndType;
	var _nodeId = node.id;
	var _nodeDwdm = node.attributes.dwdm;
	var _yhlx = node.attributes.yhlx;
	var param = {
		nodeId : _nodeId,
		nodeType : _nodeType,
		nodeDwdm : _nodeDwdm,
		yhlx:_yhlx
	};
	if (simpleGrid) {
		// 把左边的用户类型赋予群组分类
		simpleGrid.qzfl = _yhlx;
		//true刷新缓存数据
		simpleGrid.addRecord(param,true);
	}
}

/***左边树右键响应**/
function treeRightHandler() {
	if (top.leftFrame.leftTree && top.leftFrame.leftTree.getSelectNode()) {
		var _node = top.leftFrame.leftTree.getSelectNode();
		if (_node.attributes.ndType == 'sb')
			treeHandler(_node);
	}
}

//自动隐藏菜单
function hideMenu() {
	var menu = '';
	if(typeof(simpleMenu) != "undefined" ) {
		menu = top.Ext.menu.MenuMgr;  
		return;
	}
	if(typeof(parent.simpleMenu) != "undefined") {
		menu = top.Ext.menu.MenuMgr;  
	}
	if(menu)
	menu.hideAll();
}

var doc = Ext.getDoc();
doc.on('click',hideMenu);

/**
 * 根据节点类型，节点id查找相关树信息
 * @return
 */
function getZdjhArrayByParentId(node, yhlx, _cover, reloadFun){
    var nodeType= node.attributes.ndType;
	var nodeId = node.id;
	var param = {
		nodeType:nodeType,
		nodeId:nodeId,
		yhlx:yhlx,
        src:'getZdjhArrayByParentId'
	};
	if(nodeType == 'query'){
		Ext.apply(param,node.attributes.queryText);
	};
	var url = GetZdjhUrl;
	Wg.ajax(param,url,function(res){
		var zdjhArray = res.responseText;
		var doFileParam = {
			doType : 'add',
		    zdjhArray : zdjhArray,
            src:'getZdjhArrayByParentId',
		    cover: _cover,
		    yhlx : yhlx
		};
		dwr.util.setValue('yhlx',yhlx);
		//事件屏蔽
		if(Ext.query('input[name="gylx"]').length>0){
			if(dwr.util.getValue('gylx'))
			  doFileParam.csxbm='8033';
            else
              doFileParam.csxbm='04F009';
		}
		//终端控制
		if($('zdkz')){
			doFileParam.type="zdkz";
		}
		//有序用电方案
		if($('type')) {
		    doFileParam.type = $F("type");
		}
        if($('faid')) {
            doFileParam.faid = $F("faid");
        }
        if($('faly')) {
            doFileParam.faly = $F("faly");
        }
        if(controlType == 'sdk') {
        	doFileParam.zdgylx = $F("zdgylx");
        }
        if(controlType == 'zzrw' && $("gylx")) {
        	doFileParam.gylx = $F("gylx");
        }
		simpleGrid.qzfl = yhlx;
		// 此方法为extGrid.js里自定义的方法
		simpleGrid.doFile(doFileParam,function(){simpleGrid.reload({});if(reloadFun) {reloadFun();}});	
	},true,nodeType == 'dw');
}

/**
 * 切换用户类型
 */
function changeYhlx() {
	hideCol();
	var node = top.leftFrame.leftTree.getSelectNode();
	var zdyt=dwr.util.getValue('yhlx');
	if(node && node.attributes.ndType == 'tq' && zdyt == '01'){
		if(simpleGrid) {
			simpleGrid.clearRecord();
		}
		return;
	}
	if('undefined' != typeof(node)) {
		//getZdjhArrayByParentId(node, zdyt, true);
	}
}
/**
 * 隐藏召测，下发结果列 页面重写
 */
function hideCol(){
	
}

/** ** 弹出输入框 **** */
function validPw(method) {
    var msgw, msgh, bordercolor;
    msgw = 300; // 提示窗口的宽度
    msgh = 100; // 提示窗口的高度
    titleheight = 25; // 提示窗口标题高度
    bordercolor = "#04706F"; // 提示窗口的边框颜色
    titlecolor = "#949596"; // 提示窗口的标题颜色

    var sWidth, sHeight;
    sWidth = document.body.offsetWidth;
    sHeight = Ext.getBody().getHeight();
    var bgObj = document.createElement("div");
    bgObj.setAttribute('id', 'bgDiv');
    bgObj.style.position = "absolute";
    bgObj.style.top = "0";
    bgObj.style.background = "#777";
    bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=2,opacity=25,finishOpacity=75";
    bgObj.style.opacity = "0.6";
    bgObj.style.left = "0";
    bgObj.style.width = sWidth + "px";
    bgObj.style.height = sHeight + "px";
    bgObj.style.zIndex = "10000";
    document.body.appendChild(bgObj);

    var fixIframe = document.createElement("iframe");
    fixIframe.style.position = "absolute";
    fixIframe.style.zIndex = "-1";
    fixIframe.style.width = "100%";
    fixIframe.style.height = "100%";
    fixIframe.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=0)";
    document.getElementById("bgDiv").appendChild(fixIframe);

    var msgObj = document.createElement("div");
    msgObj.setAttribute("id", "msgDiv");
    msgObj.setAttribute("align", "center");
    msgObj.style.background = "white";
    msgObj.style.border = "1px solid " + bordercolor;
    msgObj.style.position = "absolute";
    msgObj.style.left = "50%";
    msgObj.style.top = "50%";
    msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
    msgObj.style.marginLeft = "-150px";
    msgObj.style.marginTop = -75 + document.documentElement.scrollTop + "px";
    msgObj.style.width = msgw + "px";
    msgObj.style.height = msgh + "px";
    msgObj.style.textAlign = "center";
    msgObj.style.lineHeight = "25px";
    msgObj.style.zIndex = "10001";

    var title = document.createElement("h4");
    title.setAttribute("id", "msgTitle");
    title.setAttribute("align", "right");
    title.style.margin = "0";
    title.style.padding = "3px";
    title.style.background = bordercolor;
    title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
    title.style.opacity = "0.75";
    title.style.border = "1px solid " + bordercolor;
    title.style.height = "18px";
    title.style.font = "12px Verdana, Geneva, Arial, Helvetica, sans-serif";
    title.style.color = "white";
    title.style.cursor = "pointer";
    title.innerHTML = comm_title_innerHTML;
    title.onclick = function() {
        document.body.removeChild(bgObj);
        document.getElementById("msgDiv").removeChild(title);
        document.body.removeChild(msgObj);
    };
    document.body.appendChild(msgObj);
    document.getElementById("msgDiv").appendChild(title);
    var txt = document.createElement("p");
    txt.style.margin = "1em 0";
    txt.setAttribute("id", "msgTxt");
    // 根据传入参数选择弹出框内容
    if(method == 'setup') {
        txt.innerHTML = document.all.setupForPwVerify.innerHTML;
    }
    else if(method == 'plunge') {
        txt.innerHTML = document.all.plungeForPwVerify.innerHTML;
    }
    else if(method == 'relieve') {
        txt.innerHTML = document.all.relieveForPwVerify.innerHTML;
    }
    else if(method == 'touru') {
        txt.innerHTML = document.all.touru.innerHTML;
    }
    else if(method == 'cxtouru') {
        txt.innerHTML = document.all.cxtouru.innerHTML;
    }
    else if(method == 'ht_touru') {
        txt.innerHTML = document.all.ht_touru.innerHTML;
    }
    else if(method == 'jiechu') {
        txt.innerHTML = document.all.jiechu.innerHTML;
    }
    else if(method == 'jiechuForGk') {
        txt.innerHTML = document.all.jiechuforgk.innerHTML;
    }
    else if(method == 'ht_jiechu') {
        txt.innerHTML = document.all.ht_jiechu.innerHTML;
    }
    else if(method == 'lazha') {
        txt.innerHTML = document.all.lazha.innerHTML;
    }
    else if(method == 'hezha') {
        txt.innerHTML = document.all.hezha.innerHTML;
    }
    else if(method == 'yaoxin') {
        txt.innerHTML = document.all.yaoxin.innerHTML;
    }
    else if(method == 'touruForCs') {
        txt.innerHTML = document.all.touruforcs.innerHTML;
    }
    else if(method == 'touruForKz') {
        txt.innerHTML = document.all.touruforkz.innerHTML;
    }
    else if(method == 'touruForGk') {
        txt.innerHTML = document.all.touruforgk.innerHTML;
    }
    else if(method == 'touruForCsFg') {
        txt.innerHTML = document.all.touruforcsfg.innerHTML;
    }
    else if(method == 'touruForCsByGwFlsd') {
        txt.innerHTML = document.all.touruforcsbygwflsd.innerHTML;
    }
    document.getElementById("msgDiv").appendChild(txt);
    // 返回值
    var result = "";
    return result;
}

/** 隐藏弹出层 **/
function hide() {
    bgObj = document.getElementById("bgDiv");
    title = document.getElementById("msgTitle");
    msgObj = document.getElementById("msgDiv");
    document.body.removeChild(bgObj);
    document.getElementById("msgDiv").removeChild(title);
    document.body.removeChild(msgObj);
}

/**
 * 显示单位完整路径
 * @param dwdm
 */
function openDwWin(dwdm) {
	var url = ctx+'/common!getDwPath.do?dwdm='+dwdm;
	var baseParam = {
		url : url,
		title : common_DWPathWin_TITLE,
		el : 'dwPathWin',
		width :600,
		height : 150,
		draggable : true
	};
	simpleWin = new Wg.window(baseParam);
	simpleWin.open();
}
/**
 * 给定一个伊朗历月份，获得该月份的公历起至日期
 * @param iranMonth
 * @returns {monthStart:,monthEnd:}
 */
function getIranMonthStartEnd(iranMonth){  //伊朗历月份，得到公历日期起至日期
	var sd_separator = "-";
	var startPersianDay = iranMonth.substring(0,7)+sd_separator + "01";
	var pDate = P$dp.utils.parsePersianDate(iranMonth.substring(0,7),"yyyy-MM");
	var month = pDate.month;
	var year = pDate.year;
	var nextYear = (month+1>12)?(year+1):year;
	var nextMonth = (month+1>12)?1:(month+1);
	if (nextMonth<10)nextMonth = "0" + nextMonth;
	var endPersianDay = nextYear +sd_separator+nextMonth+sd_separator +"01";
	var res = {};
	res.monthStart = P$dp.utils.persianToGregorian(startPersianDay);
	res.monthEnd = P$dp.utils.persianToGregorian(endPersianDay);
	return res;
}
/**
 * 给定一个公历月份，获得该月份的公历起至日期
 * @param iranMonth
 * @returns {monthStart:,monthEnd:}
 */
function getMonthStartEnd(month){ //公历月份，得到公历日期起至日期
	var startDay = month.substring(0,7)+sd_separator + "01";
	var startDate = P$dp.utils.parseDate(startDay , "yyyy-MM-dd");
	startDate.setMonth(startDate.getMonth()+1);
	var endDay = P$dp.utils.formatDate(startDate , "yyyy-MM-dd");
	var res = {};
	res.monthStart = startDay;
	res.monthEnd = endDay;
	return res;
}


/**
 * 查询测量点信息
 * @param zdjh 终端局号
 */
function queryCld(zdjh) {
	var url = String.format(ctx + '/basic/dagl/zdgl!initCldcx.do?zdjh={0}',zdjh);
	var baseParam = {
		url : url,
		title : common_ZDCLDWin_TITLE,
		el : "cldcx",
		width : 650,
		height : 420,
		draggable : true
	};
	simpleWin = new Wg.window(baseParam);
	simpleWin.open();
}

//查询用户下的表计
function queryYh(hh,yhlx) {
	var url = String.format(ctx + "/basic/dagl/bjgl!queryYh.do?hh={0}&yhlx={1}",hh,yhlx);
	var baseParam = {
		url : url,
		title : common_YhbjWin_title,
		el : "yh",
		width : 760,
		height : 430,
		draggable : true
	};
	simpleWin = new Wg.window(baseParam);
	simpleWin.open();
}
//单设备档案查询
function queryZdda(zdjh) {
	var url = String.format(
		ctx + '/query/dsbcx/sbdacx!init.do?nodeId={0}',zdjh);
	top.openpageOnTree(common_dsbda_cx_title,'42100',common_dsbda_cx_title,null,url,'yes',1);
}

//单设备档案查询
function queryZdda(zdjh, azdz) {
	var url = String.format(
		ctx + '/query/dsbcx/sbdacx!init.do?nodeId={0}&nodeText={1}',zdjh, azdz);
	top.openpageOnTree(common_dsbda_cx_title,'42100',common_dsbda_cx_title,null,url,'yes',1);
}

//加载国际化资源文件
function loadProperties(moduleName, fileName){
	jQuery.i18n.properties({
	    name : fileName, //资源文件名称
	    path : ctx + '/js/locale/' + moduleName + '/', //资源文件路径
	    mode : 'both', //用Map的方式使用资源文件中的值
	    language : ctxLang,
	    callback : function() {//加载成功后设置显示内容
	    }
	});
}

/**
 * 根据树类型获取树
 * @param treeType 树类型：dw=单位树
 * @param treeTitle 树显示窗口的标题
 * @param rootId 根节点ID
 * @param rootText 根节点名称
 * @param rootType 根节点类型
 */
function getTree(treeType, treeTitle, rootId, rootText, rootType) {
	if($E(treeType) || $E(treeTitle) || $E(rootId) || $E(rootText) || $E(rootType))
		return;
	var url = String.format(ctx + '/util!initTree.do?nodeId={0}&nodeText={1}&nodeType={2}&treeType={3}', rootId, rootText, rootType, treeType);
	var baseParam = {
		url : url,
		title : treeTitle,
		el : 'treeDiv',
		width : 290,
		height : 330,
		draggable : true
	};
	
	treeWin = new Wg.window(baseParam);
	treeWin.open();
}

/**
 * 获取变压器弹出选择窗口
 * @param nodeIddw 单位
 */
function selectTransf(nodeIddw) {
	var url = String.format(ctx + '/main/arcMgt/transfMgt!initTransfSel.do?nodeIddw=' + nodeIddw);
	var baseParam = {
		url : url,
		title : common.kw.transformer.title,
		el : 'selectTransf',
		width : 680,
		height : 420,
		draggable : true
	};
	TransformerWin = new Wg.window(baseParam);
	TransformerWin.open();
}

/**
 * 获取安装队弹出选择窗口
 */
function selectInsteam() {
	var url = String.format(ctx + '/system/qyjggl/insteamMgt!initInsteamSel.do');
	var baseParam = {
		url : url,
		title : common.kw.insteam.title,
		el : 'selectInsteam',
		width : 520,
		height : 410,
		draggable : true
	};
	InsteamWin = new Wg.window(baseParam);
	InsteamWin.open();
}

/**
 * 获取SIM卡弹出选择窗口
 */
function selectSim() {
	var url = String.format(ctx + '/main/arcMgt/simMgt!initSimSel.do');
	var baseParam = {
		url : url,
		title : common.kw.sim.title,
		el : 'selectSim',
		width : 520,
		height : 410,
		draggable : true
	};
	SimWin = new Wg.window(baseParam);
	SimWin.open();
}



/**
 * 查询工单
 * @param woid
 */
function queryWO(woid){
	var url = String.format(ctx + '/main/insMgt/orderQuery!init.do?woid={0}',woid);
	top.openpageOnTree(common.kw.workorder.title, '126000', common.kw.workorder.title, null, url, 'yes', 1);
}

/**
 * 查询安装计划
 * @param pid
 */
function queryPlan(pid){
	var url = String.format(ctx + '/main/insMgt/planQuery!init.do?pid={0}',pid);
	top.openpageOnTree(common.kw.plan.title, '127000', common.kw.plan.title, null, url, 'yes', 1);
}


/**
 * 对象字符串转换
 * @param obj
 * @returns {String}
 */
function getValue(obj){
	if (obj == null || obj == undefined)
		return "";
	else
		return obj;
}