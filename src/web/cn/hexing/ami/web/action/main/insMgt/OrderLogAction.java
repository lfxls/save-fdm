package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.insMgt.OrderLogManagerInf;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * 
 * @Description  工单操作日志查询
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-13
 * @version FDM2.0
 */
public class OrderLogAction extends BaseAction implements QueryInf{

	private static final long serialVersionUID = 5668920759029786345L;
	private String woid;//工单号
	private String status;//工单状态
	private String startDate;//开始日期
	private String endDate;//结束日期
	private List<Object> stLs;//状态列表
	private String popid;//处理人Id
	private String popName;//处理人名称
	OrderLogManagerInf orderLogManager;
	public String getWoid() {
		return woid;
	}
	public void setWoid(String woid) {
		this.woid = woid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<Object> getStLs() {
		return stLs;
	}
	public void setStLs(List<Object> stLs) {
		this.stLs = stLs;
	}
	public String getPopid() {
		return popid;
	}
	public void setPopid(String popid) {
		this.popid = popid;
	}
	public String getPopName() {
		return popName;
	}
	public void setPopName(String popName) {
		this.popName = popName;
	}
	public OrderLogManagerInf getOrderLogManager() {
		return orderLogManager;
	}
	public void setOrderLogManager(OrderLogManagerInf orderLogManager) {
		this.orderLogManager = orderLogManager;
	}
	
	/**
	 * 初始化页面
	 */
	public String init(){
//		stLs=CommonUtil.getCodeNumber("woStatus", this.getLang(), "ASC", true);
//		startDate = StringUtil.isEmptyString(startDate) ? DateUtil.getLastWeek() : startDate;
//		endDate = StringUtil.isEmptyString(endDate) ? DateUtil.getToday(): endDate;
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(woid)) {
			param.put("woid", woid);
		}
		if(!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(popid)) {
			param.put("popid", popid);
		}
		if(!StringUtil.isEmptyString(startDate)) {
			param.put("startDate", startDate);
		}
		if(!StringUtil.isEmptyString(endDate)) {
			param.put("endDate", endDate);
		}
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> re = orderLogManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	@Override
	public String queryDetail() {
		return null;
	}
}
