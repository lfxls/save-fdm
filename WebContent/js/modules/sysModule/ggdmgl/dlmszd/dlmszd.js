var Module_URL = "${ctx}/system/ggdmgl/dlmszd!";
var initUrl = Module_URL + 'init.do';
var queryItemUrl = Module_URL + 'queryItem.do';
var menuId = "54300";
var initDlmsSubUrl = Module_URL + 'initDlmsSub.do';
var initDataSortUrl = Module_URL + 'initDataSort.do';
var expDlmsSubUrl = Module_URL + 'expDlmsSub.do';
var resource = systemModule_ggdmgl_dlmszd.resourceBundle;

var DlmsSubWin ;
//查询数据项
function query(){
	window.location.href = initUrl+"?lang=" + $F('lang') + "&dlms_sub_protocol=" + $F('dlms_sub_protocol');
}
//显示tab 
var tabs;
Ext.onReady(function(){
	hideLeft();
	tabs = new Ext.TabPanel({  
		title:"title",
	    renderTo: "div_tabs",  
	    //tabPosition:"bottom", //bottom, top
	    height: 550,  
	    activeTab: 0,  
	    defaults: {  
	        autoScroll: true,  
	        autoHeight:true
	        //,style: "padding:5"  
	    },  
	    enableTabScroll: true,
	    //增加tab右键
        listeners:{
             //传进去的三个参数分别为:这个tabpanel(tabsDemo),当前标签页,事件对象e
	        "contextmenu":function(tabs,myitem,e){
	            menu=new Ext.menu.Menu([{
	                 text:resource.Text.addDataSort,
	                 handler:function(){addDataSort();}
	            }
	            ,{
	            	text:resource.Text.editDataSort,
	            	handler:function(){editDataSort(myitem);}
	            },{
                     text:resource.Text.delDataSort,
                     handler:function(){deleteDataSort(myitem);}
	            }]);
	            //显示在当前位置
	            menu.showAt(e.getPoint());
	         }
        }
	}).hide();
});
function addTab(id,name,activeFlag){  
    var iframeSrc = queryItemUrl + "?lang=" + $F('lang') + "&dlms_sub_protocol=" + $F('dlms_sub_protocol')+"&item_sort=" + id;
    var tab = tabs.add({  
        id: "tab" + id,
        dataSort:id,
        title: name,  
        html:'<iframe scrolling="auto" frameborder="0" width="100%" height="550" src="'+iframeSrc+'"></iframe>',  
        closable: false
    }); 
    if(activeFlag == true) tab.show();
    //隐藏按钮、显示tab
    Ext.getDom("button_addDataSort").style.display="none";
    tabs.doLayout();
    if(!tabs.isVisible())tabs.show();
}  

function refreshTab(id,name){  
	var tab = tabs.getItem("tab" + id);
	tab.setTitle(name);
}
function delTab(id){
	tabs.remove("tab" + id , true);
	if(tabs.items.length == 0){
		//隐藏Tab、显示按钮
		tabs.hide();
		Ext.getDom("button_addDataSort").style.display="block";
	}
}
//增加dlms规约-打开弹出窗口
function addDlmsSub(){
	var url = String.format(initDlmsSubUrl + '?lang={0}',$F('lang'));
	var baseParam = {
		url : url,
		el:'dlmsSub',
		title : resource.Title.addDlmsSub,
		width : 420,
		height : 340,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function editDlmsSub(){
	var url = String.format(initDlmsSubUrl + '?lang={0}&dlms_sub_protocol={1}',$F('lang'),$F('dlms_sub_protocol'));
	var baseParam = {
		url : url,
		el:'dlmsSub',
		title : resource.Title.editDlmsSub,
		width : 420,
		height : 340,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function deleteDlmsSub(){
	var type = "DLMS_SUB";
	//参数
	var p = {
		type:type,
		dlms_sub_protocol : $F('dlms_sub_protocol')
	};
	Wg.confirm(resource.Confirm.delDlmsSub, function() {
		dwrAction.doDbWorks('dlmszdAction', menuId + delOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.delDlmsSubSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新窗口
					window.location.href = initUrl+"?lang=" + $F('lang');
				});
			}else {
				var msg = res.msg== null?resource.Remark.delDlmsSubFailed:res.msg;
				Wg.alert(msg);
			}
		});
	});
}

//增加DLMS数据项分类-打开弹出窗口
function addDataSort(){
	var url = String.format(initDataSortUrl + '?lang={0}&dlms_sub_protocol={1}',$F('lang'),$F('dlms_sub_protocol'));
	var baseParam = {
		url : url,
		el:'dataSort',
		title : resource.Title.addDataSort,
		width : 420,
		height : 230,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function editDataSort(myitem){
	var url = String.format(initDataSortUrl + '?lang={0}&dlms_sub_protocol={1}&item_sort={2}',$F('lang'),$F('dlms_sub_protocol'),myitem.dataSort);
	var baseParam = {
		url : url,
		el:'dataSort',
		title : resource.Title.editDataSort,
		width : 420,
		height : 230,
		draggable : true
     };
	 DlmsSubWin = new Wg.window(baseParam);
	 DlmsSubWin.open();
}
function deleteDataSort(myitem){
	var type = "DLMS_DATA_SORT";
	//参数
	var p = {
		type:type,
		dlms_sub_protocol : $F('dlms_sub_protocol'),
		item_sort:myitem.dataSort
	};
	Wg.confirm(resource.Confirm.delDataSort, function() {
		dwrAction.doDbWorks('dlmszdAction', menuId + delOpt, p, function(res) {
			if(res.success){
				var msg = res.msg== null?resource.Remark.delDataSortSuccess:res.msg;
				Wg.alert(msg, function() {
					//刷新窗口
					delTab(myitem.dataSort);
				});
			}else {
				var msg = res.msg== null?resource.Remark.delDataSortFailed:res.msg;
				Wg.alert(msg);
			}
		});
	});
}

//导出规约配置文件
function expDlmsSub(){
	window.open(expDlmsSubUrl + "?lang=" + $F("lang"));
//	//dwrAction.doAjax('dlmszdAction', 'expDlmsSub', {}, function(res) {
//		alert(res);
////		if (res.success) {
////			Wg.alert(res.msg);
////		}
//	});
}