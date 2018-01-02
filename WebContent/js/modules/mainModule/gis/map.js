loadProperties('mainModule', 'mainModule_gis');
/*var center = [-7908084, 6177492];

// This dummy layer tells Google Maps to switch to its default map type
var googleLayer = new olgm.layer.Google();

var map = new ol.Map({
  // use OL3-Google-Maps recommended default interactions
  interactions: olgm.interaction.defaults(),
  layers: [
    googleLayer
  ],
  target: 'map',
  view: new ol.View({
    center: center,
    zoom: 12
  })
});

var olGM = new olgm.OLGoogleMaps({map: map}); // map is the ol.Map instance
olGM.activate();*/

/*var overviewMapControl = new ol.control.Attribution({
	// see in overviewmap-custom.html to see the custom CSS used
	//target: 'filter',
	collapseLabel : '\u00BB',
	label : '\u00AB',
	collapsed : false
});
*/

/*var osm = new ol.layer.Tile({
	source : new ol.source.OSM()
});

  var map = new ol.Map({
    layers: [osm, clusters],
    renderer: 'canvas',
    target: 'map',
    view: new ol.View({
      center: [0, 0],
      zoom: 2
    })
  });*/
//解决jQuery方法名冲突问题
jq = jQuery.noConflict();
//表计图层
var meterLayer = '';

//OpenStreetMap地图
var osm = new ol.layer.Tile({
	source : new ol.source.OSM()
});

//点击表计图标的弹出窗
var element = document.getElementById('popup');
var closer = document.getElementById('popup-closer');
var popup = new ol.Overlay({
	element: element,
    autoPan: true,
    autoPanAnimation: {
      duration: 250
    }
});
closer.onclick = function() {
	popup.setPosition(undefined);
	closer.blur();
	return false;
};

var map = new ol.Map({
	layers : [ osm ],
	overlays: [ popup ],
	target : 'map',
	//logo: 'Hexing',
	view : new ol.View({
		projection : 'EPSG:4326',
		center : [ centerLon, centerLat ],
		minZoom : 3,
		maxZoom : 16,
		zoomFactor : 2,
		zoom : 8
	})
});

//显示收缩条
zoomslider = new ol.control.ZoomSlider();
map.addControl(zoomslider);

//地图显示区域发生变化事件：重新加载其他图层
map.on('moveend', function (evt) {
	loadMeterLayer();
});

//地图上单击事件：如果是表计图标显示弹出框
map.on('click', function(evt) {
	//获取单击位置的表计图标对象
    var feature = map.forEachFeatureAtPixel(evt.pixel,
	    function(feature) {
	      return feature;
	    }
    );
    //如果是表计图标弹出表计信息显示窗口
    if (feature && feature.get('features').length == 1) {
    	var meterNo = feature.get('features')[0].get('msn');
    	dwrAction.doAjax("mapAction", "getMeter", {meterNo: meterNo},
			function(res) {
				if (res != null) {
					if (res.success) {
						var meter = res.dataObject;
						if(meter.lon != null && meter.lat != null){
							var coordinate = [parseFloat(meter.lon), parseFloat(meter.lat)];
							document.getElementById('msn').innerHTML = nullReplaceStr(getValue(meter.msn));
							document.getElementById('mstsn').innerHTML = nullReplaceStr(getValue(meter.mstsn));
					    	document.getElementById('busstypen').innerHTML = nullReplaceStr(getValue(meter.busstypen));
					    	document.getElementById('pstsn').innerHTML = nullReplaceStr(getValue(meter.pstsn));
					    	document.getElementById('cno').innerHTML = nullReplaceStr(getValue(meter.cno));
					    	document.getElementById('cname').innerHTML = nullReplaceStr(getValue(meter.cname));
					    	popup.setPosition(coordinate);
					    	element.style.display = '';
						}
					}
				}
    	});
    }
});

