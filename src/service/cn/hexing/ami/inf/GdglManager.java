package cn.hexing.ami.inf;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.Todo;
import cn.hexing.ami.dao.common.pojo.da.KcGdmx;
import cn.hexing.ami.inf.ToDoInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.SpringContextUtil;

/** 
 * @Description  工单管理公共方法
 * @author  zhouh
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-17
 * @version MDM 
 */
public class GdglManager {
	private static Logger logger = Logger.getLogger(GdglManager.class.getName());
	
	/**
	 * 勘查工单明细插入
	 * @param kcGdmx
	 * @param baseDao
	 */
	public static void insertKcGdmx(KcGdmx kcGdmx, BaseDAOIbatis baseDao){
		baseDao.insert("mdm_gdsc.insKcGdmx", kcGdmx);
	}
	
	/**
	 * 勘查工单明细反馈更新
	 * @param param
	 * @param baseDao
	 */
	public static void updateKcGdmx(Map<String, String> param, BaseDAOIbatis baseDao){
		baseDao.insert("mdm_gdfk.updKcGdmx", param);
	}
	
	/**
	 * 调用待办接口
	 * @param todo
	 * @return
	 */
	public static ActionResult insertDbmx(Todo todo){
		ActionResult re = new ActionResult();
		// sping反射获得ToDoInf类
		ToDoInf toDoInf = (ToDoInf) SpringContextUtil.getBean("toDoInf");
		re = toDoInf.generateTodo(todo);
		return re;
	}
	
	/**
	 * 获取勘查工单明细
	 * @param kcGdmx
	 * @param baseDao
	 * @return
	 */
	public static Map<String, String> getKcGdmx(Map<String, String> param, BaseDAOIbatis baseDao){
		return (Map<String, String>) baseDao.queryForObject("mdm_gdfk.getKcGdmx", param, HashMap.class);
	}

}
