package cn.hexing.ami.service.main.insMgt;

import cn.hexing.ami.util.Constants;

/** 
 * @Description 档案导入工具类
 * @author  xcx
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2016-4-18
 * @version FDM 
 */
public class DadrUtil {
	
	/**
	 * 获取excel列对应数据字段属性
	 * @param archivesType
	 * @return
	 */
	public static String[] getExcelField(String archivesType){
		String[] field = null;
		//安装计划新装电表
		if(Constants.IMPORT_INSP_ADD_METER.equals(archivesType)){
			field = new String[]{"cno", "tfId", "mType", "wir", "mMode", "nmsn"};
		}
		
		//安装计划更换电表
		if(Constants.IMPORT_INSP_CHANGE_METER.equals(archivesType)){
			field = new String[]{"omsn", "mType", "wir", "mMode","nmsn"};
		}
	
		//安装计划拆除电表
		if(Constants.IMPORT_INSP_REMOVE_METER.equals(archivesType)){
			field = new String[]{"omsn"};
		}
		
		//安装计划新装集中器
		if(Constants.IMPORT_INSP_ADD_DCU.equals(archivesType)){
			field = new String[]{"tfId", "dcuM", "mdsn"};
		}
		
		//安装计划更换集中器
		if(Constants.IMPORT_INSP_CHANGE_DCU.equals(archivesType)){
			field = new String[]{"odsn", "dcuM","ndsn"};
		}
		
		//安装计划拆除集中器
		if(Constants.IMPORT_INSP_REMOVE_DCU.equals(archivesType)){
			field = new String[]{"odsn"};
		}
		
		//安装计划新装采集器
		if(Constants.IMPORT_INSP_ADD_COLL.equals(archivesType)){
			field = new String[]{"tfId", "colM", "ncsn"};
		}
		
		//安装计划更换采集器
		if(Constants.IMPORT_INSP_CHANGE_COLL.equals(archivesType)){
			field = new String[]{"ocsn", "colM","ncsn"};
		}
		
		//安装计划拆除采集器
		if(Constants.IMPORT_INSP_REMOVE_COLL.equals(archivesType)){
			field = new String[]{"ocsn"};
		}
		
		//新增Token
		if(Constants.IMPORT_PRE_ADD_TOKEN.equals(archivesType)){
			field = new String[]{"msn", "token", "cno", "orderid"};
		}
		//新增勘察
		if(Constants.IMPORT_INSP_SRVY.equals(archivesType)){
			field = new String[]{"cno"};
		}
		
		return field;
	}
}
