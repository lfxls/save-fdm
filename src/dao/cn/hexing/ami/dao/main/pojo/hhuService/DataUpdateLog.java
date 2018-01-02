package cn.hexing.ami.dao.main.pojo.hhuService;

import java.io.Serializable;

/**
 * @Description 数据更新日志
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-12
 * @version FDM2.0
 */
public class DataUpdateLog implements Serializable {
	private static final long serialVersionUID = -7701335207357494593L;
	//日志ID
	private String logID;
	//请求标识
	private String reqID;
	//操作员ID	
	private String optID;
	//掌机ID
	private String hhuID;
	//更新结果
	private String uptRst;
	//失败信息
	private String errMsg;
	//更新方式
	private String uptWay;
	//更新时间
	private String uptDate;
	//操作ID
	private String opID;
	//数据类型
	private String dataType;
	
	public DataUpdateLog(){
		
	}
	
	public DataUpdateLog(String reqID, String optID, String hhuID, String uptWay, String uptRst, String errMsg){
		this.logID = "";
		this.reqID = reqID;
		this.optID = optID;
		this.hhuID = hhuID;
		this.uptWay = uptWay;
		this.uptRst = uptRst;
		this.errMsg = errMsg;
		this.uptDate = "";
		this.opID = "";
		this.dataType = "";
	}
	
	public String getLogID() {
		return logID;
	}
	public void setLogID(String logID) {
		this.logID = logID;
	}
	public String getReqID() {
		return reqID;
	}
	public void setReqID(String reqID) {
		this.reqID = reqID;
	}
	public String getOptID() {
		return optID;
	}
	public void setOptID(String optID) {
		this.optID = optID;
	}
	public String getHhuID() {
		return hhuID;
	}
	public void setHhuID(String hhuID) {
		this.hhuID = hhuID;
	}
	public String getUptRst() {
		return uptRst;
	}
	public void setUptRst(String uptRst) {
		this.uptRst = uptRst;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getUptDate() {
		return uptDate;
	}
	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}
	public String getOpID() {
		return opID;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUptWay() {
		return uptWay;
	}

	public void setUptWay(String uptWay) {
		this.uptWay = uptWay;
	}
}
