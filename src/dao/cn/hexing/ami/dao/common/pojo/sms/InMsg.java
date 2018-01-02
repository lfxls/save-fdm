package cn.hexing.ami.dao.common.pojo.sms;

import java.io.Serializable;

/**
 * @Description 接收消息对象
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-17
 * @version AMI3.0
 */
public class InMsg implements Serializable {
	private static final long serialVersionUID = -7199232465083112944L;
	//消息ID
	private String msgId;
	//消息状态，"0"未处理，"1"处理
	private String process;
	//消息发送者
	private String originator;
	//消息类型，"I" for inbound message, "S" for status report message
	private String type;
	//编码类型，"7"7bit，"8"8bit，"U"Unicode/UCS2
	private String encoding;
	//消息时间
	private String msgDate;
	//接收时间
	private String recDate;
	//消息内容
	private String text;
	//发送消息参考编号
	private String orgRefNo;
	//发送消息接收时间
	private String orgRecDate;
	//网关ID
	private String gatewayId;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getRecDate() {
		return recDate;
	}
	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getOrgRefNo() {
		return orgRefNo;
	}
	public void setOrgRefNo(String orgRefNo) {
		this.orgRefNo = orgRefNo;
	}
	public String getOrgRecDate() {
		return orgRecDate;
	}
	public void setOrgRecDate(String orgRecDate) {
		this.orgRecDate = orgRecDate;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
}
