package cn.hexing.ami.service.main.arcMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.da.DaYh;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;

public class CustMgtManager implements CustMgtManagerInf {

	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="custMgt.";
	private String menuId="11100";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	//查询用户列表
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "CustData", start, limit,
				dir, sort, isExcel);
	}
	//查询用户绑定表计列表
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		
		return  baseDAOIbatis.getExtGrid(param, sqlId + "getMeter", start, limit,
				dir, sort, isExcel);
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();	
		if (czid.equals(menuId + Constants.ADDOPT)) {// 开户
			re = insCust(param);
		} else if (czid.equals(menuId + Constants.UPDOPT)) {// 编辑
			re = updCust(param);
		} else if (czid.equals(menuId + Constants.DELOPT)) {// 删除
			re = delCust(param);
		}
		return re;
	}
	public ActionResult insCust(Map<String, String> param) {
		ActionResult re = new ActionResult();	
		//验证户号是否已存在
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getCust", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.custMgt.existsCust", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.insert(sqlId + "insCust", param);	
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.custMgt.addSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	
	public ActionResult updCust(Map<String, String> param) {
		ActionResult re = new ActionResult();	
		baseDAOIbatis.insert(sqlId + "updCust", param);
		baseDAOIbatis.insert(sqlId + "updMeter", param);	
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.custMgt.updSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	public ActionResult delCust(Map<String, String> param) {
		ActionResult re = new ActionResult();
		
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getMeterQuery", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.custMgt.existsMeter", param.get(Constants.APP_LANG));
			return re;
		}
		List<Object> ls2 = baseDAOIbatis.queryForList(sqlId + "getPlanQuery", param);
		if (ls2 != null && ls2.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.custMgt.existsPlan", param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.insert(sqlId + "delCust", param);	
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.custMgt.delSuccess", param.get(Constants.APP_LANG));
		return re;
	}
	public ActionResult dropCust(Map<String, String> param) {
		ActionResult re = new ActionResult();	
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);		
	}

	@Override
	public ActionResult initCust(Map<String, String> param) {
		ActionResult re = new ActionResult();	
		//验证户号是否已存在
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getCust", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(true);
			return re;
		}
		re.setSuccess(false);
		return re;
	}
	public List<Object> getCust(Map<String,String>param){
		return baseDAOIbatis.queryForList(sqlId + "getCust", param);
	}

	/**
	 * 批量更新用户是否派工状态
	 * @param paramList
	 */
	public ActionResult updCustDispStatus(List<Object> paramList) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.executeBatch(sqlId + "updCustDispStatus", paramList);
		re.setSuccess(true);
		return re;
	}
}
