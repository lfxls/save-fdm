var Module_URL = '';
var sfrdj = false;
var sfydj = false;

// 打开选择数据项编码窗体
function openWin(url ) {
	var baseParam = {
		url : url,
		title : sjxbm_sel_sjzc,
		el : 'sjxDiv',
		width : 770,
		height : 520,
		draggable : true
	};
	simpleWin = new Wg.window(baseParam);
	simpleWin.open();
}

// 初始化数据项编码树
function initTree(url) {
	var baseParam = {
		url : url,
		title : sjxbm_sel_sjzc,
		el : 'tree',
		border : true,
		width : 330,
		height : 205
	};
	var simpleTree = new Wg.Tree(baseParam);
	simpleTree.init('0', sjxbm_sjzc, '1');
	simpleTree.tree.addListener("click", treeHander);
}

//初始化数据分类树
function initTreeSjfl(url) {
	var baseParam = {
		url : url,
		title : sjfl_sel_sjzc,
		el : 'sjflTree',
		border : true,
		width : 330,
		height : 155
	};
	var simpleTree = new Wg.Tree(baseParam);
	simpleTree.init('root', sjfl_sjzc, '1');
	simpleTree.tree.addListener("click", changeSjfl);
}


//切换数据分类
function changeSjfl(node) {
	$('sjfl').value = node.id;
	cleanList();
	$("tree").innerHTML = "";
	url = String.format(Module_URL + "querySjxbm.do?zdgylx={0}&sjfl={1}&sfcy={2}&zdjh={3}", $F('zdgylx'), node.id, $F('sfcy'), $F('zdjh'));
	initTree(url);
}

// 点击树事件
function treeHander(node) {
	var str = getAllChildrenNodes(node);	
	var zdgylx = $F('zdgylx');
	var sjfl = $F('sjfl');
	
	//如果国网下的DLMS中继的日冻结，则清空已经选择
	if(("02" == zdgylx && '70' ==sjfl && node.isLeaf() == false && node.id == 'rdj') 
			||("02" == zdgylx && '70' ==sjfl && node.isLeaf() && node.attributes.pid == 'rdj')) {
		if(!sfrdj) cleanList();	
		sfrdj = true;
	}else {
		if(sfrdj) cleanList();
		sfrdj = false;
	}
	
	//如果国网下的DLMS中继的月冻结，则清空已经选择
	if(("02" == zdgylx && '70' ==sjfl && node.isLeaf() == false && node.id == 'ydj') 
			||("02" == zdgylx && '70' ==sjfl && node.isLeaf() && node.attributes.pid == 'ydj')) {
		if(!sfydj) cleanList();	
		sfydj = true;
	}else {
		if(sfydj) cleanList();
		sfydj = false;
	}
	
	showDataItem(str);
	if("02" == zdgylx && ("30" == sjfl || "40" == sjfl || idDj(sjfl))) {
		deleteDayOrMonth(str);
	}
}

// 小项切换时不同类型的规约编码显示的参数不一样
function deleteDayOrMonth(text){
	var oSelect = $("sjx");
	var bllx = day_sjzc; // 保留数据类型
	var gllx = month_sjzc; // 过滤数据类型
	if(text.indexOf(month_sjzc) > -1) {
		if(text.indexOf(day_sjzc) > -1) {
			if(text.indexOf(day_sjzc) > text.indexOf(month_sjzc)) {
				bllx = month_sjzc;
				gllx = day_sjzc;
			}
		} else {
			bllx = month_sjzc;
			gllx = day_sjzc;
		}
	}
	for(var i = oSelect.options.length - 1; i >= 0; i--) {
		var oOption = oSelect.options[i];
		if(oOption.innerText.indexOf(gllx) > -1) {
			if(oOption.innerText.indexOf(bllx) > -1) {
				if(oOption.innerText.indexOf(bllx) > oOption.innerText.indexOf(gllx)) {
					oSelect.removeChild(oSelect.options[i]);
				}
			} else {
				oSelect.removeChild(oSelect.options[i]);
			}
		}
	}
}

// 获取一个节点的所有叶子子节点组成数据项字符串
function getAllChildrenNodes(node) {
	dwr.engine.setAsync(false); 
	var str = "";
	if(node.isLeaf()) {
		str += node.id + "{j,}" + node.text + "{j;}";		
	} else {
		node.expand();
		if(0 >= node.childNodes.length) {
			dwrAction.doAjax('sssjzcAction','querySjxbmByGybm',{'zdgylx':$F('zdgylx'),'gybm':node.id},function(res){
				str += res.msg;
			});
		} else {
			for(var i = 0; i < node.childNodes.length; i ++) {
				str += getAllChildrenNodes(node.childNodes[i]);
			}
		}
	}
	return str;
}
// 显示数据项
function showDataItem(str){
	var items = str.split("{j;}");
	var oSelect = $("sjx");
	for(var i = 0; i < items.length - 1; i++){
		var datas = items[i].split("{j,}");
		if(!isInList(datas[0])){
			var oOption = document.createElement("OPTION");
			oSelect.options.add(oOption);
			oOption.value = datas[0].replace("<BR>", "").replace("&nbsp", " ").replace("; ", " ");
			oOption.innerHTML = datas[1].replace("<BR>", "").replace("&nbsp", " ").replace("; ", " ");;
		}
	}
}


