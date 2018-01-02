package cn.hexing.ami.web.action.system.rzgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.rzgl.HhuLogManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

public class HhuLogAction extends BaseAction implements QueryInf, DbWorksInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3021539279784625392L;
	HhuLogManagerInf hhuLogManager = null;
	
	public void setHhuLogManager(HhuLogManagerInf hhuLogManager) {
		this.hhuLogManager = hhuLogManager;
	}
	
	private String optId, hhuId, logId, reqId, opt_type, upt_way, opt_rst, err_msg, op_date, startDate, endDate;
	private String upt_rst, upt_date;
	//工单
	private String woid, type, status, p_optId, c_optId, tid, c_date;
	//变压器
	private String opId, tfId, tfName, op_type;
	//参数方案
	private String verId, cateName, obis, pm_type;
	//基础代码
	private String code_name, name, language;
	//Token
	private String token, msn;
	private List<Object> hhuRst, hhuOptType;
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		hhuLogManager.dbLogger(czid, cznr, czyId, unitCode);	
	}
	
	public String init() {
		hhuRst = CommonUtil.getCode("hhuRst", getLang(), true);
		hhuOptType = CommonUtil.getCode("hhuOptType", getLang(), true);
		return SUCCESS;
	}
	
	/**
	 * @Description 掌机工单日志界面，查询日志列表
	 * @param param
	 * @return
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件操作员ID
		if (!StringUtil.isEmptyString(optId)) {
			param.put("optId", optId);
		}
		//查询条件HHU ID
		if (!StringUtil.isEmptyString(hhuId)) {
			param.put("hhuId", hhuId);
		}
		//查询条件操作结果
		if (!StringUtil.isEmptyString(opt_rst)) {
			param.put("opt_rst", opt_rst);
		}
		//查询条件起始日期
		if (!StringUtil.isEmptyString(startDate)) {
			param.put("startDate", startDate);
		}
		//查询条件结束日期
		if (!StringUtil.isEmptyString(endDate)) {
			param.put("endDate", endDate);
		}
		//查询条件操作类型
		if (!StringUtil.isEmptyString(opt_type)) {
			param.put("opt_type", opt_type);
		}
		//String cn.hexing.ami.web.action.BaseAction.getLang()
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * @Description 掌机基础数据日志界面，查询日志列表
	 * @param param
	 * @return
	 */
	public String queryHhuDuLog() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件操作员ID
		if (!StringUtil.isEmptyString(optId)) {
			param.put("optId", optId);
		}
		//查询条件HHU ID
		if (!StringUtil.isEmptyString(hhuId)) {
			param.put("hhuId", hhuId);
		}
		//查询条件操作结果
		if (!StringUtil.isEmptyString(upt_rst)) {
			param.put("upt_rst", upt_rst);
		}
		//查询条件起始日期
		if (!StringUtil.isEmptyString(startDate)) {
			param.put("startDate", startDate);
		}
		//查询条件结束日期
		if (!StringUtil.isEmptyString(endDate)) {
			param.put("endDate", endDate);
		}
		//String cn.hexing.ami.web.action.BaseAction.getLang()
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuDuLog(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryHhuWo() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件日志ID
		if (!StringUtil.isEmptyString(logId)) {
			param.put("logId", logId);
		}
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuWo(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryHhuTf() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件日志ID
		if (!StringUtil.isEmptyString(logId)) {
			param.put("logId", logId);
		}
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuTf(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryHhuPs() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件日志ID
		if (!StringUtil.isEmptyString(logId)) {
			param.put("logId", logId);
		}
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuPs(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryHhuCode() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件日志ID
		if (!StringUtil.isEmptyString(logId)) {
			param.put("logId", logId);
		}
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuCode(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	public String queryHhuToken() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件日志ID
		if (!StringUtil.isEmptyString(logId)) {
			param.put("logId", logId);
		}
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = hhuLogManager.queryHhuToken(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 初始化掌机_基础数据列表
	 * @return
	 */
	public String initHhuDuLog() {
		hhuRst = CommonUtil.getCode("hhuRst", getLang(), true);
		return "hhuDuLog";
	}
	
	/**
	 * 初始化查看日志级联的工单
	 * @return
	 */
	public String initHhuWo() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logId", logId);
		return "hhuWo";
	}
	
	/**
	 * 初始化查看日志级联的变压器
	 * @return
	 */
	public String initHhuTf() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logId", logId);
		return "hhuTf";
	}
	
	/**
	 * 初始化查看日志级联的参数方案
	 * @return
	 */
	public String initHhuPs() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logId", logId);
		return "hhuPs";
	}
	
	/**
	 * 初始化查看日志级联的基础代码
	 * @return
	 */
	public String initHhuCode() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logId", logId);
		return "hhuCode";
	}
	
	/**
	 * 初始化查看日志级联的Token
	 * @return
	 */
	public String initHhuToken() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("logId", logId);
		return "hhuToken";
	}
	
	@Override
	public String queryDetail() {
		return null;
	}

	public String getOptId() {
		return optId;
	}

	public void setOptId(String optId) {
		this.optId = optId;
	}

	public String getHhuId() {
		return hhuId;
	}

	public void setHhuId(String hhuId) {
		this.hhuId = hhuId;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getOpt_type() {
		return opt_type;
	}

	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}

	public String getUpt_way() {
		return upt_way;
	}

	public void setUpt_way(String upt_way) {
		this.upt_way = upt_way;
	}

	public String getOpt_rst() {
		return opt_rst;
	}

	public void setOpt_rst(String opt_rst) {
		this.opt_rst = opt_rst;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getOp_date() {
		return op_date;
	}

	public void setOp_date(String op_date) {
		this.op_date = op_date;
	}

	public String getUpt_rst() {
		return upt_rst;
	}

	public void setUpt_rst(String upt_rst) {
		this.upt_rst = upt_rst;
	}

	public String getUpt_date() {
		return upt_date;
	}

	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}

	public String getWoid() {
		return woid;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getP_optId() {
		return p_optId;
	}

	public void setP_optId(String p_optId) {
		this.p_optId = p_optId;
	}

	public String getC_optId() {
		return c_optId;
	}

	public void setC_optId(String c_optId) {
		this.c_optId = c_optId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getC_date() {
		return c_date;
	}

	public void setC_date(String c_date) {
		this.c_date = c_date;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getTfId() {
		return tfId;
	}

	public void setTfId(String tfId) {
		this.tfId = tfId;
	}

	public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}

	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getObis() {
		return obis;
	}

	public void setObis(String obis) {
		this.obis = obis;
	}

	public String getPm_type() {
		return pm_type;
	}

	public void setPm_type(String pm_type) {
		this.pm_type = pm_type;
	}

	public String getCode_name() {
		return code_name;
	}

	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Object> getHhuRst() {
		return hhuRst;
	}

	public void setHhuRst(List<Object> hhuRst) {
		this.hhuRst = hhuRst;
	}

	public List<Object> getHhuOptType() {
		return hhuOptType;
	}

	public void setHhuOptType(List<Object> hhuOptType) {
		this.hhuOptType = hhuOptType;
	}
	
	
	
}
