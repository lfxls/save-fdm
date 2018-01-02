package cn.hexing.ami.service.system.rzgl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.service.RoleMenusManager;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MenuUtil;
import cn.hexing.ami.util.SpringContextUtil;

/**
 * @Description 档案日志manager
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class DarzManager implements DarzManagerInf {

	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	private static String sqlId = "darz.";

	/**
	 * 取菜单
	 */
	public List<Object> getMenu(String root, String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("root", root);
		param.put("lang", lang);
		return baseDAOIbatis.queryForList(sqlId + "getMenu", param);
	}

	/**
	 * 取根菜单
	 */
	public List<Object> getRootMenu(String lang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lang", lang);
		return baseDAOIbatis.queryForList(sqlId + "getRootMenu", param);
	}

	/**
	 * 日志查询
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		String systemId = CommonUtil.getMultiSystemID();
		param.put("xtid", systemId);
		return baseDAOIbatis.getExtGrid(param, sqlId + "darz", start, limit,
				dir, sort, isExcel);
	}

	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * 菜单树
	 * @param sjcdbm
	 */
	public List<Map<String, Object>> getCdTree(String sjcdbm, String lang){
		List<XtCd> allMenu = new ArrayList<XtCd>();
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		//获取缓存中角色（操作）菜单对应关系
		HashMap<XtCd,String> menurole = (HashMap<XtCd,String>)roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_ROLE_MENUS);
		
		//取得所有菜单
		for (Iterator iterator = menurole.keySet().iterator(); iterator.hasNext();) {
			XtCd menu = (XtCd) iterator.next();
			if(lang.equals(menu.getLang())){
				allMenu.add(menu);
			}
		}
		Collections.sort(allMenu, MenuUtil.comp);
		return getCdTree(sjcdbm, allMenu);
	}
	
	/**
	 * 生成菜单树
	 * @param sjcdbm
	 * @param menu
	 * @param retTree
	 * @return
	 */
	private List<Map<String, Object>> getCdTree(String sjcdbm, List<XtCd> allmenu){
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		sjcdbm = sjcdbm==null?"":sjcdbm;
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
				node.put("leaf", true);
			}
			//根节点
			else{
				node.put("leaf", false);
				node.put("expanded", true);
				List<Map<String, Object>> children = getCdTree(menu.getId(), allmenu);
				node.put("children", children);
			}
			tree.add(node);
		}
		return tree;
	}

}
