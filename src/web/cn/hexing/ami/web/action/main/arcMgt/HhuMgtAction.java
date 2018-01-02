package cn.hexing.ami.web.action.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.arcMgt.HhuMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description HHU管理Action
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class HhuMgtAction extends BaseAction implements QueryInf, DbWorksInf {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1159313089504176838L;
	HhuMgtManagerInf hhuMgtManager = null;
	
	public void setHhuMgtManager(HhuMgtManagerInf hhuMgtManager) {
		this.hhuMgtManager = hhuMgtManager;
	}
	
	private String hhuid, model, bcap, appvn, wh_date, status, data_init;
	private List<Object> hhuModel, hhuStatus, dataInitSts;
	private String operateType;
	
	public String init() {
		hhuModel = CommonUtil.getCode("HHUModel", getLang(), true);
		hhuStatus = CommonUtil.getCode("HHUStatus", getLang(), true);
		dataInitSts = CommonUtil.getCode("dataInitSts", getLang(), true);
		return SUCCESS;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return hhuMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		hhuMgtManager.dbLogger(czid, cznr, czyId, unitCode);
		
	}
	
	/**
	 * HHU管理界面，查询HHU列表
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hhuid", hhuid);
		param.put("model", model);
		param.put("status", status);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * HHU选择界面，查询HHU列表
	 */
	public String querySel() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hhuid", hhuid);
		param.put("model", model);
		param.put("status", status);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuMgtManager.querySel(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String initHhuMgtEdit() {
		hhuModel = CommonUtil.getCode("HHUModel", getLang(), true);
		hhuStatus = CommonUtil.getCode("HHUStatus", getLang(), true);
		if("edit".equals(operateType)) {
			//Map<String, Object> param = new HashMap<String, Object>();
			//param.put("hhuid", hhuid);
			Map<String, Object> re = hhuMgtManager.getHhu(hhuid);
			hhuid = (String) re.get("HHUID");
			model = (String) re.get("MODEL");
			status = (String) re.get("STATUS");
			bcap = (String) re.get("BCAP");
			appvn = (String) re.get("APPVN");
		}
		return "hhuMgt_edit";
	}
	
	public String initHhuSel() {
		return "initHhuSel";
	}
	

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHhuid() {
		return hhuid;
	}

	public void setHhuid(String hhuid) {
		this.hhuid = hhuid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBcap() {
		return bcap;
	}

	public void setBcap(String bcap) {
		this.bcap = bcap;
	}

	public String getAppvn() {
		return appvn;
	}

	public void setAppvn(String appvn) {
		this.appvn = appvn;
	}

	public String getWh_date() {
		return wh_date;
	}

	public void setWh_date(String wh_date) {
		this.wh_date = wh_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getHhuModel() {
		return hhuModel;
	}

	public void setHhuModel(List<Object> hhuModel) {
		this.hhuModel = hhuModel;
	}

	public List<Object> getHhuStatus() {
		return hhuStatus;
	}

	public void setHhuStatus(List<Object> hhuStatus) {
		this.hhuStatus = hhuStatus;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getData_init() {
		return data_init;
	}

	public void setData_init(String data_init) {
		this.data_init = data_init;
	}

	public List<Object> getDataInitSts() {
		return dataInitSts;
	}

	public void setDataInitSts(List<Object> dataInitSts) {
		this.dataInitSts = dataInitSts;
	}

}
