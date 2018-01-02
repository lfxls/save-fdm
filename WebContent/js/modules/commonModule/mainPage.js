Ext.onReady(function() {
	var hasFlash = flashChecker();

	if(hasFlash==0) {
		alert("Your computer installed without flash, please install");
		location.href = ctx + "/public/fusionchart3/install_flash_player_11_active_x.exe";
	}else{	
		getDatj();
		getTxzxl();
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



function getDatj() {
	dwrAction.doAjax("mainPageAction", "getDatj", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			$('da_zbyh').innerHTML = '&nbsp;' + obj.zbtyyhs;
			$('da_zbzd').innerHTML = '&nbsp;' + obj.zbtyzds;

			$('da_gbyh').innerHTML = '&nbsp;' + obj.gbtyjlds;
			$('da_gbzd').innerHTML = '&nbsp;' + obj.gbtyzds;

			$('da_dyyh').innerHTML = '&nbsp;' + obj.dytyyhs;
			$('da_dyzd').innerHTML = '&nbsp;' + obj.dytyzds;
		}
	});

}

function getTxzxl() {
	dwrAction.doAjax("mainPageAction", "getTxzxl", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			if  ( FusionCharts( "txzxlId1" ) )  FusionCharts( "txzxlId1" ).dispose();
			var ac1 = new FusionCharts(AngularGauges, "txzxlId1", "140", "140");
			ac1.setDataXML(obj.zbxml);
			ac1.render("ac1");

			var obj = Ext.decode(res.msg);
			if  ( FusionCharts( "txzxlId2" ) )  FusionCharts( "txzxlId2" ).dispose();
			var ac2 = new FusionCharts(AngularGauges, "txzxlId2", "140", "140");
			ac2.setDataXML(obj.gbxml);
			ac2.render("ac2");

			var obj = Ext.decode(res.msg);
			if  ( FusionCharts( "txzxlId3" ) )  FusionCharts( "txzxlId3" ).dispose();
			var ac3 = new FusionCharts(AngularGauges, "txzxlId3", "140", "140");
			ac3.setDataXML(obj.dyxml);
			ac3.render("ac3");

			$('zbzx').innerHTML = '&nbsp;' + obj.zbtxcgl24 +'%';
			$('gbzx').innerHTML = '&nbsp;' + obj.gbtxcgl24 +'%';
			$('dyzx').innerHTML = '&nbsp;' + obj.dytxcgl24 +'%';
		}
	});
}

function getCbcgl() {
	dwrAction.doAjax("mainPageAction", "getCbcgl", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			if  ( FusionCharts( "cbcglId" ) )  FusionCharts( "cbcglId" ).dispose();
			var chart = new FusionCharts(MSColumn3D, "cbcglId", "550", "200");
			chart.setDataXML(obj.barXml);
			chart.render(cgl);
		}
	});
}

function getYctj() {
	dwrAction.doAjax("mainPageAction", "getYctj", {}, function(res) {
		if (res.success) {
			var obj = Ext.decode(res.msg);
			if  ( FusionCharts( "yctjId" ) )  FusionCharts( "yctjId" ).dispose();
			var chartpie = new FusionCharts(Pie3D, "yctjId", "550", "200", "0", "0");
		   	chartpie.setDataXML(obj.pieXml);					   
		   	chartpie.render("yctj");
		}
	});
}