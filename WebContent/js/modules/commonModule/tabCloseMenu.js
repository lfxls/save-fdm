// Very simple plugin for adding a close context menu to tabs
Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    };

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: common_vew_TabCloseMenu_onContextMenu_closeCurrent,
                style:'text-align:left',
                icon:ctx+'/public/images/tab/1.gif',
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                text: common_vew_TabCloseMenu_onContextMenu_closeOther,
                style:'text-align:left',
                icon:ctx+'/public/images/tab/2.gif',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
            }/*,{
                id: tabs.id + '-add-shortcuts',
                text: common_vew_TabCloseMenu_onContextMenu_addShortcuts,
                style:'text-align:left',
                icon:ctx+'/public/images/tab/3.gif',
                handler : function(){
                	//tabid组成：menuid +"_"+index
                	var tabId = ctxItem.id;
                	
                	//如果为快捷方式本身再增加快捷方式
                	var menuId = tabId.split("_")[0];
                	
                	dwrAction.doAjax('viewAction','addShortCuts',{menuId:menuId},function(res){
                		if(res.success){
                			Wg.alert(res.msg);
                		}
                	});
                }
            },{
                id: tabs.id + '-floatWin',
                text: common_vew_TabCloseMenu_onContextMenu_float,
                style:'text-align:left',
                icon:ctx+'/public/images/tab/4.gif',
                handler : function(){
                	switchFull('true');
                }
            },{
                id: tabs.id + '-restoreWin',
                text: common_vew_TabCloseMenu_onContextMenu_restore,
                style:'text-align:left',
                icon:ctx+'/public/images/tab/5.png',
                handler : function(){
                	switchFull('false');
                }
            }*/]);
            tabs.on('click', function(t, e){
            	menu.hide();
			});
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        var restoreFlag = false;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
        
        /*if(document.getElementById('toptb').style.display=='none'){
        	items.get(tabs.id+'-restoreWin').setVisible(true);
        	items.get(tabs.id+'-floatWin').setVisible(false);
        }else{
        	items.get(tabs.id+'-restoreWin').setVisible(false);
        	items.get(tabs.id+'-floatWin').setVisible(true);
        }*/
        

    	//如果为快捷方式本身再增加快捷方式
    	/*var menuId = item.id.split("_")[0];
    	if(menuId=='shortCutTab01' || menuId=='home'){
    		items.get(tabs.id+'-add-shortcuts').setVisible(false);
    	}else{
    		items.get(tabs.id+'-add-shortcuts').setVisible(true);
    	}*/
        
        menu.showAt(e.getPoint());
    }
};