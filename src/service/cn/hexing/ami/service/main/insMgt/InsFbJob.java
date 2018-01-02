package cn.hexing.ami.service.main.insMgt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MsgCodeConstants;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.XmlUtil;
import cn.hexing.ami.web.listener.AppEnv;

public class InsFbJob {
	Logger logger = Logger.getLogger(InsFbJob.class.getName());
	
	public synchronized void insFeedback(){
		logger.info("install feedback job start...");
		try {
			InsFbManagerInf insFbManager = (InsFbManagerInf) SpringContextUtil.getBean("insFbManager");
			//获取未同步给MDC或同步失败的反馈成功或异常的安装计划反馈数据
			List<Object> meterPlnFBDataList = insFbManager.getMeterPlnFBData();
			//获取未同步给MDC或同步失败的反馈成功或异常的安装计划反馈对应的表底度数据
			List<Object> meterFBEvDataList = insFbManager.getMeterEvData();
			String inXml = genInsFeedBackXML(meterPlnFBDataList,meterFBEvDataList);
			logger.debug(inXml);
			//调用MDC webservice 接口
			String outXml = pushPlnFBData(inXml);//MDC接口返回信息
			if(!StringUtil.isEmptyString(outXml)) {
				Document document = null;
				try {
					document = XmlUtil.GetDocument(outXml);
					procXml(document,meterPlnFBDataList);//处理MDC返回的xml信息
				} catch (Exception e) {
					logger.error("process mdc return value error:" + e.getMessage());
				}
			} else {
				logger.info("MDC return value error!");
			}
		} catch(Exception ex) {
			logger.error("install feedback job error:" + ex.getCause());
		}
		logger.info("install feedback job finish...");
	}
	
	public String pushPlnFBData(String inXML) {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		String url = sysMap.get("mdc_webService_url");
		String returnValue = "";
		if(!StringUtil.isEmptyString(url)) {
			JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
			svr.setServiceClass(InstallationFBServiceInf.class);
			svr.setAddress(url);
			InstallationFBServiceInf insFBServiceInf = (InstallationFBServiceInf) svr.create();
			returnValue = insFBServiceInf.installFeedBack(inXML);
		}
		return returnValue;
	}
	
	/**
	 * 生成安装计划反馈数据xml
	 * @param meterPlnFBDataList 安装计划反馈数据
	 * @param meterFBEvDataList 安装计划反馈表底度数据
	 * @return
	 */
	protected String genInsFeedBackXML(List<Object> meterPlnFBDataList, List<Object> meterFBEvDataList) {
		String inXml = "";
		//创建XML
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("FB");
		//获取不同安装计划类型对应的安装计划反馈数据
		Map<String,List<Object>> meterPlnFBDataMap = proMeterPlnFBData(meterPlnFBDataList);
		for(Map.Entry<String, List<Object>> entry : meterPlnFBDataMap.entrySet()) {
			String bussType = StringUtil.getValue(entry.getKey());
			if(!StringUtil.isEmptyString(bussType)) {
				List<Object> meterPlnFBList = entry.getValue();
				if(meterPlnFBList.size() > 0) {
					genMeterInsPlnFBXml(root, bussType, meterPlnFBList, meterFBEvDataList);
				}
			}
		}
		inXml = reDoc.asXML();
		return inXml;
	}
	
