Ext.namespace("ycl.Widgets");

var excelTextList = new Array();

PageSizePlugin = function(config) {
	var config = config || {};
	
	if(config.isExport==false){
	}
	else{	
		this.excel=config.excel;
	}
	PageSizePlugin.superclass.constructor.call(this, {
		store : new Ext.data.SimpleStore({
			fields : ['text', 'value'],
			data : [['10', 10], ['20', 20], ['30', 30],
			        ['50', 50], ['100', 100],['200', 200],['300', 300],['500', 500],['1000', 1000],['1500', 1500],['2000', 2000]]
		}),
		mode : 'local',
		displayField : 'text',
		valueField : 'value',
		editable : false,
		allowBlank : false,
		value:50,
		triggerAction : 'all',
		width : 60

	});
};

Ext.extend(PageSizePlugin, Ext.form.ComboBox, {
	perPage : "",
	perPageRecord : "",
	init : function(paging) {
		paging.on('render', this.onInitView, this);
	},

	onInitView : function(paging) {
		if (this.excel) {
			paging.insert(11,'-', this.perPage, this, this.perPageRecord,'-',this.excel.ui);
		} else {
			paging.insert(11,'-', this.perPage, this, this.perPageRecord);
		}
		
		this.setValue(paging.pageSize);
		this.on('select', this.onPageSizeChanged, paging);
	},

	onPageSizeChanged : function(combo) {
		this.pageSize = parseInt(combo.getValue());
		this.doLoad(0);
	}
});
// Ext Grid by ycl 2010/4 by Ext3.1
Wg = ycl.Widgets;
/*
 * 初始化参数说明
 * _cm = [{header : _head, dataIndex : _id, renderer : _fn(value), sortable : * true }] //列对象
 * _cfg = {url:_URL, //读数据源URL
 * 		   el:'grid', //元素
 *         title:'Grid', //title
 *         height:350,
 *         width:600,
 *         cModel:_cm, //列队像
 *         idProperty : 'id', //id列 默认第一列(删除依赖的唯一标识)
 *         sort:'id', //排序列ID 默认ID
 *         order:'desc', //排序  默认 ASC
 *         hasTbar:true, //是否按钮栏 
 *         pageSize:30, //分页条数 默认30
 *         fileUrl: fileUrl, //生成数据源URL(可选)
 * 		   excelUrl : excelUrl //导出ExcelUrl(可选)
 * 		   excelParam : excelParam  //导出Excel的条件
 * 		   qzfl:qzfl //加入群组群组分类
 * 		   colNum:0 //唯一性标识列
 * 		   checkOnly 默认true（是否选择checkbox才算选中）
 * 		   canResize 默认true（是否根据页面重新变换） 
 * 		   orderToFile 按顺序加载数据库中的列名（,隔开）
 * 		   heightPercent 高度百分比 默认0.75
 * 		   resultColNum:结果列是否有删除成功/删除
 * 		   helpText:说明文字显示在分页栏上(可选)
 *         plugins:扩展组件，比如多行复合表头的支持
 *         autoExpandColumn:自动扩展宽度的列
 *         layout:布局方式
 *         tbar： 工具栏
 *         stripeRows:true/false斑马线效果
 *         pageNotSupport:true/false分页支持
 *         name://打开不同的tab页，session中存在的文件名根据模块名来区分,
 *         colModel:可支持表头扩展
 *         headerExtend:扩展
 *         qzlx:表计群组或者终端群组
 *         notLockColumn:true/false 不支持锁定列 默认为支持
 *         }
 */
Wg.Grid = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
				sm : '',
				cm : '',
				ds : '',
				grid : '',
				allSelected : false, //全选标志
				paging:''
			});
};

