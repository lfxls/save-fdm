package cn.hexing.ami.service.ifs;

/** 
 * @Description 档案同步业务处理
 * @author xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */
public interface ArchSynProcessInf {	
	/**
	 * 保存同步过来的档案（MDC档案同步到FDM）
	 * @param inXML
	 */
	public String saveArch(String inXML);
	
	/**
	 * 保存同步过来的请求(MDC用户请求同步到FDM)
	 * @param inXML
	 * @return
	 */
	public String saveReqest(String inXML);
}
