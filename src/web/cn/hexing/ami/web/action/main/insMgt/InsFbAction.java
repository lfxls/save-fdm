package cn.hexing.ami.web.action.main.insMgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hexing.ami.service.main.insMgt.InsFbManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DatabaseUtil;
import cn.hexing.ami.util.ImageUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description 安装反馈
 * @author xcx
 * @Copyright 
 * @time：
 * @version FDM
 */

public class InsFbAction extends BaseAction implements QueryInf, DbWorksInf{

	private static final long serialVersionUID = -2556508527349537455L;
	private String cno;//户号
	private String status;//反馈状态
	private String bussType;//业务类型
	private String tfName;//变压器名称
	private List<Object> stList;//反馈状态列表
	private List<Object> btList;//业务类型列表
	private String pid;//计划id
	private String msn;//表号
	private String flag;//新老表号标志，0=新表，1=老表
	private String pics;//图片地址，多个图片已逗号分隔
	private String spics;//缩略图片地址，多个图片已逗号分隔
	private String dataType;//数据类型 0=工单派发 1=用户派发 2=现场派发
	private String plnStatus;//安装计划状态
	private List<Object> dtList;//数据类型列表
	private List<Object> plnStList;//安装计划状态列表
	private String tiid;//测试id
	private String verid;//内控版本号
	private String omsn;//老表号
	InsFbManagerInf insFbManager;
	
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

	public List<Object> getStList() {
		return stList;
	}

	public void setStList(List<Object> stList) {
		this.stList = stList;
	}

