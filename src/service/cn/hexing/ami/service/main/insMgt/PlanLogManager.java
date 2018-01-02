package cn.hexing.ami.service.main.insMgt;

import java.util.Map;
import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;

/**
 * 
 * @Description  安装计划操作日志查询
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-13
 * @version FDM2.0
 */
public class PlanLogManager implements PlanLogManagerInf{

	private static Logger logger = Logger.getLogger(PlanLogManager.class.getName());
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "planLog.";
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryPlanLog", start, limit, dir, sort, isExcel);
	}
	
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
}
