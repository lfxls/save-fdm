var menuId = '51100';
//结构树
var JgTree = '';
var tree_url = ctx + '/system/qyjggl/jggl!getDwTree.do';
//机构列表
var grid_url = ctx + '/system/qyjggl/jggl!query.do';
var JgGrid = '';
//右键菜单
var treeMenu = '';
var h1 = 0.48;
var parentNode;
Ext.onReady(function() {
	hideLeft();
	//结构树
	JgTree = new Wg.Tree( {
		url : tree_url,
		el : 'tree',
		height : screen.height * h1,
		width : 260,
		rootVisible : true,
		border : true
	});
	
	//初始化树
	JgTree.init(unitCode, unitName, unitLevel); //默认unitLevel为04级别
	//设置监听事件
	JgTree.tree.addListener("click", jgTreeHandler);
	//小型预付费
	if (sysMode!='1') {
		//设置响应事件
		JgTree.tree.on('contextmenu', contextmenu);
	}

	//根树
	var root = JgTree.getRoot();
	root.attributes.info = unitCode;
	
	//鼠标右键菜单
	treeMenu = new Ext.menu.Menu({
		id : 'treeMenu'
	});
	
	//表格列
	var _cm = [	{
				header : jggl_cm_op,
				dataIndex : 'OP',
				width : 100,
				renderer : function(value, cellmeta, record) {
					var url = String.format(
							'<a class="edit" title="\{2}\" href="javascript:initUpJg(\'{0}\',\'{1}\');"></a>',
							record.data.BMID,record.data.BMMC,jggl_cm_op_editbm);
					if(sysMode!='1'){
						 url += String
							.format(
									'<a class="editPermissions" title="\{2}\" href="javascript:initUpQx(\'{0}\',\'{1}\');"></a>',
									record.data.DWDM, record.data.BMID,jggl_cm_op_editqx);
					}
					url += String
							.format(
									'<a class="del" title="\{1}\" href="javascript:delJg(\'{0}\',true);"></a>',
									record.data.BMID,jggl_cm_op_del);
					return url;
				}
			}, {
				header : jggl_cm_dw,
				dataIndex : 'DWMC',
				width : 200,
				sortable : true
			}, {
				header : jggl_cm_bm,
				dataIndex : 'BMMC',
				width : 200,
				sortable : true
			}, {
				header : jggl_cm_bmlx,
				dataIndex : 'BMLX',
				width : 150
			}, {
				header : jggl_cm_jggl,
				dataIndex : 'CZYS',
				width : 120,
				renderer : function(value, cellmeta, record) {
					if (parseInt(value) > 0) {
						return String
								.format(
										'<a href="javascript:initCzy(\'{0}\',\'{1}\', \'{2}\');">{3}</a>',
										record.data.DWDM, record.data.BMID, record.data.BMMC, value);
					} else {
						return '0';
					}
				}
			}, {
				dataIndex : 'BMID',
				hidden : true
			}, {
				dataIndex : 'DWDM',
				hidden : true
			} ];

    //按钮
	var toolbar = [];
	if(sysMode!='1'){
		toolbar.push({
	        id: 'add-buton-dw',
			text : jggl_contextmenu_newdw,
	        tooltip : jggl_contextmenu_newdw,
	        iconCls : 'add',
	        handler : function(){
	        	initAddDw($('nodeType').value,$('nodeId').value);
	        }
	    });
		toolbar.push({
            xtype: 'tbseparator',
            text : ''
		});
	}
	toolbar.push({
        id: 'add-buton-jg',
		text : jggl_contextmenu_newjg,
        tooltip : jggl_contextmenu_newjg,
        iconCls : 'add',
        handler : function(){
        	initAddJg($('nodeId').value, $('nodeId').value);
        }
    });

	//加载数据
	JgGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		//sort : 'BMID',
		frame: true,
		title : jggl_grid_title,
		heightPercent : 0.8,
		widthPercent : 1,
		margin : 325,
		tbar : toolbar,
		cModel : _cm
	});
	JgGrid.init({});
	isViewBtn();
});

