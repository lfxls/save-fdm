package cn.hexing.ami.web.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hexing.ami.util.StringUtil;

/** 
 * @Description 调用birt报表引擎
 * @author  jun
 * @Copyright 2012 hexing Inc. All rights reserved
 * @time：2012-5-16
 * @version AMI3.0 
 */
public class ReportEngineServlet extends HttpServlet{
	private static final long serialVersionUID = -947633129077381576L;
	private static String reportNameKey = "reportName";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//报表名称，必须写死，包含路径，比如/pos/test 不需要带模板名称
		String reportPath = request.getParameter(reportNameKey);
		
		//报表加载类型 默认frameset 其他preview，run
		String reportType = request.getParameter("reportType");
		if (StringUtil.isEmptyString(reportType)) {
			reportType = "frameset";
		}
		String paramsUrl = "";
		for (Iterator iterator = request.getParameterMap().keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (key.equals(reportNameKey)) {
				continue;
			}
			paramsUrl += "&"+key+"="+request.getParameter(key);
		}
		
		//安全控制 session和权限
		
		
		//调用birt报表引擎 默认为frameset模式
		String url = "/"+reportType+"?__report="+reportPath+".rptdesign&__toolbar=false&__showtitle=false&__title="+paramsUrl;
		request.getRequestDispatcher(url).forward(request, response);
	}
}
