package cn.hexing.ami.web.action;         

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.service.LoginManager;
import cn.hexing.ami.util.CommonUtil;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.DateUtil;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.aes.RijndaelUtil;
import cn.hexing.ami.web.listener.AppEnv;

import com.opensymphony.xwork2.ActionContext;

/**
 * @Description 系统登录
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-8-12
 * @version AMI3.0
 */
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 7771402514618404832L;

	private LoginManager loginManager = null;

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}
	
	private List<Object> langList = null;
	private String czyId, pwd, lang;
	private String prepaySaleMode;
	private String logoMode;
	private String sysTitle;//系统名称：MDC/MDM/PREPAY/AMI
	
	public String getSysTitle() {
		return sysTitle;
	}

	public void setSysTitle(String sysTitle) {
		this.sysTitle = sysTitle;
	}

	public String getLogoMode() {
		return logoMode;
	}

	public void setLogoMode(String logoMode) {
		this.logoMode = logoMode;
	}

	public String getPrepaySaleMode() {
		return prepaySaleMode;
	}

	public void setPrepaySaleMode(String prepaySaleMode) {
		this.prepaySaleMode = prepaySaleMode;
	}

	public String init() {
		setLangByIE();
		
		session.setAttribute(Constants.APP_LOCALE, lang);
		session.setAttribute(Constants.APP_LANG, lang);
		
		//如果为预付费模式，则需要显示预付费的logo
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		prepaySaleMode = StringUtil.isEmptyString(sysMap.get("prepayMode"))? "false":sysMap.get("prepayMode");
		logoMode = StringUtil.isEmptyString(sysMap.get("logoMode"))? "1":sysMap.get("logoMode");
		
		//系统模式
		//String sysId = CommonUtil.getMultiSystemID();
		sysTitle = this.getText("login.04.title");
		session.setAttribute("sysTitle", sysTitle);
		return "init";
	}
	
	public String login() throws Exception {
		String loginMsg = "ok";
		if (StringUtil.isEmptyString(czyId) || StringUtil.isEmptyString(pwd)) {
			loginMsg = "null";
		}
		AqCzy op = loginManager.getCzyById(czyId);
		if (op == null) {
			loginMsg = "notfound";
		} else {
			//判断用户是否被锁定
			/*Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
			String wrongTimes = sysMap.get("wrongPwdTimes");
			if(wrongTimes == null || wrongTimes.trim().equals(""))
				wrongTimes = "3";*/
			//锁定状态
			if(Constants.ACCOUNTSTATUS_LOCKED.equals(op.getZt())) {
				loginMsg = "lock";
			}else {
				if (!RijndaelUtil.encodePassword(pwd).equals(op.getMm())) {
					loginMsg = "pwd";
					/****************************************************************/
					//获取系统配置参数对象
					Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
					String wrongTimes = sysMap.get("wrongPwdTimes");
					//判断配置的密码错误次数(如果为空默认为3次)
					wrongTimes  = (null == wrongTimes || "".equals(wrongTimes.trim())) ? "3" : wrongTimes.trim();
					//配置的密码错误次数如果为不为0进行限制，如果为0无限制错误次数
					if(!"0".equals(wrongTimes)){
						//密码错误次数计数加1
						int mmcucs = op.getMmcwcs() == null ? 0 : op.getMmcwcs();
						//达到最大错误次数
						if (mmcucs + 1>=Integer.parseInt(wrongTimes)) {
							op.setZt(Constants.ACCOUNTSTATUS_LOCKED);
						}
						op.setMmcwcs(mmcucs + 1);
						loginManager.updateMmcwcs(op);
					}
					/****************************************************************/
				}else {
					//密码错误次数清空
					if(op.getMmcwcs() != null) {
						op.setMmcwcs(null);
						loginManager.updateMmcwcs(op);
					}
				}
				
				//角色信息
				String jsStr = "";
				if (Constants.ACCOUNTSTATUS_LOGOFF.equals(op.getZt())) {
					loginMsg = "out";
				}else if (Constants.ACCOUNTSTATUS_LOCKED.equals(op.getZt())){
					loginMsg = "lock";
				}else{
					//判断用户是否有角色授权，考虑系统管理员的情况
					if(czyId!=null && !czyId.endsWith(Constants.SUPER_ADMIN)){
						jsStr = loginManager.getCzyJs(czyId);
						//如果没有角色授权，不能登录系统
						if (StringUtil.isEmptyString(jsStr)) {
							boolean hasCzyCd = loginManager.hasCzyCd(czyId);
							//如果有菜单授权情况，给出一个空角色字符串
							if (hasCzyCd) {
								jsStr = Constants.NULL_ROLES;
							}else{
								loginMsg = "norole";
							}
						}
					}
					
					//判断IP绑定有无限制
					String userIp = CommonUtil.getCurrentUserIP(request);
					if ((op.getBdip()!=null && !op.getBdip().equals("")) && !op.getBdip().equals(userIp)) {
						loginMsg = "login_bindIp";
					}else{
						//判断登录时间有无限制
						if((op.getSysjq()!=null && !"".equals(op.getSysjq())) || (op.getSysjz()!=null && !"".equals(op.getSysjz()))){
							String sysjq = op.getSysjq()==null?"0":op.getSysjq();
							sysjq = sysjq.replace(":", "");
							String sysjz = op.getSysjz()==null?"0":op.getSysjz();
							sysjz = sysjz.replace(":", "");
							String nowStr = DateUtil.getDateTime("HHmm", new Date());
							//在登录区间外
							if (Integer.parseInt(sysjq)>0 && Integer.parseInt(nowStr)<Integer.parseInt(sysjq)) {
								loginMsg = "login_bindDate";
							}
							
							if (Integer.parseInt(sysjz)>0 && Integer.parseInt(nowStr)>Integer.parseInt(sysjz)) {
								loginMsg = "login_bindDate";
							}
						}
					}
				}
				
				if ("ok".equals(loginMsg)) {
					session.setAttribute(Constants.USER_ROLES, jsStr); 
					session.setAttribute(Constants.CURR_STAFF, op);
					
					session.setAttribute(Constants.APP_LOCALE, lang); 
					session.setAttribute(Constants.APP_LANG, lang);
					//设置全局locale
					String[] localeArray = lang.split("_");
					Locale currentLocale = new Locale(localeArray[0],localeArray[1]);   
					ActionContext.getContext().setLocale(currentLocale);
					session.setAttribute("WW_TRANS_I18N_LOCALE", currentLocale);    
				}
			}
		}
		
		//可使用天数
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);
		int canUseDays = (sysMap.get(Constants.LICENSE_DAYS)==null || "".equals(sysMap.get(Constants.LICENSE_DAYS)))?0:Integer.parseInt(sysMap.get(Constants.LICENSE_DAYS));
		
		session.setAttribute(Constants.LICENSE_DAYS, canUseDays);
		responseText(loginMsg);
		return null;
	}

	public String loginSuccess() {
		AqCzy op = (AqCzy) session.getAttribute(Constants.CURR_STAFF);
		if(null == op) {
			return "init";
		}
		String sessionId = op.getCzyid() + System.currentTimeMillis();
		session.setAttribute(Constants.UNIT_CODE, op.getDwdm());
		session.setAttribute(Constants.UNIT_NAME, op.getDwmc());
		session.setAttribute(Constants.UNIT_LEVEL, op.getJb());
		session.setAttribute(Constants.CURR_STAFFID, op.getCzyid());
		session.setAttribute(Constants.CURR_SESSIONID, sessionId);
		session.setAttribute(Constants.CURR_DEPT, op.getBmid()); 
		session.setAttribute(Constants.CURR_IP, request.getRemoteAddr()); 
		
		// 权限判断 操作员单位代码是否存在于操作员访问区域这张表，存在标记为1，反之为0
		String fwbj = loginManager.getFwbj(op.getCzyid(),op.getDwdm());
		session.setAttribute(Constants.CURR_RIGTH, fwbj);
		
//		session.setMaxInactiveInterval(30 * 60); //会话有效期30分钟
		//登录日志
		loginManager.insLogin(op, CommonUtil.getCurrentUserIP(request), sessionId);	
		Cookie cookieUserName = new Cookie("userName", op.getCzyid()); // 登录的用户名加入cookie
		cookieUserName.setMaxAge(60 * 60 * 24 * 365 * 10);
		response.addCookie(cookieUserName);
		return "view";
	}
	
	/**
	 * 登录注销
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String logOut() {
		String sessionid = (String) session.getAttribute(Constants.CURR_SESSIONID);
		
		//zhouh Edit
//		lang = (String) session.getAttribute(Constants.APP_LANG);
		
		loginManager.updateLogger(sessionid);
		Enumeration s = session.getAttributeNames();
		while (s.hasMoreElements()) {
			session.removeAttribute((String) s.nextElement());
		}
		return init();
	}
	
	/**
	 * 没有权限
	 * @return
	 */
	public String initNoRight(){
		return "initNoRight";
	}
	
	/**
	 * 会话超期
	 * @return
	 */
	public String initNoSession(){
		setLangByIE();
		session.setAttribute(Constants.APP_LANG, lang);
		return "initNoSession";
	}
	
	/**
	 * 初始化登陆语言
	 */
	public void setLangByIE(){
		if (StringUtil.isEmptyString(lang)) {
			lang = request.getLocale().toString();
		}
		
		//如果是英语系国家，统一为en_US
		if (lang.toUpperCase().indexOf("EN")!=-1) {
			lang = Constants.LOCALE_EN;
		}
		//如果为中文国家，统一为zh_CN
		if (lang.toUpperCase().indexOf("ZH")!=-1) {
			lang = Constants.LOCALE_ZH;
		}
		//如果为阿塞拜疆国家，统一为az_AZ
		if (lang.toUpperCase().indexOf("AZ")!=-1) {
			lang = Constants.LOCALE_AZ;
		}
		
		langList = CommonUtil.getCodeNoLoale("dlyy", false);
		
		//如果当前系统的语言不在支持范围之内，默认显示为英文
		boolean supportFlag = false;
		for (int i = 0; i < langList.size(); i++) {
			Map<String,Object> tmpMap = (Map<String,Object>)langList.get(i);
			String langTmp = String.valueOf(tmpMap.get("BM"));
			if (langTmp.indexOf(lang)!=-1) {
				supportFlag = true;
				break;
			}
		}
		
		//如果当前语言不在支持范围之内
		if (!supportFlag) {
			lang = Constants.LOCALE_EN;
		}
		
		//设置全局locale
		Locale currentLocale = null;
		String[] localeArray = lang.split("_");
		//处理还有一个语言，没有国别的情况，举例：白俄罗斯语:be
		if (localeArray.length==1) {
			currentLocale = new Locale(localeArray[0],localeArray[0]);   
		}else{
			currentLocale = new Locale(localeArray[0],localeArray[1]);   
		}
		ActionContext.getContext().setLocale(currentLocale);
		request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", currentLocale);    
	}
	
	/**
	 * 出错界面
	 * @return
	 */
	public String initError(){
		return "error";
	}
 
	public List<Object> getLangList() {
		return langList;
	}

	public void setLangList(List<Object> langList) {
		this.langList = langList;
	}

	public String getCzyId() {
		return czyId;
	}

	public void setCzyId(String czyId) {
		this.czyId = czyId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
