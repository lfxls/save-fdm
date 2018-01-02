/**
	@ auther adan
	@ date 2006-02-10
	@ 常用JS函数
	@ 所有权：
*/
/*
	判断是否数值类型
*/
function isNumber(String){  
	if($E(String)) return false;
	var Letters = "1234567890";//可以自己增加可输入值
	var i;
	var c;
	for(i = 0;i < String.length; i++){  
		c = String.charAt(i);
		if(Letters.indexOf(c) < 0)
		return false;
	}
	return true;
}

// 检查是包含中文
function isChn(str){
	var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
	if(!patrn.exec(str)){ 
		return false; 
	}else{ 
		return true; 
	} 
}


//	去处前后的空格

function trim(str){
	var type = typeof(str);
	if (type!= "string") {
		return str;
	}
	if($E(str)) return "";
	var i=0,j=str.length-1,c;
	for(;i<str.length;i++){
		c=str.charAt(i);
		if(c!=' ') break;
	}
	for(;j>-1;j--){
		c=str.charAt(j);
		if(c!=' ') break;
	}
	if(i>j) return "";
	return str.substring(i,j+1);
}

//	检验是否是空字符串 空的话就返回false

function isNull(str) {
	var str1 = trim(str);
	return $E(str1);
}


//	检验字符串是否超出长度 超出返回false

function isMore(str,num)
{
	var str1 = trim(str);
	if(str1.length>num)
		return true;
	return false;
}


//	检验字符串是否满足长度

function isLess(str,num)
{
	var str1 = trim(str);
	if(str1.length<num)
		return true;
	return false;
}


//	检验是否是email地址

