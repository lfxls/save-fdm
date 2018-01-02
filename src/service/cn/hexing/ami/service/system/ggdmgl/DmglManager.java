package cn.hexing.ami.service.system.ggdmgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.system.pojo.ggdmgl.Code;
import cn.hexing.ami.dao.system.pojo.ggdmgl.CodeCategory;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 系统编码管理
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-7
 * @version FDM2.0
 */
public class DmglManager implements DmglManagerInf{
	
	private static String sqlId = "dmgl.";
	
	private static String menuId = "54100";
	
	private static String dm_xx="dmxx";
	
	private static String dm_fl="dmfl";
	
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
		ActionResult re = new ActionResult();
		//编码的操作
		if(dm_xx.equals(param.get("type"))){
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addCode(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updateCode(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = deleteCode(param);
			}
		}else if(dm_fl.equals(param.get("type"))){
			//分类的操作
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addCodeSort(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updateCodeSort(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = deleteCodeSort(param);
			}
		}
		return re;
	}

   /**
    * 删除分类
    * @param param
    * @return
    */
	private ActionResult deleteCodeSort(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//查询代码分类下面是否有Code List
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "queryCodeUnderSort", param);
		if (ls!=null && ls.size()>0) {
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.dmgl.dmfl.containCodeUnderSort", param.get(Constants.APP_LANG));
		} else {
			baseDAOIbatis.delete(sqlId + "delDmFl", param);
			re.setSuccess(true);
			re.setMsg("sysModule.ggdmgl.dmgl.dmfl.delete_success", param.get(Constants.APP_LANG));
		}
		//批量删除语句
		//List<String> sqls = new ArrayList<String>();
		//处于安全考虑，删除代码分类的时候的，不再级联删除下面的代码
		//sqls.add(sqlId+"delDmXxByFl");
		//sqls.add(sqlId+"delDmFl");
		//baseDAOIbatis.executeBatchTransaction(sqls, param);	
		return re;
	}

