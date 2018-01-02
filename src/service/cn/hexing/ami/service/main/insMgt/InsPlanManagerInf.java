package cn.hexing.ami.service.main.insMgt;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.util.ActionResult;

/** 
 * @Description  安装计划
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-15
 * @version FDM 
 */
public interface InsPlanManagerInf extends DbWorksInf{
	/**
	 * 查询安装计划表计
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryMInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
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
	public Map<String, Object> queryDInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询采集器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询安装计划表计的用户
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCust(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询表计
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryMeter(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询集中器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDcu(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询采集器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCol(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询变压器树
	 * @param nodeId
	 * @param nodeType
	 * @param hh
	 * @return
	 */
	List<TreeNode> getByqTree(String nodeId, String nodeType);
	
	/**
	 * 根据计划ID查询表安装计划
	 * @param pid
	 * @param bussType
	 * @return
	 */
	public Map<String, String> getMInsPByPid(String pid, String bussType);
	
	/**
	 * 根据计划ID查询集中器安装计划
	 * @param pid
	 * @param bussType
	 * @return
	 */
	public Map<String, String> getDInsPByPid(String pid, String bussType);
	
	/**
	 * 根据计划ID查询采集器安装计划
	 * @param pid
	 * @param bussType
	 * @return
	 */
	public Map<String, String> getCInsPByPid(String pid, String bussType);
	
	/**
	 * Excel导入
	 * @param fis
	 * @param param
	 * @param archivesType
	 * @param lang
	 * @return
	 */
	public ActionResult parseExcel(FileInputStream fis, Map<String, String> param, String importType, String lang);
	
	/**
	 * 变压器list
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTf(Map<String, Object> param,
				String start, String limit, String dir, String sort, String isExcel);
	
    /**
     * 校验换表计或者集中器是否已有存在的记录
     * @param param
     * @param util
     * @return
     */
    public ActionResult validateReplace(Map<String,String> param, Util util);
    
    /**
     * 根据工单id获取对应的表安装计划
     * @param woIDList 工单id列表
     * @return
     */
    public List<Object> getMeterPln(List<Object> woIDList);
    
    /**
     * 更新表安装计划状态
     * @param paramList 安装计划状态列表
     * @return
     */
	public ActionResult updPlnStatus(List<Object> paramList);
	
	/**
	 * 获取工单下指定状态的安装计划数量（表，集中器，采集器）
	 * @param woid
	 * @param status
	 * @return
	 */
	public Integer getInsPlnCount(String woid,String[] status);
	
	/**
     * 根据安装计划id获取对应的表安装计划
     * @param pid 安装计划id
     * @return
     */
    public List<Object> getMeterPlnByPid(String pid);
    
    /**
     * 获取变压器id下所有已派工的安装计划
     * @param tfid
     * @return
     */
    public List<Object> getMeterPlnByTfId(String tfid);
    
    /**
     * 保存安装计划操作日志
     * @param param
     * @return
     */
    public ActionResult storePlanOPLog(List<Object> param);
}
