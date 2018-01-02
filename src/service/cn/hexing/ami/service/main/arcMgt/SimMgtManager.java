package cn.hexing.ami.service.main.arcMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;

/**
 * @Description sim卡管理manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-25
 * @version FDM2.0
 */

public class SimMgtManager implements SimMgtManagerInf {
	
	private BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "11500";
	static String sqlId = "simMgt.";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * @Description 新增sim卡
	 * @param param
	 * @return
	 */
	private ActionResult addSim(Map<String, String> param){
		ActionResult re = new ActionResult();
		String simno = param.get("simno");
		String simsn = param.get("simsn");
		String msp = param.get("msp");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		List<String> sqlIds = new ArrayList<String>();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsSim", param);
		if(ls != null && ls.size()>0){
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.simMgt.add_existsSim", param.get(Constants.APP_LANG));
		}else{
			m.put("simno", simno);
			m.put("simsn", simsn);
			m.put("msp", msp);
			sqlIds.add(sqlId + "insertSim");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setSuccess(true);
			re.setMsg("mainModule.arcMgt.simMgt.add_success", param.get(Constants.APP_LANG));		
		}		
		return re;
	} 
	
	/**
	 * @Description 更新sim卡
	 * @param param
	 * @return
	 */
	private ActionResult updateSim(Map<String, String> param){
		ActionResult re = new ActionResult();
		String simno = param.get("simno");
		String simsn = param.get("simsn");
		String msp = param.get("msp");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("simno", simno);
		m.put("simsn", simsn);
		m.put("msp", msp);		
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		sqlIds.add(sqlId + "updateSim");
		baseDAOIbatis.executeBatchTransaction(sqlIds, m);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.simMgt.update_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * @Description 删除sim卡
	 * @param param
	 * @return
	 */
	private ActionResult deleteSim(Map<String, String> param){
		ActionResult re = new ActionResult();
		String simno = param.get("simno");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("simno", simno);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "queryMeterUsingSim", simno);
		if (ls != null && ls.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.simMgt.meterUsingSim", param.get(Constants.APP_LANG));
		} else {
			sqlIds.add(sqlId + "deleteSim");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setMsg("mainModule.arcMgt.simMgt.delete_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);
		}		
		return re;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if ((menuId + "01").equals(czid)) {
			re = addSim(param);
		} else if ((menuId + "02").equals(czid)) {
			re = updateSim(param);
		} else if ((menuId + "03").equals(czid)) {
			re = deleteSim(param);
		}
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);		
	}

	/**
	 * @Description sim卡管理界面，查询sim卡
	 * @param param
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getSimList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	/**
	 * @Description sim卡选择界面，查询sim卡
	 * @param param
	 * @return
	 */
	public Map<String, Object> querySel(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "simSel", start, limit, dir, sort, isExcel);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 编辑SIM获取信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getSim(Map<String, Object> param) {
		return (Map<String, Object>) baseDAOIbatis.queryForList(sqlId+"getSim", param).get(0);
	}

}
