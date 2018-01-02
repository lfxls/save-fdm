package cn.hexing.ami.service.system.qxgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MenuUtil;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 角色管理manager
 * @author ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class JsglManager implements JsglManagerInf {

	BaseDAOIbatis baseDAOIbatis = null;
	private static String menuId = "52200";
	private String sqlId = "jsgl.";

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 取角色菜单树
	 * 
	 * @param jsid
	 * @param sjcdb
	 * @param menurole
	 * @param lang
	 * @return
	 */
	public List<TreeCheckNode> getJsCdTree(String jsid, String sjcdbm,HashMap<XtCd,String> menurole,String lang) {
		//所有菜单
		List<XtCd> menuItems = new ArrayList<XtCd>();
		
		for (Iterator iterator = menurole.keySet().iterator(); iterator.hasNext();) {
			XtCd menu = (XtCd) iterator.next();
			//菜单角色关联关系
			String mroles = String.valueOf(menurole.get(menu));
			//菜单语言
			String menuLang = menu.getLang()==null?"":menu.getLang();
			
			//包含该角色，上级菜单的对应下级菜单
			if (menuLang.equals(lang) && 
					((sjcdbm.equals("root") && menu.getPid()==null) || sjcdbm.equals(menu.getPid()))) {
				menuItems.add(menu);
			}
		}
		Collections.sort(menuItems, MenuUtil.comp);
		
		List<TreeCheckNode> nodes = new ArrayList<TreeCheckNode>();
		if (menuItems != null && menuItems.size() > 0) {
			for (XtCd cd:menuItems) {
				String leaf = cd.getIsLeaf();
				String cdid = cd.getId();
				String cdmc = cd.getText();
				String checked = getTreeCheckNode(jsid,menurole,cd);
				TreeCheckNode tcn = new TreeCheckNode(cdid, cdmc, "cd", "1"
						.equals(leaf));
				tcn.setChecked("1".equals(checked));
				if ("0".equals(leaf)) {
					tcn.setChildren(getJsCdTree(jsid, cdid,menurole,lang));
				}
				nodes.add(tcn);
			}
		}
		return nodes;
	}
	
	/**
	 * 判断当前所选角色对菜单是否有权限
	 * @param jsid
	 * @param menurole
	 * @param cd
	 * @return
	 */
	private String getTreeCheckNode(String jsid,HashMap<XtCd,String> menurole,XtCd cd){
		for (Iterator iterator = menurole.keySet().iterator(); iterator.hasNext();) {
			XtCd menu = (XtCd) iterator.next();
			//菜单角色关联关系
			String mroles = String.valueOf(menurole.get(menu));
			//包含该角色，上级菜单的对应下级菜单
			if ( mroles.indexOf(jsid+Constants.SYS_SPLIT_STRING)!=-1 && 
					menu.getId().equals(cd.getId()) ) {
				return "1";
			}
		}
		return "0";
	}
	/**
	 * 角色操作树
	 * 
	 * @param jsid
	 * @param lang
	 * @return
	 */
	public List<TreeCheckNode> getJsCzTree(String jsid, String lang) {
		List<TreeCheckNode> tree = new ArrayList<TreeCheckNode>();
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("jsid", jsid);
		pm.put("lang", lang);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "getJsCz", pm);
		if (ls != null && ls.size() > 0) {
			for (int i = 0, j = ls.size(); i < j; i++) {
				Map<?, ?> o = (Map<?, ?>) ls.get(i);
				String czlbid = StringUtil.getValue(o.get("CZLBID"));
				String czlbmc = StringUtil.getValue(o.get("CZLBMC"));
				String checked = StringUtil.getValue(o.get("CHECKED"));
				TreeCheckNode node = new TreeCheckNode(czlbid, czlbmc, "cz",
						true);
				node.setChecked("1".equals(checked));
				tree.add(node);
			}
		}
		return tree;
	}

	/**
	 * 角色首页树
	 * @param jsid
	 * @return
	 */
	public List<TreeCheckNode> getJsSyTree(String jsid, String lang) {
		List<TreeCheckNode> tree = new ArrayList<TreeCheckNode>();
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("jsid", jsid);
		pm.put("xtmr", Constants.SY_XTMR);
		pm.put("lang", lang);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "getJsSy", pm);
		if (ls != null && ls.size() > 0) {
			for (int i = 0, j = ls.size(); i < j; i++) {
				Map<?, ?> o = (Map<?, ?>) ls.get(i);
				String syid = StringUtil.getValue(o.get("SYID"));
				String symc = StringUtil.getValue(o.get("SYMC"));
				String checked = StringUtil.getValue(o.get("CHECKED"));
				TreeCheckNode node = new TreeCheckNode(syid, symc, "sy",true);
				node.setChecked("1".equals(checked));
				tree.add(node);
			}
		}
		return tree;
	}
	
	/**
	 * 订阅信息树
	 * @param jsid
	 * @param lang
	 * @return
	 */
	/*public List<TreeCheckNode> getJsDyTree(String jsid,String lang){
		List<TreeCheckNode> tree = new ArrayList<TreeCheckNode>();
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("jsid", jsid);
		pm.put(Constants.APP_LANG, lang);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "getJsDy", pm);
		if (ls != null && ls.size() > 0) {
			for (int i = 0, j = ls.size(); i < j; i++) {
				Map<?, ?> o = (Map<?, ?>) ls.get(i);
				String xxbm = StringUtil.getValue(o.get("XXBM"));
				String xxmc = StringUtil.getValue(o.get("XXMC"));
				String checked = StringUtil.getValue(o.get("CHECKED"));
				TreeCheckNode node = new TreeCheckNode(xxbm, xxmc, "dy",true);
				node.setChecked("1".equals(checked));
				tree.add(node);
			}
		}
		return tree;
	}*/
	
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = null;
		if ((menuId + Constants.ADDOPT).equals(czid)) { // 新增
			re = addJs(param);
		} else if ((menuId + Constants.UPDOPT).equals(czid)) { // 更新
			re = updateJs(param);
		} else if ((menuId + "03").equals(czid)) { // 删除
			re = deleteJs(param);
		}
		return re;
	}

	/**
	 * 新增角色
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult addJs(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String cdids = param.get("cdids");
		String czids = param.get("czids");
		String jsmc = param.get("jsmc");
		String syids = param.get("syids");
//		String xxbms  = param.get("xxbms");
//		String cdubz = param.get("cdubz");
//		String resType = param.get("resType");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		List<String> sqlIds = new ArrayList<String>();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsJsmc", param);
		if (ls.size() > 0) {
			re.setSuccess(false);
			re.setMsg("sysModule.qxgl.jsgl.add_existsName", param.get(Constants.APP_LANG));
		} else {
			m.put("jsmc", jsmc);
			m.put("cdid", cdids.split(","));
			m.put("czlbid", czids.split(","));
			m.put("syid", syids.split(","));
//			m.put("xxbm", xxbms.split(","));
			
			String systemId = CommonUtil.getMultiSystemID();
//			m.put("xtid", systemId);
//			m.put("cdubz", cdubz);
//			m.put("resType", resType);
			sqlIds.add(sqlId + "insJs");
			sqlIds.add(sqlId + "insJscd");
			sqlIds.add(sqlId + "insJscz");
			sqlIds.add(sqlId + "insJssy");
//			sqlIds.add(sqlId + "insJsdy");
			baseDAOIbatis.executeBatchTransaction(sqlIds, m);
			re.setSuccess(true);
			re.setMsg("sysModule.qxgl.jsgl.add_success", param.get(Constants.APP_LANG));
			
			//刷新角色和菜单缓存
			MenuUtil.loadRoleMenus();
		}
		return re;
	}

	/**
	 * 更新角色
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult updateJs(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String cdids = param.get("cdids");
		String czids = param.get("czids");
		String jsid = param.get("jsid");
		String syids = param.get("syids");
//		String xxbms  = param.get("xxbms");
		String jsmc = param.get("jsmc");
//		String cdubz = param.get("cdubz");
//		String resType = param.get("resType");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("jsid", jsid);
		m.put("jsmc", jsmc);
		m.put("cdid", cdids.split(","));
		m.put("czlbid", czids.split(","));
		m.put("syid", syids.split(","));
//		m.put("xxbm", xxbms.split(","));
//		m.put("cdubz", cdubz);
//		m.put("resType", resType);
		m.put(Constants.APP_LANG, param.get(Constants.APP_LANG));
		
		sqlIds.add(sqlId + "delJscd");
		sqlIds.add(sqlId + "delJscz");
		//删除首页数据
		sqlIds.add(sqlId + "delJssy");
		//删除订阅信息
//		sqlIds.add(sqlId + "delJsdy");
		//删除操作菜单授权的数据
		sqlIds.add(sqlId +"delCzycd");
		//更新角色名称
		baseDAOIbatis.update(sqlId+"updJs", m);
		
		sqlIds.add(sqlId + "insJscd");
		sqlIds.add(sqlId + "insJscz");
		sqlIds.add(sqlId + "insJssy");
//		sqlIds.add(sqlId + "insJsdy");
		baseDAOIbatis.executeBatchTransaction(sqlIds, m);
		re.setSuccess(true);
		re.setMsg("sysModule.qxgl.jsgl.upd_success", param.get(Constants.APP_LANG));
		
		//刷新角色和菜单缓存
		MenuUtil.loadRoleMenus();
		return re;
	}

	/**
	 * 删除角色
	 * 
	 * @param param
	 * @return
	 */
	private ActionResult deleteJs(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String jsid = param.get("jsid");
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> sqlIds = new ArrayList<String>();
		m.put("jsid", jsid);
		sqlIds.add(sqlId + "delJsczy");
		sqlIds.add(sqlId + "delJscd");
		sqlIds.add(sqlId + "delJscz");
		sqlIds.add(sqlId + "delJssy");
//		sqlIds.add(sqlId + "delJsdy");
		sqlIds.add(sqlId + "delCzycdByJs");
		sqlIds.add(sqlId + "delJs");
		
		baseDAOIbatis.executeBatchTransaction(sqlIds, m);
		re.setMsg("sysModule.qxgl.jsgl.del_success", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		
		//刷新角色和菜单缓存
		MenuUtil.loadRoleMenus();
		return re;
	}
	
	/**
	 * 角色是否使用中
	 * @param param
	 * @return
	 */
	public boolean jsIsUse(Map<String, String> param) {
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "jsIsUse", param);
		if(null != ls && ls.size() > 0){
			return true;
		}
		return false;
	}

	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
//		String systemId = CommonUtil.getMultiSystemID();
//		param.put("xtid", systemId);
		return baseDAOIbatis.getExtGrid(param, sqlId + "js", start, limit, dir,
				sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
}