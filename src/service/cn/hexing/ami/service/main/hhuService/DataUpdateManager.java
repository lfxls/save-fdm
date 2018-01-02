package cn.hexing.ami.service.main.hhuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.arc.Transformer;
import cn.hexing.ami.dao.common.pojo.paramData.ParamData;
import cn.hexing.ami.dao.common.pojo.paramData.TestParam;
import cn.hexing.ami.dao.common.pojo.paramData.TestParamName;
import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.dao.main.pojo.hhuService.DataRecord;
import cn.hexing.ami.dao.main.pojo.hhuService.DataUpdateLog;
import cn.hexing.ami.dao.system.pojo.ggdmgl.Code;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
/**
 * @Description HHU数据更新服务Service
 * @author zhaoyy
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time 2016-4-12
 * @version FDM2.0
 */
public class DataUpdateManager implements DataUpdateManagerInf {
	private String sqlId = "dataUpdate.";
	private BaseDAOIbatis baseDAOIbatis;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 保存发生更新的基础数据
	 * @param dataType 数据类型：0=变压器，1=参数方案，2=基础代码，3=TOKEN
	 * @param opType 操作类型：0=新增，1=修改，2=删除
	 * @param optID 操作员ID
	 * @param dataList 数据列表
	 * @param lang 语言
	 * @return
	 */
	public ActionResult storeBasicData(String dataType, String opType, String optID, List<Object> dataList, String lang) {
		ActionResult re = new ActionResult();
		re.setSuccess(true);
		//变压器
		if(Constants.DATAUPDATE_DATATYPE_TF.equals(dataType)){
			List<DataRecord> dataRecordList = new ArrayList<DataRecord>();
			for (Object data : dataList) {
				Transformer tf = (Transformer) data;
				DataRecord dataRecord = new DataRecord(dataType, opType, optID, Constants.DATAUPDATE_DATASTAT_WAITUPT);
				//设置变压器ID
				dataRecord.setTfID(tf.getTfid());
				//设置变压器Name
				dataRecord.setTfName(tf.getTfname());
				dataRecordList.add(dataRecord);
			}
			baseDAOIbatis.executeBatch(sqlId + "updTf", dataRecordList);
			baseDAOIbatis.executeBatch(sqlId + "insTf", dataRecordList);
		}else if(Constants.DATAUPDATE_DATATYPE_PARAMSOL.equals(dataType)){//参数方案
			List<DataRecord> dataRecordList = new ArrayList<DataRecord>();
			for (Object data : dataList) {
				if(data instanceof ParamData){
					ParamData pd = (ParamData) data;
					DataRecord dataRecord = new DataRecord(dataType, opType, optID, Constants.DATAUPDATE_DATASTAT_WAITUPT);
					//设置分类编码
					dataRecord.setCateNo(pd.getCate_no());
					//设置参数项编码
					dataRecord.setObis(pd.getObis());
					//设置参数类型
					dataRecord.setPmType(pd.getPm_type());
					//设置分类名称
					dataRecord.setCate_name(pd.getCate_name());
					//设置内控版本号
					dataRecord.setVerId(pd.getVerId());
					dataRecordList.add(dataRecord);
				}else if(data instanceof TestParam){
					TestParam pd = (TestParam) data;
					DataRecord dataRecord = new DataRecord(dataType, opType, optID, Constants.DATAUPDATE_DATASTAT_WAITUPT);
					//设置分类编码
					dataRecord.setCateNo(pd.getCate_no());
					//设置参数项编码
					dataRecord.setObis(pd.getTiid());
					//设置参数类型
					dataRecord.setPmType(Constants.PMSOL_PMTYPE_TEST);
					//设置分类名称
					dataRecord.setCate_name(pd.getCate_name());
					//设置内控版本号
					dataRecord.setVerId(pd.getVerid());
					dataRecordList.add(dataRecord);
				}
			}
			baseDAOIbatis.executeBatch(sqlId + "updPs", dataRecordList);
			baseDAOIbatis.executeBatch(sqlId + "insPs", dataRecordList);
		}else if(Constants.DATAUPDATE_DATATYPE_CODE.equals(dataType)){//基础代码
			List<DataRecord> dataRecordList = new ArrayList<DataRecord>();
			for (Object data : dataList) {
				Code code = (Code) data;
				DataRecord dataRecord = new DataRecord(dataType, opType, optID, Constants.DATAUPDATE_DATASTAT_WAITUPT);
				//设置代码分类
				dataRecord.setCodeType(code.getCateCode());
				//设置老代码分类
				dataRecord.setCodeTypeO(code.getCateCodeOld());
				//设置分类名称
				dataRecord.setCateName(code.getCateName());
				//设置代码值
				dataRecord.setValue(code.getCodeValue());
				//设置老代码值
				dataRecord.setValueO(code.getCodeValueOld());
				//设置代码名
				dataRecord.setName(code.getCodeName());
				//设置语言
				dataRecord.setLang(code.getLang());
				dataRecordList.add(dataRecord);
			}
			baseDAOIbatis.executeBatch(sqlId + "updCode", dataRecordList);
			baseDAOIbatis.executeBatch(sqlId + "insCode", dataRecordList);
		}else if(Constants.DATAUPDATE_DATATYPE_TOKEN.equals(dataType)){//TOKEN
			List<DataRecord> dataRecordList = new ArrayList<DataRecord>();
			for (Object data : dataList) {
				Token token = (Token)data;
				DataRecord dataRecord = new DataRecord(dataType, opType, optID, Constants.DATAUPDATE_DATASTAT_WAITUPT);
				//TOKEN唯一标识
				dataRecord.setTokenID(token.getTid());
				//Token
				dataRecord.setToken(token.getToken());
				//表号
				dataRecord.setMsn(token.getMsn());
				dataRecordList.add(dataRecord);
			}
			baseDAOIbatis.executeBatch(sqlId + "updToken", dataRecordList);
			baseDAOIbatis.executeBatch(sqlId + "insToken", dataRecordList);
		}else{
			re.setSuccess(false);
			re.setMsg("basic.dagl.bjgl.hint.save", lang);
		}
		return re;
	}

