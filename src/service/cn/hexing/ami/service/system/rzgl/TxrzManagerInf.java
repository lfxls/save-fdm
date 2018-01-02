package cn.hexing.ami.service.system.rzgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

/** 
 * @Description  通讯日志
 * @author  panc
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-6-15
 * @version AMI3.0 
 */
public interface TxrzManagerInf extends QueryInf {

	List<Object> getCzlxList(Map<String, Object> param);

}

