package cn.hexing.ami.web.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.TreeNode;
import cn.hexing.ami.service.CommonManager;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.TempUtil;

public class CommonAction extends BaseAction {
	private static final long serialVersionUID = -7919206667811838192L;
	private static Logger logger = Logger.getLogger(CommonAction.class.getName());
	CommonManager commonManager = null;
	private String dwFullPath;
	private String treeType;

	public String getDwFullPath() {
		return dwFullPath;
	}
	public void setDwFullPath(String dwFullPath) {
		this.dwFullPath = dwFullPath;
	}
	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	public String getTreeType() {
		return treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
	/**
     * 获取备选用户列表
     * @throws IOException
     */
    public void getGrid() throws IOException {
    	String name = request.getParameter("name");
    	if(StringUtil.isEmptyString(name)){
    		name = (String) request.getSession().getAttribute("moduleName");
    	}
        String fileName = (String) request.getSession().getAttribute("fileName" + name);
        if (StringUtil.isEmptyString(fileName)) {
            fileName = name + this.getCzyid() + System.currentTimeMillis() + ".txt";
            // 新建空文件时候不验证重复
            TempUtil.listToFile(fileName, new ArrayList<Object>(), 0, null, true, null,"0,0");
            request.getSession().setAttribute("fileName" + name, fileName);
        }
        int start = Integer.parseInt(request.getParameter("start"));
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        Map<String, Object> result = TempUtil.filePaging(fileName, start, pageSize);
        responseGrid(result);
    }

    /**
     * 对备选用户列表进行操作
     */
    public void doFile() throws Exception {
    	long startTime = System.currentTimeMillis();
        String doType = request.getParameter("doType"); // 操作类型
        String name = request.getParameter("name");
        // 刷新页面保存用name
        request.getSession().setAttribute("moduleName",name);
        String fileName = (String) request.getSession().getAttribute("fileName" + name);
        if(StringUtil.isEmptyString(fileName)) {
            fileName = name + this.getCzyid() + System.currentTimeMillis()+ ".txt";
            request.getSession().setAttribute("fileName" + name, fileName);
        }
        
        int idColNum = StringUtil.isEmptyString(request.getParameter("colNum")) ? 0 : Integer.valueOf(request.getParameter("colNum"));//id列号
        
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        int startInt = Integer.parseInt(StringUtil.isEmptyString(start)?"0":start);
        int limitInt = Integer.parseInt(StringUtil.isEmptyString(limit)?"0":limit);
        
        // 添加文件
        if ("addRecord".equals(doType)) {
        	Map<String,Object> param = new HashMap<String,Object>();
        	param.put("start", startInt);
        	param.put("limit", limitInt);
        	param.put("nodeId", nodeId);
        	param.put("nodeType", nodeType);
        	param.put("nodeDwdm", nodeDwdm);
        	param.put("yhlx", yhlx);
        	param.put("bjgy", (request.getParameter("bjgy") == null ? null : request.getParameter("bjgy").split(",")));
        	param.put(Constants.APP_LANG, this.getLang());
        	// 功率控制，任务管理等模块浙规和国网分tab显示的页面
        	if(!StringUtil.isEmptyString(request.getParameter("zdgylx"))){
        		param.put("zdgylx", request.getParameter("zdgylx"));
        	}
        	//提供分页
        	Map<String, Object> userMap = getUserPage(param);
            List<Object> addList = (List<Object>)userMap.get("result");
            int allRows = userMap.get("rows")==null?0:(Integer)userMap.get("rows");
            String idProperty = request.getParameter("idProperty"); // ID
            boolean cover = StringUtil.isEmptyString(request.getParameter("cover")) ? false : true;
            String[] orderToFile = StringUtil.isEmptyString(request.getParameter("orderToFile")) ? null : request.getParameter("orderToFile").split(",");
            long endTime = System.currentTimeMillis();
          //查询时间 四舍五入，保留3位小数，单位：秒
			BigDecimal time =  new BigDecimal(endTime).subtract(new BigDecimal(startTime)).divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP);
            TempUtil.listToFile(fileName, addList, idColNum, idProperty, cover, orderToFile,allRows+","+time);
        }
        // 刷新文件
        if ("refresh".equals(doType)) {
        	/*Map<String,Object> param = new HashMap<String,Object>();
        	param.put("start", startInt);
        	param.put("end", endInt);
        	param.put("nodeId", nodeId);
        	param.put("nodeType", nodeType);
        	param.put("nodeDwdm", nodeDwdm);
        	param.put("yhlx", yhlx);
        	param.put("bjgy", (request.getParameter("bjgy") == null ? null : request.getParameter("bjgy").split(",")));
        	param.put(Constants.APP_LANG, this.getLang());
        	// 功率控制，任务管理等模块浙规和国网分tab显示的页面
        	if(!StringUtil.isEmptyString(request.getParameter("zdgylx"))){
        		param.put("zdgylx", request.getParameter("zdgylx"));
        	}
        	
        	//提供分页
            List<Object> addList = getUserList(param);
            int allRows = getUserCount(param);
            String idProperty = request.getParameter("idProperty"); // ID
            boolean cover = StringUtil.isEmptyString(request.getParameter("cover")) ? false : true;
            String[] orderToFile = StringUtil.isEmptyString(request.getParameter("orderToFile")) ? null : request.getParameter("orderToFile").split(",");
            TempUtil.listToFile(fileName, addList, idColNum, idProperty, cover, orderToFile,allRows);*/
            
            String czlx = request.getParameter("czlx"); // 根据模块自己操作
            String taskId = request.getParameter("taskId");
            if("BULK".equals(taskId)){
            	
            }else{
            	String totalCol = request.getParameter("totalCol");
            	TempUtil.addToFile(fileName, idColNum, refreshFile(taskId, czlx), Integer.valueOf(totalCol));
            }
        }
        // 删除文件
        if ("delFile".equals(doType)) {
            request.getSession().removeAttribute("fileName" + name);
            File file = new File(Constants.TMPEFILE + fileName);
            if (file.exists()) {
                FileUtils.forceDelete(file);
            }
        }
        // 根据ID删除文件中的某行
        if ("delRecordById".equals(doType)) {
        	String ids = request.getParameter("ids"); // 需要删除的ID
            TempUtil.removeFormFile(fileName, idColNum, ids.split(","));
        }
    	// 删除成功
		if("delSucecss".equals(doType)) {
			int resultColNum = Integer.valueOf(request.getParameter("resultColNum"));
			TempUtil.removeScOrFail(fileName, resultColNum, false);
		}
		// 删除失败
		if("delFail".equals(doType)) {
			int resultColNum = Integer.valueOf(request.getParameter("resultColNum"));
			TempUtil.removeScOrFail(fileName, resultColNum, true);
		}
		//根据指定列删除记录(剔除不同规约)
		if("delRecord".equals(doType)) {
			int delColNum = Integer.valueOf(request.getParameter("delColNum"));
			String compare = request.getParameter("compare");
			TempUtil.removeRecord(fileName, delColNum, compare);
		}
		
		// 清空文件
        if ("delAll".equals(doType)) {
            TempUtil.clearFile(fileName);
        }
        
        // 获取指定列值（默认ID列）
        if("getColValues".equals(doType)) {
        	int colNum = StringUtil.isEmptyString(request.getParameter("cnum")) ?  idColNum : Integer.valueOf(request.getParameter("cnum"));
        	String ids = TempUtil.getIdFromFile(fileName, colNum);
        	responseText(ids);
        }
        
        // gird上增加群组
        if("initGroup".equals(doType)) {
        	// 保存终端局号
        	String zdjhs = request.getParameter("zdjhs"); // 加入群组的zdjh
        	request.getSession().removeAttribute("groupZdjhs");
        	request.getSession().setAttribute("groupZdjhs", zdjhs);
        	
        	// 删除原有的群组文件
    		String groupFile = (String) request.getSession().getAttribute("groupFileName");
    		if(!StringUtil.isEmptyString(groupFile)) { //删除旧Group文件
    			request.getSession().removeAttribute("groupFileName");
        		File file = new File(Constants.TMPEFILE + groupFile);
                if (file.exists()) { 
                     FileUtils.forceDelete(file);
                }
    		}
    		responseText("ok");
        }
    }
	
