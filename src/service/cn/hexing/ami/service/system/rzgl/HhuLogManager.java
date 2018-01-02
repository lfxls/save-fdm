package cn.hexing.ami.service.system.rzgl;

import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;

/**
 * @Description 掌机日志manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-6-13
 * @version FDM2.0
 */

public class HhuLogManager implements HhuLogManagerInf {
	
	private BaseDAOIbatis baseDAOIbatis;
	static String menuId = "53300";
	static String sqlId = "hhuLog.";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);		
	}

	/**
	 * @Description 掌机工单日志界面，查询日志列表
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuWoLogList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 掌机基础数据日志界面，查询日志列表
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuDuLog(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuDuLogList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 明细中，查询工单信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuWo(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuWoList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 明细中，查询变压器信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuTf(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuTfList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 明细中，查询参数方案信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuPs(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuPsList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 明细中，查询基础代码信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuCode(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuCodeList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description 明细中，查询Token信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryHhuToken(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuTokenList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

}
