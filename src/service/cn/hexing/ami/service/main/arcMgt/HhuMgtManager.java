package cn.hexing.ami.service.main.arcMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;

/**
 * @Description HHU管理manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class HhuMgtManager implements HhuMgtManagerInf {
	private BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "11600";
	static String sqlId = "hhuMgt.";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * @Description 新增HHU
	 * @param param
	 * @return
	 */
	private ActionResult addHhu(Map<String, String> param){
		ActionResult re = new ActionResult();
		String hhuid = param.get("hhuid");
		String model = param.get("model");
		String bcap = param.get("bcap");
		String appvn = param.get("appvn");
		String wh_date = param.get("wh_date");
		String status = param.get("status");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		List<String> sqlIds = new ArrayList<String>();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsHhu", param);
		if(ls != null && ls.size()>0){
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.hhuMgt.add_existsHhu", param.get(Constants.APP_LANG));
		}else{
			m.put("hhuid", hhuid);
			m.put("model", model);
			m.put("bcap", bcap);
			m.put("appvn", appvn);
			m.put("wh_date", wh_date);
			m.put("status", status);
			sqlIds.add(sqlId + "insertHhu");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setSuccess(true);
			re.setMsg("mainModule.arcMgt.hhuMgt.add_success", param.get(Constants.APP_LANG));		
		}		
		return re;
	} 
	
	/**
	 * @Description 更新HHU
	 * @param param
	 * @return
	 */
	private ActionResult updateHhu(Map<String, String> param){
		ActionResult re = new ActionResult();
		String hhuid = param.get("hhuid");
		String model = param.get("model");
		String bcap = param.get("bcap");
		String appvn = param.get("appvn");
		String wh_date = param.get("wh_date");
		String status = param.get("status");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("hhuid", hhuid);
		m.put("model", model);
		m.put("bcap", bcap);
		m.put("appvn", appvn);
		m.put("wh_date", wh_date);
		m.put("status", status);		
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		sqlIds.add(sqlId + "updateHhu");
		baseDAOIbatis.executeBatchTransaction(sqlIds, m);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.hhuMgt.update_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * @Description 删除HHU
	 * @param param
	 * @return
	 */
	private ActionResult deleteHhu(Map<String, String> param){
		ActionResult re = new ActionResult();
		String hhuid = param.get("hhuid");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("hhuid", hhuid);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "querysts", hhuid);
		if(ls != null && ls.size()>0){
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.hhuMgt.stsenabled", param.get(Constants.APP_LANG));
		}else{
			sqlIds.add(sqlId + "deleteHhu");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setMsg("mainModule.arcMgt.hhuMgt.delete_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);			
		}		
		return re;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if ((menuId + "01").equals(czid)) {
			re = addHhu(param);
		} else if ((menuId + "02").equals(czid)) {
			re = updateHhu(param);
		} else if ((menuId + "03").equals(czid)) {
			re = deleteHhu(param);
		} else if ((menuId + "04").equals(czid)) {
			re = resetHhu(param);
		}
		return re;
	}
	
	/**
	 * @Description 复位HHU
	 * @param param
	 * @return
	 */
	private ActionResult resetHhu(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String hhuid = param.get("hhuid");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("hhuid", hhuid);		
		sqlIds.add(sqlId + "resetHhu");
		baseDAOIbatis.update("hhuMgt.resetHhu", m);
		re.setMsg("mainModule.arcMgt.hhuMgt.reset_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);					
		return re;
	}
	
	/**
	 * @Description 下载完基础数据，数据初始化状态从No改成Yes
	 * @param param
	 * @return
	 */
	@Override
	public void setHhuInit(String hhuID) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("hhuid", hhuID);		
		baseDAOIbatis.update("hhuMgt.setHhuInit", m);				
	}
	
	@Override
	public String getHhuInit(String hhuID) {
		String result = "";
		/*String hhuid = param.get("hhuid");*/
		Map<String,String> map = new HashMap<String,String>();
		map.put("hhuID",hhuID);
		map.put("status",Constants.HHU_STATUS_ENABLED);//过滤出有效的hhu
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getHhuInit", map);
		if(list != null && list.size()>0) {
			result = (String) list.get(0);
		}
		return result;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
		
	}
	
	/**
	 * @Description HHU管理界面，查询HHU
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getHhuList", start, limit, dir, sort, isExcel);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description HHU选择界面，查询HHU
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> querySel(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "hhuSel", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	
	/**
	 * @Description 编辑掌机获取信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getHhu(String hhuid) {
		return (Map<String, Object>) baseDAOIbatis.queryForObject(sqlId+"getHhu", hhuid, HashMap.class);
	}

}
