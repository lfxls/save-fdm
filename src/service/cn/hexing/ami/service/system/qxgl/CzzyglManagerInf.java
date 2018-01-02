package cn.hexing.ami.service.system.qxgl;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.QueryInf;

public interface CzzyglManagerInf extends QueryInf {

	List<Map<String, Object>> getCzTree(String czlbid, String sjcdbm,
			String lang);

}
