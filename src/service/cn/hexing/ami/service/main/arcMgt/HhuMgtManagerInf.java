package cn.hexing.ami.service.main.arcMgt;

import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface HhuMgtManagerInf extends DbWorksInf, QueryInf {
	
	Map<String, Object> querySel(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);

	
	
	/**
	 * @Description 根据HHUID查询数据是否初始化
	 * @param param
	 * @return
	 */
	public String getHhuInit(String hhuID);
	
	/**
	 * @Description 下载完基础数据，数据初始化状态从No改成Yes
	 * @param param
	 * @return
	 */
	public void setHhuInit(String hhuID);
	
	/**
	 * @Description 编辑掌机获取信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getHhu(String hhuid);

}
