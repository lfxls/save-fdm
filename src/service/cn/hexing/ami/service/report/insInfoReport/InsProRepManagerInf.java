package cn.hexing.ami.service.report.insInfoReport;

import java.util.HashMap;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface InsProRepManagerInf extends QueryInf {
	public String getChart(Map<String, String> params);
}
