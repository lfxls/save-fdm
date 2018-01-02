package cn.hexing.ami.web.action.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.arcMgt.SimMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description sim卡管理Action
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-25
 * @version FDM2.0
 */

public class SimMgtAction extends BaseAction implements QueryInf, DbWorksInf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1529255159056426800L;
	SimMgtManagerInf simMgtManager = null;
	

	public void setSimMgtManager(SimMgtManagerInf simMgtManager) {
		this.simMgtManager = simMgtManager;
	}
	private String simno, simsn, msp;
	private List<Object> txfwsLs;
	private String operateType;
	
	public String init() {
		//System.out.println("1");
		txfwsLs = CommonUtil.getCode("txfwsLs", getLang(), true);
		return SUCCESS;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return simMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		simMgtManager.dbLogger(czid, cznr, czyId, unitCode);	
	}
	
	/**
	 * sim卡管理界面，查询sim卡列表
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("simno", simno);
		param.put("simsn", simsn);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = simMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * sim卡选择界面，查询sim卡列表
	 */
	public String querySel() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("simno", simno);
		param.put("simsn", simsn);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = simMgtManager.querySel(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String initSimMgtEdit() {
		txfwsLs = CommonUtil.getCode("txfwsLs", getLang(), true);
		if("edit".equals(operateType)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("simno", simno);
			Map<String, Object> re = simMgtManager.getSim(param);
			simsn = (String) re.get("SIMSN");
			msp = (String) re.get("MSP");
		}
		return "simMgt_edit";
	}
	
	public String initSimSel() {
		return "initSimSel";
	}
	
	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSimno() {
		return simno;
	}

	public void setSimno(String simno) {
		this.simno = simno;
	}

	public String getSimsn() {
		return simsn;
	}

	public void setSimsn(String simsn) {
		this.simsn = simsn;
	}

	public String getMsp() {
		return msp;
	}

	public void setMsp(String msp) {
		this.msp = msp;
	}

	public List<Object> getTxfwsLs() {
		return txfwsLs;
	}

	public void setTxfwsLs(List<Object> txfwsLs) {
		this.txfwsLs = txfwsLs;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
}
