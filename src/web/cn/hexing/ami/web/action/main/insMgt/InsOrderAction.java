package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.main.insMgt.InsOrderManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;
/**
 * @Description 工单生成
 * @author xcx
 * @Copyright 
 * @time：
 * @version FDM
 */
public class InsOrderAction extends BaseAction implements QueryInf, DbWorksInf{

	private static final long serialVersionUID = -3966513876113863299L;
	private String woid;//工单号
	private String status;//工单状态
	private String startDate;//开始日期
	private String endDate;//结束日期
	private List<Object> stLs;//状态列表
	private String cno;//户号
	private String bussType;//业务类型
	private String mType;//表计类型
	private String wir;//接线方式
	private String mMode;//表计模式
	private String msn;//表计局号
	private String omsns;//老表计局号组合
	private String domsns;//删除老表计局号组合
	private String nmsns;//新表计局号组合
	private String dnmsns;//删除新表计局号组合
	private String dcuM;//集中器型号
	private String colM;//采集器型号
	private String tfId;//变压器ID
	private String tfName;//变压器名称
	private String mszh;//选择进来的表安装计划组合信息
	private String mczh;//新增的表安装计划组合信息
	private String mdzh;//删除选择的表安装计划组合信息
	private String dszh;//选择进来的集中器安装计划组合信息
	private String dczh;//新增的集中器安装计划组合信息
	private String cszh;//选择进来的采集器安装计划组合信息
	private String cczh;//新增的采集器安装计划组合信息
	private List<Object> btList;//安装计划业务类型列表
	private List<Object> mTypeList;//表计类型列表
	private List<Object> wirList;//接线方式列表
	private List<Object> mModeList;//表计模式列表
	private List<Object> dcuMList;//集中器型号列表
	private List<Object> colMList;//采集器型号列表
	private String operateType;//操作类型
	private String nodeIddw;//单位ID
	private String opId;//操作员ID
	private String opName;//操作员名称
	private String type;//新老表标志 新表=new 老表=old
	private String popid;//处理人Id
	private String popName;//处理人名称
	private String fTime;//计划完成时间
	private String uid;//单位id
	private String oType;//工单类型
	private List<Object> oTList; //工单类型列表
	
	private String sszh;//选择进来的勘察计划信息
	private String sczh;//新增的勘察计划信息
	private String sdzh;//删除选择的勘察计划信息
	private String helpDocAreaId;
	
	InsOrderManagerInf insOrderManager;
	
	public String getHelpDocAreaId() {
		return helpDocAreaId;
	}

	public void setHelpDocAreaId(String helpDocAreaId) {
		this.helpDocAreaId = helpDocAreaId;
	}

