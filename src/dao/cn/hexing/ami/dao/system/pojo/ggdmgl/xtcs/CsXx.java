package cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs;

/**
 * @Description 参数详细信息实体类
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-14
 * @version AMI3.0
 */
public class CsXx {
	//参数id
	private String para_id;
	//参数分类id
	private String para_sort_id;
	//参数描述
	private String name;
	//参数值类型
	private String type_code;
	//参数值
	private String default_value;
	//参数值
	private String max_limit;
	//参数值
	private String min_limit;
	//参数的使用状态
	private String syzt;
	
	public String getPara_id() {
		return para_id;
	}
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getPara_sort_id() {
		return para_sort_id;
	}
	public void setPara_sort_id(String para_sort_id) {
		this.para_sort_id = para_sort_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public String getMax_limit() {
		return max_limit;
	}
	public void setMax_limit(String max_limit) {
		this.max_limit = max_limit;
	}
	public String getMin_limit() {
		return min_limit;
	}
	public void setMin_limit(String min_limit) {
		this.min_limit = min_limit;
	}
	public String getSyzt() {
		return syzt;
	}
	public void setSyzt(String syzt) {
		this.syzt = syzt;
	}
}
