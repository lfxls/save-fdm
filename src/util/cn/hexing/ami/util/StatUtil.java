package cn.hexing.ami.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//统计工具类
public class StatUtil {

	//初始化BigDecimal数组
	public static BigDecimal[] getBgdArray(int size) {
		BigDecimal[] array = new BigDecimal[size];
		for(int i=0;i<array.length;i++) {
			array[i] = BigDecimal.ZERO;
		}
		return array;
	}
	
	// map 取值
	public static BigDecimal getBgdValue(Map<?, ?> m, String key) {
		Object o = m.get(key);
		if (o instanceof BigDecimal && o != null) {
			return (BigDecimal) o;
		} else {
			//兼容字符型
			try {
				BigDecimal value = new BigDecimal(String.valueOf(o));
				return value;
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		}
	}
	
	//算百分比 a / b * 100
	public static BigDecimal getBdgPersent(BigDecimal a, BigDecimal b) {
		if(BigDecimal.ZERO.equals(b)){
			return BigDecimal.ZERO;
		} else if(BigDecimal.ZERO.equals(a)){
			return BigDecimal.ZERO;
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			double l = (a.doubleValue() / b.doubleValue()) * 100;
			return new BigDecimal(nf.format(l));
		}
	}
	
	//算汇总
	public static Map<String, Object> getHz(List<Object> resultList, String[] yhlxs, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		BigDecimal[] bdArray = getBgdArray(zb.length);
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : yhlxs) {
				for(String ss : zb) {
					bdArray[index] = bdArray[index].add(getBgdValue(m,s+ss));
					index ++;
				}
			}
		}
		
