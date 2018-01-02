package cn.hexing.ami.service.report.insInfoReport;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface InsNumRepManagerInf extends QueryInf {
	public String getChart(Map<String, Object> params);
	public ActionResult addNumRep(List<Object> dateList);
}
