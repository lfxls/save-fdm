package cn.hexing.ami.service.sms;

import java.util.List;

import cn.hexing.ami.dao.common.pojo.sms.InMsg;
import cn.hexing.ami.dao.common.pojo.sms.OutMsg;
import cn.hexing.ami.util.ActionResult;

/**
 * @Description 短信操作接口
 * @author zyy
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time 2013-6-17
 * @version AMI3.0
 */
public interface SmsServerServiceInf {
	
	/**
	 * 发送短信（保存短信内容到数据表smsserver_out）
	 * @param outMsg
	 * @param lang
	 * @return
	 */
	public ActionResult sendMessage(OutMsg outMsg, String lang);
	
	/**
	 * 读取所有未处理短信
	 * @return
	 */
	public List<InMsg> readMessages();
	
	/**
	 * 根据短信ID将短信设置为处理状态
	 * @param msgId
	 */
	public void processMessage(String msgId);
	
	/**
	 * 根据短信ID获取已发送短信
	 * @param msgId
	 * @return
	 */
	public OutMsg getOutMsg(String msgId);
	
	/**
	 * 根据短信ID获取接收短信
	 * @param msgId
	 * @return
	 */
	public InMsg getInMsg(String msgId);
	
	/**
	 * 获取返回短信
	 * @param simNo
	 * @param sendDate
	 * @return
	 */
	public InMsg getRetMsg(String simNo, String sendDate);
	
	/**
	 * 同步发送一个告警消息
	 * @param zdjh  终端局号
	 * @param content  告警内容
	 * @param lang 语言
	 */
	public String sendAlarmMessageToOper(String zdjh, String content, String lang);
}
