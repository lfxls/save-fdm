package cn.hexing.ami.service.main.preMgt;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.service.main.insMgt.DadrUtil;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description Token管理manager
 * @author zrp
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time:2016-5-17
 * @version FDM2.0
 */

public class TokenMgtManager implements TokenMgtManagerInf{
	
	private BaseDAOIbatis baseDAOIbatis = null;
	static String menuId = "14100";
	static String sqlId = "tokenMgt.";
	
	private static final String RESULT_TYPE_VALIDATE = "01"; 	//校验提示信息
	private static final String RESULT_TYPE_IMPORT = "02"; //导入结果提示信息
	
	private static Logger logger = Logger.getLogger(TokenMgtManager.class.getName());
	
	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		if ((menuId + "01").equals(czid)) {
			re = addToken(param);
		} else if ((menuId + "02").equals(czid)) {
			re = updateToken(param);
		} else if ((menuId + "03").equals(czid)) {
			re = deleteToken(param);
		}
		return re;
	}
	
	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		baseDAOIbatis.insRzFwxx(czid, menuId, czyId, unitCode, cznr);
		
	}
	
	/**
	 * @Description Token管理界面，查询Token
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		Map<String, Object> resultMap = baseDAOIbatis.getExtGrid(param, sqlId + "getTokenList", start, limit, dir, sort, isExcel);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return null;
	}
	
	/**
	 * @Description 新增Token
	 * @param param
	 * @return
	 */
	public ActionResult addToken(Map<String, String> param) {
		ActionResult re = new ActionResult();
		String msn = param.get("msn");
		String cno = param.get("cno");
		String token = param.get("token");
		//过滤掉-
		token = token.replaceAll("-", "");
		String orderid = param.get("orderid");
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("msn", msn);
		m.put("cno", cno);
		m.put("token", token);
		m.put("orderid", orderid);
		String tid0=(String)baseDAOIbatis.queryForObject(sqlId+"getTid", param, String.class);
		m.put("tid", tid0);
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsToken", m);
		if(ls != null && ls.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.preMgt.tokenMgt.add_existsToken", param.get(Constants.APP_LANG));
		}else{
			List<?> ls1 = baseDAOIbatis.queryForList(sqlId + "existsMeterSort", param);
			if(ls1 != null && ls1.size()>0) {
				baseDAOIbatis.insert(sqlId + "insertTokenNormal", m);
			} else {
				baseDAOIbatis.insert(sqlId + "insertTokenFirst", m);
			}
			
			Token t = new Token();
			String czyId=param.get(Constants.CURR_STAFFID);
			String lang=param.get(Constants.APP_LANG);
			List<Object> ls2=new ArrayList<Object>();
			
			//t.setTid(param.get("tid"));新增param里面没有tid,需从数据库中查询
			List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "qyTid", token);
			String tid = StringUtil.getValue(ls3.get(0));
			
			t.setTid(tid);
			t.setMsn(param.get("msn"));
			t.setCno(param.get("cno"));
			t.setOrderid(param.get("orderid"));
			t.setToken(param.get("token"));
			t.setSort(param.get("sort"));
			t.setUpt_date(param.get("upt_date"));
			t.setStatus(param.get("status"));
			ls2.add(t);						
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TOKEN, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls2, lang);
			
			re.setSuccess(true);
			re.setMsg("mainModule.preMgt.tokenMgt.add_success", param.get(Constants.APP_LANG));		
		}		
		return re;
	} 
	
	/**
	 * @Description 更新Token
	 * @param param
	 * @return
	 */
	private ActionResult updateToken(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<?> ls = baseDAOIbatis.queryForList(sqlId + "existsToken", param);
		
		List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "queryStatus", param);
		String stsOld = StringUtil.getValue(ls3.get(0));
		if ("0".equals(stsOld)) {
			re.setSuccess(false);
			re.setMsg("mainModule.preMgt.tokenMgt.upt_stsIsSuc", param.get(Constants.APP_LANG));
		} else if (ls != null && ls.size()>0) {
			re.setSuccess(false);
			re.setMsg("mainModule.preMgt.tokenMgt.add_existsToken", param.get(Constants.APP_LANG));
		} else {
			baseDAOIbatis.update(sqlId + "updateToken", param);
			
			Token t = new Token();
			String czyId=param.get(Constants.CURR_STAFFID);
			String lang=param.get(Constants.APP_LANG);
			List<Object> ls2=new ArrayList<Object>();
			t.setTid(param.get("tid"));
			t.setMsn(param.get("msn"));
			t.setCno(param.get("cno"));
			t.setOrderid(param.get("orderid"));
			t.setToken(param.get("token"));
			t.setSort(param.get("sort"));
			t.setUpt_date(param.get("upt_date"));
			t.setStatus(param.get("status"));
			ls2.add(t);						
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TOKEN, Constants.DATAUPDATE_OPTYPE_UPT, czyId, ls2, lang);
			
			re.setSuccess(true);
			re.setMsg("mainModule.preMgt.tokenMgt.update_success", param.get(Constants.APP_LANG));			
		}
		
		return re;
	}
	
	/**
	 * @Description 删除Token
	 * @param param
	 * @return
	 */
	private ActionResult deleteToken(Map<String, String> param){
		ActionResult re = new ActionResult();
		List<Object> ls3 = baseDAOIbatis.queryForList(sqlId + "queryStatus", param);
		String stsOld = StringUtil.getValue(ls3.get(0));
		if ("0".equals(stsOld)) {
			re.setSuccess(false);
			re.setMsg("mainModule.preMgt.tokenMgt.del_stsIsSuc", param.get(Constants.APP_LANG));
		} else {
			
			
			Token t = new Token();
			String czyId=param.get(Constants.CURR_STAFFID);
			String lang=param.get(Constants.APP_LANG);
			List<Object> ls2=new ArrayList<Object>();
			t.setTid(param.get("tid"));
			
			//根据tid从数据库中查出token和msn
			List<Object> ls4 = baseDAOIbatis.queryForList(sqlId + "qyToken", param);
			String token = StringUtil.getValue(ls4.get(0));
			List<Object> ls5 = baseDAOIbatis.queryForList(sqlId + "qyMsn", param);
			String msn = StringUtil.getValue(ls5.get(0));
			
			t.setMsn(msn);
			t.setCno(param.get("cno"));
			t.setOrderid(param.get("orderid"));
			t.setToken(token);
			t.setSort(param.get("sort"));
			t.setUpt_date(param.get("upt_date"));
			t.setStatus(param.get("status"));
			ls2.add(t);
			//删除之前先记录
			baseDAOIbatis.delete(sqlId + "deleteToken", param);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TOKEN, Constants.DATAUPDATE_OPTYPE_DEL, czyId, ls2, lang);
			
			re.setMsg("mainModule.preMgt.tokenMgt.delete_success", param.get(Constants.APP_LANG));		
		}		
		re.setSuccess(true);		
		return re;
	}
	
	/**
	 * Excel导入
	 */
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
				result.setMsg("mainModule.preMgt.tokenMgt.import.valid.notformat",lang);
				result.setDataObject(RESULT_TYPE_VALIDATE);
				return result;
			}
			
			int errNum = 1;
			int notNullNum = 0;
		
			//去掉第一行，i从1开始
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
				//如果全部为列为空，跳过该行
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
					if(!rowResult.getMsg().equals("dataExist")) {//过滤已导入数据
						m.put("CURR_STAFFID", param.get("CURR_STAFFID"));
						if(importType.equals(Constants.IMPORT_PRE_ADD_TOKEN) ){
							m.put("bussType", "0");//新Token
						} 
						archivesList.add(m);
					}
				}else{
					validateBuf.append((errNum++)+"." + rowResult.getMsg()+"<br>");
				}
			}
		
			//没有数据的情况
			if (notNullNum == 0) {
				result.setSuccess(false);
				result.setMsg("mainModule.preMgt.tokenMgt.import.valid.empty",lang);
				return result;
			}

			//没有错误，数据入库
			if (validateBuf.toString() == null || "".equals(validateBuf.toString())) {
				logger.debug("add token begin time 4 = " + DateUtil.getFulltime());
				//保存档案信息
				String errMsg = saveTokenData(importType, archivesList, lang);
				logger.debug("add token begin time 5 = " + DateUtil.getFulltime());
				//导入存在错误的情况
				if (errMsg != null && !"".equals(errMsg)) {
					result.setSuccess(false);
					result.setDataObject(RESULT_TYPE_IMPORT); //导入保存错误信息
					result.setMsg(errMsg);
				}else{
					result.setSuccess(true);
					result.setMsg("mainModule.preMgt.tokenMgt.import.valid.success",lang);
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
			String errrMsg = I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.failed", lang);
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
	 * @param archivesType
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	
	public ActionResult validateData(String archivesType, int lineNo, Map<String, String> m, String lang){
		ActionResult result = new ActionResult();
		String reStr = "";
		
		//新增Token
		if(archivesType.equals(Constants.IMPORT_PRE_ADD_TOKEN)){
			reStr = validateAddToken(lineNo, m, lang, baseDAOIbatis);
		}
		

		
		if (!"".equals(reStr) && !"dataExist".equals(reStr)) {
			result.setSuccess(false);
		} else{
			result.setSuccess(true);
		}
		result.setMsg(reStr);
		return result;
	}
	
	/**
	 * Token导入校验
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateAddToken(int lineNo, Map<String, String> m, String lang, BaseDAOIbatis dao){
		StringBuffer validateBuf = new StringBuffer();
		StringBuffer reValidateBuf = new StringBuffer();
		String i18nStr = "";
		
		//表号
		String msn = StringUtil.getValue(m.get("msn"));
		if(StringUtil.isEmptyString(msn)){
			i18nStr = I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.msn", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.preMgt.tokenMgt.import.valid.line.notEmpty", lang,
					new String[] {i18nStr}));
		} else if (!StringUtil.isEmptyString(m.get("msn"))) {
			String str = "[a-zA-Z0-9]{1,32}";
			if(!(m.get("msn").matches(str))) {
				validateBuf.append(I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.msn.format", lang));
			}
		}
		
		//Token
		String token = StringUtil.getValue(m.get("token"));
		if(StringUtil.isEmptyString(token)){
			i18nStr = I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.token", lang);
			validateBuf.append(I18nUtil.getText(
					"mainModule.preMgt.tokenMgt.import.valid.line.notEmpty", lang,
					new String[] {i18nStr}));
		}
		boolean dataExist = false;
		if (!StringUtil.isEmptyString(m.get("token"))) {
			//过滤掉-
			token=token.replaceAll("-","");
			m.put("token", token);
			//查询token对应的表
			List<Object> list = dao.queryForList(sqlId+"getMSNByToken", m);
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++) {
					Map<String,String> rMap = (Map<String, String>) list.get(i);
					String rMSN = rMap.get("MSN");
					if(msn.equals(rMSN)) {
						dataExist = true;
					} else {
						i18nStr = I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.token", lang);
						validateBuf.append(I18nUtil.getText(
								"mainModule.preMgt.tokenMgt.import.valid.line.tokenExist", lang,
								new String[] {i18nStr, token}));
					}
				}
			}
		}
		
		if (!StringUtil.isEmptyString(m.get("token"))) {
			String str = "[0-9]{20}";
			String s = m.get("token");
			//过滤掉-
			s = s.replaceAll("-", ""); 
			if(!s.matches(str)){
				i18nStr = I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.token", lang);
				validateBuf.append(I18nUtil.getText("mainModule.preMgt.tokenMgt.import.valid.token.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
				
		if(validateBuf.length() > 0) {
			reValidateBuf.append(I18nUtil.getText(
					"mainModule.preMgt.tokenMgt.import.valid.line", 
					lang, new String[]{String.valueOf(lineNo)}))
					.append(validateBuf.toString());
		} else {
			if(dataExist) {
				reValidateBuf.append("dataExist");
			}
		}
		
		return reValidateBuf.toString();
	}
	
	/**
	 *  保存Token
	 */
	public String saveTokenData(String importType, List<Object> archivesList, String lang){
		StringBuffer errBuf = new StringBuffer();	
		int errNum = 2;
		ActionResult re =new ActionResult();
		List<Object> normalLs=new ArrayList<Object>();
		List<Object> firstLs=new ArrayList<Object>();
		String czyId="";
		List<Object> ls2=new ArrayList<Object>();
		//新增Token
		if(importType.equals(Constants.IMPORT_PRE_ADD_TOKEN)){
			String tid = getTokenSequenceID(archivesList.size());
			for(int i = 0; i < archivesList.size(); i++){
				errNum = errNum + 1;
				
				Map<String, String> param = (Map<String, String>) archivesList.get(i);
				param.put("bussType", "0");
				param.put(Constants.APP_LANG, lang);
				tid = String.valueOf(Integer.parseInt(tid)+1);
				String token = param.get("token");
				//过滤掉-
				param.put("tid", tid);
				token = token.replaceAll("-", "");
				param.put("token", token);
				normalLs.add(param);
				/*List<?> ls1 = baseDAOIbatis.queryForList(sqlId + "existsMeterSort", param);
				if(ls1 != null && ls1.size()>0) {
					normalLs.add(param);
					//baseDAOIbatis.insert(sqlId + "insertTokenNormal", param);
				} else {
					firstLs.add(param);
					//baseDAOIbatis.insert(sqlId + "insertTokenFirst", param);
				}*/
				Token t = new Token();
				czyId=param.get(Constants.CURR_STAFFID);
				t.setTid(tid);
				t.setMsn(param.get("msn"));
				t.setCno(param.get("cno"));
				t.setOrderid(param.get("orderid"));
				t.setToken(param.get("token"));
				t.setSort(param.get("sort"));
				t.setUpt_date(param.get("upt_date"));
				t.setStatus(param.get("status"));
				ls2.add(t);						
			//	re = addToken(param);
			}
			baseDAOIbatis.executeBatch(sqlId + "insertTokenNormal", normalLs);
			//baseDAOIbatis.executeBatch(sqlId + "insertTokenFirst", firstLs);
			CommonUtil.saveBasicData(Constants.DATAUPDATE_DATATYPE_TOKEN, Constants.DATAUPDATE_OPTYPE_NEW, czyId, ls2, lang);			
		}
		re.setSuccess(true);
		re.setMsg("mainModule.preMgt.tokenMgt.add_success", lang);		
	
		return errBuf.toString();
    }
	
	/**
	 * 获取所有的Token
	 * @return
	 */
	@Override
	public List<Object> getAllToken() {
		List<Object> list = baseDAOIbatis.queryForList(sqlId + "getAllToken", null);
		return list;
	}
	
	/**
	 * 修改Token，包括表号、Token、执行状态
	 * @return
	 */
	public ActionResult UptToken(List<Token> tks) {
		ActionResult re = new ActionResult();
		for(int i=0; i<tks.size(); i++) {
			Token tk = tks.get(i);
			baseDAOIbatis.update(sqlId + "uptToken", tk);
		}	
		re.setSuccess(true);	
		return re;		
	}
	/**
	 * @Description获取操作员工单对应Token信息
	 * @param param
	 * @return
	 */
	public List<Object> getPARTToken(String optid) {
		List<Object> list = baseDAOIbatis.queryForList(sqlId+"getPARTToken", optid);
		return  list;
	}
	/**
	 * @Description 编辑Token获取信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getToken(Map<String, Object> param) {
		return (Map<String, Object>) baseDAOIbatis.queryForList(sqlId+"getToken", param).get(0);
	}
	
	public synchronized String getTokenSequenceID (int extendValue) {
		//获取当前序列id
		String tid = (String) baseDAOIbatis.queryForObject(sqlId+"getTid", null, String.class);
		Map<String, String> map = new HashMap<String,String>();
		String currValue = String.valueOf(Integer.parseInt(tid) + extendValue+1);
		map.put("currValue", currValue);
		baseDAOIbatis.update(sqlId + "updTokenSequence", map);//更新序列id
		return tid;
	}
}
