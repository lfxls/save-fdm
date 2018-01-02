package cn.hexing.ami.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hexing.ami.dao.common.pojo.CdCz;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.service.RoleMenusManager;

/** 
 * @Description 获取系统菜单辅助类
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-19
 * @version AMI3.0 
 */
public class MenuUtil {
	/**
	 * 创建下拉菜单
	 * @param menuitems
	 * @param topMenuId 一级菜单id
	 * @return
	 */
	public static List<XtCd> createDownMenuTree(List<XtCd> menuitems,String topMenuId) {
		List<XtCd> treeList = new ArrayList<XtCd>();
		if (menuitems==null || menuitems.size()==0) {
			return null;
		}
		
		//搜索当前一级菜单的下级菜单列表
		for(int i = 0;i<menuitems.size();i++){
			XtCd _menuitem = (XtCd) menuitems.get(i);
			if (_menuitem.getPid()!=null && _menuitem.getPid().equals(topMenuId)) {
				treeList.add(_menuitem);
			}
		}
		//去除重复的菜单项
		treeList = removeDuplicateList(treeList);
		Collections.sort(treeList,comp);
		for (XtCd cd : treeList) {
			int num = 0;
			String isLeaf = cd.getIsLeaf();
			Map<String, Object> menu = cd.getMenu() == null ?new HashMap<String, Object>():cd.getMenu();
			if ("0".equals(isLeaf)) { // 非叶子节点
				String menuId = cd.getId();
				List<XtCd> subMenus = (List<XtCd>)createDownMenuTree(menuitems, menuId);
				Collections.sort(subMenus, comp);
				for(XtCd item:subMenus){
					Map<String,Object> map = item.getMenu();
					if(map!=null && !map.isEmpty()){
						num += (Integer)map.get("leafNum");
					}
				}
				//cd.setMenu(menu);
				menu.put("items", subMenus);
			}else{
				num++;
			}
			menu.put("leafNum", num);
			cd.setMenu(menu);
		}
		return treeList;
	}
	
	/**
	 * 排序 正序
	 */
	public static Comparator comp = new Comparator() {
		public int compare(Object o1, Object o2) {
			XtCd a = (XtCd) o1;
			XtCd b = (XtCd) o2;
			String aPxbm = (a.getPxbm()==null || "".equals(a.getPxbm()))?"0":a.getPxbm();
			String bPxbm = (b.getPxbm()==null || "".equals(b.getPxbm()))?"0":b.getPxbm();
			return (aPxbm.compareTo(bPxbm));
		}
	};
	
	/**
	 * 刷新菜单角色缓存信息
	 */
	public static void loadRoleMenus(){
		//刷新角色和菜单缓存
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		roleMenusManager.loadRoleMenusData();
	}
	
	/**
	 * 刷新角色操作缓存信息
	 */
	public static void loadMenuOpt(){
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		roleMenusManager.loadMenusOptData();
	}
	
	/**
	 * 根据菜单id从缓存中获取页面操作资源（增删改）
	 * @param menuId
	 * @return
	 */
	public static String getOptByMenuId(String menuId){
		menuId = menuId==null?"":menuId;
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		//获取缓存中角色（操作）菜单对应关系
		List<CdCz> czList = (List<CdCz>)roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_MENUS_OPT);
		
		String czStr = "";
		for (CdCz cdCz : czList) {
			if (cdCz.getCdid()!=null && cdCz.getCdid().equals(menuId)) {
				czStr += cdCz.getCzid()+Constants.SYS_SPLIT_STRING;
			}
		}
		return czStr;
	}
	
	/**
	 * 取出根菜单中的重复数据
	 * @param arlList
	 * @return
	 */
	public static List removeDuplicateList(List arlList) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = arlList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		arlList.clear();
		arlList.addAll(newList);
		return arlList;
	}
}