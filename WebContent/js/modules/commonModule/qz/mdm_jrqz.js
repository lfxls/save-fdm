var meuid = '00002';
var Module_URL = ctx + "/qzsz!";
var Grid_URL = Module_URL + 'queryDetail.do';
var simpleGrid = '';
var tabs="";
Ext.onReady(function() {
	 var _cm = [{
			header : cz_cm,
			dataIndex : 'OP',
			width : 80,
			renderer : render
		}, {
			header : qzm_cm,
			dataIndex : 'QZMC',
			width : 150,
			sortable : true
		}, {
			header : cjsj_cm,
			dataIndex : 'CJSJ',
			sortable : true,
			width : 150
		}, {
			header : smzq_cm,
			dataIndex : 'SMZQ',
			width : 100,
			sortable : true
		}, {
			header : sfgx_cm,
			dataIndex : 'SFGX',
			width : 80,
			sortable : true
		}, {
			header : czy_cm,
			dataIndex : 'CZYID',
			width : 110,
			sortable : true
		}, {
			dataIndex : 'QZID',
			hidden : true
		}, {
			dataIndex : 'QZFL',
			hidden : true
		} ,{
			dataIndex : 'QZLX',
			hidden : true
	}];
		
	simpleGrid = new Wg.Grid({
		url : Grid_URL,
		el : 'grid',
		title : grid_title,
		cModel : _cm,
		heightPercent : 0.75
	});
	simpleGrid.init();
});

//查询
function query() {
	var baseParam = {
		qzm : $('nodeText').value
	};
	simpleGrid.reload(baseParam);
}

/**
 * tab跳转
 * @param lx
 */
function pageTo(lx){
	if(lx == 'l1'){
		document.getElementById("l1").className = "ui-tab-item ui-tab-item-current";
		document.getElementById("l2").className = "ui-tab-item";
		$('tab1').style.display='';
		$('tab2').style.display='none';
	} else if(lx == 'l2'){
		document.getElementById("l2").className = "ui-tab-item ui-tab-item-current";
		document.getElementById("l1").className = "ui-tab-item";
		$('tab1').style.display='none';
		$('tab2').style.display='';
	}
}

//操作
function render(value, cellmeta, record) {
	var qzid = record.data.QZID;
	var url = String.format('<a class="addCell" title='+grid_toolbar_g_add
						+' href="javascript:save(\'{0}\',\'{1}\');" ></a>',  qzid,  'tab2');
	return url;
}

//保存（加入群组保存和新建群组保存）
function save(qzid, tab) {
	var p = $FF('queryForm');
	if(tab=='tab1'){
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
	}else{
		p.qzid=qzid;
	}
	
	p.sfgx=$('sfgx').checked == true?'1':'0';
	p.smzq=$('yxsj').checked == true?'1':'0';
	p.tab = tab;
	
	Wg.confirm(qd_add, function() {
		dwrAction.doAjax("qzszAction", "save", p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.win.close();
				}
			});
		});
	});
}



