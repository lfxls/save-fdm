package cn.hexing.ami.util.charts;

import java.util.HashMap;
import java.util.Map;

public class Category {

	private Map<String, String> properties;

	public Category(String label) {
		Map<String, String> temp = new HashMap<String, String>();
		temp.put("label", label);
		this.properties = temp;
	}

	public Category() {
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getLabel() {
		return this.properties.get("label");
	}
}
