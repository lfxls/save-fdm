package cn.hexing.ami.inf;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.Todo;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;


/** 
 * @Description  接口实现对待办事项的新增，撤销，完成
 * @author  panc
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-16
 * @version AMI3.0 
 */
public interface ToDoInf extends QueryInf{

	/**
	 * 待办事项的新增
	 * @param todo
	 * @return
	 */
	public ActionResult generateTodo(Todo todo);
	
	/**
	 * 待办事项的完成
	 * @param appTodoId
	 * @param appId
	 * @return
	 */
	public ActionResult achieveTodo(String appTodoId, String appId);
	
	/**
	 * 待办事项的撤销
	 * @param appTodoId
	 * @param appId
	 * @return
	 */
	public ActionResult revokeTodo(String appTodoId, String appId);
	
	/**
	 * 业务类型分类统计
	 * @param param
	 * @return
	 */
	public List<Object> getTodoFl(Map<String, String> param);		
	
	/**
	 * 业务统计
	 * @param param
	 * @return
	 */
	public int getTodoCnt(Map<String, String> param);
	
}

