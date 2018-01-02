package cn.hexing.ami.dao.common.pojo.arc;

import java.io.Serializable;

/**
 * @Description HHU管理实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class Hhu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5560548463371293629L;
	//HHU id号
	private String hhuid;
	//HHU型号
	private String model;
	//HHU电池容量
	private String bcap;
	//HHU程序版本
	private String appvn;
	//HHU入库时间
	private String wh_date;
	//HHU状态
	private String status;
	//是否初始化
	private String data_init;
	
	public String getHhuid() {
		return hhuid;
	}
	public void setHhuid(String hhuid) {
		this.hhuid = hhuid;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBcap() {
		return bcap;
	}
	public void setBcap(String bcap) {
		this.bcap = bcap;
	}
	public String getAppvn() {
		return appvn;
	}
	public void setAppvn(String appvn) {
		this.appvn = appvn;
	}
	public String getWh_date() {
		return wh_date;
	}
	public void setWh_date(String wh_date) {
		this.wh_date = wh_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData_init() {
		return data_init;
	}
	public void setData_init(String data_init) {
		this.data_init = data_init;
	}
	
}
