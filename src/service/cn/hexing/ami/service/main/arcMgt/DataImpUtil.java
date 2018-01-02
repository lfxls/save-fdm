package cn.hexing.ami.service.main.arcMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.dao.common.pojo.arc.Customer;
import cn.hexing.ami.dao.common.pojo.arc.Meter;
import cn.hexing.ami.dao.common.pojo.da.DaCjq;
import cn.hexing.ami.dao.common.pojo.da.DaZd;
import cn.hexing.ami.service.system.qyjggl.JgglManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;

public class DataImpUtil {

	
	/**
	 * 校验电网线路
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateXl(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		String xlbm=m.get("xlbm");
		String xlmc=m.get("xlmc");
		String dwdm=m.get("dwdm");
		String dydj=m.get("dydj");
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(dwdm)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.xl.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//线路编码
		if (StringUtil.isEmptyString(xlbm)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.xl.xlbm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//线路名称
		if (StringUtil.isEmptyString(xlmc)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.xl.xlmc", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//线路电压
		if (StringUtil.isEmptyString(dydj)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.xl.dydj", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	
	/**
	 * 校验电网台区
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateTq(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		String tqbm=m.get("tqbm");
		String tqmc=m.get("tqmc");
		String nodeId=m.get("nodeId");
		String bz=m.get("bz");
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(nodeId)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.tq.xlid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//台区编码
		if (StringUtil.isEmptyString(tqbm)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.tq.tqbm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//台区名称
		if (StringUtil.isEmptyString(tqmc)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.tq.tqmc", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//公变专变标志
		if (StringUtil.isEmptyString(bz)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.tq.bz", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	
	/**
	 * 校验电网变压器
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateByq(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		String mc=m.get("byqmc");
		String rl=m.get("rl");
		String tqId=m.get("nodeId");
		String zbxz=m.get("zbxz");
		String azdz=m.get("azdz");
		String i18nStr = "";
		//台区ID
		if (StringUtil.isEmptyString(tqId)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.byq.tqid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//变压器名称
		if (StringUtil.isEmptyString(mc)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.byq.mc", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//变压器铭牌容量
		if (StringUtil.isEmptyString(rl)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.byq.rl", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//主备性质
		if (StringUtil.isEmptyString(zbxz)) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.byq.zbxz", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//安装地址
		if (StringUtil.isEmptyString(azdz)) {
			i18nStr = I18nUtil.getText("basicModule.dagl.cjqda.azdz", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	
	/**
	 * 校验表计档案 485、PLC
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validateBj485(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		Meter meter = new Meter();
//		CommonUtil.map2Obj(m, meter);
//		String i18nStr = "";
//		ModelMgtManagerInf modelMgtManagerInf = (ModelMgtManagerInf)SpringContextUtil.getBean("modelMgtManager");
//		Map<String, String> bxhmap = modelMgtManagerInf.queryByModel(m.get("bxh"));
//		//表型号不存在
//		if (bxhmap == null){
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted", lang, new String[]{String.valueOf(lineNo),i18nStr,m.get("bxh")}));
//		}
//		
//		//表计局号
//		if (StringUtil.isEmptyString(meter.getMsn())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}else{
//			//表计局号必须为12位
//			if (meter.getMsn().length()!=12) {
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.length", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//			}
//		}
//		//单位
//		if (StringUtil.isEmptyString(meter.getUid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//表计类型
//		if (StringUtil.isEmptyString(bj.getBjlx())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjlx", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//表计规约
//		/*if (StringUtil.isEmptyString(bj.getTxgy())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.txgy", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}*/
//		//表计模式
//		if (StringUtil.isEmptyString(meter.getMode())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//	
//	
//	
//		return validateBuf.toString();
//	}
	
