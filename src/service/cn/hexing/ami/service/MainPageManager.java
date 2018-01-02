package cn.hexing.ami.service;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.charts.Category;
import cn.hexing.ami.util.charts.Color;
import cn.hexing.ami.util.charts.ColorRange;
import cn.hexing.ami.util.charts.DataSet;
import cn.hexing.ami.util.charts.Dial;
import cn.hexing.ami.util.charts.Dials;
import cn.hexing.ami.util.charts.Dom4jChart;
import cn.hexing.ami.util.charts.Set;

public class MainPageManager {

	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	private String sqlId = "mainPage.";
	
	/**
	 * 某单位下的总表计数量以及告警表计数量统计
	 * @param param
	 * @param util
	 * @return
	 */
	public 	List<Object> getDetailMsg(){
		List<Object> TotalLs = baseDAOIbatis.queryForList(sqlId + "getBjNumTotal", null);
		List<Object> alertLs =  baseDAOIbatis.queryForList(sqlId + "getBjNumAlert", null);
		if (TotalLs.size() > 0) {
			if(alertLs.size()<=0){//无告警数据，加一列(ALERT=0)
				for (int i = 0; i < TotalLs.size(); i++)
				{
					Map<String, Object> m = (Map<String, Object>) TotalLs.get(i);
					m.put("ALERT", String.valueOf(0));
				}
			}else{//某单位下存在告警表计，加一列(ALERT=?)
				for (int i = 0; i < TotalLs.size(); i++) {
					Map<String, Object> map_total = (Map<String, Object>) TotalLs.get(i);
					int j=0;
					int alert_size =alertLs.size();
					for(;j<alert_size ;j++)
					{
						Map<String, Object> map_alert = (Map<String, Object>) alertLs.get(j);
						if(map_total.get("DWDM").toString().equals( map_alert.get("DWDM").toString())){
							map_total.put("ALERT", map_alert.get("ALERT").toString());
							break;
						}
					}
					if(j==alert_size){
						map_total.put("ALERT", String.valueOf(0));
					}
					
				}
			}
		}
		return TotalLs;
	}
	/**
	 * 档案统计
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getDatj(Map<String, String> param, Util util) {
		String[] zbs = new String[] { "zbtyyhs", "zbtyzds", "gbtyjlds",
				"gbtyzds", "dytyyhs", "dytyzds" };
		Map<String, String> zb = getZb(zbs, param.get(Constants.UNIT_CODE),
				param.get(Constants.APP_LANG));
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}

	/**
	 * 通讯在线率
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getTxzxl(Map<String, String> param, Util util) {
		String[] zbs = new String[] { "zbtxcgl24", "gbtxcgl24", "dytxcgl24" };
		Map<String, String> zb = getZb(zbs, param.get(Constants.UNIT_CODE),
				param.get(Constants.APP_LANG));
		String zbcgl = zb.get("zbtxcgl24");
		String gbcgl = zb.get("gbtxcgl24");
		String dycgl = zb.get("dytxcgl24");
		
		zb.put("zbxml", getAngularCurve(zbcgl));
		zb.put("gbxml", getAngularCurve(gbcgl));
		zb.put("dyxml", getAngularCurve(dycgl));
		
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}

	/**
	 * 画通讯成功率指针
	 * @param value
	 * @return
	 */
	private String getAngularCurve(String value) {
		Map<String, String> colorProperties1 = new HashMap<String, String>();
		colorProperties1.put("minValue", "0");
		colorProperties1.put("maxValue", "60");
		colorProperties1.put("code", "FF0000");
		Color color = new Color(colorProperties1);

		Map<String, String> colorProperties2 = new HashMap<String, String>();
		colorProperties2.put("minValue", "60");
		colorProperties2.put("maxValue", "80");
		colorProperties2.put("code", "F9D157");
		Color color1 = new Color(colorProperties2);

		Map<String, String> colorProperties3 = new HashMap<String, String>();
		colorProperties3.put("minValue", "80");
		colorProperties3.put("maxValue", "100");
		colorProperties3.put("code", "86B300");
		Color color2 = new Color(colorProperties3);
		List<Color> colorList = new ArrayList<Color>();
		colorList.add(color);
		colorList.add(color1);
		colorList.add(color2);
		ColorRange colorRange = new ColorRange(colorList);

		List<String> ls = new ArrayList<String>();
		ls.add(value);
		return AngularCurve(ls, colorRange, 180, 90, 80, 60);
	}