//创建右键菜单
function contextmenu(node, e) {
	var ndType = node.attributes.ndType;
	treeMenu.removeAll();
	if(ndType == 'jg'){
		var menuItems = [ /**{ DEL 2012/6/25 机构下不可以在新建机构
			id : 'newjg',
			text : jggl_contextmenu_newjg,
			handler : menuHandler
		},*/{
			id : 'updjg',
			text : jggl_contextmenu_updjg,
			handler : menuHandler
		},{
			id : 'deljg',
			text : jggl_contextmenu_deljg,
			handler : menuHandler
		},{
			id : 'updqx',
			text : jggl_contextmenu_updjgqx,
			handler : menuHandler
		}];
		
		for ( var i = 0; i < menuItems.length; i++) {
			treeMenu.addItem(menuItems[i]);
		}
	} 
	/*else if(ndType == '06'){  
		var menuItems = [{
			//新增机构
			id : 'newjg',
			text : jggl_contextmenu_newjg,
			handler : menuHandler
		},{
			//修改单位
			id : 'updw',
			text : jggl_contextmenu_upddw,
			handler : menuHandler
			
		},{
			//删除单位
			id : 'deldw',
			text : jggl_contextmenu_deldw,
			handler : menuHandler
		}];
		
		for ( var i = 0; i < menuItems.length; i++) {
			treeMenu.addItem(menuItems[i]);
		}
	}*/ 
	else if(node == JgTree.getRoot()){
		//根节点只有新增下级机构和下级单位的功能
		var menuItems = [{
			//新增单位
			id : 'newdw',
			text : jggl_contextmenu_newdw,
			handler : menuHandler
		},{
			//新增机构
			id : 'newjg',
			text : jggl_contextmenu_newjg,
			handler : menuHandler
		}];
		
		for ( var i = 0; i < menuItems.length; i++) {
			treeMenu.addItem(menuItems[i]);
		}
	}
	else {  
		var menuItems = [{
			//新增单位
			id : 'newdw',
			text : jggl_contextmenu_newdw,
			handler : menuHandler
		},{
			//新增机构
			id : 'newjg',
			text : jggl_contextmenu_newjg,
			handler : menuHandler
		},{
			//修改单位
			id : 'updw',
			text : jggl_contextmenu_upddw,
			handler : menuHandler
		},{
			//删除单位
			id : 'deldw',
			text : jggl_contextmenu_deldw,
			handler : menuHandler
		}];
		
		for ( var i = 0; i < menuItems.length; i++) {
			treeMenu.addItem(menuItems[i]);
		}
	} 
	
	treeMenu.doLayout();
	//被选中的节点
	JgTree.selectedNode = node;
	//显示位置
	var coords = e.getXY();
	treeMenu.showAt([ coords[0], coords[1] ]);
}

//菜单响应
function menuHandler(item) {
	var node = JgTree.selectedNode;
	if (item.id == 'newjg') {
		//新增机构
		initAddJg(node.attributes.info, node.id);
	} else if (item.id == 'deljg') {
		//删除机构
		delJg(node.id,true);
	} else if (item.id == 'updqx') {
		//修改机构权限
		initUpQx(node.attributes.info, node.id);
	} else if (item.id == 'updjg'){
		//修改机构
		initUpJg(node.id , node.text);
	} else if (item.id == 'newdw') {
		//新增单位
		initAddDw(node.attributes.ndType,node.id);
	} else if(item.id == 'deldw'){
		//删除单位
		delDw(node.id,true);
	} else if(item.id == 'updw'){
		//修改单位
		initUpDw(node.id , node.text); 
	}else{
		return;
	}
}

// 机构树响应
function jgTreeHandler(_node) {
	$('nodeId').value = _node.id;
	$('nodeText').value = _node.text;
	$('nodeType').value = _node.attributes.ndType;
	query();
	isViewBtn();
}

//查询
function query() {
	var p = {
		nodeId : $F('nodeId'),
		nodeType : $F('nodeType')
		
	};
	JgGrid.reload(p);
}