	/**
	 * 获取需要更新的基础数据
	 * @param hhuID 掌机ID
	 * @param uptWay 更新方式
	 * @param optID 操作员ID
	 * @param reqID 请求标识
	 * @return
	 */
	public List<Object> getBasicData(String hhuID, String uptWay, String optID, String reqID) {
		List<Object> list = new ArrayList<Object>();
		
		//获取所有需要更新的基础数据
		if(Constants.DATAUPDATE_UPTWAY_FULL.equals(uptWay)){
			//获取变压器
			List<Object> tfList = CommonUtil.getAllTransformer();
			//获取读参数方案
			List<Object> psReadList = CommonUtil.getAllRead();
			//获取设置参数方案
			List<Object> psSetList = CommonUtil.getAllSet();
			//获取测试参数方案
			List<Object> psTestList = CommonUtil.getAllTest();
			//获取基础代码
			List<Object> codeList = CommonUtil.getAllCode();
			//List<Object> codeList = baseDAOIbatis.queryForList(sqlId + "getAllCode", null);
			//获取所有TOKEN
			//List<Object> tokenList = CommonUtil.getAllToken(optID);
			
			//获取操作员TOKEN
			List<Object> tokenList = CommonUtil.getPARTToken(optID);
			
			List<Object> tempList = new ArrayList<Object>();
			//将所有基础数据合到一起
			tempList.addAll(tfList);
			tempList.addAll(psReadList);
			tempList.addAll(psSetList);
			tempList.addAll(psTestList);
			tempList.addAll(codeList);
			tempList.addAll(tokenList);
			//将操作类型设置为新增
			for (Object object : tempList) {
				//变压器
				if(object instanceof Transformer){
					Transformer tf = (Transformer)object;
					tf.setOpType(Constants.DATAUPDATE_OPTYPE_NEW);
					list.add(tf);
				}else if(object instanceof ParamData){//参数方案
					ParamData pd = (ParamData)object;
					pd.setOpType(Constants.DATAUPDATE_OPTYPE_NEW);
					list.add(pd);
				}else if(object instanceof TestParam){//参数方案
					TestParam pd = (TestParam)object;
					pd.setOpType(Constants.DATAUPDATE_OPTYPE_NEW);
					list.add(pd);
				}else if(object instanceof Code){//基础数据
					Code code = (Code)object;
					code.setOpType(Constants.DATAUPDATE_OPTYPE_NEW);
					list.add(code);
				}else if(object instanceof Token){//TOKEN
					Token token = (Token)object;
					token.setOpType(Constants.DATAUPDATE_OPTYPE_NEW);
					list.add(token);
				}else{//其他
					continue;
				}
			}
		}else{
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("hhuID", hhuID);			
			param.put("dataStat", Constants.DATAUPDATE_DATASTAT_WAITUPT);
			param.put("logStat", Constants.DATAUPDATE_LOGSTAT_SUCC);
			param.put("optID", optID);
			List<Object> verLs=baseDAOIbatis.queryForList(sqlId+"getVerLs", null);
			List<Object> psReadList=new ArrayList<Object>();
			List<Object> psSetList=new ArrayList<Object>();
			List<Object> psTestList=new ArrayList<Object>();
			for(int i=0;i<verLs.size();i++){
				//获取读参数方案
				param.put("pmType", Constants.PMSOL_PMTYPE_READ);
				
				param.put("M_VERID", ((Map<String,String>)verLs.get(i)).get("M_VERID"));
				param.put("VERID", ((Map<String,String>)verLs.get(i)).get("VERID"));
				List<Object> readList = baseDAOIbatis.queryForList(sqlId + "getPsRead", param);
				List<ParamData>readLs= CommonUtil.getParamDataLs(readList, param);
				//获取设置参数方案
				param.put("pmType", Constants.PMSOL_PMTYPE_SET);
				List<Object> setList = baseDAOIbatis.queryForList(sqlId + "getPsSet", param);
				List<ParamData>setLs= CommonUtil.getParamDataLs(setList, param);
				//获取测试参数方案
				param.put("pmType", Constants.PMSOL_PMTYPE_TEST);
				List<Object> testList = baseDAOIbatis.queryForList(sqlId + "getPsTest", param);
				List<Object> testParamLs=new ArrayList<Object>();
				
				
				 for(int j=0;j<testList.size();j++){
					 List<ParamData> paramDataLs=new ArrayList<ParamData>();
					 List<TestParamName> testParamNameLs=new ArrayList<TestParamName>();
					 HashMap<String,Object> map=new HashMap<String, Object>();
					 map=(HashMap<String, Object>) testList.get(j);
					
					 List<Object> paramLs=baseDAOIbatis.queryForList(sqlId+"getAllTest", map);
					 List<Object> nameLs=baseDAOIbatis.queryForList(sqlId+"getTiName", map);
					 for(int n=0;n<nameLs.size();n++){
						 testParamNameLs.add((TestParamName)nameLs.get(n));
					 }
				
					 paramDataLs=CommonUtil.getParamDataLs(paramLs,map);
					 TestParam testParam=new TestParam();
					 testParam.setOpID((String)map.get("OPID"));
					 testParam.setCate_no((String)map.get("CATE_NO"));
					 testParam.setM_model((String)map.get("MODEL"));
					 testParam.setVerid((String)map.get("VERID"));
					 testParam.setOpType((String)map.get("OPTYPE"));
					 if(!"2".equals((String)map.get("OPTYPE"))){
						 testParam.setLevel((String)map.get("LEVEL"));
						 testParam.setStatus((String)map.get("STATUS"));
						 testParam.setSort(StringUtil.getValue(map.get("SORT")));
					 }
					 testParam.setTiid((String)map.get("TIID"));
					 testParam.setParamList(paramDataLs);
					 testParam.setTestParamNameList(testParamNameLs);
					 testParamLs.add(testParam);
				 }
				
				
				//List<ParamData>testLs= CommonUtil.getParamDataLs(testList, param);
				psReadList.addAll(readLs);
				psSetList.addAll(setLs);
				psTestList.addAll(testParamLs);
			}
			//获取变压器
			List<Object> tfList = baseDAOIbatis.queryForList(sqlId + "getTf", param);
			//获取基础代码
			List<Object> codeList = baseDAOIbatis.queryForList(sqlId + "getCode", param);
			//获取TOKEN
			List<Object> tokenList = baseDAOIbatis.queryForList(sqlId + "getToken", param);
			
			//将所有基础数据合到一起
			list.addAll(tfList);
			list.addAll(psReadList);
			list.addAll(psSetList);
			list.addAll(psTestList);
			list.addAll(codeList);
			list.addAll(tokenList);
		}
		
		return list;
	}

