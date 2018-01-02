package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.insMgt.PlanQueryManagerInf;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * 
 * @Description  安装计划明细查询
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-13
 * @version FDM2.0
 */
public class PlanQueryAction extends BaseAction implements QueryInf{

	private static final long serialVersionUID = -1345660470338302274L;
	private String pid;//安装计划id
	private String woid;//工单id
	private String cno;//户号
	private String nmsn;//新表号
	private String bussType;//业务类型
	private String pType;//计划类型
	private String status;//安装计划状态
	private List<Object> stLs;//状态列表
	private List<Object> btLs;//业务类型列表
	private List<Object> ptLs;//计划类型列表
	PlanQueryManagerInf planQueryManager;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Object> getStLs() {
		return stLs;
	}
	public void setStLs(List<Object> stLs) {
		this.stLs = stLs;
	}
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public List<Object> getBtLs() {
		return btLs;
	}
	public void setBtLs(List<Object> btLs) {
		this.btLs = btLs;
	}
	public List<Object> getPtLs() {
		return ptLs;
	}
	public void setPtLs(List<Object> ptLs) {
		this.ptLs = ptLs;
	}
	public String getNmsn() {
		return nmsn;
	}
	public void setNmsn(String nmsn) {
		this.nmsn = nmsn;
	}
	public PlanQueryManagerInf getPlanQueryManager() {
		return planQueryManager;
	}
	public void setPlanQueryManager(PlanQueryManagerInf planQueryManager) {
		this.planQueryManager = planQueryManager;
	}
	/**
	 * 初始化页面
	 */
	public String init(){
		stLs=CommonUtil.getCodeNumber("planStatus", this.getLang(), "ASC", true);
		for(int i = 0; i < stLs.size(); i++) {
			Map<String,Object> plnStMap = (Map<String, Object>) stLs.get(i);
			String bm = StringUtil.getValue(plnStMap.get("BM"));
			if(Constants.PLN_STATUS_UNPAID.equals(bm) ||
					Constants.PLN_STATUS_PAID.equals(bm)) {
				stLs.remove(plnStMap);
				i--;
			}
		}
		ptLs=CommonUtil.getCodeNumber("fbDataType", this.getLang(), "ASC", true);
		btLs=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(pid)) {
			param.put("pid", pid);
		}
		if(!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(pType)) {
			param.put("pType", pType);
		}
		if(!StringUtil.isEmptyString(bussType)) {
			param.put("bussType", bussType);
		}
		if(!StringUtil.isEmptyString(woid)) {
			param.put("woid", woid);
		}
		if(!StringUtil.isEmptyString(cno)) {
			param.put("cno", cno);
		}
		if(!StringUtil.isEmptyString(nmsn)) {
			param.put("nmsn", nmsn);
		}
		param.put(Constants.APP_LANG, this.getLang());
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = planQueryManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String queryDetail() {
		return null;
	}
}
