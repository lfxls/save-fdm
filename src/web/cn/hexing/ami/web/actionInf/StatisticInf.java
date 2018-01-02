package cn.hexing.ami.web.actionInf;

import java.util.List;

//统计类接口
public interface StatisticInf extends BaseInf {

	// 主统计
	public String statistic();

	// 导出Excel
	public String excel();

	// 导出excel表头
	public List<String> getExcelHead();
}
