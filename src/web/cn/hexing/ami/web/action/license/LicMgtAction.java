package cn.hexing.ami.web.action.license;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import cn.hexing.ami.dao.common.pojo.license.Client;
import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.service.license.LicMgtManagerInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.action.BaseAction;
import cn.hexing.ami.web.actionInf.DbWorksInf;
import cn.hexing.ami.web.actionInf.QueryInf;

/**
 * @Description license管理
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-11-4
 * @version AMI3.0
 */
public class LicMgtAction extends BaseAction  implements DbWorksInf, QueryInf {
	private static final long serialVersionUID = 2692219481118216231L;
	
	//客户编号
	private String clientNo;
	//license编号
	private String licNo;
	//操作类型
	private String optType;
	//操作对象
	private String optObj;
	//客户
	private Client client;
	//license
	private License license;
	//机器硬件信息上传文件
	private File uploadFile;
	//当前时间
	private String currentDate;
	
	private LicMgtManagerInf licMgtManager = null;
	
	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getOptObj() {
		return optObj;
	}

	public void setOptObj(String optObj) {
		this.optObj = optObj;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public LicMgtManagerInf getLicMgtManager() {
		return licMgtManager;
	}

	public void setLicMgtManager(LicMgtManagerInf licMgtManager) {
		this.licMgtManager = licMgtManager;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * 初始化license管理主页面
	 */
	public  String init(){
		return SUCCESS;
	}
	
	/**
	 * 初始化客户管理页面
	 */
	public  String initClient(){
		//如果是编辑操作，需要获取对应客户信息
		if("edit".equals(optType)){
			client = licMgtManager.getClientById(clientNo);
		}
		return "initClient";
	}
	
	/**
	 * 初始化license管理页面
	 */
	public  String initLic(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(new Date());
		//如果是编辑操作，需要获取对应license信息
		if("edit".equals(optType)){
			license = licMgtManager.getLicById(licNo);
		}
		return "initLic";
	}
	
	/**
	 * 初始化机器信息导入页面
	 */
	public  String initMach(){
		return "initMach";
	}

	/**
	 * 查询客户信息
	 */
	public String query() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		//客户号
		if(!StringUtil.isEmptyString(clientNo)){
			param.put("clientNo", clientNo);
		}
		Map<String, Object> m = licMgtManager.query(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}

	/**
	 * 查询license信息
	 */
	public String queryDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		//客户号
		if(!StringUtil.isEmptyString(clientNo)){
			param.put("clientNo", clientNo);
		}
		Map<String, Object> m = licMgtManager.queryDetail(param, start, limit, dir, sort, isExcel);
		responseGrid(m);
		return null;
	}

	/**
	 * 生成license文件
	 * @param licNo
	 * @return
	 * @throws IOException 
	 */
	public ActionResult generateLicenseFile() throws IOException{
		ActionResult re = new ActionResult();
		//获取免费license
		if("freeLicense".equals(licNo))
			re = licMgtManager.generateFreeLicenseFile();
		else
			re = licMgtManager.generateLicenseFile(licNo);
		if(re.isSuccess()) {
			String licenseFile = (String)re.getDataObject();
			response.setContentType("application/xml;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"license.hx\"");
			ServletOutputStream out = null;
			try {
				out = response.getOutputStream();
				out.write(StringUtil.getUTF8Bytes(licenseFile));
				out.flush();
				out.close();
			} catch (IOException e) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
			}
		}else{
			//返回导入结果信息
			response.setContentType("text/html;charset=UTF-8"); 
			response.getWriter().print("{success:'" + re.isSuccess() + "', msg:'" + re.getMsg() + "'}");
		}
		return null;
	}
	
	/**
	 * 导入硬件信息
	 * @return
	 * @throws IOException 
	 */
	public ActionResult importMachInfo() throws IOException{
		//导入硬件信息文件
		ActionResult re = licMgtManager.importMachInfo(uploadFile, clientNo);
		//返回导入结果信息
		response.setContentType("text/html;charset=UTF-8"); 
		response.getWriter().print("{success:'" + re.isSuccess() + "', msg:'" + re.getMsg() + "'}");
		return null;
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		return licMgtManager.doDbWorks(czid, param);
	}

	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
		licMgtManager.dbLogger(czid, cznr, czyId, unitCode);
	}
	
}
