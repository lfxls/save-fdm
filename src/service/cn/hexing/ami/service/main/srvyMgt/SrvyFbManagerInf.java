package cn.hexing.ami.service.main.srvyMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface SrvyFbManagerInf extends QueryInf{

	public ActionResult storeSrvyFBData(List<Object> srvyFbDataList);
	
	public List<Object> getSrvyFbDataList(Map<String,Object> param);
	
	public ActionResult updSrvyFb(List<Object> srvyFbDataList);

	public ActionResult updCustomer(Map<String, String> param);
}
