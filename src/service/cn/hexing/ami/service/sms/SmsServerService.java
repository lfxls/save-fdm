package cn.hexing.ami.service.sms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.sms.InMsg;
import cn.hexing.ami.dao.common.pojo.sms.OutMsg;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

/**
 * @Description 短信操作实现
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-17
 * @version AMI3.0
 */
public class SmsServerService implements SmsServerServiceInf{
	private static Logger logger = Logger.getLogger(SmsServerService.class.getName());
	private BaseDAOIbatis baseDAOIbatis = null;
	private static String sqlId = "SmsServer.";
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}

	/**
	 * 发送短信（保存短信内容到数据表smsserver_out）
	 */
	public ActionResult sendMessage(OutMsg outMsg, String lang) {
		if (StringUtil.isEmptyString(lang)) {
			lang = Constants.DEFAULT_LANG;
		}
		ActionResult re = new ActionResult();
		//消息类型
		if(StringUtil.isEmptyString(outMsg.getType()))
			outMsg.setType(Constants.SM_TYPE_OUTBOUND);
		//接收者
		if(StringUtil.isEmptyString(outMsg.getRecipient())){
			re.setSuccess(false);
			re.setMsg("common.smsserverservice.sendmessage.smrecipempty", lang);
			return re;
		}
		//短信内容
		if(StringUtil.isEmptyString(outMsg.getText())){
			re.setSuccess(false);
			re.setMsg("common.smsserverservice.sendmessage.smtestmpty", lang);
			return re;
		}else{
			String msgText = outMsg.getText();
			try {
				//如果包含中文字符，设置短信编码为Unicode
				if(msgText.getBytes("UTF-8").length > msgText.length())
					outMsg.setEncoding(Constants.SM_ENCODETYPE_U);
				else
					outMsg.setEncoding(Constants.SM_ENCODETYPE_7);
			} catch (UnsupportedEncodingException e) {
				re.setSuccess(false);
				re.setMsg("common.smsserverservice.sendmessage.smsendfail", lang);
				return re;
			}
		}
		//发送者
		if(StringUtil.isEmptyString(outMsg.getOriginator()))
			outMsg.setOriginator("");
		//状态报告消息
		if(StringUtil.isEmptyString(outMsg.getStatusReport()))
			outMsg.setStatusReport(Constants.STATUS_REPORT_NO);
		//Flash短信
		if(StringUtil.isEmptyString(outMsg.getFlashSms()))
			outMsg.setFlashSms("0");
		//源端口
		if(StringUtil.isEmptyString(outMsg.getSrcPort()))
			outMsg.setSrcPort("-1");
		//目标端口
		if(StringUtil.isEmptyString(outMsg.getDstPort()))
			outMsg.setDstPort("-1");
		//优先级
		if(StringUtil.isEmptyString(outMsg.getPriority()))
			outMsg.setPriority(Constants.SM_PRIORITY_NORMAL);
		//短信状态
		if(StringUtil.isEmptyString(outMsg.getStatus()))
			outMsg.setStatus(Constants.SM_STATUS_UNSENT);
		//网关ID
		if(StringUtil.isEmptyString(outMsg.getGatewayId()))
			outMsg.setGatewayId("*");
		//发送短信ID
		String msgId = (String)baseDAOIbatis.insert(sqlId + "sendMessage", outMsg);
		//短信发送失败
		if(StringUtil.isEmptyString(msgId)){
			re.setSuccess(false);
			re.setMsg("common.smsserverservice.sendmessage.smsendfail", lang);
			return re;
		}
		re.setDataObject(msgId);
		re.setSuccess(true);
		return re;
	}
	
	/**
	 * 读取所有未处理短信
	 * @return
	 */
	public List<InMsg> readMessages() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", Constants.SM_TYPE_INBOUND);
		param.put("process", Constants.SM_PROCESS_NO);
		List<Object> objList = baseDAOIbatis.queryForList(sqlId + "readMessages", param);
		List<InMsg> msgList = new ArrayList<InMsg>();
		//对象转换
		for (Object object : objList) {
			msgList.add((InMsg)object);
		}
		return msgList;
	}
	
	/**
	 * 根据短信ID将短信设置为处理状态
	 * @param msgId
	 */
	public void processMessage(String msgId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("msgId", msgId);
		param.put("process", Constants.SM_PROCESS_YES);
		baseDAOIbatis.update(sqlId + "processMessage", param);
	}
	
	/**
	 * 根据短信ID获取已发送短信
	 * @param msgId
	 * @return
	 */
	public OutMsg getOutMsg(String msgId){
		Map<String, String> param = new HashMap<String, String>();
		param.put("msgId", msgId);
		return (OutMsg)baseDAOIbatis.queryForObject(sqlId + "getOutMsg", param, OutMsg.class);
	}
	
	/**
	 * 根据短信ID获取接收短信
	 * @param msgId
	 * @return
	 */
	public InMsg getInMsg(String msgId){
		Map<String, String> param = new HashMap<String, String>();
		param.put("msgId", msgId);
		return (InMsg)baseDAOIbatis.queryForObject(sqlId + "getInMsg", param, InMsg.class);
	}
	
	/**
	 * 获取返回短信
	 * @param simNo
	 * @param sendDate
	 * @return
	 */
	public InMsg getRetMsg(String simNo, String sendDate){
		Map<String, String> param = new HashMap<String, String>();
		param.put("sendDate", sendDate);
		param.put("simNo", simNo);
		param.put("process", Constants.SM_PROCESS_NO);
		return (InMsg)baseDAOIbatis.queryForObject(sqlId + "getRetMsg", param, InMsg.class);
	}
	
	/**
	 * 同步发送一个告警消息
	 * @param zdjh  终端局号
	 * @param content  告警内容
	 * @param lang 语言
	 */
	public String sendAlarmMessageToOper(String zdjh, String content, String lang) {
		boolean flag = true;
		//获取手机号码
		List<Object> phoneNumList = baseDAOIbatis.queryForList(sqlId + "getNotifyPhoneInfo", zdjh);
		
		if(phoneNumList != null && phoneNumList.size() > 0) {
			for (Object obj : phoneNumList) {
				Map<String, String> map = (Map<String, String>) obj;
				
				OutMsg outMsg = new OutMsg();
				outMsg.setRecipient(map.get("SJHM"));
				outMsg.setText(content);
				
				ActionResult actionResult = sendMessage(outMsg, lang);
				flag &= actionResult.isSuccess();
			}
		} else { //终端没有订阅告警
			return "2";
		}
		
		if(flag) {	//全部发送成功
			return "1";
		}else { //部分手机发送失败或者全部失败
			return "0";
		}
	}
}