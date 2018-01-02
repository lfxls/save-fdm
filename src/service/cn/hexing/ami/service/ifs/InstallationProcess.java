package cn.hexing.ami.service.ifs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.arc.Transformer;
import cn.hexing.ami.dao.common.pojo.paramData.ParamData;
import cn.hexing.ami.dao.common.pojo.paramData.ParamName;
import cn.hexing.ami.dao.common.pojo.paramData.TestParam;
import cn.hexing.ami.dao.common.pojo.paramData.TestParamName;
import cn.hexing.ami.dao.common.pojo.pre.Token;
import cn.hexing.ami.dao.main.pojo.insMgt.WorkOrder;
import cn.hexing.ami.dao.system.pojo.ggdmgl.Code;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.FileUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.MsgCodeConstants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.SymmetricCrypto;
import cn.hexing.ami.util.XmlUtil;
import cn.hexing.ami.util.aes.RijndaelUtil;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description 现场设备安装业务处理
 * @author jun
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */
public class InstallationProcess implements InstallationProcessInf{

	private static Logger logger = Logger.getLogger(InstallationProcess.class.getName());
	//新装表
	public static final String[] meter_install_new = { "S", "NO", "ONO", "MB", "DNO",
		"CLNO", "TFNO", "CNO", "CNAME", "ADDR", "CM", "MT", "MM", "MML", "CT", "PT", 
		"MC", "SIM", "LGT", "LAT", "QF", "DT", "STS", "RM", "OPT", "VNO"};
	//换表
	public static final String[] meter_install_change = { "S", "NO", "ONO", "MB", "DNO",
		"CLNO", "TFNO", "CNO", "CNAME", "ADDR", "CM", "MT", "MM", "MML", "CT", "PT", 
		"MC", "SIM", "LGT", "LAT", "QF", "DT", "STS", "RM", "OPT", "VNO"};
	//拆表
	public static final String[] meter_install_remove = { "S", "NO", "TFNO", "CNO",
		"CNAME", "ADDR", "DT", "STS", "RM", "OPT", "VNO"};
	//有序和无序新装表验证
	public static final String[] meter_install_new_validate = { "S", "NO" , "CNO", "LGT", 
		"LAT", "DT", "STS", "OPT"};
	//有序和无序换表验证
	public static final String[] meter_install_change_validate = { "S", "NO", "ONO", "CNO", "LGT", 
		"LAT", "DT", "STS", "OPT"};
	//有序和无序拆表验证
	public static final String[] meter_install_remove_validate = { "S", "NO", "CNO", 
		"DT", "STS", "OPT"};
	//现场新增新装表验证
	public static final String[] meter_install_new_add_validate = { "NO" , "CNO", "LGT", 
		"LAT", "DT", "STS", "OPT"};
	//现场新增换表验证
	public static final String[] meter_install_change_add_validate = {"NO", "ONO", "CNO", "LGT", 
		"LAT", "DT", "STS", "OPT"};
	//现场新增拆表验证
	public static final String[] meter_install_remove_add_validate = {"NO", "CNO", 
		"DT", "STS", "OPT"};
	//勘察反馈
	public static final String[] srvy_plan_fb = { "S", "CNO", "CNAME", "ADDR", "PHO", "LGT", "LAT", "CL", "CT",
		"CM", "BT", "CB", "PN", "PS", "PNN", "PEN", "DT", "STS", "RM", 
		"OPT"};
	//勘察反馈数据校验
	public static final String[] srvy_plan_fb_validate = { "S", "CNO", "CNAME", "ADDR", "PHO", "LGT", 
		"LAT", "DT", "STS", "OPT"};
	
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "insProcess.";
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 创建工单节点
	 * @param inXML 
	 */
	public String createWorkOrders(String inXML) {
		long start = System.currentTimeMillis();
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String key = "";
		String lang = "";
		if (sysMap != null) {
			key = sysMap.get("secretKey");
			lang = sysMap.get("interfaceLang");
		}
		logger.debug(inXML);
		//创建返回的XML
		Document reDoc = DocumentHelper.createDocument();
		//创建根节点
		Element root = reDoc.addElement("R");
		//验证输入的XML
		Document document = null;
		String optID = "";//操作员ID
		String pwd = "";//操作员密码
		String hhuID = "";//HHU ID
		String reqID = "";//HHU请求ID
		ActionResult re = new ActionResult(true,"");
		try {
			document = XmlUtil.GetDocument(inXML);
			Element inRoot = document.getRootElement();
			re = valideInputXml(inRoot,"down",lang);//验证xml节点
			if(re.isSuccess() == false) {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			//操作员ID解密
			re = decrypt(inRoot.elementTextTrim("ID"),key);
			if(re.isSuccess()) {
				optID = StringUtil.getValue(re.getDataObject());
			} else {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			//操作员密码解密
			re = decrypt(inRoot.elementTextTrim("PWD"),key);
			if(re.isSuccess()) {
				pwd = StringUtil.getValue(re.getDataObject());
			} else {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			hhuID = inRoot.elementTextTrim("HID");
			reqID = inRoot.elementTextTrim("RID");
			//操作员验证
			re = validOperator(optID,pwd);
			if(re.isSuccess() == false) {//接收人信息不正确
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			} else {//接收人信息正确
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
				//按xml格式生成工单
				re = genWorkOrderXML(root, hhuID, optID, reqID);
				if(re.isSuccess() == false) {
					return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return getReturnMsg(Constants.HHU_FDM_FAIL,MsgCodeConstants.HX10001);
		}
		logger.debug(reDoc.getRootElement().asXML());
		logger.debug("download work order use time:" + ((System.currentTimeMillis()-start)/1000) + "S");
		String xml=reDoc.getRootElement().asXML();
		//xml=xml.replaceAll("&amp;", "&");
		return xml;
	}
	
	/**
	 * 保存工单下载反馈
	 * @param inXML
	 */
	public String saveDownLoadFB(String inXML) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		//创建XML
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		logger.debug(inXML);
		//验证输入的XML
		Document document = null;
		ActionResult re = new ActionResult(true,"");
		try {
			document = XmlUtil.GetDocument(inXML);
			Element inRoot = document.getRootElement();
			re = valideInputXml(inRoot,"downFB",lang);//验证xml格式
			if(re.isSuccess() == false) {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			String rst = inRoot.elementTextTrim("C");
			String optID = inRoot.elementTextTrim("ID");
			String hhuID = inRoot.elementTextTrim("HID");
			String reqID = inRoot.elementTextTrim("RID");
			String msg = inRoot.elementTextTrim("MSG");
			//更新HHU下载反馈操作
			re = storeDownLoadFB(hhuID,reqID,optID,rst,msg);
			if(re.isSuccess()) {
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
			} else {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
		} catch(Exception e) {
			logger.debug(e.getMessage());
			return getReturnMsg(Constants.HHU_FDM_FAIL,MsgCodeConstants.HX10001);
		}
		logger.debug(reDoc.getRootElement().asXML());
		return reDoc.getRootElement().asXML();
	}
	
	/**
	 * 保存上传工单数据
	 * @param inXML
	 */
	public synchronized String saveUpload(String inXML) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		long start = System.currentTimeMillis();
		//创建返回XML文档对象
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		logger.debug(inXML);
		//创建输入的XML文档对象
		Document document = null;
		ActionResult re = new ActionResult(false,"");
		try {
			document = XmlUtil.GetDocument(inXML);
			re = valideInputXml(document.getRootElement(),"upload",lang);
			if(re.isSuccess() == false) {
				logger.debug("saveUpload use time:" + ((System.currentTimeMillis()-start)/1000) + "S");
				storeHHUOPWOFailLog(re.getDataObject(),document);
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			//解析输入xml
			re = parseXml(document,lang);
			if(re.isSuccess()) {
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
			} else {
				logger.debug("saveUpload use time:" + ((System.currentTimeMillis()-start)/1000) + "S");
				storeHHUOPWOFailLog(re.getDataObject(),document);
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
		} catch(Exception e) {
			logger.debug("saveUpload use time:" + ((System.currentTimeMillis()-start)/1000) + "S");
			logger.error("upload work order error: ", e);
			return getReturnMsg(Constants.HHU_FDM_FAIL,MsgCodeConstants.HX10001);
		}
		logger.debug("saveUpload use time:" + ((System.currentTimeMillis()-start)/1000) + "S");
		logger.debug(reDoc.getRootElement().asXML());
		return reDoc.getRootElement().asXML();
	}
	
	/**
	 * 登录接口
	 * @param inXML
	 * @return
	 */
	public String login(String inXML) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String key = "";
		String lang = "";
		if (sysMap != null) {
			key = sysMap.get("secretKey");
			lang = sysMap.get("interfaceLang");
		}
		//创建返回XML文档对象
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		//创建输入的XML文档对象
		Document document = null;
		String optID = "";//操作员ID
		String pwd = "";//操作员密码
		ActionResult re = new ActionResult();
		try {
			document = XmlUtil.GetDocument(inXML);
			Element inRoot = document.getRootElement();
			re = valideInputXml(inRoot,"login",lang);
			if(re.isSuccess() == false) {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			re = decrypt(inRoot.elementTextTrim("ID"),key);//解密操作员ID
			if(re.isSuccess()) {
				optID = StringUtil.getValue(re.getDataObject());
			} else {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			re = decrypt(inRoot.elementTextTrim("PWD"),key);//解密操作员ID
			if(re.isSuccess()) {
				pwd = StringUtil.getValue(re.getDataObject());
			} else {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			}
			re = validOperator(optID,pwd);
			if(re.isSuccess() == false) {
				return getReturnMsg(Constants.HHU_FDM_FAIL,re.getMsg());
			} else {
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			return getReturnMsg(Constants.HHU_FDM_FAIL,MsgCodeConstants.HX10001);
		}
		return reDoc.getRootElement().asXML();
	}
	
	/**
	 * App升级接口
	 * @param ctx 消息上下文
	 * @return
	 */
	public String appUpgrade(MessageContext ctx) {
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		String url = "";
		try {
			HttpServletRequest request = (HttpServletRequest)
					ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			url = request.getRequestURL().toString();//获取服务端的url
			url = url.substring(0,url.lastIndexOf(request.getServletPath()));//截取url
			//获取最新的app版本信息
			List<Object> appInfList = baseDAOIbatis.queryForList(sqlId + "getNewAppVerInf", null);
			if(appInfList.size() > 0 && !StringUtil.isEmptyString(url)) {
				Map<String,String> appInfMap = (Map<String, String>) appInfList.get(0);
				String appVRN = StringUtil.getValue(appInfMap.get("APPVRN")); 
				String appURL = StringUtil.getValue(appInfMap.get("APPURL"));
				if(url.endsWith("/")) {
					if(appURL.startsWith("/")) {
						appURL = url + appURL.substring(1);
					} else {
						appURL = url + appURL;
					}
				} else {
					if(appURL.startsWith("/")) {
						appURL = url + appURL;
					} else {
						appURL = url + "/" + appURL;
					}
				}
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
				root.addElement("APPVRN").addText(appVRN);
				root.addElement("FURL").addText(appURL);
			} else {
				root.addElement("C").addText(Constants.HHU_FDM_FAIL);
				root.addElement("MSG").addText(MsgCodeConstants.HX20004);//无法找到app版本
				root.addElement("APPVRN").addText("");
				root.addElement("FURL").addText("");
			}
		} catch(Exception e) {
			root.addElement("C").addText(Constants.HHU_FDM_FAIL);
			root.addElement("MSG").addText(MsgCodeConstants.HX20004);//无法找到app版本
			root.addElement("APPVRN").addText("");
			root.addElement("FURL").addText("");
		}
		
		return root.asXML();
	}
	
	/**
	 * 按xml格式生成工单，并记录工单下载日志
	 * @param root xml根节点
	 * @param hhuID HHU ID
	 * @param optID 操作员ID
	 * @param reqID HHU请求ID
	 */
	public ActionResult genWorkOrderXML(Element root, String hhuID, String optID, String reqID) {
		ActionResult re = new ActionResult(true,"");
		String uptWay = "";//更新方式
		try {
			String hhuInitStatus = CommonUtil.getHhuInit(hhuID);
			if(Constants.HHU_INIT_NO.equals(hhuInitStatus)){
				uptWay = Constants.DATAUPDATE_UPTWAY_FULL;//全量
			} else {
				uptWay = Constants.DATAUPDATE_UPTWAY_INC;//增量
			}
			//获取该接收人的已分配和待撤销工单
			List<Object> woList = CommonUtil.getDownLoadWO(optID,uptWay);
			//获取基本数据
			List<Object> basicDataList = CommonUtil.getBasicData(hhuID, uptWay, optID, reqID);
			//创建工单信息
			List<Object> woidList = genWorkOrders(root,woList);
			//创建基本信息
			genBasicInf(root,optID,basicDataList);
			//保存下载操作日志
			re = storeDownLoadOptLog(optID,hhuID,reqID,uptWay,woidList,basicDataList);
		} catch (Exception ex) {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10026);//生成工单失败
		}
		return re;
	}
	
	/**
	 * 保存下载操作日志
	 * @param optID
	 * @param hhuID
	 * @param reqID
	 * @return
	 */
	public ActionResult storeDownLoadOptLog(String optID, String hhuID, String reqID, 
			String uptWay,List<Object> woList, List<Object> basicDataList) {
		ActionResult re = new ActionResult(false,"");
		//保存工单日志
		try {
			CommonUtil.storeWOLog(hhuID, optID, reqID, Constants.DATAUPDATE_LOGSTAT_UNKNOW, null, Constants.WO_LOG_DOWNLOAD, woList,uptWay);
			CommonUtil.saveBasicDataLog(hhuID, uptWay, optID, reqID, basicDataList);
			re.setSuccess(true);
		} catch(Exception e) {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10010);
		}
		return re;
	}
	
	/**
	 * 解析上传工单的xml
	 * @param doc xml文档对象
	 * @param lang 语言
	 * @return
	 */
	public ActionResult parseXml(Document doc, String lang) {
		ActionResult re = new ActionResult(true,"");
    	//安装计划反馈对象（每个工单对应不同的安装计划反馈（表，集中器，采集器））
    	Map<String,Map<String,List<Object>>> woPlnFBMap = new HashMap<String,Map<String,List<Object>>>();
    	//现场新增的安装计划反馈对象（表，集中器，采集器）
    	Map<String,List<Object>> newPlnFBMap = new HashMap<String,List<Object>>();
    	//测试反馈数据对象（包含读取类，设置类，测试类）
    	Map<String,Object> testFBMap = new HashMap<String,Object>();
    	//token执行结果列表
    	List<Token> tokenList = new ArrayList<Token>();
    	//安装计划操作日期精确到天
    	List<Object> opDateList = new ArrayList<Object>(); 
    	//获取反馈xml的根节点
    	Element root = doc.getRootElement();
    	String hhuID = root.elementTextTrim("HID");//掌机ID
    	String optID = root.elementTextTrim("OPTID");//操作员ID
    	Element wosNode = root.element("WOS");
		List<Element> woNodeList = wosNode.elements("WO");//所有工单节点列表
		if(woNodeList == null) {//不存在工单节点元素
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10018);
			//记录上传失败消息提示，用于记录到掌机操作工单表中（ins_wo_hhulog）
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{"WO"});
			re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
			return re;
		} 
		//获取sim卡序列号对应的obis
		String simSnObis = (String) baseDAOIbatis.queryForObject(sqlId + "getSimSNObis", null, String.class);
		for(Element woNode : woNodeList) {
			//解析每一个工单
			re = parseWorkOrderXml(woNode,woPlnFBMap,newPlnFBMap,testFBMap,
					tokenList,simSnObis,opDateList);
			if(re.isSuccess() == false) {
				return re;
			}
		}
		//保存反馈数据
		re = storeFeadBackData(woPlnFBMap,newPlnFBMap,testFBMap,tokenList,hhuID,optID);
		if(re.isSuccess() == true) {
			try {
				//重新计算安装数量统计
				re = CommonUtil.addNumRep(opDateList);
			} catch(Exception e) {
				logger.error("upload work order error: ", e);
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10010);
				String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.store.fail", lang);
				re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
			}
		}
		return re;
	}
	
	/**
	 * 解析工单
	 * @param woNode 工单节点
	 * @param insPlnFBMap 安装计划反馈数据对象
	 * @param newInsPlnFBMap 现场新增安装计划反馈数据对象
	 * @param testFBMap 测试反馈数据对象
	 * @param tokenList token执行结果列表
	 * @param simSnObis sim卡序号obis
	 * @param opDateList 计划操作日期列表
	 * @return
	 */
	public ActionResult parseWorkOrderXml(Element woNode,Map<String,Map<String,List<Object>>> woPlnFBMap, 
			Map<String,List<Object>> newPlnFBMap,Map<String,Object> testFBMap, 
			List<Token> tokenList, String simSnObis, List<Object> opDateList) {
		ActionResult re = new ActionResult(true,"");
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		//安装计划反馈Map对象（包含集中器，采集器，表）
		Map<String,List<Object>> plnFBMap = new HashMap<String,List<Object>>();
		//勘察计划反馈Map对象
		Map<String,List<Object>> srvyFBMap = new HashMap<String,List<Object>>();
		//新增的安装计划反馈Map对象（包含集中器，采集器，表）
//		Map<String,List<Object>> newPlnFBMap = new HashMap<String,List<Object>>();	
		//表反馈数据列表
		List<Object> meterPlnFBList = new ArrayList<Object>();
		//勘察反馈数据列表
		List<Object> srvyFBList = new ArrayList<Object>();
		//现场新增的表反馈数据列表
		List<Object> newMeterPlnFBList = new ArrayList<Object>();
		String woid = woNode.attributeValue("ID");//工单ID
		
		String woType = "";
		re = getWorkorderType(woid,lang);//获取工单类型
		if(re.isSuccess() == false) {
			return re;
		} else {
			woType = StringUtil.getValue(re.getDataObject());
		}
		
		if(Constants.WO_TYPE_SRVY.equals(woType)) {//勘察工单
			List<Object> srvyNodeList = woNode.elements("SRVY");//多个勘察计划节点列表
			for(Object obj : srvyNodeList) {
				Element srvyNode = (Element) obj;
				re = parseSrvyFBXml(srvyNode,woid,lang);
				if(re.isSuccess() == false) {
					return re;
				} else {
					Map<String,String> srvyFBDataMap = (Map<String, String>) re.getDataObject();
					if(srvyFBDataMap.size() > 0) {
						srvyFBList.add(srvyFBDataMap);
					}
				}
			}
			srvyFBMap.put("survey", srvyFBList);
			woPlnFBMap.put(woid, srvyFBMap);
		} else {//安装工单
			Element msNode = woNode.element("MS");
			re = parseMeterFBXml(msNode,woid,woType,meterPlnFBList,
					newMeterPlnFBList,testFBMap,tokenList,simSnObis,opDateList);//解析表反馈xml
			if(re.isSuccess() == false) {
				return re;
			} else {
				plnFBMap.put("meter", meterPlnFBList);//将表反馈信息添加到安装计划Map对象中
				if(newMeterPlnFBList.size() > 0) {
					//将现场新增的表反馈信息添加到现场新增的安装计划Map对象中
					newPlnFBMap.put("newMeter", newMeterPlnFBList);
				}
			}
			if(plnFBMap.size() > 0 && !"00000000".equals(woid)) {
				woPlnFBMap.put(woid, plnFBMap);
			}
		}
		
//		if(newPlnFBMap.size() > 0) {
//			String newWoid = CommonUtil.getWOID();
//			newWOPlnFBMap.put(newWoid, newPlnFBMap);
//		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 解析表反馈数据
	 * @param msNode 表反馈计划根节点
	 * @param woid 工单号
	 * @param woType 工单类型
	 * @param insPlnFBMap 安装喜欢反馈Map对象
	 * @param testFBMap 测试反馈Map对象
	 * @param tokenList token对象列表
	 * @param simSnObis sim卡序号Obis
	 * @param opDateList 计划操作日期列表
	 * @return
	 */
	public ActionResult parseMeterFBXml(Element msNode, String woid, String woType,
			List<Object> meterPlnFBList, List<Object> newMeterPlnFBList, Map<String,Object> testFBMap, 
			List<Token> tokenList, String simSnObis, List<Object> opDateList) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		ActionResult re = new ActionResult(true,"");
		//设置类反馈数据列表
		List<Object> setFBList = (List<Object>) testFBMap.get("set");
		if(setFBList == null) {
			setFBList = new ArrayList<Object>();
		}
		//读取类反馈数据列表
		List<Object> readFBList = (List<Object>) testFBMap.get("read");
		if(readFBList == null) {
			readFBList = new ArrayList<Object>();
		}
		//测试类反馈数据列表
		Map<String,List<Object>> testPMFBMap = (Map<String, List<Object>>) testFBMap.get("test");
		if(testPMFBMap == null) {
			testPMFBMap = new HashMap<String,List<Object>>();
		}
		if(msNode != null) {//存在表安装计划根节点
			List<Object> mNodeList = msNode.elements("M");//多表安装计划节点列表
			for(Object obj : mNodeList) {
				String simSN = "";//sim卡序列号
				Element mNode = (Element) obj;
				//校验反馈数据是否为空
				re = validateData(woid,mNode);
				if(re.isSuccess() == false) {
					String errMsg = StringUtil.getValue(re.getDataObject());
					re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
					return re;
				}
				//获取安装计划状态
				String pid = mNode.elementTextTrim("S");
				String plnStatus = getPlanStatus(pid);
				if(Constants.PLN_STATUS_SUCCESS.equals(plnStatus) ||
						Constants.PLN_STATUS_ABNORMAL.equals(plnStatus)) {
					//成功或异常的安装计划，循环下一条执行（考虑到掌机上传工单因网络问题超时情况）
					continue;
				} else if("".equals(plnStatus)) {//不存在安装计划状态
					if(!"00000000".equals(woid)) {//非现场新建工单
						re.setSuccess(false);
						re.setMsg(MsgCodeConstants.HX10022);
						String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.plan.notExist", lang, new String[]{pid});
						re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
						return re;
					}
				}
				//校验换表业务中新表号和老表号是否一样
				re = validateRepMeter(mNode);
				if(re.isSuccess() == false) {
					String errMsg = StringUtil.getValue(re.getDataObject());
					re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
					return re;
				}
				//获取反馈数据
				re = processBasicFBData(woid,woType,mNode,lang);
				if(re.isSuccess()) {//记录反馈数据
					setFBList.addAll(processSetFBData(mNode,tokenList));
					readFBList.addAll(processReadFBData(mNode,simSnObis,simSN));
					testPMFBMap.putAll(processTestFBData(mNode));
					Map<String,String> fbMap = (Map<String, String>) re.getDataObject();
					fbMap.put("SIMSN", simSN);//记录sim卡号序列
					if("00000000".equals(woid)) {//现场新增工单的安装计划
						newMeterPlnFBList.add(fbMap);
					} else {//系统派发的安装计划反馈数据
						meterPlnFBList.add(fbMap);
					}
				} else {
					String errMsg = StringUtil.getValue(re.getDataObject());
					re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
					return re;
				}
				//记录安装计划操作日期
				String opDate = StringUtil.getValue(mNode.elementTextTrim("DT"));//安装计划反馈操作时间
				opDate = DateUtil.convertFormat(opDate, "yyyy-MM-dd");
				if(opDateList.size() == 0) {
					opDateList.add(opDate);
				} else {
					setPlnOpDate(opDate,opDateList);
				}
			}
			testFBMap.put("set", setFBList);
			testFBMap.put("read", readFBList);
			testFBMap.put("test", testPMFBMap);
			re.setSuccess(true);
		} else {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10021);
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{"MS"});
			re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
		}
		return re;
	}
	
	/**
	 * 处理反馈数据
	 * @param woid 工单号
	 * @param woType 工单类型
	 * @param mNode 表节点
	 * @param lang 语言
	 * @return
	 */
	public ActionResult processBasicFBData(String woid, String woType, Element mNode, String lang) {
		ActionResult re = new ActionResult(true,"");
		String type = mNode.attributeValue("TYPE");//安装计划类型
		String pid = mNode.elementTextTrim("S");
		Map<String, String> map = new HashMap<String, String>(); //每个对象属性Map
		if("00000000".equals(woid)) {//现场新增工单的安装计划
			map.put("DATA_TYPE", Constants.WO_TYPE_NEW);
		} else {//有序和无序工单对应的安装计划
			map.put("DATA_TYPE", woType);
//			List<Object> meterPlnList = CommonUtil.getMeterPlnByPid(pid);
//			if(meterPlnList.size() == 0) {//不存在表安装计划
//				re.setSuccess(false);
//				re.setMsg(MsgCodeConstants.HX10022);
//				re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.plan.notExist", lang, new String[]{pid}));
//				return re;
//			}
		}
		if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(type)) {//新装表
			String sim = StringUtil.getValue(mNode.elementTextTrim("SIM"));
			String msn = StringUtil.getValue(mNode.elementTextTrim("NO"));
			String omsn = StringUtil.getValue(mNode.elementTextTrim("ONO"));
			String cno = StringUtil.getValue(mNode.elementTextTrim("CNO"));
			String tfNo = StringUtil.getValue(mNode.elementTextTrim("TFNO"));
			re = checkSim(sim,pid,lang);//验证sim卡是否已经使用
			if(re.isSuccess() == false) {
				return re;
			}
			//验证新表号是否已经在系统中装出
			re = checkNewMeter(msn,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}
			//验证老表号是否属于当前户号下(新装表也存在换现场老表的情况)
//			re = checkOldMeter(cno,omsn,pid,lang);
//			if(re.isSuccess() == false) {
//				return re;
//			}
			//验证户，变压器所属单位是否一致
			re = checkTFAndCustUnit(cno,msn,tfNo,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}
			//调用根据转为Map对象
			map.putAll(Xml2Map(mNode, meter_install_new));
			map.put("OPMSN", StringUtil.getValue(mNode.elementTextTrim("NO")));
			re = doPicture(mNode, "PICS", "S", "NO","meter", map);
			if(re.isSuccess() == false) {
				return re;
			}
			//老表图片
			re = doPicture(mNode, "PICSO", "S", "ONO", "meter", map);
			if(re.isSuccess() == false) {
				return re;
			}
		} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(type)) {//更换
			String sim = StringUtil.getValue(mNode.elementTextTrim("SIM"));
			String msn = StringUtil.getValue(mNode.elementTextTrim("NO"));
			String omsn = StringUtil.getValue(mNode.elementTextTrim("ONO"));
			String cno = StringUtil.getValue(mNode.elementTextTrim("CNO"));
			String tfNo = StringUtil.getValue(mNode.elementTextTrim("TFNO"));
			re = checkSim(sim,pid,lang);//验证sim卡是否已经使用
			if(re.isSuccess() == false) {
				return re;
			}
			//验证老表号是否属于当前户号下
			/*此处不验证，由于现场实际装的老表号与计划中拆的老表号不一致20161031
			re = checkOldMeter(cno,omsn,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}*/
			//验证新表号是否已经在系统中装出
			re = checkNewMeter(msn,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}
			//验证户，变压器所属单位是否一致
			re = checkTFAndCustUnit(cno,msn,tfNo,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}
			//调用根据转为Map对象
			map.putAll(Xml2Map(mNode, meter_install_change));
			map.put("OPMSN", StringUtil.getValue(mNode.elementTextTrim("ONO")));
			//新表图片
			re = doPicture(mNode, "PICS", "S", "NO","meter", map);
			if(re.isSuccess() == false) {
				return re;
			}
			//老表图片
			re = doPicture(mNode, "PICSO", "S", "ONO", "meter", map);
			if(re.isSuccess() == false) {
				return re;
			}
		} else if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(type)) {//拆回
			//验证老表号是否属于当前户号下
			String omsn = mNode.elementTextTrim("NO");
			String cno = mNode.elementTextTrim("CNO");
			re = checkOldMeter(cno,omsn,pid,lang);
			if(re.isSuccess() == false) {
				return re;
			}
			//调用根据转为Map对象
			map.putAll(Xml2Map(mNode, meter_install_remove));
			map.put("OPMSN", StringUtil.getValue(mNode.elementTextTrim("NO")));
			//老表图片
			re = doPicture(mNode, "PICS", "S", "NO", "meter", map);
			if(re.isSuccess() == false) {
				return re;
			}
			map.put("ONO", map.get("NO"));
			map.put("NO", null);
			map.put("PICSO", map.get("PICS"));
			map.put("PICS", null);
		}
		String rst = mNode.elementTextTrim("STS");
		if(Constants.PLN_FB_SUCCESS.equals(rst)) {
			map.put("PLNSTATUS", Constants.PLN_STATUS_SUCCESS);
		} else if(Constants.PLN_FB_ABNORMAL.equals(rst)) {
			map.put("PLNSTATUS", Constants.PLN_STATUS_ABNORMAL);
		} else if(Constants.PLN_FB_FAIL.equals(rst)) {
			map.put("PLNSTATUS", Constants.PLN_STATUS_FAIL);
		}
		map.put("PID", pid);
		map.put("BUSSTYPE", type);
		map.put("DEVTYPE", "0");
		map.put("CURR_STAFFID", StringUtil.getValue(mNode.elementTextTrim("OPT")));
		String sealid = StringUtil.getValue(map.get("QF"));//铅封id
		if(!StringUtil.isEmptyString(sealid)) {//铅封id过滤最后的逗号
			if(sealid.endsWith(",")) {
				sealid = sealid.substring(0,sealid.lastIndexOf(","));
				map.put("QF", sealid);
			}
		}
		re.setDataObject(map);
		re.setSuccess(true);
		return re;
	}
	
	public List<Object> processSetFBData(Element mNode, List<Token> tokenList) {
		String pid = mNode.elementTextTrim("S");
		String msn = mNode.elementTextTrim("NO");
		Element tNode = mNode.element("T");
		List<Object> setFBList = new ArrayList<Object>();
		if(tNode != null) {
			List<Element> cNodeList = tNode.elements("C");
			for(Element cNode : cNodeList) {
				String pmType = cNode.attributeValue("TYPE");
				if("1".equals(pmType)) {
					setFBList = setFB(cNode,pid,msn,tokenList);
					break;
				} 
			}
		}
		return setFBList;
	}
	
	public List<Object> processReadFBData(Element mNode,String simSnObis,String simSN) {
		String pid = mNode.elementTextTrim("S");
		String plnType = mNode.attributeValue("TYPE");//安装计划类型
		Element tNode = mNode.element("T");
		Element toNode = mNode.element("TO");
		List<Object> readFBList = new ArrayList<Object>();
		if(tNode != null) {
			String msn = mNode.elementTextTrim("NO");
			if(!StringUtil.isEmptyString(msn)) {
				List<Element> cNodeList = tNode.elements("C");
				for(Element cNode : cNodeList) {
					String pmType = cNode.attributeValue("TYPE");
					if("0".equals(pmType)) {
						readFBList.addAll(readFB(cNode,pid,msn,simSnObis,simSN));
						break;
					} 
				}
			}
		}
		if(toNode != null) {
			String omsn = "";
			List<Element> cNodeList = toNode.elements("C");
			if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(plnType)) {
				omsn = mNode.elementTextTrim("ONO");
			} else 	if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(plnType)) {
				omsn = mNode.elementTextTrim("NO");
			} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(plnType)) {
				omsn = mNode.elementTextTrim("ONO");
			}
			if(!StringUtil.isEmptyString(omsn)) {
				for(Element cNode : cNodeList) {
					String pmType = cNode.attributeValue("TYPE");
					if("0".equals(pmType)) {
						readFBList.addAll(readFB(cNode,pid,omsn,"",simSN));
						break;
					} 
				}
			}
		}
		return readFBList;
	}
	
	public Map<String,List<Object>> processTestFBData(Element mNode) {
		String pid = mNode.elementTextTrim("S");
		String msn = mNode.elementTextTrim("NO");
		Element tNode = mNode.element("T");
		Map<String,List<Object>> testFBMap = new HashMap<String,List<Object>>();
		if(tNode != null) {
			List<Element> cNodeList = tNode.elements("C");
			for(Element cNode : cNodeList) {
				String pmType = cNode.attributeValue("TYPE");
				if("2".equals(pmType)) {
					testFBMap = testFB(cNode,pid,msn);
					break;
				} 
			}
		}
		return testFBMap;
	}
	
	/**
	 * 获取设置类反馈数据
	 * @param parentNode
	 * @return
	 */
	public List<Object> setFB(Element parentNode,String pid,
			String msn, List<Token> tokenList) {
		List<Object> reList = new ArrayList<Object>();
		List<Element> ksNodeList = parentNode.elements();
		for(Element ksNode : ksNodeList) {
			Map<String, String> setFBMap = new HashMap<String,String>();
			setFBMap.put("PID", pid);//安装计划id
			setFBMap.put("MSN", msn);//表号
			String setType = ksNode.elementTextTrim("T");//参数类型，0=普通，1=token
			setFBMap.put("OBIS", ksNode.elementTextTrim("K"));
			setFBMap.put("TYPE", ksNode.elementTextTrim("T"));
			if("0".equals(setType)) {
				setFBMap.put("RST", ksNode.elementTextTrim("R"));//设置结果
				setFBMap.put("RSTV", ksNode.elementTextTrim("V"));//设置值
			} else if("1".equals(setType)) {
				List<Element> tsElementList = ksNode.elements("TS");
				boolean existFail = false;//标志token执行存在失败，true存在失败，false不存在失败
				for(Element tsNode : tsElementList) {
					Token token = new Token();
					String tokenRst = tsNode.elementTextTrim("R");
					if(!tokenRst.equals("00") && !tokenRst.equals("02")) {//存在失败
						existFail = true;
					}
					token.setMsn(msn);
					token.setToken(tsNode.elementTextTrim("T"));
					token.setStatus(tokenRst);
					tokenList.add(token);
				}
				if(existFail) {//存在失败
					setFBMap.put("RST", "1");//则记录token设置失败
				} else {
					setFBMap.put("RST", "0");//则记录token设置成功
				}
			}
			reList.add(setFBMap);
		}
		return reList;
	}
	
	/**
	 * 获取测试类反馈数据
	 * @param parentNode
	 * @param pid 安装计划id
	 * @param msn 表号
	 * @return
	 */
	public Map<String,List<Object>> testFB(Element parentNode,String pid,String msn) {
		List<Element> tiNodeList = parentNode.elements("TI");
		Map<String,List<Object>> testMap = new HashMap<String,List<Object>>();
		for(Element tiNode : tiNodeList) {
			String key = pid+":"+msn+":"+StringUtil.getValue(tiNode.elementTextTrim("ID"))+":"+StringUtil.getValue(tiNode.elementTextTrim("R"));
			List<Element> ksNodeList = tiNode.elements("KS");
			List<Object> ksList = new ArrayList<Object>();
			for(Element ksNode : ksNodeList) {
				Map<String, String> map = new HashMap<String,String>();
				map.put("OBIS", StringUtil.getValue(ksNode.elementTextTrim("K")));
				map.put("RST", StringUtil.getValue(ksNode.elementTextTrim("R")));
				map.put("RSTV", StringUtil.getValue(ksNode.elementTextTrim("V")));
				ksList.add(map);
			}
			testMap.put(key, ksList);
		}
		return testMap;
	}
	
	/**
	 * 获取读取类反馈数据
	 * @param parentNode
	 * @return
	 */
	public List<Object> readFB(Element parentNode,String pid,String msn,
			String simSnObis, String simSN) {
		List<Object> reList = new ArrayList<Object>();
		List<Element> ksList = parentNode.elements();
		for(Element ks : ksList) {
			Map<String, String> map = new HashMap<String,String>();
			String obis = StringUtil.getValue(ks.elementTextTrim("K"));
			String value = StringUtil.getValue(ks.elementTextTrim("V"));
			if(simSnObis.equals(obis)) {//如果当前读取的是sim卡序列号的obis
				simSN = value;//记录读取的sim卡序列号
			}
			map.put("PID", pid);//安装计划id
			map.put("MSN", msn);//表号
			map.put("OBIS", obis);
			map.put("VALUE", value);
			reList.add(map);
		}
		return reList;
	}
	
	public ActionResult doPicture(Element mNode, String nodeName, String sTag, 
			String deviceTag, String deviceName, Map<String,String> map) {
		ActionResult re = new ActionResult(false, "");
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		String picField = ""; //保存到数据库的图片路径
		Element pics = mNode.element(nodeName);
		if(pics != null) {
			List<Element> picList = pics.elements();
			SimpleDateFormat formatStr = new SimpleDateFormat("yyyyMMdd");
			String dateStr = DateUtil.getCurrentDayByFormat(formatStr);
			if(picList != null) {
				String deviceNo = StringUtil.getString(map.get(deviceTag)); //设备号
				String pid = StringUtil.getString(map.get(sTag));//安装计划id
				int i = 0; 
				for(Element pic : picList) {
					i++;
					String strBin = pic.getTextTrim();//照片二进制字符串
					if(strBin.equals("")) {//不存在图片数据
						continue;
					}
					//图片名称:表号+"_"+计划号+"_"+序号
					String picName = deviceNo + "_" + pid + "_" + String.valueOf(i) + ".png";
					String picDir = "/picture" + "/" + deviceName + "/" + dateStr;
					String picPackage = picDir + "/" + picName;
					int num = FileUtil.saveToFileByImgStr(strBin, picDir, picName);
					if(num != 1){//图片上传失败
						re.setSuccess(false);
						re.setMsg(MsgCodeConstants.HX10023);
						re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.uploadPic.fail", lang, new String[]{pid}));
						return re;
					}
					if(i == 1) {
						picField = picPackage;
					} else {
						picField = picField  + "," + picPackage;
					}
				}
			}
		}
		map.put(nodeName, picField);
		//成功返回
		re.setSuccess(true);
		return re;
	}
	
	/**
	 *  XML转为Map
	 * @param parentEle
	 * @param mapKey
	 * @return
	 */
	public static Map<String, String> Xml2Map(Element parentEle, String[] mapKey){
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < mapKey.length; i++){
			String mapK = mapKey[i];
			String mapV = StringUtil.getString(parentEle.elementTextTrim(mapK));
			map.put(mapK, mapV);
		}
		return map;
	}

	/**
	 * 操作员验证
	 * @param optID
	 * @param pwd
	 * @return
	 */
	public ActionResult validOperator(String optID, String pwd) {
		ActionResult result = new ActionResult(true,"");
		HashMap<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("optid", optID);
			param.put("pwd", RijndaelUtil.encodePassword(pwd).trim());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setSuccess(false);
			result.setMsg(MsgCodeConstants.HX10016);
			return result;
		}
		List<Object> optInfList = baseDAOIbatis.queryForList(sqlId + "getOprInfo", optID);
		if(optInfList.size() == 0) {//不存在操作员
			result.setSuccess(false);
			result.setMsg(MsgCodeConstants.HX20001);
		} else {
			List<Object> list = baseDAOIbatis.queryForList(sqlId + "validOpt", param);
			if(list.size() == 0) {//操作员密码不正确
				result.setSuccess(false);
				result.setMsg(MsgCodeConstants.HX20002);
			} else {
				result.setSuccess(true);
			}
		}
		return result;
	}
	
	/**
	 * 创建工单（包括勘察,有序和无序）
	 * @param root xml对象根节点
	 * @param woList 工单列表
	 */
	public List<Object> genWorkOrders(Element root, List<Object> woList) {
		List<Object> reList = new ArrayList<Object>();
		Element wosNode = root.addElement("WOS");//工单列表根节点
		//遍历工单数据
		for(int i = 0; i < woList.size(); i++) {
			Map<String,String> reMap = new HashMap<String,String>();
			Map<String,String> woMap = (Map<String, String>) woList.get(i);
			//工单ID
			String woid = StringUtil.getValue(woMap.get("WOID"));
			//工单状态：0=未处理，1=已分配，2=已派工，3=待撤销，4=已撤销，5=已反馈，6=已完成
			String status = StringUtil.getValue(woMap.get("STATUS"));
			String optType = "";//工单操作类型：0=新增，1=撤销
			if(Constants.WO_STATUS_ASSIGNED.equals(status) || 
					Constants.WO_STATUS_DISPATCHED.equals(status) || 
					Constants.WO_STATUS_REVOKED.equals(status) ||
					Constants.WO_STATUS_FEEDBACKED.equals(status)) {
				optType = Constants.WO_OPTYPE_NEW;//新增
			} else if(Constants.WO_STATUS_REVOKING.equals(status)) {
				optType = Constants.WO_OPTYPE_REVOKE;//撤销
			}
			//工单类型：勘察，有序，无序
			String type = StringUtil.getValue(woMap.get("TYPE"));
			//新增工单节点，并设置属性
			Element woNode = wosNode.addElement("WO");
			woNode.addAttribute("ID", woid);
			woNode.addAttribute("TYPE", type);
			woNode.addAttribute("OPT", optType);
			if(!Constants.WO_OPTYPE_REVOKE.equals(optType)) {//非撤销工单，需要生成安装计划节点
				if(Constants.WO_TYPE_SRVY.equals(type)) {//勘察工单
					//根据工单id获取勘察计划
					List<Object> srvyPlnList = CommonUtil.getSrvyPln(woid);
					genSrvyPln(woNode,srvyPlnList);//生成勘察计划节点
				} else {
					//获取表安装计划（包含有序和无序安装计划）
					List<Object> woidList = new ArrayList<Object>();
					woidList.add(woid);
					List<Object> meterInsPlnList = CommonUtil.getMeterPln(woidList);
					genMInsPln(woNode,type,meterInsPlnList);
				}
			}
			reMap.put("woid", woid);
			reList.add(reMap);
		}
		return reList;
	}
	
	/**
	 * 创建表安装计划节点
	 * @param woNode 工单节点
	 * @param woType 工单类型：0=有序，1=无序
	 * @param meterInsPlnList 表安装计划列表
	 */
	public void genMInsPln(Element woNode, String woType, List<Object> meterInsPlnList) {
		//获取工单对应的表安装计划信息
		if(Constants.WO_TYPE_ORDER.equals(woType)) {//有序工单
			Element msNode = woNode.addElement("MS");
			if(meterInsPlnList.size() > 0) {
				for(Object obj : meterInsPlnList) {
					Map<String,String> map = (Map<String, String>) obj;
					String bussType = StringUtil.getValue(map.get("BUSSTYPE"));
					Element mNode = msNode.addElement("M");
					mNode.addAttribute("TYPE", bussType);
					mNode.addElement("S").addText(map.get("PID"));
					if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(bussType)) {//新装
						mNode.addElement("CNO").addText(StringUtil.getValue(map.get("CNO")));
						mNode.addElement("CNAME").addText(StringUtil.getValue(map.get("CNAME")));
						mNode.addElement("ADDR").addText(StringUtil.getValue(map.get("ADDR")));
						mNode.addElement("TFNO").addText(StringUtil.getValue(map.get("TFNO")));
						mNode.addElement("PUC").addText(StringUtil.getValue(map.get("UID")));
						mNode.addElement("PUN").addText(StringUtil.getValue(map.get("UNAME")));
						mNode.addElement("CM").addText(StringUtil.getValue(map.get("WIRING")));
						mNode.addElement("MT").addText(StringUtil.getValue(map.get("MTYPE")));
						mNode.addElement("MM").addText(StringUtil.getValue(map.get("MMODE")));
						mNode.addElement("NO").addText(StringUtil.getValue(map.get("NMSN")));
					} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(bussType)) {//更换
						mNode.addElement("NO").addText(StringUtil.getValue(map.get("NMSN")));
						mNode.addElement("ONO").addText(StringUtil.getValue(map.get("OMSN")));
						mNode.addElement("OMB").addText(StringUtil.getValue(map.get("MBOXID")));
						mNode.addElement("CNO").addText(StringUtil.getValue(map.get("CNO")));
						mNode.addElement("CNAME").addText(StringUtil.getValue(map.get("CNAME")));
						mNode.addElement("ADDR").addText(StringUtil.getValue(map.get("ADDR")));
						mNode.addElement("TFNO").addText(StringUtil.getValue(map.get("TFNO")));
						mNode.addElement("PUC").addText(StringUtil.getValue(map.get("UID")));
						mNode.addElement("PUN").addText(StringUtil.getValue(map.get("UNAME")));
						mNode.addElement("CM").addText(StringUtil.getValue(map.get("WIRING")));
						mNode.addElement("MT").addText(StringUtil.getValue(map.get("MTYPE")));
						mNode.addElement("MM").addText(StringUtil.getValue(map.get("MMODE")));
					} else if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(bussType)) {//拆回
						mNode.addElement("NO").addText(StringUtil.getValue(map.get("OMSN")));
						mNode.addElement("MB").addText(StringUtil.getValue(map.get("MBOXID")));
						mNode.addElement("CNO").addText(StringUtil.getValue(map.get("CNO")));
						mNode.addElement("CNAME").addText(StringUtil.getValue(map.get("CNAME")));
						mNode.addElement("ADDR").addText(StringUtil.getValue(map.get("ADDR")));
						mNode.addElement("TFNO").addText(StringUtil.getValue(map.get("TFNO")));
						mNode.addElement("PUC").addText(StringUtil.getValue(map.get("UID")));
						mNode.addElement("PUN").addText(StringUtil.getValue(map.get("UNAME")));
					}
				}
			}
		} else {//无序工单
			if(meterInsPlnList.size() > 0) {
				for(Object obj : meterInsPlnList) {
					Element cmsNode = woNode.addElement("CMS");
					Map<String,String> custMap = (Map<String, String>) obj;
					cmsNode.addElement("S").addText(StringUtil.getValue(custMap.get("PID")));
					cmsNode.addElement("CNO").addText(StringUtil.getValue(custMap.get("CNO")));
					cmsNode.addElement("CNAME").addText(StringUtil.getValue(custMap.get("CNAME")));
					cmsNode.addElement("PUC").addText(StringUtil.getValue(custMap.get("UID")));
					cmsNode.addElement("PUN").addText(StringUtil.getValue(custMap.get("UNAME")));
					cmsNode.addElement("ADDR").addText(StringUtil.getValue(custMap.get("ADDR")));
				}
			}
		}
	}
	
	/**
	 * 创建勘察计划节点
	 * @param woNode 工单节点
	 * @param srvyPlnList 勘察计划列表
	 */
	public void genSrvyPln(Element woNode, List<Object> srvyPlnList) {
		if(srvyPlnList.size() > 0) {
			for(Object obj : srvyPlnList) {
				Element srvyNode = woNode.addElement("SRVY");
				Map<String,String> srvyMap = (Map<String, String>) obj;
				srvyNode.addElement("S").addText(StringUtil.getValue(srvyMap.get("PID")));
				srvyNode.addElement("CNO").addText(StringUtil.getValue(srvyMap.get("CNO")));
				srvyNode.addElement("CNAME").addText(StringUtil.getValue(srvyMap.get("CNAME")));
				srvyNode.addElement("PUC").addText(StringUtil.getValue(srvyMap.get("UID")));
				srvyNode.addElement("PUN").addText(StringUtil.getValue(srvyMap.get("UNAME")));
				srvyNode.addElement("ADDR").addText(StringUtil.getValue(srvyMap.get("ADDR")));
				srvyNode.addElement("PHO").addText(StringUtil.getValue(srvyMap.get("PHONE")));
			}
		}
	}
	
	/**
	 * 创建基本信息
	 * @param root 根节点
	 * @param optID 操作员ID
	 * @param basicDataList 基本数据列表
	 */
	public void genBasicInf(Element root, String optID, List<Object> basicDataList) {
		Element basic = root.addElement("BASIC");
		//创建设备基本信息
		genDevInfo(basic, optID, basicDataList);
		//创建档案信息
		genArInfo(basic,basicDataList);
		//创建方案库信息
		genProgInfo(basic,basicDataList);
		//创建token信息
		genTokenInfo(basic,basicDataList);
	}
	
	/**
	 * 创建基本代码信息
	 * @param basic 基本信息节点
	 * @param popid 操作员id
	 * @param basicDataList 基础数据列表
	 */
	public void genDevInfo(Element basic, String optID, List<Object> basicDataList) {
		Element b = basic.addElement("B");
		//获取操作员信息
		List<Object> oprList = baseDAOIbatis.queryForList(sqlId + "getOprInfo", optID);
		if(oprList.size() > 0) {
			Element opt = b.addElement("OPT");
			Map<String,String> oMap = (Map<String, String>) oprList.get(0);
			opt.addElement("ID").addText(StringUtil.getValue(oMap.get("OPTID")));//操作员id
			opt.addElement("N").addText(StringUtil.getValue(oMap.get("NAME")));//操作员名称
			opt.addElement("M").addText(StringUtil.getValue(oMap.get("PHONE")));//手机号码
			opt.addElement("ITN").addText(StringUtil.getValue(oMap.get("TNAME")));//安装队名称
		}
		
		//获取P_CODE表里信息
		Element pcs = b.addElement("PCS");
		for(Object obj : basicDataList) {
			if(obj instanceof Code) {//如果对象为基础代码信息对象
				Code code = (Code) obj;
				String optType = code.getOpType();
				if(StringUtil.isEmptyString(optType)) {
					optType = "0";
				}
				Element pc = pcs.addElement("PC");
				pc.addAttribute("TYPE", optType);
				pc.addAttribute("LANG", StringUtil.getValue(code.getLang()));
				//pc.addAttribute("NAME", StringUtil.getValue(code.getName()));编码名
				pc.addAttribute("NAME", StringUtil.getValue(code.getCodeName()));
				//pc.addAttribute("VALUE", StringUtil.getValue(code.getValue()));编码值
				pc.addAttribute("VALUE", StringUtil.getValue(code.getCodeValue()));
				//pc.addText(StringUtil.getValue(code.getCode_type()));编码分类
				pc.addText(StringUtil.getValue(code.getCateCode()));
			}
		}
	}
	
	/**
	 * 创建档案信息
	 * @param basic 基本信息节点
	 * @param basicDataList 基础数据列表
	 */
	public void genArInfo(Element basic,List<Object> basicDataList) {
		//获取厂商表型信息
		Element aNode = basic.addElement("A");
		Element mms = aNode.addElement("MMS");
		List<Object> mmList = baseDAOIbatis.queryForList(sqlId + "getFacModel", null);
		if(mmList.size() > 0) {
			for(Object obj : mmList) {
				Map<String,String> mmMap = (Map<String, String>) obj;
				Element mm = mms.addElement("MM");
				//厂商编码
				mm.addElement("FAC").addText(StringUtil.getValue(mmMap.get("FAC")));
				//电表型号
				mm.addElement("MOD").addText(StringUtil.getValue(mmMap.get("MMODEL")));
			}
		}
		
		//变压器档案信息
		Element tfs = aNode.addElement("TFS");
		for(Object obj : basicDataList) {
			if(obj instanceof Transformer) {//如果对象为变压器信息对象
				Transformer transformer = (Transformer) obj;
				Element tf = tfs.addElement("TF");
				tf.addAttribute("TYPE", transformer.getOpType());
				//变压器ID
				tf.addElement("ID").addText(StringUtil.getValue(transformer.getTfid()));
				//变压器名称
				tf.addElement("N").addText(StringUtil.getValue(transformer.getTfname()));
				//变压器地址
				tf.addElement("ADDR").addText(StringUtil.getValue(transformer.getAddr()));
			}
		}
	}
	
	/**
	 * 创建方案库信息
	 * @param basic 基本参数节点
	 * @param basicDataList 基础数据列表
	 */
	public void genProgInfo(Element basic, List<Object> basicDataList) {
		Element p = basic.addElement("P");
		if(basicDataList.size() > 0) {//存在方案数据，下发内控版本对应obis信息
			Element viNode = p.addElement("VI");
			viNode.addAttribute("TYPE", "0");//默认为新增
			//获取内控版本号对应的obis信息
			List<Object> verObisList = baseDAOIbatis.queryForList(sqlId + "getVerOBISInfo", null);
			int num = 0;
			Element lsNode = null;
			for(Object verObj : verObisList) {//增加内控版本obis节点
				Map<String,String> verObisMap = (Map<String, String>) verObj;
				if(num == 0) {
					viNode.addElement("ID").addText(StringUtil.getValue(verObisMap.get("ITEMID")));
					viNode.addElement("DT").addText(StringUtil.getValue(verObisMap.get("DATATYPE")));
					viNode.addElement("OT").addText(StringUtil.getValue(verObisMap.get("OPTTYPE")));
					lsNode = viNode.addElement("LS");
				} 
				Element lNode = lsNode.addElement("L");
				lNode.addAttribute("LANG", StringUtil.getValue(verObisMap.get("LANG")));
				lNode.addText(StringUtil.getValue(verObisMap.get("ITEMNAME")));
				num++;
			}
		}
		//获取表型和表内控版本号信息
		Map<String,Map<String,List<Object>>> vParamMap =  getVParamData(basicDataList);
		for(Map.Entry<String, Map<String,List<Object>>> entry : vParamMap.entrySet()) {
			String key = entry.getKey();
			String verid = key.split(":")[0];//内控版本号
			String meterModel = key.split(":")[1];//表型号
			Element vNode = p.addElement("V");
			//内控版本ID
			vNode.addAttribute("ID", verid);
			//表型号
			vNode.addAttribute("MM", meterModel);
			Map<String,List<Object>> paramMap = entry.getValue();
			List<Object> setDataList = paramMap.get("set");
			List<Object> readDataList = paramMap.get("read");
			List<Object> testDataList = paramMap.get("test");
			if(readDataList != null) {//存在读取数据
				genReadPM(vNode,readDataList,verid);
			}
			if(setDataList != null) {//存在设置数据
				genSetPM(vNode,setDataList,verid);
			}
			if(testDataList != null) {//存在测试数据
				genTestPM(vNode,testDataList,verid);
			}
		}
	}
	
	/**
	 * 创建Token信息
	 * @param basic 基本参数节点
	 * @param basicDataList 基础数据列表
	 */
	public void genTokenInfo(Element basic, List<Object> basicDataList) {
		Element tNode = basic.addElement("T");
		Map<String,List<Object>> meterTokenMap = processToken(basicDataList);
		for(Map.Entry<String, List<Object>> entry : meterTokenMap.entrySet()) {
			Element mNode = tNode.addElement("M");
			mNode.addAttribute("MSN", entry.getKey());
			List<Object> tokenList = entry.getValue();
			for(Object obj : tokenList) {
				Token token = (Token) obj;
				Element mtNode = mNode.addElement("MT");
				mtNode.addAttribute("TYPE", StringUtil.getValue(token.getOpType()));
				mtNode.addElement("ID").addText(StringUtil.getValue(token.getTid()));
				mtNode.addElement("S").addText(StringUtil.getValue(token.getSort()));
				mtNode.addElement("T").addText(StringUtil.getValue(token.getToken()));
			}
		}
			
	}
	
	/**
	 * token处理，过滤出同一表号对应的token信息
	 * @param basicDataList
	 * @return
	 */
	protected Map<String,List<Object>> processToken(List<Object> basicDataList) {
		Map<String,List<Object>> meterTokenMap = new HashMap<String,List<Object>>();
		List<Object> tempBasicDataList = new ArrayList<Object>();
		tempBasicDataList.addAll(basicDataList);
		for(int i = 0; i < tempBasicDataList.size(); i++) {
			List<Object> tokenList = new ArrayList<Object>();
			Object obj = tempBasicDataList.get(i);
			if(obj instanceof Token) {
				Token token = (Token) obj;
				tokenList.add(token);
				String msn = token.getMsn();//表号
				for(int j = i+1; j < tempBasicDataList.size(); j++) {
					Object nextObj = tempBasicDataList.get(j);
					if(nextObj instanceof Token) {
						Token nextToken = (Token) nextObj;
						String nextMsn = nextToken.getMsn();//下一个表号
						if(msn.equals(nextMsn)) {
							tokenList.add(nextToken);
							tempBasicDataList.remove(nextObj);
							j--;
						}
					}
				}
				meterTokenMap.put(msn, tokenList);
			}
		}
		return meterTokenMap;
	}
	
	/**
	 * 创建读取参数
	 * @param vNode 内控版本节点
	 * @param readDataList 读取类方案数据列表
	 * @param verid 内控版本id
	 */
	public void genReadPM(Element vNode, List<Object> readDataList, String verid) {
		Element cNode = vNode.addElement("C");
		cNode.addAttribute("TYPE", Constants.PMSOL_PMTYPE_READ);
		for(Object obj : readDataList) {
			ParamData paramD = (ParamData) obj;
			String optType = StringUtil.getValue(paramD.getOpType());
			if(StringUtil.isEmptyString(optType)) {
				//如果操作类型为空，默认为新增
				optType = "0";
			}
			Element isNode = cNode.addElement("IS");
			isNode.addAttribute("TYPE", optType);//操作类型
			isNode.addElement("ID").addText(StringUtil.getValue(paramD.getObis()));//obis
			if(!optType.equals("2")) {//非删除操作
				isNode.addElement("DT").addText(StringUtil.getValue(paramD.getDlms_data_type()));//数据类型
				isNode.addElement("OT").addText(StringUtil.getValue(paramD.getOp_type()));//操作类型，读，写
				isNode.addElement("L").addText(StringUtil.getValue(paramD.getScale()));//量纲
				isNode.addElement("UT").addText(StringUtil.getValue(paramD.getUnit()));//单位
				isNode.addElement("CTPT").addText(StringUtil.getValue(paramD.getXctpt()));//是否乘CTPT
				isNode.addElement("S").addText(StringUtil.getValue(paramD.getSort()));//序号
				List<ParamName> paramNameList = paramD.getParamNameLs();
				Element lsNode = isNode.addElement("LS");
				for(ParamName paramName : paramNameList) {
					Element lNode = lsNode.addElement("L");
					lNode.addAttribute("LANG", StringUtil.getValue(paramName.getLang()));//语言
					lNode.addAttribute("TYPE", "1");//操作类型
					lNode.addText(StringUtil.getValue(paramName.getItem_name()));//obis名称
				}
			}
		}
	}
	
	/**
	 * 创建设置参数
	 * @param vNode 内控版本节点
	 * @param setDataList 设置类方案数据列表
	 * @param verid 内控版本id
	 */
	public void genSetPM(Element vNode, List<Object> setDataList,String verid) {
		Element cNode = vNode.addElement("C");
		cNode.addAttribute("TYPE", Constants.PMSOL_PMTYPE_SET);
		for(Object obj : setDataList) {
			ParamData paramD = (ParamData) obj;
			String optType = StringUtil.getValue(paramD.getOpType());
			if(StringUtil.isEmptyString(optType)) {
				//如果操作类型为空，默认为新增
				optType = "0";
			}
			String bt = StringUtil.getValue(paramD.getBuss_type());
//			String bt = "0";//普通
//			if("PREPAY_TOKEN_ISSUE".equals(bussType)) {
//				bt = "1";//token
//			}
			Element isNode = cNode.addElement("IS");
			isNode.addAttribute("TYPE", optType);//操作类型
			isNode.addElement("ID").addText(StringUtil.getValue(paramD.getObis()));//obis
			if(!optType.equals("2")) {
				isNode.addElement("DT").addText(StringUtil.getValue(paramD.getDlms_data_type()));//数据类型
				isNode.addElement("OT").addText(StringUtil.getValue(paramD.getOp_type()));//操作类型，读，写
				isNode.addElement("V").addText(StringUtil.getValue(paramD.getValue()));//值
				isNode.addElement("L").addText(StringUtil.getValue(paramD.getScale()));//量纲
				isNode.addElement("UT").addText(StringUtil.getValue(paramD.getUnit()));//单位
				isNode.addElement("BT").addText(bt);//业务
				isNode.addElement("CTPT").addText(StringUtil.getValue(paramD.getXctpt()));//是否乘CTPT
				isNode.addElement("S").addText(StringUtil.getValue(paramD.getSort()));//序号
				Element lsNode = isNode.addElement("LS");
				List<ParamName> paramNameList = paramD.getParamNameLs();
				for(ParamName paramName : paramNameList) {
					Element lNode = lsNode.addElement("L");
					lNode.addAttribute("LANG", StringUtil.getValue(paramName.getLang()));//语言
					lNode.addAttribute("TYPE", "1");//操作类型
					lNode.addText(StringUtil.getValue(paramName.getItem_name()));//obis名称
				}
			} 
		}
	}
	
	/**
	 * 创建测试参数
	 * @param vNode 内控版本节点
	 * @param testDataList 测试类方案数据列表
	 * @param verid 内控版本id
	 */
	public void genTestPM(Element vNode, List<Object> testDataList, String verid) {
		Element c = vNode.addElement("C");
		c.addAttribute("TYPE", Constants.PMSOL_PMTYPE_TEST);
		for(Object obj : testDataList) {
			TestParam testParam = (TestParam) obj;
			String optType = StringUtil.getValue(testParam.getOpType());
			if(StringUtil.isEmptyString(optType)) {
				//如果操作类型为空，默认为新增
				optType = "0";
			}
			Element tiNode = c.addElement("TI");
			tiNode.addAttribute("TYPE", optType);//操作类型
			tiNode.addElement("ID").addText(StringUtil.getValue(testParam.getTiid()));//测试id
			if(!optType.equals("2")) {
				tiNode.addElement("LV").addText(StringUtil.getValue(testParam.getLevel()));//严重等级
				tiNode.addElement("S").addText(StringUtil.getValue(testParam.getSort()));//序号
				List<TestParamName> testParamNameList = testParam.getTestParamNameList();
				Element lsNode = tiNode.addElement("LS");
				for(TestParamName testParamName : testParamNameList) {
					Element lNode = lsNode.addElement("L");
					lNode.addAttribute("LANG", StringUtil.getValue(testParamName.getLang()));
					lNode.addAttribute("TYPE", "1");
					lNode.addText(StringUtil.getValue(testParamName.getTiName()));
					Element lhNode = lsNode.addElement("L");
					lhNode.addAttribute("LANG", StringUtil.getValue(testParamName.getLang()));
					lhNode.addAttribute("TYPE", "2");
					lhNode.addText(StringUtil.getValue(testParamName.getFhint()));
				}
				Element ilNode = tiNode.addElement("IL");
				List<ParamData> paramDataList = testParam.getParamList();
				for(ParamData paramData : paramDataList) {
					Element isNode = ilNode.addElement("IS");
					isNode.addElement("ID").addText(StringUtil.getValue(paramData.getObis()));//obis
					isNode.addElement("DT").addText(StringUtil.getValue(paramData.getDlms_data_type()));//数据类型
					isNode.addElement("OT").addText(StringUtil.getValue(paramData.getOp_type()));//操作类型，读，写
					isNode.addElement("JW").addText(StringUtil.getValue(paramData.getJud_way()));//判断方式，0=有返回值，1=和固定值比较，2=位值判断，3=和数据项比较
					isNode.addElement("PF").addText(StringUtil.getValue(paramData.getPass_flag()));//通过标记，0=大于，1=等于，2=小于，3=不等于，4=大于等于，5=小于等于
					isNode.addElement("P1").addText(StringUtil.getValue(paramData.getParam1()));//第一个参数
					isNode.addElement("P2").addText(StringUtil.getValue(paramData.getParam2()));//第二个参数
					isNode.addElement("L").addText(StringUtil.getValue(paramData.getScale()));//量纲
					isNode.addElement("UT").addText(StringUtil.getValue(paramData.getUnit()));//单位
					isNode.addElement("CTPT").addText(StringUtil.getValue(paramData.getXctpt()));//是否乘CTPT
					isNode.addElement("S").addText(StringUtil.getValue(paramData.getSort()));//序号
					Element oLsNode = isNode.addElement("LS");//参数项对应的国际化语言节点
					List<ParamName> paramNameList = paramData.getParamNameLs();
					for(ParamName paramName : paramNameList) {
						Element oLNode = oLsNode.addElement("L");
						oLNode.addAttribute("LANG", StringUtil.getValue(paramName.getLang()));
						oLNode.addAttribute("TYPE", "1");
						oLNode.addText(StringUtil.getValue(paramName.getItem_name()));
						Element oLHNode = oLsNode.addElement("L");
						oLHNode.addAttribute("LANG", StringUtil.getValue(paramName.getLang()));
						oLHNode.addAttribute("TYPE", "2");
						oLHNode.addText(StringUtil.getValue(paramName.getFhint()));
					}
				}
			}
		}
	}
	
	/**
	 * 处理工单下载反馈信息
	 * @param hhuID 掌机id
	 * @param reqID 请求id
	 * @param optID 接收人id
	 * @param rst 工单下载反馈结果
	 * @param msg 错误信息
	 */
	public ActionResult storeDownLoadFB(String hhuID, String reqID, String optID,
			String rst, String msg) {
		ActionResult re = new ActionResult(false,"");
		String optRst = "";//日志操作结果
		if(Constants.HHU_FDM_SUCCESS.equals(rst)) {//下载成功
			optRst = Constants.WO_LOG_STATUS_SUCC;
		} else if(Constants.HHU_FDM_FAIL.equals(rst)){//下载失败
			optRst = Constants.WO_LOG_STATUS_FAIL;
		} else {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10017);
			return re;
		}
		List<Object> woList = CommonUtil.getWOFromLog(hhuID, reqID, optID);
		//更新用户信息列表
		List<Object> updCustList = new ArrayList<Object>();
		//工单状态列表
		List<Object> woStatusList = new ArrayList<Object>();
		//工单对应安装计划列表
		List<Object> newInsPlnList = new ArrayList<Object>();
		//工单对应勘察计划列表
		List<Object> newSrvyPlnList = new ArrayList<Object>();
		try {
			if(woList.size() > 0) {
				for(Object obj : woList) {
					//工单ID列表
					List<Object> woIDList = new ArrayList<Object>();
					List<Object> srvyPlnList = new ArrayList<Object>();
					List<Object> mPlnList = new ArrayList<Object>();
					Map<String,String> woMap = (Map<String, String>) obj;
					Map<String,String> map = new HashMap<String,String>();
					String woid = StringUtil.getValue(woMap.get("WOID"));
					String status = StringUtil.getValue(woMap.get("STATUS"));
					String woType = StringUtil.getValue(woMap.get("TYPE"));
					woIDList.add(woid);
					if(Constants.WO_TYPE_SRVY.equals(woType)) {//勘察工单
						//获取工单下的勘察计划
						srvyPlnList = CommonUtil.getSrvyPln(woid);
					} else {
						//获取工单下的表安装计划
						mPlnList = CommonUtil.getMeterPln(woIDList);
					}
					if(Constants.WO_STATUS_ASSIGNED.equals(status)) {//工单状态为已分配
						map.put("status", Constants.WO_STATUS_DISPATCHED);//设置为已派工
						map.put("woid", woid);
						//记录下载后的勘察计划信息
						recordDownLoadPln(srvyPlnList, Constants.WO_STATUS_DISPATCHED,
								optID, Constants.DEV_TYPE_UNKNOW, newSrvyPlnList);
						//记录下载后的安装计划信息
						recordDownLoadPln(mPlnList, Constants.WO_STATUS_DISPATCHED,
								optID, Constants.DEV_TYPE_METER, newInsPlnList);
						//记录下载后的用户状态信息(已派工)
						recordCustStatus(mPlnList, Constants.CUST_DISPSTATUS_DISPATCHED,
								updCustList);
					} else if(Constants.WO_STATUS_REVOKING.equals(status)) {//工单状态为待撤销
						map.put("status", Constants.WO_STATUS_REVOKED);//设置为已撤销
						map.put("woid", woid);
						//记录下载后的勘察计划信息
						recordDownLoadPln(srvyPlnList, Constants.WO_STATUS_ASSIGNED,
								optID, Constants.DEV_TYPE_UNKNOW, newSrvyPlnList);
						//记录下载后的安装计划信息
						recordDownLoadPln(mPlnList, Constants.WO_STATUS_ASSIGNED,
								optID, Constants.DEV_TYPE_METER, newInsPlnList);
						//记录下载后的用户状态信息(未派工)
						recordCustStatus(mPlnList, Constants.CUST_DISPSTATUS_UNHANDLED,
								updCustList);
					}
					map.put("CURR_STAFFID", optID);
					woStatusList.add(map);
				}
				if(Constants.HHU_FDM_SUCCESS.equals(rst)) {//下载成功
					//更新工单状态
					CommonUtil.updWOStatus(woStatusList);
					//插入工单操作日志
					CommonUtil.storeWOOPLog(woStatusList);
					//更新勘察计划状态
					CommonUtil.updSrvyPlnStatus(newSrvyPlnList);
					//更新表安装计划状态
					CommonUtil.updPlnStatus(newInsPlnList);
					//更新用户派工状态
					CommonUtil.updCustDispStatus(updCustList);
					//插入勘察计划操作日志
					CommonUtil.storePlanOPLog(newSrvyPlnList);
					//插入安装计划操作日志
					CommonUtil.storePlanOPLog(newInsPlnList);
				}
			}
			//更新掌机操作工单日志状态
			CommonUtil.updWOLog(hhuID, reqID, optID, optRst, msg);
			//更新基础信息操作日志状态
			CommonUtil.uptBasicDataLogSts(hhuID, reqID, optRst, msg);
			//更新掌机为已初始化
			CommonUtil.setHhuInit(hhuID);
			re.setSuccess(true);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10010);
		}
		return re;
	}
	
	/**
	 * 校验数据是否为空
	 * @param woid 工单id
	 * @param mNode 安装计划反馈数据根节点
	 * @param lang 国际化
	 * @return
	 */
	public ActionResult validateData(String woid, Element mNode) {
		ActionResult re = new ActionResult(false,"");
		String type = mNode.attributeValue("TYPE");//安装计划类型
		if("00000000".equals(woid)) {//现场新增的工单
			if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(type)) {//新装
				re = validateDataEmpty(mNode,meter_install_new_add_validate);
			} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(type)) {//更换
				re = validateDataEmpty(mNode,meter_install_change_add_validate);
			} else {//拆回
				re = validateDataEmpty(mNode,meter_install_remove_add_validate);
			}
		} else {//系统派发下去的工单（有序和无序）
			if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(type)) {//新装
				re = validateDataEmpty(mNode,meter_install_new_validate);
			} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(type)) {//更换
				re = validateDataEmpty(mNode,meter_install_change_validate);
			} else {//拆回
				re = validateDataEmpty(mNode,meter_install_remove_validate);
			}
		}
		return re;
	}
	
	/**
	 * 校验换表业务中新表和老表是否一样
	 * @param mNode 安装计划反馈数据根节点
	 * @return
	 */
	public ActionResult validateRepMeter(Element mNode) {
		ActionResult re = new ActionResult(true,"");
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		String nmsn = StringUtil.getValue(mNode.elementTextTrim("NO"));
		String omsn = StringUtil.getValue(mNode.elementTextTrim("ONO"));
		String pid = StringUtil.getValue(mNode.elementTextTrim("S"));
		if(!StringUtil.isEmptyString(nmsn) && !StringUtil.isEmptyString(omsn)) {
			if(nmsn.equals(omsn)) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX20006);
				re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.nometer.same", lang, new String[]{pid,nmsn,omsn}));
			}
		}
		return re;
	}
	
	/**
	 * 验证安装计划反馈节点不存在及值是否为空
	 * @param mNode 反馈根节点
	 * @param validateNode 校验节点属性
	 * @return
	 */
	public ActionResult validateDataEmpty(Element mNode, String[] validateNode) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		ActionResult re = new ActionResult(false,"");
		for(String nodeName : validateNode) {
			Element childNode = mNode.element(nodeName);
			if(childNode == null) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10024);
				re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{nodeName}));
				return re;
			} else {
				String childNodeValue = childNode.getText();
				if(StringUtil.isEmptyString(childNodeValue)) {
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10025);
					re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.node.empty", lang, new String[]{nodeName}));
					return re;
				}
			}
		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 保存反馈数据
	 * @param insPlnFBMap
	 * @param testFBMap
	 * @param woList
	 * @param tokenList
	 * @param hhuID
	 * @param optID
	 * @param lang
	 * @return
	 */
	public ActionResult storeFeadBackData(Map<String,Map<String,List<Object>>> woPlnFBMap,
			Map<String,List<Object>> newPlnFBMap,
			Map<String,Object> testFBMap,
			List<Token> tokenList, 
			String hhuID, String optID) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		String lang = "";
		if (sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		ActionResult re = new ActionResult(false,"");
		//现场新增安装计划反馈数据列表(工单对应多个表安装计划列表)
		Map<String,List<Object>> newMeterPlnFBMap = new HashMap<String,List<Object>>();
		//系统派发的表安装计划反馈数据列表
		List<Object> meterPlnFBList = new ArrayList<Object>();
		//安装计划反馈数据（现场新增的和系统派发的）
		List<Object> allPlnFBList = new ArrayList<Object>();
		//勘察计划反馈数据列表
		List<Object> srvyPlnFBList = new ArrayList<Object>();
		//设置类反馈数据列表
		List<Object> setFBList = (List<Object>) testFBMap.get("set");
		//读取类反馈数据列表
		List<Object> readFBList = (List<Object>) testFBMap.get("read");
		//测试类反馈数据列表
		Map<String,List<Object>> testPMFBMap = (Map<String, List<Object>>) testFBMap.get("test");
		List<Object> woList = new ArrayList<Object>();
		try {
			//遍历工单安装计划反馈数据对象
			for(Map.Entry<String, Map<String,List<Object>>> entry : woPlnFBMap.entrySet()) {
				String woid = entry.getKey();
				Map<String,List<Object>> plnFBMap = entry.getValue();
				//所有系统派发的安装计划列表
				List<Object> allUnNewPlnList = new ArrayList<Object>();
				for(Map.Entry<String,List<Object>> cEntry : plnFBMap.entrySet()) {
					String plnCate = cEntry.getKey();//安装计划分类（表，集中器，采集器，勘察）
					if(plnCate.equals("meter")) {
						meterPlnFBList.addAll(cEntry.getValue());
						allUnNewPlnList.addAll(meterPlnFBList);
					} else if(plnCate.equals("dcu")) {
						
					} else if(plnCate.equals("collector")) {
						
					} else if(plnCate.equals("survey")) {
						srvyPlnFBList.addAll(cEntry.getValue());
					}
				}
				if(srvyPlnFBList.size() > 0) {//存在勘察反馈数据
					Map<String,String> woStatusMap = getWOStatus(woid,Constants.WO_TYPE_SRVY,srvyPlnFBList);
					woStatusMap.put("CURR_STAFFID", optID);
					woList.add(woStatusMap);//添加到工单列表中
				}
				if(allUnNewPlnList.size() > 0) {
					Map<String,String> woStatusMap = getWOStatus(woid,"",allUnNewPlnList);
					woStatusMap.put("CURR_STAFFID", optID);
					woList.add(woStatusMap);//添加到工单列表中
				}
			}
			if(meterPlnFBList.size() > 0) {
				allPlnFBList.addAll(meterPlnFBList);
			}
			//所有现场新增的安装计划列表
			List<Object> allNewPlnList = new ArrayList<Object>();
			for(Map.Entry<String,List<Object>> nEntry : newPlnFBMap.entrySet()) {
				String cate = nEntry.getKey();//安装计划分类（表，集中器，采集器）
				if(cate.equals("newMeter")) {
					allNewPlnList.addAll(nEntry.getValue());
				} else if(cate.equals("newDcu")) {
					
				} else if(cate.equals("newCollector")) {
					
				}
			}
			if(allNewPlnList.size() > 0) {
				allPlnFBList.addAll(allNewPlnList);
			}
			
			if(srvyPlnFBList.size() > 0) {
				//保存勘察计划反馈基本数据
				re = CommonUtil.storeSrvyFBData(srvyPlnFBList);
				if(re.isSuccess() == false) {
					String errMsg = StringUtil.getValue(re.getDataObject());
					re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
					return re;
				}
				//更新勘察计划状态
				CommonUtil.updSrvyPlnStatus(srvyPlnFBList);
				//保存勘察计划操作日志
				CommonUtil.storePlanOPLog(srvyPlnFBList);
				//更新工单状态
				CommonUtil.updWOStatus(woList);
				//保存工单操作日志
				CommonUtil.storeWOOPLog(woList);
				//保存HHU操作工单日志
				CommonUtil.storeWOLog(hhuID, optID, null, Constants.WO_LOG_STATUS_SUCC, null,
						Constants.WO_LOG_UPLOAD, woList, null);
			}
			
			if(allPlnFBList.size() > 0) {
				//保存安装计划反馈基本数据
				re = CommonUtil.storeMeterFBBasicData(allPlnFBList);
				if(re.isSuccess() == false) {
					String errMsg = StringUtil.getValue(re.getDataObject());
					re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
					return re;
				}
				//保存安装计划反馈测试数据
				CommonUtil.storeMeterFBTestData(setFBList,readFBList,testPMFBMap);
				if(meterPlnFBList.size() > 0) {
					//更新安装计划状态
					CommonUtil.updPlnStatus(meterPlnFBList);
					//保存安装计划操作日志
					CommonUtil.storePlanOPLog(meterPlnFBList);
					//更新工单状态
					CommonUtil.updWOStatus(woList);
					//保存工单操作日志
					CommonUtil.storeWOOPLog(woList);
					//更新token
					CommonUtil.UptToken(tokenList);
					if(allNewPlnList.size() > 0) {
						//保存现场新增工单
						CommonUtil.storeWO(woList,allNewPlnList);
					}
					//保存HHU操作工单日志
					CommonUtil.storeWOLog(hhuID, optID, null, Constants.WO_LOG_STATUS_SUCC, null,
							Constants.WO_LOG_UPLOAD, woList, null);
				}
			}
			re.setSuccess(true);
		} catch(Exception e) {
			logger.error("upload work order error: ", e);
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10010);
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.store.fail", lang);
			re.setDataObject(getUploadWOFailInfo(errMsg,null,woList));
		}
		return re;
	}
	
	/**
	 * 获取工单状态Map对象（已反馈和已完成两种）
	 * @param woid 工单id
	 * @param woType 工单类型
	 * @param insPlnList 反馈安装计划数据列表
	 * @return
	 */
	public Map<String,String> getWOStatus(String woid, String woType, List<Object> insPlnList) {
		Map<String,String> woStatusMap = new HashMap<String,String>();
		woStatusMap.put("woid", woid);
		String[] status = new String[]{Constants.PLN_STATUS_DISPATCHED,
				Constants.PLN_STATUS_FAIL};
		int plnCount = 0;
		if(Constants.WO_TYPE_SRVY.equals(woType)) {//勘察工单
			//获取系统中工单对应的已派工和失败的勘察计划数量
			plnCount = CommonUtil.getSrvyPlnCount(woid, status);
		} else {
			//获取系统中工单对应的已派工和失败的安装计划数量
			plnCount = CommonUtil.getInsPlnCount(woid, status);
		}
		int fbPlnCount=0;//某一工单计划反馈数量
		boolean existFailPln = false;//存在反馈失败的计划
		for(Object obj : insPlnList) {//返回非失败的反馈数量
			Map<String,String> insPlnFBMap = (Map<String, String>) obj;
			String sts = insPlnFBMap.get("STS");
			if(Constants.PLN_FB_FAIL.equals(sts)) {
				existFailPln = true;
				break;
			} else {
				fbPlnCount++;
			}
		}
		if(existFailPln == true) {//存在反馈失败的计划
			woStatusMap.put("status", Constants.WO_STATUS_FEEDBACKED);
		} else {
			if(fbPlnCount >= plnCount) {//反馈的计划成功或异常数量大于等于系统中未反馈的计划数量
				woStatusMap.put("status", Constants.WO_STATUS_COMPLETED);
			} else {
				woStatusMap.put("status", Constants.WO_STATUS_FEEDBACKED);
			}
		}
		return woStatusMap;
	}
	
	public Map<String,Map<String,List<Object>>> getVParamData(List<Object> basicDataList) {
		//版本对应的方案数据对象（读取类，设置类，测试类）
		Map<String,Map<String,List<Object>>> vParamMap = new HashMap<String,Map<String,List<Object>>>();
		List<Object> tempBasicDataList = new ArrayList<Object>();
		tempBasicDataList.addAll(basicDataList);
		for(int i = 0; i < tempBasicDataList.size(); i++) {
			Object obj = tempBasicDataList.get(i);
			Map<String,List<Object>> paramMap = new HashMap<String,List<Object>>();//方案数据对象
			List<Object> readList = new ArrayList<Object>();
			List<Object> setList = new ArrayList<Object>();
			List<Object> testList = new ArrayList<Object>();
			String key = "";
			if(obj instanceof ParamData) {
				ParamData paramD = (ParamData) obj;
				key = paramD.getVerId() + ":" + paramD.getModel();
				String pmType = paramD.getPm_type();//方案分类
				if(pmType.equals("0")) {
					readList.add(obj);
				} else if(pmType.equals("1")) {
					setList.add(obj);
				} 
			} else if(obj instanceof TestParam) {
				TestParam testParam = (TestParam) obj;
				key = testParam.getVerid() + ":" + testParam.getM_model();
				testList.add(obj);
			}
			for(int j = i+1; j < tempBasicDataList.size(); j++) {
				Object nextObj = tempBasicDataList.get(j);
				if(nextObj instanceof ParamData) {
					ParamData nextParamD = (ParamData) nextObj;
					String nextKey = nextParamD.getVerId() + ":" + nextParamD.getModel(); 
					String nextPmType = nextParamD.getPm_type();
					if(key.equals(nextKey)) {
						if(nextPmType.equals("0")) {
							readList.add(nextParamD);
						} else if(nextPmType.equals("1")) {
							setList.add(nextParamD);
						} 
						tempBasicDataList.remove(nextObj);
						j--;
					}
				} else if(nextObj instanceof TestParam) {
					TestParam nextTestParam = (TestParam) nextObj;
					String nextKey = nextTestParam.getVerid() + ":" + nextTestParam.getM_model(); 
					if(key.equals(nextKey)) {
						testList.add(nextObj);
						tempBasicDataList.remove(nextObj);
						j--;
					}
				}
			}
			if(readList.size() > 0) {
				paramMap.put("read", readList);
			}
			if(setList.size() > 0) {
				paramMap.put("set", setList);
			}
			if(testList.size() > 0) {
				paramMap.put("test", testList);
			}
			if(!StringUtil.isEmptyString(key)) {
				vParamMap.put(key, paramMap);
			}
		}
		return vParamMap;
	}
	
	/**
	 * 验证输入的xml
	 * @param root
	 * @param flag 标志操作类型 下载，下载反馈，上传
	 * @param lang 语言
	 * @return
	 */
	public ActionResult valideInputXml(Element root, String flag, String lang) {
		ActionResult re = new ActionResult(true,"");
		Element hidNode = root.element("HID");//HHUID节点
		Element ridNode = root.element("RID");//请求ID节点
		Element pwdNode = root.element("PWD");//操作员密码节点
		Element cNode = root.element("C");//操作结果节点
		Element optNode = null;
		String hid = "";
		String rid = "";
		String optID = "";
		String optNodeName = "";
		if("upload".equals(flag)) {//代表工单上传
			optNode = root.element("OPTID");
			optNodeName = "OPTID";
		} else {
			optNode = root.element("ID");
			optNodeName = "ID";
		}
		if(!"login".equals(flag)) {//非登录操作类型，需要验证hhu节点
			if(hidNode == null) {//HHUID节点为不存在
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10004);
				String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{"HID"});
				re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
				return re;
			} else {//HHUID节点存在
				hid = hidNode.getTextTrim();
				if(StringUtil.isEmptyString(hid)) {//HHUID节点值为空
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10005);
					String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.empty", lang, new String[]{"HID"});
					re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
					return re;	
				}
			}
			String hhuStatus = CommonUtil.getHhuInit(hid);
			if(StringUtil.isEmptyString(hhuStatus)) {
				//不存在HHU
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10006);
				String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.hhuNode.illegal", lang, new String[]{hid});
				re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
				return re;
			}
		}
		
		if(optNode == null) {//验证操作员id节点
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10007);
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{optNodeName});
			re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
			return re;
		} else {
			optID = optNode.getTextTrim();
		}
		
		if(StringUtil.isEmptyString(optID)) {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10008);
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.empty", lang, new String[]{optNodeName});
			re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
			return re;	
		} else {
			if("upload".equals(flag)) {
				List<Object> optInfList = baseDAOIbatis.queryForList(sqlId + "getOprInfo", optID);
				if(optInfList.size() == 0) {
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX20001);
					String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.optNode.invalid", lang, new String[]{optID});
					re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
					return re;	
				}
			}
		}
		
		if("down".equals(flag) || "downFB".equals(flag) ) {//下载和下载反馈验证请求id节点
			if(ridNode == null) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10009);
				return re;
			} else {
				rid = ridNode.getTextTrim();
				if(StringUtil.isEmptyString(rid)) {
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10011);
					return re;	
				}
			}
		}
		
		if("down".equals(flag) || "login".equals(flag)) {//下载和登录操作需要验证密码节点
			if(pwdNode == null) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10012);
				return re;
			} else {
				if(StringUtil.isEmptyString(pwdNode.getTextTrim())) {
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10013);
					return re;	
				}
			}
		}
		
		if("downFB".equals(flag)) {//下载反馈
			if(cNode == null) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10027);
				return re;
			} else {
				if(StringUtil.isEmptyString(cNode.getTextTrim())) {
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10028);
					return re;	
				}
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("rid", rid);
			map.put("hhuid", hid);
			map.put("optid", optID);
			map.put("rst", Constants.DATAUPDATE_LOGSTAT_UNKNOW);
			List<Object> bRidList = baseDAOIbatis.queryForList(sqlId + "validateRidByBasic", map);
			List<Object> woRidList = baseDAOIbatis.queryForList(sqlId + "validateRidByWO", map);
			if(bRidList.size() == 0 && woRidList.size() == 0) {//不存在
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10014);
				return re;
			} 
		}
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 解密
	 * @param content 待解密的内容
	 * @param key 密钥
	 * @return
	 */
	public ActionResult decrypt(String content, String key) {
		ActionResult re = new ActionResult(false,"");
		String str = "";
		try {
			str = SymmetricCrypto.hhuDecrypt(content,key);
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10015);
			return re;
		}
		if(str == null) {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10015);
		} else {
			re.setSuccess(true);
			re.setDataObject(str);
		}
		return re;
	}
	
	/**
	 * 返回消息
	 * @param rst 结果 1=成功，0=失败
	 * @param msg 失败提示编码
	 * @return
	 */
	public String getReturnMsg(String rst, String msg) {
		//创建返回的XML
		Document doc = DocumentHelper.createDocument();
		//创建根节点
		Element root = doc.addElement("R");
		root.addElement("C").addText(Constants.HHU_FDM_FAIL);
		root.addElement("MSG").addText(msg);
		logger.debug(doc.getRootElement().asXML());
		return doc.getRootElement().asXML();
	}
	
	/**
	 * 验证工单反馈中sim卡号是否已经在系统中使用
	 * @param sim sim卡号
	 * @param pid 计划号
	 * @param 语言
	 * @return
	 */
	public ActionResult checkSim(String sim, String pid, String lang) {
		ActionResult re = new ActionResult(true,"");
		if(!StringUtil.isEmptyString(sim)) {//绑定sim卡情况下，验证其他表是否已经绑定
			Map<String,String> mMap = new HashMap<String,String>();
			mMap.put("simNo", sim);
			mMap.put("status", Constants.METER_STATUS_INS);
			List<Object> existSimList = baseDAOIbatis.queryForList(sqlId + "existSimInMeter", mMap);
			if(existSimList.size() > 0) {
				re.setMsg(MsgCodeConstants.HX20005);
				re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.sim.isUsed", lang, new String[]{pid,sim}));
				re.setSuccess(false);
				return re;
			}
		}
		return re;
	}
	
	/**
	 * 验证老表号是否属于当前户号
	 * @param cno 户号
	 * @param msn 老表号
	 * @param pid 计划号
	 * @param lang 语言
	 * @return
	 */
	public ActionResult checkOldMeter(String cno,String msn,String pid,String lang) {
		ActionResult re = new ActionResult(true,"");
		if(!StringUtil.isEmptyString(msn)) {//反馈老表号不为空
			//查询老表
			List<Object> meterList = baseDAOIbatis.queryForList(sqlId + "getMeterByMsn", msn);
			if(meterList.size() > 0) {//系统中存在表
				Map<String,String> meterMap = (Map<String, String>) meterList.get(0);
				String o_cno = StringUtil.getValue(meterMap.get("CNO"));//老表所在户号
				if(cno.equals(o_cno)) {//户号与老表号所在的户号相同
					re.setSuccess(true);
				} else {
					re.setMsg(MsgCodeConstants.HX20007);
					re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.meter.notInCust", lang, new String[]{pid,msn,cno}));
					re.setSuccess(false);
				}
			} else {//系统中不存在表
				re.setSuccess(true);
			}
		} else {
			re.setSuccess(true);
		}
		return re;
	}
	
	/**
	 * 验证新表号是否在系统中已装出
	 * @param msn 新表号
	 * @param pid 计划号
	 * @param lang 语言
	 * @return
	 */
	public ActionResult checkNewMeter(String msn,String pid,String lang) {
		ActionResult re = new ActionResult(true,"");
		//查询新表
		List<Object> meterList = baseDAOIbatis.queryForList(sqlId + "getMeterByMsn", msn);
		if(meterList.size() > 0) {//系统中存在表
			Map<String,String> meterMap = (Map<String, String>) meterList.get(0);
			String status = StringUtil.getValue(meterMap.get("STATUS"));//表状态
			if(Constants.METER_STATUS_INS.equals(status)) {//当前表已装出
				re.setMsg(MsgCodeConstants.HX20008);
				re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.meter.installed", lang, new String[]{pid,msn}));
				re.setSuccess(false);
			} else {
				re.setSuccess(true);
			}
		} else {
			re.setSuccess(true);
		}
		return re;
	}
	
	/**
	 * 验证装表的户号，变压器号单位是否一致
	 * @param cno 户号
	 * @param msn 新表号
	 * @param tfNo 变压器id
	 * @param pid 计划号
	 * @param lang 语言
	 * @return
	 */
	public ActionResult checkTFAndCustUnit(String cno, String msn, String tfNo, String pid, String lang) {
		ActionResult re = new ActionResult(true,"");
		//获取系统中已存在用户信息
		List<Object> custList = baseDAOIbatis.queryForList(sqlId + "getCustByCno", cno);
		//获取系统中已存在变压器信息
		List<Object> tfList = baseDAOIbatis.queryForList(sqlId + "getTFByTFNO", tfNo);
		if(tfList.size() > 0) {//系统中存在变压器
			Map<String,String> tfMap = (Map<String, String>) tfList.get(0);
			String t_uid = StringUtil.getValue(tfMap.get("UID"));
			if(custList.size() > 0) {//系统中存在户号
				Map<String,String> custMap = (Map<String, String>) custList.get(0);
				String c_uid = StringUtil.getValue(custMap.get("UID"));
				Map<String,String> param = new HashMap<String,String>();
				param.put("CNO", cno);
				param.put("MSN", msn);
				param.put("STATUS", Constants.METER_STATUS_INS);
				//查询当前户号下是否装了其他表
				List<Object> otherMeterList = baseDAOIbatis.queryForList("insFb." + "getMeterByCno", param);
				if(otherMeterList.size() > 0) {
					if(t_uid.equals(c_uid)) {//变压器与已装其他表的户号所属单位一致
						re.setSuccess(true);
					} else {
						re.setMsg(MsgCodeConstants.HX20009);//变压器单位与已装其他表的户号单位不一致
						re.setDataObject(I18nUtil.getText("mainModule.ifs.uploadWO.uid.notSame", lang, new String[]{pid,cno,tfNo}));
						re.setSuccess(false);
					}
				}
			}
		}
		return re;
	}
	
	/**
	 * 设置计划操作时间（过滤掉重复日期）
	 * @param opDate 计划操作日期
	 * @param opDateList 计划操作日期列表
	 */
	public void setPlnOpDate(String opDate, List<Object> opDateList) {
		boolean exist = false;//标志当前日期在安装计划日期列表中是否存在，false=不存在，true=存在
		for(Object obj : opDateList) {
			String tempOpDate = String.valueOf(obj);
			if(opDate.equals(tempOpDate)) {//当前日期在安装计划日期列表中存在
				exist = true;
				break;
			}
		}
		if(!exist) {//安装计划日期列表中不存在当前日期
			opDateList.add(opDate);//将安装计划日期添加列表中
		}
	}
	
	/**
	 * 保存掌机上传工单失败日志信息
	 * @param obj 工单上传失败对象信息
	 * @param obj 工单上传xml文档对象
	 */
	public void storeHHUOPWOFailLog(Object obj,Document doc) {
		if(obj != null) {
			Map<String,Object> map = (Map<String, Object>) obj;
			Element root = doc.getRootElement();
	    	String hhuID = root.elementTextTrim("HID");//掌机ID
	    	String optID = root.elementTextTrim("OPTID");//操作员ID
	    	String errMsg = StringUtil.getValue(map.get("errMsg"));
	    	List<Object> woList = new ArrayList<Object>();
	    	Object woObj = map.get("wos");
	    	if(woObj != null) {
	    		woList = (List<Object>) map.get("wos");
	    	}
	    	CommonUtil.storeWOLog(hhuID, optID, null, Constants.WO_LOG_STATUS_FAIL, errMsg, Constants.WO_LOG_UPLOAD, woList, null);
		}
	}
	
	/**
	 * 获取工单上传失败信息
	 * @param errMsg 失败信息
	 * @param woid 工单号
	 * @param woList 工单列表
	 * @return
	 */
	public Map<String,Object> getUploadWOFailInfo(String errMsg, String woid, List<Object> woList) {
		Map<String,Object> failMap = new HashMap<String,Object>();
		if(woid != null && !woid.equals("")) {
			//失败工单列表
			List<Object> failWOList = new ArrayList<Object>();
			//失败工单Map对象
			Map<String,String> failWOMap = new HashMap<String,String>();
			failWOMap.put("woid", woid);
			failWOList.add(failWOMap);
			failMap.put("wos", failWOList);
		} else {
			if(woList != null && woList.size() > 0) {
				failMap.put("wos", woList);
			} else {
				failMap.put("wos", null);
			}
		}
		failMap.put("errMsg", errMsg);
		return failMap;
	}
	
	/**
	 * 获取安装计划状态
	 * @param pid 计划号
	 * @return
	 */
	public String getPlanStatus(String pid) {
		String status = "";
		List<Object> mPlanList = CommonUtil.getMeterPlnByPid(pid);
		if(mPlanList.size() > 0) {//存在安装计划
			Map<String,String> mPlanMap = (Map<String, String>) mPlanList.get(0);
			status = StringUtil.getValue(mPlanMap.get("STATUS"));
		}
		return status;
	}
	
	/**
	 * 获取勘察计划状态
	 * @param pid 计划号
	 * @return
	 */
	public String getSrvyPlanStatus(String pid) {
		String status = "";
		Map<String,String> srvyPlanMap = CommonUtil.getSrvyPlnByPid(pid);
		if(srvyPlanMap != null) {
			status = StringUtil.getValue(srvyPlanMap.get("STATUS"));
		}
		return status;
	}
	
	/**
	 * 解析勘察反馈数据
	 * @param srvyNode 勘察反馈计划根节点
	 * @param woid 工单号
	 * @param lang 国际化语言
	 * @return
	 */
	public ActionResult parseSrvyFBXml(Element srvyNode, String woid, String lang) {
		ActionResult re = new ActionResult(true,"");
		Map<String, String> map = new HashMap<String, String>(); //每个对象属性Map
		if(srvyNode != null) {
			re = validateDataEmpty(srvyNode,srvy_plan_fb_validate);
			if(re.isSuccess() == false) {
				String errMsg = StringUtil.getValue(re.getDataObject());
				re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
				return re;
			}
			//获取勘察计划状态
			String pid = srvyNode.elementTextTrim("S");
			String plnStatus = getSrvyPlanStatus(pid);//查询反馈前计划状态
			if(Constants.PLN_STATUS_SUCCESS.equals(plnStatus) ||
					Constants.PLN_STATUS_PAID.equals(plnStatus) || 
					Constants.PLN_STATUS_UNPAID.equals(plnStatus)) {
				//成功或异常的安装计划，循环下一条执行（考虑到掌机上传工单因网络问题超时情况）
			} else {
				map.putAll(Xml2Map(srvyNode, srvy_plan_fb));
				String rst = srvyNode.elementTextTrim("STS");//工单反馈操作结果
				if(Constants.PLN_FB_SUCCESS.equals(rst)) {
					map.put("PLNSTATUS", Constants.PLN_STATUS_SUCCESS);
				} else if(Constants.PLN_FB_FAIL.equals(rst)) {
					map.put("PLNSTATUS", Constants.PLN_STATUS_FAIL);
				}
				map.put("PID", pid);
				map.put("DEVTYPE", Constants.DEV_TYPE_UNKNOW);
				map.put("CURR_STAFFID", StringUtil.getValue(srvyNode.elementTextTrim("OPT")));
			}
			re.setDataObject(map);
			re.setSuccess(true);
		} else {
			re.setSuccess(false);
			re.setMsg(MsgCodeConstants.HX10029);
			String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.notExist", lang, new String[]{"MS"});
			re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
		}
		return re;
	}
	
	/**
	 * 记录计划下载后信息
	 * @param oldPlnList 下载前计划信息列表
	 * @param status 计划状态
	 * @param optId 当前操作员id
	 * @param deviceType 设备类型
	 * @param newPlnList 下载后计划信息列表
	 */
	public void recordDownLoadPln(List<Object> oldPlnList, String status, String optId, String deviceType, List<Object> newPlnList) {
		if(oldPlnList!=null&&oldPlnList.size()>0){
			for(int i = 0; i < oldPlnList.size(); i++) {
				Map<String,String> oldPlnMap = (Map<String, String>) oldPlnList.get(i);
				Map<String,String> newPlnMap = new HashMap<String,String>();
				newPlnMap.put("PID", oldPlnMap.get("PID"));
				newPlnMap.put("PLNSTATUS", status);
				newPlnMap.put("CURR_STAFFID", optId);
				newPlnMap.put("DEVTYPE", deviceType);
				newPlnList.add(newPlnMap);
			}
		}
		
	}
	
	/**
	 * 记录计划下载后客户的状态
	 * @param oldPlnList 下载前计划信息列表
	 * @param status 客户状态
	 * @param custList 客户信息列表
	 */
	public void recordCustStatus(List<Object> oldPlnList, String status, List<Object> custList) {
		if(oldPlnList!=null&&oldPlnList.size()>0){
			for(int i = 0; i < oldPlnList.size(); i++) {
				Map<String,String> oldPlnMap = (Map<String, String>) oldPlnList.get(i);
				Map<String,String> map = new HashMap<String,String>();
				map.put("CNO", oldPlnMap.get("CNO"));
				map.put("DSTATUS", status);
				custList.add(map);
			}
		}
	}
	
	/**
	 * 根据工单id，获取工单类型
	 * @param woid 工单id
	 * @param lang 国际化语言
	 * @return
	 */
	public ActionResult getWorkorderType(String woid, String lang) {
		ActionResult re = new ActionResult(true,"");
		String woType = "";//工单类型
		if("00000000".equals(woid)) {//现场新增工单
			woType = Constants.WO_TYPE_NEW;
			re.setSuccess(true);
			re.setDataObject(woType);
		} else {
			if(StringUtil.isEmptyString(woid)) {
				re.setSuccess(false);
				re.setMsg(MsgCodeConstants.HX10019);
				String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.node.empty", lang, new String[]{"WO"});
				re.setDataObject(getUploadWOFailInfo(errMsg,null,null));
			} else {
				WorkOrder workOrder = CommonUtil.getWOInfo(woid);
				if(workOrder == null) {//工单不存在
					re.setSuccess(false);
					re.setMsg(MsgCodeConstants.HX10020);
					String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.workOrder.notExist", lang, new String[]{woid});
					re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
				} else {//工单存在，验证工单状态
					String woStatus = StringUtil.getValue(workOrder.getStatus());
					if(Constants.WO_STATUS_REVOKED.equals(woStatus) || 
							Constants.WO_STATUS_ASSIGNED.equals(woStatus)) {//已撤销和已分配
						re.setSuccess(false);
						re.setMsg(MsgCodeConstants.HX20003);
						String errMsg = I18nUtil.getText("mainModule.ifs.uploadWO.workOrder.invalidate", lang, new String[]{woid});
						re.setDataObject(getUploadWOFailInfo(errMsg,woid,null));
					} else {
						woType = workOrder.getType();
						re.setSuccess(true);
						re.setDataObject(woType);
					}
				}
			}
		}
		return re;
	}
}
