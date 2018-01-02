var map;//地图对象
var baseLayer;//底图
var concentLayer;//终端图层
var meterLayer;//表计图层
var transformerLayer;//变压器图层
var centerPoint;//中心点

var popup;//弹出框
var selectedFeature;//选择的节点
var selectControl;//选择控制

var filter=0;//Meter filter
var zdlx='05';//默认终端类型为GPRS表

handlerType = "query";

Ext.onReady(function() {
    map = new OpenLayers.Map('map',{
        controls: [
            new OpenLayers.Control.Navigation(),
            new OpenLayers.Control.PanZoomBar(),
            new OpenLayers.Control.ScaleLine(),
            new OpenLayers.Control.KeyboardDefaults()
        ],
        numZoomLevels:19,//缩放级别数,当前默认级别为1-18
        projection: new OpenLayers.Projection("EPSG:900913"),//坐标系转换
        displayProjection: new OpenLayers.Projection("EPSG:4326")
    });

    map.events.register("moveend",map, onMoveend);//移动事件
    
    initBaseLayer();//初始化底图
    intiConcentLayer();//初始化终端图层
    initMeterLayer();//初始化表计图层
    initTransformerLayer();//初化化变压器图层
    
//    map.zoomToExtent();
    addSelectControl();//添加选择控制
    defaultCenter();//获取地图显示中心位置
    
    window.setInterval('loadAlarms()',30000);//1000为1秒钟
});

/**
 * 初始化底图
 */
function initBaseLayer(){
	//初始化底图
	if(mapType==0){
		baseLayer = new OpenLayers.Layer.OSM();
	}else if(mapType==1){
		baseLayer = new OpenLayers.Layer.WMS(
				"hexing:china_base",offlineMap_url,
				{layers: offlineMap_layerName, format: 'image/png' },
				{ tileSize: new OpenLayers.Size(256,256)});
	}else{
		baseLayer = new OpenLayers.Layer.Google("Google Streets",{numZoomLevels: 19});
	}
	centerPoint=new OpenLayers.Layer.Vector("Center point");
    map.addLayers([baseLayer,centerPoint]);
}

/**
 * 初始化终端图层
 */
function intiConcentLayer(){
	concentLayer =new OpenLayers.Layer.Vector("Concent layer");
    map.addLayer(concentLayer);
}

/**
 * 初始化表计图层
 */
function initMeterLayer(){
	meterLayer = new OpenLayers.Layer.Vector("Meter layer");
    map.addLayer(meterLayer);
}

/**
 * 初始化变压器图层
 */
function initTransformerLayer(){
	transformerLayer = new OpenLayers.Layer.Vector("Transformer layer");
	map.addLayer(transformerLayer);
}

/**
 * 地图放大、缩小、拖动事件
 */
function onMoveend(evt){
	meterFilter();
}

/**
 * 地图居中
 */
function defaultCenter(){
	var param= {};
	dwrAction.doAjax("meterGisAction", "getCenterGeometry",param,
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					map.setCenter(new OpenLayers.LonLat(datas.longitude,datas.latitude).transform(map.displayProjection,map.getProjectionObject()),14);
					var feature  = new OpenLayers.Feature.Vector(
							new OpenLayers.Geometry.Point(datas.longitude,datas.latitude).transform(map.displayProjection, map.getProjectionObject()),null,
							{externalGraphic: "../../public/images/gis/center.png", graphicHeight: 48, graphicWidth: 48}
					);
					feature.style.title=gisModule_gis_default_center;
					feature.style.cursor="pointer";
					feature.attributes={"TYPE":"default"};
					centerPoint.addFeatures([feature]);
				} 
			}
		});
}

/**
 * 添加选择控制
 */
function addSelectControl(){
    selectControl = new OpenLayers.Control.SelectFeature([transformerLayer,concentLayer,meterLayer,centerPoint], {
        clickout: false,
        toggle: true,
        multiple: false,
        hover: false,
        onSelect: onFeatureSelect,
        onUnselect: onFeatureUnselect
    });
    map.addControl(selectControl);
    selectControl.activate();
    
}

/**
 * 构造弹出窗口
 */
