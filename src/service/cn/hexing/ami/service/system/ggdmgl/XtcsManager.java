package cn.hexing.ami.service.system.ggdmgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsFl;
import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsXx;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;


/**
 * @Description 系统配置Service
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-14
 * @version AMI3.0
 */
public class XtcsManager implements XtcsManagerInf{
	
	private static String sqlId = "xtcs.";
	
	private static String menuId = "54200";
	
	private static String cs_xx="csxx";
	
	private static String cs_fl="csfl";
	
	BaseDAOIbatis baseDAOIbatis = null;
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
     * 查询系统参数分类的grid的内容
     */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "csfl" ,start, limit, dir, sort, isExcel);
	}

	/**
     * 查询系统参数详细信息的grid的内容
     */
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "csxx" ,start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 根据 id查询系统参数分类
	 * @param param
	 * @return
	 */
	public CsFl queryCsFlById(Map<String, Object> param) {
		List<Object> result = baseDAOIbatis.queryForList(sqlId + "QueryCsflByID", param);
		if(result!=null && result.size()>0){
			return (CsFl) result.get(0);
		}
		return null;
	}
	
	/**
	 * 根据id查询系统参数详细信息
	 * @param param
	 * @return
	 */
	public CsXx queryCsXxById(Map<String, Object> param) {
		List<Object> result = baseDAOIbatis.queryForList(sqlId + "QueryCsxxByID", param);
		if(result!=null && result.size()>0){
			return (CsXx) result.get(0);
		}
		return null;
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		//系统参数信息的操作
		if(cs_xx.equals(param.get("type"))){
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addCsxx(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updateCsxx(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = deleteCsxx(param);
			}
		}else if(cs_fl.equals(param.get("type"))){
			//系统参数类别的操作
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addCsflSort(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updateCsfl(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = deleteCsfl(param);
			}
		}
		return re;
	}
	
	/**
	 * 修改系统参数分类
	 * @param param
	 * @return
	 */
	private ActionResult updateCsfl(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//修改系统参数类型
		baseDAOIbatis.update(sqlId + "updCsfl", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.update_sort_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 修改系统参数
	 * @param param
	 * @return
	 */
	private ActionResult updateCsxx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//修改系统参数
		baseDAOIbatis.update(sqlId + "updCsxx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.update_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 添加系统参数类型
	 * @param param
	 * @return
	 */
	private ActionResult addCsflSort(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//新增系统参数类型
		baseDAOIbatis.insert(sqlId + "inCsfl", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.add_sort_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 添加系统参数值
	 * @param param
	 * @return
	 */
	private ActionResult addCsxx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//新增系统参数值
		baseDAOIbatis.insert(sqlId + "inCsxx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.add_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除系统参数值
	 * @param param
	 * @return
	 */
	private ActionResult deleteCsxx(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.delete(sqlId + "delCsxx", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.delete_success", param.get(Constants.APP_LANG));
		return re;
	}
	
   /**
    * 删除系统参数类型
    * @param param
    * @return
    */
	private ActionResult deleteCsfl(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//增加批量删除语句
		List<String> sqls = new ArrayList<String>();
		sqls.add(sqlId + "delCsxxByFl");
		sqls.add(sqlId + "delCsfl");
		baseDAOIbatis.executeBatchTransaction(sqls, param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.xtcs.delete_sort_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
}
