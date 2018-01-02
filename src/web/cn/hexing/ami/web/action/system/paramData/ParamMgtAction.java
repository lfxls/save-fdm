package cn.hexing.ami.web.action.system.paramData;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.system.paramData.ParamMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;


/**
 * @Description 参数方案管理
 * @author wft
 * @Copyright2016 hexing Inc. All rights reserved
 * @time：2016-5-26
 * @versionFDM2.0
 */


public class ParamMgtAction extends BaseAction implements QueryInf,DbWorksInf {

	private ParamMgtManagerInf paramMgtManager;
	private String itemName,itemId,cateId,cateName,paramType,verName,verId,status,scale,unit,sortNum,value,ulimit,dlimit,level,czid,itemSort,bussType,oldStatus,tiName;
	private String testId,testName,svalue;
	//参数类型,状态，严重等级,分类,内控版本号,参数项分类,业务类型,结算日
	private List<Object> paramTypeLs,statusLs,levelLs,cateLs,verLs,itemSortLs,bussTypeLs,svalueLs;
	
	public String init(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put(Constants.APP_LANG, getLang());
		paramTypeLs=CommonUtil.getCode("pmType", getLang(), false);
		verLs=paramMgtManager.getVerLs();
		if(paramTypeLs!=null&&paramTypeLs.size()>0){
		paramType=StringUtil.isEmptyString(paramType)?(String)((Map<String, Object>) paramTypeLs.get(0)).get("BM"):paramType;
		}
		if(verLs!=null&&verLs.size()>0){
			verId=StringUtil.isEmptyString(verId)?(String)((Map<String, Object>) verLs.get(0)).get("BM"):verId;
		}
		map.put("paramType",paramType);
		map.put("verId", verId);
		map.put(Constants.APP_LANG,getLang());
		cateLs=paramMgtManager.getCateLs(map);
		if(cateLs!=null&&cateLs.size()>0){
			cateId=StringUtil.isEmptyString(cateId)?(String)((Map<String, Object>) cateLs.get(0)).get("BM"):cateId;
		}
		return SUCCESS;
	}
	
	public String initParam(){
		
		HashMap<String, Object> m=new HashMap();
		if("02".equals(czid)){
			m.put("verId", verId);
			m.put("itemId", itemId);
			m.put("paramType", paramType);
			m.put("testId",testId);
			//m.put("cate_name", cate_name);
			m.put(Constants.APP_LANG, getLang());
			List<Object> ls=paramMgtManager.getParam(m);
			m=(HashMap<String, Object>) ls.get(0);
			 if("2".equals(paramType)){
				level=StringUtil.getValue(m.get("LEVEL"));
				testId=StringUtil.getValue(m.get("TESTID"));
				testName=StringUtil.getValue(m.get("TESTNAME"));
			}
			 else{
				 value=StringUtil.getValue(m.get("VALUE"));
				 svalue=StringUtil.getValue(m.get("VALUE"));
				 itemName=StringUtil.getValue(m.get("ITEM_NAME"));
			 }
			status=StringUtil.getValue(m.get("STATUS"));
			oldStatus=StringUtil.getValue(m.get("STATUS"));
		}
		svalueLs=CommonUtil.getCode("bdate", getLang(), false);
		levelLs=CommonUtil.getCode("level", getLang(), false);
		statusLs=CommonUtil.getCode("pmStatus", getLang(), false);
		bussTypeLs=CommonUtil.getCode("bussTypes", getLang(), false);
		return "param_edit";
	}
	public String initCate(){
		paramTypeLs=CommonUtil.getCode("pmType", getLang(), false);
		verLs=paramMgtManager.getVerLs();
		paramType=StringUtil.isEmptyString(paramType)?(String)((Map<String, Object>) paramTypeLs.get(0)).get("BM"):paramType;
		
		if(!(verLs==null||verLs.size()==0)){
			verId=StringUtil.isEmptyString(verId)?(String)((Map<String, Object>) verLs.get(0)).get("BM"):verId;
		}
		return "cate";
	}
	public String initTestDetail(){
		return "testDetail";
	}
	public ActionResult ChangeCate(Map<String, Object> param, Util util){
		/*HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("pmType",paramType);
		map.put("verId", verId);
		map.put(Constants.APP_LANG,getLang());*/
		ActionResult re=new ActionResult();
		cateLs=paramMgtManager.getCateLs(param);
		re.setDataObject(cateLs);
		re.setSuccess(true);
		re.setMsg(JSONArray.fromObject(cateLs).toString());
		return re;
	}
	
