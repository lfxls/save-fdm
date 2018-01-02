Ext.onReady(function() {
	var hasFlash = flashChecker();
	if(hasFlash==0) {
		alert("Your computer installed without flash, please install");
		location.href = ctx + "/public/fusionchart3/install_flash_player_11_active_x.exe";
	}else{	
		getXlxs();
		getYctj();
		getCbcgl();
	}
});

function flashChecker(){
	var hasFlash=0;         //是否安装了flash
	var isIE=/*@cc_on!@*/0;      //是否IE浏览器
	
	var swf;
	if(isIE) {
		try{
			swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash'); 
		}catch(e){
		}
	}
	if(swf) {
		hasFlash=1;
	} else {
		if (navigator.plugins && navigator.plugins.length > 0){
			var swf=navigator.plugins["Shockwave Flash"];
			if (swf){
				hasFlash=1;
			}
		}
	}
	return hasFlash;
}


function getCbcgl() {
	dwrAction.doAjax("mainPageAction", "getMDMCbcgl", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			var chart = new FusionCharts(MSColumn3D, "cbcglId", "550", "200");
			chart.setDataXML(obj.barXml);
			chart.render(cgl);
		}
	});
}

function getXlxs() {
	dwrAction.doAjax("mainPageAction", "getMDMXlxs", {nodeId:$F('nodeId'),
		nodeType:$F('nodeType'),
		nodeDwdm:$F('nodeDwdm'),
		czyId:$F('czyId')}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			var chart = new FusionCharts(MSColumn3D, "cbcglId1", "550", "200");
			chart.setDataXML(obj.barXml);
			chart.render(xlxs);
		}
	});
}

function getYctj() {
	dwrAction.doAjax("mainPageAction", "getMDMYctj", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			var chartpie = new FusionCharts(Pie3D, "yctjId", "550", "200", "0", "0");
		   	chartpie.setDataXML(obj.pieXml);					   
		   	chartpie.render("yctj");
		}
	});
}