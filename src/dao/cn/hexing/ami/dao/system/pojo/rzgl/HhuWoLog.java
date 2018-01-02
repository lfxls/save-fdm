package cn.hexing.ami.dao.system.pojo.rzgl;

import java.io.Serializable;

/**
 * @Description 工单_掌机日志实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-6-13
 * @version FDM2.0
 */

public class HhuWoLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4108833942727263284L;
	//日志ID
	private String logId;
	//请求标识
	private String reqId;
	//操作类型
	private String opt_type;
	//操作员ID
	private String optId;
	//掌机ID
	private String hhuId;
	//更新方式
	private String upt_way;
	//操作结果
	private String opt_rst;
	//失败信息
	private String err_msg;
	//操作日期
	private String op_date;
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
	

}
