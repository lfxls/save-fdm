package cn.hexing.ami.service.license;

import java.io.File;

import cn.hexing.ami.dao.common.pojo.license.Client;
import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.serviceInf.DbWorksInf;
import cn.hexing.ami.serviceInf.QueryInf;
import cn.hexing.ami.util.ActionResult;

/**
 * @Description license管理service接口
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-11-4
 * @version AMI3.0
 */
public interface LicMgtManagerInf  extends DbWorksInf, QueryInf{
	/**
	 * 根据客户编号获取客户对象
	 * @param clientNo
	 * @return
	 */
	public Client getClientById(String clientNo);
	
	/**
	 * 根据客户编号获取客户对象
	 * @param licNo
	 * @return
	 */
	public License getLicById(String licNo);
	
	/**
	 * 解析系统license文件
	 * @return
	 */
	public ActionResult resolveLicense();
	
	/**
	 * 验证机器信息是否符合license文件的授权
	 * @param license
	 * @return
	 */
	public boolean validateMachInfo(License license);
	
	/**
	 * 生成license文件
	 * @param licNo
	 * @return
	 */
	public ActionResult generateLicenseFile(String licNo);
	
	/**
	 * 生成license文件
	 * @param license
	 * @return
	 */
	public ActionResult generateLicenseFile(License license);
	
	/**
	 * 导入硬件信息
	 * @param uploadFile
	 * @param clientNo
	 * @return
	 */
	public ActionResult importMachInfo(File uploadFile, String clientNo);
	
	/**
	 * 生成免费license文件
	 */
	public ActionResult generateFreeLicenseFile();
}