	/**
	 * 校验表计档案 485、PLC
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validateOneStep485(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		
//		Meter meter = new Meter();
//		CommonUtil.map2Obj(m, meter);
//		String i18nStr = "";
//
//		ModelMgtManagerInf modelMgtManagerInf = (ModelMgtManagerInf)SpringContextUtil.getBean("modelMgtManager");
//		Map<String, String> bxhmap = modelMgtManagerInf.queryByModel(m.get("bxh"));
//	
//		//表型号不存在
//		if (bxhmap == null){
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted", lang, new String[]{String.valueOf(lineNo),i18nStr,m.get("bxh")}));
//		}
//		//表计局号
//		if (StringUtil.isEmptyString(meter.getMsn())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}else{
//			//表计局号必须为12位
//			if (meter.getMsn().length()!=12) {
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.length", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//			}
//		}
//		//单位
//		if (StringUtil.isEmptyString(meter.getUid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//表计类型
//		if (StringUtil.isEmptyString(bj.getBjlx())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjlx", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//表计规约
//		/*if (StringUtil.isEmptyString(bj.getTxgy())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.txgy", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}*/
//		//表计模式
//		if (StringUtil.isEmptyString(meter.getMode())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//	
//		//表计型号
//		if (StringUtil.isEmptyString(bj.getBxh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//变压器ID
//		if (StringUtil.isEmptyString(meter.getTfid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.byqid", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		
//		
//		
//		DaYh cust=new DaYh();
//		CommonUtil.map2Obj(m, cust);
//		//户号
//		if (StringUtil.isEmptyString(cust.getHh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//户名
//		if (StringUtil.isEmptyString(cust.getHm())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hm", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//用户类型
//		if (StringUtil.isEmptyString(cust.getYhlx())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.yhlx", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		return validateBuf.toString();
//	}
	