function initExcelPlugin(types) {
	var me = {};
	var menuItem = [];
	
	//export word
	if (gridExportDoc.indexOf('word')!=-1) {
		menuItem.push({	
			text : Wg_Grid_excel_menu_text,
			iconCls:"doc",
			tooltip : Wg_Grid_excel_menu_text,
			handler : function() {
				exportDocument(me,"word",false);
			}
		 });
		
		//export pdf all
		menuItem.push({
			text : Wg_Grid_excel_menuAll_text,
			tooltip : Wg_Grid_excel_menuAll_text,
			iconCls:"doc",
			handler : function() {
				exportDocument(me,"word",true);
			}
		 });
	}
	
	//export pdf
	if (gridExportDoc.indexOf('pdf')!=-1) {
		menuItem.push({
			text : Wg_Grid_excel_menu_text,
			iconCls:"pdf",
			tooltip : Wg_Grid_excel_menu_text,
			handler : function() {
				exportDocument(me,"pdf",true);
			}
		 });
		
		//export pdf all
		menuItem.push({
			text : Wg_Grid_excel_menuAll_text,
			tooltip : Wg_Grid_excel_menuAll_text,
			iconCls:"pdf",
			handler : function() {
				exportDocument(me,"pdf",true);
			}
		 });
	}
	
	//export txt
	if (gridExportDoc.indexOf('txt')!=-1) {
		menuItem.push({
			text : Wg_Grid_excel_menu_text,
			iconCls:"note",
			tooltip : Wg_Grid_excel_menu_text,
			handler : function() {
				exportDocument(me,"txt",false);
			}
		});
		
		//export txt all
		menuItem.push({
			text : Wg_Grid_excel_menuAll_text,
			tooltip : Wg_Grid_excel_menuAll_text,
			iconCls:"note",
			handler : function() {
				exportDocument(me,"txt",true);
			}
		 });
	}
	
	//export excel 不配置默认支持excel导出
	if (gridExportDoc.indexOf('excel')!=-1 || gridExportDoc=='') {
		menuItem.push({
			text : Wg_Grid_excel_menu_text,
			iconCls:"excel",
			tooltip : Wg_Grid_excel_menu_text,
			handler : function() {
				exportDocument(me,"excel",false);
			}
		 });
		//export excel all
		menuItem.push({
			text : Wg_Grid_excel_menuAll_text,
			tooltip : Wg_Grid_excel_menuAll_text,
			iconCls:"excel",
			handler : function() {
				exportDocument(me,"excel",true);
		}});
	}
	
	var expBtn = new Ext.SplitButton({
		cls : 'x-btn-text-icon',
		iconCls:"exportDoc",
		tooltip : Wg_Grid_excel_exp_tooltip,
		style:"background-repeat:no-repeat;",
		handler : function(b) {
			b.showMenu();
		},
		menu : new Ext.menu.Menu({
			items:menuItem})
	});
	
	
	
	//通用导出程序
	function exportDocument(me,docType,allFlag){
		var hs = calcHeaders();
		var groupHs = calcGroupHeaders();
		var ts = me.types;
		if(typeof setExcelTextList==="function")
		setExcelTextList();
		var params = {
			"excel.gridTitle" : me.grid.title,
			"excel.gridHeaders" : hs,
			"excel.gridGrouupHeaders": groupHs,
			"excel.gridTypes" : ts,
			"excel.gridRoot" : queryRoot(),
			"excel.gridMethod" : getDataMethod(),
			"excelTextList" :excelTextList.join(';')
		};
		var store = me.grid.getStore();
		Ext.apply(params, store.lastOptions && store.lastOptions.params);
		//全部导出
		var allStr = "_page";
		if (allFlag) {
			params.start = 0;
			params.limit = 100000;
			allStr = "_all";
		}
		
		var url = replaceUrl(docType);
		
		//每种类型文档导出的form名称唯一，否则会出现切换文档类型时总是提交第一个form的情况
		var d = new Date();
		var formName = 'exportFrom'+allStr+d.getTime();
//		var fd=Ext.get(formName);
		var fd = null;
    	var hiddenParams = [];
    	//遍历参数，设置成隐藏按钮
    	for(var key in params){
    		var paramValue = params[key];
    		if(typeof paramValue == 'undefined' || paramValue=='undefined'){
    			paramValue = '';
    		}
    		hiddenParams.push({tag:'input',name:key,id:key,value:paramValue,type:'hidden'});
    	}
    	var charts = [];  //增加导出页面图表的代码
    	for(var chartKey in FusionCharts.items){
			if(FusionCharts.items[chartKey].exportChart)
				charts.push(FusionCharts.items[chartKey]);
		}
    	
    	if("excel"  == docType && charts.length>0){  
    		//如果是导出excel和页面的图表，则先将页面所有的图标一起导出。
    		exportExcelAndImage(charts ,formName , url , hiddenParams , "PNG");
    	}else{
    		fd=Ext.DomHelper.append(Ext.getBody(),
    				{tag:'form',method:'post',id:formName,name:formName,action:url, target:'_blank',cls:'x-hidden',cn:hiddenParams}
    		,true);
    		fd.dom.submit();
    	}
	}
	
	
	//计算组合表头
	function calcGroupHeaders(){
		//获取组合表头 Ext.ux.grid.ColumnHeaderGroup
		var groupHeader = '';
		var cm = me.grid.colModel;
		var rows = cm.rows;
		//如果存在组合表头
		if (rows) {
			for(var row =0; row < rows.length; row++){
	             var r = rows[row], len = r.length;
	             for(var i = 0, gcol = 0; i < len; i++){
	                 var group = r[i];
	                 groupHeader +=group.header+"~"+group.colspan+',';
	             }
	         }
		}
		return groupHeader;
	}
	//计算表头
	function calcHeaders() {
		var gs = me.grid.getColumnModel();
		var headers = ["{"];
		var clen = gs.getColumnCount();
		
		for (var i = 0; i < clen; i++) {
			var g = gs.config[i];
			if(!g){
				continue;
			}
			//嵌套表格
			if(g.grid){
				continue;
			}
			
			//序号
			if(g.id=="numberer"){
				g.dataIndex="numberer";
			}
			headers.push("'", g.dataIndex, "'");
			
			var header = '';
			//隐藏列
			if(g.hidden){
				headers.push(":", "'", g.header+',hide', "'");
			}else{
				headers.push(":", "'", g.header+',show', "'");
			}
			
			if (i != clen - 1) {
				headers.push(",");
			}
		}
		headers.push("}");
		return headers.join("");
	}
	//解析数据类型
	function calcType() {
		if (!types) {
			return;
		}
		var mp = {};
		for (k in types) {
			var v = types[k];
			var vs = (v + "").split(",");
			for (var i = 0; i < vs.length; i++) {
				mp[vs[i]] = k;
			}
		}
		return Ext.encode(mp);
	}
	me.types = calcType();
	function queryRoot() {
		var store = me.grid.getStore();
		return me.root||store.reader.meta.root;
	}
	
	//获取grid的查询url
	function queryUrl(){
		return me.grid.getStore().url || me.grid.getStore().proxy.url||me.grid.getStore().proxy.conn.url;
	};
	//获取查询方法
	function getDataMethod() {
		var url = queryUrl();
		var reg = /!(\w+)\.do/;
		var rs = reg.exec(url);
		if (rs) {
			return rs[1];
		}
	}
	function replaceUrl(docType) {
		var url =queryUrl() ;
		return url.replace(/!(\w+)\.do/, "!"+docType+".do");
	}
	
	me.ui = expBtn;
	me.setGrid = function(grid) {
		me.grid = grid;
	};
	me.changeTypes=function(types){
		me.types=types;
	};
	return me;
}//end initpage

