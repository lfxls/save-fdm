package cn.hexing.ami.service.report.insQualReport;

import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

public interface ProbNumRepManagerInf extends QueryInf {
	public Map<String, Object> queryTestDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
}
