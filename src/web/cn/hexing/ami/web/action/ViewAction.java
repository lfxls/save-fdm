package cn.hexing.ami.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.Sy;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.service.CommonManager;
import cn.hexing.ami.service.LoginManager;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 登录后主框架 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-8-12
 * @version AMI3.0
 */
public class ViewAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3322016922298681582L;

	private LoginManager loginManager = null;

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	private String menuId;
	private List<XtCd> rootMenus;
	private String syJson;
	private String prepaySaleMode;
	private boolean sbgjFlag;
	private List<Object> shortCutsLs;
	private String helpDocAreaId;
	private String cdIds;
	private String logoMode;
	//首页告警刷新时间
	private String alermRefreshInterval;
	//系统名称
	private String sysTitle;

	public String getSysTitle() {
		return sysTitle;
	}

	public void setSysTitle(String sysTitle) {
		this.sysTitle = sysTitle;
	}

	public String getAlermRefreshInterval() {
		return alermRefreshInterval;
	}

	public void setAlermRefreshInterval(String alermRefreshInterval) {
		this.alermRefreshInterval = alermRefreshInterval;
	}

	public String getLogoMode() {
		return logoMode;
	}

	public void setLogoMode(String logoMode) {
		this.logoMode = logoMode;
	}

	public String getCdIds() {
		return cdIds;
	}

	public void setCdIds(String cdIds) {
		this.cdIds = cdIds;
	}

	public List<Object> getShortCutsLs() {
		return shortCutsLs;
	}

	public void setShortCutsLs(List<Object> shortCutsLs) {
		this.shortCutsLs = shortCutsLs;
	}

	public boolean isSbgjFlag() {
		return sbgjFlag;
	}

	public void setSbgjFlag(boolean sbgjFlag) {
		this.sbgjFlag = sbgjFlag;
	}

	public String getPrepaySaleMode() {
		return prepaySaleMode;
	}

	public void setPrepaySaleMode(String prepaySaleMode) {
		this.prepaySaleMode = prepaySaleMode;
	}

	public String getSyJson() {
		return syJson;
	}

	public void setSyJson(String syJson) {
		this.syJson = syJson;
	}

	public String init() {
		//用户角色列表
		String userRoles = (String)session.getAttribute(Constants.USER_ROLES);
		// 获取根菜单
//		rootMenus = loginManager.getMenus(this.getCzyid(),"root", getLang(), null,userRoles); 
		rootMenus = loginManager.getMenus(this.getCzyid(),"all", getLang(), null,userRoles); 
		cdIds = "";
		if (rootMenus!=null && rootMenus.size()>0) {
			for (XtCd cd : rootMenus) {
				cdIds += cd.getId()+",";
			}
		}
		if (cdIds!=null && cdIds.indexOf(",")!=-1) {
			cdIds = cdIds.substring(0,cdIds.length()-1);
		}
		
		//获取角色对应首页关联
		List<Sy> syList = loginManager.getSyList(this.getCzyid(),getLang()); 
		if (syList==null || syList.size()==0) {
			syJson = "";
		}else{
			JSONArray jsonarray = JSONArray.fromObject(syList);
			syJson = jsonarray.toString();
		}
		
		//如果为预付费模式，则需要显示预付费的logo
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		prepaySaleMode = StringUtil.isEmptyString(sysMap.get("prepayMode"))?"false":sysMap.get("prepayMode");
		logoMode = StringUtil.isEmptyString(sysMap.get("logoMode"))? "1":sysMap.get("logoMode");
		
		/*List<Object> dyList = new ArrayList<Object>();
		//获取用户订阅信息授权
		String jsStr = (String)session.getAttribute(Constants.USER_ROLES);
		jsStr = jsStr==null?"":jsStr;
		if (!jsStr.equals("")) {
			String[] jsArray = jsStr.split(",");
			for (int i = 0; i < jsArray.length; i++) {
				List<Object> tmpList = loginManager.getDyxxList(jsArray[i]);
				dyList.addAll(tmpList);
			}
			//设备告警
			String sbgj = "1001";
			sbgjFlag = false;
			for (int i = 0; i < dyList.size(); i++) {
				Map tmpMap = (Map)dyList.get(i);
				String tmpValue = (String)tmpMap.get("XXBM");
				if (tmpValue!=null && sbgj.equals(tmpValue)) {
					sbgjFlag = true;
				}
			}
		}*/
		
		//导出文档格式支持
		String gridExportDoc = sysMap.get(Constants.GRIDDOC);
		gridExportDoc = gridExportDoc==null?"":gridExportDoc;
		session.setAttribute(Constants.GRIDDOC, gridExportDoc.toLowerCase());
		
		//货币单位
		String cu = sysMap.get("currencyUnit");
		cu = cu==null?"":cu;
		session.setAttribute(Constants.CU, cu);
		
		//小型预付费模式
		String sysMode = sysMap.get(Constants.SYS_MODE);
		sysMode = (sysMode==null || "".equals(sysMode))?Constants.SYSMODE_NORMAL:sysMode;
		session.setAttribute(Constants.SYS_MODE, sysMode);
		
		//是否支持卡表
		String ucm = sysMap.get("useCardMeter");
		ucm = ucm==null?"":ucm;
		session.setAttribute(Constants.UCM, ucm);
		
		//首页告警刷新时间  没有配置默认为60s
		alermRefreshInterval = StringUtil.isEmptyString(sysMap.get("alermRefreshInterval"))? "60":sysMap.get("alermRefreshInterval");
		
		helpDocAreaId = sysMap.get("helpDocAreaId");
		
		sysTitle = (String)session.getAttribute("sysTitle");
		return SUCCESS;
	}

	public String getMenus() {
		//用户角色列表
		String userRoles = (String)session.getAttribute(Constants.USER_ROLES);
		List<XtCd> menus = loginManager.getMenus(this.getCzyid(), menuId, getLang(), getText("view.main"),userRoles);
		responseJson(menus, false);
		return null;
	}
	
	/**
	 * 获取告警信息
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getAlarmInfo(Map<String, Object> param, Util util){
		ActionResult re = new ActionResult();
		String nodeId = (String)param.get(Constants.UNIT_CODE);
		String nodeType = "dw";
		String nodeDwdm = "";
		String czyid = (String)param.get(Constants.CURR_STAFFID);
		String fwbj = (String)param.get(Constants.CURR_RIGTH);
		String bm = (String)param.get(Constants.CURR_DEPT);
		
		//查询当天的数据
		String nowStr = DateUtil.getDateTime("yyyy-MM-dd", new Date());
		param.put("ksrq", nowStr);
		param.put("jsrq", nowStr);
		
		param.put(Constants.APP_LANG, param.get(Constants.APP_LOCALE));
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, czyid, fwbj, bm, "ZD");
		re = loginManager.getAlarmInfo(param);
		return re;
	}
	
	/**
	 * 显示license到期信息
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getLicenseInfo(Map<String, Object> param, Util util){
		ActionResult re = new ActionResult();
		
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String expiredFlag = (String)sysMap.get(Constants.LICENSE_DAYS);
		
		int leftDays = -1;
		//如果expiredFlag为-1，表示不过期
		if (!expiredFlag.equals("-1")) {
			CommonManager commonManager = (CommonManager)SpringContextUtil.getBean("commonManager");
			Map<String,Object> dayMap = commonManager.getUsedDaysLicense();
			
			//时间使用的天数
			int usedDays = Integer.parseInt(String.valueOf(dayMap.get("DAYS")));
			
			//可使用天数
			int canUseDays = (sysMap.get(Constants.LICENSE_DAYS)==null || "".equals(sysMap.get(Constants.LICENSE_DAYS)))?0:Integer.parseInt(sysMap.get(Constants.LICENSE_DAYS));
			
			//可用剩余天数
			leftDays = canUseDays-usedDays;
			leftDays = leftDays<0?0:leftDays;
			re.setDataObject(leftDays);
			re.setSuccess(true);
		}else{
			re.setSuccess(false);
		}
		return re;
	}
	
	public String initMain() {
		return "main";
	}

	/**
	 * 初始化左侧树菜单
	 * @return
	 */
	public String initLeft() {
		return "left";
	}
	
	/**
	 * 初始化左侧树菜单 小型预付费
	 * @return
	 */
	public String initLeftS() {
		return "leftS";
	}
	
	/**
	 * 初始化快捷方式
	 * @return
	 */
	public String initShortCuts() {
		shortCutsLs = loginManager.getShortCutsList(this.getCzyid(),this.getLang());
		return "shortCuts";
	}
	
	/**
	 * 新增快捷方式
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult addShortCuts(Map<String, Object> param, Util util){
		ActionResult re = new ActionResult();
		String czyId = String.valueOf(param.get(Constants.CURR_STAFFID));
		param.put("czyId", czyId);
		loginManager.addShortCuts(param);
		re.setSuccess(true);
		re.setMsg("common.kjfs.addSuccess", String.valueOf(param.get(Constants.APP_LANG)));
		return re;
	}
	
	/**
	 * 删除快捷方式
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult delShortCuts(Map<String, Object> param, Util util){
		ActionResult re = new ActionResult();
		String czyId = String.valueOf(param.get(Constants.CURR_STAFFID));
		param.put("czyId", czyId);
		loginManager.delShortCuts(param);
		re.setSuccess(true);
		re.setMsg("common.kjfs.delSuccess", String.valueOf(param.get(Constants.APP_LANG)));
		return re;
	}
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public List<XtCd> getRootMenus() {
		return rootMenus;
	}

	public void setRootMenus(List<XtCd> rootMenus) {
		this.rootMenus = rootMenus;
	}

	public String getHelpDocAreaId() {
		return helpDocAreaId;
	}

	public void setHelpDocAreaId(String helpDocAreaId) {
		this.helpDocAreaId = helpDocAreaId;
	}
}