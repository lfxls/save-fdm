package cn.hexing.ami.web.action.system.ggdmgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.Code;
import cn.hexing.ami.dao.system.pojo.ggdmgl.CodeCategory;
import cn.hexing.ami.service.system.ggdmgl.DmglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 系统编码管理
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-7
 * @version FDM2.0
 */
public class DmglAction extends BaseAction implements DbWorksInf,QueryInf{
	private static final long serialVersionUID = -3944564250376803520L;
	private DmglManagerInf  dmglManager;
	//语言
	private String lang;
	//分类编码
	private String cateCode;
	//分类名称
	private String cateName;
	//编码值
	private String value;
	//名称
	private String name;
	//编码是否显示
	private String isShow = "1";
	//语言下拉列表框
	private List<Object> langList;
	//编码分类
	private CodeCategory codeCategory;
	//编码
	private Code code;
	//编码显示序号
	private String disp_sn;
	
	/**
	 * 初始化代码管理的页面
	 */
	public  String init(){
		langList = CommonUtil.getCodeNoLoale("dlyy",false);
		return SUCCESS;
	}

    /**
     * 查询代码类型的grid的内容
     */
	public String query() throws Exception {
		//查询的参数
		Map<String, Object> param = new HashMap<String, Object>();
		String _lang = StringUtil.isEmptyString(lang)?session.getAttribute(Constants.APP_LANG).toString():lang;
		param.put("lang", _lang);
		param.put("name", name);
		param.put("cateCode", cateCode);
		//param.put("code_type", code_type);
		Map<String, Object> m = dmglManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m, getExcelHead(), "", "");
		return null;
	}
	
	/**
	 * 查询代码值的grid的内容
	 */
	public String queryDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		String _lang = StringUtil.isEmptyString(lang)?session.getAttribute(Constants.APP_LANG).toString():lang;
		param.put("lang", _lang);
		param.put("name", name);
		param.put("cateCode", cateCode);
		Map<String, Object> m = dmglManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(m, getExcelHead(), "", "");
		return null;
	}
	
	/**
	 * 初始化代码分类的编辑页面
	 * @return
	 */
	public String initEditCodeSort(){
		//表示更新
		if(!StringUtil.isEmptyString(cateCode)){
			Map<String, String> param = new HashMap<String, String>();
			param.put("lang", lang);
			param.put("cateCode", cateCode);
			codeCategory = dmglManager.queryDmFlById(param);
		}
		return "editDmFl";
	}
	
	/**
	 * 初始化代码信息的编辑页面
	 * @return
	 */
	public String initEditCode(){
		//codeId不等于-1表示返回更新的页面
		if(!StringUtil.isEmptyString(cateCode) && !StringUtil.isEmptyString(value)){
			Map<String, String> param = new HashMap<String, String>();
			param.put("lang", lang);
			param.put("cateCode", cateCode);
			param.put("cateName", cateName);
			param.put("value", value);
			param.put("disp_sn", disp_sn);
			code = dmglManager.queryDmXxById(param);
			isShow = code.getIsShow();
			
		}
	
		return "editDmXx";
	}

	public void dbLogger(String czid, String cznr, String czyId,String ip) {
		dmglManager.dbLogger(czid, cznr, czyId, ip);
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return dmglManager.doDbWorks(czid, param);
	}

	public List<String> getExcelHead() {
		return null;
	}
	
	public List<Object> getLangList() {
		return langList;
	}

	public void setLangList(List<Object> langList) {
		this.langList = langList;
	}
	public DmglManagerInf getDmglManager() {
		return dmglManager;
	}

	public void setDmglManager(DmglManagerInf dmglManager) {
		this.dmglManager = dmglManager;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public CodeCategory getCodeCategory() {
		return codeCategory;
	}

	public void setCodeCategory(CodeCategory codeCategory) {
		this.codeCategory = codeCategory;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getDisp_sn() {
		return disp_sn;
	}

	public void setDisp_sn(String disp_sn) {
		this.disp_sn = disp_sn;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
}

