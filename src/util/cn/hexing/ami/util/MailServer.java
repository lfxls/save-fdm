package cn.hexing.ami.util;

import java.util.Date;
import java.util.HashMap;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import cn.hexing.ami.web.listener.AppEnv;

/**
 * @Description 简易邮件服务器 
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-1-25
 * @version AMI3.0
 */
public class MailServer {
	private static Logger logger = Logger.getLogger(MailServer.class.getName());
	/**
	 * 发送文本邮件
	 * @return
	 */
	public static boolean sendSimpleMail(String sendTo,String receiver,String subject,String msg){
		HashMap<String,String> mailMap = (HashMap<String,String>)AppEnv.getObject(Constants.MAILPARA);
		String hostName = (String)mailMap.get("hostName");
		String sendFrom = (String)mailMap.get("sendFrom");
		String sender = (String)mailMap.get("sender");
		String authenUser = (String)mailMap.get("authenUser");
		String authenPwd = (String)mailMap.get("authenPwd");
		String smtpPort = (String)mailMap.get("smtpPort");
		try{
			SimpleEmail email = new SimpleEmail();
			email.setCharset("UTF-8");
			email.setHostName(hostName);
			email.setAuthentication(authenUser,authenPwd);
			email.addTo(sendTo, receiver);
			email.setFrom(sendFrom,sender);
			email.setSmtpPort(StringUtil.isEmptyString(smtpPort)? 25:Integer.parseInt(smtpPort.trim()));
			email.setSubject(subject);
			email.setContent(msg, "text/plain;charset=UTF-8");
			email.setSentDate(new Date());
			email.send();
		}catch(Exception ex){
			logger.error("send simple mail[sendTo:"+sendTo+",receiver:"+receiver+",subject:"+subject+",text:"+msg+"] error:"+StringUtil.getExceptionDetailInfo(ex));
			return false;
		}
		return true;
	}
	/**
	 * 发送html邮件
	 * @return
	 */
	public static boolean sendSimpleHtmlMail(String sendTo,String receiver,String subject,String msg){
		HashMap<String,String> mailMap = (HashMap<String,String>)AppEnv.getObject(Constants.MAILPARA);
		String hostName = (String)mailMap.get("hostName");
		String sendFrom = (String)mailMap.get("sendFrom");
		String sender = (String)mailMap.get("sender");
		String authenUser = (String)mailMap.get("authenUser");
		String authenPwd = (String)mailMap.get("authenPwd");
		String smtpPort = (String)mailMap.get("smtpPort");
		try{
			SimpleEmail email = new SimpleEmail();
			email.setCharset("UTF-8");
			email.setHostName(hostName);
			email.setAuthentication(authenUser,authenPwd);
			email.addTo(sendTo, receiver);
			email.setFrom(sendFrom,sender);
			email.setSmtpPort(StringUtil.isEmptyString(smtpPort)? 25:Integer.parseInt(smtpPort.trim()));
			email.setSubject(subject);
			email.setContent(msg, "text/html;charset=UTF-8");
			email.setSentDate(new Date());
			email.send();
		}catch(Exception ex){
			logger.error("send simple mail[sendTo:"+sendTo+",receiver:"+receiver+",subject:"+subject+",text:"+msg+"] error:"+StringUtil.getExceptionDetailInfo(ex));
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 发送附件
	 * @return
	 */
	public static boolean sendAttachmentMail(String sendTo,String receiver,String subject,String msg,String filePath){
		HashMap<String,String> mailMap = (HashMap<String,String>)AppEnv.getObject(Constants.MAILPARA);
		String hostName = (String)mailMap.get("hostName");
		String sendFrom = (String)mailMap.get("sendFrom");
		String sender = (String)mailMap.get("sender");
		String authenUser = (String)mailMap.get("authenUser");
		String authenPwd = (String)mailMap.get("authenPwd");
		String smtpPort = (String)mailMap.get("smtpPort");
		try{
			//创建附件
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(MimeUtility.decodeText(filePath));
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			String fileName = filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
			attachment.setName(MimeUtility.encodeText(fileName)); 

			MultiPartEmail  email = new MultiPartEmail();
			email.setAuthentication(authenUser,authenPwd);
			email.setCharset("UTF-8");
			email.setHostName(hostName);
			email.addTo(sendTo, receiver);
			email.setFrom(sendFrom,sender);
			email.setSmtpPort(StringUtil.isEmptyString(smtpPort)? 25:Integer.parseInt(smtpPort.trim()));
			email.setSubject(subject);
			email.setMsg(msg);
	
			//增加附件
			email.attach(attachment);
			email.setSentDate(new Date());
			email.send();
		}catch(Exception ex){
			logger.error("send attachment mail[sendTo:"+sendTo+",receiver:"+receiver+",subject:"+subject+",text:"+msg+"] error:"+StringUtil.getExceptionDetailInfo(ex));
			return false;
		}
		return true;
	}
}
