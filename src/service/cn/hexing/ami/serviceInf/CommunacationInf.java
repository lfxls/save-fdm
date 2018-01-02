package cn.hexing.ami.serviceInf;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.util.ActionResult;

public interface CommunacationInf extends BaseInf {
	public List<Object> getUserList(Map<String, Object> param) ;
	
	public ActionResult createTask(final String czid, final Map<String, String> param);
	
	public List<Object> getTaskResult(final String czid ,final Map<String, Object> param);
	
	// 写操作终端日志
	public void taskLogger(final String czid,final Map<String, String> param ,final String czyid, final String ip);
}