	private String AngularCurve(List<?> valueList, ColorRange colorRange,
			int start, int end, int outter, int inner) {
		Map<String, String> addition = new HashMap<String, String>();
		addition.put("palette", "1");
		addition.put("bgColor", "DFE8F6");
		addition.put("upperLimit", "100");
		addition.put("lowerLimit", "0");
		addition.put("showBorder", "1");
		addition.put("chartBottomMargin", "30");
		addition.put("gaugeStartAngle", String.valueOf(start));
		addition.put("gaugeEndAngle", String.valueOf(end));
		addition.put("basefontColor", "333333");
		addition.put("toolTipBorderColor", "333333");
		addition.put("numberSuffix", "%25");
		addition.put("decimals", "2");
		addition.put("gaugeInnerRadius", String.valueOf(inner));
		addition.put("gaugeOuterRadius", String.valueOf(outter));
		String[] color = new String[3];
		color[0] = "CCCCCC";
		color[1] = "006600";
		color[2] = "CCCC00";
		List<Dial> dialList = new ArrayList<Dial>();
		for (int i = 0; i < valueList.size(); i++) {
			String value = (String) valueList.get(i);
			if (!StringUtil.isEmptyString(value)) {
				Map<String, String> dialProperties = new HashMap<String, String>();
				dialProperties.put("baseWidth", "6");
				dialProperties.put("topWidth", "1");
				dialProperties.put("showValue", "1");
				dialProperties.put("rearExtension", "10");
				dialProperties.put("valueY", "270");
				dialProperties.put("value", value);
				dialProperties.put("bgColor", color[i]);
				dialProperties.put("borderColor", color[i]);
				dialList.add(new Dial(dialProperties));
			}
		}
		Dials dials = new Dials(dialList);
		return Dom4jChart.getAngularGauges(addition, colorRange, null, null,
				dials);
	}

