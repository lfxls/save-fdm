package cn.hexing.ami.web.action.report.insInfoReport;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.service.report.insInfoReport.InsNumRepManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

public class InsNumRepAction extends BaseAction implements QueryInf {
	
	private String tno,tname,dateType,startDate,endDate,startMonth,endMonth,chartType;
	private List<Object> dateTypeLs,chartTypeLs;
	private InsNumRepManagerInf insNumRepManager;
	@Override
	public String init() {
		dateTypeLs=CommonUtil.getCode("datetype", getLang(), false);
		startMonth = StringUtil.isEmptyString(startMonth) ? DateUtil.getLastMonth():startMonth;
		endMonth = StringUtil.isEmptyString(endMonth) ? DateUtil.getCurrentlyMonth():endMonth;
		startDate = StringUtil.isEmptyString(startDate) ? DateUtil.getLastWeek():startDate;
		endDate = StringUtil.isEmptyString(endDate) ? DateUtil.getCurrentlyDate():endDate;
		return SUCCESS;
	}
	public String initDetail() {
		chartTypeLs=CommonUtil.getCode("chartType", getLang(), false);
		dateTypeLs=CommonUtil.getCode("datetype", getLang(), false);
		chartType= (String)((Map<String, Object>)chartTypeLs.get(0)).get("BM");
		startMonth = StringUtil.isEmptyString(startMonth) ? DateUtil.getLastMonth():startMonth;
		endMonth = StringUtil.isEmptyString(endMonth) ? DateUtil.getCurrentlyMonth():endMonth;
		startDate = StringUtil.isEmptyString(startDate) ? DateUtil.getLastWeek():startDate;
		endDate = StringUtil.isEmptyString(endDate) ? DateUtil.getCurrentlyDate():endDate;
		return "detail";
	}
	@Override
	public String query() throws Exception {
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(tno)){
			param.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(dateType)){
			param.put("dateType", dateType);
		}
		if(!StringUtil.isEmptyString(startDate)){
			param.put("startDate", startDate);
		}
		if(!StringUtil.isEmptyString(endDate)){
			param.put("endDate",  DateUtil.getNextDay(endDate));
		}
		if(!StringUtil.isEmptyString(startMonth)){
			param.put("startMonth",startMonth);
		}
		if(!StringUtil.isEmptyString(endMonth)){
			param.put("endMonth",  DateUtil.getNextNMonth(endMonth, 1));
		}
		Map<String,Object> re;
		if("0".equals(dateType)){
			re=insNumRepManager.queryDetail(param, start, limit, dir, sort, isExcel);
		}
		else{
			re=insNumRepManager.query(param, start, limit, dir, sort, isExcel);
		}
		responseGrid(re);
		return null;
	}
	
	public ActionResult getChart(Map<String, Object> params,Util util) {
		ActionResult res = new ActionResult();
		endDate=(String) params.get("endDate");
		try {
			params.put("endDate", DateUtil.getNextDay(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		endMonth=(String) params.get("endMonth");
		if(!StringUtil.isEmptyString(endMonth)){
			params.put("endMonth",  DateUtil.getNextNMonth(endMonth, 1));
		}
		String result =  insNumRepManager.getChart(params);
		res.setSuccess(true);
		res.setDataObject(result);
		return res;
	}

	@Override
	public String queryDetail() {
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put(Constants.APP_LANG, getLang());
		if(!StringUtil.isEmptyString(dateType)){
			param.put("dateType", dateType);
		}
		if(!StringUtil.isEmptyString(tno)){
			param.put("tno", tno);
		}
		if(!StringUtil.isEmptyString(startDate)){
			param.put("startDate", startDate);
		}
		if(!StringUtil.isEmptyString(endDate)){
			param.put("endDate", endDate);
		}
		if(!StringUtil.isEmptyString(startMonth)){
			param.put("startMonth",startMonth);
		}
		if(!StringUtil.isEmptyString(endMonth)){
			param.put("endMonth",  DateUtil.getNextNMonth(endMonth, 1));
		}
		Map<String,Object> re;
		if("0".equals(dateType)){
			re=insNumRepManager.queryDetail(param, start, limit, dir, sort, isExcel);
		}
		else{
			re=insNumRepManager.query(param, start, limit, dir, sort, isExcel);
		}
		responseGrid(re);
		return null;
	}

	public String getTno() {
		return tno;
	}

	public String getTname() {
		return tname;
	}

	public String getDateType() {
		return dateType;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getChartType() {
		return chartType;
	}

	public List<Object> getDateTypeLs() {
		return dateTypeLs;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public void setDateTypeLs(List<Object> dateTypeLs) {
		this.dateTypeLs = dateTypeLs;
	}

	public InsNumRepManagerInf getInsNumRepManager() {
		return insNumRepManager;
	}

	public void setInsNumRepManager(InsNumRepManagerInf insNumRepManager) {
		this.insNumRepManager = insNumRepManager;
	}
	public List<Object> getChartTypeLs() {
		return chartTypeLs;
	}
	public void setChartTypeLs(List<Object> chartTypeLs) {
		this.chartTypeLs = chartTypeLs;
	}

}
