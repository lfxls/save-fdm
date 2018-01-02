package cn.hexing.ami.web.action.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.main.arcMgt.ModelMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

public class ModelMgtAction extends BaseAction implements QueryInf,DbWorksInf {
	//厂商ID，表型号，版本名称，内控版本号
	private String mfid,model, vername,verid,m_verid,czid;
	private List<Object> mfLs,modelLs;
	private ModelMgtManagerInf modelMgtManager;
	//初始化界面
	public String init(){
		mfLs=CommonUtil.getCode("MF", getLang(),true);
		mfid=((Map<String, String>) mfLs.get(0)).get("BM");
		modelLs=modelMgtManager.getModelLs(mfid,getLang());
		return SUCCESS; 
	}
	
	//初始化编辑界面
	public String initModel(){
		mfLs=CommonUtil.getCode("MF", getLang(),true);
		if("01".equals(czid)){
			mfid=((Map<String, String>) mfLs.get(0)).get("BM");
		}
		//获取表型列表
		modelLs=modelMgtManager.getModelLs(mfid,getLang());
		return "initModel";
	}
	
	@Override	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		
		String czyid=param.get(Constants.CURR_STAFFID);
		param.put("czyid", czyid);
		return modelMgtManager.doDbWorks(czid, param);
	}
	//刷新表型号
	public ActionResult getModels(Map<String, String> param, Util util){
		mfid=param.get("mfid");
		
		ActionResult re=new ActionResult();
		List<Object> ls=modelMgtManager.getModelLs(mfid,param.get(Constants.APP_LANG));
		re.setSuccess(true);
		re.setDataObject(ls);
		return	re;
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		modelMgtManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	//查询表型列表
	@Override
	public String query() throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(mfid)){
			param.put("mfid",mfid);
		}
		if(!StringUtil.isEmptyString(model)){
			param.put("model",model);
		}
		if(!StringUtil.isEmptyString(vername)){
			param.put("vername",vername);
		}
		if(!StringUtil.isEmptyString(verid)){
			param.put("verid",verid);
		}
		Map<String,Object> m=modelMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelMgtManagerInf getModelMgtManager() {
		return modelMgtManager;
	}

	public void setModelMgtManager(ModelMgtManagerInf modelMgtManager) {
		this.modelMgtManager = modelMgtManager;
	}

	public String getMfid() {
		return mfid;
	}

	public void setMfid(String mfid) {
		this.mfid = mfid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVername() {
		return vername;
	}

	public void setVername(String vername) {
		this.vername = vername;
	}

	public String getVerid() {
		return verid;
	}

	public void setVerid(String verid) {
		this.verid = verid;
	}

	public String getM_verid() {
		return m_verid;
	}

	public void setM_verid(String m_verid) {
		this.m_verid = m_verid;
	}

	public String getCzid() {
		return czid;
	}
	public void setCzid(String czid) {
		this.czid = czid;
	}
	public List<Object> getMfLs() {
		return mfLs;
	}

	public void setMfLs(List<Object> mfLs) {
		this.mfLs = mfLs;
	}

	public List<Object> getModelLs() {
		return modelLs;
	}

	public void setModelLs(List<Object> modelLs) {
		this.modelLs = modelLs;
	}

	
}
