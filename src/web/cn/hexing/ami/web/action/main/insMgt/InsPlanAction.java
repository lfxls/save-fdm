package cn.hexing.ami.web.action.main.insMgt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.main.insMgt.InsPlanManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description  安装计划
 * @author  xcx
 * @Copyright 
 * @time：2016-04-18
 * @version FDM 
 */
public class InsPlanAction extends BaseAction implements DbWorksInf{

	private static final long serialVersionUID = 2179841857273946121L;
	private static Logger logger = Logger.getLogger(InsPlanAction.class.getName());
	private InsPlanManagerInf insPlanManager; //安装计划
	
	private List<Object> stList; //安装计划状态列表
	private List<Object> btList; //业务类型列表
	private String msn; //表计局号
	private String omsn; //新表计局号
	private String nmsn; //老表计局号
	private String dsn; //集中器号
	private String ndsn; //新集中器号
	private String odsn; //老集中器号
	private String csn; //采集器号
	private String ncsn; //新采集器号
	private String ocsn; //老采集器号
	private String status; //安装计划状态
	private String bussType; //业务类型
	private String operateType; //操作类型  新装  更换 拆除
	private List<Object> mTypeList; //表计类型列表
	private List<Object> wirList; //接线方式列表
	private List<Object> mModeList; //表计模式列表
	private List<Object> dcuMList; //集中器型号列表
	private List<Object> colMList; //采集器型号列表
	private String cno; //用户号
	private String cname; //用户名
	private String addr; //用户地址
	private String uid, uname; //单位代码 ，单位名称
	private String pid; //计划ID
	private String mType, wir, mMode; //表计类型,  接线方式, 表计模式
	private String dcuM; //集中器型号
	private String colM; //采集器型号
	private String tfId; //变压器ID
	private String tfName; //变压器名称
	private String devAddr; //变压器地址
	private String insType; //安装类型
	private Map<String, String> singleMap;
	private String selTfFlag;//选择变压器标志：表选择变压器，集中器选择变压器或采集器选择变压器
	private String type;//新老表标志，新表为new，老表为old
	
	private String helpDocAreaId;
	
	private File addMInsPFile; //新装表文件
	private File chanMInsPFile; //换表文件
	private File remMInsPFile; //拆表文件
	
	private File addDInsPFile; //新装集中器文件
	private File chanDInsPFile; //换集中器文件
	private File remDInsPFile; //拆集中器文件
	
	private File addCInsPFile; //新装集中器文件
	private File chanCInsPFile; //换集中器文件
	private File remCInsPFile; //拆集中器文件
	
