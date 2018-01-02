var menuId = '123456';

//保存客户信息
function saveClient(optType){
	if('add' == optType)
		addClient();
	if('edit' == optType)
		editClient();
}

//新增客户
function addClient(){
	//验证各个字段信息
	if(dataValidate())
		return;
	var p = {
		clientNo: $F('clientNo'),
		clientName: $F('clientName'),
		contact: $F('contact'),
		countryNo: $F('countryNo'),
		address: $F('address'),
		optObj: 'client',
		logger: '新增客户' + $F('clientNo')
	};
	parent.Wg.confirm('确定要新增客户？', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + addOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新客户列表
					parent.clientGrid.reload();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});
}

//编辑客户
function editClient(){
	//验证各个字段信息
	if(dataValidate())
		return;
	var p = {
		clientNo: $F('clientNo'),
		clientName: $F('clientName'),
		contact: $F('contact'),
		countryNo: $F('countryNo'),
		address: $F('address'),
		optObj: 'client',
		logger: '编辑客户' + $F('clientNo')
	};
	parent.Wg.confirm('确定要编辑客户？', function() {
		dwrAction.doDbWorks('licMgtAction', menuId + updOpt, p, function(res) {
			parent.Wg.alert(res.msg, function() {
				if (res.success) {
					//刷新客户列表
					parent.clientGrid.reload();
					//关闭窗口
					parent.win.close();
				}
			});
		});
	});
}

//数据验证
function dataValidate(){
	if($F('clientNo') == ''){
		msgShow('客户编号不能为空！', $('clientNo'));
		return true;
	}
	if($F('clientName') == ''){
		msgShow('客户名称不能为空！', $('clientName'));
		return true;
	}
}

//取消
function cancelWin(){
	parent.win.close();
}