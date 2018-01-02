package cn.hexing.ami.service.system.qyjggl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

/**
 * 区域管理
 * @Description 
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-9-25
 * @version AMI3.0
 */
public interface JgglManagerInf extends DbWorksInf, QueryInf {

	List<TreeNode> getDwTree(String nodeId, String nodeType);

	List<TreeCheckNode> getQxFwTree(String dwdm, String bmid);
	public ActionResult getPf(Map<String, String> param);
	/**
	 * 根据区域代码模糊查询区域信息
	 * @return
	 */
	Map<String, Object> getQyList(Map<String, Object> param, String start,String limit, String dir, String sort, String isExcel);
}