Ext.apply(Wg.Grid.prototype, {
			init : function(_params, _hasSel, _singleSel) { // 初始化(查询参数、 是否选择 、是否单选)
				var obj = this;
				
				if(!this.collapsible) {//初始grid收缩值
					this.collapsible = false;
				}
				
//				if(!this.collapsed) {//默认grid为展开
//					this.collapsible = false;
//				}
				if(!this.url) {
					alert(Wg_Grid_url_null);
					return;
				}
				
				if (!this.cModel || this.cModel.length == 0) {
					alert(Wg_Grid_cModel_null);
					return;
				}
				
				// 是否勾选、是否单选
				this.sm = _hasSel ? new Ext.grid.CheckboxSelectionModel({
							checkOnly:this.checkOnly,
							singleSelect:_singleSel,
							header : _singleSel ? '': '<div class="x-grid3-hd-checker"> </div>'
						 }): '';

				var models = []; // 列
				if (_hasSel) {
					models.push(this.sm);
				}

				var index = [this.cModel.length]; // 索引

				for (var i = 0; i < this.cModel.length; i++) { //装配列内容
					index[i] = {
						name : this.cModel[i].dataIndex,
						type : this.cModel[i].type ? this.cModel[i].type : 'string'
					};
					models.push(this.cModel[i]);
				}
				
               /* if(!this.orderToFile) { //不指定写入文件顺序则按CM定义的顺序排列
                	this.orderToFile = "";
                	for(var o=0;o<index.length;o++){
                		this.orderToFile += index[o].name + ',';
                	}
                	this.orderToFile = this.orderToFile.substring(0,this.orderToFile.length -1);
                }*/
				/*var cmArray = '';
				if (this.headerExtend) {
					alert("headerExtend---:"+this.headerExtend);
					cmArray = {columns:models,rows:this.headerExtend};
				}else{
					cmArray = [models];
				}*/
				
				this.notLockColumn = this.notLockColumn?this.notLockColumn:false;
				var view = '';
				//支持冻结列
				if (!this.notLockColumn) {
					this.cm = new Ext.ux.grid.LockingColumnModel(models);
					view = new Ext.ux.grid.LockingGridView();
				}else{
					//在组合表头的时候冻结列存在问题，暂时未解决  jun 2012-11-21
					this.cm = new Ext.grid.ColumnModel(models);
				}
				
//				this.idProperty = this.idProperty ? this.idProperty : this.cModel[0].dataIndex; //id列
				this.sort = this.sort ? this.sort : this.idProperty; // 排序列
				this.order = this.order ? this.order : 'ASC';//排序方向
				this.heightPercent = this.heightPercent ? this.heightPercent : 0.75; // 默认高度百分比
				this.widthPercent = this.widthPercent? this.widthPercent : 1.00; //************************************edited
				this.pageNotSupport = this.pageNotSupport?true:false;
				
				if(this.isGrouping){//分组显示
					this.ds = new Ext.data.GroupingStore({
						baseParams : _params,
				        proxy : new Ext.data.HttpProxy({// 数据源
							url : this.url,
							autoAbort:true,
                			disableCaching:true,
							timeout: 15 * 60 * 1000  //15min超时
						}),						
						reader: new Ext.data.JsonReader({
							root: 'result',
							totalProperty: 'rows',
							remoteSort: true,
							fields: this.gFields
						}),
						sortInfo: this.gSortInfo,
						groupField: this.gGroupField
				    });
				    
				} else {
					this.ds = new Ext.data.JsonStore({// 数据源
				        root: 'result',
				        totalProperty: 'rows',
				        /*sortInfo: {//不默认排序，否则前后台逻辑不一致
						    field: this.sort,
						    direction: this.order  
						},*/
					    remoteSort: this.remoteSort ? true : false,
				        fields: index,
				        baseParams : _params,
				        proxy : new Ext.data.HttpProxy({
							url : this.url,
							timeout: 15 * 60 * 1000  //15min超时
						})
				    });					
				}
				
				var size = top.pagesize;
				this.pageSize = this.pageSize ? this.pageSize : size * 1; // 默认分页

                var excelPlugin = initExcelPlugin();
                // 文字说明
                var helpText = this.helpText ? this.helpText :'';
				this.paging = new Ext.PagingToolbar({ // 是否分页
					pageSize : this.pageSize,
					store : this.ds,
					displayInfo : true,
					displayMsg : helpText + Wg_Grid_paging_displayMsg,
					emptyMsg : Wg_Grid_paging_emptyMsg,
					plugins : [new PageSizePlugin({excel:excelPlugin,isExport:this.isExport})],
					listeners : {"beforechange" : function(bbar, params){  
						//翻页触发事件
						var grid = bbar.ownerCt;  
						var store = grid.getStore();  
						var start = params.start;  
						var limit = params.limit; 
						try{
							gridPagingEvent(start,limit);	
						}catch(e){
						}
					}}

				});
				
				//var _tbar=[];			
				 var _tbar = this.hasTbar ?  
				   [
					 {
					   text:Wg_Grid_tbar_selectAll_text,
						  tooltip:Wg_Grid_tbar_selectAll_tooltip,
				           iconCls:'multiple',
				           id:'multiple',
				           handler : function(){
				        	   if(obj.sm) {
				        		   obj.sm.selectAll(); 
				        		   obj.allSelected = true; 
				        		   //批量全选的时候显示选中的记录数
				        		   var rows = obj.grid.getStore().getTotalCount(); 
				        		   document.getElementById(obj.showId).innerHTML=Wg_select_records+rows;
				        		   return;
				        	   }
				        	 }
			        },'-',{ text:Wg_Grid_tbar_cancelAll_text,
						   tooltip:Wg_Grid_tbar_cancelAll_tooltip,
				           iconCls:'unmultiple',
				           handler:function(){
				        	   if(obj.sm) {
				        	   obj.sm.clearSelections(); 
				        	   obj.allSelected = false; 
				    		   document.getElementById(obj.showId).innerHTML=Wg_select_records+obj.sm.getSelections().length;
				        	   return;
				        	  }
				           }}/*,'-',{
			           text:Wg_Grid_tbar_delSel_text,
			           tooltip:Wg_Grid_tbar_delSel_tooltip,
			           iconCls:'remove',
			           handler:function(){obj.delSelRecord();return;}
			        },'-',{
			           text:Wg_Grid_tbar_clearAll_text,
			           tooltip:Wg_Grid_tbar_clearAll_tooltip,
			           iconCls:'remove',
			           handler:function(){obj.clearRecord();return;}
			        },'-',{
				           text:Wg_Grid_tbar_addGroup_text,
				           tooltip:Wg_Grid_tbar_addGroup_tooltip,
				           iconCls:'add',
				           handler:function(){obj.addGroup();}
				        }*/] : ((this.tbar!=null && this.tbar!='')?this.tbar:'');
				        //tbar与hasTbar同时存在
				        if(this.tbar!=null && this.tbar!=''&&this.hasTbar){
				        	_tbar.push('-');
				        	_tbar.push(this.tbar);
				        }
				        
				       if(obj.sm){
				        if(_tbar!=null && _tbar!=''&&_tbar!='undefined'){
				        	 _tbar.push('-','<span id="'+obj.showId+'" style="float: right;">'+Wg_select_records+''+obj.sm.getSelections().length+'</span>');
				        }else{
				        	_tbar=['<span id="'+obj.showId+'"  style="float: right;">'+Wg_select_records+''+obj.sm.getSelections().length+'</span>'];
				        	
				        }
				       }
			    
			    //存在结果列时显示删除成功或失败
			    if(this.resultColNum){
			    	_tbar.push('-');
			    	_tbar.push({
				           text:Wg_Grid_tbar_delSuccess_text,
				           tooltip:Wg_Grid_tbar_delSuccess_tooltip,
				           iconCls:'remove',
				           handler:function(){obj.delSuccessRecord();return;}
				        },'-',{
				           text:Wg_Grid_tbar_delFail_text,
				           tooltip:Wg_Grid_tbar_delFail_tooltip,
				           iconCls:'remove',
				           handler:function(){obj.delFailRecord();return;}
				    });
			    }
			    
			    //var widthPer = 1.00;
			    this.widthPercent = Ext.isEmpty(this.widthPercent) ? 1 : this.widthPercent;
			    //this.widthPercent = this.widthPercent*1>widthPer?widthPer:this.widthPercent;
			    //if(this.width){
			    	//this.width= this.width*1>Ext.getBody().getWidth()*widthPer?Ext.getBody().getWidth()*widthPer:this.width;
			    //}
			    //是否根据页面重新变换
			    /*
				var canResize = true;
				if(this.canResize==undefined){canResize=true;}
				if(this.canResize==false){
					canResize = false;
				}
			    */
			    this.margin = Ext.isEmpty(this.margin)? 30 : this.margin;
			    this.grid = new Ext.grid.EditorGridPanel({
					id : this.title,
					ds : this.ds,
					cm : this.cm,
					colModel:this.colModel,
					cls : 'custom-grid',
					sm : this.sm,
					tbar: _tbar,
//					collapsed: this.collapsed,
					collapsible: this.collapsible,
					bbar : this.pageNotSupport?'':this.paging,
					height : obj.height ? obj.height : (Ext.getBody().getHeight()) * obj.heightPercent,
					//width : this.width ? this.width : (Ext.getBody().getWidth()) * this.widthPercent, 
//					width : this.width ? this.width : (window.screen.width - 30) * this.widthPercent - 15, 
					width : obj.width ? obj.width : Ext.getBody().getWidth()*obj.widthPercent - obj.margin,
					
					resize : function(){
						var realWidth = Ext.getBody().getWidth() * obj.widthPercent - obj.margin;
						this.setWidth(realWidth);
						
						//alert(realWidth);
					},
					loadMask: Ext.isEmpty(this.loadMask) ?true: this.loadMask,
					title : this.title,
					plugins :this.plugins,
					layout:this.layout,
					clicksToEdit:1,
					stripeRows:true,
					enableColumnHide:false,//false不允许用户隐藏列，true为允许
					autoExpandColumn:this.autoExpandColumn,
					//锁定列
					//view: view,
					view: this.view? this.view:view, //分组显示
					columns: this.isGrouping? this.cModel: '',//分组显示
					podTitle : this.podTitle? this.podTitle: '',
					listeners:{ // 右键复制s
						cellcontextmenu: function(grid,rowIndex,columnIndex,e) {
							var record = grid.getStore().getAt(rowIndex);
							var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
							var data= record.get(fieldName);
							clipboardData.setData("Text",data.toString());
						},
						//给每个cell增加tip显示，解决cell内容显示不全的问题
						render: function (grid){  
				           var view = grid.getView();    // Capture the GridView.  
				           grid.tip = new Ext.ToolTip({  
				               target: view.mainBody,    // The overall target element.  
				               delegate: '.x-grid3-cell', // Each grid row causes its own seperate show and hide.  
				               trackMouse: true,         // Moving within the row should not hide the tip.  
				               renderTo: document.body,  // Render immediately so that tip.body can be  
				               maxWidth:350,
				               minWidth:120,
				               anchor: 'top',  
				               floating: {//渲染提示信息效果  
				            	   shadow: true,  
				            	   shim: false,  
				            	   useDisplay: true,  
				            	   constrain: false  
				            	},
				               listeners: {                 
				                    //Change content dynamically depending on which element  triggered the show.  
				                    beforeshow: function updateTipBody(tip) {  
				                    	var index = view.findRowIndex(tip.triggerElement);
										var cindex = view.findCellIndex(tip.triggerElement);//列号
										var record = grid.store.getAt(index);//行记录  
										var field = grid.colModel.getDataIndex(cindex);//列名  
										var cellValue = '';
										//去除checkbox列
										if(trim(field)!=''){
											try{
												cellValue = record.get(field);
											}catch(e){}
										}
										
										
				                    /*    var rowIndex = view.findRowIndex(tip.triggerElement);  
				                        var cellIndex = view.findCellIndex(tip.triggerElement);  
				                        var cell = view.getCell(rowIndex, cellIndex); 
				                        var cellText = cell.innerText;*/
				                        if (trim(cellValue)!='') {
//				                        	tip.body.dom.innerHTML = cell.innerHTML;
				                        	//根据文字长度自动控制
				                        	tip.body.dom.innerHTML = cellValue;
										}else{
											//内容为空的时候，不显示tip
											return false;
										}
				                    }  
				                }  
				            });  
					    }  
					}
				});

		    	excelPlugin.setGrid(obj.grid);
		    	obj.grid.render(obj.el);
		    	


				//设置grid对象
				
				this.ds.load({
					params : { // 读取数据
					start : 0,
					limit : this.pageSize
				}}); 
				
				//全选后保持翻页全选
				this.ds.addListener('datachanged',function(){if(obj.allSelected){
					obj.sm.selectAll();
					return;
				}});
				
				//单选后全选标志false
				if(this.sm){
					this.sm.addListener('rowdeselect',function(){
					if(obj.allSelected){
						obj.allSelected = false;
						return;
					}
    				document.getElementById(obj.showId).innerHTML=Wg_select_records+obj.sm.getSelections().length;
					});
						
					this.sm.addListener('rowselect', function (){
		    			document.getElementById(obj.showId).innerHTML=Wg_select_records+obj.sm.getSelections().length;
	    				return ;
						
					});
					
					this.grid.addListener('rowclick', function (){
	    				document.getElementById(obj.showId).innerHTML=Wg_select_records+obj.sm.getSelections().length;
	    				return ;
					});
				}
				
				//显示查询耗时，在grid的title上显示，兼容无title的情况，无title暂时不显示查询时间
				this.ds.addListener('load', function(store, rds, opts) {
					try{
						var time = store.reader.jsonData['time'];
						if(typeof obj.podTitle != "undefined" && typeof time != "undefined"){
							obj.grid.setTitle(obj.title+":"+obj.podTitle+Wg_Grid_loadTime1+time+Wg_Grid_loadTime2);
						} else if(typeof time != "undefined"){
							obj.grid.setTitle(obj.title+Wg_Grid_loadTime1+time+Wg_Grid_loadTime2);
						}
					}catch(e){
					}
				});
				
				// 窗口大小调整
				if(window.attachEvent){
					if(!obj.width){
						window.attachEvent('onresize', function(){
							obj.grid.resize();
						})
					}
				}else{
					if(!obj.width){
						window.addEventListener('resize', function(){
							obj.grid.resize();
						})
					}
				}
				/*
				window.onresize = function(){
					
					if (canResize==true) {
						// 页面两个grid的情况下，重新定义grid宽度
						//obj.grid.setWidth(Ext.getBody().getWidth()*widthPer);
						obj.grid.setWidth(Ext.getBody().getWidth() - 30);
					}
					
					
				};
				*/
				return;
			},
			reload : function(_params) { 
				//检查会话
				if(dwrAction){
					dwrAction.checkSession();
				}
				if(_params){
					this.ds.baseParams = _params;
				}else{
					this.ds.baseParams = {};
				}
				
				//定位当前页，如果有起始记录，就从起始开始，否则从0开始
				var start=0;
				try{
					start = _params['start'];
				}catch(e){
				}
				
				if(typeof start == "undefined"){
					start = 0;
				}
				
				//每页记录数，获取翻页控件的每页记录数值
				this.ds.load({
							params : { // 读取数据
							start : start,
							limit : this.paging.pageSize
						}}); 
				
				//重新加载grid的时候取消全选
				if(this.hasTbar){
					//取消批量全选的时候 不显示选中的记录数
//		        	   var btnText = Ext.getCmp('multiple').getText();
//		        	   if(btnText.indexOf('(')!=-1){
//		        		   btnText = btnText.substring(0,btnText.lastIndexOf('('));
//		        	   }
//		        	   Ext.getCmp('multiple').setText(btnText)
				}
				
				Wg.removeLoading();
				return;
			},
			getRecords : function() {
				if (this.sm && this.sm != '') {
					var record = this.sm.getSelections();
					if (record && record.length > 0) {
						return record;
					} else {
						return false;
					}
				}
				return;
			},
			hideCol : function(index) {
				if(this.cm) {
					this.cm.setHidden(index,true);
				}
				return;
			},
			showCol : function(index) {
				if(this.cm) {
					this.cm.setHidden(index,false);
				}
				return;
			},
			isSelAll : function(){
				return this.allSelected;
			},
			//操作文件
			doFile : function(_doFileParam, _fn, hasLoad) {
				if(!this.fileUrl) {
					Wg.alert(Wg_Grid_doFile_fileUrl_null);
					return;
				}
				//id列
				if(!this.colNum) {
					Wg.alert(Wg_Grid_doFile_colNum_null);
					return;
				} else {
					Ext.apply(_doFileParam ,{colNum:this.colNum});
				}
				//id的列名
				if(this.idProperty) {
					Ext.apply(_doFileParam ,{idProperty:this.idProperty});
				}
				//文件列顺序
				if(this.orderToFile){
					Ext.apply(_doFileParam ,{orderToFile:this.orderToFile});
				}
				
				//模块名字
				if(this.name){
					Ext.apply(_doFileParam ,{name:this.name});
				}
				Wg.ajax(this.fileUrl,_doFileParam,_fn, hasLoad,null);
			},
			//增加记录
			addRecord : function(_params,_cover) {
				var flag = false;
				for ( var i in _params) {
					if (i=='start'){
						flag = true;
						break;
					}
				}
				if(flag){
					Ext.apply(_params ,{doType:'addRecord',cover:_cover});
				}else{
					var start = 0;
					var limit = this.grid.getStore().lastOptions.params.limit;
					Ext.apply(_params ,{doType:'addRecord',cover:_cover,start:start,limit:limit});
				}
				
				var o = this;
				this.doFile(_params,function(){Wg.removeLoading();o.reload(_params);return;},true);
			},
			
			//根据删除记录
			delSelRecord : function() {
				if(this.allSelected) { //全选即删除全部
					this.clearRecord();
					return;
				}
				var record = this.sm.getSelections();
				if (record && record.length > 0) {
					var ids = '';
					for(var i=0;i < record.length;i++){
						ids += record[i].get(this.idProperty) + ',';
					}
					ids = ids.substring(0,ids.length-1);
					var doFileParam = { 
						doType:'delRecordById',
						ids: ids
					};
					var o = this;
					this.doFile(doFileParam,function(){o.reload();return;},false);
				} else {
					Wg.alert(Wg_Grid_delSelRecord_alert);
					return;
				}
			},
			
			//删除成功记录
			delSuccessRecord : function() {
				var o = this;
				if(o.resultColNum){
					var doFileParam = { 
						doType:'delSucecss',
						resultColNum:o.resultColNum
					};
					this.doFile(doFileParam,function(){o.reload();return;},false);
				} else {
					alert(Wg_Grid_delSuccessRecord_alert);
					return;
				}
			},
			
			//删除失败
			delFailRecord : function() {
				var o = this;
				if(o.resultColNum){
					var doFileParam = { 
						doType:'delFail',
						resultColNum:o.resultColNum
					};
					this.doFile(doFileParam,function(){o.reload();return;},false);
				} else {
					alert(Wg_Grid_delSuccessRecord_alert);
					return;
				}
			},
			
			//清空文件
			clearRecord : function() {
				var doFileParam = {
					doType:'delAll'
				};
				var o = this;
				this.doFile(doFileParam,function(){o.reload();return;},false);
			},
			
			//刷新文件
			refreshFile : function(_taskId, _czlx, _totalCol,_params) {
				if(!_taskId) {
					alert(Wg_Grid_refreshFile_taskId_null);
					return;
				}
				if(!_czlx) {
					alert(Wg_Grid_refreshFile_czlx_null);
					return;
				}
				if(!_totalCol) {
					alert(Wg_Grid_refreshFile_totalCol_null);
					return;
				}
				
				var doFileParam = { doType:'refresh',
					taskId: _taskId,
		            czlx: _czlx,
		            totalCol:_totalCol,
		            colNum : this.colNum
		        };
				var o = this;
				this.doFile(doFileParam,function(){o.reload(_params);return;},true);
			},
			
			//根据指定列内容删除记录
			delRecord : function(_delColNum,_compare) {
				if(!_delColNum) {
					alert(Wg_Grid_delRecord_delColNum_null);
					return;
				}
				if(!_compare) {
					alert(Wg_Grid_delRecord_compare_null);
					return;
				}
				var doFileParam = { 
					doType:'delRecord',
					delColNum: _delColNum,
					compare:_compare
				};
				var o = this;
				this.doFile(doFileParam,function(){o.reload();return;},false);
			},
			
			// 页面出来后删除文件
		    delFile : function() {
				var doFileParam = {
					doType:'delFile',
					name:this.name
				};
				var o = this;
				getResponseText(this.fileUrl,doFileParam);
			},
			
			// 获取指定列值（默认ID列）
			getColValues : function(_colNum) {
				var re = '';
				var doFileParam = {
					doType:'getColValues',
					cnum : _colNum
				};
				
				if(!this.fileUrl) {
					alert(Wg_Grid_getColValues_fileUrl_null);
					return;
				}
				//id列
				if(!this.colNum) {
					alert(Wg_Grid_getColValues_colNum_null);
					return;
				} else {
					Ext.apply(_doFileParam ,{colNum:this.colNum});
				}
			 
				var re = Wg.ajax(_doFileParam, this.fileUrl);
				return re;
			},
			
			//导出Excel
			getExcel : function() {
				if(!this.excelUrl) {
					Wg.alert(Wg_Grid_getExcel_excelUrl_null);
					return;
				}
				if(this.ds.getTotalCount() != 0){
					var url = this.excelUrl;
					var excelParam = this.excelParam;
					if(excelParam) {
						url = this.excelUrl + '?isExcel=1&';
						for(var i in excelParam){
							if($(excelParam[i])) {
								url += excelParam[i] +'='+$(excelParam[i]).value + '&';
							}
						}
						window.location = url.substring(0,url.length - 1); 
					}else{
						window.location = url;//从文件直接导出Excel
					}	
				} else {
					Wg.alert(Wg_Grid_getExcel_alert);
				}
				return;
			},
			removeAll : function() {
				this.ds.removeAll();
			},
			// 加入群组
			addGroup : function(){
				var record = this.sm.getSelections();
				if(!record || record.length == 0) {
					Wg.alert(Wg_Grid_addGroup_alert1);
					return;
				}else{
					// 默认专变群组
					if(typeof(this.qzfl) == 'undefined'){
						this.qzfl = "01";
					}
					
					// 默认终端群组
					if(typeof(this.qzlx) == 'undefined'){
						this.qzlx = "sb";
					}
					var zdjhs = "";
					if(!this.allSelected == true) {
						if(record.length > 50) {
							Wg.alert(Wg_Grid_addGroup_alert2);
							return;
						}
						if(this.qzlx == 'bj'){
							for(var i=0;i < record.length;i++){
								zdjhs += record[i].get("BJJH") + ',';
							}
							zdjhs = zdjhs.substring(0,zdjhs.length-1);
						}else{
							for(var i=0;i < record.length;i++){
								zdjhs += record[i].get("ZDJH") + ',';
							}
							zdjhs = zdjhs.substring(0,zdjhs.length-1);
						}
					}
					
					var doFileParam = {
						doType : 'initGroup',
						zdjhs : zdjhs,
						allSelected : this.allSelected
					};
					var o = this;
					var url = String.format(ctx + "/qz!initQz.do?doType={0}&qzfl={1}&flg={2}&colNum={3}&name={4}&qzlx={5}","addGroup",this.qzfl,this.allSelected,this.colNum,this.name,this.qzlx);
					var baseParam = {
							url : url,
							title : Wg_Grid_addGroup_title,
							el : 'detail',
							width : 720,
							height : 500,
							draggable : true
						};
						var simpleWin = new Wg.window(baseParam);
						simpleWin.open();
					/*this.doFile(doFileParam, function() {
						var baseParam = {
							url : url,
							title : Wg_Grid_addGroup_title,
							el : 'detail',
							width : 720,
							height : 500,
							draggable : true
						};
						var simpleWin = new Wg.window(baseParam);
						simpleWin.open();
						return;
					}, false);*/
				}
			}
		});