function onFeatureSelect(feature) {
    for( var i = map.popups.length - 1; i >= 0; i-- ) { 
        map.removePopup(map.popups[i]); 
    };
    selectedFeature = feature;
    if(feature.attributes.TYPE=="concent"){
    	dwrAction.doAjax("meterGisAction", "getConcentById",{"ZDJH":feature.attributes.ZDJH},
			function(res) {
				if (res != null) {
					if (res.success) {
						var datas = res.dataObject;
						var markerPopups=$("#concentPopups").html().replace("#CustomerNo#",feature.attributes.ZDJH)
						.replace("#zdlx#",feature.attributes.ZDLX=='03'?gisModule_concent_zdlx_concent:gisModule_concent_zdlx_gprs)
						.replace("#bjsl#",datas.BJSL)
						.replace("onclick=\"toEvent();\"","onclick=\"toEvent(\'"+feature.attributes.ZDJH+"\',\'"+feature.attributes.ZDJH+"\',\'sb\'"+");\"")
						.replace("#state#",feature.attributes.STATE>0?gisModule_concent_state_online:gisModule_concent_state_offline);
						popup = new OpenLayers.Popup.FramedCloud("concent",
					            feature.geometry.getBounds().getCenterLonLat(),
					            new OpenLayers.Size(250,75),
					            markerPopups,null, true, onPopupClose);
					    		feature.popup = popup;
					    map.addPopup(popup);
					} 
				}
			});
    }else if(feature.attributes.TYPE=="meter"){
    	if(feature.attributes.BJLX=='02'){
    		dwrAction.doAjax("meterGisAction", "getGprsMeterInfoById",{"BJJH":feature.attributes.BJJH},
    				function(res) {
    					if (res != null) {
    						if (res.success) {
    							var datas = res.dataObject;
    							var markerPopups=$("#gprsMeterPopups").html().replace("#CustomerNo#",feature.attributes.BJJH)
    								.replace("#HH#",datas.HH!=null?datas.HH:"--").replace("#HM#",datas.HM!=null?datas.HM:"--")
    								.replace("#V_AVG_A#",datas.AXDY!=null?datas.AXDY+"(V)":'--').replace("#V_AVG_B#",datas.BXDY!=null?datas.BXDY+"(V)":"--").replace("#V_AVG_C#",datas.CXDY!=null?datas.CXDY+"(V)":"--")
    								.replace("#A_AVG_A#",datas.AXDL!=null?datas.AXDL+"(A)":"--").replace("#A_AVG_B#",datas.BXDL!=null?datas.BXDL+"(A)":"--").replace("#A_AVG_C#",datas.CXDL!=null?datas.CXDL+"(A)":"--")
    								.replace("#PF_AVG_A#",datas.AXGLYS!=null?datas.AXGLYS+"(%)":"--").replace("#PF_AVG_B#",datas.BXGLYS!=null?datas.BXGLYS+"(%)":"--").replace("#PF_AVG_C#",datas.CXGLYS!=null?datas.CXGLYS+"(%)":"--")
    								.replace("#PF_AVG_TOTAL#",datas.GLYS!=null?datas.GLYS+"(%)":"--").replace("#ZXYGZ#",datas.ZXYGZ!=null?datas.ZXYGZ+"(kWh)":"--").replace("#FXYGZ#",datas.FXYGZ!=null?datas.FXYGZ+"(kWh)":"--")
    								.replace("#ZXWGZ#",datas.ZXWGZ!=null?datas.ZXWGZ+"(kvarh)":"--").replace("#FXWGZ#",datas.FXWGZ!=null?datas.FXWGZ+"(kvarh)":"--")
    								.replace("onclick=\"toEvent();\"","onclick=\"toEvent(\'"+feature.attributes.BJJH+"\',\'"+feature.attributes.BJJH+"\',\'bj\'"+");\"")
    								.replace("#state#",feature.attributes.ALARM>0?gisModule_meter_state_alarm:gisModule_meter_state_normal);
    						    popup = new OpenLayers.Popup.FramedCloud("meter",
    						            feature.geometry.getBounds().getCenterLonLat(),
    						            new OpenLayers.Size(250,75),
    						            markerPopups,null, true, onPopupClose);
    						    		feature.popup = popup;
    						    map.addPopup(popup);
    						} 
    					}
    				});
    	}else{
    		dwrAction.doAjax("meterGisAction", "getMeterInfoById",{"BJJH":feature.attributes.BJJH},
    				function(res) {
    					if (res != null) {
    						if (res.success) {
    							var datas = res.dataObject;
    							var markerPopups=$("#meterPopups").html().replace("#CustomerNo#",feature.attributes.BJJH)
    								.replace("#HH#",datas.HH!=null?datas.HH:"--").replace("#HM#",datas.HM!=null?datas.HM:"--")
    								.replace("#ZXYGZ#",datas.ZXYGZ!=null?datas.ZXYGZ+"(kWh)":"--").replace("#FXYGZ#",datas.FXYGZ!=null?datas.FXYGZ+"(kvarh)":"--")
    								.replace("#ZXWGZ#",datas.ZXWGZ!=null?datas.ZXWGZ+"(kWh)":"--").replace("#FXWGZ#",datas.FXWGZ!=null?datas.FXWGZ+"(kvarh)":"--")
    								.replace("onclick=\"toEvent();\"","onclick=\"toEvent(\'"+feature.attributes.BJJH+"\',\'"+feature.attributes.BJJH+"\',\'bj\'"+");\"")
    								.replace("#state#",feature.attributes.ALARM>0?gisModule_meter_state_alarm:gisModule_meter_state_normal);
    						    popup = new OpenLayers.Popup.FramedCloud("meter",
    						            feature.geometry.getBounds().getCenterLonLat(),
    						            new OpenLayers.Size(250,75),
    						            markerPopups,null, true, onPopupClose);
    						    		feature.popup = popup;
    						    map.addPopup(popup);
    						} 
    					}
    				});
    	}
    	
    	
    }else if(feature.attributes.TYPE=="transformer"){
    	if(feature.attributes.BZ=="01"){
    		dwrAction.doAjax("meterGisAction", "getPublicTransInfoById",{"BYQID":feature.attributes.BYQID},
        			function(res) {
        				if (res != null) {
        					if (res.success) {
        						var datas = res.dataObject;
        						var alarm = getLastAlarmByMeterId(datas.BJJH);
        						var markerPopups=$("#publicTransPopups").html().replace("#MC#",datas.MC)
        						.replace("#RL#",datas.RL+"kVA").replace("#BJSL#",datas.BJSL)
        						.replace("#ALARM#",alarm!=null&&alarm.GJMC!=null?alarm.GJMC:"--")
        						.replace("onclick=\"toEvent();\"","onclick=\"toEvent(\'"+datas.BYQID+"\',\'"+datas.MC+"\',\'byq\'"+");\"")
        						.replace("onclick=\"toBJ();\"","onclick=\"toBJ(\'"+datas.BYQID+"\',\'"+datas.MC+"\',\'byq\'"+");\"")
        						.replace("#ALARM_TIME#",alarm!=null&&alarm.GJMC!=null?alarm.GJMC:"--");
        					    popup = new OpenLayers.Popup.FramedCloud("transformer",
        					            feature.geometry.getBounds().getCenterLonLat(),
        					            new OpenLayers.Size(250,75),
        					            markerPopups,null, true, onPopupClose);
        					    		feature.popup = popup;
        					    map.addPopup(popup);
        					} 
        				}
        			});
    	}else{
    		dwrAction.doAjax("meterGisAction", "getSpecialTransInfoById",{"DWDM":feature.attributes.DWDM,"BYQID":feature.attributes.BYQID},
        			function(res) {
        				if (res != null) {
        					if (res.success) {
        						var datas = res.dataObject;
        						var markerPopups=$("#specialTransPopups").html().replace("#MC#",datas.MC)
        						.replace("onclick=\"toEvent();\"","onclick=\"toEvent(\'"+datas.BYQID+"\',\'"+datas.MC+"\',\'byq\'"+");\"")
        						.replace("onclick=\"toYH();\"","onclick=\"toYH(\'"+datas.BYQID+"\',\'"+datas.MC+"\',\'byq\'"+");\"")
        						.replace("#RL#",datas.RL+"kVA").replace("#YHSL#",datas.YHSL);
        					    popup = new OpenLayers.Popup.FramedCloud("transformer",
        					            feature.geometry.getBounds().getCenterLonLat(),
        					            new OpenLayers.Size(250,75),
        					            markerPopups,null, true, onPopupClose);
        					    		feature.popup = popup;
        					    map.addPopup(popup);
        					} 
        				}
        			});
    	}
    	
    }
    
    
}

