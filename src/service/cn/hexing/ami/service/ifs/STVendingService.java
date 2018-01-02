package cn.hexing.ami.service.ifs;

import cn.hexing.ami.util.SpringContextUtil;

/** 
 * @Description FDM对VENDING的接口，通过Webservice方式
 * @author xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-10-16
 * @version FDM2.0
 */

public class STVendingService implements STVendingServiceInf{
	/**
	 * 更新勘察状态（取消缴费，已缴费）
	 * @param inXML
	 * @return
	 */
	public String srvyStatusUpdate(String inXML) {
		STVendingProcessInf process= (STVendingProcessInf)SpringContextUtil.getBean("stVendingProcess");
		String xml = process.saveSrvyPlnStatus(inXML);
		return xml;
	}
}
