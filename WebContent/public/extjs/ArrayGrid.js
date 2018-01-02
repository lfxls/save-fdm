Ext.namespace("ycl.Widgets");
// Ext Grid by ycl 2009/10
Wg = ycl.Widgets;
/*
 * _cm = [{header : _head, dataIndex : _id, renderer : _fn(value), sortable : true }]
 * _cfg = {url:_URL,el:'grid',title:'Grid',height:350,width:600,cModel:_cm,sort:'id',order:'desc'}
 */
Wg.ArrayGrid = function(_cfg) {
	Ext.apply(this, _cfg);
	Ext.apply(this, {
				sm : '',
				cm : '',
				ds : ''
			});
}

Ext.apply(Wg.ArrayGrid.prototype, {
			init : function(_hasSelect) { //初始化

				this.sm =  new Ext.grid.CheckboxSelectionModel({singleSelect:true, checkOnly:true});
				var models = []; // 列
				if(!_hasSelect){
				  models.push(this.sm);
				}

				if (!this.cModel || this.cModel.length == 0) {
					Wg.alert(wg.ArrayGrid.cModel.length.zero);
					return;
				}

				var index = [this.cModel.length]; // 索引

				for (var i = 0; i < this.cModel.length; i++) {
					index[i] = {
						name : this.cModel[i].dataIndex
					}
					models.push(this.cModel[i]);
				}

				this.cm = new Ext.grid.ColumnModel(models);

				this.ds = new Ext.data.Store({ 
  								 proxy : new Ext.data.MemoryProxy([]),
 						 		 reader : new Ext.data.ArrayReader({}, index)
 							   });	
 							   
 			    var grid = new Ext.grid.GridPanel({
							id : "gp",
							ds : this.ds,
							cm : this.cm,
							sm : this.sm,
							height : this.height,
							width : this.width,
							loadMask: true,
							title : this.title
						});

				grid.render(this.el);
				
				this.sort = this.sort ? this.sort : this.cModel[0].dataIndex; //默认排序
				this.order = this.order ? this.order : 'asc';

				this.ds.setDefaultSort(this.sort, this.order);
				
				this.ds.load({}); // 读取数据
			},
			reload : function(_pagaParam) {
				this.ds.baseParams = _pagaParam;
				this.ds.reload({});
			},
			clear: function() {
				this.ds.removeAll();
			},
			destory:function() {
				Ext.getCmp("gp").destroy();
			},
			getRecords : function() {
				if (this.sm && this.sm != '') {
					var record = this.sm.getSelections();
					if (record && record.length > 0) {
						return record;
					}
				}
			},
			selectRow:function(_id){
			  var i = this.ds.getCount();
				while(i>0) {
					i --;
				 	var rd = this.ds.getAt(i);
				 	var id = rd.get(this.cModel[0].dataIndex); //第一列匹配即勾选
				 	if( id == _id) {
				 		this.sm.selectRow(i);
				 		break;
				 	}
				}
			},
			addRecords : function(obj) {
				if(obj) {
					for(var i=0;i<obj.length;i++) {
						var rd = new Ext.data.Record(obj[i]);
						this.ds.addSorted(rd);
					}
				}
			},
			removeRecord : function (_id) {
				var i = this.ds.getCount();
				while(i>0) {
					i --;
				 	var rd = this.ds.getAt(i);
				 	var id = rd.get(this.cModel[0].dataIndex); //第一列匹配即删除
				 	if( id == _id) {
				 		this.ds.remove(rd);
				 		break;
				 	}
				}
			},
			existRecord : function (_id) {
				var i = this.ds.getCount();
				while(i>0) {
					i --;
				 	var rd = this.ds.getAt(i);
				 	var id = rd.get(this.cModel[0].dataIndex); //第一列匹配即重复
				 	if( id == _id) {
				 		Wg.alert(wg_ArrayGrid_existRecord);
				 		return true;
				 	}
				}
			},
			getAllRecord : function() { //列转数据值
				var count = this.ds.getCount();
				if(count == 0) {
					Wg.alert(wg_ArrayGrid_noRecord);
					return false;
				}
				var str = '';
				for(var i=0;i<count;i++) {
				 	var rd = this.ds.getAt(i);
				 	if(rd.data) {
				 		var pp = '';
				 		for(var p in rd.data) {
				 			pp += rd.get(p) + '@';
				 		}
				 		pp = pp.substring(0,pp.length-1);
				 		str += pp + ';';
				 	}
				}
				str = str.substring(0,str.length-1);
				return count+'#'+str;
			}
			
		});
		
Ext.QuickTips.init();