function getLastAlarmByMeterId(BJJH){
	if(BJJH!=null){
		dwrAction.doAjax("meterGisAction", "getLastAlarmByMeterId",{"BJJH":BJJH},
			function(res) {
				if (res != null) {
					if (res.success) {
						return res.dataObject;
					} 
				}
			});
	}
	return null;
}

/**
 * 销毁弹出窗口
 */
function onFeatureUnselect(feature) {
    map.removePopup(feature.popup);
    feature.popup.destroy();
    feature.popup = null;
    for( var i = map.popups.length - 1; i >= 0; i-- ) { 
        map.removePopup(map.popups[i]); 
    };
}

/**
 * 弹出框关闭
 */
function onPopupClose(evt) {
    selectControl.unselect(selectedFeature);
}

/**
 * 加载变压器
 * @param minLat
 * @param minLng
 * @param maxLat
 * @param maxLng
 * @param nodeId
 * @param nodeType
 */
function loadTransformers(minLat,minLng,maxLat,maxLng,nodeId,nodeType){
	var baseParam = {
			minLng : minLng,
	        maxLng : maxLng,
	        minLat : minLat,
	        maxLat : maxLat,
	        nodeId:nodeId,
	        nodeType:nodeType
		};
	var mothed = "getGeometryTransformers";
	if(nodeType=='bjqz'){
		mothed = "getGeometryGroups";
	}
	dwrAction.doAjax("meterGisAction", mothed, baseParam,
			function(res) {
				if (res != null) {
					if (res.success) {
						var datas = res.dataObject;
						for ( var i = 0; i < datas.length; i++) {
							if(nodeType=='bjqz'){
								var meter = datas[i];
								if(getMeterById(meter.BJJH)==null){
									if(filter==101){//只显示离线GPRS表
										if(meter.BJLX=='02'&&(meter.STATE==null||meter.STATE==0)){
											var feature  = new OpenLayers.Feature.Vector(
													new OpenLayers.Geometry.Point(meter.JD,meter.WD).transform(map.displayProjection, map.getProjectionObject()),null,
													{externalGraphic: "../../public/images/gis/meter-gray.png", graphicHeight: 32, graphicWidth: 32  }
											);
											feature.style.title=meter.BJJH;
											feature.style.cursor="pointer";
											feature.attributes={"BJJH":meter.BJJH,"TYPE":"meter","ALARM":meter.ALARM,"BJLX":meter.BJLX};
											meterLayer.addFeatures([feature]);
										}
									}else{//显示所有表计
										var fraphic="";
										if(meter.ALARM>0){
											//报警状态
											fraphic="../../public/images/gis/meter-red.png";
										}else{
											//GRPS表
											if(meter.BJLX=="02"){
												//GRPS表显示在线和离线状态
												if(meter.STATE==null||meter.STATE==0){
													//离线状态
													fraphic="../../public/images/gis/meter-gray.png";
												}else{
													//正常状态
													fraphic="../../public/images/gis/meter-green.png";
												}
											}else{
												//非GRPS表只显示正常状态
												fraphic="../../public/images/gis/meter-green.png";
											}
										}
										var feature  = new OpenLayers.Feature.Vector(
												new OpenLayers.Geometry.Point(meter.JD,meter.WD).transform(map.displayProjection, map.getProjectionObject()),null,
												{externalGraphic: fraphic, graphicHeight: 32, graphicWidth: 32  }
										);
										feature.style.title=meter.BJJH;
										feature.style.cursor="pointer";
										feature.attributes={"BJJH":meter.BJJH,"TYPE":"meter","ALARM":meter.ALARM,"BJLX":meter.BJLX};
										meterLayer.addFeatures([feature]);
									}
								}
							}
							else{
							var transformer = datas[i];
							if(getTransformerById(transformer.BYQID)==null){
//								var fraphic=transformer.ALARM>0?"../../public/images/gis/transformer-red.png":"../../public/images/gis/transformer-green.png";
								
								//此处对变压器的类型和状态进行区分--zhangc
								/**
								 * 公变
								 * DT_transformer_red
								 * DT_transformer_gray
								 * DT_transformer_green
								 * 专变
								 * PT_transformer_red
								 * PT_transformer_gray
								 * PT_transformer_green
								 */
								var fraphic= "" ;
								var transformerArrayImages = [	
								                              					["../../public/images/gis/DT_transformer_red.png","../../public/images/gis/DT_transformer_green.png"],
								                              					["../../public/images/gis/PT_transformer_red.png", "../../public/images/gis/PT_transformer_green.png"] 
								                              				] ; 
								
								if (transformer.BZ== "01"){
									//专变
									if (transformer.ALARM>0){
										fraphic = transformerArrayImages[0][0]; 
									}else{
										fraphic = transformerArrayImages[0][1];
									}
								}else if (transformer.BZ== "02"){
									//公变
									if (transformer.ALARM>0){
										fraphic = transformerArrayImages[1][0]; 
									}else{
										fraphic = transformerArrayImages[1][1];
									}
								}
								
								
								var feature  = new OpenLayers.Feature.Vector(
										new OpenLayers.Geometry.Point(transformer.JD,transformer.WD).transform(map.displayProjection, map.getProjectionObject()),null,
										{externalGraphic: fraphic, graphicHeight: 24, graphicWidth: 24}
								);
								feature.style.title=transformer.MC;
								feature.style.cursor="pointer";
								feature.attributes={"BYQID":transformer.BYQID,"ALARM":transformer.ALARM,"TYPE":"transformer","BZ":transformer.BZ,"DWDM":transformer.DWDM};
								transformerLayer.addFeatures([feature]);
							}
							}
						}
					} 
				}
			});
}