function isEmail (str) {
	
	var validate=/^[_a-zA-Z0-9]+@([_a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,3}$/;
	return validate.test(str);
}


//	检验是否是身份证

function isIDCard(str){
	var num = trim(str);
	var len = num.length;
	var re = null;
	if (len == 15)	
		re = new RegExp(/^\d{6}\d{2}(1[012]|0\d)([012]\d|3[01])\d{3}$/);
	else if (len == 18)
		re = new RegExp(/^\d{6}(19|2\d)\d{2}(1[012]|0\d)([012]\d|3[01])\d{3}[\dXx]$/);
	else {
		return false;
	}
	return re.test(num);
}


//	检验是否是double类型

function isDouble(str){
	if(str=="0") return true;
	var validate = /^[0-9]+(\.+[0-9]+)?$/;
	return validate.test(str);
}

//	检验是否是整数类型

function isInt(str){
	/***
		整数								/^(-|\+)?\d+$/
		大于0的整数 （用于传来的ID的验证)   /^\d+$/
		负整数的验证						/^-\d+$/
	*/
	var validate = /^\d+$/;
	return validate.test(str);
}

/**
	检验数值是否小于某个值
*/
function isSmaller(str,num)
{
	var str1 = trim(str);
	if(str1<num)
		return true;
	return false;
}
/**
   根据HTML对象检测object.value的值是否小于某个值
   @param object HTML对象
   @param num    比较的数值
   @param str    提示信息
*/
function isObjectSmaller(object,num,str)
{
	 if(object.value<num){
	   Wg.alert(ExtTools_warn_title,str+checkfunction_isObjectSmaller_alert+num);
	   object.select();
	   return false;
	 }
	 return true;
}
/**
	 检验数值是否大于某个值
*/
function isBigger(str,num)
{
	var str1 = trim(str);
	if(str1>num)
		return true;
	return false;
}
/*
   根据HTML对象检测object.value的值是否大于某个值
   @param object HTML对象
   @param num    比较的数值
   @param str    提示信息
*/
function isObjectBigger(object,num,str)
{
	 if(object.value>num){
	   Wg.alert(ExtTools_warn_title,str+"的值不能大于"+num);
	   object.select();
	   return false;
	 }
	 return true;
}

/**
 * 新增验证手机号码方法， 11 位数字 并且以 13 15 18开头
 */
function isPhone(str)
{
 var reg0=/^13\d{9}$/;   
 var reg1=/^15\d{9}$/;  
 var reg2=/^18\d{9}$/; 
 var my=false;
 if (reg0.test(str))my=true;
 if (reg1.test(str))my=true;
 if (reg2.test(str))my=true;
 return my;
}
/**
	判断客户端使用分辨率是否是1024*768
*/
function checkFenBianLv()
{
	var aa = screen.width;
	var bb = screen.height;	
	if(aa!=1024||bb!=768)
		return true;
	else
		return false;
}

/**
	判断客户端是否使用IE6.0
*/
function checkIE6()
{
	var cc = navigator.appName;
	if(cc!="Microsoft Internet Explorer")
	{
		return false;
	}
	if(cc=="Microsoft Internet Explorer")
	{
		var versionFull=navigator.appVersion;
		
		//alert(versionFull);
		
		var version=versionFull.substring(versionFull.indexOf(';')+7,versionFull.lastIndexOf(';'));
		
		if(parseInt(version)<6)
		{
			return false;
		}else{
				return true;
			}
    }
}

/**
	返回客户端使用的操作系统名称(Windows)
*/
function checkWindows()
{
	var versionFull=navigator.appVersion;
	// Windows NT
	var windowsVersion =  versionFull.substring(versionFull.indexOf(';')+12,versionFull.indexOf(';')+22);
	return windowVersion;
}
/**
 判断是否闰平年	
*/
function IsPinYear(year)           
  {
    if (0==year%4&&((year%100!=0)||(year%400==0)))
		return true;else return false;
  }
/**
	求某天的星期几
*/
function GetDOW(day,month,year)
  {
    var dt=new Date(year,month-1,day).getDay()/7; return dt;
  }
  /**
   * 判断字符串是否为年份
   * 年份范围为: 1900~2099
   **/
   function isYear(str){
	   if(str<="2099"&&str>="1900")return true;
	   return false;
   }
   /**
    *判断字符是否是月份1~12
    **/
   function isMonth(str){
     if(str.length == 1)str = "0"+str;
     if(str<="12"&&str>="01")return true;
     return false;
   }
   /**
    *判断字符是否是天数1~31
    **/
   function isDay(str){
    if(str.length == 1)str = "0"+str;
    if(str<="31"&&str>="01")return true;
    return false;
  }
  /**
   *判断是否为六位0~9的数字
   **/
   function isD6(str){
      var reg =/^\d{6}$/;
	  return reg.test(str);
   }
   /**
   *判断是否为0~9的数字
   **/
   function isDigits(str){
      var reg = /^\d+$/;
      return reg.test(str);
  }
 
/**
   *判断是否为八位0~9的数字
   **/
   function isD8(str){
      var reg =/^\d{8}$/;
	  return reg.test(str);
   }
/**
  返回请求的路径
*/ 
function getContextPath(){
	var docUrl = document.URL;
	var index0 = docUrl.indexOf('//');
	var index1 = docUrl.indexOf('/', index0+2);
	var index2 = docUrl.indexOf('/', index1+1);
	
	return docUrl.substring(index1, index2);
}

/**
	显示控件
*/
function showElement()
{
	this.style.display = "block";
}
/**
	隐藏控件
*/
function hideElement()
{
	this.style.display = "none";
}
/**
	检测小时的值
*/
function CheckHour(value){
  var tmp=true;
  var hour=parseInt(value);
  if(isNaN(hour)){
    return false;
  }
  if(hour>=24){
    tmp=false;
  }else if(hour<=-1){
    tmp=false;
  }
  
  return tmp;
}
/**
	检测分钟的值
*/
function CheckMinute(value){
  var tmp=true;
  var minute=parseInt(value);
  if(isNaN(minute)){
    return false;
  }
 
  if(minute>59){
    tmp=false;
  }else if(minute<0){
    tmp=false;
  }
  return tmp;
}

/**
	是否是时间（形如：13：06：12）
*/
function isYearMonthDay(str){
     var reg = /^[1-2][0-9]{3}-[0-9]{2}-[0-9]{2}$/;
     return reg.test(str);
 }

/**
	判断输入框内容长度不能超过某值length
*/
function checkMaxLength(obj,length)
	{
		var aaa=obj.value;
		if(aaa.length>length)
		{
			alert(checkfunction_checkMaxLength_alert1+aaa+checkfunction_checkMaxLength_alert2);
			obj.focus();
			obj.select()
			return false;
		}
    }
/**
	判断输入框内容长度小于某值length
*/
function checkMixLength(obj,length)
	{
		var aaa=obj.value;
		if(aaa.length<length)
		{
			alert(checkfunction_checkMaxLength_alert1+aaa+checkfunction_checkMaxLength_alert3);
			obj.focus();
			obj.select()
			return false;
		}
    }

/**
	校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
*/
function isTel(str)
{
	var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if (!patrn.exec(str)) 
		return false;
	return true;
}
/**
	校验邮政编码
*/
function isPostalCode(str)
{
	var patrn=/^[1-9]{1}(\d){5}$/;
	if (!patrn.exec(str)) 
		return false;
	return true;
}

/**
	判断输入框中输入的是否是 IP 地址
	@ 字符串"inputId" 为所判断的输入框的Id值
*/
function isIP(inputId)
{
	var ip = document.getElementById(inputId).value;
	re=/(\d+)\.(\d+)\.(\d+)\.(\d+)/g;    //匹配IP地址的正则表达式
	if(re.test(ip))
		return true;
	else
		return false;
}



//将传入的日期格式化为yyyy-mm-dd的字符串
function formatDate(date) {
	var y = date.getYear();
	var m = date.getMonth() + 1;
	if(m<10)
		m = "0"+m;
	else
		m = m;
	var d = date.getDate();
	if(d<10)
		d = "0"+d;
	else
		d = d;


	var r = y + "-" + m + "-" + d;		
	return r;
}

// 验证手机号码是否是11位
function checkMobilNumber(number){
  if (trim(number.value).length != 11 || trim(number.value).substring(0,1)!="1")
    {
        //alert("您的SIM卡号输入错误，长度必须为11位且必须以\"1\"开头。");
        number.select();
        return false;
    }
   return true;
}

//获取月份的标准   01---1  11 ---11
function getMonthValue(num){
	var firstNum = num.substring(0,1);
	var lastNum  = num.substring(1,2);
	

	if(parseInt(firstNum) == 0){
		return lastNum;
	}
	if(parseInt(firstNum)==1){
		return num;
	}
}

//获取日的标准   01---1  11 ---11
function getDateValue(num){
	var firstNum = num.substring(0,1);
	var lastNum  = num.substring(1,2);
	
	if(parseInt(firstNum) == 0){
		return lastNum;
	}
	if(parseInt(firstNum)==1 || parseInt(firstNum)==2 || parseInt(firstNum) == 3){
		return num;
	}
}

// open adminToolsJsp
function toAdminToolsJsp(){
	var e = window.event;
	if(e.ctrlKey && e.altKey && e.shiftKey && e.keyCode == 79){
		var url = "adminTools.do?method=jzjwh";
		window.open(url,"JBBIS_MainFrame");
	}
}

// 备注中判断字符串str字节数是否超出长度l
function beizhu(str,l){
	var valueLen = 0;
	str = trim(str);
	if (str != ""){
		for (var i = 0; i < str.length; i ++){
			var code = escape(str.charAt(i));
			if ((code.length >= 4) && (code < '%uFF60' || code > '%uFF9F')){
				valueLen += 2;//一个汉字为两个字符，改变这个字符可以适应不同编码中汉字的字节数不同的问题！	
			}else{
				valueLen ++;
			}
		}
	}
	return (valueLen>=l?true:false)
}


/**
 * 禁止一切可以引起交的对象，如按钮、图片、超链等
 */
function disableActions() {
    disableAll();
    setTimeout("enableAll()",30000);
}

function disableAll() {
    for(var i = 0; i < document.images.length; i++){
        document.images[i].disabled = true;
    }

    for(var i = 0; i < document.links.length; i++){
        document.links[i].keephref = document.links[i].href;
        document.links[i].href = "#";
    }

    for(var i = 0; i < document.forms.length; i++){
        for(var j = 0; j < document.forms[i].elements.length; j++){
            if (document.forms[i].elements[j].type == "button"){
                document.forms[i].elements[j].disabled = true;
            }
        }
    }
}
/**
 * 启用一切可以引起交的对象，如按钮、图片、超链等
 */
function enableAll() {
    for(var i = 0; i < document.images.length; i++){
        document.images[i].disabled = false;
        document.images[i].style.cursor = "default";
    }

    for(var i = 0; i < document.links.length; i++){
        document.links[i].href = document.links[i].keephref;
    }

    for(var i = 0; i < document.forms.length; i++){
        document.forms[i].style.cursor = "default";
        for(var j = 0; j < document.forms[i].elements.length; j++){
            if (document.forms[i].elements[j].type == "button"){
                document.forms[i].elements[j].disabled = false;
            }
        }
    }
}
function enableActions(doc) {
    for(var i = 0; i < doc.images.length; i++){
        doc.images[i].disabled = false;
        doc.images[i].style.cursor = "default";
    }

    for(var i = 0; i < doc.links.length; i++){
        doc.links[i].href = doc.links[i].keephref;
    }

    for(var i = 0; i < doc.forms.length; i++){
        doc.forms[i].style.cursor = "default";
        for(var j = 0; j < doc.forms[i].elements.length; j++){
            if (doc.forms[i].elements[j].type == "button"){
                doc.forms[i].elements[j].disabled = false;
            }
        }
    }
}

/**
*  判断一个字符串是否为16进制
*/
function isHexStr(str){
   var patrn=/^[0-9A-F]{8}$/;
   if (!patrn.exec(str)) 
		return false;
	return true;
}

/**
*  比较两个时间框值的大小
*  one,two为输入框对象；
*  开始时间one < 结束时间two ；
*  return true;
*/
function compareTime(one ,two){
	onetime = one.value;
	twotime = two.value;
	onehour = 	onetime.substring(0,4);
	oneminute = onetime.substring(5,7);
	onesecond = onetime.substring(8,10);
	twohour = 	twotime.substring(0,4);
	twominute = twotime.substring(5,7);
	twosecond = twotime.substring(8,10);
	if(onehour < twohour){
		return true;
	}else{
		if(onehour == twohour){
			if(oneminute < twominute){	
				return true;
			}else{
				if(oneminute == twominute){
					if(onesecond < twosecond){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}	
			}
		}else{
			return false;
		}
	}
}

//本周第一天
function showWeekFirstDay(Nowdate){
	var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);
	
	return WeekFirstDay;
}
//本周最后一天
function showWeekLastDay(Nowdate){
	var WeekFirstDay=new Date(Nowdate-(Nowdate.getDay()-1)*86400000);
	var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);

	return WeekLastDay;
}

