Ext.namespace("ycl.Widgets");
// Ext Menu by ycl 2009/10
Wg = ycl.Widgets;
/*
 * _cfg ={ url:_URL,el:'toolbar', hander: fn}
 */
Wg.menu = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
		tb : ''
	});
};

Ext.apply(Wg.menu.prototype, {
	init : function() {
		var obj = this;
		this.tb = new Ext.Toolbar({
					id : 'tbar',
					//autoHeight : true
					height:25
				});
		var _url = this.url;
		Wg.ajax( _url, {menuId : 'main'}, function(result, request){
						var json = result.responseText;
						if(Ext.isEmpty(json)) {
							//Ext.apply("",{width:80});
							//obj.tb.add("");
							obj.tb.render("");	 
							return;
						}
						var items = Ext.decode(json);  
						//alert('items length ' + items.length);
						for (var i = 0; i < items.length; i++) {
							Ext.apply(items[i],{width:80});
							obj.tb.add(items[i]);
							if (i < items.length - 1)
								obj.tb.add('-');
						}
						obj.tb.render(obj.el);	 
				}, false, null);
	},
 	rebuild : function(_menuId) {
 		if(this.tb || this.tb!=null){
 			this.tb.removeAll();
 		}
 		
		var obj = this;
		var json = Wg.ajax(this.url, {menuId : _menuId}, function(result, request){
				var json = result.responseText;
				if(Ext.isEmpty(json)) {
					return;
				}
				//清除对象
				for ( var i = 0; i < obj.tb.items.length; i++) {
					obj.tb.remove(obj.tb.items[i]);
					obj.tb.items[i].destroy();
				}
				
				var items = Ext.decode(json);  
				for (var i = 0; i < items.length; i++) {
					obj.setHandler(items[i]);
					obj.tb.add(items[i]);
					if (i < items.length - 1)
						obj.tb.add('-');
				}
				obj.tb.doLayout();
		}, false, null);
	},
	setHandler : function(item) {
		var obj = this;
		if(Ext.isEmpty(item.menu)) {
			return;
		} else {
			if(Ext.isEmpty(item.menu.items) || item.menu.items.length == 0) {
				return;
			} else {
//				alert(item.menu.items.length);
				Ext.each(item.menu.items,function(o){
					if(o.isLeaf == '1') {
						Ext.apply(o,{handler:obj.menuHandler});
					} else {
						obj.setHandler(o);
					}
					return;
				});
			}
		}
	},
	menuHandler : function(item) {
		var timestamp = Date.UTC(new Date());
		if(!Ext.isEmpty(item.url)){
			var url = ctx + item.url +'?timestamp=' + timestamp;
			openpageOnTree(item.text,item.id,item.text,'',url,'yes',1);
		}
		
		/*if(!Ext.isEmpty(item.url)) {
			var url = ctx + item.url +'?timestamp=' + timestamp;
			Ext.getDom('mainFrame').src = url;
		}*/
		return;
	}
});