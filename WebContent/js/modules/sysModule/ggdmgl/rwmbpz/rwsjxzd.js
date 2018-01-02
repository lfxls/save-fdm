var menuId = '54400';

function query(){
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	var sjb = $F('sjb');
	var url = String.format(ctx +  '/system/ggdmgl/rwmbpz!showSjxzdWin.do?rwsx={0}&zdgylx={1}&sjb={2}',rwsx, zdgylx,sjb);
	location.href = url;
}

function save(){
	var sjxbm  = document.getElementsByName("sjxbm");
	var zd = document.getElementsByName("zd");
	var xh = document.getElementsByName("xh");
	var dbsjxbm = document.getElementsByName("dbsjxbm");
	var sjxsx = document.getElementsByName("sjxsx");
	
	var sjxbms = "";
	var zds = "";
	var xhs = "";
	var dbsjxbms = "";
	var sjxsxs = "";
	
	for ( var i = 0; i < sjxbm.length; i++) {
		sjxbms += sjxbm[i].value+",";
	}
	for ( var i = 0; i < zd.length; i++) {
		if(zd[i].value!="")zds += zd[i].value+",";
		else zds += "null"+",";
	}
	for ( var i = 0; i < xh.length; i++) {
		xhs += xh[i].value+",";
	}
	for ( var i = 0; i < dbsjxbm.length; i++) {
		dbsjxbms += dbsjxbm[i].value+",";
	}
	for ( var i = 0; i < sjxsx.length; i++) {
		sjxsxs += sjxsx[i].value+",";
	}
	
	var rwsx = $F('rwsx');
	var zdgylx = $F('zdgylx');
	var sjb = $F('sjb');
	var p = {
		rwsx:rwsx,
		zdgylx:zdgylx,
		sjb:sjb,
		sjxbms:sjxbms,
		zds:zds,
		xhs:xhs,
		dbsjxbms:dbsjxbms,
		sjxsxs:sjxsxs,
		logger :rwsjxzd_logger1+ zdgylx + rwsjxzd_logger2 + rwsx+rwsjxzd_logger3
	};
	
	Wg.confirm(rwsjxzd_confirm, function() {	
		dwrAction.doDbWorks('rwmbpzAction', menuId + '02', p, function(res) {
			 Wg.alert(res.msg, function(){
				 if(res.success) {
					 parent.rwsjxGrid.reload();
				 }
			 });
		});
	});
}