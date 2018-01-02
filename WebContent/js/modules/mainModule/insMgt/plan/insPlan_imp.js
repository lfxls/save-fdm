Ext.onReady(function() {
	hideLeft();
	var templatePath = '/jsp/mainModule/insMgt/plan/template/';
	
	// 新装表计
	var addMInsPUpload = new Wg.ExtFileUpload({
		id : 'addMInsPFile',
		name : 'addInsPFile',
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
	
	// 新装集中器
	var addDInsPUpload = new Wg.ExtFileUpload({
		id : 'addDInsPFile',
		name : 'addDInsPFile',
		fieldLabel : main.insMgt.plan.label.addDInsP,
		width : 400
	});
	
	// 更换集中器
	var chanDInsPUpload = new Wg.ExtFileUpload({
		id : 'chanDInsPFile',
		name : 'chanDInsPFile',
		fieldLabel : main.insMgt.plan.label.chanDInsP,
		width : 400
	});
	
	// 拆回集中器
	var remDInsPUpload = new Wg.ExtFileUpload({
		id : 'remDInsPFile',
		name : 'remDInsPFile',
		fieldLabel : main.insMgt.plan.label.remDInsP,
		width : 400
	});
	
	// 新装采集器
	var addCInsPUpload = new Wg.ExtFileUpload({
		id : 'addCInsPFile',
		name : 'addCInsPFile',
		fieldLabel : main.insMgt.plan.label.addCInsP,
		width : 400
	});
	
	// 更换采集器
	var chanCInsPUpload = new Wg.ExtFileUpload({
		id : 'chanCInsPFile',
		name : 'chanCInsPFile',
		fieldLabel : main.insMgt.plan.label.chanCInsP,
		width : 400
	});
	
	// 拆除采集器
	var remCInsPUpload = new Wg.ExtFileUpload({
		id : 'remCInsPFile',
		name : 'remCInsPFile',
		fieldLabel : main.insMgt.plan.label.remCInsP,
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
	
	//集中器部分
	var dcuImportArray = [{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : addDInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Dcu_Installation_'
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
			items : chanDInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Dcu_Replacement_'
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
			items : remDInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Dcu_Uninstallation_'
					+ lang
					+ '">'
					+ main.insMgt.plan.text.templatedown
					+ '</a>'
		}]
	}];
	
	//采集器部分
	var colImportArray = [{
		layout : 'column',
		height : 30,
		items : [{
			width : 580, // 该列在整行中所占百分比
			layout : 'form', // 从上往下的布局
			items : addCInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Collector_Installation_'
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
			items : chanCInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Collector_Replacement_'
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
			items : remCInsPUpload
		},{
			width : 150,
			layout : 'form',
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Collector_Uninstallation_'
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
			title : main.insMgt.plan.title.meterInsPlan,
			collapsible : true,
			autoHeight : true,
			width : 820,
			items : meterImportArray
		} ]
	}];
	
	//集中器导入部分
	var dcuArray = [{
		layout : "column",
		items : [ {
			xtype : 'fieldset',
			title : main.insMgt.plan.title.dcuInsPlan,
			collapsible : true,
			autoHeight : true,
			width : 820,
			items : dcuImportArray
		} ]
	}];
	
	//采集器导入部分
	var colArray = [{
		layout : "column",
		items : [ {
			xtype : 'fieldset',
			title : main.insMgt.plan.title.colInsPlan,
			collapsible : true,
			autoHeight : true,
			width : 820,
			items : colImportArray
		} ]
	}];
	
	var allArray = [meterArray, dcuArray, colArray];
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'insPlanImpForm',
		fileUpload : true,
		width :	890,
		frame : true,
		title :  main.insMgt.plan.win.title.insPlan,
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
					var fileArray = [addMInsPUpload.getValue(), chanMInsPUpload.getValue(), remMInsPUpload.getValue(),
					                 addDInsPUpload.getValue(), chanDInsPUpload.getValue(), remDInsPUpload.getValue(),
					                 addCInsPUpload.getValue(), chanCInsPUpload.getValue(), remCInsPUpload.getValue()];
					
					if(validateFile(fileArray)){
						Ext.Msg.wait(
								resource.Text.msg,
								resource.Text.ts,
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
	
	msg = '<b>'+ main.insMgt.plan.hint.import.fail +'</b><br>'+ msg;
	
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