package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.service.main.insMgt.InsUnPlanManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/** 
 * @Description  无序安装计划
 * @author  xcx
 * @Copyright 
 * @time：2016-04-18
 * @version FDM 
 */
public class InsUnPlanAction extends BaseAction implements DbWorksInf, QueryInf{

	private static final long serialVersionUID = -5840592198797250926L;
	private static Logger logger = Logger.getLogger(InsUnPlanAction.class.getName());
	private InsUnPlanManagerInf insUnPlanManager;
	
	private List<Object> stList; //工单状态列表
	private List<Object> btList; //业务类型列表
	private String status; //工单状态
	private String bussType; //业务类型
	private String operateType; //操作类型  新增，编辑，删除
	private String cno; //用户号
	private String cname; //用户名
	private String popid; //处理人ID
	private String popName; //处理人名称
	private String pid; //计划ID
	private String cnos; //新增用户派发时户号组合
	private String ecnos; //编辑用户派发时户号组合
	private String dcnos; //记录编辑时，删除的已选择用户户号组合
	private String woid; //工单ID
	private String tfId; //变压器ID
	private String tfName; //变压器名称
	private String tfIds; //新增变压器派发时变压器ID组合
	private String etfIds; //编辑变压器派发时变压器ID组合
	private String dispFlag; //派发标志：meter=表派发,dcu=集中器派发，col=采集器派发
	private String nodeIddw;
	private String fTime;//计划完成时间
	
	public InsUnPlanManagerInf getInsUnPlanManager() {
		return insUnPlanManager;
	}

	public void setInsUnPlanManager(InsUnPlanManagerInf insUnPlanManager) {
		this.insUnPlanManager = insUnPlanManager;
	}

	public List<Object> getStList() {
		return stList;
	}

	public void setStList(List<Object> stList) {
		this.stList = stList;
	}

	public List<Object> getBtList() {
		return btList;
	}

	public void setBtList(List<Object> btList) {
		this.btList = btList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCnos() {
		return cnos;
	}

	public void setCnos(String cnos) {
		this.cnos = cnos;
	}

	public String getWoid() {
		return woid;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public String getEcnos() {
		return ecnos;
	}

	public String getDcnos() {
		return dcnos;
	}

	public void setDcnos(String dcnos) {
		this.dcnos = dcnos;
	}

	public void setEcnos(String ecnos) {
		this.ecnos = ecnos;
	}

	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getTfId() {
		return tfId;
	}

	public void setTfId(String tfId) {
		this.tfId = tfId;
	}

	public String getTfIds() {
		return tfIds;
	}

	public void setTfIds(String tfIds) {
		this.tfIds = tfIds;
	}

	public String getEtfIds() {
		return etfIds;
	}

	public void setEtfIds(String etfIds) {
		this.etfIds = etfIds;
	}

	public String getDispFlag() {
		return dispFlag;
	}

	public void setDispFlag(String dispFlag) {
		this.dispFlag = dispFlag;
	}

	public String getNodeIddw() {
		return nodeIddw;
	}

	public void setNodeIddw(String nodeIddw) {
		this.nodeIddw = nodeIddw;
	}

	public String getfTime() {
		return fTime;
	}

	public void setfTime(String fTime) {
		this.fTime = fTime;
	}

	/**
	 * 表安装计划页面初始化
	 */
	public String init(){
		initData();
		return SUCCESS;
	}
	
	/**
	 * 集中器安装计划页面初始化
	 */
	public String initDcuUnP(){
		initData();
		return "dcuInsP";
	}
	
	/**
	 * 采集器安装计划页面初始化
	 */
	public String initColUnP(){
		initData();
		return "colInsP";
	}
	
	/**
	 * 用户页面初始化
	 */
	public String initCust(){
		return "cust";
	}
	
	/**
	 * 变压器页面初始化
	 */
	public String initTf(){
		return "tf";
	}
	
	/**
	 * 用户分配页面初始化
	 */
	public String initCustDisp(){
		if(!operateType.equals("01")) {
			cnos = insUnPlanManager.getDispCustByWoid(woid);
			Map<String,String> optInfoMap = insUnPlanManager.getOPTInfoByWOID(woid);
			popid = StringUtil.getValue(optInfoMap.get("OPTID"));
			popName = StringUtil.getValue(optInfoMap.get("OPTNAME"));
		}
		return "dispCust";
	}
	
	/**
	 * 变压器分配页面初始化
	 */
	public String initTfDisp(){
		if(!operateType.equals("01")) {
			if("dcu".equals(dispFlag)) {
				tfIds = insUnPlanManager.getDDispTfByWoid(woid);
			} else if ("col".equals(dispFlag)) {
				tfIds = insUnPlanManager.getCDispTfByWoid(woid);
			}
		}
		return "dispTf";
	}
	
	/**
	 * 页面数据初始化
	 * @return
	 */
	public String initData(){
		stList = CommonUtil.getCodeNumber("woStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		return SUCCESS;
	}
	
	/**
	 * 已选用户查询
	 * @return
	 */
	public String querySelCust(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cno", cno);
		param.put("cname", cname);
		param.put("nodeIddw", nodeIddw);
		if(!StringUtil.isEmptyString(cnos)) {
			param.put("cnos", cnos.split(","));
		} else {
			param.put("cnos", new String[]{""});
		}
		Map<String, Object> re = insUnPlanManager.querySelCust(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询变压器分配
	 * @return
	 */
	public String queryTfDisp(){
		Map<String, Object> param = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(tfIds)) {
			param.put("tfIds", tfIds.split(","));
		} else {
			param.put("tfIds", new String[]{""});
		}
		Map<String, Object> re = insUnPlanManager.querySelTf(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 可选用户查询
	 * @return
	 */
	public String queryUnSelCust(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("cname", cname);
		param.put("nodeIddw", nodeIddw);
		if(!StringUtil.isEmptyString(cnos)) {
			param.put("cnos", cnos.split(","));
		}
		if(!StringUtil.isEmptyString(dcnos)) {
			param.put("dcnos", dcnos.split(","));
		}
		Map<String, Object> re = insUnPlanManager.queryUnSelCust(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 变压器查询
	 * @return
	 */
	public String queryTf(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("tfId", tfId);
		param.put("tfName", tfName);
		param.put("nodeIddw", nodeIddw);
		if(!StringUtil.isEmptyString(tfIds)) {
			param.put("tfIds", tfIds.split(","));
		}
		Map<String, Object> re = insUnPlanManager.queryUnSelTf(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return insUnPlanManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		insUnPlanManager.dbLogger(czid, cznr, czyId, unitCode);
	}

	/**
	 * 查询表安装计划
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("woid", woid);
		param.put("status", status);
		param.put("popid", popid);
		Map<String, Object> re = insUnPlanManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	/**
	 * 查询采集器安装计划
	 */
	public String queryCInsP() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("tfName", tfName);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "QY");
		Map<String, Object> re = insUnPlanManager.queryCInsP(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询集中器安装计划
	 */
	public String queryDInsP() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("tfName", tfName);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "QY");
		Map<String, Object> re = insUnPlanManager.queryDInsP(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		return null;
	}
}