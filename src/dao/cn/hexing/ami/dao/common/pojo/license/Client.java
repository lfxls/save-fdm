package cn.hexing.ami.dao.common.pojo.license;

import java.io.Serializable;

/**
 * @Description 客户对象
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-11-5
 * @version AMI3.0
 */
public class Client implements Serializable {
	//客户编号
	private String clientNo;
	//客户名称
	private String clientName;
	//联系方式
	private String contact;
	//国家
	private String countryNo;
	//地址
	private String address;
	//CPU标识
	private String cpuId;
	//BIOS标识
	private String biosId;
	//硬盘标识
	private String diskId;
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCountryNo() {
		return countryNo;
	}
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCpuId() {
		return cpuId;
	}
	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}
	public String getBiosId() {
		return biosId;
	}
	public void setBiosId(String biosId) {
		this.biosId = biosId;
	}
}
