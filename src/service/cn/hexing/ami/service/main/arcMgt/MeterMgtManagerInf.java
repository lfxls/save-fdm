package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;


public interface MeterMgtManagerInf extends QueryInf,DbWorksInf {

	public Map<String,Object> getMeter(Map<String, Object> param);
	public ActionResult queryMeterbyMsn(Map<String, String>param);
	public ActionResult queryTFByUid(Map<String, String>param);
	public List<Object> queryTFById(Map<String, String> param);
	public ActionResult insMeter(Map<String, String> param);
}
