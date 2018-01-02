package cn.hexing.ami.util;
/**
 * 处理字符串相关的工具类
 * @ClassName:StringUtils
 * @Description:TODO
 * @author kexl
 * @date 2014-4-18 上午11:18:02
 *
 */
public class StringUtils {
	/**
	 * 将字符串头尾的逗号（,）去掉
	 * 例如：将,,,1,2,3,,,转换为：1,2,3
	 * @param str
	 * @return
	 */
	public static String trimOne(String str){   
        if(str.startsWith(","))
            return trimOne(new String(new StringBuffer(str).deleteCharAt(0)));
        else if(str.endsWith(","))
            return trimOne(new String(new StringBuffer(str).deleteCharAt(str.length()-1)));
        else
            return str;
	}
}
