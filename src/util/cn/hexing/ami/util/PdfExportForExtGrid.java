package cn.hexing.ami.util;

import java.awt.Color;
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

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 通用PDF导出程序，for ext grid
 * @author jun
 *
 */
public class PdfExportForExtGrid {
	private static Logger logger = Logger.getLogger(PdfExportForExtGrid.class.getName());
	public void exportPdf(HttpServletRequest request,HttpServletResponse response,List rootList){
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//gird的title加日期为excel文件名
		fileName = StringUtils.isBlank(fileName) ? title+"_"+(sdf.format(new Date()) + ".pdf"): fileName;
		
		
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
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		}catch (Exception e2) {
			logger.equals("设置pdf导出编码出现异常："+CommonUtil.getExceptionDetailInfo(e2));
		}
		
		String pageSize = "A4"; 
		//下边距  
		float margin_bottom = 20; 
		float margin_left = 10;  
		float margin_top = 20;  
		float margin_right = 10;
		Rectangle rectangle = null;  
		//自动计算文档的大小，每个列大小为70px，宽度794为A4纸的高度，列数小于10列的情况，直接使用A纸
		if (headers.size()<=10) {
			rectangle = new Rectangle(getPdfPageSize(pageSize)); 
		}else{
			rectangle = new Rectangle(headers.size()*70,794);
		}

		Document document = new Document(rectangle,margin_bottom,margin_left,margin_right,margin_top);
		
		try{
			PdfWriter.getInstance(document, os);

			// 基本配置信息
			document.addTitle(title);
			document.addAuthor("Hexing");
			// 打开pdf文档
			document.open();

			// 定义页眉和页脚
			String pageHeader = "";
			String pageFooter = "";
			HeaderFooter header = null;
			HeaderFooter footer = null;
			if (pageHeader != null) {

				header = new HeaderFooter(new Paragraph(pageHeader), false);
				header.setBorderWidth(0);
				header.setAlignment(Element.ALIGN_CENTER);
			}
			if (pageFooter != null) {

				footer = new HeaderFooter(new Paragraph(pageFooter), false);
				footer.setBorderWidth(0);
				footer.setAlignment(Element.ALIGN_CENTER);
			}
			document.setHeader(header);
			document.setFooter(footer);
			

			Set<Entry<String, String>> hs = newHeaders.entrySet();

			String[] headersArray = new String[newHeaders.size()];
			int i = 0;
			// 表头
			for (Entry<String, String> en : hs) {
				headersArray[i++] = en.getValue();
			}

			// 创建多少列的表格
			float[] widths = new float[headersArray.length];
			for (int j = 0; j < widths.length; j++) {
				 widths[j] = 100f;
			}
			PdfPTable table = new PdfPTable(widths);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			//设置表格大小为可用空白区域的98%
			table.setWidthPercentage(98);

			Font font = null;
			PdfPCell cell = null;
			
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
				for (i = 0; i < groups.length; i++) {
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
					
					BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置中文字体  
					//中文大小为11，加粗  
					font = new Font(bfChinese, 11, Font.BOLD);  
					cell = new PdfPCell(new Paragraph(items[0],font));
					cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
					//保持和excel相同颜色
					cell.setBackgroundColor(new Color(153,204,255));
					cell.setBorderColor(Color.black);
					
					//跨列
					cell.setColspan(curCol);
					table.addCell(cell);
					validCol += curCol;
				}
			}
			
			// 产生表头栏
			for (i = 0; i < headersArray.length; i++) {
				BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置中文字体  
				//中文大小为11，加粗  
				font = new Font(bfChinese, 11, Font.BOLD);  
				cell = new PdfPCell(new Paragraph(headersArray[i],font));
				cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
				//保持和excel相同颜色
				cell.setBackgroundColor(new Color(153,204,255));
				cell.setBorderColor(Color.black);
				table.addCell(cell);
			}

			// 输出表格内容
			int j = 1;
			if (rootList != null) {
				for (Object obj : rootList) {
					for (Entry<String, String> en : hs) {
						String key = en.getKey();
						String parseValue = ExcelExportForExtGrid.parseObj(obj,
								key);
						
						BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置中文字体  
						//中文大小为10，加粗  
						font = new Font(bfChinese, 10, Font.NORMAL);  
						
						cell = new PdfPCell(
								new Paragraph(parseValue.toString(),font));
						cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
						cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
						cell.setBorderColor(Color.black);
						//保持和excel相同颜色
						if (j%2==0) {
							cell.setBackgroundColor(new Color(204,255,255));
						}else{
							cell.setBackgroundColor(Color.white);
						}
						
						table.addCell(cell);
					}
					j++;
				}
			}
			document.add(table);  

		} catch (Exception e) {
			logger.error("导出pdf["+title+"]出现异常："+CommonUtil.getExceptionDetailInfo(e));
		}finally {
			try {
				document.close();
				os.close();
			} catch (Exception e) {
				logger.error(StringUtil.getExceptionDetailInfo(e));
			}
		}
	}
	
	/**
	 * 设置纸张的类型
	 * @param pageSize
	 * @return
	 */
	public Rectangle getPdfPageSize(String pageSize) {
		Rectangle pSize = null;
		if ("A4".equals(pageSize)) {
			pSize = PageSize.A4;
		} else if ("A3".equals(pageSize)) {
			pSize = PageSize.A3;
		} else if ("A2".equals(pageSize)) {
			pSize = PageSize.A2;
		} else if ("A1".equals(pageSize)) {
			pSize = PageSize.A1;
		} else {
			pSize = PageSize.A4;
		}
		return pSize;
	}
}

