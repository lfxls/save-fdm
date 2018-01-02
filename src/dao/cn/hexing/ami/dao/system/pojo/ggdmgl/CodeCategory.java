package cn.hexing.ami.dao.system.pojo.ggdmgl;

/**
 * @Description 编码分类对象
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-7
 * @version FDM2.0
 */

public class CodeCategory {
	//分类名称
	private String cateName;
	//分类编码
	private String cateCode;
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	
}