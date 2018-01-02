package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface TransfMgtManagerInf extends DbWorksInf, QueryInf {
	
	Map<String, Object> querySel(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);

	public List<Object> getAllTransformer();
	
	/**
	 * @Description 编辑变压器获取信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getTf(Map<String, Object> param);

}
