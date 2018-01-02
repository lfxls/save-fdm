package cn.hexing.ami.util;

import java.util.Locale;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 国际化工具类
 * @author jun
 *
 */
public class I18nUtil {

	/**
	 * 获取对应国际化标签
	 * @param aTextName
	 * @return
	 */
	public static String getText(String aTextName, String lang){
		String[] localeArray = lang.split("_");
		Locale currentLocale = new Locale(localeArray[0],localeArray[1]);   
		return LocalizedTextUtil.findDefaultText(aTextName,currentLocale);
	}
	
	/**
	 * 获取对应国际化标签 带占位符
	 * @param aTextName
	 * @return
	 */
	public static String getText(String aTextName, String lang, String[] args){
		String[] localeArray = lang.split("_");
		Locale currentLocale = new Locale(localeArray[0],localeArray[1]);   
		return LocalizedTextUtil.findDefaultText(aTextName, currentLocale, args);
		
	}
}
