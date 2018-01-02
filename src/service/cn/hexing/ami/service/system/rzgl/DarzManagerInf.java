package cn.hexing.ami.service.system.rzgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

public interface DarzManagerInf extends QueryInf {

	public List<Object> getRootMenu(String lang);
	
	public List<Object> getMenu(String root, String lang);
	
	public List<Map<String, Object>> getCdTree(String sjcdbm, String lang);
}
