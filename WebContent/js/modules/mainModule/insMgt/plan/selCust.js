var menuId = '12200';
var CustGrid = '';
var grid_url = ctx + '/main/insMgt/insPlan!queryCust.do';
Ext.onReady(function() {	
	var _cm = [{
		header : common.kw.other.Operat,
		dataIndex : 'OP',
		width : 70,
		renderer : function(value, cellmeta, record) {
			var url = String.format('<a class="addCell" title="'
									+ main.insMgt.plan.text.selCust
									+ '" href="javascript:selectCust(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');" ></a>',
			record.data.CNO, record.data.CNAME, record.data.ADDR,record.data.UID,record.data.UNAME,record.data.MNO);
			return url;
		}
	} ,{
		header : common.kw.customer.CSN,
		dataIndex : 'CNO',
		width : 120,
		sortable : true
	}, {
		header : common.kw.customer.CName,
		dataIndex : 'CNAME',
		width : 120
	}, {
		header : common.kw.customer.CA,
		dataIndex : 'ADDR',
		width : 120
	},{
		header : common.kw.other.Mobile,
		dataIndex : 'MNO',
		width : 120
	},{
		dataIndex : 'UID',
		hidden : true	
	},{
		dataIndex : 'UNAME',
		hidden : true	
	}];
	
	CustGrid = new Wg.Grid( {
		url : grid_url,
		el : 'CustGrid',
		title : main.insMgt.plan.grid.title.custList,
		heightPercent : 0.73,
		cModel : _cm
	});
	
	var p = {
			cno: $F('cno'),
			cname: $F('cname'),
			otype: $F('otype')
		};
	CustGrid.init(p);
	 
});

// 查询
function query() {
	var p = {
				cno: $F('cno'),
				cname: $F('cname'),
				otype: $F('otype')
			};
	CustGrid.reload(p);
}

//选择用户
function selectCust(cno, cname, addr, uid, uname,mno){
	parent.$('cno').value = cno;
	parent.$('cname').value = cname;
	parent.$('addr').value = addr;
	if(parent.$('mno')!=null){
		parent.$('mno').value = mno;
	}
	parent.$('uid').value = uid;
	parent.$('uname').value = uname;
	
//	parent.$('tfId').value = '';
//	parent.$('tfName').value = '';
	
	
	parent.win.close();
}
