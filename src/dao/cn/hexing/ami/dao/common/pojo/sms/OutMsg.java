package cn.hexing.ami.dao.common.pojo.sms;

import java.io.Serializable;

/**
 * @Description 发送消息对象
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-17
 * @version AMI3.0
 */
public class OutMsg implements Serializable {
	private static final long serialVersionUID = -7669202524447334156L;
	//消息ID
	private String msgId;
	//消息类型，"O" for normal outbound messages, or "W" for wap si messages
	private String type;
	//接收者
	private String recipient;
	//消息内容
	private String text;
	//WAP SI URL address
	private String wapUrl;
	//The WAP SI expiry date. If no value is given, SMSServer will calculate a 7 day ahead expiry date
	private String wapExpDate;
	//The WAP SI signal. Use "N" for NONE, "L" for LOW, "M" for MEDIUM, "H" for HIGH, "D" for DELETE. If no value/invalid value is given, the NONE signal will be used.
	private String wapSign;
	//创建日期
	private String createDate;
	//消息发送者
	private String originator;
	//编码类型，"7"7bit，"8"8bit，"U"Unicode/UCS2
	private String encoding;
	//Set to 1 if you require a status report message to be generated
	private String statusReport;
	//Set to 1 if you require your message to be sent as a flash message.
	private String flashSms;
	//Set to source port (for midlets)
	private String srcPort;
	//Set to destination port (for midlets)
	private String dstPort;
	//The sent date. This field is updated by SMSServer when it sends your message.
	private String sentDate;
	//The Reference ID of your message. This field is updated by SMSServer when it sends your message.
	private String refNo;
	//Lower (or negative) values mean lower priority than higher (or positive) values. By convention, a priority of a value 0 (zero) is considered the normal priority. High priority messages get sent first than others.
	private String priority;
	//"U" : unsent, "Q" : queued, "S" : sent, "F" : failed. This field is updated by SMSServer when it sends your message. If set in the configuration file, this field takes extra values depending on the received status report message: "D" : delivered, "P" : pending, "A" : aborted.
	private String status;
	//The number of retries SMSServer did to send your message. This field is updated by SMSServer.
	private String errors;
	//Set it to the star character if you want to leave to SMSServer the decision as to which gateway to use to send your message. Set it to a specific Gateway ID to force SMSServer to send your message via this gateway.
	private String gatewayId;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getWapUrl() {
		return wapUrl;
	}
	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}
	public String getWapExpDate() {
		return wapExpDate;
	}
	public void setWapExpDate(String wapExpDate) {
		this.wapExpDate = wapExpDate;
	}
	public String getWapSign() {
		return wapSign;
	}
	public void setWapSign(String wapSign) {
		this.wapSign = wapSign;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getStatusReport() {
		return statusReport;
	}
	public void setStatusReport(String statusReport) {
		this.statusReport = statusReport;
	}
	public String getFlashSms() {
		return flashSms;
	}
	public void setFlashSms(String flashSms) {
		this.flashSms = flashSms;
	}
	public String getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}
	public String getDstPort() {
		return dstPort;
	}
	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
}
