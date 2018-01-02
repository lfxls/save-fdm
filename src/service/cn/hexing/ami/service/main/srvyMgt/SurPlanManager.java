package cn.hexing.ami.service.main.srvyMgt;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
















import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.service.main.insMgt.DadrUtil;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

public class SurPlanManager implements SurPlanManagerInf {
	
	private static Logger logger = Logger.getLogger(SurPlanManager.class.getName());
	private static final String RESULT_TYPE_VALIDATE = "01"; 	//校验提示信息
	private static final String RESULT_TYPE_IMPORT = "02"; //导入结果提示信息
	private BaseDAOIbatis baseDAOIbatis;
	
	private static String menuId = "15100"; //菜单ID
	private static String sqlId = "surPlan."; //sql文件命名空间

	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}


	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		String lang =StringUtil.getString(param.get(Constants.APP_LANG));
		if(czid.equals(menuId+"01")) {//新增
			re = addSurPln(param, lang);
		} else if(czid.equals(menuId+"02")) {//编辑
			re = editSurPln(param, lang);
		} else if(czid.equals(menuId+"03")) {//删除
			re = delSurPln(param, lang);
		}
		
		return re;
	}

	private ActionResult delSurPln(Map<String, String> param, String lang) {
		ActionResult re = new ActionResult();
		String pid = StringUtil.getValue(param.get("pid"));
		baseDAOIbatis.insert(sqlId+"delPlnOPLog", param);
		baseDAOIbatis.insert(sqlId+"delSurPln", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.srvyMgt.plan.hint.del.success", lang);
		return re;
	}
	private ActionResult editSurPln(Map<String, String> param, String lang) {
		ActionResult re = new ActionResult();
		param.put("status", Constants.PLN_STATUS_UNHANDLED);
		//String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getPID", null, String.class);
		//安装计划id格式：P00000000000001
		//param.put("pid", "P" + StringUtil.leftZero(pid, 14));
		baseDAOIbatis.insert(sqlId+"updSurPln", param);
		baseDAOIbatis.insert(sqlId+"insPlnOPLog", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.srvyMgt.plan.hint.edit.success", lang);
		return re;
	}
	private ActionResult addSurPln(Map<String, String> param, String lang) {
		ActionResult re = new ActionResult();
		param.put("status", Constants.PLN_STATUS_UNHANDLED);
		String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getPID", null, String.class);
		//安装计划id格式：P00000000000001
		param.put("pid", "P" + StringUtil.leftZero(pid, 14));
		baseDAOIbatis.insert(sqlId+"insAddSurPln", param);
		baseDAOIbatis.insert(sqlId+"insPlnOPLog", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.srvyMgt.plan.hint.add.success", lang);
		return re;
	}
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);

	}
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "querySurP", start,
				limit, dir, sort, isExcel);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getSurPByPid(String pid) {
		Map<String, String> param= new HashMap<String, String>();
		param.put("pid", pid);
		List<Object> splan=(List<Object>) baseDAOIbatis.queryForList(sqlId+"getSPlan", param);
		Map<String, String >map=(Map<String, String>) splan.get(0);
		return map; 
	}
	
	@Override
	public List<Object> getSrvyPln(String woid) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("woid", woid);
		return baseDAOIbatis.queryForList(sqlId+"getSrvyPlnByWoid", map);
	}
	
	@Override
	public ActionResult updSrvyPlnStatus(List<Object> paramList) {
		ActionResult re = new ActionResult(true,"");
		baseDAOIbatis.executeBatch(sqlId + "updSrvyPlnStatus", paramList);
		re.setSuccess(true);
		return re;
	}
	
	@Override
	public int getSrvyPlnCount(String woid, String[] status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("woid", woid);
		map.put("status", status);
		return (Integer) baseDAOIbatis.queryForObject(sqlId + "getSrvyPlnCount", map, Integer.class);
	}

	@Override
	public int existSrvyPln(String pid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid", pid);
		return (Integer) baseDAOIbatis.queryForObject(sqlId + "existSrvyPln", map, Integer.class);
	}
	/**
	 * Excel导入
	 */
	@Override
	public ActionResult parseExcel(FileInputStream fis, Map<String, String> param, String importType, String lang) {
		ActionResult result = new ActionResult();
		Workbook workbook = null;
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
				ActionResult rowResult = validateData(importType, r, m, lang);
				if (rowResult.isSuccess()) {
					m.put("CURR_STAFFID", param.get("CURR_STAFFID"));
					
					archivesList.add(m);
					
				}else{
					validateBuf.append((errNum++)+"." + rowResult.getMsg()+"<br>");
				}
			}
			
			String i18nStr = "";
			
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
	
	public ActionResult validateData(String archivesType, int lineNo, Map<String, String> m, 
			 String lang){
		ActionResult result = new ActionResult();
		String reStr = "";
		
		
		if(archivesType.equals(Constants.IMPORT_INSP_SRVY)){
			reStr = validateAddSrvy(lineNo, m, lang);
		}
		
		if (!"".equals(reStr)) {
			result.setSuccess(false);
			result.setMsg(reStr);
		}else{
			result.setSuccess(true);
		}
		return result;
	}
	
	public String validateAddSrvy(int lineNo, Map<String, String> m,  String lang){
		StringBuffer validateBuf = new StringBuffer();
		StringBuffer reValidateBuf = new StringBuffer();
		String i18nStr = "";
		
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
				m.put("cname", cMap.get("CNAME"));
				m.put("addr", cMap.get("ADDR"));
				m.put("mno", cMap.get("PHONE"));
				
			}
		}
		
	
		return reValidateBuf.toString();
	}
	
	/**
	 *  保存安装计划
	 */
	public String saveInsPlanData(String importType, List<Object> archivesList, String lang){
		StringBuffer errBuf = new StringBuffer();	
		int errNum = 2;
		
	
		if(importType.equals(Constants.IMPORT_INSP_SRVY)){
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum++;
				ActionResult re =new ActionResult();
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				re = addSrvyPln(param, lang);
				if(!re.isSuccess()){
					String tmpStr = I18nUtil.getText("mainModule.insMgt.plan.import.addCol.error",
							lang, new String[]{String.valueOf(errNum), param.get("tfId")});
					errBuf.append(String.valueOf(errNum) + tmpStr);
				}
			}
		}
		
		
		return errBuf.toString();
    }
	
	public ActionResult addSrvyPln(Map<String, String> param, String lang){
		ActionResult re = new ActionResult();
		
		//param.put("devType", Constants.DEV_TYPE_METER);//设备类型 0=表计，1=集中器，2=采集器
		param.put("status", Constants.PLN_STATUS_UNHANDLED);
		String pid = (String) baseDAOIbatis.queryForObject(sqlId + "getPID", null, String.class);
		//安装计划id格式：P00000000000001
		param.put("pid", "P" + StringUtil.leftZero(pid, 14));
		baseDAOIbatis.insert(sqlId+"insAddSurPln", param);
		baseDAOIbatis.insert(sqlId+"insPlnOPLog", param);
		//成功返回
		re.setSuccess(true);
		re.setMsg("mainModule.insMgt.plan.hint.add.success", lang);
		return re;
	}
}
