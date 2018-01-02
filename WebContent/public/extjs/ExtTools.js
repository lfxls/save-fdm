Ext.namespace("ycl.Widgets");
// Ext Tools by ycl 2009/10
Wg = ycl.Widgets;

Wg.ajax = function(_url , _param, _callback, _hasLoad, _loading) {
	if(dwrAction){
		dwrAction.checkSession(function(){
			if (_hasLoad) {
				Wg.showLoading(_loading);
			}
			Ext.Ajax.request( {
				url : _url,
				params : _param,
				loadMask : false,
				success : _callback,
				failure : function(res) {
					alert('ajax error');
				}
			});
		});
	} else {
		alert('dwr error');
	}
};

Wg.alert = function(str,fn) {
	//加上try catch解决进度条在运行过程中，切换画面，超时后，第二次再点击进度，进度卡死，计数继续的问题 2013-04-09 jun
	try{
		Ext.Msg.show( {
			title : ExtTools_alert_title,
			msg : str,
			minWidth : 220 + str.length * 5,
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.INFO,
			fn : function(btn) {
				if (btn == 'ok') {
					if (fn)
						fn();
				}
			}
		});
	}catch(e){
	}	
};

Wg.warn = function(str,fn) {
	//加上try catch解决进度条在运行过程中，切换画面，超时后，第二次再点击进度，进度卡死，计数继续的问题 2013-04-09 jun
	try{
		Ext.Msg.show( {
			title : ExtTools_warn_title,
			msg : str,
			minWidth : 220 + str.length * 5,
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING,
			fn : function(btn) {
				if (btn == 'ok') {
					if (fn)
						fn();
				}
			}
		});
	}catch(e){
	}	
};

Wg.confirm = function(str, okfn, nofn) {
	Ext.Msg.show( {
		title : ExtTools_confirm_title,
		msg : str,
		minWidth : 220 + str.length * 5,
		buttons : Ext.Msg.OKCANCEL,
		icon : Ext.MessageBox.QUESTION,
		fn : function(btn) {
			if (btn == 'ok') {
				if (okfn)
					okfn();
			} else {
				if (nofn)
					nofn();
			}
		}
	});
};

Wg.wait = function(str, s) {
	Ext.Msg.wait(str, ExtTools_wait_title, {
		interval : 15,
		duration : s ? s * 1000 : 1000,
		increment : 200,
		fn : function() {
			Ext.Msg.hide();
		}
	});
};

Wg.showLoading = function(v) {
	if (!v){
		v = ExtTools_loading;
	}
	Ext.getBody().mask(v, "x-mask-loading"); //loading
};

Wg.removeLoading = function() {
	// 去除x-mask-loading效果
	Ext.getBody().unmask();
};
 
