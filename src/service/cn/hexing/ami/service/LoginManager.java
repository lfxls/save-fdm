package cn.hexing.ami.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.dao.common.pojo.Sy;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MenuUtil;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;

public class LoginManager {
	private static Logger logger = Logger.getLogger(LoginManager.class.getName());
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	private String sqlId = "login.";

	public AqCzy getCzyById(String czyId) {
		AqCzy op = null;
		Map<String,Object> m = new HashMap<String, Object>();
		m.put("czyid", czyId);
		List<Object> l = new ArrayList<Object>();;
		try {
			l = baseDAOIbatis.queryForList(sqlId + "getAqCzy", m);
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		}
		if (l != null && l.size() > 0) {
			op = (AqCzy) l.get(0);
		}
		return op;
	}

	/**
	 * 写登录日志
	 * 
	 * @param op
	 * @param localAddr
	 * @param sessionId
	 */
	public void insLogin(AqCzy op, String localAddr, String sessionId) {
		String systemId = CommonUtil.getMultiSystemID();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czyId", op.getCzyid());
		param.put("dwdm", op.getDwdm());
		param.put("ip", localAddr);
		param.put("sessionId", sessionId);
		param.put("xtid", systemId);
	
		baseDAOIbatis.insert(sqlId + "insRzCzy", param);
	}

	/**
	 * 退出写日志
	 * 
	 * @param sessionId
	 */
	public void updateLogger(String sessionId) {
		baseDAOIbatis.update(sqlId + "updateRzCzy", sessionId);
	}

	/**
	 * 取菜单
	 * 
	 * @param czyid
	 * @param sjcdbm
	 * @param lang
	 * @param userRoles 用户角色列表
	 * @return
	 */
	public List<XtCd> getMenus(String czyid, String sjcdbm, String lang,
			String main,String userRoles) {
		//一级菜单
		List<XtCd> topMenu = new ArrayList<XtCd>();
		//所有菜单
		List<XtCd> menuItems = new ArrayList<XtCd>();
		
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		//获取缓存中角色（操作）菜单对应关系
		HashMap<XtCd,String> menurole = (HashMap<XtCd,String>)roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_ROLE_MENUS);
		
		for (Iterator iterator = menurole.keySet().iterator(); iterator.hasNext();) {
			XtCd menu = (XtCd) iterator.next();
			//菜单角色关联关系
			String mroles = String.valueOf(menurole.get(menu));
			//菜单语言
			String menuLang = menu.getLang()==null?"":menu.getLang();
			//超级管理员
			if (czyid.equals(Constants.SUPER_ADMIN) && menuLang.equals(lang)) {
				menuItems.add(menu);
				if (menu.getSfgjd().equals("1")) {
					topMenu.add(menu);
				}
			}else{
				//是否为操作员菜单授权模式
				if (userRoles.equals(Constants.NULL_ROLES)) {
					if (mroles.indexOf(czyid+Constants.SYS_SPLIT_STRING)!=-1 && menuLang.equals(lang)) {
						//是否为一级菜单
						if (menu.getSfgjd().equals("1")) {
							topMenu.add(menu);
						}
						menuItems.add(menu);
					}
				}else{
					//角色菜单授权模式 （超级管理员授权模式 角色列表为空字符）
					if (!userRoles.equals("")) {
						String[] userRolesArray = userRoles.split(",");
						for (int i = 0; i < userRolesArray.length; i++) {
							String roleIdTmp = userRolesArray[i];
							//包含该角色
							if (mroles.indexOf(roleIdTmp+Constants.SYS_SPLIT_STRING)!=-1 && menuLang.equals(lang)) {
								//是否为一级菜单
								if (menu.getSfgjd().equals("1")) {
									topMenu.add(menu);
								}
								menuItems.add(menu);
							}
						}
					}
				}
			}
		}
		
		//剔除topmenu中的重复菜单项目
		topMenu = MenuUtil.removeDuplicateList(topMenu);
		Collections.sort(topMenu, MenuUtil.comp);
		
