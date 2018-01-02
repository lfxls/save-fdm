package cn.hexing.ami.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.I18nUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

public class CommonManager {
	private static Logger logger = Logger.getLogger(CommonManager.class.getName());
	BaseDAOIbatis baseDAOIbatis = null;

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	String sqlId = "common.";

	/**
     * 获取系统配置
     * @param sortId
     * @param name
     * @return
     */
    public String getPSys(String paraId) {
       Map<String, String> param = new HashMap<String, String>();
       param.put("id", paraId);
       return (String) baseDAOIbatis.queryForObject(sqlId + "getPSys", param, String.class);
    }
	/**
	 * 字典数据
	 * @param codeType
	 * @param locale
	 * @param all
	 * @param orderType
	 * @param type 01：表示getPcode  02:表示getPCodeNumber   03：表示getPCodeOrderBySN
	 * @return
	 */
	public List<Object> getCode(String codeType, String locale, boolean all,String orderType, String type) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code_type", codeType);
		if(!StringUtil.isEmptyString(orderType)) {
			paramMap.put("orderType", orderType);
		}
		if(!StringUtil.isEmptyString(locale)){
			paramMap.put("lang", locale);
		}
		List<Object> ls = null;
		if ("02".equals(type)) {
			ls =  baseDAOIbatis.queryForList(sqlId + "getPCodeNumber", paramMap);
		}else if("03".equals(type)){
			ls =  baseDAOIbatis.queryForList(sqlId + "getPCodeOrderBySN", paramMap);
		}else {
			ls =  baseDAOIbatis.queryForList(sqlId + "getPCode", paramMap);
		}
		if(all) {
			Map<String,String> o = new HashMap<String, String>();
			o.put("BM", "");
			o.put("MC", I18nUtil.getText("common.select.all", locale));
			ls.add(0,o);
		}
		return ls;
	}
	/**
	 * 字典数据
	 * @param gylx
	 * @param lang
	 * @param all
	 * @return
	 */
	public List<Object> getRwsx(String gylx, String lang, boolean all) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		if(!StringUtil.isEmptyString(lang)){
			paramMap.put("lang", lang);
		}
			paramMap.put("gylx", gylx);
		List<Object> ls  =  baseDAOIbatis.queryForList(sqlId + "getRwsx", paramMap);
		if(all) {
			Map<String,String> o = new HashMap<String, String>();
			o.put("BM", "");
			o.put("MC", I18nUtil.getText("common.select.all", lang));
			ls.add(0,o);
		}
		return ls;
	}
	/**
     * 获取用户列表（公共）
     * @param param
     * @return
     */
    public Map<String, Object> getUserPage(final Map<String, Object> param) {
    	String start = String.valueOf(param.get("start"));
    	String limit = String.valueOf(param.get("limit"));
    	return baseDAOIbatis.getExtGrid(param, sqlId + "getUserList", start, limit,"", "", "");
    }
    
    /**
     * 获取用户列表（公共）
     * @param param
     * @return
     */
    public int getUserCount(final Map<String, Object> param) {
    	return (Integer)baseDAOIbatis.queryForObject(sqlId + "getUserCount", param,Integer.class);
    }
    
    /**
     * 获取当前单位代码全路径
     * @param dwdm
     * @return
     */
    public List<Object> getDwPath(String dwdm){
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dwdm", dwdm);
    	return baseDAOIbatis.queryForList(sqlId+"getDwPath", paramMap);
    }
    
    /**
     * 初始化license 超期信息
     */
    public void initDayLimitLicense(String expDate,String days){
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	int rtnTmp = (Integer)baseDAOIbatis.queryForObject(sqlId+"getDayLimitLicense", paramMap, Integer.class);
    	if (rtnTmp==0) {
    		paramMap.put("expDate", expDate);
    		paramMap.put("days", days);
    		//没有初始化，即完成初始化
    		baseDAOIbatis.insert(sqlId+"initDayLimitLicense", paramMap);
		}
    }
    
    /**
     * 获取license已经使用天数
     * @return
     */
    public Map<String,Object> getUsedDaysLicense(){
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	List<Object> ls = baseDAOIbatis.queryForList(sqlId+"getUsedDaysLicense",paramMap);
    	if (ls!=null && ls.size()>0) {
			return (Map<String,Object>)ls.get(0);
		}else{
			return null;
		}
    }
    
    /**
     * 计算使用天数
     * @param days
     */
    public void updateDayLimitLicense(int days){
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("days", days);
    	baseDAOIbatis.update(sqlId+"updateDayLimitLicense", paramMap);
    }
    
    /**
     * 重置使用天数
     * @param expDate 过期时间
     */
    public void resetDayLimitLicense(String expDate){
    	Map<String,Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("expDate", expDate);
    	baseDAOIbatis.update(sqlId+"resetDayLimitLicense", paramMap);
    }
    
	/**
	 * 获取终端唤醒标志
	 */
	public ActionResult getWakingFlag(Map<String, String> param) {
		ActionResult re = new ActionResult(true, "");
		int wakingTime = 300;//默认5分钟
		try{wakingTime = Integer.parseInt(((Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP)).get("wakingTime"));}catch(Exception e){
			logger.error(StringUtil.getExceptionDetailInfo(e));}
		param.put("zdjh", this.getZdjh(param));
		if(param.get("zdjh")!=null && !"".equals(param.get("zdjh"))){
			Integer wakingSecondObj = (Integer)baseDAOIbatis.queryForObject(sqlId + "getWakingSecond", param, Integer.class);
			if(wakingSecondObj==null){
				re.setDataObject("1");//需要重新唤醒
			}else{
				int wakingSecond = wakingSecondObj;
				if(wakingSecond>=wakingTime){ //唤醒已过时，需要重新唤醒
					re.setDataObject("1");
				}else{
					re.setDataObject("0");//不需要重新唤醒
				}
			}
		}else{
			re.setDataObject("0");//不需要重新唤醒//如果取不到zdjh，则不进行唤醒
		}
		return re;
	}
	
	/**
	 * 获取唤醒标志
	 * @param param
	 * @return
	 */
	public ActionResult getWakeUpStatus(Map<String, String> param) {
		ActionResult re = new ActionResult(false, "");
		String zdjh = this.getZdjh(param);
		param.put("zdjh", zdjh);
		Integer bwCount = (Integer)baseDAOIbatis.queryForObject(sqlId + "getWakingUpBwCount", param, Integer.class);
		if(bwCount>0) re.setSuccess(true);
		return re;
	}
	private String getZdjh(Map<String, String> param){
		if(param.get("zdjh")!=null && !"".equals(param.get("zdjh"))){
			return param.get("zdjh");
		}else if((param.get("bjjh")!=null && !"".equals(param.get("bjjh")))
				|| ((param.get("cldjh")!=null && !"".equals(param.get("cldjh"))))
		){
			return (String)baseDAOIbatis.queryForObject(sqlId + "getZdjh", param, String.class);
		}else if("zddsgl".equals(param.get("moduleName"))){
			try{
				String[] zhs = param.get("zh").split(",");
				String[]items = zhs[0].split(":");
				return items[0];
			}catch (Exception e) {logger.error(StringUtil.getExceptionDetailInfo(e));}
		}
		return null;
	}
	
	/**
	 * 刷新终端档案和模板--保存刷新纪录
	 */
	public void freshFepData(Map<String,Object> paramMap){
		baseDAOIbatis.insert(sqlId+"insertZdcstb", paramMap);
	}
	
	public String getVeeSjzd(){
		List<Object> zds = baseDAOIbatis.queryForList(sqlId+"getVeeSjzd", null);
		String res = "";
		for(Object obj : zds){
			Map map = (Map) obj;
			String zd = (String) map.get("ZD");
			if(zd==null || "".equals(zd)) continue;
			if(!"".equals(res)) res += ",";
			res += zd;
		}
		return res;
	}
	
	public String getVeeSjzdForLoad(){
		List<Object> zds = baseDAOIbatis.queryForList(sqlId+"getVeeSjzd", null);
		List<Object> loadColumns = baseDAOIbatis.queryForList(sqlId+"getLoadTableColumn", null);
		
		String res = "";
		for(Object obj : zds){
			Map map = (Map) obj;
			String zd = (String) map.get("ZD");
			if(zd==null || "".equals(zd)) continue;
			for(Object col : loadColumns){ //判断sb_fhsj表是否支持该字段
				Map mapCol = (Map) col;
				String colName = (String) mapCol.get("COLUMN_NAME");
				if(zd.endsWith(colName)){
					if(!"".equals(res)) res += ",";
					res += zd;
					break;
				}
			}
		}
		return res;
	}
	
	/**
	 * 查询抄表数据项
	 * @param param
	 * @return
	 */
	public List<Object> queryCbsjx(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "cbsjx", param);
	}
	
	/**
	 * 查询抄表标题
	 * @param param
	 * @return
	 */
	public List<Object> queryCbbt(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "xlx", param);
	}
	
	/**
	 * 查询图形标题
	 * @param param
	 * @return
	 */
	public List<Object> queryTxbt(Map<String, Object> param) {
		return baseDAOIbatis.queryForList(sqlId + "tx", param);
	}
	/**
	 * 服务器启动时，查看并填充为空的WCSJ
	 * 清空job_control表中所有记录
	 * @param 
	 * @return
	 */
	public void updVeeRunningFlag(){
		Map<String,Object> param = new HashMap<String, Object>();
		List<String> ipLs = getPrivateIps();
		param.put("moduleName","VEE");
		param.put("localIP", ipLs);
		param.put("runState", 6);
    	baseDAOIbatis.update(sqlId+"veeLogWcsjUpd", param);		//update finish time in vee_log belong to restarted server	
		baseDAOIbatis.delete(sqlId+"jobControlClear", param);	//delete job_control related records

    	param.clear();
    	param = null;
	}
	
	/**
	 * 服务器启动时，查看并填充为空的WCSJ
	 * 清空job_control表中所有记录
	 * @param 
	 * @return
	 */
	public void updAggRunningFlag(){
		Map<String,Object> param = new HashMap<String, Object>();
		List<String> ipLs = getPrivateIps();
		param.put("localIP", ipLs);
    	param.put("runState", 6);
    	param.put("moduleName", "Aggregation");
    	
    	baseDAOIbatis.update(sqlId+"aggLogWcsjUpd", param);
    	baseDAOIbatis.delete(sqlId+"jobControlClear", param);
    	param.clear();
    	param = null;
	}
	
	/**
	 * 获取所有private IP address
	 * @param 
	 * @return Set<String>
	 */
	 public List<String> getPrivateIps(){
	       String localip  = null;
	       InetAddress ip  = null;
	       Set<String> set = new HashSet<String>();
	       List<String> ls = new ArrayList<String>();
	       
	       Enumeration<NetworkInterface> netInterfaces = null;
		try{
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		}catch(SocketException e){
			logger.error("SocketException occurs when obtaining private IP Address.");
			e.printStackTrace();
		} 
	   while(netInterfaces.hasMoreElements()) 
	        {
	           NetworkInterface ni		        = netInterfaces.nextElement();
	           Enumeration<InetAddress> address = ni.getInetAddresses();
	           while(address.hasMoreElements())
	            {
	                  ip = address.nextElement();	
	              if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()&& ip.getHostAddress().indexOf(":") == -1)
	              {
	                 localip = ip.getHostAddress();
	              }
	              if(localip != null)
	              {
		              set.add(localip);
	              }
	           }
	       }
			   Iterator<String> it = set.iterator();
		       while(it.hasNext())
		       {
		       	ls.add(it.next());
		       }
		       	return ls;
	 }
	 
	/**
	 * 获取公共树，所有的树形展现都通过这个方法生成
	 * @param treeType 树类型
	 * @param param 参数
	 * @return
	 */
	public List<TreeNode> getTree(String treeType, Map<String, String> param){
		//单位树
		if(Constants.TREETYPE_POWERUTILITY.equals(treeType)){
			String optID = param.get("optID");
			String departID = param.get("departID");
			String accessFlag = param.get("accessFlag");
			String nodeId = param.get("nodeId");
			//访问权限过滤
			Map<String, Object> pm = new HashMap<String, Object>();
			pm.put("nodeId", nodeId);
			DatabaseUtil.fwdwFilter(pm, optID, accessFlag, departID, "DW");
			
			//获取当前单位的所有下级单位
			List<Object> ls = baseDAOIbatis.queryForList("lefttree.getDwDw", pm);
			List<TreeNode> tree = new ArrayList<TreeNode>();
			for (int i = 0, len = ls.size(); i < len; i++) {
				Map<String, Object> m = (Map<String, Object>) ls.get(i);
				String dwdm = StringUtil.getValue(m.get("DWBM"));
				String dwmc = StringUtil.getValue(m.get("DWMC"));
				String type = StringUtil.getValue(m.get("TYPE"));
				TreeNode node = new TreeNode(dwdm, dwmc, type, false);
				node.setIconCls("dw");
				tree.add(node);
			}
			return tree;
		}else
			return null;
	}
	
	public List<Object> getAllPCode() {
		return baseDAOIbatis.queryForList(sqlId + "getAllPCode", null);
	}
}