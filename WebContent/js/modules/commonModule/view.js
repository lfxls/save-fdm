 var simpleMenu = '';
 var pagesize = '30'; //grid默认分页pagesize
 var pwdTabId = "pwdTab01";
 var shortCutTabId = 'shortCutTab01';
 var defaultTabId="0";
 var mainPage=common_view_mainPage;
 var tip;
 var licenseTip;
 var viewport;
 Ext.onReady(function() {
 	 var syArray = [];
	 //组织首页信息
	 for(var i =0;i < syJson.length && syJson!='';i++){
		 var syItem = syJson[i];
		 var syUrl = syItem.url;
		 if(syUrl!='' && syUrl.substring(0,1)!='/'){
			 syUrl = ctx + "/"+syUrl;
		 }else{
			 syUrl = ctx + syUrl;
		 }
		 
		 var tabId = 'home_'+syItem.syid;
		 var frameId = "homeFrame_"+syItem.syid;
		 syArray[i] = {
		    id:tabId,
            title:syItem.symc,
            tabTip:syItem.symc,
            iconCls: 'tabs',
            html: '<iframe algin="right" src="'+syUrl+'" width="100%" height="100%" frameborder="0" id="'+frameId+'"  name="'+frameId+'"></iframe>',
            closable:false	 
		 };
	 }
	 
	 var collapseStatus = 1;
	 var maintab = new Ext.TabPanel({
     	 id:'defaulttab0',
         region:'center',
         deferredRender:false,
         activeTab:0,
         enableTabScroll: true,
         layoutOnTabChange:true,
         //height:250,
         plugins: new Ext.ux.TabCloseMenu(),
         items:syArray,
         initEvents : function(){   
        	 Ext.TabPanel.superclass.initEvents.call(this);   
        	 //双击收缩和打开左侧树
        	 this.mon(this.strip,'dblclick',this.onTitleDbClick,this);
        	 this.mon(this.strip, 'contextmenu', this.onStripContextMenu, this);   
        	 this.on('remove', this.onRemove, this, {target: this});  
        	 this.mon(this.strip, 'mousedown', this.onStripMouseDown, this); 
         }, 
         onTitleDbClick: function(e, target, o) {
        	 //通过双击可以收缩和打开左侧树
        	 if(collapseStatus==1){
        		 Ext.getCmp('ws').expand();
        		 collapseStatus = 0;
        	 }else{
        		 Ext.getCmp('ws').collapse();
        		 collapseStatus = 1;
        	 }
         }
	  });
	 
	 var main = new Ext.Panel({
		id:'main',
		region : "center",
		layout : 'border',
		margins : '-2 0 0 0',
		items : [maintab]
	 });
	 
	var border = new Ext.Panel({
		region:'center',
		layout: 'border',
		items:[{
			id:'top',
			region: 'north',
			//height : 90,//顶部菜单区域高度
			border : false,
			collapseFirst : true,
			margins: '0 0 2 0',
			contentEl: 'toptb'
		},{
			id:'ws',
			title: '',
			collapsible : true,
			split:false,//不能拖动左边布局
			animCollapse:false,
			region: 'west',
			margins: '-2 0 0 0',
			layout:'fit',
			width: 329,
            autoScroll: true,
			html:'<iframe src="" width="100%" height="100%" name="leftFrame" id="leftFrame" frameborder="no" scrolling="no" /></iframe>'
		},
		main]
	});
		
		viewport = new Ext.Viewport({
			layout:'border',
			items:[border]
		});
//		var _URL = ctx + '/view!getMenus.do';
//		if(simpleMenu){
//			simpleMenu.destroy(); 
//		}
//		simpleMenu = new Wg.menu({url:_URL,el:'menubar'}); //菜单
//		simpleMenu.init();
//		//默认加载第一个菜单的子菜单
//		simpleMenu.rebuild(firstMenuId);
//		
//		//默认第一个菜单选中
//		if(firstMenuId != ''){
//			document.getElementById(firstMenuId).className = 'ui-nav-item ui-nav-item-current';
//		}
		
	 	Ext.getCmp('ws').collapse(true);
	 	initLeft();
	 	
//	 	//告警提示
//	 	tip = new Ext.ToolTip({
//	        target: 'alarmTip',
//	        anchor: 'bottom',
//			autoHide: false,
//			closable: true,
//	        anchorOffset: 50, // center the anchor on the tooltip
//	        html: null,
//	        contentEl:'alermText'
//	    });
	 	
	    //license过期提示
	 	licenseTip = new Ext.ToolTip({
	        target: 'licenseTip',
	        anchor: 'bottom',
			autoHide: false,
			closable: true,
	        anchorOffset: 50, // center the anchor on the tooltip
	        html: null,
	        contentEl:'licenseText'
	    });
	 	
	 	Ext.QuickTips.init();
//	 	var sbgjFlag = $F('sbgjFlag');
//	 	if (sbgjFlag=="true") {
//	 		window.setInterval(showAlarm,$F('alermRefreshInterval')*1*1000);
//		}
	 	
	 	//显示license到期信息
//	 	if(licenseDays*1!=-1){
//	 		showLicense();
//	 	}
	 	
	 	//showTodo();
//	 	var sbgjFlag = $F('sbgjFlag');
//	 	if (sbgjFlag=="true") {
//	 	    setInterval("showTodo()", $F('alermRefreshInterval')* 1 * 1000);
//	 	}
 });
 
 /*------告警信息--------------*/
 function showAlarm(){
	 var param = {};
	 dwrAction.doAjax('viewAction', 'getAlarmInfo', param, function(res){
			if(res.success) {
				//tip.hide();
				var alarmNum = res.dataObject;
				if (alarmNum*1>0) {
					document.getElementById("alermText").innerHTML='<img width="20" height="20" src="'+
						ctx+'/public/images/main/alarm.png"/><font color="red">[<a href="javascript:getAlertAlarm()">'+alarmNum+'</a>]</font>'+common_alermInfo;
					tip.show();
				}
			}
	 });
 }
 
 /*------license到期提示--------------*/
 function showLicense(){
	 var param = {};
	 dwrAction.doAjax('viewAction', 'getLicenseInfo', param, function(res){
			if(res.success) {
				var leftNum = res.dataObject;
				//可用天数小于等于20天，给出提示
				if (leftNum*1<=20) {
					document.getElementById("licenseText").innerHTML='<img width="20" height="20" src="'+
						ctx+'/public/images/main/alarm.png"/>'+common_licenseInfo1+' <font color="red">'+leftNum+'</font> '+common_licenseInfo2;
					licenseTip.show();
				}
			}
	 });
 }

 function initMain() {
	var url = ctx + '/view!initMain.do';
	var text = mainPage;
	var id = defaultTabId;
	openpageOnTree(text,id,text,'',url,'yes',1);
	return;
}

