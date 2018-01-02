package cn.hexing.ami.service.system.paramData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.paramData.ParamData;
import cn.hexing.ami.dao.common.pojo.paramData.ParamName;
import cn.hexing.ami.dao.common.pojo.paramData.TestParam;
import cn.hexing.ami.dao.common.pojo.paramData.TestParamName;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 参数方案管理Manager
 * @author wft
 * @Copyright2016 hexing Inc. All rights reserved
 * @time：2016-5-26
 * @versionFDM2.0
 */

public class ParamMgtManager implements ParamMgtManagerInf{

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="paramMgt.";
	private String menuId="54200";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		
		 Map<String, Object>  map=new HashMap<String, Object>();
		String pmType=StringUtil.getString(param.get("paramType"));
		if("2".equals(pmType)){
			map=baseDAOIbatis.getExtGrid(param, sqlId+"getTestParam", start, limit, dir, sort, isExcel);
		}
		else{
			map=baseDAOIbatis.getExtGrid(param, sqlId+"getParam", start, limit, dir, sort, isExcel);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		
		return baseDAOIbatis.getExtGrid(param, sqlId+"getCate", start, limit, dir, sort, isExcel);
	}
	public Map<String,Object> queryParamList(Map<String, Object> param, String start, String limit, String dir, String sort, String isExcel){
		
		 Map<String, Object>  map=new HashMap<String, Object>();
			String pmType=StringUtil.getString(param.get("paramType"));
			if("2".equals(pmType)){
				map=baseDAOIbatis.getExtGrid(param, sqlId+"queryTestParamList", start, limit, dir, sort, isExcel);
			}
			else{
				map=baseDAOIbatis.getExtGrid(param, sqlId+"queryParamList", start, limit, dir, sort, isExcel);
			}
		return map;

	}

	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re=new ActionResult();
		List<Object> ls=new ArrayList<Object>();
		List<Object> ls2=baseDAOIbatis.queryForList(sqlId+"getCateLs", param);
		if(ls2!=null&&ls2.size()>0){
			HashMap<String, String> map=(HashMap<String, String>) ls2.get(0);
			String cate_no=map.get("BM");
			param.put("cateId", cate_no);
		}
		else{
			String cate_no=(String)baseDAOIbatis.insert(sqlId+"insCate", param);
			param.put("cateId", cate_no);
		}
		
		if("2".equals(param.get("paramType"))){
			TestParam p=new TestParam();
			//测试项ID
			p.setTiid(param.get("testId"));
			//分类编码
			p.setCate_no(param.get("cateId"));
			
			//分类名称，从数据库中获取
//			//p.setCate_name(param.get("cate_name"));
//			List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qyCateName", param);
//			String cateName = StringUtil.getValue(ls1.get(0));
//			p.setCate_name(cateName);
			
			//内控版本号，从数据库中获取
			p.setVerid(param.get("verId"));
//			List<Object> ls2 = baseDAOIbatis.queryForList(sqlId + "qyVerId", param);
//			String verId = StringUtil.getValue(ls2.get(0));
//			p.setVerid(verId);
			
			ls.add(p);
		}
		else{
			
			ParamData p=new ParamData();
			p.setPm_type(param.get("paramType"));
			//分类编码
			p.setCate_no(param.get("cateId"));
			//参数项编码
			p.setObis(param.get("itemId"));
			
			//分类名称，从数据库中获取
//			//p.setCate_name(param.get("cate_name"));
//			List<Object> ls1 = baseDAOIbatis.queryForList(sqlId + "qyCateName", param);
//			String cateName = StringUtil.getValue(ls1.get(0));
//			p.setCate_name(cateName);
			
			//内控版本号，从数据库中获取
			p.setVerId(param.get("verId"));
//			List<Object> ls2 = baseDAOIbatis.queryForList(sqlId + "qyVerId", param);
//			String verId = StringUtil.getValue(ls2.get(0));
//			p.setVerId(verId);
			
			ls.add(p);
		}
		String czyId=param.get(Constants.CURR_STAFFID);
		String lang=param.get(Constants.APP_LANG);
		String status=param.get("status");
		String OldStatus=param.get("oldStatus");
				
