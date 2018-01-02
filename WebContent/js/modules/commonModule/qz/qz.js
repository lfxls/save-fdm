var groupGrid = '';
var groupUrl = ctx + '/qz!query.do';
var menuId = "00000";
function save(flg) {
	var qzlx = $('qzlx').value;
	var	record = groupGrid.sm.getSelections();

	if (!record || record.length == 0) {
		Wg.alert(yh_sel);
		return;
	} else {
		var zdjhs = "";
		for ( var i = 0; i < record.length; i++) {
			if (qzlx=='bj') {
				zdjhs += ':' + record[i].get("BJJH") + ',';
			}else{
				//选择终端,户信息不存在的处理
				var hh = record[i].get("HH");
				hh = (hh+''=='undefined')?'':hh;
				zdjhs += hh + ':' + record[i].get("ZDJH") + ',';
			}
		}
		
		zdjhs = zdjhs.substring(0, zdjhs.length - 1);
		var url = ctx + '/qz!qzcz.do';
		var name;
		var qzid;
		var menuIds="";
		var loggerTip;
		if (flg == 'add') {
			//加入群组
			menuIds = menuId + "02";
			if ($('allgroup').value == '') {
				Wg.alert(addqz_null);
				return;
			}
			qzid = $('allgroup').value;
			loggerTip = updQz+' '+qzid+' ' + zdjhs;
		} else {
			//新增群组
			menuIds = menuId + "01";
			qzid = '';
			var qzm = $F('qzm');
			var smzq =$F('smzq');
			
			if (qzm == '') {
				Ext.Msg.alert(alert_Title,qzm_null,function(btn,text){
					if(btn=='ok'){
						$('qzm').focus();
					}
				});
				return;
			}
			if (!checkGroupName(qzm)) {
				Ext.Msg.alert(alert_Title,qzm_error,function(btn,text){
					if(btn=='ok'){
						$('qzm').focus();
					}
				});
				return;
			}

			if (smzq == '') {
				Ext.Msg.alert(alert_Title,smzq_null,function(btn,text){
					if(btn=='ok'){
						$('smzq').focus();
					}
				});
				return;
			}

			if (!isNumber(smzq)) {
				Ext.Msg.alert(alert_Title,smzq_num,function(btn,text){
					if(btn=='ok'){
						$('smzq').focus();
					}
				});
				return;
			}
			if(smzq*1>999){
				Ext.Msg.alert(alert_Title,smzq_numMax,function(btn,text){
					if(btn=='ok'){
						$('smzq').focus();
					}
				});
				return;
			}
			loggerTip = addQz +' '+ $('qzm').value;
		}
		var fileParam = {
			zdjh : zdjhs,
			qzid : qzid,
			smzq : $('smzq').value,
			qzm : trim($('qzm').value),
			qzfl:$('qzfl').value,
			qzlx:qzlx,
			logger:loggerTip
		};
		
		dwrAction.doDbWorks('qzAction',menuIds, fileParam, function(res) {
			Wg.alert(res.msg,function(){
				if(res.success) {
					//parent.win.close();
				}
			});
		});
	}
}
Ext.onReady(function() {
	var qzlx = $F('qzlx');
	var _cm  = [];
	
	if (qzlx == 'bj') {
		_cm.push({
			header : bjjh_cm,
			dataIndex : 'BJJH',
			width : 128,
			sortable : true
		});
		_cm.push( {
			header : hh_cm,
			width : 100,
			dataIndex : 'HH',
			sortable : true
		});
		_cm.push({
			header : hm_cm,
			dataIndex : 'HM',
			width : 188,
			sortable : true
		});
		_cm.push({
			header : bjlx_cm,
			dataIndex : 'BJLX',
			width : 188,
			sortable : true
		});
	}else{
		_cm.push({
			header : zdjh_cm,
			dataIndex : 'ZDJH',
			width : 128,
			sortable : true
		});
		_cm.push({
			header : zdljdz_cm,
			dataIndex : 'ZDLJDZ',
			width : 100,
			sortable : true
		});
		_cm.push({
			header : zdyt_cm,
			dataIndex : 'ZDYT',
			width : 188,
			sortable : true
		});
		_cm.push({
			header : status_cm,
			dataIndex : 'ZDZT',
			width : 188,
			sortable : true
		});
	}
	
	var toolbar = [{
        id: 'add-buton',
		text:grid_toolbar_g_new,
        tooltip:grid_toolbar_g_new,
        iconCls:'g_add',
        handler: function(){
        	save('new');
        }
    },{
        id: 'join-buton',
		text:grid_toolbar_g_add,
        tooltip:grid_toolbar_g_add,
        iconCls:'g_join',
        handler: function(){
        	save('add');
        }
    }];
	
	groupGrid = new Wg.Grid({
		url : groupUrl,
		el : 'groupGrid',
		//title : title_cm,
		width:(Ext.getBody().getWidth()) *0.95,
		height : 345,
		tbar:toolbar,
		pageSize:50,
		cModel : _cm
	});
	groupGrid.init({
		name : "group",
		colNum:$F('colNum')
	}, true, false);
	
	//默认全选
	groupGrid.grid.getStore().on('load', function (s, records) {  
		groupGrid.sm.selectAll();
		groupGrid.allSelected = true;
	 });
});

// 选择所有终端时 grid列check
function checkAll(obj) {
	if (obj.checked) {
		if (groupGrid.sm) {
			groupGrid.sm.selectAll();
			groupGrid.allSelected = true;
			return;
		}
	} else {
		if (groupGrid.sm) {
			groupGrid.sm.clearSelections();
			groupGrid.allSelected = false;
			return;
		}
	}
}