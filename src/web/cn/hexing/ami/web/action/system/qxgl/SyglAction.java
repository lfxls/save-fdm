package cn.hexing.ami.web.action.system.qxgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.system.pojo.sygl.Sygl;
import cn.hexing.ami.service.system.qxgl.SyglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/** 
 * @Description  首页管理
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-13
 * @version AMI3.0 
 */
public class SyglAction extends BaseAction implements DbWorksInf, QueryInf {

	private static final long serialVersionUID = -8377726975399872297L;
	//语言
	private String syid, lang;
	private List<Object> langList;
	//页面字段
	Sygl sygl = new Sygl();
	private SyglManagerInf syglManager = null; 
	
	/**
	 * 初始化首页
	 */
	public String init() {
		lang = getLang();
		langList = CommonUtil.getCode("dlyy", getLang(), false);
		return SUCCESS;
	}
	
	/**
	 * 查询首页
	 * @return
	 * @throws Exception 
	 */
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lang", lang);
		param.put(Constants.APP_LANG, this.getLang());
		Map<String, Object> m = syglManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}

	/**
	 * 编辑首页
	 * @return
	 * @throws Exception
	 */
	public String editSy() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtil.isEmptyString(syid)){
			sygl.setLang(lang);
		}else{
			param.put("syid", syid);
			param.put("lang", lang);
			sygl = (Sygl)syglManager.getSy(param);
		}
		
		return "editSy";
	}
	
	/**
	 * 页面操作
	 * @param
	 * @param
	 */
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return syglManager.doDbWorks(czid, param);
	}
	
	/**
	 * 判断名称是否重复
	 * @param param
	 * @return
	 */
	public ActionResult existingNm(Map<String, String> param, Util util){
		return syglManager.existingNm(param);
	}
	
	/**
	 * 日志记录
	 * @param czid
	 * @param cznr
	 * @param czyId
	 * @param ip
	 */
	public void dbLogger(String czid, String cznr, String czyId, String ip) {
		syglManager.dbLogger(czid, cznr, czyId, ip);
	}

	public String queryDetail() {
		return null;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<Object> getLangList() {
		return langList;
	}

	public void setLangList(List<Object> langList) {
		this.langList = langList;
	}

	public String getSyid() {
		return syid;
	}

	public void setSyid(String syid) {
		this.syid = syid;
	}

	public Sygl getSygl() {
		return sygl;
	}

	public void setSygl(Sygl sygl) {
		this.sygl = sygl;
	}

	public SyglManagerInf getSyglManager() {
		return syglManager;
	}

	public void setSyglManager(SyglManagerInf syglManager) {
		this.syglManager = syglManager;
	}

}

