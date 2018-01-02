package cn.hexing.ami.service.report.insInfoReport;

import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;
public interface WoStsRepManagerInf extends QueryInf {
	public String getChart(Map<String, String> params);
}
