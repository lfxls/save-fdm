package cn.hexing.ami.web.action.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.arcMgt.MeterMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 表计管理
 * @author wft
 * @Copyright2016 hexing Inc. All rights reserved
 * @time：2016-5-26
 * @versionFDM2.0
 */

public class MeterMgtAction extends BaseAction implements QueryInf,DbWorksInf {
	
	private MeterMgtManagerInf meterMgtManager;
	private String uid,msn,mtype,mfid,mode,status,czid,wiring,mboxid,lon,lat,ct,pt,sealid,tfid,simno,uname,tfname,nodeTextdw,nodeIddw,nodeTypedw,dataSrc;
	private String matCode;//物料编码

	private List<Object> mtypeLs,mfLs,modeLs,statusLs,wiringLs,ctLs,ptLs,dataSrcLs;
	//初始化界面
	public String init(){
		mtypeLs=CommonUtil.getCode("mType",getLang(), true);
		mfLs=CommonUtil.getCode("MF",getLang(), true);
		modeLs=CommonUtil.getCode("mode",getLang(), true);
		statusLs=CommonUtil.getCode("mStatus",getLang(), true);
		dataSrcLs=CommonUtil.getCode("dataSrc",getLang(), true);
		return SUCCESS;
	}

	public String initTree(){
		return "tree";
	}
	public String initTF(){
		return "TF";
	}
	//初始化编辑界面
	public String initMeter(){
		mtypeLs=CommonUtil.getCode("mType",getLang(), true);
		mfLs=CommonUtil.getCode("MF",getLang(), true);
		modeLs=CommonUtil.getCode("mode",getLang(), true);
		statusLs=CommonUtil.getCode("mStatus",getLang(), true);
		wiringLs=CommonUtil.getCode("wiring",getLang(), true);
		ctLs=CommonUtil.getCode("ct",getLang(), false);
		ptLs=CommonUtil.getCode("pt",getLang(), false);
		if("02".equals(czid)){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("msn", msn);
			Map<String, Object> re=meterMgtManager.getMeter(param);
			nodeIddw=(String) re.get("UID");
			nodeTextdw=(String) re.get("NODETEXTDW");
			mode=(String) re.get("MODE");
			mtype=(String) re.get("M_TYPE");
			wiring=(String) re.get("WIRING");
			mboxid=(String) re.get("MBOXID");
			lon=(String) re.get("LON");
			lat=(String) re.get("LAT");
			mfid=(String )re.get("MFID");
			ct=(String) re.get("CT");
			pt=(String) re.get("PT");
			sealid=(String) re.get("SEALID");
			tfid=(String) re.get("TFID");
			simno=(String) re.get("SIMNO");
			tfname=(String) re.get("TFNAME");
			status=(String) re.get("STATUS");
			matCode=(String) re.get("MATCODE");
		}
		
		return "initMeter";
	}
	//初始化导入界面
	public String initImport(){
		return "initimport";
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return meterMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		meterMgtManager.dbLogger(czid, cznr, czyId, unitCode);
		
	}
	//查询表计列表
	@Override
	public String query() throws Exception {
		if (StringUtil.isEmptyString(nodeId))
			return null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appLang", getLang());
		if (!StringUtil.isEmptyString(nodeId)) {
		param.put("uid", nodeId);
		}
		if (!StringUtil.isEmptyString(msn)) {
		param.put("msn", msn);
		}
		if (!StringUtil.isEmptyString(mtype)) {
			param.put("mtype",mtype);
		}
		if (!StringUtil.isEmptyString(mfid)) {
			param.put("mfid",mfid);
		}
		if (!StringUtil.isEmptyString(mode)) {
			param.put("mode",mode);
		}
		if (!StringUtil.isEmptyString(status)) {
			param.put("status",status);
		}
		if (!StringUtil.isEmptyString(dataSrc)) {
			param.put("dataSrc",dataSrc);
		}
		
			DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, this.getCzyid(), this.getFwbj(), this.getBm(), "BJ");
		
		Map<String, Object> re =meterMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public MeterMgtManagerInf getMeterMgtManager() {
		return meterMgtManager;
	}

	public void setMeterMgtManager(MeterMgtManagerInf meterMgtManager) {
		this.meterMgtManager = meterMgtManager;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getMfid() {
		return mfid;
	}

	public void setMfid(String mfid) {
		this.mfid = mfid;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWiring() {
		return wiring;
	}

	public void setWiring(String wiring) {
		this.wiring = wiring;
	}

	public String getMboxid() {
		return mboxid;
	}

	public void setMboxid(String mboxid) {
		this.mboxid = mboxid;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getSealid() {
		return sealid;
	}

	public void setSealid(String sealid) {
		this.sealid = sealid;
	}

	public String getTfid() {
		return tfid;
	}

	public void setTfid(String tfid) {
		this.tfid = tfid;
	}

	public String getSimno() {
		return simno;
	}

	public void setSimno(String simno) {
		this.simno = simno;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getTfname() {
		return tfname;
	}

	public void setTfname(String tfname) {
		this.tfname = tfname;
	}

	public String getCzid() {
		return czid;
	}

	public void setCzid(String czid) {
		this.czid = czid;
	}

	public List<Object> getMtypeLs() {
		return mtypeLs;
	}

	public void setMtypeLs(List<Object> mtypeLs) {
		this.mtypeLs = mtypeLs;
	}

	public List<Object> getMfLs() {
		return mfLs;
	}

	public void setMfLs(List<Object> mfLs) {
		this.mfLs = mfLs;
	}

	public List<Object> getModeLs() {
		return modeLs;
	}

	public void setModeLs(List<Object> modeLs) {
		this.modeLs = modeLs;
	}

	public List<Object> getStatusLs() {
		return statusLs;
	}

	public void setStatusLs(List<Object> statusLs) {
		this.statusLs = statusLs;
	}

	public List<Object> getWiringLs() {
		return wiringLs;
	}

	public void setWiringLs(List<Object> wiringLs) {
		this.wiringLs = wiringLs;
	}

	public List<Object> getCtLs() {
		return ctLs;
	}

	public void setCtLs(List<Object> ctLs) {
		this.ctLs = ctLs;
	}

	public List<Object> getPtLs() {
		return ptLs;
	}

	public void setPtLs(List<Object> ptLs) {
		this.ptLs = ptLs;
	}

	public String getNodeTextdw() {
		return nodeTextdw;
	}

	public void setNodeTextdw(String nodeTextdw) {
		this.nodeTextdw = nodeTextdw;
	}

	public String getNodeIddw() {
		return nodeIddw;
	}

	public void setNodeIddw(String nodeIddw) {
		this.nodeIddw = nodeIddw;
	}

	public String getNodeTypedw() {
		return nodeTypedw;
	}

	public void setNodeTypedw(String nodeTypedw) {
		this.nodeTypedw = nodeTypedw;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public List<Object> getDataSrcLs() {
		return dataSrcLs;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public void setDataSrcLs(List<Object> dataSrcLs) {
		this.dataSrcLs = dataSrcLs;
	}

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
}
