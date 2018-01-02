package cn.hexing.ami.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.dao.common.pojo.paramData.ParamData;
import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.dao.common.pojo.sms.InMsg;
import cn.hexing.ami.dao.common.pojo.sms.OutMsg;
import cn.hexing.ami.dao.main.pojo.insMgt.WorkOrder;
import cn.hexing.ami.service.CommonManager;
import cn.hexing.ami.service.license.LicMgtManagerInf;
import cn.hexing.ami.service.main.arcMgt.CustMgtManagerInf;
import cn.hexing.ami.service.main.arcMgt.HhuMgtManagerInf;
import cn.hexing.ami.service.main.arcMgt.TransfMgtManagerInf;
import cn.hexing.ami.service.main.hhuService.DataUpdateManagerInf;
import cn.hexing.ami.service.main.insMgt.InsFbManagerInf;
import cn.hexing.ami.service.main.insMgt.InsOrderManagerInf;
import cn.hexing.ami.service.main.insMgt.InsPlanManagerInf;
import cn.hexing.ami.service.main.preMgt.TokenMgtManagerInf;
import cn.hexing.ami.service.main.srvyMgt.SrvyFbManagerInf;
import cn.hexing.ami.service.main.srvyMgt.SurPlanManagerInf;
import cn.hexing.ami.service.report.insInfoReport.InsNumRepManagerInf;
import cn.hexing.ami.service.sms.SmsServerServiceInf;
import cn.hexing.ami.service.system.ggdmgl.DmglManagerInf;
import cn.hexing.ami.service.system.paramData.ParamMgtManagerInf;
import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 公用代码工具栏
 * @author jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-4-10
 * @version AMI3.0
 */
public class CommonUtil {
	private static Logger logger = Logger.getLogger(CommonUtil.class.getName());
	/**
	 * 获取对应类型的代码列表
	 * 
	 * @param codeType
	 * @param lang
	 * @param allOption
	 *            true表示自动增加一个请选择项
	 * @return
	 */
	public static List<Object> getCode(String codeType, String lang,
			boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getCode(codeType, lang, allOption,null,"03");
	}
	/**
	 * 获取对应类型的任务属性
	 * 
	 * @param gylx
	 * @param lang
	 * @param allOption
	 *            true表示自动增加一个请选择项
	 * @return
	 */
	public static List<Object> getRwsx(String gylx, String lang,
			boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getRwsx(gylx, lang, allOption);
	}
	
	/**
	 * 获取对应类型的代码列表 值数字类型，升序排序
	 * 
	 * @param codeType
	 * @param lang
	 * @param allOption
	 *            true表示自动增加一个请选择项
	 * @param orderType DESC 倒序
	 *                  ASC  正序 默认正序
	 *        
	 * @return
	 */
	public static List<Object> getCodeNumber(String codeType, String lang,String orderType,
			boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getCode(codeType, lang, allOption,orderType,"02");
	}
	
	/**
	 * 获取对应类型的代码列表 按disp_sn排序
	 * 
	 * @param codeType
	 * @param lang
	 * @param allOption
	 *            true表示自动增加一个请选择项
	 * @param orderType DESC 倒序
	 *                  ASC  正序 默认正序
	 *        
	 * @return
	 */
	public static List<Object> getPCodeOrderBySN(String codeType, String lang,String orderType,
			boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getCode(codeType, lang, allOption,orderType,"03");
	}
	
	
	/**
	 * 获取对应类型的代码列表
	 * 
	 * @param codeType
	 * @param allOption
	 *            true表示自动增加一个请选择项
	 * @return
	 */
	public static List<Object> getCodeNoLoale(String codeType, boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getCode(codeType, null, allOption,null,"01");
	}
	
