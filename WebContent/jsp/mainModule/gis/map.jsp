<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Map</title>
		<%@ include file="/jsp/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/public/js/openlayers/css/ol3gm.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/public/js/openlayers/css/ol.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/public/js/openlayers/css/map.css" type="text/css" />
	</head>
	<body>
		<div id="map" class="map"></div>

		<div id="popup" class="ol-popup" style="display: none;">
	      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
	      <div id="popup-content" style="width:532px">
	      	<table class="pop-detail" style="table-layout:fixed">
				<tr>
					<th style="border-left:none"><s:text name="common.kw.customer.CSN"></s:text></th>
					<td><label id="cno"></label></td>
					<th><s:text name="common.kw.customer.CName"></s:text></th>
					<td><label id="cname"></label></td>
				</tr>
				<tr>
					<th style="border-left:none"><s:text name="common.kw.meter.MSN"></s:text></th>
					<td><label id="msn"></label></td>
					<th><s:text name="mainModule.gis.map.mstatus"></s:text></th>
					<td><label id="mstsn"></label></td>
				</tr>
				<tr>
					<th style="border-left:none"><s:text name="mainModule.gis.map.ptype"></s:text></th>
					<td><label id="busstypen"></label></td>
					<th><s:text name="mainModule.gis.map.pstatus"></s:text></th>
					<td><label id="pstsn"></label></td>
				</tr>
			</table>
	      </div>
	    </div>
		<script src="${ctx}/public/js/openlayers/ol.js"></script>
		<script src="${ctx}/public/js/openlayers/ol3gm.js"></script>
		<!-- <script type="text/javascript" src="https://ditu.google.cn/maps/api/js?v=3.7&sensor=false&language=en-US"></script> -->
		<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script> -->
		<script type="text/javascript">
			//地图初始化显示的中心点经纬度
			var centerLon = ${centerLon};
			var centerLat = ${centerLat};
		</script>
		<script src="${ctx}/js/modules/mainModule/gis/map.js"></script>
	</body>
</html>
