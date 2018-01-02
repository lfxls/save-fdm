package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;

public interface ModelMgtManagerInf extends QueryInf,DbWorksInf {
	public Map<String, String> queryByModel(String model);
		
	public List<Object> getModelLs(String mfid,String lang);
}