	public String getWoid() {
		return woid;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<Object> getStLs() {
		return stLs;
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

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getOmsns() {
		return omsns;
	}

	public void setOmsns(String omsns) {
		this.omsns = omsns;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDomsns() {
		return domsns;
	}

	public void setDomsns(String domsns) {
		this.domsns = domsns;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public void setStLs(List<Object> stLs) {
		this.stLs = stLs;
	}

	public String getMszh() {
		return mszh;
	}

	public void setMszh(String mszh) {
		this.mszh = mszh;
	}

	public String getMczh() {
		return mczh;
	}

	public void setMczh(String mczh) {
		this.mczh = mczh;
	}

	public String getMdzh() {
		return mdzh;
	}

	public void setMdzh(String mdzh) {
		this.mdzh = mdzh;
	}

	public String getDszh() {
		return dszh;
	}

	public void setDszh(String dszh) {
		this.dszh = dszh;
	}

	public String getDczh() {
		return dczh;
	}

	public void setDczh(String dczh) {
		this.dczh = dczh;
	}

	public String getCszh() {
		return cszh;
	}

	public void setCszh(String cszh) {
		this.cszh = cszh;
	}

	public String getCczh() {
		return cczh;
	}

	public void setCczh(String cczh) {
		this.cczh = cczh;
	}

	public List<Object> getBtList() {
		return btList;
	}

	public void setBtList(List<Object> btList) {
		this.btList = btList;
	}

	public List<Object> getmTypeList() {
		return mTypeList;
	}

	public void setmTypeList(List<Object> mTypeList) {
		this.mTypeList = mTypeList;
	}

	public List<Object> getWirList() {
		return wirList;
	}

	public void setWirList(List<Object> wirList) {
		this.wirList = wirList;
	}

	public List<Object> getmModeList() {
		return mModeList;
	}

	public void setmModeList(List<Object> mModeList) {
		this.mModeList = mModeList;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getWir() {
		return wir;
	}

	public void setWir(String wir) {
		this.wir = wir;
	}

	public String getmMode() {
		return mMode;
	}

	public void setmMode(String mMode) {
		this.mMode = mMode;
	}

	public String getDcuM() {
		return dcuM;
	}

	public void setDcuM(String dcuM) {
		this.dcuM = dcuM;
	}

	public String getColM() {
		return colM;
	}

	public void setColM(String colM) {
		this.colM = colM;
	}

	public List<Object> getDcuMList() {
		return dcuMList;
	}

	public void setDcuMList(List<Object> dcuMList) {
		this.dcuMList = dcuMList;
	}

	public List<Object> getColMList() {
		return colMList;
	}

	public void setColMList(List<Object> colMList) {
		this.colMList = colMList;
	}

	public String getNmsns() {
		return nmsns;
	}

	public void setNmsns(String nmsns) {
		this.nmsns = nmsns;
	}

	public String getDnmsns() {
		return dnmsns;
	}

	public void setDnmsns(String dnmsns) {
		this.dnmsns = dnmsns;
	}

	public InsOrderManagerInf getInsOrderManager() {
		return insOrderManager;
	}

	public String getTfId() {
		return tfId;
	}

	public void setTfId(String tfId) {
		this.tfId = tfId;
	}

	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getNodeIddw() {
		return nodeIddw;
	}

	public void setNodeIddw(String nodeIddw) {
		this.nodeIddw = nodeIddw;
	}

	public String getPopid() {
		return popid;
	}

	public void setPopid(String popid) {
		this.popid = popid;
	}

	public String getPopName() {
		return popName;
	}

	public void setPopName(String popName) {
		this.popName = popName;
	}

	public String getfTime() {
		return fTime;
	}

	public void setfTime(String fTime) {
		this.fTime = fTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	

	public String getoType() {
		return oType;
	}

	public void setoType(String oType) {
		this.oType = oType;
	}

	public List<Object> getoTList() {
		return oTList;
	}

	public void setoTList(List<Object> oTList) {
		this.oTList = oTList;
	}

	public String getSszh() {
		return sszh;
	}

	public void setSszh(String sszh) {
		this.sszh = sszh;
	}

	public String getSczh() {
		return sczh;
	}

	public void setSczh(String sczh) {
		this.sczh = sczh;
	}

	public String getSdzh() {
		return sdzh;
	}

	public void setSdzh(String sdzh) {
		this.sdzh = sdzh;
	}

	public void setInsOrderManager(InsOrderManagerInf insOrderManager) {
		this.insOrderManager = insOrderManager;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return insOrderManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		insOrderManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	
	/**
     * 工单主页面初始
     */
	public String init(){
		stLs=CommonUtil.getCodeNumber("woStatus", this.getLang(), "ASC", true);
		oTList=CommonUtil.getCodeNumber("fbDataType", this.getLang(), "ASC", true);
		startDate = StringUtil.isEmptyString(startDate) ? DateUtil.getLastWeek() : startDate;
		endDate = StringUtil.isEmptyString(endDate) ? DateUtil.getToday(): endDate;
		
		Map<String, String> sysMap = (Map<String, String>) AppEnv
				.getObject(Constants.SYS_PARAMMAP);
		helpDocAreaId=sysMap.get("helpDocAreaId");
		
		return SUCCESS;
	}
	
	/**
	 * 工单生成页面初始化
	 * @return
	 */
	public String initCInsWO() {
		return "create_wo";
	}
	
	/**
	 * 工单编辑页面初始化
	 * @return
	 */
	public String initEditInsWO() {
		Map<String,String> optInfoMap = insOrderManager.getOPTInfoByWOID(woid);
		popid = StringUtil.getValue(optInfoMap.get("OPTID"));
		popName = StringUtil.getValue(optInfoMap.get("OPTNAME"));
		Map<String,String> param = new HashMap<String,String>();
		param.put("woid", woid);
		mszh = insOrderManager.getMInsPByWOID(param);
//		dszh = insOrderManager.getDInsPByWOID(param);
//		cszh = insOrderManager.getCInsPByWOID(param);
		sszh = insOrderManager.getSurPByWOID(param);
		return "create_wo";
	}
	
	/**
	 * 选择表安装计划页面初始化
	 * @return
	 */
	public String initMInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);
		return "sel_mInsP";
	}
	
	/**
	 * 新增表安装计划页面初始化
	 * @return
	 */
	public String initCMInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", false);
		bussType="0";
		mTypeList = CommonUtil.getCodeNumber("mType", this.getLang(), "ASC", true);  //false表示不加全选项
		wirList = CommonUtil.getCodeNumber("wiring", this.getLang(), "ASC", true);  //false表示不加全选项
		mModeList = CommonUtil.getCodeNumber("mode", this.getLang(), "ASC", true);  //false表示不加全选项
		return "add_mInsP";
	}
	
	/**
	 * 选择集中器安装计划页面初始化
	 * @return
	 */
	public String initDInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);
		return "sel_dInsP";
	}
	
	/**
	 * 新增集中器安装计划页面初始化
	 * @return
	 */
	public String initCDInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", false);
		bussType="0";
		dcuMList = CommonUtil.getCodeNumber("dcuModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "add_dInsP";
	}
	
	/**
	 * 选择采集器安装计划页面初始化
	 * @return
	 */
	public String initCInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);
		return "sel_cInsP";
	}
	
