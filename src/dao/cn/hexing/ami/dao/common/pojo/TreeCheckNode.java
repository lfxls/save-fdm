package cn.hexing.ami.dao.common.pojo;

import java.util.List;


public class TreeCheckNode extends TreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2320589795081982266L;

	private boolean checked;
	private List<?> children;
	
	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public TreeCheckNode(String id, String text, String ndType, boolean  leaf) {
		super(id, text, ndType, leaf);
		this.expanded = !leaf;
	}
	
	

}
