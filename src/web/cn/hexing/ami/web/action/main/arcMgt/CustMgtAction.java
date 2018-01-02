package cn.hexing.ami.web.action.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.arcMgt.CustMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

public class CustMgtAction extends BaseAction implements QueryInf, DbWorksInf{
	private CustMgtManagerInf custMgtManager;
	//户号，户名，单位代码，地址，计算月，结算日，联系电话，单位名称，操作id，节点ID，节点名称，节点类型，用户状态,数据来源,用户状态值
	private String cno,cname,uid,addr,billing_date,billing_dates,phone,uname,czid,nodeIddw,nodeTextdw,nodeTypedw,custStatus,dataSrc,status;
	//用户状态列表，数据来源列表
	private List<Object> custStatusLs,dataSrcLs;
	//初始化界面
	public String init(){
		custStatusLs=CommonUtil.getCode("custStatus", getLang(),true);
		dataSrcLs=CommonUtil.getCode("dataSrc", getLang(),true);
		return SUCCESS;
	}
	//初始化编辑界面
	public String initCust(){
		Map<String, String> param = new HashMap<String, String>();
		param.put(Constants.APP_LANG, getLang());
		if (!StringUtil.isEmptyString(cno)) {
			param.put("cno",cno);	
		}
		List<Object> custLs=custMgtManager.getCust(param);
		if (custLs != null && custLs.size() > 0) { 
		HashMap<String,Object> customer=(HashMap<String,Object>)custLs.get(0);
		cname= StringUtil.getValue(customer.get("CNAME"));
		nodeTextdw=StringUtil.getValue(customer.get("DWMC"));
		nodeIddw=StringUtil.getValue(customer.get("UID"));
		addr=StringUtil.getValue(customer.get("ADDR"));
		billing_dates=StringUtil.getValue(customer.get("BILLING_DATE"));
		phone=StringUtil.getValue(customer.get("PHONE"));
		status=StringUtil.getValue(customer.get("STATUS"));
		}
		return "initCust";
	}
	//初始化导入界面
	public String initImport(){
		return "initimport";
	}
	//查询用户列表
	public String query(){
		if (StringUtil.isEmptyString(nodeId))
			return null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if (!StringUtil.isEmptyString(cno)) {
			param.put("cno",cno);	
		}
		if (!StringUtil.isEmptyString(cname)) {
			param.put("cname",cname);	
		}
		if (!StringUtil.isEmptyString(nodeId)) {
			param.put("nodeIddw",nodeId);	
		}
		if(!StringUtil.isEmptyString(custStatus)){
			param.put("custStatus",custStatus);	
		}
		if(!StringUtil.isEmptyString(dataSrc)){
			param.put("dataSrc",dataSrc);	
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, this.getCzyid(), this.getFwbj(), this.getBm(), "CUST");
		Map<String, Object> re = custMgtManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return custMgtManager.doDbWorks(czid, param);
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		custMgtManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	//查询用户绑定的表计
	@Override
	public String queryDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if (!StringUtil.isEmptyString(cno)) {
			param.put("cno",cno);	
		}
		Map<String, Object> re = custMgtManager.queryDetail(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	
	public CustMgtManagerInf getCustMgtManager() {
		return custMgtManager;
	}

	public void setCustMgtManager(CustMgtManagerInf custMgtManager) {
		this.custMgtManager = custMgtManager;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getBilling_date() {
		return billing_date;
	}
	public void setBilling_date(String billing_date) {
		this.billing_date = billing_date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getCzid() {
		return czid;
	}
	public void setCzid(String czid) {
		this.czid = czid;
	}
	public String getNodeIddw() {
		return nodeIddw;
	}
	public void setNodeIddw(String nodeIddw) {
		this.nodeIddw = nodeIddw;
	}
	public String getNodeTextdw() {
		return nodeTextdw;
	}
	public void setNodeTextdw(String nodeTextdw) {
		this.nodeTextdw = nodeTextdw;
	}
	public String getNodeTypedw() {
		return nodeTypedw;
	}
	public void setNodeTypedw(String nodeTypedw) {
		this.nodeTypedw = nodeTypedw;
	}
	public String getBilling_dates() {
		return billing_dates;
	}
	public void setBilling_dates(String billing_dates) {
		this.billing_dates = billing_dates;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public List<Object> getCustStatusLs() {
		return custStatusLs;
	}
	public List<Object> getDataSrcLs() {
		return dataSrcLs;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public void setCustStatusLs(List<Object> custStatusLs) {
		this.custStatusLs = custStatusLs;
	}
	public void setDataSrcLs(List<Object> dataSrcLs) {
		this.dataSrcLs = dataSrcLs;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
}
