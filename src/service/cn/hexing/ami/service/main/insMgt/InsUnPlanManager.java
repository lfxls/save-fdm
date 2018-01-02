package cn.hexing.ami.service.main.insMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.main.pojo.insMgt.WorkOrder;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/** 
 * @Description  无序安装计划
 * @author  xcx
 * @Copyright 
 * @time：2016-4-18
 * @version FDM 
 */
public class InsUnPlanManager implements InsUnPlanManagerInf {
	
	private static Logger logger = Logger.getLogger(InsUnPlanManager.class.getName());

	private BaseDAOIbatis baseDAOIbatis;

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	private static String menuId = "12100"; //菜单ID
	private static String sqlId = "insUnPlan."; //sql文件命名空间

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		String dispFlag = StringUtil.getValue(param.get("dispFlag"));
		if((menuId + Constants.ADDOPT).equals(czid)) {//新增
			if("meter".equals(dispFlag)) {
				re = addDispCust(param);
			} else if("dcu".equals(dispFlag)) {
				re = addDDispTf(param);
			} if("col".equals(dispFlag)) {
				re = addCDispTf(param);
			}
		} else if((menuId + Constants.UPDOPT).equals(czid)) {//编辑
			if("meter".equals(dispFlag)) {
				re = editDispCust(param);
			} else if("dcu".equals(dispFlag)) {
				re = editDDispTf(param);
			} if("col".equals(dispFlag)) {
				re = editCDispTf(param);
			}
		} else if((menuId + Constants.DELOPT).equals(czid)) {//删除
			if("meter".equals(dispFlag)) {
				re = delDispCust(param);
			} else if("dcu".equals(dispFlag)) {
				re = delDDispTf(param);
			} if("col".equals(dispFlag)) {
				re = delCDispTf(param);
			}
			
		}
		return re;
	}
	
	/**
	 * 新增表派发用户安装计划
	 * @param param
	 * @return
	 */
	public ActionResult addDispCust(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String cnos = StringUtil.getValue(param.get("cnos"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		String[] cnoArray = cnos.split(",");
		//获取工单序列号
		String woid = (String) baseDAOIbatis.queryForObject(sqlId + "getWOID", null, String.class);
		woid = "W" + StringUtil.leftZero(woid, 14);
		//生成工单
		insWorkOrder(woid,popid,coptid,fTime);
		//生成表安装计划
		List<Object> paramList = new ArrayList<Object>();
		for(String cno : cnoArray) {
			String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getMeterPlnId", null, String.class);
			Map<String,Object> newParam = new HashMap<String,Object>();
			newParam.put("pid", "P" + StringUtil.leftZero(pid,14));
			newParam.put("cno", cno);
			newParam.put("woid", woid);
			newParam.put("status", status);
			newParam.put("coptid", coptid);
			newParam.put("devType", Constants.DEV_TYPE_METER);
			paramList.add(newParam);
		}
		baseDAOIbatis.executeBatch(sqlId + "insMInsP", paramList);
		//插入安装计划操作日志
		baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", paramList);
		re.setSuccess(true);
		re.setDataObject(woid);
		re.setMsg("mainModule.insMgt.unPlan.add.success", lang);
		return re;
	}
	
	/**
	 * 编辑表派发用户安装计划
	 * @param param
	 * @return
	 */
	public ActionResult editDispCust(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String cnos = StringUtil.getValue(param.get("cnos"));
		String ecnos = StringUtil.getValue(param.get("ecnos"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String woid = StringUtil.getValue(param.get("woid"));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		re = woCheck(woid,lang);//验证工单
		if(!re.isSuccess()) {
			return re;
		}
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		//更新工单
		updWorkOrder(woid,popid,coptid,fTime);
		//更新用户分配安装计划
		if(!StringUtil.isEmptyString(cnos)) {
			String[] cnoArray = cnos.split(",");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("cnos", cnoArray);
			map.put("woid", woid);
			//删除对应工单下当前户号之外的其他的表安装计划操作日志
			baseDAOIbatis.delete(sqlId + "delOPLogByWOAndCno", map);
			//删除对应工单下当前户号之外的其他的表安装计划
			baseDAOIbatis.delete(sqlId + "delByWOAndCno", map);
			List<Object> paramList = new ArrayList<Object>();
			for(String cno : cnoArray) {
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("cno", cno);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("coptid", coptid);
				newParam.put("devType", Constants.DEV_TYPE_METER);
				String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getIdByWOIDAndCno", newParam, String.class);
				newParam.put("pid", pid);
				paramList.add(newParam);
			}
			//批量更新表安装计划
			baseDAOIbatis.executeBatch(sqlId + "updMInsP", paramList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", paramList);
		} else {
			if(StringUtil.isEmptyString(cnos)) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("cnos", new String[]{""});
				map.put("woid", woid);
				//删除对应工单下当前户号之外的其他的表安装计划操作日志
				baseDAOIbatis.delete(sqlId + "delOPLogByWOAndCno", map);
				//删除对应工单下当前户号之外的其他的的表安装计划
				baseDAOIbatis.delete(sqlId + "delByWOAndCno", map);
			}
		}
		if(!StringUtil.isEmptyString(ecnos)) {
			String[] ecnoArray = ecnos.split(",");
			List<Object> paramList = new ArrayList<Object>();
			for(String cno : ecnoArray) {
				String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getMeterPlnId", null, String.class);
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("pid", "P" + StringUtil.leftZero(pid,14));
				newParam.put("cno", cno);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("devType", Constants.DEV_TYPE_METER);
				newParam.put("coptid", coptid);
				paramList.add(newParam);
			}
			//批量插入表安装计划
			baseDAOIbatis.executeBatch(sqlId + "insMInsP", paramList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", paramList);
		}
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.edit.success", lang);
		return re;
	}
	
	/**
	 * 删除表派发用户
	 * @param param
	 * @return
	 */
	public ActionResult delDispCust(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String woid = StringUtil.getValue(param.get("woid"));
		re = woCheck(woid,lang);//验证工单
		if(!re.isSuccess()) {
			return re;
		}
		//删除工单对应的表安装计划操作日志
		baseDAOIbatis.delete(sqlId + "delMInsPlnOPLog", param);
		//删除工单对应的表安装计划
		baseDAOIbatis.delete(sqlId + "delMInsP", param);
		//删除工单操作日志
		baseDAOIbatis.delete(sqlId + "delWOOPLog", param);
		//删除工单
		baseDAOIbatis.delete(sqlId + "delWO", param);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.del.success", lang);
		return re;
	}
	
	/**
	 * 生成工单
	 * @param woid 工单号
	 * @param popid 处理人
	 * @param coptid 创建人
	 * @param fTime 计划完成时间
	 */
	public void insWorkOrder(String woid, String popid, String coptid,String fTime) {
		Map<String,Object> newParam = new HashMap<String,Object>();
		newParam.put("woid", woid);
		if(popid.equals("")) {
			newParam.put("popid", null);
			newParam.put("status", "0");
		} else {
			newParam.put("popid", popid);
			newParam.put("status", "1");
		}
		newParam.put("coptid", coptid);
		newParam.put("rspName", null);
		newParam.put("fTime", fTime);
		//插入工单
		baseDAOIbatis.insert(sqlId + "insWorkOrder", newParam);
		//插入工单操作日志
		baseDAOIbatis.insert(sqlId + "insWOOPLog", newParam);
	}
	
	/**
	 * 更新工单
	 * @param woid 工单号
	 * @param popid 处理人
	 * @param coptid 创建人
	 * @param fTime 计划完成时间
	 */
	public void updWorkOrder(String woid, String popid,String coptid,String fTime) {
		Map<String,Object> newParam = new HashMap<String,Object>();
		newParam.put("woid", woid);
		if(popid.equals("")) {
			newParam.put("popid", null);
			newParam.put("status", "0");
		} else {
			newParam.put("popid", popid);
			newParam.put("status", "1");
		}
		newParam.put("coptid", coptid);
		newParam.put("rspName", null);
		newParam.put("fTime", fTime);
		//更新工单
		baseDAOIbatis.update(sqlId + "updWorkOrder", newParam);
		//插入工单操作日志
		baseDAOIbatis.insert(sqlId + "insWOOPLog", newParam);
	}
	
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryInsWO", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询集中器安装计划
	 */
	public Map<String, Object> queryDInsP(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryDInsP", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询采集器安装计划
	 */
	public Map<String, Object> queryCInsP(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryCInsP", start, limit, dir, sort, isExcel);
	}
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * 查询未选择的用户
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryUnSelCust(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "unSelCust", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询已选择的用户
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySelCust(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "selCust", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 根据工单ID查询已分配的用户
	 * @param woid
	 * @return
	 */
	public String getDispCustByWoid(String woid) {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getDispCust", woid);
		String cnos = "";
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> map = (Map<String, String>) list.get(i);
			String cno = StringUtil.getValue(map.get("CNO"));
			if(i == 0) {
				cnos = cno;
			} else {
				cnos = cnos + "," + cno;
			}
		}
		return cnos;
	}
	
	/**
	 * 查询已选择的变压器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySelTf(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "selTf", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询未选择的变压器
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryUnSelTf(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "unSelTf", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 新增集中器派发变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult addDDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String tfIds = StringUtil.getValue(param.get("tfIds"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		String[] tfIdArray = tfIds.split(",");
		//获取工单序列号
		String woid = (String) baseDAOIbatis.queryForObject(sqlId + "getWOID", null, String.class);
		//生成工单
		insWorkOrder(woid,popid,coptid,fTime);
		//生成表安装计划
		List<Object> paramList = new ArrayList<Object>();
		for(String tfId : tfIdArray) {
			Map<String,Object> newParam = new HashMap<String,Object>();
			newParam.put("tfId", tfId);
			newParam.put("woid", woid);
			newParam.put("status", status);
			newParam.put("coptid", coptid);
			paramList.add(newParam);
		}
		baseDAOIbatis.executeBatch(sqlId + "insDInsP", paramList);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.add.success", lang);
		return re;
	}
	
	/**
	 * 编辑集中器派发变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult editDDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String tfIds = StringUtil.getValue(param.get("tfIds"));
		String etfIds = StringUtil.getValue(param.get("etfIds"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String woid = StringUtil.getValue(param.get("woid"));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		//更新工单
		updWorkOrder(woid,popid,coptid,fTime);
		//更新安装计划
		if(!StringUtil.isEmptyString(tfIds)) {
			String[] tfIdArray = tfIds.split(",");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tfIds", tfIdArray);
			map.put("woid", woid);
			//删除对应工单下当前变压器之外的其他集中器安装计划
			baseDAOIbatis.delete(sqlId + "delDByWOAndTf", map);
			List<Object> paramList = new ArrayList<Object>();
			for(String tfId : tfIdArray) {
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("tfId", tfId);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("coptid", coptid);
				paramList.add(newParam);
			}
			//批量更新集中器安装计划
			baseDAOIbatis.executeBatch(sqlId + "updDInsP", paramList);
		} else {
			if(StringUtil.isEmptyString(tfIds)) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tfIds", new String[]{""});
				map.put("woid", woid);
				//删除对应工单下集中器安装计划
				baseDAOIbatis.delete(sqlId + "delDByWOAndTf", map);
			}
		}
		if(!StringUtil.isEmptyString(etfIds)) {
			String[] etfIdArray = etfIds.split(",");
			List<Object> paramList = new ArrayList<Object>();
			for(String tfId : etfIdArray) {
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("tfId", tfId);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("coptid", coptid);
				paramList.add(newParam);
			}
			//批量插入集中器安装计划
			baseDAOIbatis.executeBatch(sqlId + "insDInsP", paramList);
		}
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.edit.success", lang);
		return re;
	}
	
	/**
	 * 删除集中器派发变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult delDDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		baseDAOIbatis.delete(sqlId + "delDInsP", param);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.del.success", lang);
		return re;
	}
	
	/**
	 * 新增采集器派发变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult addCDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String tfIds = StringUtil.getValue(param.get("tfIds"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		String[] tfIdArray = tfIds.split(",");
		//获取工单序列号
		String woid = (String) baseDAOIbatis.queryForObject(sqlId + "getWOID", null, String.class);
		//生成工单
		insWorkOrder(woid,popid,coptid,fTime);
		//生成表安装计划
		List<Object> paramList = new ArrayList<Object>();
		for(String tfId : tfIdArray) {
			Map<String,Object> newParam = new HashMap<String,Object>();
			newParam.put("tfId", tfId);
			newParam.put("woid", woid);
			newParam.put("status", status);
			newParam.put("coptid", coptid);
			paramList.add(newParam);
		}
		baseDAOIbatis.executeBatch(sqlId + "insCInsP", paramList);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.add.success", lang);
		return re;
	}
	
	/**
	 * 编辑采集器派发变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult editCDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String tfIds = StringUtil.getValue(param.get("tfIds"));
		String etfIds = StringUtil.getValue(param.get("etfIds"));
		String popid = StringUtil.getValue(param.get("popid"));
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String woid = StringUtil.getValue(param.get("woid"));
		String coptid = StringUtil.getValue(param.get("CURR_STAFFID"));
		String fTime = StringUtil.getValue(param.get("fTime"));
		String status = "0";
		if(!popid.equals("")) {
			status = "1";
		}
		//更新工单
		updWorkOrder(woid,popid,coptid,fTime);
		//更新安装计划
		if(!StringUtil.isEmptyString(tfIds)) {
			String[] tfIdArray = tfIds.split(",");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tfIds", tfIdArray);
			map.put("woid", woid);
			//删除对应工单下当前变压器之外的其他集中器安装计划
			baseDAOIbatis.delete(sqlId + "delCByWOAndTf", map);
			List<Object> paramList = new ArrayList<Object>();
			for(String tfId : tfIdArray) {
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("tfId", tfId);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("coptid", coptid);
				paramList.add(newParam);
			}
			//批量更新采集器安装计划
			baseDAOIbatis.executeBatch(sqlId + "updCInsP", paramList);
		} else {
			if(StringUtil.isEmptyString(tfIds)) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tfIds", new String[]{""});
				map.put("woid", woid);
				//删除对应工单下采集器安装计划
				baseDAOIbatis.delete(sqlId + "delCByWOAndTf", map);
			}
		}
		if(!StringUtil.isEmptyString(etfIds)) {
			String[] etfIdArray = etfIds.split(",");
			List<Object> paramList = new ArrayList<Object>();
			for(String tfId : etfIdArray) {
				Map<String,Object> newParam = new HashMap<String,Object>();
				newParam.put("tfId", tfId);
				newParam.put("woid", woid);
				newParam.put("status", status);
				newParam.put("coptid", coptid);
				paramList.add(newParam);
			}
			//批量插入采集器安装计划
			baseDAOIbatis.executeBatch(sqlId + "insCInsP", paramList);
		}
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.edit.success", lang);
		return re;
	}
	
	/**
	 * 删除派发采集器变压器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult delCDispTf(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		baseDAOIbatis.delete(sqlId + "delCInsP", param);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.unPlan.del.success", lang);
		return re;
	}
	
	/**
	 * 根据工单ID查询集中器已分配的变压器
	 * @param woid
	 * @return
	 */
	public String getDDispTfByWoid(String woid) {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getDDispTf", woid);
		String tfIds = "";
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> map = (Map<String, String>) list.get(i);
			String tfId = StringUtil.getValue(map.get("TFID"));
			if(i == 0) {
				tfIds = tfId;
			} else {
				tfIds = tfIds + "," + tfId;
			}
		}
		return tfIds;
	}
	
	/**
	 * 根据工单ID查询采集器已分配的变压器
	 * @param woid
	 * @return
	 */
	public String getCDispTfByWoid(String woid) {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getCDispTf", woid);
		String tfIds = "";
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> map = (Map<String, String>) list.get(i);
			String tfId = StringUtil.getValue(map.get("TFID"));
			if(i == 0) {
				tfIds = tfId;
			} else {
				tfIds = tfIds + "," + tfId;
			}
		}
		return tfIds;
	}
	
	/**
	 * 根据工单id获取处理人信息
	 * @param woid 工单id
	 * @return
	 */
	public Map<String,String> getOPTInfoByWOID(String woid) {
		return (Map<String, String>) baseDAOIbatis.queryForObject(sqlId + "getOPTInfoByWOID", woid, HashMap.class);
	}
	
	/**
	 * 工单检查
	 * @param woid 工单号
	 * @param lang
	 * @return
	 */
	protected ActionResult woCheck(String woid, String lang) {
		ActionResult re = new ActionResult(true,"");
		WorkOrder wo = CommonUtil.getWOInfo(woid);
		if(wo == null) {
			re.setSuccess(false);
			re.setMsg("mainModule.insMgt.order.valid.notExist", lang);
		} else {
			String woStatus = wo.getStatus();
			if(!Constants.WO_STATUS_UNHANDLED.equals(woStatus) &&
					!Constants.WO_STATUS_ASSIGNED.equals(woStatus) &&
					!Constants.WO_STATUS_REVOKED.equals(woStatus)) {
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.order.valid.dispatched", lang);
			} 
		}
		return re;
	}
}
