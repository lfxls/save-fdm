package cn.hexing.ami.dao.system.pojo.ggdmgl;
/** 
 * @Description  抄表数据配置
 * @author  panc
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-12-20
 * @version AMI3.0 
 */
public class Cbsjpz {

	private String sjx; // 数据项
	private String sjxmc; // 数据项名称
	private String dw; // 单位
	private String width; // 宽度
	private String xctpt; // 乘CTPT
	private String sfxs; //是否显示
	private String format; // 格式化
	private String pxbm; // 排序
	private String txid; // 图形ID
	
	public String getSjx() {
		return sjx;
	}
	public void setSjx(String sjx) {
		this.sjx = sjx;
	}
	public String getSjxmc() {
		return sjxmc;
	}
	public void setSjxmc(String sjxmc) {
		this.sjxmc = sjxmc;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getXctpt() {
		return xctpt;
	}
	public void setXctpt(String xctpt) {
		this.xctpt = xctpt;
	}
	public String getSfxs() {
		return sfxs;
	}
	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPxbm() {
		return pxbm;
	}
	public void setPxbm(String pxbm) {
		this.pxbm = pxbm;
	}
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
}

