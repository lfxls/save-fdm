package cn.hexing.ami.dao.common.pojo;

public class ExtTreeNode extends TreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843389567647074825L;
	// 对象区类型 dw sb qz cx
	private String viewType;
	private String start;
	private String end;
	private boolean pages;

	// 电网类型(10,20,30)
	private String dwlx;

	// 终端局号
	private String zdjh;
	// 单位代码
	private String dwdm;
	// 单位代码
	private String dwmc;
	// 逻辑地址
	private String zdljdz;
	// 状态
	private String zt;
	// 支线标志
	private String zxbz;
	// 规约类型
	private String zdgylx;
	// 操作员ID
	private String czyId;
	
	//操作员卡号
	//private String czykh;
	//业务卡号
	//private String ywkh;
	// 户号
	private String hh;
	// 户名
	private String hm;
	// 终端类型
	private String zdlx;
	// sim卡号
	private String simkh;
	
	//用户类型
	private String yhlx;
	//变压器ID
	private String byqid;
	//线路台区关系
	private String xltqsl;
	//通用传值对象
	private Object objectValue;
	//终端用途 专变/公变/电压
	private String zdyt;
	//表计类型
	private String bjlx;
	//变压器名称
	private String byqmc;
	
	public String getByqmc() {
		return byqmc;
	}

	public void setByqmc(String byqmc) {
		this.byqmc = byqmc;
	}

	private int xh;
	
	public String getBjlx() {
		return bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public String getZdyt() {
		return zdyt;
	}

	public void setZdyt(String zdyt) {
		this.zdyt = zdyt;
	}

	public Object getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;
	}

	public String getXltqsl() {
		return xltqsl;
	}

	public void setXltqsl(String xltqsl) {
		this.xltqsl = xltqsl;
	}

	public String getByqid() {
		return byqid;
	}

	public void setByqid(String byqid) {
		this.byqid = byqid;
	}

	public String getYhlx() {
		return yhlx;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getZdgylx() {
		return zdgylx;
	}

	public void setZdgylx(String zdgylx) {
		this.zdgylx = zdgylx;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getZdjh() {
		return zdjh;
	}

	public void setZdjh(String zdjh) {
		this.zdjh = zdjh;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public boolean isPages() {
		return pages;
	}

	public void setPages(boolean pages) {
		this.pages = pages;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getCzyId() {
		return czyId;
	}

	public void setCzyId(String czyId) {
		this.czyId = czyId;
	}

	public ExtTreeNode(String id, String text, String ndType, String viewType,
			boolean leaf, boolean pages) {
		super();
		this.id = id;
		this.text = text;
		this.ndType = ndType;
		this.viewType = viewType;
		this.leaf = leaf;
		this.pages = pages;
		this.expanded = false;
	}

	public String getZdljdz() {
		return zdljdz;
	}

	public void setZdljdz(String zdljdz) {
		this.zdljdz = zdljdz;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getHm() {
		return hm;
	}

	public void setHm(String hm) {
		this.hm = hm;
	}

	public String getZdlx() {
		return zdlx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getSimkh() {
		return simkh;
	}

	public void setSimkh(String simkh) {
		this.simkh = simkh;
	}

	public String getZxbz() {
		return zxbz;
	}

	public void setZxbz(String zxbz) {
		this.zxbz = zxbz;
	}

	public int getXh() {
		return xh;
	}

	public void setXh(int xh) {
		this.xh = xh;
	}
}
