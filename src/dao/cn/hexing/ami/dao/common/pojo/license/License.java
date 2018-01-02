package cn.hexing.ami.dao.common.pojo.license;

import java.io.Serializable;

/**
 * @Description license对象
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-11-4
 * @version AMI3.0
 */
public class License implements Serializable {
	private static final long serialVersionUID = 8798384776386259183L;
	//license编号
	private String licNo;
	//客户编号
	private String clientNo;
	//电表数量
	private String meterNum;
	//过期时间
	private String expDate;
	//有效期
	private String validDay;
	//创建日期
	private String createDate;
	//CPU标识
	private String cpuId;
	//硬盘标识
	private String diskId;
	//BIOS标识
	private String biosId;
	//加密狗序列号
	private String softDogId;
	//电表数量校验标志
	private boolean meterLimitFlag;
	//过期时间校验标志
	private boolean expLimitFlag;
	//机器信息校验标志
	private boolean machInfoLimitFlag;
	
	public String getLicNo() {
		return licNo;
	}
	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getMeterNum() {
		return meterNum;
	}
	public void setMeterNum(String meterNum) {
		this.meterNum = meterNum;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public String getSoftDogId() {
		return softDogId;
	}
	public void setSoftDogId(String softDogId) {
		this.softDogId = softDogId;
	}
	public String getValidDay() {
		return validDay;
	}
	public void setValidDay(String validDay) {
		this.validDay = validDay;
	}
	public boolean isMeterLimitFlag() {
		return meterLimitFlag;
	}
	public void setMeterLimitFlag(boolean meterLimitFlag) {
		this.meterLimitFlag = meterLimitFlag;
	}
	public boolean isExpLimitFlag() {
		return expLimitFlag;
	}
	public void setExpLimitFlag(boolean expLimitFlag) {
		this.expLimitFlag = expLimitFlag;
	}
	public boolean isMachInfoLimitFlag() {
		return machInfoLimitFlag;
	}
	public void setMachInfoLimitFlag(boolean machInfoLimitFlag) {
		this.machInfoLimitFlag = machInfoLimitFlag;
	}
}
