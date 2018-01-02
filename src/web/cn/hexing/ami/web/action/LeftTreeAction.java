package cn.hexing.ami.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.ExtTreeNode;
import cn.hexing.ami.service.LeftTreeManager;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 左边树 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-8-16
 * @version AMI3.0
 */
public class LeftTreeAction extends BaseAction {
	private static final long serialVersionUID = 8886328810119464467L;
	private String viewType;
	private String start;
	private String end;
	private String count;
	private String pages;
	private String supportTerminal;
	
	LeftTreeManager leftTreeManager = null;

	public String getSupportTerminal() {
		return supportTerminal;
	}

	public void setSupportTerminal(String supportTerminal) {
		this.supportTerminal = supportTerminal;
	}

	public void setLeftTreeManager(LeftTreeManager leftTreeManager) {
		this.leftTreeManager = leftTreeManager;
	}
	
	private List<Map<String, String>> typeList;

	public List<Map<String, String>> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Map<String, String>> typeList) {
		this.typeList = typeList;
	}
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 初始化页面
	public String init() {
		viewType = StringUtil.isEmptyString(viewType) ? "bj" : viewType;
		//默认选择第一个用户类型
		List<Object> yhlxList = super.getYhlxLs();
		/*String firstYhlx = "";
		if (yhlxList!=null && yhlxList.size()>0) {
			Map<String,Object> tmpMap = (Map<String,Object>)yhlxList.get(0);
			firstYhlx  = String.valueOf(tmpMap.get("BM"));
		}
		yhlx =  StringUtil.isEmptyString(yhlx) ? firstYhlx : yhlx;*/
		
		if("qz".equals(viewType) || "cx".equals(viewType)){
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			Map<String, String> m1 = new HashMap<String, String>();
			m1.put("BM", "bj");
			m1.put("MC", getText("left.tab.bj"));
			ls.add(m1);
			typeList = ls;
			
			/*
			Map<String, String> m = new HashMap<String, String>();
			m.put("BM", "sb");
			m.put("MC", getText("left.tab.sb"));
			ls.add(m);
			*/
//			typeList = this.getZslxLs();
		}
		
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		supportTerminal = sysMap.get("supportTerminal");
		supportTerminal = StringUtil.isEmptyString(supportTerminal)?"true":supportTerminal;
		
		//查询条件（普通和高级查询）
		Map<String, Object> queryInfo = (Map<String, Object>) request.getSession().getAttribute("queryInfo");
		if (queryInfo!=null) {
			type = String.valueOf(queryInfo.get("viewType"));
		}
		
		// 查询页面的切换赋值
//		request.setAttribute("dwdm", request.getParameter("dwdm"));
//		request.setAttribute("hh", request.getParameter("hh"));
//		request.setAttribute("hm", request.getParameter("hm"));
//		request.setAttribute("zdjh", request.getParameter("zdjh"));
//		request.setAttribute("zdljdz", request.getParameter("zdljdz"));
		
		return viewType;
	}
	
	// 初始化页面 小型预付费
	public String initS() {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		supportTerminal = sysMap.get("supportTerminal");
		supportTerminal = StringUtil.isEmptyString(supportTerminal)?"true":supportTerminal;
		
		viewType = StringUtil.isEmptyString(viewType) ? "sb" : viewType;
		
		if("cx".equals(viewType)){
			typeList = this.getZslxLs();
		}
		
		//查询条件（普通和高级查询）
		Map<String, Object> queryInfo = (Map<String, Object>) request.getSession().getAttribute("queryInfo");
		if (queryInfo!=null) {
			type = String.valueOf(queryInfo.get("viewType"));
		}
		if (viewType.equals("bj")) {
			return "leftSBj";
		}
		if (viewType.equals("sb")) {
			return "leftS";
		}
		if (viewType.equals("cx")) {
			return "leftSCx";
		}
		
		return viewType;
	}
	
	/**
	 * 获取树结构
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getViews() {
		Map<String, Object> re = new HashMap<String, Object>();
		String czyid = this.getCzyid();
		String fwbj = this.getFwbj();
		String bmid = this.getBm();
		
		if ("root".equals(nodeId)) { //根节点
			getRoot(getLang());
			return null;
		}  
		
		if ("sb".equals(viewType)) { // 设备
			re = leftTreeManager.getDw(nodeType, nodeId, czyid, fwbj, bmid, viewType,yhlx,count, pages, start, end, getLang());
		} else if ("bj".equals(viewType)) {// 表计
			re = leftTreeManager.getDw(nodeType, nodeId, czyid, fwbj, bmid, viewType,yhlx,count, pages, start, end, getLang());
		} else if (viewType.indexOf("qz") > -1) {// 群组
			re = leftTreeManager.getQz(nodeType, nodeId, yhlx, viewType,count, pages, start, end, getLang());
		} else if ("cx".equals(viewType)) {// 查询
			// 查询条件（普通和高级查询）
			Map<String, Object> queryInfo = (Map<String, Object>) request.getSession().getAttribute("queryInfo"); 
			String sbid = request.getParameter("sbid"); //反推的设备ZDJH
			if(queryInfo == null && StringUtil.isEmptyString(sbid)) {
				return null;
			}
			if(StringUtil.isEmptyString(String.valueOf(queryInfo.get("viewType")))){
				return null;
			}
			re = leftTreeManager.getCx(sbid, queryInfo, czyid, fwbj, bmid, count, pages, start, end, getLang());
		}
		if ("true".equals(count)) {
			Long c = (Long) re.get("count");
			responseText(String.valueOf(c));
		} else {
			List<ExtTreeNode> nodes = (List<ExtTreeNode>) re.get("nodes");
			responseJson(nodes, false);
		}
		return null;
	}
	
	/**
	 * 加载小型预付费树
	 * @return
	 */
	public String getViewSimple() {
		if ("root".equals(nodeId)) { //根节点
			getRootS(getLang());
			return null;
		}  
		
		Map<String, Object> re = new HashMap<String, Object>();
		String czyid = this.getCzyid();
		String fwbj = this.getFwbj();
		String bmid = this.getBm();
		
		if ("sb".equals(viewType)) { // 设备
			re = leftTreeManager.getSbS(nodeType,viewType, nodeId, czyid, fwbj, bmid,count, pages, start, end, getLang());
		} else if ("bj".equals(viewType)) {// 表计
			re = leftTreeManager.getSbS(nodeType,viewType, nodeId, czyid, fwbj, bmid,count, pages, start, end, getLang());
		} else if ("cx".equals(viewType)) {// 查询
			Map<String, Object> queryInfo = (Map<String, Object>) request.getSession().getAttribute("queryInfo"); // 查询条件（普通和高级查询）
			String sbid = request.getParameter("sbid"); //反推的设备ZDJH
			if(queryInfo == null && StringUtil.isEmptyString(sbid)) return null;
			if(StringUtil.isEmptyString(String.valueOf(queryInfo.get("viewType")))) return null;
			re = leftTreeManager.getCxS(sbid, queryInfo, czyid, fwbj, bmid, count, pages, start, end, getLang());
		}
		
		if ("true".equals(count)) {
			Long c = (Long) re.get("count");
			responseText(String.valueOf(c));
		} else {
			List<ExtTreeNode> nodes = (List<ExtTreeNode>) re.get("nodes");
			responseJson(nodes, false);
		}
		return null;
	}
	
	// 把查询条件放入缓存
	public ActionResult setQueryInfo(Map<String,String> param,Util util){
		WebContext wctx = WebContextFactory.get();
		HttpSession session = wctx.getHttpServletRequest().getSession();
		session.removeAttribute("queryInfo");
		Map<String, Object> queryInfo = this.setQueryInfo(param);
		session.setAttribute("queryInfo", queryInfo);
		ActionResult re = new ActionResult(true,"");
		return re;
	}
	
	/**
	 * 保存查询条件
	 * @param parameter
	 * @return
	 */
	private Map<String, Object> setQueryInfo(Map<String, String> query) {
		Map<String, Object> m = new HashMap<String, Object>();
		// 用户类型和展示类型
		String viewType = String.valueOf(getValue(query,"type",false)); //这里的viewType是前台下拉框传入的表计或终端查询
		m.put("yhlx", getValue(query,"yhlx",false));
		m.put("viewType", viewType);
		
		m.put("dwdm", "-1".equals(query.get("dwdm")) ? null : getValue(query,"dwdm",false));
		// 查询
		m.put("tqmc", getValue(query,"tqmc",false));
		
		//终端的时候去掉表计信息，防止session内容冲突，造成查询错误 20121212 edit by jun
		if (viewType.equals("bj")) {
			m.put("hm", getValue(query,"hm",false));
			m.put("hh", getValue(query,"hh",false));
			m.put("bjjh", getValue(query,"bjjh",false));
			m.put("bjtxdz", getValue(query,"bjtxdz",false));
		}else{
			m.remove("hm");
			m.remove("hh");
			m.remove("bjjh");
			m.remove("bjtxdz");
		}
		
		m.put("zdjh", getValue(query,"zdjh",false));
		m.put("zdljdz", getValue(query,"zdljdz",false));
		
		// 高级查询
		m.put("xlmc",  getValue(query,"xlmc",false));
		m.put("advhh", getValue(query,"hhs",true));
		m.put("advzdjh", getValue(query,"advzdjhs",true));
		m.put("advzdljdz", getValue(query,"zdljdzs",true));
		m.put("advbjjh", getValue(query,"bjjhs",true));
		m.put("simkh", getValue(query,"simkh",true));
		m.put("zdgylx", "-1".equals(query.get("zdgylx")) ? null : getValue(query,"zdgylx",false));
		m.put("zdzt", getValue(query,"zdzt",true));
		m.put("sdrl", "-1".equals(query.get("sdrl")) ? null : getValue(query,"sdrl",false));
		m.put("zdysdrl", getValue(query,"zdysdrl",false));
		m.put("minrl", getValue(query,"minrl",false));
		m.put("maxrl", getValue(query,"maxrl",false));
		m.put("dydj", getValue(query,"dydj",true));
		m.put("zdjhFrom", getValue(query,"zdjhFrom",false));
		m.put("zdjhTo", getValue(query,"zdjhTo",false));
		
		return m;
	}
	
	
	// 把查询条件放入缓存
	public ActionResult setQueryInfoS(Map<String,String> param,Util util){
		WebContext wctx = WebContextFactory.get();
		HttpSession session = wctx.getHttpServletRequest().getSession();
		session.removeAttribute("queryInfo");
		Map<String, Object> queryInfo = this.setQueryInfoS(param);
		session.setAttribute("queryInfo", queryInfo);
		ActionResult re = new ActionResult(true,"");
		return re;
	}
	
	/**
	 * 保存查询条件(小型预付费)
	 * @param parameter
	 * @return
	 */
	private Map<String, Object> setQueryInfoS(Map<String, String> query) {
		Map<String, Object> m = new HashMap<String, Object>();
		// 用户类型和展示类型
		String viewType = String.valueOf(getValue(query,"type",false));
		m.put("yhlx", getValue(query,"yhlx",false));
		m.put("viewType", viewType);
		
		m.put("dwdm", "-1".equals(query.get("dwdm")) ? null : getValue(query,"dwdm",false));
		m.put("bjjh", getValue(query,"bjjh",false));
		return m;
	}
	
	private Object getValue(Map<String, String> m, String key, boolean split) {
		String o = m.get(key);
		if(o == null || StringUtil.isEmptyString(o)) {
			return null;
		} 
		if(split) {
			return o.split(",");
		}
		return o;
	}
	
	/**
	 * 高级查询区域条件
	 * @param parameter
	 * @param doType
	 * @param util
	 * @return
	 */
	public ActionResult getAdvList(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult(true,"");
		Map<String,Object> p = new HashMap<String,Object>();
		p.putAll(param);
		p.put("nodeId", StringUtil.isEmptyString(param.get("dwdm"))?param.get(Constants.UNIT_CODE):param.get("dwdm"));
		DatabaseUtil.fwdwFilter(p, param.get(Constants.CURR_STAFFID), param.get(Constants.CURR_RIGTH), param.get(Constants.CURR_DEPT), "DW");
		
		List<Object> ls = leftTreeManager.getAdvList(p);
		if("dw".equals(param.get("doType"))) {
			util.removeAllOptions("sjdwdm");
			util.addOptions("sjdwdm", ls, "DWBM", "DWMC");
		} else {
			util.removeAllOptions("dwdm");
			util.addOptions("dwdm", ls, "DWBM", "DWMC");
		}
		return re;
	}
	
	/**
	 * 获取根节点
	 */
	private void getRoot(String lang) {
		ExtTreeNode root = null;
		if ("sb".equals(viewType)) { //根节点
			root = new ExtTreeNode(this.getUnitCode(), this.getUnitName(), "dw", viewType, false, true);
			root.setDwlx(this.getUnitLevel());
			root.setDwdm(this.getUnitCode());
		} else if ("bj".equals(viewType)) {// 表计
			root = new ExtTreeNode(this.getUnitCode(), this.getUnitName(), "dw", viewType, false, true);
			root.setDwlx(this.getUnitLevel());
		} else if (viewType.indexOf("qz") > -1) {// 群组
			//默认名称
			String name =  I18nUtil.getText("common.qz.root.bjgroup", lang);
			if("02".equals(yhlx)){
				name = I18nUtil.getText("common.qz.root.gb", lang);
			}else if("03".equals(yhlx)){
				name = I18nUtil.getText("common.qz.root.dy", lang);
			}
			root = new ExtTreeNode(this.getCzyid(), name, "qzRoot", viewType, false, true);
		} else { // 查询
			root = new ExtTreeNode("queryRoot", I18nUtil.getText("left.cx.cxjg", lang), "cx", viewType, false, true);
		}
		root.setExpanded(true);
		root.setYhlx(yhlx);
		List<ExtTreeNode> ls = new ArrayList<ExtTreeNode>();
		ls.add(root);
		responseJson(ls, false);
	}
	
	/**
	 * 获取根节点-小型预付费
	 */
	private void getRootS(String lang) {
		ExtTreeNode root = null;
		if ("sb".equals(viewType)) { //根节点
			//根节点
			root = new ExtTreeNode(this.getUnitCode(), this.getUnitName(), "dw", viewType, false, true);
			root.setDwlx(this.getUnitLevel());
			root.setDwdm(this.getUnitCode());
		}else if ("bj".equals(viewType)) {// 表计
			//根节点
			root = new ExtTreeNode(this.getUnitCode(), this.getUnitName(), "dw", viewType, false, true);
		}else{//查询
			//根节点
			root = new ExtTreeNode("queryRoot", I18nUtil.getText("left.cx.cxjg", lang), "cx", viewType, false, true);
		}
		
		//自动展开
		root.setExpanded(true);
		List<ExtTreeNode> ls = new ArrayList<ExtTreeNode>();
		ls.add(root);
		responseJson(ls, false);
	}
	/**
	 * 检查右键菜单访问权限 dwr
	 * @param parameter
	 * @param doType
	 * @param util
	 * @return
	 */
	public ActionResult getQxcd(Map<String, String> parameter, Util util) {
		boolean access = leftTreeManager.getCdqx(parameter.get(Constants.CURR_STAFFID), parameter.get("actionName"));
		ActionResult re = new ActionResult(access,"");
		return re;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}
	
	private List<Object> zdgyls,dydjls,sdrlls,zdztls;
	
	public List<Object> getZdgyls() {
		return zdgyls;
	}

	public void setZdgyls(List<Object> zdgyls) {
		this.zdgyls = zdgyls;
	}

	public List<Object> getDydjls() {
		return dydjls;
	}

	public void setDydjls(List<Object> dydjls) {
		this.dydjls = dydjls;
	}

	public List<Object> getSdrlls() {
		return sdrlls;
	}

	public void setSdrlls(List<Object> sdrlls) {
		this.sdrlls = sdrlls;
	}

	public List<Object> getZdztls() {
		return zdztls;
	}

	public void setZdztls(List<Object> zdztls) {
		this.zdztls = zdztls;
	}

	/**
	 * 高级查询初始化
	 * 
	 * @return
	 */
	public String initAdv() {
		yhlx = StringUtil.isEmptyString(yhlx) ? "01" : yhlx;
		type = StringUtil.isEmptyString(type) ? "sb" : type;
		zdgyls = CommonUtil.getCode("zdgylx",this.getLang(), true);
		dydjls = CommonUtil.getCode("dydj",this.getLang(), false);
		sdrlls = CommonUtil.getCode("sdrldj",this.getLang(), true);
		zdztls = CommonUtil.getCode("zdzt",this.getLang(), false);
		return "adv";
	}
}
