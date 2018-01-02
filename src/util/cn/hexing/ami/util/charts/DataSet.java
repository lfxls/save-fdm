package cn.hexing.ami.util.charts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.util.StringUtil;

public class DataSet {
	public List<Set> sets;
	private Map<String, String> properties;
	
	
	public DataSet() {
		
	}
	
	public DataSet(String seriesName, String color) {
		properties = new HashMap<String, String>();
		properties.put("seriesName", seriesName);
		properties.put("showValues", "0");
		if(color != null)
		properties.put("color", color);
	}
	
	
	public DataSet(String seriesName, String color,int showVaues) {
		properties = new HashMap<String, String>();
		properties.put("seriesName", seriesName);
		properties.put("showValues", String.valueOf(showVaues));
		if(color != null)
		properties.put("color", color);
	}
	
	public DataSet(String seriesName, String color, String renderAs) {
		properties = new HashMap<String, String>();
		properties.put("seriesName", seriesName);
		properties.put("showValues", "0");
		if(color != null)
		properties.put("color", color);
		if(renderAs != null)
		properties.put("renderAs", renderAs);
	}
	
	
	
	/**
	 * 构造组合图表(曲线和柱状图)
	 * @param seriesName
	 * @param color
	 * @param renderAs
	 * @param parentYAxis:指定数据源属于哪个轴(p:代表主轴,S:代表次轴)
	 */
	public DataSet(String seriesName, String color, String renderAs,String parentYAxis
			,String anchorSides,String anchorRadius,String anchorBorderColor,String numberPrefix) {
		properties = new HashMap<String, String>();
		properties.put("seriesName", seriesName);
		properties.put("showValues", "0");
		if(color != null)
		properties.put("color", color);
		if(!StringUtil.isEmptyString(renderAs))
		properties.put("renderAs", renderAs);
		properties.put("parentYAxis", parentYAxis);
		if(!StringUtil.isEmptyString(anchorSides))
		properties.put("anchorSides", anchorSides);
		if(!StringUtil.isEmptyString(anchorRadius))
		properties.put("anchorRadius", anchorRadius);
		if(!StringUtil.isEmptyString(anchorBorderColor))
		properties.put("anchorBorderColor", anchorBorderColor);
		if(!StringUtil.isEmptyString(numberPrefix))
		properties.put("numberPrefix", numberPrefix);
	}

	public List<Set> getSets() {
		return sets;
	}

	public void setSets(List<Set> sets) {
		this.sets = sets;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
