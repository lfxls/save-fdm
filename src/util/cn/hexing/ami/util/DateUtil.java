package cn.hexing.ami.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.util.LogErrorHandler;

import com.ibatis.common.logging.Log;

import cn.hexing.ami.web.listener.AppEnv;

/**
 * 时间日期工具方法
 */
public class DateUtil {

	private static Logger log = Logger.getLogger(DateUtil.class.getName());

	private static String pattern = "yyyy-MM-dd";
	private static SimpleDateFormat sdf = new SimpleDateFormat(pattern);

	private static String defaultDatePattern = null;

	private static String timePattern = "HH:mm";

	public static final String TYPE_DATE = "D"; // 日期

	public static final String TYPE_TIME = "T"; // 时间

	public static final String TYPE_DATETIME = "DT"; // 日期时间

	public static final String STYLE_XML = "X"; // XML日期时间格式

	public static final String STYLE_AD = "AD"; // 日期时间格式：CCYYMMDDhhmmss

	public static final String STYLE_ROC = "R"; // 日期时间格式：YYYMMDDhhmmss

	public static final String STYLE_FORMAT = "F"; // 日期时间格式：CCYY-MM-DD

	public static final String STYLE_FORMAT_FOR_USER = "FU"; // 日期时间格式:CCYY/MM/DD

	public static final SimpleDateFormat DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT_MEDIUM = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");
	
	public static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");

	public static final SimpleDateFormat DATE_FORMAT_MEDIUM_BBS = new SimpleDateFormat("MM-dd HH:mm");

	public static final SimpleDateFormat DATE_FORMAT_SHORT_BBS = new SimpleDateFormat("MM-dd");

	public static final SimpleDateFormat DATE_FORMAT_SHORT_BBSFEN = new SimpleDateFormat("HH:mm");

	public static final SimpleDateFormat DATE_FORMAT_FULL_ZH = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

	public static final SimpleDateFormat DATE_FORMAT_MEDIUM_ZH = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	public static final SimpleDateFormat DATE_FORMAT_SHORT_ZH = new SimpleDateFormat("yyyy年MM月dd日");
	
