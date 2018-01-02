package cn.hexing.ami.service.main.arcMgt;

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
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
public class DataImpManager implements DataImpManagerInf {
	private static Logger logger = Logger.getLogger(DataImpManager.class);
	//校验提示信息
	private static final String RESULT_TYPE_VALIDATE = "01";
	//导入结果提示信息
	private static final String RESULT_TYPE_IMPORT = "02";
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId="dataImp.";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	@Override
	public ActionResult parseExcel(FileInputStream fis,
			Map<String, String> param, String archivesType, String lang) {
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
			int cellNum = 0;
			for (int i = 0; i < rowOne.length; i++) {
				if(!StringUtil.getValue(rowOne[i].getContents()).equals("")){
					cellNum++;
				}
			}
			
			//正确的档案列数
			int daCellNum = DataImpUtil.getExcelField(archivesType).length;
			
			//判断列数是否合法
			if (cellNum!=daCellNum) {
				result.setSuccess(false);
				result.setMsg("mainModule.arcMgt.dataImp.validate.formatErr",lang);
				result.setDataObject(RESULT_TYPE_VALIDATE);
				return result;
			}
			
			int errNum = 1;
			int notNullNum =0;
			for (int i = 1; i < rowNum; i++) {
				Cell[] row = sheet.getRow(i);
				Cell[] rowNew = new Cell[daCellNum];
				//最后N列有空的情况，补空值
				for (int j = 0; j < row.length; j++) {
					rowNew[j]=row[j];
				}
				boolean allCellNull = true;
				for (int j = 0; j < rowNew.length; j++) {
					if (!StringUtil.getValue(rowNew[j]==null?"":rowNew[j].getContents()).equals("")) {
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
				Map<String, String> m = CommonUtil.cell2Map(rowNew, DataImpUtil.getExcelField(archivesType));
			
				//行数据校验5
				ActionResult rowResult = validateData(archivesType,r,m,lang);
				if (rowResult.isSuccess()) {
					archivesList.add(m);
				}else{
					validateBuf.append((errNum++)+"."+rowResult.getMsg()+"<br>");
				}
			}			
			//没有数据的情况
			if (notNullNum==0) {
				result.setSuccess(false);
				result.setMsg("mainModule.arcMgt.dataImp.import.empty",lang);
				return result;
			}

			//没有错误，数据入库
			if (validateBuf.toString() == null || "".equals(validateBuf.toString())) {
				String errMsg = saveArchivesData(archivesType,archivesList,lang);
				//导入存在错误的情况
				if (errMsg!=null && !"".equals(errMsg)) {
					result.setSuccess(false);
					//导入保存错误信息
					result.setDataObject(RESULT_TYPE_IMPORT);
					result.setMsg(errMsg);
				}else{
					result.setSuccess(true);
					result.setMsg("mainModule.arcMgt.dataImp.import.success",lang);
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
			String errrMsg = DataImpUtil.getErrMsg(archivesType,lang);
			logger.error(errrMsg+":"+StringUtil.getExceptionDetailInfo(e));
			result.setSuccess(false);
			result.setMsg(errrMsg);
			return result;
		}finally{
			if (workbook!=null) {
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
	@Override
	public String saveArchivesData(String archivesType,
			List<Object> archivesList, String lang) {
		StringBuffer errBuf = new StringBuffer();
		
		
		//GPRS表
		if ( Constants.DA_BJ_GPRS.equals(archivesType)) {
			MeterMgtManagerInf meterMgtManager= (MeterMgtManagerInf)SpringContextUtil.getBean("meterMgtManager");
			int errNum = 1;
			for (int i = 0; i < archivesList.size(); i++) {
				Map<String, String> param = (Map<String, String>)archivesList.get(i);
				param.put(Constants.APP_LANG, lang);
				param.put("newMeter", "new");
				param.put("dataImp", "1");
				param.put("dataSrc", "1");
				ActionResult tmpResult=new ActionResult();
//				try {
					 tmpResult = meterMgtManager.insMeter(param);
//				} catch (Exception e) {
//					logger.error("Meter Import Error:"+StringUtil.getExceptionDetailInfo(e));
//				}
				if (!tmpResult.isSuccess()) {
					String tmpStr = I18nUtil.getText("mainModule.arcMgt.dataImp.saveArchivesData.meter.err", lang,new String[]{param.get("msn"),tmpResult.getMsg()});
					errBuf.append((errNum++)+tmpStr);
					logger.equals(tmpStr);
				}
			}
		}
		
		
	

		//用户
		if (Constants.DA_YH.equals(archivesType)) {
			//导入之前判断数据主键有没有重复的
			int errNumSame = 1;
			for(int i = 0; i < archivesList.size() - 1; i++){
				Map<String, String> preMap = (Map<String, String>)archivesList.get(i);
				String preCno = StringUtil.getValue(preMap.get("cno"));
				
				for(int j = i + 1; j < archivesList.size(); j++){
					Map<String, String> nextMap = (Map<String, String>)archivesList.get(j);
					String nextCno = StringUtil.getValue(nextMap.get("cno"));
					
					if(nextCno.equals(preCno)){
						String temp = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.excel.hh.data.same", lang, new String[]{preCno});
						String bjjhSameErr = I18nUtil.getText("mainModule.arcMgt.dataImp.saveArchivesData.yhda.err", lang, new String[]{preCno, temp});
					    errBuf.append((errNumSame++) + bjjhSameErr);
//					    logger.equals(bjjhSameErr);
						return errBuf.toString();
						
					} else {
						continue;
					}
				}
			}
			
			CustMgtManagerInf  custMgtManager= (CustMgtManagerInf)SpringContextUtil.getBean("custMgtManager");
			int errNum = 1;
			for (int i = 0; i < archivesList.size(); i++) {
				Map<String, String> param = (Map<String, String>)archivesList.get(i);
				param.put(Constants.APP_LANG, lang);
				param.put("dataImp", "1");
				param.put("dataSrc", "1");
				param.put("custStatus", "0");
				ActionResult tmpResult=new ActionResult();
//				try {
					 tmpResult =custMgtManager.insCust(param);
//				} catch (Exception e) {
//					logger.error("Customer Import Error:"+StringUtil.getExceptionDetailInfo(e));
//				}
				if (!tmpResult.isSuccess()) {
					String tmpStr = I18nUtil.getText("mainModule.arcMgt.dataImp.saveArchivesData.yhda.err", lang,new String[]{param.get("hh"),tmpResult.getMsg()});
					errBuf.append((errNum++)+tmpStr);
					logger.equals(tmpStr);
				}
			}
		}
		return errBuf.toString();
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
		String alidateStr = "";
		//GPRS表
		if (Constants.DA_BJ_GPRS.equals(archivesType)) {
			alidateStr = DataImpUtil.validateMeterGPRS(lineNo, m,lang);
		}
		
		//用户档案
		if (Constants.DA_YH.equals(archivesType)) {
			alidateStr = DataImpUtil.validateCust(lineNo, m,lang);
		}
		
		if (!alidateStr.equals("")) {
			result.setSuccess(false);
			result.setMsg(alidateStr);
		}else{
			result.setSuccess(true);
		}
		return result;
	}
	public List<Object> getCode(String codeType,String value){
		
		HashMap<String, String> param=new HashMap<String, String>();
		param.put("codeType", codeType);
		param.put("value", value);
		return baseDAOIbatis.queryForList(sqlId+"getCode", param);
	}
}