	/**
	 * 处理安装计划反馈数据，根据不同的安装计划类型进行归类
	 * @param meterPlnFBDataList
	 * @return
	 */
	protected Map<String,List<Object>> proMeterPlnFBData(List<Object> meterPlnFBDataList) {
		Map<String,List<Object>> reFBDataMap = new HashMap<String,List<Object>>();
		List<Object> installMList = new ArrayList<Object>();//新装反馈的安装计划反馈数据列表
		List<Object> changeMList = new ArrayList<Object>();//更换反馈的安装计划反馈数据列表
		List<Object> removeMList = new ArrayList<Object>();//拆回反馈的安装计划反馈数据列表
		for(Object pObj : meterPlnFBDataList) {
			Map<String,String> mFbDataMap = (Map<String, String>) pObj;
			String bussType = StringUtil.getValue(mFbDataMap.get("BUSSTYPE"));//安装计划类型
			if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(bussType)) {
				installMList.add(pObj);
			} else if(Constants.PLN_BUSSTYPE_REPLACEMENT.equals(bussType)) {
				changeMList.add(pObj);
			} else if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(bussType)) {
				removeMList.add(pObj);
			}
		}
		reFBDataMap.put(Constants.PLN_BUSSTYPE_INSTALLATION, installMList);
		reFBDataMap.put(Constants.PLN_BUSSTYPE_REPLACEMENT, changeMList);
		reFBDataMap.put(Constants.PLN_BUSSTYPE_UNINSTALLATION, removeMList);
		return reFBDataMap;
	}
	
	/**
	 * 生成安装计划表反馈数据xml节点
	 * @param root xml根节点
	 * @param bussType 安装计划类型
	 * @param meterInsPlnFBDataList 安装计划反馈数据
	 * @param meterFBEvDataList 安装计划反馈表底度信息
	 */
	protected void genMeterInsPlnFBXml(Element root, String bussType, List<Object> meterInsPlnFBDataList, List<Object> meterFBEvDataList) {
		Map<String,String> sysDataMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_DATAMAP);				
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Element msNode = root.addElement("MS");
		msNode.addAttribute("TYPE", bussType);
		if(Constants.PLN_BUSSTYPE_INSTALLATION.equals(bussType) || 
				Constants.PLN_BUSSTYPE_REPLACEMENT.equals(bussType)) {
			for(Object mObj : meterInsPlnFBDataList) {
				Element mNode = msNode.addElement("M");
				Map<String,String> mInsPlnFBDataMap = (Map<String, String>) mObj;
				String pid = StringUtil.getValue(mInsPlnFBDataMap.get("PID"));//计划id
				String nmsn = StringUtil.getValue(mInsPlnFBDataMap.get("NMSN"));//新表号
				String omsn = StringUtil.getValue(mInsPlnFBDataMap.get("OMSN"));//老表号
				String mType = StringUtil.getValue(mInsPlnFBDataMap.get("MTYPE"));//表计类型
				String wiring = StringUtil.getValue(mInsPlnFBDataMap.get("MWIRING"));//接线方式
				String mMode = StringUtil.getValue(mInsPlnFBDataMap.get("MMODE"));//表计模式
				mNode.addElement("S").addText(pid);
				mNode.addElement("MN").addText(nmsn);
				mNode.addElement("OMN").addText(omsn);
				mNode.addElement("MB").addText(StringUtil.getValue(mInsPlnFBDataMap.get("MBOXID")));
				mNode.addElement("TN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TFID")));
				mNode.addElement("TNM").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TFNAME")));
				mNode.addElement("TAD").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TADDR")));
				mNode.addElement("CN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CNO")));
				mNode.addElement("CNM").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CNAME")));
				mNode.addElement("AD").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CADDR")));
				mNode.addElement("MT").addText(StringUtil.getValue(sysDataMap.get("meter_type_" + mType)));
				mNode.addElement("CM").addText(StringUtil.getValue(sysDataMap.get("connection_method_" + wiring)));
				mNode.addElement("MM").addText(StringUtil.getValue(sysDataMap.get("meter_mode_" + mMode)));
				mNode.addElement("MC").addText(StringUtil.getValue(mInsPlnFBDataMap.get("MSP")));
				mNode.addElement("SIM").addText(StringUtil.getValue(mInsPlnFBDataMap.get("SIMNO")));
				mNode.addElement("SN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("SIMSN")));
				mNode.addElement("CT").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CT")));
				mNode.addElement("PT").addText(StringUtil.getValue(mInsPlnFBDataMap.get("PT")));
				mNode.addElement("LN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("LON")));
				mNode.addElement("LT").addText(StringUtil.getValue(mInsPlnFBDataMap.get("LAT")));
				mNode.addElement("SL").addText(StringUtil.getValue(mInsPlnFBDataMap.get("SEALID")));
				mNode.addElement("MAT").addText(StringUtil.getValue(mInsPlnFBDataMap.get("MATCODE")));
				String opDate = "";
				try {
					opDate = format.format(format.parse(StringUtil.getValue(mInsPlnFBDataMap.get("OPDATE"))));
				} catch (ParseException e) {
					logger.error("date convert error:" + e.getMessage());
				}
				mNode.addElement("OD").addText(opDate);
				String evs = procEV(pid,nmsn,omsn,meterFBEvDataList);//表底度信息 （多个表的底度信息用冒号":"分隔）
				if(!StringUtil.isEmptyString(evs)) {
					String[] evArray = evs.split(":");
					if(evArray.length == 2) {
						mNode.addElement("EV").addText(evArray[0]);
						mNode.addElement("OEV").addText(evArray[1]);
					} else if(evArray.length == 1) {
						mNode.addElement("EV").addText(evArray[0]);
					}
				} else {
					mNode.addElement("EV").addText("");
					mNode.addElement("OEV").addText("");
				}
			}
		} else if(Constants.PLN_BUSSTYPE_UNINSTALLATION.equals(bussType)) {
			for(Object mObj : meterInsPlnFBDataList) {
				Element mNode = msNode.addElement("M");
				Map<String,String> mInsPlnFBDataMap = (Map<String, String>) mObj;
				String pid = StringUtil.getValue(mInsPlnFBDataMap.get("PID"));//计划id
				String omsn = StringUtil.getValue(mInsPlnFBDataMap.get("OMSN"));//老表号
				mNode.addElement("S").addText(pid);
				mNode.addElement("MN").addText(omsn);
				mNode.addElement("TN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TFID")));
				mNode.addElement("TNM").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TFNAME")));
				mNode.addElement("TAD").addText(StringUtil.getValue(mInsPlnFBDataMap.get("TADDR")));
				mNode.addElement("CN").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CNO")));
				mNode.addElement("CNM").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CNAME")));
				mNode.addElement("AD").addText(StringUtil.getValue(mInsPlnFBDataMap.get("CADDR")));
				String opDate = "";
				try {
					opDate = format.format(format.parse(StringUtil.getValue(mInsPlnFBDataMap.get("OPDATE"))));
				} catch (ParseException e) {
					logger.error("date convert error:" + e.getMessage());
				}
				mNode.addElement("OD").addText(opDate);
				String evs = procEV(pid,"",omsn,meterFBEvDataList);//表底度信息 （多个表的底度信息用冒号":"分隔）
				if(!StringUtil.isEmptyString(evs)) {
					String[] evArray = evs.split(":");
					if(evArray.length == 2) {
						mNode.addElement("EV").addText(evArray[1]);
					}
				} else {
					mNode.addElement("EV").addText("");
				}
			}
		}	
	}
	
	/**
	 * 处理表底度信息，返回多个表的底度信息（不同表底度信息用冒号分隔，同一个表不同底度值用逗号分隔）
	 * @param pid 安装计划id
	 * @param nmsn 新表号
	 * @param omsn 老表号
	 * @param meterFBEvDataList 表号对应的底度信息
	 * @return
	 */
	protected String procEV(String pid, String nmsn, String omsn, List<Object> meterFBEvDataList) {
		StringBuilder nev = new StringBuilder();//新表底度值
		StringBuilder oev = new StringBuilder();//老表底度值
		for(Object obj : meterFBEvDataList) {
			Map<String,String> evMap = (Map<String, String>) obj;
			String tempPid = StringUtil.getValue(evMap.get("PID"));
			String tempMsn = StringUtil.getValue(evMap.get("Msn"));
			String tempValue = StringUtil.getValue(evMap.get("VALUE"));
			if(pid.equals(tempPid)) {
				if(!StringUtil.isEmptyString(nmsn) && nmsn.equals(tempMsn)) {
					nev.append(tempValue).append(",");
				}
				if(!StringUtil.isEmptyString(omsn) && omsn.equals(tempMsn)) {
					oev.append(tempValue).append(",");
				}
			}
		}
		String ev = "";//包含新表和老表的底度信息
		if(nev.length() > 0) {
			String tempEv = nev.toString();
			tempEv = tempEv.substring(0, tempEv.lastIndexOf(","));
			ev = tempEv;
		}
		if(oev.length() > 0) {
			String tempOev = oev.toString();
			tempOev = tempOev.substring(0, tempOev.lastIndexOf(","));
			ev = ev + ":" + tempOev;
		}
		return ev;
	}
	
	/**
	 * 处理MDC返回的xml信息,返回失败的安装计划id
	 * @param document
	 */
	protected void procXml(Document document, List<Object> meterPlnFBDataList) {
		Element root = document.getRootElement();
		String cValue = StringUtil.getValue(root.elementTextTrim("C"));//标志着MDC执行结果，成功或失败
		String failPids = StringUtil.getValue(root.elementTextTrim("PIDS"));//标志着MDC端没有执行成功的安装计划id
		String msg = StringUtil.getValue(root.elementTextTrim("MSG"));//消息
		updMeterInsPlnFB(meterPlnFBDataList,cValue,failPids,msg);//更新表安装计划反馈数据同步到MDC状态
	}
	
	/**
	 * 更新安装计划反馈数据同步状态
	 * @param meterPlnFBDataList 表安装计划反馈数据
	 * @param cValue 标志返回结果1=成功，0=失败
	 * @param failPids 失败的安装计划id
	 * @param msg 失败消息
	 */
	protected void updMeterInsPlnFB(List<Object> meterPlnFBDataList,String cValue, String failPids, String msg) {
		InsFbManagerInf insFbManager = (InsFbManagerInf) SpringContextUtil.getBean("insFbManager");
		List<Object> paramList = new ArrayList<Object>();
		if(Constants.HHU_FDM_FAIL.equals(cValue)) {//返回失败 
			if(msg.equals(MsgCodeConstants.HX10003)) {//存在校验不通过的安装计划反馈数据
				String[] failPidArray = failPids.split(",");
				for(Object obj : meterPlnFBDataList) {
					Map<String,String> updMap = new HashMap<String,String>();
					Map<String,String> mPlnFBMap = (Map<String, String>) obj;
					String pid = StringUtil.getValue(mPlnFBMap.get("PID"));
					boolean exist = false;//存在失败的安装计划id，false不存在，true存在
					for(String failPid : failPidArray) {
						if(pid.equals(failPid)) {
							updMap.put("pid", pid);
							updMap.put("status", Constants.PLN_TOMDC_SYNCFAIL);
							paramList.add(updMap);
							exist = true;
							break;
						}
					}
					if(!exist) {
						updMap.put("pid", pid);
						updMap.put("status", Constants.PLN_TOMDC_SYNC);
						paramList.add(updMap);
					}
				}
			}
		} else {//返回成功
			for(Object obj : meterPlnFBDataList) {
				Map<String,String> updMap = new HashMap<String,String>();
				Map<String,String> mPlnFBMap = (Map<String, String>) obj;
				updMap.put("pid", StringUtil.getValue(mPlnFBMap.get("PID")));
				updMap.put("status", Constants.PLN_TOMDC_SYNC);
				paramList.add(updMap);
			}
		}
		if(paramList.size() > 0) {
			//更新表安装计划反馈数据同步到MDC状态
			insFbManager.updMeterFBSynStatus(paramList);
		}
	}
}
