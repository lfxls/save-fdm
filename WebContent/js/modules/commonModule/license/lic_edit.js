var menuId = '123456';

//保存license信息
function saveLic(optType){
	if('add' == optType)
		addLic();
	if('edit' == optType)
		editLic();
}

//新增license
function addLic(){
	//验证各个字段信息
	if(dataValidate())
		return;
	var p = {
		clientNo: $F('clientNo'),
		meterNum: $F('meterNum'),
		expDate: $F('expDate'),
		optObj: 'license',
		logger: '新增license'
	};
	parent.Wg.confirm('确定要新增license？', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + addOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新客户列表
					parent.licGrid.reload({clientNo: $F('clientNo')});
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});
}

//编辑license
function editLic(){
	//验证各个字段信息
	if(dataValidate())
		return;
	var p = {
		licNo: $F('licNo'),
		meterNum: $F('meterNum'),
		expDate: $F('expDate'),
		optObj: 'license',
		logger: '编辑license' + $F('licNo')
	};
	parent.Wg.confirm('确定要编辑license？', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + updOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新客户列表
					parent.licGrid.reload({clientNo: $F('clientNo')});
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});
}

//数据验证
function dataValidate(){
	/*if($F('meterNum') == '' && $F('expDate') == ''){
		msgShow('电表个数和过期时间不能同时为空！', $('meterNum'));
		return true;
	}*/
	if($F('meterNum') != '') {
		if(!checkNum($F('meterNum'))){
			msgShow('电表个数必须大于0！', $('meterNum'));
			return true;
		}
	}
}

//取消
function cancelWin(){
	parent.win.close();
}

//验证正整数
function checkNum(obj){
	var re = /^[1-9]\d*$/;
	if (re.test(obj)){
        return true;
    }
	return false;
} 
