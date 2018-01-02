package cn.hexing.ami.service.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;

public class ModelMgtManager implements ModelMgtManagerInf {

	private String sqlId="modelMgt.";
	private String menuId="11700";
	private BaseDAOIbatis baseDAOIbatis;
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	public List<Object> getModelLs(String mfid,String lang){
		Map<String ,String>map=new HashMap<String, String>();
		map.put("mfid", mfid);
		List<Object> ModelLs=baseDAOIbatis.queryForList(sqlId+"getModelLs",map);
		Map<String,String> o = new HashMap<String, String>();
		o.put("BM","");
		String str=I18nUtil.getText("common.select.all",lang);
		o.put("MC",str);
		ModelLs.add(0,o);
		return ModelLs;
	}
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getModel", start, limit, dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re=new ActionResult();
		if (czid.equals(menuId + Constants.ADDOPT)) {//新增
			re = insModel(param);
		} else if (czid.equals(menuId + Constants.UPDOPT)) {// 编辑
			re = updModel(param);
		} else if (czid.equals(menuId + Constants.DELOPT)) {// 删除
			re = delModel(param);
		}
		
		return re;
	}

	private ActionResult delModel(Map<String, String> param) {
		ActionResult re=new ActionResult();
		List<Object>ls=baseDAOIbatis.queryForList(sqlId+"getDlms", param);
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.modelMgt.existDlms",param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.delete(sqlId+"delModel", param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.modelMgt.delSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult updModel(Map<String, String> param) {
		ActionResult re=new ActionResult();
		baseDAOIbatis.update(sqlId+"updModel", param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.modelMgt.updSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	private ActionResult insModel(Map<String, String> param) {
		String mfid=param.get("mfid");
		if(!"14".equals(mfid)){
			String model=param.get("model");
			param.put("verid", mfid+model);
		}
		List<Object>ls=baseDAOIbatis.queryForList(sqlId+"getVerid", param);
		ActionResult re=new ActionResult();
		if (ls != null && ls.size() > 0) { 
			re.setSuccess(false);
			re.setMsg("mainModule.arcMgt.modelMgt.exist",param.get(Constants.APP_LANG));
			return re;
		}
		baseDAOIbatis.insert(sqlId+"insModel", param);
		re.setSuccess(true);
		re.setMsg("mainModule.arcMgt.modelMgt.insSuccess", param.get(Constants.APP_LANG));
		return re;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> queryByModel(String model) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