	/**
     * 获取备选用户列表添加到文件（被doFile调用）
     * @param nodeId
     * @param nodeType
     * @param nodeDwdm
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public Map<String, Object> getUserPage(Map<String, Object> param) throws Exception {
		Map<String, Object> queryInfo = (Map<String, Object>) request
				.getSession().getAttribute("queryInfo");
		if (queryInfo != null && queryInfo.size() > 0  && "cx".equals(param.get("nodeType"))) {
			param.putAll(queryInfo);
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, this
				.getCzyid(), this.getFwbj(), this.getBm(), "ZD");
		return commonManager.getUserPage(param);
	}
    
    /**
     * 获取备选用户数量
     * @param param
     * @return
     * @throws Exception
     */
	public int getUserCount(Map<String, Object> param) throws Exception {
		Map<String, Object> queryInfo = (Map<String, Object>) request
				.getSession().getAttribute("queryInfo");
		if (queryInfo != null && queryInfo.size() > 0
				&& "cx".equals(param.get("nodeType"))) {
			param.putAll(queryInfo);
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm,
				this.getCzyid(), this.getFwbj(), this.getBm(), "ZD");
		return commonManager.getUserCount(param);
	}
    /**
     * 根据任务ID重新刷新结果列表（被doFile调用）
     * @param taskId
     * @param type
     * @return
     */
    public Map<String, String> refreshFile(String taskId, String type) {
        Map<String, String> resultMap = new HashMap<String, String>();
        return resultMap;
    }
    
