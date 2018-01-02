package cn.hexing.ami.dao.system.pojo.sygl;

import java.io.Serializable;

/** 
 * @Description  首页管理实体类
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-13
 * @version AMI3.0 
 */
public class Sygl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3076213557852116063L;
	//首页ID
	private String syid;
	//首页名称
	private String symc;	
	//URL
	private String url;
	//系统默认标志
	private String xtmr;
	//描述
	private String ms;
	//语言
	private String lang;
	
    public String getSyid() {
		return syid;
	}
	public void setSyid(String syid) {
		this.syid = syid;
	}
	public String getSymc() {
		return symc;
	}
	public void setSymc(String symc) {
		this.symc = symc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getXtmr() {
		return xtmr;
	}
	public void setXtmr(String xtmr) {
		this.xtmr = xtmr;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}

