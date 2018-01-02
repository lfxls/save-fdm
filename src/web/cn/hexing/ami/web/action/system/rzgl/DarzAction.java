package cn.hexing.ami.web.action.system.rzgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.system.rzgl.DarzManagerInf;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 档案日志action
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class DarzAction extends BaseAction implements QueryInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707628906537889958L;

	DarzManagerInf darzManager = null;

	public void setDarzManager(DarzManagerInf darzManager) {
		this.darzManager = darzManager;
	}

	List<Object> menus = null;
	private String menuId, czyId, kssj, jssj, rznr, sjcdbm;

	public String init() {
//		menus = darzManager.getRootMenu(this.getLang());  DEL PANC 2012/6/5 改修
		kssj = DateUtil.getYesterday();
		jssj = DateUtil.getToday();
		return SUCCESS;
	}

//	public ActionResult getMenu(Map<String, String> param, Util util) {  DEL PANC 2012/6/4 改修
//		String root = param.get("root");
//		String lang = param.get(Constants.APP_LANG);
//		List<Object> ls = darzManager.getMenu(root, lang);
//		util.removeAllOptions("menuId");
//		util.addOptions("menuId", ls, "BM", "MC");
//		return null;
//	}
	
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kssj", kssj);
		param.put("jssj", jssj);
		param.put("lang", this.getLang());
		param.put("menuId", menuId);
		if (!StringUtil.isEmptyString(czyId))
			param.put("czyId", czyId.trim());
		if (!StringUtil.isEmptyString(rznr))
			param.put("rznr", rznr);
		Map<String, Object> re = darzManager.query(param, start, limit, dir,
				sort, isExcel);
		responseGrid(re, getExcelHead(), "darz", "darz");
		return null;
	}

	/**
	 * 菜单树
	 * @return
	 * @throws Exception
	 */
	public String getCdTree() throws Exception {
		List<Map<String, Object>> ls = darzManager.getCdTree(sjcdbm, this.getLang());
		responseJson(ls, false);
		return null;
	}
	
	public List<String> getExcelHead() {
		List<String> ls = new ArrayList<String>();
		ls.add("DWMC,单位"); // 用properties 作国际化
		ls.add("CZY,操作员");
		ls.add("CDMC,菜单");
		ls.add("CZLB,操作类别");
		ls.add("CZMC,操作");
		ls.add("CZSJ,操作时间");
		ls.add("RZNR,日志内容");
		return ls;
	}

	/**
	 * 菜单树
	 * @return
	 */
	public String getTree() {
		return "cdTree";
	}
	
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getMenus() {
		return menus;
	}

	public void setMenus(List<Object> menus) {
		this.menus = menus;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getCzyId() {
		return czyId;
	}

	public void setCzyId(String czyId) {
		this.czyId = czyId;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getRznr() {
		return rznr;
	}

	public void setRznr(String rznr) {
		this.rznr = rznr;
	}

	public String getSjcdbm() {
		return sjcdbm;
	}

	public void setSjcdbm(String sjcdbm) {
		this.sjcdbm = sjcdbm;
	}

}
