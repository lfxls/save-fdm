package cn.hexing.ami.dao.system.pojo.qyjggl;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 安装队管理实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-11
 * @version FDM2.0
 */

public class Insteam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3969365466948750705L;
	//安装队编码
	private String tno;
	//安装队名称
	private String tname;
	//负责人
	private String rsp_name;
	//联系电话
	private String phone;
	//人员数量
	private String p_num;
	//状态
	private String status;
	//创建时间
	private Date crt_date;
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getRsp_name() {
		return rsp_name;
	}
	public void setRsp_name(String rsp_name) {
		this.rsp_name = rsp_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCrt_date() {
		return crt_date;
	}
	public void setCrt_date(Date crt_date) {
		this.crt_date = crt_date;
	}
	public String getP_num() {
		return p_num;
	}
	public void setP_num(String p_num) {
		this.p_num = p_num;
	}
		
}
