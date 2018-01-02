package cn.hexing.ami.util;

import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.DateFormats;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.fusioncharts.exporter.beans.ExportConfiguration;

/**
 * 通用excel导出程序，for ext grid
 * @author jun
 *
 */
public class ExcelExportForExtGrid {
	public static String splitFlag = ",";
	private static Logger logger = Logger.getLogger(ExcelExportForExtGrid.class.getName());
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,List rootList,String[] excelTextList){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.getExceptionDetailInfo(e));
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
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
		//gird的title加日期为excel文件名
		fileName = StringUtils.isBlank(fileName) ? title+"_"+(sdf.format(new Date()) + ".xls"): fileName;
		title = StringUtils.isBlank(title) ? "sheet1" : title;
		
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
			if (cellText.indexOf(splitFlag)!=-1) {
				String[] cellTextArr = cellText.split(splitFlag);
				cellValue = cellTextArr[0];
				if (cellTextArr[1].equals("show")) {
					newHeaders.put(en.getKey(), cellValue);
				}
				headerStatus[num++] = cellTextArr[1];
			}
		}
		
		//转换列类型
		String typesStr = request.getParameter("excel.gridTypes");
		if (StringUtils.isNotBlank(typesStr)) {
			types = com.alibaba.fastjson.JSON.parseObject(typesStr,LinkedHashMap.class);
		}
		
		
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gbk"), "ISO8859-1"));
			
			/*response.setHeader("Content-Transfer-Encoding","binary");     
			response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Pragma", "no-cache");*/
		}catch (Exception e2) {
			logger.error("设置excel导出编码出现异常："+CommonUtil.getExceptionDetailInfo(e2));
		}
		
		WritableWorkbook wc = null;
		try {
			WorkbookSettings settings = new WorkbookSettings();
			settings.setEncoding("UTF-8");
			wc = Workbook.createWorkbook(os, settings);
			WritableSheet ws = wc.createSheet(title, 0);
			Set<Entry<String, String>> hs = newHeaders.entrySet();
			
			int startRow = 0;
			//插入chart图片
			String exportedChartFileNames = request.getParameter("exportedChartFileNames");//获取图片名称
			if(exportedChartFileNames!=null){
				String[] imageNames = exportedChartFileNames.split(",");
				double widthCol = 6 , heightRow = 10;
				int colIndex =0 , rowIndex = 0;
				String dirPath = ExportConfiguration.SAVEABSOLUTEPATH;
				//创建目录
				FileUtil.mkdirs(dirPath);
				//删除历史图片问题，今天以前的所有图片
				delHistoryPic(dirPath);
				//excel插入图片
				for(String imageName : imageNames){
					File imgFile = new File(dirPath + File.separator + imageName);
					WritableImage image = new WritableImage(colIndex, rowIndex, widthCol, heightRow, imgFile); //从A1开始，8列宽，20行高
					ws.addImage(image);
					rowIndex+=heightRow;
				}
				startRow = rowIndex;
			}
	            
			
			//获取非组合表头的显示属性
			for (Entry<String, String> en : newHeaders.entrySet()) {
				String cellText = en.getValue();
				String cellValue = "";
				if (cellText.indexOf(splitFlag)!=-1) {
					String[] cellTextArr = cellText.split(splitFlag);
					cellValue = cellTextArr[0];
					if (cellTextArr[1].equals("show")) {
						newHeaders.put(en.getKey(), cellValue);
					}
				}
			}
			
			//获取组合表头
			String groupHeaderStr = request.getParameter("excel.gridGrouupHeaders");
			if (!StringUtil.isEmptyString(groupHeaderStr)) {
				//groupHeaderStr格式:表头1|跨列数,表头2|跨列数
				String[] groups = groupHeaderStr.split(",");
				//有效列
				int validCol = 0;
				//总列数
				int totalCol = 0;
				for (int i = 0; i < groups.length; i++) {
					String[] items = null;
					if (groups[i].indexOf("~")==-1) {
						items = new String[2];
						items[0] = groups[i];
						items[1] = "1";
					}else{
						items = groups[i].split("~");
					}
					 
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
					
					WritableFont wf = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD, false);
					WritableCellFormat wcf = new WritableCellFormat(wf);
					//设置背景色
					wcf.setBackground(Colour.PALE_BLUE);
					//设置边框线
					wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
					//设置居中
					wcf.setAlignment(Alignment.CENTRE);
					
					
					
					
					
					
					Label lb = new Label(validCol, startRow, items[0],wcf);
					ws.addCell(lb); 
					
					ws.mergeCells(validCol,startRow, validCol+curCol-1, startRow);
					validCol += curCol;
				}
				startRow += 1;
			}
			