	/**
	 * 保存基础数据操作日志
	 * @param hhuID 掌机ID
	 * @param uptWay 更新方式
	 * @param optID 操作员ID
	 * @param reqID 请求标识
	 * @param basicDataList 基础数据列表
	 * @return
	 */
	public ActionResult storeBasicDataLog(String hhuID, String uptWay, String optID, String reqID, List<Object> basicDataList) {
		ActionResult re = new ActionResult();
		re.setSuccess(true);
		//记录操作日志(不管是否存在基础数据，都新增一条日志，用于接口中验证掌机请求id是否正确)
		DataUpdateLog dataUpdateLog = new DataUpdateLog(reqID, optID, hhuID, uptWay, Constants.DATAUPDATE_LOGSTAT_UNKNOW, "");
		String logID = (String)baseDAOIbatis.insert(sqlId + "insLog", dataUpdateLog);
		//如果存在更新的基础数据，保存操作日志
		if(basicDataList != null && basicDataList.size() > 0){	
			if(Constants.DATAUPDATE_UPTWAY_INC.equals(uptWay)){
				//记录日志对应的所有操作
				List<DataUpdateLog> logOpList = new ArrayList<DataUpdateLog>();
				for (Object object : basicDataList) {
					DataUpdateLog logOp = new DataUpdateLog();
					logOp.setLogID(logID);
					//变压器
					if(object instanceof Transformer){
						Transformer tf = (Transformer)object;
						logOp.setOpID(tf.getOpID());
						logOp.setDataType(Constants.DATAUPDATE_DATATYPE_TF);
					}else if(object instanceof ParamData){//参数方案
						ParamData pd = (ParamData)object;
						logOp.setOpID(pd.getOpID());
						logOp.setDataType(Constants.DATAUPDATE_DATATYPE_PARAMSOL);
					}else if(object instanceof TestParam){//参数方案
						TestParam pd = (TestParam)object;
						logOp.setOpID(pd.getOpID());
						logOp.setDataType(Constants.DATAUPDATE_DATATYPE_PARAMSOL);
					}else if(object instanceof Code){//基础数据
						Code code = (Code)object;
						logOp.setOpID(code.getOpID());
						logOp.setDataType(Constants.DATAUPDATE_DATATYPE_CODE);
					}else if(object instanceof Token){//TOKEN
						Token token = (Token)object;
						logOp.setOpID(token.getOpID());
						logOp.setDataType(Constants.DATAUPDATE_DATATYPE_TOKEN);
					}else{//其他
						continue;
					}
					logOpList.add(logOp);
				}
				baseDAOIbatis.executeBatch(sqlId + "insLogOp", logOpList);
			}
		}
		return re;
	}

	/**
	 * 更新基础数据日志状态
	 * @param hhuID 掌机ID
	 * @param reqID 请求标识
	 * @param uptRst 更新结果：0=未知，1=成功，2=失败
	 * 
	 * @param errMsg 错误信息
	 * @return
	 */
	public ActionResult uptBasicDataLogSts(String hhuID, String reqID, String uptRst, String errMsg) {
		ActionResult re = new ActionResult();
		re.setSuccess(true);
		Map<String, String> param = new HashMap<String, String>();
		param.put("hhuID", hhuID);
		param.put("reqID", reqID);
		param.put("uptRst", uptRst);
		param.put("errMsg", errMsg);
		baseDAOIbatis.update(sqlId + "updLogStat", param);
		return re;
	}
	
	@Override
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		// TODO Auto-generated method stub

	}
}