    /**
     * 从文件获取全部标识列--grid全选的时候用
     * @param request
     * @return
     * @throws IOException
     */
    public String getIdsFormFile(String fileName, int col) {
        String result = "";
        try {
            result = TempUtil.getIdFromFile(fileName, col);
        } catch (IOException e) {
            logger.error(StringUtil.getExceptionDetailInfo(e));
        }
        return result;
    }
    
    /*
     * 根据字段名获取对应内容--从文件获取
     */
    public String getIdsFormFileByName(String fileName, String recName) {
        String result = "";
        try {
            result = TempUtil.getIdFromFileByName(fileName, recName);
        } catch (IOException e) {
            logger.error(StringUtil.getExceptionDetailInfo(e));
        }
        return result;
    }
    
	/**
     * 过滤参数 是否全选
     */
    public Map<String, String> paramFilter(Map<String, String> param) {
        String selectAllFlag = param.get("selectAllFlag"); // 全选标志
        if("true".equals(selectAllFlag)) {
            String zh = this.getIdsFormFile(param.get("fileName"), 0);
            if(!StringUtil.isEmptyString(zh)) {
                param.put("zh", zh);
            }
        }
        return param;
    }
    
    /**
	 * 根据左边节点获取终端局号
	 * @return
	 */
	public void getZdjhArrayByParentId() throws IOException{
		try{
			String nodeType = request.getParameter("nodeType");
			String nodeId = request.getParameter("nodeId");
			
			String isAdv = request.getParameter("isAdv");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("nodeType", nodeType);
			paramMap.put("nodeId", nodeId);
			if(!"query".equals(nodeType)){
				// 此用户类型为操作页面带过来的用户类型
				String yhlx = request.getParameter("yhlx");
				DatabaseUtil.fwdwFilter(paramMap, this.getCzyid(),this.getFwbj(), this.getBm(), "DW");
				paramMap.put("yhlx", yhlx);
			}else{
				// 高级查询
				if("true".equals(isAdv)){
					paramMap = (Map<String,Object>)request.getSession().getAttribute("adv");
				}else{
					String yhlx = request.getParameter("yhlx");
					paramMap.put("yhlx", yhlx);
					paramMap.put("isAdv", isAdv);
					String hm = request.getParameter("hm");
					if(!StringUtil.isEmptyString(hm)) paramMap.put("hm", hm);
					//String dwdm = StringUtil.isEmptyString(request.getParameter("dwdm")) ? this.getUnitCode(request):request.getParameter("dwdm");
					//paramMap.put("dwdm", dwdm);
					String hh = request.getParameter("hh");
					if(!StringUtil.isEmptyString(hh)) paramMap.put("hh", hh);
					String zdjh = request.getParameter("zdjh");
					if(!StringUtil.isEmptyString(zdjh)) paramMap.put("zdjh", zdjh);
					String zdljdz = request.getParameter("zdljdz");
					if(!StringUtil.isEmptyString(zdljdz)) paramMap.put("zdljdz", zdljdz);
					String tqmc = request.getParameter("tqmc");
					if(!StringUtil.isEmptyString(tqmc)) paramMap.put("tqmc", tqmc);
					String cjdymc = request.getParameter("cjdymc");
					if(!StringUtil.isEmptyString(cjdymc)) paramMap.put("cjdymc", cjdymc);
					String bjjh = request.getParameter("bjjh");
					if(!StringUtil.isEmptyString(bjjh)) paramMap.put("bjjh", bjjh);
					String dylx = request.getParameter("dylx");
					if(!StringUtil.isEmptyString(dylx)) paramMap.put("dylx", dylx);
				}
			}
			DatabaseUtil.fwdwFilter(paramMap, this.getCzyid(),this.getFwbj(), this.getBm(), "DW");
			StringBuffer zdjhStr = new StringBuffer();
			if("sb".equals(nodeType) || "jm".equals(nodeType)){
				zdjhStr.append(nodeId + ",");
			} else if("dw".equals(nodeType)) {
				zdjhStr.append("dwdm," + nodeId + ",");
			} else if("xzqy".equals(nodeType)) {
				zdjhStr.append("xzqy," + nodeId + ",");
			} else {
				List<Object> zdjhList = new ArrayList<Object>();
				if("true".equals(isAdv)){
					//zdjhList =  commonManager.getZdjhArrayForAdv(paramMap);
				}else{
					//zdjhList =  commonManager.getZdjhArrayByParentId(paramMap);
				}
				if (zdjhList != null && zdjhList.size()>0) {
					for(int i=0; i < zdjhList.size();i++){
						Map<String,String> zdjhMap= (Map<String,String>)zdjhList.get(i);
						zdjhStr.append(zdjhMap.get("ZDJH") + ",");
					}
				}
			}
			String zdjh = zdjhStr.length() > 0 ? zdjhStr.substring(0,zdjhStr.length() -1) : "";
			response.getOutputStream().write(StringUtil.getUTF8Bytes(zdjh));
			LOG.debug("zdjhs:" + zdjhStr);
		}catch(Exception e){
			//responseExcText(response, e);
		}
	}
	
