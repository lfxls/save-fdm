package cn.hexing.ami.service.main.insMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.main.pojo.insMgt.WorkOrder;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface InsOrderManagerInf extends DbWorksInf, QueryInf{
	/**
	 * 查询已添加到工单生成中的表安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryMInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询已添加到工单生成中的集中器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询已添加到工单生成中的采集器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询未处理的表安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryMPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	/**
	 * 查询未处理的集中器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询未处理的采集器安装计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryCPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	/**
	 * 查询处理人
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryPOP(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	/**
	 * 获取表安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getMInsPByWOID(Map<String,String> param);
	
	/**
	 * 获取集中器安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getDInsPByWOID(Map<String,String> param);
	
	/**
	 * 获取采集器安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getCInsPByWOID(Map<String,String> param);
	
	/**
	 * 查询安装计划更换表计
	 */
	public Map<String, Object> queryMeter(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	
	/**
	 * 获取接收人下可下载的已分配和待撤销工单
	 * @param optID 接收人id
	 * @param uptWay 更新方式 0=全量，1=增量
	 * @return
	 */
	public List<Object> getDownLoadWO(String optID, String uptWay);
	
	/**
	 * 获取操作员对应的下载反馈工单
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 接收人id
	 * @return
	 */
	public List<Object> getWOFromLog(String hhuID,String reqID,String optID);
	
	/**
	 * 更新工单状态
	 * @param paramList 工单状态列表
	 * @return
	 */
	public ActionResult updWOStatus(List<Object> paramList);
	
	/**
	 * 保存工单及工单下的安装计划
	 * @param woList 工单号列表
	 * @param insPlnList 安装计划列表
	 * @return
	 */
	public ActionResult storeWO(List<Object> woList, List<Object> insPlnList);
	
	/**
	 * 更新HHU操作工单日志状态
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 操作员id
	 * @param optRst 操作结果
	 * @param errMsg 失败信息
	 * @return
	 */
	public ActionResult updWOLog(String hhuID, String reqID, String optID, 
			String optRst, String errMsg);
	
	/**
	 * 保存HHU操作工单日志状态
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 操作员id
	 * @param optRst 操作结果
	 * @param errMsg 失败信息
	 * @param optType 操作类型
	 * @param woList 工单列表
	 * @param uptWay 更新方式
	 * @return
	 */
	public ActionResult stroeWOLog(String hhuID, String reqID, String optID, 
			String optRst, String errMsg, String optType, List<Object> woList, String uptWay);
	
	/**
	 * 获取工单信息
	 * @param woid 工单id
	 * @return
	 */
	public WorkOrder getWOInfo(String woid);
	
	/**
	 * 获取工单序列标识id
	 * @return
	 */
	public String getWOID();
	
	/**
	 * 撤销工单
	 * @param map
	 * @return
	 */
	public ActionResult revokeWorkOrder(Map<String,String> map);
	
	/**
	 * 保存工单操作日志
	 * @param param
	 * @return
	 */
	public ActionResult storeWOOPLog(List<Object> param);
	
	/**
	 * 根据工单id获取处理人信息
	 * @param woid 工单id
	 * @return
	 */
	public Map<String,String> getOPTInfoByWOID(String woid);
	
	/**
	 * 查询已添加到工单生成中的勘察计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	
	public Map<String, Object> querySurPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);

	/**
	 * 查询未处理的勘察计划
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);

	public String getSurPByWOID(Map<String, String> param);

}
