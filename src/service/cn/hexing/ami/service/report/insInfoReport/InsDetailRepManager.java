package cn.hexing.ami.service.report.insInfoReport;

import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;

public class InsDetailRepManager implements InsDetailRepManagerInf{

	private String sqlId="insDetailRep.";
	private BaseDAOIbatis baseDAOIbatis;
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"insDetail", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

}
