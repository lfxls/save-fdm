package cn.hexing.ami.service.ifs;

import cn.hexing.ami.util.SpringContextUtil;


/** 
 * @Description FDM对MDC的接口，通过Webservice方式
 * @author xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-6-02
 * @version FDM2.0
 */

public class ArchSynService implements ArchSynServiceInf{
	/**
	 * 档案同步（MDC档案同步到FDM）
	 * @param inXML
	 */
	public String arcSynchronization(String inXML) {
		ArchSynProcessInf process= (ArchSynProcessInf)SpringContextUtil.getBean("archSynProcess");
		String xml = process.saveArch(inXML);
		return xml;
	}

	@Override
	public String reqeustFDM(String inXML) {
		ArchSynProcessInf process= (ArchSynProcessInf)SpringContextUtil.getBean("archSynProcess");
		String xml = process.saveReqest(inXML);
		return xml;
	}
}
