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

public class InsOrderManager implements InsOrderManagerInf{

	private static Logger logger = Logger.getLogger(InsOrderManager.class.getName());
	private String menuId="12300";
	private String[] mAttribute = new String[]{"bussType","pid","cno","cname",
			"uid","uname","tfId","tfName","mType","wir","mMode","nmsn","omsn","status"};
	private String[] dAttribute = new String[]{"bussType","pid","uid","uname","tfId","tfName","addr","dcuM","ndsn","odsn"};
	private String[] cAttribute = new String[]{"bussType","pid","uid","uname","tfId","tfName","addr","colM","ncsn","ocsn"};
	private String[] sAttribute = new String[]{"pid","cno","cname","addr","mno","uid","uname","status"};
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "insWO.";
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if((menuId + "01").equals(czid)) {//新增安装工单
			re = addInsWorkOrder(param);
		} if((menuId + "02").equals(czid)) {//修改安装工单
			re = editInsWorkOrder(param);
		} if((menuId + "03").equals(czid)) {//删除安装工单
			re = delInsWorkOrder(param);
		}
		return re;
	}

	public ActionResult addInsWorkOrder(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		List<Object> insDataList = new ArrayList<Object>();//插入记录数
		List<Object> updDataList = new ArrayList<Object>();//更新记录数
		List<String> insSqlList = new ArrayList<String>();//插入sql列表
		List<String> updSqlList = new ArrayList<String>();//更新sql列表
		String popid = StringUtil.getValue(param.get("popid"));//处理人
		String status = "0";//工单状态为未处理
		if(!popid.equals("")) {//当没有指定处理人时
			//指定处理人时
			status = "1";//工单状态已分配
		}
		String woid = (String) baseDAOIbatis.queryForObject(sqlId + "getWOId", null, String.class);
		woid = "W"+ StringUtil.leftZero(woid,14);
		param.put("woid", woid);
		param.put("status", status);
		//获取表安装计划插入和更新操作sql及值
		meterInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取集中器安装计划插入和更新操作sql及值
		dcuInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取采集器安装计划插入和更新操作sql及值
		colInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取勘察计划插入和更新操作sql及值
		surInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		
		//插入工单
		baseDAOIbatis.insert(sqlId + "insWorkOrder", param);
		//插入工单操作日志
		baseDAOIbatis.insert(sqlId + "insWOOPLog", param);
		//更新安装计划
		if(updDataList.size() > 0 && updSqlList.size() > 0) {
			baseDAOIbatis.executeBatchMultiSql(updSqlList, updDataList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", updDataList);
		}
		//插入安装计划
		if(insDataList.size() > 0 && insSqlList.size() > 0) {
			baseDAOIbatis.executeBatchMultiSql(insSqlList, insDataList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", insDataList);
		}
		re.setSuccess(true);
		re.setDataObject(woid);
		re.setMsg("mainModule.insMgt.order.add.success", lang);
		return re;
	}
	
	/**
	 * 表安装计划操作
	 * @param insDataList
	 * @param insSqlList
	 * @param updDataList
	 * @param updSqlList
	 * @param woid
	 * @param status
	 * @param param
	 */
	public void meterInsPOp(List<Object> insDataList, List<String> insSqlList,
			List<Object> updDataList, List<String> updSqlList,
			String woid, String status, Map<String,String> param) {
		param.put("type", "0");
		String mszh = StringUtil.getValue(param.get("mszh"));
		String mczh = StringUtil.getValue(param.get("mczh"));
		if(!StringUtil.isEmptyString(mszh)) {
			if(mszh.startsWith(";")) {
				mszh = mszh.substring(1);
			}
			String[] mszhs = mszh.split(";");
			String[] pids = new String[mszhs.length];
			for(int i = 0; i < mszhs.length; i++) {
				String[] colArray = mszhs[i].split(",");
				pids[i] = colArray[1];
				Map<String,Object> uDataMap = convertToMap(mAttribute,colArray,-1,null,null);
				uDataMap.put("woid", woid);
				uDataMap.put("status", status);
				uDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				uDataMap.put("devType","0");
				updDataList.add(uDataMap);
				updSqlList.add(sqlId + "updMInsPln");
			}
			if(pids.length > 0) {
				//移除该工单下的存在的除当前计划外的其他计划
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pids", pids);
				map.put("woid", woid);
				map.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				baseDAOIbatis.update(sqlId + "updOtherMInsP", map);
			}
		}
		if(!StringUtil.isEmptyString(mczh)) {
			if(mczh.startsWith(";")) {
				mczh = mczh.substring(1);
			}
			String[] mczhs = mczh.split(";");
			for(int i = 0; i < mczhs.length; i++) {
				String[] colArray = mczhs[i].split(",");
				Map<String,Object> iDataMap = convertToMap(mAttribute,colArray,-1,null,null);
				iDataMap.put("woid", woid);
				iDataMap.put("status", status);
				iDataMap.put("devType","0");
				iDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				String pid = (String) baseDAOIbatis.queryForObject("insPlan.getPID", null, String.class);
				iDataMap.put("pid", "P" + StringUtil.leftZero(pid, 14));
				insDataList.add(iDataMap);
				insSqlList.add(sqlId + "insMInsPln");
			}
		}
	}
	
	/**
	 * 集中器安装计划操作
	 * @param insDataList
	 * @param insSqlList
	 * @param updDataList
	 * @param updSqlList
	 * @param woid
	 * @param status
	 * @param param
	 */
	public void dcuInsPOp(List<Object> insDataList, List<String> insSqlList,
			List<Object> updDataList, List<String> updSqlList,
			String woid, String status, Map<String,String> param) {
		param.put("type", "0");
		String dszh = StringUtil.getValue(param.get("dszh"));
		String dczh = StringUtil.getValue(param.get("dczh"));
		if(!StringUtil.isEmptyString(dszh)) {
			if(dszh.startsWith(";")) {
				dszh = dszh.substring(1);
			}
			String[] dszhs = dszh.split(";");
			String[] pids = new String[dszhs.length];
			for(int i = 0; i < dszhs.length; i++) {
				String[] colArray = dszhs[i].split(",");
				pids[i] = colArray[1];
				Map<String,Object> uDataMap = convertToMap(dAttribute,colArray,-1,null,null);
				uDataMap.put("woid", woid);
				uDataMap.put("status", status);
				uDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				updDataList.add(uDataMap);
				updSqlList.add(sqlId + "updDInsPln");
			}
			if(pids.length > 0) {
				//移除该工单下的存在的除当前计划外的其他计划
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pids", pids);
				map.put("woid", woid);
				map.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				baseDAOIbatis.update(sqlId + "updOtherDInsP", map);
			}
		}
		if(!StringUtil.isEmptyString(dczh)) {
			if(dczh.startsWith(";")) {
				dczh = dczh.substring(1);
			}
			String[] dczhs = dczh.split(";");
			for(int i = 0; i < dczhs.length; i++) {
				String[] colArray = dczhs[i].split(",");
				Map<String,Object> iDataMap = convertToMap(dAttribute,colArray,-1,null,null);
				iDataMap.put("woid", woid);
				iDataMap.put("status", status);
				iDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				insDataList.add(iDataMap);
				insSqlList.add(sqlId + "insDInsPln");
			}
		}
	}
	
	/**
	 * 采集器安装计划操作
	 * @param insDataList
	 * @param insSqlList
	 * @param updDataList
	 * @param updSqlList
	 * @param woid
	 * @param status
	 * @param param
	 */
	public void colInsPOp(List<Object> insDataList, List<String> insSqlList,
			List<Object> updDataList, List<String> updSqlList,
			String woid, String status, Map<String,String> param) {
		param.put("type", "0");
		String cszh = StringUtil.getValue(param.get("cszh"));
		String cczh = StringUtil.getValue(param.get("cczh"));
		if(!StringUtil.isEmptyString(cszh)) {
			if(cszh.startsWith(";")) {
				cszh = cszh.substring(1);
			}
			String[] cszhs = cszh.split(";");
			String[] pids = new String[cszhs.length];
			for(int i = 0; i < cszhs.length; i++) {
				String[] colArray = cszhs[i].split(",");
				pids[i] = colArray[i];
				Map<String,Object> uDataMap = convertToMap(cAttribute,colArray,-1,null,null);
				uDataMap.put("woid", woid);
				uDataMap.put("status", status);
				uDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				updDataList.add(uDataMap);
				updSqlList.add(sqlId + "updCInsPln");
			}
			if(pids.length > 0) {
				//移除该工单下的存在的除当前计划外的其他计划
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pids", pids);
				map.put("woid", woid);
				map.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				baseDAOIbatis.update(sqlId + "updOtherCInsP", map);
			}
		}
		if(!StringUtil.isEmptyString(cczh)) {
			if(cczh.startsWith(";")) {
				cczh = cczh.substring(1);
			}
			String[] cczhs = cczh.split(";");
			for(int i = 0; i < cczhs.length; i++) {
				String[] colArray = cczhs[i].split(",");
				Map<String,Object> iDataMap = convertToMap(cAttribute,colArray,-1,null,null);
				iDataMap.put("woid", woid);
				iDataMap.put("status", status);
				iDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				insDataList.add(iDataMap);
				insSqlList.add(sqlId + "insCInsPln");
			}
		}
	}
	
	/**
	 * 勘察计划操作
	 * @param insDataList
	 * @param insSqlList
	 * @param updDataList
	 * @param updSqlList
	 * @param woid
	 * @param status
	 * @param param
	 */
	public void surInsPOp(List<Object> insDataList, List<String> insSqlList,
			List<Object> updDataList, List<String> updSqlList,
			String woid, String status, Map<String,String> param) {
		//param.put("type", "3");
		String sszh = StringUtil.getValue(param.get("sszh"));
		String sczh = StringUtil.getValue(param.get("sczh"));
		if(!StringUtil.isEmptyString(sszh)) {
			param.put("type", "3");
			if(sszh.startsWith(";")) {
				sszh = sszh.substring(1);
			}
			String[] sszhs = sszh.split(";");
			String[] pids = new String[sszhs.length];
			for(int i = 0; i < sszhs.length; i++) {
				String[] surArray = sszhs[i].split(",");
				pids[i] = surArray[0];
				Map<String,Object> uDataMap = convertToMap(sAttribute,surArray,-1,null,null);
				uDataMap.put("woid", woid);
				uDataMap.put("status", status);
				uDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				updDataList.add(uDataMap);
				updSqlList.add(sqlId + "updSurPln");
			}
			if(pids.length > 0) {
				//移除该工单下的存在的除当前计划外的其他计划
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pids", pids);
				map.put("woid", woid);
				map.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				baseDAOIbatis.update(sqlId + "updOtherSurP", map);
			}
		}
		if(!StringUtil.isEmptyString(sczh)) {
			param.put("type", "3");
			if(sczh.startsWith(";")) {
				sczh = sczh.substring(1);
			}
			String[] cczhs = sczh.split(";");
			for(int i = 0; i < cczhs.length; i++) {
				String[] surArray = cczhs[i].split(",");
				Map<String,Object> iDataMap = convertToMap(sAttribute,surArray,-1,null,null);
				iDataMap.put("woid", woid);
				iDataMap.put("status", status);
				iDataMap.put("CURR_STAFFID", param.get("CURR_STAFFID"));
				String pid = (String) baseDAOIbatis.queryForObject("insPlan.getPID", null, String.class);
				iDataMap.put("pid", "P" + StringUtil.leftZero(pid, 14));
				insDataList.add(iDataMap);
				insSqlList.add(sqlId + "insSurPln");
			}
		}
	}
	
	/**
	 * 编辑安装工单
	 * @param param
	 * @return
	 */
	public ActionResult editInsWorkOrder(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		List<Object> insDataList = new ArrayList<Object>();//插入记录数
		List<Object> updDataList = new ArrayList<Object>();//更新记录数
		List<String> insSqlList = new ArrayList<String>();//插入sql列表
		List<String> updSqlList = new ArrayList<String>();//更新sql列表
		String popid = StringUtil.getValue(param.get("popid"));//处理人
		String woid = StringUtil.getValue(param.get("woid"));//工单id
		re = woCheck(woid, lang);
		if(!re.isSuccess()) {
			return re;
		}
		String status = "0";//工单状态为未处理
		if(!popid.equals("")) {//当没有指定处理人时
			//指定处理人时
			status = "1";//工单状态已分配
		}
		param.put("status", status);
		//获取表安装计划插入和更新操作sql及值
		meterInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取集中器安装计划插入和更新操作sql及值
		dcuInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取采集器安装计划插入和更新操作sql及值
		colInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//获取勘察计划插入和更新操作sql及值
		surInsPOp(insDataList,insSqlList,updDataList,updSqlList,woid,status,param);
		//更新工单
		baseDAOIbatis.insert(sqlId + "updWorkOrder", param);
		//插入工单操作日志
		baseDAOIbatis.insert(sqlId + "insWOOPLog", param);
		//更新安装计划
		if(updDataList.size() > 0 && updSqlList.size() > 0) {
			baseDAOIbatis.executeBatchMultiSql(updSqlList, updDataList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", updDataList);
		}
		//插入安装计划
		if(insDataList.size() > 0 && insSqlList.size() > 0) {
			baseDAOIbatis.executeBatchMultiSql(insSqlList, insDataList);
			//插入安装计划操作日志
			baseDAOIbatis.executeBatch(sqlId + "insPlnOPLog", insDataList);
		}
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.order.edit.success", lang);
		return re;
	}
	
	/**
	 * 删除安装工单
	 * @param param
	 * @return
	 */
	public ActionResult delInsWorkOrder(Map<String,String> param) {
		ActionResult re = new ActionResult();
		String lang = StringUtil.getValue(param.get(Constants.APP_LANG));
		String woid = StringUtil.getValue(param.get("woid"));
		re = woCheck(woid, lang);
		if(!re.isSuccess()) {
			return re;
		}
		//删除工单操作日志
		baseDAOIbatis.delete(sqlId + "delWOOPLog", param);
		//删除工单
		baseDAOIbatis.delete(sqlId + "delWorkOrder", param);
		//更新表安装计划
		baseDAOIbatis.update(sqlId + "updMInsPByDelWO", param);
		//更新集中器安装计划
		baseDAOIbatis.update(sqlId + "updDInsPByDelWO", param);
		//更新采集器安装计划
		baseDAOIbatis.update(sqlId + "updCInsPByDelWO", param);
		//更新勘察计划
		baseDAOIbatis.update(sqlId + "updSInsPByDelWO", param);
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.order.del.success", lang);
		return re;
	}
	
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);	
	}

	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getWO", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询未处理的表安装计划
	 */
	public Map<String, Object> queryMPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		String mszh = StringUtil.getValue(param.get("mszh"));
		if(!StringUtil.isEmptyString(mszh)) {//存在已选表安装计划组合值
			String[] mszhs = mszh.split(";");
			String[] spids = new String[mszhs.length];
			for(int i = 0; i < mszhs.length; i++) {
				spids[i] = mszhs[i].split(",")[1];//安装计划的ID
			}
			param.put("spids", spids);
		}
		String mdzh = StringUtil.getValue(param.get("mdzh"));
		if(!StringUtil.isEmptyString(mdzh)) {//存在删除已选表安装计划组合值
			String[] mdzhs = mdzh.split(";");
			String[] pids = new String[mdzhs.length];
			for(int i = 0; i < mdzhs.length; i++) {
				pids[i] = mdzhs[i].split(",")[1];//安装计划的ID
			}
			param.put("pids", pids);
		}
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryMInsP", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询未处理的集中器安装计划
	 */
	public Map<String, Object> queryDPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryDInsP", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询未处理的采集器安装计划
	 */
	public Map<String, Object> queryCPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryCInsP", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 将值转成对应的map对象
	 * @param attribute 属性数组
	 * @param valueArray 值数组
	 * @param sn 序号
	 * @param lang
	 * @param flag 01=表，02=集中器，03=采集器
	 * @return
	 */
	public Map<String,Object> convertToMap(String[] attribute,String[] valueArray,
			int sn, String lang, String flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(valueArray.length != 0) {
			if(sn == -1) {//生成入库用的数据
				for(int i = 0; i < attribute.length; i++) {
					if(i > (valueArray.length -1)) {
						map.put(attribute[i], null);
					} else {
						map.put(attribute[i], valueArray[i]);
					}
				}
			} else {//生成页面grid展示用的数据
				map.put("SN", sn);
				for(int i = 0; i < attribute.length; i++) {
					if(i > (valueArray.length -1)) {
						map.put(attribute[i].toUpperCase(), null);
					} else {
						map.put(attribute[i].toUpperCase(), valueArray[i]);
					}
				}
				if("01".equals(flag)) {
					pushMToMap(map,lang,valueArray);
				} else if("02".equals(flag)) {
					pushDToMap(map,lang,valueArray);
				} else if("03".equals(flag)) {
					pushCToMap(map,lang,valueArray);
				}else if("04".equals(flag)) {
					pushSToMap(map,lang,valueArray);
				}
			}
		}
		return map;
	}
	
	/**
	 * 查询已添加到工单生成的表安装计划
	 */
	public Map<String, Object> queryMInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		List<Object> allList = new ArrayList<Object>();//总记录数
		String mszh = StringUtil.getValue(param.get("mszh"));
		String mczh = StringUtil.getValue(param.get("mczh"));
		String lang = StringUtil.getValue(param.get("lang"));
		int sn = 1;
		if(!StringUtil.isEmptyString(mszh)) {
			if(mszh.startsWith(";")) {
				mszh = mszh.substring(1);
			}
			String[] mszhs = mszh.split(";");
			for(int i = 0; i < mszhs.length; i++) {
				String[] colArray = mszhs[i].split(",");
				allList.add(convertToMap(mAttribute,colArray,sn++,lang,"01"));
			}
		}
		if(!StringUtil.isEmptyString(mczh)) {
			if(mczh.startsWith(";")) {
				mczh = mczh.substring(1);
			}
			String[] mczhs = mczh.split(";");
			for(int i = 0; i < mczhs.length; i++) {
				String[] colArray = mczhs[i].split(",");
				allList.add(convertToMap(mAttribute,colArray,sn++,lang,"01"));
			}
		}
		int count = allList.size();
		if(count != 0) {
			if (StringUtil.isEmptyString(isExcel)) {
				int startInt = Integer.valueOf(start==null?"0":start);
				int endInt = Integer.valueOf(limit==null?"30":limit);
				endInt = startInt + endInt > count ? count : startInt + endInt;
				for(int i = startInt; i < endInt; i++) {
					resultList.add(allList.get(i));
				}
				mapAll.put("result", resultList);
				mapAll.put("rows", count);
			} else {
				mapAll.put("result", allList);
			}
		} else {
			mapAll.put("result", new ArrayList<Object>());
			mapAll.put("rows", 0);
		}
		return mapAll;
	}

	/**
	 * 查询已添加到工单生成的集中器安装计划
	 */
	public Map<String, Object> queryDInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		List<Object> allList = new ArrayList<Object>();//总记录数
		String dszh = StringUtil.getValue(param.get("dszh"));
		String dczh = StringUtil.getValue(param.get("dczh"));
		String lang = StringUtil.getValue(param.get("lang"));
		int sn = 1;
		if(!StringUtil.isEmptyString(dszh)) {
			if(dszh.startsWith(";")) {
				dszh = dszh.substring(1);
			}
			String[] dszhs = dszh.split(";");
			for(int i = 0; i < dszhs.length; i++) {
				String[] colArray = dszhs[i].split(",");
				allList.add(convertToMap(dAttribute,colArray,sn++,lang,"02"));
			}
		}
		if(!StringUtil.isEmptyString(dczh)) {
			if(dczh.startsWith(";")) {
				dczh = dczh.substring(1);
			}
			String[] dczhs = dczh.split(";");
			for(int i = 0; i < dczhs.length; i++) {
				String[] colArray = dczhs[i].split(",");
				allList.add(convertToMap(dAttribute,colArray,sn++,lang,"02"));
			}
		}
		int count = allList.size();
		if(count != 0) {
			if (StringUtil.isEmptyString(isExcel)) {
				int startInt = Integer.valueOf(start==null?"0":start);
				int endInt = Integer.valueOf(limit==null?"30":limit);
				endInt = startInt + endInt > count ? count : startInt + endInt;
				for(int i = startInt; i < endInt; i++) {
					resultList.add(allList.get(i));
				}
				mapAll.put("result", resultList);
				mapAll.put("rows", count);
			} else {
				mapAll.put("result", allList);
			}
		} else {
			mapAll.put("result", new ArrayList<Object>());
			mapAll.put("rows", 0);
		}
		return mapAll;
	}

	/**
	 * 查询已添加到工单生成的采集器安装计划
	 */
	public Map<String, Object> queryCInsPlan(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		List<Object> allList = new ArrayList<Object>();//总记录数
		String cszh = StringUtil.getValue(param.get("cszh"));
		String cczh = StringUtil.getValue(param.get("cczh"));
		String lang = StringUtil.getValue(param.get("lang"));
		int sn = 1;
		if(!StringUtil.isEmptyString(cszh)) {
			if(cszh.startsWith(";")) {
				cszh = cszh.substring(1);
			}
			String[] cszhs = cszh.split(";");
			for(int i = 0; i < cszhs.length; i++) {
				String[] colArray = cszhs[i].split(",");
				allList.add(convertToMap(cAttribute,colArray,sn++,lang,"03"));
			}
		}
		if(!StringUtil.isEmptyString(cczh)) {
			if(cczh.startsWith(";")) {
				cczh = cczh.substring(1);
			}
			String[] cczhs = cczh.split(";");
			for(int i = 0; i < cczhs.length; i++) {
				String[] colArray = cczhs[i].split(",");
				allList.add(convertToMap(cAttribute,colArray,sn++,lang,"03"));
			}
		}
		int count = allList.size();
		if(count != 0) {
			if (StringUtil.isEmptyString(isExcel)) {
				int startInt = Integer.valueOf(start==null?"0":start);
				int endInt = Integer.valueOf(limit==null?"30":limit);
				endInt = startInt + endInt > count ? count : startInt + endInt;
				for(int i = startInt; i < endInt; i++) {
					resultList.add(allList.get(i));
				}
				mapAll.put("result", resultList);
				mapAll.put("rows", count);
			} else {
				mapAll.put("result", allList);
			}
		} else {
			mapAll.put("result", new ArrayList<Object>());
			mapAll.put("rows", 0);
		}
		return mapAll;
	}
	
	/**
	 * 获取表安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getMInsPByWOID(Map<String,String> param) {
		String result = "";
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getMInsPByWOID", param);
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> reMap = (Map<String, String>) list.get(i);
			if(i == 0) {
				result = reMap.get("SZH");
			} else {
				result = result + ";" + reMap.get("SZH");
			}
		}
		return result;
	}
	
	/**
	 * 获取集中器安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getDInsPByWOID(Map<String,String> param) {
		String result = "";
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getDInsPByWOID", param);
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> reMap = (Map<String, String>) list.get(i);
			if(i == 0) {
				result = reMap.get("SZH");
			} else {
				result = result + ";" + reMap.get("SZH");
			}
		}
		return result;
	}
	
	/**
	 * 获取采集器安装计划信息组合
	 * @param param
	 * @return
	 */
	public String getCInsPByWOID(Map<String,String> param) {
		String result = "";
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getCInsPByWOID", param);
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> reMap = (Map<String, String>) list.get(i);
			if(i == 0) {
				result = reMap.get("SZH");
			} else {
				result = result + ";" + reMap.get("SZH");
			}
		}
		return result;
	}
	
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}

	@Override
	public Map<String, Object> queryPOP(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryPOP", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 将表安装计划涉及数据添加到map中
	 * @param map
	 * @param lang
	 * @param value
	 */
	public void pushMToMap(Map<String,Object> map,String lang,String[] value) {
		List<Object> btList = CommonUtil.getCodeNumber("bussType", lang, "ASC", false);  //业务类型
		List<Object> mTypeList = CommonUtil.getCodeNumber("mType", lang, "ASC", false);  //表计类型
		List<Object> wirList = CommonUtil.getCodeNumber("wiring", lang, "ASC", false);  //接线方式
		List<Object> mModeList = CommonUtil.getCodeNumber("mode", lang, "ASC", false);  //表计模式
		List<Object> plnStatusList = CommonUtil.getCodeNumber("planStatus", lang, "ASC", false);  //安装计划状态
		String bussType = "";
		String mType = "";
		String wiring = "";
		String mMode = "";
		String status = "";
		String addr = "";//用户地址
		if(value.length > 0) {
			bussType = value[0];//业务类型
		}
		if(value.length > 2) {
			String cno = value[2];//户号
			addr = (String) baseDAOIbatis.queryForObject(sqlId + "getCustAddr", cno, String.class);
		}
		if(value.length > 8) {
			mType = value[8];//表计类型
		}
		if(value.length > 9) {
			wiring = value[9];//接线方式
		}
		if(value.length > 10) {
			mMode = value[10];//表计模式
		}
		if(value.length > 13) {
			status = value[13];//表计模式
		}
		map.put("ADDR", addr);
		pushToMap(btList,"BTNAME",bussType,map);
		pushToMap(mTypeList,"MTNAME",mType,map);
		pushToMap(wirList,"WNAME",wiring,map);
		pushToMap(mModeList,"MMNAME",mMode,map);
		pushToMap(plnStatusList,"STNAME",status,map);
	}
	
	/**
	 * 将集中器安装计划涉及数据添加到map中
	 * @param map
	 * @param lang
	 * @param value
	 */
	public void pushDToMap(Map<String,Object> map,String lang,String[] value) {
		List<Object> btList = CommonUtil.getCodeNumber("bussType", lang, "ASC", false);  //业务类型
		List<Object> dcuMList = CommonUtil.getCodeNumber("dcuModel", lang, "ASC", false);  //表计模式
		String bussType = "";
		String dcuM = "";
		if(value.length > 0) {
			bussType = value[0];//业务类型
		}
		if(value.length > 9) {
			dcuM = value[7];//集中器型号
		}
		pushToMap(btList,"BTNAME",bussType,map);
		pushToMap(dcuMList,"DMNAME",dcuM,map);
	}
	
	/**
	 * 将采集器安装计划涉及数据添加到map中
	 * @param map
	 * @param lang
	 * @param value
	 */
	public void pushCToMap(Map<String,Object> map,String lang,String[] value) {
		List<Object> btList = CommonUtil.getCodeNumber("bussType", lang, "ASC", false);  //业务类型
		List<Object> colMList = CommonUtil.getCodeNumber("clModel", lang, "ASC", false);  //表计模式
		String bussType = "";
		String colM = "";
		if(value.length > 0) {
			bussType = value[0];//业务类型
		}
		if(value.length > 9) {
			colM = value[7];//采集器型号
		}
		pushToMap(btList,"BTNAME",bussType,map);
		pushToMap(colMList,"CMNAME",colM,map);
	}
	
	/**
	 * 将勘察计划涉及数据添加到map中
	 * @param map
	 * @param lang
	 * @param value
	 */
	public void pushSToMap(Map<String,Object> map,String lang,String[] value) {
		List<Object> plnStatusList = CommonUtil.getCodeNumber("planStatus", lang, "ASC", false);  //计划状态
		String status = "";
		if(value.length > 7) {
			status = value[7];
		}
		pushToMap(plnStatusList,"STNAME",status,map);
	}
	
	/**
	 * 将值添加到map中
	 * @param list
	 * @param key
	 * @param value
	 * @param map
	 */
	public void pushToMap(List<Object> list,String key,String value, Map<String,Object> map) {
		for(Object object : list) {
			Map<String,String> pMap = (Map<String, String>) object;
			String bm = pMap.get("BM");
			String mc = pMap.get("MC");
			if(bm.equals(value)) {
				map.put(key, mc);
				break;
			}
		}
	}
	
	/**
	 * 查询安装计划更换表计
	 */
	@Override
	public Map<String, Object> queryMeter(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryMeter", start, limit, dir, sort, isExcel);
	}

	/**
	 * 获取接收人下可下载的已分配和待撤销工单
	 * @param optID 接收人id
	 * @param uptWay 更新方式 0=全量，1=增量
	 * @return
	 */
	public List<Object> getDownLoadWO(String optID, String uptWay) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("optID", optID);
		map.put("uptWay", uptWay);
		List<Object> woList = baseDAOIbatis.queryForList(sqlId + "getDownLoadWO", map);
		return woList;
	}
	
	/**
	 * 获取操作员对应的下载反馈工单
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 接收人id
	 * @return
	 */
	public List<Object> getWOFromLog(String hhuID,String reqID,String optID) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("hhuID", hhuID);
		map.put("reqID", reqID);
		map.put("optID", optID);
		map.put("optRst", Constants.DATAUPDATE_LOGSTAT_UNKNOW);
		return baseDAOIbatis.queryForList(sqlId + "getWOFromLog", map);
	}
	
	/**
	 * 更新工单状态
	 * @param woStatusList 工单状态列表
	 * @return
	 */
	public ActionResult updWOStatus(List<Object> woStatusList) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.executeBatch(sqlId + "updWOStatus", woStatusList);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 保存工单及工单下的安装计划
	 * @param woList 工单号列表
	 * @param insPlnList 安装计划列表
	 * @return
	 */
	public ActionResult storeWO(List<Object> woList,List<Object> insPlnList) {
		ActionResult re = new ActionResult(false,"");
		List<Object> insWOList = new ArrayList<Object>();
		List<Object> insPlanList = new ArrayList<Object>();//插入安装计划列表
		List<Object> updPlanList = new ArrayList<Object>();//更新安装计划列表
		String woid = CommonUtil.getWOID();
		boolean existFailPlnFB = false;//标志反馈的安装计划是否存在状态为失败
		boolean existWO = false;//标志工单是存在，true存在，false不存在
		for(Object obj : insPlnList) {
			Map<String,String> map = (Map<String, String>) obj;
			String rst = StringUtil.getValue(map.get("STS"));//安装反馈结果
			String pid = StringUtil.getValue(map.get("S"));
			if(rst.equals(Constants.PLN_FB_SUCCESS)) {
				map.put("STATUS", Constants.PLN_STATUS_SUCCESS);
			} else if(rst.equals(Constants.PLN_FB_ABNORMAL)) {
				map.put("STATUS", Constants.PLN_STATUS_ABNORMAL);
			} else if(rst.equals(Constants.PLN_FB_FAIL)) {
				map.put("STATUS", Constants.PLN_STATUS_FAIL);
				existFailPlnFB = true;
			}
			List<Object> plnList = CommonUtil.getMeterPlnByPid(pid);
			if(plnList.size() > 0) {
				Map<String,String> existPlnMap = (Map<String, String>) plnList.get(0);
				woid = StringUtil.getValue(existPlnMap.get("WOID"));
				map.put("WOID", woid);
				existWO = true;//存在安装计划，则代表工单存在
				updPlanList.add(map);
			} else {
				map.put("WOID", woid);
				insPlanList.add(map);
			}
		}
		if(insPlnList.size() > 0) {
			Map<String,String> map = (Map<String, String>) insPlnList.get(0);
			Map<String,String> insWOMap = new HashMap<String,String>();
			if(existFailPlnFB) {
				insWOMap.put("status", Constants.WO_STATUS_FEEDBACKED);
			} else {
				insWOMap.put("status", Constants.WO_STATUS_COMPLETED);
			}
			insWOMap.put("woid", woid);
			insWOMap.put("type", Constants.WO_TYPE_NEW);
			insWOMap.put("optid", map.get("OPT"));
			insWOMap.put("coptid", map.get("OPT"));
			insWOMap.put("CURR_STAFFID", map.get("OPT"));
			insWOList.add(insWOMap);
			if(updPlanList.size() > 0) {//存在需要更新的安装计划
				baseDAOIbatis.executeBatch(sqlId + "updNewMeterPln", updPlanList);
				CommonUtil.storePlanOPLog(updPlanList);
			} 
			if(insPlanList.size() > 0) {//存在需要插入的安装计划
				baseDAOIbatis.executeBatch(sqlId + "insNewMeterPln", insPlanList);
				CommonUtil.storePlanOPLog(insPlanList);
			}
			if(existWO) {//存在工单，则更新
				baseDAOIbatis.executeBatch(sqlId + "updNewWO", insWOList);
			} else {
				baseDAOIbatis.executeBatch(sqlId + "insNewWO", insWOList);
			}
			CommonUtil.storeWOOPLog(insWOList);//记录工单操作日志
			woList.add(insWOMap);
		}
		
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 更新HHU操作工单日志状态
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 操作员id
	 * @param optRst 操作结果
	 * @param errMsg 失败信息
	 * @return
	 */
	public ActionResult updWOLog(String hhuID, String reqID, String optID, 
			String optRst, String errMsg) {
		ActionResult re = new ActionResult(false,"");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hhuID", hhuID);
		map.put("reqID", reqID);
		map.put("optID", optID);
		map.put("optRst", optRst);
		if(StringUtil.isEmptyString(errMsg)) {
			map.put("errMsg", null);
		} else {
			map.put("errMsg", errMsg);
		}
		baseDAOIbatis.update(sqlId + "updWOLog", map);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 保存HHU操作工单日志状态
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 操作员id
	 * @param optRst 操作结果
	 * @param errMsg 失败信息
	 * @param optType 操作类型
	 * @param woList 工单列表
	 * @param uptWay 更新方式
	 * @return
	 */
	public ActionResult stroeWOLog(String hhuID, String reqID, String optID, 
			String optRst, String errMsg, String optType, List<Object> woList,String uptWay) {
		ActionResult re = new ActionResult(false,"");
		String logID = (String) baseDAOIbatis.queryForObject(sqlId + "getWOLogId", null, String.class);
		List<Object> paramList = new ArrayList<Object>();
		for(Object obj : woList) {
			Map<String,String> woLogMap = new HashMap<String,String>();
			Map<String,String> woMap = (Map<String, String>) obj;
			woLogMap.put("logID", logID);
			woLogMap.put("woid", StringUtil.getValue(woMap.get("woid")));
			paramList.add(woLogMap);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("logID", logID);
		map.put("hhuID", hhuID);
		map.put("reqID", reqID);
		map.put("optID", optID);
		map.put("optType", optType);//操作类型为1=下载，0=上传
		map.put("optRst", optRst);//操作结果
		map.put("uptWay", uptWay);//更新方式
		map.put("errMsg", errMsg);//错误消息
		//更新工单操作日志(不管工单是否存在，都创建一条工单日志，用于接口中验证掌机请求id是否正确)
		baseDAOIbatis.insert(sqlId + "insWOLog", map);
		if(paramList.size() > 0) {
			//批量更新HHU操作日志关联工单
			baseDAOIbatis.executeBatch(sqlId + "insWORelateLog", paramList);
		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 获取工单信息
	 * @param woid 工单id
	 * @return
	 */
	public WorkOrder getWOInfo(String woid) {
		return (WorkOrder) baseDAOIbatis.queryForObject(sqlId + "getWOInfo", woid, WorkOrder.class);
	}
	
	/**
	 * 获取工单序列标识id
	 * @return
	 */
	public String getWOID() {
		return (String) baseDAOIbatis.queryForObject(sqlId + "getWOId", null, String.class);
	}
	
	/**
	 * 撤销工单
	 * @param map
	 * @return
	 */
	public ActionResult revokeWorkOrder(Map<String,String> map) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.update(sqlId + "updWOStatus", map);
		//插入工单操作日志
		baseDAOIbatis.insert(sqlId + "insWOOPLog", map);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 保存工单操作日志
	 * @param param
	 * @return
	 */
	public ActionResult storeWOOPLog(List<Object> param) {
		ActionResult re = new ActionResult(false,"");
		//批量插入工单操作日志
		baseDAOIbatis.executeBatch(sqlId + "insWOOPLog", param);
		re.setSuccess(true);
		return re;
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
		WorkOrder workOrder = getWOInfo(woid);
		if(workOrder == null) {
			re.setSuccess(false);
			re.setMsg("mainModule.insMgt.order.valid.notExist", lang);
		} else {
			String woStatus = workOrder.getStatus();
			if(!Constants.WO_STATUS_UNHANDLED.equals(woStatus) &&
					!Constants.WO_STATUS_ASSIGNED.equals(woStatus) &&
					!Constants.WO_STATUS_REVOKED.equals(woStatus)) {
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.order.valid.dispatched", lang);
			}
		}
		return re;
	}

	@Override
	public Map<String, Object> querySurPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {

		Map<String, Object> mapAll = new HashMap<String, Object>();
		List<Object> resultList = new ArrayList<Object>();
		List<Object> allList = new ArrayList<Object>();//总记录数
		String sszh = StringUtil.getValue(param.get("sszh"));
		String sczh = StringUtil.getValue(param.get("sczh"));
		String lang = StringUtil.getValue(param.get("lang"));
		int sn = 1;
		if(!StringUtil.isEmptyString(sszh)) {
			if(sszh.startsWith(";")) {
				sszh = sszh.substring(1);
			}
			String[] sszhs = sszh.split(";");
			for(int i = 0; i < sszhs.length; i++) {
				String[] colArray = sszhs[i].split(",");
				allList.add(convertToMap(sAttribute,colArray,sn++,lang,"04"));
			}
		}
		if(!StringUtil.isEmptyString(sczh)) {
			if(sczh.startsWith(";")) {
				sczh = sczh.substring(1);
			}
			String[] sczhs = sczh.split(";");
			for(int i = 0; i < sczhs.length; i++) {
				String[] colArray = sczhs[i].split(",");
				allList.add(convertToMap(sAttribute,colArray,sn++,lang,"04"));
			}
		}
		int count = allList.size();
		if(count != 0) {
			if (StringUtil.isEmptyString(isExcel)) {
				int startInt = Integer.valueOf(start==null?"0":start);
				int endInt = Integer.valueOf(limit==null?"30":limit);
				endInt = startInt + endInt > count ? count : startInt + endInt;
				for(int i = startInt; i < endInt; i++) {
					resultList.add(allList.get(i));
				}
				mapAll.put("result", resultList);
				mapAll.put("rows", count);
			} else {
				mapAll.put("result", allList);
			}
		} else {
			mapAll.put("result", new ArrayList<Object>());
			mapAll.put("rows", 0);
		}
		return mapAll;
	
	}

	@Override
	public Map<String, Object> querySPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		String sszh = StringUtil.getValue(param.get("sszh"));
		if(!StringUtil.isEmptyString(sszh)) {//存在已选表安装计划组合值
			String[] sszhs = sszh.split(";");
			String[] spids = new String[sszhs.length];
			for(int i = 0; i < sszhs.length; i++) {
				spids[i] = sszhs[i].split(",")[0];//安装计划的ID
			}
			param.put("spids", spids);
		}
		String sdzh = StringUtil.getValue(param.get("sdzh"));
		if(!StringUtil.isEmptyString(sdzh)) {//存在删除已选表安装计划组合值
			String[] sdzhs = sdzh.split(";");
			String[] pids = new String[sdzhs.length];
			for(int i = 0; i < sdzhs.length; i++) {
				pids[i] = sdzhs[i].split(",")[1];//安装计划的ID
			}
			param.put("pids", pids);
		}
		return baseDAOIbatis.getExtGrid(param, sqlId+"querySurP", start, limit, dir, sort, isExcel);
	}
	/**
	 * 获取勘察计划信息组合
	 * @param param
	 * @return
	 */
	@Override
	public String getSurPByWOID(Map<String, String> param) {
		String result = "";
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getSurPByWOID", param);
		for(int i = 0; i < list.size(); i++) {
			Map<String,String> reMap = (Map<String, String>) list.get(i);
			if(i == 0) {
				result = reMap.get("SZH");
			} else {
				result = result + ";" + reMap.get("SZH");
			}
		}
		return result;
	}

}
