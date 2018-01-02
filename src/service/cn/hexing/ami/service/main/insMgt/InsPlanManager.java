package cn.hexing.ami.service.main.insMgt;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description  设备安装计划
 * @author  xcx
 * @Copyright 
 * @time：2016-4-18
 * @version FDM 
 */
public class InsPlanManager implements InsPlanManagerInf {
	
	private static Logger logger = Logger.getLogger(InsPlanManager.class.getName());

	private static final String RESULT_TYPE_VALIDATE = "01"; 	//校验提示信息
	private static final String RESULT_TYPE_IMPORT = "02"; //导入结果提示信息
	private BaseDAOIbatis baseDAOIbatis;

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	private static String menuId = "12200"; //菜单ID
	private static String sqlId = "insPlan."; //sql文件命名空间
	
	/**
	 * 查询安装计划表计
	 */
	@Override
	public Map<String, Object> queryMInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryMInsP", start,
				limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询集中器安装计划
	 */
	@Override
	public Map<String, Object> queryDInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryDInsPlan", start,
				limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询采集器安装计划
	 */
	@Override
	public Map<String, Object> queryCInsPlan(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "queryCInsPlan", start,
				limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询安装计划表计的用户
	 */
	@Override
	public Map<String, Object> queryCust(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryCust", start, limit, dir, sort, isExcel);
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
	 * 查询集中器
	 */
	@Override
	public Map<String, Object> queryDcu(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryDcu", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询采集器
	 */
	@Override
	public Map<String, Object> queryCol(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"queryCol", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 查询变压器树
	 */
	@Override
	public List<TreeNode> getByqTree(String nodeId, String nodeType) {
		Map<String, Object> param = new HashMap<String, Object>();
		if("dw".equals(nodeType)) {
			param.put("dwdm", nodeId);
		} else {
			JSONObject job = JSONObject.fromObject(nodeId);
			Map<String, String> m = (Map<String, String>) JSONObject.toBean(job, Map.class);
			param.put("dwdm", m.get("dwdm"));
			param.put("byqmc", m.get("byqmc"));
		}
	
		List<Object> ls = baseDAOIbatis.queryForList(sqlId + "getByqTree", param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (int i = 0, len = ls.size(); i < len; i++) {
			Map<String, Object> m = (Map<String, Object>) ls.get(i);
			String byqid = StringUtil.getValue(m.get("BYQID"));
			String byqmc = StringUtil.getValue(m.get("MC")); //T.DWDM||':'||T.AZDZ AS INFO
			TreeNode node = new TreeNode(byqid, byqmc, "byq", true);
			node.setIconCls("byq");
			node.setInfo(StringUtil.getString(m.get("INFO")));
			tree.add(node);
		}
		return tree;
	}

	/**
	 * 数据库操作
	 */
	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		//安装类型分：表，集中器和采集器
		String insType = StringUtil.getString(param.get("insType"));
		String bussType = StringUtil.getString(param.get("bussType"));
		String operateType = StringUtil.getString(param.get("operateType"));
		String lang = StringUtil.getString(param.get(Constants.APP_LANG)); //语言
		
		if(insType.equals("dcu")){//集中器
			if(operateType.equals("01")) {//新增
				re = addDcuInsPln(param, lang);
			} else if(operateType.equals("02")) {//编辑
				re = editDcuInsPln(param, lang);
			} else if(operateType.equals("03")) {//删除
				re = delDcuInsPln(param, lang);
			}
		}else if(insType.equals("col")){//采集器
			if(operateType.equals("01")) {//新增
				re = addColInsPln(param, lang);
			} else if(operateType.equals("02")) {//编辑
				re = editColInsPln(param, lang);
			} else if(operateType.equals("03")) {//删除
				re = delColInsPln(param, lang);
			}
		}else{//表
			if(operateType.equals("01")) {//新增
				re = addMeterInsPln(param, lang);
			} else if(operateType.equals("02")) {//编辑
				re = editMeterInsPln(param, lang);
			} else if(operateType.equals("03")) {//删除
				re = delMeterInsPln(param, lang);
			}	
		}
		
		return re;
	}
	
	/**
	 * 新增表安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult addMeterInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		Map<String, String> sysMap = (Map<String, String>) AppEnv
				.getObject(Constants.SYS_PARAMMAP);
		String helpDocAreaId=sysMap.get("helpDocAreaId");
		if("CameroonEneo".equals(helpDocAreaId)){
			re=cusPaidCheck(param,lang);
			if(!re.isSuccess()){
				return re;
			}
		}
		param.put("devType", Constants.DEV_TYPE_METER);//设备类型 0=表计，1=集中器，2=采集器
		param.put("status", Constants.PLN_STATUS_UNHANDLED);
		String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getPID", null, String.class);
		//安装计划id格式：P00000000000001
		param.put("pid", "P" + StringUtil.leftZero(pid, 14));
		baseDAOIbatis.insert(sqlId+"insAddMInsPln", param);
		baseDAOIbatis.insert(sqlId+"insPlnOPLog", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.add.success", lang);
		return re;
	}
	
	/**
	 * 编辑表安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult editMeterInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		Map<String, String> sysMap = (Map<String, String>) AppEnv
				.getObject(Constants.SYS_PARAMMAP);
		String helpDocAreaId=sysMap.get("helpDocAreaId");
		if("CameroonEneo".equals(helpDocAreaId)){
			re=cusPaidCheck(param,lang);
			if(!re.isSuccess()){
				return re;
			}
		}
		String pid = StringUtil.getValue(param.get("pid"));
		param.put("devType", Constants.DEV_TYPE_METER);//设备类型 0=表计，1=集中器，2=采集器
		param.put("status", Constants.PLN_STATUS_UNHANDLED);
		re = plnCheck(pid,lang);
		if(re.isSuccess()) {
			baseDAOIbatis.insert(sqlId+"updAddMInsPln", param);
			baseDAOIbatis.insert(sqlId+"insPlnOPLog", param);
			//成功返回
			re.setSuccess(true);
			re.setMsg("mainModule.insMgt.plan.hint.edit.success", lang);
		} 
		return re;
	}
	
	/**
	 * 删除表安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult delMeterInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		String pid = StringUtil.getValue(param.get("pid"));
		re = plnCheck(pid,lang);//验证表安装计划
		if(re.isSuccess()) {
			baseDAOIbatis.insert(sqlId+"delPlnOPLog", param);
			baseDAOIbatis.insert(sqlId+"delAddMInsPln", param);
			//成功返回
			re.setSuccess(true);
			re.setMsg("mainModule.insMgt.plan.hint.del.success", lang);
		}
		return re;
	}
	
	/**
	 * 新增集中器安装计划 
	 * @param param
	 * @return
	 */
	public ActionResult addDcuInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"insAddDInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.add.success", lang);
		return re;
	}
	
	/**
	 * 新增采集器安装计划
	 * @param param
	 * @return
	 */
	public ActionResult addColInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"insAddCInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.add.success", lang);
		return re;
	}
	
