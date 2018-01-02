package cn.hexing.ami.dao.main.pojo.insMgt;

import java.util.Date;

public class WorkOrder implements java.io.Serializable{
	private static final long serialVersionUID = 5130254768940245762L;
	private String woid;
	private String type;
	private String status;
	private String rsp_name;
	private String p_optid;
	private String c_optid;
	private Date c_date;
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
	public String getRsp_name() {
		return rsp_name;
	}
	public void setRsp_name(String rsp_name) {
		this.rsp_name = rsp_name;
	}
	public String getP_optid() {
		return p_optid;
	}
	public void setP_optid(String p_optid) {
		this.p_optid = p_optid;
	}
	public String getC_optid() {
		return c_optid;
	}
	public void setC_optid(String c_optid) {
		this.c_optid = c_optid;
	}
	public Date getC_date() {
		return c_date;
	}
	public void setC_date(Date c_date) {
		this.c_date = c_date;
	}
}
