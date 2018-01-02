loadProperties('mainModule', 'mainModule_arcMgt');
Ext.onReady(function() {
	var templatePath = '/jsp/mainModule/arcMgt/custMgt/template/';

	// 用户导入
	var custUpload = new Wg.ExtFileUpload({
		id : "custUpload",
		name : "custUpload",
		fieldLabel : main.arcMgt.dataImp.file.title,
		width : 300
	});
	
	var meterImportArray = [];
	var allArray = [];
	meterImportArray.push({
		layout : "column",
		height : 40,
		items : [{
			width : 410, // 该列有整行中所占百分比
			layout : "form", // 从上往下的布局
			items : custUpload
		},
		{
			width : 190,
			layout : "form",
			xtype : 'label',
			html : '<a href="'
					+ ctx
					+ templatePath
					+ 'Customer_'
					+ lang
					+ '">'
					+ main.arcMgt.dataImp.href.offline
					+ '</a>'
		} ]
	});

	//表计资产部分
	allArray.push({
		layout : "column",
		items : [ {
			xtype : 'fieldset',
			//title : main_arcMgt_import_win_title,
			collapsible : true,
			autoHeight : true,
			width : "650",
			items : meterImportArray
		} ]
	});
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'uploadForm',
		fileUpload : true,
		width :	650,
		frame : true,
		title : main.arcMgt.dataImp.win.title,
		autoHeight : true,
		bodyStyle : 'padding: 10px 10px 0 10px;',
		layout : "form",
		labelWidth : 100,
		labelAlign : "right",
		defaults : {
			anchor : '95%',
			allowBlank : true,
			msgTarget : 'side'
		},
		items : allArray,
		buttonAlign : 'center',
		buttons : [{
			text : main.arcMgt.dataImp.btn.title,
			cls : 'ext-button',
			handler : function() {
				if (uploadFormPanel.form.isValid()) {
					var fileNum = 0;
					var extName = "";
					
					//表计档案
					var bjOffLineFilePath = custUpload.getValue();
					if (bjOffLineFilePath != "") {
						fileNum++;
						extName = bjOffLineFilePath.substring(bjOffLineFilePath.lastIndexOf("."),bjOffLineFilePath.length);
					}
					
					
					//判断是否有选择导入文件
					if (fileNum > 1) {
						Wg.alert(main.arcMgt.dataImp.add.max);
						return;
					}

					// 只能导入excel文件
					if (extName.toUpperCase() == ".XLS") {
						Ext.Msg.wait(
							main.arcMgt.dataImp.add.msg,
							main.arcMgt.dataImp.add.ts
							,{
								interval : 10,//每0.01秒更新一次
								increment : 300//300次更新完毕
//								
							}
						);
						uploadFormPanel.form.doAction('submit',{
							url : ctx+ '/main/arcMgt/dataImp!uploadArchives.do',
							success : function(uploadFormPanel,action) {
								if (action.result.success == "true") {
									
									Wg.alert(action.result.errMsg, function(){	
										Ext.Msg.hide();
									});
									
									
								} else {
									// 隐藏进度条提示
									Ext.Msg.hide();
									var msgType = action.result.msgType;
									var errMsg = action.result.errMsg;
									showImpResult(msgType,errMsg);
								}
							},failure : function(form,action) {
								// 隐藏进度条提示
								Ext.Msg.hide();
							}
						});
					} else {
						Wg.alert(main.arcMgt.dataImp.add.excelType);
					}
				}
			}
		}, {
			text : main.arcMgt.dataImp.btn.reset,
			cls : 'ext-button',
			handler : function() {
				uploadFormPanel.form.reset();
			}
		} ]
	});
});

// 导入结果显示窗口
function showImpResult(msgType, msg) {
	if(msgType=='01'){
		msg = '<b>'+main.arcMgt.dataImp.valid +'</b><br>'+msg;
	}else{
		msg = '<b>'+main.arcMgt.dataImp.error +'</b><br>'+msg;
	}
	Ext.DomHelper.append(document.body, {id:'showImpResult', tag:'div'});
	win = new Ext.Window({
		title : main.arcMgt.dataImp.result,
		applyTo :'showImpResult',
		width : 480,
		height : 220,
		layout : 'fit',
		resizable : true,
		plain : true,
		animCollapse : true,
		draggable : true,
		autoScroll:true,
		html : msg,
		buttons : [{text:ExtWindow_close,iconCls:'win_close',handler:function(){win.close();}}]
	});
	win.show();
}