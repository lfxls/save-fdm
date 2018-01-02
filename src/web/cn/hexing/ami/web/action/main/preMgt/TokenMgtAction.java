package cn.hexing.ami.web.action.main.preMgt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.service.main.preMgt.TokenMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description Token管理Action
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-5-17
 * @version FDM2.0
 */

public class TokenMgtAction extends BaseAction implements QueryInf, DbWorksInf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2579773755640877037L;
	TokenMgtManagerInf tokenMgtManager = null;
	
	public void setTokenMgtManager(TokenMgtManagerInf tokenMgtManager) {
		this.tokenMgtManager = tokenMgtManager;
	}
	private String tid, msn, cno, orderid, token, sort, upt_date, status;
	private List<Object> tokenSts;
	//private String tokenStatus;
	private String operateType;
	private File addTokenFile; //导入Token文件
	private static Logger logger = Logger.getLogger(TokenMgtAction.class.getName());
	
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return tokenMgtManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		tokenMgtManager.dbLogger(czid, cznr, czyId, unitCode);		
	}
	
	/**
	 * Token管理界面，查询Token列表
	 */
	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//查询条件中，表号首尾的空格去掉
		msn = msn.trim();
		param.put("msn", msn);
		param.put("cno", cno);
		param.put("orderid", orderid);
		param.put("status", status);
		param.put(Constants.APP_LANG, this.getLang());		
		Map<String, Object> re = tokenMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		
		return null;
	}
	
	public String init() {
		tokenSts = CommonUtil.getCode("tokenSts", getLang(), true);
		return SUCCESS;
	}
	
	public String initTokenMgtEdit() {
		tokenSts = CommonUtil.getCode("tokenSts", getLang(), true);	
		if ("edit".equals(operateType)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tid", tid);
			Map<String, Object> re = tokenMgtManager.getToken(param);
			msn = (String) re.get("MSN");
			token = (String) re.get("TOKEN");
			//token需要处理下：每四个数字插入一个-
			token = token.replaceAll("\\d{4}(?!$)", "$0-");
			cno = (String) re.get("CNO");
			orderid = (String) re.get("ORDERID");
		}
		return "tokenMgt_edit";
	}
	
	/**
	 * 初始Token导入
	 * @return
	 */
	public String initTokenImport() {
		return "tokenImp";
	}
	
	/**
	 * 档案导入
	 * @return
	 */
	public String importExcel(){
		ActionResult result = new ActionResult();
		String lang = getLang();
		long beginTime = System.currentTimeMillis(); // 这段代码放在程序执行前
		try {
			Map<String, String> param = new HashMap<String, String>();
			String czyid =(String) session.getAttribute(Constants.CURR_STAFFID);
			param.put("CURR_STAFFID", czyid);
			
			//新增Token
			if (addTokenFile != null) {
				FileInputStream fis = new FileInputStream(addTokenFile);
				result = tokenMgtManager.parseExcel(fis, param, Constants.IMPORT_PRE_ADD_TOKEN, lang);
				//删除临时文件
				addTokenFile.delete();
			}
			
		} catch (Exception e) {
			logger.error(getText("mainModule.insMgt.plan.import.failed") + StringUtil.getExceptionDetailInfo(e));
			result.setSuccess(false);
			result.setMsg("mainModule.insMgt.plan.import.failed", getLang());
		} finally {
			addTokenFile = null; //导入Token文件
			
			
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
		long endTime = System.currentTimeMillis() ; // 这段代码放在程序执行后
		long wasteTime=endTime-beginTime;
		double seconds=wasteTime/1000;
		System.out.println("耗时：" + seconds+ "秒");
		logger.error("耗时：" + seconds+ "秒");
		return null;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getUpt_date() {
		return upt_date;
	}

	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getTokenSts() {
		return tokenSts;
	}

	public void setTokenSts(List<Object> tokenSts) {
		this.tokenSts = tokenSts;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public File getAddTokenFile() {
		return addTokenFile;
	}

	public void setAddTokenFile(File addTokenFile) {
		this.addTokenFile = addTokenFile;
	}

	/*public String getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}*/
	
	
}
