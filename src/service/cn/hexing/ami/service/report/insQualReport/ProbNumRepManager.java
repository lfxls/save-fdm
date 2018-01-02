package cn.hexing.ami.service.report.insQualReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;

public class ProbNumRepManager implements ProbNumRepManagerInf {

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="probNumRep.";

	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"probNumRep", start, limit, dir, sort, isExcel);
	}
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		Map<String,Object> map=baseDAOIbatis.getExtGrid(param, sqlId+"probDetailRep", start, limit, dir, sort, isExcel);
		return  map;
	}
	public Map<String, Object> queryTestDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel){
		return baseDAOIbatis.getExtGrid(param, sqlId+"getTestDetail", start, limit, dir, sort, isExcel);
	}
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
}
