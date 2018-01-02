package cn.hexing.ami.service.system.ggdmgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/** 
 * @Description 任务模板配置
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-1-12
 * @version AMI3.0 
 */
public interface RwmbpzManagerInf extends DbWorksInf, QueryInf{
	
	/**
	 * 查询待选数据项
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDxsjx(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询任务数据项
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryRwsjx(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 获取结构体内对应的数据项列表
	 * @param param
	 * @return
	 */
	public List<Object> getStructSjxList(Map<String, Object> param);
	
	/**
	 * 获取结构体内对应的数据项列表
	 * 根据任务属性
	 * @param param
	 * @return
	 */
	public List<Object> getStructSjxRwsxList(Map<String, Object> param);
}