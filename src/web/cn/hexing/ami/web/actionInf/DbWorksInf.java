package cn.hexing.ami.web.actionInf;

import java.util.Map;

import cn.hexing.ami.util.ActionResult;

//操作类接口
public interface DbWorksInf extends BaseInf {

	// 数据库操作dwr
	public ActionResult doDbWorks(final String czid, final Map<String, String> param);

	// 日志
	public void dbLogger(final String czid, final String cznr,
			final String czyId, String unitCode);

}
