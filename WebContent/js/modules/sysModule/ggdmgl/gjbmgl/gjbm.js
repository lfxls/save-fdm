var menuId = '54600';
var grid_url =  ctx + '/system/ggdmgl/gjbmgl!query.do';
var GjbmGrid = '';

var fields = [
          	  {name: 'OP'},
              {name: 'GJBM'},
              {name: 'GJMC'},
              {name: 'GJLBMC'},
              {name: 'GJDJMC'},
              {name: 'YY'},
              {name: 'GYLX'},
              {name: 'GJLB'},
              {name: 'GJDJ'},
          	  {name: 'LANG'}
          ];
Ext.onReady(function() {
	handlerType = 'query';
	
	var _cm = [ {
		header : gjbmgl_title_cz,
		dataIndex : 'OP',
		width : 60,
		renderer : function(value, cellmeta, record) {
		var url="";
		url += String.format('<a class="edit" title="\{2}\" href="javascript:initEditGjbm(\'{0}\',\'{1}\');"></a>',
				record.data.GJBM,record.data.LANG,gjbmgl_title_bj);
	
		 url += String.format('<a class="del" title="\{2}\"href="javascript:del(\'{0}\',\'{1}\');"></a>',
				record.data.GJBM,record.data.LANG,gjbmgl_title_sc);
			return url;
		}
	}, {
		header : gjbmgl_title_gjbm,
		dataIndex : 'GJBM',
		width : 100,
		sortable : true
	},
	{
		header : gjbmgl_title_gjmc,
		dataIndex : 'GJMC',
		width : 420,
		sortable : true
	}, {
		header : gjbmgl_title_gjlb,
		dataIndex : 'GJLBMC',
		width : 150
	},{
		header : gjbmgl_title_gjdj,
		dataIndex : 'GJDJMC',
		width : 100
	}, {
		header : gjbmgl_title_yy,
		dataIndex : 'YY',
		width : 200
	}, {
		dataIndex : 'GYLX',
		hidden : true
	}, {
		dataIndex : 'GJLB',
		hidden : true
	}, {
		dataIndex : 'GJDJ',
		hidden : true
	}, {
		dataIndex : 'LANG',
		hidden : true
	}];
	var toolbar = [{
        id: 'add-buton',
		text:gjbmgl_title_add,
        tooltip:gjbmgl_title_add,
        iconCls:'add',
        handler: function(){
        	add();
        }
    }];
	
	GjbmGrid = new Wg.Grid( {
		url : grid_url,
		el : 'grid',
		title : gjbmgl_title_gjbmgl,
		heightPercent : 0.85,
		cModel : _cm,
		excelUrl : grid_url,
		tbar:toolbar,
		notLockColumn: true,
    	view: new Ext.grid.GroupingView({
            forceFit: false,
            groupTextTpl: gjbmgl_title_gjdj +': '+ '{[values.rs[0].data.GJDJMC]}({[values.rs.length]} {[values.rs.length > 1 ? "'+gjbmgl_title_jl+'" : "'+gjbmgl_title_jl+'"]})'
        }),		
		isGrouping: true,//是否分组
		gFields: fields,//字段列
		gSortInfo: {field: 'GJDJ', direction: "ASC"},//排序
		gGroupField: 'GJDJMC' //分组列
	});
	var p = $FF('queryForm');
	GjbmGrid.init(p);
	 
});


function query() {
	var p = $FF('queryForm');
	GjbmGrid.reload(p);
}


function add(){
	var url = String.format(
			ctx + '/system/ggdmgl/gjbmgl!initGjbmgl.do?czid={0}','01');
	var baseParam = {
		url : url,
		el : 'Gjbmglwin',
		title : gjbmgl_title_addgjbm,
		width : 500,
		height : 380,
		draggable : true
	};
	Gjbmglwin = new Wg.window(baseParam);
	Gjbmglwin.open();
}

//修改
function initEditGjbm(gjbm,yy){
	var url = String.format(
			ctx + '/system/ggdmgl/gjbmgl!initGjbmgl.do?gjbm={0}&czid={1}&yy={2}',gjbm,'02',yy);
	var baseParam = {
			url : url,
			title : gjbmgl_title_upgjbm,
			el : 'Gjbmglwin',
			width : 500,
			height : 380,
			draggable : true
	};
	Gjbmglwin = new Wg.window(baseParam);
	Gjbmglwin.open();
}
// 删除
function del(gjbm,yy){
	var czid = '03';
	var p = {
			gjbm : gjbm,
			lang:yy
	};
	Ext.apply(p, {
		logger : gjbmgl_title_scgjbm + gjbm
	});
	Wg.confirm(gjbmgl_title_surescgjbm, function() {
		dwrAction.doDbWorks('gjbmglAction', menuId + czid, p, function(re) {
			Wg.alert(re.msg, function() {
				if (re.success) {
					query();
				}
			});
		});
	});
}

