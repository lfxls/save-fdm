Ext.onReady(function() {
	var hasFlash = flashChecker();
	if (hasFlash == 0) {
		alert("Your computer installed without flash, please install");
		location.href = ctx
				+ "/public/fusionchart3/install_flash_player_11_active_x.exe";
	} else {
		getDatj();
		getTxzxl();
		getGjtj();
		getCbcgl();
		autoRefresh();
	}
});

//自动刷新
function autoRefresh() {
    window.setInterval('query();', parseInt(10 * 60 * 1000)); //10分钟
}

function query() {
	getDatj();
	getTxzxl();
	getGjtj();
	getCbcgl();
}

function flashChecker() {
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

function getDatj() {
	dwrAction.doAjax("mainPageAction", "getMDCSbsl", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			$('sb_gprsb').innerHTML = '&nbsp;' + obj.gprsb;
			$('sb_jzq').innerHTML = '&nbsp;' + obj.jzq;
			$('sb_plcb').innerHTML = '&nbsp;' + obj.plcb;
		}
	});

}

function getTxzxl() {
	dwrAction.doAjax("sbzxjkAction", "getSbxlByzdlx", {},
			function(res) {
				if (res.success) {
					if  ( FusionCharts( "zxlId1" ) )  FusionCharts( "zxlId1" ).dispose();
					if  ( FusionCharts( "zxlId2" ) )  FusionCharts( "zxlId2" ).dispose();
					var obj = Ext.decode(res.msg);
					var ac1 = new FusionCharts(Pie3D, "zxlId1", "250", "200",
							"0", "0");
					ac1.setDataXML(obj.pieXmlGprs);
					ac1.render("ac1");
					
					
					var ac2 = new FusionCharts(Pie3D, "zxlId2", "250", "200",
							"0", "0");
					ac2.setDataXML(obj.pieXmlJzq);
					ac2.render("ac2");
				}
			});
}

function getCbcgl() {
	dwrAction.doAjax("mainPageAction", "getMDCCbcgl", {},
			function(res) {
				if (res.success) {
					if  ( FusionCharts( "cbcglId1" ) )  FusionCharts( "cbcglId1" ).dispose();
					if  ( FusionCharts( "cbcglId2" ) )  FusionCharts( "cbcglId2" ).dispose();
					if  ( FusionCharts( "cbcglId3" ) )  FusionCharts( "cbcglId3" ).dispose();
					var obj = Ext.decode(res.msg);
					var cb1 = new FusionCharts(AngularGauges, "cbcglId1",
							"140", "140");
					cb1.setDataXML(obj.dyxmljt);
					cb1.render("cb1");

					var obj = Ext.decode(res.msg);
					var cb2 = new FusionCharts(AngularGauges, "cbcglId2",
							"140", "140");
					cb2.setDataXML(obj.dyxmlzt);
					cb2.render("cb2");

					var obj = Ext.decode(res.msg);
					var cb3 = new FusionCharts(AngularGauges, "cbcglId3",
							"140", "140");
					cb3.setDataXML(obj.dyxmlqt);
					cb3.render("cb3");

					$('cbjt').innerHTML = '&nbsp;' + obj.cbcgljt + '%';
					$('cbzt').innerHTML = '&nbsp;' + obj.cbcglzt + '%';
					$('cbqt').innerHTML = '&nbsp;' + obj.cbcglqt + '%';
				}
			});
}

function getGjtj() {
	dwrAction.doAjax("mainPageAction", "getMDCGjtj", {}, function(res) {
		if (res.success) {
			if  ( FusionCharts( "gjtjId" ) )  FusionCharts( "gjtjId" ).dispose();
			var obj = Ext.decode(res.msg);
			var chartpie = new FusionCharts(Pie3D, "gjtjId");
			chartpie.setDataXML(obj.pieXml);
			chartpie.render("gjtj");
		}
	});
}