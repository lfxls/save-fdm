package cn.hexing.ami.web.action.system.ggdmgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsFl;
import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsXx;
import cn.hexing.ami.service.system.ggdmgl.XtcsManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;

/**
 * @Description 系统配置Action
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-14
 * @version AMI3.0
 */
public class XtcsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	//语言，系统参数分类名称
	private String lang, csflbm, csflmc, csflms, syzt,csmc, xtcszlx, csz, cszsx, cszxx;
	//系统参数类别的id
	private int paraSortId=-1;
	//系统参数信息的id
	private int paraId = -1;
	//操作标示
	private String czbs;
	//语言下拉列表框,使用状态下拉列表,系统参数类型下拉列表
	private List<Object> langList, syztList, xtcszlxList;
	
	private XtcsManagerInf  xtcsManager;
	//参数分类
	private CsFl csfl;
	//参数详细信息
	private CsXx csxx;
	
	/**
	 * 初始化系统参数管理的页面
	 */
	public  String init(){
		langList = CommonUtil.getCode("dlyy", getLang(), false);
		return SUCCESS;
	}

    /**
     * 查询系统参数分类的grid的内容
     */
	public String query() throws Exception {
		//查询的参数
		Map<String, Object> param = new HashMap<String, Object>();
		String _lang = StringUtil.isEmptyString(lang)?session.getAttribute(Constants.APP_LANG).toString():lang;
		param.put("lang", _lang);
		param.put("name", csflmc);
		param.put("para_sort_id", paraSortId);
		Map<String, Object> m = xtcsManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m, getExcelHead(), "", "");
		return null;
	}
	
	/**
	 * 查询系统参数详细信息的grid的内容
	 */
	public String queryDetail() {
		if(-1 == paraSortId){
			return null;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		String _lang = StringUtil.isEmptyString(lang)?session.getAttribute(Constants.APP_LANG).toString():lang;
		param.put("lang", _lang);
		param.put("name", csflmc);
		param.put("para_sort_id", paraSortId);
		Map<String, Object> m = xtcsManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(m, getExcelHead(), "", "");
		return null;
	}
	
	/**
	 * 初始化系统参数分类的编辑页面
	 * @return
	 */
	public String initEditParaSort(){
		syztList = CommonUtil.getCodeNumber("syzt", getLang(),"DESC", false);
		//表示更新
		if(paraSortId != -1){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("lang", lang);
			param.put("para_sort_id", paraSortId);
			csfl = xtcsManager.queryCsFlById(param);
			syzt = csfl.getSyzt();
		}
		
		return "editXtcsFl";
	}
	
	/**
	 * 初始化系统参数详细信息的编辑页面
	 * @return
	 */
	public String initEditPara(){
		xtcszlxList = CommonUtil.getCode("xtcszlx", getLang(), false);
		syztList = CommonUtil.getCodeNumber("syzt", getLang(), "DESC",false);
		//paraId不等于-1表示返回更新的页面
		if(paraId != -1){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("lang", lang);
			param.put("para_id", paraId);
			csxx = xtcsManager.queryCsXxById(param);
			xtcszlx = csxx.getType_code();
			syzt = csxx.getSyzt();
		}
		return "editXtcsXx";
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return xtcsManager.doDbWorks(czid, param);
	}
	
	public void dbLogger(String czid, String cznr, String czyId,String ip) {
		xtcsManager.dbLogger(czid, cznr, czyId, ip);
	}
	
	public List<String> getExcelHead() {
		return null;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCsflbm() {
		return csflbm;
	}

	public void setCsflbm(String csflbm) {
		this.csflbm = csflbm;
	}

	public String getCsflmc() {
		return csflmc;
	}

	public void setCsflmc(String csflmc) {
		this.csflmc = csflmc;
	}

	public String getCsflms() {
		return csflms;
	}

	public void setCsflms(String csflms) {
		this.csflms = csflms;
	}

	public String getSyzt() {
		return syzt;
	}

	public void setSyzt(String syzt) {
		this.syzt = syzt;
	}

	public String getCsmc() {
		return csmc;
	}

	public void setCsmc(String csmc) {
		this.csmc = csmc;
	}

	public String getXtcszlx() {
		return xtcszlx;
	}

	public void setXtcszlx(String xtcszlx) {
		this.xtcszlx = xtcszlx;
	}

	public String getCsz() {
		return csz;
	}

	public void setCsz(String csz) {
		this.csz = csz;
	}

	public String getCszsx() {
		return cszsx;
	}

	public void setCszsx(String cszsx) {
		this.cszsx = cszsx;
	}

	public String getCszxx() {
		return cszxx;
	}

	public void setCszxx(String cszxx) {
		this.cszxx = cszxx;
	}

	public int getParaSortId() {
		return paraSortId;
	}

	public void setParaSortId(int paraSortId) {
		this.paraSortId = paraSortId;
	}

	public int getParaId() {
		return paraId;
	}

	public void setParaId(int paraId) {
		this.paraId = paraId;
	}

	public List<Object> getLangList() {
		return langList;
	}

	public void setLangList(List<Object> langList) {
		this.langList = langList;
	}

	public List<Object> getSyztList() {
		return syztList;
	}

	public void setSyztList(List<Object> syztList) {
		this.syztList = syztList;
	}

	public List<Object> getXtcszlxList() {
		return xtcszlxList;
	}

	public void setXtcszlxList(List<Object> xtcszlxList) {
		this.xtcszlxList = xtcszlxList;
	}

	public XtcsManagerInf getXtcsManager() {
		return xtcsManager;
	}

	public void setXtcsManager(XtcsManagerInf xtcsManager) {
		this.xtcsManager = xtcsManager;
	}

	public CsFl getCsfl() {
		return csfl;
	}

	public void setCsfl(CsFl csfl) {
		this.csfl = csfl;
	}

	public CsXx getCsxx() {
		return csxx;
	}

	public void setCsxx(CsXx csxx) {
		this.csxx = csxx;
	}

	public String getCzbs() {
		return czbs;
	}

	public void setCzbs(String czbs) {
		this.czbs = czbs;
	}
	
}
