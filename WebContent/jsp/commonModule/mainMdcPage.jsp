<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/jsp/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/modules/commonModule/mainMdcPage.js"></script>
</head>
<body>
	<div class="main-page-container">
		<table width="100%" align="center">
			<tr height="50%">
				<td class="main-page-cell">
					<div class="ui-panel" style="border-bottom:0">
						<div class="ui-panel-header"><s:text name="common.zysz.sbsl"/></div>
					
						<div class="ui-panel-body">
							<table class="ui-table ui-table-inbox" class="home_table">
								<thead>
									<tr class="home_title">
										<th><s:text name="common.zysz.sblx"/></th>
										<th><s:text name="common.zysz.sbsl"/></th>
									</tr>
								</thead>
								<tbody>
									<tr height="58px">
										<td class="home_td">&nbsp;<s:text name="mainPage.gprsb"/></td>
										<td id="sb_gprsb" class="home_td">&nbsp;</td>
									</tr>
									<tr height="58px">
										<td class="home_td">&nbsp;<s:text name="mainPage.jzq"/></td>
										<td id="sb_jzq" class="home_td">&nbsp;</td>
									</tr>
									<tr height="58px">
										<td class="home_td">&nbsp;<s:text name="mainPage.plcb"/></td>
										<td id="sb_plcb" class="home_td">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</td>
				<td class="main-page-cell">
					<div class="ui-panel online-rate-chart">
						<div class="ui-panel-header"><s:text name="common.zysz.txzxl"/></div>
						<div class="ui-panel-body" style="height:202px">
							<table width="100%" height="100%">
								<tr>
									<td width="48%" align="center" style="border-right:1px dashed #cecece">
										<div id="ac1">FusionCharts.</div>
										<div class="chart-explain"><s:text name="mainPage.gprsb"/>:<span id="zbzx"></span></div>
									</td>
									<td width="48%" align="center" style="border-right:1px dashed #cecece">
										<div id="ac2">FusionCharts.</div>
										<div class="chart-explain"><s:text name="mainPage.jzq"/>:<span id="gbzx"></span></div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="main-page-cell">
					<div class="ui-panel">
						<div class="ui-panel-header"><s:text name="common.zysz.cbcgl"/></div>
						<div class="ui-panel-body">
							<table width="100%" height="200px" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td width="33%" align="center"><div id="cb1">FusionCharts.</div></td>
									<td width="33%" align="center"><div id="cb2">FusionCharts.</div></td>
									<td width="33%" align="center"><div id="cb3">FusionCharts.</div></td>
								</tr>
								<tr>
									<td align="center"><li><s:text name="mainPage.cgl.jt"/>:<span id="cbjt"></span></li></td>
									<td align="center"><li><s:text name="mainPage.cgl.zt"/>:<span id="cbzt"></span></li></td>
									<td align="center"><li><s:text name="mainPage.cgl.qt"/>:<span id="cbqt"></span></li></td>
								</tr>							
							</table>
						</div>
					</div>
				</td>
				<td  class="main-page-cell">
					<div class="ui-panel">
						<div class="ui-panel-header"><s:text name="common.zysz.gjtj"/></div>
						<div class="ui-panel-body">
							<table width="100%" height="200px" align="center" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<div id="gjtj">FusionCharts.</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div id="id_obj" style="display: none">
	<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="100%" height="400" id="Untitled-1" align="middle">
	<param name="allowScriptAccess" value="sameDomain" />
	<param name="movie" value="focus.swf" /> 
	<param name="quality" value="high" /> 
	<param name="bgcolor" value="#ffffff" />
	<embed src="mymovie.swf" quality="high" bgcolor="#ffffff" width="100%" height="400" name="mymovie" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
	</object>

	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"  type="application/x-shockwave-flash" data="c.swf?path=movie.swf" width="100%" height="300">
	<param name="movie" value="focus.swf" />
	</object>
	</div>
</body>
</html>