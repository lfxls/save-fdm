loadProperties('mainModule', 'mainModule_arcMgt');
var menuId = '11700';
var grid_url = ctx + '/main/arcMgt/modelMgt!query.do';
var modelGrid = '';
Ext.onReady(function() {
	handlerType = 'query';
	hideLeft();
	var _cm = [
{
	header : main.arcMgt.modelMgt.grid.col.op,
	dataIndex : 'OP',
	width : 80,
	locked:true,
	renderer : function(value, cellmeta, record) {
		var url="";
		url += String.format('<a class="edit" title="\{5}\" href="javascript:initEditModel(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\');"></a>',
				record.data.M_MODEL,record.data.VERID,record.data.M_VERNAME,record.data.MFID,record.data.M_VERID, main.arcMgt.modelMgt.grid.col.edit);
		url += String.format('<a class="del" title="\{2}\" href="javascript:delModel(\'{0}\',\'{1}\');"></a>',
				record.data.VERID,record.data.M_VERNAME,main.arcMgt.modelMgt.grid.col.del);
		return url;
	}
	},{
		dataIndex : 'M_VERID',
		hidden:true
	},{
		header : main.arcMgt.modelMgt.grid.col.vername,
		dataIndex : 'M_VERNAME',
		width : 160,
	}, {
		header : common.kw.meter.MModel,
		dataIndex : 'M_MODEL',
		width : 120
	},{
		header : main.arcMgt.modelMgt.grid.col.verid,
		dataIndex : 'VERID',
		width : 250
	},{
		header : main.arcMgt.modelMgt.grid.col.mf,
		dataIndex : 'MF',
		width : 120
	},{
		dataIndex : 'MFID',
		hidden:true
	},{
		header : main.arcMgt.modelMgt.grid.col.name,
		dataIndex : 'OPTID',
		width : 120,
	}, {
		header : main.arcMgt.modelMgt.grid.col.crt_date,
		dataIndex : 'CRT_DATE',
		width : 200
	}];
	var toolbar = [{
        id: 'add-buton',
		text:main.arcMgt.modelMgt.tb.add,
        tooltip:main.arcMgt.modelMgt.tb.add,
        iconCls:'add',
        handler: function(){
        	initAddModel();
        }
    }];
	modelGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : main.arcMgt.modelMgt.grid.title,
		heightPercent : 0.80,
//		width:Ext.getBody().getWidth()*0.97,
		cModel : _cm,
		excelUrl : grid_url,
		tbar:toolbar,
//		excelParam : ['nodeId','nodeType','yhlx','yhzt']
	});
	modelGrid.init({});
	
	
});
	

	function query(){
		var p = $FF('queryForm');
		modelGrid.reload(p);
	}
	
	function initAddModel(){
		
		var url = String.format(ctx + '/main/arcMgt/modelMgt!initModel.do?czid={0}','01');
		var baseParam = {
				url : url,
				title : main.arcMgt.modelMgt.add.wh,
				el : 'add',
				width : 700,
				height : 400,
				draggable : true
			};
		AddWin = new Wg.window(baseParam);
		AddWin.open();
		
		
	}
	function initEditModel(model,verid,vername,mfid,m_verid){
		var url = String.format(ctx + '/main/arcMgt/modelMgt!initModel.do?model={0}&verid={1}&vername={2}&mfid={3}&m_verid={4}&czid={5}',$En(model),$En(verid),$En(vername),mfid,m_verid,'02');
		
		var baseParam = {
				url : url,
				title : main.arcMgt.modelMgt.edit.wh,
				el : 'edit',
				width : 700,
				height : 400,
				draggable : true
			};
		EditWin = new Wg.window(baseParam);
		EditWin.open();
	}
	
	function delModel(verid,vername){
		var czid = '03';
		
		var p = {
				verid: $En(verid),
				vername:vername
				};
		Ext.apply(p,{logger:main.arcMgt.modelMgt.del.logger + vername});
		Wg.confirm(main.arcMgt.modelMgt.del.confirm,function(){
			dwrAction.doDbWorks('modelMgtAction',menuId + czid, p, function(res){
				Wg.alert(res.msg,function(){
					if(res.success) {
						query();
					}
				});
			});
		});
	}
	function ChangeMf(mfid){
		dwrAction.doAjax('modelMgtAction','getModels',{mfid:$F('mfid')},function(res) {
			if (res.success) {
				$('model').options.length=0;
				var obj =res.dataObject;
				for(var i=0;i<obj.length;i++){
					var optionObj = document.createElement("option"); 
					optionObj.value = obj[i].BM;
					optionObj.text=obj[i].MC;
					$('model').appendChild(optionObj);
				}
				
			} else {
				top.Wg.alert(menuHandler_norole);
				return;
			}
		});
	}