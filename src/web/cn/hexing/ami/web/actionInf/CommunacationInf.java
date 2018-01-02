package cn.hexing.ami.web.actionInf;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.util.ActionResult;

//通讯控制类接口
public interface CommunacationInf extends BaseInf {

	// 取用户列表
	public List<Object> getUserList(final Map<String, Object> param) throws Exception;

	// 创建任务dwr
	public ActionResult createTask(final String czid, Map<String, String> param);
	// 取结果
	public String getTaskResult();
	
	// 写操作终端日志
	public void taskLogger(final String czid,final Map<String, String> param ,final String czyid, final String ip);
}