	public static final SimpleDateFormat DATE_FORMAT_DAY_ZH = new SimpleDateFormat("dd日");
	
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		try {
			defaultDatePattern = "yyyy-MM-dd";
		} catch (MissingResourceException mse) {
			defaultDatePattern = "MM/dd/yyyy";
		}
		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * 格式化日期值为yyyy-MM-dd格式
	 * @param 日期行date值
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据传进来的format格式 以及 需要格式化的String类型的字符串 转化为日期型值
	 * String ---- Date
	 * @param aMask
	 *            format对象
	 * @param strDate
	 *            需要格式化的string类型字符串
	 * @return 日期型
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return (date);
	}

	/**
	 * 把日期型格式化为时间型 返回为string类型的数值
	 * 格式为hh:mm
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}
	
	/**
	 * 日期型转化为字符串
	 * 格式为yyyy-mm-dd
	 * @param aDate
	 *            A date to convert
	 * @return string
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}
	
	/**
	 * 根据传进来的格式化类型与日期，格式为相应的字符串
	 * @param aMask
	 *            format类型
	 * @param aDate
	 *            日期类型值
	 * @return string
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * string --- date
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}
	
	/**
	 * string --- date
	 * 根据传进来的calendar值，时间类型
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * @throws ParseException
	 */
	public synchronized static String getDateTime(Calendar calendar, String type, String style) {
		String myDateTime = "";
		if (type == null || type.equals("")) {
			type = TYPE_DATETIME;
		}
		if (style == null || style.equals("")) {
			style = STYLE_AD;
		}
		String year, month, day, hour, min, sec;
		if (style.equals(STYLE_ROC)) {
			year = padding(calendar.get(Calendar.YEAR) - 1911, 2);
		} else {
			year = padding(calendar.get(Calendar.YEAR), 4);
		}
		month = padding(calendar.get(Calendar.MONTH) + 1, 2);
		day = padding(calendar.get(Calendar.DATE), 2);
		hour = padding(calendar.get(Calendar.HOUR_OF_DAY), 2);
		min = padding(calendar.get(Calendar.MINUTE), 2);
		sec = padding(calendar.get(Calendar.SECOND), 2);

		if (type.equals(TYPE_DATE) || type.equals(TYPE_DATETIME)) {
			myDateTime = year + month + day;
		}
		if (type.equals(TYPE_TIME) || type.equals(TYPE_DATETIME)) {
			myDateTime = myDateTime + hour + min + sec;
		}
		if (style.equals(STYLE_FORMAT)) {
			myDateTime = formateDateTime(myDateTime);
		} 
		return myDateTime;
	}

	/**
	 * 根据所需长度补空格
	 * @param srcString
	 *            源字符串
	 * @param len
	 *            所需长度
	 * @return 所需长度的字符串
	 */
	public synchronized static String padding(String srcString, int len) {
		String desString = null;
		srcString = cropping(srcString, len);
		int srcLen = srcString.getBytes().length;
		desString = srcString;
		for (int i = 0; i < (len - srcLen); i++) {
			desString = desString + " ";
		}
		return desString;
	}

	/**
	 * 根据所需长度补0
	 * @param srcLong
	 *            源数
	 * @param len
	 *            所需长度
	 * @return 所长度的字符串
	 */
	public synchronized static String padding(long srcLong, int len) {
		String desString = null;
		String srcString = String.valueOf(srcLong);
		srcString = cropping(srcString, len);
		int srcLen = srcString.length();
		desString = srcString;
		for (int i = 0; i < (len - srcLen); i++) {
			desString = "0" + desString;
		}
		return desString;
	}

	/**
	 * 剪裁字符串
	 * @param srcString
	 * @param maxLen
	 * @return
	 */
	public synchronized static String cropping(String srcString, int maxLen) {
		String desString = null;
		byte[] desBytes = srcString.getBytes();
		if (desBytes.length > maxLen) {
			byte[] tmpBytes = cropping(desBytes, maxLen);
			desBytes = tmpBytes;
		}
		desString = new String(desBytes);
		return desString;
	}

	/**
	 * 剪裁字符串成字节
	 * @param srcBytes
	 * @param maxLen
	 * @return
	 */
	public synchronized static byte[] cropping(byte[] srcBytes, int maxLen) {
		byte[] desBytes = srcBytes;
		if (srcBytes.length > maxLen) {
			for (int i = 0; i < maxLen; i++) {
				if (srcBytes[i] < 0) {
					i++;
				}
				if (i == maxLen) {
					maxLen = maxLen - 1;
				}
			}
			byte[] tmpBytes = new byte[maxLen];
			System.arraycopy(srcBytes, 0, tmpBytes, 0, maxLen);
			desBytes = tmpBytes;
		}
		return desBytes;
	}

	/**
	 * 格式化日期时间 根据string类型格式化为yyyy-mm 或者yyyy-mm-dd hh:mm类型的字符串
	 * @param myDateTime
	 * @return
	 */
	public synchronized static String formateDateTime(String myDateTime) {
		String rtnDateTime = "";
		if (myDateTime.length() == 8 || myDateTime.length() == 14) {
			rtnDateTime = myDateTime.substring(0, 4) + "-" + myDateTime.substring(4, 6) + "-" + myDateTime.substring(6, 8);
			if (myDateTime.length() == 14) {
				rtnDateTime = rtnDateTime + " ";
				myDateTime = myDateTime.substring(8);
			}
		}
		if (myDateTime.length() == 6) {
			rtnDateTime = rtnDateTime + myDateTime.substring(0, 2) + ":" + myDateTime.substring(2, 4) + ":" + myDateTime.substring(4, 6);
		}
		return rtnDateTime;
	}
	
	
	/**
	 * 格式化日期时间 根据string类型格式化为yyy-mm-dd hh:mm:ss类型的字符串
	 * @param myDateTime
	 * @return
	 */
	public synchronized static String formateFullDateTime(String myDateTime) {
		String rtnDateTime = "";
		if (myDateTime.length() == 14) {
			rtnDateTime = myDateTime.substring(0, 4) + "-" + myDateTime.substring(4, 6) + "-" + myDateTime.substring(6, 8)+" "+myDateTime.substring(8,10)+":"+myDateTime.substring(10,12)+":"+myDateTime.substring(12,14);
		}
		return rtnDateTime;
	}
	
	
	

	/**
	 * 得到当前时间
	 * @param type
	 * @param style
	 * @return
	 */
	public synchronized static String getCurrentTime(String type, String style) {
		Calendar calendar = Calendar.getInstance();
		return getDateTime(calendar, type, style);
	}

	
	 /**
	 * 获取明日时间
	 * @return
	 */
	public static String getTomorrowTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
		return DATE_FORMAT_FULL.format(cal.getTime());
	}
	
	
	/**
	 * 日期加减操作
	 * @param source
	 *            源日期
	 * @param field
	 *            项（日，月，年)
	 * @param num
	 *            数量 + 为加，-为减
	 * @return
	 */
	public static Date dateRoler(Date source, int field, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(source);
		c.add(field, num);
		return c.getTime();
	}

	/**
	 * 日期加减 
	 * @param 所给日期
	 * @param 需要加减天数
	 * @return 返回日期类型数值
	 */
	public static Date addDate(Date date, int day) {
		Date result = null;
		if (date == null)
			return null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day1 = cal.get(Calendar.DATE);
			GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day1);
			gregorianCalendar.add(Calendar.DATE, day);
			result = new java.sql.Date(gregorianCalendar.getTime().getTime());
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	/**
	 * 返回起止日期的小时数组(24点)--画图用
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException 
	 */
	public static String[] getTime(String startday, String endday) throws ParseException {
		String[] returnArray = null;
		String[] hours = new String[24];
		for (int i = 0; i < hours.length; i++) {
			if (i < 10) {
				hours[i] = "0" + String.valueOf(i);
			} else {
				hours[i] = String.valueOf(i);
			}
		}
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days * 24];
		int k = 0;
		String day = startday;
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < hours.length; j++) {
				returnArray[k] = day + " " + hours[j];
				k++;
			}
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 返回起止日期的小时数组(96点)--画图用
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException 
	 */
	public static String[] getTime96(String startday, String endday) throws ParseException {
		String[] returnArray = null;
		String[] hours = new String[24];
		String[] minute = { "00", "15", "30", "45" };

		for (int i = 0; i < hours.length; i++) {
			if (i < 10) {
				hours[i] = "0" + String.valueOf(i);
			} else {
				hours[i] = String.valueOf(i);
			}
		}
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days * 96];
		int k = 0;
		String day = startday;
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < hours.length; j++) {
				for (int m = 0; m < minute.length; m++) {
					returnArray[k] = day + " " + hours[j] + ":" + minute[m];
					k++;
				}
			}
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 返回起止日期的日期数组 间隔日期天数数组--画图用 
	 * @param startday
	 * @param endday
	 * @return
	 * @throws ParseException 
	 */
	public static String[] getDateArray(String startday, String endday) throws ParseException {
		String[] returnArray = null;
		int days = getDate(startday, endday) + 1;
		returnArray = new String[days];

		String day = startday;
		for (int i = 0; i < days; i++) {
			returnArray[i] = day;
			day = getNextDay(day);
		}

		return returnArray;

	}

	/**
	 * 取得起止日期之间的间隔天数
	 * @param startday
	 * @param endday
	 * @return
	 * @throws Exception
	 */
	public static int getDate(String startday, String endday) {
		long ei = 0;
		try {
			// 开始时间和结束时间(Date型)
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = new Date(sf.parse(startday).getTime());
			Date end = new Date(sf.parse(endday).getTime());

			Calendar startcal = Calendar.getInstance();
			Calendar endcal = Calendar.getInstance();
			startcal.setTime(start);
			endcal.setTime(end);

			// 分别得到两个时间的毫秒数
			long sl = startcal.getTimeInMillis();
			long el = endcal.getTimeInMillis();

			ei = el - sl;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
		// 根据毫秒数计算间隔天数
		return (int) (ei / (1000 * 60 * 60 * 24));

	}
	/**
	 * 获取当日日期
	 * @return
	 */
	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
	/**
	 * 获取昨日日期
	 * @return
	 */
	public static String getYesterday(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取明日日期
	 * @return
	 */
	public static String getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
		return sdf.format(cal.getTime());
	}
	
	/**
     * 获取昨日所在月份
     * @return
     */
    public static String getYesterdayMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
        return DATE_FORMAT_MONTH.format(cal.getTime());
    }
   
	/**
	 * 获取前一周日期
	 * @return
	 */
	public static String getLastWeek(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取后一周日期
	 * @return
	 */
	public static String getNextWeek(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 7);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 根据当日获取前一月当前日期 yyyy-mm-dd
	 * @return
	 */
	public static String getLastMonthDay(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		return sdf.format(cal.getTime());
	}
	
	/**
     * 根据某日获取上月同日 yyyy-mm-dd
     * @return
     */
    public static String getLastMonthDay(String day) {
        String lastMonthDay = "";
        try {
            java.util.Date date = sdf.parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            lastMonthDay = sdf.format(cal.getTime());
        } catch (Exception e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return lastMonthDay;
    }
	
	/**
	 * 获取前一月 格式为yyyy-mm
	 * @return
	 */
	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return DATE_FORMAT_MONTH.format(calendar.getTime());
	}
	
	/** 三个月前的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static String getMonth(Date date , int day) {
		String re ="";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day1 = cal.get(Calendar.DATE);
			GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day1);
			gregorianCalendar.add(Calendar.MONTH, day);
			Date result = new java.sql.Date(gregorianCalendar.getTime().getTime());
			re = sdf.format(result);
		} catch (Exception e) {
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
		return re;
	}
	/**
	 * 获取后一月日期
	 * @return
	 */
	public static String getNextMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 获得某日后一日日期
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static String getNextDay(String day) throws ParseException {
		String nextday = "";
		java.util.Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +1);
		nextday = sdf.format(cal.getTime());
		return nextday;
	}
	
	/**
	 * 获得某日前一日日期
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static String getBeforeDay(String day) throws ParseException {
		String nextday = "";
		java.util.Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		nextday = sdf.format(cal.getTime());
		return nextday;
	}
	
	/**
	 * 获得某日前N日的日期
	 * @param day
	 * @param span
	 * @return
	 * @throws ParseException
	 */
	public static String getBeforeDay(String day, int span) throws ParseException {
		String nday = "";
		java.util.Date date = sdf.parse(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -span);
		nday = sdf.format(cal.getTime());
		return nday;
	}
	
	/**
	 * 获得某日后N日日期
	 * @param day
	 * @param span
	 * @return
	 * @throws ParseException
	 */
	public static String getNextNDay(String day, int span) {
		String nday = "";
		java.util.Date date = null;
		try {
			date = sdf.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, +span);
		nday = sdf.format(cal.getTime());
		return nday;
	}
	
	/**
     * 获得某月前月月份
     * @param month
     * @return
     */
    public static String getBeforeMonth(String month) {
        if(month == null || "".equals(month)) return "";
        String beforeMonth = "";
        try {
            java.util.Date date = DATE_FORMAT_MONTH.parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            beforeMonth = DATE_FORMAT_MONTH.format(cal.getTime());
        } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return beforeMonth;
    }
    
    /**
     * 获得某月前N月的月份
     * @param month
     * @param span
     * @return
     */
    public static String getBeforeMonth(String month, int span) {
        if(month == null || "".equals(month)) return "";
        String beforeMonth = "";
        try {
            java.util.Date date = DATE_FORMAT_MONTH.parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -span);
            beforeMonth = DATE_FORMAT_MONTH.format(cal.getTime());
        } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return beforeMonth;
    }
    
    /**
     * 获得某月后N月的月份
     * @param month
     * @param span
     * @return
     */
    public static String getNextNMonth(String month, int span) {
        if(month == null || "".equals(month)) return "";
        String beforeMonth = "";
        try {
            java.util.Date date = DATE_FORMAT_MONTH.parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, +span);
            beforeMonth = DATE_FORMAT_MONTH.format(cal.getTime());
        } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return beforeMonth;
    }
    
    /**
     * 获取某月的第一天日期
     * @param strtmp
     * @return
     * @throws ParseException
     */
    public static String getFirstDayOfMonth(String strtmp) {
    	int year = Integer.parseInt(strtmp.substring(0, 4));
		int month = Integer.parseInt(strtmp.substring(5, 7));
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month-1); 
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String value = sdf.format(cal.getTime());
        return value;
    }
    
    /**
     * 获取某月的最后一天日期
     * @param strtmp
     * @return
     * @throws ParseException
     */
    public static String getLastDayOfMonth(String strtmp) {
    	int year = Integer.parseInt(strtmp.substring(0, 4));
		int month = Integer.parseInt(strtmp.substring(5, 7));
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String value = sdf.format(cal.getTime());
        return value;
    }
    
    /**
     * 获得某年前年的年份
     * @param month
     * @return
     */
    public static String getBeforeYear(String year) {
        if(year == null || "".equals(year)) return "";
        String beforeYear = "";
        try {
            java.util.Date date = DATE_FORMAT_YEAR.parse(year);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, -1);
            beforeYear = DATE_FORMAT_YEAR.format(cal.getTime());
        } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return beforeYear;
    }
    
    /**
     * 获得某年前N年的年份
     * @param month
     * @param span
     * @return
     */
    public static String getBeforeYear(String year, int span) {
        if(year == null || "".equals(year)) return "";
        String beforeYear = "";
        try {
            java.util.Date date = DATE_FORMAT_YEAR.parse(year);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, -span);
            beforeYear = DATE_FORMAT_YEAR.format(cal.getTime());
        } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
        return beforeYear;
    }
	
	/**
	 * String---Calendar
	 * @param dateString
	 * @return Calendar
	 */
	public synchronized static Calendar string2Calendar(String dateString) {
		int year = 0, month = 0, date = 0, hour = 0, min = 0, sec = 0, myLen = 0;
		if (dateString == null) {
			log.debug("string2Calendar():传入时间为null!");
			return null;
		}
		myLen = dateString.length();
		if (myLen == 8 || myLen == 14) {
			year = Integer.parseInt(dateString.substring(0, 4));
			month = Integer.parseInt(dateString.substring(4, 6)) - 1;
			date = Integer.parseInt(dateString.substring(6, 8));
			if (myLen == 14) {
				dateString = dateString.substring(8);
			}
		}

		if (dateString.length() == 6) {
			hour = Integer.parseInt(dateString.substring(0, 2));
			min = Integer.parseInt(dateString.substring(2, 4));
			sec = Integer.parseInt(dateString.substring(4, 6));
		} else 	if (dateString.length() == 4) {
			hour = Integer.parseInt(dateString.substring(0, 2));
			min = Integer.parseInt(dateString.substring(2, 4));
		}

		Calendar calendarObj = Calendar.getInstance();
		if (myLen == 8) {
			calendarObj.set(year, month, date);
			if (year != calendarObj.get(Calendar.YEAR)
					|| month != (calendarObj.get(Calendar.MONTH))
					|| date != calendarObj.get(Calendar.DATE)) {
				log.debug("日期格式错误!");
				return null;
			}
		}  else if (myLen == 4) {
			calendarObj.set(Calendar.HOUR_OF_DAY, hour);
			calendarObj.set(Calendar.MINUTE, min);
			if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0
					|| sec >= 60) {
				log.debug("时间格式错误!");
				return null;
			}
		} else if (myLen == 6) {
			calendarObj.set(Calendar.HOUR_OF_DAY, hour);
			calendarObj.set(Calendar.MINUTE, min);
			calendarObj.set(Calendar.SECOND, sec);
			if (hour < 0 || hour >= 24 || min < 0 || min >= 60 || sec < 0
					|| sec >= 60) {
				log.debug("时间格式错误!");
				return null;
			}
		} else if (myLen == 14) {
			calendarObj.set(year, month, date, hour, min, sec);
			if (year != calendarObj.get(Calendar.YEAR)
					|| month != (calendarObj.get(Calendar.MONTH))
					|| date != calendarObj.get(Calendar.DATE)
					|| hour != calendarObj.get(Calendar.HOUR_OF_DAY)
					|| min != calendarObj.get(Calendar.MINUTE)
					|| sec != calendarObj.get(Calendar.SECOND)) {
				log.debug("日期或时间格式错误!");
				return null;
			}
		} else {
			log.debug("传入长度错误!");
			return null;
		}
		return calendarObj;
	}
	/**
	 * 取得当前时间 yyyy-MM-dd hh24:mi:ss
	 * @return
	 */
	public static String getFulltime() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT_FULL.format(cal.getTime());
	}
	/**
	 * 将所给的Date对象转换为字符串
	 * 
	 * @param source
	 *            要转换的Date对象
	 * @return 目标字符串
	 */
	public static String DateToString(Date source) {
		String result = "";
		result = sdf.format(source);
		return result;
	}
	/**
	 * 得到当前月份，格式为：YYYY-MM
	 * 
	 * @return String
	 */
	public static String getCurrentlyMonth() {
		StringBuffer date = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		date.append(calendar.get(Calendar.YEAR));
		date.append("-");
		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			date.append("0" + (calendar.get(Calendar.MONTH) + 1));
		} else {
			date.append("" + (calendar.get(Calendar.MONTH) + 1));
		}
		return date.toString();
	}
	/**
	 * 得到当前年，格式为：YYYY
	 * 
	 * @return String
	 */
	public static String getCurrentlyYear() {
		StringBuffer date = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		date.append(calendar.get(Calendar.YEAR));
		return date.toString();
	}
	/**
	 * 得到前一个月的日期
	 * 
	 * @return String
	 */
	public static String getFormerDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return getCalendarStr(calendar);
	}
	/**
	 * 得到当前日期
	 * 
	 * @return String
	 */
	public static String getCurrentlyDate() {
		Calendar calendar = Calendar.getInstance();
		return getCalendarStr(calendar);
	}
	
	/**
	 * 从calendar获得字符串表达形式
	 * 
	 * @param date
	 * @param calendar
	 * @return
	 */
	private static String getCalendarStr(Calendar calendar) {
		StringBuffer date = new StringBuffer();
		date.append(calendar.get(Calendar.YEAR));
		date.append("-");
		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			date.append("0" + (calendar.get(Calendar.MONTH) + 1));
		} else {
			date.append("" + (calendar.get(Calendar.MONTH) + 1));
		}
		date.append("-");
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			date.append("0" + calendar.get(Calendar.DAY_OF_MONTH));
		} else {
			date.append("" + calendar.get(Calendar.DAY_OF_MONTH));
		}
		return date.toString();
	}
	
	/**
	 * 得到一个日期所在月的第一天和最后一天
	 * 
	 * @param sj
	 *            时间 格式YYYY-MM-DD
	 * @return
	 */
	public static String[] getMonthFirsAndLast(String sj) {
		String[] dayResult = { "", "" };
		String strtmp = sj;
		if (sj.length() >= 7) {
			strtmp = strtmp.substring(0, 7);
		}
		int year = Integer.parseInt(strtmp.substring(0, 4));
		int month = Integer.parseInt(strtmp.substring(5, 7));
		Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month-1);  
        
      /*  // 某年某月的第一天  
        dayResult[0] = strtmp+"-"+String.valueOf(cal.getActualMinimum(Calendar.DATE));
        // 某年某月的最后一天  
        dayResult[1] = strtmp+"-"+String.valueOf(cal.getActualMaximum(Calendar.DATE));
	  */
        //xcx
        try {
        	// 某年某月的第一天  
			dayResult[0] = sdf.format(sdf.parse(strtmp+"-"+String.valueOf(cal.getActualMinimum(Calendar.DATE))));
			// 某年某月的最后一天  
	        dayResult[1] = sdf.format(sdf.parse(strtmp+"-"+String.valueOf(cal.getActualMaximum(Calendar.DATE))));
        } catch (ParseException e) {
        	// 某年某月的第一天  
            dayResult[0] = strtmp+"-"+String.valueOf(cal.getActualMinimum(Calendar.DATE));
            // 某年某月的最后一天  
            dayResult[1] = strtmp+"-"+String.valueOf(cal.getActualMaximum(Calendar.DATE));
		}
        return dayResult;
	}

	/**
	 * 得到当前月的天数
	 * @return
	 */
	public static int getThisMonthDays() {
		String nextMonth = getNextMonth().substring(0, 7) + "-01";
		String thisMonth = getToday().substring(0, 7) + "-01";
		return getDate(thisMonth, nextMonth);
	}
	
	/** 得到当前月的总天数
	 * @param date
	 * @param day
	 * @return
	 */
	public static int getCountDays() {
		Calendar cld = Calendar.getInstance();
		return cld.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 得到两个时间的间隔分钟
	 */
	public static long getMinuteDiff(Date startDate, Date endDate) {
	    long startTime = 0;
	    if(null != startDate) {
	        startTime = startDate.getTime();
	    }
	    long endTime = 0;
        if(null != endDate) {
            endTime = endDate.getTime();
        }
	    long minute = (endTime - startTime) / (1000 * 60);
	    return minute;
	}
	
	/**
	 * 得到两个时间的间隔天数
	 */
	public static int getDayDiff(Date startDate, Date endDate){
		long minutes = getMinuteDiff(startDate,endDate);
	    return  (int) (minutes / (24 * 60));
	}
	
	/**
	 * 获取某日期之前N天
	 * @param day
	 * @param diff
	 * @return
	 */
	public static String getDayBefore(String day, int diff) {
	    Calendar calendar = Calendar.getInstance();
	    try {
	        calendar.setTime(DATE_FORMAT_SHORT.parse(day));
	        calendar.add(Calendar.DATE, - diff);
	    } catch (ParseException e) {
            log.error(StringUtil.getExceptionDetailInfo(e));
        }
	    return DATE_FORMAT_DAY_ZH.format(calendar.getTime());
	}
	
	//验证HH:MM
	public static boolean validTime(String time) {
		boolean flg = true;
		String[] tt = time.split(":");
		try {
			if (tt.length == 2) {
				int hh = Integer.parseInt(tt[0]);
				int mm = Integer.parseInt(tt[1]);
				if (hh < 0 || hh > 23) {
					flg = false;
				}
				if (mm < 0 || hh > 59) {
					flg = false;
				}
				if (mm == 0 || mm == 30 ) {

				} else if(mm == 59 && hh == 23) {
				    
				} else {
					flg = false;
				}
			} else {
				flg = false;
			}
		} catch (Exception e) {
			flg = false;
		}
		return flg;
	}
	
	@SuppressWarnings("static-access")
	public static String getGwRwSbjzsj(String sbjgsjdw, int addMin) {
		String re = "";
	  	int key = Integer.valueOf(sbjgsjdw);
	  	switch (key) {
		case 2: { //分
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
//			cal.add(cal.MINUTE, addMin); //当前加基准分和随机分
//			re = DATE_FORMAT_FULL.format(cal.getTime());
			//国网集中器上报基准时间，原来基于当前时间，这样加上间隔时间会导致当天数据不会上报的情况，现在基于0时0秒
			re = DATE_FORMAT_SHORT.format(cal.getTime());
			re += " "+minTools(addMin);
			break;
		}
		case 3: { //时
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
//			cal.add(cal.HOUR, 1);//下小时
//			cal.add(cal.MINUTE, addMin);
//			re = DATE_FORMAT_FULL.format(cal.getTime());	
			//国网集中器上报基准时间，原来基于当前时间，这样加上间隔时间会导致当天数据不会上报的情况，现在基于0分0秒
			re = DATE_FORMAT_SHORT.format(cal.getTime());
			re += " "+hourTools(addMin);
			break;
		}
		case 4: { //日
			Calendar cal = Calendar.getInstance();
			String day = sdf.format(cal.getTime());
			re = getNextNDay(day, addMin)+" 00:00:00";//后N天
//			re = getTomorrow() + " 00:00:00"; //明天
			break;
		}
		case 5: { //月
			Calendar cal = Calendar.getInstance();
			String date = sdf.format(cal.getTime());
			String month = date.substring(0,7);
			String day = date.substring(8,date.length());
			re = getNextNMonth(month, addMin)+"-"+day + " 00:00:00"; //后N月
//			re = getNextMonth() + " 00:00:00";//下月
			break;
		}
		default: {
			break;
		}
		};
		return re;
	}

	/**
	 * 拼接小时
	 * @param addHour
	 * @return
	 */
	private static String hourTools(int addHour) {
		String fm = "";
		if(addHour<10) {
			fm = "0" + String.valueOf(addHour) + ":00:00";
		} else {
			fm =  String.valueOf(addHour) + ":00:00";
		}
		return fm;
	}
	/**
	 * 拼接分
	 * @param addmin
	 * @return
	 */
	private static String minTools(int addmin) {
		String fm = "";
		if(addmin<10) {
			fm = "00:" + "0" + String.valueOf(addmin) + ":00";
		} else {
			fm = "00:" + String.valueOf(addmin) + ":00";
		}
		return fm;
	}
	
	public static int getSbjzsj(int sbjzsj, int sbsjz) {
		return sbjzsj + (int)(Math.random() * Double.valueOf(sbsjz)); //基准分和随机分
	}
	
	public static Calendar getCalendr(String time, String format) {
		Calendar cld = Calendar.getInstance();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date;
			date = formatter.parse(time);
			cld.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
		return cld;
	}
	
	/**
	 * 判断字符串是否为日期字符串:yyyy-mm-dd
	 * 公历
	 * @return
	 */
	public static boolean isGregorianDateStr(String dateStr){
		String datePattern = "[\\d]{4}-[0-1][0-9]-[0-3][0-9]";
//		String datePattern = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
		Pattern p = Pattern.compile(datePattern);
	    Matcher m = p.matcher(dateStr);
	    return m.matches();
	}
	
	/**
	 * 判断字符串是否为日期字符串:yyyy-mm-dd
	 * 伊朗历
	 * @return
	 */
	public static boolean isIranDateStr(String dateStr){
		String datePattern = "[\\d]{4}-[0-1][0-9]-[0-3][0-9]";
		Pattern p = Pattern.compile(datePattern);
	    Matcher m = p.matcher(dateStr);
	    return m.matches();
	}
	
	/**
	 * 页面显示list转换伊朗历
	 * @param sourceList
	 * @return
	 */
	public static List<Object> showListByIranDate(List<Object> sourceList){
		List<Object> rtnList = new ArrayList<Object>();
		for (int i = 0; i < sourceList.size(); i++) {
			Object tmpRow = sourceList.get(i);
			//为map的情况
			if (tmpRow instanceof Map) {
				Map rowMap = (Map)tmpRow;
				for (Iterator iterator = rowMap.keySet().iterator(); iterator.hasNext();) {
					Object key = (Object) iterator.next();
					Object value = rowMap.get(key);
					if (value instanceof String) {
						String objStr = String.valueOf(value);
						
						//字符长度 yyyy-mm-dd 长度为10 
						if (!StringUtil.isEmptyString(objStr) && objStr.indexOf("-")!=-1 && objStr.length()>=10) {
							String dateStr1 = objStr.substring(0,10);
							String dateStr2 = objStr.substring(10,objStr.length());
							if (DateUtil.isGregorianDateStr(dateStr1)) {
								String iranDate = DateConvert.gregorianToIran(dateStr1);
								rowMap.put(key, iranDate+dateStr2);
							}
						}
						
						//字符长度 yyyy-mm 长度为7
						if (!StringUtil.isEmptyString(objStr) && objStr.indexOf("-")!=-1 && objStr.length()==7) {
							//拼接一个dd上去
							String dateStr = objStr+"-01";
							if (DateUtil.isGregorianDateStr(dateStr)) {
								String iranDate = DateConvert.gregorianToIran(dateStr);
								iranDate = iranDate.substring(0,7);
								rowMap.put(key, iranDate);
							}
						}
					}
				}
				rtnList.add(rowMap);
			}else{
				//为pojo对象时
				rtnList.add(tmpRow);
			}
		}
		sourceList = null;
		return rtnList;
	}
	
	/**
	 * 页面参数传递后台map，转换其中日期字段
	 * @param param
	 * @return
	 */
	public static Map<String, Object> showMapByIranDate(Map<String, Object> param){
		for (Iterator iterator = param.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object value = (Object)param.get(key);
			if (value instanceof String) {
				String objStr = String.valueOf(value);
				//字符长度 yyyy-mm-dd 长度为10 
				if (!StringUtil.isEmptyString(objStr) && objStr.indexOf("-")!=-1 && objStr.length()>=10) {
					String dateStr1 = objStr.substring(0,10);
					String dateStr2 = objStr.substring(10,objStr.length());
					if (DateUtil.isIranDateStr(dateStr1)) {
						String iranDate = DateConvert.gregorianToIran(dateStr1);
						param.put(key, iranDate+dateStr2);
					}
				}
			}
		}
		return param;
	}
	
	/**
	 * queryforobject对象中公历转换伊朗历
	 * @param rtnObj
	 * @param clazz
	 * @return
	 */
	public static Object changeObjByIranDate(Object rtnObj,Class clazz){
		Object newObj = new Object();
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		
		if (sysMap==null || sysMap.get("calendarType")==null || rtnObj==null) {
			return rtnObj;
		}
		//伊朗模式
		if (sysMap.get("calendarType").equals("iran")) {
			//如果为基本类型，不做解析
			if (CommonUtil.isPrimitive(rtnObj)) {
				return rtnObj;
			}
			//转换伊朗历
			try {
				newObj = Class.forName(clazz.getName()).newInstance();
				newObj = BeanUtils.cloneBean(rtnObj);
				 
				Map param = BeanUtils.describe(rtnObj);
				param = DateUtil.showMapByIranDate(param);
				for (Iterator iterator = param.keySet().iterator(); iterator.hasNext();) {
					String key = String.valueOf(iterator.next());
					Object value = param.get(key);
					try {
						BeanUtils.setProperty(newObj, key, value);
					} catch (Exception e) {
						log.error(StringUtil.getExceptionDetailInfo(e));
					}
				}
			} catch (Exception e) {
				log.error(StringUtil.getExceptionDetailInfo(e));
				return rtnObj;
			} 
			
		}else{
			newObj = rtnObj;
		}
		return newObj;
	}
	
	/**
	 * 转换入库日期 伊朗历转换为公历
	 * @param param
	 * @return
	 */
	public static Object changeMapByGregorianDate(Object obj){
		if (obj instanceof Map) {
			Map<String, Object> param = (Map<String, Object>)obj;
			for (Iterator iterator = param.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Object value = (Object)param.get(key);
				if (value instanceof String) {
					String objStr = String.valueOf(value);
					//字符长度 yyyy-mm-dd 长度为10 
					if (!StringUtil.isEmptyString(objStr) && objStr.indexOf("-")!=-1 && objStr.length()>=10) {
						String dateStr1 = objStr.substring(0,10);
						String dateStr2 = objStr.substring(10,objStr.length());
						if (DateUtil.isIranDateStr(dateStr1)) {
							String iranDate = DateConvert.gregorianToIran(dateStr1);
							param.put(key, iranDate+dateStr2);
						}
					}
				}
			}
			return param;
		}else{
			return obj;
		}
	}
	
	/**
	 * 伊朗日历转换公历
	 * @param iranDateStr
	 * @return
	 */
	public static String iran2gregorian(String iranDateStr){
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			String calendarType = sysMap.get("calendarType");
			//伊朗模式
			if (calendarType!=null && calendarType.equals("iran")) {
				iranDateStr = DateConvert.iranToGregorian(iranDateStr);
			}
		}
		return iranDateStr;
	}
	
	/**
	 * 公历转换伊朗日历
	 * @param iranDateStr
	 * @return
	 */
	public static String gregorian2Iran(String gregorianDateStr){
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			String calendarType = sysMap.get("calendarType");
			//伊朗模式
			if (calendarType!=null && calendarType.equals("iran")) {
				gregorianDateStr = DateConvert.gregorianToIran(gregorianDateStr);
			}
		}
		return gregorianDateStr;
	}
	
	/**
	 * 计算两个时间年相差的数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer getYearDiff(Date startDate,Date endDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String start = dateFormat.format(startDate);
		String end = dateFormat.format(endDate);
		int period = Integer.parseInt(end) - Integer.parseInt(start);
		return period;
	}

	public static Calendar getCalendar(String month, int week)
			throws ParseException {
		Date newDate = convertStringToDate(month + "-01");
		Calendar caleNew = Calendar.getInstance();
		caleNew.setTime(newDate);
		caleNew.add(Calendar.WEEK_OF_MONTH, week - 1);
		return caleNew;
	}

	/**
	 * 获取某月的第n星期的第一天
	 * @param month
	 * @param week
	 * @return
	 */
	public static String getFirstOfWeek(String month, int week) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			GregorianCalendar gc = (GregorianCalendar) getCalendar(month, week);
			cal.setTime(gc.getTime());
			cal.set(Calendar.DATE, gc.get(Calendar.DATE) - gc.get(Calendar.DAY_OF_WEEK) + 2);

		} catch (Exception e) {
		}
		return df.format(cal.getTime());
	}

	/**
	 * 获取某月的第n星期的最后一天
	 * @param month
	 * @param week
	 * @return
	 */
	public static String getLastOfWeek(String month, int week) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar myCale = Calendar.getInstance();
		try {
			GregorianCalendar gc = (GregorianCalendar) getCalendar(month, week);
			myCale.setTime(gc.getTime());
			myCale.set(Calendar.DATE, gc.get(Calendar.DATE) + 8 - gc.get(Calendar.DAY_OF_WEEK));
		} catch (Exception e) {
		}
		return df.format(myCale.getTime());
	}

	/**
	 * 判断当前日期是星期几
	 * @param pTime
	 * @return
	 * @throws Exception
	 */
	public static int dayForWeek(String pTime) {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int dayForWeek = 0;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(pTime));
			if (c.get(Calendar.DAY_OF_WEEK) == 1) {
				dayForWeek = 7;
			} else {
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
		} catch (Exception e) {
		}
		return dayForWeek;
	}
	
	/**
     * 解析日期字符串。日期格式可以是 yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm、yyyy-MM-dd、
     * HH:mm:ss、HH:mm 中的任何一种
     * @param val 日期字符串
     * @return Calendar 对象
     */
    public static Calendar parse(String val) {
        if (val == null) {
            return null;
        }
        
        try {
            Date date = null;
            String s = val.trim();
            int indexOfDateDelim = s.indexOf("-");
            int indexOfTimeDelim = s.indexOf(":");
            int indexOfTimeDelim2 = s.indexOf(":", indexOfTimeDelim + 1);
            if (indexOfDateDelim < 0 && indexOfTimeDelim > 0) {
                if (indexOfTimeDelim2 > 0) {
                    date = timeFormat.parse(s);
                }
                else {
                    date = DATE_FORMAT_SHORT_BBSFEN.parse(s);
                }
            }
            else if (indexOfDateDelim > 0 && indexOfTimeDelim < 0) {
                date = DATE_FORMAT_SHORT.parse(s);
            }
            else {
                if (indexOfTimeDelim2 > 0) {
                    date = DATE_FORMAT_FULL.parse(s);
                }
                else {
                    date = DATE_FORMAT_MEDIUM.parse(s);
                }
            }
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(val + " is invalid date format");
        }
    }
    /**
     * 解析日期字符串。日期格式可以是 yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm、yyyy-MM-dd、
     * HH:mm:ss、HH:mm 中的任何一种
     * @param val 日期字符串
     * @param defaultValue 缺省值。如果 val 非法，则使用该值
     * @return Calendar 对象
     */
    public static Calendar parse(String val, Calendar defaultValue) {
        try {
            return parse(val);
        }
        catch (Exception ex) {
            return defaultValue;
        }
    }
    
    /**
	 * 获取指定格式的当天日期
	 * @return
	 */
	public static String getCurrentDayByFormat(SimpleDateFormat formatStr) {
		Calendar cal = Calendar.getInstance();
		return formatStr.format(cal.getTime());
	}
	
	public static String convertFormat(String dateStr, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String reStr = "";
		try {
			reStr = format.format(format.parse(dateStr));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return reStr;
	}
}