		if((menuId+Constants.ADDOPT).equals(czid)){
			re=addParam(param);
			if("0".equals(status))
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_PARAMSOL, Constants.DATAUPDATE_OPTYPE_NEW,czyId, ls,lang);
		}
		else if((menuId+Constants.UPDOPT).equals(czid)){
			re=editParam(param);
			if(OldStatus.equals(status)&&("0").equals(OldStatus)){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_PARAMSOL, Constants.DATAUPDATE_OPTYPE_UPT,czyId, ls,lang);
			}
			else if((!OldStatus.equals(status))&&("0").equals(OldStatus)){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_PARAMSOL, Constants.DATAUPDATE_OPTYPE_DEL,czyId, ls,lang);
			}
			else if((!OldStatus.equals(status))&&("1").equals(OldStatus)){
				CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_PARAMSOL, Constants.DATAUPDATE_OPTYPE_NEW,czyId, ls,lang);
			}
			
		}
		else if((menuId+Constants.DELOPT).equals(czid)){
			re=delParam(param);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_PARAMSOL, Constants.DATAUPDATE_OPTYPE_DEL,czyId, ls,lang);
		}
		else if((menuId+Constants.ADDOPT_CATE).equals(czid)){
			re=addCate(param);
		}
		else if((menuId+Constants.UPDOPT_CATE).equals(czid)){
			re=editCate(param);
		}
		else if((menuId+Constants.DELOPT_CATE).equals(czid)){
			re=delCate(param);
		}
		return re;
	}
	
	private ActionResult delParam(Map<String, String> param) {
		ActionResult re=new ActionResult();
		baseDAOIbatis.delete(sqlId+"delParam", param);
		
		List<Object> ls=baseDAOIbatis.queryForList(sqlId+"getParamByCate", param);
		if(!(ls!=null&&ls.size()>0)){
			baseDAOIbatis.delete(sqlId+"delCate", param);
		}
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.delSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult editParam(Map<String, String> param) {
		
		ActionResult re=new ActionResult();
		List<Object> ls2=baseDAOIbatis.queryForList(sqlId+"existSort", param);
		if(ls2!=null&&ls2.size()>0){
			re.setSuccess(false);
			re.setMsg("sysModule.paramData.paramMgt.existSort", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.update(sqlId+"updParam", param);
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.editSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult addParam(Map<String, String> param) {
		ActionResult re=new ActionResult();
	
		List<Object> ls2=baseDAOIbatis.queryForList(sqlId+"existSort", param);
		if(ls2!=null&&ls2.size()>0){
			re.setSuccess(false);
			re.setMsg("sysModule.paramData.paramMgt.existSort", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.insert(sqlId+"insParam", param);
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.addSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult addCate(Map<String, String> param){
		ActionResult re=new ActionResult();
		param.put("cateName", param.get("cateName").trim());
		int num=(Integer) baseDAOIbatis.queryForObject(sqlId+"getCateCount", param,Integer.class);
		if(num>0){
			re.setSuccess(false);
			re.setMsg("sysModule.paramData.paramMgt.exists", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.insert(sqlId+"insCate", param);
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.addSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	private ActionResult editCate(Map<String, String> param){
		ActionResult re=new ActionResult();
		param.put("cateName", param.get("cateName").trim());
		int num=(Integer) baseDAOIbatis.queryForObject(sqlId+"getCateCount", param,Integer.class);
		if(num>0){
			re.setSuccess(false);
			re.setMsg("sysModule.paramData.paramMgt.exists", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.update(sqlId+"updCate", param);
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.editSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	private ActionResult delCate(Map<String, String> param){
		ActionResult re=new ActionResult();
		List<Object> ls=baseDAOIbatis.queryForList(sqlId+"getParamByCate", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("sysModule.paramData.paramMgt.ExistParam", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.delete(sqlId+"delCate", param);
		re.setSuccess(true);
		re.setMsg("sysModule.paramData.paramMgt.delSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		
	}

	@Override
	public List<Object> getCateLs(Map<String,Object> map) {
		return baseDAOIbatis.queryForList(sqlId+"getCateLs", map);
	}
	public List<Object> getVerLs(){
		return baseDAOIbatis.queryForList(sqlId+"getVerLs", null);
	}
	public List<Object>  getParam(Map<String, Object> param){
		String pmType=StringUtil.getValue(param.get("paramType"));
		if("2".equals(pmType)){
			return baseDAOIbatis.queryForList(sqlId+"getTestParamById", param);
		}
		else{
			return baseDAOIbatis.queryForList(sqlId+"getParamById", param);
		}
	}
	
	
	@Override
	public List<Object> getAllRead() {
		List<Object> allReadLs=new ArrayList<Object>();
		List<Object> readLs=baseDAOIbatis.queryForList(sqlId+"getReadLs", null);
		for(int i=0;i<readLs.size();i++){
			 HashMap<String,Object> map=new HashMap<String, Object>();
			 map=(HashMap<String, Object>) readLs.get(i);
			 List<Object> paramLs=baseDAOIbatis.queryForList(sqlId+"getAllRead", map);
	/*		 for(int j=0;j<paramLs.size();j++){
					ParamData pd=(ParamData)paramLs.get(j);
					map.put("obis", pd.getObis());
					List<ParamName> paramNameLs=new ArrayList<ParamName>();
					List<Object> nameLs=baseDAOIbatis.queryForList(sqlId+"getItemName", map);
					 for(int n=0;n<nameLs.size();n++){
						 paramNameLs.add((ParamName)nameLs.get(n));
					 }
					pd.setParamNameLs(paramNameLs);
				
				 }*/
				allReadLs.addAll(getParamDataLs(paramLs,map));
		}
		return allReadLs;
	}

	@Override
	public List<Object> getAllSet() {
		List<Object> allSetLs=new ArrayList<Object>();
		List<Object> setLs=baseDAOIbatis.queryForList(sqlId+"getSetLs", null);
		for(int i=0;i<setLs.size();i++){
			 HashMap<String,Object> map=new HashMap<String, Object>();
			 map=(HashMap<String, Object>) setLs.get(i);
			 List<Object> paramLs=baseDAOIbatis.queryForList(sqlId+"getAllSet", map);
			/* for(int j=0;j<paramLs.size();j++){
				ParamData pd=(ParamData)paramLs.get(j);
				map.put("obis", pd.getObis());
				List<ParamName> paramNameLs=new ArrayList<ParamName>();
				List<Object> nameLs=baseDAOIbatis.queryForList(sqlId+"getItemName", map);
				 for(int n=0;n<nameLs.size();n++){
					 paramNameLs.add((ParamName)nameLs.get(n));
				 }
				pd.setParamNameLs(paramNameLs);
				AllSetLs.add(pd);
			 }*/
			 allSetLs.addAll(getParamDataLs(paramLs,map));
			/*  ParamData pd =getParamData(paramLs,map);
			  allSetLs.add(pd);*/
		}
		return allSetLs;
	}

	@Override
	public List<Object> getAllTest() {
		 List<Object> testParamLs=new ArrayList<Object>();
		
		 List<Object> Ls=baseDAOIbatis.queryForList(sqlId+"getTestLs", null);
		 
		 for(int i=0;i<Ls.size();i++){
			 List<ParamData> paramDataLs=new ArrayList<ParamData>();
			 List<TestParamName> testParamNameLs=new ArrayList<TestParamName>();
			 HashMap<String,Object> map=new HashMap<String, Object>();
			 map=(HashMap<String, Object>) Ls.get(i);
			
			 List<Object> paramLs=baseDAOIbatis.queryForList(sqlId+"getAllTest", map);
			 List<Object> nameLs=baseDAOIbatis.queryForList(sqlId+"getTiName", map);
			 for(int n=0;n<nameLs.size();n++){
				 testParamNameLs.add((TestParamName)nameLs.get(n));
			 }
			/* for(int j=0;j<paramLs.size();j++){
				 List<ParamName> paramNameLs=new ArrayList<ParamName>();
				 ParamData pd=(ParamData) paramLs.get(j);
				 map.put("obis", pd.getObis());
				 map.put("PM_TYPE", "2");
				 nameLs=baseDAOIbatis.queryForList(sqlId+"getItemName", map);
				 for(int n=0;n<nameLs.size();n++){
					 paramNameLs.add((ParamName)nameLs.get(n));
				 }
				 pd.setParamNameLs(paramNameLs);
				 paramDataLs.add(pd);
			 }*/
			 paramDataLs=getParamDataLs(paramLs,map);
			 TestParam testParam=new TestParam();
			 testParam.setCate_no((String)map.get("CATE_NO"));
			 testParam.setM_model((String)map.get("M_MODEL"));
			 testParam.setVerid((String)map.get("VERID"));
			 testParam.setLevel((String)map.get("LEVEL"));
			 testParam.setSort(map.get("SORT").toString());
			 testParam.setStatus((String)map.get("STATUS"));
			 testParam.setTiid((String)map.get("TIID"));
			 testParam.setParamList(paramDataLs);
			 testParam.setTestParamNameList(testParamNameLs);
			 testParamLs.add(testParam);
		 }
		 
		 return testParamLs;
	}

	public List<ParamData> getParamDataLs( List<Object> paramLs, HashMap<String,Object> map){
		 List<ParamData> paramDataLs=new ArrayList<ParamData>();
		 for(int j=0;j<paramLs.size();j++){
			 List<ParamName> paramNameLs=new ArrayList<ParamName>();
			 ParamData pd=(ParamData) paramLs.get(j);
			 map.put("obis", pd.getObis());
			 List<Object> nameLs=baseDAOIbatis.queryForList(sqlId+"getItemName", map);
			 for(int n=0;n<nameLs.size();n++){
				 paramNameLs.add((ParamName)nameLs.get(n));
			 }
			 pd.setParamNameLs(paramNameLs);
			 paramDataLs.add(pd);
		 }
		return paramDataLs;
	}

	@Override
	public Map<String, Object> queryTestDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {

		 	return baseDAOIbatis.getExtGrid(param, sqlId+"queryTestDetail", start, limit, dir, sort, isExcel);

	}
}
