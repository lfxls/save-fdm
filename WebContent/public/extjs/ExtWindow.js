Ext.namespace("ycl.Widgets");

Wg = ycl.Widgets;
var win;
/**
  _config={url:url,title:,el:,width:,height:}
*/
Wg.window = function(_config){ 
  Ext.apply(this, _config);
};
Ext.apply(Wg.window.prototype,
{
	open : function() {
		if(win) {
			win.close();
		}
		var e = Ext.get(this.el);
		if(!e) {
			Ext.DomHelper.append(document.body, {id:this.el, tag:'div'});
		}
		
		if(this.closeBtnShow==false){
			win = new Ext.Window({
				title : this.title,
				applyTo : this.el,
				width : this.width ? this.width : Ext.getBody().getWidth()* this.wip,
				height : this.height ? this.height : Ext.getBody().getHeight()* this.hip,
				layout : 'fit',
				maximizable : this.max,
				resizable : true,
				plain : true,
				animCollapse : true,
				draggable : this.draggable ? this.draggable : false,
				autoScroll:false,
				modal:true,
				html : "<iframe id='openwin' name='openwin' scrolling='auto' frameborder='0' style='width:100%;height:100%;margin:0;padding:0;z-index:0'></iframe>"
			});
		}else{
			win = new Ext.Window({
				title : this.title,
				applyTo : this.el,
				width : this.width ? this.width : Ext.getBody().getWidth()* this.wip,
				height : this.height ? this.height : Ext.getBody().getHeight()* this.hip,
				layout : 'fit',
				maximizable : this.max,
				resizable : true,
				plain : true,
				animCollapse : true,
				autoDestroy:true,
				modal:true,
				draggable : this.draggable ? this.draggable : false,
				autoScroll:false,
				html : "<iframe id='openwin' name='openwin' scrolling='auto' frameborder='0' style='width:100%;height:100%;margin:0;padding:0;z-index:0'></iframe>",
				buttons : [{text:ExtWindow_close,iconCls:'win_close',handler:function(){win.close();}}]
			});
		}
		if($('openwin')) {
			$('openwin').src = this.url;
		}
		win.show(this);
  	},
  	
  	// 子页面调用主页面 关闭窗口
	closeWin:function() {
  		if(win) {
			win.close();
		}
  	}
});

