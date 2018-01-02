package cn.hexing.ami.dao.common.pojo;


public class TreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968881488535980134L;

	public String id;
	public String text;
	public String ndType;
	public String icon;
	public boolean leaf;
	public boolean expanded;
	public String qtip;
	public String iconCls;
	public String info;
	 
	public TreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TreeNode(String id, String text, String ndType, boolean leaf) {
		super();
		this.id = id;
		this.text = text;
		this.ndType = ndType;
		this.leaf = leaf;
	}
			
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getNdType() {
		return ndType;
	}
	public void setNdType(String ndType) {
		this.ndType = ndType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
