package cn.hexing.ami.service.ifs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.XmlUtil;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description FDM对VENDING的数据处理
 * @author xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-10-16
 * @version FDM2.0
 */
public class STVendingProcess implements STVendingProcessInf{

	private static Logger logger = Logger.getLogger(STVendingProcess.class.getName());

	private BaseDAOIbatis baseDAOIbatis;
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 更新勘察计划状态（取消缴费，已缴费）
	 * @param inXML
	 * @return
	 */
	public String saveSrvyPlnStatus(String inXML) {
		logger.debug(inXML);
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String lang = "en_US";
		if(sysMap != null) {
			lang = sysMap.get("interfaceLang");
		}
		//创建输入的XML文档对象
		Document document = null;
		List<Object> validDataList = new ArrayList<Object>();//有效数据
		List<Object> invalidDataList = new ArrayList<Object>();//无效数据
		String reXML = "";
		try {
			document = XmlUtil.GetDocument(inXML);
			Element inRoot = document.getRootElement();
			//校验输入的xml文件，筛选出有效数据和无效数据
			checkInputXml(inRoot,lang,validDataList,invalidDataList);
			if(validDataList.size() > 0) {
				CommonUtil.updSrvyPlnStatus(validDataList);
				CommonUtil.storePlanOPLog(validDataList);
			}
			reXML = getReturnMsg(validDataList,invalidDataList);
		} catch(Exception e) {
			logger.error(e.fillInStackTrace());
			reXML = getExceptionMsg(e.getMessage());
		}
		return reXML;
	}
	
	/**
	 * 返回异常信息
	 * @param msg 信息
	 * @return
	 */
	public String getExceptionMsg(String msg) {
		//创建返回的XML
		Document doc = DocumentHelper.createDocument();
		//创建根节点
		Element root = doc.addElement("R");
		Element srvyNode = root.addElement("SRVY");
		srvyNode.addElement("S").addText("");
		srvyNode.addElement("C").addText(Constants.HHU_FDM_FAIL);
		srvyNode.addElement("MSG").addText(msg);
		logger.debug(doc.getRootElement().asXML());
		return doc.getRootElement().asXML();
	}
	
	/**
	 * 返回消息
	 * @param validDataList 有效数据
	 * @param invalidDataList 无效数据
	 * @return
	 */
	public String getReturnMsg(List<Object> validDataList, List<Object> invalidDataList) {
		//创建返回的XML
		Document doc = DocumentHelper.createDocument();
		//创建根节点
		Element root = doc.addElement("R");
		for(int i = 0; i < validDataList.size(); i++) {
			Map<String,String> validDataMap = (Map<String, String>) validDataList.get(i);
			Element srvyNode = root.addElement("SRVY");
			srvyNode.addElement("S").addText(validDataMap.get("PID"));
			srvyNode.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
			srvyNode.addElement("MSG").addText("");
		}
		for(int j = 0; j < invalidDataList.size(); j++) {
			Map<String,String> invalidDataMap = (Map<String, String>) invalidDataList.get(j);
			Element srvyNode = root.addElement("SRVY");
			srvyNode.addElement("S").addText(invalidDataMap.get("S"));
			srvyNode.addElement("C").addText(Constants.HHU_FDM_FAIL);
			srvyNode.addElement("MSG").addText(invalidDataMap.get("MSG"));
		}
		logger.debug(doc.getRootElement().asXML());
		return doc.getRootElement().asXML();
	}
	
	/**
	 * 检查VENDING系统输入的xml数据
	 * @param node 根节点
	 * @param lang 国际化语言
	 * @param validDataList 有效数据
	 * @param invalidDataList 无效数据
	 * @return
	 */
	public void checkInputXml(Element node, String lang,
			List<Object> validDataList, List<Object> invalidDataList) {
		Map<String,String> dataMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_DATAMAP);
		
