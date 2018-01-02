package cn.hexing.ami.service.system.ggdmgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.Cbsjpz;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/** 
 * @Description  抄表数据配置接口
 * @author  panc
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-12-20
 * @version AMI3.0 
 */
public interface CbsjpzManagerInf extends QueryInf, DbWorksInf {

	/**
	 * 通过大类型,语言，查询抄表数据配置
	 * @param param
	 * @return
	 */
	public List<Object> queryXlxDrop(Map<String, Object> param);
	
	/**
	 * 图形下拉框
	 * @param param
	 * @return
	 */
	public List<Object> queryTxDrop(Map<String, Object> param);
	
	/**
	 * 数据项映射
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySjx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);	
	
	/**
	 * 小类型
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryXlx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 图形类型
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTxlx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 取得对象
	 * @param param
	 * @return
	 */
	public Map<String, Object> getObject(Map<String, Object> param);	
	
}

