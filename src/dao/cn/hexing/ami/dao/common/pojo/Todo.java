package cn.hexing.ami.dao.common.pojo;

import java.util.Date;


/** 
 * @Description  待办事项信息
 * @author  panc
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-16
 * @version AMI3.0 
 */
public class Todo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -636862418727746517L;
	private String id;//待办事项唯一编号
    private String appid;//应用系统ID
    private String originator;//待办事项发起人
    private String appuserid;//接收人用户ID，多个用户以;分隔
    private String apptodotype;//待办事项在应用系统中的类型
    private String apptodoid;//待办事项在该应用系统中的ID
    private String url;//待办事项的处理链接
    private String tododesc;//待办事项的显示描述
    private String status;//待办事项信息状态，0：待处理1：完成；2：撤销；3：超期
    private String priority;//待办事项信息优先级，越小优先级越高，0：紧急 1：重要 2：一般3：低，缺省2
    private Date activetime;//待办事项信息启用时间yyyy-mm-dd hh-MM-ss
    private Date rexpiretime;//待办事项信息超期时间，默认30天，在系统参数可以配置yyyy-mm-dd hh-MM-ss
    private String notice;//提醒方式 0:不提醒 2:手机短信 3:邮件提醒，空值：不提醒。多种方式组合提醒，采用逗号分割
    private String remark;//附加信息
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	public String getAppuserid() {
		return appuserid;
	}
	public void setAppuserid(String appuserid) {
		this.appuserid = appuserid;
	}
	public String getApptodotype() {
		return apptodotype;
	}
	public void setApptodotype(String apptodotype) {
		this.apptodotype = apptodotype;
	}
	public String getApptodoid() {
		return apptodoid;
	}
	public void setApptodoid(String apptodoid) {
		this.apptodoid = apptodoid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTododesc() {
		return tododesc;
	}
	public void setTododesc(String tododesc) {
		this.tododesc = tododesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getActivetime() {
		return activetime;
	}
	public void setActivetime(Date activetime) {
		this.activetime = activetime;
	}
	public Date getRexpiretime() {
		return rexpiretime;
	}
	public void setRexpiretime(Date rexpiretime) {
		this.rexpiretime = rexpiretime;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

