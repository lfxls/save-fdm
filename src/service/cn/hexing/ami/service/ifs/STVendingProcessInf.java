package cn.hexing.ami.service.ifs;


/** 
 * @Description FDM对VENDING的数据处理接口
 * @author xcx
 * @Copyright 2017 hexing Inc. All rights reserved
 * @time：2017-10-16
 * @version FDM2.0
 */
public interface STVendingProcessInf {
	
	/**
	 * 更新勘察计划状态（取消缴费，已缴费）
	 * @param inXML
	 * @return
	 */
	public String saveSrvyPlnStatus(String inXML);
}
