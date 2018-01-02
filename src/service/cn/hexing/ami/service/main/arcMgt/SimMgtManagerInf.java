package cn.hexing.ami.service.main.arcMgt;

import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface SimMgtManagerInf extends DbWorksInf, QueryInf {
	
	Map<String, Object> querySel(Map<String, Object> param, String start, String limit, String dir, String sort, String isExcel);
	
	public Map<String,Object> getSim(Map<String, Object> param);
}
