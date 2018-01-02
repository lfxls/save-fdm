package cn.hexing.ami.web.action.main.srvyMgt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.service.main.srvyMgt.SurPlanManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;
/**
 * 勘察计划
 * @author HEC214
 *
 */
public class SurPlanAction extends BaseAction implements QueryInf, DbWorksInf {
	

	private static final long serialVersionUID = 7120707313839907981L;
	private static Logger logger = Logger.getLogger(SurPlanAction.class.getName());

	private SurPlanManagerInf surPlanManager;
	
	private String cno, status, bussType, operateType, pid;
	private Map<String, String> singleMap;
	
	private File addSurPFile; //新勘察文件
	
	private List<Object> stList; //安装计划状态列表
	/**
	 * 页面初始化
	 */
	public String init(){
		
		stList = CommonUtil.getCodeNumber("planStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		for(int i=0;i<stList.size();i++){
			Map<String, String> map=new HashMap<String, String>();
			map=(Map<String, String>) stList.get(i);
			if("4".equals(map.get("BM"))){
				stList.remove(i);
			}
		}
		//初始化节点
		nodeText = StringUtil.isEmptyString(nodeText) == true ? this.getUnitName() : nodeText;
		nodeId = StringUtil.isEmptyString(nodeId) == true ? this.getUnitCode() : nodeId;
		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) == true ? this.getUnitCode() : nodeDwdm; //this.getNodeDwdm();
		nodeType = StringUtil.isEmptyString(nodeType) == true ? "dw" : nodeType; 
		
		return SUCCESS;
	}
	
	public String initPAdd(){
		return "surPlan_op";
	}
	
	public String initSurEdit(){
		singleMap = surPlanManager.getSurPByPid(pid);
		return "surPlan_op";
	}
	/**
	 * 初始勘察计划导入
	 * @return
	 */
	public String initSurPImport() {
		return "surPlnImp";
	}
	
	/**
	 * 档案导入
	 * @return
	 */
	public String importExcel(){
		ActionResult result = new ActionResult();
		String lang = getLang();
		try {
			Map<String, String> param = new HashMap<String, String>();
			String czyid =(String) session.getAttribute(Constants.CURR_STAFFID);
			param.put("CURR_STAFFID", czyid);
			
			if (addSurPFile != null) {
				FileInputStream fis = new FileInputStream(addSurPFile);
				result = surPlanManager.parseExcel(fis, param, Constants.IMPORT_INSP_SRVY, lang);
				//删除临时文件
				addSurPFile.delete();
			}
			
		} catch (Exception e) {
			logger.error(getText("mainModule.insMgt.plan.import.failed") + StringUtil.getExceptionDetailInfo(e));
			result.setSuccess(false);
			result.setMsg("mainModule.insMgt.plan.import.failed", getLang());
		} finally {
			addSurPFile = null; //新装表文件
		}
		//输出信息
		response.setContentType("text/html; charset=UTF-8"); 
		try {
			response.getWriter().print("{success:'" + StringUtil.getString(result.isSuccess()) 
									+ "', msgType:'" + StringUtil.getString(result.getDataObject())
									+ "', errMsg:'" + StringUtil.getString(result.getMsg()) + "'}");
		} catch (IOException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
		} 
		return null;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return surPlanManager.doDbWorks(czid, param);
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		surPlanManager.dbLogger(czid, cznr, czyId, unitCode);

	}

	@Override
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Constants.APP_LANG, this.getLang());
		param.put("cno", cno);
		param.put("status", status);
		param.put("bussType", bussType);
		
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = surPlanManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public SurPlanManagerInf getSurPlanManager() {
		return surPlanManager;
	}

	public void setSurPlanManager(SurPlanManagerInf surPlanManager) {
		this.surPlanManager = surPlanManager;
	}

	public List<Object> getStList() {
		return stList;
	}

	public void setStList(List<Object> stList) {
		this.stList = stList;
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Map<String, String> getSingleMap() {
		return singleMap;
	}

	public void setSingleMap(Map<String, String> singleMap) {
		this.singleMap = singleMap;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public File getAddSurPFile() {
		return addSurPFile;
	}

	public void setAddSurPFile(File addSurPFile) {
		this.addSurPFile = addSurPFile;
	}
	
	

}
