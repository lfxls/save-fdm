package cn.hexing.ami.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * 通用txt导出程序，for ext grid
 * @author jun
 *
 */
public class TxtExportForExtGrid {
	
	private static Logger log = Logger.getLogger(TxtExportForExtGrid.class.getName());
	public void exportTxt(HttpServletRequest request,HttpServletResponse response,List rootList){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
		
		String title = request.getParameter("excel.gridTitle");
		title = title==null?"":title;
		//去掉查询时间部分 格式如：Load Data(The query took_0.02 seconds)
		if (title.indexOf("(")!=-1) {
			title = title.substring(0,title.indexOf("("));
		}
		
		//在IE6下文件名只要超过17个字，就会报：无法下载或者无法复制等错误，导致无法下载，去掉mmddHHmm8字符，还有9个字符
		if (title.length()>9) {
			title = title.substring(0,9);
		}
		String fileName = ServletActionContext.getRequest().getParameter("excel.fileName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//gird的title加日期为excel文件名
		fileName = StringUtils.isBlank(fileName) ? title+"_"+(sdf.format(new Date()) + ".txt"): fileName;
		
		Map<String, String> headers = new LinkedHashMap<String, String>();
		Map<String, String> types = new HashMap<String, String>();
		//获取表头列表
		String headerStr = request.getParameter("excel.gridHeaders");
		if (StringUtils.isNotBlank(headerStr)) {
			headers = com.alibaba.fastjson.JSON.parseObject(headerStr,LinkedHashMap.class);
			//去掉操作列，固定键值OP
			headers.remove("OP");
		}
		
		//表头显示或者隐藏的状态
		String[] headerStatus = new String[headers.size()];
		
		Map<String, String> newHeaders = new LinkedHashMap<String, String>();
		int num = 0;
		//去除隐藏列
		for (Entry<String, String> en : headers.entrySet()) {
			String cellText = en.getValue();
			String cellValue = "";
			if (cellText.indexOf(ExcelExportForExtGrid.splitFlag)!=-1) {
				String[] cellTextArr = cellText.split(ExcelExportForExtGrid.splitFlag);
				cellValue = cellTextArr[0];
				if (cellTextArr[1].equals("show")) {
					newHeaders.put(en.getKey(), cellValue);
				}
				headerStatus[num++] = cellTextArr[1];
			}
		}
		
		//列类型
		String typesStr = request.getParameter("excel.gridTypes");
		if (StringUtils.isNotBlank(typesStr)) {
			types = com.alibaba.fastjson.JSON.parseObject(typesStr,LinkedHashMap.class);
		}
		
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		}catch (Exception e2) {
			log.equals("设置excel导出编码出现异常："+CommonUtil.getExceptionDetailInfo(e2));
		}
		
		StringBuffer txtBuf = new StringBuffer();
		Set<Entry<String, String>> hs = newHeaders.entrySet();
		
		//获取非组合表头的显示属性
		for (Entry<String, String> en : newHeaders.entrySet()) {
			String cellText = en.getValue();
			String cellValue = "";
			if (cellText.indexOf(ExcelExportForExtGrid.splitFlag)!=-1) {
				String[] cellTextArr = cellText.split(ExcelExportForExtGrid.splitFlag);
				cellValue = cellTextArr[0];
				if (cellTextArr[1].equals("show")) {
					newHeaders.put(en.getKey(), cellValue);
				}
			}
		}
		
		//获取组合表头
		String groupHeaderStr = request.getParameter("excel.gridGrouupHeaders");
		if (!StringUtil.isEmptyString(groupHeaderStr)) {
			//有效列
			int validCol = 0;
			//总列数
			int totalCol = 0;
			
			//groupHeaderStr格式:表头1|跨列数,表头2|跨列数
			String[] groups = groupHeaderStr.split(",");
			for (int i = 0; i < groups.length; i++) {
				String[] items = groups[i].split("~");
				
				//跨列
				int curCol = Integer.parseInt(items[1]);
				//最后一列配置跨列配置错误，用总列数减去前面的列数
				if (i==groups.length-1) {
					if (hs.size()-validCol<curCol) {
						curCol = hs.size()-validCol;
					}
				}
				
				totalCol += curCol;
				//如果组合列对应列中有隐藏列，则组合列不显示
				if(headerStatus[totalCol-1].equals("hide")){
					continue;
				}
				
				txtBuf.append(items[0]);
				int col = Integer.parseInt(items[1]);
				for (int j = 0; j < col-1; j++) {
					txtBuf.append("\t\t\t");
				}
				validCol += curCol;
			}
			txtBuf.append("\r\n");
		}
		
		//输出表头
		for (Entry<String, String> en : hs) {
			if(!"序号".equals(en.getValue()) && !"NO".equals(en.getValue().toUpperCase())){
				txtBuf.append(en.getValue()+"\t\t");
			}
		}
		txtBuf.append("\r\n");
		
		//输出表格内容
		int j = 1;
		if(rootList!=null){
			for (Object obj : rootList) {
				for (Entry<String, String> en : hs) {
					String key = en.getKey();
					String parseValue = ExcelExportForExtGrid.parseObj(obj, key);
					txtBuf.append(parseValue==null?"--"+"\t\t":parseValue.trim()+"\t\t");
				}
				txtBuf.append("\r\n");
				j++;
			}
		}
		
		//导出文本
		BufferedOutputStream buff =null;
		try {
			 buff = new BufferedOutputStream(os);  
			 buff.write(txtBuf.toString().getBytes("UTF-8"));  
			 buff.flush();  
		} catch (Exception e) {
			log.error("导出文本["+title+"]出现异常："+CommonUtil.getExceptionDetailInfo(e));
		}finally {
			try {
				buff.close();
				os.close();
			} catch (Exception e) {
				log.error(StringUtil.getExceptionDetailInfo(e));
			}
		}
	}
}
