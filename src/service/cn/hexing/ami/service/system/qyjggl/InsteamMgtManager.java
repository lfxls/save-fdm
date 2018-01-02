package cn.hexing.ami.service.system.qyjggl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 安装队管理manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-11
 * @version FDM2.0
 */

public class InsteamMgtManager implements InsteamMgtManagerInf {
	private BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "51200";
	static String sqlId = "insteamMgt.";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * @Description 新增安装队
	 * @param param
	 * @return
	 */
	private ActionResult addInsteam(Map<String, String> param){
		ActionResult re = new ActionResult();
		String tno = param.get("tno");
		String tname = param.get("tname");
		String status = param.get("status");
		String rsp_name = param.get("rsp_name");
		String p_num = param.get("p_num");
		String phone = param.get("phone");		
		String crt_date = param.get("crt_date");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		List<String> sqlIds = new ArrayList<String>();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsInsteam", param);
		if(ls != null && ls.size()>0){
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.insteamMgt.existsInsteam", param.get(Constants.APP_LANG));
		}else{
			m.put("tno", tno);
			m.put("tname", tname);
			m.put("status", status);
			m.put("rsp_name", rsp_name);
			m.put("p_num", p_num);
			m.put("phone", phone);			
			m.put("crt_date", crt_date);
			sqlIds.add(sqlId + "insertInsteam");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.insteamMgt.add_success", param.get(Constants.APP_LANG));		
		}		
		return re;
	} 
	
	/**
	 * @Description 更新安装队
	 * @param param
	 * @return
	 */
	private ActionResult updateInsteam(Map<String, String> param){
		ActionResult re = new ActionResult();
		String tno = param.get("tno");
		String tname = param.get("tname");
		String rsp_name = param.get("rsp_name");
		String phone = param.get("phone");
		String status = param.get("status");
		String p_num = param.get("p_num");
		
		//从数据库中获取安装队修改之前的状态
		List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qySts", param);
		String stsOld = StringUtil.getValue(ls1.get(0));
		
		//从数据库中查询，安装队下面是否有操作员
		List<?> ls2 = baseDAOIbatis.queryForList(sqlId + "qyCzyUnderTeam",  tno);
		
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsInsteam", param);
		if (ls != null && ls.size()>0 ) {
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.insteamMgt.existsInsteam", param.get(Constants.APP_LANG));
		} else if ( (ls2 != null) && (ls2.size()>0) && (!stsOld.equals(status)) ) {
			//安装队下面有操作员时，不允许修改状态，防止出现修改安装队为解散状态，在操作员模块还能看到有操作员绑定已经解散的安装队的情况
			re.setSuccess(false);
			re.setMsg("sysModule.qyjggl.insteamMgt.updSts_hasCzy", param.get(Constants.APP_LANG));
		} else {
			m.put("tno", tno);
			m.put("tname", tname);
			m.put("rsp_name", rsp_name);
			m.put("phone", phone);
			m.put("status", status);
			m.put("p_num", p_num);
			m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
			sqlIds.add(sqlId + "updateInsteam");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setSuccess(true);
			re.setMsg("sysModule.qyjggl.insteamMgt.update_success", param.get(Constants.APP_LANG));
		}
		return re;
	}
	
	/**
	 * @Description 删除安装队
	 * @param param
	 * @return
	 */
	private ActionResult deleteInsteam(Map<String, String> param){
		ActionResult re = new ActionResult();
		String tname = param.get("tname");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("tname", tname);
		
		List<Object> ls = this.baseDAOIbatis.queryForList(sqlId + "qyczy", tname);
		List<Object> ls1 = this.baseDAOIbatis.queryForList(sqlId + "cxstatus", tname);
		if ((ls != null) && (ls.size() > 0))
	    {
	      re.setSuccess(false);
	      re.setMsg("sysModule.qyjggl.insteamMgt.existczy", (String)param.get("appLang"));
	    } else if ((ls1 != null) && (ls1.size() > 0)) {
		  re.setSuccess(false);
		  re.setMsg("sysModule.qyjggl.insteamMgt.statusNotDisband", (String)param.get("appLang"));
	    }	else {
	    	sqlIds.add(sqlId + "deleteInsteam");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setMsg("sysModule.qyjggl.insteamMgt.delete_success", param.get(Constants.APP_LANG));
			re.setSuccess(true);	    	
	    }
		//刷新菜单和菜单缓存
		//MenuUtil.loadRoleMenus();
		return re;
	}
	
	/**
	 * @Description 查询安装队
	 * @param param
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getInsteamList", start, limit, dir, sort, isExcel);
		return resultMap;
	}
	
	public Map<String, Object> querySel(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "insteamSel", start, limit, dir, sort, isExcel);
		return resultMap;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if ((menuId + "01").equals(czid)) {
			re = addInsteam(param);
		} else if ((menuId + "02").equals(czid)) {
			re = updateInsteam(param);
		} else if ((menuId + "03").equals(czid)) {
			re = deleteInsteam(param);
		}
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
		
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * @Description 编辑安装队获取信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getInsteam(Map<String, Object> param) {
		return (Map<String, Object>) baseDAOIbatis.queryForList(sqlId+"getInsteam", param).get(0);
	}
	

}
