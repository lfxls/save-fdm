package cn.hexing.ami.dao.common.pojo.da;

import java.util.Date;

/** 
 * @Description  勘查工单明细对象
 * @author  zhouh
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-7-17
 * @version MDM
 */
public class KcGdmx implements java.io.Serializable{

	private static final long serialVersionUID = -8086625534705160396L;
	private String kcid; //唯一性ID
	private String gdbh; //工单编号
	private String hh; //户号
	private String jzqh; //集中器号
	private String bjjh; //表计局号
	private String byqid; //变压器ID
	private String kcdz; //勘查地址
	private String ywlx; //业务类型
	private String yssjid; //业务模块ID
	private String zt; //状态
	private String gdnr; //工单内容
	private String gdfknr; //工单反馈内容
	private String czyid; //操作员ID
	private Date gxsj; //更新时间
	
	public String getKcid() {
		return kcid;
	}
	public void setKcid(String kcid) {
		this.kcid = kcid;
	}
	public String getGdbh() {
		return gdbh;
	}
	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}
	public String getHh() {
		return hh;
	}
	public void setHh(String hh) {
		this.hh = hh;
	}
	public String getJzqh() {
		return jzqh;
	}
	public void setJzqh(String jzqh) {
		this.jzqh = jzqh;
	}
	public String getBjjh() {
		return bjjh;
	}
	public void setBjjh(String bjjh) {
		this.bjjh = bjjh;
	}
	public String getByqid() {
		return byqid;
	}
	public void setByqid(String byqid) {
		this.byqid = byqid;
	}
	public String getKcdz() {
		return kcdz;
	}
	public void setKcdz(String kcdz) {
		this.kcdz = kcdz;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getYssjid() {
		return yssjid;
	}
	public void setYssjid(String yssjid) {
		this.yssjid = yssjid;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getGdnr() {
		return gdnr;
	}
	public void setGdnr(String gdnr) {
		this.gdnr = gdnr;
	}
	public String getGdfknr() {
		return gdfknr;
	}
	public void setGdfknr(String gdfknr) {
		this.gdfknr = gdfknr;
	}
	public String getCzyid() {
		return czyid;
	}
	public void setCzyid(String czyid) {
		this.czyid = czyid;
	}
	public Date getGxsj() {
		return gxsj;
	}
	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}
}
