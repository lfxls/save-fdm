package cn.hexing.ami.web.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.util.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @Description 显示每次请求传递的参数
 * @author  jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-4-8
 * @version AMI3.0
 */
public class LoggerInterceptor extends AbstractInterceptor {

	static Logger log = Logger.getLogger(LoggerInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600307978246800434L;

	
	public String intercept(ActionInvocation ai) throws Exception {
		ActionContext ac = ai.getInvocationContext();
		ActionProxy px = ai.getProxy();
		if (log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer();
			msg.append("\n	<nameSpace>").append(px.getNamespace()).append("</nameSpace>\n");
			msg.append("	<actionName>").append(px.getActionName()).append("</actionName>\n");
			msg.append("	<method>").append(px.getMethod()).append("</method>\n");
			Map<String, Object> mp = ac.getParameters();
			Iterator<String> it = mp.keySet().iterator();
			while(it.hasNext()) {
				String pname = it.next();
				//处理对象为null或者为空的情况
				Object[] paramMap = mp.get(pname)==null?new Object[]{""}:(Object[])mp.get(pname);
				paramMap = paramMap.length==0?new String[]{""}:paramMap;
				if(paramMap instanceof String[]){
					msg.append("	<param name=\"").append(pname).append("\">").append(paramMap[0]).append("</param>\n");
				}else{
					msg.append("	<param name=\"").append(pname).append("\">").append((pname)).append("</param>\n");
				}
			}
			AqCzy op = (AqCzy) ac.getSession().get(Constants.CURR_STAFF);
			msg.append("	<op czyId=\""+op.getCzyid()+"\"/>");
			log.debug(msg);
		}
		return ai.invoke();
	}

}
