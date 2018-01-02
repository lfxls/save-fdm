package cn.hexing.ami.dao.common.pojo;

public class CsxTreeNode {
	private String id;
	private String ndType;
	private String text;
	private String gylx;
	private String dxbz;
	private String cd;
	private boolean leaf;
	private boolean expanded;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNdType() {
		return ndType;
	}
	public void setNdType(String ndType) {
		this.ndType = ndType;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGylx() {
		return gylx;
	}
	public void setGylx(String gylx) {
		this.gylx = gylx;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getDxbz() {
		return dxbz;
	}
	public void setDxbz(String dxbz) {
		this.dxbz = dxbz;
	}
}
