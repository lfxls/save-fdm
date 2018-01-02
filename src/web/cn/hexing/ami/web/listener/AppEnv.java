package cn.hexing.ami.web.listener;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.ServletContext;

/**
 * 存放全局变量
 * @author jun
 *
 */
public class AppEnv implements Serializable {
	private static final long serialVersionUID=1l;
	private static String configFilePath = null;
	private static String webRootPath = null;
	private static ServletContext servletContext=null;
	
	public static String getWebRootPath() {
		return webRootPath;
	}

	public static void setWebRootPath(String webRootPath) {
		AppEnv.webRootPath = webRootPath;
	}

	/**
	 * 存放整个系统的全局对象
	 */
	private static HashMap<Object,Object> configMap = new HashMap<Object,Object>();

	public static void setConfigFilePath(String path) {
		configFilePath = path;
	}

	public static String getConfigFilePath() {
		return configFilePath;
	}

	public static void setObject(Object key, Object obj) {
		configMap.put(key, obj);
	}

	public static Object getObject(Object key) {
		return configMap.get(key);
	}

	public static String getParameter(String key) {
		return (String) configMap.get(key);
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		AppEnv.servletContext = servletContext;
	}
	
	/**
	 * 获取配置目录下指定路径的文件流
	 * @param path
	 * @return
	 */
	public static InputStream getConfigFileStream(String path){
		return AppEnv.getServletContext().getResourceAsStream(path);
	}
}