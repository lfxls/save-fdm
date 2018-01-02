package cn.hexing.ami.service.main.srvyMgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.XmlUtil;
import cn.hexing.ami.web.listener.AppEnv;

public class SrvyFbJob {
	Logger logger = Logger.getLogger(SrvyFbJob.class.getName());
	
	public synchronized void srvyFeedback(){
		logger.info("survey feedback job start...");
		try {
			SrvyFbManagerInf srvyFbManager = (SrvyFbManagerInf) SpringContextUtil.getBean("srvyFbManager");
			//获取未同步或同步失败的勘察数据
			String[] status = new String[]{Constants.PLN_TOVENDING_UNSYNC,Constants.PLN_TOVENDING_SYNCFAIL};
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("status", status);
			List<Object> srvyFbList = srvyFbManager.getSrvyFbDataList(param);
			String inXml = genSrvyFeedBackXML(srvyFbList);
			logger.debug(inXml);
			//调用MDC webservice 接口
			if(srvyFbList != null && srvyFbList.size() >= 1) {
				String outXml = pushSrvyFBData(inXml);//VENDING接口返回信息
				logger.debug("VENDING return result:" + outXml);
				if(!StringUtil.isEmptyString(outXml)) {
					Document document = null;
					try {
						document = XmlUtil.GetDocument(outXml);
						procXml(document);//处理MDC返回的xml信息
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("process vengding system return value error:" + e.getMessage());
					}
				} else {
					logger.info("vending system return value error!");
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.error("survey feedback job error:" + ex.getCause());
		}
		logger.info("survey feedback job finish...");
	}
	
	public String pushSrvyFBData(String inXML) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String url = sysMap.get("vending_webService_url");
		long timeOut =Long.parseLong(sysMap.get("vending_webService_timeOut"));
		String returnValue = "";
		if(!StringUtil.isEmptyString(url)) {
			JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance(); 
			Client client = clientFactory.createClient(url); 
			
			HTTPConduit conduit = (HTTPConduit) client.getConduit();  
			HTTPClientPolicy policy = new HTTPClientPolicy();  
			policy.setConnectionTimeout(5000);  
			policy.setReceiveTimeout(timeOut);  
			conduit.setClient(policy); 
			
			Object[] result;
			try {
				result = (Object[]) client.invoke("CalculateInstallCharge", inXML);
				returnValue = String.valueOf(result[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}
	
	/**
	 * 生成勘察计划反馈数据xml
	 * @param srvyFBDataList 勘察计划反馈数据
	 * @return
	 */
	protected String genSrvyFeedBackXML(List<Object> srvyFbDataList) {
		String inXml = "";
		//创建XML
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		
		for(int i = 0; i < srvyFbDataList.size(); i++) {
			Map<String,String> srvyFbDataMap = (Map<String, String>) srvyFbDataList.get(i);
			Element woNode = root.addElement("WO");
			woNode.addElement("CONS").addText(srvyFbDataMap.get("CNO"));
			woNode.addElement("PID").addText(srvyFbDataMap.get("PID"));
			woNode.addElement("WOID").addText(srvyFbDataMap.get("WOID"));
			woNode.addElement("CBN").addText(srvyFbDataMap.get("CBNUM"));
			woNode.addElement("LX").addText(StringUtil.getValue(srvyFbDataMap.get("CABLE_TYPE")));
			woNode.addElement("LL").addText(StringUtil.getValue(srvyFbDataMap.get("CABLE_LEN")));
		}
		inXml = reDoc.asXML();
		return inXml;
	}
	
	/**
	 * 处理MDC返回的xml信息
	 * @param document
	 */
	protected void procXml(Document document) {
		List<Object> updSrvyFbDataList = new ArrayList<Object>();
		Element root = document.getRootElement();
		if(root != null) {
			List<Element> woNodeList = root.elements();
			for(int i = 0; i < woNodeList.size(); i++) {
				Element woNode = woNodeList.get(i);
				if(woNode != null) {
					Map<String,String> updSrvyFbDataMap = new HashMap<String,String>();
					String pid = woNode.elementTextTrim("PID");
					String result = woNode.elementTextTrim("RESULT");
					String tCharge = woNode.elementTextTrim("TC");
					updSrvyFbDataMap.put("pid", pid);
					updSrvyFbDataMap.put("tCharge", tCharge);
					if("1".equals(result)) {
						updSrvyFbDataMap.put("status", Constants.PLN_TOVENDING_SYNC);
					} else if("2".equals(result)) {
						updSrvyFbDataMap.put("status", Constants.PLN_TOVENDING_SYNCFAIL);
					}
					updSrvyFbDataList.add(updSrvyFbDataMap);
				}
			}
		}
		updSrvyFB(updSrvyFbDataList);//更新勘察计划反馈数据
	}
	
	/**
	 * 更新勘察计划反馈数据
	 * @param updSrvyFbDataList 勘察计划反馈数据
	 */
	protected void updSrvyFB(List<Object> updSrvyFbDataList) {
		SrvyFbManagerInf insFbManager = (SrvyFbManagerInf) SpringContextUtil.getBean("srvyFbManager");
		insFbManager.updSrvyFb(updSrvyFbDataList);
	}
}
