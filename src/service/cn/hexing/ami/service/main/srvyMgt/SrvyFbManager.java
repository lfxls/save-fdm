package cn.hexing.ami.service.main.srvyMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 勘察反馈
 * @author xcx
 * @Copyright 
 * @time：
 * @version FDM
 */

public class SrvyFbManager implements SrvyFbManagerInf {

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "srvyFb.";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 查询勘察反馈信息
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String,String> pCodeMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PCODEMAP);
		Map<String,Object> re = baseDAOIbatis.getExtGrid(param, sqlId + "querySrvyFb", start, limit, dir, sort, isExcel);
		List<Object> resultList = (List<Object>) re.get("result");
		for(int i = 0; i < resultList.size(); i++) {
			Map<String,Object> resultMap = (Map<String, Object>) resultList.get(i);
			for(Map.Entry<String, Object> entry : resultMap.entrySet()) {
				String key = entry.getKey();
				String value = StringUtil.getValue(entry.getValue());
				resultMap.put(key, StringUtil.isEmptyString(pCodeMap.get(value)) ? value : pCodeMap.get(value));
			}
		}
		return re;
	}
	
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	@Override
	public ActionResult storeSrvyFBData(List<Object> srvyFbDataList) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.executeBatch(sqlId + "insSrvyFB", srvyFbDataList);
		re.setSuccess(true);
		return re;
	}
	
	@Override
	public List<Object> getSrvyFbDataList(Map<String,Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "getSrvyFbData", param);
	}
	
	@Override
	public ActionResult updSrvyFb(List<Object> srvyFbDataList) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.executeBatch(sqlId + "updSrvyFb", srvyFbDataList);
		re.setSuccess(true);
		return null;
	}
	@Override
	public ActionResult updCustomer(Map<String, String> param) {
		ActionResult re = new ActionResult(false,"");
		List<Object> List =baseDAOIbatis.queryForList(sqlId+"getFbAr", param);
		Map<String, String> ar=(Map<String, String>) List.get(0);
		param.put("rname", ar.get("CNAME"));
		param.put("raddr", ar.get("ADDR"));
		param.put("rphone", ar.get("PHONE"));
		baseDAOIbatis.update(sqlId+"updCustomerAr", param);
		baseDAOIbatis.update(sqlId+"updSurPlnAr", param);
		re.setSuccess(true);
		re.setMsg("Customer Archive Update Success");
		return re;
	}

}