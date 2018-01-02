package cn.hexing.ami.service.system.ggdmgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.*;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/**
 * @Description 编码管理接口
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-6-7
 * @version FDM2.0
 */
public interface DmglManagerInf extends DbWorksInf, QueryInf{
	
	//根据分类编码查询分类信息
	public Code queryDmXxById(Map<String, String> param);
	
	//根据 分类编码和编码值查询编码信息
	public CodeCategory queryDmFlById(Map<String, String> param);
	/**
	 * 获取所有的Code
	 * @return
	 */
	public List<Object> getAllCode();
}