	/**
	 * 抄表成功率
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getCbcgl(Map<String, String> param, Util util) {
		String[] zbs = new String[] { "zbyccbcgl", "zbcbcgl", "gbyccbcgl", "gbcbcgl", "dyyccbcgl", "dycbcgl" };
		Map<String, String> zb = getZb(zbs, param.get(Constants.UNIT_CODE),
				param.get(Constants.APP_LANG));
		
		String zbcgl1 = zb.get("zbyccbcgl");
		String zbcgl = zb.get("zbcbcgl");
		String gbcgl1 = zb.get("gbyccbcgl");
		String gbcgl = zb.get("gbcbcgl");
		String dycgl1 = zb.get("dyyccbcgl");
		String dycgl = zb.get("dycbcgl");
		String lang = param.get(Constants.APP_LANG);
		
		Map<String, String> properties = new HashMap<String, String>();
        properties.put("caption", "");
        properties.put("palette", "6");
        properties.put("useRoundEdges", "0");
        properties.put("showBorder", "1");
        properties.put("showValues", "0");
        properties.put("bgColor", "DFE8F6");
        properties.put("baseFontSize", "12");
        properties.put("yAxisMinValue", "60"); // 纵坐标最小值
        properties.put("yAxisMaxValue", "100"); // 纵坐标最大值
        
        // x坐标的时间
        String[] categoryArray = new String[]{ 
        		I18nUtil.getText("mainPage.zb", lang),
        		I18nUtil.getText("mainPage.gb", lang),
        		I18nUtil.getText("mainPage.dy", lang)};
        List<Category> categoryList = new ArrayList<Category>();
        for(int i = 0; i < categoryArray.length; i++) {
            categoryList.add(new Category(categoryArray[i]));
        }
        
        List<DataSet> dataSetList = new ArrayList<DataSet>();
        DataSet dataSet1 = new DataSet(I18nUtil.getText("mainPage.cbcgl.cgl", lang), null);
        List<Set> sets1 = new ArrayList<Set>();
        sets1.add(new Set(null,zbcgl));
        sets1.add(new Set(null,gbcgl));
        sets1.add(new Set(null,dycgl));
		dataSet1.setSets(sets1);
		dataSetList.add(dataSet1);
		
        DataSet dataSet2 = new DataSet(I18nUtil.getText("mainPage.cbcgl.yccgl", lang), null);
        List<Set> sets2 = new ArrayList<Set>();
        sets2.add(new Set(null,zbcgl1));
        sets2.add(new Set(null,gbcgl1));
        sets2.add(new Set(null,dycgl1));
		dataSet2.setSets(sets2);
        dataSetList.add(dataSet2);
        String barXml = Dom4jChart.getBarXml(categoryList, properties, dataSetList);
        zb.clear();
        zb.put("barXml", barXml);
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 异常统计
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getYctj(Map<String, String> param, Util util) {
		String[] zbs = new String[] { "zbycs", "gbycs", "dyycs" };
		Map<String, String> zb = getZb(zbs, param.get(Constants.UNIT_CODE),
				param.get(Constants.APP_LANG));
		String zbycs = zb.get("zbycs");
		String gbycs = zb.get("gbycs");
		String dyycs = zb.get("dyycs");
		String lang = param.get(Constants.APP_LANG);
		
		List<Set> sets = new ArrayList<Set>();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("label", I18nUtil.getText("mainPage.yctj.zb", lang));
		properties.put("value", zbycs);
		properties.put("isSliced", "0");
		sets.add(new Set(properties));
		
		Map<String, String> properties2 = new HashMap<String, String>();
		properties2.put("label", I18nUtil.getText("mainPage.yctj.gb", lang));
		properties2.put("value", gbycs);
		properties2.put("isSliced", "0");
		sets.add(new Set(properties2));
		
		Map<String, String> properties3 = new HashMap<String, String>();
		properties3.put("label", I18nUtil.getText("mainPage.yctj.dy", lang));
		properties3.put("value", dyycs);
		properties3.put("isSliced", "0");
		sets.add(new Set(properties3));
		
		String pieXml = Dom4jChart.getPie("", sets);
		zb.clear();
		zb.put("pieXml", pieXml);
		ActionResult re = new ActionResult();
		String msg = JSONObject.fromObject(zb).toString();
		re.setMsg(msg);
		re.setSuccess(true);
		return re; 
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getZb(String[] zbs, String dwdm, String lang) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("zbbms", zbs);
		obj.put("dwdm", dwdm);
		obj.put(Constants.APP_LANG, lang);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getSyzb", obj);
		Map<String, String> zb = new HashMap<String, String>();
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<String, Object> m = (Map<String, Object>) ls.get(i);
				String zbbm = StringUtil.getValue(m.get("BM"));
//				String zbmc = StringUtil.getValue(m.get("MC"));
				String zbz = StringUtil.getValue(m.get("ZBZ"));
				zb.put(zbbm, zbz); // 指标编码 指标值
			}
		}
		return zb;
	}
	
	
	
	
	

	/**
	 * 档案统计
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public List<Object> getMDMDatj(String dwdm,String lang) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("zbbms", "ty__s");
		obj.put("dwdm", dwdm);
		obj.put("lang", lang);
		return baseDAOIbatis.queryForList(sqlId + "getDA", obj);
	}

	/**
	 * 线路线损
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDMXlxs(Map<String, Object> param, Util util) {
		Map<String, String> zb = new HashMap<String, String>();
		String[] zbs = new String[] { "xlxsl" };
		param.put("zbbms", zbs);
		String lang = (String) param.get(Constants.APP_LANG);
		List<Object> resultList = baseDAOIbatis.queryForList(sqlId + "getXlxs",
				param);
		List<DataSet> dataSets = new ArrayList<DataSet>();
		DataSet dataSet;
		// 设置图形参数
		Map<String, String> prop = new HashMap<String, String>();
		//prop.put("caption",I18nUtil.getText("common.mdmsy.cs.xlxsl", lang) );
		prop.put("palette", "2");
		prop.put("bgColor", "ffffff");
		prop.put("showBorder", "0");
		prop.put("showValues", "1");
		//prop.put("showhovercap", "0");
		prop.put("useRoundEdges", "1");
		prop.put("baseFont", "Arial"); // 图表字体
		prop.put("baseFontSize", "12");
		prop.put("yAxisMinValue", "60");
		prop.put("yAxisMaxValue", "100");
		// 设置图形数据
		List<Category> categoryList = new ArrayList<Category>();
		List<Set> sets = new ArrayList<Set>();
		for (Object obj : resultList) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Category category = new Category(String.valueOf(map.get("XLMC")));
			categoryList.add(category);
			sets.add(new Set((String) map.get("XLMC"), String.valueOf(map
					.get("XLXSL"))));

		}
		dataSet = new DataSet(I18nUtil.getText("common.mdmsy.cs.xlxsl", lang)+"(%)" , null,1);
		dataSet.setSets(sets);
		dataSets.add(dataSet);

		String barXml = Dom4jChart.getBarXml(categoryList, prop, dataSets);
		zb.clear();
		zb.put("barXml", barXml);
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;

	}

	/**
	 * 抄表成功率
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDMCbcgl(Map<String, String> param, Util util) {
		Map<String, String> zb = new HashMap<String, String>();
		List<Object> ls  =getMdmZb("cbcgl", param.get(Constants.UNIT_CODE), param.get(Constants.APP_LANG));
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("caption", "");
		properties.put("palette", "6");
		properties.put("useRoundEdges", "0");
		properties.put("showBorder", "1");
		properties.put("showValues", "1");//'0/1'(是否在图形上显示每根柱子具体的值)
		properties.put("bgColor", "DFE8F6");
		properties.put("baseFontSize", "12");
		properties.put("yAxisMinValue", "60"); // 纵坐标最小值
		properties.put("yAxisMaxValue", "100"); // 纵坐标最大值
		
		List<Category> categoryList = new ArrayList<Category>();
		List<DataSet> dataSetList = new ArrayList<DataSet>();
		DataSet dataSet1 = new DataSet(I18nUtil.getText("common.zysz.cbcgl", param.get(Constants.APP_LANG))+"(%)", null,1);
		List<Set> sets1 = new ArrayList<Set>();
		
		if (ls.size() > 0) {
			for (Object obj:ls) {
				Map<?, ?> m = (Map<?, ?>) obj;
				categoryList.add(new Category( StringUtil.getValue(m.get("CSMC"))));
				sets1.add(new Set(null, StringUtil.getValue(m.get("ZBZ"))));
			}
		}
		dataSet1.setSets(sets1);
		dataSetList.add(dataSet1);
		String barXml = Dom4jChart.getBarXml(categoryList, properties, dataSetList);
		zb.clear();
		zb.put("barXml", barXml);
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}

	/**
	 * 异常统计
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDMYctj(Map<String, String> param, Util util) {
		Map<String, String> zb = new HashMap<String, String>();
		List<Set> sets = new ArrayList<Set>();
		List<Object> ls  = getMdmZb("ycs", param.get(Constants.UNIT_CODE), param.get(Constants.APP_LANG));
		if (ls.size() > 0) {
			for (Object obj:ls) {
				Map<?, ?> m = (Map<?, ?>) obj;
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("label", StringUtil.getValue(m.get("CSMC")));
				properties.put("value", StringUtil.getValue(m.get("ZBZ")));
				properties.put("isSliced", "0");
				sets.add(new Set(properties));
			}
		}
		String pieXml = Dom4jChart.getPie("", sets);
		zb.clear();
		zb.put("pieXml", pieXml);
		ActionResult re = new ActionResult();
		String msg = JSONObject.fromObject(zb).toString();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}

	public List<Object>  getMdmZb(String zbs, String dwdm, String lang) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("zbbms", zbs);
		obj.put("dwdm", dwdm);
		obj.put("lang", lang);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getMdmSyzb", obj);
		return ls;
	}
	
	
	/**
	 * 设备数量
	 * 
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDCSbsl(Map<String, String> param, Util util) {
		Map<String, String> sb = getSb(param.get(Constants.UNIT_CODE));
		String msg = JSONObject.fromObject(sb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 最近三天抄表成功率
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDCCbcgl(Map<String, String> param, Util util) {
		String[] zbs = new String[] {"dycbcgl" };
		Map<String, String> zb = getZbByrq(zbs, param.get(Constants.UNIT_CODE),
				param.get(Constants.APP_LANG), 3);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		String jt = format.format(new Date());
		ca.add(Calendar.DAY_OF_MONTH, -1);
		String zt = format.format(ca.getTime());
		ca.add(Calendar.DAY_OF_MONTH, -1);
		String qt = format.format(ca.getTime());
		String dycgl_jt = zb.get("dycbcgl" +":" + jt);
		String dycgl_zt = zb.get("dycbcgl" +":" + zt);
		String dycgl_qt = zb.get("dycbcgl" +":" + qt);
		zb.put("cbcgljt", dycgl_jt==null?"0.0" : dycgl_jt);
		zb.put("cbcglzt", dycgl_zt==null?"0.0" : dycgl_zt);
		zb.put("cbcglqt", dycgl_qt==null?"0.0" : dycgl_qt);
		String lang = param.get(Constants.APP_LANG);
		zb.put("dyxmljt", getAngularCurve(dycgl_jt));
		zb.put("dyxmlzt", getAngularCurve(dycgl_zt));
		zb.put("dyxmlqt", getAngularCurve(dycgl_qt));
		String msg = JSONObject.fromObject(zb).toString();
		ActionResult re = new ActionResult();
		re.setMsg(msg);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 告警统计
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getMDCGjtj(Map<String, String> param, Util util) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getGjtj", obj);
		List<Set> sets = new ArrayList<Set>();
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<String, Object> m = (Map<String, Object>) ls.get(i);
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("label", StringUtil.getValue(m.get("NAME")));
				properties.put("value", StringUtil.getValue(m.get("RN")));
				properties.put("isSliced", "0");
				sets.add(new Set(properties));
			}
		}
		Map<String, String> zb = new HashMap<String, String>();
		String pieXml = Dom4jChart.getPie("", sets);
		zb.put("pieXml", pieXml);
		ActionResult re = new ActionResult();
		String msg = JSONObject.fromObject(zb).toString();
		re.setMsg(msg);
		re.setSuccess(true);
		return re; 
	}
	
	/**
	 * 最近几天的指标值
	 * @param param
	 * @param util
	 * @param days  最近n天数据
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getZbByrq(String[] zbs, String dwdm, String lang, Integer days) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("zbbms", zbs);
		obj.put("dwdm", dwdm);
		obj.put("days", days);
		obj.put(Constants.APP_LANG, lang);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getSyCbcgl", obj);
		Map<String, String> zb = new HashMap<String, String>();
		if (ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<String, Object> m = (Map<String, Object>) ls.get(i);
				String zbbm = StringUtil.getValue(m.get("BM"));
				String zbrq = m.get("RQ") == null ? "" : DateUtil.convertDateToString((Timestamp)m.get("RQ"));
				String zbz = StringUtil.getValue(m.get("ZBZ"));
				if(zbz != null && !zbz.equals("null") && !zbz.trim().equals(""))
					zbz = String.valueOf(Double.valueOf(zbz));   //处理.75类似数据
				zb.put(zbbm + ":" +zbrq, zbz); // 指标编码 指标值
			}
		}
		return zb;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getSb(String dwdm) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("dwdm", dwdm);
		Integer gprsCount = (Integer)baseDAOIbatis.queryForObject(sqlId + "getGprs", obj, Integer.class);
		Integer jzqCount = (Integer)baseDAOIbatis.queryForObject(sqlId + "getJzq", obj, Integer.class);
		Integer plcCount = (Integer)baseDAOIbatis.queryForObject(sqlId + "getPlc", obj, Integer.class);
		Map<String, String> zb = new HashMap<String, String>();
		zb.put("gprsb", String.valueOf(gprsCount));
		zb.put("jzq", String.valueOf(jzqCount));
		zb.put("plcb", String.valueOf(plcCount));
		return zb;
	}
}
