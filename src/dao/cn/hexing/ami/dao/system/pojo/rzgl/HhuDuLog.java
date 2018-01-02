package cn.hexing.ami.dao.system.pojo.rzgl;

import java.io.Serializable;

/**
 * @Description 数据更新_掌机操作日志实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-6-13
 * @version FDM2.0
 */

public class HhuDuLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6567227867828592174L;
	//日志ID
	private String logId;
	//请求标识
	private String reqId;
	//操作员ID
	private String optId;
	//掌机ID
	private String hhuId;
	//更新方式
	private String upt_way;
	//更新结果
	private String upt_rst;
	//失败信息
	private String err_msg;
	//更新时间
	private String upt_date;
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
	public String getUpt_way() {
		return upt_way;
	}
	public void setUpt_way(String upt_way) {
		this.upt_way = upt_way;
	}
	public String getUpt_rst() {
		return upt_rst;
	}
	public void setUpt_rst(String upt_rst) {
		this.upt_rst = upt_rst;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getUpt_date() {
		return upt_date;
	}
	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}
	
}
