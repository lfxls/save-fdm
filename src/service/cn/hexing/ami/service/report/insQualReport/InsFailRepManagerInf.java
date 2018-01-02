package cn.hexing.ami.service.report.insQualReport;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

public interface InsFailRepManagerInf extends QueryInf {
public List<Object> getTestItem(String lang);
public String getChart(Map<String, String> params);
}
