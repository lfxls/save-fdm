package cn.hexing.ami.dao.common.pojo;
/** 
 * @Description 菜单关联的操作
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-17
 * @version AMI3.0 
 */
public class CdCz implements java.io.Serializable{
	private static final long serialVersionUID = -2656542787576138458L;
	private String czid;
	private String czlbid;
	private String czmc;
	private String cdid;
	private String sfxs;
	private String pxbm;
	private String lang;
	public String getCzlbid() {
		return czlbid;
	}
	public void setCzlbid(String czlbid) {
		this.czlbid = czlbid;
	}
	public String getCzid() {
		return czid;
	}
	public void setCzid(String czid) {
		this.czid = czid;
	}
	public String getCzmc() {
		return czmc;
	}
	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}
	public String getCdid() {
		return cdid;
	}
	public void setCdid(String cdid) {
		this.cdid = cdid;
	}
	public String getSfxs() {
		return sfxs;
	}
	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}
	public String getPxbm() {
		return pxbm;
	}
	public void setPxbm(String pxbm) {
		this.pxbm = pxbm;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
}
