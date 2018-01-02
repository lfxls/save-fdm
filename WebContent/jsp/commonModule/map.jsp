<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript"
	src="${ctx}/public/js/openlayers/OpenLayers.js"></script>
<%-- <script type="text/javascript"
	src="${ctx}/js/modules/commonModule/jquery-2.1.1.min.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/jquery-1.11.2.js"></script>
<script type="text/javascript"
	src="${ctx}/js/locale/commonModule/map_${appLang}.js"></script>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/map.js"></script>
<script type="text/javascript"
	src="${ctx}/js/modules/commonModule/popups.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/map.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/public/css/popups.css">
<c:if test="${mapType==2}">
	<script
		src="http://maps.google.com/maps/api/js?v=3&sensor=false&language=en"></script>
</c:if>
<title>GIS</title>
</head>
<body style="padding: 0px;">
	<div id="map"></div>
	<div id="gprsMeterPopups" class="marker-layer" style="display: none;">
		<div class="tri"></div>
		<table class="pop-detail">
			<tr>
				<th><s:text name="gisModule.meter.customerNo" /></th>
				<td>#CustomerNo#</td>
				<th><s:text name="gisModule.meter.state" /></th>
				<td>#state#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.hh" /></th>
				<td>#HH#</td>
				<th><s:text name="gisModule.meter.hm" /></th>
				<td>#HM#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.voltage" /></th>
				<td>#V_AVG_A#</td>
				<td>#V_AVG_B#</td>
				<td>#V_AVG_C#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.current" /></th>
				<td>#A_AVG_A#</td>
				<td>#A_AVG_B#</td>
				<td>#A_AVG_C#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.factor" /></th>
				<td>#PF_AVG_A#</td>
				<td>#PF_AVG_B#</td>
				<td>#PF_AVG_C#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.totalFactor" /></th>
				<td>#PF_AVG_TOTAL#</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.zxygz" /></th>
				<td>#ZXYGZ#</td>
				<th><s:text name="gisModule.meter.fxygz" /></th>
				<td>#FXYGZ#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.zxwgz" /></th>
				<td>#ZXWGZ#</td>
				<th><s:text name="gisModule.meter.fxwgz" /></th>
				<td>#FXWGZ#</td>
			</tr>
			<tr>
				<td colspan="4"><a href="javascript:void(0);"
					onclick="toEvent();"><s:text name="gisModule.meter.event" /></a></td>
			</tr>
		</table>
	</div>
	<div id="meterPopups" class="marker-layer" style="display: none;">
		<div class="tri"></div>
		<table class="pop-detail">
			<tr>
				<th><s:text name="gisModule.meter.customerNo" /></th>
				<td>#CustomerNo#</td>
				<th><s:text name="gisModule.meter.state" /></th>
				<td>#state#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.hh" /></th>
				<td>#HH#</td>
				<th><s:text name="gisModule.meter.hm" /></th>
				<td>#HM#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.zxygz" /></th>
				<td>#ZXYGZ#</td>
				<th><s:text name="gisModule.meter.fxygz" /></th>
				<td>#FXYGZ#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.meter.zxwgz" /></th>
				<td>#ZXWGZ#</td>
				<th><s:text name="gisModule.meter.fxwgz" /></th>
				<td>#FXWGZ#</td>
			</tr>
			<tr>
				<td colspan="4"><a href="javascript:void(0);"
					onclick="toEvent();"><s:text name="gisModule.meter.event" /></a></td>
			</tr>
		</table>
	</div>
	<div id="concentPopups" class="marker-layer" style="display: none;">
		<table class="pop-detail">
			<tr>
				<th><s:text name="gisModule.concent.zdjh" /></th>
				<td>#CustomerNo#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.concent.zdlx" /></th>
				<td>#zdlx#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.concent.state" /></th>
				<td>#state#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.concent.bjsl" /></th>
				<td>#bjsl#</td>
			</tr>
		</table>
	</div>
	<div id="publicTransPopups" class="marker-layer" style="display: none;">
		<table class="pop-detail">
			<tr>
				<th><s:text name="gisModule.transformer.name" /></th>
				<td>#MC#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.transformer.rl" /></th>
				<td>#RL#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.transformer.meterNumber" /></th>
				<td><a href="javascript:void(0);" onclick="toBJ();">#BJSL#</a></td>
			</tr>
			<tr>
				<th><s:text name="gisModule.alarm.alarmType" /></th>
				<td>#ALARM#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.alarm.alarmTime" /></th>
				<td>#ALARM_TIME#</td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:void(0);"
					onclick="toEvent();"><s:text name="gisModule.meter.event" /></a></td>
			</tr>
		</table>
	</div>
	<div id="specialTransPopups" class="marker-layer"
		style="display: none;">
		<table class="pop-detail">
			<tr>
				<th><s:text name="gisModule.transformer.name" /></th>
				<td>#MC#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.transformer.rl" /></th>
				<td>#RL#</td>
			</tr>
			<tr>
				<th><s:text name="gisModule.transformer.customerNumber" /></th>
				<td><a href="javascript:void(0);" onclick="toYH();">#YHSL#</a></td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:void(0);"
					onclick="toEvent();"><s:text name="gisModule.meter.event" /></a></td>
			</tr>
		</table>
	</div>
	<div class="filter-toggler">
		<i class="button"> <i></i>
		</i>
	</div>
	<div class="filter" style="z-index: 1001;">
		<div class="filter-header">
			<s:text name="gisModule.filter.title" />
			<span id="selectNodeText"></span> <i class="button"> <i></i>
			</i>
		</div>
		<div class="filter-body">
			<form id="exportForm"
				action="${ctx}/amigis/zy/gis!excelAlarmMeters.do" method="post">
				<input type="hidden" id="filter" name="filter" /> <input
					type="hidden" id="excelTextList" name="excelTextList" /> <input
					type="hidden" id="nodeId" name="nodeId" /> <input type="hidden"
					id="nodeType" name="nodeType" />
			</form>
			<ul>
				<li style="border-top: none" class="active"
					onclick="showMeterByType(0);"><i></i> <s:text
						name="gisModule.filter.all" /></li>
				<li onclick="showMeterByType(100);"><i></i> <s:text
						name="gisModule.filter.allAlarms" />
					<button class="btn-label" onclick="exportAlarmMeters(100);">Export</button>
				</li>
				<c:forEach var="item" items="${alarmTypes }">
					<li onclick="showMeterByType(${item.BM});"><i></i>  <%-- <c:choose> --%>
							<%-- <c:when test="${item.BM eq '1' }">
								<s:text name="gisModule.filter.phaseLoss" />
							</c:when> --%>
							${item.MC}
							<%-- <c:when test="${item eq 'threephaseUnbalance' }">
								<s:text name="gisModule.filter.threephaseUnbalance" />
							</c:when>
							<c:when test="${item eq 'underVoltage' }">
								<s:text name="gisModule.filter.underVoltage" />
							</c:when>
							<c:when test="${item eq 'overVoltage' }">
								<s:text name="gisModule.filter.overVoltage" />
							</c:when>
							<c:when test="${item eq 'overLoad' }">
								<s:text name="gisModule.filter.overLoad" />
							</c:when>
							<c:when test="${item eq 'tamper' }">
								<s:text name="gisModule.filter.tamper" />
							</c:when>
							<c:when test="${item eq 'tempetrature' }">
								<s:text name="gisModule.filter.tempetrature" />
							</c:when>
							<c:when test="${item eq 'gass' }">
								<s:text name="gisModule.filter.gass" />
							</c:when>
							<c:when test="${item eq 'oil' }">
								<s:text name="gisModule.filter.oil" />
							</c:when>
							<c:when test="${item eq 'relay' }">
								<s:text name="gisModule.filter.relay" />
							</c:when>
							<c:when test="${item eq 'power' }">
								<s:text name="gisModule.filter.power" />
							</c:when> --%>
						<%-- </c:choose>  --%>
						<button class="btn-label"
							onclick="exportAlarmMeters(${item.BM});">Export</button></li>
				</c:forEach>
				<li onclick="showMeterByType(101);"><i></i> <s:text
						name="gisModule.filter.offline" /></li>
			</ul>
		</div>
	</div>
	<div class="alarm" style="z-index: 1001;">
		<div class="alarm-header">
			<s:text name="gisModule.alarm.title" />
			<i class="toggle-dot" id="">${fn:length(meterAlarms)}</i>
		</div>
		<div class="alarm-body">
			<table class="ui-table ui-table-inbox" id="alarmTable">
				<thead>
					<tr>
						<th><s:text name="gisModule.alarm.customerNo" /></th>
						<th><s:text name="gisModule.alarm.transformer" /></th>
						<th><s:text name="gisModule.alarm.alarmType" /></th>
						<th><s:text name="gisModule.alarm.alarmTime" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${meterAlarms }">
						<c:choose>
							<c:when test="${item.BJLX eq '05'}">
								<tr onclick="selectAlarmPoint(${item.BYQJD},${item.BYQWD });"
									style="cursor: pointer;">
									<td>${item.CLDJH }</td>
									<td>${item.MC }</td>
									<td class="data">${item.GJMC }</td>
									<td><fmt:formatDate value="${item.YCFSSJ}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr
									onclick="selectAlarmPoint('${item.CLDJH}',${item.JD},${item.WD });"
									style="cursor: pointer;">
									<td>${item.CLDJH }</td>
									<td>${item.MC }</td>
									<td class="data">${item.GJMC }</td>
									<td><fmt:formatDate value="${item.YCFSSJ}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<script>
	var mapType = '${mapType}';
    var offlineMap_url="${offlineMapUrl}";
    var offlineMap_layerName="${offlineMapLayerName}";
    $(document).ready(function() {
        $("#map").height($(document).height());//地图高度
    });
    var toggle = {
    		trigger : $('.alarm-header'),
    		block : $('.alarm'),
    		moveHeight : $('.alarm-body').height(),
    		setHeight : function(){
    			toggle.block.css('bottom', - toggle.moveHeight - 2 +'px');
    		},
    		slide : function(){
    			if(toggle.block.css('bottom') !== '-2px'){
    				toggle.block.animate({
    					bottom : '-2px'
    				})
    			}else{
    				toggle.block.animate({
    					bottom : - toggle.moveHeight - 2 +'px'
    				})
    			}
    		},
    		init : function(){
    			toggle.setHeight();
    			toggle.trigger.bind('click', toggle.slide);
    		}
    	}
    	toggle.init();

    	var toggle2 = {
    		trigger : $('.filter-toggler'),
    		li : $('.filter-body li'),
    		block : $('.filter'),
    		init : function(){
    			toggle2.trigger.bind('click', function(){
    				toggle2.block.toggle();
    			});
    			toggle2.block.delegate('li', 'click', function(){
    				toggle2.li.removeClass('active');
    				$(this).addClass('active');
    			});
    		}
    	}
    	toggle2.init();
</script>
</body>
</html>