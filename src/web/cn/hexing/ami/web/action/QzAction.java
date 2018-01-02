package cn.hexing.ami.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import cn.hexing.ami.serviceInf.QzManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.TempUtil;
import cn.hexing.ami.web.actionInf.DbWorksInf;

public class QzAction extends CommonAction implements DbWorksInf{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3396332319699800428L;
	private String doType;//操作类型（左边树还是ext操作）
	private String qzfl;//用户类型
	private String qzlx;//终端or表计群组
	private String flq;//全选标志
	private String colNum;//标志列
	private String name;//模块名 ext界面
	private String zdjh;//左边树加入群组
	private List<Object> allGroupList; // 群组列表
	
	public List<Object> getAllGroupList() {
		return allGroupList;
	}

	public void setAllGroupList(List<Object> allGroupList) {
		this.allGroupList = allGroupList;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getQzfl() {
		return qzfl;
	}

	public void setQzfl(String qzfl) {
		this.qzfl = qzfl;
	}

	public String getFlq() {
		return flq;
	}

	public void setFlq(String flq) {
		this.flq = flq;
	}

	public String getColNum() {
		return colNum;
	}

	public void setColNum(String colNum) {
		this.colNum = colNum;
	}

	private QzManagerInf qzManager;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQzManager(QzManagerInf qzManager) {
		this.qzManager = qzManager;
	}
	public String getZdjh() {
		return zdjh;
	}

	public void setZdjh(String zdjh) {
		this.zdjh = zdjh;
	}

	public String getQzlx() {
		return qzlx;
	}

	public void setQzlx(String qzlx) {
		this.qzlx = qzlx;
	}

	public String initQz() throws IOException{
		// 保存类型供页面js调用
		request.getSession().removeAttribute("qzZdjhs");
		
		// 左边数加入群组时执行下面（和grid里增加群组区分）
		if(!StringUtil.isEmptyString(zdjh)){
			// 保存终端局号
        	request.getSession().removeAttribute("groupZdjhs");
        	request.getSession().setAttribute("groupZdjhs", zdjh);
        	
        	// 删除原有的群组文件
    		String groupFile = (String) request.getSession().getAttribute("groupFileName");
    		if(!StringUtil.isEmptyString(groupFile)) { //删除旧Group文件
    			request.getSession().removeAttribute("groupFileName");
        		File file = new File(Constants.TMPEFILE + groupFile);
                if (file.exists()) { 
                     FileUtils.forceDelete(file);
                }
    		}
		}
		
		if(StringUtil.isEmptyString(colNum)){
			colNum="0";
		}
		if("addGroup".equals(doType)){
			request.getSession().setAttribute("qzfl", qzfl);
			request.getSession().setAttribute("qzlx", qzlx);
	        String fileName = (String) request.getSession().getAttribute("fileName" + name);
			String flg = request.getParameter("flg");
			Set<String> zdjhList = new HashSet<String>();
			String zdjhs = "";
			// 从页面得到群组类型的参数,用于查询群组 选择全部时直接从文件里取数据
			if("true".equals(flg)){
				zdjhs = getIdsFormFileByName(fileName, "ZH");
			} else {
				zdjhs = (String)request.getSession().getAttribute("groupZdjhs"); //从session获取
			}
			
			// 去除重复记录
			for(String zdjh:zdjhs.split(",")){
				zdjhList.add(zdjh);
			}

			if (zdjhList != null && zdjhList.size() > 0) {
				request.getSession().setAttribute("qzZdjhs", (String[])zdjhList.toArray(new String[0]));
			}
		// 左边树加入群组
		}else if("LeftaddGroup".equals(doType)){
			Map<String, Object> queryInfo = (Map<String, Object>)session.getAttribute("queryInfo");
//			String viewType = String.valueOf(queryInfo.get("viewType"));
//			if("sb".equals(viewType)){
//				qzfl = "01";
//			}else{
//				qzfl = "04";
//			}
			request.getSession().setAttribute("qzfl", qzfl);
			request.getSession().setAttribute("qzlx", qzlx);
			List<Object> groupUserList = qzManager.getAdvZdjh(queryInfo);
			if(groupUserList != null && groupUserList.size() != 0){
				List<String> zdjhStr = new ArrayList<String>();
				for(Object a:groupUserList){
					Map<String,Object> m = (Map<String,Object>)a;
					zdjhStr.add(String.valueOf(m.get("ZDJH")));
				}
				request.getSession().setAttribute("qzZdjhs", (String[])zdjhStr.toArray(new String[0]));
			}
		} 
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("qzfl", qzfl);
		paramMap.put("czyid", this.getCzyid());
		allGroupList = qzManager.allGroup(paramMap);
		
		return "qzDetail";
	}

	public String query() throws Exception {
		String[] zdjh = (String[])request.getSession().getAttribute("qzZdjhs");
		String qzlx = (String)request.getSession().getAttribute("qzlx");
		String qzfl = (String)request.getSession().getAttribute("qzfl");
		int index = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String name =request.getParameter("name");
		
		String fileName = (String)request.getSession().getAttribute("groupFileName"+qzlx);
		// 加入群组时直接写文件
		String idPro ="ZDJH";
		if("bj".equals(qzlx)){
			idPro = "BJJH";
		}
//		if(StringUtil.isEmptyString(fileName)){
		fileName = name + this.getCzyid() + System.currentTimeMillis()+ ".txt";
		// 新建空文件时候不验证重复
		TempUtil.listToFile(fileName, new ArrayList(),Integer.valueOf(colNum),idPro,true, null,"0,0");
		request.getSession().setAttribute("groupFileName"+qzlx, fileName);
		
		// 分批导入
		int count = zdjh.length / 500;
		if((zdjh.length % 500) > 0) {
			count ++;
		}
		List<Object> fileList = new ArrayList<Object>();
		for(int i = 0; i < count;i++){
			// 把带过来的终端局号放入到文件中去
	        Map<String, Object> newMap = new HashMap<String, Object>();
	        newMap.put("qzlx", qzlx);
	        newMap.put("qzfl", qzfl);
	        newMap.put("zdjh", StringUtil.strDateToGrid(zdjh,i,500));
			
	        newMap.put(Constants.APP_LANG, this.getLang());
			List<Object> newList = qzManager.getList(newMap);
			fileList.addAll(newList);
		}
		if("bj".equals(qzlx)){
			//表计
			TempUtil.listToFile(fileName,fileList,Integer.valueOf(colNum),idPro,true,new String[]{"BJJH","HH","HM","BJLX"},fileList.size()+",0");
		}else{
			//终端
			TempUtil.listToFile(fileName,fileList,Integer.valueOf(colNum),idPro,true,new String[]{"ZDJH","ZDLJDZ","ZDYT","ZDZT"},fileList.size()+"0");
		}
		Map<String, Object> result = TempUtil.filePaging(fileName,index,limit);
//		responseJson(result, true);
		responseGrid(result);
		return null;
	}

	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return qzManager.doDbWorks(czid, param);
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		qzManager.dbLogger(czid, cznr, czyId, unitCode);		
	}
}
