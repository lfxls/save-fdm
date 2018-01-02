Ext.onReady(function() {
	var lang = ctxLang+'.xls';
	var type = $F('qzlx');
//	var fieldLabels = "";
	
	var templatePath = "/jsp/commonModule/qz/";
	var Upload = new Wg.ExtFileUpload({
		id : "Upload",
		name : "Upload",
		fieldLabel : type=='bj'?batchImport_meter:batchImport_terminal,
		width : 320
	});
	var imp=type=='bj'?'ImportMeterList_':"ImportTerminalList_";
	var allArray = [];
		allArray.push({
			layout : "column",
			height : 45,
			items : [{
				width : 500, // 该列有整行中所占百分比
				layout : "form", // 从上往下的布局
				items : Upload
			},{
				width : 80,
				layout : "form",
				xtype : 'label',
				html : '<a href="' + ctx + templatePath + imp+ lang + '">' + template_text + '</a>'
			} ]
		});
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'uploadForm',
		fileUpload : true,
		width :	680,
		frame : true,
		autoHeight : true,
		bodyStyle : 'padding: 0px 0px 0px 0px;',
		layout : "form",
		labelWidth : 140,
		labelAlign : "right",
		defaults : {
			anchor : '90%',
			allowBlank : true,
			msgTarget : 'side'
		},
		items : allArray,
		buttonAlign : 'center',
		buttons : [{
			text : dadr_add_dr,
			cls:"ext_btn",
			handler : function() {
				if (uploadFormPanel.form.isValid()) {
					var fileNum = 0;
					var extName = "";
						var FilePath = Upload.getValue();
						if (FilePath != "") {
							fileNum++;
							extName = FilePath.substring(FilePath.lastIndexOf("."),FilePath.length);
						}
					//判断是否有选择导入文件
					if (fileNum > 1) {
						Wg.alert(dadr_add_max);
						return;
					}

					// 只能导入excel文件
					if (extName.toUpperCase() == ".XLS") {
						Ext.Msg.wait(
							dadr_add_msg,
							dadr_add_ts,{
								interval : 10,
								duration : 90 * 1000,
								increment : 300
							}
						);
						uploadFormPanel.form.doAction('submit',{
							url : ctx+ '/qzsz!batchUpload.do',
							success : function(uploadFormPanel,action) {
									// 隐藏进度条提示
									Ext.Msg.hide();
									var ids = action.result.msgType;
									parent.query("dr",ids);
									parent.win.close();
							},failure : function(form,action) {
								// 隐藏进度条提示
								Ext.Msg.hide();
							}
						});
					} else {
						Wg.alert(dadr_add_excelType);
					}
				}
			}
		}, {
			text : dadr_add_reset,
			cls:"ext_btn",
			handler : function() {
				uploadFormPanel.form.reset();
			}
		} ]
	});
});







