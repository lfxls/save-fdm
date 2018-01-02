package cn.hexing.ami.service.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.license.Client;
import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.HardwareUtil;
import cn.hexing.ami.util.RSACoder;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.XmlUtil;

/**
 * @Description license管理service
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2012-11-4
 * @version AMI3.0
 */
public class LicMgtManager implements LicMgtManagerInf {
	private static String sqlId = "licMgt.";
	
	private static String menuId = "123456";
	
	private BaseDAOIbatis baseDAOIbatis = null;
	
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 查询客户信息
	 */
	public Map<String, Object> query(Map<String, Object> param, String start,
			String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getClientList", start, limit, dir, sort, isExcel);
	}

	/**
	 * 查询license信息
	 */
	public Map<String, Object> queryDetail(Map<String, Object> param,
			String start, String limit, String dir, String sort, String isExcel) {
		return baseDAOIbatis.getExtGrid(param, sqlId + "getLicList", start, limit, dir, sort, isExcel);
	}

	/**
	 * 根据客户编号获取客户信息
	 */
	public Client getClientById(String clientNo) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("clientNo", clientNo);
		List<Object> result = baseDAOIbatis.queryForList(sqlId + "getClientById", param);
		if(result!=null && result.size()>0){
			return (Client) result.get(0);
		}
		return null;
	}

	/**
	 * 根据license编号获取license信息
	 */
	public License getLicById(String licNo) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("licNo", licNo);
		List<Object> result = baseDAOIbatis.queryForList(sqlId + "getLicById", param);
		if(result!=null && result.size()>0){
			return (License) result.get(0);
		}
		return null;
	}
	
	/**
	 * 解析系统license文件
	 */
	public ActionResult resolveLicense() {
		ActionResult re = new ActionResult();
		//license文件
		License license = new License();
		//明文数据
		String data = "";
		//签名
		String signature = "";
		
		//读取license文件
		Document document = null;
		try {
			//处理路径中有隔空的问题
			URI uri = new URI(LicMgtManager.class.getResource("/res/license.hx").getFile());
			document = XmlUtil.getDocument(uri.getPath());
			Element root = document.getRootElement();
			//电表数量
			String meterNum = StringUtil.getString(root.elementText("meternum"));
			//电表数量校验标志
			boolean meterLimit = !"nolimit".equals(meterNum);
			license.setMeterNum(meterNum);
			license.setMeterLimitFlag(meterLimit);
			data += meterNum;
			
			//过期时间
			String expiration = StringUtil.getString(root.elementText("expiration"));
			license.setExpDate(expiration);
			data += expiration;
			
			//有效期
			String validDay = StringUtil.getString(root.elementText("validday"));
			license.setValidDay(validDay);
			//过期时间校验标志
			license.setExpLimitFlag(!("never".equals(expiration) && "nolimit".equals(validDay)));
			data += validDay;
			
			//加密狗序列号
			String softDogId = StringUtil.getString(root.elementText("softdogid"));
			license.setSoftDogId(softDogId);
			data += softDogId;
			
			/*//CPU标识
			String cpuId = StringUtil.getString(root.elementText("cpuid"));
			license.setCpuId(cpuId);
			data += cpuId;
			
			//BIOS标识
			String biosId = StringUtil.getString(root.elementText("biosid"));
			license.setBiosId(biosId);
			data += biosId;
			
			//硬盘标识
			String diskId = StringUtil.getString(root.elementText("diskid"));
			license.setDiskId(diskId);
			data += diskId;*/
			license.setMachInfoLimitFlag(!("any".equals(softDogId)));
			
			//签名
			signature = StringUtil.getString(root.elementText("signature"));
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("读取license文件失败！");
			return re;
		}
		
		boolean status = false;
		try {
			//获取公共密钥
			URI uri = new URI(LicMgtManager.class.getResource("/res/app.properties").getFile());
			FileInputStream is = new FileInputStream(uri.getPath());
			Properties props = new Properties();
			props.load(is);
			String licensePublicKey = (String)props.get("licensePublicKey");
			//对签名进行验证
			status = RSACoder.verify(data.getBytes(), licensePublicKey, signature);
		} catch (Exception e) {
			status = false;
		}
		//签名验证失败
		if(!status){
			re.setSuccess(false);
			re.setMsg("license文件非法！");
			return re;
		}
		
		re.setSuccess(true);
		re.setDataObject(license);
		return re;
	}
	
	/**
	 * 验证机器信息是否符合license文件的授权
	 */
	public boolean validateMachInfo(License license){
		boolean isMatch = false;
		try {
			//获取加密狗序列号
			Map<String, String> hardInfo = HardwareUtil.getHardwareInfo();
			String softDogId = hardInfo.get("softDogId");
			//和license文件中绑定的加密狗序列号进行对比
			if("any".equals(license.getSoftDogId()) || softDogId.equals(license.getSoftDogId()))
				isMatch = true;
			else
				isMatch = false;
			
			/*String cpuId = hardInfo.get("cpuId");
			String diskId = hardInfo.get("diskId");
			String biosId = hardInfo.get("biosId");*/

			//和license文件中绑定的机器信息进行对比
			/*if("any".equals(license.getCpuId()) || cpuId.equals(license.getCpuId()))
				if("any".equals(license.getDiskId()) || diskId.equals(license.getDiskId()))
					if("any".equals(license.getBiosId()) || biosId.equals(license.getBiosId()))
						isMatch = true;
					else
						isMatch = false;
				else
					isMatch = false;
			else
				isMatch = false;*/
		} catch (Exception e) {
			isMatch = false;
		}
		return isMatch;
	}

	/**
	 * 生成license文件
	 */
	public ActionResult generateLicenseFile(String licNo) {
		ActionResult re = new ActionResult();
		Map<String, String> param = new HashMap<String, String>();
		param.put("licNo", licNo);
		List<Object> result = baseDAOIbatis.queryForList(sqlId + "generateLicenseFile", param);
		License license = (License)result.get(0);
		//电表数量
		String meterNum = StringUtil.isEmptyString(license.getMeterNum()) ? "nolimit" : StringUtil.getString(license.getMeterNum());
		//过期时间
		String expiration = StringUtil.isEmptyString(license.getExpDate()) ? "never" : StringUtil.getString(license.getExpDate());
		//CPU标识
		String cpuId = StringUtil.isEmptyString(license.getCpuId()) ? "any" : StringUtil.getString(license.getCpuId());
		//BIOS标识
		String biosId = StringUtil.isEmptyString(license.getBiosId()) ? "any" : StringUtil.getString(license.getBiosId());
		//硬盘标识
		String diskId = StringUtil.isEmptyString(license.getDiskId()) ? "any" : StringUtil.getString(license.getDiskId());
		//有效期
		String validDay = "nolimit";
		if(!"never".equals(expiration)){
			//创建时间
			String createDate = license.getCreateDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date expDate = null;
			Date nowDate = null;
			try {
				expDate = sdf.parse(expiration);
				nowDate = sdf.parse(createDate);
			} catch (ParseException e1) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
				return re;
			}
			Calendar expCal = Calendar.getInstance();
			Calendar nowCal = Calendar.getInstance();
			expCal.setTime(expDate);
			nowCal.setTime(nowDate);
			//计算license的有效天数
			if(expCal.after(nowCal))
				validDay = String.valueOf((expCal.getTime().getTime() - nowCal.getTime().getTime())/(24*60*60*1000));
			else
				validDay = "";
		}

		//明文数据
		String data = meterNum + expiration + validDay + cpuId + biosId + diskId;
		
		String signature = "";
		try {
			//获取私钥
			URI uri = new URI(LicMgtManager.class.getResource("/res/prepay.properties").getFile());
			FileInputStream is = new FileInputStream(uri.getPath());
			Properties props = new Properties();
			props.load(is);
			String licensePrivateKey = (String)props.get("licensePrivateKey");
			//生成签名
			signature = RSACoder.sign(data.getBytes(), licensePrivateKey);
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("license文件签名生成失败！");
			return re;
		}
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("license");
		root.addElement("cpuid").addText(cpuId);
	
		root.addElement("biosid").addText(biosId);
	
		root.addElement("diskid").addText(diskId);
	
		root.addElement("meternum").addText(meterNum);
	
		root.addElement("expiration").addText(expiration);
	
		if(!StringUtil.isEmptyString(validDay))
			root.addElement("validday").addText(validDay);
	
		root.addElement("signature").addText(signature);
		
		//格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setTrimText(false);
		StringWriter writer = new StringWriter();
		//格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer,format);
		//将document写入到输出流
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			re.setSuccess(false);
			re.setMsg("license文件生成失败！");
			return re;
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
				return re;
			}
		}
		
		re.setSuccess(true);
		re.setDataObject(writer.toString());

		return re;
	}
	
	/**
	 * 生成license文件
	 */
	public ActionResult generateLicenseFile(License license) {
		ActionResult re = new ActionResult();
		//电表数量
		String meterNum = StringUtil.isEmptyString(license.getMeterNum()) ? "nolimit" : StringUtil.getString(license.getMeterNum());
		//过期时间
		String expiration = StringUtil.isEmptyString(license.getExpDate()) ? "never" : StringUtil.getString(license.getExpDate());
		//加密狗序列号
		String softDogId = StringUtil.isEmptyString(license.getSoftDogId()) ? "any" : StringUtil.getString(license.getSoftDogId());
		//有效期
		String validDay = "nolimit";
		if(!"never".equals(expiration)){
			//创建时间
			String createDate = license.getCreateDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date expDate = null;
			Date nowDate = null;
			try {
				expDate = sdf.parse(expiration);
				nowDate = sdf.parse(createDate);
			} catch (ParseException e1) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
				return re;
			}
			Calendar expCal = Calendar.getInstance();
			Calendar nowCal = Calendar.getInstance();
			expCal.setTime(expDate);
			nowCal.setTime(nowDate);
			//计算license的有效天数
			if(expCal.after(nowCal))
				validDay = String.valueOf((expCal.getTime().getTime() - nowCal.getTime().getTime())/(24*60*60*1000));
			else
				validDay = "";
		}

		//明文数据
		String data = meterNum + expiration + validDay + softDogId;
		
		String signature = "";
		try {
			//获取私钥
			URI uri = new URI(LicMgtManager.class.getResource("/res/prepay.properties").getFile());
			FileInputStream is = new FileInputStream(uri.getPath());
			Properties props = new Properties();
			props.load(is);
			String licensePrivateKey = (String)props.get("licensePrivateKey");
			//生成签名
			signature = RSACoder.sign(data.getBytes(), licensePrivateKey);
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("license文件签名生成失败！");
			return re;
		}
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("license");
		root.addElement("softdogid").addText(softDogId);
	
		root.addElement("meternum").addText(meterNum);
	
		root.addElement("expiration").addText(expiration);
	
		if(!StringUtil.isEmptyString(validDay))
			root.addElement("validday").addText(validDay);
	
		root.addElement("signature").addText(signature);
		
		//格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setTrimText(false);
		StringWriter writer = new StringWriter();
		//格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer,format);
		//将document写入到输出流
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			re.setSuccess(false);
			re.setMsg("license文件生成失败！");
			return re;
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
				return re;
			}
		}
		
		re.setSuccess(true);
		re.setDataObject(writer.toString());

		return re;
	}
	
	/**
	 * 生成免费license文件
	 */
	public ActionResult generateFreeLicenseFile() {
		ActionResult re = new ActionResult();
		//电表数量
		String meterNum = "nolimit";
		//过期时间
		String expiration = "never";
		//CPU标识
		String cpuId = "any";
		//BIOS标识
		String biosId = "any";
		//硬盘标识
		String diskId = "any";
		//有效期
		String validDay = "nolimit";
		//明文数据
		String data = meterNum + expiration + validDay + cpuId + biosId + diskId;
		
		String signature = "";
		try {
			//获取私钥
			URI uri = new URI(LicMgtManager.class.getResource("/res/prepay.properties").getFile());
			FileInputStream is = new FileInputStream(uri.getPath());
			Properties props = new Properties();
			props.load(is);
			String licensePrivateKey = (String)props.get("licensePrivateKey");
			//生成签名
			signature = RSACoder.sign(data.getBytes(), licensePrivateKey);
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("license文件签名生成失败！");
			return re;
		}
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("license");
		root.addElement("cpuid").addText(cpuId);
		
		root.addElement("biosid").addText(biosId);
	
		root.addElement("diskid").addText(diskId);
	
		root.addElement("meternum").addText(meterNum);
	
		root.addElement("expiration").addText(expiration);
	
		root.addElement("validday").addText(validDay);
	
		root.addElement("signature").addText(signature);
		
		//格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		format.setTrimText(false);
		StringWriter writer = new StringWriter();
		//格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer,format);
		//将document写入到输出流
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			re.setSuccess(false);
			re.setMsg("license文件生成失败！");
			return re;
		} finally {
			try {
				xmlWriter.close();
			} catch (IOException e) {
				re.setSuccess(false);
				re.setMsg("license文件生成失败！");
				return re;
			}
		}
		
		re.setSuccess(true);
		re.setDataObject(writer.toString());

		return re;
	}
	
	/**
	 * 导入硬件信息
	 */
	public ActionResult importMachInfo(File uploadFile, String clientNo){
		ActionResult re = new ActionResult();
		//硬件信息
		String machInfo = "";
		try {
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				machInfo += new String(buffer, 0, len, "UTF-8");
			}
			//删除临时文件
			uploadFile.delete();
			fis.close();
		} catch (Exception e) {
			re.setMsg("上传硬件信息失败！");
			re.setSuccess(false);
			return re;
		}
		
		Document doc = null;
		try {
			doc = XmlUtil.GetDocument(machInfo);
			//获取根节点
			Element root = doc.getRootElement();
			//获取硬件信息
			String cpuId = root.elementText("cpuid");
			String biosId = root.elementText("biosid");
			String diskId = root.elementText("diskid");
			//更新客户的硬件信息
			Map<String, String> param = new HashMap<String, String>();
			param.put("clientNo", clientNo);
			param.put("cpuId", cpuId);
			param.put("biosId", biosId);
			param.put("diskId", diskId);
			baseDAOIbatis.update(sqlId + "updateMachInfo", param);
			
		} catch (Exception e) {
			re.setMsg("解析硬件信息失败！");
			re.setSuccess(false);
			return re;
		}
		re.setSuccess(true);
		re.setMsg("导入硬件信息成功！");
		return re;	
	}
	
	public ActionResult doDbWorks(String czid, Map<String, String> param) {
		ActionResult re = new ActionResult();
		//客户操作
		if("client".equals(param.get("optObj"))){
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addClient(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updClient(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = delClient(param);
			}
		}else if("license".equals(param.get("optObj"))){
			//license操作
			if((menuId + Constants.ADDOPT).equals(czid)) { 
				//新增
				re = addLicense(param);
			} else if((menuId + Constants.UPDOPT).equals(czid)){ 
				//更新
				re = updLicense(param);
			} else if((menuId + Constants.DELOPT).equals(czid)){ 
				//删除
				re = delLicense(param);
			}
		}
		return re;
	}

	/**
	 * 新增客户
	 * @param param
	 * @return
	 */
	private ActionResult addClient(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "inClient", param);
		re.setSuccess(true);
		re.setMsg("新增客户成功！");
		return re;
	}
	
	/**
	 * 修改客户信息
	 * @param param
	 * @return
	 */
	private ActionResult updClient(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.update(sqlId + "updClient", param);
		re.setSuccess(true);
		re.setMsg("修改客户成功！");
		return re;
	}
	
	/**
	 * 删除客户
	 * @param param
	 * @return
	 */
	private ActionResult delClient(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.update(sqlId + "delClient", param);
		re.setSuccess(true);
		re.setMsg("删除客户成功！");
		return re;
	}
	
	/**
	 * 新增license
	 * @param param
	 * @return
	 */
	private ActionResult addLicense(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.insert(sqlId + "inLicense", param);
		re.setSuccess(true);
		re.setMsg("新增license成功！");
		return re;
	}
	
	/**
	 * 修改license
	 * @param param
	 * @return
	 */
	private ActionResult updLicense(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.update(sqlId + "updLicense", param);
		re.setSuccess(true);
		re.setMsg("修改license成功！");
		return re;
	}
	
	/**
	 * 删除license
	 * @param param
	 * @return
	 */
	private ActionResult delLicense(Map<String, String> param) {
		ActionResult re = new ActionResult();
		baseDAOIbatis.update(sqlId + "delLicense", param);
		re.setSuccess(true);
		re.setMsg("删除license成功！");
		return re;
	}
	
	public void dbLogger(String czid, String cznr, String czyId, String unitCode) {
	}
}
