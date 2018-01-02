package cn.hexing.ami.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.hexing.ami.dao.common.pojo.AqCzy;
import cn.hexing.ami.util.Constants;

/**
 * @Description 控制session
 * @author jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-16
 * @version AMI3.0
 */
public class SessionFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 2110162290895589374L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	protected String loginPage = null;
	protected String ignoreURL = null;
	protected FilterConfig filterConfig = null;
	protected List<String> alIgnoreURL = new ArrayList<String>();
	private static Logger logger = Logger.getLogger(SessionFilter.class
			.getName());

	public boolean isIgnoreURL(String url) {
		for (int i = 0; i < this.alIgnoreURL.size(); i++) {
			// 如果在权限忽略控制的列表中,则返回真
			if (url.indexOf((String) alIgnoreURL.get(i)) != -1) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		this.loginPage = filterConfig.getInitParameter("loginPage");
		//从web.xml中获取ignoreURL
		this.ignoreURL = filterConfig.getServletContext().getInitParameter("ignoreURL");
		
		StringTokenizer tokenizer = new StringTokenizer(ignoreURL, ",");
		while (tokenizer.hasMoreElements()) {
			String s = (String) tokenizer.nextElement();
			alIgnoreURL.add(s);
		}

	}
	
	/**
	 * 过滤url和sesion
	 */
	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletrequest;
		HttpServletResponse response = (HttpServletResponse) servletresponse;

		HttpSession session = request.getSession();
		String url = request.getRequestURI().toString();
		
		// 取得操作员信息
		AqCzy op = (AqCzy) session.getAttribute(Constants.CURR_STAFF);
		if ((session == null || null == op) && (!isIgnoreURL(url) && !url.equals(request.getContextPath()) && !url.equals(request.getContextPath()+"/") )) {
			logger.error("来自IP:" + request.getRemoteAddr() + "用户访问页面" + url
					+ "时没有登录!" + session.getId());
			//response.sendRedirect(request.getContextPath() + loginPage);
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=gb2312\">");
			out.println("<head><title>Servlet1</title></head>");
			out.println("<body bgcolor=\"#ffffff\">");
			out.println("<form name='form1' method='post' action="
					+ request.getContextPath() + loginPage
					+ " target=\"_parent\">");
			out.println("</form>");
			out.println("</body></html>");

			out.println("<script language='javascript'>");
			out.println("form1.submit();");
			out.println("</script>");
			return;
		}

		filterChain.doFilter(request, response);
	}

	// Clean up resources
	public void destroy() {
	}
}
