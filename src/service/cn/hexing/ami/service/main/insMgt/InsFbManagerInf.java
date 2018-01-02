package cn.hexing.ami.service.main.insMgt;

import java.util.List;
import java.util.Map;

import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

public interface InsFbManagerInf extends DbWorksInf, QueryInf{
	/**
	 * 查询设置参数结果
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> querySetParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询测试参数结果
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTestParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询测试参数结果
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryTestOBIS(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询读取参数结果
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryReadParam(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询集中器反馈信息
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryDcuFb(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 查询采集器反馈信息
	 * @param param
	 * @param start
	 * @param limit
	 * @param dir
	 * @param sort
	 * @param isExcel
	 * @return
	 */
	public Map<String, Object> queryColFb(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel);
	
	/**
	 * 根据计划id
	 * @param pid 计划ID
	 * @param flag 0=查看新表图片，1=查看老表图片
	 * @return
	 */
	public String getPicsPath(String pid,String flag);
	
	/**
	 * 保存安装反馈基本数据
	 * @param paramList 安装计划反馈数据
	 * @return
	 */
	public ActionResult storeMeterFBBasicData (List<Object> paramList);
	
	/**
	 * 保存安装反馈测试数据（设置，读取，测试）
	 * @param setList 设置数据列表
	 * @param readList 读取数据列表
	 * @param testPMMap 测试数据Map对象
	 * @return
	 */
	public ActionResult storeMeterFBTestData(List<Object> setList, List<Object> readList, 
			Map<String,List<Object>> testPMMap);
	
	/**
	 * 获取未同步给MDC或同步失败的反馈成功或异常的安装计划反馈数据
	 * @return
	 */
	public List<Object> getMeterPlnFBData();
	
	/**
	 * 获取未同步给MDC或同步失败的反馈成功或异常的安装计划中表对应的底度信息
	 * @return
	 */
	public List<Object> getMeterEvData();
	
	/**
	 * 更新表安装计划反馈数据同步到MDC状态
	 * @param paramList
	 */
	public void updMeterFBSynStatus(List<Object> paramList);
}