////////////////////////////excel 表头///////////////////////////////
	if(excelTextList!=null){
	WritableFont wfExcel = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD, false);
	WritableCellFormat wcfExcel = new WritableCellFormat(wfExcel);
	//设置背景色
	wcfExcel.setBackground(Colour.WHITE);
	//设置边框线
	wcfExcel.setBorder(Border.ALL, BorderLineStyle.THIN);
	//设置居中
	wcfExcel.setAlignment(Alignment.CENTRE);
	//换行
	wcfExcel.setWrap(true);
	
	int excelTextListLength = excelTextList.length;
	String excelTextListString ="";
	
	for(int i = 0;i<excelTextListLength;i++){
		if(i!=excelTextListLength-1){
			excelTextListString = excelTextListString+excelTextList[i]+"\n";
		}else{
			excelTextListString = excelTextListString+excelTextList[i];
		}
	}


	Label excelTitleLabel = new Label(0,startRow,excelTextListString,wcfExcel);
	ws.addCell(excelTitleLabel); 
	ws.mergeCells(0,startRow, hs.size()-1, startRow+excelTextListLength*2);
	startRow+=excelTextListLength*2+1;
	}
    ////////////////////////////excel 表头///////////////////////////////
			
			int i=0;
			//表头
			for (Entry<String, String> en : hs) {
				//11号字体，粗体
				WritableFont wf = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD, false);
				WritableCellFormat wcf = new WritableCellFormat(wf);
				//设置背景色
				wcf.setBackground(Colour.PALE_BLUE);
				//设置边框线
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
				//设置居中
				wcf.setAlignment(Alignment.CENTRE);
				//列的宽度
				//序号列，控制在5个字符 同时去掉操作栏
				if("序号".equals(en.getValue()) || "NO".equals(en.getValue().toUpperCase())){
					ws.setColumnView(i, 5);
				}else{
					ws.setColumnView(i, 22);
				}
			
				Label lb = new Label(i++, startRow, en.getValue(),wcf);
				ws.addCell(lb);
			}
			//设置列宽
			
			//输出表格内容
			if(rootList!=null){
				for (Object obj : rootList) {
					startRow+=1;
					i = 0;
					for (Entry<String, String> en : hs) {
						String key = en.getKey();
						String parseValue = parseObj(obj, key);
						
						//10号字体
						WritableFont wf = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, false);
						WritableCellFormat wcf = null;
						//设置单元格类型
						if (null != parseValue) {
							String type = types.get(key);
							if ("number".equalsIgnoreCase(type)) {
								DisplayFormat format = NumberFormats.FLOAT;
								wcf = new WritableCellFormat(wf,format);
							} else if ("date".equalsIgnoreCase(type)) {
								DisplayFormat format = DateFormats.DEFAULT;
								wcf = new WritableCellFormat(wf,format);
							}else{
								wcf = new WritableCellFormat(wf);
							}
						}else{
							wcf = new WritableCellFormat(wf);
							
							//如果没有指明类型，又是数字字符串，进行格式化
							try {
								Double.parseDouble(parseValue);
								DisplayFormat format = NumberFormats.FLOAT;
								wcf = new WritableCellFormat(wf,format);
							} catch (Exception e) {
								
							}
						}
						
						//设置背景色，斑马线效果
						if (startRow%2==0) {
							wcf.setBackground(Colour.LIGHT_TURQUOISE);
						}
						//设置边框线
						wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
						//设置居中
						wcf.setAlignment(Alignment.CENTRE);
						
						Label lb = new Label(i++, startRow, parseValue,wcf);
						ws.addCell(lb);
					}
				}
			}
			wc.write();
		} catch (Exception e) {
			logger.error("导出Excel["+title+"]出现异常："+CommonUtil.getExceptionDetailInfo(e));
		}finally {
			if (wc != null) {
				try {
					wc.close();
					os.close();
				} catch (Exception e) {
					logger.error(StringUtil.getExceptionDetailInfo(e));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String parseObj(Object obj, String key) {
		if (null == obj) {
			return "";
		}
		if (StringUtils.isBlank(key)) {
			return "";
		}
		if (obj instanceof Map) {
			Map mm = (Map) obj;
			Object v = mm.get(key);
			if (v instanceof Date) {
				Date date = (Date) v;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				return sdf.format(date);
			} else {
				return v == null ? "" : v.toString();
			}
		}
		//如果为json对象字符串
		if (obj instanceof String) {
			JSONObject jasonObject = JSONObject.fromObject(obj);
			Map objMap = (Map)jasonObject;
			Object v = objMap.get(key);
			if (v instanceof Date) {
				Date date = (Date) v;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				return sdf.format(date);
			} else {
				return v == null ? "" : v.toString();
			}
		}
		
		//为pojo对象
		PropertyDescriptor now = BeanUtils.getPropertyDescriptor(
				obj.getClass(), key);
		if (now == null) {
			return "";
		}
		Method rm = now.getReadMethod();
		// .getReadMethod();
		if (null != rm) {
			try {
				Object v = rm.invoke(obj, new Object[] {});
				if (v instanceof Date) {
					Date date = (Date) v;
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					return sdf.format(date);
				} else {
					return v == null ? "" : v.toString();
				}
			} catch (Exception e) {
				logger.error(StringUtil.getExceptionDetailInfo(e));
			}
		}
		return "";
	}
	
	/**
	 * 删除历史图片，昨天之前的图片
	 */
	public static void delHistoryPic(String dirPath){
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			long date = files[i].lastModified();
			Calendar cal=Calendar.getInstance();
			cal.setTimeInMillis(date);
			String pattern = "yyyyMMdd";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			
			String fileDate = sdf.format(cal.getTime());
			//昨天
			cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 1);
			String curDate = sdf.format(cal.getTime());
			if (Long.parseLong(fileDate)<Long.parseLong(curDate)) {
				files[i].delete();
			}
		}
	}
}