	/**
	 * 获取单位代码完整路径，举例：杭州电力局/浙江杭州电力客户服务中心/杭州电力客户服务中心城东分中心
	 * @return
	 */
	public String getDwPath(){
		String dwdm = request.getParameter("dwdm");
		List<Object> dwList = commonManager.getDwPath(dwdm);
		
		String dwStr = "";
		dwStr += "<b><font color='#09387E'>"+(String)((Map)dwList.get(0)).get("DWMC")+"</font></b>";
		if (dwList!=null && dwList.size()>1) {
			dwStr += "(";
		}
		for (int i = 1; i < dwList.size(); i++) {
			String dwmc  = (String)((Map)dwList.get(i)).get("DWMC");
			dwStr += dwmc + "/";
		}
		if (dwList!=null && dwList.size()>1) {
			dwStr += ")";
		}
		dwFullPath = dwStr;
		return SUCCESS;
	}
	/**
	 * 获取终端最后唤醒时间是否有效
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getWakingFlag(Map<String, String> param, Util util) {
		return commonManager.getWakingFlag(param);
	}
	
	public ActionResult getWakeUpStatus(Map<String, String> param, Util util) {
		return commonManager.getWakeUpStatus(param);
	}
	
	public List<Object> getUserList(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取公共树，所有的树形展现都通过这个方法生成
	 * @return
	 */
	public String getTree(){
		//操作员ID
		String optID = this.getCzyid();
		//部门ID
		String departID = this.getBm();
		//访问标记
		String accessFlag = this.getFwbj();

		Map<String, String> param = new HashMap<String, String>();
		//访问权限信息
		param.put("optID", optID);
		param.put("departID", departID);
		param.put("accessFlag", accessFlag);
		//当前节点信息
		param.put("nodeId", nodeId);
		param.put("nodeType", nodeType);
		
		//获取树
		List<TreeNode> tree = commonManager.getTree(treeType, param);
		if(tree != null && tree.size() > 0){
			responseJson(tree, false);
		}
		
		return null;
	}
	
	/**
	 * 公共树页面初始化，所有公共树通过这个方法获取
	 * @return
	 */
	public String initTree() {
		return "initTree";
	}
}