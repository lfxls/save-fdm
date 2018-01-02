package cn.hexing.ami.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.pojo.license.License;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description license超使用期限任务
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-11-4
 * @version AMI3.0 
 */
public class LicenseJob {
	private static Logger logger = Logger.getLogger(LicenseJob.class.getName());
	private CommonManager commonManager;
	
	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	/**
	 * 任务主体
	 */
	public void monitorTimeLimit(){
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		
		//获取授权使用天数
		int canUseDays = (sysMap.get(Constants.LICENSE_DAYS)==null || "".equals(sysMap.get(Constants.LICENSE_DAYS)))?0:Integer.parseInt(sysMap.get(Constants.LICENSE_DAYS));
		
		//如果永不过期，则不做任何校验
		if (canUseDays==-1) {
			return;
		}
		
		//如果系统启动时间比当前时间晚 说明系统时间被往前修改
		String startDate = sysMap.get(Constants.LICENCE_SYS_STARTDATE);
		long tmpMins = 0;
		try {
			tmpMins = DateUtil.getMinuteDiff(new Date(),DateUtil.DATE_FORMAT_SHORT.parse(startDate));
			if (tmpMins>0) {
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}
		} catch (ParseException e) {
		}
		
		
		//增加过期时间判断，如果过期直接退出
		long leftMins = 0;
		String expDate = sysMap.get(Constants.LICENCE_EXPDATE);
		try {
			leftMins = DateUtil.getMinuteDiff(new Date(),DateUtil.DATE_FORMAT_SHORT.parse(expDate));
		} catch (ParseException e) {
		}
		//过期退出
		if (leftMins<=0) {
			logger.error("Sorry,the system license expires, apply for a new license!");
			System.exit(-1);
		}
		
		int days = 0;
		
		Map<String,Object> dayMap = commonManager.getUsedDaysLicense();
		int usedDays = 0;
		//最后更新时间
		String lastUpdDate = "";
		if (dayMap!=null) {
			
			usedDays = Integer.parseInt(String.valueOf(dayMap.get("DAYS")));
			lastUpdDate = String.valueOf(dayMap.get("UPDDATE"));
			try {
				days = DateUtil.getDayDiff(DateUtil.DATE_FORMAT_SHORT.parse(lastUpdDate), new Date());
			} catch (ParseException e) {
			}
			//如果出现负数，表示系统在启动后修改过往前时间
			if (days<0) {
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}
		}else{
			
			//如果license记录被人为删除，定时新增记录，把系统启动天数作为已用天数
			try {
				days = DateUtil.getDayDiff(DateUtil.DATE_FORMAT_SHORT.parse(startDate), new Date());
				commonManager.initDayLimitLicense(expDate,String.valueOf(days));
			} catch (ParseException e) {
			}
		}
		
		//系统刷新时间，间隔为天
		if (days>0) {
			//超出授权使用期限
			if (usedDays+days>canUseDays) {
				commonManager.updateDayLimitLicense(usedDays+days);
				logger.error("Sorry,the system license expires, apply for a new license!");
				System.exit(-1);
			}else{
				commonManager.updateDayLimitLicense(usedDays+days);
			}
		}
	}
	
	/**
	 * 监控加密狗
	 */
	public void monitorSoftDog(){
		//解析license信息
		ActionResult rs = CommonUtil.resolveLicense();
		//解析失败(未加载到license或者license非法),退出
		if (!rs.isSuccess()) {
			logger.error("The License is illegal,please re-apply for a new license!");
			System.exit(-1);
		}
		
		//获取到license信息
		License license = (License)rs.getDataObject();
		
		//如果机器信息限制
		if(license.isMachInfoLimitFlag()){
			boolean validateFlag = CommonUtil.validateMachInfo(license);
			//通不过验证，则退出
			if (!validateFlag) {
				logger.error("The machine information not authorized,please re-apply for a new license!");
				System.exit(-1);
			}
		}
	}
}