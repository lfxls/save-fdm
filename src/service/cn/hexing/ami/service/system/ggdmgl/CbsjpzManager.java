package cn.hexing.ami.service.system.ggdmgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;

/** 
 * @Description  抄表数据配置
 * @author  panc
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-12-20
 * @version AMI3.0 
 */
public class CbsjpzManager implements CbsjpzManagerInf {
	
	private static String sqlId = "cbsjpz.";
	private static String menuId = "54700";
	private BaseDAOIbatis baseDAOIbatis = null;
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 小类型下拉框
	 * @param param
	 * @return
	 */
	public List<Object> queryXlxDrop(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "xlxDrop", param);
	}
	
	/**
	 * 图形下拉框
	 * @param param
	 * @return
	 */
	public List<Object> queryTxDrop(Map<String, Object> param) {
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "txDrop", param);
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("BM", "");
		p.put("MC", I18nUtil.getText("common.select.all", (String)param.get(Constants.APP_LANG)));
		ls.add(0, p);
		return ls;
	}
	
	/**
	 * 抄表配置
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
		return baseDAOIbatis.getExtGrid(param, sqlId + "cbpz", start, limit, dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "selSjx", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 数据项映射
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySjx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "sjx", start, limit, dir, sort, isExcel);
	}

	/**
	 * 小类型
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryXlx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "xlx", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 图形类型
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTxlx(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "tx", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 取得对象
	 * @param param
	 * @return
	 */
	public Map<String, Object> getObject(Map<String, Object> param) {
		String sql = "";
		if("01".equals(param.get("rwlx"))) {
			sql = "getPz";
		} else if("02".equals(param.get("rwlx"))) {
			sql = "getSjx";
		} else if("03".equals(param.get("rwlx"))) {
			sql = "getXlx";
		} else if("04".equals(param.get("rwlx"))) {
			sql = "getTx";
		}
		
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + sql, param);
		if(null != ls && ls.size() > 0){
			return (Map<String, Object>)ls.get(0);
		}
		return null;
	}	
	
	/**
	 * 编辑操作
	 * @param czid
	 * @param param
	 * @return
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = null;
		String rwlx = (String)param.get("rwlx");
		if ((menuId + Constants.ADDOPT).equals(czid)) { // 新增
			if("01".equals(rwlx)) { // 数据项参数配置 
				re = insPz(param);
			} else if("02".equals(rwlx)) { // 数据项
				re = insSjx(param);
			} else if("03".equals(rwlx)) { // 小类型
				re = insXlx(param);
			} else if("04".equals(rwlx)) { // 图形类型
				re = insTxlx(param);
			}
		} else if ((menuId + Constants.DELOPT).equals(czid)) {// 删除
			if("01".equals(rwlx)){
				re = delPz(param);
			} else if("02".equals(rwlx)) { // 数据项
				re = delSjx(param);
			} else if("03".equals(rwlx)) { // 小类型
				re = delXlx(param);
			} else if("04".equals(rwlx)) { // 图形类型
				re = delTxlx(param);
			}
		}
	
		return re;
	}
	
	/**
	 * 新增配置
	 * @param param
	 * @return
	 */
	private ActionResult insPz(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "insPz", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.bccg", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除配置
	 * @param param
	 * @return
	 */
	private ActionResult delPz(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "delPz", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.sccg", param.get(Constants.APP_LANG));
		return re;
	}	
	
	/**
	 * 新增数据项
	 * @param param
	 * @return
	 */
	private ActionResult insSjx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "insSjx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.bccg", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除数据项
	 * @param param
	 * @return
	 */
	private ActionResult delSjx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		Map<String, String> obj = (Map<String, String>)baseDAOIbatis.queryForObject(sqlId + "isExists", param, HashMap.class);
		if(null != obj){
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.cbsjpz.scsb", param.get(Constants.APP_LANG), new String[]{(String)obj.get("MC")});
			return re;
		}
		
		baseDAOIbatis.insert(sqlId + "delSjx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.sccg", param.get(Constants.APP_LANG));
		return re;
	}
		
	/**
	 * 新增小类型
	 * @param param
	 * @return
	 */
	private ActionResult insXlx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "insXlx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.bccg", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除小类型
	 * @param param
	 * @return
	 */
	private ActionResult delXlx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		Map<String, String> obj = (Map<String, String>)baseDAOIbatis.queryForObject(sqlId + "isExists", param, HashMap.class);
		if(null != obj){
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.cbsjpz.scsb", param.get(Constants.APP_LANG), new String[]{(String)obj.get("MC")});
			return re;
		}
				
		baseDAOIbatis.insert(sqlId + "delXlx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.sccg", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 新增图形类型
	 * @param param
	 * @return
	 */
	private ActionResult insTxlx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "insTxlx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.bccg", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除图形类型
	 * @param param
	 * @return
	 */
	private ActionResult delTxlx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		Map<String, String> obj = (Map<String, String>)baseDAOIbatis.queryForObject(sqlId + "isExists", param, HashMap.class);
		if(null != obj){
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.cbsjpz.scsb", param.get(Constants.APP_LANG), new String[]{(String)obj.get("MC")});
			return re;
		}
		
		baseDAOIbatis.insert(sqlId + "delTxlx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.cbsjpz.sccg", param.get(Constants.APP_LANG));
		return re;
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}

}

