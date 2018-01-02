package cn.hexing.ami.dao.system.pojo.ggdmgl;

import java.io.Serializable;

/**
 * @Description 编码对象
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-7
 * @version FDM2.0
 */

public class Code implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3401632245309149527L;
	//分类编码
	private String cateCode;
	//分类编码-老、
	private String cateCodeOld;
	//分类名称
	private String cateName;
	//语言类型
	private String lang;
	//编码名称
	private String codeName;
	//编码值
	private String codeValue;
	//编码值-老
	private String codeValueOld;
	//是否显示
	private String isShow;
	//显示序号
	private String disp_sn;
	//操作标识
	private String opID;
	//操作类型
	private String opType;
	
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
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
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getOpID() {
		return opID;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateCodeOld() {
		return cateCodeOld;
	}
	public void setCateCodeOld(String cateCodeOld) {
		this.cateCodeOld = cateCodeOld;
	}
	public String getCodeValueOld() {
		return codeValueOld;
	}
	public void setCodeValueOld(String codeValueOld) {
		this.codeValueOld = codeValueOld;
	}
	
}
