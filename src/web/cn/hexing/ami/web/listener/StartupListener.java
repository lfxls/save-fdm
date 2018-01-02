package cn.hexing.ami.web.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.service.CommonManager;
import cn.hexing.ami.service.LoginManager;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.MenuUtil;
import cn.hexing.ami.util.SpringContextUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.TempUtil;

/** 
 * @Description 系统启动加载
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-19
 * @version AMI3.0 
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(StartupListener.class.getName());
	private static String pathPrefix="jar:file:/";
	
	public void contextInitialized(ServletContextEvent event) {
		//servlet上下文
		AppEnv.setServletContext(event.getServletContext());
		logger.error("Initialize the system parameters ...");
		
		//初始化spring上下文
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		SpringContextUtil.setContext(ctx);
		
		//加载license信息
		loadLicense();
		
		//初始化系统配置文件
		initSysProps();
		
		//权限菜单缓存
		MenuUtil.loadRoleMenus();
		
		//菜单操作缓存
		MenuUtil.loadMenuOpt();
		
		//清除通信过程中的临时缓存文件目录
		cleanTmpFilePath();
		
		//加载邮件服务配置
		//loadMailConfig();
		
		//加载数据映射配置文件
		loadDataMappingConfig();
		
		//加载P_CODE基础数据
		loadPCode();
		
		logger.error("Initialize is complete!");
	}
	
	/**
	 * 初始化系统配置信息，存放classes/sys.properties文件中
	 */
	public void initSysProps(){
		InputStream fis = null;
		try {
			//1---加载系统总配置文件
			fis = StartupListener.class.getResourceAsStream("/res/app.properties");
			String configFileFullPath = StartupListener.class.getResource("").toString();
			
			if (configFileFullPath.indexOf("WEB-INF")==-1) {
				configFileFullPath = StartupListener.class.getResource(".").getPath();
			}
        	
        	//考虑打包成ear发布时的情况
        	if (configFileFullPath.indexOf("WEB-INF")==-1) {
        		configFileFullPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			}
        	//web根路径
        	String webRootPath = configFileFullPath.substring(0,configFileFullPath.indexOf("WEB-INF"));
        	
        	//去掉前缀
        	if (webRootPath.startsWith(pathPrefix)) {
        		webRootPath = webRootPath.replace(pathPrefix, "");
			}
        	 
        	 //设置WEB根路径
            AppEnv.setWebRootPath(webRootPath);
            
			Properties props = new Properties();
			props.load(fis);
			
			Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
			if (sysMap==null) {
				sysMap = new HashMap<String,String>();
			}
			for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String svalue = (String) props.get(key);
				sysMap.put(key,svalue);
			}
			
			AppEnv.setObject(Constants.SYS_PARAMMAP,sysMap);
			
			props.clear();
			sysMap=null;
		} catch (FileNotFoundException e) {
			logger.error("读取配置文件/res/app.properties，没有找到文件",e);
		} catch (IOException e) {
			logger.error("读取配置文件/res/app.properties，IO异常",e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(CommonUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}
	
	/**
	 * 清除通信过程中的临时缓存文件目录
	 */
	public void cleanTmpFilePath(){
		//获取应用根路径
		String webRootPath = AppEnv.getWebRootPath();
		Constants.TMPEFILE =  webRootPath+"temp" + File.separator;//this.getPSys("1") + File.separator; //缓存文件路径
        Constants.PAGESIZE = 30;
		if(!StringUtil.isEmptyString(Constants.TMPEFILE+TempUtil.FILEDIR) && Constants.TMPEFILE.length() > 5) {
			File file = new File(Constants.TMPEFILE+TempUtil.FILEDIR);
			if (file.isDirectory()) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					e.getStackTrace();
				}
			}
		}
	}
	
	/**
	 *加载license信息
	 */
	public void loadLicense(){
		//1解析license信息
		ActionResult rs = CommonUtil.resolveLicense();
		//解析失败(未加载到license或者license非法),退出
		if (!rs.isSuccess()) {
			logger.error("The License is illegal,please re-apply for a new license!");
			System.exit(-1);
		}
		
		Map<String,String> sysMap =  new HashMap<String,String>();
		//加载系统启动时间
		Date startDate = new Date();
		String sysStartDate = DateUtil.DATE_FORMAT_SHORT.format(startDate);
		sysMap.put(Constants.LICENCE_SYS_STARTDATE, sysStartDate);
		
		//获取到license信息
		License license = (License)rs.getDataObject();
		
		long meterNum = -1;
		//2如果电表数限制 -1表示不限制电表数
		if (license.isMeterLimitFlag()) {
			meterNum = (license.getMeterNum()==null||"".equals(license.getMeterNum()))?0:Long.parseLong(license.getMeterNum());
		}
		//获取电表使用限制
		sysMap.put(Constants.LICENCE_METER_NUMBER, String.valueOf(meterNum));
		
		//3如果超期限制
		if(license.isExpLimitFlag()){
			//1)获取授权文件中的过期时间
			String expDateNew = license.getExpDate();
			expDateNew = expDateNew==null?"":expDateNew;
			//系统当前时间和过期时间作比，判断是否过期
			long leftMins = 0;
			try {
				leftMins = DateUtil.getMinuteDiff(new Date(),DateUtil.DATE_FORMAT_SHORT.parse(expDateNew));
			} catch (ParseException e) {
			}
			//过期退出
			if (leftMins<=0) {
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}
			
			//初始化授权使用天数
			String canUseDays = license.getValidDay();
			//如果生成一个过期的license，则直接不能启动
			if (StringUtil.isEmptyString(canUseDays)) {
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}
			
			/*-----------------------超期判断----------------------------*/
			CommonManager commonManager = (CommonManager)SpringContextUtil.getBean("commonManager");
			
			
			//2)判断使用到期时间是否有变化，如果发生变更表示有新的授权文件，系统重新计时
			Map<String,Object> dayMap =commonManager.getUsedDaysLicense();
			String expDateOld = "";
			if (dayMap!=null) {
				expDateOld = String.valueOf(dayMap.get("EXPDATE"));
				expDateOld = expDateOld==null?"":expDateOld;
			}
			
			//获取已使用天数
			int usedDays = 0;
			if (!expDateNew.equals(expDateOld)) {
				commonManager.resetDayLimitLicense(expDateNew);
			}else{
				usedDays = Integer.parseInt(String.valueOf(dayMap.get("DAYS")));
			}
		
			//3)使用时间超期判断
			//超期
			if (usedDays > Integer.parseInt(canUseDays)) {
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}else{
				sysMap.put(Constants.LICENSE_DAYS, String.valueOf(canUseDays));
				sysMap.put(Constants.LICENCE_EXPDATE, expDateNew);
			}
			
			//4)更新使用天数
			commonManager.initDayLimitLicense(expDateNew,"0");
		}else{
			//判断超期
			sysMap.put(Constants.LICENSE_DAYS, "-1");
		}
		
		//4如果机器信息限制
		if(license.isMachInfoLimitFlag()){
			boolean validateFlag = CommonUtil.validateMachInfo(license);
			//通不过验证，则退出
			if (!validateFlag) {
				logger.error("The machine information not authorized,please re-apply for a new license!");
				System.exit(-1);
			}
		}
		
		AppEnv.setObject(Constants.SYS_PARAMMAP, sysMap);
	}
	
	/**
	 * 加载邮件服务器配置信息
	 */
	public void loadMailConfig(){
		InputStream fis = null;
		Map<String, String> maiMap = new HashMap<String,String>();
		try {
			fis = StartupListener.class.getResourceAsStream("/res/mail.properties");
			Properties props = new Properties();
			props.load(fis);
			for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String svalue = (String) props.get(key);
				maiMap.put(key,svalue);
			}
			props.clear();
		} catch (IOException e) {
			logger.error("读取配置文件/res/mail.properties，IO异常",e);
		}finally{
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(CommonUtil.getExceptionDetailInfo(e));
				}
			}
		}
		AppEnv.setObject(Constants.MAILPARA,maiMap);
	}
	
	/**
	 * 加载数据映射配置信息
	 */
	public void loadDataMappingConfig(){
		InputStream fis = null;
		Map<String, String> maiMap = new HashMap<String,String>();
		try {
			fis = StartupListener.class.getResourceAsStream("/res/dataMapping.properties");
			Properties props = new Properties();
			props.load(fis);
			for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String svalue = (String) props.get(key);
				maiMap.put(key,svalue);
			}
			props.clear();
		} catch (IOException e) {
			logger.error("读取配置文件/res/dataMapping.properties，IO异常",e);
		}finally{
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(CommonUtil.getExceptionDetailInfo(e));
				}
			}
		}
		AppEnv.setObject(Constants.SYS_DATAMAP,maiMap);
	}
	
	/**
	 * 加载P_CODE基础数据
	 */
	public void loadPCode(){
		Map<String,String> map = new HashMap<String,String>();
		try {
			CommonManager commonManager = (CommonManager)SpringContextUtil.getBean("commonManager");
			List<Object> pcList = commonManager.getAllPCode();
			for (int i = 0; i < pcList.size(); i++) {
				Map<String,String> pcMap = (Map<String, String>) pcList.get(i);
				String key = pcMap.get("BM");
				String value = pcMap.get("MC");
				map.put(key,value);
			}
		} catch (Exception e) {
			logger.error("Load PCode data error", e);
		} finally{
			
		}
		AppEnv.setObject(Constants.SYS_PCODEMAP, map);
	}
}