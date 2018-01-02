package cn.hexing.ami.serviceInf;

import java.util.Map;

public interface QueryInf extends BaseInf {

	public Map<String, Object> query(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);

	public Map<String, Object> queryDetail(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
}
