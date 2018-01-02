package cn.hexing.ami.service.report.insQualReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.charts.Dom4jChart;
import cn.hexing.ami.util.charts.Set;

public class InsFailRepManager implements InsFailRepManagerInf {
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="insFailRep.";

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getFailRep", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	@Override
	public String getChart(Map<String, String> params) {
		
		List<Set> sets = new ArrayList<Set>();
		Map<String, String> zb = new HashMap<String, String>();
		List<Object> testItemLs=baseDAOIbatis.queryForList(sqlId+"getTestItem", params);
		for(int i=0;i<testItemLs.size();i++){
			Map<String, String> properties = new HashMap<String, String>();
			String tiid=StringUtil.getValue(((Map<String, Object>) testItemLs.get(i)).get("BM"));
			String tiname=StringUtil.getValue(((Map<String, Object>) testItemLs.get(i)).get("MC"));
			params.put("tiid", tiid);
			int count=(Integer) baseDAOIbatis.queryForObject(sqlId+"getChart", params,Integer.class);
			properties.put("value", String.valueOf(count));
			properties.put("label",tiname);
			properties.put("toolText",tiname+":"+count);
			properties.put("link", "JavaScript:reload("+tiid+")");
			sets.add(new Set(properties));
		}
		String caption=I18nUtil.getText("report.insQualReport.insFailRep.chart.title", params.get("appLang"));
		
		String pieXml = Dom4jChart.getPie(caption, sets,getProperties(caption));
		zb.put("pieXml", pieXml);
		String msg = JSONObject.fromObject(zb).toString();
		return msg;
	}
	
	private Map<String, String> getProperties(String caption){
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("caption", caption);
		properties.put("bgColor", "FFFFFF");
		properties.put("baseFontSize", "12");
		properties.put("showBorder", "0");
		//保留2位小数
		properties.put("decimals", "2");
		//1=将数值转换成K (Thousands) & M (Millions) 0=不转换
		properties.put("formatNumberScale", "0");
		properties.put("enableSmartLabels", "1");
		properties.put("palette", "4");
		properties.put("enableRotation", "0");
		properties.put("bgRatio", "0,100");
		properties.put("bgAngle", "360");
		properties.put("bgAlpha", "40,100");
		properties.put("startingAngle", "70");
		properties.put("showPercentValues", "0");
		properties.put("showPercentInToolTip", "0");
		properties.put("showValues", "1");
		properties.put("enableSmartLabels", "1");
		properties.put("pieYScale", "80");
		return properties;
	}
	public List<Object> getTestItem(String lang){
		HashMap<String,Object>param=new HashMap<String, Object>();
		param.put("appLang", lang);
		List<Object> ls=baseDAOIbatis.queryForList(sqlId+"getTestItem", param);
		Map<String,String> o = new HashMap<String, String>();
		o.put("BM", "");
		o.put("MC", I18nUtil.getText("common.select.all", lang));
		ls.add(0,o);
		return ls;
	}

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	

}
