package cn.hexing.ami.service.main.insMgt;

import java.util.Map;
import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;

/**
 * 
 * @Description  工单操作日志查询
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-13
 * @version FDM2.0
 */
public class OrderLogManager implements OrderLogManagerInf{

	private static Logger logger = Logger.getLogger(OrderLogManager.class.getName());
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "woLog.";
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryWOLog", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
}