if(!Ext.grid.GridView.prototype.templates){
	Ext.grid.GridView.prototype.templates={};
}

Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
'<td class = "x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style = "{style}" tabIndex = "0" {cellAttr}>',
'<div class = "x-grid3-cell-inner x-grid3-col-{id}" {attr}> {value}</div>',
'</td>'
);

function getResponseText(_url, _param) {
	var http_request = false;
	if (window.XMLHttpRequest) { // Mozilla 浏览器
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {// 设置MiME类别
			http_request.overrideMimeType('text/xml');
		}
	} else if (window.ActiveXObject) { // IE浏览器
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	if (!http_request) { // 异常，创建对象实例失败
		return false;
	}
	var res = '';
	http_request.open('POST', _url, false);
	http_request.setRequestHeader("CONTENT-TYPE",
			"application/x-www-form-urlencoded");
	
	if(Ext.isIE) {
		http_request.onreadystatechange = function() {
			if (http_request.readyState == 4) {
				if (http_request.status == 200) {
					try {
						res = http_request.responseText;
						Wg.removeLoading();
						return;
					} catch (e) {
						return;
					}
				} 
			}
		};
	} else {
		http_request.onload=function() {
			try {
				res  = http_request.responseText;
				Wg.removeLoading();
				return;
			} catch (e) {
				return;
			}
         };
	}
	var pp = '';
	if (_param) {
		pp=Ext.urlEncode(_param);
	}
	http_request.send(Ext.isEmpty(pp) ? null : pp);
	return res;
};

Ext.QuickTips.init();

