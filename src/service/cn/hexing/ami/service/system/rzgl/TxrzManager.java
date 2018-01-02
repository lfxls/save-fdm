package cn.hexing.ami.service.system.rzgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.CommonUtil;

/** 
 * @Description  通讯日志
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-15
 * @version AMI3.0 
 */
public class TxrzManager implements TxrzManagerInf {
	private static String sqlId = "txrz.";
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 通讯日志
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		String systemId = CommonUtil.getMultiSystemID();
		param.put("xtid", systemId);
		return baseDAOIbatis.getExtGrid(param, sqlId + "txrz", start, limit,
				dir, sort, isExcel);
	}

	/**
	 * 通讯日志明细
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 */
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	@Override
	public List<Object> getCzlxList(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId+"czlxList", param);
	}

}

