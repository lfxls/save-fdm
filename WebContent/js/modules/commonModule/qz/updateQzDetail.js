var Module_URL = ctx + "/qzsz!";
var Grid_URL = Module_URL + 'getUpdateGrid.do';
var groupGrid = '';
var menuId = "00000";
var qzid="";
var qzfl="";
Ext.onReady(function() {
//	var zdOrbj = zdjh_cm;
	
	qzid = $F('qzid');
	qzfl = $F('qzfl');
	qzlx = $F('qzlx');
	
	if($F("smzq")=='0'){
		$('yjyx').checked = true;
	}else{
		$('yxsj').checked = true;
		$('yxrq').value = $F("smzq");
	}
	if($F("sfgxs")==1){//共享
		$("sfgx").checked = true;
	}else {
		$("sfgx").checked = false;
	}
	
	//grid列表
	var _cm = [ {
		header : grid_op,
		dataIndex : 'OP',
		width : 90,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="del" title="\{1}\" href="javascript:deleteSb(\'{0}\');" ></a>',
					record.data.ZDJH,grid_sc);
			
			return url;
		}
	},{
		header : grid_sbh,
		dataIndex : 'ZDJH',
		width : 120,
		sortable : true
	}, {
		header : grid_sblx,
		dataIndex : 'SBLXMC',
		width : 200,
		sortable : true
	},{
		dataIndex : 'SBLX',
		hidden : true
	}];
	
	var toolbar = [{
        id: 'del',
		text:grid_sc,
        tooltip:grid_sc,
        iconCls:'delT',
        handler: function(){
        	deleteSb();
        }
    },{
        id: 'dela',
		text:grid_scall,
        tooltip:grid_scall,
        iconCls:'cancel',
        handler: function(){
        	deleteSb('all');
        }
    }];
	
	groupGrid = new Wg.Grid({
		url : Grid_URL,
		el : 'groupGrid',
		title : grid_title,
		height : 248,
		cModel : _cm,
		tbar:toolbar,
		sort : 'ZDJH'
	});
	groupGrid.init({
		qzid : qzid,
		qzfl : qzfl,
		qzlx : qzlx
	}, true, false);
});

// 删除
function deleteSb(sfqx) {
	if (sfqx == 'all') {
		Wg.confirm(delall_sure, function() {
			var baseParam = {
				qzid : qzid,
				logger : $F('qzm') + del_allYH
			};

			dwrAction.doDbWorks("qzszAction", menuId + '03', baseParam, function(res) {
				if(res.success){
					Wg.alert(res.msg);
					groupGrid.reload({
						qzid : qzid,
						qzfl : qzfl,
						qzlx : qzlx
					});
				}
			});
		});
	} else {
		var record = groupGrid.getRecords();
		if (!record || record.length == 0) {
			Wg.alert(delYh_sel);
			return;
		} else {
			//拼接终端或者表计ID
			var zdjhs = "";
			for ( var i = 0; i < record.length; i++) {
				zdjhs += record[i].get("ZDJH") + '@';
			}
			zdjhs = zdjhs.substring(0, zdjhs.length - 1);
			
			var sblxs = "";
			for ( var i = 0; i < record.length; i++) {
				sblxs += record[i].get("SBLX") + '@';
			}
			sblxs = sblxs.substring(0, sblxs.length - 1);
			
			Wg.confirm(delYh_sure, function() {
//				var delUrl = Module_URL + 'delUser.do';
				var baseParam = {
					qzid : qzid,
					zdjhArray : zdjhs,
					sblxArray : sblxs,
					logger : $F('qzm') + del_qzYH + zdjhs
				};
				
				dwrAction.doDbWorks("qzszAction", menuId + '03', baseParam, function(res) {
					Wg.alert(res.msg,function(){
						if(res.success) {
								groupGrid.reload({ qzid : qzid, qzfl : qzfl, qzlx : qzlx });
						}
					});
				});
			});
		}
	}
}

//保存群组定义
function saveGroup() {
	var qzm = $F('qzm');
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
	var p = $FF('queryForm');
	p.sfgx=$('sfgx').checked == true?'1':'0';
	p.smzq=$('yxsj').checked == true?'1':'0';
	var menuIds = menuId+"02";
	dwrAction.doDbWorks('qzszAction',menuIds, p, function(res) {
		Wg.alert(res.msg,function(){
			if(res.success) {
				parent.query();
				parent.win.close();
			}
		});
	});
}