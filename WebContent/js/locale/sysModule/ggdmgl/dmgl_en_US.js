Ext.ns("ami.systemModule.ggdmgl");

Ext.ns("systemModule.ggdmgl");

systemModule.ggdmgl.dmgl = function(){};
systemModule.ggdmgl.dmgl.prototype.resourceBundle = {
		Text:{
		},
		Grid:{
			cz:'Operate',
			xg:'Edit',
			sc:'Delete',
			xz:'Add',
			dm_sort_cm:{
				dmflmc:'Category Name',
				dmfl:'Category Code'
			},
			dmfllb:'Category List',
			dm_cm:{
				dmfl:'Code Category', 
				dmmc:'Code Name',
				dmz:'Code Value',
				disp_sn:'Sort',
				isshow:'Is Show',
				yes : 'Yes',
				No : 'No'
			},
			dmlb:'Code List'
		},
		Button:{
		},
		Prompt:{
			dmfl:'Please select code category.'
		},
		Error:{
		},
		Remark:{
		},
		Title:{
			editdmfl:'Edit Category',
			editdmv:'Edit Code',
			addmv:'Add Code',
			adddmfl:'Add Category'
		},
		Validate:{
			
		},
		Logger:{
			delfl1:'Delete category:',
			delfl2:'and related code',
			delvalue:'Delete code:'
		},
		Confirm:{
			delfl:'Confirm to delete the category?',
			delvalue:'Confirm to delete the code?'
		}
};
var systemModule_ggdmgl_dmgl = new systemModule.ggdmgl.dmgl();