package cn.hexing.ami.web.action.main.arcMgt;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.arcMgt.TransfMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 变压器管理Action
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class TransfMgtAction extends BaseAction implements QueryInf, DbWorksInf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5002612158942143951L;
	TransfMgtManagerInf transfMgtManager = null;

	public void setTransfMgtManager(TransfMgtManagerInf transfMgtManager) {
		this.transfMgtManager = transfMgtManager;
	}
	private String tfId, uid, tfName, addr, cap, status, nodeTextdw, nodeIddw, nodeTypedw, uname, dataSrc;
	private List<Object> tfStatus, dataSrcLs;
	private String operateType;
	private String nodeId;
	//配置变压器档案是否只从其他系统同步：true只从其他系统同步，界面上不显示增删改按钮；false不只可以从其他系统同步，也可FDM直接录入
	private String transformerFromOther;
	
	public String init() {
		tfStatus = CommonUtil.getCode("tfStatus", getLang(), true);
		dataSrcLs = CommonUtil.getCode("dataSrc", getLang(), true);
		
		//Power Utility需要给默认值，默认值取当前操作员最上级单位
		nodeText = StringUtil.isEmptyString(nodeText) == true ? this.getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) == true ? this.getUnitCode() : nodeId;
		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) == true ? this.getUnitCode() : nodeDwdm; //this.getNodeDwdm();
		nodeType = StringUtil.isEmptyString(nodeType) == true ? "dw" : nodeType;
		
		//配置变压器档案是否只从其他系统同步：true只从其他系统同步，界面上不显示增删改按钮；false不只可以从其他系统同步，也可FDM直接录入
		Map<String, String> sysMap = (Map<String, String>) AppEnv.getObject(Constants.SYS_PARAMMAP);
		if ( sysMap!=null ) {
			transformerFromOther = StringUtil.isEmptyString(sysMap.get("transformerFromOther")) == true  ? "false" : sysMap.get("transformerFromOther");
		}
		
		return SUCCESS;
	}
	
	public String initTransfMgtEdit() {
		tfStatus = CommonUtil.getCode("tfStatus", getLang(), true);	
		if("edit".equals(operateType)) {
			Map<String, Object> param = new HashMap<String, Object>();			
			param.put("tfId", tfId);
			Map<String, Object> re = transfMgtManager.getTf(param);
			nodeIddw = (String) re.get("UID");
			tfName = (String) re.get("TFNAME");
			nodeTextdw = (String) re.get("NODETEXTDW");
			status = (String) re.get("STATUS");
			cap = (String) re.get("CAP");
			//cap取出的是有千分位的数字，需要去去掉。
			/*if(cap.length() != 0) {
				int cap1 = (int) new DecimalFormat().parse(cap).doubleValue();
				cap = String.valueOf(cap1);
			}*/			
			addr = (String) re.get("ADDR");			
		}
		return "transfMgt_edit";
	}
	
	public String initTransfSel() {
		tfStatus = CommonUtil.getCode("tfStatus", getLang(), true);
		return "initTransfSel";
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return transfMgtManager.doDbWorks(czid, param);
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		transfMgtManager.dbLogger(czid, cznr, czyId, unitCode);		
	}
	
	/**
	 * 变压器管理界面，查询变压器列表
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();	
		if (StringUtil.isEmptyString(nodeId))
			return null;
		if (!StringUtil.isEmptyString(nodeId)) {
			param.put("uid", nodeId);
		}
		if (!StringUtil.isEmptyString(tfId)) {
			param.put("tfId", tfId);
		}
		if (!StringUtil.isEmptyString(tfName)) {
			param.put("tfName", tfName);
		}
		if (!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if (!StringUtil.isEmptyString(dataSrc)) {
			param.put("dataSrc",dataSrc);
		}
		param.put(Constants.APP_LANG, this.getLang());
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, this
				.getCzyid(), this.getFwbj(), this.getBm(), "TF");
		Map<String, Object> re = transfMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	/**
	 * 变压器选择界面，查询安装队列表
	 */
	public String querySel() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		if(!StringUtil.isEmptyString(nodeIddw)) {
			param.put("uid", nodeIddw);
		}		
		param.put("tfName", tfName);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = transfMgtManager.querySel(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String queryDetail() {
		return null;
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
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Object> getTfStatus() {
		return tfStatus;
	}
	public void setTfStatus(List<Object> tfStatus) {
		this.tfStatus = tfStatus;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
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

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}

	public List<Object> getDataSrcLs() {
		return dataSrcLs;
	}

	public void setDataSrcLs(List<Object> dataSrcLs) {
		this.dataSrcLs = dataSrcLs;
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

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getTransformerFromOther() {
		return transformerFromOther;
	}

	public void setTransformerFromOther(String transformerFromOther) {
		this.transformerFromOther = transformerFromOther;
	}
	
	
	
}
