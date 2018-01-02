<%@page import="java.util.Map"%>
<%@page import="cn.hexing.ami.util.Constants"%>
<%@page import="cn.hexing.ami.web.listener.AppEnv"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
	Map<String,String> sysPara = (Map<String,String>)AppEnv.getObject(Constants.SYSPARA);
	pageContext.setAttribute("sysMap",sysMap);
	pageContext.setAttribute("sysPara",sysPara);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<link rel="shortcut icon" href="${ctx}/public/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/reset.css" />
<!-- font icon -->
<link rel="stylesheet" type="text/css" href="${ctx}/public/font-awesome/css/font-awesome.min.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/resources/css/ext-all-notheme.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/hexing.css">
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/ext-theme.css">
<link rel="stylesheet" type="text/css" href="${ctx}/public/css/other.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/ExtPagingTree.css" />

<script type="text/javascript" src="${ctx}/public/extjs/ext-base.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ext-all.js"> </script>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = '${ctx}/public/extjs/resources/images/default/s.gif';
	//当前tab的ID值
	var curTabId = '${curTabId}';
	var ctx='${ctx}';
	var cu = '${CU}';
	var gridExportDoc = '${gridExportDoc}';
	var sysMode = '${sysMode}';
	var ctxLang = '${appLang}';
	var unitCode='${UNIT_CODE}';
	var unitName='${UNIT_NAME}';
	var unitLevel='${UNIT_LEVEL}';
	var onlineMode = '${sysMap.onlineMode==null ? "0" : sysMap.onlineMode}'; //默认为0-一直在线 
	var waitWakeUpSmsResult = ${sysMap.waitWakeUpSmsResult==null ? 50 : sysMap.waitWakeUpSmsResult};
	var onlineTimeRange = '${sysMap.onlineTimeRange==null? "00:00:00-23:59:59" : sysMap.onlineTimeRange}'; //默认在线时段为00:00-24:00
	var onlineTimeRangeArray = onlineTimeRange.split(",");
	try{
		//将在线时段转换为object模式
		for(var i=0;i<onlineTimeRangeArray.length;i++){
			var times = onlineTimeRangeArray[i].split("-");
			var temp = {startTime:times[0],endTime:times[1]};
			onlineTimeRangeArray[i] = temp;
		}
	}catch(e){}
	//判断系统时间是否在在线时段内
	function isInOnlineTimeRange(time){
		var strTime = P$dp.utils.formatDate(time , "HH:mm:ss");
		for(var i=0;i<onlineTimeRangeArray.length;i++){
			if(strTime>onlineTimeRangeArray[i].startTime && strTime<onlineTimeRangeArray[i].endTime){
				return true;
			}
		}
		return false;
	}
	//操作类别
	var addOpt='01';
	var updOpt='02';
	var delOpt='03';
	
	//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外   
	function banBackSpace(e){     
	    var ev = e || window.event;//获取event对象      
	    var obj = ev.target || ev.srcElement;//获取事件源      
	      
	    var t = obj.type || obj.getAttribute('type');//获取事件源类型     
	      
	    //获取作为判断条件的事件类型   
	    var vReadOnly = obj.getAttribute('readonly');  
	    var vEnabled = obj.getAttribute('enabled');  
	    //处理null值情况   
	    vReadOnly = (vReadOnly == null) ? false : vReadOnly;  
	    vEnabled = (vEnabled == null) ? true : vEnabled;  
	      
	    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，   
	    //并且readonly属性为true或enabled属性为false的，则退格键失效   
	    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")   
	                && (vReadOnly==true || vEnabled!=true))?true:false;  
	     
	    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效   
	    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")  
	                ?true:false;          
	      
	    //判断   
	    if(flag2){  
	        return false;  
	    }  
	    if(flag1){     
	        return false;     
	    }     
	}  
	  
	//禁止后退键 作用于Firefox、Opera   
	document.onkeypress=banBackSpace;  
	//禁止后退键  作用于IE、Chrome   
	document.onkeydown=banBackSpace; 
	//系统是否是伊朗日历类型
	var isPersianCalender = "";
	<%if(Constants.CALENDAR_TYPE.equals(sysMap.get("calendarType"))){ //是否使用伊朗历  %>
		isPersianCalender = "1";//日历控件使用伊朗历
	<%}%>
	
	Ext.onReady(function() {
		initTimeInputElForPage();//初始化处理页面上所有的日历输入框
	});
	
	function initTimeInputElForPage(){
		if(Ext.query("*[class=Wdate]").length>0){
			Ext.each(Ext.query("*[class=Wdate]"),function(item){
				initTimeInputEl(item);
			});
		}
	}
	function initTimeInputEl(item){
		var sd_separator = "-";
		var gregorianEl = item;
		/************************************
		*	日历输入框特殊处理
		*	为每个输入框增加Start、End隐藏域
		*************************************/
		var monthStartDayEl = document.getElementById(gregorianEl.id+"Start");
		var monthEndDayEl = document.getElementById(gregorianEl.id+"End");
		if((!monthStartDayEl || !monthEndDayEl)){ //如果是月份选择，则生成月份起始日期值（公历）的隐藏输入框
			if(!monthStartDayEl){
				monthStartDayEl = document.createElement('input');
				if(gregorianEl.name)monthStartDayEl.name = gregorianEl.name+"Start";
				monthStartDayEl.id = gregorianEl.id+"Start";
				monthStartDayEl.style.display = "none";
				gregorianEl.parentNode.insertBefore(monthStartDayEl , gregorianEl );
			}
			if(!monthEndDayEl){
				monthEndDayEl = document.createElement('input');
				if(gregorianEl.name)monthEndDayEl.name = gregorianEl.name+"End";
				monthEndDayEl.id = gregorianEl.id+"End";
				monthEndDayEl.style.display = "none";
				gregorianEl.parentNode.insertBefore(monthEndDayEl , gregorianEl );
			}
		}
		/***日历输入框特殊处理end**/
		<%if(!Constants.CALENDAR_TYPE.equals(sysMap.get("calendarType"))){ //使用公历   %>
			/************************
			 *  月份特殊处理
			 ***********************/
			try{
				if(gregorianEl.value && gregorianEl.value.length==7){ //对月份输入框已有的值进行处理
					var startDay = gregorianEl.value+sd_separator + "01";
					var startDate = P$dp.utils.parseDate(startDay , "yyyy-MM-dd");
					startDate.setMonth(startDate.getMonth()+1);
					var endDay = P$dp.utils.formatDate(startDate , "yyyy-MM-dd");
					monthStartDayEl.value = startDay;
					monthEndDayEl.value = endDay;
				}else if(gregorianEl.value && gregorianEl.value.length==4){ //对年份输入框已有的值进行处理
					var startDay = gregorianEl.value+sd_separator + "01"+sd_separator + "01";
					var endDay = (parseInt(gregorianEl.value,10)+1)+sd_separator + "01"+sd_separator + "01";
					monthStartDayEl.value = startDay;
					monthEndDayEl.value = endDay;
				}
			}catch (e) {}
			/************************
			 *  月份 end
			 ***********************/
		<%}%>
		<%if(Constants.CALENDAR_TYPE.equals(sysMap.get("calendarType"))){ //使用伊朗历   %>
			/************************
			 *  伊朗历特殊处理
			 *	生成一个伊朗历输入框、隐藏原公历输入框
			 ***********************/
			var persianEl = document.getElementById(gregorianEl.id+"Persian");
			if(!persianEl){
				persianEl = document.createElement('input');
				persianEl.name = gregorianEl.name+"Persian";
				persianEl.id = gregorianEl.id+"Persian";
				persianEl.isPersian = "1";
				persianEl.srcEl = gregorianEl;
				for(var key in gregorianEl){ //复制原input的其他属性
					try{
						if(key.toLowerCase() == "name" || key.toLowerCase() == "id"
							|| key.toLowerCase() == "height" || key.toLowerCase() == "width") {
							continue;
						}
						persianEl[key] = gregorianEl[key];
					}catch(e){}
				}
				persianEl.style.width = gregorianEl.style.width;
				if(parseInt(gregorianEl.value.substring(0,4),10)>2010){ //初始时间是公历时间 
				 	persianEl.value = P$dp.utils.calendarToPersianForString(gregorianEl.value);
				}else{//初始时间是伊朗时间 
					gregorianEl.value = P$dp.utils.persianToGregorian(gregorianEl.value);
				}
				gregorianEl.style.display = "none";
				gregorianEl.parentNode.insertBefore(persianEl , gregorianEl );
				
				//关联srcEl的值变化实时反映到persianEl
				if("\v"=="v") {  //ie
					gregorianEl.onpropertychange = gregorianElChange; 
				}else{
					gregorianEl.addEventListener("input",gregorianElChange,false); 
				}
			}
			function gregorianElChange(){ //公历隐藏域值变化时，将对应的伊朗历值赋值到伊朗历输入框
				if(gregorianEl.value.length>4){
					try{
						var year = parseInt(gregorianEl.value.substring(0,4));
						if(year>1000){//判断值是否合法
							if(year>2010){  //值为公历值
								if(gregorianEl.value.length==4 && persianEl.value!=""){
									//年份，用公历年的1月1号来转换为伊朗年，获取不准确的值
									persianEl.value = P$dp.utils.calendarToPersianForString(gregorianEl.value);
								}else if(gregorianEl.value.length==7 && persianEl.value!=""){
									//月份，用公历月的1号来转换为伊朗月，获取不准确的值
									persianEl.value = P$dp.utils.calendarToPersianForString(gregorianEl.value);
								}else{
									persianEl.value = P$dp.utils.calendarToPersianForString(gregorianEl.value);
								}
							}else{ //值为伊朗历值
								 persianEl.value = gregorianEl.value;
							}
						}
					}catch(e){}
				}else{ //年
					persianEl.value = gregorianEl.value;
				}
			}
			/************************
			 *  伊朗历特殊处理 end
			 ***********************/
			/************************
			 *  月份特殊处理
			 ***********************/
			try{
				if(persianEl.value && persianEl.value.length==7){ //对月份输入框已有的值进行处理
					var startPersianDay = persianEl.value+sd_separator + "01";
					var pDate = P$dp.utils.parsePersianDate(persianEl.value.substring(0,7),"yyyy-MM");
					var month = pDate.month;
					var year = pDate.year;
					var nextYear = (month+1>12)?(year+1):year;
					var nextMonth = (month+1>12)?1:(month+1);
					if (nextMonth<10)nextMonth = "0" + nextMonth;
					var endPersianDay = nextYear +sd_separator+nextMonth+sd_separator +"01";
					monthStartDayEl.value = P$dp.utils.persianToGregorian(startPersianDay);
					monthEndDayEl.value = P$dp.utils.persianToGregorian(endPersianDay);
				}else if(persianEl.value && persianEl.value.length==4){ //对年份输入框已有的值进行处理
					var startPersianDay = persianEl.value+sd_separator + "01"+sd_separator + "01";
					var endPersianDay = (parseInt(persianEl.value,10)+1)+sd_separator + "01"+sd_separator + "01";
					monthStartDayEl.value = P$dp.utils.persianToGregorian(startPersianDay);
					monthEndDayEl.value = P$dp.utils.persianToGregorian(endPersianDay);
				}
			}catch (e) { }
			/************************
			 *  月份 end
			 ***********************/
		<%}%>
	}

	//导出图表到Excel公用处理方式
	var exportCharts = {};//格式：{chartKey1:chartObject1 , chartKey2:chartObject2}
	var exportFd, exportFormName , exportUrl , exportHiddenParams ;
	var exportedSize = 0 ;
	var exportingCharts = [] , exportedFileNames = [];
	function exportExcelAndImage(charts , formName , url , hiddenParams , fileFormat){ //初始化导出，判断页面上可以导出的图表都上传到服务器端生成图片文件或者PDF文件
		exportingSize=0; exportedSize = 0; exportingCharts = [] ; exportedFileNames = [];
		exportFd = null;exportFormName = formName; exportUrl = url ; exportHiddenParams = hiddenParams;
		if(charts.length>0){
			for(var i=0; i<charts.length; i++){
				try{
					charts[i].exportChart({
						exportAction : "Save"
						, exportFormat : fileFormat
						,exportHandler:ctx + "/FCExporter"
					});
					exportingCharts.push(charts[i]);
				}catch(e){}
			}
			if(exportingCharts.length==0) doExport();
		}else{
			doExport();
		}
	};
	function FC_Exported(objRtn){ //服务器端文件生成回调函数，每生成成功一个文件回调一次该函数
		exportedSize ++;
        if (objRtn.statusCode=="1"){
            var fileUrl = objRtn.fileName;
        	var tmp=fileUrl.split("/");//按照"/"分割 
        	exportedFileNames.push(tmp[tmp.length-1]);
        }
        if(exportedSize>=exportingCharts.length){
        	exportHiddenParams.push({tag:'input',name:"exportedChartFileNames",id:"exportedChartFileNames",value:exportedFileNames.join(","),type:'hidden'});
        	doExport();
        }
    }
	function doExport(){ //图片生成成功之后执行原有的导出Excel、PDF函数 （上传的参数增加了exportedChartFileNames） 
		exportFd=Ext.DomHelper.append(Ext.getBody(),
   				{tag:'form',method:'post',id:exportFormName,name:exportFormName,action:exportUrl, target:'_blank',cls:'x-hidden',cn:exportHiddenParams}
   		,true);
       	exportFd.dom.submit();
	}
