package cn.hexing.ami.dao.common.pojo;

import java.util.HashMap;
import java.util.Map;

public class XtCd {
	private String id;
	private String iconCls;
	private String text;
	private String url;
	private String isLeaf;
	private Map<String, Object> menu = new HashMap<String, Object>();
	private String handler;
	private String pxbm;
	private String lang;
	private String sfgjd;
	//父菜单id
	private String pid;
	//系统IDS
	private String xtids;

	public String getXtids() {
		return xtids;
	}

	public void setXtids(String xtids) {
		this.xtids = xtids;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSfgjd() {
		return sfgjd;
	}

	public void setSfgjd(String sfgjd) {
		this.sfgjd = sfgjd;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Map<String, Object> getMenu() {
		return menu;
	}

	public void setMenu(Map<String, Object> menu) {
		this.menu = menu;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getPxbm() {
		return pxbm;
	}

	public void setPxbm(String pxbm) {
		this.pxbm = pxbm;
	}

	public XtCd(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public XtCd() {
	}

}