function initLeft() {
	if (sysMode=='1') { //小型预付费，定制左边树
		$('leftFrame').src = ctx + '/view!initLeftS.do';
	}else{
		$('leftFrame').src = ctx + '/view!initLeft.do';
	}
	return;
}

function loginOut() {
	window.location = ctx + '/login!logOut.do';
}

//切换菜单
/*
function doMenus(menuId) {
//	if(simpleMenu) {
//		if(cdIds!=''){//显示选中效果
//			var cdArr = cdIds.split(',');
//			for ( var i=0; i < cdArr.length; i++) {
//				document.getElementById(cdArr[i]).className = 'ui-nav-item';
//			}
//			document.getElementById(menuId).className = 'ui-nav-item ui-nav-item-current';
//		}
//		simpleMenu.rebuild(menuId);
//	}
}
*/

//高级查询
var AdvWin = '';
function advQuery(type) {
	var url = ctx + '/leftTree!initAdv.do?type=' + type;
	var baseParam = {
		url:url,
		title:common_vew_advQuery_title,
		el:'adv',
		width:850,
		height:500,
		draggable:true	
	};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//群组设置
var QzszWin = '';
function qzszQuery() {
	var url = ctx + '/qzsz!init.do';
	var text = common_vew_qzszQuery_title;
	var menuId = "00001";
	openpageOnTree(text,menuId,text,'',url,'yes',1);
	/*
	var baseParam = {
		url:url,
		title:common_vew_qzszQuery_title,
		el:'qzsz',
		width:750,
		height:550,
		draggable:true	
	};
	QzszWin = new Wg.window(baseParam);
	QzszWin.open();*/
}

//群组添加
function qzadd() {
	var url = ctx + '/qzsz!initMdmQzBj.do';
	var text = common_vew_qzaddQuery_title;
	var menuId = "00002";
	openpageOnTree(text,menuId,text,'',url,'yes',1);
}

function initHelp(){
	window.open(ctx+"/help/"+ctxLang+"/"+helpDocAreaId+".html");
}

function initPwd() {
	//Ext.getDom('mainFrame').src = ctx + '/system/qxgl/czygl!initPwd.do';
	var url = ctx + '/system/qxgl/czygl!initPwd.do';
	var text = common_vew_initPwd_text;
	var id = pwdTabId;
	openpageOnTree(text,id,text,'',url,'yes',1);
}

//初始化地图信息
function initGisMap() {
	var url = ctx + '/amigis/zy/gis!map.do';
	var text = common_vew_amiGis_text;
	var id = "gis" ;
	openpageOnTree(text,id,text,'',url,'yes',1);
}

//Alarm dashboard
function initAlarmDashBoard(){
	var url  = ctx+'/mainPage!cai.do';
	var text = common_view_AlarmDashBorad_Name; 
	var id = "dashboard";
	openpageOnTree(text,id,text,'',url,'yes',1);
}

function initShortCuts() {
	//Ext.getDom('mainFrame').src = ctx + '/system/qxgl/czygl!initPwd.do';
	var url = ctx + '/view!initShortCuts.do';
	var text = common_vew_initShortCut_text;
	var id = shortCutTabId;
	openpageOnTree(text,id,text,'',url,'yes',1);
}

var tabindex = 1;
//在右侧tab中打开树菜单
function openpageOnTree(location,menuid,menuname,menuiconpath,href,addflag,allowednum){
	if(href.indexOf('?') > -1){
		href += "&menuid="+menuid+"&menupath="+encodeURIComponent(location);
	}else{
		href += "?menuid="+menuid+"&menupath="+encodeURIComponent(location);
	}
	
	if(href.indexOf('_target=_blank')!=-1){
		window.open(href,'_blank','');
		return false;
	}

	menuid = menuid.trim();
	var openedidx = -1;
	var openednum = 0;
	var curopenidx = -1;//当前打开tab页的位置
	
	var tabs = Ext.getCmp("defaulttab0");
	var _currenttab = tabs.getActiveTab();
	var curId = "";
	if(_currenttab!=null){
		curId = _currenttab.id;
	}
	
	var i = 0;
	
    tabs.items.each(function(){
    	 if(this.id.substring(0,this.id.indexOf('_')) == menuid){
 			if(openedidx == -1){
 				openedidx = i;
 			}
 			openednum ++;
         }
         if(this.id.substring(0,this.id.indexOf('_') + 1) == curId){
         	curopenidx = i;
         }
        i++;
    });
	
	if(openednum < allowednum){
		++(tabindex);
  		var html = "";
  		//传入当前tab的ID
  		if (href.indexOf('?')!=-1) {
  			href += '&curTabId='+menuid;
		}else{
			href += '?curTabId='+menuid;
		}
		html = "<iframe algin='right' src='"+href+"' width='100%' height='100%' frameborder='0' id='mainFrame"+tabindex+"' name='mainFrame"+tabindex+"'></iframe>";

		tabs.add({
			id: menuid+'_'+tabindex,
            title: menuname,
            tabTip:location,
            iconCls: 'tabs',
            html: html,
            closable:true
        }).show();
    }else{
    	tabs.setActiveTab(openedidx);
    	var tidx = getCurrentTabIndex();
    	//if(document.all){
    	//	document.frames('mainFrame'+tidx).location.reload();
    	//}else{
    		document.getElementById('mainFrame'+tidx).contentWindow.location = href;  
    	//}
    }
}

//得到当前tab的序号
function getCurrentTabIndex(){
	var tabs = Ext.getCmp("defaulttab0");
	if (tabs.getActiveTab()!=null) {
		var tabid = tabs.getActiveTab().id;
		return tabid.substring(tabid.indexOf('_') + 1,tabid.length);
	}else{
		return "-1";
	}
}

//树右键加入群组
function addGroup(nodeId, qzfl, qzlx) {
	var url='';
	if(nodeId == ''){
		url = String.format(ctx + '/qz!initQz.do?doType=LeftaddGroup&qzfl={0}&zdjh={1}&qzlx={2}',qzfl,nodeId,qzlx);
	}else{
		var url = String.format(ctx + '/qzsz!initMdmjrqz.do?jh={0}&qzlx={1}&totalCount={2}&selectAllFlg={3}&type={4}&sjid={5}',
				nodeId,'bj',1,'false','','');
//		url = String.format(ctx + '/qz!initQz.do?doType=addGroup&qzfl={0}&zdjh={1}&qzlx={2}',qzfl,nodeId,qzlx);
	}
	
	var baseParam = {
		url:url,
		title:common_vew_addGroup_title,
		el:'detail',
		width:750,
		height:450,
		draggable:true
	};
	var GroupWin = new Wg.window(baseParam);
	GroupWin.open();
}

//告警信息
function getAlertAlarm(){
	var url = ctx+"/run/yccl/zdgjcx!getAlertAlarm.do";
	var baseParam = {
		url : url,
		title : common_notice_title,
		el : 'parseAlarm',
		width : 900,
		height : 500,
		draggable : true
	};
	alertWin = new Wg.window(baseParam);
	alertWin.open();
}

/**
 * 收缩和展现头部信息
 * @param flag
 */
function switchTitle(flag){
/*	if ('up'==flag) {
		Ext.getCmp('top').setHeight(50);
		document.getElementById('fullHead').style.display = 'none';
		document.getElementById('shrinkHead').style.display = '';
		viewport.doLayout();
	}else{
		document.getElementById('fullHead').style.display = '';
		document.getElementById('shrinkHead').style.display = 'none';
		Ext.getCmp('top').setHeight(110);
		viewport.doLayout();
	}*/
}
//全屏切换
function switchFull(flag){
/*	if('true'==flag){
		Ext.getCmp('top').setHeight(0);
		document.getElementById('toptb').style.display = 'none';
		viewport.doLayout();
	}else{
		Ext.getCmp('top').setHeight(110);
		document.getElementById('fullHead').style.display = '';
		document.getElementById('shrinkHead').style.display = 'none';
		document.getElementById('toptb').style.display = '';
		viewport.doLayout();
	}*/
}

function changeMenu(){
	window.location = ctx + '/view!init.do?dtmFlag=true';
}
/*window.onbeforeunload=function(){  
	alert("event.clientX----:"+event.clientX);
	alert("document.body.clientWidth----:"+document.body.clientWidth);
	alert("event.clientY---:"+event.clientY);
	alert("event.altKey---:"+event.altKey);
	if  (event.clientX> document.body.clientWidth   &&event.clientY <0||event.altKey){
	   alert('close---');
   }
}  

*/

//显示待办工单类型区分
function showTodo() {
	var param = {};
	dwrAction.doAjax('todoAction', 'initTodo', param, function(res) {
		if (res.success) {
			if(res.dataObject != null && res.dataObject != '') {
				$('msgTotal').innerHTML = res.dataObject.msgCnt;//总消息个数
				
				var strMsg = "";
				var ls = res.dataObject.flLs;
				for (var i = 0; i < ls.length; i++) {
					var oMap = ls[i];
					strMsg += "<li><a href=\"#\" onclick=\"msgCenter('"+oMap.LX+"', '"+oMap.CNT+"');\"><span class=\"badge\">"+oMap.CNT+"</span>"+oMap.MC+"</a></li>";
				}
				//
				$('msgDetail').innerHTML = strMsg;
			}
		}
	});
}

//待处理查询界面
function msgCenter(ywlx, cnt) {
	var url;
	if('01' == ywlx) {
		url = String.format(ctx + "/todo!init.do", null);
		top.openpageOnTree(common_todo_title, '100000', common_todo_title, null, url, 'yes', 1);	
	}
	
	if('02' == ywlx) {
		if('03' != xtid) { //MDC
			url = String.format(ctx+"/run/yccl/zdgjcx!getAlertAlarm.do", null);
			top.openpageOnTree(common_notice_title, '200000', common_notice_title, null, url, 'yes', 1);			
		}
		if('03' == xtid) { //MDM
			url = String.format(ctx+"/run/yccl/zdgjcx!mdmAlarm.do", null);
			top.openpageOnTree(common_notice_title, '200000', common_notice_title, null, url, 'yes', 1);			
		}	
	}
	
	if('03' == ywlx) {
		Wg.alert(common_licenseInfo1 +" "+ cnt +" "+ common_licenseInfo2);
	}
}

function todoHid(target) {
	var event = window.event || arguments.callee.caller.arguments[0]; 
	var obj = event.toElement || event.relatedTarget ; 
	var parent = target; 
	if(parent.contains(obj)) return false; 
	$('to_do').className = 'ui-nav-item notice-parent';
}

function todoShow() {
	if(0 != $('msgTotal').innerHTML) {
		$('to_do').className = 'ui-nav-item notice-parent notice-active';	
	}
}
