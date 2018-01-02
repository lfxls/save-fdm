package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;

public class MeterMgtManager implements MeterMgtManagerInf{
	
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="meterMgt.";
	private String menuId="11200";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "meterData", start, limit,
				dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re=new ActionResult();
		if (czid.equals(menuId + Constants.ADDOPT)) {//新增
			re = insMeter(param);
		} else if (czid.equals(menuId + Constants.UPDOPT)) {// 编辑
			re = updMeter(param);
		} else if (czid.equals(menuId + Constants.DELOPT)) {// 删除
			re = delMeter(param);
		}
		else if (czid.equals(menuId + "04")) {// 注销
			re = dropMeter(param);
		}
		return re;
	}
	/**
	 * 注销
	 * */
	private ActionResult dropMeter(Map<String, String> param) {
		ActionResult re=new ActionResult();
		baseDAOIbatis.update(sqlId+"dropMeter", param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.meterMgt.dropSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	/**
	 * 删除表计
	 * */
	private ActionResult delMeter(Map<String, String> param) {
		ActionResult re=new ActionResult();
		baseDAOIbatis.delete(sqlId+"delMeter", param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.meterMgt.delSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	/**
	 * 编辑表计
	 * */
	private ActionResult updMeter(Map<String, String> param) {
		ActionResult re=new ActionResult();
		if(!StringUtil.isEmptyString(param.get("simno"))){
			List<Object>simLs=baseDAOIbatis.queryForList(sqlId+"getSim", param);
			if(simLs!=null&&simLs.size()>0){
				re.setSuccess(false);
				re.setMsg("mainModule.arcMgt.meterMgt.ExistsSim", param.get(Constants.APP_LANG));
				return re;
			}
			else{
				SimMgtManagerInf simMgtManger=(SimMgtManagerInf) SpringContextUtil.getBean("simMgtManager");
				param.put("simsn", "");
				param.put("msp", "");
				simMgtManger.doDbWorks("1150001",param);
			}
			
		}
		baseDAOIbatis.update(sqlId+"updMeter",param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.meterMgt.updSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	/**
	 * 新增表计
	 * */
	public ActionResult insMeter(Map<String, String> param) {
		
		param.put("status", "0");
		ActionResult re=new ActionResult();
		List<Object>ls=baseDAOIbatis.queryForList(sqlId+"getMeter", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.meterMgt.existsMeter", param.get(Constants.APP_LANG));
			return re;
		}
		
		if((!StringUtil.isEmptyString(param.get("tfid")))&&(!StringUtil.isEmptyString(param.get("cno")))){
			List<Object> tfLs=queryTFById(param);
			if(tfLs!=null&&tfLs.size()>0){
				Map<String,String>map=(Map<String, String>) tfLs.get(0);
				param.put("nodeIddw", map.get("UID"));
				param.put("status", "1");
			}
			else{
				re.setSuccess(false);
				re.setMsg("mainModule.arcMgt.meterMgt.notExistsTf", param.get(Constants.APP_LANG));
				return re;
			}
			CustMgtManagerInf custMgtManager=(CustMgtManagerInf) SpringContextUtil.getBean("custMgtManager");
			List<Object>custLs=custMgtManager.getCust(param);
			if(custLs!=null&&custLs.size()>0){
				Map<String,String>map=(Map<String, String>) custLs.get(0);
				String uid=map.get("UID");
				if(!uid.equals(param.get("nodeIddw"))){
					re.setSuccess(false);
					re.setMsg("mainModule.arcMgt.meterMgt.UidNotEquals", param.get(Constants.APP_LANG));
					return re;
				}
			}
			else{
				re.setSuccess(false);
				re.setMsg("mainModule.arcMgt.meterMgt.notExistsCust", param.get(Constants.APP_LANG));
				return re;
			}
		}
		if(!StringUtil.isEmptyString(param.get("simno"))){
			List<Object>simLs=baseDAOIbatis.queryForList(sqlId+"getSim", param);
			if(simLs!=null&&simLs.size()>0){
				re.setSuccess(false);
				re.setMsg("mainModule.arcMgt.meterMgt.ExistsSim", param.get(Constants.APP_LANG));
				return re;
			}
			else{
				SimMgtManagerInf simMgtManger=(SimMgtManagerInf) SpringContextUtil.getBean("simMgtManager");
				param.put("simsn", "");
				param.put("msp", "");
				simMgtManger.doDbWorks("1150001",param);
			}
			
		}
		baseDAOIbatis.insert(sqlId+"insMeter",param);
		if(!StringUtil.isEmptyString(param.get("cno"))){
			baseDAOIbatis.insert(sqlId+"updCust",param);
		}
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.meterMgt.insSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	//编辑表计获取信息
	@Override
	public Map<String, Object> getMeter(Map<String, Object> param) {
		return (Map<String, Object>) baseDAOIbatis.queryForList(sqlId+"getMeter", param).get(0);
	}
	//校验表计是否已存在
	@Override
	public ActionResult queryMeterbyMsn(Map<String, String> param) {
		ActionResult re = new ActionResult();
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getMeter", param);
		if (ls != null && ls.size() > 0) {
		
			re.setSuccess(true);
		} else {
			re.setSuccess(false);
		}
		return re;
	}
	//校验变压器是否在该单位下
	public ActionResult queryTFByUid(Map<String, String> param) {
		ActionResult re = new ActionResult();
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getBj", param);
		if (ls != null && ls.size() > 0) {
			//DaBj bj = (DaBj) ls.get(0);
		//	re.setMsg(CommonUtil.obj2Json(bj));
			re.setSuccess(true);
		} else {
			re.setSuccess(false);
		}
		return re;
	}
	public List<Object> queryTFById(Map<String, String> param) {
		return baseDAOIbatis.queryForList(sqlId + "queryTFById", param);
	}



}