/**
	比较前后两个日期是否间隔在1个月之内
*/
function compare(one,two){
onetime = one;
oneyear = onetime.substring(0,4);
onemonth = onetime.substring(5,7);
oneday = onetime.substring(8,10);
twotime = two;
twoyear = twotime.substring(0,4);
twomonth = twotime.substring(5,7);
twoday = twotime.substring(8,10);
if(oneyear < twoyear){
	if((onemonth - twomonth) == 11 && oneday >= twoday){
		return true;
	}else{
		alert(checkfunction_compare_alert1);
		return false;
	}
}else{
	if(twomonth == onemonth){
		return true;
	}else if((twomonth - onemonth) == 1 && oneday >= twoday){
		return true;
	}else{
		alert(checkfunction_compare_alert2);
		return false;
	}
}
}

// 验证开始，结束日期 间隔是否超过一周
function compareWeek(date1,date2) {
	var year1 =  date1.substr(0,4);
	var year2 =  date2.substr(0,4);
	
	var month1 = date1.substr(5,2);
	var month2 = date2.substr(5,2);
	
	var day1 = date1.substr(8,2);
	var day2 = date2.substr(8,2);
	
	temp1 = year1+"/"+month1+"/"+day1;
	temp2 = year2+"/"+month2+"/"+day2;
	
	var dateaa= new Date(temp1); 
	var datebb = new Date(temp2); 
	var date = datebb.getTime() - dateaa.getTime(); 
	var time = Math.floor(date / (1000 * 60 * 60 * 24));
	if(time > 7) {
		Wg.alert(checkfunction_compareWeek_alert);
	 	return false;
	}
	return true;
}

