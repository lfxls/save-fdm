package cn.hexing.ami.dao.common.pojo.arc;

import java.io.Serializable;

/**
 * @Description Sim卡实体类 
 * @author HEZ297
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-25
 * @version FDM2.0	
 */

public class Sim implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7348263738555760799L;
	//SIM卡号
	private String simno;
	//SIM序列号
	private String simsn;
	//通讯服务商
	private String msp;
	public String getSimno() {
		return simno;
	}
	public void setSimno(String simno) {
		this.simno = simno;
	}
	public String getSimsn() {
		return simsn;
	}
	public void setSimsn(String simsn) {
		this.simsn = simsn;
	}
	public String getMsp() {
		return msp;
	}
	public void setMsp(String msp) {
		this.msp = msp;
	}
	
	
}
