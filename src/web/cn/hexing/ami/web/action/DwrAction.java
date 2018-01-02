package cn.hexing.ami.web.action;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;

public class DwrAction implements ApplicationContextAware {

	static Logger log = Logger.getLogger(DwrAction.class);

	ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 普通dwr调用
	 * 
	 * @param beanID
	 * @param methodName
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ActionResult doAjax(String beanID, String methodName,
			Map<String, String> param) throws Exception {
		checkSession();
		logger(beanID, methodName, param);
		try {
			ActionResult re = new ActionResult();
			WebContext wctx = WebContextFactory.get();
			Map<String, Object> newParam = setSessionParam(wctx, param);
			Util util = new Util(wctx.getScriptSession());
			Object target = applicationContext.getBean(beanID);
			Class<?> targetClass = target.getClass();
			Method targetMethod = targetClass.getDeclaredMethod(methodName,
					Map.class, Util.class);
			re =  (ActionResult) targetMethod.invoke(target, newParam, util);
			return re;
		} catch (Exception ex) {
			log.error(StringUtil.getExceptionDetailInfo(ex));
			throw new Exception(ex.getCause());
		}
	}

	/**
	 * 数据库操作
	 * 
	 * @param beanID
	 * @param param
	 * @param doType
	 * @return
	 * @throws Exception
	 */
	public ActionResult doDbWorks(String beanID, String czid,
			Map<String, String> param) throws Exception {
		checkSession();
		logger(beanID, "doDbWorks", param);
		//修改密码无需写操作过滤
		if(!czid.equals("5210004")){ 
			checkCzqx(czid);
		}
		ActionResult re = new ActionResult();
		WebContext wctx = WebContextFactory.get();
		Map<String, Object> newParam = setSessionParam(wctx, param);
		try {
			Object target = applicationContext.getBean(beanID);
			Class<?> targetClass = target.getClass();
			// 执行操作
			Method operate = targetClass.getDeclaredMethod("doDbWorks",
					String.class, Map.class);
			re = (ActionResult) operate.invoke(target, czid, newParam);
			// 写日志
			Method logger = targetClass.getDeclaredMethod("dbLogger",
					String.class, String.class, String.class, String.class);
			if(re.isSuccess()){ //操作有效的记录日志
				Object loggerContent = newParam.get("logger");
				//判断logger的字符长度
				String loggerContentStr = String.valueOf(loggerContent);
				//日志内容数据表字段为4000
				if (loggerContentStr!=null && loggerContentStr.length()>3500) {
					loggerContentStr = loggerContentStr.substring(0,3500);
				}
				logger.invoke(target, czid, loggerContentStr, newParam
						.get(Constants.CURR_STAFFID), newParam
						.get(Constants.UNIT_CODE));
			}
		} catch (Exception ex) {
			log.error(StringUtil.getExceptionDetailInfo(ex));
			throw new Exception(ex.getCause());
		}
		return re;
	}

	/**
	 * 创建通信任务
	 * @param beanID
	 * @param czid
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ActionResult createTask(String beanID, String czid,
			Map<String, String> param) throws Exception {
		checkSession();
		logger(beanID, "createTask", param);
		//网络售电不判断权限
		if (!czid.equals("6210001")) {
			checkCzqx(czid);
		}
		
		ActionResult re = new ActionResult();
		WebContext wctx = WebContextFactory.get();
		Map<String, Object> newParam = setSessionParam(wctx, param);
		try {
			Object target = applicationContext.getBean(beanID);
			Class<?> targetClass = target.getClass();
			// 执行操作
			Method operate = targetClass.getDeclaredMethod("createTask",
					String.class, Map.class);
			re = (ActionResult) operate.invoke(target, czid, newParam);
			// 写日志
			Method logger = targetClass.getDeclaredMethod("taskLogger",
					String.class, Map.class, String.class, String.class);
			logger.invoke(target, czid, newParam, newParam
					.get(Constants.CURR_STAFFID), newParam.get(Constants.CURR_IP));
		} catch (Exception ex) {
			log.error(StringUtil.getExceptionDetailInfo(ex));
			throw new Exception(ex.getCause());
		}
		return re;
	}
	public String checkSession() throws Exception {
		WebContext wctx = WebContextFactory.get();
		HttpSession session = wctx.getHttpServletRequest().getSession();
		AqCzy op = (AqCzy) session.getAttribute(Constants.CURR_STAFF);
		if (null == op) {
			throw new Exception("nosession");
		}
		return null;
	}
	
	/**
	 * 将session中的转化到map对象中
	 * @param wctx
	 * @param param
	 * @return
	 */
	private  Map<String, Object> setSessionParam(WebContext wctx, Map<String, String> param) {
		Map<String, Object> newParam = new HashMap<String,Object>();
		newParam.putAll(param);
		
		Enumeration em = wctx.getSession().getAttributeNames();
		while(em.hasMoreElements()){
			String attributeName = String.valueOf(em.nextElement());
			//if(Constants.CURR_STAFF.equals(attributeName)) continue;
			newParam.put(attributeName, wctx.getSession().getAttribute(attributeName));
		}
		// 把文件名放到param里 控制页面用
		String fileName = String.valueOf(wctx.getSession().getAttribute("fileName" + newParam.get("name")));
		if(!StringUtil.isEmptyString(fileName)){
			newParam.put("fileName", fileName);
		}
		return newParam;
	}

	/**
	 * 打印日志
	 * @param beanId
	 * @param method
	 * @param param
	 */
	private void logger(String beanId, String method, Map<String, String> param) {
		if (log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer();
			msg.append("	<actionName>").append(beanId).append("</actionName>\n");
			msg.append("	<method>").append(method).append("</method>\n");
			Iterator<String> it = param.keySet().iterator();
			while(it.hasNext()) {
				String pname = it.next();
				msg.append("	<param name=\"").append(pname).append("\">").append(param.get(pname)).append("</param>\n");
			}
			log.debug(msg);
		}
	}
	/**
	 * 功能权限过滤
	 * 
	 * @param czid
	 * @return
	 * @throws Exception 
	 */
	public String checkCzqx(String czid) throws Exception {
		WebContext wctx = WebContextFactory.get();
		HttpSession session = wctx.getHttpServletRequest().getSession();
		String czyid = (String)session.getAttribute(Constants.CURR_STAFFID);
		if(Constants.SUPER_ADMIN.equals(czyid)) return null;
		BaseDAOIbatis dao= (BaseDAOIbatis)applicationContext.getBean("baseDAOIbatis");
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("czyid", czyid);
		obj.put("czid", czid);
		List<Object> ls = dao.queryForList("common.getCzyCzqx", obj);
		if(ls!=null && ls.size() >0) {
			
		} else {
			throw new Exception("noright");
		}
		return null;
	}

}
