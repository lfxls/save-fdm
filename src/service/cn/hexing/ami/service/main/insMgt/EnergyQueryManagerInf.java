package cn.hexing.ami.service.main.insMgt;

import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

/**
 * 
 * @Description  电量查询
 * @author  xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-9-26
 * @version FDM2.0
 */
public interface EnergyQueryManagerInf extends QueryInf{
	/**
	 * 变压器list
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTf(Map<String, Object> param,
				String start, String limit, String dir, String sort, String isExcel);
}