//在地图上加载表计图层
function loadMeterLayer(){
	var extent = getMapExtent();
	dwrAction.doAjax("mapAction", "getMeters", {minLon: extent.minLon, maxLon: extent.maxLon, minLat: extent.minLat, maxLat: extent.maxLat},
		function(res) {
			if (res != null) {
				if (res.success) {
					map.removeLayer(meterLayer);
					
					var meters = res.dataObject;
					var features = new Array(meters.length);
					for (var i = 0; i < meters.length; ++i) {
					  var coordinates = [parseFloat(meters[i].lon), parseFloat(meters[i].lat)];
					  features[i] = new ol.Feature({
						  geometry: new ol.geom.Point(ol.proj.fromLonLat(coordinates, 'EPSG:4326')),
						  msn: meters[i].msn,
						  psts: meters[i].psts,
						  msts: meters[i].status
					  });
					}
					
					var source = new ol.source.Vector({
					  features: features
					});

					var clusterSource = new ol.source.Cluster({
					  distance: 40,
					  source: source
					});

					meterLayer = new ol.layer.Vector({
					  source: clusterSource,
					  style: function(feature) {
					    var size = feature.get('features').length;
					    //如果聚合了多个表计显示绿色的聚合图标
				    	if(size > 1){
				    		style = new ol.style.Style({
					          image: new ol.style.Icon({
					        	  src : ctx + '/public/images/gis/cluster-green.png'
					          }),
					          text: new ol.style.Text({
					              text: size.toString(),
					              stroke: new ol.style.Stroke({
					                  color: '#333'
					              })
					          })
					        });
				    	}else{
				    		//存在安装异常的电表以红色图标显示
				    		if(feature.get('features')[0].get('psts') == 5){
				    			style = new ol.style.Style({
					  	          image: new ol.style.Icon({
					  	        	  src : ctx + '/public/images/gis/meter-red.png'
					  	          }),
						          text: new ol.style.Text({
						              text: feature.get('features')[0].get('msn'),
						              stroke: new ol.style.Stroke({
						                  color: '#333'
						              })
						          })
					  	        });
				    		}else{//没有安装异常的电表以绿色图标显示
				    			if(feature.get('features')[0].get('msts') == 1){
					    			style = new ol.style.Style({
						  	          image: new ol.style.Icon({
						  	        	  src : ctx + '/public/images/gis/meter-green.png'
						  	          }),
							          text: new ol.style.Text({
							              text: feature.get('features')[0].get('msn'),
							              stroke: new ol.style.Stroke({
							                  color: '#333'
							              })
							          })
						  	        });
				    			}else{
				    				style = new ol.style.Style({
							  	          image: new ol.style.Icon({
							  	        	  src : ctx + '/public/images/gis/meter-gray.png'
							  	          }),
								          text: new ol.style.Text({
								              text: feature.get('features')[0].get('msn'),
								              stroke: new ol.style.Stroke({
								                  color: '#333'
								              })
								          })
							  	        });
				    			}
				    		}
				    	}
					    return style;
					  }
					});
					map.addLayer(meterLayer);
				}
			}
		}
	);
}

//获取当前显示地图的范围
function getMapExtent(){
    var extent = map.getView().calculateExtent(map.getSize());
    var bottomLeft = ol.extent.getBottomLeft(extent);
    var topRight = ol.extent.getTopRight(extent);
    var extent = {
    	minLat: bottomLeft[1],//最小纬度
    	minLon: bottomLeft[0],//最小经度
    	maxLat: topRight[1],//最大纬度
    	maxLon: topRight[0]//最大经度
    };
    return extent;
}

//定位到需要显示的表计图标
handlerType = 'query';
function checkNode(_node) {
	if(_node.attributes.ndType == 'bj'){
		dwrAction.doAjax("mapAction", "getMeter", {meterNo: _node.id},
		function(res) {
			if (res != null) {
				if (res.success) {
					//获取表计坐标
					var meter = res.dataObject;
					if(meter.lon != null && meter.lat != null){
						var coordinate = [parseFloat(meter.lon), parseFloat(meter.lat)];
						//弹出表计信息窗口
						document.getElementById('msn').innerHTML = nullReplaceStr(getValue(meter.msn));
						document.getElementById('mstsn').innerHTML = nullReplaceStr(getValue(meter.mstsn));
						document.getElementById('busstypen').innerHTML = nullReplaceStr(getValue(meter.busstypen));
				    	document.getElementById('pstsn').innerHTML = nullReplaceStr(getValue(meter.pstsn));
				    	document.getElementById('cno').innerHTML = nullReplaceStr(getValue(meter.cno));
				    	document.getElementById('cname').innerHTML = nullReplaceStr(getValue(meter.cname));
					    popup.setPosition(coordinate);
					    element.style.display = '';
					    //地图定位到需要显示的表计图标
						var pan = ol.animation.pan({
							duration: 2000,
							source: (map.getView().getCenter())
					    });
					    map.beforeRender(pan);
					    map.getView().setCenter(ol.proj.fromLonLat(coordinate, 'EPSG:4326'));
					}
				}
			}
		});
	}
	return true;
}

//空字符串替换字符
function nullReplaceStr(src){
	if(src == null || src == '')
		return '---';
	else
		return src;
}

/*var toggle2 = {
	trigger : jq('.filter-toggler'),
	li : jq('.filter-body li'),
	block : jq('.filter'),
	init : function(){
		toggle2.trigger.bind('click', function(){
			toggle2.block.toggle();
		});
		toggle2.block.delegate('li', 'click', function(){
			toggle2.li.removeClass('active');
			jq(this).addClass('active');
		});
	}
};
toggle2.init();*/