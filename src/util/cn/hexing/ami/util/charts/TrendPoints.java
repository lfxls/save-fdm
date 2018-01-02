package cn.hexing.ami.util.charts;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public class TrendPoints {
	Map<String, String> properties;
	List<Point> points;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}