	/**
	 * 更新分类
	 * @param param
	 * @return
	 */
	private ActionResult updateCodeSort(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//判断分类是否存在
		String cateCodeOld = StringUtil.isEmptyString(param.get("cateCodeOld"))?"":param.get("cateCodeOld");
		String cateCode = StringUtil.isEmptyString(param.get("cateCode"))?"":param.get("cateCode");
		//如果改了分类编码，需要判断分类编码是否存在
		if(!cateCodeOld.equals(cateCode)){
			CodeCategory codeCategory = this.queryDmFlById(param);
			if(codeCategory != null){
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dmgl.dmfl.exist", param.get(Constants.APP_LANG));
				return re;
			}
		}
		//更新分类
		List<String> sqls = new ArrayList<String>();
		sqls.add(sqlId+"updDmFl");		
		sqls.add(sqlId+"updDmXXFl");
		baseDAOIbatis.executeBatchTransaction(sqls, param);
		//修改分类编码且编码的isShow为YES才调用增量更新接口
		if(!cateCodeOld.equals(cateCode)){
			//调用数据更新接口saveBasicData
			List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qyCodeBySort", param);
			for (int i=0; i<ls1.size(); i++) {
				Code cd = (Code)ls1.get(i);
				//只有isShow为YES的编码，才需要调用增量更新接口
				if (cd.getIsShow().equals("1")) {
					String czyId = param.get(Constants.CURR_STAFFID);
					String lang = param.get(Constants.APP_LANG);
					List<Object> ls = new ArrayList<Object>();
					//修改代码分类，需要记录修改之前的老值
					cd.setCateCodeOld(cateCodeOld);
					cd.setCodeValueOld(cd.getCodeValue());
					ls.add(cd);
					CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_UPT, czyId, ls, lang);
				}	
			}
		}				
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.dmgl.dmfl.update_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 新增分类
	 * @param param
	 * @return
	 */
	private ActionResult addCodeSort(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//判断分类是否存在
		CodeCategory codeCategory = this.queryDmFlById(param);
		if(codeCategory != null){
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.dmgl.dmfl.exist", param.get(Constants.APP_LANG));
			return re;
		}
		//新增分类
		baseDAOIbatis.insert(sqlId + "inDmFl", param);
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.dmgl.dmfl.add_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除编码
	 * @param param
	 * @return
	 */
	private ActionResult deleteCode(Map<String, String> param) {
		ActionResult re = new ActionResult();			
		//调用数据更新接口saveBasicData
		Code cd = new Code();
		String czyId = param.get(Constants.CURR_STAFFID);
		String lang = param.get(Constants.APP_LANG);
		List<Object> ls = new ArrayList<Object>();
		//删除Code，cateCode新老值当成一样处理
		cd.setCateCode(param.get("cateCode"));
		cd.setCateCodeOld(param.get("cateCode"));
		//删除时，param里面没有cateName，需从数据库中查询
		List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qyCateName", param);
		String cateName = StringUtil.getValue(ls1.get(0));
		cd.setCateName(cateName);
		
		//删除时，param里面没有codeName，需从数据库中查询
		List<Object> ls2 = baseDAOIbatis.queryForList(sqlId + "qyCodeName", param);
		String codeName = StringUtil.getValue(ls2.get(0));
		cd.setCodeName(codeName);
		//删除时，param里面没有isShow，从数据库中查询
		List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "qyDelIsShow", param);
		String isShow = StringUtil.getValue(ls3.get(0));
		//删除Code，cateCode新老值当成一样处理
		cd.setCodeValue(param.get("value"));
		cd.setCodeValueOld(param.get("value"));
		cd.setDisp_sn(param.get("disp_sn"));
		cd.setIsShow(param.get("isShow"));
		cd.setLang(param.get("lang"));
		ls.add(cd);
		
		baseDAOIbatis.delete(sqlId+"delDmXx", param);
		if ("1".equals(isShow)) {
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_DEL, czyId, ls, lang);	
		}		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.dmgl.dmxx.delete_success", param.get(Constants.APP_LANG));
		return re;
	}

	/**
	 * 更新编码
	 * @param param
	 * @return
	 */
	private ActionResult updateCode(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//判断编码是否存在
		String valueOld = StringUtil.isEmptyString(param.get("valueOld"))?"":param.get("valueOld");
		String value = StringUtil.isEmptyString(param.get("value"))?"":param.get("value");
		//从数据库中获取修改之前的isShow
		List<Object> ls2 = baseDAOIbatis.queryForList(sqlId + "qyIsShow", param);
		String isShowOld = StringUtil.getValue(ls2.get(0));
		String isShowNew = param.get("isShow");
		//如果改了编码值，需要判断编码值是否存在
		if(!valueOld.equals(value)){
			Code code = this.queryDmXxById(param);
			if(code != null){
				re.setSuccess(false);
				re.setMsg("sysModule.ggdmgl.dmgl.dmxx.exist", param.get(Constants.APP_LANG));
				return re;
			}
		}
		//更新编码
		baseDAOIbatis.insert(sqlId + "updDmXx", param);	
		//修改之前isShow为YES，修改之后isShow为NO，则调用删除接口
		if (isShowOld.equals("1") && isShowNew.equals("0")) {
			Code cd = new Code();
			String czyId = param.get(Constants.CURR_STAFFID);
			String lang = param.get(Constants.APP_LANG);
			List<Object> ls = new ArrayList<Object>();
			//更新Code，cateCode不变
			cd.setCateCode(param.get("cateCode"));
			cd.setCateCodeOld(param.get("cateCode"));
			cd.setCateName(param.get("cateName"));
			cd.setCodeName(param.get("name"));
			//删除接口，这里的编码值应该为valueOld
			cd.setCodeValue(param.get("value"));
			cd.setCodeValueOld(param.get("valueOld"));
			cd.setDisp_sn(param.get("disp_sn"));
			cd.setIsShow(param.get("isShow"));
			cd.setLang(param.get("lang"));
			ls.add(cd);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_DEL, czyId, ls, lang);
		}
		//修改之前isShow为NO，修改之后isShow为Yes，则调用新增接口
		if (isShowOld.equals("0") && isShowNew.equals("1"))	{
			Code cd = new Code();
			String czyId = param.get(Constants.CURR_STAFFID);
			String lang = param.get(Constants.APP_LANG);
			List<Object> ls = new ArrayList<Object>();
			//更新Code，cateCode不变
			cd.setCateCode(param.get("cateCode"));
			cd.setCateCodeOld(param.get("cateCode"));
			cd.setCateName(param.get("cateName"));
			cd.setCodeName(param.get("name"));
			cd.setCodeValue(param.get("value"));
			cd.setCodeValueOld(param.get("valueOld"));
			cd.setDisp_sn(param.get("disp_sn"));
			cd.setIsShow(param.get("isShow"));
			cd.setLang(param.get("lang"));
			ls.add(cd);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls, lang);
		}
		//新增之前和修改之后isShow的值相等,且isShow=YES，则调用修改接口
		if (isShowOld.equals(isShowNew) && isShowOld.equals("1")) {
			Code cd = new Code();
			String czyId = param.get(Constants.CURR_STAFFID);
			String lang = param.get(Constants.APP_LANG);
			List<Object> ls = new ArrayList<Object>();
			//更新Code，cateCode不变
			cd.setCateCode(param.get("cateCode"));
			cd.setCateCodeOld(param.get("cateCode"));
			cd.setCateName(param.get("cateName"));
			cd.setCodeName(param.get("name"));
			//更新Code，需要记录更新之前的编码值
			cd.setCodeValue(param.get("value"));
			cd.setCodeValueOld(param.get("valueOld"));
			cd.setDisp_sn(param.get("disp_sn"));
			cd.setIsShow(param.get("isShow"));
			cd.setLang(param.get("lang"));
			ls.add(cd);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_UPT, czyId, ls, lang);
		}
		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.dmgl.dmxx.update_success", param.get(Constants.APP_LANG));
		return re;
	}

	/**
	 * 新增编码
	 * @param param
	 * @return
	 */
	private ActionResult addCode(Map<String, String> param) {
		ActionResult re = new ActionResult();
		//判断编码是否存在
		Code code = this.queryDmXxById(param);
		if(code != null){
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.dmgl.dmxx.exist", param.get(Constants.APP_LANG));
			return re;
		}
		//解决BUG:先添加了分类，然后又把分类删除了，再点击编码那里的添加竟然添加成功了，但是不知道添加到哪个分类下面去了
		List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qyExitsSort", param);
		if(ls1.size()==0) {
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.dmgl.dmxx.notExistSort", param.get(Constants.APP_LANG));
			return re;
		}
		
		//新增编码
		baseDAOIbatis.insert(sqlId + "inDmXx", param);
		//只有当ISSHOW=YES时，才需要调用增量更新接口saveBasicData
		String isShow = param.get("isShow");
		if ( isShow.equals("1")  ) {
			Code cd = new Code();
			String czyId = param.get(Constants.CURR_STAFFID);
			String lang = param.get(Constants.APP_LANG);
			List<Object> ls = new ArrayList<Object>();
			//新增Code，cateCode新老值当成一样处理
			cd.setCateCode(param.get("cateCode"));
			cd.setCateCodeOld(param.get("cateCode"));
			cd.setCateName(param.get("cateName"));
			cd.setCodeName(param.get("name"));
			//新增Code，cateValue新老值当成一样处理
			cd.setCodeValue(param.get("value"));
			cd.setCodeValueOld(param.get("value"));
			cd.setDisp_sn(param.get("disp_sn"));
			cd.setIsShow(param.get("isShow"));
			cd.setLang(param.get("lang"));
			ls.add(cd);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_CODE, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls, lang);			
		}		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.dmgl.dmxx.add_success", param.get(Constants.APP_LANG));
		return re;
	}
	
	//查询分类
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param,sqlId+"DMFL" ,start, limit, dir, sort, isExcel);
	}

	//查询编码
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param,sqlId+"DMXX" ,start, limit, dir, sort, isExcel);
	}

	public Code queryDmXxById(Map<String, String> param) {
		List<Object> result = baseDAOIbatis.queryForList(sqlId+"QueryDMXXByID", param);
		if(result!=null && result.size()>0){
			return (Code) result.get(0);
		}
		return null;
	}

	public CodeCategory queryDmFlById(Map<String, String> param) {
		List<Object> result = baseDAOIbatis.queryForList(sqlId+"QueryDMFLByID", param);
		if(result!=null && result.size()>0){
			return (CodeCategory) result.get(0);
		}
		return null;
	}
	/**
	 * 获取所有的Code
	 * @return
	 */
	@Override
	public List<Object> getAllCode() {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getAllCode", null);
		return list;
	}

}