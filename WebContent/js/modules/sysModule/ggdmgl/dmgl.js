var menuId = '54100';
var codeGrid="";
var categoryGrid="";
var dm_grid_url = ctx + '/system/ggdmgl/dmgl!queryDetail.do';
var dm_sort_grid_url = ctx + '/system/ggdmgl/dmgl!query.do';
var cateCode = "";
var cateName = "";
Ext.onReady(function(){
	hideLeft();
	//编码分类的grid
	var dm_sort_cm = [ {
			header: systemModule_ggdmgl_dmgl.resourceBundle.Grid.cz,
			dataIndex:'OP',
			width:80,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title='+systemModule_ggdmgl_dmgl.resourceBundle.Grid.xg+' href="javascript:initEditCodeSort(\'{0}\');"></a>',
								record.data.CATECODE);
				url += String.format('<a class="del" title='+systemModule_ggdmgl_dmgl.resourceBundle.Grid.sc+' href="javascript:delCodeSort(\'{0}\');"></a>',
								record.data.CATECODE);
				return url;
			}
		},
		{
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_sort_cm.dmflmc,
			dataIndex : 'CATENAME',
			width : 200,
			sortable : true
		},
		{
			id:'CATECODE',
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_sort_cm.dmfl,
			dataIndex : 'CATECODE',
			width :200,
			sortable : true
		}/*,{
			dataIndex : 'SORTID',
			hidden : true
		}*/];
	
		var dmSortToolbar = [{
	        id: 'add-buton',
			text:systemModule_ggdmgl_dmgl.resourceBundle.Grid.xz,
	        tooltip:systemModule_ggdmgl_dmgl.resourceBundle.Grid.xz,
	        iconCls:'add',
	        handler: function(){
	        	initEditCodeSort('');
	        }
	    }];

		categoryGrid = new Wg.Grid( {
			url : dm_sort_grid_url,
			el : 'p_code_sort_grid',
			frame: true,
			title : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dmfllb,
			heightPercent : 0.85,
			widthPercent : 0.5,
			margin : 22,
			tbar:dmSortToolbar,
			/*autoExpandColumn:'CATECODE',*/
			cModel : dm_sort_cm,
			canResize:false
		});
		
		//编码的grid
		var dm_cm = [ {
			header:systemModule_ggdmgl_dmgl.resourceBundle.Grid.cz,
			dataIndex:'OP',
			width:80,
			renderer : function(value, cellmeta, record) {
				var url = String.format('<a class="edit" title='+systemModule_ggdmgl_dmgl.resourceBundle.Grid.xg+'  href="javascript:initEditCode(\'{0}\',\'{1}\',\'{2}\');"></a>',
						record.data.CATECODE,record.data.CODEVALUE,record.data.CATENAME);
				url += String.format('<a class="del" title='+systemModule_ggdmgl_dmgl.resourceBundle.Grid.sc+' href="javascript:delCode(\'{0}\',\'{1}\');"></a>',
						record.data.CATECODE,record.data.CODEVALUE);
				return url;
			}
		},
		{
			id: 'CATECODE',
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.dmfl,
			dataIndex : 'CATECODE',
			width : 150,
			sortable : true
		},
		{
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.dmmc,
			dataIndex : 'CODENAME',
			width : 150,
			sortable : true
		},
		{
			id:'CODEVALUE',
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.dmz,
			dataIndex : 'CODEVALUE',
			width : 150
		},
		{
			header : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.disp_sn,
			dataIndex : 'DISP_SN',
			width : 70
		},{
			dataIndex : 'CATENAME',
			hidden : true
		},/*{
			dataIndex : 'ID',
			hidden : true
		} ,{
			dataIndex : 'CODE_SORT_ID',
			hidden : true
		} ,*/{
			header:systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.isshow,
			dataIndex:'ISSHOW',
			width:70,
			renderer : function(value, cellmeta, record) {
				var u;
				if(record.data.ISSHOW == "1"){
					u=systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.yes;
				}else{
					u=systemModule_ggdmgl_dmgl.resourceBundle.Grid.dm_cm.No;
				}
				return u;
			}
		}];
		
		var dmToolbar = [{
	        id: 'add-buton',
			text:systemModule_ggdmgl_dmgl.resourceBundle.Grid.xz,
	        tooltip:systemModule_ggdmgl_dmgl.resourceBundle.Grid.xz,
	        iconCls:'add',
	        handler: function(){
	        	initEditCode(cateCode, '', cateName);
	        }
	    }];

		codeGrid = new Wg.Grid( {
			url : dm_grid_url,
			el : 'p_code_grid',
			frame: true,
			title : systemModule_ggdmgl_dmgl.resourceBundle.Grid.dmlb,
			heightPercent : 0.85,
			widthPercent : 0.5,
			margin : 22,
			tbar:dmToolbar,
			//autoExpandColumn:'CODEVALUE',
			cModel : dm_cm,
			canResize:false
		});
		
		categoryGrid.init({lang:$F('yy')});
		codeGrid.init({lang:$F('yy')});
		
		categoryGrid.grid.addListener('rowclick', function(grid, rowIndex, e) {
			//根据分类进行查询
			var value= grid.getStore().getAt(rowIndex).get('CATECODE');
			cateName = grid.getStore().getAt(rowIndex).get('CATENAME');
			//记录当前选中的分类
			cateCode = value;
			var p ={
				lang: $F('yy'),
				cateCode: value,
				cateName: cateName
			};
			codeGrid.reload(p);
		});	
});


