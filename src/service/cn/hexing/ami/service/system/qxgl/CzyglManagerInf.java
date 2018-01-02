package cn.hexing.ami.service.system.qxgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.dao.common.pojo.TreeCheckNode;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface CzyglManagerInf extends DbWorksInf,QueryInf {

	List<TreeNode> getJgTree(String nodeId, String nodeType, String unitCode, String opID, String deptID, String accessFlag);

	List<TreeCheckNode> getJsTree(String czyid);

	List<TreeCheckNode> getQxFwTree(String czyid, String dwdm);

	public AqCzy initCzy(String czyid);
	
	/**
	 * 获取用户列表
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 已经授权的售电点列表
	 */
	public Map<String, Object> getAuthorizedPosList(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
	
	/**
	 * 获取单位树
	 * @param sjdwdm
	 * @param czyId
	 * @param bmid
	 * @param fwbj
	 * @return
	 */
	public List<TreeNode> getDwTree(String sjdwdm, String czyId, String bmid,
			String fwbj);

	/**
	 * 获得未授权的pos机列表 
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	Map<String, Object> getPosMachineList(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);

	/**
	 * 获取已授权的pos机列表
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	Map<String, Object> getAuthorizedPosMachineList(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel);
}
