package cn.hexing.ami.service.system.qxgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.XtCd;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

/**
 * @Description 角色管理 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-7-25
 * @version AMI3.0
 */
public interface JsglManagerInf extends QueryInf, DbWorksInf {
	
	/**
	 * 获取角色对应的菜单，并且自动选中
	 * @param jsid
	 * @param sjcdbm
	 * @param menurole
	 * @param lang
	 * @return
	 */
	public List<TreeCheckNode> getJsCdTree(String jsid, String sjcdbm,HashMap<XtCd,String> menurole,String lang);

	/**
	 * 获取操作树菜单
	 * @param jsid
	 * @param lang
	 * @return
	 */
	List<TreeCheckNode> getJsCzTree(String jsid, String lang);
	
	/**
	 * 角色首页树
	 * @param jsid
	 * @param lang
	 * @return
	 */
	public List<TreeCheckNode> getJsSyTree(String jsid, String lang);
	
	/**
	 * 订阅信息树
	 * @param jsid
	 * @param String lang
	 * @return
	 */
//	public List<TreeCheckNode> getJsDyTree(String jsid,String lang);
	
	/**
	 * 角色是否使用中
	 * @param param
	 * @return
	 */
	public boolean jsIsUse(Map<String, String> param);
}
