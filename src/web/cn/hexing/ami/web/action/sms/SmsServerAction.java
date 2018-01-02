package cn.hexing.ami.web.action.sms;

import java.util.Map;

import org.directwebremoting.proxy.dwr.Util;

import cn.hexing.ami.dao.common.pojo.sms.InMsg;
import cn.hexing.ami.dao.common.pojo.sms.OutMsg;
import cn.hexing.ami.service.sms.SmsServerServiceInf;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.web.action.BaseAction;

/**
 * @Description 短信服务
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-20
 * @version AMI3.0
 */
public class SmsServerAction extends BaseAction {
	private static final long serialVersionUID = -3420073359678598526L;
	private SmsServerServiceInf smsServerService;
	
	public SmsServerServiceInf getSmsServerService() {
		return smsServerService;
	}

	public void setSmsServerService(SmsServerServiceInf smsServerService) {
		this.smsServerService = smsServerService;
	}

	/**
	 * 根据短信ID获取发送短信
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getOutMsg(Map<String, String> param, Util util){
		ActionResult re = new ActionResult();
		//短信ID
		String msgId = param.get("msgId");
		OutMsg outMsg = smsServerService.getOutMsg(msgId);
		//发送短信不存在
		if(outMsg == null){
			re.setSuccess(false);
			return re;
		}
		re.setSuccess(true);
		re.setDataObject(outMsg);
		return re;
	}
	
	/**
	 * 获取返回短信
	 * @param param
	 * @param util
	 * @return
	 */
	public ActionResult getRetMsg(Map<String, String> param, Util util){
		ActionResult re = new ActionResult();
		//SIM卡卡号
		String simNo = param.get("simNo");
		//短信发送时间
		String sendDate = param.get("sendDate");
		InMsg inMsg = smsServerService.getRetMsg(simNo, sendDate);
		//未找到返回短信
		if(inMsg == null){
			re.setSuccess(false);
			return re;
		}
		re.setSuccess(true);
		re.setDataObject(inMsg);
		return re;
	}
}
