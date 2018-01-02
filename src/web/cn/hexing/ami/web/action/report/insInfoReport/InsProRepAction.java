package cn.hexing.ami.web.action.report.insInfoReport;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.report.insInfoReport.InsProRepManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class InsProRepAction extends BaseAction implements QueryInf {
	
	private String insNum,averNum,delayNum;
	private InsProRepManagerInf insProRepManager;
	

	public String init(){
		return SUCCESS;
	}
	public ActionResult getChart(Map<String, String> params,Util util) {
		ActionResult res = new ActionResult();
		if(!StringUtil.isEmptyString(insNum)){
			params.put("insNum", insNum);
		}
		if(!StringUtil.isEmptyString(delayNum)){
			params.put("delayNum", delayNum);
		}
		
		String result =  insProRepManager.getChart(params);	
		res.setSuccess(true);
		res.setDataObject(result);
		return res;
	}
	
	public String getInsNum() {
		return insNum;
	}

	public void setInsNum(String insNum) {
		this.insNum = insNum;
	}

	public String getAverNum() {
		return averNum;
	}

	public void setAverNum(String averNum) {
		this.averNum = averNum;
	}

	public InsProRepManagerInf getInsProRepManager() {
		return insProRepManager;
	}

	public void setInsProRepManager(InsProRepManagerInf insProRepManager) {
		this.insProRepManager = insProRepManager;
	}
	@Override
	public String query() throws Exception {
		HashMap<String, Object> param=new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(insNum)){
			param.put("insNum", insNum);
		}
		if(!StringUtil.isEmptyString(delayNum)){
			param.put("delayNum", delayNum);
		}
		Map<String, Object>re=  insProRepManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	@Override
	public String queryDetail() {
		return null;
	}
	
	public String getDelayNum() {
		return delayNum;
	}
	public void setDelayNum(String delayNum) {
		this.delayNum = delayNum;
	}

}
