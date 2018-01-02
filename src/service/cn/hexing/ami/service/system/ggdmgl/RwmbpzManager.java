package cn.hexing.ami.service.system.ggdmgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/** 
 * @Description 任务模板配置
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-1-12
 * @version AMI3.0 
 */
public class RwmbpzManager implements RwmbpzManagerInf{
	private static String sqlId = "rwmbpz.";
	private static String menuId = "54400";
	private BaseDAOIbatis baseDAOIbatis = null;

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		
		//新增任务数据项
		if (czid.equals(menuId + Constants.ADDOPT)) { // 新增
			re = addRwsjx(param);
		}
		
		//配置数据项字段映射关系
		if (czid.equals(menuId + Constants.UPDOPT)) { // 编辑
			re = saveSjxzd(param);
		}
		
		//删除任务模板数据项
		if (czid.equals(menuId + Constants.DELOPT)) { // 编辑
			re = delRwsjx(param);
		}
		return re;
	}
	
	/**
	 * 新增任务数据项
	 * @param param
	 * @return
	 */
	public ActionResult addRwsjx(Map<String, String> param){
		ActionResult re = new ActionResult();
		//判断该数据项是否已经存在
		int sjxCount = (Integer)baseDAOIbatis.queryForObject(sqlId+"rwsjxExist", param, Integer.class);
		if (sjxCount>0) {
			re.setSuccess(false);
			re.setMsg("sysModule.ggdmgl.rwmbpz.action.addrwsjx.dataItemExist", param.get(Constants.APP_LANG));
			return re;
		}
		
		//新增任务数据项
		baseDAOIbatis.insert(sqlId+"insRwsjx", param);
		re.setMsg("sysModule.ggdmgl.rwmbpz.action.addrwsjx.addDataItemSuccess", param.get(Constants.APP_LANG));
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 配置数据项字段映射关系
	 * @param param
	 * @return
	 */
	public ActionResult saveSjxzd(Map<String, String> param){
		ActionResult re = new ActionResult();
		String sjxbms = param.get("sjxbms");
		String zds = param.get("zds");
		String xhs = param.get("xhs");
		String dbsjxbms = param.get("dbsjxbms");
		String sjb = param.get("sjb");
		String sjxsxs = param.get("sjxsxs");
		
		String[] zdArr = zds.split(",");
		String[] xhArr = xhs.split(",");
		String[] sjxsxArr = sjxsxs.split(",");
		
		Map<String, Object> paramNew = new HashMap<String,Object>();
		String zdgylx = param.get("zdgylx");
		paramNew.put("zdgylx", zdgylx);
		String rwsx = param.get("rwsx");
		paramNew.put("rwsx", rwsx);
		
		String[] sjxbmArr = sjxbms.split(",");
		paramNew.put("sjxbmArr", sjxbmArr);
		
		//删除数据关系表的数据 p_dlmssjxgx
		baseDAOIbatis.delete(sqlId+"delDlmsSjxgx", paramNew);
		
		//删除任务模板数据项p_rwsjx
		/*List<String> sqlRwsjxLs = new ArrayList<String>();
		List<Map<String,Object>> rwSjxParamObjs = new ArrayList<Map<String,Object>>();
		sqlRwsjxLs.add(sqlId+"delRwsjx");*/
		
		//新增数据关系表的数据 p_dlmssjxgx
		List<String> sqlLs = new ArrayList<String>();
		List<Map<String,Object>> paramObjs = new ArrayList<Map<String,Object>>();
		sqlLs.add(sqlId+"insDlmsSjxgx");
		for (int i = 0; i < sjxbmArr.length; i++) {
			Map<String,Object> tmpMap = new HashMap<String,Object>();
			tmpMap.put("sjxbm", sjxbmArr[i]);
			paramObjs.add(tmpMap);
			
			/*tmpMap = new HashMap<String,Object>();
			tmpMap.put("zdgylx", zdgylx);
			tmpMap.put("rwsx", rwsx);
			tmpMap.put("sjxbm", sjxbmArr[i]);
			rwSjxParamObjs.add(tmpMap);*/
		}
		
		//数据关系表的数据 p_dlmssjxgx
		baseDAOIbatis.executeBatch(sqlLs, paramObjs);
		//删除任务模板数据项p_rwsjx
//		baseDAOIbatis.executeBatch(sqlRwsjxLs, rwSjxParamObjs);
		
		//删除p_sjxdy和P_DBSJXMX的数据
		List<String> dxDelSqlLs = new ArrayList<String>();
		List<Map<String,Object>> dxDelParamObjs = new ArrayList<Map<String,Object>>();
		
		List<String> xxDelSqlLs = new ArrayList<String>();
		List<Map<String,Object>> xxDelParamObjs = new ArrayList<Map<String,Object>>();
		
		List<String> dbDelSqlLs = new ArrayList<String>();
		List<Map<String,Object>> dbDelParamObjs = new ArrayList<Map<String,Object>>();
		
		//新增p_sjxdy和P_DBSJXMX的数据
		List<String> dxInsSqlLs = new ArrayList<String>();
		List<Map<String,Object>> dxInsParamObjs = new ArrayList<Map<String,Object>>();
		
		List<String> xxInsSqlLs = new ArrayList<String>();
		List<Map<String,Object>> xxInsParamObjs = new ArrayList<Map<String,Object>>();
		
		//p_rwsjx xh
		List<String> rwsjxXhUpdSqlLs = new ArrayList<String>();
		List<Map<String,Object>> rwsjxXhParamObjs = new ArrayList<Map<String,Object>>();
		
		String[] dbsjxbmArr = dbsjxbms.split(",");
		for (int i = 0; i < sjxbmArr.length; i++) {
			//删除P_DBSJXMX数据
			//如果为大项数据，明细数据
			if (!StringUtil.isEmptyString(dbsjxbmArr[i])) {
				//如果字段属性没有配置则跳过
				if (StringUtil.isEmptyString(zdArr[i])) {
					continue;
				}
				//del
				Map<String,Object> tmpMap = new HashMap<String,Object>();
				tmpMap.put("dxsjxbm", dbsjxbmArr[i]);
				tmpMap.put("xxsjxbm", sjxbmArr[i]);
				dxDelParamObjs.add(tmpMap);
				
				//ins
				tmpMap = new HashMap<String,Object>();
				tmpMap.put("dxsjxbm", dbsjxbmArr[i]);
				tmpMap.put("xxsjxbm", sjxbmArr[i]);
				tmpMap.put("dysjb", sjb);
				tmpMap.put("dyzd", zdArr[i]);
				tmpMap.put("xh", i);
				tmpMap.put("rwsx", rwsx);
				dxInsParamObjs.add(tmpMap);
			}else if(sjxsxArr[i].equals("02")){
				//del大项数据，p_sjxdy
				Map<String,Object> tmpMap = new HashMap<String,Object>();
				tmpMap.put("sjxbm", sjxbmArr[i]);
				tmpMap.put("rwsx", rwsx);
				dbDelParamObjs.add(tmpMap);
				
				//ins
				tmpMap = new HashMap<String,Object>();
				tmpMap.put("sjxbm", sjxbmArr[i]);
				tmpMap.put("xh", xhArr[i]);
				tmpMap.put("rwsx", rwsx);
				tmpMap.put("sjxsx", sjxsxArr[i]);
				xxInsParamObjs.add(tmpMap);
			}else if(sjxsxArr[i].equals("01")){
				//如果字段属性没有配置则跳过
				if (StringUtil.isEmptyString(zdArr[i])) {
					continue;
				}
				//del小型数据 p_sjxdy
				Map<String,Object> tmpMap = new HashMap<String,Object>();
				tmpMap.put("sjxbm", sjxbmArr[i]);
				tmpMap.put("dysjb", sjb);
				tmpMap.put("dyzd", zdArr[i]);
				tmpMap.put("rwsx", rwsx);
				xxDelParamObjs.add(tmpMap);
				
				//ins
				tmpMap = new HashMap<String,Object>();
				tmpMap.put("sjxbm", sjxbmArr[i]);
				tmpMap.put("dysjb", sjb);
				tmpMap.put("dyzd", zdArr[i]);
				tmpMap.put("xh", xhArr[i]);
				tmpMap.put("rwsx", rwsx);
				tmpMap.put("sjxsx", sjxsxArr[i]);
				xxInsParamObjs.add(tmpMap);
				
				tmpMap = new HashMap<String,Object>();
				tmpMap.put("zdgylx", zdgylx);
				tmpMap.put("rwsx", rwsx);
				tmpMap.put("xh", xhArr[i]);
				tmpMap.put("sjxbm", sjxbmArr[i]);
				rwsjxXhParamObjs.add(tmpMap);
			}
		}
		
		dxDelSqlLs.add(sqlId+"delDlmsDbSjxdy");
		dxInsSqlLs.add(sqlId+"insDlmsDbSjxdy");
		xxDelSqlLs.add(sqlId+"delDlmsXxSjxdy");
		xxInsSqlLs.add(sqlId+"insDlmsXxSjxdy");
		dbDelSqlLs.add(sqlId+"delDlmsXxSjxdy2");
		rwsjxXhUpdSqlLs.add(sqlId+"updRwsjxXh");
		
		//del
		baseDAOIbatis.executeBatch(dxDelSqlLs, dxDelParamObjs);
		baseDAOIbatis.executeBatch(xxDelSqlLs, xxDelParamObjs);
		//删除p_sjxdy，打包数据单独处理
		baseDAOIbatis.executeBatch(dbDelSqlLs, dbDelParamObjs);
		
		//新增p_sjxdy和P_DBSJXMX的数据
		baseDAOIbatis.executeBatch(dxInsSqlLs, dxInsParamObjs);
		baseDAOIbatis.executeBatch(xxInsSqlLs, xxInsParamObjs);
		
		//更新p_rwsjx 序号
		baseDAOIbatis.executeBatch(rwsjxXhUpdSqlLs, rwsjxXhParamObjs);
		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.rwmbpz.action.saveSjxzd.success",param.get(Constants.APP_LANG));
		return re;
	}
	
	/**
	 * 删除任务模板数据项
	 * @param param
	 * @return
	 */
	public ActionResult delRwsjx(Map<String, String> param){
		ActionResult re = new ActionResult();
		
		String sjxbm= param.get("sjxbm");
		String struct = param.get("struct");
		
		//如果是结构体的情况
		if (!StringUtil.isEmptyString(struct)) {
			//结构体格式：8.0.0.1.0.0.255.2#3.1.0.3.8.0.255.2#3.1.0.3.8.1.255.2#3.1.0.3.8.2.255.2#3.1.0.3.8.3.255.2#3.1.0.3.8.4.255.2
			String[] structArray = struct.split("#");
			for (int j = 0; j < structArray.length; j++) {
				String obis = structArray[j];
				int pos1 = obis.indexOf(".");
				int pos2 = obis.lastIndexOf(".");
				obis = obis.substring(0,pos1)+"#"+obis.substring(pos1+1,pos2)+"#"+obis.substring(pos2+1,obis.length());
				structArray[j] = obis;
			}
			
			Map<String, Object> paramNew = new HashMap<String, Object>();
			paramNew.put(Constants.APP_LANG,param.get(Constants.APP_LANG));
			paramNew.put("sjxbms", structArray);
			//获取结构体数据项列表
			List<Object> structSjxLs = getStructSjxList(paramNew);
			
			List<String> dxDelSqlLs = new ArrayList<String>();
			List<Map<String,Object>> dxDelParamObjs = new ArrayList<Map<String,Object>>();
			
			//删除p_dbsjxmx
			dxDelSqlLs.add(sqlId+"delDlmsDbSjxdy");
			
			String[] sjxbmArr = new String[structSjxLs.size()];
			for (int i = 0; i < structSjxLs.size(); i++) {
				Map<String,Object> tmpMap = (Map<String,Object>)structSjxLs.get(i);
				String xxsjxbm = tmpMap.get("SJXBM")==null?"":(String)tmpMap.get("SJXBM");
				
				Map<String,Object> tmpMap2 = new HashMap<String,Object>();
				tmpMap2.put("dxsjxbm", sjxbm);
				tmpMap2.put("xxsjxbm", xxsjxbm);
				
				dxDelParamObjs.add(tmpMap2);
				
				sjxbmArr[i] = xxsjxbm;
			}
			baseDAOIbatis.executeBatch(dxDelSqlLs, dxDelParamObjs);
			
			//删除p_dlmssjxgx
			Map<String,Object> param2 = new HashMap<String,Object>();
			param2.put("sjxbmArr", sjxbmArr);
			baseDAOIbatis.delete(sqlId+"delDlmsSjxgx", param2);
			
			//删除p_sjxdy 大项的情况
			baseDAOIbatis.delete(sqlId+"delDlmsXxSjxdy2", param);
		}else{
			//删除p_sjxdy 小项的情况
			baseDAOIbatis.delete(sqlId+"delDlmsXxSjxdy", param);
		}
		
		//删除p_dlmssjxgx
		Map<String,Object> param2 = new HashMap<String,Object>();
		String[] sjxbmArr = {sjxbm};
		param2.put("sjxbmArr", sjxbmArr);
		baseDAOIbatis.delete(sqlId+"delDlmsSjxgx", param2);
		//删除p_rwsjx
		baseDAOIbatis.delete(sqlId+"delRwsjx", param);
		
		re.setSuccess(true);
		re.setMsg("sysModule.ggdmgl.rwmbpz.action.delRwsjx.success",param.get(Constants.APP_LANG));
		return re;
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 查询任务模板
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "rwmb", start, limit,dir, sort, isExcel);
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 查询待选数据项
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDxsjx(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel){
		return baseDAOIbatis.getExtGrid(param, sqlId + "dxsjx", start, limit,dir, sort, isExcel);
	}
	
	/**
	 * 查询任务数据项
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryRwsjx(final Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel){
		return baseDAOIbatis.getExtGrid(param, sqlId + "rwsjx", start, limit,dir, sort, isExcel);
	}
	
	/**
	 * 获取结构体内对应的数据项列表
	 * @param param
	 * @return
	 */
	public List<Object> getStructSjxList(Map<String, Object> param){
		return baseDAOIbatis.queryForList(sqlId+"structSjxQuery", param);
	}
	
	/**
	 * 获取结构体内对应的数据项列表
	 * 根据任务属性
	 * @param param
	 * @return
	 */
	public List<Object> getStructSjxRwsxList(Map<String, Object> param){
		return baseDAOIbatis.queryForList(sqlId+"structSjxRwsxQuery", param);
	}
}
