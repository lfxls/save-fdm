package cn.hexing.ami.service.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * 
 * @Description  电量查询
 * @author  xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-9-26
 * @version FDM2.0
 */
public class EnergyQueryManager implements EnergyQueryManagerInf{

	private static Logger logger = Logger.getLogger(EnergyQueryManager.class.getName());
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "energyQuery.";
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String sjx = "tt";//默认为正向有功总
		if(sysMap != null) {
			sjx = sysMap.get("old_msn_energy_sjx");
		}
		String[] sjxs = sjx.split(",");
		param.put("sjxs", sjxs);
		Map<String,Object> reMap = baseDAOIbatis.getExtGrid(param, sqlId + "energy", start, limit, dir, sort, isExcel);
		Map<String,String> readMap = getReadPM(param);
		List<Object> resultList = (List<Object>) reMap.get("result");
		if(resultList != null) {
			for(int i = 0; i <  resultList.size(); i++) {
				Map<String,Object> resultMap = (Map<String, Object>) resultList.get(i);
				String pid = StringUtil.getValue(resultMap.get("PID"));
				String o_msn = StringUtil.getValue(resultMap.get("OMSN"));
				for(int j = 0; j < sjxs.length; j++) {
					String tempSjxValue = readMap.get(pid+o_msn+sjxs[j]);
					if(!StringUtil.isEmptyString(tempSjxValue)) {
						resultMap.put(sjxs[j], tempSjxValue);
					} else {
						resultMap.put(sjxs[j], "");
					}
				}
			}
		}
		return reMap;
	}
	
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * 变压器查询
	 */
	public Map<String, Object> queryTf(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getTfList", start, limit, dir, sort, isExcel);
	}
	
	private Map<String,String> getReadPM(Map<String,Object> param) {
		Map<String,String> readMap = new HashMap<String,String>();
		List<Object> readList = baseDAOIbatis.queryForList(sqlId + "getReadPM", param);
		for(int i = 0; i < readList.size(); i++) {
			Map<String,String> map = (Map<String, String>) readList.get(i);
			readMap.put(map.get("BM"),map.get("MC"));
		}
		return readMap;
	}
}
