package cn.hexing.ami.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.CdCz;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.cache.RoleMenusCache;

/** 
 * @Description 权限菜单
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-19
 * @version AMI3.0 
 */
public class RoleMenusManager {
	private String sqlId = "login.";
	private BaseDAOIbatis baseDAOIbatis = null;
	private RoleMenusCache roleMenusCache;

	public RoleMenusCache getRoleMenusCache() {
		return roleMenusCache;
	}

	public void setRoleMenusCache(RoleMenusCache roleMenusCache) {
		this.roleMenusCache = roleMenusCache;
	}

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 加载角色和菜单关联数据
	 */
	public void loadRoleMenusData(){
		HashMap<XtCd,String> menurole = new HashMap<XtCd,String>();
	
		//获取所有菜单
		List<Object> menusList = baseDAOIbatis.queryForList(sqlId+"getAllMenus", null);
		
		//所有角色菜单关联
		List<Object> roleMenusList = baseDAOIbatis.queryForList(sqlId+"getAllRoleMenus", null);
		
		//所有操作员菜单关联
		List<Object> operatorMenusList = baseDAOIbatis.queryForList(sqlId+"getAllOperatorMenus", null);
		
		for (int i = 0; i < menusList.size(); i++) {
			XtCd menu = (XtCd)menusList.get(i);
			//默认加入操作管理员 特殊处理
			String roleIdStr = Constants.SUPER_ADMIN+Constants.SYS_SPLIT_STRING;
			for (int ii = 0; ii < roleMenusList.size(); ii++) {
				Map tmpMap = (Map)roleMenusList.get(ii);
				String roleId = String.valueOf(tmpMap.get("JSID")==null?"":tmpMap.get("JSID"));
				String menuId = String.valueOf(tmpMap.get("CDID")==null?"":tmpMap.get("CDID"));
				if (menu.getId().equals(menuId)) {
					roleIdStr += roleId+Constants.SYS_SPLIT_STRING;
				}
			}
			
			//操作员和菜单对应特殊处理
			for (int ii = 0; ii < operatorMenusList.size(); ii++) {
				Map tmpMap = (Map)operatorMenusList.get(ii);
				String operatorId = String.valueOf(tmpMap.get("CZYID")==null?"":tmpMap.get("CZYID"));
				String menuId = String.valueOf(tmpMap.get("CDID")==null?"":tmpMap.get("CDID"));
				if (menu.getId().equals(menuId)) {
					roleIdStr += operatorId+Constants.SYS_SPLIT_STRING;
				}
			}
			menurole.put(menu, roleIdStr);
		}
		
		roleMenusCache.putData2Cache(Constants.SYS_ROLE_MENUS, menurole);
	}
	
	/**
	 * 加载角色操作关联数据
	 */
	public void loadMenusOptData(){
		List<Object> optList = baseDAOIbatis.queryForList(sqlId+"getAllMenusOpt", null);
		
		List<CdCz> newOptList = new ArrayList<CdCz>();
		for (int i = 0; i < optList.size(); i++) {
			CdCz cdcz = (CdCz)optList.get(i);
			newOptList.add(cdcz);
		}
		roleMenusCache.putData2Cache(Constants.SYS_MENUS_OPT, newOptList);
	}
}
