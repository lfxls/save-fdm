package cn.hexing.ami.dao.common.pojo.da;


/**
 * 水表档案
 * 
 * @author shiy
 * 
 */
public class DaSb implements java.io.Serializable{
	private static final long serialVersionUID = 8670248650833907093L;
	private String sbjh;//水表局号
	private String dwdm;//单位
	private String dwmc;//单位名称
	private String bjjh;//电表局号
	private String bjlx;//表计类型
	private String csbm;//厂商
	private String zt;//表计状态
	private String azrq;//安装日期
	private String cbfs;//抄表方式
	private String txdk;//通讯端口
	private String hh;//户号
	private String yffjz;//预付费介质
	private String tariffProgramId;//费率方案
	private String tariffProgramMC;//费率名称
	private String bjms;//表计模式
	
	public String getYffjz() {
		return yffjz;
	}
	public void setYffjz(String yffjz) {
		this.yffjz = yffjz;
	}
	public String getTariffProgramId() {
		return tariffProgramId;
	}
	public void setTariffProgramId(String tariffProgramId) {
		this.tariffProgramId = tariffProgramId;
	}
	
	public String getTariffProgramMC() {
		return tariffProgramMC;
	}
	public void setTariffProgramMC(String tariffProgramMC) {
		this.tariffProgramMC = tariffProgramMC;
	}
	public String getBjms() {
		return bjms;
	}
	public void setBjms(String bjms) {
		this.bjms = bjms;
	}
	public String getHh() {
		return hh;
	}
	public void setHh(String hh) {
		this.hh = hh;
	}
	public String getSbjh() {
		return sbjh;
	}
	public void setSbjh(String sbjh) {
		this.sbjh = sbjh;
	}
	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getBjjh() {
		return bjjh;
	}
	public void setBjjh(String bjjh) {
		this.bjjh = bjjh;
	}
	public String getBjlx() {
		return bjlx;
	}
	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}
	public String getCsbm() {
		return csbm;
	}
	public void setCsbm(String csbm) {
		this.csbm = csbm;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getAzrq() {
		return azrq;
	}
	public void setAzrq(String azrq) {
		this.azrq = azrq;
	}
	public String getCbfs() {
		return cbfs;
	}
	public void setCbfs(String cbfs) {
		this.cbfs = cbfs;
	}
	public String getTxdk() {
		return txdk;
	}
	public void setTxdk(String txdk) {
		this.txdk = txdk;
	}
	
}