package cn.hexing.ami.web.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.util.Constants;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @Description 会话拦截器
 *          支持根据web.xml中ignoreURL的配置列表，形成过滤功能
 * @author  jun
 * @Copyright 2014 hexing Inc. All rights reserved
 * @time：2014-2-20
 * @version AMI3.0
 */
public class SessionIntercept extends AbstractInterceptor {
	private static final long serialVersionUID = -5671718768878211489L;
	 
	public String intercept(ActionInvocation ai) throws Exception {
		Map<String, Object> session = ai.getInvocationContext().getSession();
		AqCzy op = (AqCzy) session.get(Constants.CURR_STAFF);
		//从web.xml中获取ignoreURL
		String  ignoreURL = ServletActionContext.getServletContext().getInitParameter("ignoreURL");
		List<String> alIgnoreURL = new ArrayList<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(ignoreURL, ",");
		while (tokenizer.hasMoreElements()) {
			String s = (String) tokenizer.nextElement();
			alIgnoreURL.add(s);
		}
		
		HttpServletRequest request = (HttpServletRequest)ai.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);  
		String url = request.getRequestURI().toString();
		boolean isIgnoreUrl = false;
		//忽略登录控制的url
		for (int i = 0; i < alIgnoreURL.size(); i++) {
			// 如果在权限忽略控制的列表中,则返回真
			if (url.indexOf((String) alIgnoreURL.get(i)) != -1) {
				isIgnoreUrl = true;
				break;
			}
		}
		
		//不在忽略列表中的url需要检查session
		if (!isIgnoreUrl && (session == null || null == op)) {
			return "nosession";
		}
		return ai.invoke();
	}

}