	/**
	 * 校验表计档案 485-PLC-RF-CT-UO
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validate485PLCRFCTUO(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		m.put(Constants.APP_LANG, lang);
//		
//		Meter meter = new Meter();
//		CommonUtil.map2Obj(m, meter);
//		String i18nStr = "";
//		MeterMgtManagerInf meterMgtManager = (MeterMgtManagerInf)SpringContextUtil.getBean("meterMgtManager");
//		ModelMgtManagerInf modelMgtManagerInf = (ModelMgtManagerInf)SpringContextUtil.getBean("modelMgtManager");
//		Map<String, String> bxhmap = modelMgtManagerInf.queryByModel(m.get("bxh"));
//	
//	//	CjqglManagerInf cjqglManagerInf = (CjqglManagerInf)SpringContextUtil.getBean("cjqglManager");
//		//表计型号
//		if (StringUtil.isEmptyString(bj.getBxh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		
//		//表型号不存在
//		if (bxhmap == null){
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted", lang, new String[]{String.valueOf(lineNo),i18nStr,m.get("bxh")}));
//		}
//		//表计局号
//		if (StringUtil.isEmptyString(meter.getMsn())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}else{
//			
//		
//				String meterjh=meter.getMsn();
//				m.put("bjjh", meterjh);
//				
//
//			Map<String, String> mapParam = new HashMap<String, String>();
//			mapParam.put("bjjh", meterjh);
//			ActionResult reBj = meterMgtManager.queryMeterbyMsn(mapParam);
//			if(reBj.isSuccess()){ //导入表计数据库已存在
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.existed",
//						lang,new String[] { String.valueOf(lineNo), i18nStr, meterjh}));
//			}
//		}
//		//单位
//		if (StringUtil.isEmptyString(meter.getUid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		} else {
//			ActionResult result = meterMgtManager.queryTFByUid(m);
//			if(!result.isSuccess()){
////			    i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.dwdm", lang);
//				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.dwdm.line",
//						lang, new String[]{String.valueOf(lineNo), meter.getDwdm()}));
//				
//			}else{
//			    String byqid = result.getMsg();
//				m.put("byqid", byqid);
//			}
//		}
//	
//		//是否RF表
//		if (StringUtil.isEmptyString(bj.getSfrfb())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.sfrfb", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		
////		//采集器
////		if(!StringUtil.isEmptyString(bj.getCjqjh())){
////			ActionResult acre = cjqglManagerInf.queryCjq(m);
////			if(!acre.isSuccess()){
////				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.cjqjh", lang);
////				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted",
////						lang, new String[]{String.valueOf(lineNo),i18nStr,m.get("cjqjh")}));		
////			}
////		}
//		
//		DaYh cust=new DaYh();
//		CommonUtil.map2Obj(m, cust);
//		//户号
//		if (StringUtil.isEmptyString(cust.getHh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//户名
//		if (StringUtil.isEmptyString(cust.getHm())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hm", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//用电属性
//		if (StringUtil.isEmptyString(cust.getYdsx())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.ydsx", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//用户地址
//		if (StringUtil.isEmptyString(cust.getYhdz())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.yhdz", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		
//		return validateBuf.toString();
//	}
	
	/**
	 * 校验表计档案 485、PLC
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validateOneStepGprs(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		String i18nStr = "";
//		
//		Meter meter = new Meter();
//		CommonUtil.map2Obj(m, meter);
//		DaZd zd = new DaZd();
//		CommonUtil.map2Obj(m, zd);
//		
//		//表型号
//		String bxh = StringUtil.getString(m.get("bxh"));
//		if(StringUtil.isEmptyString(bxh)){
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}else{
//			ModelMgtManagerInf modelMgtManagerInf = (ModelMgtManagerInf)SpringContextUtil.getBean("modelMgtManager");
//			Map<String, String> bxhmap = modelMgtManagerInf.queryByModel(m.get("bxh"));
//			//表型号不存在
//			if (bxhmap == null){
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.model", lang);
//				validateBuf.append(I18nUtil.getText(
//						"mainModule.arcMgt.dataImp.validate.line.notexisted",
//						lang,new String[] { String.valueOf(lineNo), i18nStr,
//								m.get("bxh") }));
//			}else{
//				m.put("csbm", StringUtil.getString(bxhmap.get("CSBM")));
//				m.put("jxfs", StringUtil.getString(bxhmap.get("JXFS")));
//				m.put("txgy", StringUtil.getString(bxhmap.get("BJGY")));
//				m.put("btl", StringUtil.getString(bxhmap.get("BTL")));
//			}
//		}
//
//		//表计局号 + 终端规约类型
//		String msn = StringUtil.getString(meter.getMsn());
//		if (StringUtil.isEmptyString(msn)) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}else{
//			//终端规约类型
//			String zdgylx = StringUtil.getString(zd.getZdgylx());
//			if (StringUtil.isEmptyString(zdgylx)) {
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.txgy", lang);
//				validateBuf.append(I18nUtil.getText(
//						"mainModule.arcMgt.dataImp.validate.line", lang,
//						new String[] { String.valueOf(lineNo), i18nStr }));
//			}
//		}
//		
//		//判断导入的表计局号数据库是否存在
//		MeterMgtManagerInf meterMgtManager = (MeterMgtManagerInf)SpringContextUtil.getBean("meterMgtManager");
//		Map<String, String> mapParam = new HashMap<String, String>();
//		mapParam.put("msn", msn);
//		ActionResult reBj = meterMgtManager.queryMeterbyMsn(mapParam);
//		if(reBj.isSuccess()){ //导入表计数据库已存在
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line.existed",
//					lang,new String[] { String.valueOf(lineNo), i18nStr, msn}));
//		}
//		
//		//变压器
//		String byqid = StringUtil.getString(meter.getTfid());
//		if(StringUtil.isEmptyString(byqid)){
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.byqid", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}else{
//			//变压器信息
//			Map<String, String> byqMap = new HashMap<String, String>();
//			byqMap.put("byqid", byqid);
//			List<Object> lst = meterMgtManager.queryTFById(byqMap);
//			if(lst != null && lst.size() > 0){
//				byqMap = (Map<String, String>) lst.get(0);
//				m.put("dwdm", StringUtil.getString(byqMap.get("DWDM")));
//				m.put("yhlx", StringUtil.getString(byqMap.get("BZ")));
//				m.put("byqazdz", StringUtil.getString(byqMap.get("AZDZ")));
//				bj.setDwdm(StringUtil.getString(byqMap.get("DWDM")));
//			}else{
//				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.byqid", lang);
//				validateBuf.append(I18nUtil.getText(
//						"mainModule.arcMgt.dataImp.validate.line.notexisted",
//						lang,new String[] { String.valueOf(lineNo), i18nStr, byqid}));
//			}
//		}
//		
//		//单位
//		if (StringUtil.isEmptyString(meter.getUid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}
//		
//		//表计模式
//		if(StringUtil.isEmptyString(meter.getMode())){
//			m.put("bjms", "01");//默认正常模式
//			meter.setMode("01");//默认正常模式
//		}
//		if (StringUtil.isEmptyString(meter.getMode())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}
//		
//		
//		//用户部分
//		DaYh cust = new DaYh();
//		CommonUtil.map2Obj(m, cust);
//		//户号
//		if (StringUtil.isEmptyString(cust.getHh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hh", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}
//		
//		//户名
//		if (StringUtil.isEmptyString(cust.getHm())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.hm", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}
//		
//		//用户类型
//		if (StringUtil.isEmptyString(cust.getYhlx())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.yhlx", lang);
//			validateBuf.append(I18nUtil.getText(
//					"mainModule.arcMgt.dataImp.validate.line", lang, new String[] {
//							String.valueOf(lineNo), i18nStr }));
//		}
//		
//		//用户地址
//		if(StringUtil.isEmptyString(cust.getYhdz())){
//			if(!StringUtil.isEmptyString(m.get("byqazdz"))){
//				yh.setYhdz(m.get("byqazdz"));
//			}
//		}
//		
//		return validateBuf.toString();
//	}
	
	/**
	 * 校验表计档案 离线表
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateBjOffline(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		Meter meter = new Meter();
		CommonUtil.map2Obj(m, meter);
		String i18nStr = "";
		//表计局号
		if (StringUtil.isEmptyString(meter.getMsn())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}else{
			//表计局号必须为12位
			if (meter.getMsn().length()!=12) {
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.bjjh", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.length", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		//单位
		if (StringUtil.isEmptyString(meter.getNodeIddw())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//表计模式
		if (StringUtil.isEmptyString(meter.getMode())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	
	/**
	 * 校验表计档案 GPRS表
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateMeterGPRS(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		Meter meter = new Meter();
		CommonUtil.map2Obj(m, meter);
		String i18nStr = "";
		DataImpManagerInf dataImpManager=(DataImpManagerInf)SpringContextUtil.getBean("dataImpManager");
		JgglManagerInf jgglManager = (JgglManagerInf)SpringContextUtil.getBean("jgglManager");
			Map<String, String> mapParam = new HashMap<String, String>();

		//单位
		if (StringUtil.isEmptyString(meter.getNodeIddw())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			mapParam.put("nodeIddw", meter.getNodeIddw());
			ActionResult re=jgglManager.getPf(mapParam);
			if(re.isSuccess()){ //导入单位数据库不存在
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted", lang, new String[]{String.valueOf(lineNo),i18nStr,meter.getNodeIddw()}));
			}
		}
		//表计局号
		if (StringUtil.isEmptyString(meter.getMsn())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.msn", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else if(meter.getMsn().length()>32||(!meter.getMsn().matches("^[A-Za-z0-9]+$"))){
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.msn", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
	
		//表计类型
		if (StringUtil.isEmptyString(meter.getMtype())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mtype", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else {
			String mtype=meter.getMtype();
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mtype", lang);
			List<Object>ls=dataImpManager.getCode("mType",mtype);
			if(ls==null||ls.size()<=0){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
		}
		//变压器
		if (StringUtil.isEmptyString(meter.getTfid())&&(!StringUtil.isEmptyString(meter.getCno()))) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.tfid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		if ((!StringUtil.isEmptyString(meter.getTfid()))&&StringUtil.isEmptyString(meter.getCno())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.cno", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//厂商编码
		if (StringUtil.isEmptyString(meter.getMfid())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mfid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mfid", lang);
			List<Object>ls=dataImpManager.getCode("MF",meter.getMfid());
			if(!(ls!=null&&ls.size()>0)){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
		}
		//CTPT校验
		if (StringUtil.isEmptyString(meter.getCt())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.ct", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.ct", lang);
			List<Object>ls=dataImpManager.getCode("ct",meter.getCt());
			if(!(ls!=null&&ls.size()>0)){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
		}
		if (StringUtil.isEmptyString(meter.getPt())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.pt", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.pt", lang);
			List<Object>ls=dataImpManager.getCode("pt",meter.getPt());
			if(!(ls!=null&&ls.size()>0)){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
		}
		//表计模式
		if (StringUtil.isEmptyString(meter.getMode())) {
		
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			String mode=meter.getMode();
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.mode", lang);
			List<Object>ls=dataImpManager.getCode("mode",mode);
			if(ls==null||ls.size()<=0){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
			
		}
		//接线方式
		if (StringUtil.isEmptyString(meter.getWiring())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.wiring", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
		}
		if (!StringUtil.isEmptyString(meter.getWiring())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.wiring", lang);
			List<Object>ls=dataImpManager.getCode("wiring",meter.getWiring());
			if(!(ls!=null&&ls.size()>0)){
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));	
			}
		}
		//经纬度校验
		if (!StringUtil.isEmptyString(meter.getLon())) {
			double lon=Double.parseDouble(meter.getLon());
			
			if(lon<-180||lon>180){
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.lon", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		if (!StringUtil.isEmptyString(meter.getLat())) {
			double lat=Double.parseDouble(meter.getLat());
			if(lat<-90||lat>90){
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.lat", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		return validateBuf.toString();
	}
	/**
	 * 校验用户档案
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateCust(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		m.put(Constants.APP_LANG, lang);
		
		Customer cust=new Customer();
		CommonUtil.map2Obj(m, cust);
		String i18nStr = "";
		//户号
		if (StringUtil.isEmptyString(cust.getCno())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.cno", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else if(cust.getCno().length()>32||(!cust.getCno().matches("^[A-Za-z0-9]+$"))){
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.cno", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//户名
		if (StringUtil.isEmptyString(cust.getCname())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.cname", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else if(cust.getCname().length()>256){
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.cname", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//判断导入的户号数据库是否存在
		CustMgtManagerInf custMgtManager = (CustMgtManagerInf)SpringContextUtil.getBean("custMgtManager");
		JgglManagerInf jgglManager = (JgglManagerInf)SpringContextUtil.getBean("jgglManager");
		
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("cno",cust.getCno());
		ActionResult re = custMgtManager.initCust(mapParam);
		if(re.isSuccess()){ //导入用户数据库已存在
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.cno", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.existed",
					lang,new String[] { String.valueOf(lineNo), i18nStr,cust.getCno()}));
		}
		//用户地址
		if (StringUtil.isEmptyString(cust.getAddr())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.addr", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else if(cust.getAddr().length()>256){
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.addr", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//单位
		if (StringUtil.isEmptyString(cust.getNodeIddw())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.uid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		else{
			 mapParam.put("nodeIddw", cust.getNodeIddw());
			 re=jgglManager.getPf(mapParam);
			 if(re.isSuccess()){ //导入单位数据库不存在
					i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.meter.uid", lang);
					validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.notexisted", lang, new String[] {String.valueOf(lineNo), i18nStr,cust.getNodeIddw()}));
			 }
		}
		//结算日
		String billing_date=cust.getBilling_date();
		if(!StringUtil.isEmptyString(billing_date)){
			int billing_dates=Integer.parseInt(billing_date);
			if(billing_dates<1||billing_dates>31){
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.billing_date", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		//电话号码长度校验
		String phone =cust.getPhone();
		if(!StringUtil.isEmptyString(phone)){
			if(!phone.matches("^\\d{0,32}$")){
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.phone", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.format", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		
		return validateBuf.toString();
	}
	
	/**
	 * 校验采集器信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateCjq(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		m.put(Constants.APP_LANG, lang);
		DaCjq daCjq=new DaCjq();
		CommonUtil.map2Obj(m, daCjq);
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(daCjq.getDwdm())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集器局号
		if (StringUtil.isEmptyString(daCjq.getCjqjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.cjqjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//规约类型
		if (StringUtil.isEmptyString(daCjq.getZdgylx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.zdgylx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集方式
		if (StringUtil.isEmptyString(daCjq.getCjfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.cjfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//单位
		if (StringUtil.isEmptyString(daCjq.getDwdm())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}else{
			MeterMgtManagerInf MeterMgtManagerInf = (MeterMgtManagerInf)SpringContextUtil.getBean("bjglManager");
			ActionResult result = MeterMgtManagerInf.queryTFByUid(m);
			if(!result.isSuccess()){
//			    i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cust.dwdm", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.dwdm.line",
						lang, new String[]{String.valueOf(lineNo), daCjq.getDwdm()}));
				
			}
		}
		return validateBuf.toString();
	}
	/**
	 * 校验G4采集器信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateGCjq(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		DaCjq daCjq=new DaCjq();
		CommonUtil.map2Obj(m, daCjq);
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(daCjq.getDwdm())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集器局号
		if (StringUtil.isEmptyString(daCjq.getCjqjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.cjqjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//表箱局号
		if (StringUtil.isEmptyString(daCjq.getBxjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.bxjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//规约类型
		if (StringUtil.isEmptyString(daCjq.getZdgylx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.zdgylx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集方式
		if (StringUtil.isEmptyString(daCjq.getCjfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.cjq.cjfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	/**
	 * 校验终端信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateZd(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		DaZd daZd=new DaZd();
		CommonUtil.map2Obj(m, daZd);
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(daZd.getDwdm())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端局号
		if (StringUtil.isEmptyString(daZd.getZdjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}else{
			//终端局号必须为8位
			if (daZd.getZdjh().length()!=8) {
				i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdjh", lang);
				validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line.lengths", lang, new String[]{String.valueOf(lineNo),i18nStr}));
			}
		}
		//规约类型
		if (StringUtil.isEmptyString(daZd.getZdgylx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdgylx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//apn
		if (StringUtil.isEmptyString(daZd.getApn())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.apn", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端类型
		if (StringUtil.isEmptyString(daZd.getZdlx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdlx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端用途
		if (StringUtil.isEmptyString(daZd.getZdyt())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdyt", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//通讯端口号
		if (StringUtil.isEmptyString(daZd.getTxdkh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.txdkh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//通讯方式
		if (StringUtil.isEmptyString(daZd.getTxfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.txfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集方式
		if (StringUtil.isEmptyString(daZd.getCjfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.cjfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//接线方式
		if (StringUtil.isEmptyString(daZd.getJxfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.jxfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	/**
	 * 校验G4终端信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateGZd(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		DaZd daZd=new DaZd();
		CommonUtil.map2Obj(m, daZd);
		String i18nStr = "";
		//单位
		if (StringUtil.isEmptyString(daZd.getDwdm())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.dwdm", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端局号
		if (StringUtil.isEmptyString(daZd.getZdjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//表箱局号
		if (StringUtil.isEmptyString(daZd.getBxjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.bxjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//规约类型
		if (StringUtil.isEmptyString(daZd.getZdgylx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdgylx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//apn
		if (StringUtil.isEmptyString(daZd.getApn())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.apn", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端类型
		if (StringUtil.isEmptyString(daZd.getZdlx())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdlx", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端用途
		if (StringUtil.isEmptyString(daZd.getZdyt())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdyt", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//通讯端口号
		if (StringUtil.isEmptyString(daZd.getTxdkh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.txdkh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//通讯方式
		if (StringUtil.isEmptyString(daZd.getTxfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.txfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//采集方式
		if (StringUtil.isEmptyString(daZd.getCjfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.cjfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//接线方式
		if (StringUtil.isEmptyString(daZd.getJxfs())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.jxfs", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	/**
	 * 校验集中器信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateJzq(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		DaZd daZd=new DaZd();
		CommonUtil.map2Obj(m, daZd);
		String i18nStr = "";
		//集中器局号
		if (StringUtil.isEmptyString(daZd.getZdjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.jzqjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	/**
	 * 校验sim卡信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validateSim(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		DaSim daSim=new DaSim();
//		CommonUtil.map2Obj(m, daSim);
//		String i18nStr = "";
//		//台区编码
//		if (StringUtil.isEmptyString(daSim.getDwdm())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.sim.dwdm", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//台区名称
//		if (StringUtil.isEmptyString(daSim.getSimkh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.sim.simkh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		return validateBuf.toString();
//	}
	/**
	 * 校验户表关联信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
//	public static String validateHbgl(int lineNo,Map<String, String> m,String lang){
//		StringBuffer validateBuf = new StringBuffer();
//		DaYh daYh=new DaYh();
//		CommonUtil.map2Obj(m, daYh);
//		String i18nStr = "";
//		//户号
//		if (StringUtil.isEmptyString(daYh.getHh())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.hh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		arMeter Meter=new arMeter();
//		CommonUtil.map2Obj(m, Meter);
//		//表计局号
//		if (StringUtil.isEmptyString(Meter.getMsn())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.bjjh", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		//变压器ID
//		if (StringUtil.isEmptyString(Meter.getTfid())) {
//			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.byqid", lang);
//			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
//		}
//		return validateBuf.toString();
//	}
	/**
	 * 校验投运终端信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	public static String validateTyzd(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		DaZd daZd=new DaZd();
		CommonUtil.map2Obj(m, daZd);
		String i18nStr = "";
		//终端局号
		if (StringUtil.isEmptyString(daZd.getZdjh())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.zd.zdjh", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		//终端局号
		if (StringUtil.isEmptyString(daZd.getByqid())) {
			i18nStr = I18nUtil.getText("mainModule.arcMgt.dataImp.validate.hbgl.byqid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		return validateBuf.toString();
	}
	
	/**
	 * 校验POD信息
	 * @param lineNo
	 * @param m
	 * @param lang
	 * @return
	 */
	/*public static String validatePOD(int lineNo,Map<String, String> m,String lang){
		StringBuffer validateBuf = new StringBuffer();
		POD pod = new POD();
		CommonUtil.map2Obj(m, pod);
		String i18nStr = "";
		//POD_CODE
		if (StringUtil.isEmptyString(pod.getPodid())) {
			i18nStr = I18nUtil.getText("basicModule.dagl.pod.wl.podid", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}
		if (StringUtil.isEmptyString(pod.getIsvirtual())) {
			i18nStr = I18nUtil.getText("basicModule.dagl.pod.xn", lang);
			validateBuf.append(I18nUtil.getText("mainModule.arcMgt.dataImp.validate.line", lang, new String[]{String.valueOf(lineNo),i18nStr}));
		}		
		return validateBuf.toString();
	}	*/
	
