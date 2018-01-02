package cn.hexing.ami.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.Todo;
import cn.hexing.ami.util.ActionResult;

/** 
 * @Description  实现对待办事项的新增，撤销，完成
 * @author  panc
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-16
 * @version AMI3.0 
 */
public class ToDoService implements ToDoInf {
	private static Logger logger = Logger.getLogger(ToDoService.class.getName());
	BaseDAOIbatis baseDAOIbatis = null;
	String sqlId = "todo.";

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 待办事项的新增
	 * @param todo
	 * @return
	 */
	public ActionResult generateTodo(Todo todo) {
		ActionResult re = new ActionResult(false, "");
		Object obj = baseDAOIbatis.insert(sqlId + "insTodo", todo);
		if(null != obj) {
			re.setSuccess(true);
		}
		return re;
	}

	/**
	 * 待办事项的完成
	 * @param appTodoId
	 * @param appId
	 * @return
	 */
	public ActionResult achieveTodo(String appTodoId, String appId) {
		ActionResult re = new ActionResult(false, "");
		Map<String, String> p = new HashMap<String, String>();
		p.put("appTodoId", appTodoId);
		p.put("appId", appId);
		p.put("status", "1");
		
		baseDAOIbatis.update(sqlId + "updTodo", p);
		re.setSuccess(true);
		return re;
	}

	/**
	 * 待办事项的撤销
	 * @param appTodoId
	 * @param appId
	 * @return
	 */
	public ActionResult revokeTodo(String appTodoId, String appId) {
		ActionResult re = new ActionResult(false, "");
		Map<String, String> p = new HashMap<String, String>();
		p.put("appTodoId", appTodoId);
		p.put("appId", appId);
		p.put("status", "2");
		
		baseDAOIbatis.update(sqlId + "updTodo", p);
		re.setSuccess(true);
		return re;
	}

	/**
	 * 业务类型分类统计
	 * @param param
	 * @return
	 */
	public List<Object> getTodoFl(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId + "fl", param);
	}
	
	/**
	 * 业务统计
	 * @param param
	 * @return
	 */
	public int getTodoCnt(Map<String, String> param) {
		return (Integer)baseDAOIbatis.queryForObject(sqlId + "allCnt", param, Integer.class);
	}	
	
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "dcl", start, limit, dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

}