//新建单位
function initAddDw(jb,dwdm) {
	var url = String.format(ctx +  '/system/qyjggl/jggl!initAddDw.do?dwjb={0}&dwdm={1}',jb, dwdm);
	var baseParam = {
			url : url,
			title : jggl_adddw_win,
			el : 'adddw',
			width : 400,
			height : 260,
			draggable : true
		};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//修改单位
function initUpDw(dwdm,dwmc){
	var url = String.format(ctx +  '/system/qyjggl/jggl!initUpDw.do?dwdm={0}&dwmc={1}',dwdm, encodeURI(dwmc));
	var baseParam = {
			url : url,
			title : jggl_upddw_win,
			el : 'updatedw',
			width : 400,
			height : 260,
			draggable : true
		};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//删除单位 
function delDw(dwdm,formTree){
	var p = {
			dwdm : dwdm,
			type : 'dw',
			logger : jggl_del_dw_logger + dwdm
		};
		Wg.confirm(jggl_del_dw_confirm, function() {
			dwrAction.doDbWorks('jgglAction', menuId + delOpt, p, function(res) {
				Wg.alert(res.msg, function() {
					if (res.success) {
						if (formTree) {
							var node = JgTree.getSelectNode();
							try{
								node.parentNode.reload();
								//alert("1");
								clear();
							}catch(e){
							}
						}
						//如果类型为机构，节点文本框赋值为空
						if($('nodeType') && $('nodeType').value == 'jg'){
							$('nodeText').value = '';
						}
					}
				});
			});
		});
}

//新建机构
function initAddJg(dwdm, bmid) {
	var url = String.format(ctx + '/system/qyjggl/jggl!initAddJg.do?dwdm={0}&sjbmid={1}', dwdm, bmid);
	var baseParam = {
			url : url,
			title : jggl_addjg_win,
			el : 'xzjg',
			width : 420,
			height : 260,
			draggable : true
		};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//修改机构权限
function initUpQx(_dwdm, _bmid) {
	var url = String.format(
			ctx + '/system/qyjggl/jggl!initFwQx.do?dwdm={0}&bmid={1}', _dwdm, _bmid);
	var baseParam = {
		url : url,
		title : jggl_upd_win,
		el : 'fwqx',
		width : 430,
		height : 480,
		draggable : true
	};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//修改机构信息
function initUpJg(_bmid,_bmmc){
	var url = String.format(
			ctx + '/system/qyjggl/jggl!initUpJg.do?bmid={0}&bmmc={1}',_bmid,encodeURI(_bmmc));
	var baseParam = {
		url : url ,
		title : jggl_updjg_win,
		el:'upjg',
		width : 380,
		height: 260,
		draggable : true
	};
	AdvWin = new Wg.window(baseParam);
	AdvWin.open();
}

//删除机构
function delJg(_bmid,formTree) {
	var p = {
		bmid : _bmid,
		type : 'jg',
		logger : jggl_del_bm_logger + _bmid
	};
	Wg.confirm(jggl_del_bm_confirm, function() {
		dwrAction.doDbWorks('jgglAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					if (formTree) {
						var node = JgTree.getSelectNode();
						if(node.attributes.ndType=='jg'){ //如果节点是机构，刷新父亲节点 
							node.parentNode.reload();
						}else{    //如果非机构节点 ，刷新本身节点 
							node.reload();
						}
					}
					//如果类型为机构，节点文本框赋值为空
					if($('nodeType') && $('nodeType').value == 'jg'){
						$('nodeText').value = '';
					}
					//刷新表格
					query();
				}
			});
		});
	});
}

//控制按钮显示 
function isViewBtn(){
	if(!$E($F('nodeType'))){
		//根据节点类型，显示或隐藏按钮
		var nType = $('nodeType').value;
		if(nType == 'jg'){
			hideBtn();
		/*}else if(nType == '06'){ 
			//非小型预付费
			if (sysMode!='1') {
				Ext.getCmp("add-buton-dw").disable();
			}
			Ext.getCmp("add-buton-jg").enable();*/
		}else{
			showBtn();
		}
	}
	else {
		hideBtn();
	}
}

//显示按钮
function showBtn(){
	//非小型预付费
	if (sysMode!='1') {
		Ext.getCmp("add-buton-dw").enable();
	}
	Ext.getCmp("add-buton-jg").enable();
}

//隐藏按钮
function hideBtn(){
	//非小型预付费
	if (sysMode!='1') {
		Ext.getCmp("add-buton-dw").disable();
	}
	Ext.getCmp("add-buton-jg").disable();
}

//显示部门下员工信息
function initCzy(dwdm, bmid, bmmc){
	var url = String.format(ctx + '/system/qxgl/czygl!init.do?dwdm={0}&bmid={1}&bmmc={2}', dwdm, bmid, bmmc);
	top.openpageOnTree(jggl_czygl_title,'52100',jggl_czygl_title,null,url,'yes',1);
}

function queryDw(){
	var dwmc = $F('dwmc');
	var dwdm = $F('dwdm');
	if(trim(dwmc)=='' && trim(dwdm)==''){
		Ext.Msg.alert(jggl_alert_title,jggl_dwdm_null,function(btn,text){
			if(btn=='ok'){
				$('dwmc').focus();
			}
		});
		return;
	}
	var url = String.format(ctx + "/system/qyjggl/jggl!initDw.do?dwmc={0}&dwdm={1}",encodeURI(encodeURI(dwmc)),dwdm);
	var baseParam = {
		url : url,
		title : jggl_grid_dw_title,
		el : 'dw',
		width : 770,
		height : 500,
		draggable : true
	};
	codeWin = new top.Wg.window(baseParam);
	codeWin.open();
}

//删除单位后，需要情况Node和Code的文本框的数据，同时将新增下级单位和新增下级机构的按钮隐藏
function clear() {
	//清空文本框的内容
	$('nodeText').value = '';
	$('nodeId').value = '';
	$('nodeType').value = '';
	//将新增下级单位和新增下级机构的按钮隐藏
	hideBtn();
}