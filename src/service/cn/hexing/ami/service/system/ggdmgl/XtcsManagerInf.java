package cn.hexing.ami.service.system.ggdmgl;

import java.util.Map;

import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsFl;
import cn.hexing.ami.dao.system.pojo.ggdmgl.xtcs.CsXx;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/**
 * @Description 系统配置Service接口
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-14
 * @version AMI3.0
 */
public interface XtcsManagerInf extends DbWorksInf, QueryInf{
	
	/**
	 * 根据 id查询系统参数分类
	 * @param param
	 * @return
	 */
	public CsFl queryCsFlById(Map<String, Object> param);
	
	/**
	 * 根据id查询系统参数详细信息
	 * @param param
	 * @return
	 */
	public CsXx queryCsXxById(Map<String, Object> param);
}
