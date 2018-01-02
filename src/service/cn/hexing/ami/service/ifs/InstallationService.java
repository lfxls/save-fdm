package cn.hexing.ami.service.ifs;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import cn.hexing.ami.util.SpringContextUtil;


/** 
 * @Description FDM对HHU的接口，通过Webservice方式
 * @author jun
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */

//@WebService(endpointInterface = "cn.hexing.ami.service.ifs.InstallationServiceInf")
public class InstallationService implements InstallationServiceInf{
	@Resource
	private WebServiceContext webServiceContext;
	/**
	 * 工单下载 
	 * @param inXML
	 * @return
	 */
	public String downloadWorkOrders(String inXML){
		InstallationProcessInf process= (InstallationProcessInf)SpringContextUtil.getBean("installationProcess");
		String xml = process.createWorkOrders(inXML);
		return xml;
	}
	
	/**
	 * 工单下载反馈
	 * @param inXML
	 * @return
	 */
	public String downloadWorkOrdersFB(String inXML){
		InstallationProcessInf process= (InstallationProcessInf)SpringContextUtil.getBean("installationProcess");
		String xml = process.saveDownLoadFB(inXML);
		return xml;
	}
	
	/**
	 * 工单上传
	 * @param inXML
	 * @return
	 */
	public String uploadWorkOrders(String inXML){
		InstallationProcessInf process= (InstallationProcessInf)SpringContextUtil.getBean("installationProcess");
		String xml = process.saveUpload(inXML);
		return xml;
	}

	/**
	 * 登录接口
	 * @param inXML
	 * @return
	 */
	public String login(String inXML) {
		InstallationProcessInf process= (InstallationProcessInf)SpringContextUtil.getBean("installationProcess");
		String xml = process.login(inXML);
		return xml;
	}
	
	/**
	 * App升级接口
	 * @return
	 */
	public String appUpgrade() {
		MessageContext ctx = (MessageContext) webServiceContext.getMessageContext();
		InstallationProcessInf process= (InstallationProcessInf)SpringContextUtil.getBean("installationProcess");
		String xml = process.appUpgrade(ctx);
		return xml;
	}
}