/**
 * 加载报警变压器
 * @param minLat
 * @param minLng
 * @param maxLat
 * @param maxLng
 * @param nodeId
 * @param nodeType
 */
function loadAlarmTransformers(nodeId,nodeType){
	var baseParam = {
			filter:filter,
	        nodeId:nodeId,
	        nodeType:nodeType
		};
	dwrAction.doAjax("meterGisAction", "getAlarmTransformers", baseParam,
			function(res) {
				if (res != null) {
					if (res.success) {
						var datas = res.dataObject;
						for ( var i = 0; i < datas.length; i++) {
							var transformer = datas[i];
							if(getTransformerById(transformer.BYQID)==null){
								var fraphic=transformer.ALARM>0?"../../public/images/gis/transformer-red.png":"../../public/images/gis/transformer-green.png";
								var feature  = new OpenLayers.Feature.Vector(
										new OpenLayers.Geometry.Point(transformer.JD,transformer.WD).transform(map.displayProjection, map.getProjectionObject()),null,
										{externalGraphic: fraphic, graphicHeight: 24, graphicWidth: 24  }
								);
								feature.style.title=transformer.MC;
								feature.style.cursor="pointer";
								feature.attributes={"BYQID":transformer.BYQID,"ALARM":transformer.ALARM,"TYPE":"transformer","BZ":transformer.BZ,"DWDM":transformer.DWDM};
								transformerLayer.addFeatures([feature]);
							}
						}
					} 
				}
			});
}

/**
 * 加载终端数据
 */
