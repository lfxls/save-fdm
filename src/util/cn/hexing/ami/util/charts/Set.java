package cn.hexing.ami.util.charts;

import java.util.HashMap;
import java.util.Map;

public class Set {
    
	Map<String, String> properties;
	
	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Set(String label, String value) {
		Map<String ,String> temp = new HashMap<String ,String>();
		temp.put("value", value);
		temp.put("label", label);
		this.properties = temp;
	}
	/**
	 * 图形上触发事件
	 */
	public Set(String label, String value, String link) {
		Map<String ,String> temp = new HashMap<String ,String>();
		temp.put("value", value);
		temp.put("label", label);
		temp.put("link", link);
		this.properties = temp;
	}
	
	public Set(Map<String, String> properties) {
		super();
		this.properties = properties;
	}

	/**
	 * 针对泰国项目特殊设置 2013/10/15
	 * @param label
	 * @param value
	 * @param color
	 * @param b
	 */
	public Set(String label, String value, String color, boolean b) {
		Map<String ,String> temp = new HashMap<String ,String>();
		temp.put("value", value);
		temp.put("label", label);
		temp.put("color", color);
		this.properties = temp;
	}
	
	public String getLabel(){
		if(properties!=null) return properties.get("label");
		else return null;
	}
}
