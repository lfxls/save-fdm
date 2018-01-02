package cn.hexing.ami.util.charts;

import java.util.List;
import java.util.Map;

public class ColorRange {
	Map<String, String> properties;
	List<Color> colors;

	public ColorRange(List<Color> colors) {
		this.colors = colors;
	}

	public ColorRange() {
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

}
