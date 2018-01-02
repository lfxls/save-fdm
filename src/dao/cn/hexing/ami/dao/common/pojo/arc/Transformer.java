package cn.hexing.ami.dao.common.pojo.arc;

import java.io.Serializable;

/**
 * @Description 变压器管理实体类
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-4-27
 * @version FDM2.0
 */

public class Transformer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9007338815965280967L;
	//变压器ID
	private String tfid;
	//单位代码
	private String uid;
	//名称
	private String tfname;
	//安装地址
	private String addr;
	//铭牌容量
	private String cap;
	//运行状态
	private String status;
	//操作标识
	private String opID;
	//操作类型
	private String opType;
	//nodeId
	private String nodeIddw;
	//数据来源
	private String dataSrc;
	public String getTfid() {
		return tfid;
	}
	public void setTfid(String tfid) {
		this.tfid = tfid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTfname() {
		return tfname;
	}
	public void setTfname(String tfname) {
		this.tfname = tfname;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
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
	public String getNodeIddw() {
		return nodeIddw;
	}
	public void setNodeIddw(String nodeIddw) {
		this.nodeIddw = nodeIddw;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
}
