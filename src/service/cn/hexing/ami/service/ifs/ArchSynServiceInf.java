package cn.hexing.ami.service.ifs;

import javax.jws.WebService;

/**
 * @Description  FDM对MDC的接口，通过Webservice方式
 * @author  xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-02
 * @version FDM2.0
 */
@WebService 
public interface ArchSynServiceInf {
	/**
	 * 档案同步
	 * @param inXML
	 * @return
	 */
	public String arcSynchronization(String inXML);
	
	public String reqeustFDM(String inXML);
}