		if ("main".equals(sjcdbm)) { // 首页
			return new ArrayList<XtCd>();
		}else{
			if ("root".equals(sjcdbm)) { // 根菜单
				return topMenu;
			} if("all".equals(sjcdbm)){ //取所有
				for(XtCd cd:topMenu){
					Map<String,Object> items = new HashMap<String, Object>();
					List<XtCd> menus = MenuUtil.createDownMenuTree(menuItems, cd.getId());
					items.put("items",menus);
					int leftNum = 0;
					for(XtCd item:menus){
						Map<String,Object> map = item.getMenu();
						if(map!=null && !map.isEmpty()){
							leftNum += (Integer)map.get("leafNum");
						}
					}
					items.put("leafNum", leftNum);
					cd.setMenu(items);
				}
				return topMenu;
			}else{
				return MenuUtil.createDownMenuTree(menuItems, sjcdbm);
			}
		}
	}

	/**
	 * 根据操作员单位代码区分操作员权限或部门权限
	 * 
	 * @param czyid
	 * @param dwdm
	 * @return
	 */
	public String getFwbj(String czyid, String dwdm) {
		String fwbj = "0";
		Map<String, String> param = new HashMap<String, String>();
		param.put("czyid", czyid);
		param.put("dwdm", dwdm);
		List<Object> l = baseDAOIbatis.queryForList(sqlId + "getFwbj", param);
		if (l != null && l.size() > 0) {
			fwbj = "1";
		}
		return fwbj;
	}
	
	/**
	 * 获取操作员对应的角色列表
	 * @param czyid
	 * @return
	 */
	public String getCzyJs(String czyid){
		String jsStr = "";
		Map<String, String> param = new HashMap<String, String>();
		param.put("czyid", czyid);
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getJsByCzy", param);
		if (ls!=null && ls.size()>0) {
			for (int i = 0; i < ls.size(); i++) {
				Map<String, Object> obj = (Map<String, Object>) ls.get(i);
				String jsTmp = (String)obj.get("JSID");
				jsStr += jsTmp+",";
			}
		}
		return jsStr;
	}
	
	/**
	 * 获取操作员对应的菜单授权列表
	 * @param czyid
	 * @return
	 */
	public boolean hasCzyCd(String czyid){
		Map<String, String> param = new HashMap<String, String>();
		param.put("czyid", czyid);
		Integer count = (Integer)baseDAOIbatis.queryForObject(sqlId + "getCdByCzy", param,
				Integer.class);
		if (count>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据操作员获取首页信息
	 * @param czyid
	 * @return
	 */
	public List<Sy> getSyList(String czyid,String lang){
		List<Sy> syList = new ArrayList<Sy>();
		//如果用户没有赋予任何首页，采用默认首页
		Map<String, String> param = new HashMap<String, String>();
		param.put("czyid", czyid);
		param.put("lang", lang);
		param.put("xtid", CommonUtil.getMultiSystemID());
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getJsSy", param);
		if (ls==null || ls.size()==0) {
			param.put("xtmr", Constants.SY_XTMR);
			//获取默认首页
			Sy xtMrSy = (Sy)baseDAOIbatis.queryForObject(sqlId+"getMrSy", param, Sy.class);
			syList.add(xtMrSy);
		}else{
			for (int i = 0; i < ls.size(); i++) {
				syList.add((Sy)ls.get(i));
			}
		}
		return syList;
	}
	
	/**
	 *  获取告警信息
	 * @return
	 */
	public ActionResult getAlarmInfo(Map<String, Object> param){
		ActionResult result = new ActionResult();
		int alarmNum = (Integer)baseDAOIbatis.queryForObject(sqlId+"zdgjAlertCount", param, Integer.class);
		result.setSuccess(true);
		result.setDataObject(alarmNum);
		return result;
	}
	
	/**
	 * 根据用户角色获取订阅信息
	 * @param jsid
	 * @return
	 */
/*	public List<Object> getDyxxList(String jsid){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("jsid", jsid);
		return baseDAOIbatis.queryForList(sqlId+"getDyxxList", param);
	}*/
	
	/**
	 * 获取用户快捷方式列表
	 */
	public List<Object> getShortCutsList(String czyId,String lang){
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("czyId", czyId);
		param.put(Constants.APP_LANG, lang);
		return baseDAOIbatis.queryForList(sqlId+"getShortCutsList", param);
	}
	
	/**
	 * 增加快捷方式
	 * @param param
	 */
	public void addShortCuts(Map<String, Object> param){
		baseDAOIbatis.insert(sqlId+"delShortCuts", param);
		baseDAOIbatis.delete(sqlId+"addShortCuts", param);
	}
	
	/**
	 * 删除快捷方式
	 * @param param
	 */
	public void delShortCuts(Map<String, Object> param){
		baseDAOIbatis.delete(sqlId+"delShortCuts", param);
	}
	
	/**
	 * 修改输入密码错误计数
	 * 
	 * @param op	 	
	 */
	public void updateMmcwcs(AqCzy op) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("czyId", op.getCzyid());
		param.put("mmcwcs", op.getMmcwcs());
		param.put("zt", op.getZt());
		baseDAOIbatis.update(sqlId + "updateMmcwcs", param);
	}
}
