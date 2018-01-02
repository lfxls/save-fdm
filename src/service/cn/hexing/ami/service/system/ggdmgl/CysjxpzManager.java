package cn.hexing.ami.service.system.ggdmgl;

import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;

/**
 * @Description 常用数据项Service
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-25
 * @version AMI3.0
 */
public class CysjxpzManager implements CysjxpzManagerInf{
	private BaseDAOIbatis baseDAOIbatis = null;
	private static String sqlId = "cysjxpz.";
	private static String menuId = "54500";
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "cysjx", start, limit, dir, sort, isExcel);
	}
	
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "sjx", start, limit, dir, sort, isExcel);
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if (czid.equals(menuId + Constants.ADDOPT)) { // 新增
			re = addSjx(param);
		} else if (czid.equals(menuId + Constants.DELOPT)) { // 删除
			re = delSjx(param);
		}
		return re;
	}

	private ActionResult addSjx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		int count = (Integer) baseDAOIbatis.queryForObject(sqlId + "getCysjxBySjxbm", param, Integer.class);
		
		if (count == 0) {
			try {
				baseDAOIbatis.insert(sqlId + "addSjx", param);
			}catch (Exception e) {
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.cysjxpz.add_fail", param.get(Constants.APP_LANG));
			}
		}else {
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.cysjxpz.sjx_exsit", param.get(Constants.APP_LANG));
			return re;
		}
		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cysjxpz.add_success", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult delSjx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		int count = (Integer) baseDAOIbatis.queryForObject(sqlId + "getCysjxBySjxbm", param, Integer.class);
		
		if (count > 0) {
			// 删除常用数据项
			try {
				baseDAOIbatis.delete(sqlId + "delSjx", param);
			}catch (Exception e) {
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.cysjxpz.del_fail", param.get(Constants.APP_LANG));
			}
		}
		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cysjxpz.del_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
}