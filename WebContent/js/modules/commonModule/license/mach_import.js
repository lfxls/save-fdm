Ext.onReady(function() {
	var extFileUpload = new Wg.ExtFileUpload({
		id : "uploadFile",
		name : "uploadFile",
		fieldLabel : '硬件信息',
		anchor : '95%'
	});
	
	var uploadFormPanel = new Ext.FormPanel({
		renderTo : 'uploadForm',
		fileUpload : true,
		width : 580,
		frame : true,
		autoHeight : true,
		bodyStyle : 'padding: 10px 10px 0px 10px;',
		labelWidth : 60,
		items : [extFileUpload],
		buttons : [{
			text : '导入',
			handler : function() {
				if (uploadFormPanel.getForm().isValid()) {
					//客户编号
					var clientNo = $F('clientNo');
					uploadFormPanel.getForm().submit({
						url : ctx + '/common/license/licMgt!importMachInfo.do?clientNo=' + clientNo,
						success : function(form, action) {
							//导入成功
							parent.Wg.alert(action.result.msg, function() {
								//刷新客户信息
								parent.clientGrid.reload();
								parent.win.close();
							});
						},
						failure : function(form, action) {
							//导入失败
							Wg.alert(action.result.msg);
						}
					});
				}
			}
		}]
	});
});
