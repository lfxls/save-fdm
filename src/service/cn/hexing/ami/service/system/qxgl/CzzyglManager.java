package cn.hexing.ami.service.system.qxgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.CdCz;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.service.RoleMenusManager;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MenuUtil;
import cn.hexing.ami.util.SpringContextUtil;

/**
 * @Description 操作资源管理manager
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @time: 2012-6-25 把菜单树由数据库读取修改为从缓存中读取
 * @version AMI3.0
 */
public class CzzyglManager implements CzzyglManagerInf {
	BaseDAOIbatis baseDAOIbatis = null;
	private static String sqlId = "czzygl.";

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "czlb", start, limit,
				dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	/**@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCzTree(String czlbid, String sjcdbm,
			String lang) {
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czlbid", czlbid);
		param.put("sjcdbm", sjcdbm);
		param.put("lang", lang);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "getCd", param);
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> obj = (Map<String, Object>) ls.get(i);
			String cdid = StringUtil.getValue(obj.get("CDID"));
			String cdmc = StringUtil.getValue(obj.get("CDMC"));
			String leaf = StringUtil.getValue(obj.get("SFYJD"));
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", cdid);
			node.put("text", cdmc);
			node.put("leaf", false);
			node.put("expanded", true);
			if ("1".equals(leaf)) {// 加载功能点（按钮）
				param.put("sjcdbm", cdid);
				List<?> lls = baseDAOIbatis.queryForList(sqlId + "getCz", param);
				if (lls != null && lls.size() > 0) {
					List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
					for (int j = 0, k = lls.size(); j < k; j++) {
						Map<String, Object> o = (Map<String, Object>) lls.get(j);
						String czid = StringUtil.getValue(o.get("CZID"));
						String czmc = StringUtil.getValue(o.get("CZMC"));
						Map<String, Object> e = new HashMap<String, Object>();
						e.put("id", czid);
						e.put("text", czmc);
						e.put("leaf", true);
						e.put("iconCls", "gnd");
						children.add(e);
					}
					node.put("children", children);
				}
			} else {
				List<Map<String, Object>> children = getCzTree(czlbid, cdid, lang);
				node.put("children", children);
			}
			tree.add(node);
		}
		return tree;
	}*/
	
	/**
	 * 从缓存中取得菜单并生成树结构
	 * @param czlbid
	 * @param sjcdbm
	 * @param lang
	 */
	public List<Map<String, Object>> getCzTree(String czlbid, String sjcdbm, String lang) {
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		//获取缓存中角色（操作）菜单对应关系
		HashMap<XtCd,String> menurole = (HashMap<XtCd,String>)roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_ROLE_MENUS);
		//获取缓存中操作菜单
		List<CdCz> menuOpt = (List<CdCz>) roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_MENUS_OPT);
		//过滤语言（从缓存中取得对应语言的操作菜单）
		List<CdCz> langLst = new ArrayList<CdCz>();
		for(CdCz menu: menuOpt){
			if(lang.equals(menu.getLang())){
				langLst.add(menu);
			}
		}
		
		List<XtCd> allMenu = new ArrayList<XtCd>();
		//取得所有菜单
		for (Iterator iterator = menurole.keySet().iterator(); iterator.hasNext();) {
			XtCd menu = (XtCd) iterator.next();
			if(lang.equals(menu.getLang())){
				allMenu.add(menu);
			}
		}
		Collections.sort(allMenu, MenuUtil.comp);
		return getCdTree(czlbid, sjcdbm, allMenu, langLst);
	}

	/**
	 * 生成菜单树
	 * @param czlbid
	 * @param sjcdbm
	 * @param menu
	 * @param menuOpt
	 * @return retTree
	 */
	private List<Map<String, Object>> getCdTree(String czlbid, String sjcdbm, List<XtCd> allmenu, List<CdCz> menuOpt){
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		sjcdbm = (sjcdbm == null?"":sjcdbm);
		List<XtCd> childrenList = new ArrayList<XtCd>();
		//获取sjcdbm的所有字节点 只获取一层
		for (XtCd menu : allmenu) {
			if (sjcdbm.equals("root")) {
				if (menu.getSfgjd().equals("1")) {
					childrenList.add(menu);
				}
			}else{
				if (menu.getPid()!=null && menu.getPid().equals(sjcdbm)) {
					childrenList.add(menu);
				}
			}
		}
		//获取子节点
		for (XtCd menu : childrenList) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", menu.getId());
			node.put("text", menu.getText());
			//叶子节点
			if (menu.getIsLeaf().equals("1")) {
				node.put("leaf", false);
				node.put("expanded", true);
				//加载功能点（按钮）
				if(menuOpt != null && menuOpt.size() > 0){
					List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
					for (CdCz cz : menuOpt) {
						//如果操作类别ID相等并且菜单ID相等，添加按钮子节点
						if(czlbid.equals(cz.getCzlbid()) && cz.getCdid().equals(menu.getId())){
							Map<String, Object> e = new HashMap<String, Object>();
							e.put("id", cz.getCzid());
							e.put("text", cz.getCzmc());
							e.put("leaf", true);
							e.put("iconCls", "gnd");
							children.add(e);;
						}
					}
					node.put("children", children);
				}
			}
			//根节点
			else{
				node.put("leaf", false);
				node.put("expanded", true);
				List<Map<String, Object>> children = getCdTree(czlbid, menu.getId(), allmenu, menuOpt);
				node.put("children", children);
			}
			tree.add(node);
		}
		return tree;
	}
}