function loadConcents(minLat,minLng,maxLat,maxLng,nodeId,nodeType){
	var baseParam = {
			minLng : minLng,
	        maxLng : maxLng,
	        minLat : minLat,
	        maxLat : maxLat,
	        nodeId:nodeId,
	        nodeType:nodeType
		};
	dwrAction.doAjax("meterGisAction", "getGeometryConcents", baseParam,
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					for ( var i = 0; i < datas.length; i++) {
						var concent = datas[i];
						if(getConcentById(concent.ZDJH)==null){
							//显示离线集中器
							if(filter==101){
								if(concent.STATE==null||concent.STATE==0){
									var feature  = new OpenLayers.Feature.Vector(
											new OpenLayers.Geometry.Point(concent.JD,concent.WD).transform(map.displayProjection, map.getProjectionObject()),null,
											{externalGraphic: "../../public/images/gis/utility-gray.png", graphicHeight: 32, graphicWidth: 32}
									);
									feature.style.title=concent.ZDJH;
									feature.style.cursor="pointer";
									feature.attributes={"ZDJH":concent.ZDJH,"STATE":concent.STATE,"TYPE":"concent","ZDLX":concent.ZDLX};
									concentLayer.addFeatures([feature]);
								}
							}else{
								var fraphic = "";
								if(concent.STATE==null||concent.STATE==0){
									fraphic="../../public/images/gis/utility-gray.png";
								}else{
									fraphic="../../public/images/gis/utility-green.png";
								}
								var feature  = new OpenLayers.Feature.Vector(
										new OpenLayers.Geometry.Point(concent.JD,concent.WD).transform(map.displayProjection, map.getProjectionObject()),null,
										{externalGraphic: fraphic, graphicHeight: 32, graphicWidth: 32}
								);
								feature.style.title=concent.ZDJH;
								feature.style.cursor="pointer";
								feature.attributes={"ZDJH":concent.ZDJH,"STATE":concent.STATE,"TYPE":"concent","ZDLX":concent.ZDLX};
								concentLayer.addFeatures([feature]);
							}
						}
					}
				} 
			}
		});
 
}

/**
 * 加载表计数据
 */
function loadMeters(minLat,minLng,maxLat,maxLng,nodeId,nodeType){ 
	var baseParam = {
		minLng : minLng,
        maxLng : maxLng,
        minLat : minLat,
        maxLat : maxLat,
        nodeId:nodeId,
        nodeType:nodeType
	};
	
	dwrAction.doAjax("meterGisAction", "getMeterByCoordinate", baseParam,
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					for ( var i = 0; i < datas.length; i++) {
						var meter = datas[i];
						if(getMeterById(meter.BJJH)==null){
							if(filter==101){//只显示离线GPRS表
								if(meter.BJLX=='02'&&(meter.STATE==null||meter.STATE==0)){
									var feature  = new OpenLayers.Feature.Vector(
											new OpenLayers.Geometry.Point(meter.JD,meter.WD).transform(map.displayProjection, map.getProjectionObject()),null,
											{externalGraphic: "../../public/images/gis/meter-gray.png", graphicHeight: 32, graphicWidth: 32  }
									);
									feature.style.title=meter.BJJH;
									feature.style.cursor="pointer";
									feature.attributes={"BJJH":meter.BJJH,"TYPE":"meter","ALARM":meter.ALARM,"BJLX":meter.BJLX};
									meterLayer.addFeatures([feature]);
								}
							}else{//显示所有表计
								var fraphic="";
								if(meter.ALARM>0){
									//报警状态
									fraphic="../../public/images/gis/meter-red.png";
								}else{
									//GRPS表
									if(meter.BJLX=="02"){
										//GRPS表显示在线和离线状态
										if(meter.STATE==null||meter.STATE==0){
											//离线状态
											fraphic="../../public/images/gis/meter-gray.png";
										}else{
											//正常状态
											fraphic="../../public/images/gis/meter-green.png";
										}
									}else{
										//非GRPS表只显示正常状态
										fraphic="../../public/images/gis/meter-green.png";
									}
								}
								var feature  = new OpenLayers.Feature.Vector(
										new OpenLayers.Geometry.Point(meter.JD,meter.WD).transform(map.displayProjection, map.getProjectionObject()),null,
										{externalGraphic: fraphic, graphicHeight: 32, graphicWidth: 32  }
								);
								feature.style.title=meter.BJJH;
								feature.style.cursor="pointer";
								feature.attributes={"BJJH":meter.BJJH,"TYPE":"meter","ALARM":meter.ALARM,"BJLX":meter.BJLX};
								meterLayer.addFeatures([feature]);
							}
						}
					}
				} 
			}
		});
}

/**
 * 加载报警表计数据
 */
function loadAlarmMeters(nodeId,nodeType){ 
	var baseParam = {
        filter : filter,
        nodeId:nodeId,
        nodeType:nodeType
	};
	dwrAction.doAjax("meterGisAction", "getAlarmMeters", baseParam,
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					for ( var i = 0; i < datas.length; i++) {
						var meter = datas[i];
						if(getMeterById(meter.BJJH)==null){
							var feature  = new OpenLayers.Feature.Vector(
									new OpenLayers.Geometry.Point(meter.JD,meter.WD).transform(map.displayProjection, map.getProjectionObject()),null,
									{externalGraphic: "../../public/images/gis/meter-red.png", graphicHeight: 32, graphicWidth: 32  }
							);
							feature.style.title=meter.BJJH;
							feature.style.cursor="pointer";
							feature.attributes={"BJJH":meter.BJJH,"TYPE":"meter","ALARM":meter.ALARM,"BJLX":meter.BJLX};
							meterLayer.addFeatures([feature]);
							
						}
					}
				} 
			}

		});
}

