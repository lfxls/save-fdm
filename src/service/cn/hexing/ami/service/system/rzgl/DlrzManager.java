package cn.hexing.ami.service.system.rzgl;

import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.CommonUtil;

/**
 * @Description 登陆日志Service
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-26
 * @version AMI3.0
 */
public class DlrzManager implements DlrzManagerInf {
	private BaseDAOIbatis baseDAOIbatis = null;
	private static String sqlId = "dlrz.";

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		String systemId = CommonUtil.getMultiSystemID();
		param.put("xtid", systemId);
		return baseDAOIbatis.getExtGrid(param, sqlId + "dlrzList", start,
				limit, dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}
}