	private String otype;//工单类型
	

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}

	public InsPlanManagerInf getInsPlanManager() {
		return insPlanManager;
	}

	public void setInsPlanManager(InsPlanManagerInf insPlanManager) {
		this.insPlanManager = insPlanManager;
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

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getOmsn() {
		return omsn;
	}

	public void setOmsn(String omsn) {
		this.omsn = omsn;
	}

	public String getNmsn() {
		return nmsn;
	}

	public void setNmsn(String nmsn) {
		this.nmsn = nmsn;
	}

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getNdsn() {
		return ndsn;
	}

	public void setNdsn(String ndsn) {
		this.ndsn = ndsn;
	}

	public String getOdsn() {
		return odsn;
	}

	public void setOdsn(String odsn) {
		this.odsn = odsn;
	}

	public String getCsn() {
		return csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public String getNcsn() {
		return ncsn;
	}

	public void setNcsn(String ncsn) {
		this.ncsn = ncsn;
	}

	public String getOcsn() {
		return ocsn;
	}

	public void setOcsn(String ocsn) {
		this.ocsn = ocsn;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getDevAddr() {
		return devAddr;
	}

	public void setDevAddr(String devAddr) {
		this.devAddr = devAddr;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Map<String, String> getSingleMap() {
		return singleMap;
	}

	public void setSingleMap(Map<String, String> singleMap) {
		this.singleMap = singleMap;
	}

	public File getAddMInsPFile() {
		return addMInsPFile;
	}

	public void setAddMInsPFile(File addMInsPFile) {
		this.addMInsPFile = addMInsPFile;
	}

	public File getChanMInsPFile() {
		return chanMInsPFile;
	}

	public void setChanMInsPFile(File chanMInsPFile) {
		this.chanMInsPFile = chanMInsPFile;
	}

	public File getRemMInsPFile() {
		return remMInsPFile;
	}

	public void setRemMInsPFile(File remMInsPFile) {
		this.remMInsPFile = remMInsPFile;
	}

	public File getAddDInsPFile() {
		return addDInsPFile;
	}

	public void setAddDInsPFile(File addDInsPFile) {
		this.addDInsPFile = addDInsPFile;
	}

	public File getChanDInsPFile() {
		return chanDInsPFile;
	}

	public void setChanDInsPFile(File chanDInsPFile) {
		this.chanDInsPFile = chanDInsPFile;
	}

	public File getRemDInsPFile() {
		return remDInsPFile;
	}

	public void setRemDInsPFile(File remDInsPFile) {
		this.remDInsPFile = remDInsPFile;
	}

	public File getAddCInsPFile() {
		return addCInsPFile;
	}

	public void setAddCInsPFile(File addCInsPFile) {
		this.addCInsPFile = addCInsPFile;
	}

	public File getChanCInsPFile() {
		return chanCInsPFile;
	}

	public void setChanCInsPFile(File chanCInsPFile) {
		this.chanCInsPFile = chanCInsPFile;
	}

	public File getRemCInsPFile() {
		return remCInsPFile;
	}

	public void setRemCInsPFile(File remCInsPFile) {
		this.remCInsPFile = remCInsPFile;
	}

	public String getSelTfFlag() {
		return selTfFlag;
	}

	public void setSelTfFlag(String selTfFlag) {
		this.selTfFlag = selTfFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	public String getHelpDocAreaId() {
		return helpDocAreaId;
	}

	public void setHelpDocAreaId(String helpDocAreaId) {
		this.helpDocAreaId = helpDocAreaId;
	}

	/**
	 * 页面初始化
	 */
	public String init(){
		initData();
		return SUCCESS;
	}
	
	/**
	 * 安装计划——集中器初始页面
	 * @return
	 */
	public String initDInsP(){
		initData();
		dcuMList = CommonUtil.getCodeNumber("dcuModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "dcuPlan";
	}
	
	/**
	 * 安装计划——采集器初始页面
	 * @return
	 */
	public String initCInsP(){
		initData();
		colMList = CommonUtil.getCodeNumber("clModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "colPlan";
	}
	
	/**
	 * 页面数据初始化
	 * @return
	 */
	public String initData(){
		stList = CommonUtil.getCodeNumber("planStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		for(int i = 0; i < stList.size(); i++) {
			Map<String,Object> plnStMap = (Map<String, Object>) stList.get(i);
			String bm = StringUtil.getValue(plnStMap.get("BM"));
			if(Constants.PLN_STATUS_UNPAID.equals(bm) ||
					Constants.PLN_STATUS_PAID.equals(bm)) {
				stList.remove(plnStMap);
				i--;
			}
		}
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		//初始化节点
		nodeText = StringUtil.isEmptyString(nodeText) == true ? this.getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) == true ? this.getUnitCode() : nodeId;
		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) == true ? this.getUnitCode() : nodeDwdm; //this.getNodeDwdm();
		nodeType = StringUtil.isEmptyString(nodeType) == true ? "dw" : nodeType; 
		return SUCCESS;
	}
	
	/**
	 * 安装计划表计查询
	 * @return
	 */
	public String queryMInsPlan(){
//		if (StringUtil.isEmptyString(nodeId)){
//			return null;
//		}
			
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = insPlanManager.queryMInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 安装计划集中器查询
	 * @return
	 */
	public String queryDInsPlan(){
		if (StringUtil.isEmptyString(nodeId)){
			return null;
		}
			
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("dcuM", dcuM);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "TF");
		Map<String, Object> re = insPlanManager.queryDInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;	
	}
	
	/**
	 * 采集器安装计划查询
	 * @return
	 */
	public String queryCInsPlan(){
		if (StringUtil.isEmptyString(nodeId)){
			return null;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("colM", colM);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "TF");
		Map<String, Object> re = insPlanManager.queryCInsPlan(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;	
	}
	
	/**
	 * 初始化新增表安装计划
	 * @return
	 */
	public String initMInsPAdd(){
		initMInsPOPData(true);
		
		return "meterPlan_op";
	}
	
	/**
	 * 初始化新增集中器安装计划
	 * @return
	 */
	public String initDInsPAdd(){
		dcuMList = CommonUtil.getCodeNumber("dcuModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "dcuPlan_op";
	}
	
	/**
	 * 初始化新增采集器安装计划
	 * @return
	 */
	public String initCInsPAdd(){
		colMList = CommonUtil.getCodeNumber("clModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "colPlan_op";
	}
	
	
	/**
	 * 初始化编辑表安装计划
	 * @return
	 */
	public String initMInsPEdit(){
		initMInsPOPData(true);
		singleMap = insPlanManager.getMInsPByPid(pid, bussType);
		return "meterPlan_op";
	}
	
	/**
	 * 初始化编辑集中器安装计划
	 * @return
	 */
	public String initDInsPEdit(){
		dcuMList = CommonUtil.getCodeNumber("dcuModel", this.getLang(), "ASC", true);  //false表示不加全选项
		singleMap = insPlanManager.getDInsPByPid(pid, bussType);
		return "dcuPlan_op";
	}
	
	/**
	 * 初始化编辑采集器安装计划
	 * @return
	 */
	public String initCInsPEdit(){
		colMList = CommonUtil.getCodeNumber("clModel", this.getLang(), "ASC", true);  //false表示不加全选项
		singleMap = insPlanManager.getCInsPByPid(pid, bussType);
		return "colPlan_op";
	}
	
	/**
	 * 初始表安装计划导入
	 * @return
	 */
	public String initMInsPImport() {
		return "mInsPlnImp";
	}
	
	/**
	 * 初始集中器安装计划导入
	 * @return
	 */
	public String initDInsPImport() {
		return "dInsPlnImp";
	}
	
	/**
	 * 初始采集器安装计划导入
	 * @return
	 */
	public String initCInsPImport() {
		return "cInsPlnImp";
	}
	
	/**
	 * 初始化选择用户的界面
	 * @return
	 */
	public String initCust(){
		otype = StringUtil.isEmptyString(otype) ? "" : otype;
		return "selCust";
	}
	
	/**
	 * 表计操作页面下拉框初始
	 * @return
	 */
	public void initMInsPOPData(boolean allSeelct){
		mTypeList = CommonUtil.getCodeNumber("mType", this.getLang(), "ASC", allSeelct);  //false表示不加全选项
		wirList = CommonUtil.getCodeNumber("wiring", this.getLang(), "ASC", allSeelct);  //false表示不加全选项
		mModeList = CommonUtil.getCodeNumber("mode", this.getLang(), "ASC", allSeelct);  //false表示不加全选项
	}
	
	/**
	 * 查询用户
	 * @return
	 */
	public String queryCust()  {
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, String> sysMap = (Map<String, String>) AppEnv
				.getObject(Constants.SYS_PARAMMAP);
		String helpDocAreaId=sysMap.get("helpDocAreaId");
		if("CameroonEneo".equals(helpDocAreaId)&&"0".equals(otype)){
			param.put("oType", otype);
		}
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("cname", cname);
		param.put("nodeId", this.getUnitCode());
		param.put("nodeType","dw");
		
		DatabaseUtil.nodeFilter(param, this.getUnitCode(), "dw",
				this.getUnitCode(), this.getCzyid(), this.getFwbj(),
				this.getBm(), "C");
		Map<String, Object> re = insPlanManager.queryCust(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
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
//		DatabaseUtil.nodeFilter(param, dwdm, "dw",
//				dwmc, this.getCzyid(), this.getFwbj(),
//				this.getBm(), "TF");
		Map<String, Object> re = insPlanManager.queryTf(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 初始化选择表计的界面
	 * @return
	 */
	public String initMeter(){
		initMInsPOPData(true);
		mType = StringUtil.isEmptyString(mType) ? "" : mType;
		wir = StringUtil.isEmptyString(wir) ? "" : wir;
		mMode = StringUtil.isEmptyString(mMode) ? "" : mMode;
		return "selMeter";
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
		param.put("mMode", mMode);
		param.put("type", type);
		if(!type.equals("new")) {
			DatabaseUtil.nodeFilter(param, this.getUnitCode(), "dw",
					this.getUnitCode(), this.getCzyid(), this.getFwbj(),
					this.getBm(), "M");
		} else {
			param.put("uid", uid);
		}
		Map<String, Object> re = insPlanManager.queryMeter(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 初始化选择集中器的界面
	 * @return
	 */
	public String initDcu(){
		dcuMList = CommonUtil.getCodeNumber("dcuModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "selDcu";
	}
	
	/**
	 * 查询选择集中器
	 * @return
	 */
	public String queryDcu(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("dsn", dsn);
		param.put("dcuM", dcuM);
		param.put("nodeId", this.getUnitCode());
		param.put("nodeType","dw");
		DatabaseUtil.nodeFilter(param, this.getUnitCode(), "dw",
				this.getUnitCode(), this.getCzyid(), this.getFwbj(),
				this.getBm(), "D");
		Map<String, Object> re = insPlanManager.queryDcu(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 初始化选择采集器的界面
	 * @return
	 */
	public String initCol(){
		colMList = CommonUtil.getCodeNumber("clModel", this.getLang(), "ASC", true);  //false表示不加全选项
		return "selCol";
	}
	
	/**
	 * 查询选择集中器
	 * @return
	 */
	public String queryCol(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("csn", csn);
		param.put("colM", colM);
		param.put("nodeId", this.getUnitCode());
		param.put("nodeType","dw");
		DatabaseUtil.nodeFilter(param, this.getUnitCode(), "dw",
				this.getUnitCode(), this.getCzyid(), this.getFwbj(),
				this.getBm(), "C");
		Map<String, Object> re = insPlanManager.queryCol(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 校验换表计或者集中器,采集器是否已存在的未反馈的安装计划记录
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult validateReplace(Map<String,String> param, Util util){
		ActionResult re = new ActionResult();
		re = insPlanManager.validateReplace(param, util);
		return re;
	}
	
	/**
	 * 安装计划导入初始
	 * @return
	 */
	public String initImport(){
		return "insPlan_imp";
	}
	
	/**
	 * 档案导入
	 * @return
	 */
	public String importExcel(){
		ActionResult result = new ActionResult();
		String lang = getLang();
		try {
			Map<String, String> param = new HashMap<String, String>();
			String czyid =(String) session.getAttribute(Constants.CURR_STAFFID);
			param.put("CURR_STAFFID", czyid);
			
			//新装表
			if (addMInsPFile != null) {
				FileInputStream fis = new FileInputStream(addMInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_ADD_METER, lang);
				//删除临时文件
				addMInsPFile.delete();
			}
			
			//换表
			if (chanMInsPFile != null) {
				FileInputStream fis = new FileInputStream(chanMInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_CHANGE_METER, lang);
				//删除临时文件
				chanMInsPFile.delete();
			}
			
			//拆表
			if (remMInsPFile != null) {
				FileInputStream fis = new FileInputStream(remMInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_REMOVE_METER, lang);
				//删除临时文件
				remMInsPFile.delete();
			}
			
			//新集中器
			if (addDInsPFile != null) {
				FileInputStream fis = new FileInputStream(addDInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_ADD_DCU, lang);
				//删除临时文件
				addDInsPFile.delete();
			}
			
			//换集中器
			if (chanDInsPFile != null) {
				FileInputStream fis = new FileInputStream(chanDInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_CHANGE_DCU, lang);
				//删除临时文件
				chanDInsPFile.delete();
			}
			
			//拆集中器
			if (remDInsPFile != null) {
				FileInputStream fis = new FileInputStream(remDInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_REMOVE_DCU, lang);
				//删除临时文件
				remDInsPFile.delete();
			}
			
			//新采集器
			if (addCInsPFile != null) {
				FileInputStream fis = new FileInputStream(addCInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_ADD_COLL, lang);
				//删除临时文件
				addCInsPFile.delete();
			}
			
			//换采集器
			if (chanCInsPFile != null) {
				FileInputStream fis = new FileInputStream(chanCInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_CHANGE_COLL, lang);
				//删除临时文件
				chanCInsPFile.delete();
			}
			
			//拆采集器
			if (remCInsPFile != null) {
				FileInputStream fis = new FileInputStream(remCInsPFile);
				result = insPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_REMOVE_COLL, lang);
				//删除临时文件
				remCInsPFile.delete();
			}
			
		} catch (Exception e) {
			logger.error(getText("mainModule.insMgt.plan.import.failed") + StringUtil.getExceptionDetailInfo(e));
			result.setSuccess(false);
			result.setMsg("mainModule.insMgt.plan.import.failed", getLang());
		} finally {
			addMInsPFile = null; //新装表文件
			chanMInsPFile = null; //换表文件
			remMInsPFile = null; //拆表文件
			
			addDInsPFile = null; //新装集中器文件
			chanDInsPFile = null; //换集中器文件
			remDInsPFile = null; //拆集中器文件
			
			addCInsPFile = null; //新装集中器文件
			chanCInsPFile = null; //换集中器文件
			remCInsPFile = null; //拆集中器文件
		}
		//输出信息
		response.setContentType("text/html; charset=UTF-8"); 
		try {
			response.getWriter().print("{success:'" + StringUtil.getString(result.isSuccess()) 
									+ "', msgType:'" + StringUtil.getString(result.getDataObject())
									+ "', errMsg:'" + StringUtil.getString(result.getMsg()) + "'}");
		} catch (IOException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		} 
		return null;
	}
	
	/**
	 * 数据库事务操作
	 */
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return insPlanManager.doDbWorks(czid, param);
	}

	/**
	 * 数据库操作日志
	 */
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		insPlanManager.dbLogger(czid, cznr, czyId, unitCode);
	}
}