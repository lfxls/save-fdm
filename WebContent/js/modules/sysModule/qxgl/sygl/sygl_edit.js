var menuId = '52400';

Ext.onReady(function() {
//	if($F('syid') != '' || null == $F('syid')){
//		$('symc').readOnly = "true";
//		$('symc').cssStyle = "background-color:#cccccc";
//	} else {
//		alert();
//		$('symc').readOnly = "false";
//	}
});

//保存首页
function saveSy(){
	//如果大钩，默认首页
	if($('sylx').checked){
		$('xtmr').value = '01';
	}else{
		$('xtmr').value = '02';
	}
	
	if($E($F('syid'))) {
		addSy();
	}else{
		editSy();
	}
}

//新建
function addSy() {
	var czid = '01';
	if(valid(czid)) {
		
		dwrAction.doAjax('syglAction', 'existingNm', {symc:$F('symc'), appLang:$F('lang')}, function(res) {
			if (res.success) {
				$('symc').focus();
				Wg.alert(sysModule_qxgl_sygl.resourceBundle.Prompt.mccf);
			} else {
				var p = $FF('sygl');
				Ext.apply(p,{logger:sysModule_qxgl_sygl.resourceBundle.Logger.add + $F('symc')});
				Wg.confirm(sysModule_qxgl_sygl.resourceBundle.Confirm.add, function(){
					dwrAction.doDbWorks('syglAction',menuId + czid, p, function(res){
						Wg.alert(res.msg, function(){
							if(res.success) {
								parent.reloadGrid();
							 	parent.win.close();
							}
						});
					});
				});				
			}
		});
	}
}

//编辑
function editSy() {
	var czid = '02';
	if(valid(czid)) {
		var p = $FF('sygl');
		Ext.apply(p,{logger:sysModule_qxgl_sygl.resourceBundle.Logger.edit + $F('symc')});
		Wg.confirm(sysModule_qxgl_sygl.resourceBundle.Confirm.edit, function(){
			dwrAction.doDbWorks('syglAction', menuId + czid, p, function(res){
				Wg.alert(res.msg, function(){
					if(res.success) {
						parent.reloadGrid();
					 	parent.win.close();
					}
				});
			});
		});	
	}
}

//验证合法
function valid(czid) {
	if($E($F('symc'))) {//首页名称
		_errorMsg(sysModule_qxgl_sygl.resourceBundle.Text.warn, sysModule_qxgl_sygl.resourceBundle.Validate.symc, 'symc');
		return false;
	}

	if($E($F('url'))) {//URL
		_errorMsg(sysModule_qxgl_sygl.resourceBundle.Text.warn, sysModule_qxgl_sygl.resourceBundle.Validate.url, 'url');
		return false;
	}			
	return true;
}

//error message 
function _errorMsg(errTitle, errContent, errId){
	Ext.Msg.alert(errTitle,errContent, function(btn,text){
		if(btn == 'ok'){
			$(''+errId+'').focus();
		}
	});
}