/**
 * 根据ID取得地图上的终端节点
 */
function getConcentById(ZDJH){
    var features = concentLayer.features;
    for (var i = 0; i < features.length; i++) {
        if (features[i].attributes.ZDJH==ZDJH) {
            return features[i];
        }
    }
    return null;
}

/**
 * 根据ID取得地图上的变压器节点
 * @param BYQID
 * @returns
 */
function getTransformerById(BYQID){
	var features = transformerLayer.features;
	for (var i = 0; i < features.length; i++) {
        if (features[i].attributes.BYQID==BYQID) {
            return features[i];
        }
    }
    return null;
}

/**
 * 根据ID取得地图上的表计节点
 */
function getMeterById(BJJH){
    var features = meterLayer.features;
    for (var i = 0; i < features.length; i++) {
        if (features[i].attributes.BJJH==BJJH) {
            return features[i];
        }
    }
    return null;
}

/**
 * 根据报警定位表计
 * @param BJJH
 * @param JD
 * @param WD
 */
function selectAlarmPoint(JD,WD){
	map.setCenter(new OpenLayers.LonLat(JD,WD).transform(map.displayProjection, map.getProjectionObject()),16);
}
function selectAlarmPoint(CLDJH,JD,WD){
//	map.setCenter(new OpenLayers.LonLat(JD,WD).transform(map.displayProjection, map.getProjectionObject()),16);
	$("#nodeId").val(CLDJH);
	$("#nodeType").val('bj');
	$("#selectNodeText").html(CLDJH);
	transformerLayer.removeAllFeatures();
	concentLayer.removeAllFeatures();
	meterLayer.removeAllFeatures();
	queryMeterById(CLDJH);
}
/**
 * 切换过滤条件
 * @param type
 */
function showMeterByType(type){
	filter = type;
    for( var i = map.popups.length - 1; i >= 0; i-- ) { 
        map.removePopup(map.popups[i]); 
    };
	transformerLayer.removeAllFeatures();
	concentLayer.removeAllFeatures();
	meterLayer.removeAllFeatures();
	var nodeId=$("#nodeId").val();
	var nodeType = $("#nodeType").val();
	if( nodeType=='bj'){
		//如果在导航树上点击表计，居中指定表计
		queryMeterById(nodeId);
	}else if( nodeType=='sb'){
		//如果在导航树上点击集中器或GPRS表，居中指定集中器或GPRS表
		queryConcentById(nodeId);
	}else if( nodeType=='byq'){
		//如果在导航树上点击变压器，居中指定变压器，
		queryTransformerById(nodeId);
	}else{
		//移动中心位置
		moveToCenter(nodeId,nodeType);
	}

}

/**
 * 显示及过滤
 */
