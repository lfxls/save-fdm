package cn.hexing.ami.service.system.qxgl;

import java.util.Map;

import cn.hexing.ami.dao.system.pojo.sygl.Sygl;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

/** 
 * @Description  首页管理
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-13
 * @version AMI3.0 
 */
public interface SyglManagerInf extends DbWorksInf, QueryInf {
	
	/**
	 * 取得首页
	 * @param param
	 * @return
	 */
	public Sygl getSy(Map<String, Object> param) ;

	/**
	 * 判断名称是否重复
	 * @param param
	 * @return
	 */
	public ActionResult existingNm(Map<String, String> param);
	
}

