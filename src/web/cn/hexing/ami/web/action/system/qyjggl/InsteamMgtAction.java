package cn.hexing.ami.web.action.system.qyjggl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.qyjggl.InsteamMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 安装队管理Action
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-11
 * @version FDM2.0
 */

public class InsteamMgtAction extends BaseAction implements QueryInf, DbWorksInf {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3364825992031903659L;
	InsteamMgtManagerInf insteamMgtManager = null;
	public void setInsteamMgtManager(InsteamMgtManagerInf insteamMgtManager) {
		this.insteamMgtManager = insteamMgtManager;
	}
	private String tno, tname, status, rsp_name, p_num, phone, crt_date;
	private List<Object> insteamsts;
	private String operateType;
	
	public String init() {
		insteamsts = CommonUtil.getCode("insteamsts", getLang(), true);
		return SUCCESS;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return insteamMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		insteamMgtManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	
	/**
	 * 查询安装队列表
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tname", tname);
		param.put("status", status);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = insteamMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String querySel() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tname", tname);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = insteamMgtManager.querySel(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	
	
	public String initInsteamMgtEdit() {
		insteamsts = CommonUtil.getCode("insteamsts", getLang(), true);
		if("edit".equals(operateType)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tno", tno);
			Map<String, Object> re = insteamMgtManager.getInsteam(param);
			tname = (String) re.get("TNAME");
			status = (String) re.get("STATUS");
			rsp_name = (String) re.get("RSP_NAME");
			p_num = (String) re.get("P_NUM");
			phone = (String) re.get("PHONE");			
		}
		return "insteamMgt_edit";
	}
	
	public String initInsteamSel() {
		return "initInsteamSel";
	}

	@Override
	public String queryDetail() {
		return null;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getRsp_name() {
		return rsp_name;
	}

	public void setRsp_name(String rsp_name) {
		this.rsp_name = rsp_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrt_date() {
		return crt_date;
	}

	public void setCrt_date(String crt_date) {
		this.crt_date = crt_date;
	}

	public List<Object> getInsteamsts() {
		return insteamsts;
	}

	public void setInsteamsts(List<Object> insteamsts) {
		this.insteamsts = insteamsts;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getP_num() {
		return p_num;
	}

	public void setP_num(String p_num) {
		this.p_num = p_num;
	}
	
}
