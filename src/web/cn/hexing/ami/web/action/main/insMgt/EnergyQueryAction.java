package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.insMgt.EnergyQueryManagerInf;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * 
 * @Description  老表电量查询
 * @author  xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-9-26
 * @version FDM2.0
 */
public class EnergyQueryAction extends BaseAction implements QueryInf{

	private static final long serialVersionUID = -3465381657815428847L;
	private String cno;//户号
	private String tfName;//变压器
	private String omsn;//老表号
	private String startDate;//反馈开始时间
	private String endDate;//反馈结束时间
	private String dlsjx;//电量数据项
	private String uid;//单位代码
	EnergyQueryManagerInf energyQueryManager;
	
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getTfName() {
		return tfName;
	}
	public void setTfName(String tfName) {
		this.tfName = tfName;
	}
	public String getOmsn() {
		return omsn;
	}
	public void setOmsn(String omsn) {
		this.omsn = omsn;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDlsjx() {
		return dlsjx;
	}
	public void setDlsjx(String dlsjx) {
		this.dlsjx = dlsjx;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public EnergyQueryManagerInf getEnergyQueryManager() {
		return energyQueryManager;
	}
	public void setEnergyQueryManager(EnergyQueryManagerInf energyQueryManager) {
		this.energyQueryManager = energyQueryManager;
	}
	
	/**
	 * 初始化页面
	 */
	public String init(){
		startDate = StringUtil.isEmptyString(startDate) ? DateUtil.getYesterday() : startDate;
		endDate = StringUtil.isEmptyString(endDate) ? DateUtil.getToday() : endDate;
		List<Object> sjxList = CommonUtil.getPCodeOrderBySN("oev", this.getLang(), "ASC", false);
		dlsjx = getPageField(sjxList); 
		nodeId = StringUtil.isEmptyString(nodeId) ? this.getUnitCode() : nodeId;
		nodeType = StringUtil.isEmptyString(nodeType) ? "dw" : nodeType;
		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) ? this.getUnitCode() : nodeDwdm;
		nodeText = StringUtil.isEmptyString(nodeText) ? this.getUnitName() : nodeText;
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(cno)) {
			param.put("cno", cno);
		}
		if(!StringUtil.isEmptyString(tfName)) {
			param.put("tfName", tfName);
		}
		if(!StringUtil.isEmptyString(omsn)) {
			param.put("omsn", omsn);
		}
		if(!StringUtil.isEmptyString(startDate)) {
			param.put("startDate", startDate);
		}
		if(!StringUtil.isEmptyString(endDate)) {
			param.put("endDate", endDate);
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = energyQueryManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String queryDetail() {
		return null;
	}
	
	public String getPageField(List<Object> ls) {
		StringBuilder sb = new StringBuilder();
		String sjxbm = CommonUtil.getAppConfigItemValue("old_msn_energy_sjx");
		if(null != ls && ls.size() > 0) { // 代码考虑共通
			for(int i = 0; i < ls.size(); i++){
				Map<String, Object> map = (Map<String, Object>)ls.get(i);
				String bm = StringUtil.getValue(map.get("BM"));
				String mc = StringUtil.getValue(map.get("MC")) + "(kWh)";
				if(sjxbm.indexOf(bm) != -1) {
					sb.append(";");
					sb.append(bm).append(",").append(mc);
				}
			}
			return sb.toString().substring(1);
		}
		return "";
	}
	
	/**
	 * 初始化选择变压器的界面
	 * @return
	 */
	public String initTf(){
		return "selTf";
	}
	
	/**
	 * 查询变压器树
	 * @return
	 */
	public String queryTf() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tfName", tfName);
		param.put("uid", uid);
		Map<String, Object> re = energyQueryManager.queryTf(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
}