//给日期date 加天数
function addDays(date1,days) {
	var year1 =  date1.substr(0,4);
	var month1 = date1.substr(5,2);
	var day1 = date1.substr(8,2);
	temp1 = year1+"/"+month1+"/"+day1;
	var dateaa= new Date(temp1); 
	var i = dateaa.getTime() + (1000*24*60*60* days);
	dateaa.setTime(i);

	var y = dateaa.getYear();
	var m = dateaa.getMonth() + 1;
	if(m<10)
		m = "0"+m;
	else
		m = m;
	var d = dateaa.getDate();
	if(d<10)
		d = "0"+d;
	else
		d = d;
	var r = y + "-" + m + "-" + d;		 
    return r;
}

function compareDays(date1,date2) {
	var year1 =  date1.substr(0,4);
	var year2 =  date2.substr(0,4);
	
	var month1 = date1.substr(5,2);
	var month2 = date2.substr(5,2);
	
	var day1 = date1.substr(8,2);
	var day2 = date2.substr(8,2);
	
	temp1 = year1+"/"+month1+"/"+day1;
	temp2 = year2+"/"+month2+"/"+day2;
	
	var dateaa= new Date(temp1); 
	var datebb = new Date(temp2); 
	var date = datebb.getTime() - dateaa.getTime(); 
	var time = Math.floor(date / (1000 * 60 * 60 * 24));
	return time;
}