</script>
<script type="text/javascript" src="${ctx}/public/extjs/locale/ext-lang-${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/js/locale/commonModule/commonRes_${appLang}.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtTools.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/ExtWindow.js"> </script>
<script type="text/javascript" src="${ctx}/public/charts/FusionCharts.js"> </script>
<!-- 屏蔽FusionChartsExportComponent.js，解决页面访问时提示错误信息：FusionChartsExport:object::FusionCharts was not found.Verify script inclusions. -->
<%-- <script type="text/javascript" src="${ctx}/public/charts/FusionChartsExportComponent.js"> </script>--%>
<script type="text/javascript" src="${ctx}/public/js/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/js/DatePicker/plugin/skin/gray/PersianWdatePicker.css" />
<script type="text/javascript" src="${ctx}/public/js/DatePicker/plugin/PersianWdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/public/js/checkfunction.js"></script>
<script type='text/javascript' src="${ctx}/dwr/engine.js"> </script>
<script type='text/javascript' src="${ctx}/dwr/util.js"> </script>
<script type='text/javascript' src="${ctx}/dwr/interface/dwrAction.js"> </script>
<script type="text/javascript" src="${ctx}/public/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/public/js/jquery.i18n.properties-min-1.0.9.js"></script>
<script type="text/javascript" src="${ctx}/public/js/comm.js"> </script>
<script type="text/javascript" src="${ctx}/public/extjs/LockingGridView.js"> </script>
<link rel="stylesheet" type="text/css" href="${ctx}/public/extjs/LockingGridView.css" />
<style type="text/css">
     .x-grid3-row{
        height:25px;
    }
    /* style rows on mouseover */
    .x-grid3-row-over .x-grid3-cell-inner {
        font-weight: bold;
    }
    .x-grid3-row-selected{
		background:#DFE8F6!important;border:1px dotted #a3bae9;
	} /*鼠标移动上面改变背景色*/  
 	.x-grid3-row-over { 
		background-color:#EFEFEF;/*鼠标移上去改变行的底色 #C9D8FC*/ 
	} 
	.x-selectable, .x-selectable * {      
        -moz-user-select: text! important ;      
        -khtml-user-select: text! important ;      
    } 
</style>
<s:hidden name="curTabId" id="curTabId"></s:hidden>
<script>
//解决extgrid内容无法选择导致无法复制，chrome暂时无法兼容 edit by jun 20140930
if  (!Ext.grid.GridView.prototype.templates) {    
    Ext.grid.GridView.prototype.templates = {};      
}      
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(      
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,      
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,      
     '</td>'      
);
</script>
<script type="text/javascript">
	//加载公共国际化资源文件
	loadProperties('commonModule', 'common');
</script>
