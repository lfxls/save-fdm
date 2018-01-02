package cn.hexing.ami.web.action.report.insInfoReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.report.insInfoReport.PlanStsRepManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class PlanStsRepAction  extends BaseAction implements QueryInf{
	private PlanStsRepManagerInf planStsRepManager;
	private String tno,status,woid,delayDate;
	private List<Object> statusLs;
	
	public String init(){
		statusLs=CommonUtil.getCode("planStatus", getLang(), true);
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(tno)){
			param.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(status)){
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(woid)){
			param.put("woid", woid);
		}
		if(!StringUtil.isEmptyString(delayDate)){
			param.put("delayDate", delayDate);
		}
		Map<String, Object>re=planStsRepManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}
	public ActionResult getChart(Map<String, String> params,Util util) {
		ActionResult res = new ActionResult();
		if(!StringUtil.isEmptyString(tno)){
			params.put("tno", tno);
		}
		String result =  planStsRepManager.getChart(params);
		res.setSuccess(true);
		res.setMsg(result);
		return res;
	}
	
	
	public String getTno() {
		return tno;
	}

	public String getStatus() {
		return status;
	}


	public void setStatusLs(List<Object> statusLs) {
		this.statusLs = statusLs;
	}

	public List<Object> getStatusLs() {
		return statusLs;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public PlanStsRepManagerInf getPlanStsRepManager() {
		return planStsRepManager;
	}

	public void setPlanStsRepManager(PlanStsRepManagerInf planStsRepManager) {
		this.planStsRepManager = planStsRepManager;
	}

	public String getWoid() {
		return woid;
	}

	public String getDelayDate() {
		return delayDate;
	}

	public void setWoid(String woid) {
		this.woid = woid;
	}

	public void setDelayDate(String delayDate) {
		this.delayDate = delayDate;
	}

}
