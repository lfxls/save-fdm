package cn.hexing.ami.web.action.system.rzgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.rzgl.TxrzManagerInf;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/** 
 * @Description  通讯日志
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-15
 * @version AMI3.0 
 */
public class TxrzAction extends BaseAction implements QueryInf {

	private static final long serialVersionUID = 1327607044793823505L;
	//操作员ID，终端局号，数据项名称，开始时间，结束时间
	private String czybm, zdjh, csxmc, kssj, jssj  , czlx;
	private List<Object> czlxList = null;//操作类型
	private TxrzManagerInf txrzManager = null;
	
	/**
	 * 初始化
	 */
	public String init() {
		kssj = DateUtil.getYesterday();
		jssj = DateUtil.getToday();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		czlxList = txrzManager.getCzlxList(param);
		return SUCCESS;
	}
	
	/**
	 * 查询通讯日志
	 */
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("nodeId", nodeId);
		
		if (!StringUtil.isEmptyString(czybm))
			param.put("czybm", czybm.trim());
		if (!StringUtil.isEmptyString(zdjh))
			param.put("zdjh", zdjh.trim());
		
		param.put("csxmc", csxmc);
		param.put("kssj", kssj);
		param.put("jssj", jssj);
		param.put("czlx", czlx);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "qy");
		Map<String, Object> re =  txrzManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}

	public String queryDetail() {
		return null;
	}
	
	public String getCzybm() {
		return czybm;
	}

	public void setCzybm(String czybm) {
		this.czybm = czybm;
	}

	public String getZdjh() {
		return zdjh;
	}
	public void setZdjh(String zdjh) {
		this.zdjh = zdjh;
	}
	public String getCsxmc() {
		return csxmc;
	}
	public void setCsxmc(String csxmc) {
		this.csxmc = csxmc;
	}
	public String getKssj() {
		return kssj;
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj;
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public TxrzManagerInf getTxrzManager() {
		return txrzManager;
	}
	public void setTxrzManager(TxrzManagerInf txrzManager) {
		this.txrzManager = txrzManager;
	}


	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public List<Object> getCzlxList() {
		return czlxList;
	}

	public void setCzlxList(List<Object> czlxList) {
		this.czlxList = czlxList;
	}

}

