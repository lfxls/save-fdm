package cn.hexing.ami.service.system.rzgl;

import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;


/**
 * @Description 登陆日志Service接口
 * @author yuj
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-6-26
 * @version AMI3.0
 */
public interface DlrzManagerInf extends QueryInf {

	Map<String, Object> queryDetail(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
}