	public String initEditCate(){
		paramTypeLs=CommonUtil.getCode("pmType", getLang(), false);
		verLs=paramMgtManager.getVerLs();
		statusLs=CommonUtil.getCode("pmStatus",getLang(), false);
		paramType=StringUtil.isEmptyString(paramType)?(String)((Map<String, Object>) paramTypeLs.get(0)).get("BM"):paramType;
		verId=StringUtil.isEmptyString(verId)?(String)((Map<String, Object>) verLs.get(0)).get("BM"):verId;
		
		return "cate_edit";
	}
	public String initParamList(){
		itemSortLs=CommonUtil.getCode("item_sort", getLang(), true);
		return "paramList";
	}
	
	
	public String queryTestDetail(){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(verId)){
			param.put("verId", verId);
		}
		if(!StringUtil.isEmptyString(testId)){
			param.put("tiid", testId);
		}
		Map<String,Object> re=paramMgtManager.queryTestDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return paramMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		paramMgtManager.dbLogger(czid, cznr, czyId, unitCode);
	}

	@Override
	public String query() throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(verId)){
			param.put("verId", verId);
		}
		if(!StringUtil.isEmptyString(paramType)){
			param.put("paramType", paramType);
		}
		Map<String,Object> re=paramMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	/**
	 * 获取参数分类列表
	 */
	@Override
	public String queryDetail() {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(verId)){
			param.put("verId", verId);
		}
		if(!StringUtil.isEmptyString(paramType)){
			param.put("paramType", paramType);
		}
		if(!StringUtil.isEmptyString(paramType)){
			param.put("cateName", cateName);
		}
		Map<String,Object> re=paramMgtManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	/**
	 * 获取参数列表
	 * @return
	 * @throws Exception
	 */
	public String queryParamList() throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(itemName)){
			param.put("itemName", itemName);
		}
		if(!StringUtil.isEmptyString(tiName)){
			param.put("tiName", tiName);
		}
		if(!StringUtil.isEmptyString(itemSort)){
			param.put("itemSort", itemSort);
		}
		if(!StringUtil.isEmptyString(paramType)){
			param.put("paramType", paramType);
		}
		if(!StringUtil.isEmptyString(verId)){
			param.put("verId", verId);
		}
		param.put("cateId", cateId);
		Map<String,Object> re=paramMgtManager.queryParamList(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	public ParamMgtManagerInf getParamMgtManager() {
		return paramMgtManager;
	}
	
	public void setParamMgtManager(ParamMgtManagerInf paramMgtManager) {
		this.paramMgtManager = paramMgtManager;
	}
	

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public String getVerId() {
		return verId;
	}
	public void setVerId(String verId) {
		this.verId = verId;
	}
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUlimit() {
		return ulimit;
	}

	public void setUlimit(String ulimit) {
		this.ulimit = ulimit;
	}

	public String getDlimit() {
		return dlimit;
	}

	public void setDlimit(String dlimit) {
		this.dlimit = dlimit;
	}

	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCzid() {
		return czid;
	}
	public void setCzid(String czid) {
		this.czid = czid;
	}
	public List<Object> getParamTypeLs() {
		return paramTypeLs;
	}
	public void setParamTypeLs(List<Object> paramTypeLs) {
		this.paramTypeLs = paramTypeLs;
	}
	public List<Object> getStatusLs() {
		return statusLs;
	}
	public void setStatusLs(List<Object> statusLs) {
		this.statusLs = statusLs;
	}
	public List<Object> getLevelLs() {
		return levelLs;
	}
	public void setLevelLs(List<Object> levelLs) {
		this.levelLs = levelLs;
	}
	public List<Object> getCateLs() {
		return cateLs;
	}
	public void setCateLs(List<Object> cateLs) {
		this.cateLs = cateLs;
	}
	public List<Object> getVerLs() {
		return verLs;
	}
	public void setVerLs(List<Object> verLs) {
		this.verLs = verLs;
	}

	public String getItemSort() {
		return itemSort;
	}

	public List<Object> getItemSortLs() {
		return itemSortLs;
	}

	public void setItemSort(String itemSort) {
		this.itemSort = itemSort;
	}

	public void setItemSortLs(List<Object> itemSortLs) {
		this.itemSortLs = itemSortLs;
	}

	public String getBussType() {
		return bussType;
	}

	public List<Object> getBussTypeLs() {
		return bussTypeLs;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public void setBussTypeLs(List<Object> bussTypeLs) {
		this.bussTypeLs = bussTypeLs;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getTestId() {
		return testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTiName() {
		return tiName;
	}

	public void setTiName(String tiName) {
		this.tiName = tiName;
	}

	public String getSvalue() {
		return svalue;
	}

	public List<Object> getSvalueLs() {
		return svalueLs;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

	public void setSvalueLs(List<Object> svalueLs) {
		this.svalueLs = svalueLs;
	}


	
}
