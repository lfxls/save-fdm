package cn.hexing.ami.web.action.system.ggdmgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.ggdmgl.CysjxpzManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;

/**
 * @Description 常用数据项Action
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-25
 * @version AMI3.0
 */
public class CysjxpzAction extends BaseAction implements DbWorksInf {
	private static final long serialVersionUID = 2254981732485127602L;
	/** 终端规约类型 **/
	private String gylx;
	/** 数据项编码 */
    private String sjxbm;
    /** 数据项名称 */
    private String sjxmc;
    /** 规约类型List **/
    private List<Object> gyLs = new ArrayList<Object>();
	
	private CysjxpzManagerInf cysjxpzManager;

	/**
	 * 页面初始化
	 * @return
	 */
	public String init(){
		gyLs = CommonUtil.getSupportProtocol(CommonUtil.getCode("zdgylx", this.getLang(), true));
		return SUCCESS;
	}
	
	public String showAddWin(){
		gyLs = CommonUtil.getSupportProtocol(CommonUtil.getCode("zdgylx", this.getLang(), false));
		return "cysjxadd";
	}
	
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gylx", gylx);
		param.put("sjxbm", sjxbm);
		param.put("sjxmc", sjxmc);
		param.put("appLang", getLang());
		
		Map<String, Object> re = cysjxpzManager.query(param, start, limit, dir,sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryDetail() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gylx", gylx);
		param.put("sjxbm", sjxbm);
		param.put("sjxmc", sjxmc);
		param.put("appLang", getLang());
		
		Map<String, Object> re = cysjxpzManager.queryDetail(param, start, limit, dir,sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = cysjxpzManager.doDbWorks(czid, param);
		return re;
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		cysjxpzManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	
	public String getGylx() {
		return gylx;
	}

	public void setGylx(String gylx) {
		this.gylx = gylx;
	}

	public String getSjxbm() {
		return sjxbm;
	}

	public void setSjxbm(String sjxbm) {
		this.sjxbm = sjxbm;
	}

	public String getSjxmc() {
		return sjxmc;
	}

	public void setSjxmc(String sjxmc) {
		this.sjxmc = sjxmc;
	}

	public List<Object> getGyLs() {
		return gyLs;
	}

	public void setGyLs(List<Object> gyLs) {
		this.gyLs = gyLs;
	}

	public void setCysjxpzManager(CysjxpzManagerInf cysjxpzManager) {
		this.cysjxpzManager = cysjxpzManager;
	}
}