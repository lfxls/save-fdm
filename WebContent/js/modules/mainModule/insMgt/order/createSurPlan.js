var menuId = '12200';
Ext.onReady(function() {
	
});

/**
 * 用户选择
 */
function queryCust(){
	var url = String.format(ctx + '/main/insMgt/insPlan!initCust.do');
	var baseParam = {
		url : url,
		title : main.insMgt.plan.win.title.cust,
		el : 'queryCust',
		width : 620,
		height : 380,
		draggable : true
	};
	Win = new Wg.window(baseParam);
	Win.open();
}





/**
 * 勘察计划保存校验
 */
function saveValidate(){
	
	//用户
	if(trim($('cno').value) == ''){
		Ext.Msg.alert(main.insMgt.plan.valid.tip,
				main.insMgt.plan.valid.cust.select, function(btn,text){
			if(btn=='ok'){
				$('cno').focus();
			}
		});
		return false;
	}
	
	return true;
}

/**
 * 保存勘察计划
 */
function save(){
	if(!saveValidate()) {
		return;
	}
	var zh = '';
		zh = '' + ',' +
		$F('cno') + ',' + $F('cname') + ',' + 
		$F('addr') + ',' + $F('mno') + ',' +
		$F('uid') + ',' +
		$F('uname') + ',' + 
		'0';
	if(parent.$('sczh').value == '') {
		parent.$('sczh').value = zh;
	} else {
		parent.$('sczh').value = parent.$('sczh').value + ';' + zh;
	}
	parent.surPQuery();
	parent.win.close();
}