	/**
	 * 新增采集器安装计划页面初始化
	 * @return
	 */
	public String initCCInsP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", false);
		bussType="0";
		colMList = CommonUtil.getCodeNumber("clMode", this.getLang(), "ASC", true);  //false表示不加全选项
		return "add_cInsP";
	}
	
	//initSurP
	/**
	 * 选择勘察计划页面初始化
	 * @return
	 */
	public String initSurP() {
		btList=CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);
		return "sel_surP";
	}
	
	/**
	 * 新增勘察计划页面初始化
	 * @return
	 */
	public String initCSurP() {
		
		return "add_surP";
	}
	
	/**
	 * 初始化处理人选择页面
	 * @return
	 */
	public String initPOP() {
		return "selPOP";
	}
	
	/**
	 * 初始化选择表计的界面
	 * @return
	 */
	public String initMeter(){
		mTypeList = CommonUtil.getCodeNumber("mType", this.getLang(), "ASC", true);  //false表示不加全选项
		wirList = CommonUtil.getCodeNumber("wiring", this.getLang(), "ASC", true);  //false表示不加全选项
		mModeList = CommonUtil.getCodeNumber("mode", this.getLang(), "ASC", true);  //false表示不加全选项
		mType = StringUtil.isEmptyString("mType") ? "" : mType;
		wir = StringUtil.isEmptyString("wir") ? "" : wir;
		mMode = StringUtil.isEmptyString("mMode") ? "" : mMode;
		return "selMeter";
	}
	
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(woid)) {
			param.put("woid", woid);
		}
		if (!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if (!StringUtil.isEmptyString(startDate)) {
			param.put("startDate", startDate);
		}
		if (!StringUtil.isEmptyString(endDate)) {
			param.put("endDate", endDate);
		}
		if (!StringUtil.isEmptyString(popid)) {
			param.put("popid", popid);
		}
		if (!StringUtil.isEmptyString(oType)) {
			param.put("oType", oType);
		}
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = insOrderManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询未处理的表安装计划
	 * @return
	 */
	public String queryMPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("mszh", mszh);
		param.put("bussType", bussType);
		param.put("nodeIddw", nodeIddw);
		param.put("mdzh", mdzh);
		Map<String, Object> re = insOrderManager.queryMPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询未处理的集中器安装计划
	 * @return
	 */
	public String queryDPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("tfName", tfName);
		param.put("bussType", bussType);
		param.put("nodeIddw", nodeIddw);
		Map<String, Object> re = insOrderManager.queryDPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询未处理的采集器安装计划
	 * @return
	 */
	public String queryCPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("tfName", tfName);
		param.put("bussType", bussType);
		param.put("nodeIddw", nodeIddw);
		Map<String, Object> re = insOrderManager.queryCPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询未处理的勘察计划
	 * @return
	 */
	public String querySPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("sszh", sszh);
		param.put("bussType", bussType);
		param.put("nodeIddw", nodeIddw);
		param.put("sdzh", sdzh);
		Map<String, Object> re = insOrderManager.querySPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	
	/**
	 * 查询已添加到工单生成中的表安装计划
	 * @return
	 */
	public String queryMInsPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mszh", mszh);
		param.put("mczh", mczh);
		param.put("lang", this.getLang());
		Map<String,Object> re = insOrderManager.queryMInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询已添加到工单生成中的集中器安装计划
	 * @return
	 */
	public String queryDInsPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dszh", dszh);
		param.put("dczh", dczh);
		param.put("lang", this.getLang());
		Map<String,Object> re = insOrderManager.queryDInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询已添加到工单生成中的采集器安装计划
	 * @return
	 */
	public String queryCInsPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cszh", cszh);
		param.put("cczh", cczh);
		param.put("lang", this.getLang());
		Map<String,Object> re = insOrderManager.queryCInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询已添加到工单生成中的勘察计划
	 * @return
	 */
	public String querySurPlan(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sszh", sszh);
		param.put("sczh", sczh);
		param.put("lang", this.getLang());
		Map<String,Object> re = insOrderManager.querySurPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	

	/**
	 * 选择处理人
	 * @return
	 */
	public String queryPOP() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("opId", opId);
		param.put("opName", opName);
		param.put("nodeIddw", nodeIddw);
		Map<String,Object> re = insOrderManager.queryPOP(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询选择表计
	 * @return
	 */
	public String queryMeter(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("msn", msn);
		param.put("mType", mType);
		param.put("wir", wir);
		param.put("type", type);
		param.put("mMode", mMode);
		if(!StringUtil.isEmptyString(omsns)) {
			param.put("omsns", omsns.split(","));
		}
		if(!StringUtil.isEmptyString(domsns)) {
			param.put("domsns", domsns.split(","));
		}
		if(!StringUtil.isEmptyString(nmsns)) {
			param.put("nmsns", nmsns.split(","));
		}
		if(!StringUtil.isEmptyString(dnmsns)) {
			param.put("dnmsns", dnmsns.split(","));
		}
		if(!type.equals("new")) {
			DatabaseUtil.nodeFilter(param, this.getUnitCode(), "dw",
					this.getUnitCode(), this.getCzyid(), this.getFwbj(),
					this.getBm(), "M");
		} else {
			param.put("uid", uid);
		}
		Map<String, Object> re = insOrderManager.queryMeter(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	//撤销已派工的工单
	public ActionResult revokeWorkOrder(Map<String,String> param,Util util){
		ActionResult re = new ActionResult(false,"");
		param.put("status", "3");
		re = insOrderManager.revokeWorkOrder(param);
		return re;
	}
}
