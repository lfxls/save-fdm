package cn.hexing.ami.service.report.insInfoReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.charts.Category;
import cn.hexing.ami.util.charts.DataSet;
import cn.hexing.ami.util.charts.Dom4jChart;
import cn.hexing.ami.util.charts.Set;

public class InsProRepManager implements InsProRepManagerInf {

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="insProRep.";

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getInsProRep", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public String getChart(Map<String, String> param) {
		String lang= param.get(Constants.APP_LANG);
		List<Category> categoryList = new ArrayList<Category>();
		List<DataSet> dataSets = new ArrayList<DataSet>();
		List<Set> sets = new ArrayList<Set>();
		List<Object> ls=new ArrayList<Object>();
		ls=baseDAOIbatis.queryForList(sqlId+"getChart", param);
		for(int i=0;i<ls.size();i++){
			Map<String,Object> map=(Map<String, Object>) ls.get(i);
			Map<String, String> properties = new HashMap<String, String>();
			String tname=StringUtil.getValue(map.get("TNAME"));
			 Category category = new Category(tname);
	            categoryList.add(category);
			sets.add(new Set(tname,StringUtil.getValue(map.get("INSNUM"))));
		}
		   DataSet dataSet = new DataSet(I18nUtil.getText("report.insInfoReport.insProRep.insNum", lang), null);
	        dataSet.setSets(sets);
	        dataSets.add(dataSet);
		
		
		return Dom4jChart.getBarXml(categoryList, getProperties(I18nUtil.getText("report.insInfoReport.insProRep.chart.title", lang)), dataSets);
	}
	 private Map<String, String> getProperties(String caption) {
	        Map<String, String> properties = new HashMap<String, String>();
	        properties.put("caption", caption);
	        properties.put("palette", "2");
	        properties.put("bgColor", "ffffff");
	        properties.put("showBorder", "0");
	        properties.put("showValues", "0");
	        properties.put("useRoundEdges", "1");
	        properties.put("baseFont", "Arial");
	    	properties.put("baseFontSize", "11");
			properties.put("labelDisplay", "Rotate");
			properties.put("slantLabels", "1");
	        return properties;
	    }
}
