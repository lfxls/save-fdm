package cn.hexing.ami.serviceInf;

import java.util.Map;

public interface StatisticInf extends BaseInf {

	public Map<String, Object> statistic(final Map<String, Object> param);

	public String getChart(final Map<String, Object> param, String lang);

}
