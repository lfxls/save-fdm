package cn.hexing.ami.serviceInf;

import java.util.Map;

import cn.hexing.ami.util.ActionResult;

public interface DbWorksInf extends BaseInf {
	
	public ActionResult doDbWorks(final String czid, final Map<String, String> param);
	
	// 日志
	public void dbLogger(final String czid, final String cznr,
			final String czyId, String unitCode);
}