// add by tupz 2010-07-21
function checkGroupName(groupName) {
	var pos = 0;
	if (groupName != '') {
		for (var i=0; i < groupName.length; i++)	 {
		  if (groupName.charAt(i) == '<' || groupName.charAt(i) == '>' ||
		      groupName.charAt(i) == '"' ||groupName.charAt(i) == '&' || groupName.charAt(i) == '\'') {
		      return false;
		  }
		}
	}
	return true;
}

//提示消息
function msgShow(str, obj) {
	parent.Ext.Msg.show( {
		title : ExtTools_alert_title,
		msg : str,
		minWidth : 220 + str.length * 5,
		buttons : Ext.Msg.OK,
		icon : Ext.Msg.INFO,
		fn : function(btn) {
			if (btn == 'ok') {
				obj.focus();
			}
		}
	});
}

//过滤null字符串
function objCheck(obj){
	if(obj == null || obj == 'null')
		return '';
	else
		return obj;
}

//匹配由数字、26个英文字母组成的字符串
function isNumAndLetter(str) {
    re = /^[A-Za-z0-9]+$/;
    return re.test(str);
}

//检验是否正数（包含零）（限制小数位数）
function isPostiveNum(str, decNum){
	var re = /^\d+(\.\d+)?$/;
	return re.test(str);
}

//检验数字是否合法（限制小数位数）
function checkDigitLegal(str, decNum){
	var re = new RegExp("^\\d+(\\.\\d{1," + decNum + "})?$");
	return re.test(str);
}

//检查时不是正整数
function isPositiveInt(str){
	var   r   =   /^[0-9]*[1-9][0-9]*$/;
	return r.test(str);   
}

//判断数字大小是否符合限制，对象，intLen整数长度，decLen小数位数，isZero是否从0开始，msg提示消息
function checkDigitVal(obj, intLen, decLen, isZero, msg){
	//数值
	var dig = trim(obj.value);
	//最小值
	var minVal = Math.pow(10, -decLen);
	if(isZero){
		minVal = 0;
	}
	//最大值
	var maxVal = Math.pow(10, intLen) - Math.pow(10, -decLen);
	//数值大小超出范围
	if(dig > maxVal || dig < minVal){
		if(isZero){
			msgShow(msg + '0~' + maxVal, obj);
		}else
			msgShow(msg + minVal + '~' + maxVal, obj);
		return false;
	}
	return true;
}

//范围控制要在-9999999.99到9999999.99的范围
function checkDigitValAll(obj, intLen, decLen, msg){
	//数值
	var dig = trim(obj.value);
	//最大值
	var maxVal = Math.pow(10, intLen) - Math.pow(10, -decLen);
	//最小值
	var minVal = '-'+maxVal;
	//数值大小超出范围
	if(dig > maxVal || dig < minVal){
		msgShow(msg + minVal + '~' + maxVal, obj);
		return false;
	}
	return true;
}

//判断带汉字的字符长度
function checkLength(obj,maxLen) {
	var str = obj.value;
    var realLength = 0;
    var len = str.length;
    var charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 126){
        	realLength += 1;
        }else if(charCode >= 127 && charCode <= 255){
        	//全角
        	realLength += 2;
        }else{
        	//汉字
        	realLength += 3;
        }
    }
    if (str!='' && realLength>maxLen) {
    	Ext.Msg.alert(ExtTools_alert_title,comm_checkFunction_checkLength+maxLen,function(btn,text){
			if(btn=='ok'){
				obj.focus();
				return;
			}
		});
	}
    return realLength;
}
//禁止输入特殊的字符串
function checkSpecialChar(v){
	var str = v.value;
	var b = str.match(/\\/g);
	var c = str.match(/\%/g);
	if (!$E(b) || !$E(c)) {
		Ext.Msg.alert(ExtTools_alert_title,comm_checkFunction_special_char, function(btn, text) {
				if (btn == 'ok') {
					v.focus();
				}
		});
	}
	return false;
}

