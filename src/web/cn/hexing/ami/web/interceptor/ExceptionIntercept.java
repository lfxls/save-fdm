package cn.hexing.ami.web.interceptor;

import org.apache.log4j.Logger;

import cn.hexing.ami.util.StringUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionIntercept extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8239537834328179683L;
	
	static Logger log = Logger.getLogger(ExceptionIntercept.class);

	public String intercept(ActionInvocation invocation) throws Exception {
		String result = "";
		try {
			result = invocation.invoke();
		} catch (Exception ex) {
			log.error(StringUtil.getExceptionDetailInfo(ex));
			throw ex;
		}
		return result;
	}

}
