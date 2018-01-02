package cn.hexing.ami.web.action.system.qxgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.service.RoleMenusManager;
import cn.hexing.ami.service.system.qxgl.JsglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 角色管理action
 * @author  ycl
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class JsglAction extends BaseAction implements QueryInf, DbWorksInf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1024535220107165481L;

	JsglManagerInf jsglManager = null;

	public void setJsglManager(JsglManagerInf jsglManager) {
		this.jsglManager = jsglManager;
	}

	private String jsid, sjcdbm;

	public List<TreeCheckNode> getJsCdTree() throws Exception {
		RoleMenusManager roleMenusManager = (RoleMenusManager) SpringContextUtil.getBean("roleMenusManager");
		//获取缓存中角色（操作）菜单对应关系
		HashMap<XtCd,String> menurole = (HashMap<XtCd,String>)roleMenusManager.getRoleMenusCache().getCache(Constants.SYS_ROLE_MENUS);
		
		List<TreeCheckNode> tree = jsglManager.getJsCdTree(jsid, sjcdbm,menurole,getLang());
		responseJson(tree, false);
		return null;
	}

	public List<TreeCheckNode> getJsCzTree() {
		List<TreeCheckNode> tree = jsglManager.getJsCzTree(jsid, getLang());
		responseJson(tree, false);
		return null;
	}
	
	/**
	 * 获取首页信息
	 * @return
	 */
	public List<TreeCheckNode> getJsSyTree(){
		List<TreeCheckNode> tree = jsglManager.getJsSyTree(jsid, getLang());
		responseJson(tree, false);
		return null;
	}
	
	/**
	 * 获取可订阅信息
	 * @return
	 */
	/*public List<TreeCheckNode> getJsDyTree(){
		List<TreeCheckNode> tree = jsglManager.getJsDyTree(jsid,getLang());
		responseJson(tree, false);
		return null;
	}*/

	public void dbLogger(String czid, String cznr, String czyId, String ip) {
		jsglManager.dbLogger(czid, cznr, czyId, ip);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return jsglManager.doDbWorks(czid, param);
	}

	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG,getLang());
		Map<String, Object> re = jsglManager.query(param, start, limit, dir,
				sort, isExcel);
		//responseGrid(re, getExcelHead(), "jsgl", "角色管理");
		responseGrid(re);
		return null;
	}

	/**
	 * 角色是否使用中
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult jsIsUse(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult(true, "");
		boolean bl = jsglManager.jsIsUse(param);
		if(bl){
			re.setMsg("used");
		}
		return re;
	}
	
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getExcelHead() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJsid() {
		return jsid;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public String getSjcdbm() {
		return sjcdbm;
	}

	public void setSjcdbm(String sjcdbm) {
		this.sjcdbm = sjcdbm;
	}
}