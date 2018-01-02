package cn.hexing.ami.service.main.insMgt;

import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/** 
 * @Description  无序安装计划
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-15
 * @version FDM 
 */
public interface InsUnPlanManagerInf extends DbWorksInf,QueryInf{
	/**
	 * 查询未选择用户
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryUnSelCust(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	/**
	 * 查询已选择用户
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySelCust(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);

	/**
	 * 根据工单ID查询已分配的用户
	 * @param woid
	 * @return
	 */
	public String getDispCustByWoid(String woid);
	
	/**
	 * 根据工单ID查询集中器已分配的变压器
	 * @param woid
	 * @return
	 */
	public String getDDispTfByWoid(String woid);
	
	/**
	 * 根据工单ID查询采集器已分配的变压器
	 * @param woid
	 * @return
	 */
	public String getCDispTfByWoid(String woid);
	
	/**
	 * 查询集中器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDInsP(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询集中器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCInsP(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);

	/**
	 * 查询已选择变压器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySelTf(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询未选择变压器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryUnSelTf(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 根据工单id获取处理人信息
	 * @param woid 工单id
	 * @return
	 */
	public Map<String,String> getOPTInfoByWOID(String woid);
}
