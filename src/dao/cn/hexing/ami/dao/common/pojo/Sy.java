package cn.hexing.ami.dao.common.pojo;
/** 
 * @Description 首页信息
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-12
 * @version AMI3.0 
 */
public class Sy implements java.io.Serializable{
	private static final long serialVersionUID = 6926604030783365813L;
	private String syid;
	private String url;
	private String ms;
	private String symc;
	public String getSymc() {
		return symc;
	}
	public void setSymc(String symc) {
		this.symc = symc;
	}
	public String getSyid() {
		return syid;
	}
	public void setSyid(String syid) {
		this.syid = syid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
}
