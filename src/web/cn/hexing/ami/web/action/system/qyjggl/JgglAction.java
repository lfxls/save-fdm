package cn.hexing.ami.web.action.system.qyjggl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.service.system.qyjggl.JgglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
/**
 * @Description 机构管理action
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class JgglAction extends BaseAction implements DbWorksInf, QueryInf {

	private static final long serialVersionUID = -4298953259967557314L;

	JgglManagerInf jgglManager = null;

	private List<Object> jglxList = null;
	//单位代码，部门id，单位名称，父部门id，单位级别,部门名称
	private String dwdm, bmid, sjbmid,dwmc,dwjb,bmmc;
	/**
	 * 初始化
	 */
	public String init() {
		return SUCCESS;
	}
	
	public String getDwTree() {
		List<TreeNode> ls = jgglManager.getDwTree(nodeId, nodeType);
		responseJson(ls, false);
		return null;
	}
	
	public String getQxFwTree() {
		List<TreeCheckNode> ls = jgglManager.getQxFwTree(dwdm, bmid);
		responseJson(ls, false);
		return null;
	}
	
	public String initAddJg() {
		jglxList = CommonUtil.getCode("bmlx", getLang(), false);
		return "addJg";
	}
	
	/**
	 * 增加单位初始化界面 
	 * @return
	 */
	public String initAddDw() {
		return "addDw";
	}
	
	
	/**
	 * 修改单位初始化界面
	 * @return
	 */
	public String initUpDw() {
		return "updateDw";
	}

	/**
	 * 修改权限初始化界面 
	 * @return
	 */
	public String initFwQx() {
		return "fwqx";
	}
	
	/**
	 * 修改部门初始化界面
	 * @return
	 */
	public String initUpJg(){
		return "updateJg";
	}
	
	/**
	 * 初始化单位查询的界面
	 * @return
	 */
	public String initDw(){
		return "dw";
	}
	
	public void dbLogger(String czid, String cznr, String czyId,
			String ip) {
		jgglManager.dbLogger(czid, cznr, czyId, ip);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return jgglManager.doDbWorks(czid, param);
	}

	public List<String> getExcelHead() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询机构详细信息
	 */
	public String query() throws Exception {
		if(StringUtil.isEmptyString(nodeId)) return null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nodeId", nodeId);
		param.put("nodeType", nodeType);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = jgglManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}
	
	/**
	 * 查询机构代码
	 */
	public String queryDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		String dwdm=StringUtil.isEmptyString(this.getDwdm())?"":this.getDwdm();
		param.put("dwmc", dwmc);
		param.put("dwdm", dwdm);
		Map<String, Object> m = jgglManager.getQyList(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}

	public List<Object> getJglxList() {
		return jglxList;
	}

	public void setJglxList(List<Object> jglxList) {
		this.jglxList = jglxList;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getSjbmid() {
		return sjbmid;
	}

	public void setSjbmid(String sjbmid) {
		this.sjbmid = sjbmid;
	}
	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	
	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public JgglManagerInf getJgglManager() {
		return jgglManager;
	}
	
	public void setJgglManager(JgglManagerInf jgglManager) {
		this.jgglManager = jgglManager;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
}
