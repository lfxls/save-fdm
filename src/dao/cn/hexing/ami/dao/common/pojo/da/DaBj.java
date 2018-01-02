package cn.hexing.ami.dao.common.pojo.da;

import java.util.Date;

/**
 * 档案表计
 * 
 * @author ycl
 * ,bjlx.name bjlxmc , bjms.name bjmsmc , dbgylx.name txgymc , zd.csdzt , zd.apn , zd.simkh , zd.zddxzxhm
           	   ,yh.hh , yh.hm , yh.yhdz, yhlxr.lxr ,yhlxr.SJHM
 */
public class DaBj implements java.io.Serializable{
	private static final long serialVersionUID = -8916581766639955706L;
	private String bjjh;
	private String hh;
	private String dwmc;
	private String dwdm;
	private String byqmc;
	private String byqid;
	private String xlmc;
	private String xlid;
	private String bjtxm;
	private String bjlx;
	private String zt;
	private String ct;
	private String pt;
	private String zsbl;
	private String txgy;
	private String txdz;
	private String txfs;
	private String txdkh;
	private String btl;
	private String mccs;
	private String jlfs;
	private String jxfs;
	private String ws;
	private String xsws;
	private String csbm;
	private String blb;
	private String bxh;
	private String syyt;
	private Date zcrq;
	private Date chrq;
	private String eddy;
	private String eddl;
	private String ccbh;
	private String cqbz;
	private String bjms;
	private String yffjz;
	private String tariffProgramId;
	private String sdrl;
	private String tariffProgramMc;
	private String gmm;
	private String dqxmm;
	private String gqxmm;
	private String dlmsSub;
	private String zbfs;
	private String daly;
	private String jd;
	private String wd;
	private String zblx;
	private String qfid;
	private String byqfxlid;
	private String byqfxlmc;
	private String rl;
	private String azdz;
	private String mc;
	private String byqzt;
	private String byqztmc;
	private String gjmc;
	private String sgcId;
	private String ti;
	private String kv;
	private String kr;
	private String bjlxmc ,bjmsmc ,  txgymc , csdzt , apn , simkh , zddxzxhm
	   , hm , yhdz, lxr ,sjhm;
	private String cjqjh;
	private String sfrfb;
	private String bjazdz;
	private String cbxh;

	//CAI大用户增加费率分组字段 yaoj
	private String tariffGroupId,voltageGroup;

	public String getQfid() {
		return qfid;
	}

	public void setQfid(String qfid) {
		this.qfid = qfid;
	}

	public String getByqfxlmc() {
		return byqfxlmc;
	}

	public void setByqfxlmc(String byqfxlmc) {
		this.byqfxlmc = byqfxlmc;
	}

	public String getGmm() {
		return gmm;
	}

	public void setGmm(String gmm) {
		this.gmm = gmm;
	}

	public String getDqxmm() {
		return dqxmm;
	}

	public void setDqxmm(String dqxmm) {
		this.dqxmm = dqxmm;
	}

	public String getGqxmm() {
		return gqxmm;
	}

	public void setGqxmm(String gqxmm) {
		this.gqxmm = gqxmm;
	}

	public String getTariffProgramMc() {
		return tariffProgramMc;
	}

	public void setTariffProgramMc(String tariffProgramMc) {
		this.tariffProgramMc = tariffProgramMc;
	}

	public String getSdrl() {
		return sdrl;
	}

	public void setSdrl(String sdrl) {
		this.sdrl = sdrl;
	}

	public String getTariffProgramId() {
		return tariffProgramId;
	}

	public void setTariffProgramId(String tariffProgramId) {
		this.tariffProgramId = tariffProgramId;
	}

	public String getYffjz() {
		return yffjz;
	}

	public void setYffjz(String yffjz) {
		this.yffjz = yffjz;
	}

	public String getBjms() {
		return bjms;
	}

	public void setBjms(String bjms) {
		this.bjms = bjms;
	}

	public String getBjjh() {
		return bjjh;
	}

	public void setBjjh(String bjjh) {
		this.bjjh = bjjh;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getByqid() {
		return byqid;
	}

	public void setByqid(String byqid) {
		this.byqid = byqid;
	}

	public String getXlmc() {
		return xlmc;
	}

	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}

	public String getXlid() {
		return xlid;
	}

	public void setXlid(String xlid) {
		this.xlid = xlid;
	}

	public String getBjtxm() {
		return bjtxm;
	}

	public void setBjtxm(String bjtxm) {
		this.bjtxm = bjtxm;
	}

	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getZsbl() {
		return zsbl;
	}

	public void setZsbl(String zsbl) {
		this.zsbl = zsbl;
	}

	public String getTxgy() {
		return txgy;
	}

	public void setTxgy(String txgy) {
		this.txgy = txgy;
	}

