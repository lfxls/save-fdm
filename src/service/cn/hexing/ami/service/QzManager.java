package cn.hexing.ami.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.serviceInf.QzManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;

public class QzManager implements QzManagerInf{
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	String sqlId = "qz.";
	String menuId = "00000";
	
	/**
	 * 得到加入群组列表
	 * @param fwdw
	 * @param fwjb
	 * @param fwcj
	 * @param operatorId
	 * @param qzlx
	 * @param qzfl
	 * @return
	 */
	public List<Object> allGroup(Map<String,Object> paramMap){
		return baseDAOIbatis.queryForList(sqlId + "getGroup",paramMap);
	}

	public List<Object> getList(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "getGroupGrid",param);
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult(true,"");
		String qzid = param.get("qzid");
		String qzlx = param.get("qzlx");
		if ((menuId + Constants.ADDOPT).equals(czid)) { // 新增
			String[] zdjhs = param.get("zdjh").split(",");
			// 查找群组名称是否存在，存在的话提示
			List<Object> qzmList = baseDAOIbatis.queryForList(sqlId + "qzmExsit",param);
			if(qzmList != null && qzmList.size() != 0){
				re.setSuccess(false);
				re.setMsg(I18nUtil.getText("common.qz.exist_qz", param.get(Constants.APP_LANG)));
				return re;
			}
			// 创建群组定义
			param.put("dwdm", param.get(Constants.UNIT_CODE));
			param.put("czyid", param.get(Constants.CURR_STAFFID));
			baseDAOIbatis.insert(sqlId + "insQzdy",param);
			// 创建群组明细
			List<Object> reLs = new ArrayList<Object>();
			for(String jh:zdjhs){
				Map<String,String> in = new HashMap<String,String>();
				in.put("qzid", param.get("qzid"));
				in.put("qzlx", qzlx);
				if("bj".equals(qzlx)){
					in.put("bjjhIns", jh.split(":")[1]);
					in.put("zdjhIns", "");
				}else{
					in.put("zdjhIns", jh.split(":")[1]);
					in.put("bjjhIns", "");
				}
				
				in.put("hhIns",  jh.split(":")[0]);
				reLs.add(in);
			}
			
			List<String> statementID = new ArrayList<String>();
			statementID.add(sqlId + "insQzmx");
			
			baseDAOIbatis.executeBatchTransaction(statementID, reLs);
			re.setMsg(I18nUtil.getText("common.qz.new_succ", param.get(Constants.APP_LANG)));
		} else if ((menuId + Constants.UPDOPT).equals(czid)) { // 更新
			String[] zdjhs = param.get("zdjh").split(",");
			// 加入群组明细
			List<Object> reLs = new ArrayList<Object>();
			for(String jh:zdjhs){
				Map<String,String> in = new HashMap<String,String>();
				in.put("qzid", qzid);
				in.put("qzlx", qzlx);
				if("bj".equals(qzlx)){
					in.put("bjjhIns", jh.split(":")[1]);
					in.put("zdjhIns", "");
				}else{
					in.put("zdjhIns", jh.split(":")[1]);
					in.put("bjjhIns", "");
				}
				
				in.put("hhIns",  jh.split(":")[0]);
				reLs.add(in);
			}
			
			List<String> statementID = new ArrayList<String>();
			statementID.add(sqlId + "updQzmx");
			baseDAOIbatis.executeBatchTransaction(statementID, reLs);
			re.setMsg(I18nUtil.getText("common.qz.add_succ", param.get(Constants.APP_LANG)));
		} else if ((menuId + Constants.DELOPT).equals(czid)) { // 删除
			baseDAOIbatis.delete(sqlId + "deleteOneGroupMx",qzid);
			baseDAOIbatis.delete(sqlId + "deleteOneGroup",qzid);
			re.setMsg(I18nUtil.getText("common.qz.del_succ", param.get(Constants.APP_LANG)));
		}
		return re;
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
	public List<Object> getAdvZdjh(Map<String,Object> paramMap){
		return baseDAOIbatis.queryForList(sqlId + "getAdvZdjh",paramMap);
	}
}
