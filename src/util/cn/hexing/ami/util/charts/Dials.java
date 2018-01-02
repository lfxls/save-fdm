package cn.hexing.ami.util.charts;

import java.util.List;
import java.util.Map;

public class Dials {
	Map<String, String> properties;

	List<Dial> dials;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<Dial> getDials() {
		return dials;
	}

	public void setDials(List<Dial> dials) {
		this.dials = dials;
	}

	public Dials(List<Dial> dials) {
		this.dials = dials;
	}

}