function meterFilter(){
	var zoom = map.getZoom();
	var nodeId=$("#nodeId").val();
	var nodeType = $("#nodeType").val();
	var extent=map.getExtent().transform(map.getProjectionObject(),map.displayProjection);
	if(zoom>15){
		centerPoint.display(false);
		if(filter==0){	
			//16,17,18三层，无过滤条件时显示所有
			transformerLayer.display(true);
			concentLayer.display(true);
			meterLayer.display(true);
			if(nodeType=='bj'){
				//如果过滤条件是表计，则只显示表计
				loadMeters(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
			}else if(nodeType=='sb'){
				//如果过滤条件是终端，需判断终端类型是集中器还是GPRS表，如果是GPRS表，只显示表计
				loadGprsMeterOrConcent(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
			} else{
				//如果点击是单位、线路、变压器，显示下面所有的变压器、集中器和表计；
				loadTransformers(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
				if(nodeType!='bjqz'){
					loadConcents(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
					loadMeters(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
				}
			}
		}else if(filter==101){		
			//16,17,18三层，过滤条件为离线，显示离线集中器和GRPS表，隐藏变压器
			transformerLayer.display(false);
			concentLayer.display(true);
			meterLayer.display(true);
			if(nodeType=='bj'){
				loadMeters(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
			}else if(nodeType=='sb'){
				loadGprsMeterOrConcent(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
			}else{
				if(nodeType!='bjqz'){
					loadConcents(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
					loadMeters(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
				}
			}
		}else{						
			//16,17,18三层，过滤条件为报警，显示报警变压器和表计，隐藏集中器
			transformerLayer.display(true);
			concentLayer.display(false);
			meterLayer.display(true);
			if(nodeType!='sb'){
				if(nodeType!='bj'){
					loadAlarmTransformers(nodeId,nodeType);
				}
				loadAlarmMeters(nodeId, nodeType);
			}
		}
	}else if(zoom>9){
		centerPoint.display(false);
		if(filter==0){
			//14-15两层，无过滤条件时，显示变压器和集中器，隐藏表计
			transformerLayer.display(true);
			concentLayer.display(true);
			meterLayer.display(false);
			if(nodeType!='bj' ){
				if(nodeType!='sb'){
					meterLayer.display(true);
					loadTransformers(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
				}
				if(nodeType!='bjqz'){
					loadConcents(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
				}
			}
		}else if(filter==101){
			//14-15两层，过滤条件为离线时，显示离线集中器，隐藏离线表计和变压器
			transformerLayer.display(false);
			concentLayer.display(true);
			meterLayer.display(false);
			if(nodeType!='bj'&&nodeType!='bjqz'){
				loadConcents(extent.left,extent.bottom,extent.right,extent.top,nodeId,nodeType);
			}
		}else{
			//14-15两层，过滤条件为离报警时，显示所有报警；隐藏集中器
			transformerLayer.display(true);
			concentLayer.display(false);
			meterLayer.display(true);
			if(nodeType!='sb'){
				if(nodeType!='bj'&&nodeType!='bjqz'){
					loadAlarmTransformers(nodeId,nodeType);
				}
				loadAlarmMeters(nodeId, nodeType);
			}
		}

	}else{
		//1-9层，隐藏所有节点，显示中心位置
		centerPoint.display(true);
		transformerLayer.display(false);
		concentLayer.display(false);
		meterLayer.display(true);
	}
}

/**
 * 点击左侧导航树
 * @param _node
 * @returns {Boolean}
 */
function checkNode(_node) {
    for( var i = map.popups.length - 1; i >= 0; i-- ) { 
        map.removePopup(map.popups[i]); 
    };
//	$('nodeId').value = _node.id;
//	$('nodeType').value= _node.attributes.ndType;

	$("#nodeId").val(_node.id);
	$("#nodeType").val(_node.attributes.ndType);
	transformerLayer.removeAllFeatures();
	concentLayer.removeAllFeatures();
	meterLayer.removeAllFeatures();
	if( _node.attributes.ndType=='bj'){
		//如果在导航树上点击表计，居中指定表计
		$("#selectNodeText").html(_node.id);
		queryMeterById(_node.id);
	}else if( _node.attributes.ndType=='sb'){
		//如果在导航树上点击集中器或GPRS表，居中指定集中器或GPRS表
		$("#selectNodeText").html(_node.id);
		queryConcentById(_node.id);
	}else if( _node.attributes.ndType=='byq'){
		//如果在导航树上点击变压器，居中指定变压器，
		$("#selectNodeText").html(_node.text);	//变压器 显示变压器名称
		queryTransformerById(_node.id);
	}else{
		//移动中心位置
		$("#selectNodeText").html(_node.text);
		moveToCenter(_node.id,_node.attributes.ndType);
	}
	return true;

}

/**
 * 根据ID查询表计并居中
 * @param BJJH
 */
function queryMeterById(BJJH){
	dwrAction.doAjax("meterGisAction", "getMeterInfoById",{"BJJH":BJJH},
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
			    	map.setCenter(new OpenLayers.LonLat(datas.JD,datas.WD).transform(map.displayProjection, map.getProjectionObject()),16);
				} 
			}
		});
}

function loadGprsMeterOrConcent(minLat,minLng,maxLat,maxLng,nodeId,nodeType){
	if(zdlx=='05'){
		dwrAction.doAjax("meterGisAction", "getGprsMeterById",{"ZDJH":nodeId},
				function(res) {
					if (res != null) {
						if (res.success) {
							var datas = res.dataObject;
							//GPRS表
							loadMeters(minLat,minLng,maxLat,maxLng,datas.BJJH,"bj");
						} 
					}
				});
	}else{
		loadConcents(minLat,minLng,maxLat,maxLng,nodeId,nodeType);
	}
}

/**
 * 根据ID查询集中器并居中
 * @param BJJH
 */
function queryConcentById(ZDJH){
	dwrAction.doAjax("meterGisAction", "getConcentById",{"ZDJH":ZDJH},
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					//GPRS表
					var zoomLevel=datas.ZDLX=='05'?16:14;
					zdlx=datas.ZDLX;
					map.setCenter(new OpenLayers.LonLat(datas.JD,datas.WD).transform(map.displayProjection, map.getProjectionObject()),zoomLevel);	
				} 
			}
		});
}

/**
 * 根据ID查询变压器并居中
 * @param BJJH
 */
function queryTransformerById(BYQID){
	dwrAction.doAjax("meterGisAction", "getTransformerById",{"BYQID":BYQID},
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					map.setCenter(new OpenLayers.LonLat(datas.JD,datas.WD).transform(map.displayProjection, map.getProjectionObject()),16);	
				} 
			}
		});
}

/**
 * 导出报警Excel
 * @param alarmType
 */
function exportAlarmMeters(alarmType){
	var alarmTypeName="";
	switch(alarmType){
		case 1:
			alarmTypeName=gisModule_filter_phaseLoss;
			break;
		case 2:
			alarmTypeName=gisModule_filter_threephaseUnbalance;
			break;
		case 3:
			alarmTypeName=gisModule_filter_underVoltage;
			break;
		case 4:
			alarmTypeName=gisModule_filter_overVoltage;
			break;
		case 5:
			alarmTypeName=gisModule_filter_overLoad;
			break;
		case 6:
			alarmTypeName=gisModule_filter_tamper;
			break;
		case 7:
			alarmTypeName=gisModule_filter_tempetrature;
			break;
		case 8:
			alarmTypeName=gisModule_filter_gass;
			break;
		case 9:
			alarmTypeName=gisModule_filter_oil;
			break;
		case 10:
			alarmTypeName=gisModule_filter_relay;
			break;
		case 11:
			alarmTypeName=gisModule_filter_power;
			break;
		case 100:
			alarmTypeName=gisModule_filter_all;
			break;
		default:
			break;
	}
	$("#filter").val(alarmType);
	$("#excelTextList").val(alarmTypeName);
	$('#exportForm').submit();
 
}

/**
 * 跳转到事件页面
 */
function toEvent(nodeId,nodeText,nodeType) {
//	var url = ctx + "/basic/zjgl/Qdgj!eventDetail.do";
//	var url = String.format(ctx + "/run/yccl/zdgjcx!init.do?nodeId={0}&nodeText={1}&nodeType={2}&falg={3}",$("#nodeId").val(),$("#nodeId").val(),$("#nodeType").val(),"1");
	var url = String.format(ctx + "/run/yccl/zdgjcx!init.do?nodeId={0}&nodeText={1}&nodeType={2}&falg={3}",nodeId,nodeText,nodeType,"1");
	top.openpageOnTree(gisModule_eventDetail,"14730",gisModule_eventDetail,'eventDetail',url,'yes',1);
}

function toBJ(){
	var url = ctx + "/basic/dagl/bjgl!init.do";
	top.openpageOnTree(gisModule_bjgl,"12200",gisModule_bjgl,gisModule_bjgl,url,'yes',1);
}
function toBJ(nodeId,nodeText,nodeType){
//	var url = ctx + "/basic/dagl/bjgl!init.do";
	var url = String.format(ctx + "/basic/dagl/bjgl!init.do?nodeId={0}&nodeText={1}&nodeType={2}",nodeId,nodeText,nodeType);
	top.openpageOnTree(gisModule_bjgl,"12200",gisModule_bjgl,gisModule_bjgl,url,'yes',1);
}

function toYH(){
	var url = ctx + "/basic/dagl/yhgl!init.do";
	top.openpageOnTree(gisModule_yhgl,"12100",gisModule_yhgl,gisModule_yhgl,url,'yes',1);
}

function toYH(nodeId,nodeText,nodeType){
	var url = String.format(ctx + "/basic/dagl/yhgl!init.do?nodeId={0}&nodeText={1}&nodeType={2}",nodeId,nodeText,nodeType);
	top.openpageOnTree(gisModule_yhgl,"12100",gisModule_yhgl,gisModule_yhgl,url,'yes',1);
}

/**
 * 移动到中心位置
 * @param nodeId
 * @param nodeType
 */
function moveToCenter(nodeId,nodeType){
	var param= {nodeId:nodeId,nodeType:nodeType};
	if (nodeType == 'bjqz') {
		dwrAction.doAjax("meterGisAction", "getCenterGroupsGeometry",
				param, function(res) {
					if (res != null) {
						if (res.success) {
							var datas = res.dataObject;
							var level = filter == 101 ? 16 : 14
							if(datas.DJD>0.01){
								level=level-3;
							}
							map.setCenter(new OpenLayers.LonLat(datas.JD,
									datas.WD).transform(map.displayProjection,
									map.getProjectionObject()),
									level);

						}
					}
				});
	} else {
		dwrAction.doAjax("meterGisAction", "getCenterTransformerGeometry",
				param, function(res) {
					if (res != null) {
						if (res.success) {
							var datas = res.dataObject;
							map.setCenter(new OpenLayers.LonLat(datas.JD,
									datas.WD).transform(map.displayProjection,
									map.getProjectionObject()),
									filter == 101 ? 16 : 14);
						}
					}
				});
	}
}

/**
 * 第隔一分钟加载报警
 */
function loadAlarms(){
	dwrAction.doAjax("meterGisAction", "getRealAlarms",{},
		function(res) {
			if (res != null) {
				if (res.success) {
					var datas = res.dataObject;
					$("#alarmAmount").html(datas.length);
					$("#alarmTable  tr:not(:first)").empty();
					for ( var i = 0; i < datas.length; i++) {
						var alarm=datas[i];
						if(alarm.BJLX=='05'){
							var tr="<tr onclick='selectAlarmPoint("+alarm.BYQJD+","+alarm.BYQWD+");' style='cursor: pointer;'>"
									+"<td>"+alarm.CLDJH+"</td>"
									+"<td>"+alarm.MC+"</td>"
									+"<td class='data'>"+alarm.GJMC+"</td>"
									+"<td>"+Ext.util.Format.date(alarm.YCFSSJ,'Y-m-d H:i:s')+"</td>"
									+"</tr>";
							$("#alarmTable").append(tr);
						}else{
							var tr="<tr onclick='selectAlarmPoint(\""+alarm.CLDJH+"\","+alarm.JD+","+alarm.WD+");' style='cursor: pointer;'>"
							+"<td>"+alarm.CLDJH+"</td>"
							+"<td>"+alarm.MC+"</td>"
							+"<td class='data'>"+alarm.GJMC+"</td>"
							+"<td>"+Ext.util.Format.date(alarm.YCFSSJ,'Y-m-d H:i:s')+"</td>"
							+"</tr>";
							$("#alarmTable").append(tr);
						}
					}
				} 
			}
		});
}