//根据查询条件从新加载两个grid
function query(){
	//编码分类加载
	var p = {
		lang: $F('yy'),
		name: $F('dmflmc'),
		cateCode: $F('cateCode')  
	};
	//cateCode = "";
	//加载gird
	categoryGrid.reload(p);
	codeGrid.reload(p);
}


//切换语言的时候，查询一次，并且把分类值清空
function changeLang(){
	//编码分类加载
	var p = {
		lang: $F('yy'),
		name: $F('dmflmc'),
		cateCode: $F('cateCode')  
	};
	cateCode = "";
	//加载gird
	categoryGrid.reload(p);
	codeGrid.reload(p);
	//$('cateCode').value = '';
}

//初始化分类编辑窗口
function initEditCodeSort(cateCode){
	  var lang = $F('yy');
	  var url = String.format(
		  ctx + '/system/ggdmgl/dmgl!initEditCodeSort.do?cateCode={0}&lang={1}',cateCode,lang);
	  var title;
	  if(cateCode == ''){
		  title=systemModule_ggdmgl_dmgl.resourceBundle.Title.adddmfl;
	  }else{
		  title=systemModule_ggdmgl_dmgl.resourceBundle.Title.editdmfl;
	  }
	  var baseParam = {
		url : url,
		el:'editDmSort',
		title : title,
		width : 380,
		height : 220,
		draggable : true
     };
	 AdvWin = new Wg.window(baseParam);
	 AdvWin.open();
}

//初始化编码编辑窗口
function initEditCode(cateCode,value,cateName){
	  //如果分类编码为空，提示信息
	  if($E(cateCode)){
	  	Wg.alert(systemModule_ggdmgl_dmgl.resourceBundle.Prompt.dmfl);
	  	return false;
	  }
	  
	  var lang = $F('yy');
	  var url = String.format(
		  ctx + '/system/ggdmgl/dmgl!initEditCode.do?cateCode={0}&value={1}&lang={2}&cateName={3}',cateCode,value,lang,cateName);
	  var title;
	  if(value == ''){
		  title=systemModule_ggdmgl_dmgl.resourceBundle.Title.addmv;
	  }else{
		  title=systemModule_ggdmgl_dmgl.resourceBundle.Title.editdmv;
	  }
	  var baseParam = {
		url : url,
		el:'editDm',
		title : title,
		width : 600,
		height : 250,
		draggable : true
	 };
	 AdvWin = new Wg.window(baseParam);
	 AdvWin.open();
}

//删除一条分类数据，以及级联的编码数据
function delCodeSort(cateCode){
	//删除分类
	var p = {
		cateCode : cateCode,
		lang:$F('lang'),
		type:'dmfl',
		logger : systemModule_ggdmgl_dmgl.resourceBundle.Logger.delfl1+cateCode
	};
	
	Wg.confirm(systemModule_ggdmgl_dmgl.resourceBundle.Confirm.delfl, function() {
		dwrAction.doDbWorks('dmglAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					queryCodeSort();
				}
			});
		});
	});
}

//删除一条编码数据
function delCode(cateCode, value){
	var p = {
		cateCode : cateCode,
		lang:$F('lang'),
		value: value,
		type:'dmxx',
		logger : systemModule_ggdmgl_dmgl.resourceBundle.Logger.delvalue+cateCode
	};
	
	Wg.confirm(systemModule_ggdmgl_dmgl.resourceBundle.Confirm.delvalue, function() {
		dwrAction.doDbWorks('dmglAction', menuId + delOpt, p, function(res) {
			Wg.alert(res.msg, function() {
				if (res.success) {
					queryCode();
				}
			});
		});
	});
}

//删除后的更新grid
function queryCode(){
	//代码分类加载
	var p = {
		lang:$F('lang'),
		cateCode:cateCode
	};
	//加载gird
	codeGrid.reload(p);
}

//删除分类后刷新页面
function queryCodeSort(){
	var p = {
		lang: $F('lang'),
		name: $F('name'),
		cateCode: $F('cateCode')
	};
	//加载gird
	categoryGrid.reload(p);
	codeGrid.reload(p);
}