	public String getTxdz() {
		return txdz;
	}

	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}

	public String getTxfs() {
		return txfs;
	}

	public void setTxfs(String txfs) {
		this.txfs = txfs;
	}

	public String getTxdkh() {
		return txdkh;
	}

	public void setTxdkh(String txdkh) {
		this.txdkh = txdkh;
	}

	public String getBtl() {
		return btl;
	}

	public void setBtl(String btl) {
		this.btl = btl;
	}

	public String getMccs() {
		return mccs;
	}

	public void setMccs(String mccs) {
		this.mccs = mccs;
	}

	public String getJlfs() {
		return jlfs;
	}

	public void setJlfs(String jlfs) {
		this.jlfs = jlfs;
	}

	public String getJxfs() {
		return jxfs;
	}

	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getXsws() {
		return xsws;
	}

	public void setXsws(String xsws) {
		this.xsws = xsws;
	}

	public String getCsbm() {
		return csbm;
	}

	public void setCsbm(String csbm) {
		this.csbm = csbm;
	}

	public String getBlb() {
		return blb;
	}

	public void setBlb(String blb) {
		this.blb = blb;
	}

	public String getBxh() {
		return bxh;
	}

	public void setBxh(String bxh) {
		this.bxh = bxh;
	}

	public String getSyyt() {
		return syyt;
	}

	public void setSyyt(String syyt) {
		this.syyt = syyt;
	}

	public Date getZcrq() {
		return zcrq;
	}

	public void setZcrq(Date zcrq) {
		this.zcrq = zcrq;
	}

	public Date getChrq() {
		return chrq;
	}

	public void setChrq(Date chrq) {
		this.chrq = chrq;
	}

	public String getEddy() {
		return eddy;
	}

	public void setEddy(String eddy) {
		this.eddy = eddy;
	}

	public String getEddl() {
		return eddl;
	}

	public void setEddl(String eddl) {
		this.eddl = eddl;
	}

	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}

	public String getCqbz() {
		return cqbz;
	}

	public void setCqbz(String cqbz) {
		this.cqbz = cqbz;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getByqmc() {
		return byqmc;
	}

	public void setByqmc(String byqmc) {
		this.byqmc = byqmc;
	}

	public String getDlmsSub() {
		return dlmsSub;
	}

	public void setDlmsSub(String dlmsSub) {
		this.dlmsSub = dlmsSub;
	}

	public String getZbfs() {
		return zbfs;
	}

	public void setZbfs(String zbfs) {
		this.zbfs = zbfs;
	}

	public String getDaly() {
		return daly;
	}

	public void setDaly(String daly) {
		this.daly = daly;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getZblx() {
		return zblx;
	}

	public void setZblx(String zblx) {
		this.zblx = zblx;
	}

	public String getByqfxlid() {
		return byqfxlid;
	}

	public void setByqfxlid(String byqfxlid) {
		this.byqfxlid = byqfxlid;
	}

	public String getRl() {
		return rl;
	}

	public void setRl(String rl) {
		this.rl = rl;
	}

	public String getAzdz() {
		return azdz;
	}

	public void setAzdz(String azdz) {
		this.azdz = azdz;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getByqzt() {
		return byqzt;
	}

	public void setByqzt(String byqzt) {
		this.byqzt = byqzt;
	}

	public String getByqztmc() {
		return byqztmc;
	}

	public void setByqztmc(String byqztmc) {
		this.byqztmc = byqztmc;
	}

	public String getGjmc() {
		return gjmc;
	}

	public void setGjmc(String gjmc) {
		this.gjmc = gjmc;
	}

	public String getSgcId() {
		return sgcId;
	}

	public void setSgcId(String sgcId) {
		this.sgcId = sgcId;
	}

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getKv() {
		return kv;
	}

	public void setKv(String kv) {
		this.kv = kv;
	}

	public String getKr() {
		return kr;
	}

	public void setKr(String kr) {
		this.kr = kr;
	}

	public String getBjlxmc() {
		return bjlxmc;
	}

	public void setBjlxmc(String bjlxmc) {
		this.bjlxmc = bjlxmc;
	}

	public String getBjmsmc() {
		return bjmsmc;
	}

	public void setBjmsmc(String bjmsmc) {
		this.bjmsmc = bjmsmc;
	}

	public String getTxgymc() {
		return txgymc;
	}

	public void setTxgymc(String txgymc) {
		this.txgymc = txgymc;
	}

	public String getCsdzt() {
		return csdzt;
	}

	public void setCsdzt(String csdzt) {
		this.csdzt = csdzt;
	}

	public String getApn() {
		return apn;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public String getSimkh() {
		return simkh;
	}

	public void setSimkh(String simkh) {
		this.simkh = simkh;
	}

	public String getZddxzxhm() {
		return zddxzxhm;
	}

	public void setZddxzxhm(String zddxzxhm) {
		this.zddxzxhm = zddxzxhm;
	}

	public String getHm() {
		return hm;
	}

	public void setHm(String hm) {
		this.hm = hm;
	}

	public String getYhdz() {
		return yhdz;
	}

	public void setYhdz(String yhdz) {
		this.yhdz = yhdz;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getTariffGroupId() {
		return tariffGroupId;
	}

	public void setTariffGroupId(String tariffGroupId) {
		this.tariffGroupId = tariffGroupId;
	}

	public String getVoltageGroup() {
		return voltageGroup;
	}

	public void setVoltageGroup(String voltageGroup) {
		this.voltageGroup = voltageGroup;
	}

	public String getCjqjh() {
		return cjqjh;
	}

	public void setCjqjh(String cjqjh) {
		this.cjqjh = cjqjh;
	}

	public String getSfrfb() {
		return sfrfb;
	}

	public void setSfrfb(String sfrfb) {
		this.sfrfb = sfrfb;
	}

	public String getBjazdz() {
		return bjazdz;
	}

	public void setBjazdz(String bjazdz) {
		this.bjazdz = bjazdz;
	}

	public String getCbxh() {
		return cbxh;
	}

	public void setCbxh(String cbxh) {
		this.cbxh = cbxh;
	}
}
