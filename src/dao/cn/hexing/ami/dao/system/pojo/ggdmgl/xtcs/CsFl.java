package cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs;
/**
 * @Description 参数分类实体类
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-14
 * @version AMI3.0
 */
public class CsFl {
	//参数分类id
	private String para_sort_id;
	//参数分类编码
	private String p_sort_code;
	//参数分类名称
	private String name;
	//参数分类描述
	private String sort_desc;
	//参数的使用状态
	private String syzt;
	
	public String getPara_sort_id() {
		return para_sort_id;
	}
	public void setPara_sort_id(String para_sort_id) {
		this.para_sort_id = para_sort_id;
	}
	public String getP_sort_code() {
		return p_sort_code;
	}
	public void setP_sort_code(String p_sort_code) {
		this.p_sort_code = p_sort_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSort_desc() {
		return sort_desc;
	}
	public void setSort_desc(String sort_desc) {
		this.sort_desc = sort_desc;
	}
	public String getSyzt() {
		return syzt;
	}
	public void setSyzt(String syzt) {
		this.syzt = syzt;
	}
	
}
