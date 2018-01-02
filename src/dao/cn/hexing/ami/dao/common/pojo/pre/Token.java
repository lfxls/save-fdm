package cn.hexing.ami.dao.common.pojo.pre;

import java.io.Serializable;

/**
 * @Description Token管理实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-5-17
 * @version FDM2.0
 */

public class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8322374712906944887L;
	//Token标识
	private String tid;
	//表号
	private String msn;
	//户号
	private String cno;
	//订单号
	private String orderid;
	//Token
	private String token;
	//序号
	private String sort;
	//更新时间
	private String upt_date;
	//状态
	private String status;
	//操作标识
	private String opID;
	//操作类型
	private String opType;
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
	public String getOpID() {
		return opID;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}	
}