// 判断是否已经在列表中
function isInList(data){
	var oSelect = $("sjx");
	for(var i = 0; i < oSelect.options.length; i++){
		var oOption = oSelect.options[i];
		if(data == oOption.value){return true;}
	}
	return false;
}

// 清除列表中数据项
function cleanList(){
	dwr.util.removeAllOptions('sjx');
}

// 删除数据项
function delDataItem(){
	var oSelect = $('sjx');
	var min_sel = 0;
	for(var i = oSelect.length - 1; i >= 0; i --) {
		if(oSelect.options[i].selected) {
			if(min_sel == 0 || i < min_sel) {
				min_sel = i;
			}
			oSelect.options[i] = null;
		}
	}
	var len = oSelect.length;
	if (len > 0) {
		if (min_sel >= len) {
			min_sel = len - 1;
		}
		oSelect.options[min_sel].selected = true;
	}
}

// 数据项确定
function ok() {
//	window.parent.$("sfrdj").value = sfrdj;
//	window.parent.$("sfydj").value = sfydj;
	if (window.parent.document.getElementById("sfrdj")) {
		window.parent.document.getElementById("sfrdj").value = sfrdj;
	}
	if (window.parent.document.getElementById("sfydj")) {
		window.parent.document.getElementById("sfydj").value = sfydj;
	}
	
	var sjx = $('sjx');
	var str = "";
	for(var i = 0; i < sjx.length; i ++) {
		str += sjx.options[i].value + "{j,}";
		str += sjx.options[i].innerHTML + "{j;}";
	}
	window.parent.changeSjfl($F("sjfl"));
	window.parent.showDataItem(str);
	window.parent.showSjjgds(str);
	window.parent.win.close();
}

// 获取数据项编码(以","隔开)
function getSjxbm(sjx) {
	var sjxbm = "";
	if("undefined" != typeof(sjx)) {
		for(var i = 0; i < sjx.length; i ++) {
	   		sjxbm += sjx.options[i].value + ",";
		}
		sjxbm = sjxbm.substring(0, sjxbm.length - 1);
	}
	return sjxbm;
}

// 获取告警编码(以","隔开)
function getGjbm(sjx) {
	var gjbm = "";
	if("undefined" != typeof(sjx)) {
		for(var i = 0; i < sjx.length; i ++) {
	   		gjbm += sjx.options[i].value + ",";
		}
		gjbm = gjbm.substring(0, gjbm.length - 1);
	}
	return gjbm;
}

//获取测量点号(以","隔开)
function getCldh(record) {
	var cldh = "";
	if(record) {
		for(var i = 0; i < record.length; i ++) {
			cldh += record[i].data.CLDH + ',';
		}
		cldh = cldh.substring(0, cldh.length - 1);
	}
	return cldh;
}

//获取组合字符串(以","隔开)
function getZh(record) {
	var zh = "";
	if(record) {
		for(var i = 0; i < record.length; i ++) {
			zh += record[i].data.ZH + ',';
		}
		zh = zh.substring(0, zh.length - 1);
	}
	return zh;
}

/**
 * 国网判断数据类型id ，以3
 **/
 function idDj(str){
    var reg =/[5][0-3]/;
	return reg.test(str);
 }
 
//添加测量点号
 function addCldh() {
 	selectAllCldh('false');
 	var cldh = Ext.getCmp('cldhField').getValue();
 	if('' != cldh) {
 		cldh = validateCldh(cldh);
 		if(!cldh) {
 			return false;
 		}
 	}
 	var url = String.format(ctx + "/basic/sjzc//plsjzc!addCldh.do?cldh={0}", cldh);
 	var baseParam = {
 		url : url,
 		title : add_cld_sjzc,
 		el : 'cld',
 		width : 400,
 		height : 255,
 		draggable : true
 	};
    simpleWin = new Wg.window(baseParam);
 	simpleWin.open();
 }

 // 返回设置测量点号
 function setCldh(cldh) {
 	Ext.getCmp('cldhField').setValue(cldh);
 }

 // 全选测量点号
 function selectAllCldh(flag) {
 	if('true' == flag) {
 		Ext.getCmp('cldhField').setValue(add_cldSel_sjzc);
 		$('cldh').disabled = true;
 	} else {
 		$('cldh').disabled = false;
 		Ext.getCmp('cldhField').setValue('');
 	}
 	$('selectAllCldhFlag').value = flag;
 }
 
//验证测量点号，批量巡测中使用
function validateCldh(cldh) {
 	if('' == cldh) {
 		Wg.alert(cld_sel_sjzc);
 		return false;
 	}
 	var flag = true;
 	var cldhs = cldh.split(',');
 	for(var i = 0; i < cldhs.length; i ++) {
 		if(!isNumber(cldhs[i]) || cldhs[i] > 1000) {
 			flag = false;
 			break;
 		}
 	}
 	if(!flag) {
 		flag = true;
 		var cldhs = cldh.split('-');
 		if(2 != cldhs.length || !isNumber(cldhs[0]) || !isNumber(cldhs[1]) || parseInt(cldhs[0]) > parseInt(cldhs[1])) {
 			flag = false;
 		} else {
 			cldh = '';
 			for(var i = cldhs[0]; i <= parseInt(cldhs[1]); i ++) {
 				cldh += i + ",";
 			}
 			cldh = cldh.substring(0, cldh.length - 1);
 		}
 	}
 	if(!flag) {
 		Wg.alert(cld_error_sjzc);
 		return false;
 	}
 	return cldh;
}