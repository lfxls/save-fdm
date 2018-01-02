Ext.namespace("ycl.Widgets");

Wg = ycl.Widgets;

/**
  _config={url:url,fileType:type,el:el}
*/
Wg.FileUpload = function(_config){
  Ext.apply(this, _config);
  Ext.apply(this, {
	  uploadForm : '',
	  formWindow : ''
  });
  
};
Ext.apply(Wg.FileUpload.prototype,
{
	init : function(uploadCallback){
		var obj = this;
		if(!this.url) {
			alert(Wg_FileUpload_url_null);
			return;
		}
		
		if(!this.el) {
			alert(Wg_FileUpload_el_null);
		}
		
		if(!this.fileType) {
			alert(Wg_FileUpload_fileType_null);
		}
		
		this.uploadForm = new Ext.form.FormPanel({
			renderTo: this.el,
	        fileUpload : true,
	        frame: true,
	        labelAlign: 'right',   
	        baseCls: 'x-plain',   
	        labelWidth: 145, 
	    	waitMsg : Wg_FileUpload_uploadForm_waitMsg,
	        items: [{      
	            fieldLabel : Wg_FileUpload_uploadForm_fieldLabel1+this.fileType+Wg_FileUpload_uploadForm_fieldLabel2,
	            xtype:'fileuploadfield',   
	            id : 'form-file',   
	            name : 'upload',
	            width : 300,
	            allowBlank:false
	        }]   
	    }); 
		 //一次只能添加或者修改一条记录.   
	    //if(this.formWindow!=null){   
	    //	this.formWindow.destroy();   
	    //}   
	    this.formWindow = new Ext.Window({   
	        title: Wg_FileUpload_formWindow_title,   
	        width: 500,   
	        height: 105,   
	        minWidth: 200,   
	        minHeight: 200,   
	        layout: 'fit',   
	        plain:true,   
	        bodyStyle:'padding:5px;',   
	        buttonAlign:'center',   
	        items: this.uploadForm,   
	        buttons: [{   
	            text: Wg_FileUpload_formWindow_buttons_text,   
	            handler: function() {   
	                var fileName = Ext.getDom('form-file').value; 
	            	if (obj.checkFileType(fileName)) {
		            	if (obj.uploadForm.getForm().isValid()) {
		                	obj.uploadForm.getForm().submit({
		                		url: obj.url, 
		                		method:'POST',
		                		//params:{fileName:fileName},
		                    	waitMsg : Wg_FileUpload_formWindow_buttons_waitMsg,
		                        success : function(fp,o){
		                			obj.formWindow.hide();  
			                        var res = o.result.info;
			                    	Wg.alert(ExtTools_alert_title,res,function(res){
			                    		obj.formWindow.hide(); 
			                    		if(uploadCallback)
			                    			uploadCallback(res);
			                    	});
		                        },
		                        failure :  function(fp,o){
		                			obj.formWindow.hide();   
			                        var res = o.result.info;
			                    	Wg.alert(ExtTools_alert_title,res,function(res){
			                    		obj.formWindow.hide(); 
			                    	});
		                        }
		                    }); 
		                } 
	                }else {   
	                   return;
	                }   
	            }   
	        },{   
	            text: Wg_FileUpload_formWindow_buttons_cancel,   
	            handler: function() {   
	        		
	                obj.formWindow.hide();   
	            }   
	        }]   
	    }); 
	    this.uploadForm.hide();
	    this.formWindow.hide(); 
	},
	checkFileType : function(value) {
		var start = value.lastIndexOf('.');
	    var end = value.length;
	    var f = value.substring((start+1),end);
	    if(f==this.fileType){
	    	return true;
	    }else{
	    	Wg.alert(Wg_FileUpload_checkFileType_alert_title,Wg_FileUpload_uploadForm_fieldLabel1+this.fileType+Wg_FileUpload_uploadForm_fieldLabel2);
	    	return false;
	    }
	},
	showWin : function(){
		this.uploadForm.show();
		this.formWindow.show();   
	}
});
 