	/**
	 * 编辑集中器安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult editDcuInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"updAddDInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.edit.success", lang);
		return re;
	}
	
	
	/**
	 * 编辑采集器安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult editColInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"updAddCInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.edit.success", lang);
		return re;
	}
	
	/**
	 * 删除集中器安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult delDcuInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"delAddDInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.del.success", lang);
		return re;
	}

	/**
	 * 删除采集器安装计划
	 * @param param
	 * @param lang
	 * @return
	 */
	public ActionResult delColInsPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId+"delAddCInsPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.del.success", lang);
		return re;
	}

	/**
	 * 数据库操作日志
	 */
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
	}
	
	/**
	 * 根据计划ID查询表安装计划
	 */
	@Override
	public Map<String, String> getMInsPByPid(String pid, String bussType) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("pid", pid);
		param.put("bussType", bussType);
		List<Object> list = baseDAOIbatis.queryForList(sqlId+"getMInsPByPid", param);
		if(list != null && list.size() > 0){
			Map<String, String> map = (Map<String, String>)list.get(0);
			return map;
		}
		return null;
	}
	
	/**
	 * 根据计划ID查询集中器安装计划
	 */
	@Override
	public Map<String, String> getDInsPByPid(String pid, String bussType) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("pid", pid);
		param.put("bussType", bussType);
		List<Object> list = baseDAOIbatis.queryForList(sqlId+"getDInsPByPid", param);
		if(list != null && list.size() > 0){
			Map<String, String> map = (Map<String, String>)list.get(0);
			return map;
		}
		return null;
	}
	
	/**
	 * 根据计划ID查询采集器安装计划
	 */
	@Override
	public Map<String, String> getCInsPByPid(String pid, String bussType) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("pid", pid);
		param.put("bussType", bussType);
		List<Object> list = baseDAOIbatis.queryForList(sqlId+"getCInsPByPid", param);
		if(list != null && list.size() > 0){
			Map<String, String> map = (Map<String, String>)list.get(0);
			return map;
		}
		return null;
	}

	/**
	 * Excel导入
	 */
	@Override
	public ActionResult parseExcel(FileInputStream fis, Map<String, String> param, String importType, String lang) {
		ActionResult result = new ActionResult();
		Workbook workbook = null;
		//表计类型List对象
		List<Object> mTypeList = new ArrayList<Object>();
		//接线方式List对象
		List<Object> wirList = new ArrayList<Object>();
		//表计模式List对象
		List<Object> mModeList = new ArrayList<Object>();
		//新表号Map对象
		Map<String,String> nmsnMap = new HashMap<String,String>();
		//老表号Map对象
		Map<String,String> omsnMap = new HashMap<String,String>();
		//重复新表号
		Map<String,String> dupNMSNMap = new HashMap<String,String>();
		//重复老表号
		Map<String,String> dupOMSNMap = new HashMap<String,String>();
		//暂时只读取第一个sheet的内容
		try {
			workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(0);
			int rowNum = sheet.getRows();
			StringBuffer validateBuf = new StringBuffer();
			List<Object> archivesList = new ArrayList<Object>();
			
			//获取第一行的列
			Cell[] rowOne = sheet.getRow(0);
			int cellNum = 0; //导入的列数
			for (int i = 0; i < rowOne.length; i++) {
				if(!"".equals(StringUtil.getValue(rowOne[i].getContents()))){
					cellNum++;
				}
			}
			
			//正确的档案列数
			int daCellNum = DadrUtil.getExcelField(importType).length;
			
			//判断列数是否合法
			if (cellNum != daCellNum) {
				result.setSuccess(false);
				result.setMsg("mainModule.insMgt.plan.import.valid.notformat",lang);
				result.setDataObject(RESULT_TYPE_VALIDATE);
				return result;
			}
			
			if(Constants.IMPORT_INSP_ADD_METER.equals(importType) ||
					Constants.IMPORT_INSP_CHANGE_METER.equals(importType)) {
				mTypeList = CommonUtil.getCode("mType", lang, false);
				wirList = CommonUtil.getCode("wiring", lang, false);
				mModeList = CommonUtil.getCode("mode", lang, false);
			}
			
			int errNum = 1;
			int notNullNum = 0;
			for (int i = 1; i < rowNum; i++) {
				Cell[] row = sheet.getRow(i);
				Cell[] rowNew = new Cell[daCellNum];
				//最后N列有空的情况，补空值
				for (int j = 0; j < row.length; j++) {
					rowNew[j] = row[j]; //只去有表头的列值
				}
				
				boolean allCellNull = true;
				for (int j = 0; j < rowNew.length; j++) {
					if (!"".equals(StringUtil.getValue(rowNew[j] == null? "" : rowNew[j].getContents()))) {
						allCellNull = false;
						break;
					}
				}
				//如果全部列为空，跳过该行
				if (allCellNull) {
					continue;
				}
				
				//记数非空行
				notNullNum++;
				//行号
				int r = i + 1;
				Map<String, String> m = CommonUtil.cell2Map(rowNew, DadrUtil.getExcelField(importType));
				
				//行数据校验
				ActionResult rowResult = validateData(importType, r, m, mTypeList,
						wirList, mModeList, lang);
				//校验新表号或老表号是否重复
				validateDupMeter(importType,m,nmsnMap,omsnMap,dupNMSNMap,dupOMSNMap);
				if (rowResult.isSuccess()) {
					m.put("CURR_STAFFID", param.get("CURR_STAFFID"));
					
					if(importType.equals(Constants.IMPORT_INSP_ADD_METER) || 
							importType.equals(Constants.IMPORT_INSP_ADD_DCU) ||
							importType.equals(Constants.IMPORT_INSP_ADD_COLL)){
						m.put("bussType", "0");//新装
					} else if(importType.equals(Constants.IMPORT_INSP_CHANGE_METER) || 
							  importType.equals(Constants.IMPORT_INSP_CHANGE_DCU) ||
							  importType.equals(Constants.IMPORT_INSP_CHANGE_COLL)){
						m.put("bussType", "1");//更换
					} else if(importType.equals(Constants.IMPORT_INSP_REMOVE_METER) || 
							  importType.equals(Constants.IMPORT_INSP_REMOVE_DCU) ||
							  importType.equals(Constants.IMPORT_INSP_REMOVE_COLL)){
						m.put("bussType", "2");//拆回
					}
					
					archivesList.add(m);
					
				}else{
					validateBuf.append((errNum++)+"." + rowResult.getMsg()+"<br>");
				}
			}
			
			String i18nStr = "";
			if(dupNMSNMap.size() > 0) {//新表号存在重复
				String dupNMSN = "";
				for(Map.Entry<String, String> entry : dupNMSNMap.entrySet()) {
					dupNMSN = entry.getValue() + ",";
				}
				if(!StringUtil.isEmptyString(dupNMSN)) {
					i18nStr = I18nUtil.getText(
							"mainModule.insMgt.plan.import.valid.newMeterNo", lang);
					validateBuf.append((errNum++)+".")
						.append(I18nUtil.getText("mainModule.insMgt.plan.import.valid.line.meterNo.dup", lang,
							new String[] {i18nStr,dupNMSN.substring(0, dupNMSN.length()-1)}))
						.append("<br>");
				}
			}
			
			if(dupOMSNMap.size() > 0) {//老表号存在重复
				String dupOMSN = "";
				for(Map.Entry<String, String> entry : dupOMSNMap.entrySet()) {
					dupOMSN = entry.getValue() + ",";
				}
				if(!StringUtil.isEmptyString(dupOMSN)) {
					i18nStr = I18nUtil.getText(
							"mainModule.insMgt.plan.import.valid.oldMeterNo", lang);
					validateBuf.append((errNum++)+".")
						.append(I18nUtil.getText("mainModule.insMgt.plan.import.valid.line.meterNo.dup", lang,
							new String[] {i18nStr,dupOMSN.substring(0, dupOMSN.length()-1)}))
						.append("<br>");
				}
			}
			
			//没有数据的情况
			if (notNullNum == 0) {
				result.setSuccess(false);
				result.setMsg("mainModule.insMgt.plan.import.valid.empty",lang);
				return result;
			}

			//没有错误，数据入库
			if (validateBuf.toString() == null || "".equals(validateBuf.toString())) {
				
				//保存档案信息
				String errMsg = saveInsPlanData(importType, archivesList, lang);
				//导入存在错误的情况
				if (errMsg != null && !"".equals(errMsg)) {
					result.setSuccess(false);
					result.setDataObject(RESULT_TYPE_IMPORT); //导入保存错误信息
					result.setMsg(errMsg);
				}else{
					result.setSuccess(true);
					result.setMsg("mainModule.insMgt.plan.import.valid.success",lang);
				}
				return result;
			}else{
				//有错误，直接返回提示信息
				result.setSuccess(false);
				//导入保存前数据校验信息
				result.setDataObject(RESULT_TYPE_VALIDATE);
				result.setMsg(validateBuf.toString());
				return result;
			}
		} catch (Exception e) {
			String errrMsg = I18nUtil.getText("mainModule.insMgt.plan.import.valid.failed", lang);
			logger.error(errrMsg + ":" + StringUtil.getExceptionDetailInfo(e));
			result.setSuccess(false);
			result.setMsg(errrMsg);
			return result;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}
	
	/**
	 * 校验数据
	 * @param archivesType 档案类型
	 * @param lineNo 行号
	 * @param m 导入数据
	 * @param mTypeList 表计类型列表
	 * @param wirList 接线方式列表
	 * @param mModeList 表计模式列表
	 * @param lang 国际化语言
	 * @return
	 */
	public ActionResult validateData(String archivesType, int lineNo, Map<String, String> m, 
			List<Object> mTypeList, List<Object> wirList, List<Object> mModeList, String lang){
		ActionResult result = new ActionResult();
		String reStr = "";
		
		//新装表
		if(archivesType.equals(Constants.IMPORT_INSP_ADD_METER)){
			reStr = validateAddMeter(lineNo, m, mTypeList, wirList, mModeList, lang);
		}
		
		//换表
		if(archivesType.equals(Constants.IMPORT_INSP_CHANGE_METER)){
			reStr = validateChanMeter(lineNo, m, mTypeList, wirList, mModeList, lang);
		}
		
		//拆表
		if(archivesType.equals(Constants.IMPORT_INSP_REMOVE_METER)){
			reStr = validateRemMeter(lineNo, m, lang);
		}
		
		//新装集中器
		if(archivesType.equals(Constants.IMPORT_INSP_ADD_DCU)){
			reStr = validateAddDcu(lineNo, m, lang);
		}
		
		//换集中器
		if(archivesType.equals(Constants.IMPORT_INSP_CHANGE_DCU)){
			reStr = validateChanDcu(lineNo, m, lang);
		}		

		//拆集中器
		if(archivesType.equals(Constants.IMPORT_INSP_REMOVE_DCU)){
			reStr = validateRemDcu(lineNo, m, lang);
		}	
		
		//新装采集器
		if(archivesType.equals(Constants.IMPORT_INSP_ADD_COLL)){
			reStr = validateAddCol(lineNo, m, lang);
		}
		
		//更换采集器
		if(archivesType.equals(Constants.IMPORT_INSP_CHANGE_COLL)){
			reStr = validateChanCol(lineNo, m, lang);
		}
		
		//拆除采集器
		if(archivesType.equals(Constants.IMPORT_INSP_REMOVE_COLL)){
			reStr = validateRemCol(lineNo, m, lang);
		}
		
		if (!"".equals(reStr)) {
			result.setSuccess(false);
			result.setMsg(reStr);
		}else{
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 * 新装表导入校验
	 * @param lineNo 行号
	 * @param m 导入数据
	 * @param mTypeList 表计类型列表
	 * @param wirList 接线方式列表
	 * @param mModeList 表计模式列表
	 * @param lang 国际化语言
	 * @return
	 */
	public String validateAddMeter(int lineNo, Map<String, String> m, List<Object> mTypeList,
			List<Object> wirList, List<Object> mModeList, String lang){
		StringBuffer validateBuf = new StringBuffer();
		StringBuffer reValidateBuf = new StringBuffer();
		String i18nStr = "";
		String c_uid = "";//户号所属单位
		String t_uid = "";//变压器所属单位
		String m_uid = "";//表所属单位
		boolean flag = false;//标志户与变压器所属单位是否一致
		
		//用户号
		String cno = StringUtil.getValue(m.get("cno"));
		if(StringUtil.isEmptyString(cno)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.custNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr}));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId + "getCustByCno",m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.custNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, cno}));
			} else {
				Map<String,String> cMap = (Map<String, String>) list.get(0);
				c_uid = StringUtil.getValue(cMap.get("UID"));
			}
		}
		
		//变压器ID
		String tfId = StringUtil.getValue(m.get("tfId"));
		if(StringUtil.isEmptyString(tfId)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.tf", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getTfByTfId", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.tf", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, tfId}));
			} else {
				Map<String,String> tMap = (Map<String, String>) list.get(0);
				t_uid = StringUtil.getValue(tMap.get("UID"));
			}
			
		}
		
		if(!StringUtil.isEmptyString(c_uid) && !StringUtil.isEmptyString(t_uid)) {//代表户号和变压器都存在
			if(!c_uid.equals(t_uid)) {//户号和变压器单位不一致
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.tUid.different", lang,
						new String[] {c_uid, t_uid}));
			} else {
				flag = true;
			}
		}
		
		//表计类型
		String mType = StringUtil.getValue(m.get("mType"));
		validateMType(mType,validateBuf,mTypeList,lang);
		
		//接线方式
		String wiring = StringUtil.getValue(m.get("wir"));
		validateWir(wiring,validateBuf,wirList,lang);
		
		//表计模式
		String mMode = StringUtil.getValue(m.get("mMode"));
		validateWir(mMode,validateBuf,mModeList,lang);
		
		//当存在新表号时，进行验证
		String nmsn = StringUtil.getValue(m.get("nmsn"));
		if(!StringUtil.isEmptyString(nmsn)){
			List<Object> meterList = baseDAOIbatis.queryForList(sqlId + "existMeter", m);
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.newMeterNo", lang);
			if(meterList.size() > 0) {//档案中存在新表
				Map<String,String> mMap = (Map<String, String>) meterList.get(0);
				m_uid = StringUtil.getValue(mMap.get("UID"));
				if(!StringUtil.isEmptyString(c_uid) && !StringUtil.isEmptyString(t_uid) && flag == true) {
					if(!m_uid.equals(c_uid)) {
						validateBuf.append(I18nUtil.getText(
								"mainModule.insMgt.plan.import.valid.line.mUid.different", 
								lang,new String[] {m_uid,c_uid}));
					}
				}
				List<Object> list = baseDAOIbatis.queryForList(sqlId + "existInsPByNMSN", m);
				if(list.size() > 0) {//存在新表对应的未完成的安装计划
					validateBuf.append(I18nUtil.getText(
							"mainModule.insMgt.plan.import.valid.line.insPln.exist", 
							lang,new String[] {i18nStr,nmsn}));
				}
			} else {
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", 
						lang,new String[] {i18nStr,nmsn}));
			}
		}
		
		if(validateBuf.length() > 0) {
			reValidateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line", 
					lang, new String[]{String.valueOf(lineNo)}))
					.append(validateBuf.toString());
		}
		
		return reValidateBuf.toString();
	}
	
	/**
	 * 换表导入校验
	 * @param lineNo 行号
	 * @param m 导入数据
	 * @param mTypeList 表计类型列表
	 * @param wirList 接线方式列表
	 * @param mModeList 表计模式列表
	 * @param lang 国际化语言
	 * @return
	 */
	public String validateChanMeter(int lineNo, Map<String, String> m, List<Object> mTypeList,
			List<Object> wirList, List<Object> mModeList, String lang){
		StringBuffer validateBuf = new StringBuffer();
		StringBuffer reValidateBuf = new StringBuffer();
		String i18nStr = "";
		String o_uid = "";//老表所属单位
		String n_uid = "";//新表所属单位
		
		//老表计
		String omsn = StringUtil.getValue(m.get("omsn"));
		if(StringUtil.isEmptyString(omsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldMeterNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		}else{
			List<Object> list= baseDAOIbatis.queryForList(sqlId+"getMeterInfo", m);
			if(list == null || list.size() <= 0) {
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldMeterNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, omsn}));
			} else {
				Map<String,String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
				m.put("cno", StringUtil.getValue(rMap.get("CNO")));
				o_uid = StringUtil.getValue(rMap.get("UID"));
			}
		}
		
		//校验要换的表计安装计划是否存在了
		ActionResult re = importReplaceValidate(omsn, "meter", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}
		
		//表计类型
		String mType = StringUtil.getValue(m.get("mType"));
		validateMType(mType,validateBuf,mTypeList,lang);
		
		//接线方式
		String wiring = StringUtil.getValue(m.get("wir"));
		validateMType(wiring,validateBuf,wirList,lang);
		
		//表计模式
		String mMode = StringUtil.getValue(m.get("mMode"));
		validateMType(mMode,validateBuf,mModeList,lang);
		
		//当存在新表号时，进行验证
		String nmsn = StringUtil.getValue(m.get("nmsn"));
		if(!StringUtil.isEmptyString(nmsn)){
			List<Object> meterList = baseDAOIbatis.queryForList(sqlId + "existMeter", m);
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.newMeterNo", lang);
			if(meterList.size() > 0) {//档案中存在新表
				Map<String,String> mMap = (Map<String, String>) meterList.get(0);
				n_uid = StringUtil.getValue(mMap.get("UID"));
				if(!StringUtil.isEmptyString(o_uid) && !StringUtil.isEmptyString(n_uid)) {
					if(!o_uid.equals(n_uid)) {
						validateBuf.append(I18nUtil.getText(
								"mainModule.insMgt.plan.import.valid.line.omUid.different", 
								lang,new String[] {o_uid,n_uid}));
					}
				}
				List<Object> list = baseDAOIbatis.queryForList(sqlId + "existInsPByNMSN", m);
				if(list.size() > 0) {//存在新表对应的未完成的安装计划
					validateBuf.append(I18nUtil.getText(
							"mainModule.insMgt.plan.import.valid.line.exist", 
							lang,new String[] {i18nStr,nmsn}));
				}
			} else {
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", 
						lang,new String[] {i18nStr,nmsn}));
			}
		}
		
		if(validateBuf.length() > 0) {
			reValidateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line", 
					lang, new String[]{String.valueOf(lineNo)}))
					.append(validateBuf.toString());
		}
		
		return reValidateBuf.toString();
	}
	
	/**
	 * 拆表导入校验
	 * @param lineNo 行号
	 * @param m 导入数据
	 * @param lang 国际化语言
	 * @return
	 */
	public String validateRemMeter(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		StringBuffer reValidateBuf = new StringBuffer();
		String i18nStr = "";
		
		//老表计
		String omsn = StringUtil.getValue(m.get("omsn"));
		if(StringUtil.isEmptyString(omsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldMeterNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr}));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getMeterInfo", m);
			if(list == null || list.size() <= 0 ){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldMeterNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, omsn}));
			}else{
				Map<String,String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
				m.put("cno", StringUtil.getValue(rMap.get("CNO")));
				m.put("mType", StringUtil.getValue(rMap.get("MTYPE")));
				m.put("wir", StringUtil.getValue(rMap.get("WIR")));
				m.put("mMode", StringUtil.getValue(rMap.get("MMODE")));
			}
		}
		
		//校验要拆的表计安装计划是否存在了
		ActionResult re = importReplaceValidate(omsn, "meter", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}
		
		if(validateBuf.length() > 0) {
			reValidateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line", 
					lang, new String[]{String.valueOf(lineNo)}))
					.append(validateBuf.toString());
		}
		
		return reValidateBuf.toString();
	}
	
	/**
	 * 新装集中器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateAddDcu(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//变压器ID
		String tfId = StringUtil.getValue(m.get("tfId"));
		if(StringUtil.isEmptyString(tfId)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.tf", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getTf", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.tf", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, tfId}));
			}
		}
		
		//集中器型号
		if(StringUtil.isEmptyString(StringUtil.getValue(m.get("dcuM")))){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.dcuModel", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}

		return validateBuf.toString();
	}
	
	/**
	 * 更换集中器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateChanDcu(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//集中器号
		String odsn = StringUtil.getValue(m.get("odsn"));
		if(StringUtil.isEmptyString(odsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldDcuNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId + "getDcuInfo", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldDcuNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] { i18nStr, odsn}));
			}else{
				Map<String,String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
			}
		}
		
		//校验要换的集中器安装计划是否存在了
		ActionResult re = importReplaceValidate(odsn, "dcu", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}
		
		//集中器型号
		if(StringUtil.isEmptyString(StringUtil.getValue(m.get("dcuM")))){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.dcuModel", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}
		
		return validateBuf.toString();
	}
	
	/**
	 * 拆除集中器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateRemDcu(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//集中器号
		String odsn = StringUtil.getValue(m.get("odsn"));
		if(StringUtil.isEmptyString(odsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldDcuNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId + "getDcuInfo", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldDcuNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] {i18nStr, odsn}));
			}else{
				Map<String,String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
				m.put("dcuM", StringUtil.getValue(rMap.get("DCUM")));
			}
		}
		
		//校验要拆的集中器安装计划是否存在了
		ActionResult re = importReplaceValidate(odsn, "dcu", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}

		return validateBuf.toString();
	}
	
	/**
	 * 新装采集器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateAddCol(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//变压器ID
		String tfId = StringUtil.getValue(m.get("tfId"));
		if(StringUtil.isEmptyString(tfId)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.tf", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getTf", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.line.tf", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] { i18nStr, tfId}));
			}
		}
		
		//采集器型号
		if(StringUtil.isEmptyString(StringUtil.getValue(m.get("colM")))){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.colModel", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		}
				
		return validateBuf.toString();
	}
	
	/**
	 * 更换采集器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateChanCol(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//老采集器号
		String ocsn = StringUtil.getValue(m.get("ocsn"));
		if(StringUtil.isEmptyString(ocsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldColNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getColInfo", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldColNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] { i18nStr, ocsn}));
			}else{
				Map<String, String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
			}
		}
		
		//校验要换的采集器安装计划是否存在了
		ActionResult re = importReplaceValidate(ocsn, "col", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}
		
		//采集器型号
		if(StringUtil.isEmptyString(StringUtil.getValue(m.get("colM")))){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.colModel", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}
		
		return validateBuf.toString();
	}
	
	/**
	 * 删除采集器导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public String validateRemCol(int lineNo, Map<String, String> m, String lang){
		StringBuffer validateBuf = new StringBuffer();
		String i18nStr = "";
		
		//老采集器号
		String ocsn = StringUtil.getValue(m.get("ocsn"));
		if(StringUtil.isEmptyString(ocsn)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldColNo", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] { i18nStr }));
		}else{
			List<Object> list = baseDAOIbatis.queryForList(sqlId+"getColInfo", m);
			if(list == null || list.size() <= 0){
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldColNo", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.notExist", lang,
						new String[] { i18nStr, ocsn}));
			}else{
				Map<String, String> rMap = (Map<String, String>) list.get(0);
				m.put("tfId", StringUtil.getValue(rMap.get("TFID")));
				m.put("colM", StringUtil.getValue(rMap.get("COLM")));
			}
		}
		
		//校验要拆的采集器安装计划是否存在了
		ActionResult re = importReplaceValidate(ocsn, "col", lang, lineNo);
		if(!re.isSuccess()){
			validateBuf.append(re.getMsg());
		}
		return validateBuf.toString();
	}
	
	/**
	 *  保存安装计划
	 */
	public String saveInsPlanData(String importType, List<Object> archivesList, String lang){
		StringBuffer errBuf = new StringBuffer();	
		int errNum = 2;
		
		//新装表
		if(importType.equals(Constants.IMPORT_INSP_ADD_METER)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum + 1;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "0");
				re = addMeterInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.addMeter.error",
							lang, new String[]{String.valueOf(errNum), param.get("cno")});
					errBuf.append(String.valueOf(errNum) + tmpStr + re.getMsg());
				}
			}
		}
		
		//换表
		if(importType.equals(Constants.IMPORT_INSP_CHANGE_METER)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "1");
				re = addMeterInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.chanMeter.error",
							lang, new String[]{String.valueOf(errNum), param.get("omsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//拆表
		if(importType.equals(Constants.IMPORT_INSP_REMOVE_METER)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "2");
				re = addMeterInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.remMeter.error",
							lang, new String[]{String.valueOf(errNum), param.get("omsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//安装集中器
		if(importType.equals(Constants.IMPORT_INSP_ADD_DCU)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "0");
				re = addDcuInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.addDcu.error",
							lang, new String[]{String.valueOf(errNum), param.get("tfId")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//更换集中器
		if(importType.equals(Constants.IMPORT_INSP_CHANGE_DCU)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "1");
				re = addDcuInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.chanDcu.error",
							lang, new String[]{String.valueOf(errNum), param.get("odsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//拆除集中器
		if(importType.equals(Constants.IMPORT_INSP_REMOVE_DCU)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "2");
				re = addDcuInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.remDcu.error",
							lang, new String[]{String.valueOf(errNum), param.get("odsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//安装采集器
		if(importType.equals(Constants.IMPORT_INSP_ADD_COLL)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "0");
				re = addColInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.addCol.error",
							lang, new String[]{String.valueOf(errNum), param.get("tfId")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//更换采集器
		if(importType.equals(Constants.IMPORT_INSP_CHANGE_COLL)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "1");
				re = addColInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.chanCol.error",
							lang, new String[]{String.valueOf(errNum), param.get("ocsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		//拆除采集器
		if(importType.equals(Constants.IMPORT_INSP_REMOVE_COLL)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "2");
				re = addColInsPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.remCol.error",
							lang, new String[]{String.valueOf(errNum), param.get("ocsn")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		return errBuf.toString();
    }
	
	/**
	 * 变压器查询
	 */
	public Map<String, Object> queryTf(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId+"getTfList", start, limit, dir, sort, isExcel);
	}
	
	/**
	 * 校验换表计或者集中器,采集器是否已存在的未反馈的安装计划记录
	 */
	@Override
	public ActionResult validateReplace(Map<String, String> param, Util util) {
		ActionResult re = new ActionResult();//返回re
		
		String appLang = StringUtil.getString(param.get(Constants.APP_LANG));
		String valType = StringUtil.getString(param.get("valType"));
		String deviceOldNo = StringUtil.getString(param.get("deviceOldNo"));
		
		if(valType.equals("meter")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "mInsPlanExist", param, Integer.class);
			if(num > 0){
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.valid.meter.existInsPlan", appLang, new String[]{deviceOldNo});
				return re;
			}
			
		}else if(valType.equals("dcu")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "dInsPlanExist", param, Integer.class);
			if(num > 0){
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.valid.dcu.existInsPlan", appLang, new String[]{deviceOldNo});
				return re;
			}
			
		}else if(valType.equals("col")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "cInsPlanExist", param, Integer.class);
			if(num > 0){
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.valid.col.existInsPlan", appLang, new String[]{deviceOldNo});
				return re;
			}
		}
		
	    re.setSuccess(true);
	    return re;
	}
	
	/**
	 * 校验换表计或者集中器是否已有存在的记录 (Excel导入用)
	 * @param deviceOldNo
	 * @param valType
	 * @param appLang
	 * @return
	 */
	public ActionResult importReplaceValidate(String deviceOldNo, String valType, String appLang, int lineNo){
		ActionResult re = new ActionResult();//返回re
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("deviceOldNo", deviceOldNo);
		
		if(valType.equals("meter")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "mInsPlanExist", param, Integer.class);
			if(num > 0){
				String i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldMeterNo", appLang);
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.import.valid.line.exist", appLang, new String[]{i18nStr, deviceOldNo});
				return re;
			}
			
		}else if(valType.equals("dcu")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "dInsPlanExist", param, Integer.class);
			if(num > 0){
				String i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldDcuNo", appLang);
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.import.valid.line.exist", appLang, new String[]{i18nStr, deviceOldNo});
				return re;
			}
			
		}else if(valType.equals("col")){
			Integer num = (Integer) baseDAOIbatis.queryForObject(sqlId + "cInsPlanExist", param, Integer.class);
			if(num > 0){
				String i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.oldColNo", appLang);
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.import.valid.line.exist", appLang, new String[]{i18nStr, deviceOldNo});
				return re;
			}
		}
		
		//注册返回
	    re.setSuccess(true);
	    return re;
	}
	
	/**
     * 根据工单id获取对应的安装计划
     * @param woIDList 工单ID列表
     * @return
     */
	public List<Object> getMeterPln(List<Object> woIDList) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(woIDList.size() > 0) {
			String[] woids = woIDList.toArray(new String[]{});
			map.put("woids", woids);
		}
		return baseDAOIbatis.queryForList(sqlId + "getMeterPln", map);
	}
	
	/**
     * 更新表安装计划状态
     * @param paramList 安装计划状态列表
     * @return
     */
	public ActionResult updPlnStatus(List<Object> paramList) {
		ActionResult re = new ActionResult(false,"");
		baseDAOIbatis.executeBatch(sqlId + "updPlnStatus", paramList);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 获取工单下指定状态的安装计划数量（表，集中器，采集器）
	 * @param woid
	 * @param status
	 * @return
	 */
	public Integer getInsPlnCount(String woid,String[] status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("woid", woid);
		map.put("status", status);
		int meterPlnCount = (Integer) baseDAOIbatis.queryForObject(sqlId + "getMeterPlnCount", map, Integer.class);
		int dcuPlnCount = (Integer) baseDAOIbatis.queryForObject(sqlId + "getDcuPlnCount", map, Integer.class);
		int colPlnCount = (Integer) baseDAOIbatis.queryForObject(sqlId + "getColPlnCount", map, Integer.class);
		return meterPlnCount + dcuPlnCount + colPlnCount;
	}
	
	/**
     * 根据安装计划id获取对应的表安装计划
     * @param pid 安装计划id
     * @return
     */
    public List<Object> getMeterPlnByPid(String pid) {
    	return  baseDAOIbatis.queryForList(sqlId + "getMeterPlnByPid", pid);
    }
    
    /**
     * 获取变压器id下所有已派工的安装计划
     * @param tfid
     * @return
     */
    public List<Object> getMeterPlnByTfId(String tfid) {
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("status", Constants.PLN_STATUS_DISPATCHED);
    	map.put("tfid", tfid);
    	return baseDAOIbatis.queryForList(sqlId + "getMeterPlnByTfId", map);
    }
    
    /**
     * 保存安装计划操作日志
     * @param param
     * @return
     */
    public ActionResult storePlanOPLog(List<Object> param) {
    	ActionResult re = new ActionResult(false,"");
    	baseDAOIbatis.executeBatch(sqlId + "insPlnOPLogForWeb", param);
    	re.setSuccess(true);
    	return re;
    }
    
    /**
     * 安装计划检查
     * @param pid 计划id
     * @param lang
     * @return
     */
    protected ActionResult plnCheck(String pid,String lang) {
    	ActionResult re = new ActionResult(true,"");
    	Map<String,String> param = new HashMap<String,String>();
    	param.put("pid", pid);
    	param.put("status", Constants.PLN_STATUS_UNHANDLED);
    	List<Object> existEditPlnList = baseDAOIbatis.queryForList(sqlId + "existEditMPln", param);
		if(existEditPlnList != null && existEditPlnList.size() > 0) {
			re.setSuccess(true);
		} else {//不存在未处理的表安装计划
			re.setSuccess(false);
			re.setMsg("mainModule.insMgt.plan.valid.disOperate", lang);
		}
		return re;
    }
    
    /**
     * 校验表计类型
     * @param mType 表计类型
     * @param validateBuf 校验不通过信息
     * @param mTypeList 表计类型列表
     * @param lang 国际化语言
     */
    public static void validateMType(String mType,StringBuffer validateBuf,List<Object> mTypeList, String lang) {
    	String i18nStr = "";
    	if(StringUtil.isEmptyString(mType)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.meterType", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr}));
		} else {
			//标志表计类型是否在基础数据中定义，false=未定义，true=已定义
			boolean existMType = false;
			for(Object mTypeObj : mTypeList) {
				Map<String,String> mTypeMap = (Map<String, String>) mTypeObj;
				if(StringUtil.getValue(mTypeMap.get("BM")).equals(mType)) {
					existMType = true;
					break;
				}
			}
			if(!existMType) {
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.meterType", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.illegal", lang,
						new String[] {i18nStr,mType}));
			}
		}
    }
    
    /**
     * 校验接线方式
     * @param wiring 接线方式
     * @param validateBuf 校验不通过信息
     * @param wirList 表计类型列表
     * @param lang 国际化语言
     */
    public static void validateWir(String wiring,StringBuffer validateBuf,List<Object> wirList, String lang) {
    	String i18nStr = "";
    	if(StringUtil.isEmptyString(wiring)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.wiring", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		} else {
			//标志接线方式是否在基础数据中定义，false=未定义，true=已定义
			boolean existWir = false;
			for(Object wirObj : wirList) {
				Map<String,String> wirMap = (Map<String, String>) wirObj;
				if(StringUtil.getValue(wirMap.get("BM")).equals(wiring)) {
					existWir = true;
					break;
				}
			}
			if(!existWir) {
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.wiring", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.illegal", lang,
						new String[] {i18nStr,wiring}));
			}
		}
    }
    
    /**
     * 校验表计模式
     * @param mMode 表计模式
     * @param validateBuf 校验不通过信息
     * @param mModeList 表计模式列表
     * @param lang 国际化语言
     */
    public static void validateMMode(String mMode,StringBuffer validateBuf,List<Object> mModeList, String lang) {
    	String i18nStr = "";
    	if(StringUtil.isEmptyString(mMode)){
			i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.meterMode", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.insMgt.plan.import.valid.line.notEmpty", lang,
					new String[] {i18nStr }));
		} else {
			//标志表计模式是否在基础数据中定义，false=未定义，true=已定义
			boolean existMMode = false;
			for(Object mModeObj : mModeList) {
				Map<String,String> mModeMap = (Map<String, String>) mModeObj;
				if(StringUtil.getValue(mModeMap.get("BM")).equals(mMode)) {
					existMMode = true;
					break;
				}
			}
			if(!existMMode) {
				i18nStr = I18nUtil.getText("mainModule.insMgt.plan.import.valid.meterMode", lang);
				validateBuf.append(I18nUtil.getText(
						"mainModule.insMgt.plan.import.valid.line.illegal", lang,
						new String[] {i18nStr,mMode}));
			}
		}
    }
    
    /**
     * 验证重复表号
     * @param importType 导入档案类型
     * @param m 导入数据
     * @param nmsnMap 新表号Map对象
     * @param omsnMap 老表号Map对象
     */
    public void validateDupMeter(String importType, Map<String,String> m, Map<String,String> nmsnMap,
    		Map<String,String> omsnMap, Map<String,String> dupNMSNMap, Map<String,String> dupOMSNMap) {
    	if(Constants.IMPORT_INSP_ADD_METER.equals(importType) ||
    			Constants.IMPORT_INSP_CHANGE_METER.equals(importType)) {//新装表或换表
    		String nmsn = StringUtil.getValue(m.get("nmsn"));
    		if(!StringUtil.isEmptyString(nmsn)) {
    			if(nmsnMap.size() == 0) {
        			nmsnMap.put(nmsn, nmsn);
        		} else {
        			if(StringUtil.isEmptyString(nmsnMap.get(nmsn))) {//不存在重复
        				nmsnMap.put(nmsn, nmsn);
        			} else {//存在重复
        				dupNMSNMap.put(nmsn, nmsn);
        			}
        		}
    		}
    	} 
    	
    	if(Constants.IMPORT_INSP_REMOVE_METER.equals(importType) ||
    			Constants.IMPORT_INSP_CHANGE_METER.equals(importType)) {//换表或拆表
    		String omsn = StringUtil.getValue(m.get("omsn"));
    		if(omsnMap.size() == 0) {
    			omsnMap.put(omsn, omsn);
    		} else {
    			if(StringUtil.isEmptyString(omsnMap.get(omsn))) {//不存在重复
    				omsnMap.put(omsn, omsn);
    			} else {//存在重复
    				dupOMSNMap.put(omsn, omsn);
    			}
    		}
    	} 
    }
    
    /**
     * 安装计划客户缴费检查
     * @param pid 计划id
     * @param lang
     * @return
     */
    protected ActionResult cusPaidCheck(Map<String, String> param,String lang) {
    	ActionResult re = new ActionResult(true,"");
    	List<Object> list = baseDAOIbatis.queryForList(sqlId+"getSvryStByCno", param);
		if(list != null && list.size() > 0){
			Map<String, String> map = (Map<String, String>)list.get(0);
			String status =map.get("STATUS");
			if(!"6".equals(status)){
				re.setSuccess(false);
				re.setMsg("mainModule.insMgt.plan.hint.add.cusNoPay", lang);
			}
		}else{
			re.setSuccess(false);
			re.setMsg("mainModule.insMgt.plan.hint.add.cusNoPay", lang);
		}
		return re;
    }
}