	/**
	 * 获取excel列对应数据字段属性
	 * @param archivesType
	 * @return
	 */
	public static String[] getExcelField(String archivesType){
		String[] field = null;
		
		//GPRS表
		if (Constants.DA_BJ_GPRS.equals(archivesType)) {
			field = new String[]{"msn","nodeIddw","mtype","mode","mfid","ct","pt","wiring","tfid","cno","mboxid","lon","lat","simno","sealid","matCode"};
		}
		
		//用户
		if (Constants.DA_YH.equals(archivesType)) {
			field = new String[]{"cno","cname","nodeIddw","addr","billing_date","phone"};
		}
		
		return field;
	}
	
	/**
	 * 提示导入失败信息
	 * @param archivesType
	 * @param lang
	 * @return
	 */
	public static String getErrMsg(String archivesType,String lang){
		String errMsg = "";
		
		//GPRS表
		if (Constants.DA_BJ_GPRS.equals(archivesType)) {
			errMsg = I18nUtil.getText("mainModule.arcMgt.dataImp.gprs.errmsg", lang);
		}
		//用户
		if (Constants.DA_YH.equals(archivesType)) {
			errMsg = I18nUtil.getText("mainModule.arcMgt.dataImp.customer.errmsg", lang);
		}
		
				
		return errMsg;
	}
	
//	/**
//	 * 电表表号格式化
//	 * @param meterNo
//	 * @param length
//	 * @return
//	 */
//	public static String FormatMeterNo(String meterNo, int length){
//		if(!StringUtil.isEmptyString(meterNo)){
//			meterNo = meterNo.trim();
//			int len = meterNo.length(); //传入表号的位数
//			if(len < length){//小于则前面补零
//				for(int i = 0; i < length - len; i++){
//					meterNo = "0" + meterNo;
//				}
//			}else if(len > length){//大于则截掉前面的
//				meterNo = meterNo.substring(len-length);
//			}
//			return meterNo;				
//		}else{
//			return "";
//		}
//	}

}
