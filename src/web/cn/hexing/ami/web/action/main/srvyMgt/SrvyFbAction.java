package cn.hexing.ami.web.action.main.srvyMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.main.srvyMgt.SrvyFbManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 勘察反馈
 * @author xcx
 * @Copyright 
 * @time：
 * @version FDM
 */
public class SrvyFbAction extends BaseAction implements QueryInf {

	private static final long serialVersionUID = 2756606998917095978L;

	private static Logger logger = Logger.getLogger(SrvyFbAction.class.getName());

	private SrvyFbManagerInf srvyFbManager;
	
	private String cno, plnStatus, status, pid, bussType;
	private List<Object> plnStList; //计划状态列表
	private List<Object> stList; //勘察数据同步状态列表
	private List<Object> btList; //业务类型列表
	
	/**
	 * 页面初始化
	 */
	public String init(){
		plnStList = CommonUtil.getCodeNumber("planStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		stList = CommonUtil.getCodeNumber("fbStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		//初始化节点
		nodeText = StringUtil.isEmptyString(nodeText) == true ? this.getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) == true ? this.getUnitCode() : nodeId;
		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) == true ? this.getUnitCode() : nodeDwdm; //this.getNodeDwdm();
		nodeType = StringUtil.isEmptyString(nodeType) == true ? "dw" : nodeType; 
		for(int i = 0; i < plnStList.size(); i++) {
			Map<String,Object> plnStMap = (Map<String, Object>) plnStList.get(i);
			String bm = StringUtil.getValue(plnStMap.get("BM"));
			if(Constants.PLN_STATUS_ASSIGNED.equals(bm) || 
					Constants.PLN_STATUS_DISPATCHED.equals(bm) || 
					Constants.PLN_STATUS_UNHANDLED.equals(bm)) {
				plnStList.remove(plnStMap);
				i--;
			}
			
		}
		for(int i = 0; i < btList.size(); i++) {
			Map<String,Object> bussTypeMap = (Map<String, Object>) btList.get(i);
			String bm = StringUtil.getValue(bussTypeMap.get("BM"));
			if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(bm)) {
				btList.remove(bussTypeMap);
				i--;
			}
		}
		return SUCCESS;
	}

	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("status", status);
		param.put("pid", pid);
		param.put("plnStatus", plnStatus);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = srvyFbManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public ActionResult updCurtomer(Map<String, String> param,Util util){

		return srvyFbManager.updCustomer(param);
	}
	

	@Override
	public String queryDetail() {
		return null;
	}

	public SrvyFbManagerInf getSrvyFbManager() {
		return srvyFbManager;
	}

	public void setSrvyFbManager(SrvyFbManagerInf srvyFbManager) {
		this.srvyFbManager = srvyFbManager;
	}

	

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getPlnStatus() {
		return plnStatus;
	}

	public void setPlnStatus(String plnStatus) {
		this.plnStatus = plnStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<Object> getPlnStList() {
		return plnStList;
	}

	public void setPlnStList(List<Object> plnStList) {
		this.plnStList = plnStList;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
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
}
