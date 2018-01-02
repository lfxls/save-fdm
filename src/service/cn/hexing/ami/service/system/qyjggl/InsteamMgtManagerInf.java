package cn.hexing.ami.service.system.qyjggl;

import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface InsteamMgtManagerInf extends DbWorksInf, QueryInf {

	Map<String, Object> querySel(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	public Map<String,Object> getInsteam(Map<String, Object> param);
	
}
