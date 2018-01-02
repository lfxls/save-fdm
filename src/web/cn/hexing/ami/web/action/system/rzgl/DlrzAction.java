package cn.hexing.ami.web.action.system.rzgl;

import java.util.HashMap;
import java.util.Map;

import cn.hexing.ami.service.system.rzgl.DlrzManagerInf;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;

/**
 * @Description 登陆日志Action
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-26
 * @version AMI3.0
 */
public class DlrzAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private DlrzManagerInf dlrzManager = null;
	
	private String czy, ip, kssj, jssj;
	
	/**
	 * 登陆日志页面初始化
	 */
	public String init() {
		if (StringUtil.isEmptyString(kssj)) {
			//kssj = DateUtil.getLastMonthDay();
			kssj = DateUtil.getYesterday();
		}
		if (StringUtil.isEmptyString(jssj)) {
			jssj = DateUtil.getToday();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询登陆日志
	 */
	public String query(){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(nodeId)) {
			param.put("dept", nodeId);
		}
		if (!StringUtil.isEmptyString(czy)) {
			param.put("czy", czy.trim());
		}
		if (!StringUtil.isEmptyString(ip)) {
			param.put("ip", ip.trim());
		}
		if (!StringUtil.isEmptyString(kssj)) {
			param.put("kssj", kssj);
		}
		if (!StringUtil.isEmptyString(jssj)) {
			param.put("jssj", jssj);
		}
		//过滤单位
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, this.getCzyid(), this.getFwbj(), this.getBm(), "dlxx");
		Map<String, Object> re = new HashMap<String, Object>();
		
		if(!StringUtil.isEmptyString(nodeId))
			re = dlrzManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	public DlrzManagerInf getDlrzManager() {
		return dlrzManager;
	}

	public void setDlrzManager(DlrzManagerInf dlrzManager) {
		this.dlrzManager = dlrzManager;
	}

	public String getCzy() {
		return czy;
	}

	public void setCzy(String czy) {
		this.czy = czy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
}
