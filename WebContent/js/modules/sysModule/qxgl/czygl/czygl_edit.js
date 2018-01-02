var menuId = '52100';
var jgtree_url = ctx + '/system/qxgl/czygl!getJgTree.do';
var jsTree_url = ctx + '/system/qxgl/czygl!getJsTree.do';
var qxtree_url = ctx + '/system/qxgl/czygl!getQxFwTree.do';
var JgTree = '';
var JsTree = '';
var QxTree = '';
var YhWin = '';
var h1 = 0.5;
Ext.onReady(function() {
	hideLeft();
	/*JgTree = new Wg.Tree( {
		url : jgtree_url,
		el : 'jgtree',
		height : screen.height * 0.4,
		width : 260,
		rootVisible : true,
		border : true
	});
	JgTree.init(unitCode, unitName, 'DW');
	JgTree.tree.addListener("click", jgTreeHandler);*/

	JsTree = new Wg.Tree( {
		title : czygl_jstree_title,
		url : jsTree_url,
		el : 'cdtree',
		height : screen.height * h1,
		width : 260,
		isCheckTree : true,
		rootVisible : false,
		border : true
	});

	JsTree.init('root', 'root', 'cd', function(treeloader, node) {
		treeloader.baseParams = {
			czyid : $E($F('czyid')) ? 'root' : $F('czyid')
		};
	});
	
	QxTree = new Wg.Tree( {
		url : qxtree_url,
		el : 'qxtree',
		title : czygl_qxtree_title,
		height : screen.height * h1,
		width : 260,
		rootVisible : false,
		isCheckTree:true,
		border : true
	});
	
	QxTree.init('qxRoot', 'qxRoot', 'null', function(treeloader, node) {
		if($E($F('dwdm'))) return false;
		treeloader.baseParams = {
			dwdm : $F('dwdm'),
			czyid : $E($F('czyid')) ? 'root' : $F('czyid')
		};
	});
});

//树
function getBmTree(selType) {
	var title = czygl_czy_bmTreeTitle;
	var url = String.format(ctx + '/system/qxgl/czygl!initBmTree.do?selType={0}', selType);
	var baseParam = {
		url : url,
		title : title,
		el : 'treeDiv',
		width : 320,
		height : 450,
		draggable : true
	};
	YhWin = new Wg.window(baseParam);
	YhWin.open();
}

function reloadPage() {
	$('czyForm').reset();
	JsTree.reload();
	QxTree.reload();
}
// 机构树响应
function jgTreeHandler(node) {
	var nodeId = node.id;
	var nodeType = node.attributes.ndType;
	var nodeText = node.attributes.text;
	var dwdm = node.attributes.info;
	if ('DW' == nodeType) { // 单位
		reloadPage();
		return;
	} else if ('JG' == nodeType) { // 机构
		$('czyForm').reset();
		$('bmmc').value = nodeText;
		$('bmid').value = nodeId;
		$('dwdm').value = dwdm;
		JsTree.reload();
		QxTree.reload();
	} else if ('QUERY' == nodeType) {
		reloadPage(); // 查询根节点
		return;
	} else { // 操作员
		dwrAction.doAjax('czyglAction', 'initCzy', {
			czyid : nodeId
		}, function(res) {
			if (!$E(res.msg)) {
				var op = Ext.decode(res.msg);
				$('dwdm').value = op.dwdm;
				$('bmmc').value = op.bmmc;
				$('bmid').value = op.bmid;
				$('czyid').value = nodeId;
				$('xm').value = op.xm;
				if(!$E($('mm')))
				$('mm').value = op.mm;
				$('sjhm').value = op.sjhm;
				$('dhhm').value = op.dhhm;
				$('yxdz').value = op.yxdz;
				$('zt').value = op.zt;
				JsTree.reload();
				QxTree.reload();
			}
		});
	}
}

function save(operateType){
	if('add' == operateType){
		addCzy();
	}
		
	if('edit' == operateType){
		updCzy();
	}
}
// 查询操作员
function queryCzy() {
	var qczyid = $F('qczyid');
	var qxm = $F('qxm');
	if ($E(qczyid) && $E(qxm)) {
		Wg.alert(czygl_query_null);
		return;
	}
	var root = JgTree.getRoot();
	root.setText(czygl_query_result);
	root.id = Ext.encode( {
		czyid : qczyid,
		xm : qxm
	});
	root.attributes.ndType = 'QUERY';
	root.reload();
}

// 新增操作员
function addCzy() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	
	var bmid = $F('bmid');
	var bmmc = $F('bmmc');
	var czyid = $F('czyid');
	var xm = $F('xm');
	var txfws = $F('txfws');
	
	var jsids = JsTree.getCheckChildren();
	jsids =  $E(jsids) ? '' : jsids;
	if (jsids=='') {
		Wg.alert(czygl_add_null_js);
		return;
	}
	
	var o = QxTree.tree.getChecked('id');
	var _dwdms = '';
	for(var i=0;i<o.length;i++) {
		if(o[i] != $F('dwdm'))
		_dwdms += o[i] + ',';
	}
	if (o.length > 0 && !$E(_dwdms)) {
		_dwdms = _dwdms.substring(0, _dwdms.length - 1);
	}
	
	var tname = $F('tname');
	var tno = $F('tno');
	//alert(tno);
	
	var p = {
		czyid : czyid,
		dwdm : $F('dwdm'),
		bmid : bmid,
		xm : xm,
		mm : $F('qrmm'),
		sjhm : $F('sjhm'),
		dhhm : $F('dhhm'),
		yxdz : $F('yxdz'),
		zt : $F('zt'),
		tname : tname,
		tno : tno,
		jsids : jsids,
		qxfw : o.length > 0 ? 'true' : '',
		dwdms : $E(_dwdms) ? '' : _dwdms,
		ip:$F('ip'),
		sysjq:$F('sysjq'),
		sysjz:$F('sysjz'),
		txfws:txfws,
		type:'czy',
		logger : czygl_add_logger + czyid
	};

	Wg.confirm(czygl_add_confirm, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '01', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.reloadGrid();
					parent.win.close();
				}
			});
		});
	});
}