		hzMap.put("DWMC", I18nUtil.getText("common.zjtj.hz", lang));
		hzMap.put("SFXZ", null);
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : yhlxs) {
			for(String ss : zb) {
				hzMap.put(s + ss, bdArray[index]);
				index ++;
			}
		}
		return hzMap;
	}
	
	//算汇总没有单位
	public static Map<String, Object> getHzLst(List<Object> resultList, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		if(resultList.size()>0&&resultList!=null){
		BigDecimal[] bdArray = new BigDecimal[zb.length];
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				if( bdArray[index] == null) {
					bdArray[index] = new BigDecimal(0);
				}
				bdArray[index] = bdArray[index].add(getBgdValue(m,s));
				index ++;
			}
		}
		hzMap.put("GJMC", I18nUtil.getText("common.zjtj.hz", lang));
		hzMap.put("GJBM", null);
		int index = 0;
		for(String s : zb) {
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		}
		return hzMap;
	}
	
	
	//汇总月每一天
	public static Map<String, Object> getHzForMonth(List<Object> resultList, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		String[] bdArray = new String[zb.length];
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				if( bdArray[index] == null) {
					bdArray[index] = "";
				}
				bdArray[index] = getFxValue(bdArray[index], m, s);
				index ++;
			}
		}
		
		hzMap.put("DWMC", I18nUtil.getText("common.zjtj.hz", lang));
		hzMap.put("SFXZ", "FALSE");
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : zb) {
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		return hzMap;
	}
	
	
	//汇总同单位月每一天
	public static Map<String, Object> getHzDwForMonth(List<Object> resultList, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		String[] bdArray = new String[zb.length];
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				if( bdArray[index] == null) {
					bdArray[index] = "";
				}
				bdArray[index] = getFxValue(bdArray[index], m, s);
				index ++;
			}
		}
		
		//hzMap.put("DWMC", "DWMC");
		hzMap.put("SFXZ", "TRUE");
		//hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : zb) {
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		return hzMap;
	}
	
	
	//汇总
	public static Map<String, Object> getHz(List<Object> resultList, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		BigDecimal[] bdArray = new BigDecimal[zb.length];
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				if( bdArray[index] == null) {
					bdArray[index] = new BigDecimal(0);
				}
				bdArray[index] = bdArray[index].add(getBgdValue(m,s));
				index ++;
			}
		}
		
		hzMap.put("DWMC", I18nUtil.getText("common.zjtj.hz", lang));
		hzMap.put("SFXZ", "FALSE");
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : zb) {
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		return hzMap;
	}
	
	
	
	
	/**
	 * 合计值
	 * @param value 原值
	 * @param m 添加值
	 * @param key
	 * @return
	 */
	public static String getFxValue(String value, Map<?,?> m, String key) {
		 Object o =m.get(key);
		 if(o instanceof BigDecimal && o != null) {
			 if(null == value || "".equals(value)){
				 return o.toString();
			 } else {
				 if("".equals(o)){
					 return "";
				 }
				 return ((BigDecimal)o).add(new BigDecimal(value)).toString();
			 }
		 } else {
			 return "";
		 }
	}
	
	
	/**
	 * 算汇总（饼图）
	 */
	public static Map<String, Object> getHzForPie(List<Object> resultList, String[] zb, String lang) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		BigDecimal[] bdArray = new BigDecimal[zb.length];
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				if( bdArray[index] == null) {
					bdArray[index] = new BigDecimal(0);
				}
				bdArray[index] = bdArray[index].add(getBgdValue(m,s));
				index ++;
			}
		}
		
		hzMap.put("DWMC", I18nUtil.getText("common.zjtj.hz", lang));
		hzMap.put("SFXZ", "FALSE");
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : zb) {
			if(s.equals("XSLZCTQS")){
				hzMap.put(I18nUtil.getText("advModule.tqxs.tqxs.xslzctqs", lang), bdArray[index]);
			}else if(s.equals("XSLCDTQS")){
				hzMap.put(I18nUtil.getText("advModule.tqxs.tqxs.xslcdtqs", lang), bdArray[index]);
			}else if(s.equals("XSLWFTQS")){
				hzMap.put(I18nUtil.getText("advModule.tqxs.tqxs.xslwftqs", lang), bdArray[index]);
			}else if(s.equals("XSBKSTQS")){
				hzMap.put(I18nUtil.getText("advModule.tqxs.tqxs.xsbkstqs", lang), bdArray[index]);
			}
			index ++;
		}
		return hzMap;
	}
	
	
	public static Map<String, String> calcAvg(List<Map<String, String>> list,
			int points) {
		int count = list.size();
		double[] _sum = new double[points];
		for (int i = 0; i < count; i++) {
			Map<String, String> map = list.get(i);
			for (int k = 0; k < points; k++) {
				String dv = null;
				if (k < 10) {
					dv = map.get("D0" + k);
				} else {
					dv = map.get("D" + k);
				}
				if (!StringUtil.isEmptyString(dv)) {
					_sum[k] +=  Double.parseDouble(dv)*1000;
				}
			}
		}
		Map<String, String> reMap = new HashMap<String, String>();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		for (int j = 0; j < points; j++) {
			double d = _sum[j] / count;
			String v = String.valueOf(nf.format(d));
			if (j < 10) {
				reMap.put("H0" + j, v);
			} else {
				reMap.put("H" + j, v);
			}
		}
		return reMap;
	}
	
	public static Map<String, String> calc65Value(List<Map<String, String>> list,
			int points) throws ParseException {
		int count = list.size();
		Map<String, String> reMap = new HashMap<String, String>();
		for (int k = 0; k < points; k++) {
			List<Double> tempList = new ArrayList<Double>();
			for (int i = 0; i < count; i++) {
				Map<String, String> map = list.get(i);
				String dv = null;
				if (k < 10) {
					dv = map.get("H0" + k);
				} else {
					dv = map.get("H" + k);
				}
				if (!StringUtil.isEmptyString(dv)) {
					NumberFormat nf = NumberFormat.getInstance();
					Number n;
					n = nf.parse(dv);
					tempList.add(n.doubleValue());
				}
			}
			if (tempList.size()>0){
				Double[] _value = (Double[]) tempList.toArray(new Double[0]);
				Arrays.sort(_value);
				int ilen = _value.length;
				Double aa = Math.ceil(ilen*0.65);
				int pos = aa.intValue();
				DecimalFormat df = new DecimalFormat("#0.##");
				String v = df.format(_value[pos-1]);
				if (k < 10) {
					reMap.put("H0" + k, v);
				} else {
					reMap.put("H" + k, v);
				}
			}		
		}
		return reMap;
	}
	
	public static void main() {
		
	}
	
}
