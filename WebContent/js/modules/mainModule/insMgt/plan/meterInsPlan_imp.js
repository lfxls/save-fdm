Ext.onReady(function() {
	hideLeft();
	var templatePath = '/jsp/mainModule/insMgt/plan/template/';
	
	// 新装表计
	var addMInsPUpload = new Wg.ExtFileUpload({
		id : 'addMInsPFile',
		name : 'addMInsPFile',
		fieldLabel : main.insMgt.plan.label.addMInsP,
		width : 400
	});
	
	// 更换表计
	var chanMInsPUpload = new Wg.ExtFileUpload({
		id : 'chanMInsPFile',
		name : 'chanMInsPFile',
		fieldLabel : main.insMgt.plan.label.chanMInsP,
		width : 400
	});
	
	// 拆回表计
	var remMInsPUpload = new Wg.ExtFileUpload({
		id : 'remMInsPFile',
		name : 'remMInsPFile',
		fieldLabel : main.insMgt.plan.label.remMInsP,
		width : 400
	});
	
	//表计部分
	var meterImportArray = [{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : addMInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Meter_Installation_'
					+ lang
					+ '">'
					+ main.insMgt.plan.text.templatedown
					+ '</a>'
		}]
	},{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : chanMInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Meter_Replacement_'
					+ lang
					+ '">'
					+ main.insMgt.plan.text.templatedown
					+ '</a>'
		}]
	},{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : remMInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Meter_Uninstallation_'
					+ lang
					+ '">'
					+ main.insMgt.plan.text.templatedown
					+ '</a>'
		}]
	}];
	
	//表计导入部分
	var meterArray = [{
		layout : "column",
		items : [ {
			xtype : 'fieldset',
			collapsible : true,
			autoHeight : true,
			width : 820,
			items : meterImportArray
		} ]
	}];
	
	var allArray = [meterArray];
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'mInsPlanImpForm',
		fileUpload : true,
		width :	890,
		frame : true,
		title :  main.insMgt.plan.title.meterInsPlan,
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
			text : main.insMgt.plan.btn.imp,
			cls : 'ext-button',
			handler : function() {
				if (uploadFormPanel.form.isValid()) {
					var fileArray = [addMInsPUpload.getValue(), chanMInsPUpload.getValue(), remMInsPUpload.getValue()];
					
					if(validateFile(fileArray)){
						Ext.Msg.wait(
								main.insMgt.plan.text.imp.msg,
								main.insMgt.plan.imp.tip,
								{
									interval : 10,//每0.01秒更新一次
									increment : 300 //300次更新完毕
//									duration : 90 * 1000//进度条持续更新90秒
//									text: '进度'  //进度条文字 
								}
						);
						
						uploadFormPanel.form.doAction('submit',{
							url : ctx + '/main/insMgt/plan/insPlan!importExcel.do',
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
								Wg.alert(main.insMgt.plan.valid.imp.fail, function() {
									// 隐藏进度条提示
									Ext.Msg.hide();
								});
							}
						});
					}
				}
			}
		}, {
			text : main.insMgt.plan.btn.reset,
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
					Wg.alert(main.insMgt.plan.valid.selectXLS);
					return false;
				}
			}
		}
		//文件的个数
		if(fileNum == 0){
			Wg.alert(main.insMgt.plan.valid.selectFile);
			return false;
		}
		
		if(fileNum > 1){
			Wg.alert(main.insMgt.plan.valid.selectOne);
			return false;	
		}
		
		return true;
	}else{
		Wg.alert(main.insMgt.plan.valid.selectFile);
		return false;
	}
}

/**
 * 导入结果显示窗口
 */
function showImpResult(msgType, msg) {
	if(msgType=='01'){
		msg = '<b>'+ main.insMgt.plan.hint.imp.check +'</b><br>'+msg;
	}else{
		msg = '<b>'+ main.insMgt.plan.hint.imp.result +'</b><br>'+msg;
	}
	Ext.DomHelper.append(document.body, {id:'showImpResult', tag:'div'});
	win = new Ext.Window({
		title : main.insMgt.plan.win.title.impResult,
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