package cn.hexing.ami.service.ifs;

import javax.xml.ws.handler.MessageContext;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.hexing.ami.util.ActionResult;

/** 
 * @Description 现场设备安装业务处理
 * @author jun
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */
public interface InstallationProcessInf {
	/**
	 * 下载工单
	 * @param inXML
	 * @return
	 */
	public String createWorkOrders(String inXML);
	
	/**
	 * 工单下载反馈
	 * @param inXML
	 * @return
	 */
	public String saveDownLoadFB(String inXML);
	
	/**
	 * 工单上传
	 * @param inXML
	 * @return
	 */
	public String saveUpload(String inXML);
	
	/**
	 * 登录接口
	 * @param inXML
	 * @return
	 */
	public String login(String inXML);
	
	/**
	 * App升级接口
	 * @param ctx 消息上下文
	 * @return
	 */
	public String appUpgrade(MessageContext ctx);
}
