loadProperties('mainModule', 'mainModule_preMgt');

Ext.onReady(function() {
	hideLeft();
	var templatePath = '/jsp/mainModule/preMgt/tokenMgt/template/';
	
	
	// 新增Token
	var addTokenUpload = new Wg.ExtFileUpload({
		id : 'addTokenFile',
		name : 'addTokenFile',
		fieldLabel : main.preMgt.tokenMgt.label.addToken,
		width : 400
	});
		
	//部分
	var tokenImportArray = [{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : addTokenUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Token_Import_'
					+ lang
					+ '">'
					+ main.preMgt.tokenMgt.text.templatedown
					+ '</a>'
		}]
	}];
	
	//Token导入部分
	var tokenArray = [{
		layout : "column",
		items : [ {
			xtype : 'fieldset',
			collapsible : true,
			autoHeight : true,
			width : 820,
			items : tokenImportArray
		} ]
	}];
	
	var allArray = [tokenArray];
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'tokenImpForm',
		fileUpload : true,
		width :	890,
		frame : true,
		title :  main.preMgt.tokenMgt.title.tokenImp,
		autoHeight : true,
		bodyStyle : 'padding: 10px 10px 0 10px;',
		layout : "form",
		labelWidth : 160,
		labelAlign : "right",
		defaults : {
			anchor : '95%',
			allowBlank : true,
			msgTarget : 'side'
		},
		items : allArray,
		buttonAlign : 'center',
		buttons : [{
			text : main.preMgt.tokenMgt.btn.imp,
			cls : 'ext-button',
			handler : function() {
				if (uploadFormPanel.form.isValid()) {
					var fileArray = [addTokenUpload.getValue()];
					
					if(validateFile(fileArray)){
						Ext.Msg.wait(
								main.preMgt.tokenMgt.text.imp.msg,
								main.preMgt.tokenMgt.imp.tip,
								{
									interval : 10,//每0.01秒更新一次
									increment : 300 //300次更新完毕
//									duration : 90 * 1000//进度条持续更新90秒
//									text: '进度'  //进度条文字 
								}
						);
						
						uploadFormPanel.form.doAction('submit',{
							url : ctx + '/main/preMgt/tokenMgt!importExcel.do',
							success : function(uploadFormPanel, action) {
								if (action.result.success == "true") {
									// 隐藏进度条提示
									Ext.Msg.hide();
									Wg.alert(action.result.errMsg);
								} else {
									// 隐藏进度条提示
									Ext.Msg.hide();
									var msgType = action.result.msgType;
									var errMsg = action.result.errMsg;
									showImpResult(msgType, errMsg);
								}
							},
							failure : function(form,action) {
								Wg.alert(main.preMgt.tokenMgt.valid.imp.fail, function() {
									// 隐藏进度条提示
									Ext.Msg.hide();
								});
							}
						});
					}
				}
			}
		}, {
			text : main.preMgt.tokenMgt.btn.reset,
			cls : 'ext-button',
			handler : function() {
				uploadFormPanel.form.reset();
			}
		}]
	});
	
});

/**
 * 导入文件校验
 * @param fileUploadLst
 */
function validateFile(fileUploadLst){
	if(fileUploadLst){
		var fileNum = 0;
		for(var i = 0; i < fileUploadLst.length; i++){
			var extName = "";
			var filePath = fileUploadLst[i];
			if (filePath != "") {
				fileNum ++;
				extName = filePath.substring(filePath.lastIndexOf("."), filePath.length);
				if(extName.toUpperCase() != ".XLS"){
					Wg.alert(main.preMgt.tokenMgt.valid.selectXLS);
					return false;
				}
			}
		}
		//文件的个数
		if(fileNum == 0){
			Wg.alert(main.preMgt.tokenMgt.valid.selectFile);
			return false;
		}
		
		if(fileNum > 1){
			Wg.alert(main.preMgt.tokenMgt.valid.selectOne);
			return false;	
		}
		
		return true;
	}else{
		Wg.alert(main.preMgt.tokenMgt.valid.selectFile);
		return false;
	}
}

/**
 * 导入结果显示窗口
 */
function showImpResult(msgType, msg) {
	if(msgType=='01'){
		msg = '<b>'+ main.preMgt.tokenMgt.hint.imp.check +'</b><br>'+msg;
	}else{
		msg = '<b>'+ main.preMgt.tokenMgt.hint.imp.result +'</b><br>'+msg;
	}
	Ext.DomHelper.append(document.body, {id:'showImpResult', tag:'div'});
	win = new Ext.Window({
		title : main.preMgt.tokenMgt.win.title.impResult,
		applyTo :'showImpResult',
		width : 550,
		height : 380,
		layout : 'fit',
		resizable : true,
		plain : true,
		animCollapse : true,
		draggable : true,
		autoScroll:true,
		html : msg,
		buttons : [{text:ExtWindow_close, iconCls:'win_close',handler:function(){win.close();}}]
	});
	win.show();
}