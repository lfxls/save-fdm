package cn.hexing.ami.util.charts;

import java.util.List;
import java.util.Map;

public class Chart {
	private Map<String, String> properties;
	// X座标
	private Categories categories;
	// 数据分类堆栈
	private List<DataSet> dataset;
	// 数据堆栈
	private List<Set> sets;
	// 指针 颜色范围
	private ColorRange colorRange;
	// 指针 指针
	private Dials dials;
	// 标准线
	private List<TrendLines> tls;
	private Annotations annotations;
	private TrendPoints trendPoints;
	private String value;
 

	public List<TrendLines> getTls() {
		return tls;
	}

	public void setTls(List<TrendLines> tls) {
		this.tls = tls;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public List<DataSet> getDataset() {
		return dataset;
	}

	public void setDataset(List<DataSet> dataset) {
		this.dataset = dataset;
	}

	public List<Set> getSets() {
		return sets;
	}

	public void setSets(List<Set> sets) {
		this.sets = sets;
	}

	public ColorRange getColorRange() {
		return colorRange;
	}

	public void setColorRange(ColorRange colorRange) {
		this.colorRange = colorRange;
	}

	public Dials getDials() {
		return dials;
	}

	public void setDials(Dials dials) {
		this.dials = dials;
	}

	public Annotations getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotations annotations) {
		this.annotations = annotations;
	}

	public TrendPoints getTrendPoints() {
		return trendPoints;
	}

	public void setTrendPoints(TrendPoints trendPoints) {
		this.trendPoints = trendPoints;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
