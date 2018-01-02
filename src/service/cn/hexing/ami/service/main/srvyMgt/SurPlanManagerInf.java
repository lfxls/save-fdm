package cn.hexing.ami.service.main.srvyMgt;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.util.ActionResult;

public interface SurPlanManagerInf extends DbWorksInf {

	Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);

	Map<String, String> getSurPByPid(String pid);
	
	/**
	 * 获取某工单下的勘察计划
	 * @param woid
	 * @return
	 */
	List<Object> getSrvyPln(String woid);
	
	/**
	 * 更新勘察计划状态
	 * @param paramList 数据列表
	 * @return
	 */
	ActionResult updSrvyPlnStatus(List<Object> paramList);

	/**
	 * 获取指定工单下，指定状态的勘察计划数量
	 * @param woid 工单id
	 * @param status 状态
	 * @return
	 */
	int getSrvyPlnCount(String woid, String[] status);
	
	/**
	 * 是否存在勘察计划
	 * @param pid
	 * @return
	 */
	public int existSrvyPln(String pid);

	ActionResult parseExcel(FileInputStream fis, Map<String, String> param,
			String iMPORT_INSP_ADD_METER, String lang);
}