		List<Element> srvyNodeList = node.elements("SRVY");
		if(srvyNodeList.size() > 0) {
			for(int i = 0; i < srvyNodeList.size(); i++) {
				StringBuffer errMsgBuf = new StringBuffer();
				Map<String,String> map = new HashMap<String,String>();
				map.put("CURR_STAFFID", "Vending");
				Element srvyNode = srvyNodeList.get(i);
				Element sNode = srvyNode.element("S");
				Element stNode = srvyNode.element("ST");
				boolean err = false;//标记当前节点数据是否存在异常
				ActionResult sCheck = checkSNode(sNode,lang);
				if(sCheck.isSuccess() == false) {//校验不通过
					err = true;
					errMsgBuf.append(sCheck.getMsg()).append(",");
					map.put("S", StringUtil.getValue(sCheck.getDataObject()));
				} else {
					map.put("S", srvyNode.elementTextTrim("S"));
				}
				ActionResult stCheck = checkSTNode(stNode,lang);
				if(stCheck.isSuccess() == false) {//校验不通过
					err = true;
					errMsgBuf.append(stCheck.getMsg());
				}
				if(err == false) {
					map.put("PID", srvyNode.elementTextTrim("S"));
					map.put("PLNSTATUS", dataMap.get("srvy_paid_" + srvyNode.elementTextTrim("ST")));
					validDataList.add(map);
				} else {
					map.put("MSG", errMsgBuf.toString());
					invalidDataList.add(map);
				}
			}
		} else {
			Map<String,String> map = new HashMap<String,String>();
			map.put("MSG", I18nUtil.getText("mainModule.ifs.stVending.node.notExist", lang, new String[]{"SRVY"}));
			map.put("S", "");
			invalidDataList.add(map);
		}
	}
	
	/**
	 * 检查唯一序列节点
	 * @param node 节点
	 * @param lang 国际化语言
	 * @return
	 */
	public ActionResult checkSNode(Element node, String lang) {
		ActionResult re = new ActionResult(true,"");
		String errMsg = "";
		if(node == null) {
			errMsg = I18nUtil.getText("mainModule.ifs.stVending.node.notExist", lang, new String[]{node.getName()});
			re.setDataObject("");
		} else {
			String nodeValue = node.getTextTrim();
			if(StringUtil.isEmptyString(nodeValue)) {
				errMsg = I18nUtil.getText("mainModule.ifs.stVending.node.empty", lang, new String[]{node.getName()});
				re.setDataObject("");
			} else {
				int svryCount = CommonUtil.existSrvyPln(nodeValue);
				if(svryCount == 0) {
					errMsg = I18nUtil.getText("mainModule.ifs.stVending.srvyPlan.notExist", lang, new String[]{nodeValue});
					re.setDataObject(nodeValue);
				}
			}
		}
		if(!StringUtil.isEmptyString(errMsg)) {
			re.setMsg(errMsg);
			re.setSuccess(false);
		}
		return re;
	}
	
	/**
	 * 检查状态节点
	 * @param node 节点
	 * @param lang 国际化语言
	 * @return
	 */
	public ActionResult checkSTNode(Element node, String lang) {
		ActionResult re = new ActionResult(true,"");
		String errMsg = "";
		if(node == null) {
			errMsg = I18nUtil.getText("mainModule.ifs.stVending.node.notExist", lang, new String[]{node.getName()});
		} else {
			String nodeValue = node.getTextTrim();
			if(StringUtil.isEmptyString(nodeValue)) {
				errMsg = I18nUtil.getText("mainModule.ifs.stVending.node.empty", lang, new String[]{node.getName()});
			} else {
				if(!Constants.VENDING_FDM_UNPAID.equals(nodeValue) 
						&& !Constants.VENDING_FDM_PAID.equals(nodeValue)) {
					errMsg = I18nUtil.getText("mainModule.ifs.stVending.node.data.illegal", lang, new String[]{node.getName()});
				}
			}
		}
		if(!StringUtil.isEmptyString(errMsg)) {
			re.setSuccess(false);
			re.setMsg(errMsg);
		}
		return re;
	}
}
