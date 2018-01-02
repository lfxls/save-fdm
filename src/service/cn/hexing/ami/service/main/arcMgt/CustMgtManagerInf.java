package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface CustMgtManagerInf extends QueryInf,DbWorksInf{

	
	public ActionResult initCust(Map<String,String> param);
	public ActionResult insCust(Map<String,String> param);
	public List<Object> getCust(Map<String,String> param);
	/**
	 * 批量更新用户是否派工状态
	 * @param paramList
	 */
	public ActionResult updCustDispStatus(List<Object> paramList);
}
