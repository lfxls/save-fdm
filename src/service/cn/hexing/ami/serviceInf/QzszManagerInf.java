package cn.hexing.ami.serviceInf;

import java.util.List;
import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.util.ActionResult;

public interface QzszManagerInf extends DbWorksInf,QueryInf{
	List<Object> getQzDetail(String qzid);
	
	Map<String, Object> getUpdateGrid(Map<String, Object> paramMap, String start,
			String limit, String dir, String sort, String isExcel);
	
	Map<String, Object> queryForYh(Map<String, Object> paramMap, String start,
			String limit, String dir, String sort, String isExcel);
	
	Map<String, Object> queryDetailQzBj(Map<String, Object> paramMap, String start,
			String limit, String dir, String sort, String isExcel);
	
	ActionResult save(Map<String, String> param,Util util);
	
	List<Object> getQzMc(Map<String,Object> param);
	
	 void insqzsj(List<Object> reLs );
	 
	 String getSeq();
	 
	Map<String, Object> queryDetailQz(Map<String, Object> paramMap, String start,
				String limit, String dir, String sort, String isExcel);
	
	 /**
		 * 获得当前所有费率方案
		 * @param 
		 * @return
		 */
	 public List<Object> getAllTariff();
	 

		/**
		 * group setting, enable delete meters or terminals in a certain group when Edit the Group
		 * @param String czid, Map<String,String>
		 * @return ActionResult
		 * @date 2015.01.06
		 * @author yaoj
		 */
	 public ActionResult delBjsInGroup(String czid, Map<String, String> param);
	 
	 /**
		 * 获取行业树
		 * @param sjhydm
		 * @return
		 */
		List<TreeNode> getHyTree(String sjhydm,String lang);
		/**
		 * 获取用电属性树
		 * @return
		 */
		List<TreeNode> getYdsxTree(String lang);
}
