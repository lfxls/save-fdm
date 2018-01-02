package cn.hexing.ami.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 通用txt导出程序，for ext grid
 * @author jun
 */
public class CsvExport {
	
	private static Logger log = Logger.getLogger(CsvExport.class.getName());
	private static String separator = ",";
	
	public static void exportCsv(HttpServletResponse response, String title, 
			String[] headers, String[] keys, List<Object> datas){
		//gird的title加日期为excel文件名
		String fileName = (title==null?"":title)+"_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		}catch (Exception e2) {
			log.warn("设置csv导出编码出现异常" , e2);
		}
		StringBuffer txtBuf = new StringBuffer();
		//输出表头
		int i=0;
		for (String header : headers) {
			if(i!=0)txtBuf.append(separator);
			txtBuf.append(header);
			i++;
		}
		txtBuf.append("\r\n");
		
		//输出表格内容
		for (Object obj : datas) {
			Map<String,Object> map = (Map<String,Object>)obj;
			int j=0;
			for (String key : keys) {
				if(j!=0)txtBuf.append(separator);
				txtBuf.append(String.valueOf(map.get(key)==null?"":map.get(key)));
				j++;
			}
			txtBuf.append("\r\n");
		}
		
		//导出文本
		BufferedOutputStream buff =null;
		try {
			 buff = new BufferedOutputStream(os);  
			 buff.write(txtBuf.toString().getBytes("UTF-8"));  
			 buff.flush();  
		} catch (Exception e) {
			log.error("导出文本["+title+"]出现异常：" , e);
		}finally {
			try {
				buff.close();
				os.close();
			} catch (Exception e) {
				log.warn("关闭输出流出错", e);
			}
		}
	}
}
