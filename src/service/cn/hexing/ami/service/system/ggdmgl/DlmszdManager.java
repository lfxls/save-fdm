package cn.hexing.ami.service.system.ggdmgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;


/** 
 * @Description  代码管理
 * @author  luofan
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-16
 * @version AMI3.0 
 */
public class DlmszdManager implements DlmszdManagerInf{
	
	private static String sqlId = "dlmszd.";
	
	private static String menuId = "54300";
	
	private static String type_DLMS_SUB="DLMS_SUB";
	
	private static String type_DLMS_PARAMS="DLMS_PARAMS";
	
	private static String type_DLMS_DATA_SORT="DLMS_DATA_SORT";
	
	BaseDAOIbatis baseDAOIbatis = null;
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = null;
		if(type_DLMS_SUB.equals(param.get("type"))){
			re = doDbWorks_DLMS_SUB(czid, param);
		}else if(type_DLMS_DATA_SORT.equals(param.get("type"))){
			//数据项分类的操作
			re = doDbWorks_DLMS_DATA_SORT(czid, param);
		}else if(type_DLMS_PARAMS.equals(param.get("type"))){
			//数据项的操作
			re = doDbWorks_DLMS_PARAMS(czid, param);
		}
		return re;
	}

	private ActionResult doDbWorks_DLMS_SUB(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		String status = param.get("status");
		if((menuId + Constants.ADDOPT).equals(czid)) { 
			//判断规约编码是否存在
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsSubByCode", param, Integer.class);
			if(i>0){
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_sub.add.fail_duplicateCode", param.get(Constants.APP_LANG));
			}else{
				if("1".equals(status)) disableAllDlmsSub(param); //使用新增的规约需要修改所有其他规约的状态为“不使用”
				baseDAOIbatis.update(sqlId + "inDlmsSub", param);
				//增加DLMS规约继承关系
				if(!StringUtil.isEmptyString(param.get("base_protocol"))){
					baseDAOIbatis.update(sqlId + "inDlmsRelation", param);
				}
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_sub.add_success", param.get(Constants.APP_LANG));
			}
		} else if((menuId + Constants.UPDOPT).equals(czid)){ 
			if("1".equals(status)) disableAllDlmsSub(param);//使用新增的规约需要先修改所有规约的状态为“不使用”
			String lang_exist_flag = param.get("lang_exist_flag");
			if("1".equals(lang_exist_flag)){ //当前语言的数据已存在，则修改
				baseDAOIbatis.update(sqlId + "updDlmsSub", param);
			}else{
				baseDAOIbatis.update(sqlId + "inDlmsSub", param);
			}
			baseDAOIbatis.update(sqlId + "updDlmsSubStatus", param); //同步当前规约的状态
			re.setSuccess(true);
			re.setMsg("sysModule.ggdmgl.dlmszd.dlms_sub.update_success", param.get(Constants.APP_LANG));
		} else if((menuId + Constants.DELOPT).equals(czid)){ 
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsDataSort", param, Integer.class);
			if(i==0){
				baseDAOIbatis.update(sqlId+"delDlmsDataSortByGy", param); //删除DLMS数据项分类
				baseDAOIbatis.update(sqlId+"delDlmsRelation", param); //删除DLMS规约继承关系
				baseDAOIbatis.update(sqlId+"delDlmsSub", param); //删除DLMS规约
				
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_sub.delete_success", param.get(Constants.APP_LANG));
			}else{
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_sub.delete_hasdatasort", param.get(Constants.APP_LANG));
			}
		}
		return re;
	}
	private void disableAllDlmsSub(Map<String, String> param){
		baseDAOIbatis.update(sqlId + "diableAllDlmsSub", param);
	}
	private ActionResult doDbWorks_DLMS_DATA_SORT(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if((menuId + Constants.ADDOPT).equals(czid)) { 
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsDataSortByCode", param, Integer.class);
			if(i>0){
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_data_sort.add.fail_duplicateCode", param.get(Constants.APP_LANG));
			}else{
				baseDAOIbatis.update(sqlId + "inDlmsDataSort", param);
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_data_sort.add_success", param.get(Constants.APP_LANG));
			}
		} else if((menuId + Constants.UPDOPT).equals(czid)){ 
			String lang_exist_flag = param.get("lang_exist_flag");
			if("1".equals(lang_exist_flag)){ //当前语言的数据已存在，则修改
				baseDAOIbatis.update(sqlId + "updDlmsDataSort", param);
			}else{
				baseDAOIbatis.update(sqlId + "inDlmsDataSort", param);
			}
			re.setSuccess(true);
			re.setMsg("sysModule.ggdmgl.dlmszd.dlms_data_sort.update_success", param.get(Constants.APP_LANG));
		} else if((menuId + Constants.DELOPT).equals(czid)){ 
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsParams", param, Integer.class);
			if(i==0){
				baseDAOIbatis.update(sqlId+"delDlmsDataSort", param); //删除DLMS数据项分类
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_data_sort.delete_success", param.get(Constants.APP_LANG));
			}else{
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_data_sort.delete_hasparams", param.get(Constants.APP_LANG));
			}
		}
		return re;
	}
	private ActionResult doDbWorks_DLMS_PARAMS(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if((menuId + Constants.ADDOPT).equals(czid)) { 
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsParamsByID", param, Integer.class);
			if(i>0){
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_params.add.fail_duplicate", param.get(Constants.APP_LANG));
			}else{
				baseDAOIbatis.update(sqlId + "inDlmsParams", param);
				baseDAOIbatis.update(sqlId + "inDlmsParamsName", param);
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_params.add_success", param.get(Constants.APP_LANG));
			}
		} else if((menuId + Constants.UPDOPT).equals(czid)){
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countDlmsParamsByID", param, Integer.class);
			baseDAOIbatis.update(sqlId + "updDlmsParams", param);
			String lang_exist_flag = param.get("lang_exist_flag");
			if("1".equals(lang_exist_flag)){ //当前语言的数据已存在，则修改
				baseDAOIbatis.update(sqlId + "updDlmsParamsName", param);
			}else{
				baseDAOIbatis.update(sqlId + "inDlmsParamsName", param);
			}
			re.setSuccess(true);
			re.setMsg("sysModule.ggdmgl.dlmszd.dlms_params.update_success", param.get(Constants.APP_LANG));
		} else if((menuId + Constants.DELOPT).equals(czid)){ 
			int i = (Integer)baseDAOIbatis.queryForObject(sqlId+"countArrayStruct", param, Integer.class);
			if(i==0){
				baseDAOIbatis.update(sqlId+"delDlmsParamsName", param); //删除DLMS数据项分类
				baseDAOIbatis.update(sqlId+"delDlmsParams", param); //删除DLMS数据项分类
				re.setSuccess(true);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_params.delete_success", param.get(Constants.APP_LANG));
			}else{
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dlmszd.dlms_params.delete_hasparams", param.get(Constants.APP_LANG));
			}
		}
		return re;
	}
	/**
	 * 查询所有的DLMS规约
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsSub(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId+"queryDlmsSub", param);
	}
	/**
	 * 根据规约ID查询数据项分类
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsDataSort(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId+"queryDlmsDataSort", param);
	}
	
	/**
	 * 根据规约ID、数据项名称查询所有的DLMS数据项
	 * @param param
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param , sqlId+"dlmsParams", start, limit, dir, sort, isExcel );
	}

	//查询代码值明细
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	/**
	 * 根据数据项ID查询数据项信息
	 * @param param
	 * @return
	 */
	public List<Object> getDlmsParams(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId+"dlmsParamsQuery", param);
	}

	public String getParentDlmsSub(Map<String, String> param) {
		return (String)baseDAOIbatis.queryForObject(sqlId+"getParentDlmsSub", param, String.class);
	}


}
