package cn.hexing.ami.util.charts;

import java.util.Map;

public class Color {
	
	
	public Color(Map<String, String> properties) {
		super();
		this.properties = properties;
	}

	Map<String, String> properties;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
