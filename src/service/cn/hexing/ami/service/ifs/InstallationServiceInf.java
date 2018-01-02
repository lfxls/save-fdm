package cn.hexing.ami.service.ifs;

import javax.jws.WebService;

/**
 * @Description  FDM对HHU的接口，通过Webservice方式
 * @author  jun
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */
@WebService 
public interface InstallationServiceInf {
	
	/**
	 * 工单下载 
	 * @param inXML
	 * @return
	 */
	public String downloadWorkOrders(String inXML);
	
	/**
	 * 工单下载反馈
	 * @param inXML
	 * @return
	 */
	public String downloadWorkOrdersFB(String inXML);
	
	/**
	 * 工单上传
	 * @param inXML
	 * @return
	 */
	public String uploadWorkOrders(String inXML);
	
	/**
	 * 登录接口
	 * @param inXML
	 * @return
	 */
	public String login(String inXML);
	
	/**
	 * App升级接口
	 * @return
	 */
	public String appUpgrade();
}