	public List<Object> getBtList() {
		return btList;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setBtList(List<Object> btList) {
		this.btList = btList;
	}

	public String getTfName() {
		return tfName;
	}

	public void setTfName(String tfName) {
		this.tfName = tfName;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getSpics() {
		return spics;
	}

	public void setSpics(String spics) {
		this.spics = spics;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPlnStatus() {
		return plnStatus;
	}

	public void setPlnStatus(String plnStatus) {
		this.plnStatus = plnStatus;
	}

	public List<Object> getDtList() {
		return dtList;
	}

	public void setDtList(List<Object> dtList) {
		this.dtList = dtList;
	}

	public List<Object> getPlnStList() {
		return plnStList;
	}

	public void setPlnStList(List<Object> plnStList) {
		this.plnStList = plnStList;
	}

	public String getTiid() {
		return tiid;
	}

	public void setTiid(String tiid) {
		this.tiid = tiid;
	}

	public String getVerid() {
		return verid;
	}

	public void setVerid(String verid) {
		this.verid = verid;
	}

	public String getOmsn() {
		return omsn;
	}

	public void setOmsn(String omsn) {
		this.omsn = omsn;
	}

	public InsFbManagerInf getInsFbManager() {
		return insFbManager;
	}

	public void setInsFbManager(InsFbManagerInf insFbManager) {
		this.insFbManager = insFbManager;
	}

	@Override
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return null;
	}

	@Override
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		insFbManager.dbLogger(czid, cznr, czyId, unitCode);
	}

	/**
	 * 初始化表反馈页面
	 * @return
	 */
	public String init() {
		stList = CommonUtil.getCodeNumber("fbStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		plnStList = CommonUtil.getCodeNumber("planStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		dtList = CommonUtil.getCodeNumber("fbDataType", this.getLang(), "ASC", true);  //false表示不加全选项
//		nodeText = StringUtil.isEmptyString(nodeText) == true ? this.getUnitName() : nodeText;
//		nodeId = StringUtil.isEmptyString(nodeId) == true ? this.getUnitCode() : nodeId;
//		nodeDwdm = StringUtil.isEmptyString(nodeDwdm) == true ? this.getUnitCode() : nodeDwdm; //this.getNodeDwdm();
//		nodeType = StringUtil.isEmptyString(nodeType) == true ? "dw" : nodeType; 
		for(int i = 0; i < plnStList.size(); i++) {
			Map<String,Object> plnStMap = (Map<String, Object>) plnStList.get(i);
			String bm = StringUtil.getValue(plnStMap.get("BM"));
			if(Constants.PLN_STATUS_ASSIGNED.equals(bm) || 
					Constants.PLN_STATUS_DISPATCHED.equals(bm) || 
					Constants.PLN_STATUS_UNHANDLED.equals(bm) ||
					Constants.PLN_STATUS_UNPAID.equals(bm) ||
					Constants.PLN_STATUS_PAID.equals(bm)) {
				plnStList.remove(plnStMap);
				i--;
			}
			
		}
		return "meterFb";
	}
	
	/**
	 * 初始化集中器反馈页面
	 * @return
	 */
	public String initDcuFb() {
		stList = CommonUtil.getCodeNumber("fbStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		return "dcuFb";
	}
	
	/**
	 * 初始化采集器反馈页面
	 * @return
	 */
	public String initColFb() {
		stList = CommonUtil.getCodeNumber("fbStatus", this.getLang(), "ASC", true);  //false表示不加全选项
		btList = CommonUtil.getCodeNumber("bussType", this.getLang(), "ASC", true);  //false表示不加全选项
		return "colFb";
	}
	
	/**
	 * 初始化表参数反馈页面
	 * @return
	 */
	public String initMPhoto() {
		String path = InsFbAction.class.getResource("/").getPath();
		String dir = "";
		if(!StringUtil.isEmptyString(path)) {
			dir = path.substring(0,path.lastIndexOf("WEB-INF"));
		}
		pics = insFbManager.getPicsPath(pid, flag);
		if(!StringUtil.isEmptyString(pics)) {
			StringBuilder picsFiles = new StringBuilder();
			StringBuilder spicsFiles = new StringBuilder();
			String[] fileArray = pics.split(",");
			for(String fileStr : fileArray) {
				ImageUtil.resizeFile(dir, fileStr);
				if(picsFiles.length() == 0) {
					picsFiles.append(fileStr.split("\\.")[0] + "e." + fileStr.split("\\.")[1]);
				} else {
					picsFiles.append(",").append(fileStr.split("\\.")[0] + "e." + fileStr.split("\\.")[1]);
				}
				if(spicsFiles.length() == 0) {
					spicsFiles.append(fileStr.split("\\.")[0] + "n." + fileStr.split("\\.")[1]);
				} else {
					spicsFiles.append(",").append(fileStr.split("\\.")[0] + "n." + fileStr.split("\\.")[1]);
				}
			}
			if(picsFiles.length() != 0) {
				pics = picsFiles.toString();
			}
			if(spicsFiles.length() != 0) {
				spics = spicsFiles.toString();
			}
		}
		return "meter_photo";
	}
	
	public String convertPhotoPath(String files) {
		String newFiles = "";
		String[] fileArray = files.split(",");
		for(int i = 0; i < fileArray.length; i++) {
			String fileName = fileArray[i].substring(fileArray[i].lastIndexOf("/")+1);
			if(i == 0) {
				newFiles = "/picture/" + fileName;
			} else {
				newFiles = newFiles + "," + "/picture/" + fileName;
			}
		}
		return newFiles;
	}
	
	/**
	 * 初始化表设置参数反馈页面
	 * @return
	 */
	public String initMSetParam() {
		return "meter_setP";
	}
	
	/**
	 * 初始化表测试参数反馈页面
	 * @return
	 */
	public String initMTestParam() {
		return "meter_testP";
	}
	
	/**
	 * 初始化表读取参数反馈页面
	 * @return
	 */
	public String initMReadParam() {
		return "meter_readP";
	}
	
	/**
	 * 查询表反馈信息
	 */
	@Override
	public String query() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(cno)) {
			param.put("cno", cno);
		}
		if(!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(bussType)) {
			param.put("bussType", bussType);
		}
		if(!StringUtil.isEmptyString(plnStatus)) {
			param.put("plnStatus", plnStatus);
		}
		if(!StringUtil.isEmptyString(dataType)) {
			param.put("dataType", dataType);
		}
		if(!StringUtil.isEmptyString(pid)) {
			param.put("pid", pid);
		}
		if(!StringUtil.isEmptyString(msn)) {
			param.put("msn", msn);
		}
		if(!StringUtil.isEmptyString(omsn)) {
			param.put("omsn", omsn);
		}
		param.put(Constants.APP_LANG, this.getLang());
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "C");
		Map<String, Object> re = insFbManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询集中器反馈信息
	 */
	public String queryDcuFb() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(tfName)) {
			param.put("tfName", tfName);
		}
		if(!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(bussType)) {
			param.put("bussType", bussType);
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "T");
		Map<String, Object> re = insFbManager.queryDcuFb(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询采集器反馈信息
	 */
	public String queryColFb() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(tfName)) {
			param.put("tfName", tfName);
		}
		if(!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if(!StringUtil.isEmptyString(bussType)) {
			param.put("bussType", bussType);
		}
		DatabaseUtil.nodeFilter(param, nodeId, nodeType, nodeDwdm, 
				this.getCzyid(), this.getFwbj(), this.getBm(), "T");
		Map<String, Object> re = insFbManager.queryColFb(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}

	@Override
	public String queryDetail() {
		return null;
	}
	
	/**
	 * 查询设置参数
	 * @return
	 */
	public String querySetParam() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pid", pid);
		param.put("msn", msn);
		param.put("verid", verid);
		param.put("lang", this.getLang());
		Map<String,Object> re = insFbManager.querySetParam(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询测试参数
	 * @return
	 */
	public String queryTestParam() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pid", pid);
		param.put("msn", msn);
		param.put("verid", verid);
		param.put("lang", this.getLang());
		Map<String,Object> re = insFbManager.queryTestParam(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询测试参数
	 * @return
	 */
	public String queryTestOBIS() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pid", pid);
		param.put("msn", msn);
		param.put("tiid", tiid);
		param.put("verid", verid);
		param.put("lang", this.getLang());
		Map<String,Object> re = insFbManager.queryTestOBIS(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
	
	/**
	 * 查询读取参数
	 * @return
	 */
	public String queryReadParam() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pid", pid);
		param.put("msn", msn);
		param.put("verid", verid);
		param.put("flag", flag);
		param.put("lang", this.getLang());
		Map<String,Object> re = insFbManager.queryReadParam(param, start, limit, dir, sort, isExcel);
		responseGrid(re);
		return null;
	}
}
