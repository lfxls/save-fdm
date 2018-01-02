package cn.hexing.ami.service.system.paramData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.paramData.ParamData;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface ParamMgtManagerInf extends QueryInf,DbWorksInf {
	public List<Object> getCateLs(Map<String,Object> map);
	public List<Object> getVerLs();
	public Map<String,Object> queryParamList(Map<String, Object> param, String start, String limit, String dir, String sort, String isExcel);
	public List<Object>  getParam(Map<String, Object> param);
	public List<Object>  getAllRead();
	public List<Object>  getAllSet();
	public List<Object>  getAllTest();
	public List<ParamData> getParamDataLs( List<Object> paramLs, HashMap<String,Object> map);
	public Map<String,Object> queryTestDetail(Map<String, Object> param, String start, String limit, String dir, String sort, String isExcel);

}
