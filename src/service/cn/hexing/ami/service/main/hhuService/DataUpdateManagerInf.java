package cn.hexing.ami.service.main.hhuService;

import java.util.List;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;
/**
 * @Description HHU数据更新服务Service接口
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-12
 * @version FDM2.0
 */
public interface DataUpdateManagerInf extends QueryInf, DbWorksInf {
	
	/**
	 * 保存发生更新的基础数据
	 * @param dataType 数据类型：0=变压器，1=参数方案，2=基础代码
	 * @param opType 操作类型：0=新增，1=修改，2=删除
	 * @param optID 操作员ID
	 * @param dataList 数据列表
	 * @param lang 语言
	 * @return
	 */
	public ActionResult storeBasicData(String dataType, String opType, String optID, List<Object> dataList, String lang);
	
	/**
	 * 获取需要更新的基础数据
	 * @param hhuID 掌机ID
	 * @param uptWay 更新方式
	 * @param optID 操作员ID
	 * @param reqID 请求标识
	 * @return
	 */
	public List<Object> getBasicData(String hhuID, String uptWay, String optID, String reqID);
	
	/**
	 * 保存基础数据操作日志
	 * @param hhuID 掌机ID
	 * @param uptWay 更新方式
	 * @param optID 操作员ID
	 * @param reqID 请求标识
	 * @param basicDataList 基础数据列表
	 * @return
	 */
	public ActionResult storeBasicDataLog(String hhuID, String uptWay, String optID, String reqID, List<Object> basicDataList);
	
	/**
	 * 更新基础数据日志状态
	 * @param hhuID 掌机ID
	 * @param reqID 请求标识
	 * @param uptRst 更新结果：0=未知，1=成功，2=失败
	 * @param errMsg 错误信息
	 * @return
	 */
	public ActionResult uptBasicDataLogSts(String hhuID, String reqID, String uptRst, String errMsg);
}
