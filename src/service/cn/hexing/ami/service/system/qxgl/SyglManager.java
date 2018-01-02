package cn.hexing.ami.service.system.qxgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.system.pojo.sygl.Sygl;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;

/** 
 * @Description  首页管理
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-13
 * @version AMI3.0 
 */
public class SyglManager implements SyglManagerInf {
	BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "52400";
	static String sqlId = "sygl.";

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 查询首页
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		//String systemId = CommonUtil.getMultiSystemID();
		//param.put("xtid", systemId);
		return baseDAOIbatis.getExtGrid(param,sqlId + "sygl" ,start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 首页管理操作
	 * @param czid
	 * @param param
	 * @return
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = null;
		if ((menuId + Constants.ADDOPT).equals(czid)) { 
			//新增
			param.put("syid", CommonUtil.generateID());
			param.put("symc", param.get("sygl.symc"));
			param.put("url", param.get("sygl.url"));
			param.put("xtmr", param.get("sygl.xtmr"));
			param.put("ms", param.get("sygl.ms"));
			param.put("lang", param.get("sygl.lang"));
			
			//String systemId = CommonUtil.getMultiSystemID();
			//param.put("xtid", systemId);
			re = insSygl(param);
		} else if ((menuId + Constants.UPDOPT).equals(czid)) { 
			//更新
			param.put("syid", param.get("sygl.syid"));
			param.put("symc", param.get("sygl.symc"));
			param.put("url", param.get("sygl.url"));
			param.put("xtmr", param.get("sygl.xtmr"));
			param.put("ms", param.get("sygl.ms"));
			param.put("lang", param.get("sygl.lang"));
			
			//String systemId = CommonUtil.getMultiSystemID();
			//param.put("xtid", systemId);
			re = updSygl(param);
		} else if ((menuId + Constants.DELOPT).equals(czid)) { 
			//删除
			re = delSygl(param);
		}
		return re;
	}

	/**
	 * 新增首页
	 * @param param
	 * @return
	 */
	private ActionResult insSygl(Map<String, String> param) {
		ActionResult re = new ActionResult();
		// 如果新建记录默认为首页，变更其他记录为非首页
		if("01".equals(param.get("xtmr"))){
			baseDAOIbatis.update(sqlId + "updXtmr", param);
		}
		baseDAOIbatis.insert(sqlId + "insSygl", param);
		re.setSuccess(true);
		re.setMsg("sysModule.qxgl.sygl.add_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 编辑首页
	 * @param param
	 * @return
	 */
	private ActionResult updSygl(Map<String, String> param) {
		ActionResult re = new ActionResult();
		// 如果编辑记录默认为首页，变更其他记录为非首页
		List<String> sqlIds = new ArrayList<String>();
		if("01".equals(param.get("xtmr"))){
			sqlIds.add(sqlId + "updXtmr");
		}
		
		sqlIds.add(sqlId + "updSygl");
		baseDAOIbatis.executeBatch(sqlIds, param);
		re.setSuccess(true);
		re.setMsg("sysModule.qxgl.sygl.upd_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除首页
	 * @param param
	 * @return
	 */
	private ActionResult delSygl(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//首页ID（首页和角色首页同步删除）
		List<String> sqlIds = new ArrayList<String>();
		sqlIds.add(sqlId + "delJssy");
		sqlIds.add(sqlId + "delSygl");
		baseDAOIbatis.executeBatch(sqlIds, param);
		re.setSuccess(true);
		re.setMsg("sysModule.qxgl.sygl.del_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 日志log
	 * @param czid
	 * @param cznr
	 * @param czyId
	 * @param unitCode
	 * @return
	 */
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}

	public Map<String, Object> queryDetail(Map<String, Object> arg0,
			String arg1, String arg2, String arg3, String arg4, String arg5) {
		return null;
	}

	/**
	 * 取得首页
	 * @param param
	 * @return
	 */
	public Sygl getSy(Map<String, Object> param) {
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getSyById", param);
		if(ls != null && ls.size() > 0){
			return (Sygl)ls.get(0);
		}
		return null;
	}
	
	/**
	 * 判断名称是否重复
	 * @param param
	 * @return
	 */
	public ActionResult existingNm(Map<String, String> param) {
		ActionResult re = new ActionResult(false, "");
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "existingNm", param);
		if(ls != null && ls.size() > 0){
			re.setSuccess(true);
		}
		return re;
	}
	
}

