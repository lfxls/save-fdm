package cn.hexing.ami.util.charts;

import java.util.Map;

//标准线
public class TrendLines {

	private Map<String, String> properties;

	private Line line;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public TrendLines(Line l) {
		this.line = l;
	}

}