	/**
	 * 获取对应类型的代码列表
	 * @param codeType
	 * @param lang
	 * @param orderType
	 * @param allOption
	 *  		true表示自动增加一个请选择项
	 * @return
	 */
	public static List<Object> getCodeLoale(String codeType, String lang, String orderType, boolean allOption) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getCode(codeType, lang, allOption, orderType, "01");
	}

	/**
	 * 获取系统配置
	 * 
	 * @param paraId
	 * @return
	 */
	public static String getPSys(String paraId) {
		CommonManager commonManager = (CommonManager) SpringContextUtil
				.getBean("commonManager");
		return commonManager.getPSys(paraId);
	}

	/**
	 * 获取异常的完整信息
	 * 
	 * @param ex
	 * @return
	 */
	public static String getExceptionDetailInfo(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	public static void map2Obj(Map<String, String> m, Object bean) {
		try {
			if (m != null && !m.isEmpty()) {
				Field[] destFields = bean.getClass().getDeclaredFields();
				AccessibleObject.setAccessible(destFields, true);
				Iterator<String> it = m.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					if (!(m.get(key) instanceof String)) {
						continue;
					}
					String value = m.get(key);
					if (!StringUtil.isEmptyString(value)) {
						for (Field fd : destFields) {
							String fdName = fd.getName();
							if (key.toUpperCase().equals(fdName.toUpperCase())) {
								if (fd.getType().equals(String.class)) {
									fd.set(bean, value);
								}
								if (fd.getType().equals(Integer.class)) {
									fd.setInt(bean, Integer.parseInt(value));
								}
								if (fd.getType().equals(Long.class)) {
									fd.setLong(bean, Long.parseLong(value));
								}
								if (fd.getType().equals(Double.class)) {
									fd.setDouble(bean, Double
											.parseDouble(value));
								}
								if (fd.getType().equals(java.util.Date.class)) {
									java.util.Date d = null;
									if(fdName.toUpperCase().endsWith("SJ")){
										d = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",value);
									} else if(fdName.toUpperCase().endsWith("RQ")) {
										d = DateUtil.convertStringToDate("yyyy-MM-dd",value);
									}
									fd.set(bean, d);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		} 
	}

	public static String obj2Json(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateProcessor()); 
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonDateProcessor()); 
		JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
		String json = jsonObject.toString();
		return json;
	}
	
	/**
	 * 通过hibernate的UUID产生主键(32位)
	 * @return
	 */
	public static String generateID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-","");
	}
	
	/**
	 * 获取当前用户的ip
	 * @param reqest
	 * @return
	 */
	public static String getCurrentUserIP(HttpServletRequest request) {
		// 取用户IP
		// return getRequest().getRemoteAddr();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
	
	public static Map<String, String> cell2Map(Cell[] row, String[] head)
			throws Exception {
		Map<String, String> m = new HashMap<String, String>();
		try {
			for (int i = 0, k = row.length; i < k; i++) {
				String v = StringUtil.getValue(row[i]==null?"":row[i].getContents());
				m.put(head[i], v.trim());
			}
		} catch (Exception e) {
			throw e;
		}
		return m;
	}
	
	/**
	 * 判断返回值是否为基本类型
	 */
	public static boolean isPrimitive(Object obj){
		String typeName = obj.getClass().getSimpleName().toUpperCase();
		if(typeName.equals("INTEGER") || typeName.equals("DOUBLE") || typeName.equals("FLOAT") || typeName.equals("LONG") 
				|| typeName.equals("SHORT") || typeName.equals("BOOLEAN") || typeName.equals("BYTE") || typeName.equals("CHAR")
				|| typeName.equals("STRING")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 生成导航条
	 * @param lst
	 * @return
	 */
	public static String getNavibar(List<Object> lst){
		if(lst.isEmpty()){
			return "";
		}

		String bar = "";
		for(int i = 0; i < lst.size(); i++){
			HashMap map = (HashMap)lst.get(i);
			if(i > 0){
				bar += "&nbsp;&nbsp;>";
			}
			bar += "&nbsp;&nbsp;<a href=\'#\' onclick=\'subQuery(\""+map.get("DWDM")+"\",\""+map.get("JB")+"\")\'>" +map.get("DWMC")+ "</a>";
		}
		return bar;
	}
	
	/**
	 * 生成导航条
	 * @param lst
	 * @return
	 */
	public static String getNavibarTj(List<Object> lst){
		if(lst.isEmpty()){
			return "";
		}

		String bar = "";
		for(int i = 0; i < lst.size(); i++){
			HashMap map = (HashMap)lst.get(i);
			if(i > 0){
				bar += "&nbsp;&nbsp;>";
			}
			bar += "&nbsp;&nbsp;<a href=\'#\' onclick=\'subQuery(\""+map.get("DWDM")+"\",\""+map.get("JB")+"\",\""+map.get("DWMC")+"\")\'>" +map.get("DWMC")+ "</a>";
		}
		return bar;
	}
	
	/**
	 * 解析系统license文件
	 * @return
	 */
	public static ActionResult resolveLicense(){
		LicMgtManagerInf licMgtManager = (LicMgtManagerInf)SpringContextUtil.getBean("licMgtManager");
		return licMgtManager.resolveLicense();
	}
	
	/**
	 * 解析系统license文件
	 * @return
	 */
	public static boolean validateMachInfo(License license){
		LicMgtManagerInf licMgtManager = (LicMgtManagerInf)SpringContextUtil.getBean("licMgtManager");
		return licMgtManager.validateMachInfo(license);
	}
	
	/**
	 * 获取系统模式
	 * @return
	 */
	public static String getSystemMode(){
		String mode = Constants.SYSTEM_MODE_N;
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			mode = sysMap.get("sysMode");
			mode = (mode==null || "".equals(mode))?Constants.SYSTEM_MODE_N:mode;
		}
		return mode;
	}
	
	/**
	 * 获取系统模式
	 * @return
	 */
	public static String getDatabaseType(){
		String mode = Constants.SYSTEM_MODE_N;
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			mode = sysMap.get("databaseType");
			mode = (mode==null || "".equals(mode))?Constants.DATABASETYPE_ORACLE:mode;
		}
		return mode;
	}
	/**
	 * 获取是否支持预付费
	 * @return
	 */
	public static boolean isSupportPrepay(){
		boolean isSupportPrepay = false;
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			isSupportPrepay = Boolean.parseBoolean(sysMap.get("prepayMode"));
		}
		return isSupportPrepay;
	}
	
	/**
	 * 从系统配置文件中获取支持的规约类型
	 * @param gyLs 数据库中的规约类型
	 * @return
	 */
	public static List<Object> getSupportProtocol(List<Object> gyLs) {
		Map<String, String> sysMap = (Map<String, String>) AppEnv.getObject(Constants.SYS_PARAMMAP);
		//支持的规约类型
		String[] supportProtocol = sysMap.get("supportProtocol").split(",");
		
		for (Iterator<Object> iter = gyLs.iterator(); iter.hasNext(); ) {
			Map<String, String> gy = (Map<String, String>) iter.next();
			//是否存在
			boolean isExist = false;
			for (int i = 0; i < supportProtocol.length; i++) {
				if (gy.get("BM").equals(supportProtocol[i]) || gy.get("BM").equals("")) {
					isExist = true;
					break;
				}
			}
			//如果不存在，则移除
			if(!isExist) iter.remove();
		}
		return gyLs;
	}
	
	/**
	 * 发送短信
	 * @param outMsg
	 * @param lang
	 * @return
	 */
	public static ActionResult sendMessage(OutMsg outMsg, String lang){
		SmsServerServiceInf smsServerService = (SmsServerServiceInf)SpringContextUtil.getBean("smsServerService");
		return smsServerService.sendMessage(outMsg, lang);
	}
	
	/**
	 * 解析返回短信内容
	 * @param msgId
	 */
	public static void processMessage(String msgId){
		SmsServerServiceInf smsServerService = (SmsServerServiceInf) SpringContextUtil.getBean("smsServerService");
		smsServerService.processMessage(msgId);
	}
	
	/**
	 * 读取所有未处理短信
	 * @return
	 */
	public static List<InMsg> readMessages(){
		SmsServerServiceInf smsServerService = (SmsServerServiceInf) SpringContextUtil.getBean("smsServerService");
		return smsServerService.readMessages();
	}
	
	/**
	 * 获取app.properties文件中的配置项值
	 * @param item
	 * @return
	 */
	public static String getAppConfigItemValue(String item){
		String itemValue = "";
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		if (sysMap!=null) {
			itemValue = sysMap.get(item);
		}
		sysMap = null;
		return itemValue;
	}
	
	/**
	 * 多系统模式下，获取当前系统ID
	 * @return
	 */
	public static String getMultiSystemID(){
		return CommonUtil.getAppConfigItemValue("Multi-systemID"); 
	}
	
	/**
	 * 获取需要更新的基础数据
	 * @param hhuID 掌机ID
	 * @param uptWay 更新方式
	 * @param optID 操作员ID
	 * @param reqID 请求标识
	 * @return
	 */
	public static List<Object> getBasicData(String hhuID, String uptWay, String optID, String reqID){
		DataUpdateManagerInf hhuServiceManager = (DataUpdateManagerInf) SpringContextUtil.getBean("hhuServiceManager");
		return hhuServiceManager.getBasicData(hhuID, uptWay, optID, reqID);
	}
	
	/**
	 * 保存发生更新的基础数据
	 * @param dataType 数据类型：0=变压器，1=参数方案，2=基础代码
	 * @param opType 操作类型：0=新增，1=修改，2=删除
	 * @param optID 操作员ID
	 * @param dataList 数据列表
	 * @param lang 语言
	 * @return
	 */
	public static ActionResult saveBasicData(String dataType, String opType, String optID, List<Object> dataList, String lang){
		DataUpdateManagerInf hhuServiceManager = (DataUpdateManagerInf) SpringContextUtil.getBean("hhuServiceManager");
		return hhuServiceManager.storeBasicData(dataType, opType, optID, dataList, lang);
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
	public static ActionResult saveBasicDataLog(String hhuID, String uptWay, String optID, String reqID, List<Object> basicDataList) {
		DataUpdateManagerInf hhuServiceManager = (DataUpdateManagerInf) SpringContextUtil.getBean("hhuServiceManager");
		return hhuServiceManager.storeBasicDataLog(hhuID, uptWay, optID, reqID, basicDataList);
	}
	
	/**
	 * 更新基础数据日志状态
	 * @param hhuID 掌机ID
	 * @param reqID 请求标识
	 * @param uptRst 更新结果：0=未知，1=成功，2=失败
	 * @param errMsg 错误信息
	 * @return
	 */
	public static ActionResult uptBasicDataLogSts(String hhuID, String reqID, String uptRst, String errMsg){
		DataUpdateManagerInf hhuServiceManager = (DataUpdateManagerInf) SpringContextUtil.getBean("hhuServiceManager");
		return hhuServiceManager.uptBasicDataLogSts(hhuID, reqID, uptRst, errMsg);
	}
	/**
	 * 查询所有的读取参数项
	 * @return
	 */
	public static List<Object> getAllRead(){
		ParamMgtManagerInf paramMgtManager=(ParamMgtManagerInf) SpringContextUtil.getBean("paramMgtManager");
		return paramMgtManager.getAllRead();
	}
	/**
	 * 查询所有的设置参数项
	 * @return
	 */
	public static List<Object> getAllSet(){
		ParamMgtManagerInf paramMgtManager=(ParamMgtManagerInf) SpringContextUtil.getBean("paramMgtManager");
		return paramMgtManager.getAllSet();
	}
	/**
	 * 查询所有的测试参数项
	 * @return 
	 */
	public static List<Object> getAllTest(){
		ParamMgtManagerInf paramMgtManager=(ParamMgtManagerInf) SpringContextUtil.getBean("paramMgtManager");
		return paramMgtManager.getAllTest();
	}
	public static List<ParamData> getParamDataLs( List<Object> paramLs, HashMap<String,Object> map){
		ParamMgtManagerInf paramMgtManager=(ParamMgtManagerInf) SpringContextUtil.getBean("paramMgtManager");
		return paramMgtManager.getParamDataLs(paramLs, map);
	}
	
	
	/**
	 * 根据HHUID查询数据是否初始化
	 * @return
	 */
	public static String getHhuInit(String hhuID){
		HhuMgtManagerInf hhuMgtManager = (HhuMgtManagerInf) SpringContextUtil.getBean("hhuMgtManager");
		return hhuMgtManager.getHhuInit(hhuID);
	}
	
	/**
	 * 下载完基础数据，数据初始化状态从No改成Yes
	 * @return
	 */
	public static void setHhuInit(String hhuID){
		HhuMgtManagerInf hhuMgtManager = (HhuMgtManagerInf)SpringContextUtil.getBean("hhuMgtManager");
		hhuMgtManager.setHhuInit(hhuID);
	}
	/**
	 * 获得所有的变压器
	 * @return
	 */
	public static List<Object> getAllTransformer(){
		TransfMgtManagerInf transfMgtManager = (TransfMgtManagerInf) SpringContextUtil.getBean("transfMgtManager");
		return transfMgtManager.getAllTransformer();
	}
	
	/**
	 * 获取操作人对应的已分配和待撤销工单数据
	 * @param optID 操作员ID
	 * @param uptWay 更新方式 0=全量，1=增量
	 * @return
	 */
	public static List<Object> getDownLoadWO(String optID, String uptWay){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.getDownLoadWO(optID,uptWay);
	}
	
	/**
	 * 获取操作人对应的下载反馈工单数据
	 * @param hhuID 掌机ID
	 * @param reqID 请求标识
	 * @param optID 操作员ID
	 * @return
	 */
	public static List<Object> getWOFromLog(String hhuID, String reqID, String optID){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.getWOFromLog(hhuID, reqID, optID);
	}
	
	/**
	 * 根据工单ID获取工单信息
	 * @param woid
	 * @return
	 */
	public static WorkOrder getWOInfo(String woid) {
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.getWOInfo(woid);
	}
	
	/**
	 * 获取工单标识序列id
	 * @return
	 */
	public static String getWOID() {
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return "W" + StringUtil.leftZero(insOrderManager.getWOID(),14);
	}
	
	/**
	 * 更新工单状态
	 * @param paramList 工单状态列表
	 * @return
	 */
	public static ActionResult updWOStatus(List<Object> paramList){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.updWOStatus(paramList);
	}
	
	/**
	 * 更新HHU操作工单日志状态
	 * @param hhuID 掌机ID
	 * @param reqID 请求ID
	 * @param optID 操作员ID
	 * @param optRst 操作结果
	 * @param errMsg 错误信息
	 * @return
	 */
	public static ActionResult updWOLog(String hhuID,String reqID,String optID,String optRst,String errMsg){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.updWOLog(hhuID, reqID, optID, optRst, errMsg);
	}
	
	/**
	 * 保存工单
	 * @param woList 工单号列表
	 * @param insPlnMap 安装计划对象map
	 * @return
	 */
	public static ActionResult storeWO(List<Object> woList, List<Object> insPlnList){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.storeWO(woList,insPlnList);
	}
	
	/**
	 * 保存HHU操作工单日志
	 * @param hhuID 掌机ID
	 * @param reqID 请求ID
	 * @param optID 操作员ID
	 * @param optRst 操作结果
	 * @param errMsg 错误信息
	 * @param optType 操作类型 0=上传，1=下载
	 * @param woList 工单列表
	 * @param uptWay 更新方式
	 * @return
	 */
	public static ActionResult storeWOLog(String hhuID,String optID,String reqID, String rst,String errMsg, String optType, List<Object> woList, String uptWay){
		InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.stroeWOLog(hhuID, reqID, optID, rst, errMsg, optType, woList, uptWay);
	}
	
	/**
	 * 获取工单下的表安装计划
	 * @param woIDList 工单ID列表
	 * @return
	 */
	public static List<Object> getMeterPln(List<Object> woIDList){
		InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.getMeterPln(woIDList);
	}
	
	/**
	 * 获取工单下指定状态的安装计划数量（表，集中器，采集器）
	 * @param woid
	 * @param status
	 * @return
	 */
	public static Integer getInsPlnCount(String woid,String[] status) {
		InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.getInsPlnCount(woid,status);
	}
	
	/**
	 * 更新安装计划状态
	 * @param paramList 安装计划状态列表
	 * @return
	 */
	public static ActionResult updPlnStatus(List<Object> paramList){
		InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.updPlnStatus(paramList);
	}
	
	/**
     * 根据安装计划id获取对应的表安装计划
     * @param pid 安装计划id
     * @return
     */
    public static List<Object> getMeterPlnByPid(String pid) {
    	InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.getMeterPlnByPid(pid);
    }
	
	/**
	 * 保存安装计划反馈数据（不包含测试反馈数据）
	 * @param paramList 安装计划反馈数据列表
	 * @return
	 */
	public static ActionResult storeMeterFBBasicData(List<Object> paramList){
		InsFbManagerInf insFbManager = (InsFbManagerInf) SpringContextUtil.getBean("insFbManager");
		return insFbManager.storeMeterFBBasicData(paramList);
	}
	
	/**
	 * 保存安装计划测试反馈数据（读取类，设置类，测试类）
	 * @param setList 安装计划设置反馈数据列表
	 * @param readList 安装计划读取反馈数据列表
	 * @param testPMMap 安装计划测试反馈数据map对象
	 * @return
	 */
	public static ActionResult storeMeterFBTestData(List<Object> setList, List<Object> readList, 
			Map<String,List<Object>> testPMMap){
		InsFbManagerInf insFbManager = (InsFbManagerInf) SpringContextUtil.getBean("insFbManager");
		return insFbManager.storeMeterFBTestData(setList,readList,testPMMap);
	}
	/**
	 * 获取所有的Token
	 * @return
	 */
	public static List<Object> getAllToken(){
		TokenMgtManagerInf tokenManager = (TokenMgtManagerInf) SpringContextUtil.getBean("tokenMgtManager");
		return tokenManager.getAllToken();
	}
	/**
	 * 获取操作员所有的Token
	 * @return
	 */
	public static List<Object> getPARTToken( String optid){
		TokenMgtManagerInf tokenManager = (TokenMgtManagerInf) SpringContextUtil.getBean("tokenMgtManager");
		return tokenManager.getPARTToken(optid);
	}
	/**
	 * 获取所有的基础代码
	 * @return
	 */
	public static List<Object> getAllCode(){
		DmglManagerInf dmglManager = (DmglManagerInf) SpringContextUtil.getBean("dmglManager");
		return dmglManager.getAllCode();
	}
	/**
	 * 更新Token状态
	 * @return
	 */
	public static ActionResult UptToken(List<Token> tks){
		TokenMgtManagerInf tokenManager = (TokenMgtManagerInf) SpringContextUtil.getBean("tokenMgtManager");
		return tokenManager.UptToken(tks);
	}
	
	 /**
     * 获取变压器id下所有已派工的安装计划
     * @param tfid
     * @return
     */
    public static List<Object> getMeterPlnByTfId(String tfid) {
    	InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.getMeterPlnByTfId(tfid);
    }
    
    /**
     * 保存工单操作日志
     * @param param
     * @return
     */
    public static ActionResult storeWOOPLog(List<Object> param) {
    	InsOrderManagerInf insOrderManager = (InsOrderManagerInf) SpringContextUtil.getBean("insOrderManager");
		return insOrderManager.storeWOOPLog(param);
    }
    
    /**
     * 保存安装计划操作日志
     * @param param
     * @return
     */
    public static ActionResult storePlanOPLog(List<Object> param) {
    	InsPlanManagerInf insPlanManager = (InsPlanManagerInf) SpringContextUtil.getBean("insPlanManager");
		return insPlanManager.storePlanOPLog(param);
    }
    
    /**
	 * 批量更新用户是否派工状态
	 * @param paramList
	 */
	public static ActionResult updCustDispStatus(List<Object> paramList) {
		CustMgtManagerInf custMgtManager = (CustMgtManagerInf) SpringContextUtil.getBean("custMgtManager");
		return custMgtManager.updCustDispStatus(paramList);
	}
	
	public static ActionResult addNumRep(List<Object> dateList) {
		InsNumRepManagerInf insNumRepManager = (InsNumRepManagerInf) SpringContextUtil.getBean("insNumRepManager");
		return insNumRepManager.addNumRep(dateList);
	}
	
	/**
	 * 获取工单下勘察计划
	 * @param woid 工单id
	 * @return
	 */
	public static List<Object> getSrvyPln(String woid) {
		SurPlanManagerInf surPlanManager = (SurPlanManagerInf) SpringContextUtil.getBean("surPlanManager");
		return surPlanManager.getSrvyPln(woid);
	}
	
	/**
	 * 更新勘察计划状态
	 * @param paramList 勘察计划状态列表
	 * @return
	 */
	public static ActionResult updSrvyPlnStatus(List<Object> paramList){
		SurPlanManagerInf surPlanManager = (SurPlanManagerInf) SpringContextUtil.getBean("surPlanManager");
		return surPlanManager.updSrvyPlnStatus(paramList);
	}
	
	/**
	 * 获取勘察计划
	 * @param pid
	 * @return
	 */
	public static Map<String,String> getSrvyPlnByPid(String pid) {
		SurPlanManagerInf surPlanManager = (SurPlanManagerInf) SpringContextUtil.getBean("surPlanManager");
		return surPlanManager.getSurPByPid(pid);
	}
	
	/**
	 * 工单下指定勘察计划状态的勘察计划数量
	 * @param woid
	 * @param status
	 * @return
	 */
	public static int getSrvyPlnCount(String woid, String[] status) {
		SurPlanManagerInf surPlanManager = (SurPlanManagerInf) SpringContextUtil.getBean("surPlanManager");
		return surPlanManager.getSrvyPlnCount(woid, status);
	}
	
	/**
	 * 是否存在勘察计划
	 * @param pid
	 * @return
	 */
	public static int existSrvyPln(String pid) {
		SurPlanManagerInf surPlanManager = (SurPlanManagerInf) SpringContextUtil.getBean("surPlanManager");
		return surPlanManager.existSrvyPln(pid);
	}
	
	/**
	 * 保存勘察计划反馈数据
	 * @param srvyPlnFBList
	 * @return
	 */
	public static ActionResult storeSrvyFBData(List<Object> srvyPlnFBList) {
		SrvyFbManagerInf srvyFbManager = (SrvyFbManagerInf) SpringContextUtil.getBean("srvyFbManager");
		return srvyFbManager.storeSrvyFBData(srvyPlnFBList);
	}
 }