// 修改操作员
function updCzy() {
	//验证各个字段信息
	if(!dataValidate()){
		return;
	}
	var czyid = $F('czyid');
	var xm = $F('xm');
	var txfws = $F('txfws');
	var jsids = JsTree.getCheckChildren();
	jsids =  $E(jsids) ? '' : jsids;
	if (jsids=='') {
		Wg.alert(czygl_add_null_js);
		return;
	}
	
	var o = QxTree.tree.getChecked('id');
	var _dwdms = '';
	for(var i=0;i<o.length;i++) {
		if(o[i] != $F('dwdm'))
		_dwdms += o[i] + ',';
	}
	if (o.length > 0 && !$E(_dwdms)) {
		_dwdms = _dwdms.substring(0, _dwdms.length - 1);
	}
	var tname = $F('tname');
	var tno = $F('tno');
	
	var p = {
		czyid : czyid,
		xm : xm,
		bmid : $F('bmid'),
		dwdm : $F('dwdm'),
		sjhm : $F('sjhm'),
		dhhm : $F('dhhm'),
		yxdz : $F('yxdz'),
		mm : $F('qrmm'),
		zt : $F('zt'),
		tname : tname,
		tno : tno,
		jsids : $E(jsids) ? '' : jsids,
		qxfw : o.length > 0 ? 'true' : '',
		dwdms : $E(_dwdms) ? '' : _dwdms,
		ip:$F('ip'),
		sysjq:$F('sysjq'),
		sysjz:$F('sysjz'),
		txfws:txfws,
		type:'czy',
		logger : czygl_upd_logger + czyid
	};

	Wg.confirm(czygl_upd_confirm, function() {
		dwrAction.doDbWorks('czyglAction', menuId + '02', p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					parent.reloadGrid();
					parent.win.close();
				}
			});
		});
	});
}

//数据验证
function dataValidate(){
	var bmid = $F('bmid');
	var bmmc = $F('bmmc');
	var czyid = $F('czyid');
	var xm = $F('xm');
	var mm = $F('mm');
	var qrmm = $F('qrmm');
	var yxdz = $F('yxdz');
	var ip = $F('ip');
	var sysjq = $F('sysjq');
	var sysjz = $F('sysjz'); 
	
	if ($E(bmmc)) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_dept,function(btn,text){
			if(btn=='ok'){
				$('bmmc').focus();
			}
		});
		return false;
	}
	
	if ($E(czyid)) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_id,function(btn,text){
			if(btn=='ok'){
				$('czyid').focus();
			}
		});
		return false;
	}
	
	if ($E(xm)) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_name,function(btn,text){
			if(btn=='ok'){
				$('xm').focus();
			}
		});
		return false;
	}
	if ($E(mm)) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_pwd,function(btn,text){
			if(btn=='ok'){
				$('mm').focus();
			}
		});
		return false;
	}
	if($F('qrmm') != $F('mm')) {
		Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_pwdConfirm,function(btn,text){
			if(btn=='ok'){
				$('qrmm').focus();
			}
		});
		return false;
	}
	if(!$E(yxdz)){
		var validate = /^[a-zA-Z0-9-_.]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,3}$/;
		if(!validate.test(yxdz)){
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_yxdz,function(btn,text){
				if(btn=='ok'){
					$('yxdz').focus();
				}
			});
			return false;
		}
	}
	if (!$E(ip)) {
		var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
		if(re.test(ip)) { 
			if( RegExp.$1 > 256 || RegExp.$2 > 256 || RegExp.$3 > 256 || RegExp.$4 > 256){
				Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_ip,function(btn,text){
					if(btn=='ok'){
						$('ip').focus();
					}
				});
				return false;
			}
		} else {
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_ip,function(btn,text){
				if(btn=='ok'){
					$('ip').focus();
				}
			});
			return false;
		}
	}
	
	//有允许登录的起始时间，则也需要有结束时间
	if(!$E(sysjq)) {
		if($E(sysjz)) {
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_sysjz,function(btn,text){
				if(btn=='ok'){
					$('sysjz').focus();
				}
			});
			return false;
		}
	}
	
	//有允许登录的结束时间，则也需要有起始时间
	if(!$E(sysjz)) {
		if($E(sysjq)) {
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_null_sysjz,function(btn,text){
				if(btn=='ok'){
					$('sysjq').focus();
				}
			});
			return false;
		}
	}
	
	//目前只允许，登录的起始时间比登录的结束时间小
	if(!$E(sysjz)&&!$E(sysjq)) {
		if(sysjq>sysjz) {
			Ext.Msg.alert(ExtTools_alert_title,czygl_add_sysjqsmallsysjz,function(btn,text){
				if(btn=='ok'){
					$('sysjz').focus();
				}
			});
			return false;
		}
	}
	
	return true;
}