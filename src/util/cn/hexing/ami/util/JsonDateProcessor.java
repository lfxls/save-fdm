package cn.hexing.ami.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateProcessor implements JsonValueProcessor {
	private static Logger logger = Logger.getLogger(JsonDateProcessor.class.getName());

	private String datePattern = "yyyy-MM-dd";
	private String timePattern = "yyyy-MM-dd HH:mm:ss";

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public String getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

	public Object processArrayValue(Object value, JsonConfig arg1) {
		return dateProcess(value);
	}

	private Object dateProcess(Object value) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			if (value instanceof Date) {
				return sdf.format((Date) value);
			} else if (value instanceof java.sql.Date) {
				return sdf.format((Date) value);
			}
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
		return value == null ? "" : value.toString();
	}

	private Object timeProcess(Object value) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
			if (value instanceof Date) {
				return sdf.format((Date) value);
			} else if (value instanceof java.sql.Date) {
				java.util.Date utilDate = new java.util.Date(((java.sql.Date) value).getTime());
				return sdf.format(utilDate);
			}
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
		return value == null ? "" : value.toString();
	}

	public Object processObjectValue(String key, Object value, JsonConfig arg2) {
		key = key.toLowerCase();
		if (key.endsWith("sj") || key.endsWith("time")) {
			return timeProcess(value);
		} else if (key.endsWith("rq") || key.endsWith("date")) {
			return dateProcess(value);
		}
		return value == null ? "" : value.toString();
	}

}
